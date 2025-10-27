package cn.hutool.core.lang.hash;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ByteUtil;
import cn.hutool.core.util.CharsetUtil;
import java.io.Serializable;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes.dex */
public class MurmurHash implements Serializable {
    private static final long C1 = -8663945395140668459L;
    private static final int C1_32 = -862048943;
    private static final long C2 = 5545529020109919103L;
    private static final int C2_32 = 461845907;
    private static final Charset DEFAULT_CHARSET = CharsetUtil.CHARSET_UTF_8;
    private static final ByteOrder DEFAULT_ORDER = ByteOrder.LITTLE_ENDIAN;
    private static final int DEFAULT_SEED = 0;
    private static final int M = 5;
    private static final int M_32 = 5;
    private static final int N1 = 1390208809;
    private static final int N2 = 944331445;
    private static final int N_32 = -430675100;
    private static final int R1 = 31;
    private static final int R1_32 = 15;
    private static final int R2 = 27;
    private static final int R2_32 = 13;
    private static final int R3 = 33;
    private static final long serialVersionUID = 1;

    private static int fmix32(int i2) {
        int i3 = (i2 ^ (i2 >>> 16)) * (-2048144789);
        int i4 = (i3 ^ (i3 >>> 13)) * (-1028477387);
        return i4 ^ (i4 >>> 16);
    }

    private static long fmix64(long j2) {
        long j3 = (j2 ^ (j2 >>> 33)) * (-49064778989728563L);
        long j4 = (j3 ^ (j3 >>> 33)) * (-4265267296055464877L);
        return j4 ^ (j4 >>> 33);
    }

    public static long[] hash128(CharSequence charSequence) {
        return hash128(CharSequenceUtil.bytes(charSequence, DEFAULT_CHARSET));
    }

    public static int hash32(CharSequence charSequence) {
        return hash32(CharSequenceUtil.bytes(charSequence, DEFAULT_CHARSET));
    }

    public static long hash64(CharSequence charSequence) {
        return hash64(CharSequenceUtil.bytes(charSequence, DEFAULT_CHARSET));
    }

    private static int mix32(int i2, int i3) {
        return (Integer.rotateLeft((Integer.rotateLeft(i2 * C1_32, 15) * C2_32) ^ i3, 13) * 5) + N_32;
    }

    public static long[] hash128(byte[] bArr) {
        return hash128(bArr, bArr.length, 0);
    }

    public static int hash32(byte[] bArr) {
        return hash32(bArr, bArr.length, 0);
    }

    public static long hash64(byte[] bArr) {
        return hash64(bArr, bArr.length, 0);
    }

    public static long[] hash128(byte[] bArr, int i2, int i3) {
        return hash128(bArr, 0, i2, i3);
    }

    public static int hash32(byte[] bArr, int i2, int i3) {
        return hash32(bArr, 0, i2, i3);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static long hash64(byte[] bArr, int i2, int i3) {
        long jRotateLeft = i3;
        int i4 = i2 >> 3;
        for (int i5 = 0; i5 < i4; i5++) {
            jRotateLeft = (Long.rotateLeft(jRotateLeft ^ (Long.rotateLeft(ByteUtil.bytesToLong(bArr, i5 << 3, DEFAULT_ORDER) * C1, 31) * C2), 27) * 5) + 1390208809;
        }
        long j2 = 0;
        switch (i2 - (i4 << 3)) {
            case 1:
                jRotateLeft ^= Long.rotateLeft(((bArr[r4] & 255) ^ j2) * C1, 31) * C2;
                break;
            case 2:
                j2 ^= (bArr[r4 + 1] & 255) << 8;
                jRotateLeft ^= Long.rotateLeft(((bArr[r4] & 255) ^ j2) * C1, 31) * C2;
                break;
            case 3:
                j2 ^= (bArr[r4 + 2] & 255) << 16;
                j2 ^= (bArr[r4 + 1] & 255) << 8;
                jRotateLeft ^= Long.rotateLeft(((bArr[r4] & 255) ^ j2) * C1, 31) * C2;
                break;
            case 4:
                j2 ^= (bArr[r4 + 3] & 255) << 24;
                j2 ^= (bArr[r4 + 2] & 255) << 16;
                j2 ^= (bArr[r4 + 1] & 255) << 8;
                jRotateLeft ^= Long.rotateLeft(((bArr[r4] & 255) ^ j2) * C1, 31) * C2;
                break;
            case 5:
                j2 ^= (bArr[r4 + 4] & 255) << 32;
                j2 ^= (bArr[r4 + 3] & 255) << 24;
                j2 ^= (bArr[r4 + 2] & 255) << 16;
                j2 ^= (bArr[r4 + 1] & 255) << 8;
                jRotateLeft ^= Long.rotateLeft(((bArr[r4] & 255) ^ j2) * C1, 31) * C2;
                break;
            case 6:
                j2 ^= (bArr[r4 + 5] & 255) << 40;
                j2 ^= (bArr[r4 + 4] & 255) << 32;
                j2 ^= (bArr[r4 + 3] & 255) << 24;
                j2 ^= (bArr[r4 + 2] & 255) << 16;
                j2 ^= (bArr[r4 + 1] & 255) << 8;
                jRotateLeft ^= Long.rotateLeft(((bArr[r4] & 255) ^ j2) * C1, 31) * C2;
                break;
            case 7:
                j2 = 0 ^ ((bArr[r4 + 6] & 255) << 48);
                j2 ^= (bArr[r4 + 5] & 255) << 40;
                j2 ^= (bArr[r4 + 4] & 255) << 32;
                j2 ^= (bArr[r4 + 3] & 255) << 24;
                j2 ^= (bArr[r4 + 2] & 255) << 16;
                j2 ^= (bArr[r4 + 1] & 255) << 8;
                jRotateLeft ^= Long.rotateLeft(((bArr[r4] & 255) ^ j2) * C1, 31) * C2;
                break;
        }
        return fmix64(i2 ^ jRotateLeft);
    }

    public static long[] hash128(byte[] bArr, int i2, int i3, int i4) {
        long j2;
        long j3;
        long j4;
        long j5;
        long j6;
        long j7;
        long j8;
        byte[] bArr2 = bArr;
        long jRotateLeft = (int) (i4 & InternalZipConstants.ZIP_64_LIMIT);
        int i5 = i3 >> 4;
        long jRotateLeft2 = jRotateLeft;
        int i6 = 0;
        while (i6 < i5) {
            int i7 = i2 + (i6 << 4);
            ByteOrder byteOrder = DEFAULT_ORDER;
            long jBytesToLong = ByteUtil.bytesToLong(bArr2, i7, byteOrder);
            long jBytesToLong2 = ByteUtil.bytesToLong(bArr2, i7 + 8, byteOrder);
            long jRotateLeft3 = ((Long.rotateLeft((Long.rotateLeft(jBytesToLong * C1, 31) * C2) ^ jRotateLeft, 27) + jRotateLeft2) * 5) + 1390208809;
            jRotateLeft2 = ((Long.rotateLeft(jRotateLeft2 ^ (Long.rotateLeft(C2 * jBytesToLong2, 33) * C1), 31) + jRotateLeft3) * 5) + 944331445;
            i6++;
            jRotateLeft = jRotateLeft3;
            bArr2 = bArr;
        }
        long j9 = 0;
        switch ((i2 + i3) - (i2 + (i5 << 4))) {
            case 1:
                jRotateLeft ^= Long.rotateLeft((j9 ^ (bArr[r0] & 255)) * C1, 31) * C2;
                break;
            case 2:
                j9 ^= (bArr[r0 + 1] & 255) << 8;
                jRotateLeft ^= Long.rotateLeft((j9 ^ (bArr[r0] & 255)) * C1, 31) * C2;
                break;
            case 3:
                j9 ^= (bArr[r0 + 2] & 255) << 16;
                j9 ^= (bArr[r0 + 1] & 255) << 8;
                jRotateLeft ^= Long.rotateLeft((j9 ^ (bArr[r0] & 255)) * C1, 31) * C2;
                break;
            case 4:
                j9 ^= (bArr[r0 + 3] & 255) << 24;
                j9 ^= (bArr[r0 + 2] & 255) << 16;
                j9 ^= (bArr[r0 + 1] & 255) << 8;
                jRotateLeft ^= Long.rotateLeft((j9 ^ (bArr[r0] & 255)) * C1, 31) * C2;
                break;
            case 5:
                j9 ^= (bArr[r0 + 4] & 255) << 32;
                j9 ^= (bArr[r0 + 3] & 255) << 24;
                j9 ^= (bArr[r0 + 2] & 255) << 16;
                j9 ^= (bArr[r0 + 1] & 255) << 8;
                jRotateLeft ^= Long.rotateLeft((j9 ^ (bArr[r0] & 255)) * C1, 31) * C2;
                break;
            case 6:
                j9 ^= (bArr[r0 + 5] & 255) << 40;
                j9 ^= (bArr[r0 + 4] & 255) << 32;
                j9 ^= (bArr[r0 + 3] & 255) << 24;
                j9 ^= (bArr[r0 + 2] & 255) << 16;
                j9 ^= (bArr[r0 + 1] & 255) << 8;
                jRotateLeft ^= Long.rotateLeft((j9 ^ (bArr[r0] & 255)) * C1, 31) * C2;
                break;
            case 7:
                j9 ^= (bArr[r0 + 6] & 255) << 48;
                j9 ^= (bArr[r0 + 5] & 255) << 40;
                j9 ^= (bArr[r0 + 4] & 255) << 32;
                j9 ^= (bArr[r0 + 3] & 255) << 24;
                j9 ^= (bArr[r0 + 2] & 255) << 16;
                j9 ^= (bArr[r0 + 1] & 255) << 8;
                jRotateLeft ^= Long.rotateLeft((j9 ^ (bArr[r0] & 255)) * C1, 31) * C2;
                break;
            case 8:
                j9 = 0 ^ ((bArr[r0 + 7] & 255) << 56);
                j9 ^= (bArr[r0 + 6] & 255) << 48;
                j9 ^= (bArr[r0 + 5] & 255) << 40;
                j9 ^= (bArr[r0 + 4] & 255) << 32;
                j9 ^= (bArr[r0 + 3] & 255) << 24;
                j9 ^= (bArr[r0 + 2] & 255) << 16;
                j9 ^= (bArr[r0 + 1] & 255) << 8;
                jRotateLeft ^= Long.rotateLeft((j9 ^ (bArr[r0] & 255)) * C1, 31) * C2;
                break;
            case 9:
                j2 = jRotateLeft2;
                j3 = 0;
                jRotateLeft2 = j2 ^ (Long.rotateLeft((j3 ^ (bArr[r0 + 8] & 255)) * C2, 33) * C1);
                j9 = 0 ^ ((bArr[r0 + 7] & 255) << 56);
                j9 ^= (bArr[r0 + 6] & 255) << 48;
                j9 ^= (bArr[r0 + 5] & 255) << 40;
                j9 ^= (bArr[r0 + 4] & 255) << 32;
                j9 ^= (bArr[r0 + 3] & 255) << 24;
                j9 ^= (bArr[r0 + 2] & 255) << 16;
                j9 ^= (bArr[r0 + 1] & 255) << 8;
                jRotateLeft ^= Long.rotateLeft((j9 ^ (bArr[r0] & 255)) * C1, 31) * C2;
                break;
            case 10:
                j2 = jRotateLeft2;
                j4 = 0;
                j3 = j4 ^ ((bArr[r0 + 9] & 255) << 8);
                jRotateLeft2 = j2 ^ (Long.rotateLeft((j3 ^ (bArr[r0 + 8] & 255)) * C2, 33) * C1);
                j9 = 0 ^ ((bArr[r0 + 7] & 255) << 56);
                j9 ^= (bArr[r0 + 6] & 255) << 48;
                j9 ^= (bArr[r0 + 5] & 255) << 40;
                j9 ^= (bArr[r0 + 4] & 255) << 32;
                j9 ^= (bArr[r0 + 3] & 255) << 24;
                j9 ^= (bArr[r0 + 2] & 255) << 16;
                j9 ^= (bArr[r0 + 1] & 255) << 8;
                jRotateLeft ^= Long.rotateLeft((j9 ^ (bArr[r0] & 255)) * C1, 31) * C2;
                break;
            case 11:
                j2 = jRotateLeft2;
                j5 = 0;
                j4 = j5 ^ ((bArr[r0 + 10] & 255) << 16);
                j3 = j4 ^ ((bArr[r0 + 9] & 255) << 8);
                jRotateLeft2 = j2 ^ (Long.rotateLeft((j3 ^ (bArr[r0 + 8] & 255)) * C2, 33) * C1);
                j9 = 0 ^ ((bArr[r0 + 7] & 255) << 56);
                j9 ^= (bArr[r0 + 6] & 255) << 48;
                j9 ^= (bArr[r0 + 5] & 255) << 40;
                j9 ^= (bArr[r0 + 4] & 255) << 32;
                j9 ^= (bArr[r0 + 3] & 255) << 24;
                j9 ^= (bArr[r0 + 2] & 255) << 16;
                j9 ^= (bArr[r0 + 1] & 255) << 8;
                jRotateLeft ^= Long.rotateLeft((j9 ^ (bArr[r0] & 255)) * C1, 31) * C2;
                break;
            case 12:
                j2 = jRotateLeft2;
                j6 = 0;
                j5 = j6 ^ ((bArr[r0 + 11] & 255) << 24);
                j4 = j5 ^ ((bArr[r0 + 10] & 255) << 16);
                j3 = j4 ^ ((bArr[r0 + 9] & 255) << 8);
                jRotateLeft2 = j2 ^ (Long.rotateLeft((j3 ^ (bArr[r0 + 8] & 255)) * C2, 33) * C1);
                j9 = 0 ^ ((bArr[r0 + 7] & 255) << 56);
                j9 ^= (bArr[r0 + 6] & 255) << 48;
                j9 ^= (bArr[r0 + 5] & 255) << 40;
                j9 ^= (bArr[r0 + 4] & 255) << 32;
                j9 ^= (bArr[r0 + 3] & 255) << 24;
                j9 ^= (bArr[r0 + 2] & 255) << 16;
                j9 ^= (bArr[r0 + 1] & 255) << 8;
                jRotateLeft ^= Long.rotateLeft((j9 ^ (bArr[r0] & 255)) * C1, 31) * C2;
                break;
            case 13:
                j2 = jRotateLeft2;
                j7 = 0;
                j6 = j7 ^ ((bArr[r0 + 12] & 255) << 32);
                j5 = j6 ^ ((bArr[r0 + 11] & 255) << 24);
                j4 = j5 ^ ((bArr[r0 + 10] & 255) << 16);
                j3 = j4 ^ ((bArr[r0 + 9] & 255) << 8);
                jRotateLeft2 = j2 ^ (Long.rotateLeft((j3 ^ (bArr[r0 + 8] & 255)) * C2, 33) * C1);
                j9 = 0 ^ ((bArr[r0 + 7] & 255) << 56);
                j9 ^= (bArr[r0 + 6] & 255) << 48;
                j9 ^= (bArr[r0 + 5] & 255) << 40;
                j9 ^= (bArr[r0 + 4] & 255) << 32;
                j9 ^= (bArr[r0 + 3] & 255) << 24;
                j9 ^= (bArr[r0 + 2] & 255) << 16;
                j9 ^= (bArr[r0 + 1] & 255) << 8;
                jRotateLeft ^= Long.rotateLeft((j9 ^ (bArr[r0] & 255)) * C1, 31) * C2;
                break;
            case 14:
                j8 = 0;
                j2 = jRotateLeft2;
                j7 = ((bArr[r0 + 13] & 255) << 40) ^ j8;
                j6 = j7 ^ ((bArr[r0 + 12] & 255) << 32);
                j5 = j6 ^ ((bArr[r0 + 11] & 255) << 24);
                j4 = j5 ^ ((bArr[r0 + 10] & 255) << 16);
                j3 = j4 ^ ((bArr[r0 + 9] & 255) << 8);
                jRotateLeft2 = j2 ^ (Long.rotateLeft((j3 ^ (bArr[r0 + 8] & 255)) * C2, 33) * C1);
                j9 = 0 ^ ((bArr[r0 + 7] & 255) << 56);
                j9 ^= (bArr[r0 + 6] & 255) << 48;
                j9 ^= (bArr[r0 + 5] & 255) << 40;
                j9 ^= (bArr[r0 + 4] & 255) << 32;
                j9 ^= (bArr[r0 + 3] & 255) << 24;
                j9 ^= (bArr[r0 + 2] & 255) << 16;
                j9 ^= (bArr[r0 + 1] & 255) << 8;
                jRotateLeft ^= Long.rotateLeft((j9 ^ (bArr[r0] & 255)) * C1, 31) * C2;
                break;
            case 15:
                j8 = ((bArr[r0 + 14] & 255) << 48) ^ 0;
                j2 = jRotateLeft2;
                j7 = ((bArr[r0 + 13] & 255) << 40) ^ j8;
                j6 = j7 ^ ((bArr[r0 + 12] & 255) << 32);
                j5 = j6 ^ ((bArr[r0 + 11] & 255) << 24);
                j4 = j5 ^ ((bArr[r0 + 10] & 255) << 16);
                j3 = j4 ^ ((bArr[r0 + 9] & 255) << 8);
                jRotateLeft2 = j2 ^ (Long.rotateLeft((j3 ^ (bArr[r0 + 8] & 255)) * C2, 33) * C1);
                j9 = 0 ^ ((bArr[r0 + 7] & 255) << 56);
                j9 ^= (bArr[r0 + 6] & 255) << 48;
                j9 ^= (bArr[r0 + 5] & 255) << 40;
                j9 ^= (bArr[r0 + 4] & 255) << 32;
                j9 ^= (bArr[r0 + 3] & 255) << 24;
                j9 ^= (bArr[r0 + 2] & 255) << 16;
                j9 ^= (bArr[r0 + 1] & 255) << 8;
                jRotateLeft ^= Long.rotateLeft((j9 ^ (bArr[r0] & 255)) * C1, 31) * C2;
                break;
        }
        long j10 = i3;
        long j11 = jRotateLeft ^ j10;
        long j12 = j10 ^ jRotateLeft2;
        long j13 = j11 + j12;
        long j14 = j12 + j13;
        long jFmix64 = fmix64(j13);
        long jFmix642 = fmix64(j14);
        long j15 = jFmix64 + jFmix642;
        return new long[]{j15, jFmix642 + j15};
    }

    public static int hash32(byte[] bArr, int i2, int i3, int i4) {
        int i5 = i3 >> 2;
        for (int i6 = 0; i6 < i5; i6++) {
            i4 = mix32(ByteUtil.bytesToInt(bArr, (i6 << 2) + i2, DEFAULT_ORDER), i4);
        }
        int i7 = (i5 << 2) + i2;
        int i8 = (i2 + i3) - i7;
        if (i8 != 1) {
            if (i8 != 2) {
                i = i8 == 3 ? 0 ^ ((bArr[i7 + 2] & 255) << 16) : 0;
            }
            i ^= (bArr[i7 + 1] & 255) << 8;
            i4 ^= Integer.rotateLeft(((bArr[i7] & 255) ^ i) * C1_32, 15) * C2_32;
        } else {
            i4 ^= Integer.rotateLeft(((bArr[i7] & 255) ^ i) * C1_32, 15) * C2_32;
        }
        return fmix32(i4 ^ i3);
    }
}
