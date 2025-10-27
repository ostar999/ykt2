package org.apache.commons.compress.compressors.bzip2;

import com.yikaobang.yixue.R2;
import java.util.BitSet;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;

/* loaded from: classes9.dex */
class BlockSort {
    private static final int CLEARMASK = -2097153;
    private static final int DEPTH_THRESH = 10;
    private static final int FALLBACK_QSORT_SMALL_THRESH = 10;
    private static final int FALLBACK_QSORT_STACK_SIZE = 100;
    private static final int[] INCS = {1, 4, 13, 40, 121, R2.attr.arcShowLabel, R2.attr.combine_guide_text_color, R2.attr.srlDrawableProgress, R2.drawable.gufen_suo, R2.styleable.RRadioButton_icon_width_right, 88573, 265720, 797161, 2391484};
    private static final int QSORT_STACK_SIZE = 1000;
    private static final int SETMASK = 2097152;
    private static final int SMALL_THRESH = 20;
    private static final int STACK_SIZE = 1000;
    private static final int WORK_FACTOR = 30;
    private int[] eclass;
    private boolean firstAttempt;
    private final char[] quadrant;
    private int workDone;
    private int workLimit;
    private final int[] stack_ll = new int[1000];
    private final int[] stack_hh = new int[1000];
    private final int[] stack_dd = new int[1000];
    private final int[] mainSort_runningOrder = new int[256];
    private final int[] mainSort_copy = new int[256];
    private final boolean[] mainSort_bigDone = new boolean[256];
    private final int[] ftab = new int[65537];

    public BlockSort(BZip2CompressorOutputStream.Data data) {
        this.quadrant = data.sfmap;
    }

    private void fallbackQSort3(int[] iArr, int[] iArr2, int i2, int i3) {
        int i4;
        char c3 = 0;
        fpush(0, i2, i3);
        long j2 = 0;
        int i5 = 1;
        long j3 = 0;
        int i6 = 1;
        while (i6 > 0) {
            i6--;
            int[] iArrFpop = fpop(i6);
            int i7 = iArrFpop[c3];
            int i8 = iArrFpop[i5];
            if (i8 - i7 < 10) {
                fallbackSimpleSort(iArr, iArr2, i7, i8);
            } else {
                j3 = ((j3 * 7621) + 1) % 32768;
                long j4 = j3 % 3;
                long j5 = j4 == j2 ? iArr2[iArr[i7]] : j4 == 1 ? iArr2[iArr[(i7 + i8) >>> i5]] : iArr2[iArr[i8]];
                int i9 = i8;
                int i10 = i9;
                int i11 = i7;
                int i12 = i11;
                while (true) {
                    if (i12 <= i9) {
                        int i13 = iArr2[iArr[i12]] - ((int) j5);
                        if (i13 == 0) {
                            fswap(iArr, i12, i11);
                            i11++;
                        } else if (i13 <= 0) {
                        }
                        i12++;
                    }
                    i4 = i10;
                    while (i12 <= i9) {
                        int i14 = iArr2[iArr[i9]] - ((int) j5);
                        if (i14 == 0) {
                            fswap(iArr, i9, i4);
                            i4--;
                            i9--;
                        } else if (i14 < 0) {
                            break;
                        } else {
                            i9--;
                        }
                    }
                    if (i12 > i9) {
                        break;
                    }
                    fswap(iArr, i12, i9);
                    i12++;
                    i9--;
                    i10 = i4;
                    i5 = 1;
                }
                if (i4 < i11) {
                    c3 = 0;
                    j2 = 0;
                    i5 = 1;
                } else {
                    int iFmin = fmin(i11 - i7, i12 - i11);
                    fvswap(iArr, i7, i12 - iFmin, iFmin);
                    int i15 = i8 - i4;
                    int i16 = i4 - i9;
                    int iFmin2 = fmin(i15, i16);
                    fvswap(iArr, i9 + 1, (i8 - iFmin2) + 1, iFmin2);
                    int i17 = ((i12 + i7) - i11) - 1;
                    int i18 = (i8 - i16) + 1;
                    if (i17 - i7 > i8 - i18) {
                        int i19 = i6 + 1;
                        fpush(i6, i7, i17);
                        fpush(i19, i18, i8);
                        i6 = i19 + 1;
                    } else {
                        int i20 = i6 + 1;
                        fpush(i6, i18, i8);
                        fpush(i20, i7, i17);
                        i6 = i20 + 1;
                    }
                    i5 = 1;
                    c3 = 0;
                    j2 = 0;
                }
            }
        }
    }

    private void fallbackSimpleSort(int[] iArr, int[] iArr2, int i2, int i3) {
        if (i2 == i3) {
            return;
        }
        if (i3 - i2 > 3) {
            for (int i4 = i3 - 4; i4 >= i2; i4--) {
                int i5 = iArr[i4];
                int i6 = iArr2[i5];
                int i7 = i4 + 4;
                while (i7 <= i3) {
                    int i8 = iArr[i7];
                    if (i6 > iArr2[i8]) {
                        iArr[i7 - 4] = i8;
                        i7 += 4;
                    }
                }
                iArr[i7 - 4] = i5;
            }
        }
        for (int i9 = i3 - 1; i9 >= i2; i9--) {
            int i10 = iArr[i9];
            int i11 = iArr2[i10];
            int i12 = i9 + 1;
            while (i12 <= i3) {
                int i13 = iArr[i12];
                if (i11 > iArr2[i13]) {
                    iArr[i12 - 1] = i13;
                    i12++;
                }
            }
            iArr[i12 - 1] = i10;
        }
    }

    private int fmin(int i2, int i3) {
        return i2 < i3 ? i2 : i3;
    }

    private int[] fpop(int i2) {
        return new int[]{this.stack_ll[i2], this.stack_hh[i2]};
    }

    private void fpush(int i2, int i3, int i4) {
        this.stack_ll[i2] = i3;
        this.stack_hh[i2] = i4;
    }

    private void fswap(int[] iArr, int i2, int i3) {
        int i4 = iArr[i2];
        iArr[i2] = iArr[i3];
        iArr[i3] = i4;
    }

    private void fvswap(int[] iArr, int i2, int i3, int i4) {
        while (i4 > 0) {
            fswap(iArr, i2, i3);
            i2++;
            i3++;
            i4--;
        }
    }

    private int[] getEclass() {
        int[] iArr = this.eclass;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[this.quadrant.length / 2];
        this.eclass = iArr2;
        return iArr2;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x006b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void mainQSort3(org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream.Data r20, int r21, int r22, int r23, int r24) {
        /*
            Method dump skipped, instructions count: 261
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.compress.compressors.bzip2.BlockSort.mainQSort3(org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream$Data, int, int, int, int):void");
    }

    private boolean mainSimpleSort(BZip2CompressorOutputStream.Data data, int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11 = (i3 - i2) + 1;
        if (i11 < 2) {
            return this.firstAttempt && this.workDone > this.workLimit;
        }
        int i12 = 0;
        while (INCS[i12] < i11) {
            i12++;
        }
        int[] iArr = data.fmap;
        char[] cArr = this.quadrant;
        byte[] bArr = data.block;
        int i13 = i5 + 1;
        boolean z2 = this.firstAttempt;
        int i14 = this.workLimit;
        int i15 = this.workDone;
        loop1: while (true) {
            i12--;
            if (i12 < 0) {
                break;
            }
            int i16 = INCS[i12];
            int i17 = i2 + i16;
            int i18 = i17 - 1;
            while (i17 <= i3) {
                int i19 = 3;
                while (i17 <= i3) {
                    int i20 = i19 - 1;
                    if (i20 < 0) {
                        break;
                    }
                    int i21 = iArr[i17];
                    int i22 = i21 + i4;
                    int i23 = i17;
                    boolean z3 = false;
                    int i24 = 0;
                    while (true) {
                        if (z3) {
                            iArr[i23] = i24;
                            i10 = i23 - i16;
                            if (i10 <= i18) {
                                i9 = i12;
                                i7 = i16;
                                i6 = i18;
                                i8 = i20;
                                break;
                            }
                            i23 = i10;
                        } else {
                            z3 = true;
                        }
                        int i25 = iArr[i23 - i16];
                        int i26 = i25 + i4;
                        byte b3 = bArr[i26 + 1];
                        byte b4 = bArr[i22 + 1];
                        if (b3 != b4) {
                            i9 = i12;
                            i7 = i16;
                            i6 = i18;
                            i8 = i20;
                            if ((b3 & 255) <= (b4 & 255)) {
                                break;
                            }
                            i24 = i25;
                            i12 = i9;
                            i20 = i8;
                            i16 = i7;
                            i18 = i6;
                        } else {
                            byte b5 = bArr[i26 + 2];
                            byte b6 = bArr[i22 + 2];
                            if (b5 != b6) {
                                i9 = i12;
                                i7 = i16;
                                i6 = i18;
                                i8 = i20;
                                if ((b5 & 255) <= (b6 & 255)) {
                                    break;
                                }
                                i24 = i25;
                                i12 = i9;
                                i20 = i8;
                                i16 = i7;
                                i18 = i6;
                            } else {
                                byte b7 = bArr[i26 + 3];
                                byte b8 = bArr[i22 + 3];
                                if (b7 != b8) {
                                    i9 = i12;
                                    i7 = i16;
                                    i6 = i18;
                                    i8 = i20;
                                    if ((b7 & 255) <= (b8 & 255)) {
                                        break;
                                    }
                                    i24 = i25;
                                    i12 = i9;
                                    i20 = i8;
                                    i16 = i7;
                                    i18 = i6;
                                } else {
                                    byte b9 = bArr[i26 + 4];
                                    byte b10 = bArr[i22 + 4];
                                    if (b9 != b10) {
                                        i9 = i12;
                                        i7 = i16;
                                        i6 = i18;
                                        i8 = i20;
                                        if ((b9 & 255) <= (b10 & 255)) {
                                            break;
                                        }
                                        i24 = i25;
                                        i12 = i9;
                                        i20 = i8;
                                        i16 = i7;
                                        i18 = i6;
                                    } else {
                                        byte b11 = bArr[i26 + 5];
                                        byte b12 = bArr[i22 + 5];
                                        if (b11 != b12) {
                                            i9 = i12;
                                            i7 = i16;
                                            i6 = i18;
                                            i8 = i20;
                                            if ((b11 & 255) <= (b12 & 255)) {
                                                break;
                                            }
                                            i24 = i25;
                                            i12 = i9;
                                            i20 = i8;
                                            i16 = i7;
                                            i18 = i6;
                                        } else {
                                            int i27 = i26 + 6;
                                            byte b13 = bArr[i27];
                                            int i28 = i22 + 6;
                                            i9 = i12;
                                            byte b14 = bArr[i28];
                                            if (b13 != b14) {
                                                i7 = i16;
                                                i6 = i18;
                                                i8 = i20;
                                                if ((b13 & 255) <= (b14 & 255)) {
                                                    break;
                                                }
                                                i24 = i25;
                                                i12 = i9;
                                                i20 = i8;
                                                i16 = i7;
                                                i18 = i6;
                                            } else {
                                                int i29 = i5;
                                                while (true) {
                                                    if (i29 <= 0) {
                                                        i7 = i16;
                                                        i6 = i18;
                                                        i8 = i20;
                                                        break;
                                                    }
                                                    int i30 = i29 - 4;
                                                    int i31 = i27 + 1;
                                                    byte b15 = bArr[i31];
                                                    int i32 = i28 + 1;
                                                    i7 = i16;
                                                    byte b16 = bArr[i32];
                                                    if (b15 != b16) {
                                                        i6 = i18;
                                                        i8 = i20;
                                                        if ((b15 & 255) <= (b16 & 255)) {
                                                            break;
                                                        }
                                                    } else {
                                                        char c3 = cArr[i27];
                                                        char c4 = cArr[i28];
                                                        if (c3 != c4) {
                                                            i6 = i18;
                                                            i8 = i20;
                                                            if (c3 <= c4) {
                                                                break;
                                                            }
                                                        } else {
                                                            int i33 = i27 + 2;
                                                            byte b17 = bArr[i33];
                                                            int i34 = i28 + 2;
                                                            i6 = i18;
                                                            byte b18 = bArr[i34];
                                                            if (b17 != b18) {
                                                                i8 = i20;
                                                                if ((b17 & 255) <= (b18 & 255)) {
                                                                    break;
                                                                }
                                                            } else {
                                                                char c5 = cArr[i31];
                                                                char c6 = cArr[i32];
                                                                if (c5 != c6) {
                                                                    i8 = i20;
                                                                    if (c5 <= c6) {
                                                                        break;
                                                                    }
                                                                } else {
                                                                    int i35 = i27 + 3;
                                                                    byte b19 = bArr[i35];
                                                                    int i36 = i28 + 3;
                                                                    i8 = i20;
                                                                    byte b20 = bArr[i36];
                                                                    if (b19 != b20) {
                                                                        if ((b19 & 255) <= (b20 & 255)) {
                                                                            break;
                                                                        }
                                                                    } else {
                                                                        char c7 = cArr[i33];
                                                                        char c8 = cArr[i34];
                                                                        if (c7 != c8) {
                                                                            if (c7 <= c8) {
                                                                                break;
                                                                            }
                                                                        } else {
                                                                            int i37 = i27 + 4;
                                                                            byte b21 = bArr[i37];
                                                                            i28 += 4;
                                                                            byte b22 = bArr[i28];
                                                                            if (b21 != b22) {
                                                                                if ((b21 & 255) <= (b22 & 255)) {
                                                                                    break;
                                                                                }
                                                                            } else {
                                                                                char c9 = cArr[i35];
                                                                                char c10 = cArr[i36];
                                                                                if (c9 != c10) {
                                                                                    if (c9 <= c10) {
                                                                                        break;
                                                                                    }
                                                                                } else {
                                                                                    if (i37 >= i13) {
                                                                                        i37 -= i13;
                                                                                    }
                                                                                    i27 = i37;
                                                                                    if (i28 >= i13) {
                                                                                        i28 -= i13;
                                                                                    }
                                                                                    i15++;
                                                                                    i29 = i30;
                                                                                    i20 = i8;
                                                                                    i16 = i7;
                                                                                    i18 = i6;
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                i24 = i25;
                                                i12 = i9;
                                                i20 = i8;
                                                i16 = i7;
                                                i18 = i6;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    i10 = i23;
                    iArr[i10] = i21;
                    i17++;
                    i12 = i9;
                    i19 = i8;
                    i16 = i7;
                    i18 = i6;
                }
                int i38 = i12;
                int i39 = i16;
                int i40 = i18;
                if (z2 && i17 <= i3 && i15 > i14) {
                    break loop1;
                }
                i12 = i38;
                i16 = i39;
                i18 = i40;
            }
        }
        this.workDone = i15;
        return z2 && i15 > i14;
    }

    private static byte med3(byte b3, byte b4, byte b5) {
        if (b3 < b4) {
            if (b4 >= b5) {
                if (b3 >= b5) {
                    return b3;
                }
                return b5;
            }
            return b4;
        }
        if (b4 <= b5) {
            if (b3 <= b5) {
                return b3;
            }
            return b5;
        }
        return b4;
    }

    private static void vswap(int[] iArr, int i2, int i3, int i4) {
        int i5 = i4 + i2;
        while (i2 < i5) {
            int i6 = iArr[i2];
            iArr[i2] = iArr[i3];
            iArr[i3] = i6;
            i3++;
            i2++;
        }
    }

    public void blockSort(BZip2CompressorOutputStream.Data data, int i2) {
        this.workLimit = i2 * 30;
        this.workDone = 0;
        this.firstAttempt = true;
        if (i2 + 1 < 10000) {
            fallbackSort(data, i2);
        } else {
            mainSort(data, i2);
            if (this.firstAttempt && this.workDone > this.workLimit) {
                fallbackSort(data, i2);
            }
        }
        int[] iArr = data.fmap;
        data.origPtr = -1;
        for (int i3 = 0; i3 <= i2; i3++) {
            if (iArr[i3] == 0) {
                data.origPtr = i3;
                return;
            }
        }
    }

    public final void fallbackSort(BZip2CompressorOutputStream.Data data, int i2) {
        byte[] bArr = data.block;
        int i3 = i2 + 1;
        bArr[0] = bArr[i3];
        fallbackSort(data.fmap, bArr, i3);
        for (int i4 = 0; i4 < i3; i4++) {
            data.fmap[i4] = r2[i4] - 1;
        }
        for (int i5 = 0; i5 < i3; i5++) {
            int[] iArr = data.fmap;
            if (iArr[i5] == -1) {
                iArr[i5] = i2;
                return;
            }
        }
    }

    public final void mainSort(BZip2CompressorOutputStream.Data data, int i2) {
        int i3;
        int i4;
        int[] iArr;
        int i5;
        int i6;
        int i7;
        int[] iArr2 = this.mainSort_runningOrder;
        int[] iArr3 = this.mainSort_copy;
        boolean[] zArr = this.mainSort_bigDone;
        int[] iArr4 = this.ftab;
        byte[] bArr = data.block;
        int[] iArr5 = data.fmap;
        char[] cArr = this.quadrant;
        int i8 = this.workLimit;
        boolean z2 = this.firstAttempt;
        int i9 = 65537;
        while (true) {
            i9--;
            if (i9 < 0) {
                break;
            } else {
                iArr4[i9] = 0;
            }
        }
        for (int i10 = 0; i10 < 20; i10++) {
            bArr[i2 + i10 + 2] = bArr[(i10 % (i2 + 1)) + 1];
        }
        int i11 = i2 + 20 + 1;
        while (true) {
            i11--;
            if (i11 < 0) {
                break;
            } else {
                cArr[i11] = 0;
            }
        }
        int i12 = i2 + 1;
        byte b3 = bArr[i12];
        bArr[0] = b3;
        int i13 = 255;
        int i14 = b3 & 255;
        int i15 = 0;
        while (i15 <= i2) {
            i15++;
            int i16 = bArr[i15] & 255;
            int i17 = (i14 << 8) + i16;
            iArr4[i17] = iArr4[i17] + 1;
            i14 = i16;
        }
        for (int i18 = 1; i18 <= 65536; i18++) {
            iArr4[i18] = iArr4[i18] + iArr4[i18 - 1];
        }
        boolean z3 = true;
        int i19 = bArr[1] & 255;
        int i20 = 0;
        while (i20 < i2) {
            int i21 = bArr[i20 + 2] & 255;
            int i22 = (i19 << 8) + i21;
            int i23 = iArr4[i22] - 1;
            iArr4[i22] = i23;
            iArr5[i23] = i20;
            i20++;
            i19 = i21;
            z3 = true;
        }
        int i24 = ((bArr[i12] & 255) << 8) + (bArr[z3 ? 1 : 0] & 255);
        int i25 = iArr4[i24] - 1;
        iArr4[i24] = i25;
        iArr5[i25] = i2;
        int i26 = 256;
        while (true) {
            i26--;
            if (i26 < 0) {
                break;
            }
            zArr[i26] = false;
            iArr2[i26] = i26;
        }
        int i27 = R2.attr.arcShowLabel;
        while (i27 != 1) {
            i27 /= 3;
            int i28 = i27;
            while (i28 <= i13) {
                int i29 = iArr2[i28];
                int i30 = iArr4[(i29 + 1) << 8] - iArr4[i29 << 8];
                int i31 = i27 - 1;
                int i32 = iArr2[i28 - i27];
                int i33 = i28;
                while (true) {
                    i7 = i8;
                    if (iArr4[(i32 + 1) << 8] - iArr4[i32 << 8] <= i30) {
                        break;
                    }
                    iArr2[i33] = i32;
                    int i34 = i33 - i27;
                    if (i34 <= i31) {
                        i33 = i34;
                        break;
                    } else {
                        i32 = iArr2[i34 - i27];
                        i33 = i34;
                        i8 = i7;
                    }
                }
                iArr2[i33] = i29;
                i28++;
                i8 = i7;
                i13 = 255;
            }
        }
        int i35 = i8;
        int i36 = 0;
        while (i36 <= i13) {
            int i37 = iArr2[i36];
            int i38 = 0;
            while (i38 <= i13) {
                int i39 = (i37 << 8) + i38;
                int i40 = iArr4[i39];
                if ((i40 & 2097152) != 2097152) {
                    int i41 = i40 & CLEARMASK;
                    int i42 = (iArr4[i39 + 1] & CLEARMASK) - 1;
                    if (i42 > i41) {
                        i6 = 2097152;
                        i3 = i38;
                        i4 = i35;
                        iArr = iArr2;
                        i5 = i36;
                        mainQSort3(data, i41, i42, 2, i2);
                        if (z2 && this.workDone > i4) {
                            return;
                        }
                    } else {
                        i3 = i38;
                        i4 = i35;
                        i6 = 2097152;
                        iArr = iArr2;
                        i5 = i36;
                    }
                    iArr4[i39] = i40 | i6;
                } else {
                    i3 = i38;
                    i4 = i35;
                    iArr = iArr2;
                    i5 = i36;
                }
                i38 = i3 + 1;
                i36 = i5;
                iArr2 = iArr;
                i13 = 255;
                i35 = i4;
            }
            int i43 = i35;
            int[] iArr6 = iArr2;
            int i44 = i36;
            int i45 = 0;
            for (int i46 = i13; i45 <= i46; i46 = 255) {
                iArr3[i45] = iArr4[(i45 << 8) + i37] & CLEARMASK;
                i45++;
            }
            int i47 = i37 << 8;
            int i48 = iArr4[i47] & CLEARMASK;
            int i49 = (i37 + 1) << 8;
            int i50 = iArr4[i49] & CLEARMASK;
            while (i48 < i50) {
                int i51 = iArr5[i48];
                int i52 = i50;
                int i53 = bArr[i51] & 255;
                if (!zArr[i53]) {
                    iArr5[iArr3[i53]] = i51 == 0 ? i2 : i51 - 1;
                    iArr3[i53] = iArr3[i53] + 1;
                }
                i48++;
                i50 = i52;
            }
            int i54 = 256;
            while (true) {
                i54--;
                if (i54 < 0) {
                    break;
                }
                int i55 = (i54 << 8) + i37;
                iArr4[i55] = iArr4[i55] | 2097152;
            }
            zArr[i37] = true;
            if (i44 < 255) {
                int i56 = iArr4[i47] & CLEARMASK;
                int i57 = (CLEARMASK & iArr4[i49]) - i56;
                int i58 = 0;
                while ((i57 >> i58) > 65534) {
                    i58++;
                }
                int i59 = 0;
                while (i59 < i57) {
                    int i60 = iArr5[i56 + i59];
                    char c3 = (char) (i59 >> i58);
                    cArr[i60] = c3;
                    int i61 = i56;
                    if (i60 < 20) {
                        cArr[i60 + i2 + 1] = c3;
                    }
                    i59++;
                    i56 = i61;
                }
            }
            i36 = i44 + 1;
            iArr2 = iArr6;
            i13 = 255;
            i35 = i43;
        }
    }

    public final void fallbackSort(int[] iArr, byte[] bArr, int i2) {
        int i3;
        int[] iArr2 = new int[257];
        int[] eclass = getEclass();
        for (int i4 = 0; i4 < i2; i4++) {
            eclass[i4] = 0;
        }
        for (int i5 = 0; i5 < i2; i5++) {
            int i6 = bArr[i5] & 255;
            iArr2[i6] = iArr2[i6] + 1;
        }
        for (int i7 = 1; i7 < 257; i7++) {
            iArr2[i7] = iArr2[i7] + iArr2[i7 - 1];
        }
        for (int i8 = 0; i8 < i2; i8++) {
            int i9 = bArr[i8] & 255;
            int i10 = iArr2[i9] - 1;
            iArr2[i9] = i10;
            iArr[i10] = i8;
        }
        BitSet bitSet = new BitSet(i2 + 64);
        for (int i11 = 0; i11 < 256; i11++) {
            bitSet.set(iArr2[i11]);
        }
        for (int i12 = 0; i12 < 32; i12++) {
            int i13 = (i12 * 2) + i2;
            bitSet.set(i13);
            bitSet.clear(i13 + 1);
        }
        int i14 = 1;
        do {
            int i15 = 0;
            for (int i16 = 0; i16 < i2; i16++) {
                if (bitSet.get(i16)) {
                    i15 = i16;
                }
                int i17 = iArr[i16] - i14;
                if (i17 < 0) {
                    i17 += i2;
                }
                eclass[i17] = i15;
            }
            int iNextSetBit = -1;
            i3 = 0;
            while (true) {
                int iNextClearBit = bitSet.nextClearBit(iNextSetBit + 1);
                int i18 = iNextClearBit - 1;
                if (i18 >= i2 || (iNextSetBit = bitSet.nextSetBit(iNextClearBit + 1) - 1) >= i2) {
                    break;
                }
                if (iNextSetBit > i18) {
                    i3 += (iNextSetBit - i18) + 1;
                    fallbackQSort3(iArr, eclass, i18, iNextSetBit);
                    int i19 = -1;
                    while (i18 <= iNextSetBit) {
                        int i20 = eclass[iArr[i18]];
                        if (i19 != i20) {
                            bitSet.set(i18);
                            i19 = i20;
                        }
                        i18++;
                    }
                }
            }
            i14 *= 2;
            if (i14 > i2) {
                return;
            }
        } while (i3 != 0);
    }
}
