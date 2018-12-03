package dkgnkndz.lebk.cah_app.network.thread;

import codec.decoder.meta.MetaDecoder;
import codec.encoder.meta.MetaEncoder;
import dkgnkndz.lebk.cah_app.network.handler.MessageHandler;
import dkgnkndz.lebk.cah_app.network.handler.ResponseMessageHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import protocol.object.message.ProtocolMessage;

public class NetworkWorker implements Runnable {
    private final String host;
    private final int port;
    private final ProtocolMessage initialMessage;
    private final ResponseMessageHandler responseMessageHandler;

    public NetworkWorker(final String host, final int port, final ProtocolMessage initialMessage, final ResponseMessageHandler responseMessageHandler) {
        this.host = host;
        this.port = port;
        this.initialMessage = initialMessage;
        this.responseMessageHandler = responseMessageHandler;
    }

    @Override
    public void run() {
        final EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            final Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                public void initChannel(final SocketChannel ch)
                        throws Exception {
                    ch.pipeline().addLast(
                            new MetaDecoder(),
                            new MetaEncoder(),
                            new MessageHandler(initialMessage, responseMessageHandler)
                    );
                }
            });

            final ChannelFuture f = b.connect(host, port).sync();

            f.channel().closeFuture().sync();
        } catch (final InterruptedException e) {
            MessageHandler.setServerSession(null);
        }
        finally {
            workerGroup.shutdownGracefully();
        }
    }
}
