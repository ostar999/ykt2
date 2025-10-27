package com.google.android.exoplayer2.source.hls;

import android.util.SparseArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;

/* loaded from: classes3.dex */
public final class TimestampAdjusterProvider {
    private final SparseArray<TimestampAdjuster> timestampAdjusters = new SparseArray<>();

    public TimestampAdjuster getAdjuster(int i2) {
        TimestampAdjuster timestampAdjuster = this.timestampAdjusters.get(i2);
        if (timestampAdjuster != null) {
            return timestampAdjuster;
        }
        TimestampAdjuster timestampAdjuster2 = new TimestampAdjuster(TimestampAdjuster.MODE_SHARED);
        this.timestampAdjusters.put(i2, timestampAdjuster2);
        return timestampAdjuster2;
    }

    public void reset() {
        this.timestampAdjusters.clear();
    }
}
