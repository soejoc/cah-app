package io.jochimsen.cahapp.network.thread;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Named;

import io.jochimsen.cahapp.di.scope.NetworkScope;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;

@NetworkScope
public class NetworkWorker implements Runnable {
    private final String host;
    private final int port;
    private final Bootstrap bootstrap;

    private static final String TAG = "NetworkWorker";

    @Inject
    public NetworkWorker(@Named("host") final String host, @Named("port") final int port, final Bootstrap bootstrap) {
        this.host = host;
        this.port = port;
        this.bootstrap = bootstrap;
    }

    @Override
    public void run() {
        try {
            final ChannelFuture f = bootstrap.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } catch (final Throwable throwable) {
            //ToDO: Exception handling
            Log.d(TAG, throwable.getMessage());
        }
        finally {
            bootstrap.config().group().shutdownGracefully();
        }
    }
}
