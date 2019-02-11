package io.jochimsen.cahapp.di.module;

import dagger.Module;
import dagger.Provides;
import io.jochimsen.cahapp.di.qualifier.GameserverHost;
import io.jochimsen.cahapp.di.qualifier.GameserverPort;
import io.jochimsen.cahapp.di.scope.NetworkScope;

@Module
public abstract class ConnectionModule {

    @GameserverPort
    @NetworkScope
    @Provides
    static public int providePort() {
        return 666;
    }

    @GameserverHost
    @NetworkScope
    @Provides
    static public String provideHost() {
        return "192.168.0.143";
    }
}
