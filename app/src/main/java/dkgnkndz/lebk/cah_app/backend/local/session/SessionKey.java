package dkgnkndz.lebk.cah_app.backend.local.session;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.UUID;

@Entity
public class SessionKey {
    @PrimaryKey
    private UUID sessionKey;

    public UUID getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(final UUID sessionKey) {
        this.sessionKey = sessionKey;
    }
}
