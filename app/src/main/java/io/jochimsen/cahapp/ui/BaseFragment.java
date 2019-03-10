package io.jochimsen.cahapp.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public abstract class BaseFragment extends DaggerFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    protected <T extends ViewModel> T getViewModel(final Class<T> clazz) {
        return ViewModelProviders.of(this, viewModelFactory).get(clazz);
    }

    protected <T extends ViewModel> T getActivityViewModel(final Class<T> clazz) {
        return ViewModelProviders.of(getActivity(), viewModelFactory).get(clazz);
    }
}
