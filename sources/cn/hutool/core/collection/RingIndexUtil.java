package cn.hutool.core.collection;

import cn.hutool.core.lang.Assert;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes.dex */
public class RingIndexUtil {
    public static int ringNextInt(int i2, AtomicInteger atomicInteger) throws Throwable {
        int i3;
        int i4;
        Assert.notNull(atomicInteger);
        Assert.isTrue(i2 > 0);
        if (i2 <= 1) {
            return 0;
        }
        do {
            i3 = atomicInteger.get();
            i4 = (i3 + 1) % i2;
        } while (!atomicInteger.compareAndSet(i3, i4));
        return i4;
    }

    public static int ringNextIntByObj(Object obj, AtomicInteger atomicInteger) throws IllegalArgumentException {
        Assert.notNull(obj);
        return ringNextInt(CollUtil.size(obj), atomicInteger);
    }

    public static long ringNextLong(long j2, AtomicLong atomicLong) throws Throwable {
        long j3;
        long j4;
        Assert.notNull(atomicLong);
        Assert.isTrue(j2 > 0);
        if (j2 <= 1) {
            return 0L;
        }
        do {
            j3 = atomicLong.get();
            j4 = (j3 + 1) % j2;
        } while (!atomicLong.compareAndSet(j3, j4));
        return j4;
    }
}
