package io.jochimsen.cahapp.di.component;

import dagger.Subcomponent;
import io.jochimsen.cahapp.di.module.NettyModule;
import io.jochimsen.cahapp.di.module.SessionModule;
import io.jochimsen.cahapp.di.scope.NetworkScope;
import io.jochimsen.cahapp.network.thread.NetworkWorker;

@NetworkScope
@Subcomponent(modules = {
        NettyModule.class
})
public interface NetworkComponent {
    NetworkWorker networkWorker();

    SessionComponent sessionComponent(final SessionModule sessionModule);
}
