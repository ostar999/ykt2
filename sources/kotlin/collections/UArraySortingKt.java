package kotlin.collections;

import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShortArray;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0010\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0007\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\t\u0010\n\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\f\u0010\r\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0010\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0013\u0010\u0014\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0019\u0010\u001a\u001a*\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u0014\u001a*\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b\u001f\u0010\u0016\u001a*\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b \u0010\u0018\u001a*\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b!\u0010\u001a\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\""}, d2 = {"partition", "", "array", "Lkotlin/UByteArray;", "left", "right", "partition-4UcCI2c", "([BII)I", "Lkotlin/UIntArray;", "partition-oBK06Vg", "([III)I", "Lkotlin/ULongArray;", "partition--nroSd4", "([JII)I", "Lkotlin/UShortArray;", "partition-Aa5vz7o", "([SII)I", "quickSort", "", "quickSort-4UcCI2c", "([BII)V", "quickSort-oBK06Vg", "([III)V", "quickSort--nroSd4", "([JII)V", "quickSort-Aa5vz7o", "([SII)V", "sortArray", "fromIndex", "toIndex", "sortArray-4UcCI2c", "sortArray-oBK06Vg", "sortArray--nroSd4", "sortArray-Aa5vz7o", "kotlin-stdlib"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class UArraySortingKt {
    @ExperimentalUnsignedTypes
    /* renamed from: partition--nroSd4, reason: not valid java name */
    private static final int m1238partitionnroSd4(long[] jArr, int i2, int i3) {
        long jM1017getsVKNKU = ULongArray.m1017getsVKNKU(jArr, (i2 + i3) / 2);
        while (i2 <= i3) {
            while (Long.compare(ULongArray.m1017getsVKNKU(jArr, i2) ^ Long.MIN_VALUE, jM1017getsVKNKU ^ Long.MIN_VALUE) < 0) {
                i2++;
            }
            while (Long.compare(ULongArray.m1017getsVKNKU(jArr, i3) ^ Long.MIN_VALUE, jM1017getsVKNKU ^ Long.MIN_VALUE) > 0) {
                i3--;
            }
            if (i2 <= i3) {
                long jM1017getsVKNKU2 = ULongArray.m1017getsVKNKU(jArr, i2);
                ULongArray.m1022setk8EXiF4(jArr, i2, ULongArray.m1017getsVKNKU(jArr, i3));
                ULongArray.m1022setk8EXiF4(jArr, i3, jM1017getsVKNKU2);
                i2++;
                i3--;
            }
        }
        return i2;
    }

    @ExperimentalUnsignedTypes
    /* renamed from: partition-4UcCI2c, reason: not valid java name */
    private static final int m1239partition4UcCI2c(byte[] bArr, int i2, int i3) {
        int i4;
        byte bM859getw2LRezQ = UByteArray.m859getw2LRezQ(bArr, (i2 + i3) / 2);
        while (i2 <= i3) {
            while (true) {
                i4 = bM859getw2LRezQ & 255;
                if (Intrinsics.compare(UByteArray.m859getw2LRezQ(bArr, i2) & 255, i4) >= 0) {
                    break;
                }
                i2++;
            }
            while (Intrinsics.compare(UByteArray.m859getw2LRezQ(bArr, i3) & 255, i4) > 0) {
                i3--;
            }
            if (i2 <= i3) {
                byte bM859getw2LRezQ2 = UByteArray.m859getw2LRezQ(bArr, i2);
                UByteArray.m864setVurrAj0(bArr, i2, UByteArray.m859getw2LRezQ(bArr, i3));
                UByteArray.m864setVurrAj0(bArr, i3, bM859getw2LRezQ2);
                i2++;
                i3--;
            }
        }
        return i2;
    }

    @ExperimentalUnsignedTypes
    /* renamed from: partition-Aa5vz7o, reason: not valid java name */
    private static final int m1240partitionAa5vz7o(short[] sArr, int i2, int i3) {
        int i4;
        short sM1122getMh2AYeg = UShortArray.m1122getMh2AYeg(sArr, (i2 + i3) / 2);
        while (i2 <= i3) {
            while (true) {
                i4 = sM1122getMh2AYeg & 65535;
                if (Intrinsics.compare(UShortArray.m1122getMh2AYeg(sArr, i2) & 65535, i4) >= 0) {
                    break;
                }
                i2++;
            }
            while (Intrinsics.compare(UShortArray.m1122getMh2AYeg(sArr, i3) & 65535, i4) > 0) {
                i3--;
            }
            if (i2 <= i3) {
                short sM1122getMh2AYeg2 = UShortArray.m1122getMh2AYeg(sArr, i2);
                UShortArray.m1127set01HTLdE(sArr, i2, UShortArray.m1122getMh2AYeg(sArr, i3));
                UShortArray.m1127set01HTLdE(sArr, i3, sM1122getMh2AYeg2);
                i2++;
                i3--;
            }
        }
        return i2;
    }

    @ExperimentalUnsignedTypes
    /* renamed from: partition-oBK06Vg, reason: not valid java name */
    private static final int m1241partitionoBK06Vg(int[] iArr, int i2, int i3) {
        int iM938getpVg5ArA = UIntArray.m938getpVg5ArA(iArr, (i2 + i3) / 2);
        while (i2 <= i3) {
            while (Integer.compare(UIntArray.m938getpVg5ArA(iArr, i2) ^ Integer.MIN_VALUE, iM938getpVg5ArA ^ Integer.MIN_VALUE) < 0) {
                i2++;
            }
            while (Integer.compare(UIntArray.m938getpVg5ArA(iArr, i3) ^ Integer.MIN_VALUE, iM938getpVg5ArA ^ Integer.MIN_VALUE) > 0) {
                i3--;
            }
            if (i2 <= i3) {
                int iM938getpVg5ArA2 = UIntArray.m938getpVg5ArA(iArr, i2);
                UIntArray.m943setVXSXFK8(iArr, i2, UIntArray.m938getpVg5ArA(iArr, i3));
                UIntArray.m943setVXSXFK8(iArr, i3, iM938getpVg5ArA2);
                i2++;
                i3--;
            }
        }
        return i2;
    }

    @ExperimentalUnsignedTypes
    /* renamed from: quickSort--nroSd4, reason: not valid java name */
    private static final void m1242quickSortnroSd4(long[] jArr, int i2, int i3) {
        int iM1238partitionnroSd4 = m1238partitionnroSd4(jArr, i2, i3);
        int i4 = iM1238partitionnroSd4 - 1;
        if (i2 < i4) {
            m1242quickSortnroSd4(jArr, i2, i4);
        }
        if (iM1238partitionnroSd4 < i3) {
            m1242quickSortnroSd4(jArr, iM1238partitionnroSd4, i3);
        }
    }

    @ExperimentalUnsignedTypes
    /* renamed from: quickSort-4UcCI2c, reason: not valid java name */
    private static final void m1243quickSort4UcCI2c(byte[] bArr, int i2, int i3) {
        int iM1239partition4UcCI2c = m1239partition4UcCI2c(bArr, i2, i3);
        int i4 = iM1239partition4UcCI2c - 1;
        if (i2 < i4) {
            m1243quickSort4UcCI2c(bArr, i2, i4);
        }
        if (iM1239partition4UcCI2c < i3) {
            m1243quickSort4UcCI2c(bArr, iM1239partition4UcCI2c, i3);
        }
    }

    @ExperimentalUnsignedTypes
    /* renamed from: quickSort-Aa5vz7o, reason: not valid java name */
    private static final void m1244quickSortAa5vz7o(short[] sArr, int i2, int i3) {
        int iM1240partitionAa5vz7o = m1240partitionAa5vz7o(sArr, i2, i3);
        int i4 = iM1240partitionAa5vz7o - 1;
        if (i2 < i4) {
            m1244quickSortAa5vz7o(sArr, i2, i4);
        }
        if (iM1240partitionAa5vz7o < i3) {
            m1244quickSortAa5vz7o(sArr, iM1240partitionAa5vz7o, i3);
        }
    }

    @ExperimentalUnsignedTypes
    /* renamed from: quickSort-oBK06Vg, reason: not valid java name */
    private static final void m1245quickSortoBK06Vg(int[] iArr, int i2, int i3) {
        int iM1241partitionoBK06Vg = m1241partitionoBK06Vg(iArr, i2, i3);
        int i4 = iM1241partitionoBK06Vg - 1;
        if (i2 < i4) {
            m1245quickSortoBK06Vg(iArr, i2, i4);
        }
        if (iM1241partitionoBK06Vg < i3) {
            m1245quickSortoBK06Vg(iArr, iM1241partitionoBK06Vg, i3);
        }
    }

    @ExperimentalUnsignedTypes
    /* renamed from: sortArray--nroSd4, reason: not valid java name */
    public static final void m1246sortArraynroSd4(@NotNull long[] array, int i2, int i3) {
        Intrinsics.checkNotNullParameter(array, "array");
        m1242quickSortnroSd4(array, i2, i3 - 1);
    }

    @ExperimentalUnsignedTypes
    /* renamed from: sortArray-4UcCI2c, reason: not valid java name */
    public static final void m1247sortArray4UcCI2c(@NotNull byte[] array, int i2, int i3) {
        Intrinsics.checkNotNullParameter(array, "array");
        m1243quickSort4UcCI2c(array, i2, i3 - 1);
    }

    @ExperimentalUnsignedTypes
    /* renamed from: sortArray-Aa5vz7o, reason: not valid java name */
    public static final void m1248sortArrayAa5vz7o(@NotNull short[] array, int i2, int i3) {
        Intrinsics.checkNotNullParameter(array, "array");
        m1244quickSortAa5vz7o(array, i2, i3 - 1);
    }

    @ExperimentalUnsignedTypes
    /* renamed from: sortArray-oBK06Vg, reason: not valid java name */
    public static final void m1249sortArrayoBK06Vg(@NotNull int[] array, int i2, int i3) {
        Intrinsics.checkNotNullParameter(array, "array");
        m1245quickSortoBK06Vg(array, i2, i3 - 1);
    }
}
