package io.jochimsen.cahapp.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    protected <T extends ViewModel> T getViewModel(final Class<T> clazz) {
        return ViewModelProviders.of(this, viewModelFactory).get(clazz);
    }
}
