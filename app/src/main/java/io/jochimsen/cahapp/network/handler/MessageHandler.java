package io.jochimsen.cahapp.network.handler;

import android.util.Log;

import javax.inject.Inject;

import io.jochimsen.cahapp.MyApp;
import io.jochimsen.cahapp.di.qualifier.InitialMessage;
import io.jochimsen.cahapp.di.scope.NetworkScope;
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
import io.reactivex.subjects.Subject;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
@NetworkScope
public class MessageHandler extends InboundMessageHandlerBase {
    private static final String TAG = "MessageHandler";

    @InitialMessage
    private final ProtocolMessage initialMessage;

    private final Subject<StartGameResponse> startGameSubject;
    private final Subject<WaitForGameResponse> waitForGameSubject;
    private final Subject<FinishedGameResponse> finishGameSubject;
    private final MyApp myApp;
    private ServerSession serverSession;

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        serverSession = new ServerSession(ctx);
        myApp.createSessionComponent(serverSession);

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
                final StartGameResponse startGameResponse = protocolInputStream.readObject();

                startGameSubject.onNext(startGameResponse);
                break;
            }

            case MessageCode.WAIT_FOR_GAME_RS: {
                final WaitForGameResponse waitForGameResponse = protocolInputStream.readObject();

                waitForGameSubject.onNext(waitForGameResponse);
                break;
            }

            case MessageCode.FINISHED_GAME_RS: {
                final FinishedGameResponse finishedGameResponse = protocolInputStream.readObject();

                finishGameSubject.onNext(finishedGameResponse);
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
            myApp.releaseSessionComponent();
        }
    }

    @Override
    protected void onUncaughtException(final Session session, final Throwable throwable) {

    }
}
