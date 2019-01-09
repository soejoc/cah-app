package io.jochimsen.cahapp.repository;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.jochimsen.cahapp.backend.local.entity.white_card.WhiteCard;
import io.jochimsen.cahapp.model.BlackCardModel;
import io.jochimsen.cahapp.model.MergedCardModel;
import io.jochimsen.cahapp.network.handler.MessageSubject;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class MergedCardRepository {
    private final MutableLiveData<List<MergedCardModel>> mergedCardModelsLiveData = new MutableLiveData<>();

    @SuppressLint("CheckResult")
    @Inject
    public MergedCardRepository(final AvailableCardsRepository availableCardsRepository, final WhiteCardRepository whiteCardRepository) {
        MessageSubject.selectCardResponseSubject
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(selectCardResponse -> {
                    final BlackCardModel blackCardModel = availableCardsRepository.getBlackCardModelLiveData().getValue();

                    List<MergedCardModel> mergedCardModels = new ArrayList<>();

                    for(final io.jochimsen.cahframework.protocol.object.model.WhiteCardModel whiteCardModel : selectCardResponse.whiteCardModelList) {
                        final WhiteCard whiteCard = whiteCardRepository.get(whiteCardModel.whiteCardId).blockingGet();

                        String mergedCardText = blackCardModel.getText();

                        if(mergedCardText.contains("_")) {
                            mergedCardText.replace("_", whiteCard.getText());
                        } else {
                            mergedCardText += " " + whiteCard.getText();
                        }

                        mergedCardModels.add(new MergedCardModel(mergedCardText, whiteCard.getWhiteCardId()));
                    }

                    mergedCardModelsLiveData.postValue(mergedCardModels);
                });
    }

    public LiveData<List<MergedCardModel>> getMergedCardModelsLiveData() {
        return mergedCardModelsLiveData;
    }
}
