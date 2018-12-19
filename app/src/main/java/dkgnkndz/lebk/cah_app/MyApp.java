package dkgnkndz.lebk.cah_app;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dkgnkndz.lebk.cah_app.di.component.AppComponent;
import dkgnkndz.lebk.cah_app.di.component.DaggerAppComponent;
import dkgnkndz.lebk.cah_app.network.thread.NetworkWorker;
import protocol.object.message.ProtocolMessage;

public class MyApp extends DaggerApplication {
    private static final String host = "192.168.0.143";
    private static final int port = 8345;

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
