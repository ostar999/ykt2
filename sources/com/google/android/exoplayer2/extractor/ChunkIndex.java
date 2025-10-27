package com.google.android.exoplayer2.extractor;

import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;

/* loaded from: classes3.dex */
public final class ChunkIndex implements SeekMap {
    private final long durationUs;
    public final long[] durationsUs;
    public final int length;
    public final long[] offsets;
    public final int[] sizes;
    public final long[] timesUs;

    public ChunkIndex(int[] iArr, long[] jArr, long[] jArr2, long[] jArr3) {
        this.sizes = iArr;
        this.offsets = jArr;
        this.durationsUs = jArr2;
        this.timesUs = jArr3;
        int length = iArr.length;
        this.length = length;
        if (length > 0) {
            this.durationUs = jArr2[length - 1] + jArr3[length - 1];
        } else {
            this.durationUs = 0L;
        }
    }

    public int getChunkIndex(long j2) {
        return Util.binarySearchFloor(this.timesUs, j2, true, true);
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public long getDurationUs() {
        return this.durationUs;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public SeekMap.SeekPoints getSeekPoints(long j2) {
        int chunkIndex = getChunkIndex(j2);
        SeekPoint seekPoint = new SeekPoint(this.timesUs[chunkIndex], this.offsets[chunkIndex]);
        if (seekPoint.timeUs >= j2 || chunkIndex == this.length - 1) {
            return new SeekMap.SeekPoints(seekPoint);
        }
        int i2 = chunkIndex + 1;
        return new SeekMap.SeekPoints(seekPoint, new SeekPoint(this.timesUs[i2], this.offsets[i2]));
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public boolean isSeekable() {
        return true;
    }

    public String toString() {
        int i2 = this.length;
        String string = Arrays.toString(this.sizes);
        String string2 = Arrays.toString(this.offsets);
        String string3 = Arrays.toString(this.timesUs);
        String string4 = Arrays.toString(this.durationsUs);
        StringBuilder sb = new StringBuilder(String.valueOf(string).length() + 71 + String.valueOf(string2).length() + String.valueOf(string3).length() + String.valueOf(string4).length());
        sb.append("ChunkIndex(length=");
        sb.append(i2);
        sb.append(", sizes=");
        sb.append(string);
        sb.append(", offsets=");
        sb.append(string2);
        sb.append(", timeUs=");
        sb.append(string3);
        sb.append(", durationsUs=");
        sb.append(string4);
        sb.append(")");
        return sb.toString();
    }
}
