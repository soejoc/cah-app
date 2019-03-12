package io.jochimsen.cahapp.message_handler;

import io.jochimsen.cahapp.network.session.ServerSession;
import io.jochimsen.cahframework.handler.message.MessageHandler;
import io.jochimsen.cahframework.protocol.object.message.ResponseMessage;

public abstract class ClientMessageHandler<M extends ResponseMessage> implements MessageHandler<M, ServerSession> {
}
