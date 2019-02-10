package io.jochimsen.cahapp.backend;

import android.util.Log;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.jochimsen.cahapp.backend.local.entity.white_card.WhiteCard;
import io.jochimsen.cahapp.backend.local.entity.white_card.WhiteCardDao;
import io.jochimsen.cahapp.backend.local.entity.white_card.WhiteCardsHash;
import io.jochimsen.cahapp.backend.webservice.request.CheckHashRequest;
import io.jochimsen.cahapp.backend.webservice.response.CheckHashResponse;
import io.jochimsen.cahapp.backend.webservice.response.HashResponse;
import io.jochimsen.cahapp.backend.webservice.api.WhiteCardApi;
import io.jochimsen.cahapp.backend.webservice.response.WhiteCardResponse;
import io.jochimsen.cahapp.di.scope.AppScope;
import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

@AppScope
public class WhiteCardRepository {
    private final WhiteCardDao whiteCardDao;
    private final WhiteCardApi whiteCardApi;

    private static final String TAG = "WhiteCardRepository";

    @Inject
    public WhiteCardRepository(final WhiteCardDao whiteCardDao, final WhiteCardApi whiteCardApi) {
        this.whiteCardDao = whiteCardDao;
        this.whiteCardApi = whiteCardApi;
    }

    public Disposable synchronize(final Action action, final Scheduler scheduler) {
        return whiteCardDao.getHash()
                .subscribeOn(Schedulers.io())
                .defaultIfEmpty(new WhiteCardsHash())
                .observeOn(Schedulers.io())
                .subscribe(
                        whiteCardsHash -> {
                            final Integer hash = whiteCardsHash.getHash();
                            boolean needsSynchronization;

                            if(hash != null) {
                                final CheckHashResponse checkHashResponse = whiteCardApi.checkHash(CheckHashRequest.create(hash)).blockingGet();
                                needsSynchronization = !checkHashResponse.hashEqual;
                            } else {
                                needsSynchronization = true;
                            }

                            if(needsSynchronization) {
                                final HashResponse<List<WhiteCardResponse>> hashResponse = whiteCardApi.getWhiteCards().blockingGet();

                                final List<WhiteCard> whiteCards = hashResponse.data.stream()
                                        .map(whiteCardResponse -> new WhiteCard(whiteCardResponse.whiteCardId, whiteCardResponse.text))
                                        .collect(Collectors.toList());

                                whiteCardDao.delete();
                                whiteCardDao.save(whiteCards);

                                whiteCardDao.deleteHash();
                                whiteCardDao.saveHash(new WhiteCardsHash(hashResponse.hash));
                            }

                            Completable.fromAction(action)
                                    .subscribeOn(scheduler)
                                    .subscribe();
                        },
                        throwable -> Log.d(TAG, throwable.getMessage())
                );
    }
}