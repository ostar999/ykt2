package com.google.common.primitives;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Arrays;
import java.util.Comparator;

@GwtCompatible
/* loaded from: classes4.dex */
public final class SignedBytes {
    public static final byte MAX_POWER_OF_TWO = 64;

    public enum LexicographicalComparator implements Comparator<byte[]> {
        INSTANCE;

        @Override // java.lang.Enum
        public String toString() {
            return "SignedBytes.lexicographicalComparator()";
        }

        @Override // java.util.Comparator
        public int compare(byte[] bArr, byte[] bArr2) {
            int iMin = Math.min(bArr.length, bArr2.length);
            for (int i2 = 0; i2 < iMin; i2++) {
                int iCompare = SignedBytes.compare(bArr[i2], bArr2[i2]);
                if (iCompare != 0) {
                    return iCompare;
                }
            }
            return bArr.length - bArr2.length;
        }
    }

    private SignedBytes() {
    }

    public static byte checkedCast(long j2) {
        byte b3 = (byte) j2;
        Preconditions.checkArgument(((long) b3) == j2, "Out of range: %s", j2);
        return b3;
    }

    public static int compare(byte b3, byte b4) {
        return b3 - b4;
    }

    public static String join(String str, byte... bArr) {
        Preconditions.checkNotNull(str);
        if (bArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(bArr.length * 5);
        sb.append((int) bArr[0]);
        for (int i2 = 1; i2 < bArr.length; i2++) {
            sb.append(str);
            sb.append((int) bArr[i2]);
        }
        return sb.toString();
    }

    public static Comparator<byte[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    public static byte max(byte... bArr) {
        Preconditions.checkArgument(bArr.length > 0);
        byte b3 = bArr[0];
        for (int i2 = 1; i2 < bArr.length; i2++) {
            byte b4 = bArr[i2];
            if (b4 > b3) {
                b3 = b4;
            }
        }
        return b3;
    }

    public static byte min(byte... bArr) {
        Preconditions.checkArgument(bArr.length > 0);
        byte b3 = bArr[0];
        for (int i2 = 1; i2 < bArr.length; i2++) {
            byte b4 = bArr[i2];
            if (b4 < b3) {
                b3 = b4;
            }
        }
        return b3;
    }

    public static byte saturatedCast(long j2) {
        if (j2 > 127) {
            return (byte) 127;
        }
        if (j2 < -128) {
            return (byte) -128;
        }
        return (byte) j2;
    }

    public static void sortDescending(byte[] bArr) {
        Preconditions.checkNotNull(bArr);
        sortDescending(bArr, 0, bArr.length);
    }

    public static void sortDescending(byte[] bArr, int i2, int i3) {
        Preconditions.checkNotNull(bArr);
        Preconditions.checkPositionIndexes(i2, i3, bArr.length);
        Arrays.sort(bArr, i2, i3);
        Bytes.reverse(bArr, i2, i3);
    }
}
