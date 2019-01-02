package io.jochimsen.cahapp.network.thread;

import android.util.Log;

import io.jochimsen.cahapp.network.handler.MessageHandler;
import io.jochimsen.cahframework.initializer.SslClientInitializer;
import io.jochimsen.cahframework.protocol.object.message.ProtocolMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

public class NetworkWorker implements Runnable {
    private final String host;
    private final int port;
    private final ProtocolMessage initialMessage;

    private static final String TAG = "NetworkWorker";

    public NetworkWorker(final String host, final int port, final ProtocolMessage initialMessage) {
        this.host = host;
        this.port = port;
        this.initialMessage = initialMessage;
    }

    @Override
    public void run() {
        final EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            final SslContext sslCtx = SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();

            final Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new SslClientInitializer(sslCtx, new MessageHandler(initialMessage), host, port));

            final ChannelFuture f = b.connect(host, port).sync();

            f.channel().closeFuture().sync();
        } catch (final Throwable throwable) {
            Log.d(TAG, throwable.getMessage());
        }
        finally {
            workerGroup.shutdownGracefully();
        }
    }
}
