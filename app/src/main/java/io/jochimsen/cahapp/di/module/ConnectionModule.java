package io.jochimsen.cahapp.di.module;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.jochimsen.cahapp.di.scope.NetworkScope;
import io.jochimsen.cahframework.protocol.object.message.ProtocolMessage;

@Module
public class ConnectionModule {
    private final ProtocolMessage initialMessage;

    public ConnectionModule(ProtocolMessage initialMessage) {
        this.initialMessage = initialMessage;
    }

    @Named("initial_message")
    @NetworkScope
    @Provides
    public ProtocolMessage provideInitialMessage() {
        return initialMessage;
    }

    @Named("port")
    @NetworkScope
    @Provides
    static public int providePort() {
        return 666;
    }

    @Named("host")
    @NetworkScope
    @Provides
    static public String provideHost() {
        return "192.168.0.143";
    }
}
