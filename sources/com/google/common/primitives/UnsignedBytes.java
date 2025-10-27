package com.google.common.primitives;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.reflect.Field;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Arrays;
import java.util.Comparator;
import sun.misc.Unsafe;

@GwtIncompatible
/* loaded from: classes4.dex */
public final class UnsignedBytes {
    public static final byte MAX_POWER_OF_TWO = -128;
    public static final byte MAX_VALUE = -1;
    private static final int UNSIGNED_MASK = 255;

    @VisibleForTesting
    public static class LexicographicalComparatorHolder {
        static final String UNSAFE_COMPARATOR_NAME = LexicographicalComparatorHolder.class.getName() + "$UnsafeComparator";
        static final Comparator<byte[]> BEST_COMPARATOR = getBestComparator();

        public enum PureJavaComparator implements Comparator<byte[]> {
            INSTANCE;

            @Override // java.lang.Enum
            public String toString() {
                return "UnsignedBytes.lexicographicalComparator() (pure Java version)";
            }

            @Override // java.util.Comparator
            public int compare(byte[] bArr, byte[] bArr2) {
                int iMin = Math.min(bArr.length, bArr2.length);
                for (int i2 = 0; i2 < iMin; i2++) {
                    int iCompare = UnsignedBytes.compare(bArr[i2], bArr2[i2]);
                    if (iCompare != 0) {
                        return iCompare;
                    }
                }
                return bArr.length - bArr2.length;
            }
        }

        @VisibleForTesting
        public enum UnsafeComparator implements Comparator<byte[]> {
            INSTANCE;

            static final boolean BIG_ENDIAN = ByteOrder.nativeOrder().equals(ByteOrder.BIG_ENDIAN);
            static final int BYTE_ARRAY_BASE_OFFSET;
            static final Unsafe theUnsafe;

            static {
                Unsafe unsafe = getUnsafe();
                theUnsafe = unsafe;
                int iArrayBaseOffset = unsafe.arrayBaseOffset(byte[].class);
                BYTE_ARRAY_BASE_OFFSET = iArrayBaseOffset;
                if (!"64".equals(System.getProperty("sun.arch.data.model")) || iArrayBaseOffset % 8 != 0 || unsafe.arrayIndexScale(byte[].class) != 1) {
                    throw new Error();
                }
            }

            private static Unsafe getUnsafe() {
                try {
                    try {
                        return Unsafe.getUnsafe();
                    } catch (SecurityException unused) {
                        return (Unsafe) AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>() { // from class: com.google.common.primitives.UnsignedBytes.LexicographicalComparatorHolder.UnsafeComparator.1
                            @Override // java.security.PrivilegedExceptionAction
                            public Unsafe run() throws Exception {
                                for (Field field : Unsafe.class.getDeclaredFields()) {
                                    field.setAccessible(true);
                                    Object obj = field.get(null);
                                    if (Unsafe.class.isInstance(obj)) {
                                        return (Unsafe) Unsafe.class.cast(obj);
                                    }
                                }
                                throw new NoSuchFieldError("the Unsafe");
                            }
                        });
                    }
                } catch (PrivilegedActionException e2) {
                    throw new RuntimeException("Could not initialize intrinsics", e2.getCause());
                }
            }

            @Override // java.lang.Enum
            public String toString() {
                return "UnsignedBytes.lexicographicalComparator() (sun.misc.Unsafe version)";
            }

            @Override // java.util.Comparator
            public int compare(byte[] bArr, byte[] bArr2) {
                int iMin = Math.min(bArr.length, bArr2.length);
                int i2 = iMin & (-8);
                int i3 = 0;
                while (i3 < i2) {
                    Unsafe unsafe = theUnsafe;
                    int i4 = BYTE_ARRAY_BASE_OFFSET;
                    long j2 = i3;
                    long j3 = unsafe.getLong(bArr, i4 + j2);
                    long j4 = unsafe.getLong(bArr2, i4 + j2);
                    if (j3 != j4) {
                        if (BIG_ENDIAN) {
                            return UnsignedLongs.compare(j3, j4);
                        }
                        int iNumberOfTrailingZeros = Long.numberOfTrailingZeros(j3 ^ j4) & (-8);
                        return ((int) ((j3 >>> iNumberOfTrailingZeros) & 255)) - ((int) ((j4 >>> iNumberOfTrailingZeros) & 255));
                    }
                    i3 += 8;
                }
                while (i3 < iMin) {
                    int iCompare = UnsignedBytes.compare(bArr[i3], bArr2[i3]);
                    if (iCompare != 0) {
                        return iCompare;
                    }
                    i3++;
                }
                return bArr.length - bArr2.length;
            }
        }

        public static Comparator<byte[]> getBestComparator() {
            try {
                return (Comparator) Class.forName(UNSAFE_COMPARATOR_NAME).getEnumConstants()[0];
            } catch (Throwable unused) {
                return UnsignedBytes.lexicographicalComparatorJavaImpl();
            }
        }
    }

    private UnsignedBytes() {
    }

    @CanIgnoreReturnValue
    public static byte checkedCast(long j2) {
        Preconditions.checkArgument((j2 >> 8) == 0, "out of range: %s", j2);
        return (byte) j2;
    }

    public static int compare(byte b3, byte b4) {
        return toInt(b3) - toInt(b4);
    }

    private static byte flip(byte b3) {
        return (byte) (b3 ^ 128);
    }

    public static String join(String str, byte... bArr) {
        Preconditions.checkNotNull(str);
        if (bArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(bArr.length * (str.length() + 3));
        sb.append(toInt(bArr[0]));
        for (int i2 = 1; i2 < bArr.length; i2++) {
            sb.append(str);
            sb.append(toString(bArr[i2]));
        }
        return sb.toString();
    }

    public static Comparator<byte[]> lexicographicalComparator() {
        return LexicographicalComparatorHolder.BEST_COMPARATOR;
    }

    @VisibleForTesting
    public static Comparator<byte[]> lexicographicalComparatorJavaImpl() {
        return LexicographicalComparatorHolder.PureJavaComparator.INSTANCE;
    }

    public static byte max(byte... bArr) {
        Preconditions.checkArgument(bArr.length > 0);
        int i2 = toInt(bArr[0]);
        for (int i3 = 1; i3 < bArr.length; i3++) {
            int i4 = toInt(bArr[i3]);
            if (i4 > i2) {
                i2 = i4;
            }
        }
        return (byte) i2;
    }

    public static byte min(byte... bArr) {
        Preconditions.checkArgument(bArr.length > 0);
        int i2 = toInt(bArr[0]);
        for (int i3 = 1; i3 < bArr.length; i3++) {
            int i4 = toInt(bArr[i3]);
            if (i4 < i2) {
                i2 = i4;
            }
        }
        return (byte) i2;
    }

    @CanIgnoreReturnValue
    @Beta
    public static byte parseUnsignedByte(String str) {
        return parseUnsignedByte(str, 10);
    }

    public static byte saturatedCast(long j2) {
        if (j2 > toInt((byte) -1)) {
            return (byte) -1;
        }
        if (j2 < 0) {
            return (byte) 0;
        }
        return (byte) j2;
    }

    public static void sort(byte[] bArr) {
        Preconditions.checkNotNull(bArr);
        sort(bArr, 0, bArr.length);
    }

    public static void sortDescending(byte[] bArr) {
        Preconditions.checkNotNull(bArr);
        sortDescending(bArr, 0, bArr.length);
    }

    public static int toInt(byte b3) {
        return b3 & 255;
    }

    @Beta
    public static String toString(byte b3) {
        return toString(b3, 10);
    }

    @CanIgnoreReturnValue
    @Beta
    public static byte parseUnsignedByte(String str, int i2) throws NumberFormatException {
        int i3 = Integer.parseInt((String) Preconditions.checkNotNull(str), i2);
        if ((i3 >> 8) == 0) {
            return (byte) i3;
        }
        throw new NumberFormatException("out of range: " + i3);
    }

    @Beta
    public static String toString(byte b3, int i2) {
        Preconditions.checkArgument(i2 >= 2 && i2 <= 36, "radix (%s) must be between Character.MIN_RADIX and Character.MAX_RADIX", i2);
        return Integer.toString(toInt(b3), i2);
    }

    public static void sort(byte[] bArr, int i2, int i3) {
        Preconditions.checkNotNull(bArr);
        Preconditions.checkPositionIndexes(i2, i3, bArr.length);
        for (int i4 = i2; i4 < i3; i4++) {
            bArr[i4] = flip(bArr[i4]);
        }
        Arrays.sort(bArr, i2, i3);
        while (i2 < i3) {
            bArr[i2] = flip(bArr[i2]);
            i2++;
        }
    }

    public static void sortDescending(byte[] bArr, int i2, int i3) {
        Preconditions.checkNotNull(bArr);
        Preconditions.checkPositionIndexes(i2, i3, bArr.length);
        for (int i4 = i2; i4 < i3; i4++) {
            bArr[i4] = (byte) (bArr[i4] ^ 127);
        }
        Arrays.sort(bArr, i2, i3);
        while (i2 < i3) {
            bArr[i2] = (byte) (bArr[i2] ^ 127);
            i2++;
        }
    }
}
