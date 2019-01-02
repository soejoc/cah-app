package io.jochimsen.cahapp.network.handler;

import io.jochimsen.cahframework.protocol.object.message.response.FinishedGameResponse;
import io.jochimsen.cahframework.protocol.object.message.response.StartGameResponse;
import io.jochimsen.cahframework.protocol.object.message.response.WaitForGameResponse;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public final class MessageSubject {
    public static final Subject<StartGameResponse> startGameResponseSubject = PublishSubject.create();
    public static Subject<WaitForGameResponse> waitForGameResponseSubject = PublishSubject.create();
    public static Subject<FinishedGameResponse> finishedGameResponseSubject = PublishSubject.create();
}
