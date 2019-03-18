package io.jochimsen.cahapp.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dagger.MapKey;
import io.jochimsen.cahapp.message_handler.ClientMessageHandler;
import io.jochimsen.cahframework.handler.message.MessageHandler;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@MapKey
public @interface MessageHandlerKey {
    Class<? extends ClientMessageHandler> value();
}