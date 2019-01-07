package io.jochimsen.cahapp.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import io.jochimsen.cahapp.model.PlayerModel;
import io.jochimsen.cahapp.repository.PlayerRepository;

public class PlayerViewModel extends ViewModel {
    private PlayerRepository playerRepository;

    public void setPlayerRepository(final PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public LiveData<List<PlayerModel>> getPlayer() {
        return playerRepository.getPlayers();
    }
}
