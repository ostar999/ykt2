package com.google.common.math;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import java.math.BigInteger;

@GwtIncompatible
/* loaded from: classes4.dex */
final class DoubleUtils {
    static final int EXPONENT_BIAS = 1023;
    static final long EXPONENT_MASK = 9218868437227405312L;
    static final long IMPLICIT_BIT = 4503599627370496L;

    @VisibleForTesting
    static final long ONE_BITS = 4607182418800017408L;
    static final int SIGNIFICAND_BITS = 52;
    static final long SIGNIFICAND_MASK = 4503599627370495L;
    static final long SIGN_MASK = Long.MIN_VALUE;

    private DoubleUtils() {
    }

    public static double bigToDouble(BigInteger bigInteger) {
        BigInteger bigIntegerAbs = bigInteger.abs();
        boolean z2 = true;
        int iBitLength = bigIntegerAbs.bitLength() - 1;
        if (iBitLength < 63) {
            return bigInteger.longValue();
        }
        if (iBitLength > 1023) {
            return bigInteger.signum() * Double.POSITIVE_INFINITY;
        }
        int i2 = (iBitLength - 52) - 1;
        long jLongValue = bigIntegerAbs.shiftRight(i2).longValue();
        long j2 = (jLongValue >> 1) & SIGNIFICAND_MASK;
        if ((jLongValue & 1) == 0 || ((j2 & 1) == 0 && bigIntegerAbs.getLowestSetBit() >= i2)) {
            z2 = false;
        }
        if (z2) {
            j2++;
        }
        return Double.longBitsToDouble((((iBitLength + 1023) << 52) + j2) | (bigInteger.signum() & Long.MIN_VALUE));
    }

    public static double ensureNonNegative(double d3) {
        Preconditions.checkArgument(!Double.isNaN(d3));
        if (d3 > 0.0d) {
            return d3;
        }
        return 0.0d;
    }

    public static long getSignificand(double d3) {
        Preconditions.checkArgument(isFinite(d3), "not a normal value");
        int exponent = Math.getExponent(d3);
        long jDoubleToRawLongBits = Double.doubleToRawLongBits(d3) & SIGNIFICAND_MASK;
        return exponent == -1023 ? jDoubleToRawLongBits << 1 : jDoubleToRawLongBits | IMPLICIT_BIT;
    }

    public static boolean isFinite(double d3) {
        return Math.getExponent(d3) <= 1023;
    }

    public static boolean isNormal(double d3) {
        return Math.getExponent(d3) >= -1022;
    }

    public static double nextDown(double d3) {
        return -Math.nextUp(-d3);
    }

    public static double scaleNormalize(double d3) {
        return Double.longBitsToDouble((Double.doubleToRawLongBits(d3) & SIGNIFICAND_MASK) | ONE_BITS);
    }
}
