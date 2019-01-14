package io.jochimsen.cahapp.network.handler;

import android.util.Log;

import io.jochimsen.cahapp.network.session.ServerSession;
import io.jochimsen.cahframework.handler.inbound.InboundMessageHandlerBase;
import io.jochimsen.cahframework.protocol.object.message.MessageCode;
import io.jochimsen.cahframework.protocol.object.message.ProtocolMessage;
import io.jochimsen.cahframework.protocol.object.message.error.ErrorMessage;
import io.jochimsen.cahframework.protocol.object.message.response.FinishedGameResponse;
import io.jochimsen.cahframework.protocol.object.message.response.StartGameResponse;
import io.jochimsen.cahframework.protocol.object.message.response.WaitForGameResponse;
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
    protected void handleMessage(final int messageId, final ProtocolInputStream protocolInputStream, final Session session) throws Exception {
        Log.d(TAG, String.format("Received message id %d", messageId));

        switch (messageId) {
            case MessageCode.START_GAME_RS: {
                final StartGameResponse startGameResponse = protocolInputStream.readObject(StartGameResponse.class);

                MessageSubject.startGameResponseSubject.onNext(startGameResponse);
                break;
            }

            case MessageCode.WAIT_FOR_GAME_RS: {
                final WaitForGameResponse waitForGameResponse = protocolInputStream.readObject(WaitForGameResponse.class);

                MessageSubject.waitForGameResponseSubject.onNext(waitForGameResponse);
                break;
            }

            case MessageCode.FINISHED_GAME_RS: {
                final FinishedGameResponse finishedGameResponse = protocolInputStream.readObject(FinishedGameResponse.class);

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

    @Override
    protected void onUncaughtException(final Session session, final Throwable throwable) {

    }
}
