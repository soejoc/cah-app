package io.jochimsen.cahapp.network.handler;

import android.util.Log;

import javax.inject.Inject;

import io.jochimsen.cahapp.MyApp;
import io.jochimsen.cahapp.di.component.SessionComponent;
import io.jochimsen.cahapp.di.qualifier.InitialMessage;
import io.jochimsen.cahapp.di.scope.NetworkScope;
import io.jochimsen.cahapp.message_handler.StartGameHandler;
import io.jochimsen.cahapp.network.session.ServerSession;
import io.jochimsen.cahapp.repository.ProtocolMessageRepository;
import io.jochimsen.cahframework.handler.inbound.InboundHandlerBase;
import io.jochimsen.cahframework.handler.message.MessageHandlerBase;
import io.jochimsen.cahframework.protocol.object.message.MessageCode;
import io.jochimsen.cahframework.protocol.object.message.ProtocolMessage;
import io.jochimsen.cahframework.protocol.object.message.error.ErrorMessage;
import io.jochimsen.cahframework.protocol.object.message.response.FinishedGameResponse;
import io.jochimsen.cahframework.protocol.object.message.response.StartGameResponse;
import io.jochimsen.cahframework.protocol.object.message.response.WaitForGameResponse;
import io.jochimsen.cahframework.session.Session;
import io.jochimsen.cahframework.util.ProtocolInputStream;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
@NetworkScope
public class InboundHandler extends InboundHandlerBase {
    private static final String TAG = "InboundHandler";

    @InitialMessage
    private final ProtocolMessage initialMessage;
    private final ProtocolMessageRepository protocolMessageRepository;
    private final MyApp myApp;
    private ServerSession serverSession;
    private SessionComponent sessionComponent;

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        serverSession = new ServerSession(ctx);
        sessionComponent = myApp.createSessionComponent(serverSession);

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

        MessageHandlerBase messageHandler = null;

        switch (messageId) {
            case MessageCode.START_GAME_RS: {
                final StartGameResponse startGameResponse = protocolInputStream.readObject();
                messageHandler = sessionComponent.startGameHandlerBuilder()
                        .protocolMessage(startGameResponse)
                        .build()
                        .messageHandler();

                protocolMessageRepository.emit(startGameResponse);
                break;
            }

            case MessageCode.WAIT_FOR_GAME_RS: {
                final WaitForGameResponse waitForGameResponse = protocolInputStream.readObject();
                protocolMessageRepository.emit(waitForGameResponse);
                break;
            }

            case MessageCode.FINISHED_GAME_RS: {
                final FinishedGameResponse finishedGameResponse = protocolInputStream.readObject();
                messageHandler = sessionComponent.finishedGameHandlerBuilder()
                        .protocolMessage(finishedGameResponse)
                        .build()
                        .messageHandler();

                protocolMessageRepository.emit(finishedGameResponse);
                break;
            }
            default: {
                Log.i(TAG, String.format("Unknown message received %d", messageId));
                break;
            }
        }

        if(messageHandler != null) {
            messageHandler.handle();
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
            myApp.releaseSessionComponent();
        }
    }

    @Override
    protected void onUncaughtException(final Session session, final Throwable throwable) {

    }
}
