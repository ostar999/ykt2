package com.google.android.exoplayer2;

/* loaded from: classes3.dex */
public final class IllegalSeekPositionException extends IllegalStateException {
    public final long positionMs;
    public final Timeline timeline;
    public final int windowIndex;

    public IllegalSeekPositionException(Timeline timeline, int i2, long j2) {
        this.timeline = timeline;
        this.windowIndex = i2;
        this.positionMs = j2;
    }
}
