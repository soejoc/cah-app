package io.jochimsen.cahapp.backend.ressource;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.WorkerThread;
import android.util.Log;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public abstract class NetworkBoundResource<ResultType, RequestType> {
    private static final String TAG = "NetworkBoundResource";

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @MainThread
    public NetworkBoundResource() {
        result.setValue(Resource.loading(null));

        final LiveData<ResultType> dbSource = fetchFromDb();
        result.addSource(dbSource, dbResult -> {
            result.removeSource(dbSource);
            result.setValue(Resource.loading(dbResult));

            compositeDisposable.add(Single.fromCallable(this::shouldFetch)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(shouldFetch -> {
                        if(shouldFetch) {
                            fetchFromNetwork();
                        } else {
                            result.setValue(Resource.success(dbResult));
                        }
                    }, this::onError));
        });
    }

    @MainThread
    private void fetchFromNetwork() {
        final Single<RequestType> networkCall = createCall();

        compositeDisposable.add(networkCall
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(response -> {
                    persistResult(processResponse(response));
                    result.addSource(fetchFromDb(), dbResult -> result.postValue(Resource.success(dbResult)));
                }, this::onError));
    }

    @MainThread
    private void onError(final Throwable throwable) {
        result.setValue(Resource.error(throwable.getMessage(), null));
        onFetchFailed(throwable);
    }

    @MainThread
    protected void onFetchFailed(final Throwable throwable) {
        Log.e(TAG, throwable.getMessage());
    }

    @WorkerThread
    protected abstract boolean shouldFetch();

    @MainThread
    protected abstract Single<RequestType> createCall();

    @WorkerThread
    protected abstract ResultType processResponse(final RequestType response);

    @WorkerThread
    protected abstract void persistResult(final ResultType result);

    protected abstract LiveData<ResultType> fetchFromDb();

    @MainThread
    public void dispose() {
        result.removeSource(fetchFromDb());
        compositeDisposable.dispose();
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }
}
