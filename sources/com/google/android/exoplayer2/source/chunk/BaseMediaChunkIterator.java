package com.google.android.exoplayer2.source.chunk;

import java.util.NoSuchElementException;

/* loaded from: classes3.dex */
public abstract class BaseMediaChunkIterator implements MediaChunkIterator {
    private long currentIndex;
    private final long fromIndex;
    private final long toIndex;

    public BaseMediaChunkIterator(long j2, long j3) {
        this.fromIndex = j2;
        this.toIndex = j3;
        reset();
    }

    public final void checkInBounds() {
        long j2 = this.currentIndex;
        if (j2 < this.fromIndex || j2 > this.toIndex) {
            throw new NoSuchElementException();
        }
    }

    public final long getCurrentIndex() {
        return this.currentIndex;
    }

    @Override // com.google.android.exoplayer2.source.chunk.MediaChunkIterator
    public boolean isEnded() {
        return this.currentIndex > this.toIndex;
    }

    @Override // com.google.android.exoplayer2.source.chunk.MediaChunkIterator
    public boolean next() {
        this.currentIndex++;
        return !isEnded();
    }

    @Override // com.google.android.exoplayer2.source.chunk.MediaChunkIterator
    public void reset() {
        this.currentIndex = this.fromIndex - 1;
    }
}
