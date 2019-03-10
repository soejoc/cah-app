package io.jochimsen.cahapp.di.factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import io.jochimsen.cahapp.di.scope.AppScope;
import lombok.AllArgsConstructor;

@AppScope
@AllArgsConstructor(onConstructor = @__({@Inject}))
public final class ViewModelFactory implements ViewModelProvider.Factory {
    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> creators;

    @SuppressWarnings("unchecked")
    @Override
    @NonNull
    public <T extends ViewModel> T create(@NonNull final Class<T> modelClass) {
        final Provider<? extends ViewModel> creator = creators.get(modelClass);

        if (creator == null) {
            throw new IllegalArgumentException("unknown model class " + modelClass);
        }

        return (T)creator.get();
    }
}
