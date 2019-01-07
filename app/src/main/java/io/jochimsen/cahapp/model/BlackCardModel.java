package io.jochimsen.cahapp.model;

public class BlackCardModel {
    private final long blackCardId;
    private final String text;

    public BlackCardModel(final long blackCardId, final String text) {
        this.blackCardId = blackCardId;
        this.text = text;
    }

    public long getBlackCardId() {
        return blackCardId;
    }

    public String getText() {
        return text;
    }
}
