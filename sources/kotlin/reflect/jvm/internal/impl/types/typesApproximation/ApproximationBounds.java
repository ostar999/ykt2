package kotlin.reflect.jvm.internal.impl.types.typesApproximation;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public final class ApproximationBounds<T> {
    private final T lower;
    private final T upper;

    public ApproximationBounds(T t2, T t3) {
        this.lower = t2;
        this.upper = t3;
    }

    public final T component1() {
        return this.lower;
    }

    public final T component2() {
        return this.upper;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ApproximationBounds)) {
            return false;
        }
        ApproximationBounds approximationBounds = (ApproximationBounds) obj;
        return Intrinsics.areEqual(this.lower, approximationBounds.lower) && Intrinsics.areEqual(this.upper, approximationBounds.upper);
    }

    public final T getLower() {
        return this.lower;
    }

    public final T getUpper() {
        return this.upper;
    }

    public int hashCode() {
        T t2 = this.lower;
        int iHashCode = (t2 == null ? 0 : t2.hashCode()) * 31;
        T t3 = this.upper;
        return iHashCode + (t3 != null ? t3.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "ApproximationBounds(lower=" + this.lower + ", upper=" + this.upper + ')';
    }
}
