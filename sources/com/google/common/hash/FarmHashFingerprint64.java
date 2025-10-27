package com.google.common.hash;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes4.dex */
final class FarmHashFingerprint64 extends AbstractNonStreamingHashFunction {
    static final HashFunction FARMHASH_FINGERPRINT_64 = new FarmHashFingerprint64();
    private static final long K0 = -4348849565147123417L;
    private static final long K1 = -5435081209227447693L;
    private static final long K2 = -7286425919675154353L;

    @VisibleForTesting
    public static long fingerprint(byte[] bArr, int i2, int i3) {
        return i3 <= 32 ? i3 <= 16 ? hashLength0to16(bArr, i2, i3) : hashLength17to32(bArr, i2, i3) : i3 <= 64 ? hashLength33To64(bArr, i2, i3) : hashLength65Plus(bArr, i2, i3);
    }

    private static long hashLength0to16(byte[] bArr, int i2, int i3) {
        if (i3 >= 8) {
            long j2 = (i3 * 2) + K2;
            long jLoad64 = LittleEndianByteArray.load64(bArr, i2) + K2;
            long jLoad642 = LittleEndianByteArray.load64(bArr, (i2 + i3) - 8);
            return hashLength16((Long.rotateRight(jLoad642, 37) * j2) + jLoad64, (Long.rotateRight(jLoad64, 25) + jLoad642) * j2, j2);
        }
        if (i3 >= 4) {
            return hashLength16(i3 + ((LittleEndianByteArray.load32(bArr, i2) & InternalZipConstants.ZIP_64_LIMIT) << 3), LittleEndianByteArray.load32(bArr, (i2 + i3) - 4) & InternalZipConstants.ZIP_64_LIMIT, (i3 * 2) + K2);
        }
        if (i3 <= 0) {
            return K2;
        }
        return shiftMix((((bArr[i2] & 255) + ((bArr[(i3 >> 1) + i2] & 255) << 8)) * K2) ^ ((i3 + ((bArr[i2 + (i3 - 1)] & 255) << 2)) * K0)) * K2;
    }

    private static long hashLength16(long j2, long j3, long j4) {
        long j5 = (j2 ^ j3) * j4;
        long j6 = ((j5 ^ (j5 >>> 47)) ^ j3) * j4;
        return (j6 ^ (j6 >>> 47)) * j4;
    }

    private static long hashLength17to32(byte[] bArr, int i2, int i3) {
        long j2 = (i3 * 2) + K2;
        long jLoad64 = LittleEndianByteArray.load64(bArr, i2) * K1;
        long jLoad642 = LittleEndianByteArray.load64(bArr, i2 + 8);
        int i4 = i2 + i3;
        long jLoad643 = LittleEndianByteArray.load64(bArr, i4 - 8) * j2;
        return hashLength16((LittleEndianByteArray.load64(bArr, i4 - 16) * K2) + Long.rotateRight(jLoad64 + jLoad642, 43) + Long.rotateRight(jLoad643, 30), jLoad64 + Long.rotateRight(jLoad642 + K2, 18) + jLoad643, j2);
    }

    private static long hashLength33To64(byte[] bArr, int i2, int i3) {
        long j2 = (i3 * 2) + K2;
        long jLoad64 = LittleEndianByteArray.load64(bArr, i2) * K2;
        long jLoad642 = LittleEndianByteArray.load64(bArr, i2 + 8);
        int i4 = i2 + i3;
        long jLoad643 = LittleEndianByteArray.load64(bArr, i4 - 8) * j2;
        long jRotateRight = Long.rotateRight(jLoad64 + jLoad642, 43) + Long.rotateRight(jLoad643, 30) + (LittleEndianByteArray.load64(bArr, i4 - 16) * K2);
        long jHashLength16 = hashLength16(jRotateRight, jLoad643 + Long.rotateRight(jLoad642 + K2, 18) + jLoad64, j2);
        long jLoad644 = LittleEndianByteArray.load64(bArr, i2 + 16) * j2;
        long jLoad645 = LittleEndianByteArray.load64(bArr, i2 + 24);
        long jLoad646 = (jRotateRight + LittleEndianByteArray.load64(bArr, i4 - 32)) * j2;
        return hashLength16(((jHashLength16 + LittleEndianByteArray.load64(bArr, i4 - 24)) * j2) + Long.rotateRight(jLoad644 + jLoad645, 43) + Long.rotateRight(jLoad646, 30), jLoad644 + Long.rotateRight(jLoad645 + jLoad64, 18) + jLoad646, j2);
    }

    private static long hashLength65Plus(byte[] bArr, int i2, int i3) {
        long jShiftMix = shiftMix(-7956866745689871395L) * K2;
        long[] jArr = new long[2];
        long[] jArr2 = new long[2];
        long jLoad64 = 95310865018149119L + LittleEndianByteArray.load64(bArr, i2);
        int i4 = i3 - 1;
        int i5 = i2 + ((i4 / 64) * 64);
        int i6 = i4 & 63;
        int i7 = (i5 + i6) - 63;
        long j2 = 2480279821605975764L;
        int i8 = i2;
        while (true) {
            long jRotateRight = Long.rotateRight(jLoad64 + j2 + jArr[0] + LittleEndianByteArray.load64(bArr, i8 + 8), 37) * K1;
            long jRotateRight2 = Long.rotateRight(j2 + jArr[1] + LittleEndianByteArray.load64(bArr, i8 + 48), 42) * K1;
            long j3 = jRotateRight ^ jArr2[1];
            long jLoad642 = jRotateRight2 + jArr[0] + LittleEndianByteArray.load64(bArr, i8 + 40);
            long jRotateRight3 = Long.rotateRight(jShiftMix + jArr2[0], 33) * K1;
            weakHashLength32WithSeeds(bArr, i8, jArr[1] * K1, j3 + jArr2[0], jArr);
            weakHashLength32WithSeeds(bArr, i8 + 32, jRotateRight3 + jArr2[1], jLoad642 + LittleEndianByteArray.load64(bArr, i8 + 16), jArr2);
            i8 += 64;
            if (i8 == i5) {
                long j4 = ((j3 & 255) << 1) + K1;
                long j5 = jArr2[0] + i6;
                jArr2[0] = j5;
                long j6 = jArr[0] + j5;
                jArr[0] = j6;
                jArr2[0] = jArr2[0] + j6;
                long jRotateRight4 = Long.rotateRight(jRotateRight3 + jLoad642 + jArr[0] + LittleEndianByteArray.load64(bArr, i7 + 8), 37) * j4;
                long jRotateRight5 = Long.rotateRight(jLoad642 + jArr[1] + LittleEndianByteArray.load64(bArr, i7 + 48), 42) * j4;
                long j7 = jRotateRight4 ^ (jArr2[1] * 9);
                long jLoad643 = jRotateRight5 + (jArr[0] * 9) + LittleEndianByteArray.load64(bArr, i7 + 40);
                long jRotateRight6 = Long.rotateRight(j3 + jArr2[0], 33) * j4;
                weakHashLength32WithSeeds(bArr, i7, jArr[1] * j4, j7 + jArr2[0], jArr);
                weakHashLength32WithSeeds(bArr, i7 + 32, jRotateRight6 + jArr2[1], LittleEndianByteArray.load64(bArr, i7 + 16) + jLoad643, jArr2);
                return hashLength16(hashLength16(jArr[0], jArr2[0], j4) + (shiftMix(jLoad643) * K0) + j7, hashLength16(jArr[1], jArr2[1], j4) + jRotateRight6, j4);
            }
            jShiftMix = j3;
            j2 = jLoad642;
            jLoad64 = jRotateRight3;
        }
    }

    private static long shiftMix(long j2) {
        return j2 ^ (j2 >>> 47);
    }

    private static void weakHashLength32WithSeeds(byte[] bArr, int i2, long j2, long j3, long[] jArr) {
        long jLoad64 = LittleEndianByteArray.load64(bArr, i2);
        long jLoad642 = LittleEndianByteArray.load64(bArr, i2 + 8);
        long jLoad643 = LittleEndianByteArray.load64(bArr, i2 + 16);
        long jLoad644 = LittleEndianByteArray.load64(bArr, i2 + 24);
        long j4 = j2 + jLoad64;
        long j5 = jLoad642 + j4 + jLoad643;
        long jRotateRight = Long.rotateRight(j3 + j4 + jLoad644, 21) + Long.rotateRight(j5, 44);
        jArr[0] = j5 + jLoad644;
        jArr[1] = jRotateRight + j4;
    }

    @Override // com.google.common.hash.HashFunction
    public int bits() {
        return 64;
    }

    @Override // com.google.common.hash.AbstractNonStreamingHashFunction, com.google.common.hash.AbstractHashFunction, com.google.common.hash.HashFunction
    public HashCode hashBytes(byte[] bArr, int i2, int i3) {
        Preconditions.checkPositionIndexes(i2, i2 + i3, bArr.length);
        return HashCode.fromLong(fingerprint(bArr, i2, i3));
    }

    public String toString() {
        return "Hashing.farmHashFingerprint64()";
    }
}
