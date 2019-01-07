package io.jochimsen.cahapp.model;

import java.util.UUID;

public class PlayerModel {
    private final UUID playerId;
    private final String nickname;

    public PlayerModel(final UUID playerId, final String nickname) {
        this.playerId = playerId;
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public UUID getPlayerId() {
        return playerId;
    }
}
