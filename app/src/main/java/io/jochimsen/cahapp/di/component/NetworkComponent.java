package io.jochimsen.cahapp.di.component;

import dagger.BindsInstance;
import dagger.Subcomponent;
import io.jochimsen.cahapp.di.module.ConnectionModule;
import io.jochimsen.cahapp.di.module.NettyModule;
import io.jochimsen.cahapp.di.qualifier.InitialMessage;
import io.jochimsen.cahapp.di.scope.NetworkScope;
import io.jochimsen.cahapp.network.thread.NetworkWorker;
import io.jochimsen.cahframework.protocol.object.message.ProtocolMessage;

@NetworkScope
@Subcomponent(modules = {
        ConnectionModule.class,
        NettyModule.class
})
public interface NetworkComponent {
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        Builder initialMessage(@InitialMessage final ProtocolMessage initialMessage);
        NetworkComponent build();
    }

    NetworkWorker networkWorker();

    SessionComponent.Builder sessionComponentBuilder();
}
