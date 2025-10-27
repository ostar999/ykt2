package com.google.zxing.oned.rss.expanded.decoders;

/* loaded from: classes4.dex */
final class BlockParsedResult {
    private final DecodedInformation decodedInformation;
    private final boolean finished;

    public BlockParsedResult(boolean z2) {
        this(null, z2);
    }

    public DecodedInformation getDecodedInformation() {
        return this.decodedInformation;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public BlockParsedResult(DecodedInformation decodedInformation, boolean z2) {
        this.finished = z2;
        this.decodedInformation = decodedInformation;
    }
}
