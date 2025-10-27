package org.eclipse.jetty.util.statistic;

import java.util.concurrent.atomic.AtomicLong;
import org.eclipse.jetty.util.Atomics;

/* loaded from: classes9.dex */
public class SampleStatistic {
    protected final AtomicLong _max = new AtomicLong();
    protected final AtomicLong _total = new AtomicLong();
    protected final AtomicLong _count = new AtomicLong();
    protected final AtomicLong _totalVariance100 = new AtomicLong();

    public long getCount() {
        return this._count.get();
    }

    public long getMax() {
        return this._max.get();
    }

    public double getMean() {
        return this._total.get() / this._count.get();
    }

    public double getStdDev() {
        return Math.sqrt(getVariance());
    }

    public long getTotal() {
        return this._total.get();
    }

    public double getVariance() {
        long j2 = this._totalVariance100.get();
        if (this._count.get() > 1) {
            return (j2 / 100.0d) / (r2 - 1);
        }
        return 0.0d;
    }

    public void reset() {
        this._max.set(0L);
        this._total.set(0L);
        this._count.set(0L);
        this._totalVariance100.set(0L);
    }

    public void set(long j2) {
        long jAddAndGet = this._total.addAndGet(j2);
        long jIncrementAndGet = this._count.incrementAndGet();
        if (jIncrementAndGet > 1) {
            long j3 = (10 * j2) - ((jAddAndGet * 10) / jIncrementAndGet);
            this._totalVariance100.addAndGet(j3 * j3);
        }
        Atomics.updateMax(this._max, j2);
    }
}
