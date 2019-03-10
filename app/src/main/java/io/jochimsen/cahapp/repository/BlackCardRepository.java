package io.jochimsen.cahapp.repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.EmptyResultSetException;
import android.support.annotation.MainThread;
import android.support.annotation.WorkerThread;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.jochimsen.cahapp.backend.local.entity.black_card.BlackCard;
import io.jochimsen.cahapp.backend.local.entity.black_card.BlackCardDao;
import io.jochimsen.cahapp.backend.local.entity.black_card.BlackCardsHash;
import io.jochimsen.cahapp.backend.ressource.Resource;
import io.jochimsen.cahapp.backend.webservice.api.BlackCardApi;
import io.jochimsen.cahapp.backend.webservice.request.CheckHashRequest;
import io.jochimsen.cahapp.backend.webservice.response.BlackCardResponse;
import io.jochimsen.cahapp.backend.webservice.response.CheckHashResponse;
import io.jochimsen.cahapp.backend.webservice.response.HashResponse;
import io.jochimsen.cahapp.di.scope.AppScope;
import io.jochimsen.cahapp.backend.ressource.NetworkBoundResource;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;

@AppScope
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class BlackCardRepository {
    private final BlackCardDao blackCardDao;
    private final BlackCardApi blackCardApi;
    private NetworkBoundResource<List<BlackCard>, HashResponse<List<BlackCardResponse>>> networkBoundResource;

    public LiveData<Resource<List<BlackCard>>> getBlackCards() {
        if(networkBoundResource == null) {
            networkBoundResource = new NetworkBoundResource<List<BlackCard>, HashResponse<List<BlackCardResponse>>>() {
                @Override
                @WorkerThread
                protected boolean shouldFetch() {
                    try {
                        final BlackCardsHash blackCardsHash = blackCardDao.getHash().blockingGet();
                        final CheckHashResponse checkHashResponse = blackCardApi.checkHash(CheckHashRequest.create(blackCardsHash.getHash())).blockingGet();

                        return !checkHashResponse.isHashEqual();
                    } catch (final EmptyResultSetException e) {
                        return true;
                    }
                }

                @Override
                @MainThread
                protected Single<HashResponse<List<BlackCardResponse>>> createCall() {
                    return blackCardApi.getBlackCards();
                }

                @Override
                @WorkerThread
                protected List<BlackCard> processResponse(final HashResponse<List<BlackCardResponse>> response) {
                    blackCardDao.deleteHash();
                    blackCardDao.saveHash(new BlackCardsHash(response.getHash()));

                    return response.getData().stream()
                            .map(blackCardResponse -> new BlackCard(blackCardResponse.getBlackCardId(), blackCardResponse.getText(), blackCardResponse.getBlankCount()))
                            .collect(Collectors.toList());
                }

                @Override
                @WorkerThread
                protected void persistResult(final List<BlackCard> result) {
                    blackCardDao.save(result);
                }

                @Override
                protected LiveData<List<BlackCard>> fetchFromDb() {
                    return blackCardDao.get();
                }
            };
        }

        return networkBoundResource.asLiveData();
    }
}
