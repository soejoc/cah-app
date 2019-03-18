package io.jochimsen.cahapp.network.session;

import io.jochimsen.collo.protocol.RequestMessage;
import io.jochimsen.collo.session.Session;
import io.netty.channel.ChannelHandlerContext;

public class ServerSession extends Session<RequestMessage> {
    public ServerSession(final ChannelHandlerContext channelHandlerContext) {
        super(channelHandlerContext);
    }
}
