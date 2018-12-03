package dkgnkndz.lebk.cah_app.network.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import protocol.object.message.ProtocolMessage;

public class MessageTransitionHandler extends Handler {
    private final ResponseMessageHandler responseMessageHandler;

    public MessageTransitionHandler(final ResponseMessageHandler responseMessageHandler) {
        super(Looper.getMainLooper());

        this.responseMessageHandler = responseMessageHandler;
    }

    @Override
    public void handleMessage(final Message msg) {
        final ProtocolMessage protocolMessage = (ProtocolMessage) msg.obj;
        final int messageId = msg.what;

        responseMessageHandler.handleMessage(messageId, protocolMessage);
    }
}
