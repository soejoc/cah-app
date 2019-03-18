package io.jochimsen.cahapp.util;

import javax.inject.Inject;

import io.jochimsen.cahapp.di.scope.AppScope;
import io.jochimsen.cahapp.message_handler.FinishedGameHandler;
import io.jochimsen.cahapp.message_handler.StartGameHandler;
import io.jochimsen.cahapp.network.session.ServerSession;
import io.jochimsen.cahprotocol.message.MessageCode;
import io.jochimsen.cahprotocol.message.response.FinishedGameResponse;
import io.jochimsen.cahprotocol.message.response.StartGameResponse;
import io.jochimsen.cahprotocol.message.response.WaitForGameResponse;
import io.jochimsen.collo.message.MessageMapper;
import io.jochimsen.collo.protocol.ResponseMessage;
import lombok.NoArgsConstructor;

@AppScope
@NoArgsConstructor(onConstructor = @__({@Inject}))
public class ResponseMessageMapper implements MessageMapper<ResponseMessage, ServerSession> {
    @Override
    public MessageMapper.Pair<ResponseMessage, ServerSession> map(final int messageId) {
        switch (messageId) {
            case MessageCode.START_GAME_RS: return new Pair<>(StartGameResponse.class, StartGameHandler.class);
            case MessageCode.WAIT_FOR_GAME_RS: return new Pair<>(WaitForGameResponse.class, null);
            case MessageCode.FINISHED_GAME_RS: return new Pair<>(FinishedGameResponse.class, FinishedGameHandler.class);
            default: return null;
        }
    }
}
