package dkgnkndz.lebk.cah_app.network.thread;

import codec.decoder.meta.MetaDecoder;
import codec.encoder.meta.MetaEncoder;
import dkgnkndz.lebk.cah_app.network.handler.MessageHandler;
import dkgnkndz.lebk.cah_app.network.handler.ProcessingHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NetworkWorker implements Runnable {
    private final String host;
    private final int port;
    private final String nickname;

    public NetworkWorker(final String host, final int port, final String nickname) {
        this.host = host;
        this.port = port;
        this.nickname = nickname;
    }

    @Override
    public void run() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                public void initChannel(SocketChannel ch)
                        throws Exception {
                    ch.pipeline().addLast(
                            new MetaDecoder(),
                            new MetaEncoder(),
                            new MessageHandler(nickname)
                    );
                }
            });

            ChannelFuture f = b.connect(host, port).sync();

            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            ProcessingHandler.setServerSession(null);
        }
        finally {
            workerGroup.shutdownGracefully();
        }
    }
}
