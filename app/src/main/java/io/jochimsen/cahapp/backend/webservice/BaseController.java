package io.jochimsen.cahapp.backend.webservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseController {
    private static final String BASE_URL = "https://api.cah.jochimsen.io/";

    private final String namespace;

    public BaseController(final String namespace) {
        this.namespace = namespace;
    }

    protected <T> T createAPI(final Class<T> service) {
        final Gson gson = new GsonBuilder()
                .create();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL + namespace + "/")
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(service);
    }
}
