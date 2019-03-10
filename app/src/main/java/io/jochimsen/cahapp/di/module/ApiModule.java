package io.jochimsen.cahapp.di.module;

import dagger.Module;
import dagger.Provides;
import io.jochimsen.cahapp.backend.webservice.api.BlackCardApi;
import io.jochimsen.cahapp.backend.webservice.api.WhiteCardApi;
import io.jochimsen.cahapp.di.scope.AppScope;
import retrofit2.Retrofit;

@Module(includes = {
        RetrofitModule.class
})
public abstract class ApiModule {

    @AppScope
    @Provides
    static BlackCardApi provideBlackCardApi(final Retrofit retrofit) {
        return retrofit.create(BlackCardApi.class);
    }

    @AppScope
    @Provides
    static WhiteCardApi provideWhiteCardApi(final Retrofit retrofit) {
        return retrofit.create(WhiteCardApi.class);
    }
}
