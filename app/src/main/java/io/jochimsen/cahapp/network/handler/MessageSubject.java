package io.jochimsen.cahapp.network.handler;

import io.jochimsen.cahframework.protocol.object.message.response.AddCardsResponse;
import io.jochimsen.cahframework.protocol.object.message.response.FinishedGameResponse;
import io.jochimsen.cahframework.protocol.object.message.response.GameMasterResponse;
import io.jochimsen.cahframework.protocol.object.message.response.SelectCardResponse;
import io.jochimsen.cahframework.protocol.object.message.response.StartGameResponse;
import io.jochimsen.cahframework.protocol.object.message.response.WaitForGameResponse;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.Subject;

public final class MessageSubject {
    public static final Subject<StartGameResponse> startGameResponseSubject = PublishSubject.create();
    public static final Subject<WaitForGameResponse> waitForGameResponseSubject = PublishSubject.create();
    public static final Subject<FinishedGameResponse> finishedGameResponseSubject = PublishSubject.create();
    public static final Subject<AddCardsResponse> addCardsResponseSubject = ReplaySubject.create();
    public static final Subject<GameMasterResponse> gameMasterResponseSubject = ReplaySubject.create();
    public static final Subject<SelectCardResponse> selectCardResponseSubject = ReplaySubject.create();
}
