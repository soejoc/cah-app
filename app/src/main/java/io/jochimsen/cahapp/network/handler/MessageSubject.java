package io.jochimsen.cahapp.network.handler;

import io.jochimsen.cahframework.protocol.object.message.response.finished_game.FinishedGameResponse;
import io.jochimsen.cahframework.protocol.object.message.response.start_game.StartGameResponse;
import io.jochimsen.cahframework.protocol.object.message.response.wait_for_game.WaitForGameResponse;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public final class MessageSubject {
    public static final Subject<StartGameResponse> startGameResponseSubject = PublishSubject.create();
    public static Subject<WaitForGameResponse> waitForGameResponseSubject = PublishSubject.create();
    public static Subject<FinishedGameResponse> finishedGameResponseSubject = PublishSubject.create();
}
