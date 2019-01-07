package io.jochimsen.cahapp.model;

public class WhiteCardModel {
    private final long whiteCardId;
    private final String text;

    public WhiteCardModel(final long whiteCardId, final String text) {
        this.whiteCardId = whiteCardId;
        this.text = text;
    }

    public long getWhiteCardId() {
        return whiteCardId;
    }

    public String getText() {
        return text;
    }
}
