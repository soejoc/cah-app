package dkgnkndz.lebk.cah_app.network.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import dkgnkndz.lebk.cah_app.MyApp;
import dkgnkndz.lebk.cah_app.activity.ActivityBase;
import dkgnkndz.lebk.cah_app.activity.LandingActivity;
import protocol.object.ProtocolObject;
import protocol.object.message.MessageCode;
import protocol.object.message.response.StartGameResponse;

public class MessageTransitionHandler extends Handler {
    public MessageTransitionHandler() {
        super(Looper.getMainLooper());
    }

    @Override
    public void handleMessage(final Message msg) {
        final ProtocolObject protocolObject = (ProtocolObject)msg.obj;
        final int messageId = msg.what;

        if(!(MyApp.getCurrentActivity() instanceof ActivityBase)){
            return;
        }

        final ActivityBase activityBase = (ActivityBase) MyApp.getCurrentActivity();

        switch (messageId) {
            case MessageCode.START_GAME_RS: {
                final StartGameResponse startGameResponse = (StartGameResponse)protocolObject;
                ((LandingActivity)activityBase).onStartGame(startGameResponse);
                break;
            }

            case MessageCode.WAIT_FOR_GAME_RS: {
                ((LandingActivity)activityBase).onWaitForGame();
                break;
            }

            default: {
                break;
            }
        }
    }
}
