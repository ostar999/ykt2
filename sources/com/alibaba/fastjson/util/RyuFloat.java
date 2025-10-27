package com.alibaba.fastjson.util;

import cn.hutool.core.text.CharPool;
import com.tencent.mm.opensdk.constants.Build;

/* loaded from: classes2.dex */
public final class RyuFloat {
    private static final int[][] POW5_SPLIT = {new int[]{536870912, 0}, new int[]{Build.SUPPORT_SEND_MUSIC_VIDEO_MESSAGE, 0}, new int[]{838860800, 0}, new int[]{1048576000, 0}, new int[]{655360000, 0}, new int[]{819200000, 0}, new int[]{1024000000, 0}, new int[]{640000000, 0}, new int[]{800000000, 0}, new int[]{1000000000, 0}, new int[]{625000000, 0}, new int[]{781250000, 0}, new int[]{976562500, 0}, new int[]{610351562, 1073741824}, new int[]{762939453, 268435456}, new int[]{953674316, 872415232}, new int[]{596046447, 1619001344}, new int[]{745058059, 1486880768}, new int[]{931322574, 1321730048}, new int[]{582076609, 289210368}, new int[]{727595761, 898383872}, new int[]{909494701, 1659850752}, new int[]{568434188, 1305842176}, new int[]{710542735, 1632302720}, new int[]{888178419, 1503507488}, new int[]{555111512, 671256724}, new int[]{693889390, 839070905}, new int[]{867361737, 2122580455}, new int[]{542101086, 521306416}, new int[]{677626357, 1725374844}, new int[]{847032947, 546105819}, new int[]{1058791184, 145761362}, new int[]{661744490, 91100851}, new int[]{827180612, 1187617888}, new int[]{1033975765, 1484522360}, new int[]{646234853, 1196261931}, new int[]{807793566, 2032198326}, new int[]{1009741958, 1466506084}, new int[]{631088724, 379695390}, new int[]{788860905, 474619238}, new int[]{986076131, 1130144959}, new int[]{616297582, 437905143}, new int[]{770371977, 1621123253}, new int[]{962964972, 415791331}, new int[]{601853107, 1333611405}, new int[]{752316384, 1130143345}, new int[]{940395480, 1412679181}};
    private static final int[][] POW5_INV_SPLIT = {new int[]{268435456, 1}, new int[]{214748364, 1717986919}, new int[]{171798691, 1803886265}, new int[]{137438953, 1013612282}, new int[]{219902325, 1192282922}, new int[]{175921860, 953826338}, new int[]{140737488, 763061070}, new int[]{225179981, 791400982}, new int[]{180143985, 203624056}, new int[]{144115188, 162899245}, new int[]{230584300, 1978625710}, new int[]{184467440, 1582900568}, new int[]{147573952, 1266320455}, new int[]{236118324, 308125809}, new int[]{188894659, 675997377}, new int[]{151115727, 970294631}, new int[]{241785163, 1981968139}, new int[]{193428131, 297084323}, new int[]{154742504, 1955654377}, new int[]{247588007, 1840556814}, new int[]{198070406, 613451992}, new int[]{158456325, 61264864}, new int[]{253530120, 98023782}, new int[]{202824096, 78419026}, new int[]{162259276, 1780722139}, new int[]{259614842, 1990161963}, new int[]{207691874, 733136111}, new int[]{166153499, 1016005619}, new int[]{265845599, 337118801}, new int[]{212676479, 699191770}, new int[]{170141183, 988850146}};

    public static String toString(float f2) {
        char[] cArr = new char[15];
        return new String(cArr, 0, toString(f2, cArr, 0));
    }

    public static int toString(float f2, char[] cArr, int i2) {
        int i3;
        boolean z2;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        if (Float.isNaN(f2)) {
            int i18 = i2 + 1;
            cArr[i2] = 'N';
            int i19 = i18 + 1;
            cArr[i18] = 'a';
            i17 = i19 + 1;
            cArr[i19] = 'N';
        } else {
            if (f2 == Float.POSITIVE_INFINITY) {
                int i20 = i2 + 1;
                cArr[i2] = 'I';
                int i21 = i20 + 1;
                cArr[i20] = 'n';
                int i22 = i21 + 1;
                cArr[i21] = 'f';
                int i23 = i22 + 1;
                cArr[i22] = 'i';
                int i24 = i23 + 1;
                cArr[i23] = 'n';
                int i25 = i24 + 1;
                cArr[i24] = 'i';
                int i26 = i25 + 1;
                cArr[i25] = 't';
                cArr[i26] = 'y';
                return (i26 + 1) - i2;
            }
            if (f2 == Float.NEGATIVE_INFINITY) {
                int i27 = i2 + 1;
                cArr[i2] = CharPool.DASHED;
                int i28 = i27 + 1;
                cArr[i27] = 'I';
                int i29 = i28 + 1;
                cArr[i28] = 'n';
                int i30 = i29 + 1;
                cArr[i29] = 'f';
                int i31 = i30 + 1;
                cArr[i30] = 'i';
                int i32 = i31 + 1;
                cArr[i31] = 'n';
                int i33 = i32 + 1;
                cArr[i32] = 'i';
                int i34 = i33 + 1;
                cArr[i33] = 't';
                i17 = i34 + 1;
                cArr[i34] = 'y';
            } else {
                int iFloatToIntBits = Float.floatToIntBits(f2);
                if (iFloatToIntBits != 0) {
                    if (iFloatToIntBits == Integer.MIN_VALUE) {
                        int i35 = i2 + 1;
                        cArr[i2] = CharPool.DASHED;
                        int i36 = i35 + 1;
                        cArr[i35] = '0';
                        int i37 = i36 + 1;
                        cArr[i36] = '.';
                        cArr[i37] = '0';
                        return (i37 + 1) - i2;
                    }
                    int i38 = (iFloatToIntBits >> 23) & 255;
                    int i39 = 8388607 & iFloatToIntBits;
                    if (i38 == 0) {
                        i3 = -149;
                    } else {
                        i3 = (i38 - 127) - 23;
                        i39 |= 8388608;
                    }
                    boolean z3 = iFloatToIntBits < 0;
                    boolean z4 = (i39 & 1) == 0;
                    int i40 = i39 * 4;
                    int i41 = i40 + 2;
                    int i42 = i40 - ((((long) i39) != 8388608 || i38 <= 1) ? 2 : 1);
                    int i43 = i3 - 2;
                    if (i43 >= 0) {
                        i7 = (int) ((i43 * 3010299) / 10000000);
                        int i44 = i7 == 0 ? 1 : (int) ((((i7 * 23219280) + 10000000) - 1) / 10000000);
                        int i45 = (-i43) + i7;
                        int[][] iArr = POW5_INV_SPLIT;
                        int[] iArr2 = iArr[i7];
                        long j2 = iArr2[0];
                        z2 = z4;
                        long j3 = iArr2[1];
                        long j4 = i40;
                        int i46 = (((i44 + 59) - 1) + i45) - 31;
                        int i47 = (int) (((j4 * j2) + ((j4 * j3) >> 31)) >> i46);
                        long j5 = i41;
                        i9 = (int) (((j5 * j2) + ((j5 * j3) >> 31)) >> i46);
                        int i48 = i42;
                        long j6 = i48;
                        int i49 = (int) (((j2 * j6) + ((j6 * j3) >> 31)) >> i46);
                        if (i7 == 0 || (i9 - 1) / 10 > i49 / 10) {
                            i13 = 0;
                        } else {
                            int i50 = i7 - 1;
                            int i51 = (i45 - 1) + (((i50 == 0 ? 1 : (int) ((((i50 * 23219280) + 10000000) - 1) / 10000000)) + 59) - 1);
                            int[] iArr3 = iArr[i50];
                            i13 = (int) ((((iArr3[0] * j4) + ((iArr3[1] * j4) >> 31)) >> (i51 - 31)) % 10);
                        }
                        int i52 = 0;
                        while (i41 > 0 && i41 % 5 == 0) {
                            i41 /= 5;
                            i52++;
                        }
                        int i53 = 0;
                        while (i40 > 0 && i40 % 5 == 0) {
                            i40 /= 5;
                            i53++;
                        }
                        int i54 = 0;
                        while (i48 > 0 && i48 % 5 == 0) {
                            i48 /= 5;
                            i54++;
                        }
                        int i55 = i52 >= i7 ? 1 : 0;
                        i10 = i53 >= i7 ? 1 : 0;
                        i12 = i54 >= i7 ? 1 : 0;
                        i8 = i55;
                        i5 = 0;
                        i4 = i49;
                        i11 = i47;
                    } else {
                        z2 = z4;
                        int i56 = -i43;
                        int i57 = (int) ((i56 * 6989700) / 10000000);
                        int i58 = i56 - i57;
                        int i59 = i58 == 0 ? 1 : (int) ((((i58 * 23219280) + 10000000) - 1) / 10000000);
                        int[][] iArr4 = POW5_SPLIT;
                        int[] iArr5 = iArr4[i58];
                        long j7 = iArr5[0];
                        long j8 = iArr5[1];
                        int i60 = (i57 - (i59 - 61)) - 31;
                        long j9 = i40;
                        int i61 = (int) (((j9 * j7) + ((j9 * j8) >> 31)) >> i60);
                        long j10 = i41;
                        int i62 = (int) (((j10 * j7) + ((j10 * j8) >> 31)) >> i60);
                        long j11 = i42;
                        i4 = (int) (((j7 * j11) + ((j11 * j8) >> 31)) >> i60);
                        if (i57 == 0 || (i62 - 1) / 10 > i4 / 10) {
                            i5 = 0;
                            i6 = 0;
                        } else {
                            int i63 = i58 + 1;
                            int i64 = (i57 - 1) - ((i63 == 0 ? 1 : (int) ((((i63 * 23219280) + 10000000) - 1) / 10000000)) - 61);
                            int[] iArr6 = iArr4[i63];
                            i5 = 0;
                            i6 = (int) ((((j9 * iArr6[0]) + ((iArr6[1] * j9) >> 31)) >> (i64 - 31)) % 10);
                        }
                        i7 = i57 + i43;
                        i8 = 1 >= i57 ? 1 : i5;
                        int i65 = (i57 >= 23 || (i40 & ((1 << (i57 + (-1))) - 1)) != 0) ? i5 : 1;
                        int i66 = (i42 % 2 == 1 ? i5 : 1) >= i57 ? 1 : i5;
                        i9 = i62;
                        i10 = i65;
                        i11 = i61;
                        int i67 = i6;
                        i12 = i66;
                        i13 = i67;
                    }
                    int i68 = 1000000000;
                    int i69 = 10;
                    while (i69 > 0 && i9 < i68) {
                        i68 /= 10;
                        i69--;
                    }
                    int i70 = (i7 + i69) - 1;
                    int i71 = (i70 < -3 || i70 >= 7) ? 1 : i5;
                    if (i8 != 0 && !z2) {
                        i9--;
                    }
                    int i72 = i5;
                    while (true) {
                        int i73 = i9 / 10;
                        int i74 = i4 / 10;
                        if (i73 <= i74 || (i9 < 100 && i71 != 0)) {
                            break;
                        }
                        i12 &= i4 % 10 == 0 ? 1 : i5;
                        i13 = i11 % 10;
                        i11 /= 10;
                        i72++;
                        i9 = i73;
                        i4 = i74;
                    }
                    if (i12 != 0 && z2) {
                        while (i4 % 10 == 0 && (i9 >= 100 || i71 == 0)) {
                            i9 /= 10;
                            i13 = i11 % 10;
                            i11 /= 10;
                            i4 /= 10;
                            i72++;
                        }
                    }
                    if (i10 != 0 && i13 == 5 && i11 % 2 == 0) {
                        i13 = 4;
                    }
                    int i75 = i11 + (((i11 != i4 || (i12 != 0 && z2)) && i13 < 5) ? i5 : 1);
                    int i76 = i69 - i72;
                    if (z3) {
                        i14 = i2 + 1;
                        cArr[i2] = CharPool.DASHED;
                    } else {
                        i14 = i2;
                    }
                    if (i71 != 0) {
                        for (int i77 = i5; i77 < i76 - 1; i77++) {
                            int i78 = i75 % 10;
                            i75 /= 10;
                            cArr[(i14 + i76) - i77] = (char) (i78 + 48);
                        }
                        cArr[i14] = (char) ((i75 % 10) + 48);
                        cArr[i14 + 1] = '.';
                        int i79 = i14 + i76 + 1;
                        if (i76 == 1) {
                            cArr[i79] = '0';
                            i79++;
                        }
                        int i80 = i79 + 1;
                        cArr[i79] = 'E';
                        if (i70 < 0) {
                            cArr[i80] = CharPool.DASHED;
                            i70 = -i70;
                            i80++;
                        }
                        if (i70 >= 10) {
                            i16 = 48;
                            cArr[i80] = (char) ((i70 / 10) + 48);
                            i80++;
                        } else {
                            i16 = 48;
                        }
                        i15 = i80 + 1;
                        cArr[i80] = (char) ((i70 % 10) + i16);
                    } else {
                        int i81 = 48;
                        if (i70 < 0) {
                            int i82 = i14 + 1;
                            cArr[i14] = '0';
                            int i83 = i82 + 1;
                            cArr[i82] = '.';
                            int i84 = -1;
                            while (i84 > i70) {
                                cArr[i83] = '0';
                                i84--;
                                i83++;
                            }
                            int i85 = i83;
                            int i86 = i5;
                            while (i86 < i76) {
                                cArr[((i83 + i76) - i86) - 1] = (char) ((i75 % 10) + i81);
                                i75 /= 10;
                                i85++;
                                i86++;
                                i81 = 48;
                            }
                            i15 = i85;
                        } else {
                            int i87 = i70 + 1;
                            if (i87 >= i76) {
                                for (int i88 = i5; i88 < i76; i88++) {
                                    cArr[((i14 + i76) - i88) - 1] = (char) ((i75 % 10) + 48);
                                    i75 /= 10;
                                }
                                int i89 = i14 + i76;
                                while (i76 < i87) {
                                    cArr[i89] = '0';
                                    i76++;
                                    i89++;
                                }
                                int i90 = i89 + 1;
                                cArr[i89] = '.';
                                i15 = i90 + 1;
                                cArr[i90] = '0';
                            } else {
                                int i91 = i14 + 1;
                                for (int i92 = i5; i92 < i76; i92++) {
                                    if ((i76 - i92) - 1 == i70) {
                                        cArr[((i91 + i76) - i92) - 1] = '.';
                                        i91--;
                                    }
                                    cArr[((i91 + i76) - i92) - 1] = (char) ((i75 % 10) + 48);
                                    i75 /= 10;
                                }
                                i15 = i14 + i76 + 1;
                            }
                        }
                    }
                    return i15 - i2;
                }
                int i93 = i2 + 1;
                cArr[i2] = '0';
                int i94 = i93 + 1;
                cArr[i93] = '.';
                i17 = i94 + 1;
                cArr[i94] = '0';
            }
        }
        return i17 - i2;
    }
}
