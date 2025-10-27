package com.google.common.math;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.umeng.analytics.pro.am;
import com.yikaobang.yixue.R2;
import java.math.RoundingMode;

@GwtCompatible(emulated = true)
/* loaded from: classes4.dex */
public final class IntMath {

    @VisibleForTesting
    static final int FLOOR_SQRT_MAX_INT = 46340;

    @VisibleForTesting
    static final int MAX_POWER_OF_SQRT2_UNSIGNED = -1257966797;

    @VisibleForTesting
    static final int MAX_SIGNED_POWER_OF_TWO = 1073741824;

    @VisibleForTesting
    static final byte[] maxLog10ForLeadingZeros = {9, 9, 9, 8, 8, 8, 7, 7, 7, 6, 6, 6, 6, 5, 5, 5, 4, 4, 4, 3, 3, 3, 3, 2, 2, 2, 1, 1, 1, 0, 0, 0, 0};

    @VisibleForTesting
    static final int[] powersOf10 = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};

    @VisibleForTesting
    static final int[] halfPowersOf10 = {3, 31, 316, R2.attr.shape_vip_line_bg, 31622, 316227, 3162277, 31622776, 316227766, Integer.MAX_VALUE};
    private static final int[] factorials = {1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800, 39916800, 479001600};

    @VisibleForTesting
    static int[] biggestBinomials = {Integer.MAX_VALUE, Integer.MAX_VALUE, 65536, R2.attr.loading_txt, R2.attr.behavior_overlapTop, 193, 110, 75, 58, 49, 43, 39, 37, 35, 34, 34, 33};

    /* renamed from: com.google.common.math.IntMath$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$java$math$RoundingMode;

        static {
            int[] iArr = new int[RoundingMode.values().length];
            $SwitchMap$java$math$RoundingMode = iArr;
            try {
                iArr[RoundingMode.UNNECESSARY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.DOWN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.FLOOR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.UP.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.CEILING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_DOWN.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_UP.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_EVEN.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    private IntMath() {
    }

    public static int binomial(int i2, int i3) {
        MathPreconditions.checkNonNegative("n", i2);
        MathPreconditions.checkNonNegative("k", i3);
        int i4 = 0;
        Preconditions.checkArgument(i3 <= i2, "k (%s) > n (%s)", i3, i2);
        if (i3 > (i2 >> 1)) {
            i3 = i2 - i3;
        }
        int[] iArr = biggestBinomials;
        if (i3 >= iArr.length || i2 > iArr[i3]) {
            return Integer.MAX_VALUE;
        }
        if (i3 == 0) {
            return 1;
        }
        if (i3 == 1) {
            return i2;
        }
        long j2 = 1;
        while (i4 < i3) {
            long j3 = j2 * (i2 - i4);
            i4++;
            j2 = j3 / i4;
        }
        return (int) j2;
    }

    @Beta
    public static int ceilingPowerOfTwo(int i2) {
        MathPreconditions.checkPositive("x", i2);
        if (i2 <= 1073741824) {
            return 1 << (-Integer.numberOfLeadingZeros(i2 - 1));
        }
        throw new ArithmeticException("ceilingPowerOfTwo(" + i2 + ") not representable as an int");
    }

    public static int checkedAdd(int i2, int i3) {
        long j2 = i2 + i3;
        int i4 = (int) j2;
        MathPreconditions.checkNoOverflow(j2 == ((long) i4), "checkedAdd", i2, i3);
        return i4;
    }

    public static int checkedMultiply(int i2, int i3) {
        long j2 = i2 * i3;
        int i4 = (int) j2;
        MathPreconditions.checkNoOverflow(j2 == ((long) i4), "checkedMultiply", i2, i3);
        return i4;
    }

    public static int checkedPow(int i2, int i3) {
        MathPreconditions.checkNonNegative("exponent", i3);
        if (i2 == -2) {
            MathPreconditions.checkNoOverflow(i3 < 32, "checkedPow", i2, i3);
            return (i3 & 1) == 0 ? 1 << i3 : (-1) << i3;
        }
        if (i2 == -1) {
            return (i3 & 1) == 0 ? 1 : -1;
        }
        if (i2 == 0) {
            return i3 == 0 ? 1 : 0;
        }
        if (i2 == 1) {
            return 1;
        }
        if (i2 == 2) {
            MathPreconditions.checkNoOverflow(i3 < 31, "checkedPow", i2, i3);
            return 1 << i3;
        }
        int iCheckedMultiply = 1;
        while (i3 != 0) {
            if (i3 == 1) {
                return checkedMultiply(iCheckedMultiply, i2);
            }
            if ((i3 & 1) != 0) {
                iCheckedMultiply = checkedMultiply(iCheckedMultiply, i2);
            }
            i3 >>= 1;
            if (i3 > 0) {
                MathPreconditions.checkNoOverflow((-46340 <= i2) & (i2 <= FLOOR_SQRT_MAX_INT), "checkedPow", i2, i3);
                i2 *= i2;
            }
        }
        return iCheckedMultiply;
    }

    public static int checkedSubtract(int i2, int i3) {
        long j2 = i2 - i3;
        int i4 = (int) j2;
        MathPreconditions.checkNoOverflow(j2 == ((long) i4), "checkedSubtract", i2, i3);
        return i4;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int divide(int r5, int r6, java.math.RoundingMode r7) {
        /*
            com.google.common.base.Preconditions.checkNotNull(r7)
            if (r6 == 0) goto L5c
            int r0 = r5 / r6
            int r1 = r6 * r0
            int r1 = r5 - r1
            if (r1 != 0) goto Le
            return r0
        Le:
            r5 = r5 ^ r6
            int r5 = r5 >> 31
            r2 = 1
            r5 = r5 | r2
            int[] r3 = com.google.common.math.IntMath.AnonymousClass1.$SwitchMap$java$math$RoundingMode
            int r4 = r7.ordinal()
            r3 = r3[r4]
            r4 = 0
            switch(r3) {
                case 1: goto L50;
                case 2: goto L57;
                case 3: goto L4d;
                case 4: goto L58;
                case 5: goto L4a;
                case 6: goto L25;
                case 7: goto L25;
                case 8: goto L25;
                default: goto L1f;
            }
        L1f:
            java.lang.AssertionError r5 = new java.lang.AssertionError
            r5.<init>()
            throw r5
        L25:
            int r1 = java.lang.Math.abs(r1)
            int r6 = java.lang.Math.abs(r6)
            int r6 = r6 - r1
            int r1 = r1 - r6
            if (r1 != 0) goto L47
            java.math.RoundingMode r6 = java.math.RoundingMode.HALF_UP
            if (r7 == r6) goto L58
            java.math.RoundingMode r6 = java.math.RoundingMode.HALF_EVEN
            if (r7 != r6) goto L3b
            r6 = r2
            goto L3c
        L3b:
            r6 = r4
        L3c:
            r7 = r0 & 1
            if (r7 == 0) goto L42
            r7 = r2
            goto L43
        L42:
            r7 = r4
        L43:
            r6 = r6 & r7
            if (r6 == 0) goto L57
            goto L58
        L47:
            if (r1 <= 0) goto L57
            goto L58
        L4a:
            if (r5 <= 0) goto L57
            goto L58
        L4d:
            if (r5 >= 0) goto L57
            goto L58
        L50:
            if (r1 != 0) goto L53
            goto L54
        L53:
            r2 = r4
        L54:
            com.google.common.math.MathPreconditions.checkRoundingUnnecessary(r2)
        L57:
            r2 = r4
        L58:
            if (r2 == 0) goto L5b
            int r0 = r0 + r5
        L5b:
            return r0
        L5c:
            java.lang.ArithmeticException r5 = new java.lang.ArithmeticException
            java.lang.String r6 = "/ by zero"
            r5.<init>(r6)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.math.IntMath.divide(int, int, java.math.RoundingMode):int");
    }

    public static int factorial(int i2) {
        MathPreconditions.checkNonNegative("n", i2);
        int[] iArr = factorials;
        if (i2 < iArr.length) {
            return iArr[i2];
        }
        return Integer.MAX_VALUE;
    }

    @Beta
    public static int floorPowerOfTwo(int i2) {
        MathPreconditions.checkPositive("x", i2);
        return Integer.highestOneBit(i2);
    }

    public static int gcd(int i2, int i3) {
        MathPreconditions.checkNonNegative(am.av, i2);
        MathPreconditions.checkNonNegative("b", i3);
        if (i2 == 0) {
            return i3;
        }
        if (i3 == 0) {
            return i2;
        }
        int iNumberOfTrailingZeros = Integer.numberOfTrailingZeros(i2);
        int iNumberOfTrailingZeros2 = i2 >> iNumberOfTrailingZeros;
        int iNumberOfTrailingZeros3 = Integer.numberOfTrailingZeros(i3);
        int i4 = i3 >> iNumberOfTrailingZeros3;
        while (iNumberOfTrailingZeros2 != i4) {
            int i5 = iNumberOfTrailingZeros2 - i4;
            int i6 = (i5 >> 31) & i5;
            int i7 = (i5 - i6) - i6;
            i4 += i6;
            iNumberOfTrailingZeros2 = i7 >> Integer.numberOfTrailingZeros(i7);
        }
        return iNumberOfTrailingZeros2 << Math.min(iNumberOfTrailingZeros, iNumberOfTrailingZeros3);
    }

    public static boolean isPowerOfTwo(int i2) {
        return (i2 > 0) & ((i2 & (i2 + (-1))) == 0);
    }

    @Beta
    @GwtIncompatible
    public static boolean isPrime(int i2) {
        return LongMath.isPrime(i2);
    }

    @VisibleForTesting
    public static int lessThanBranchFree(int i2, int i3) {
        return (~(~(i2 - i3))) >>> 31;
    }

    @GwtIncompatible
    public static int log10(int i2, RoundingMode roundingMode) {
        int iLessThanBranchFree;
        MathPreconditions.checkPositive("x", i2);
        int iLog10Floor = log10Floor(i2);
        int i3 = powersOf10[iLog10Floor];
        switch (AnonymousClass1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.checkRoundingUnnecessary(i2 == i3);
            case 2:
            case 3:
                return iLog10Floor;
            case 4:
            case 5:
                iLessThanBranchFree = lessThanBranchFree(i3, i2);
                return iLog10Floor + iLessThanBranchFree;
            case 6:
            case 7:
            case 8:
                iLessThanBranchFree = lessThanBranchFree(halfPowersOf10[iLog10Floor], i2);
                return iLog10Floor + iLessThanBranchFree;
            default:
                throw new AssertionError();
        }
    }

    private static int log10Floor(int i2) {
        byte b3 = maxLog10ForLeadingZeros[Integer.numberOfLeadingZeros(i2)];
        return b3 - lessThanBranchFree(i2, powersOf10[b3]);
    }

    public static int log2(int i2, RoundingMode roundingMode) {
        MathPreconditions.checkPositive("x", i2);
        switch (AnonymousClass1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.checkRoundingUnnecessary(isPowerOfTwo(i2));
                break;
            case 2:
            case 3:
                break;
            case 4:
            case 5:
                return 32 - Integer.numberOfLeadingZeros(i2 - 1);
            case 6:
            case 7:
            case 8:
                int iNumberOfLeadingZeros = Integer.numberOfLeadingZeros(i2);
                return (31 - iNumberOfLeadingZeros) + lessThanBranchFree(MAX_POWER_OF_SQRT2_UNSIGNED >>> iNumberOfLeadingZeros, i2);
            default:
                throw new AssertionError();
        }
        return 31 - Integer.numberOfLeadingZeros(i2);
    }

    public static int mean(int i2, int i3) {
        return (i2 & i3) + ((i2 ^ i3) >> 1);
    }

    public static int mod(int i2, int i3) {
        if (i3 > 0) {
            int i4 = i2 % i3;
            return i4 >= 0 ? i4 : i4 + i3;
        }
        throw new ArithmeticException("Modulus " + i3 + " must be > 0");
    }

    @GwtIncompatible
    public static int pow(int i2, int i3) {
        MathPreconditions.checkNonNegative("exponent", i3);
        if (i2 == -2) {
            if (i3 < 32) {
                return (i3 & 1) == 0 ? 1 << i3 : -(1 << i3);
            }
            return 0;
        }
        if (i2 == -1) {
            return (i3 & 1) == 0 ? 1 : -1;
        }
        if (i2 == 0) {
            return i3 == 0 ? 1 : 0;
        }
        if (i2 == 1) {
            return 1;
        }
        if (i2 == 2) {
            if (i3 < 32) {
                return 1 << i3;
            }
            return 0;
        }
        int i4 = 1;
        while (i3 != 0) {
            if (i3 == 1) {
                return i2 * i4;
            }
            i4 *= (i3 & 1) == 0 ? 1 : i2;
            i2 *= i2;
            i3 >>= 1;
        }
        return i4;
    }

    @Beta
    public static int saturatedAdd(int i2, int i3) {
        return Ints.saturatedCast(i2 + i3);
    }

    @Beta
    public static int saturatedMultiply(int i2, int i3) {
        return Ints.saturatedCast(i2 * i3);
    }

    @Beta
    public static int saturatedPow(int i2, int i3) {
        MathPreconditions.checkNonNegative("exponent", i3);
        if (i2 == -2) {
            return i3 >= 32 ? (i3 & 1) + Integer.MAX_VALUE : (i3 & 1) == 0 ? 1 << i3 : (-1) << i3;
        }
        if (i2 == -1) {
            return (i3 & 1) == 0 ? 1 : -1;
        }
        if (i2 == 0) {
            return i3 == 0 ? 1 : 0;
        }
        if (i2 == 1) {
            return 1;
        }
        if (i2 == 2) {
            if (i3 >= 31) {
                return Integer.MAX_VALUE;
            }
            return 1 << i3;
        }
        int i4 = ((i2 >>> 31) & i3 & 1) + Integer.MAX_VALUE;
        int iSaturatedMultiply = 1;
        while (i3 != 0) {
            if (i3 == 1) {
                return saturatedMultiply(iSaturatedMultiply, i2);
            }
            if ((i3 & 1) != 0) {
                iSaturatedMultiply = saturatedMultiply(iSaturatedMultiply, i2);
            }
            i3 >>= 1;
            if (i3 > 0) {
                if ((-46340 > i2) || (i2 > FLOOR_SQRT_MAX_INT)) {
                    return i4;
                }
                i2 *= i2;
            }
        }
        return iSaturatedMultiply;
    }

    @Beta
    public static int saturatedSubtract(int i2, int i3) {
        return Ints.saturatedCast(i2 - i3);
    }

    @GwtIncompatible
    public static int sqrt(int i2, RoundingMode roundingMode) {
        int iLessThanBranchFree;
        MathPreconditions.checkNonNegative("x", i2);
        int iSqrtFloor = sqrtFloor(i2);
        switch (AnonymousClass1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.checkRoundingUnnecessary(iSqrtFloor * iSqrtFloor == i2);
            case 2:
            case 3:
                return iSqrtFloor;
            case 4:
            case 5:
                iLessThanBranchFree = lessThanBranchFree(iSqrtFloor * iSqrtFloor, i2);
                return iSqrtFloor + iLessThanBranchFree;
            case 6:
            case 7:
            case 8:
                iLessThanBranchFree = lessThanBranchFree((iSqrtFloor * iSqrtFloor) + iSqrtFloor, i2);
                return iSqrtFloor + iLessThanBranchFree;
            default:
                throw new AssertionError();
        }
    }

    private static int sqrtFloor(int i2) {
        return (int) Math.sqrt(i2);
    }
}
