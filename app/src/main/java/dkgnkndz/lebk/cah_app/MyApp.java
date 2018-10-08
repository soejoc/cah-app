package dkgnkndz.lebk.cah_app;

import android.app.Application;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import protocol.object.ProtocolObject;

public class MyApp extends Application {
    Queue<ProtocolObject> queuedRequests = new LinkedBlockingQueue<>();

    public ProtocolObject popLastRequest() {
        return queuedRequests.poll();
    }

    public void addRequest(final ProtocolObject protocolObject) {
        queuedRequests.add(protocolObject);
    }
}
