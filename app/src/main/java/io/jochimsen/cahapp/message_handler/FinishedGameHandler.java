package io.jochimsen.cahapp.message_handler;

import javax.inject.Inject;

import io.jochimsen.cahapp.backend.local.entity.session_key.SessionKey;
import io.jochimsen.cahapp.backend.local.entity.session_key.SessionKeyDao;
import io.jochimsen.cahapp.di.scope.MessageHandlerScope;
import io.jochimsen.cahframework.handler.message.MessageHandlerBase;
import io.jochimsen.cahframework.protocol.object.message.response.FinishedGameResponse;
import io.jochimsen.cahframework.protocol.object.message.response.StartGameResponse;

@MessageHandlerScope
public class FinishedGameHandler extends MessageHandlerBase<FinishedGameResponse> {
    private final SessionKeyDao sessionKeyDao;

    @Inject
    public FinishedGameHandler(final FinishedGameResponse protocolMessage, final SessionKeyDao sessionKeyDao) {
        super(protocolMessage);

        this.sessionKeyDao = sessionKeyDao;
    }

    @Override
    public void handle() {
        sessionKeyDao.delete();
    }
}
