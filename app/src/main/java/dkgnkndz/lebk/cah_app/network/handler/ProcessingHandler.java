package dkgnkndz.lebk.cah_app.network.handler;

import dkgnkndz.lebk.cah_app.network.session.ServerSession;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocol.object.meta.MetaObject;
import util.ProtocolInputStream;

public abstract class ProcessingHandler extends ChannelInboundHandlerAdapter {
    private static ServerSession serverSession;

    public static synchronized ServerSession getServerSession() {
        return serverSession;
    }

    public static synchronized void setServerSession(final ServerSession serverSession) {
        ProcessingHandler.serverSession = serverSession;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        final ServerSession newServerSession = new ServerSession(ctx);

        setServerSession(newServerSession);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MetaObject metaObject = (MetaObject) msg;

        final int messageId = metaObject.getMessageId();
        final ProtocolInputStream rawMessage = metaObject.getStream();

    }
}