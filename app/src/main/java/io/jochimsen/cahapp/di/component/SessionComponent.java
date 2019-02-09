package io.jochimsen.cahapp.di.component;

import dagger.Subcomponent;
import io.jochimsen.cahapp.di.module.SessionActivityModule;
import io.jochimsen.cahapp.di.module.SessionModule;
import io.jochimsen.cahapp.di.scope.SessionScope;

@SessionScope
@Subcomponent(modules = {
        SessionModule.class,
        SessionActivityModule.class
})
public interface SessionComponent {
}
