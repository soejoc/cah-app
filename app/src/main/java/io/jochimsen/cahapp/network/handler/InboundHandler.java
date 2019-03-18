package io.jochimsen.cahapp.network.handler;

import javax.inject.Inject;

import io.jochimsen.cahapp.MyApp;
import io.jochimsen.cahapp.di.component.SessionComponent;
import io.jochimsen.cahapp.di.factory.ClientMessageHandlerFactory;
import io.jochimsen.cahapp.di.qualifier.InitialMessage;
import io.jochimsen.cahapp.di.scope.NetworkScope;
import io.jochimsen.cahapp.network.session.ServerSession;
import io.jochimsen.cahapp.repository.ProtocolMessageRepository;
import io.jochimsen.cahapp.util.ResponseMessageMapper;
import io.jochimsen.collo.handler.inbound.InboundHandlerBase;
import io.jochimsen.collo.protocol.ErrorMessage;
import io.jochimsen.collo.protocol.RequestMessage;
import io.jochimsen.collo.protocol.ResponseMessage;
import io.netty.channel.ChannelHandlerContext;

@NetworkScope
public class InboundHandler extends InboundHandlerBase<ServerSession, ResponseMessage> {

    @InitialMessage
    private final RequestMessage initialMessage;
    private final ProtocolMessageRepository protocolMessageRepository;
    private final MyApp myApp;
    private ServerSession serverSession;
    private SessionComponent sessionComponent;

    @Inject
    public InboundHandler(final ResponseMessageMapper messageMapper, final ClientMessageHandlerFactory clientMessageHandlerFactory, @InitialMessage final RequestMessage initialMessage, final ProtocolMessageRepository protocolMessageRepository, final MyApp myApp) {
        super(messageMapper, clientMessageHandlerFactory);
        this.initialMessage = initialMessage;
        this.protocolMessageRepository = protocolMessageRepository;
        this.myApp = myApp;
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        serverSession = new ServerSession(ctx);
        sessionComponent = myApp.createSessionComponent(serverSession);

        if(initialMessage != null) {
            serverSession.say(initialMessage);
        }
    }

    @Override
    protected ServerSession getSession(final ChannelHandlerContext ctx) {
        return serverSession;
    }

    @Override
    protected void onPostHandleMessage(final ServerSession session, final ResponseMessage message) {
        protocolMessageRepository.emit(message);
    }

    @Override
    protected void onErrorReceived(final ErrorMessage errorMessage, final ServerSession session) {

    }

    @Override
    protected void closeSession(final ServerSession session) {
        super.closeSession(session);

        if(session == serverSession) {
            serverSession = null;
            sessionComponent = null;
            myApp.releaseSessionComponent();
        }
    }

    @Override
    protected void onUncaughtException(final ServerSession session, final Throwable throwable) {

    }
}
