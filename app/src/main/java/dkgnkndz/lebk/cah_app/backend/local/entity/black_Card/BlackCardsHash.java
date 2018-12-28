package dkgnkndz.lebk.cah_app.backend.local.entity.black_Card;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class BlackCardsHash {

    @NonNull
    @PrimaryKey
    private String hash;

    public BlackCardsHash() {}

    @Ignore
    public BlackCardsHash(final String hash) {
        this.hash = hash;
    }

    @NonNull
    public String getHash() {
        return hash;
    }

    public void setHash(@NonNull final String hash) {
        this.hash = hash;
    }
}
