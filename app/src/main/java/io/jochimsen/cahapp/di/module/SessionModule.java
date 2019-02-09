package io.jochimsen.cahapp.di.module;

import dagger.Module;
import dagger.Provides;
import io.jochimsen.cahapp.di.scope.SessionScope;
import io.jochimsen.cahapp.network.session.ServerSession;

@Module
public class SessionModule {
    final private ServerSession serverSession;

    public SessionModule(ServerSession serverSession) {
        this.serverSession = serverSession;
    }

    @SessionScope
    @Provides
    public ServerSession provideServerSession() {
        return serverSession;
    }
}
