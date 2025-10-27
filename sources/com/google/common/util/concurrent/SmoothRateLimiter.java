package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.math.LongMath;
import com.google.common.util.concurrent.RateLimiter;
import java.util.concurrent.TimeUnit;

@GwtIncompatible
/* loaded from: classes4.dex */
abstract class SmoothRateLimiter extends RateLimiter {
    double maxPermits;
    private long nextFreeTicketMicros;
    double stableIntervalMicros;
    double storedPermits;

    public static final class SmoothBursty extends SmoothRateLimiter {
        final double maxBurstSeconds;

        public SmoothBursty(RateLimiter.SleepingStopwatch sleepingStopwatch, double d3) {
            super(sleepingStopwatch);
            this.maxBurstSeconds = d3;
        }

        @Override // com.google.common.util.concurrent.SmoothRateLimiter
        public double coolDownIntervalMicros() {
            return this.stableIntervalMicros;
        }

        @Override // com.google.common.util.concurrent.SmoothRateLimiter
        public void doSetRate(double d3, double d4) {
            double d5 = this.maxPermits;
            double d6 = this.maxBurstSeconds * d3;
            this.maxPermits = d6;
            if (d5 == Double.POSITIVE_INFINITY) {
                this.storedPermits = d6;
            } else {
                this.storedPermits = d5 != 0.0d ? (this.storedPermits * d6) / d5 : 0.0d;
            }
        }

        @Override // com.google.common.util.concurrent.SmoothRateLimiter
        public long storedPermitsToWaitTime(double d3, double d4) {
            return 0L;
        }
    }

    public static final class SmoothWarmingUp extends SmoothRateLimiter {
        private double coldFactor;
        private double slope;
        private double thresholdPermits;
        private final long warmupPeriodMicros;

        public SmoothWarmingUp(RateLimiter.SleepingStopwatch sleepingStopwatch, long j2, TimeUnit timeUnit, double d3) {
            super(sleepingStopwatch);
            this.warmupPeriodMicros = timeUnit.toMicros(j2);
            this.coldFactor = d3;
        }

        private double permitsToTime(double d3) {
            return this.stableIntervalMicros + (d3 * this.slope);
        }

        @Override // com.google.common.util.concurrent.SmoothRateLimiter
        public double coolDownIntervalMicros() {
            return this.warmupPeriodMicros / this.maxPermits;
        }

        @Override // com.google.common.util.concurrent.SmoothRateLimiter
        public void doSetRate(double d3, double d4) {
            double d5 = this.maxPermits;
            double d6 = this.coldFactor * d4;
            long j2 = this.warmupPeriodMicros;
            double d7 = (j2 * 0.5d) / d4;
            this.thresholdPermits = d7;
            double d8 = ((j2 * 2.0d) / (d4 + d6)) + d7;
            this.maxPermits = d8;
            this.slope = (d6 - d4) / (d8 - d7);
            if (d5 == Double.POSITIVE_INFINITY) {
                this.storedPermits = 0.0d;
                return;
            }
            if (d5 != 0.0d) {
                d8 = (this.storedPermits * d8) / d5;
            }
            this.storedPermits = d8;
        }

        @Override // com.google.common.util.concurrent.SmoothRateLimiter
        public long storedPermitsToWaitTime(double d3, double d4) {
            long jPermitsToTime;
            double d5 = d3 - this.thresholdPermits;
            if (d5 > 0.0d) {
                double dMin = Math.min(d5, d4);
                jPermitsToTime = (long) (((permitsToTime(d5) + permitsToTime(d5 - dMin)) * dMin) / 2.0d);
                d4 -= dMin;
            } else {
                jPermitsToTime = 0;
            }
            return jPermitsToTime + ((long) (this.stableIntervalMicros * d4));
        }
    }

    public abstract double coolDownIntervalMicros();

    @Override // com.google.common.util.concurrent.RateLimiter
    public final double doGetRate() {
        return TimeUnit.SECONDS.toMicros(1L) / this.stableIntervalMicros;
    }

    public abstract void doSetRate(double d3, double d4);

    @Override // com.google.common.util.concurrent.RateLimiter
    public final void doSetRate(double d3, long j2) {
        resync(j2);
        double micros = TimeUnit.SECONDS.toMicros(1L) / d3;
        this.stableIntervalMicros = micros;
        doSetRate(d3, micros);
    }

    @Override // com.google.common.util.concurrent.RateLimiter
    public final long queryEarliestAvailable(long j2) {
        return this.nextFreeTicketMicros;
    }

    @Override // com.google.common.util.concurrent.RateLimiter
    public final long reserveEarliestAvailable(int i2, long j2) {
        resync(j2);
        long j3 = this.nextFreeTicketMicros;
        double d3 = i2;
        double dMin = Math.min(d3, this.storedPermits);
        this.nextFreeTicketMicros = LongMath.saturatedAdd(this.nextFreeTicketMicros, storedPermitsToWaitTime(this.storedPermits, dMin) + ((long) ((d3 - dMin) * this.stableIntervalMicros)));
        this.storedPermits -= dMin;
        return j3;
    }

    public void resync(long j2) {
        if (j2 > this.nextFreeTicketMicros) {
            this.storedPermits = Math.min(this.maxPermits, this.storedPermits + ((j2 - r0) / coolDownIntervalMicros()));
            this.nextFreeTicketMicros = j2;
        }
    }

    public abstract long storedPermitsToWaitTime(double d3, double d4);

    private SmoothRateLimiter(RateLimiter.SleepingStopwatch sleepingStopwatch) {
        super(sleepingStopwatch);
        this.nextFreeTicketMicros = 0L;
    }
}
