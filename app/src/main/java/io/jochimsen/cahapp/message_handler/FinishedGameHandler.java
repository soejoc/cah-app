package io.jochimsen.cahapp.message_handler;

import javax.inject.Inject;

import io.jochimsen.cahapp.backend.local.entity.session_key.SessionKeyDao;
import io.jochimsen.cahapp.network.session.ServerSession;
import io.jochimsen.cahframework.handler.message.MessageHandler;
import io.jochimsen.cahframework.protocol.object.message.response.FinishedGameResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class FinishedGameHandler extends ClientMessageHandler<FinishedGameResponse> {
    private final SessionKeyDao sessionKeyDao;

    @Override
    public void handleMessage(final FinishedGameResponse protocolMessage, final ServerSession session) {
        sessionKeyDao.delete();
    }
}
