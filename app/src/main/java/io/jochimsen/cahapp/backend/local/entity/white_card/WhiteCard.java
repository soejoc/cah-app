package io.jochimsen.cahapp.backend.local.entity.white_card;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class WhiteCard {

    @PrimaryKey
    private long whiteCardId;

    @NonNull
    private String text;

    public WhiteCard() {}

    @Ignore
    public WhiteCard(final long whiteCardId, @NonNull final String text) {
        this.whiteCardId = whiteCardId;
        this.text = text;
    }

    public long getWhiteCardId() {
        return whiteCardId;
    }

    public void setWhiteCardId(final long whiteCardId) {
        this.whiteCardId = whiteCardId;
    }

    @NonNull
    public String getText() {
        return text;
    }

    public void setText(@NonNull final String text) {
        this.text = text;
    }
}
