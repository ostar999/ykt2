package io.reactivex.internal.util;

import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes8.dex */
public final class BackpressureHelper {
    private BackpressureHelper() {
        throw new IllegalStateException("No instances!");
    }

    public static long add(AtomicLong atomicLong, long j2) {
        long j3;
        do {
            j3 = atomicLong.get();
            if (j3 == Long.MAX_VALUE) {
                return Long.MAX_VALUE;
            }
        } while (!atomicLong.compareAndSet(j3, addCap(j3, j2)));
        return j3;
    }

    public static long addCancel(AtomicLong atomicLong, long j2) {
        long j3;
        do {
            j3 = atomicLong.get();
            if (j3 == Long.MIN_VALUE) {
                return Long.MIN_VALUE;
            }
            if (j3 == Long.MAX_VALUE) {
                return Long.MAX_VALUE;
            }
        } while (!atomicLong.compareAndSet(j3, addCap(j3, j2)));
        return j3;
    }

    public static long addCap(long j2, long j3) {
        long j4 = j2 + j3;
        if (j4 < 0) {
            return Long.MAX_VALUE;
        }
        return j4;
    }

    public static long multiplyCap(long j2, long j3) {
        long j4 = j2 * j3;
        if (((j2 | j3) >>> 31) == 0 || j4 / j2 == j3) {
            return j4;
        }
        return Long.MAX_VALUE;
    }

    public static long produced(AtomicLong atomicLong, long j2) {
        long j3;
        long j4;
        do {
            j3 = atomicLong.get();
            if (j3 == Long.MAX_VALUE) {
                return Long.MAX_VALUE;
            }
            j4 = j3 - j2;
            if (j4 < 0) {
                RxJavaPlugins.onError(new IllegalStateException("More produced than requested: " + j4));
                j4 = 0L;
            }
        } while (!atomicLong.compareAndSet(j3, j4));
        return j4;
    }

    public static long producedCancel(AtomicLong atomicLong, long j2) {
        long j3;
        long j4;
        do {
            j3 = atomicLong.get();
            if (j3 == Long.MIN_VALUE) {
                return Long.MIN_VALUE;
            }
            if (j3 == Long.MAX_VALUE) {
                return Long.MAX_VALUE;
            }
            j4 = j3 - j2;
            if (j4 < 0) {
                RxJavaPlugins.onError(new IllegalStateException("More produced than requested: " + j4));
                j4 = 0L;
            }
        } while (!atomicLong.compareAndSet(j3, j4));
        return j4;
    }
}
