package dkgnkndz.lebk.cah_app.backend.local.entity.black_Card;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class BlackCard {
    @PrimaryKey
    private long blackCardId;

    @NonNull
    private String text;

    public BlackCard() {}

    @Ignore
    public BlackCard(final long blackCardId, @NonNull final String text) {
        this.blackCardId = blackCardId;
        this.text = text;
    }

    public long getBlackCardId() {
        return blackCardId;
    }

    public void setBlackCardId(final long blackCardId) {
        this.blackCardId = blackCardId;
    }

    @NonNull
    public String getText() {
        return text;
    }

    public void setText(@NonNull final String text) {
        this.text = text;
    }
}
