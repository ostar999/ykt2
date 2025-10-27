package io.reactivex.rxjava3.schedulers;

import cn.hutool.core.text.StrPool;
import io.reactivex.rxjava3.annotations.NonNull;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/* loaded from: classes8.dex */
public final class Timed<T> {
    final long time;
    final TimeUnit unit;
    final T value;

    public Timed(@NonNull T value, long time, @NonNull TimeUnit unit) {
        Objects.requireNonNull(value, "value is null");
        this.value = value;
        this.time = time;
        Objects.requireNonNull(unit, "unit is null");
        this.unit = unit;
    }

    public boolean equals(Object other) {
        if (!(other instanceof Timed)) {
            return false;
        }
        Timed timed = (Timed) other;
        return Objects.equals(this.value, timed.value) && this.time == timed.time && Objects.equals(this.unit, timed.unit);
    }

    public int hashCode() {
        int iHashCode = this.value.hashCode() * 31;
        long j2 = this.time;
        return ((iHashCode + ((int) (j2 ^ (j2 >>> 31)))) * 31) + this.unit.hashCode();
    }

    public long time() {
        return this.time;
    }

    public String toString() {
        return "Timed[time=" + this.time + ", unit=" + this.unit + ", value=" + this.value + StrPool.BRACKET_END;
    }

    @NonNull
    public TimeUnit unit() {
        return this.unit;
    }

    @NonNull
    public T value() {
        return this.value;
    }

    public long time(@NonNull TimeUnit unit) {
        return unit.convert(this.time, this.unit);
    }
}
