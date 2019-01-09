package io.jochimsen.cahapp.repository;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.jochimsen.cahapp.model.BlackCardModel;
import io.jochimsen.cahapp.model.WhiteCardModel;
import io.jochimsen.cahapp.network.handler.MessageSubject;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class AvailableCardsRepository {
    private final MutableLiveData<List<WhiteCardModel>> whiteCardModelsLiveData = new MutableLiveData<>();
    private final MutableLiveData<BlackCardModel> blackCardModelLiveData = new MutableLiveData<>();

    @SuppressLint("CheckResult")
    @Inject
    public AvailableCardsRepository(final WhiteCardRepository whiteCardRepository, final BlackCardRepository blackCardRepository) {
        MessageSubject.newRoundResponseSubject
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(newRoundResponse -> {
                    final BlackCardModel blackCardModel = new BlackCardModel(newRoundResponse.blackCardModel.blackCardId, blackCardRepository.get(newRoundResponse.blackCardModel.blackCardId).blockingGet().getText());
                    List<WhiteCardModel> whiteCardModels = whiteCardModelsLiveData.getValue();

                    if(whiteCardModels == null) {
                        whiteCardModels = new ArrayList<>();
                    }

                    for(final io.jochimsen.cahframework.protocol.object.model.WhiteCardModel whiteCardModel : newRoundResponse.whiteCardModels) {
                        whiteCardModels.add(new WhiteCardModel(whiteCardModel.whiteCardId, whiteCardRepository.get(whiteCardModel.whiteCardId).blockingGet().getText()));
                    }

                    blackCardModelLiveData.postValue(blackCardModel);
                    whiteCardModelsLiveData.postValue(whiteCardModels);
                });
    }

    public LiveData<List<WhiteCardModel>> getWhiteCardModelsLiveData() {
        return whiteCardModelsLiveData;
    }

    public LiveData<BlackCardModel> getBlackCardModelLiveData() {
        return blackCardModelLiveData;
    }
}
