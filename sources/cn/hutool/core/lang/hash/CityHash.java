package cn.hutool.core.lang.hash;

import cn.hutool.core.util.ByteUtil;
import java.util.Arrays;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes.dex */
public class CityHash {

    /* renamed from: c1, reason: collision with root package name */
    private static final int f2490c1 = -862048943;

    /* renamed from: c2, reason: collision with root package name */
    private static final int f2491c2 = 461845907;

    /* renamed from: k0, reason: collision with root package name */
    private static final long f2492k0 = -4348849565147123417L;

    /* renamed from: k1, reason: collision with root package name */
    private static final long f2493k1 = -5435081209227447693L;
    private static final long k2 = -7286425919675154353L;
    private static final long kMul = -7070675565921424023L;

    private static Number128 cityMurmur(byte[] bArr, Number128 number128) {
        long jShiftMix;
        long jHashLen0to16;
        long jShiftMix2;
        int length = bArr.length;
        long lowValue = number128.getLowValue();
        long highValue = number128.getHighValue();
        int i2 = length - 16;
        int i3 = 0;
        if (i2 <= 0) {
            jShiftMix = shiftMix(lowValue * f2493k1) * f2493k1;
            jHashLen0to16 = (f2493k1 * highValue) + hashLen0to16(bArr);
            jShiftMix2 = shiftMix((length >= 8 ? fetch64(bArr, 0) : jHashLen0to16) + jShiftMix);
        } else {
            long jHashLen16 = hashLen16(fetch64(bArr, length - 8) + f2493k1, lowValue);
            long jHashLen162 = hashLen16(length + highValue, fetch64(bArr, i2) + jHashLen16);
            jShiftMix = lowValue + jHashLen162;
            do {
                jShiftMix = (jShiftMix ^ (shiftMix(fetch64(bArr, i3) * f2493k1) * f2493k1)) * f2493k1;
                highValue ^= jShiftMix;
                jHashLen16 = (jHashLen16 ^ (shiftMix(fetch64(bArr, i3 + 8) * f2493k1) * f2493k1)) * f2493k1;
                jHashLen162 ^= jHashLen16;
                i3 += 16;
                i2 -= 16;
            } while (i2 > 0);
            jHashLen0to16 = jHashLen16;
            jShiftMix2 = jHashLen162;
        }
        long jHashLen163 = hashLen16(jShiftMix, jHashLen0to16);
        long jHashLen164 = hashLen16(jShiftMix2, highValue);
        return new Number128(jHashLen163 ^ jHashLen164, hashLen16(jHashLen164, jHashLen163));
    }

    private static int fetch32(byte[] bArr, int i2) {
        return ByteUtil.bytesToInt(bArr, i2, ByteUtil.CPU_ENDIAN);
    }

    private static long fetch64(byte[] bArr, int i2) {
        return ByteUtil.bytesToLong(bArr, i2, ByteUtil.CPU_ENDIAN);
    }

    private static int fmix(int i2) {
        int i3 = (i2 ^ (i2 >>> 16)) * (-2048144789);
        int i4 = (i3 ^ (i3 >>> 13)) * (-1028477387);
        return i4 ^ (i4 >>> 16);
    }

    public static Number128 hash128(byte[] bArr) {
        return bArr.length >= 16 ? hash128(bArr, 16, new Number128(fetch64(bArr, 0), fetch64(bArr, 8) + f2492k0)) : hash128(bArr, 0, new Number128(f2492k0, f2493k1));
    }

    private static long hash128to64(Number128 number128) {
        long lowValue = (number128.getLowValue() ^ number128.getHighValue()) * kMul;
        long highValue = ((lowValue ^ (lowValue >>> 47)) ^ number128.getHighValue()) * kMul;
        return (highValue ^ (highValue >>> 47)) * kMul;
    }

    public static int hash32(byte[] bArr) {
        int length = bArr.length;
        if (length <= 24) {
            return length <= 12 ? length <= 4 ? hash32Len0to4(bArr) : hash32Len5to12(bArr) : hash32Len13to24(bArr);
        }
        int i2 = length * f2490c1;
        int iRotate32 = rotate32(fetch32(bArr, length - 4) * f2490c1, 17);
        int i3 = f2491c2;
        int i4 = iRotate32 * f2491c2;
        int iRotate322 = rotate32(fetch32(bArr, length - 8) * f2490c1, 17) * f2491c2;
        int iRotate323 = rotate32(fetch32(bArr, length - 16) * f2490c1, 17) * f2491c2;
        int iRotate324 = rotate32(fetch32(bArr, length - 12) * f2490c1, 17) * f2491c2;
        int iRotate325 = rotate32(fetch32(bArr, length - 20) * f2490c1, 17) * f2491c2;
        int iRotate326 = (rotate32(((rotate32(i4 ^ length, 19) * 5) - 430675100) ^ iRotate323, 19) * 5) - 430675100;
        int iRotate327 = (rotate32(((rotate32(iRotate322 ^ i2, 19) * 5) - 430675100) ^ iRotate324, 19) * 5) - 430675100;
        int iRotate328 = (rotate32(i2 + iRotate325, 19) * 5) - 430675100;
        int i5 = (length - 1) / 20;
        int i6 = 0;
        while (true) {
            int iRotate329 = rotate32(fetch32(bArr, i6) * f2490c1, 17) * i3;
            int iFetch32 = fetch32(bArr, i6 + 4);
            int iRotate3210 = rotate32(fetch32(bArr, i6 + 8) * f2490c1, 17) * i3;
            int iRotate3211 = rotate32(fetch32(bArr, i6 + 12) * f2490c1, 17) * i3;
            int iFetch322 = fetch32(bArr, i6 + 16);
            int iRotate3212 = (rotate32(iRotate326 ^ iRotate329, 18) * 5) - 430675100;
            int iRotate3213 = rotate32(iRotate328 + iFetch32, 19) * f2490c1;
            int iRotate3214 = (rotate32(iRotate327 + iRotate3210, 18) * 5) - 430675100;
            int iRotate3215 = (rotate32(iRotate3212 ^ (iRotate3211 + iFetch32), 19) * 5) - 430675100;
            int iReverseBytes = Integer.reverseBytes(iRotate3214 ^ iFetch322) * 5;
            iRotate327 = Integer.reverseBytes(iRotate3215 + (iFetch322 * 5));
            iRotate326 = iRotate3213 + iRotate329;
            i6 += 20;
            i5--;
            if (i5 == 0) {
                return rotate32((rotate32((rotate32((rotate32(iRotate326 + (rotate32(rotate32(iRotate327, 11) * f2490c1, 17) * f2490c1), 19) * 5) - 430675100, 17) * f2490c1) + (rotate32(rotate32(iReverseBytes, 11) * f2490c1, 17) * f2490c1), 19) * 5) - 430675100, 17) * f2490c1;
            }
            iRotate328 = iReverseBytes;
            i3 = f2491c2;
        }
    }

    private static int hash32Len0to4(byte[] bArr) {
        int length = bArr.length;
        int i2 = 9;
        int i3 = 0;
        for (byte b3 : bArr) {
            i3 = (i3 * f2490c1) + b3;
            i2 ^= i3;
        }
        return fmix(mur(i3, mur(length, i2)));
    }

    private static int hash32Len13to24(byte[] bArr) {
        int length = bArr.length;
        int i2 = length >>> 1;
        int iFetch32 = fetch32(bArr, i2 - 4);
        int iFetch322 = fetch32(bArr, 4);
        int iFetch323 = fetch32(bArr, length - 8);
        return fmix(mur(fetch32(bArr, length - 4), mur(fetch32(bArr, 0), mur(fetch32(bArr, i2), mur(iFetch323, mur(iFetch322, mur(iFetch32, length)))))));
    }

    private static int hash32Len5to12(byte[] bArr) {
        int length = bArr.length;
        int i2 = length * 5;
        return fmix(mur(fetch32(bArr, (length >>> 1) & 4) + 9, mur(fetch32(bArr, length - 4) + i2, mur(fetch32(bArr, 0) + length, i2))));
    }

    public static long hash64(byte[] bArr) {
        int length = bArr.length;
        if (length <= 32) {
            return length <= 16 ? hashLen0to16(bArr) : hashLen17to32(bArr);
        }
        if (length <= 64) {
            return hashLen33to64(bArr);
        }
        long jFetch64 = fetch64(bArr, length - 40);
        long jFetch642 = fetch64(bArr, length - 16) + fetch64(bArr, length - 56);
        long j2 = length;
        long jHashLen16 = hashLen16(fetch64(bArr, length - 48) + j2, fetch64(bArr, length - 24));
        Number128 number128WeakHashLen32WithSeeds = weakHashLen32WithSeeds(bArr, length - 64, j2, jHashLen16);
        Number128 number128WeakHashLen32WithSeeds2 = weakHashLen32WithSeeds(bArr, length - 32, jFetch642 + f2493k1, jFetch64);
        long jFetch643 = (jFetch64 * f2493k1) + fetch64(bArr, 0);
        int i2 = (length - 1) & (-64);
        Number128 number128WeakHashLen32WithSeeds3 = number128WeakHashLen32WithSeeds2;
        int i3 = 0;
        int i4 = i2;
        long j3 = jFetch643;
        while (true) {
            long jRotate64 = rotate64(j3 + jFetch642 + number128WeakHashLen32WithSeeds.getLowValue() + fetch64(bArr, i3 + 8), 37) * f2493k1;
            long jRotate642 = rotate64(jFetch642 + number128WeakHashLen32WithSeeds.getHighValue() + fetch64(bArr, i3 + 48), 42) * f2493k1;
            long highValue = jRotate64 ^ number128WeakHashLen32WithSeeds3.getHighValue();
            long lowValue = jRotate642 + number128WeakHashLen32WithSeeds.getLowValue() + fetch64(bArr, i3 + 40);
            long jRotate643 = rotate64(jHashLen16 + number128WeakHashLen32WithSeeds3.getLowValue(), 33) * f2493k1;
            number128WeakHashLen32WithSeeds = weakHashLen32WithSeeds(bArr, i3, number128WeakHashLen32WithSeeds.getHighValue() * f2493k1, highValue + number128WeakHashLen32WithSeeds3.getLowValue());
            number128WeakHashLen32WithSeeds3 = weakHashLen32WithSeeds(bArr, i3 + 32, number128WeakHashLen32WithSeeds3.getHighValue() + jRotate643, lowValue + fetch64(bArr, i3 + 16));
            i3 += 64;
            i4 -= 64;
            if (i4 == 0) {
                return hashLen16(hashLen16(number128WeakHashLen32WithSeeds.getLowValue(), number128WeakHashLen32WithSeeds3.getLowValue()) + (shiftMix(lowValue) * f2493k1) + highValue, hashLen16(number128WeakHashLen32WithSeeds.getHighValue(), number128WeakHashLen32WithSeeds3.getHighValue()) + jRotate643);
            }
            j3 = jRotate643;
            jHashLen16 = highValue;
            jFetch642 = lowValue;
        }
    }

    private static long hashLen0to16(byte[] bArr) {
        int length = bArr.length;
        if (length >= 8) {
            long j2 = (length * 2) + k2;
            long jFetch64 = fetch64(bArr, 0) + k2;
            long jFetch642 = fetch64(bArr, length - 8);
            return hashLen16((rotate64(jFetch642, 37) * j2) + jFetch64, (rotate64(jFetch64, 25) + jFetch642) * j2, j2);
        }
        if (length >= 4) {
            return hashLen16(length + ((fetch32(bArr, 0) & InternalZipConstants.ZIP_64_LIMIT) << 3), fetch32(bArr, length - 4) & InternalZipConstants.ZIP_64_LIMIT, (length * 2) + k2);
        }
        if (length <= 0) {
            return k2;
        }
        int i2 = bArr[0] & 255;
        int i3 = bArr[length >>> 1] & 255;
        return shiftMix(((length + ((bArr[length - 1] & 255) << 2)) * f2492k0) ^ ((i2 + (i3 << 8)) * k2)) * k2;
    }

    private static long hashLen16(long j2, long j3) {
        return hash128to64(new Number128(j2, j3));
    }

    private static long hashLen16(long j2, long j3, long j4) {
        long j5 = (j2 ^ j3) * j4;
        long j6 = ((j5 ^ (j5 >>> 47)) ^ j3) * j4;
        return (j6 ^ (j6 >>> 47)) * j4;
    }

    private static long hashLen17to32(byte[] bArr) {
        int length = bArr.length;
        long j2 = (length * 2) + k2;
        long jFetch64 = fetch64(bArr, 0) * f2493k1;
        long jFetch642 = fetch64(bArr, 8);
        long jFetch643 = fetch64(bArr, length - 8) * j2;
        return hashLen16((fetch64(bArr, length - 16) * k2) + rotate64(jFetch64 + jFetch642, 43) + rotate64(jFetch643, 30), jFetch643 + jFetch64 + rotate64(jFetch642 + k2, 18), j2);
    }

    private static long hashLen33to64(byte[] bArr) {
        int length = bArr.length;
        long j2 = (length * 2) + k2;
        long jFetch64 = fetch64(bArr, 0) * k2;
        long jFetch642 = fetch64(bArr, 8);
        long jFetch643 = fetch64(bArr, length - 24);
        long jFetch644 = fetch64(bArr, length - 32);
        long jFetch645 = fetch64(bArr, 16) * k2;
        long jFetch646 = fetch64(bArr, 24) * 9;
        long jFetch647 = fetch64(bArr, length - 8);
        long jFetch648 = fetch64(bArr, length - 16) * j2;
        long j3 = jFetch64 + jFetch647;
        long jRotate64 = rotate64(j3, 43) + ((rotate64(jFetch642, 30) + jFetch643) * 9);
        long j4 = (j3 ^ jFetch644) + jFetch646 + 1;
        long jReverseBytes = Long.reverseBytes((jRotate64 + j4) * j2) + jFetch648;
        long j5 = jFetch645 + jFetch646;
        long jRotate642 = rotate64(j5, 42) + jFetch643;
        long j6 = j5 + jFetch643;
        return (shiftMix(((j6 + Long.reverseBytes(((jRotate642 + j6) * j2) + ((Long.reverseBytes((j4 + jReverseBytes) * j2) + jFetch647) * j2)) + jFetch642) * j2) + jFetch644 + jFetch648) * j2) + jRotate642;
    }

    private static int mur(int i2, int i3) {
        return (rotate32((rotate32(i2 * f2490c1, 17) * f2491c2) ^ i3, 19) * 5) - 430675100;
    }

    private static int rotate32(int i2, int i3) {
        if (i3 == 0) {
            return i2;
        }
        return (i2 << (32 - i3)) | (i2 >>> i3);
    }

    private static long rotate64(long j2, int i2) {
        if (i2 == 0) {
            return j2;
        }
        return (j2 << (64 - i2)) | (j2 >>> i2);
    }

    private static long shiftMix(long j2) {
        return j2 ^ (j2 >>> 47);
    }

    private static Number128 weakHashLen32WithSeeds(long j2, long j3, long j4, long j5, long j6, long j7) {
        long j8 = j6 + j2;
        long j9 = j3 + j8 + j4;
        return new Number128(j9 + j5, rotate64(j7 + j8 + j5, 21) + rotate64(j9, 44) + j8);
    }

    private static Number128 weakHashLen32WithSeeds(byte[] bArr, int i2, long j2, long j3) {
        return weakHashLen32WithSeeds(fetch64(bArr, i2), fetch64(bArr, i2 + 8), fetch64(bArr, i2 + 16), fetch64(bArr, i2 + 24), j2, j3);
    }

    public static Number128 hash128(byte[] bArr, Number128 number128) {
        return hash128(bArr, 0, number128);
    }

    private static Number128 hash128(byte[] bArr, int i2, Number128 number128) {
        long lowValue;
        long jRotate64;
        Number128 number128WeakHashLen32WithSeeds;
        int length = bArr.length - i2;
        if (length < 128) {
            return cityMurmur(Arrays.copyOfRange(bArr, i2, bArr.length), number128);
        }
        Number128 number1282 = new Number128(0L, 0L);
        Number128 number1283 = new Number128(0L, 0L);
        long lowValue2 = number128.getLowValue();
        long highValue = number128.getHighValue();
        long highValue2 = length * f2493k1;
        number1282.setLowValue((rotate64(highValue ^ f2493k1, 49) * f2493k1) + fetch64(bArr, i2));
        number1282.setHighValue((rotate64(number1282.getLowValue(), 42) * f2493k1) + fetch64(bArr, i2 + 8));
        number1283.setLowValue((rotate64(highValue + highValue2, 35) * f2493k1) + lowValue2);
        number1283.setHighValue(rotate64(fetch64(bArr, i2 + 88) + lowValue2, 53) * f2493k1);
        int i3 = i2;
        int i4 = length;
        long j2 = highValue;
        Number128 number128WeakHashLen32WithSeeds2 = number1283;
        while (true) {
            long jRotate642 = rotate64(lowValue2 + j2 + number1282.getLowValue() + fetch64(bArr, i3 + 8), 37) * f2493k1;
            long jRotate643 = rotate64(j2 + number1282.getHighValue() + fetch64(bArr, i3 + 48), 42) * f2493k1;
            long highValue3 = jRotate642 ^ number128WeakHashLen32WithSeeds2.getHighValue();
            long lowValue3 = jRotate643 + number1282.getLowValue() + fetch64(bArr, i3 + 40);
            long jRotate644 = rotate64(highValue2 + number128WeakHashLen32WithSeeds2.getLowValue(), 33) * f2493k1;
            Number128 number128WeakHashLen32WithSeeds3 = weakHashLen32WithSeeds(bArr, i3, number1282.getHighValue() * f2493k1, highValue3 + number128WeakHashLen32WithSeeds2.getLowValue());
            Number128 number128WeakHashLen32WithSeeds4 = weakHashLen32WithSeeds(bArr, i3 + 32, number128WeakHashLen32WithSeeds2.getHighValue() + jRotate644, lowValue3 + fetch64(bArr, i3 + 16));
            int i5 = i3 + 64;
            long jRotate645 = rotate64(jRotate644 + lowValue3 + number128WeakHashLen32WithSeeds3.getLowValue() + fetch64(bArr, i5 + 8), 37) * f2493k1;
            long jRotate646 = rotate64(lowValue3 + number128WeakHashLen32WithSeeds3.getHighValue() + fetch64(bArr, i5 + 48), 42) * f2493k1;
            highValue2 = jRotate645 ^ number128WeakHashLen32WithSeeds4.getHighValue();
            lowValue = jRotate646 + number128WeakHashLen32WithSeeds3.getLowValue() + fetch64(bArr, i5 + 40);
            jRotate64 = rotate64(highValue3 + number128WeakHashLen32WithSeeds4.getLowValue(), 33) * f2493k1;
            number128WeakHashLen32WithSeeds = weakHashLen32WithSeeds(bArr, i5, number128WeakHashLen32WithSeeds3.getHighValue() * f2493k1, highValue2 + number128WeakHashLen32WithSeeds4.getLowValue());
            number128WeakHashLen32WithSeeds2 = weakHashLen32WithSeeds(bArr, i5 + 32, jRotate64 + number128WeakHashLen32WithSeeds4.getHighValue(), lowValue + fetch64(bArr, i5 + 16));
            i3 = i5 + 64;
            i4 -= 128;
            if (i4 < 128) {
                break;
            }
            number1282 = number128WeakHashLen32WithSeeds;
            lowValue2 = jRotate64;
            j2 = lowValue;
        }
        long jRotate647 = jRotate64 + (rotate64(number128WeakHashLen32WithSeeds.getLowValue() + highValue2, 49) * f2492k0);
        long jRotate648 = (lowValue * f2492k0) + rotate64(number128WeakHashLen32WithSeeds2.getHighValue(), 37);
        long jRotate649 = (highValue2 * f2492k0) + rotate64(number128WeakHashLen32WithSeeds2.getLowValue(), 27);
        number128WeakHashLen32WithSeeds2.setLowValue(number128WeakHashLen32WithSeeds2.getLowValue() * 9);
        number128WeakHashLen32WithSeeds.setLowValue(number128WeakHashLen32WithSeeds.getLowValue() * f2492k0);
        int i6 = 0;
        while (true) {
            long j3 = jRotate647;
            if (i6 < i4) {
                int i7 = i6 + 32;
                jRotate648 = (rotate64(j3 + jRotate648, 42) * f2492k0) + number128WeakHashLen32WithSeeds.getHighValue();
                int i8 = (i3 + i4) - i7;
                number128WeakHashLen32WithSeeds2.setLowValue(number128WeakHashLen32WithSeeds2.getLowValue() + fetch64(bArr, i8 + 16));
                jRotate647 = (j3 * f2492k0) + number128WeakHashLen32WithSeeds2.getLowValue();
                jRotate649 += number128WeakHashLen32WithSeeds2.getHighValue() + fetch64(bArr, i8);
                number128WeakHashLen32WithSeeds2.setHighValue(number128WeakHashLen32WithSeeds2.getHighValue() + number128WeakHashLen32WithSeeds.getLowValue());
                number128WeakHashLen32WithSeeds = weakHashLen32WithSeeds(bArr, i8, number128WeakHashLen32WithSeeds.getLowValue() + jRotate649, number128WeakHashLen32WithSeeds.getHighValue());
                number128WeakHashLen32WithSeeds.setLowValue(number128WeakHashLen32WithSeeds.getLowValue() * f2492k0);
                i6 = i7;
            } else {
                long jHashLen16 = hashLen16(j3, number128WeakHashLen32WithSeeds.getLowValue());
                long jHashLen162 = hashLen16(jRotate648 + jRotate649, number128WeakHashLen32WithSeeds2.getLowValue());
                return new Number128(hashLen16(number128WeakHashLen32WithSeeds.getHighValue() + jHashLen16, number128WeakHashLen32WithSeeds2.getHighValue()) + jHashLen162, hashLen16(jHashLen16 + number128WeakHashLen32WithSeeds2.getHighValue(), jHashLen162 + number128WeakHashLen32WithSeeds.getHighValue()));
            }
        }
    }

    public static long hash64(byte[] bArr, long j2, long j3) {
        return hashLen16(hash64(bArr) - j2, j3);
    }

    public static long hash64(byte[] bArr, long j2) {
        return hash64(bArr, k2, j2);
    }
}
