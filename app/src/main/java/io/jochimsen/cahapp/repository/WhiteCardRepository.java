package io.jochimsen.cahapp.repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.EmptyResultSetException;
import android.support.annotation.MainThread;
import android.support.annotation.WorkerThread;
import android.util.Log;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.jochimsen.cahapp.backend.local.entity.white_card.WhiteCard;
import io.jochimsen.cahapp.backend.local.entity.white_card.WhiteCardDao;
import io.jochimsen.cahapp.backend.local.entity.white_card.WhiteCardsHash;
import io.jochimsen.cahapp.backend.ressource.NetworkBoundResource;
import io.jochimsen.cahapp.backend.ressource.Resource;
import io.jochimsen.cahapp.backend.webservice.api.WhiteCardApi;
import io.jochimsen.cahapp.backend.webservice.request.CheckHashRequest;
import io.jochimsen.cahapp.backend.webservice.response.CheckHashResponse;
import io.jochimsen.cahapp.backend.webservice.response.HashResponse;
import io.jochimsen.cahapp.backend.webservice.response.WhiteCardResponse;
import io.jochimsen.cahapp.di.scope.AppScope;
import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AppScope
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class WhiteCardRepository {
    private final WhiteCardDao whiteCardDao;
    private final WhiteCardApi whiteCardApi;
    private NetworkBoundResource<List<WhiteCard>, HashResponse<List<WhiteCardResponse>>> networkBoundResource;

    public LiveData<Resource<List<WhiteCard>>> getWhiteCards() {
        if(networkBoundResource == null) {
            networkBoundResource = new NetworkBoundResource<List<WhiteCard>, HashResponse<List<WhiteCardResponse>>>() {
                @WorkerThread
                @Override
                protected boolean shouldFetch() {
                    try {
                        final WhiteCardsHash whiteCardsHash = whiteCardDao.getHash().blockingGet();
                        final CheckHashResponse checkHashResponse = whiteCardApi.checkHash(CheckHashRequest.create(whiteCardsHash.getHash())).blockingGet();

                        return !checkHashResponse.isHashEqual();
                    } catch (final EmptyResultSetException e) {
                        return true;
                    }
                }

                @MainThread
                @Override
                protected Single<HashResponse<List<WhiteCardResponse>>> createCall() {
                    return whiteCardApi.getWhiteCards();
                }

                @WorkerThread
                @Override
                protected List<WhiteCard> processResponse(final HashResponse<List<WhiteCardResponse>> response) {
                    whiteCardDao.deleteHash();
                    whiteCardDao.saveHash(new WhiteCardsHash(response.getHash()));

                    return response.getData().stream()
                            .map(whiteCardResponse -> new WhiteCard(whiteCardResponse.getWhiteCardId(), whiteCardResponse.getText()))
                            .collect(Collectors.toList());
                }

                @WorkerThread
                @Override
                protected void persistResult(final List<WhiteCard> result) {
                    whiteCardDao.save(result);
                }

                @Override
                protected LiveData<List<WhiteCard>> fetchFromDb() {
                    return whiteCardDao.get();
                }
            };
        }

        return networkBoundResource.asLiveData();
    }
}
