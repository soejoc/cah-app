package dkgnkndz.lebk.cah_app.network.handler;

import channel_handler.ProcessingHandler;
import dkgnkndz.lebk.cah_app.network.session.ServerSession;
import io.netty.channel.ChannelHandlerContext;
import protocol.object.ProtocolObject;
import session.Session;
import throwable.exception.InvalidInputStreamException;
import util.ProtocolInputStream;

public class MessageHandler extends ProcessingHandler {
    private static ServerSession serverSession;

    public static synchronized ServerSession getServerSession() {
        return serverSession;
    }

    public static synchronized void setServerSession(final ServerSession serverSession) {
        MessageHandler.serverSession = serverSession;
    }

    private final ProtocolObject initialRequest;

    public MessageHandler(final ProtocolObject initialRequest) {
        this.initialRequest = initialRequest;
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        final ServerSession serverSession = new ServerSession(ctx);
        setServerSession(serverSession);

        if(initialRequest != null) {
            serverSession.say(initialRequest);
        }
    }

    @Override
    protected Session getOrCreateSession(final ChannelHandlerContext ctx) {
        return serverSession;
    }

    @Override
    protected void handleMessage(final int messageId, final ProtocolInputStream rawMessage, final Session session) throws InvalidInputStreamException {

    }
}
