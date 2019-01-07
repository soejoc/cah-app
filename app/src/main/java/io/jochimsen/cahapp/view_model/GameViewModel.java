package io.jochimsen.cahapp.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import io.jochimsen.cahapp.model.BlackCardModel;
import io.jochimsen.cahapp.model.WhiteCardModel;
import io.jochimsen.cahapp.repository.AvailableCardsRepository;

public class GameViewModel extends ViewModel {
    private AvailableCardsRepository availableCardsRepository;

    public void setAvailableCardsRepository(final AvailableCardsRepository availableCardsRepository) {
        this.availableCardsRepository = availableCardsRepository;
    }

    public LiveData<List<WhiteCardModel>> getWhiteCardModelsLiveData() {
        return availableCardsRepository.getWhiteCardModelsLiveData();
    }

    public LiveData<BlackCardModel> getBlackCardModelLiveData() {
        return availableCardsRepository.getBlackCardModelLiveData();
    }
}
