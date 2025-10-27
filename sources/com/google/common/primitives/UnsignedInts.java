package com.google.common.primitives;

import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Arrays;
import java.util.Comparator;

@Beta
@GwtCompatible
/* loaded from: classes4.dex */
public final class UnsignedInts {
    static final long INT_MASK = 4294967295L;

    public enum LexicographicalComparator implements Comparator<int[]> {
        INSTANCE;

        @Override // java.lang.Enum
        public String toString() {
            return "UnsignedInts.lexicographicalComparator()";
        }

        @Override // java.util.Comparator
        public int compare(int[] iArr, int[] iArr2) {
            int iMin = Math.min(iArr.length, iArr2.length);
            for (int i2 = 0; i2 < iMin; i2++) {
                int i3 = iArr[i2];
                int i4 = iArr2[i2];
                if (i3 != i4) {
                    return UnsignedInts.compare(i3, i4);
                }
            }
            return iArr.length - iArr2.length;
        }
    }

    private UnsignedInts() {
    }

    public static int checkedCast(long j2) {
        Preconditions.checkArgument((j2 >> 32) == 0, "out of range: %s", j2);
        return (int) j2;
    }

    public static int compare(int i2, int i3) {
        return Ints.compare(flip(i2), flip(i3));
    }

    @CanIgnoreReturnValue
    public static int decode(String str) {
        ParseRequest parseRequestFromString = ParseRequest.fromString(str);
        try {
            return parseUnsignedInt(parseRequestFromString.rawValue, parseRequestFromString.radix);
        } catch (NumberFormatException e2) {
            NumberFormatException numberFormatException = new NumberFormatException("Error parsing value: " + str);
            numberFormatException.initCause(e2);
            throw numberFormatException;
        }
    }

    public static int divide(int i2, int i3) {
        return (int) (toLong(i2) / toLong(i3));
    }

    public static int flip(int i2) {
        return i2 ^ Integer.MIN_VALUE;
    }

    public static String join(String str, int... iArr) {
        Preconditions.checkNotNull(str);
        if (iArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(iArr.length * 5);
        sb.append(toString(iArr[0]));
        for (int i2 = 1; i2 < iArr.length; i2++) {
            sb.append(str);
            sb.append(toString(iArr[i2]));
        }
        return sb.toString();
    }

    public static Comparator<int[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    public static int max(int... iArr) {
        Preconditions.checkArgument(iArr.length > 0);
        int iFlip = flip(iArr[0]);
        for (int i2 = 1; i2 < iArr.length; i2++) {
            int iFlip2 = flip(iArr[i2]);
            if (iFlip2 > iFlip) {
                iFlip = iFlip2;
            }
        }
        return flip(iFlip);
    }

    public static int min(int... iArr) {
        Preconditions.checkArgument(iArr.length > 0);
        int iFlip = flip(iArr[0]);
        for (int i2 = 1; i2 < iArr.length; i2++) {
            int iFlip2 = flip(iArr[i2]);
            if (iFlip2 < iFlip) {
                iFlip = iFlip2;
            }
        }
        return flip(iFlip);
    }

    @CanIgnoreReturnValue
    public static int parseUnsignedInt(String str) {
        return parseUnsignedInt(str, 10);
    }

    public static int remainder(int i2, int i3) {
        return (int) (toLong(i2) % toLong(i3));
    }

    public static int saturatedCast(long j2) {
        if (j2 <= 0) {
            return 0;
        }
        if (j2 >= IjkMediaMeta.AV_CH_WIDE_RIGHT) {
            return -1;
        }
        return (int) j2;
    }

    public static void sort(int[] iArr) {
        Preconditions.checkNotNull(iArr);
        sort(iArr, 0, iArr.length);
    }

    public static void sortDescending(int[] iArr) {
        Preconditions.checkNotNull(iArr);
        sortDescending(iArr, 0, iArr.length);
    }

    public static long toLong(int i2) {
        return i2 & 4294967295L;
    }

    public static String toString(int i2) {
        return toString(i2, 10);
    }

    @CanIgnoreReturnValue
    public static int parseUnsignedInt(String str, int i2) throws NumberFormatException {
        Preconditions.checkNotNull(str);
        long j2 = Long.parseLong(str, i2);
        if ((4294967295L & j2) == j2) {
            return (int) j2;
        }
        throw new NumberFormatException("Input " + str + " in base " + i2 + " is not in the range of an unsigned integer");
    }

    public static String toString(int i2, int i3) {
        return Long.toString(i2 & 4294967295L, i3);
    }

    public static void sort(int[] iArr, int i2, int i3) {
        Preconditions.checkNotNull(iArr);
        Preconditions.checkPositionIndexes(i2, i3, iArr.length);
        for (int i4 = i2; i4 < i3; i4++) {
            iArr[i4] = flip(iArr[i4]);
        }
        Arrays.sort(iArr, i2, i3);
        while (i2 < i3) {
            iArr[i2] = flip(iArr[i2]);
            i2++;
        }
    }

    public static void sortDescending(int[] iArr, int i2, int i3) {
        Preconditions.checkNotNull(iArr);
        Preconditions.checkPositionIndexes(i2, i3, iArr.length);
        for (int i4 = i2; i4 < i3; i4++) {
            iArr[i4] = Integer.MAX_VALUE ^ iArr[i4];
        }
        Arrays.sort(iArr, i2, i3);
        while (i2 < i3) {
            iArr[i2] = iArr[i2] ^ Integer.MAX_VALUE;
            i2++;
        }
    }
}
