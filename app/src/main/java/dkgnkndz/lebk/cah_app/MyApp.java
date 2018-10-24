package dkgnkndz.lebk.cah_app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import dkgnkndz.lebk.cah_app.network.thread.NetworkWorker;
import protocol.object.ProtocolObject;

public class MyApp extends Application implements Application.ActivityLifecycleCallbacks{
    private static final String host = "10.0.2.2";
    private static final int port = 8345;
    private static Activity currentActivity;

    private Thread networkingThread;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
    }

    public Thread getNetworkingThread() {
        return networkingThread;
    }

    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    public void createConnection(final ProtocolObject initialRequest) {
        final NetworkWorker networkWorker = new NetworkWorker(host, port, initialRequest);
        networkingThread = new Thread(networkWorker);
        networkingThread.start();
    }

    @Override
    public void onActivityCreated(final Activity activity, final Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(final Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(final Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityPaused(final Activity activity) {
        if(currentActivity == activity) {
            currentActivity = null;
        }
    }

    @Override
    public void onActivityStopped(final Activity activity) {
        if(currentActivity == activity) {
            currentActivity = null;
        }
    }

    @Override
    public void onActivitySaveInstanceState(final Activity activity, final Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(final Activity activity) {
        if(currentActivity == activity) {
            currentActivity = null;
        }
    }
}
