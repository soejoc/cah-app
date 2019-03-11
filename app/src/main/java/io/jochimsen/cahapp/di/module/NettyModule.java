package io.jochimsen.cahapp.di.module;

import android.util.Log;

import javax.net.ssl.SSLException;

import dagger.Module;
import dagger.Provides;
import io.jochimsen.cahapp.di.scope.NetworkScope;
import io.jochimsen.cahapp.network.handler.InboundHandler;
import io.jochimsen.cahframework.initializer.ProtocolMessageChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

@Module
public abstract class NettyModule {
    private static final String TAG = "NettyModule";

    @NetworkScope
    @Provides
    static EventLoopGroup provideEventLoopGroup() {
        return new NioEventLoopGroup();
    }

    @NetworkScope
    @Provides
    static SslContext provideSslContext() {
        try {
            return SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } catch (final SSLException e) {
            Log.e(TAG, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @NetworkScope
    @Provides
    static ProtocolMessageChannelInitializer provideProtocolMessageChannelInitializer(final InboundHandler inboundHandler) {
        return new ProtocolMessageChannelInitializer(inboundHandler);
    }

    @NetworkScope
    @Provides
    static Bootstrap provideBootstrap(final EventLoopGroup eventLoopGroup, final ProtocolMessageChannelInitializer protocolMessageChannelInitializer) {
        final Bootstrap b = new Bootstrap();
        b.group(eventLoopGroup);
        b.channel(NioSocketChannel.class);
        b.option(ChannelOption.SO_KEEPALIVE, true);
        b.handler(protocolMessageChannelInitializer);

        return b;
    }
}
