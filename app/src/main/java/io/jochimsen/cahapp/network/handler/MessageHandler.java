package io.jochimsen.cahapp.network.handler;

import android.util.Log;

import io.jochimsen.cahapp.network.session.ServerSession;
import io.jochimsen.cahframework.handler.inbound.InboundMessageHandlerBase;
import io.jochimsen.cahframework.protocol.object.ProtocolObject;
import io.jochimsen.cahframework.protocol.object.message.MessageCode;
import io.jochimsen.cahframework.protocol.object.message.ProtocolMessage;
import io.jochimsen.cahframework.protocol.object.message.error.ErrorMessage;
import io.jochimsen.cahframework.protocol.object.message.response.finished_game.FinishedGameResponse;
import io.jochimsen.cahframework.protocol.object.message.response.start_game.StartGameResponse;
import io.jochimsen.cahframework.protocol.object.message.response.wait_for_game.WaitForGameResponse;
import io.jochimsen.cahframework.session.Session;
import io.jochimsen.cahframework.util.ProtocolInputStream;
import io.netty.channel.ChannelHandlerContext;

public class MessageHandler extends InboundMessageHandlerBase {

    private static final String TAG = "MessageHandler";

    private static ServerSession serverSession;

    public static ServerSession getServerSession() {
        return serverSession;
    }

    private final ProtocolMessage initialMessage;

    public MessageHandler(final ProtocolMessage initialMessage) {
        this.initialMessage = initialMessage;
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        serverSession = new ServerSession(ctx);

        if(initialMessage != null) {
            serverSession.say(initialMessage);
        }
    }

    @Override
    protected Session getSession(final ChannelHandlerContext ctx) {
        return serverSession;
    }

    @Override
    protected void handleMessage(final int messageId, final ProtocolInputStream protocolInputStream, final Session session) {
        Log.d(TAG, String.format("Received message id %d", messageId));

        switch (messageId) {
            case MessageCode.START_GAME_RS: {
                final StartGameResponse startGameResponse = ProtocolObject.fromProtocolInputStream(StartGameResponse.class, protocolInputStream);

                MessageSubject.startGameResponseSubject.onNext(startGameResponse);
                break;
            }

            case MessageCode.WAIT_FOR_GAME_RS: {
                final WaitForGameResponse waitForGameResponse = ProtocolObject.fromProtocolInputStream(WaitForGameResponse.class, protocolInputStream);

                MessageSubject.waitForGameResponseSubject.onNext(waitForGameResponse);
                break;
            }

            case MessageCode.FINISHED_GAME_RS: {
                final FinishedGameResponse finishedGameResponse = ProtocolObject.fromProtocolInputStream(FinishedGameResponse.class, protocolInputStream);

                MessageSubject.finishedGameResponseSubject.onNext(finishedGameResponse);
                break;
            }
            default: {
                Log.i(TAG, String.format("Unknown message received %d", messageId));
                break;
            }
        }
    }

    @Override
    protected void onErrorReceived(final ErrorMessage errorMessage, final Session session) {

    }

    @Override
    protected void closeSession(final Session session) {
        super.closeSession(session);

        if(session == serverSession) {
            serverSession = null;
        }
    }
}
