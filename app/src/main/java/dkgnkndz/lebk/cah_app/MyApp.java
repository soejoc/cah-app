package dkgnkndz.lebk.cah_app;

import android.app.Application;

import dkgnkndz.lebk.cah_app.network.handler.ResponseMessageHandler;
import dkgnkndz.lebk.cah_app.network.thread.NetworkWorker;
import protocol.object.message.ProtocolMessage;

public class MyApp extends Application {
    private static final String host = "192.168.0.143";
    private static final int port = 8345;

    private Thread networkingThread;

    public Thread getNetworkingThread() {
        return networkingThread;
    }

    public void createConnection(final ProtocolMessage initialMessage, final ResponseMessageHandler responseMessageHandler) {
        final NetworkWorker networkWorker = new NetworkWorker(host, port, initialMessage, responseMessageHandler);
        networkingThread = new Thread(networkWorker);
        networkingThread.start();
    }

}
