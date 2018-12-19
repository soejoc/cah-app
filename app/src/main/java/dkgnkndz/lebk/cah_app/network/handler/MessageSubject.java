package dkgnkndz.lebk.cah_app.network.handler;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import protocol.object.message.response.FinishedGameResponse;
import protocol.object.message.response.StartGameResponse;
import protocol.object.message.response.WaitForGameResponse;

public final class MessageSubject {
    public static Subject<StartGameResponse> startGameResponseSubject = PublishSubject.create();
    public static Subject<WaitForGameResponse> waitForGameResponseSubject = PublishSubject.create();
    public static Subject<FinishedGameResponse> finishedGameResponseSubject = PublishSubject.create();
}
