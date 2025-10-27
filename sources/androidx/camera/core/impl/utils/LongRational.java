package androidx.camera.core.impl.utils;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

@RequiresApi(21)
/* loaded from: classes.dex */
final class LongRational {
    private final long mDenominator;
    private final long mNumerator;

    public LongRational(long j2, long j3) {
        this.mNumerator = j2;
        this.mDenominator = j3;
    }

    public long getDenominator() {
        return this.mDenominator;
    }

    public long getNumerator() {
        return this.mNumerator;
    }

    public double toDouble() {
        return this.mNumerator / this.mDenominator;
    }

    @NonNull
    public String toString() {
        return this.mNumerator + "/" + this.mDenominator;
    }

    public LongRational(double d3) {
        this((long) (d3 * 10000.0d), com.heytap.mcssdk.constant.a.f7153q);
    }
}
