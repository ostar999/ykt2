package com.alibaba.fastjson.util;

import cn.hutool.core.text.CharPool;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaPeriodQueue;
import com.heytap.mcssdk.constant.a;
import java.lang.reflect.Array;
import java.math.BigInteger;
import okhttp3.internal.connection.RealConnection;

/* loaded from: classes2.dex */
public final class RyuDouble {
    private static final int[][] POW5_INV_SPLIT;
    private static final int[][] POW5_SPLIT;

    static {
        Class cls = Integer.TYPE;
        POW5_SPLIT = (int[][]) Array.newInstance((Class<?>) cls, 326, 4);
        POW5_INV_SPLIT = (int[][]) Array.newInstance((Class<?>) cls, 291, 4);
        BigInteger bigInteger = BigInteger.ONE;
        BigInteger bigIntegerSubtract = bigInteger.shiftLeft(31).subtract(bigInteger);
        BigInteger bigIntegerSubtract2 = bigInteger.shiftLeft(31).subtract(bigInteger);
        int i2 = 0;
        while (i2 < 326) {
            BigInteger bigIntegerPow = BigInteger.valueOf(5L).pow(i2);
            int iBitLength = bigIntegerPow.bitLength();
            int i3 = i2 == 0 ? 1 : (int) ((((i2 * 23219280) + 10000000) - 1) / 10000000);
            if (i3 != iBitLength) {
                throw new IllegalStateException(iBitLength + " != " + i3);
            }
            if (i2 < POW5_SPLIT.length) {
                for (int i4 = 0; i4 < 4; i4++) {
                    POW5_SPLIT[i2][i4] = bigIntegerPow.shiftRight((iBitLength - 121) + ((3 - i4) * 31)).and(bigIntegerSubtract).intValue();
                }
            }
            if (i2 < POW5_INV_SPLIT.length) {
                BigInteger bigInteger2 = BigInteger.ONE;
                BigInteger bigIntegerAdd = bigInteger2.shiftLeft(iBitLength + 121).divide(bigIntegerPow).add(bigInteger2);
                for (int i5 = 0; i5 < 4; i5++) {
                    if (i5 == 0) {
                        POW5_INV_SPLIT[i2][i5] = bigIntegerAdd.shiftRight((3 - i5) * 31).intValue();
                    } else {
                        POW5_INV_SPLIT[i2][i5] = bigIntegerAdd.shiftRight((3 - i5) * 31).and(bigIntegerSubtract2).intValue();
                    }
                }
            }
            i2++;
        }
    }

    public static String toString(double d3) {
        char[] cArr = new char[24];
        return new String(cArr, 0, toString(d3, cArr, 0));
    }

    public static int toString(double d3, char[] cArr, int i2) {
        int i3;
        boolean z2;
        boolean z3;
        int i4;
        long j2;
        long j3;
        long j4;
        int i5;
        boolean z4;
        boolean z5;
        long j5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        boolean z6;
        int i11;
        int i12;
        int i13;
        int i14;
        if (!Double.isNaN(d3)) {
            if (d3 == Double.POSITIVE_INFINITY) {
                int i15 = i2 + 1;
                cArr[i2] = 'I';
                int i16 = i15 + 1;
                cArr[i15] = 'n';
                int i17 = i16 + 1;
                cArr[i16] = 'f';
                int i18 = i17 + 1;
                cArr[i17] = 'i';
                int i19 = i18 + 1;
                cArr[i18] = 'n';
                int i20 = i19 + 1;
                cArr[i19] = 'i';
                int i21 = i20 + 1;
                cArr[i20] = 't';
                i10 = i21 + 1;
                cArr[i21] = 'y';
            } else if (d3 == Double.NEGATIVE_INFINITY) {
                int i22 = i2 + 1;
                cArr[i2] = CharPool.DASHED;
                int i23 = i22 + 1;
                cArr[i22] = 'I';
                int i24 = i23 + 1;
                cArr[i23] = 'n';
                int i25 = i24 + 1;
                cArr[i24] = 'f';
                int i26 = i25 + 1;
                cArr[i25] = 'i';
                int i27 = i26 + 1;
                cArr[i26] = 'n';
                int i28 = i27 + 1;
                cArr[i27] = 'i';
                int i29 = i28 + 1;
                cArr[i28] = 't';
                i14 = i29 + 1;
                cArr[i29] = 'y';
            } else {
                long jDoubleToLongBits = Double.doubleToLongBits(d3);
                if (jDoubleToLongBits == 0) {
                    int i30 = i2 + 1;
                    cArr[i2] = '0';
                    int i31 = i30 + 1;
                    cArr[i30] = '.';
                    i14 = i31 + 1;
                    cArr[i31] = '0';
                } else if (jDoubleToLongBits == Long.MIN_VALUE) {
                    int i32 = i2 + 1;
                    cArr[i2] = CharPool.DASHED;
                    int i33 = i32 + 1;
                    cArr[i32] = '0';
                    int i34 = i33 + 1;
                    cArr[i33] = '.';
                    i10 = i34 + 1;
                    cArr[i34] = '0';
                } else {
                    int i35 = (int) ((jDoubleToLongBits >>> 52) & 2047);
                    long j6 = jDoubleToLongBits & 4503599627370495L;
                    if (i35 == 0) {
                        i3 = -1074;
                    } else {
                        i3 = (i35 - 1023) - 52;
                        j6 |= 4503599627370496L;
                    }
                    boolean z7 = jDoubleToLongBits < 0;
                    boolean z8 = (j6 & 1) == 0;
                    long j7 = 4 * j6;
                    long j8 = j7 + 2;
                    int i36 = (j6 != 4503599627370496L || i35 <= 1) ? 1 : 0;
                    long j9 = (j7 - 1) - i36;
                    int i37 = i3 - 2;
                    if (i37 >= 0) {
                        int iMax = Math.max(0, ((int) ((i37 * 3010299) / 10000000)) - 1);
                        int i38 = ((((-i37) + iMax) + (((iMax == 0 ? 1 : (int) ((((iMax * 23219280) + 10000000) - 1) / 10000000)) + 122) - 1)) - 93) - 21;
                        if (i38 >= 0) {
                            int[] iArr = POW5_INV_SPLIT[iMax];
                            long j10 = j7 >>> 31;
                            long j11 = j7 & 2147483647L;
                            int i39 = iArr[0];
                            z2 = z7;
                            int i40 = iArr[1];
                            int i41 = iArr[2];
                            z3 = z8;
                            int i42 = iArr[3];
                            long j12 = ((((((((((((j11 * i42) >>> 31) + (i41 * j11)) + (j10 * i42)) >>> 31) + (i40 * j11)) + (i41 * j10)) >>> 31) + (i39 * j11)) + (i40 * j10)) >>> 21) + ((i39 * j10) << 10)) >>> i38;
                            long j13 = j8 >>> 31;
                            long j14 = j8 & 2147483647L;
                            long j15 = ((((((((((((j14 * i42) >>> 31) + (i41 * j14)) + (j13 * i42)) >>> 31) + (i40 * j14)) + (i41 * j13)) >>> 31) + (i39 * j14)) + (i40 * j13)) >>> 21) + ((i39 * j13) << 10)) >>> i38;
                            long j16 = j9 >>> 31;
                            long j17 = j9 & 2147483647L;
                            long j18 = j15;
                            j4 = ((((((((((((j17 * i42) >>> 31) + (i41 * j17)) + (j16 * i42)) >>> 31) + (i40 * j17)) + (i41 * j16)) >>> 31) + (i39 * j17)) + (i40 * j16)) >>> 21) + ((i39 * j16) << 10)) >>> i38;
                            if (iMax <= 21) {
                                long j19 = j7 % 5;
                                if (j19 == 0) {
                                    if (j19 != 0) {
                                        i13 = 0;
                                    } else if (j7 % 25 != 0) {
                                        i13 = 1;
                                    } else if (j7 % 125 != 0) {
                                        i13 = 2;
                                    } else if (j7 % 625 != 0) {
                                        i13 = 3;
                                    } else {
                                        long j20 = j7 / 625;
                                        i13 = 4;
                                        for (long j21 = 0; j20 > j21 && j20 % 5 == j21; j21 = 0) {
                                            j20 /= 5;
                                            i13++;
                                        }
                                    }
                                    z5 = i13 >= iMax;
                                    z6 = false;
                                    j2 = j12;
                                    j3 = j18;
                                    i4 = 2;
                                    z4 = z6;
                                    i5 = iMax;
                                } else {
                                    if (z3) {
                                        if (j9 % 5 != 0) {
                                            i12 = 0;
                                        } else if (j9 % 25 != 0) {
                                            i12 = 1;
                                        } else if (j9 % 125 != 0) {
                                            i12 = 2;
                                        } else if (j9 % 625 != 0) {
                                            i12 = 3;
                                        } else {
                                            long j22 = j9 / 625;
                                            i12 = 4;
                                            for (long j23 = 0; j22 > j23 && j22 % 5 == j23; j23 = 0) {
                                                j22 /= 5;
                                                i12++;
                                            }
                                        }
                                        z6 = i12 >= iMax;
                                        z5 = false;
                                        j2 = j12;
                                        j3 = j18;
                                        i4 = 2;
                                        z4 = z6;
                                        i5 = iMax;
                                    } else {
                                        if (j8 % 5 != 0) {
                                            i11 = 0;
                                        } else if (j8 % 25 != 0) {
                                            i11 = 1;
                                        } else if (j8 % 125 != 0) {
                                            i11 = 2;
                                        } else if (j8 % 625 != 0) {
                                            i11 = 3;
                                        } else {
                                            long j24 = j8 / 625;
                                            i11 = 4;
                                            for (long j25 = 0; j24 > j25 && j24 % 5 == j25; j25 = 0) {
                                                j24 /= 5;
                                                i11++;
                                            }
                                        }
                                        if (i11 >= iMax) {
                                            j18--;
                                        }
                                    }
                                    z5 = false;
                                    j2 = j12;
                                    j3 = j18;
                                    i4 = 2;
                                    z4 = z6;
                                    i5 = iMax;
                                }
                            } else {
                                z5 = false;
                                j2 = j12;
                                j3 = j18;
                                i4 = 2;
                                z4 = z6;
                                i5 = iMax;
                            }
                        } else {
                            throw new IllegalArgumentException("" + i38);
                        }
                    } else {
                        z2 = z7;
                        z3 = z8;
                        int i43 = -i37;
                        int iMax2 = Math.max(0, ((int) ((i43 * 6989700) / 10000000)) - 1);
                        int i44 = i43 - iMax2;
                        int i45 = ((iMax2 - ((i44 == 0 ? 1 : (int) ((((i44 * 23219280) + 10000000) - 1) / 10000000)) - 121)) - 93) - 21;
                        if (i45 >= 0) {
                            int[] iArr2 = POW5_SPLIT[i44];
                            long j26 = j7 >>> 31;
                            long j27 = j7 & 2147483647L;
                            int i46 = iArr2[0];
                            int i47 = iArr2[1];
                            int i48 = i36;
                            i4 = 2;
                            int i49 = iArr2[2];
                            int i50 = iArr2[3];
                            long j28 = ((((((((((((j27 * i50) >>> 31) + (i49 * j27)) + (j26 * i50)) >>> 31) + (i47 * j27)) + (i49 * j26)) >>> 31) + (i46 * j27)) + (i47 * j26)) >>> 21) + ((i46 * j26) << 10)) >>> i45;
                            long j29 = j8 >>> 31;
                            long j30 = j8 & 2147483647L;
                            j2 = j28;
                            long j31 = ((((((((((((j30 * i50) >>> 31) + (i49 * j30)) + (j29 * i50)) >>> 31) + (i47 * j30)) + (i49 * j29)) >>> 31) + (i46 * j30)) + (i47 * j29)) >>> 21) + ((i46 * j29) << 10)) >>> i45;
                            long j32 = j9 >>> 31;
                            long j33 = j9 & 2147483647L;
                            j3 = j31;
                            j4 = ((((((((((((j33 * i50) >>> 31) + (i49 * j33)) + (j32 * i50)) >>> 31) + (i47 * j33)) + (i49 * j32)) >>> 31) + (i46 * j33)) + (i47 * j32)) >>> 21) + ((i46 * j32) << 10)) >>> i45;
                            i5 = iMax2 + i37;
                            boolean z9 = true;
                            if (iMax2 <= 1) {
                                if (z3) {
                                    z5 = true;
                                    z4 = i48 == 1;
                                } else {
                                    j3--;
                                    z5 = z9;
                                    z4 = false;
                                }
                            } else if (iMax2 < 63) {
                                z9 = (j7 & ((1 << (iMax2 - 1)) - 1)) == 0;
                                z5 = z9;
                                z4 = false;
                            } else {
                                z4 = false;
                                z5 = false;
                            }
                        } else {
                            throw new IllegalArgumentException("" + i45);
                        }
                    }
                    int i51 = j3 >= 1000000000000000000L ? 19 : j3 >= 100000000000000000L ? 18 : j3 >= 10000000000000000L ? 17 : j3 >= 1000000000000000L ? 16 : j3 >= 100000000000000L ? 15 : j3 >= 10000000000000L ? 14 : j3 >= MediaPeriodQueue.INITIAL_RENDERER_POSITION_OFFSET_US ? 13 : j3 >= 100000000000L ? 12 : j3 >= RealConnection.IDLE_CONNECTION_HEALTHY_NS ? 11 : j3 >= C.NANOS_PER_SECOND ? 10 : j3 >= 100000000 ? 9 : j3 >= 10000000 ? 8 : j3 >= 1000000 ? 7 : j3 >= 100000 ? 6 : j3 >= a.f7153q ? 5 : j3 >= 1000 ? 4 : j3 >= 100 ? 3 : j3 >= 10 ? i4 : 1;
                    int i52 = (i5 + i51) - 1;
                    boolean z10 = i52 < -3 || i52 >= 7;
                    if (z4 || z5) {
                        boolean z11 = z5;
                        int i53 = 0;
                        int i54 = 0;
                        while (true) {
                            long j34 = j3 / 10;
                            long j35 = j4 / 10;
                            if (j34 <= j35 || (j3 < 100 && z10)) {
                                break;
                            }
                            z4 &= j4 % 10 == 0;
                            z11 &= i53 == 0;
                            i53 = (int) (j2 % 10);
                            j2 /= 10;
                            i54++;
                            j3 = j34;
                            j4 = j35;
                        }
                        if (z4 && z3) {
                            while (j4 % 10 == 0 && (j3 >= 100 || !z10)) {
                                z11 &= i53 == 0;
                                i53 = (int) (j2 % 10);
                                j3 /= 10;
                                j2 /= 10;
                                j4 /= 10;
                                i54++;
                            }
                        }
                        if (z11 && i53 == 5 && j2 % 2 == 0) {
                            i53 = 4;
                        }
                        j5 = j2 + (((j2 != j4 || (z4 && z3)) && i53 < 5) ? 0 : 1);
                        i6 = i54;
                    } else {
                        i6 = 0;
                        int i55 = 0;
                        while (true) {
                            long j36 = j3 / 10;
                            long j37 = j4 / 10;
                            if (j36 <= j37 || (j3 < 100 && z10)) {
                                break;
                            }
                            i55 = (int) (j2 % 10);
                            j2 /= 10;
                            i6++;
                            j3 = j36;
                            j4 = j37;
                        }
                        j5 = j2 + ((j2 == j4 || i55 >= 5) ? 1 : 0);
                    }
                    int i56 = i51 - i6;
                    if (z2) {
                        i7 = i2 + 1;
                        cArr[i2] = CharPool.DASHED;
                    } else {
                        i7 = i2;
                    }
                    if (!z10) {
                        char c3 = '0';
                        if (i52 < 0) {
                            int i57 = i7 + 1;
                            cArr[i7] = '0';
                            int i58 = i57 + 1;
                            cArr[i57] = '.';
                            int i59 = -1;
                            while (i59 > i52) {
                                cArr[i58] = c3;
                                i59--;
                                i58++;
                                c3 = '0';
                            }
                            i8 = i58;
                            for (int i60 = 0; i60 < i56; i60++) {
                                cArr[((i58 + i56) - i60) - 1] = (char) ((j5 % 10) + 48);
                                j5 /= 10;
                                i8++;
                            }
                        } else {
                            int i61 = i52 + 1;
                            if (i61 >= i56) {
                                for (int i62 = 0; i62 < i56; i62++) {
                                    cArr[((i7 + i56) - i62) - 1] = (char) ((j5 % 10) + 48);
                                    j5 /= 10;
                                }
                                int i63 = i7 + i56;
                                while (i56 < i61) {
                                    cArr[i63] = '0';
                                    i56++;
                                    i63++;
                                }
                                int i64 = i63 + 1;
                                cArr[i63] = '.';
                                i8 = i64 + 1;
                                cArr[i64] = '0';
                            } else {
                                int i65 = i7 + 1;
                                for (int i66 = 0; i66 < i56; i66++) {
                                    if ((i56 - i66) - 1 == i52) {
                                        cArr[((i65 + i56) - i66) - 1] = '.';
                                        i65--;
                                    }
                                    cArr[((i65 + i56) - i66) - 1] = (char) ((j5 % 10) + 48);
                                    j5 /= 10;
                                }
                                i8 = i7 + i56 + 1;
                            }
                        }
                        return i8 - i2;
                    }
                    for (int i67 = 0; i67 < i56 - 1; i67++) {
                        int i68 = (int) (j5 % 10);
                        j5 /= 10;
                        cArr[(i7 + i56) - i67] = (char) (i68 + 48);
                    }
                    cArr[i7] = (char) ((j5 % 10) + 48);
                    cArr[i7 + 1] = '.';
                    int i69 = i7 + i56 + 1;
                    if (i56 == 1) {
                        cArr[i69] = '0';
                        i69++;
                    }
                    int i70 = i69 + 1;
                    cArr[i69] = 'E';
                    if (i52 < 0) {
                        cArr[i70] = CharPool.DASHED;
                        i52 = -i52;
                        i70++;
                    }
                    if (i52 >= 100) {
                        int i71 = i70 + 1;
                        i9 = 48;
                        cArr[i70] = (char) ((i52 / 100) + 48);
                        i52 %= 100;
                        i70 = i71 + 1;
                        cArr[i71] = (char) ((i52 / 10) + 48);
                    } else {
                        i9 = 48;
                        if (i52 >= 10) {
                            cArr[i70] = (char) ((i52 / 10) + 48);
                            i70++;
                        }
                    }
                    i10 = i70 + 1;
                    cArr[i70] = (char) ((i52 % 10) + i9);
                }
            }
            return i10 - i2;
        }
        int i72 = i2 + 1;
        cArr[i2] = 'N';
        int i73 = i72 + 1;
        cArr[i72] = 'a';
        i14 = i73 + 1;
        cArr[i73] = 'N';
        return i14 - i2;
    }
}
