package cn.hutool.core.lang.hash;

import cn.hutool.core.util.ByteUtil;
import com.google.common.base.Ascii;
import java.nio.ByteOrder;
import java.util.Arrays;

/* loaded from: classes.dex */
public class MetroHash {
    private static final long k0_128 = -935685663;
    private static final long k0_64 = -691005195;
    private static final long k1_128 = -2042045477;
    private static final long k1_64 = -1565916357;
    private static final long k2_128 = 2078195771;
    private static final long k2_64 = 1654206401;
    private static final long k3_128 = 794325157;
    private static final long k3_64 = 817650473;

    public static Number128 hash128(byte[] bArr) {
        return hash128(bArr, 1337L);
    }

    public static long hash64(byte[] bArr) {
        return hash64(bArr, 1337L);
    }

    private static int littleEndian16(byte[] bArr) {
        return ByteUtil.bytesToShort(bArr, ByteOrder.LITTLE_ENDIAN);
    }

    private static int littleEndian32(byte[] bArr) {
        return (bArr[3] << Ascii.CAN) | bArr[0] | (bArr[1] << 8) | (bArr[2] << 16);
    }

    private static long littleEndian64(byte[] bArr, int i2) {
        return ByteUtil.bytesToLong(bArr, i2, ByteOrder.LITTLE_ENDIAN);
    }

    private static long rotateLeft64(long j2, int i2) {
        int i3 = i2 & 63;
        return (j2 >> (64 - i3)) | (j2 << i3);
    }

    private static long rotateRight(long j2, int i2) {
        return (j2 << (64 - i2)) | (j2 >> i2);
    }

    public static Number128 hash128(byte[] bArr, long j2) {
        long jRotateRight = (j2 - k0_128) * k3_128;
        long j3 = k1_128;
        long j4 = j2 + k1_128;
        long j5 = k2_128;
        long jRotateRight2 = j4 * k2_128;
        byte[] bArrCopyOfRange = bArr;
        if (bArrCopyOfRange.length >= 32) {
            long jRotateRight3 = (j2 + k0_128) * k2_128;
            long jRotateRight4 = (j2 - k1_128) * k3_128;
            for (int i2 = 32; bArrCopyOfRange.length >= i2; i2 = 32) {
                long jLittleEndian64 = jRotateRight + (littleEndian64(bArrCopyOfRange, 0) * k0_128);
                byte[] bArrCopyOfRange2 = Arrays.copyOfRange(bArrCopyOfRange, 8, bArrCopyOfRange.length);
                jRotateRight = rotateRight(jLittleEndian64, 29) + jRotateRight3;
                long jLittleEndian642 = jRotateRight2 + (littleEndian64(bArrCopyOfRange2, 0) * j3);
                byte[] bArrCopyOfRange3 = Arrays.copyOfRange(bArrCopyOfRange2, 8, bArrCopyOfRange2.length);
                jRotateRight2 = rotateRight(jLittleEndian642, 29) + jRotateRight4;
                long jLittleEndian643 = jRotateRight3 + (littleEndian64(bArrCopyOfRange3, 0) * j5);
                byte[] bArrCopyOfRange4 = Arrays.copyOfRange(bArrCopyOfRange3, 8, bArrCopyOfRange3.length);
                jRotateRight3 = rotateRight(jLittleEndian643, 29) + jRotateRight;
                long jLittleEndian644 = littleEndian64(bArrCopyOfRange4, 0) * k3_128;
                bArrCopyOfRange = Arrays.copyOfRange(bArrCopyOfRange4, 8, bArrCopyOfRange4.length);
                jRotateRight4 = rotateRight(jLittleEndian644, 29) + jRotateRight2;
                j3 = k1_128;
                j5 = k2_128;
            }
            long jRotateRight5 = jRotateRight3 ^ (rotateRight(((jRotateRight + jRotateRight4) * k0_128) + jRotateRight2, 21) * k1_128);
            long jRotateRight6 = jRotateRight4 ^ (rotateRight(((jRotateRight2 + jRotateRight5) * k1_128) + jRotateRight, 21) * k0_128);
            jRotateRight ^= rotateRight(((jRotateRight + jRotateRight5) * k0_128) + jRotateRight6, 21) * k1_128;
            jRotateRight2 ^= rotateRight(((jRotateRight6 + jRotateRight2) * k1_128) + jRotateRight5, 21) * k0_128;
        }
        if (bArrCopyOfRange.length >= 16) {
            long jLittleEndian645 = jRotateRight + (littleEndian64(bArrCopyOfRange, 0) * k2_128);
            byte[] bArrCopyOfRange5 = Arrays.copyOfRange(bArrCopyOfRange, 8, bArrCopyOfRange.length);
            long jRotateRight7 = rotateRight(jLittleEndian645, 33) * k3_128;
            long jLittleEndian646 = jRotateRight2 + (littleEndian64(bArrCopyOfRange5, 0) * k2_128);
            bArrCopyOfRange = Arrays.copyOfRange(bArrCopyOfRange5, 8, bArrCopyOfRange5.length);
            long jRotateRight8 = rotateRight(jLittleEndian646, 33) * k3_128;
            jRotateRight = jRotateRight7 ^ (rotateRight((jRotateRight7 * k2_128) + jRotateRight8, 45) + k1_128);
            jRotateRight2 = jRotateRight8 ^ (rotateRight((jRotateRight8 * k3_128) + jRotateRight, 45) + k0_128);
        }
        if (bArrCopyOfRange.length >= 8) {
            long jLittleEndian647 = jRotateRight + (littleEndian64(bArrCopyOfRange, 0) * k2_128);
            bArrCopyOfRange = Arrays.copyOfRange(bArrCopyOfRange, 8, bArrCopyOfRange.length);
            long jRotateRight9 = rotateRight(jLittleEndian647, 33) * k3_128;
            jRotateRight = jRotateRight9 ^ (rotateRight((jRotateRight9 * k2_128) + jRotateRight2, 27) * k1_128);
        }
        if (bArrCopyOfRange.length >= 4) {
            long jLittleEndian32 = jRotateRight2 + (littleEndian32(bArrCopyOfRange) * k2_128);
            bArrCopyOfRange = Arrays.copyOfRange(bArrCopyOfRange, 4, bArrCopyOfRange.length);
            long jRotateRight10 = rotateRight(jLittleEndian32, 33) * k3_128;
            jRotateRight2 = jRotateRight10 ^ (rotateRight((jRotateRight10 * k3_128) + jRotateRight, 46) * k0_128);
        }
        if (bArrCopyOfRange.length >= 2) {
            long jLittleEndian16 = jRotateRight + (littleEndian16(bArrCopyOfRange) * k2_128);
            bArrCopyOfRange = Arrays.copyOfRange(bArrCopyOfRange, 2, bArrCopyOfRange.length);
            long jRotateRight11 = rotateRight(jLittleEndian16, 33) * k3_128;
            jRotateRight = jRotateRight11 ^ (rotateRight((jRotateRight11 * k2_128) * jRotateRight2, 22) * k1_128);
        }
        if (bArrCopyOfRange.length >= 1) {
            long jRotateRight12 = rotateRight(jRotateRight2 + (bArrCopyOfRange[0] * k2_128), 33) * k3_128;
            jRotateRight2 = jRotateRight12 ^ (rotateRight((jRotateRight12 * k3_128) + jRotateRight, 58) * k0_128);
        }
        Long.signum(jRotateRight);
        long jRotateRight13 = jRotateRight + rotateRight((k0_128 * jRotateRight) + jRotateRight2, 13);
        long jRotateRight14 = jRotateRight2 + rotateRight((jRotateRight2 * k1_128) + jRotateRight13, 37);
        long jRotateRight15 = jRotateRight13 + rotateRight((jRotateRight13 * k2_128) + jRotateRight14, 13);
        return new Number128(jRotateRight15, jRotateRight14 + rotateRight((k3_128 * jRotateRight14) + jRotateRight15, 37));
    }

    public static long hash64(byte[] bArr, long j2) {
        long j3 = j2 + k2_64;
        long j4 = k0_64;
        long jRotateLeft64 = j3 * k0_64;
        byte[] bArrCopyOfRange = bArr;
        if (bArrCopyOfRange.length >= 32) {
            long jRotateLeft642 = jRotateLeft64;
            long jRotateLeft643 = jRotateLeft642;
            long jRotateLeft644 = jRotateLeft643;
            long jRotateLeft645 = jRotateLeft644;
            while (bArrCopyOfRange.length >= 32) {
                jRotateLeft643 = rotateLeft64(jRotateLeft643 + (littleEndian64(bArrCopyOfRange, 0) * j4), -29) + jRotateLeft642;
                jRotateLeft645 = rotateLeft64(jRotateLeft645 + (littleEndian64(bArrCopyOfRange, 8) * k1_64), -29) + jRotateLeft644;
                jRotateLeft642 = rotateLeft64(jRotateLeft642 + (littleEndian64(bArrCopyOfRange, 24) * k2_64), -29) + jRotateLeft643;
                jRotateLeft644 = rotateLeft64(jRotateLeft644 + (littleEndian64(bArrCopyOfRange, 32) * k3_64), -29) + jRotateLeft645;
                bArrCopyOfRange = Arrays.copyOfRange(bArrCopyOfRange, 32, bArrCopyOfRange.length);
                j4 = k0_64;
            }
            long jRotateLeft646 = jRotateLeft642 ^ (rotateLeft64(((jRotateLeft643 + jRotateLeft644) * k0_64) + jRotateLeft645, -37) * k1_64);
            long jRotateLeft647 = jRotateLeft644 ^ (rotateLeft64(((jRotateLeft645 + jRotateLeft646) * k1_64) + jRotateLeft643, -37) * k0_64);
            jRotateLeft64 += (jRotateLeft643 ^ (rotateLeft64(((jRotateLeft643 + jRotateLeft646) * k0_64) + jRotateLeft647, -37) * k1_64)) ^ (jRotateLeft645 ^ (rotateLeft64(((jRotateLeft645 + jRotateLeft647) * k1_64) + jRotateLeft646, -37) * k0_64));
        }
        if (bArrCopyOfRange.length >= 16) {
            long jRotateLeft648 = rotateLeft64((littleEndian64(bArrCopyOfRange, 0) * k2_64) + jRotateLeft64, -29) * k3_64;
            long jRotateLeft649 = rotateLeft64(jRotateLeft64 + (littleEndian64(bArrCopyOfRange, 8) * k2_64), -29) * k3_64;
            jRotateLeft64 += jRotateLeft649 ^ (rotateLeft64(jRotateLeft649 * k3_64, -21) + (jRotateLeft648 ^ (rotateLeft64(jRotateLeft648 * k0_64, -21) + jRotateLeft649)));
            bArrCopyOfRange = Arrays.copyOfRange(bArrCopyOfRange, 16, bArrCopyOfRange.length);
        }
        if (bArrCopyOfRange.length >= 8) {
            long jLittleEndian64 = jRotateLeft64 + (littleEndian64(bArrCopyOfRange, 0) * k3_64);
            bArrCopyOfRange = Arrays.copyOfRange(bArrCopyOfRange, 8, bArrCopyOfRange.length);
            jRotateLeft64 = jLittleEndian64 ^ (rotateLeft64(jLittleEndian64, -55) * k1_64);
        }
        if (bArrCopyOfRange.length >= 4) {
            long jLittleEndian32 = jRotateLeft64 + (littleEndian32(Arrays.copyOfRange(bArrCopyOfRange, 0, 4)) * k3_64);
            jRotateLeft64 = jLittleEndian32 ^ (rotateLeft64(jLittleEndian32, -26) * k1_64);
            bArrCopyOfRange = Arrays.copyOfRange(bArrCopyOfRange, 4, bArrCopyOfRange.length);
        }
        if (bArrCopyOfRange.length >= 2) {
            long jLittleEndian16 = jRotateLeft64 + (littleEndian16(Arrays.copyOfRange(bArrCopyOfRange, 0, 2)) * k3_64);
            bArrCopyOfRange = Arrays.copyOfRange(bArrCopyOfRange, 2, bArrCopyOfRange.length);
            jRotateLeft64 = jLittleEndian16 ^ (rotateLeft64(jLittleEndian16, -48) * k1_64);
        }
        if (bArrCopyOfRange.length >= 1) {
            long j5 = jRotateLeft64 + (bArrCopyOfRange[0] * k3_64);
            jRotateLeft64 = j5 ^ (rotateLeft64(j5, -38) * k1_64);
        }
        long jRotateLeft6410 = (rotateLeft64(jRotateLeft64, -28) ^ jRotateLeft64) * k0_64;
        return jRotateLeft6410 ^ rotateLeft64(jRotateLeft6410, -29);
    }
}
