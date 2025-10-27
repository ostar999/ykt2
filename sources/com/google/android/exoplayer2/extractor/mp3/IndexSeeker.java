package com.google.android.exoplayer2.extractor.mp3;

import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.util.LongArray;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes3.dex */
final class IndexSeeker implements Seeker {

    @VisibleForTesting
    static final long MIN_TIME_BETWEEN_POINTS_US = 100000;
    private final long dataEndPosition;
    private long durationUs;
    private final LongArray positions;
    private final LongArray timesUs;

    public IndexSeeker(long j2, long j3, long j4) {
        this.durationUs = j2;
        this.dataEndPosition = j4;
        LongArray longArray = new LongArray();
        this.timesUs = longArray;
        LongArray longArray2 = new LongArray();
        this.positions = longArray2;
        longArray.add(0L);
        longArray2.add(j3);
    }

    @Override // com.google.android.exoplayer2.extractor.mp3.Seeker
    public long getDataEndPosition() {
        return this.dataEndPosition;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public long getDurationUs() {
        return this.durationUs;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public SeekMap.SeekPoints getSeekPoints(long j2) {
        int iBinarySearchFloor = Util.binarySearchFloor(this.timesUs, j2, true, true);
        SeekPoint seekPoint = new SeekPoint(this.timesUs.get(iBinarySearchFloor), this.positions.get(iBinarySearchFloor));
        if (seekPoint.timeUs == j2 || iBinarySearchFloor == this.timesUs.size() - 1) {
            return new SeekMap.SeekPoints(seekPoint);
        }
        int i2 = iBinarySearchFloor + 1;
        return new SeekMap.SeekPoints(seekPoint, new SeekPoint(this.timesUs.get(i2), this.positions.get(i2)));
    }

    @Override // com.google.android.exoplayer2.extractor.mp3.Seeker
    public long getTimeUs(long j2) {
        return this.timesUs.get(Util.binarySearchFloor(this.positions, j2, true, true));
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public boolean isSeekable() {
        return true;
    }

    public boolean isTimeUsInIndex(long j2) {
        LongArray longArray = this.timesUs;
        return j2 - longArray.get(longArray.size() - 1) < MIN_TIME_BETWEEN_POINTS_US;
    }

    public void maybeAddSeekPoint(long j2, long j3) {
        if (isTimeUsInIndex(j2)) {
            return;
        }
        this.timesUs.add(j2);
        this.positions.add(j3);
    }

    public void setDurationUs(long j2) {
        this.durationUs = j2;
    }
}
