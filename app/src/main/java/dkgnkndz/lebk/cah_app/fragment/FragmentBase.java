package dkgnkndz.lebk.cah_app.fragment;

import android.support.v4.app.Fragment;

import dkgnkndz.lebk.cah_app.network.handler.MessageHandler;
import dkgnkndz.lebk.cah_app.network.session.ServerSession;
import dkgnkndz.lebk.cah_app.throwable.error.NoSessionError;
import protocol.object.message.ProtocolMessage;

public abstract class FragmentBase extends Fragment {
    protected void request(final ProtocolMessage requestMessage) {
        final ServerSession serverSession = ServerSession.getServerSession();

        if(serverSession == null) {
            throw new NoSessionError();
        }

        serverSession.say(requestMessage);
    }
}
