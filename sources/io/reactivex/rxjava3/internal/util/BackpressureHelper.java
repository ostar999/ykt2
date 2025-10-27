package io.reactivex.rxjava3.internal.util;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes8.dex */
public final class BackpressureHelper {
    private BackpressureHelper() {
        throw new IllegalStateException("No instances!");
    }

    public static long add(@NonNull AtomicLong requested, long n2) {
        long j2;
        do {
            j2 = requested.get();
            if (j2 == Long.MAX_VALUE) {
                return Long.MAX_VALUE;
            }
        } while (!requested.compareAndSet(j2, addCap(j2, n2)));
        return j2;
    }

    public static long addCancel(@NonNull AtomicLong requested, long n2) {
        long j2;
        do {
            j2 = requested.get();
            if (j2 == Long.MIN_VALUE) {
                return Long.MIN_VALUE;
            }
            if (j2 == Long.MAX_VALUE) {
                return Long.MAX_VALUE;
            }
        } while (!requested.compareAndSet(j2, addCap(j2, n2)));
        return j2;
    }

    public static long addCap(long a3, long b3) {
        long j2 = a3 + b3;
        if (j2 < 0) {
            return Long.MAX_VALUE;
        }
        return j2;
    }

    public static long multiplyCap(long a3, long b3) {
        long j2 = a3 * b3;
        if (((a3 | b3) >>> 31) == 0 || j2 / a3 == b3) {
            return j2;
        }
        return Long.MAX_VALUE;
    }

    public static long produced(@NonNull AtomicLong requested, long n2) {
        long j2;
        long j3;
        do {
            j2 = requested.get();
            if (j2 == Long.MAX_VALUE) {
                return Long.MAX_VALUE;
            }
            j3 = j2 - n2;
            if (j3 < 0) {
                RxJavaPlugins.onError(new IllegalStateException("More produced than requested: " + j3));
                j3 = 0L;
            }
        } while (!requested.compareAndSet(j2, j3));
        return j3;
    }

    public static long producedCancel(@NonNull AtomicLong requested, long n2) {
        long j2;
        long j3;
        do {
            j2 = requested.get();
            if (j2 == Long.MIN_VALUE) {
                return Long.MIN_VALUE;
            }
            if (j2 == Long.MAX_VALUE) {
                return Long.MAX_VALUE;
            }
            j3 = j2 - n2;
            if (j3 < 0) {
                RxJavaPlugins.onError(new IllegalStateException("More produced than requested: " + j3));
                j3 = 0L;
            }
        } while (!requested.compareAndSet(j2, j3));
        return j3;
    }
}
