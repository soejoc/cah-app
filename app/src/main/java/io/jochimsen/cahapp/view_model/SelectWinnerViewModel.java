package io.jochimsen.cahapp.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import io.jochimsen.cahapp.model.BlackCardModel;
import io.jochimsen.cahapp.model.MergedCardModel;
import io.jochimsen.cahapp.model.WhiteCardModel;
import io.jochimsen.cahapp.repository.AvailableCardsRepository;
import io.jochimsen.cahapp.repository.MergedCardRepository;

public class SelectWinnerViewModel extends ViewModel {
    private MergedCardRepository mergedCardRepository;

    public void setMergedCardRepository(final MergedCardRepository mergedCardRepository) {
        this.mergedCardRepository = mergedCardRepository;
    }

    public LiveData<List<MergedCardModel>> getMergedCardModelsLiveData() {
        return mergedCardRepository.getMergedCardModelsLiveData();
    }
}
