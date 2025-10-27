package com.plv.foundationsdk.utils;

import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class PLVTimeUnit {
    private final TimeUnit unit;
    private final long value;

    private PLVTimeUnit(TimeUnit timeUnit, long j2) {
        this.unit = timeUnit;
        this.value = j2;
    }

    public static PLVTimeUnit days(long j2) {
        return new PLVTimeUnit(TimeUnit.DAYS, j2);
    }

    public static PLVTimeUnit hours(long j2) {
        return new PLVTimeUnit(TimeUnit.HOURS, j2);
    }

    public static PLVTimeUnit micros(long j2) {
        return new PLVTimeUnit(TimeUnit.MICROSECONDS, j2);
    }

    public static PLVTimeUnit millis(long j2) {
        return new PLVTimeUnit(TimeUnit.MILLISECONDS, j2);
    }

    public static PLVTimeUnit minutes(long j2) {
        return new PLVTimeUnit(TimeUnit.MINUTES, j2);
    }

    public static PLVTimeUnit nanos(long j2) {
        return new PLVTimeUnit(TimeUnit.NANOSECONDS, j2);
    }

    public static PLVTimeUnit of(TimeUnit timeUnit, long j2) {
        return new PLVTimeUnit(timeUnit, j2);
    }

    public static PLVTimeUnit seconds(long j2) {
        return new PLVTimeUnit(TimeUnit.SECONDS, j2);
    }

    public TimeUnit getUnit() {
        return this.unit;
    }

    public long getValue() {
        return this.value;
    }

    public long toDays() {
        return this.unit.toDays(this.value);
    }

    public long toHours() {
        return this.unit.toHours(this.value);
    }

    public long toMicros() {
        return this.unit.toMicros(this.value);
    }

    public long toMillis() {
        return this.unit.toMillis(this.value);
    }

    public long toMinutes() {
        return this.unit.toMinutes(this.value);
    }

    public long toNanos() {
        return this.unit.toNanos(this.value);
    }

    public long toSeconds() {
        return this.unit.toSeconds(this.value);
    }
}
