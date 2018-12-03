package dkgnkndz.lebk.cah_app.fragment;

import android.support.v4.app.Fragment;

import dkgnkndz.lebk.cah_app.network.handler.MessageHandler;
import dkgnkndz.lebk.cah_app.network.session.ServerSession;
import protocol.object.message.ProtocolMessage;

public abstract class FragmentBase extends Fragment {
    protected void request(final ProtocolMessage requestMessage) {
        final ServerSession serverSession = MessageHandler.getServerSession();
        // ToDO: check if session exists
        serverSession.say(requestMessage);
    }
}
