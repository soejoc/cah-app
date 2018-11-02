package dkgnkndz.lebk.cah_app.network.handler;

import android.os.Message;

import channel_handler.ProcessingHandler;
import dkgnkndz.lebk.cah_app.network.session.ServerSession;
import io.netty.channel.ChannelHandlerContext;
import protocol.object.ProtocolObject;
import protocol.object.message.MessageCode;
import protocol.object.message.ProtocolMessage;
import protocol.object.message.error.ErrorObject;
import protocol.object.message.response.StartGameResponse;
import session.Session;
import util.ProtocolInputStream;

public class MessageHandler extends ProcessingHandler {
    private static ServerSession serverSession;

    private MessageTransitionHandler messageTransitionHandler = new MessageTransitionHandler();

    public static synchronized ServerSession getServerSession() {
        return serverSession;
    }

    public static synchronized void setServerSession(final ServerSession serverSession) {
        MessageHandler.serverSession = serverSession;
    }

    private final ProtocolMessage initialMessage;

    public MessageHandler(final ProtocolMessage initialMessage) {
        this.initialMessage = initialMessage;
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        final ServerSession serverSession = new ServerSession(ctx);
        setServerSession(serverSession);

        if(initialMessage != null) {
            serverSession.say(initialMessage);
        }
    }

    @Override
    protected Session getSession(final ChannelHandlerContext ctx) {
        return serverSession;
    }

    @Override
    protected void handleMessage(final int messageId, final ProtocolInputStream rawMessage, final Session session) {
        ProtocolObject protocolObject = null;

        switch (messageId) {
            case MessageCode.START_GAME_RS: {
                protocolObject = new StartGameResponse();
                break;
            }

            default: {
                break;
            }
        }

        if(protocolObject != null) {
            protocolObject.fromStream(rawMessage);
        }

        final Message message = messageTransitionHandler.obtainMessage(messageId, protocolObject);
        message.sendToTarget();
    }

    @Override
    protected void onErrorReceived(final ErrorObject errorObject, final Session session) {

    }
}
