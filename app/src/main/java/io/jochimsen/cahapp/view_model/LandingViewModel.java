package io.jochimsen.cahapp.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import io.jochimsen.cahapp.MyApp;
import io.jochimsen.cahapp.backend.local.entity.black_card.BlackCard;
import io.jochimsen.cahapp.backend.local.entity.session_key.SessionKey;
import io.jochimsen.cahapp.backend.local.entity.session_key.SessionKeyDao;
import io.jochimsen.cahapp.backend.local.entity.white_card.WhiteCard;
import io.jochimsen.cahapp.backend.ressource.Resource;
import io.jochimsen.cahapp.repository.BlackCardRepository;
import io.jochimsen.cahapp.repository.ProtocolMessageRepository;
import io.jochimsen.cahapp.repository.WhiteCardRepository;
import io.jochimsen.cahframework.protocol.object.message.request.RestartGameRequest;
import io.jochimsen.cahframework.protocol.object.message.request.StartGameRequest;
import io.jochimsen.cahframework.protocol.object.message.response.FinishedGameResponse;
import io.jochimsen.cahframework.protocol.object.message.response.StartGameResponse;
import io.jochimsen.cahframework.protocol.object.message.response.WaitForGameResponse;
import lombok.Getter;

public class LandingViewModel extends ViewModel {
    private final MyApp myApp;
    private final BlackCardRepository blackCardRepository;
    private final WhiteCardRepository whiteCardRepository;
    private final SessionKeyDao sessionKeyDao;

    @Getter
    private final LiveData<StartGameResponse> startGameResponse;

    @Getter
    private final LiveData<WaitForGameResponse> waitForGameResponse;

    @Getter
    private final LiveData<FinishedGameResponse> finishedGameResponse;

    private final MutableLiveData<Boolean> startedGame = new MutableLiveData<>();

    private String nickname;

    @Inject
    LandingViewModel(final ProtocolMessageRepository protocolMessageRepository, final MyApp myApp, final BlackCardRepository blackCardRepository, final WhiteCardRepository whiteCardRepository, final SessionKeyDao sessionKeyDao) {
        this.myApp = myApp;
        this.blackCardRepository = blackCardRepository;
        this.whiteCardRepository = whiteCardRepository;
        this.sessionKeyDao = sessionKeyDao;

        startGameResponse = LiveDataReactiveStreams.fromPublisher(protocolMessageRepository.observe(StartGameResponse.class));
        waitForGameResponse = LiveDataReactiveStreams.fromPublisher(protocolMessageRepository.observe(WaitForGameResponse.class));
        finishedGameResponse = LiveDataReactiveStreams.fromPublisher(protocolMessageRepository.observe(FinishedGameResponse.class));
    }

    public LiveData<SessionKey> getSessionKey() {
        return sessionKeyDao.getSessionKey();
    }

    public LiveData<Resource<List<BlackCard>>> getBlackCards() {
        return blackCardRepository.getBlackCards();
    }

    public LiveData<Resource<List<WhiteCard>>> getWhiteCards() {
        return whiteCardRepository.getWhiteCards();
    }

    public LiveData<Boolean> getStartedGame() {
        return startedGame;
    }

    public void restartGame(final SessionKey sessionKey) {
        final RestartGameRequest restartGameRequest = new RestartGameRequest(sessionKey.getSessionKey());
        myApp.createConnection(restartGameRequest);
    }

    public void play(final String nickname) {
        this.nickname = nickname;
        startedGame.setValue(startGame());
    }

    public boolean startGame() {
        if(getBlackCards().getValue().getStatus() == Resource.Status.SUCCESS && getWhiteCards().getValue().getStatus() == Resource.Status.SUCCESS) {
            final StartGameRequest startGameRequest = new StartGameRequest(nickname);
            myApp.createConnection(startGameRequest);
            return true;
        } else {
            return false;
        }
    }
}
