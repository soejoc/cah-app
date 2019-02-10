package io.jochimsen.cahapp.di.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import io.jochimsen.cahapp.di.qualifier.WebserviceBaseUrl;
import io.jochimsen.cahapp.di.scope.AppScope;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public abstract class RetrofitModule {

    @AppScope
    @Provides
    public static Retrofit provideRetrofit(final Gson gson, final OkHttpClient okHttpClient, @WebserviceBaseUrl final String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @AppScope
    @Provides
    public static Gson provideGson() {
        return new GsonBuilder()
                .create();
    }

    @AppScope
    @Provides
    public static OkHttpClient provideOkHttpClient() {
        return new OkHttpClient
                .Builder()
                .build();
    }

    @AppScope
    @WebserviceBaseUrl
    @Provides
    public static String provideBaseUrl() {
        return "https://api.cah.jochimsen.io/";
    }
}
