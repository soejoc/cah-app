package dkgnkndz.lebk.cah_app.activity;

import android.support.v7.app.AppCompatActivity;

import dkgnkndz.lebk.cah_app.network.handler.MessageHandler;
import dkgnkndz.lebk.cah_app.network.session.ServerSession;
import protocol.object.ProtocolObject;
import protocol.object.message.ProtocolMessage;

public abstract class ActivityBase extends AppCompatActivity {
    protected void request(final ProtocolMessage requestMessage) {
        final ServerSession serverSession = MessageHandler.getServerSession();
        // ToDO: check if session exists
        serverSession.say(requestMessage);
    }
}
