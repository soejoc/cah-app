package io.jochimsen.cahapp.message_handler;

import io.jochimsen.cahapp.network.session.ServerSession;
import io.jochimsen.collo.message.MessageHandler;
import io.jochimsen.collo.protocol.ResponseMessage;

public abstract class ClientMessageHandler<M extends ResponseMessage> implements MessageHandler<M, ServerSession> {
}
