package io.jochimsen.cahapp.di.component.message_handler;

import io.jochimsen.cahframework.handler.message.MessageHandlerBase;

public interface MessageHandlerBaseComponent<T extends MessageHandlerBase> {
    T messageHandler();
}
