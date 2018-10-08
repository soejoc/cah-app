package dkgnkndz.lebk.cah_app.activity;

import android.support.v7.app.AppCompatActivity;

import dkgnkndz.lebk.cah_app.network.handler.ProcessingHandler;
import dkgnkndz.lebk.cah_app.network.session.ServerSession;
import protocol.object.ProtocolObject;

public abstract class ActivityBase extends AppCompatActivity {
    protected void request(final ProtocolObject protocolObject) {
        final ServerSession serverSession = ProcessingHandler.getServerSession();
        // ToDO: check if session exists
        serverSession.say(protocolObject);
    }
}
