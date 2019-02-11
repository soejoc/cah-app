package io.jochimsen.cahapp.backend;

import android.util.Log;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.jochimsen.cahapp.backend.local.entity.black_card.BlackCard;
import io.jochimsen.cahapp.backend.local.entity.black_card.BlackCardDao;
import io.jochimsen.cahapp.backend.local.entity.black_card.BlackCardsHash;
import io.jochimsen.cahapp.backend.webservice.api.BlackCardApi;
import io.jochimsen.cahapp.backend.webservice.response.BlackCardResponse;
import io.jochimsen.cahapp.backend.webservice.request.CheckHashRequest;
import io.jochimsen.cahapp.backend.webservice.response.CheckHashResponse;
import io.jochimsen.cahapp.backend.webservice.response.HashResponse;
import io.jochimsen.cahapp.di.scope.AppScope;
import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import lombok.AllArgsConstructor;

@AppScope
@AllArgsConstructor(onConstructor = @__({@Inject}))
public class BlackCardRepository {
    private static final String TAG = "WhiteCardRepository";

    private final BlackCardDao blackCardDao;
    private final BlackCardApi blackCardApi;

    public Disposable synchronize(final Action action, final Scheduler scheduler) {
        return blackCardDao.getHash()
                .subscribeOn(Schedulers.io())
                .defaultIfEmpty(new BlackCardsHash())
                .observeOn(Schedulers.io())
                .subscribe(
                        blackCardsHash -> {
                            final Integer hash = blackCardsHash.getHash();
                            boolean needsSynchronization;

                            if(hash != null) {
                                final CheckHashResponse checkHashResponse = blackCardApi.checkHash(CheckHashRequest.create(hash)).blockingGet();
                                needsSynchronization = !checkHashResponse.isHashEqual();
                            } else {
                                needsSynchronization = true;
                            }

                            if(needsSynchronization) {
                                final HashResponse<List<BlackCardResponse>> hashResponse = blackCardApi.getBlackCards().blockingGet();

                                final List<BlackCard> blackCards = hashResponse.getData().stream()
                                        .map(blackCardResponse -> new BlackCard(blackCardResponse.getBlackCardId(), blackCardResponse.getText(), blackCardResponse.getBlankCount()))
                                        .collect(Collectors.toList());

                                blackCardDao.delete();
                                blackCardDao.save(blackCards);

                                blackCardDao.deleteHash();
                                blackCardDao.saveHash(new BlackCardsHash(hashResponse.getHash()));
                            }

                            Completable.fromAction(action)
                                    .subscribeOn(scheduler)
                                    .subscribe();
                        },
                        throwable -> Log.d(TAG, throwable.getMessage())
                );
    }
}
