package io.jochimsen.cahapp.message_handler;

import javax.inject.Inject;

import io.jochimsen.cahapp.backend.local.entity.session_key.SessionKeyDao;
import io.jochimsen.cahframework.handler.message.MessageHandler;
import io.jochimsen.cahframework.protocol.object.message.response.FinishedGameResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class FinishedGameHandler extends MessageHandler<FinishedGameResponse> {
    private final SessionKeyDao sessionKeyDao;

    @Override
    public void handle(final FinishedGameResponse protocolMessage) {
        sessionKeyDao.delete();
    }
}
