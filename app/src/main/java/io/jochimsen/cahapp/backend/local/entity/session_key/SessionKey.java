package io.jochimsen.cahapp.backend.local.entity.session_key;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

@Entity
public class SessionKey {

    @NonNull
    @PrimaryKey
    private UUID sessionKey;

    @NonNull
    public UUID getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(@NonNull final UUID sessionKey) {
        this.sessionKey = sessionKey;
    }
}
