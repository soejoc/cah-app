package io.jochimsen.cahapp.network.session;

import io.jochimsen.cahframework.session.Session;
import io.netty.channel.ChannelHandlerContext;

public class ServerSession extends Session {
    public ServerSession(final ChannelHandlerContext channelHandlerContext) {
        super(channelHandlerContext);
    }
}
