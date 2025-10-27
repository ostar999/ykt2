package io.reactivex.internal.util;

/* loaded from: classes8.dex */
public final class Pow2 {
    private Pow2() {
        throw new IllegalStateException("No instances!");
    }

    public static boolean isPowerOfTwo(int i2) {
        return (i2 & (i2 + (-1))) == 0;
    }

    public static int roundToPowerOfTwo(int i2) {
        return 1 << (32 - Integer.numberOfLeadingZeros(i2 - 1));
    }
}
