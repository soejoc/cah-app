package io.jochimsen.cahapp.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.jochimsen.cahapp.model.PlayerModel;

@Singleton
public class PlayerRepository {
    private final MutableLiveData<List<PlayerModel>> players = new MutableLiveData<>();

    @Inject
    public PlayerRepository() {

    }

    public LiveData<List<PlayerModel>> getPlayers() {
        return players;
    }

    public void setPlayers(final List<PlayerModel> playerModels) {
        this.players.setValue(playerModels);
    }
}
