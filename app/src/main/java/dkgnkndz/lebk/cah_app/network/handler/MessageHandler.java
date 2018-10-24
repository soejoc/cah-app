package dkgnkndz.lebk.cah_app.network.handler;

import android.os.Message;

import channel_handler.ProcessingHandler;
import dkgnkndz.lebk.cah_app.network.session.ServerSession;
import io.netty.channel.ChannelHandlerContext;
import protocol.MessageCode;
import protocol.object.ProtocolObject;
import protocol.object.error.ErrorObject;
import protocol.object.response.StartGameResponse;
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

    private final ProtocolObject initialRequest;

    public MessageHandler(final ProtocolObject initialRequest) {
        this.initialRequest = initialRequest;
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        final ServerSession serverSession = new ServerSession(ctx);
        setServerSession(serverSession);

        if(initialRequest != null) {
            serverSession.say(initialRequest);
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
