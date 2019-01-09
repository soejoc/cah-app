package io.jochimsen.cahapp.model;

public class MergedCardModel {
    private final String mergedText;
    private final long whiteCardId;

    public MergedCardModel(final String mergedText, final long whiteCardId) {
        this.mergedText = mergedText;
        this.whiteCardId = whiteCardId;
    }

    public String getMergedText() {
        return mergedText;
    }

    public long getWhiteCardId() {
        return whiteCardId;
    }
}
