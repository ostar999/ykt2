package com.google.android.exoplayer2.source.dash;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.source.dash.manifest.RangedUri;

/* loaded from: classes3.dex */
public final class DashWrappingSegmentIndex implements DashSegmentIndex {
    private final ChunkIndex chunkIndex;
    private final long timeOffsetUs;

    public DashWrappingSegmentIndex(ChunkIndex chunkIndex, long j2) {
        this.chunkIndex = chunkIndex;
        this.timeOffsetUs = j2;
    }

    @Override // com.google.android.exoplayer2.source.dash.DashSegmentIndex
    public long getAvailableSegmentCount(long j2, long j3) {
        return this.chunkIndex.length;
    }

    @Override // com.google.android.exoplayer2.source.dash.DashSegmentIndex
    public long getDurationUs(long j2, long j3) {
        return this.chunkIndex.durationsUs[(int) j2];
    }

    @Override // com.google.android.exoplayer2.source.dash.DashSegmentIndex
    public long getFirstAvailableSegmentNum(long j2, long j3) {
        return 0L;
    }

    @Override // com.google.android.exoplayer2.source.dash.DashSegmentIndex
    public long getFirstSegmentNum() {
        return 0L;
    }

    @Override // com.google.android.exoplayer2.source.dash.DashSegmentIndex
    public long getNextSegmentAvailableTimeUs(long j2, long j3) {
        return C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.source.dash.DashSegmentIndex
    public long getSegmentCount(long j2) {
        return this.chunkIndex.length;
    }

    @Override // com.google.android.exoplayer2.source.dash.DashSegmentIndex
    public long getSegmentNum(long j2, long j3) {
        return this.chunkIndex.getChunkIndex(j2 + this.timeOffsetUs);
    }

    @Override // com.google.android.exoplayer2.source.dash.DashSegmentIndex
    public RangedUri getSegmentUrl(long j2) {
        return new RangedUri(null, this.chunkIndex.offsets[(int) j2], r0.sizes[r8]);
    }

    @Override // com.google.android.exoplayer2.source.dash.DashSegmentIndex
    public long getTimeUs(long j2) {
        return this.chunkIndex.timesUs[(int) j2] - this.timeOffsetUs;
    }

    @Override // com.google.android.exoplayer2.source.dash.DashSegmentIndex
    public boolean isExplicit() {
        return true;
    }
}
