package io.jochimsen.cahapp.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import io.jochimsen.cahapp.view_model.LandingViewModel;
import io.jochimsen.cahapp.di.factory.ViewModelFactory;
import io.jochimsen.cahapp.di.scope.ViewModelKey;

@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(final ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(LandingViewModel.class)
    abstract ViewModel bindLandingViewModel(final LandingViewModel landingViewModel);
}
