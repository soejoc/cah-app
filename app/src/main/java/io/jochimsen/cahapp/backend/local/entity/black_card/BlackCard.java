package io.jochimsen.cahapp.backend.local.entity.black_card;

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

    private int blankCount;

    public BlackCard() {}

    @Ignore
    public BlackCard(final long blackCardId, @NonNull final String text, final int blankCount) {
        this.blackCardId = blackCardId;
        this.text = text;
        this.blankCount = blankCount;
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

    public int getBlankCount() {
        return blankCount;
    }

    public void setBlankCount(final int blankCount) {
        this.blankCount = blankCount;
    }
}
