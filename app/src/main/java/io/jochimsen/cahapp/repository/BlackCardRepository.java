package io.jochimsen.cahapp.repository;

import android.util.Log;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.jochimsen.cahapp.backend.local.entity.black_card.BlackCard;
import io.jochimsen.cahapp.backend.local.entity.black_card.BlackCardDao;
import io.jochimsen.cahapp.backend.local.entity.black_card.BlackCardsHash;
import io.jochimsen.cahapp.backend.webservice.black_card.BlackCardController;
import io.jochimsen.cahapp.backend.webservice.black_card.response.BlackCardResponse;
import io.jochimsen.cahapp.backend.webservice.global.response.CheckHashResponse;
import io.jochimsen.cahapp.backend.webservice.global.response.HashResponse;
import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class BlackCardRepository {
    private final BlackCardDao blackCardDao;
    private final BlackCardController blackCardController;

    private static final String TAG = "WhiteCardRepository";

    @Inject
    public BlackCardRepository(final BlackCardDao blackCardDao, final BlackCardController blackCardController) {
        this.blackCardDao = blackCardDao;
        this.blackCardController = blackCardController;
    }

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
                                final CheckHashResponse checkHashResponse = blackCardController.checkHash(hash).blockingGet();
                                needsSynchronization = !checkHashResponse.hashEqual;
                            } else {
                                needsSynchronization = true;
                            }

                            if(needsSynchronization) {
                                final HashResponse<List<BlackCardResponse>> hashResponse = blackCardController.getBlackCards().blockingGet();

                                final List<BlackCard> blackCards = hashResponse.data.stream()
                                        .map(blackCardResponse -> new BlackCard(blackCardResponse.blackCardId, blackCardResponse.text))
                                        .collect(Collectors.toList());

                                blackCardDao.delete();
                                blackCardDao.save(blackCards);

                                blackCardDao.deleteHash();
                                blackCardDao.saveHash(new BlackCardsHash((hashResponse.hash)));
                            }

                            Completable.fromAction(action)
                                    .subscribeOn(scheduler)
                                    .subscribe();
                        },
                        throwable -> Log.d(TAG, throwable.getMessage())
                );
    }
}