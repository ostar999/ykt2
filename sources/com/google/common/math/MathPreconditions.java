package com.google.common.math;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.math.BigInteger;
import java.math.RoundingMode;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@CanIgnoreReturnValue
@GwtCompatible
/* loaded from: classes4.dex */
final class MathPreconditions {
    private MathPreconditions() {
    }

    public static void checkInRangeForRoundingInputs(boolean z2, double d3, RoundingMode roundingMode) {
        if (z2) {
            return;
        }
        throw new ArithmeticException("rounded value is out of range for input " + d3 + " and rounding mode " + roundingMode);
    }

    public static void checkNoOverflow(boolean z2, String str, int i2, int i3) {
        if (z2) {
            return;
        }
        throw new ArithmeticException("overflow: " + str + "(" + i2 + ", " + i3 + ")");
    }

    public static int checkNonNegative(@NullableDecl String str, int i2) {
        if (i2 >= 0) {
            return i2;
        }
        throw new IllegalArgumentException(str + " (" + i2 + ") must be >= 0");
    }

    public static int checkPositive(@NullableDecl String str, int i2) {
        if (i2 > 0) {
            return i2;
        }
        throw new IllegalArgumentException(str + " (" + i2 + ") must be > 0");
    }

    public static void checkRoundingUnnecessary(boolean z2) {
        if (!z2) {
            throw new ArithmeticException("mode was UNNECESSARY, but rounding was necessary");
        }
    }

    public static void checkNoOverflow(boolean z2, String str, long j2, long j3) {
        if (z2) {
            return;
        }
        throw new ArithmeticException("overflow: " + str + "(" + j2 + ", " + j3 + ")");
    }

    public static long checkNonNegative(@NullableDecl String str, long j2) {
        if (j2 >= 0) {
            return j2;
        }
        throw new IllegalArgumentException(str + " (" + j2 + ") must be >= 0");
    }

    public static long checkPositive(@NullableDecl String str, long j2) {
        if (j2 > 0) {
            return j2;
        }
        throw new IllegalArgumentException(str + " (" + j2 + ") must be > 0");
    }

    public static BigInteger checkNonNegative(@NullableDecl String str, BigInteger bigInteger) {
        if (bigInteger.signum() >= 0) {
            return bigInteger;
        }
        throw new IllegalArgumentException(str + " (" + bigInteger + ") must be >= 0");
    }

    public static BigInteger checkPositive(@NullableDecl String str, BigInteger bigInteger) {
        if (bigInteger.signum() > 0) {
            return bigInteger;
        }
        throw new IllegalArgumentException(str + " (" + bigInteger + ") must be > 0");
    }

    public static double checkNonNegative(@NullableDecl String str, double d3) {
        if (d3 >= 0.0d) {
            return d3;
        }
        throw new IllegalArgumentException(str + " (" + d3 + ") must be >= 0");
    }
}
