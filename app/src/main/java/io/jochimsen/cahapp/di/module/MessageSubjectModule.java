package io.jochimsen.cahapp.di.module;

import dagger.Module;
import dagger.Provides;
import io.jochimsen.cahapp.di.scope.AppScope;
import io.jochimsen.cahframework.protocol.object.message.response.FinishedGameResponse;
import io.jochimsen.cahframework.protocol.object.message.response.StartGameResponse;
import io.jochimsen.cahframework.protocol.object.message.response.WaitForGameResponse;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

@Module
public abstract class MessageSubjectModule {

    @AppScope
    @Provides
    static public Subject<StartGameResponse> provideStartGameSubject() {
        return PublishSubject.create();
    }

    @AppScope
    @Provides
    static public Subject<WaitForGameResponse> provideWaitForGameSubject() {
        return PublishSubject.create();
    }

    @AppScope
    @Provides
    static public Subject<FinishedGameResponse> provideFinishGameSubject() {
        return PublishSubject.create();
    }
}
