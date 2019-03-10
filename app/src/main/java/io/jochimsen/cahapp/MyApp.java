package io.jochimsen.cahapp;

import android.content.Context;
import android.util.Log;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import io.jochimsen.cahapp.di.component.AppComponent;
import io.jochimsen.cahapp.di.component.DaggerAppComponent;
import io.jochimsen.cahapp.di.component.NetworkComponent;
import io.jochimsen.cahapp.di.component.SessionComponent;
import io.jochimsen.cahapp.di.scope.AppScope;
import io.jochimsen.cahapp.network.session.ServerSession;
import io.jochimsen.cahapp.network.thread.NetworkWorker;
import io.jochimsen.cahframework.protocol.object.message.ProtocolMessage;
import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@AppScope
public class MyApp extends DaggerApplication {
    private static final String TAG = "MyApp";

    private AppComponent appComponent;
    private NetworkComponent networkComponent;
    private SessionComponent sessionComponent;
    private Disposable networkDisposable;

    public static MyApp get(final Context context) {
        return (MyApp)context.getApplicationContext();
    }

    public void createConnection(final ProtocolMessage initialMessage) {
        networkComponent = appComponent
                .networkComponentBuilder()
                .initialMessage(initialMessage)
                .build();

        final NetworkWorker networkWorker = networkComponent.networkWorker();

        networkDisposable = Completable.fromRunnable(networkWorker)
                .subscribeOn(Schedulers.io())
                .subscribe(this::onNetworkThreadFinished, this::onNetworkThreadError);
    }

    private void onNetworkThreadFinished() {
        networkComponent = null;
    }

    private void onNetworkThreadError(final Throwable throwable) {
        Log.e(TAG, throwable.getMessage());
    }

    public SessionComponent createSessionComponent(final ServerSession serverSession) {
        if(sessionComponent == null) {
            sessionComponent = networkComponent
                    .sessionComponentBuilder()
                    .serverSession(serverSession)
                    .build();
        }

        return sessionComponent;
    }

    public void releaseSessionComponent() {
        sessionComponent = null;
    }

    public SessionComponent getSessionComponent() {
        return sessionComponent;
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        appComponent = DaggerAppComponent
                .builder()
                .application(this)
                .build();

        return appComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        networkDisposable.dispose();
    }
}
