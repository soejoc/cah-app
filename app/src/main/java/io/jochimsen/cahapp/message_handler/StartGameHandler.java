package io.jochimsen.cahapp.message_handler;

import javax.inject.Inject;

import io.jochimsen.cahapp.backend.local.entity.session_key.SessionKey;
import io.jochimsen.cahapp.backend.local.entity.session_key.SessionKeyDao;
import io.jochimsen.cahapp.di.scope.MessageHandlerScope;
import io.jochimsen.cahframework.handler.message.MessageHandlerBase;
import io.jochimsen.cahframework.protocol.object.message.response.StartGameResponse;

@MessageHandlerScope
public class StartGameHandler extends MessageHandlerBase<StartGameResponse> {
    private final SessionKeyDao sessionKeyDao;

    @Inject
    public StartGameHandler(final StartGameResponse protocolMessage, final SessionKeyDao sessionKeyDao) {
        super(protocolMessage);

        this.sessionKeyDao = sessionKeyDao;
    }

    @Override
    public void handle() {
        sessionKeyDao.save(new SessionKey(protocolMessage.getSessionId()));
    }
}
