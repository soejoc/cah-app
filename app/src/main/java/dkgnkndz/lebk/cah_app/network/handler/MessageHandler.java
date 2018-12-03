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

    private final ProtocolMessage initialMessage;
    private MessageTransitionHandler messageTransitionHandler;

    public MessageHandler(final ProtocolMessage initialMessage, final ResponseMessageHandler responseMessageHandler) {
        this.initialMessage = initialMessage;
        messageTransitionHandler = new MessageTransitionHandler(responseMessageHandler);
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        final ServerSession serverSession = new ServerSession(ctx);

        if(initialMessage != null) {
            serverSession.say(initialMessage);
        }
    }

    @Override
    protected Session getSession(final ChannelHandlerContext ctx) {
        return ServerSession.getServerSession();
    }

    @Override
    protected void handleMessage(final int messageId, final ProtocolInputStream rawMessage, final Session session) {
        ProtocolMessage protocolMessage = null;

        switch (messageId) {
            case MessageCode.START_GAME_RS: {
                protocolMessage = new StartGameResponse();
                break;
            }

            default: {
                break;
            }
        }

        if(protocolMessage != null) {
            protocolMessage.fromStream(rawMessage);
        }

        final Message message = messageTransitionHandler.obtainMessage(messageId, protocolMessage);
        message.sendToTarget();
    }

    @Override
    protected void onErrorReceived(final ErrorObject errorObject, final Session session) {

    }
}
