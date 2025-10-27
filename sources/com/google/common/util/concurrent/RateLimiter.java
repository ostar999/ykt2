package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.SmoothRateLimiter;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;

@Beta
@GwtIncompatible
/* loaded from: classes4.dex */
public abstract class RateLimiter {

    @MonotonicNonNullDecl
    private volatile Object mutexDoNotUseDirectly;
    private final SleepingStopwatch stopwatch;

    public static abstract class SleepingStopwatch {
        public static SleepingStopwatch createFromSystemTimer() {
            return new SleepingStopwatch() { // from class: com.google.common.util.concurrent.RateLimiter.SleepingStopwatch.1
                final Stopwatch stopwatch = Stopwatch.createStarted();

                @Override // com.google.common.util.concurrent.RateLimiter.SleepingStopwatch
                public long readMicros() {
                    return this.stopwatch.elapsed(TimeUnit.MICROSECONDS);
                }

                @Override // com.google.common.util.concurrent.RateLimiter.SleepingStopwatch
                public void sleepMicrosUninterruptibly(long j2) {
                    if (j2 > 0) {
                        Uninterruptibles.sleepUninterruptibly(j2, TimeUnit.MICROSECONDS);
                    }
                }
            };
        }

        public abstract long readMicros();

        public abstract void sleepMicrosUninterruptibly(long j2);
    }

    public RateLimiter(SleepingStopwatch sleepingStopwatch) {
        this.stopwatch = (SleepingStopwatch) Preconditions.checkNotNull(sleepingStopwatch);
    }

    private boolean canAcquire(long j2, long j3) {
        return queryEarliestAvailable(j2) - j3 <= j2;
    }

    private static void checkPermits(int i2) {
        Preconditions.checkArgument(i2 > 0, "Requested permits (%s) must be positive", i2);
    }

    public static RateLimiter create(double d3) {
        return create(d3, SleepingStopwatch.createFromSystemTimer());
    }

    private Object mutex() {
        Object obj = this.mutexDoNotUseDirectly;
        if (obj == null) {
            synchronized (this) {
                obj = this.mutexDoNotUseDirectly;
                if (obj == null) {
                    obj = new Object();
                    this.mutexDoNotUseDirectly = obj;
                }
            }
        }
        return obj;
    }

    @CanIgnoreReturnValue
    public double acquire() {
        return acquire(1);
    }

    public abstract double doGetRate();

    public abstract void doSetRate(double d3, long j2);

    public final double getRate() {
        double dDoGetRate;
        synchronized (mutex()) {
            dDoGetRate = doGetRate();
        }
        return dDoGetRate;
    }

    public abstract long queryEarliestAvailable(long j2);

    public final long reserve(int i2) {
        long jReserveAndGetWaitLength;
        checkPermits(i2);
        synchronized (mutex()) {
            jReserveAndGetWaitLength = reserveAndGetWaitLength(i2, this.stopwatch.readMicros());
        }
        return jReserveAndGetWaitLength;
    }

    public final long reserveAndGetWaitLength(int i2, long j2) {
        return Math.max(reserveEarliestAvailable(i2, j2) - j2, 0L);
    }

    public abstract long reserveEarliestAvailable(int i2, long j2);

    public final void setRate(double d3) {
        Preconditions.checkArgument(d3 > 0.0d && !Double.isNaN(d3), "rate must be positive");
        synchronized (mutex()) {
            doSetRate(d3, this.stopwatch.readMicros());
        }
    }

    public String toString() {
        return String.format(Locale.ROOT, "RateLimiter[stableRate=%3.1fqps]", Double.valueOf(getRate()));
    }

    public boolean tryAcquire(long j2, TimeUnit timeUnit) {
        return tryAcquire(1, j2, timeUnit);
    }

    @VisibleForTesting
    public static RateLimiter create(double d3, SleepingStopwatch sleepingStopwatch) {
        SmoothRateLimiter.SmoothBursty smoothBursty = new SmoothRateLimiter.SmoothBursty(sleepingStopwatch, 1.0d);
        smoothBursty.setRate(d3);
        return smoothBursty;
    }

    @CanIgnoreReturnValue
    public double acquire(int i2) {
        long jReserve = reserve(i2);
        this.stopwatch.sleepMicrosUninterruptibly(jReserve);
        return (jReserve * 1.0d) / TimeUnit.SECONDS.toMicros(1L);
    }

    public boolean tryAcquire(int i2) {
        return tryAcquire(i2, 0L, TimeUnit.MICROSECONDS);
    }

    public boolean tryAcquire() {
        return tryAcquire(1, 0L, TimeUnit.MICROSECONDS);
    }

    public static RateLimiter create(double d3, long j2, TimeUnit timeUnit) {
        Preconditions.checkArgument(j2 >= 0, "warmupPeriod must not be negative: %s", j2);
        return create(d3, j2, timeUnit, 3.0d, SleepingStopwatch.createFromSystemTimer());
    }

    public boolean tryAcquire(int i2, long j2, TimeUnit timeUnit) {
        long jMax = Math.max(timeUnit.toMicros(j2), 0L);
        checkPermits(i2);
        synchronized (mutex()) {
            long micros = this.stopwatch.readMicros();
            if (!canAcquire(micros, jMax)) {
                return false;
            }
            this.stopwatch.sleepMicrosUninterruptibly(reserveAndGetWaitLength(i2, micros));
            return true;
        }
    }

    @VisibleForTesting
    public static RateLimiter create(double d3, long j2, TimeUnit timeUnit, double d4, SleepingStopwatch sleepingStopwatch) {
        SmoothRateLimiter.SmoothWarmingUp smoothWarmingUp = new SmoothRateLimiter.SmoothWarmingUp(sleepingStopwatch, j2, timeUnit, d4);
        smoothWarmingUp.setRate(d3);
        return smoothWarmingUp;
    }
}
