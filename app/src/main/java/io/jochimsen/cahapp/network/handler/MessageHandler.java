package io.jochimsen.cahapp.network.handler;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.jochimsen.cahapp.di.component.NetworkComponent;
import io.jochimsen.cahapp.di.component.SessionComponent;
import io.jochimsen.cahapp.di.module.SessionModule;
import io.jochimsen.cahapp.di.scope.AppScope;
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

@NetworkScope
public class MessageHandler extends InboundMessageHandlerBase {

    private static final String TAG = "MessageHandler";

    private final ProtocolMessage initialMessage;
    private final Subject<StartGameResponse> startGameSubject;
    private final Subject<WaitForGameResponse> waitForGameSubject;
    private final Subject<FinishedGameResponse> finishGameSubject;
    private final NetworkComponent networkComponent;
    private SessionComponent sessionComponent;
    private ServerSession serverSession;

    @Inject
    public MessageHandler(
            @Named("initial_message") final ProtocolMessage initialMessage,
            final Subject<StartGameResponse> startGameSubject,
            final Subject<WaitForGameResponse> waitForGameSubject,
            final Subject<FinishedGameResponse> finishGameSubject,
            final NetworkComponent networkComponent
    ) {
        this.initialMessage = initialMessage;
        this.startGameSubject = startGameSubject;
        this.waitForGameSubject = waitForGameSubject;
        this.finishGameSubject = finishGameSubject;
        this.networkComponent = networkComponent;
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        serverSession = new ServerSession(ctx);
        sessionComponent = networkComponent.sessionComponent(new SessionModule(serverSession));

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
            sessionComponent = null;
        }
    }

    @Override
    protected void onUncaughtException(final Session session, final Throwable throwable) {

    }
}
