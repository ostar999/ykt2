package cn.hutool.core.util;

import cn.hutool.core.lang.hash.CityHash;
import cn.hutool.core.lang.hash.MetroHash;
import cn.hutool.core.lang.hash.MurmurHash;
import cn.hutool.core.lang.hash.Number128;
import com.yikaobang.yixue.R2;

/* loaded from: classes.dex */
public class HashUtil {
    public static int additiveHash(String str, int i2) {
        int length = str.length();
        for (int i3 = 0; i3 < str.length(); i3++) {
            length += str.charAt(i3);
        }
        return length % i2;
    }

    public static int apHash(String str) {
        int iCharAt = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            iCharAt ^= (i2 & 1) == 0 ? ((iCharAt << 7) ^ str.charAt(i2)) ^ (iCharAt >> 3) : ~(((iCharAt << 11) ^ str.charAt(i2)) ^ (iCharAt >> 5));
        }
        return iCharAt;
    }

    public static int bernstein(String str) {
        int iCharAt = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            iCharAt = (iCharAt * 33) + str.charAt(i2);
        }
        return iCharAt;
    }

    public static int bkdrHash(String str) {
        int iCharAt = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            iCharAt = (iCharAt * 131) + str.charAt(i2);
        }
        return Integer.MAX_VALUE & iCharAt;
    }

    public static long[] cityHash128(byte[] bArr) {
        return CityHash.hash128(bArr).getLongArray();
    }

    public static int cityHash32(byte[] bArr) {
        return CityHash.hash32(bArr);
    }

    public static long cityHash64(byte[] bArr, long j2) {
        return CityHash.hash64(bArr, j2);
    }

    public static int dekHash(String str) {
        int length = str.length();
        for (int i2 = 0; i2 < str.length(); i2++) {
            length = ((length >> 27) ^ (length << 5)) ^ str.charAt(i2);
        }
        return Integer.MAX_VALUE & length;
    }

    public static int djbHash(String str) {
        int iCharAt = R2.color.material_dynamic_neutral_variant30;
        for (int i2 = 0; i2 < str.length(); i2++) {
            iCharAt = str.charAt(i2) + (iCharAt << 5) + iCharAt;
        }
        return Integer.MAX_VALUE & iCharAt;
    }

    public static int elfHash(String str) {
        int iCharAt = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            iCharAt = (iCharAt << 4) + str.charAt(i2);
            int i3 = (int) (iCharAt & 4026531840L);
            if (i3 != 0) {
                iCharAt = (iCharAt ^ (i3 >> 24)) & (~i3);
            }
        }
        return Integer.MAX_VALUE & iCharAt;
    }

    public static int fnvHash(byte[] bArr) {
        int i2 = -2128831035;
        for (byte b3 : bArr) {
            i2 = (i2 ^ b3) * 16777619;
        }
        int i3 = i2 + (i2 << 13);
        int i4 = (i3 >> 7) ^ i3;
        int i5 = i4 + (i4 << 3);
        int i6 = i5 ^ (i5 >> 17);
        return Math.abs(i6 + (i6 << 5));
    }

    public static long hfHash(String str) {
        long jCharAt = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            jCharAt += str.charAt(i2) * 3 * i2;
        }
        return jCharAt < 0 ? -jCharAt : jCharAt;
    }

    public static long hfIpHash(String str) {
        long jCharAt = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            jCharAt += str.charAt(i2 % 4) ^ str.charAt(i2);
        }
        return jCharAt;
    }

    public static int identityHashCode(Object obj) {
        return System.identityHashCode(obj);
    }

    public static int intHash(int i2) {
        int i3 = i2 + (~(i2 << 15));
        int i4 = i3 ^ (i3 >>> 10);
        int i5 = i4 + (i4 << 3);
        int i6 = i5 ^ (i5 >>> 6);
        int i7 = i6 + (~(i6 << 11));
        return i7 ^ (i7 >>> 16);
    }

    public static int javaDefaultHash(String str) {
        int length = str.length();
        int i2 = 0;
        int iCharAt = 0;
        int i3 = 0;
        while (i2 < length) {
            iCharAt = (iCharAt * 31) + str.charAt(i3);
            i2++;
            i3++;
        }
        return iCharAt;
    }

    public static int jsHash(String str) {
        int iCharAt = 1315423911;
        for (int i2 = 0; i2 < str.length(); i2++) {
            iCharAt ^= ((iCharAt << 5) + str.charAt(i2)) + (iCharAt >> 2);
        }
        return Math.abs(iCharAt) & Integer.MAX_VALUE;
    }

    public static long[] metroHash128(byte[] bArr, long j2) {
        return MetroHash.hash128(bArr, j2).getLongArray();
    }

    public static long metroHash64(byte[] bArr, long j2) {
        return MetroHash.hash64(bArr, j2);
    }

    public static long mixHash(String str) {
        return (str.hashCode() << 32) | fnvHash(str);
    }

    public static long[] murmur128(byte[] bArr) {
        return MurmurHash.hash128(bArr);
    }

    public static int murmur32(byte[] bArr) {
        return MurmurHash.hash32(bArr);
    }

    public static long murmur64(byte[] bArr) {
        return MurmurHash.hash64(bArr);
    }

    public static int oneByOneHash(String str) {
        int i2 = 0;
        for (int i3 = 0; i3 < str.length(); i3++) {
            int iCharAt = i2 + str.charAt(i3);
            int i4 = iCharAt + (iCharAt << 10);
            i2 = i4 ^ (i4 >> 6);
        }
        int i5 = i2 + (i2 << 3);
        int i6 = (i5 >> 11) ^ i5;
        return i6 + (i6 << 15);
    }

    public static int pjwHash(String str) {
        int iCharAt = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            iCharAt = (iCharAt << 4) + str.charAt(i2);
            int i3 = (-268435456) & iCharAt;
            if (i3 != 0) {
                iCharAt = (iCharAt ^ (i3 >> 24)) & 268435455;
            }
        }
        return Integer.MAX_VALUE & iCharAt;
    }

    public static int rotatingHash(String str, int i2) {
        int length = str.length();
        for (int i3 = 0; i3 < str.length(); i3++) {
            length = ((length >> 28) ^ (length << 4)) ^ str.charAt(i3);
        }
        return length % i2;
    }

    public static int rsHash(String str) {
        int i2 = 63689;
        int iCharAt = 0;
        for (int i3 = 0; i3 < str.length(); i3++) {
            iCharAt = (iCharAt * i2) + str.charAt(i3);
            i2 *= 378551;
        }
        return Integer.MAX_VALUE & iCharAt;
    }

    public static int sdbmHash(String str) {
        int iCharAt = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            iCharAt = ((str.charAt(i2) + (iCharAt << 6)) + (iCharAt << 16)) - iCharAt;
        }
        return Integer.MAX_VALUE & iCharAt;
    }

    public static long tianlHash(String str) {
        int length = str.length();
        if (length == 0) {
            return 0L;
        }
        long j2 = length <= 256 ? (length - 1) * 16777216 : 4278190080L;
        char c3 = 'A';
        long j3 = 3;
        int i2 = 96;
        int i3 = 1;
        if (length <= 96) {
            while (i3 <= length) {
                char cCharAt = str.charAt(i3 - 1);
                if (cCharAt <= 'Z' && cCharAt >= c3) {
                    cCharAt = (char) (cCharAt + ' ');
                }
                long j4 = i3;
                long j5 = j4 * j3;
                long j6 = cCharAt;
                j2 += (((((j5 * j6) * j6) + ((j4 * 5) * j6)) + (j4 * 7)) + (cCharAt * 11)) % 16777216;
                i3++;
                c3 = 'A';
                j3 = 3;
            }
        } else {
            int i4 = 1;
            while (i4 <= i2) {
                char cCharAt2 = str.charAt(((i4 + length) - i2) - i3);
                if (cCharAt2 <= 'Z' && cCharAt2 >= 'A') {
                    cCharAt2 = (char) (cCharAt2 + ' ');
                }
                long j7 = i4;
                long j8 = cCharAt2;
                j2 += ((((((j7 * 3) * j8) * j8) + ((j7 * 5) * j8)) + (j7 * 7)) + (cCharAt2 * 11)) % 16777216;
                i4++;
                i2 = 96;
                i3 = 1;
            }
        }
        return j2 < 0 ? j2 * (-1) : j2;
    }

    public static int universal(char[] cArr, int i2, int[] iArr) {
        int length = cArr.length;
        int length2 = cArr.length;
        for (int i3 = 0; i3 < (length2 << 3); i3 += 8) {
            char c3 = cArr[i3 >> 3];
            if ((c3 & 1) == 0) {
                length ^= iArr[i3];
            }
            if ((c3 & 2) == 0) {
                length ^= iArr[i3 + 1];
            }
            if ((c3 & 4) == 0) {
                length ^= iArr[i3 + 2];
            }
            if ((c3 & '\b') == 0) {
                length ^= iArr[i3 + 3];
            }
            if ((c3 & 16) == 0) {
                length ^= iArr[i3 + 4];
            }
            if ((c3 & ' ') == 0) {
                length ^= iArr[i3 + 5];
            }
            if ((c3 & '@') == 0) {
                length ^= iArr[i3 + 6];
            }
            if ((c3 & 128) == 0) {
                length ^= iArr[i3 + 7];
            }
        }
        return length & i2;
    }

    public static int zobrist(char[] cArr, int i2, int[][] iArr) {
        int length = cArr.length;
        for (int i3 = 0; i3 < cArr.length; i3++) {
            length ^= iArr[i3][cArr[i3]];
        }
        return length & i2;
    }

    public static long[] cityHash128(byte[] bArr, Number128 number128) {
        return CityHash.hash128(bArr, number128).getLongArray();
    }

    public static long cityHash64(byte[] bArr, long j2, long j3) {
        return CityHash.hash64(bArr, j2, j3);
    }

    public static long[] metroHash128(byte[] bArr) {
        return MetroHash.hash128(bArr).getLongArray();
    }

    public static long metroHash64(byte[] bArr) {
        return MetroHash.hash64(bArr);
    }

    public static long cityHash64(byte[] bArr) {
        return CityHash.hash64(bArr);
    }

    public static int fnvHash(String str) {
        int iCharAt = -2128831035;
        for (int i2 = 0; i2 < str.length(); i2++) {
            iCharAt = (iCharAt ^ str.charAt(i2)) * 16777619;
        }
        int i3 = iCharAt + (iCharAt << 13);
        int i4 = (i3 >> 7) ^ i3;
        int i5 = i4 + (i4 << 3);
        int i6 = i5 ^ (i5 >> 17);
        return Math.abs(i6 + (i6 << 5));
    }
}
