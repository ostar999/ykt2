package com.google.android.exoplayer2.source.dash;

import com.google.android.exoplayer2.source.dash.manifest.RangedUri;

/* loaded from: classes3.dex */
public interface DashSegmentIndex {
    public static final int INDEX_UNBOUNDED = -1;

    long getAvailableSegmentCount(long j2, long j3);

    long getDurationUs(long j2, long j3);

    long getFirstAvailableSegmentNum(long j2, long j3);

    long getFirstSegmentNum();

    long getNextSegmentAvailableTimeUs(long j2, long j3);

    long getSegmentCount(long j2);

    long getSegmentNum(long j2, long j3);

    RangedUri getSegmentUrl(long j2);

    long getTimeUs(long j2);

    boolean isExplicit();
}
