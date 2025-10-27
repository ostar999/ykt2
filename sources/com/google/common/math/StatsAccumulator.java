package com.google.common.math;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Doubles;
import java.util.Iterator;

@Beta
@GwtIncompatible
/* loaded from: classes4.dex */
public final class StatsAccumulator {
    private long count = 0;
    private double mean = 0.0d;
    private double sumOfSquaresOfDeltas = 0.0d;
    private double min = Double.NaN;
    private double max = Double.NaN;

    public static double calculateNewMeanNonFinite(double d3, double d4) {
        if (Doubles.isFinite(d3)) {
            return d4;
        }
        if (Doubles.isFinite(d4) || d3 == d4) {
            return d3;
        }
        return Double.NaN;
    }

    public void add(double d3) {
        long j2 = this.count;
        if (j2 == 0) {
            this.count = 1L;
            this.mean = d3;
            this.min = d3;
            this.max = d3;
            if (Doubles.isFinite(d3)) {
                return;
            }
            this.sumOfSquaresOfDeltas = Double.NaN;
            return;
        }
        this.count = j2 + 1;
        if (Doubles.isFinite(d3) && Doubles.isFinite(this.mean)) {
            double d4 = this.mean;
            double d5 = d3 - d4;
            double d6 = d4 + (d5 / this.count);
            this.mean = d6;
            this.sumOfSquaresOfDeltas += d5 * (d3 - d6);
        } else {
            this.mean = calculateNewMeanNonFinite(this.mean, d3);
            this.sumOfSquaresOfDeltas = Double.NaN;
        }
        this.min = Math.min(this.min, d3);
        this.max = Math.max(this.max, d3);
    }

    public void addAll(Iterable<? extends Number> iterable) {
        Iterator<? extends Number> it = iterable.iterator();
        while (it.hasNext()) {
            add(it.next().doubleValue());
        }
    }

    public long count() {
        return this.count;
    }

    public double max() {
        Preconditions.checkState(this.count != 0);
        return this.max;
    }

    public double mean() {
        Preconditions.checkState(this.count != 0);
        return this.mean;
    }

    public double min() {
        Preconditions.checkState(this.count != 0);
        return this.min;
    }

    public final double populationStandardDeviation() {
        return Math.sqrt(populationVariance());
    }

    public final double populationVariance() {
        Preconditions.checkState(this.count != 0);
        if (Double.isNaN(this.sumOfSquaresOfDeltas)) {
            return Double.NaN;
        }
        if (this.count == 1) {
            return 0.0d;
        }
        return DoubleUtils.ensureNonNegative(this.sumOfSquaresOfDeltas) / this.count;
    }

    public final double sampleStandardDeviation() {
        return Math.sqrt(sampleVariance());
    }

    public final double sampleVariance() {
        Preconditions.checkState(this.count > 1);
        if (Double.isNaN(this.sumOfSquaresOfDeltas)) {
            return Double.NaN;
        }
        return DoubleUtils.ensureNonNegative(this.sumOfSquaresOfDeltas) / (this.count - 1);
    }

    public Stats snapshot() {
        return new Stats(this.count, this.mean, this.sumOfSquaresOfDeltas, this.min, this.max);
    }

    public final double sum() {
        return this.mean * this.count;
    }

    public double sumOfSquaresOfDeltas() {
        return this.sumOfSquaresOfDeltas;
    }

    public void addAll(Iterator<? extends Number> it) {
        while (it.hasNext()) {
            add(it.next().doubleValue());
        }
    }

    public void addAll(double... dArr) {
        for (double d3 : dArr) {
            add(d3);
        }
    }

    public void addAll(int... iArr) {
        for (int i2 : iArr) {
            add(i2);
        }
    }

    public void addAll(long... jArr) {
        for (long j2 : jArr) {
            add(j2);
        }
    }

    public void addAll(Stats stats) {
        if (stats.count() == 0) {
            return;
        }
        long j2 = this.count;
        if (j2 == 0) {
            this.count = stats.count();
            this.mean = stats.mean();
            this.sumOfSquaresOfDeltas = stats.sumOfSquaresOfDeltas();
            this.min = stats.min();
            this.max = stats.max();
            return;
        }
        this.count = j2 + stats.count();
        if (Doubles.isFinite(this.mean) && Doubles.isFinite(stats.mean())) {
            double dMean = stats.mean();
            double d3 = this.mean;
            double d4 = dMean - d3;
            this.mean = d3 + ((stats.count() * d4) / this.count);
            this.sumOfSquaresOfDeltas += stats.sumOfSquaresOfDeltas() + (d4 * (stats.mean() - this.mean) * stats.count());
        } else {
            this.mean = calculateNewMeanNonFinite(this.mean, stats.mean());
            this.sumOfSquaresOfDeltas = Double.NaN;
        }
        this.min = Math.min(this.min, stats.min());
        this.max = Math.max(this.max, stats.max());
    }
}
