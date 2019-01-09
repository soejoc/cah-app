package io.jochimsen.cahapp;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import io.jochimsen.cahapp.di.component.AppComponent;
import io.jochimsen.cahapp.di.component.DaggerAppComponent;
import io.jochimsen.cahapp.network.thread.NetworkWorker;
import io.jochimsen.cahframework.protocol.object.message.ProtocolMessage;

public class MyApp extends DaggerApplication {
    private static final String host = "192.168.0.143";
    private static final int port = 6666;

    private Thread networkingThread;

    public Thread getNetworkingThread() {
        return networkingThread;
    }

    public void createConnection(final ProtocolMessage initialMessage) {
        final NetworkWorker networkWorker = new NetworkWorker(host, port, initialMessage);
        networkingThread = new Thread(networkWorker);
        networkingThread.start();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);

        return appComponent;
    }
}
