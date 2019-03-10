package io.jochimsen.cahapp.message_handler;

import javax.inject.Inject;

import io.jochimsen.cahapp.backend.local.entity.session_key.SessionKey;
import io.jochimsen.cahapp.backend.local.entity.session_key.SessionKeyDao;
import io.jochimsen.cahframework.handler.message.MessageHandler;
import io.jochimsen.cahframework.protocol.object.message.response.StartGameResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class StartGameHandler extends MessageHandler<StartGameResponse> {
    private final SessionKeyDao sessionKeyDao;

    @Override
    public void handle(final StartGameResponse protocolMessage) {
        sessionKeyDao.save(new SessionKey(protocolMessage.getSessionId()));
    }
}
