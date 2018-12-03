package dkgnkndz.lebk.cah_app.network.session;

import dkgnkndz.lebk.cah_app.throwable.exception.AmbiguousSessionException;
import io.netty.channel.ChannelHandlerContext;
import session.Session;

public class ServerSession extends Session {
    private static ServerSession serverSession;

    public static ServerSession getServerSession() {
        return serverSession;
    }

    public static void setServerSession(final ServerSession serverSession) {
        ServerSession.serverSession = serverSession;
    }

    public ServerSession(final ChannelHandlerContext channelHandlerContext) {
        super(channelHandlerContext);

        if(serverSession != null) {
            throw new AmbiguousSessionException(serverSession);
        }

        setServerSession(this);
    }

    @Override
    public void onClose() {
        setServerSession(null);
    }
}
