package dkgnkndz.lebk.cah_app.network.handler;

import protocol.object.message.ProtocolMessage;

public abstract class ResponseMessageHandler {
    protected abstract void handleMessage(final int messageId, final ProtocolMessage protocolMessage);
}
