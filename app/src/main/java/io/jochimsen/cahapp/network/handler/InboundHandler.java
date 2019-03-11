package io.jochimsen.cahapp.network.handler;

import android.util.Log;

import javax.inject.Inject;

import io.jochimsen.cahapp.MyApp;
import io.jochimsen.cahapp.di.component.SessionComponent;
import io.jochimsen.cahapp.di.factory.MessageHandlerFactory;
import io.jochimsen.cahapp.di.qualifier.InitialMessage;
import io.jochimsen.cahapp.di.scope.NetworkScope;
import io.jochimsen.cahapp.message_handler.FinishedGameHandler;
import io.jochimsen.cahapp.message_handler.StartGameHandler;
import io.jochimsen.cahapp.network.session.ServerSession;
import io.jochimsen.cahapp.repository.ProtocolMessageRepository;
import io.jochimsen.cahframework.handler.inbound.InboundHandlerBase;
import io.jochimsen.cahframework.handler.message.MessageHandler;
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
    private final MessageHandlerFactory messageHandlerFactory;
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

        MessageHandler messageHandler = null;
        ProtocolMessage protocolMessage = null;

        switch (messageId) {
            case MessageCode.START_GAME_RS: {
                protocolMessage = protocolInputStream.readObject(StartGameResponse.class);
                messageHandler = messageHandlerFactory.create(StartGameHandler.class);
                break;
            }

            case MessageCode.WAIT_FOR_GAME_RS: {
                protocolMessage = protocolInputStream.readObject(WaitForGameResponse.class);
                break;
            }

            case MessageCode.FINISHED_GAME_RS: {
                protocolMessage = protocolInputStream.readObject(FinishedGameResponse.class);
                messageHandler = messageHandlerFactory.create(FinishedGameHandler.class);
                break;
            }
            default: {
                Log.i(TAG, String.format("Unknown message received %d", messageId));
                break;
            }
        }

        if(messageHandler != null) {
            //noinspection unchecked
            messageHandler.handle(protocolMessage);
        }

        if(protocolMessage != null) {
            protocolMessageRepository.emit(protocolMessage);
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
