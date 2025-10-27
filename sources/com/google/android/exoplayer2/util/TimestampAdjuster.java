package com.google.android.exoplayer2.util;

import androidx.annotation.GuardedBy;
import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import com.google.android.exoplayer2.C;

/* loaded from: classes3.dex */
public final class TimestampAdjuster {
    private static final long MAX_PTS_PLUS_ONE = 8589934592L;
    public static final long MODE_NO_OFFSET = Long.MAX_VALUE;
    public static final long MODE_SHARED = 9223372036854775806L;

    @GuardedBy("this")
    private long firstSampleTimestampUs;

    @GuardedBy("this")
    private long lastUnadjustedTimestampUs;
    private final ThreadLocal<Long> nextSampleTimestampUs = new ThreadLocal<>();

    @GuardedBy("this")
    private long timestampOffsetUs;

    public TimestampAdjuster(long j2) {
        reset(j2);
    }

    public static long ptsToUs(long j2) {
        return (j2 * 1000000) / 90000;
    }

    public static long usToNonWrappedPts(long j2) {
        return (j2 * 90000) / 1000000;
    }

    public static long usToWrappedPts(long j2) {
        return usToNonWrappedPts(j2) % 8589934592L;
    }

    public synchronized long adjustSampleTimestamp(long j2) {
        if (j2 == C.TIME_UNSET) {
            return C.TIME_UNSET;
        }
        if (this.timestampOffsetUs == C.TIME_UNSET) {
            long jLongValue = this.firstSampleTimestampUs;
            if (jLongValue == MODE_SHARED) {
                jLongValue = ((Long) Assertions.checkNotNull(this.nextSampleTimestampUs.get())).longValue();
            }
            this.timestampOffsetUs = jLongValue - j2;
            notifyAll();
        }
        this.lastUnadjustedTimestampUs = j2;
        return j2 + this.timestampOffsetUs;
    }

    public synchronized long adjustTsTimestamp(long j2) {
        if (j2 == C.TIME_UNSET) {
            return C.TIME_UNSET;
        }
        long j3 = this.lastUnadjustedTimestampUs;
        if (j3 != C.TIME_UNSET) {
            long jUsToNonWrappedPts = usToNonWrappedPts(j3);
            long j4 = (IjkMediaMeta.AV_CH_WIDE_RIGHT + jUsToNonWrappedPts) / 8589934592L;
            long j5 = ((j4 - 1) * 8589934592L) + j2;
            j2 += j4 * 8589934592L;
            if (Math.abs(j5 - jUsToNonWrappedPts) < Math.abs(j2 - jUsToNonWrappedPts)) {
                j2 = j5;
            }
        }
        return adjustSampleTimestamp(ptsToUs(j2));
    }

    public synchronized long getFirstSampleTimestampUs() {
        long j2;
        j2 = this.firstSampleTimestampUs;
        if (j2 == Long.MAX_VALUE || j2 == MODE_SHARED) {
            j2 = C.TIME_UNSET;
        }
        return j2;
    }

    public synchronized long getLastAdjustedTimestampUs() {
        long j2;
        j2 = this.lastUnadjustedTimestampUs;
        return j2 != C.TIME_UNSET ? j2 + this.timestampOffsetUs : getFirstSampleTimestampUs();
    }

    public synchronized long getTimestampOffsetUs() {
        return this.timestampOffsetUs;
    }

    public synchronized void reset(long j2) {
        this.firstSampleTimestampUs = j2;
        this.timestampOffsetUs = j2 == Long.MAX_VALUE ? 0L : -9223372036854775807L;
        this.lastUnadjustedTimestampUs = C.TIME_UNSET;
    }

    public synchronized void sharedInitializeOrWait(boolean z2, long j2) throws InterruptedException {
        Assertions.checkState(this.firstSampleTimestampUs == MODE_SHARED);
        if (this.timestampOffsetUs != C.TIME_UNSET) {
            return;
        }
        if (z2) {
            this.nextSampleTimestampUs.set(Long.valueOf(j2));
        } else {
            while (this.timestampOffsetUs == C.TIME_UNSET) {
                wait();
            }
        }
    }
}
