package io.reactivex.schedulers;

import cn.hutool.core.text.StrPool;
import io.reactivex.annotations.NonNull;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.TimeUnit;

/* loaded from: classes8.dex */
public final class Timed<T> {
    final long time;
    final TimeUnit unit;
    final T value;

    public Timed(@NonNull T t2, long j2, @NonNull TimeUnit timeUnit) {
        this.value = t2;
        this.time = j2;
        this.unit = (TimeUnit) ObjectHelper.requireNonNull(timeUnit, "unit is null");
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Timed)) {
            return false;
        }
        Timed timed = (Timed) obj;
        return ObjectHelper.equals(this.value, timed.value) && this.time == timed.time && ObjectHelper.equals(this.unit, timed.unit);
    }

    public int hashCode() {
        T t2 = this.value;
        int iHashCode = t2 != null ? t2.hashCode() : 0;
        long j2 = this.time;
        return (((iHashCode * 31) + ((int) (j2 ^ (j2 >>> 31)))) * 31) + this.unit.hashCode();
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

    public long time(@NonNull TimeUnit timeUnit) {
        return timeUnit.convert(this.time, this.unit);
    }
}
