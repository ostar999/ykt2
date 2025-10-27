package org.eclipse.jetty.util;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes9.dex */
public class Atomics {
    private Atomics() {
    }

    public static void updateMax(AtomicLong atomicLong, long j2) {
        long j3 = atomicLong.get();
        while (j2 > j3 && !atomicLong.compareAndSet(j3, j2)) {
            j3 = atomicLong.get();
        }
    }

    public static void updateMin(AtomicLong atomicLong, long j2) {
        long j3 = atomicLong.get();
        while (j2 < j3 && !atomicLong.compareAndSet(j3, j2)) {
            j3 = atomicLong.get();
        }
    }

    public static void updateMax(AtomicInteger atomicInteger, int i2) {
        int i3 = atomicInteger.get();
        while (i2 > i3 && !atomicInteger.compareAndSet(i3, i2)) {
            i3 = atomicInteger.get();
        }
    }

    public static void updateMin(AtomicInteger atomicInteger, int i2) {
        int i3 = atomicInteger.get();
        while (i2 < i3 && !atomicInteger.compareAndSet(i3, i2)) {
            i3 = atomicInteger.get();
        }
    }
}
