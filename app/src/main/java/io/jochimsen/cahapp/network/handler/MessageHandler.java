package io.jochimsen.cahapp.network.handler;

import android.util.Log;

import io.jochimsen.cahapp.network.session.ServerSession;
import io.jochimsen.cahframework.channel_handler.ProcessingHandler;
import io.jochimsen.cahframework.protocol.object.message.MessageCode;
import io.jochimsen.cahframework.protocol.object.message.ProtocolMessage;
import io.jochimsen.cahframework.protocol.object.message.error.ErrorObject;
import io.jochimsen.cahframework.protocol.object.message.response.FinishedGameResponse;
import io.jochimsen.cahframework.protocol.object.message.response.GameMasterResponse;
import io.jochimsen.cahframework.protocol.object.message.response.NewRoundResponse;
import io.jochimsen.cahframework.protocol.object.message.response.SelectCardResponse;
import io.jochimsen.cahframework.protocol.object.message.response.StartGameResponse;
import io.jochimsen.cahframework.protocol.object.message.response.WaitForGameResponse;
import io.jochimsen.cahframework.session.Session;
import io.jochimsen.cahframework.util.ProtocolInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.UnicastSubject;

public class MessageHandler extends ProcessingHandler {

    private static final String TAG = "MessageHandler";

    private static ServerSession serverSession;

    public static ServerSession getServerSession() {
        return serverSession;
    }

    private final ProtocolMessage initialMessage;

    public MessageHandler(final ProtocolMessage initialMessage) {
        this.initialMessage = initialMessage;
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        serverSession = new ServerSession(ctx);

        if(initialMessage != null) {
            serverSession.say(initialMessage);
        }
    }

    @Override
    protected Session getSession(final ChannelHandlerContext ctx) {
        return serverSession;
    }

    @Override
    protected void handleMessage(final int messageId, final ProtocolInputStream rawMessage, final Session session) {
        Log.d(TAG, String.format("MessageId: %d", messageId));

        switch (messageId) {
            case MessageCode.START_GAME_RS: {
                final StartGameResponse startGameResponse = new StartGameResponse();
                startGameResponse.fromStream(rawMessage);

                MessageSubject.startGameResponseSubject.onNext(startGameResponse);
                break;
            }

            case MessageCode.WAIT_FOR_GAME_RS: {
                final WaitForGameResponse waitForGameResponse = new WaitForGameResponse();
                waitForGameResponse.fromStream(rawMessage);

                MessageSubject.waitForGameResponseSubject.onNext(waitForGameResponse);
                break;
            }

            case MessageCode.FINISHED_GAME_RS: {
                final FinishedGameResponse finishedGameResponse = new FinishedGameResponse();
                finishedGameResponse.fromStream(rawMessage);

                MessageSubject.finishedGameResponseSubject.onNext(finishedGameResponse);
                break;
            }

            case MessageCode.NEW_ROUND_RS: {
                final NewRoundResponse newRoundResponse = new NewRoundResponse();
                newRoundResponse.fromStream(rawMessage);

                MessageSubject.newRoundResponseSubject.onNext(newRoundResponse);
                break;
            }

            case MessageCode.GAME_MASTER_RS: {
                final GameMasterResponse gameMasterResponse = new GameMasterResponse();
                gameMasterResponse.fromStream(rawMessage);

                MessageSubject.gameMasterResponseSubject.onNext(gameMasterResponse);
                break;
            }

            case MessageCode.SELECT_CARD_RS: {
                final SelectCardResponse selectCardResponse = new SelectCardResponse();
                selectCardResponse.fromStream(rawMessage);

                MessageSubject.selectCardResponseSubject.onNext(selectCardResponse);
                break;
            }

            default: {
                Log.i(TAG, String.format("Logged unknown message: %d", messageId));
                break;
            }
        }
    }

    @Override
    protected void onErrorReceived(final ErrorObject errorObject, final Session session) {

    }

    @Override
    protected void closeSession(final Session session) {
        super.closeSession(session);

        if(session == serverSession) {
            serverSession = null;
        }
    }
}
