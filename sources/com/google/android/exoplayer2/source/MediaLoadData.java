package com.google.android.exoplayer2.source;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;

/* loaded from: classes3.dex */
public final class MediaLoadData {
    public final int dataType;
    public final long mediaEndTimeMs;
    public final long mediaStartTimeMs;

    @Nullable
    public final Format trackFormat;

    @Nullable
    public final Object trackSelectionData;
    public final int trackSelectionReason;
    public final int trackType;

    public MediaLoadData(int i2) {
        this(i2, -1, null, 0, null, C.TIME_UNSET, C.TIME_UNSET);
    }

    public MediaLoadData(int i2, int i3, @Nullable Format format, int i4, @Nullable Object obj, long j2, long j3) {
        this.dataType = i2;
        this.trackType = i3;
        this.trackFormat = format;
        this.trackSelectionReason = i4;
        this.trackSelectionData = obj;
        this.mediaStartTimeMs = j2;
        this.mediaEndTimeMs = j3;
    }
}
