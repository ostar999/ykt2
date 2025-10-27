package org.eclipse.jetty.util.statistic;

import java.util.concurrent.atomic.AtomicLong;
import org.eclipse.jetty.util.Atomics;

/* loaded from: classes9.dex */
public class CounterStatistic {
    protected final AtomicLong _max = new AtomicLong();
    protected final AtomicLong _curr = new AtomicLong();
    protected final AtomicLong _total = new AtomicLong();

    public void add(long j2) {
        long jAddAndGet = this._curr.addAndGet(j2);
        if (j2 > 0) {
            this._total.addAndGet(j2);
        }
        Atomics.updateMax(this._max, jAddAndGet);
    }

    public void decrement() {
        add(-1L);
    }

    public long getCurrent() {
        return this._curr.get();
    }

    public long getMax() {
        return this._max.get();
    }

    public long getTotal() {
        return this._total.get();
    }

    public void increment() {
        add(1L);
    }

    public void reset() {
        reset(0L);
    }

    public void subtract(long j2) {
        add(-j2);
    }

    public void reset(long j2) {
        this._max.set(j2);
        this._curr.set(j2);
        this._total.set(0L);
    }
}
