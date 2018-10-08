package dkgnkndz.lebk.cah_app.network.session;

import io.netty.channel.ChannelHandlerContext;
import session.Session;

public class ServerSession extends Session {
    public ServerSession(final ChannelHandlerContext channelHandlerContext) {
        super(channelHandlerContext);
    }
}
