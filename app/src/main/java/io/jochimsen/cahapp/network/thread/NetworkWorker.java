package io.jochimsen.cahapp.network.thread;

import android.util.Log;

import javax.inject.Inject;

import io.jochimsen.cahapp.di.qualifier.GameserverHost;
import io.jochimsen.cahapp.di.qualifier.GameserverPort;
import io.jochimsen.cahapp.di.scope.NetworkScope;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import lombok.AllArgsConstructor;

@AllArgsConstructor(onConstructor = @__({@Inject}))
@NetworkScope
public class NetworkWorker implements Runnable {
    private static final String TAG = "NetworkWorker";

    @GameserverHost
    private final String host;

    @GameserverPort
    private final int port;

    private final Bootstrap bootstrap;

    @Override
    public void run() {
        try {
            final ChannelFuture f = bootstrap.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } catch (final InterruptedException throwable) {
            //ToDO: Exception handling
            Log.d(TAG, throwable.getMessage());
        }
        finally {
            bootstrap.config().group().shutdownGracefully();
        }
    }
}
