package dkgnkndz.lebk.cah_app.network.handler;

import dkgnkndz.lebk.cah_app.network.session.ServerSession;
import io.netty.channel.ChannelHandlerContext;
import protocol.object.request.StartGameRequest;

public class MessageHandler extends ProcessingHandler {
    private final String nickname;

    public MessageHandler(final String nickname) {
        this.nickname = nickname;
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        final ServerSession serverSession = getServerSession();

        StartGameRequest startGameRequest = new StartGameRequest();
        startGameRequest.nickName = nickname;

        serverSession.say(startGameRequest);
    }
}
