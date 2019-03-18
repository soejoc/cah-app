package io.jochimsen.cahapp.repository;

import javax.inject.Inject;

import io.jochimsen.cahapp.di.scope.AppScope;
import io.jochimsen.cahframework.protocol.object.message.ProtocolMessage;
import io.jochimsen.cahframework.protocol.object.message.ResponseMessage;
import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

@AppScope
public final class ProtocolMessageRepository {

    private final FlowableProcessor<ResponseMessage> messageProcessor = PublishProcessor.create();

    @Inject
    public ProtocolMessageRepository() {
    }

    @SuppressWarnings("unchecked")
    public <T extends ProtocolMessage> Flowable<T> observe(final Class<T> clazz) {
        return messageProcessor
                .filter(protocolMessage -> protocolMessage.getClass().equals(clazz))
                .map(protocolMessage -> (T)protocolMessage);
    }

    public void emit(final ResponseMessage message) {
        messageProcessor.onNext(message);
    }
}
