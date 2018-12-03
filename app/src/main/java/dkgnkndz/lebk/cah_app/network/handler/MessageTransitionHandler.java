package dkgnkndz.lebk.cah_app.network.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import protocol.object.ProtocolObject;
import protocol.object.message.MessageCode;
import protocol.object.message.response.StartGameResponse;

public class MessageTransitionHandler extends Handler {
    private final ResponseMessageHandler responseMessageHandler;

    public MessageTransitionHandler(final ResponseMessageHandler responseMessageHandler) {
        super(Looper.getMainLooper());

        this.responseMessageHandler = responseMessageHandler;
    }

    @Override
    public void handleMessage(final Message msg) {
        final ProtocolObject protocolObject = (ProtocolObject)msg.obj;
        final int messageId = msg.what;

        switch (messageId) {
            case MessageCode.START_GAME_RS: {
                final StartGameResponse startGameResponse = (StartGameResponse)protocolObject;
                responseMessageHandler.onStartGame(startGameResponse);
                break;
            }

            case MessageCode.WAIT_FOR_GAME_RS: {
                responseMessageHandler.onWaitForGame();
                break;
            }

            default: {
                break;
            }
        }
    }
}
