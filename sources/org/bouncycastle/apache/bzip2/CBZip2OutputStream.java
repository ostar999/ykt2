package org.bouncycastle.apache.bzip2;

import androidx.core.view.InputDeviceCompat;
import com.yikaobang.yixue.R2;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;

/* loaded from: classes9.dex */
public class CBZip2OutputStream extends OutputStream implements BZip2Constants {
    protected static final int CLEARMASK = -2097153;
    protected static final int DEPTH_THRESH = 10;
    protected static final int GREATER_ICOST = 15;
    protected static final int LESSER_ICOST = 0;
    protected static final int QSORT_STACK_SIZE = 1000;
    protected static final int SETMASK = 2097152;
    protected static final int SMALL_THRESH = 20;
    private int allowableBlockSize;
    private char[] block;
    private int blockCRC;
    boolean blockRandomised;
    int blockSize100k;
    int bsBuff;
    int bsLive;
    private OutputStream bsStream;
    int bytesOut;
    boolean closed;
    private int combinedCRC;
    private int currentChar;
    private boolean finished;
    private boolean firstAttempt;
    private int[] ftab;
    private boolean[] inUse;
    private int[] incs;
    int last;
    CRC mCrc;
    private int[] mtfFreq;
    private int nBlocksRandomised;
    private int nInUse;
    private int nMTF;
    int origPtr;
    private int[] quadrant;
    private int runLength;
    private char[] selector;
    private char[] selectorMtf;
    private char[] seqToUnseq;
    private short[] szptr;
    private char[] unseqToSeq;
    private int workDone;
    private int workFactor;
    private int workLimit;
    private int[] zptr;

    public static class StackElem {
        int dd;
        int hh;
        int ll;

        private StackElem() {
        }
    }

    public CBZip2OutputStream(OutputStream outputStream) throws IOException {
        this(outputStream, 9);
    }

    public CBZip2OutputStream(OutputStream outputStream, int i2) throws IOException {
        this.mCrc = new CRC();
        this.inUse = new boolean[256];
        this.seqToUnseq = new char[256];
        this.unseqToSeq = new char[256];
        this.selector = new char[18002];
        this.selectorMtf = new char[18002];
        this.mtfFreq = new int[258];
        this.currentChar = -1;
        this.runLength = 0;
        this.closed = false;
        this.incs = new int[]{1, 4, 13, 40, 121, R2.attr.arcShowLabel, R2.attr.combine_guide_text_color, R2.attr.srlDrawableProgress, R2.drawable.gufen_suo, R2.styleable.RRadioButton_icon_width_right, 88573, 265720, 797161, 2391484};
        this.block = null;
        this.quadrant = null;
        this.zptr = null;
        this.ftab = null;
        outputStream.write(66);
        outputStream.write(90);
        bsSetStream(outputStream);
        this.workFactor = 50;
        i2 = i2 > 9 ? 9 : i2;
        this.blockSize100k = i2 < 1 ? 1 : i2;
        allocateCompressStructures();
        initialize();
        initBlock();
    }

    private void allocateCompressStructures() {
        int i2 = this.blockSize100k * 100000;
        this.block = new char[i2 + 1 + 20];
        this.quadrant = new int[i2 + 20];
        this.zptr = new int[i2];
        this.ftab = new int[65537];
        this.szptr = new short[i2 * 2];
    }

    private void bsFinishedWithStream() throws IOException {
        while (this.bsLive > 0) {
            try {
                this.bsStream.write(this.bsBuff >> 24);
                this.bsBuff <<= 8;
                this.bsLive -= 8;
                this.bytesOut++;
            } catch (IOException e2) {
                throw e2;
            }
        }
    }

    private void bsPutIntVS(int i2, int i3) throws IOException {
        bsW(i2, i3);
    }

    private void bsPutUChar(int i2) throws IOException {
        bsW(8, i2);
    }

    private void bsPutint(int i2) throws IOException {
        bsW(8, (i2 >> 24) & 255);
        bsW(8, (i2 >> 16) & 255);
        bsW(8, (i2 >> 8) & 255);
        bsW(8, i2 & 255);
    }

    private void bsSetStream(OutputStream outputStream) {
        this.bsStream = outputStream;
        this.bsLive = 0;
        this.bsBuff = 0;
        this.bytesOut = 0;
    }

    private void bsW(int i2, int i3) throws IOException {
        while (true) {
            int i4 = this.bsLive;
            if (i4 < 8) {
                this.bsBuff = (i3 << ((32 - i4) - i2)) | this.bsBuff;
                this.bsLive = i4 + i2;
                return;
            } else {
                try {
                    this.bsStream.write(this.bsBuff >> 24);
                    this.bsBuff <<= 8;
                    this.bsLive -= 8;
                    this.bytesOut++;
                } catch (IOException e2) {
                    throw e2;
                }
            }
        }
    }

    private void doReversibleTransformation() {
        this.workLimit = this.workFactor * this.last;
        int i2 = 0;
        this.workDone = 0;
        this.blockRandomised = false;
        this.firstAttempt = true;
        mainSort();
        if (this.workDone > this.workLimit && this.firstAttempt) {
            randomiseBlock();
            this.workDone = 0;
            this.workLimit = 0;
            this.blockRandomised = true;
            this.firstAttempt = false;
            mainSort();
        }
        this.origPtr = -1;
        while (true) {
            if (i2 > this.last) {
                break;
            }
            if (this.zptr[i2] == 0) {
                this.origPtr = i2;
                break;
            }
            i2++;
        }
        if (this.origPtr == -1) {
            panic();
        }
    }

    private void endBlock() throws IOException {
        int finalCRC = this.mCrc.getFinalCRC();
        this.blockCRC = finalCRC;
        int i2 = this.combinedCRC;
        this.combinedCRC = finalCRC ^ ((i2 >>> 31) | (i2 << 1));
        doReversibleTransformation();
        bsPutUChar(49);
        bsPutUChar(65);
        bsPutUChar(89);
        bsPutUChar(38);
        bsPutUChar(83);
        bsPutUChar(89);
        bsPutint(this.blockCRC);
        if (this.blockRandomised) {
            bsW(1, 1);
            this.nBlocksRandomised++;
        } else {
            bsW(1, 0);
        }
        moveToFrontCodeAndSend();
    }

    private void endCompression() throws IOException {
        bsPutUChar(23);
        bsPutUChar(114);
        bsPutUChar(69);
        bsPutUChar(56);
        bsPutUChar(80);
        bsPutUChar(144);
        bsPutint(this.combinedCRC);
        bsFinishedWithStream();
    }

    private boolean fullGtU(int i2, int i3) {
        char[] cArr = this.block;
        int i4 = i2 + 1;
        char c3 = cArr[i4];
        int i5 = i3 + 1;
        char c4 = cArr[i5];
        if (c3 != c4) {
            return c3 > c4;
        }
        int i6 = i4 + 1;
        char c5 = cArr[i6];
        int i7 = i5 + 1;
        char c6 = cArr[i7];
        if (c5 != c6) {
            return c5 > c6;
        }
        int i8 = i6 + 1;
        char c7 = cArr[i8];
        int i9 = i7 + 1;
        char c8 = cArr[i9];
        if (c7 != c8) {
            return c7 > c8;
        }
        int i10 = i8 + 1;
        char c9 = cArr[i10];
        int i11 = i9 + 1;
        char c10 = cArr[i11];
        if (c9 != c10) {
            return c9 > c10;
        }
        int i12 = i10 + 1;
        char c11 = cArr[i12];
        int i13 = i11 + 1;
        char c12 = cArr[i13];
        if (c11 != c12) {
            return c11 > c12;
        }
        int i14 = i12 + 1;
        char c13 = cArr[i14];
        int i15 = i13 + 1;
        char c14 = cArr[i15];
        if (c13 != c14) {
            return c13 > c14;
        }
        int i16 = this.last + 1;
        do {
            char[] cArr2 = this.block;
            int i17 = i14 + 1;
            char c15 = cArr2[i17];
            int i18 = i15 + 1;
            char c16 = cArr2[i18];
            if (c15 != c16) {
                return c15 > c16;
            }
            int[] iArr = this.quadrant;
            int i19 = iArr[i14];
            int i20 = iArr[i15];
            if (i19 != i20) {
                return i19 > i20;
            }
            int i21 = i17 + 1;
            char c17 = cArr2[i21];
            int i22 = i18 + 1;
            char c18 = cArr2[i22];
            if (c17 != c18) {
                return c17 > c18;
            }
            int i23 = iArr[i17];
            int i24 = iArr[i18];
            if (i23 != i24) {
                return i23 > i24;
            }
            int i25 = i21 + 1;
            char c19 = cArr2[i25];
            int i26 = i22 + 1;
            char c20 = cArr2[i26];
            if (c19 != c20) {
                return c19 > c20;
            }
            int i27 = iArr[i21];
            int i28 = iArr[i22];
            if (i27 != i28) {
                return i27 > i28;
            }
            i14 = i25 + 1;
            char c21 = cArr2[i14];
            int i29 = i26 + 1;
            char c22 = cArr2[i29];
            if (c21 != c22) {
                return c21 > c22;
            }
            int i30 = iArr[i25];
            int i31 = iArr[i26];
            if (i30 != i31) {
                return i30 > i31;
            }
            int i32 = this.last;
            if (i14 > i32) {
                i14 = (i14 - i32) - 1;
            }
            if (i29 > i32) {
                i29 = (i29 - i32) - 1;
            }
            i15 = i29;
            i16 -= 4;
            this.workDone++;
        } while (i16 >= 0);
        return false;
    }

    private void generateMTFValues() {
        char[] cArr = new char[256];
        makeMaps();
        int i2 = this.nInUse + 1;
        for (int i3 = 0; i3 <= i2; i3++) {
            this.mtfFreq[i3] = 0;
        }
        for (int i4 = 0; i4 < this.nInUse; i4++) {
            cArr[i4] = (char) i4;
        }
        int i5 = 0;
        int i6 = 0;
        for (int i7 = 0; i7 <= this.last; i7++) {
            char c3 = this.unseqToSeq[this.block[this.zptr[i7]]];
            char c4 = cArr[0];
            int i8 = 0;
            while (c3 != c4) {
                i8++;
                char c5 = cArr[i8];
                cArr[i8] = c4;
                c4 = c5;
            }
            cArr[0] = c4;
            if (i8 == 0) {
                i5++;
            } else {
                if (i5 > 0) {
                    int i9 = i5 - 1;
                    while (true) {
                        int i10 = i9 % 2;
                        if (i10 == 0) {
                            this.szptr[i6] = 0;
                            i6++;
                            int[] iArr = this.mtfFreq;
                            iArr[0] = iArr[0] + 1;
                        } else if (i10 == 1) {
                            this.szptr[i6] = 1;
                            i6++;
                            int[] iArr2 = this.mtfFreq;
                            iArr2[1] = iArr2[1] + 1;
                        }
                        if (i9 < 2) {
                            break;
                        } else {
                            i9 = (i9 - 2) / 2;
                        }
                    }
                    i5 = 0;
                }
                int i11 = i8 + 1;
                this.szptr[i6] = (short) i11;
                i6++;
                int[] iArr3 = this.mtfFreq;
                iArr3[i11] = iArr3[i11] + 1;
            }
        }
        if (i5 > 0) {
            int i12 = i5 - 1;
            while (true) {
                int i13 = i12 % 2;
                if (i13 == 0) {
                    this.szptr[i6] = 0;
                    i6++;
                    int[] iArr4 = this.mtfFreq;
                    iArr4[0] = iArr4[0] + 1;
                } else if (i13 == 1) {
                    this.szptr[i6] = 1;
                    i6++;
                    int[] iArr5 = this.mtfFreq;
                    iArr5[1] = iArr5[1] + 1;
                }
                if (i12 < 2) {
                    break;
                } else {
                    i12 = (i12 - 2) / 2;
                }
            }
        }
        this.szptr[i6] = (short) i2;
        int[] iArr6 = this.mtfFreq;
        iArr6[i2] = iArr6[i2] + 1;
        this.nMTF = i6 + 1;
    }

    private void hbAssignCodes(int[] iArr, char[] cArr, int i2, int i3, int i4) {
        int i5 = 0;
        while (i2 <= i3) {
            for (int i6 = 0; i6 < i4; i6++) {
                if (cArr[i6] == i2) {
                    iArr[i6] = i5;
                    i5++;
                }
            }
            i5 <<= 1;
            i2++;
        }
    }

    public static void hbMakeCodeLengths(char[] cArr, int[] iArr, int i2, int i3) {
        int i4 = R2.attr.actionSheetBackground;
        int[] iArr2 = new int[R2.attr.actionSheetBackground];
        int i5 = R2.attr.biaozianzi;
        int[] iArr3 = new int[R2.attr.biaozianzi];
        int[] iArr4 = new int[R2.attr.biaozianzi];
        int i6 = 0;
        int i7 = 0;
        while (true) {
            int i8 = 1;
            if (i7 >= i2) {
                break;
            }
            int i9 = i7 + 1;
            int i10 = iArr[i7];
            if (i10 != 0) {
                i8 = i10;
            }
            iArr3[i9] = i8 << 8;
            i7 = i9;
        }
        while (true) {
            iArr2[i6] = i6;
            iArr3[i6] = i6;
            iArr4[i6] = -2;
            int i11 = i6;
            for (int i12 = 1; i12 <= i2; i12++) {
                iArr4[i12] = -1;
                i11++;
                iArr2[i11] = i12;
                int i13 = i11;
                while (true) {
                    int i14 = iArr3[i12];
                    int i15 = i13 >> 1;
                    int i16 = iArr2[i15];
                    if (i14 < iArr3[i16]) {
                        iArr2[i13] = i16;
                        i13 = i15;
                    }
                }
                iArr2[i13] = i12;
            }
            if (i11 >= i4) {
                panic();
            }
            int i17 = i2;
            while (i11 > 1) {
                int i18 = iArr2[1];
                int i19 = iArr2[i11];
                iArr2[1] = i19;
                int i20 = i11 - 1;
                int i21 = 1;
                while (true) {
                    int i22 = i21 << 1;
                    if (i22 > i20) {
                        break;
                    }
                    if (i22 < i20) {
                        int i23 = i22 + 1;
                        if (iArr3[iArr2[i23]] < iArr3[iArr2[i22]]) {
                            i22 = i23;
                        }
                    }
                    int i24 = iArr3[i19];
                    int i25 = iArr2[i22];
                    if (i24 < iArr3[i25]) {
                        break;
                    }
                    iArr2[i21] = i25;
                    i21 = i22;
                }
                iArr2[i21] = i19;
                int i26 = iArr2[1];
                int i27 = iArr2[i20];
                iArr2[1] = i27;
                int i28 = i20 - 1;
                int i29 = 1;
                while (true) {
                    int i30 = i29 << 1;
                    if (i30 > i28) {
                        break;
                    }
                    if (i30 < i28) {
                        int i31 = i30 + 1;
                        if (iArr3[iArr2[i31]] < iArr3[iArr2[i30]]) {
                            i30 = i31;
                        }
                    }
                    int i32 = iArr3[i27];
                    int i33 = iArr2[i30];
                    if (i32 < iArr3[i33]) {
                        break;
                    }
                    iArr2[i29] = i33;
                    i29 = i30;
                }
                iArr2[i29] = i27;
                i17++;
                iArr4[i26] = i17;
                iArr4[i18] = i17;
                int i34 = iArr3[i18];
                int i35 = i34 & InputDeviceCompat.SOURCE_ANY;
                int i36 = iArr3[i26];
                iArr3[i17] = (((i34 & 255) > (i36 & 255) ? i34 & 255 : i36 & 255) + 1) | (i35 + (i36 & InputDeviceCompat.SOURCE_ANY));
                iArr4[i17] = -1;
                i11 = i28 + 1;
                iArr2[i11] = i17;
                int i37 = i11;
                while (true) {
                    int i38 = iArr3[i17];
                    int i39 = i37 >> 1;
                    int i40 = iArr2[i39];
                    if (i38 < iArr3[i40]) {
                        iArr2[i37] = i40;
                        i37 = i39;
                    }
                }
                iArr2[i37] = i17;
                i5 = R2.attr.biaozianzi;
            }
            int i41 = i5;
            if (i17 >= i41) {
                panic();
            }
            boolean z2 = false;
            for (int i42 = 1; i42 <= i2; i42++) {
                int i43 = i42;
                int i44 = 0;
                while (true) {
                    i43 = iArr4[i43];
                    if (i43 < 0) {
                        break;
                    } else {
                        i44++;
                    }
                }
                cArr[i42 - 1] = (char) i44;
                if (i44 > i3) {
                    z2 = true;
                }
            }
            if (!z2) {
                return;
            }
            for (int i45 = 1; i45 < i2; i45++) {
                iArr3[i45] = (((iArr3[i45] >> 8) / 2) + 1) << 8;
            }
            i5 = i41;
            i4 = R2.attr.actionSheetBackground;
            i6 = 0;
        }
    }

    private void initBlock() {
        this.mCrc.initialiseCRC();
        this.last = -1;
        for (int i2 = 0; i2 < 256; i2++) {
            this.inUse[i2] = false;
        }
        this.allowableBlockSize = (this.blockSize100k * 100000) - 20;
    }

    private void initialize() throws IOException {
        this.bytesOut = 0;
        this.nBlocksRandomised = 0;
        bsPutUChar(104);
        bsPutUChar(this.blockSize100k + 48);
        this.combinedCRC = 0;
    }

    private void mainSort() {
        int i2;
        int i3;
        int i4;
        int i5;
        int[] iArr = new int[256];
        int[] iArr2 = new int[256];
        boolean[] zArr = new boolean[256];
        int i6 = 0;
        while (true) {
            i2 = 2;
            if (i6 >= 20) {
                break;
            }
            char[] cArr = this.block;
            int i7 = this.last;
            cArr[i7 + i6 + 2] = cArr[(i6 % (i7 + 1)) + 1];
            i6++;
        }
        int i8 = 0;
        while (true) {
            i3 = this.last;
            if (i8 > i3 + 20) {
                break;
            }
            this.quadrant[i8] = 0;
            i8++;
        }
        char[] cArr2 = this.block;
        cArr2[0] = cArr2[i3 + 1];
        if (i3 >= 4000) {
            for (int i9 = 0; i9 <= 255; i9++) {
                zArr[i9] = false;
            }
            for (int i10 = 0; i10 <= 65536; i10++) {
                this.ftab[i10] = 0;
            }
            char c3 = this.block[0];
            int i11 = 0;
            while (i11 <= this.last) {
                i11++;
                char c4 = this.block[i11];
                int[] iArr3 = this.ftab;
                int i12 = (c3 << '\b') + c4;
                iArr3[i12] = iArr3[i12] + 1;
                c3 = c4;
            }
            for (int i13 = 1; i13 <= 65536; i13++) {
                int[] iArr4 = this.ftab;
                iArr4[i13] = iArr4[i13] + iArr4[i13 - 1];
            }
            char c5 = this.block[1];
            int i14 = 0;
            while (true) {
                i4 = this.last;
                if (i14 >= i4) {
                    break;
                }
                char c6 = this.block[i14 + 2];
                int i15 = (c5 << '\b') + c6;
                int[] iArr5 = this.ftab;
                int i16 = iArr5[i15] - 1;
                iArr5[i15] = i16;
                this.zptr[i16] = i14;
                i14++;
                c5 = c6;
            }
            char[] cArr3 = this.block;
            int i17 = (cArr3[i4 + 1] << '\b') + cArr3[1];
            int[] iArr6 = this.ftab;
            int i18 = iArr6[i17] - 1;
            iArr6[i17] = i18;
            this.zptr[i18] = i4;
            for (int i19 = 0; i19 <= 255; i19++) {
                iArr[i19] = i19;
            }
            int i20 = 1;
            do {
                i20 = (i20 * 3) + 1;
            } while (i20 <= 256);
            do {
                i20 /= 3;
                for (int i21 = i20; i21 <= 255; i21++) {
                    int i22 = iArr[i21];
                    int i23 = i21;
                    do {
                        int[] iArr7 = this.ftab;
                        i5 = i23 - i20;
                        int i24 = iArr[i5];
                        if (iArr7[(i24 + 1) << 8] - iArr7[i24 << 8] > iArr7[(i22 + 1) << 8] - iArr7[i22 << 8]) {
                            iArr[i23] = i24;
                            i23 = i5;
                        }
                        iArr[i23] = i22;
                    } while (i5 > i20 - 1);
                    iArr[i23] = i22;
                }
            } while (i20 != 1);
            int i25 = 0;
            while (i25 <= 255) {
                int i26 = iArr[i25];
                for (int i27 = 0; i27 <= 255; i27++) {
                    int i28 = (i26 << 8) + i27;
                    int[] iArr8 = this.ftab;
                    int i29 = iArr8[i28];
                    if ((i29 & 2097152) != 2097152) {
                        int i30 = i29 & CLEARMASK;
                        int i31 = (CLEARMASK & iArr8[i28 + 1]) - 1;
                        if (i31 > i30) {
                            qSort3(i30, i31, i2);
                            if (this.workDone > this.workLimit && this.firstAttempt) {
                                return;
                            }
                        }
                        int[] iArr9 = this.ftab;
                        iArr9[i28] = 2097152 | iArr9[i28];
                    }
                }
                zArr[i26] = true;
                if (i25 < 255) {
                    int[] iArr10 = this.ftab;
                    int i32 = iArr10[i26 << 8] & CLEARMASK;
                    int i33 = (iArr10[(i26 + 1) << 8] & CLEARMASK) - i32;
                    int i34 = 0;
                    while ((i33 >> i34) > 65534) {
                        i34++;
                    }
                    for (int i35 = 0; i35 < i33; i35++) {
                        int i36 = this.zptr[i32 + i35];
                        int i37 = i35 >> i34;
                        int[] iArr11 = this.quadrant;
                        iArr11[i36] = i37;
                        if (i36 < 20) {
                            iArr11[i36 + this.last + 1] = i37;
                        }
                    }
                    if (((i33 - 1) >> i34) > 65535) {
                        panic();
                    }
                }
                for (int i38 = 0; i38 <= 255; i38++) {
                    iArr2[i38] = this.ftab[(i38 << 8) + i26] & CLEARMASK;
                }
                for (int i39 = this.ftab[i26 << 8] & CLEARMASK; i39 < (this.ftab[(i26 + 1) << 8] & CLEARMASK); i39++) {
                    char[] cArr4 = this.block;
                    int[] iArr12 = this.zptr;
                    int i40 = iArr12[i39];
                    char c7 = cArr4[i40];
                    if (!zArr[c7]) {
                        iArr12[iArr2[c7]] = i40 == 0 ? this.last : i40 - 1;
                        iArr2[c7] = iArr2[c7] + 1;
                    }
                }
                for (int i41 = 0; i41 <= 255; i41++) {
                    int[] iArr13 = this.ftab;
                    int i42 = (i41 << 8) + i26;
                    iArr13[i42] = iArr13[i42] | 2097152;
                }
                i25++;
                i2 = 2;
            }
            return;
        }
        int i43 = 0;
        while (true) {
            int i44 = this.last;
            if (i43 > i44) {
                this.firstAttempt = false;
                this.workLimit = 0;
                this.workDone = 0;
                simpleSort(0, i44, 0);
                return;
            }
            this.zptr[i43] = i43;
            i43++;
        }
    }

    private void makeMaps() {
        this.nInUse = 0;
        for (int i2 = 0; i2 < 256; i2++) {
            if (this.inUse[i2]) {
                char[] cArr = this.seqToUnseq;
                int i3 = this.nInUse;
                cArr[i3] = (char) i2;
                this.unseqToSeq[i2] = (char) i3;
                this.nInUse = i3 + 1;
            }
        }
    }

    private char med3(char c3, char c4, char c5) {
        if (c3 <= c4) {
            c4 = c3;
            c3 = c4;
        }
        if (c3 <= c5) {
            c5 = c3;
        }
        return c4 > c5 ? c4 : c5;
    }

    private void moveToFrontCodeAndSend() throws IOException {
        bsPutIntVS(24, this.origPtr);
        generateMTFValues();
        sendMTFValues();
    }

    private static void panic() {
        System.out.println("panic");
    }

    private void qSort3(int i2, int i3, int i4) {
        StackElem[] stackElemArr = new StackElem[1000];
        for (int i5 = 0; i5 < 1000; i5++) {
            stackElemArr[i5] = new StackElem();
        }
        StackElem stackElem = stackElemArr[0];
        stackElem.ll = i2;
        stackElem.hh = i3;
        stackElem.dd = i4;
        int i6 = 1;
        while (i6 > 0) {
            if (i6 >= 1000) {
                panic();
            }
            i6--;
            StackElem stackElem2 = stackElemArr[i6];
            int i7 = stackElem2.ll;
            int i8 = stackElem2.hh;
            int i9 = stackElem2.dd;
            if (i8 - i7 < 20 || i9 > 10) {
                simpleSort(i7, i8, i9);
                if (this.workDone > this.workLimit && this.firstAttempt) {
                    return;
                }
            } else {
                char[] cArr = this.block;
                int[] iArr = this.zptr;
                char cMed3 = med3(cArr[iArr[i7] + i9 + 1], cArr[iArr[i8] + i9 + 1], cArr[iArr[(i7 + i8) >> 1] + i9 + 1]);
                int i10 = i7;
                int i11 = i10;
                int i12 = i8;
                int i13 = i12;
                while (true) {
                    if (i10 <= i12) {
                        char[] cArr2 = this.block;
                        int[] iArr2 = this.zptr;
                        int i14 = iArr2[i10];
                        int i15 = cArr2[(i14 + i9) + 1] - cMed3;
                        if (i15 == 0) {
                            iArr2[i10] = iArr2[i11];
                            iArr2[i11] = i14;
                            i11++;
                        } else if (i15 > 0) {
                        }
                        i10++;
                    }
                    while (i10 <= i12) {
                        char[] cArr3 = this.block;
                        int[] iArr3 = this.zptr;
                        int i16 = iArr3[i12];
                        int i17 = cArr3[(i16 + i9) + 1] - cMed3;
                        if (i17 != 0) {
                            if (i17 < 0) {
                                break;
                            }
                        } else {
                            iArr3[i12] = iArr3[i13];
                            iArr3[i13] = i16;
                            i13--;
                        }
                        i12--;
                    }
                    if (i10 > i12) {
                        break;
                    }
                    int[] iArr4 = this.zptr;
                    int i18 = iArr4[i10];
                    iArr4[i10] = iArr4[i12];
                    iArr4[i12] = i18;
                    i10++;
                    i12--;
                }
                if (i13 < i11) {
                    StackElem stackElem3 = stackElemArr[i6];
                    stackElem3.ll = i7;
                    stackElem3.hh = i8;
                    stackElem3.dd = i9 + 1;
                    i6++;
                } else {
                    int i19 = i11 - i7;
                    int i20 = i10 - i11;
                    if (i19 >= i20) {
                        i19 = i20;
                    }
                    vswap(i7, i10 - i19, i19);
                    int i21 = i8 - i13;
                    int i22 = i13 - i12;
                    if (i21 >= i22) {
                        i21 = i22;
                    }
                    vswap(i10, (i8 - i21) + 1, i21);
                    int i23 = ((i10 + i7) - i11) - 1;
                    int i24 = (i8 - i22) + 1;
                    StackElem stackElem4 = stackElemArr[i6];
                    stackElem4.ll = i7;
                    stackElem4.hh = i23;
                    stackElem4.dd = i9;
                    int i25 = i6 + 1;
                    StackElem stackElem5 = stackElemArr[i25];
                    stackElem5.ll = i23 + 1;
                    stackElem5.hh = i24 - 1;
                    stackElem5.dd = i9 + 1;
                    int i26 = i25 + 1;
                    StackElem stackElem6 = stackElemArr[i26];
                    stackElem6.ll = i24;
                    stackElem6.hh = i8;
                    stackElem6.dd = i9;
                    i6 = i26 + 1;
                }
            }
        }
    }

    private void randomiseBlock() {
        for (int i2 = 0; i2 < 256; i2++) {
            this.inUse[i2] = false;
        }
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (i3 <= this.last) {
            if (i4 == 0) {
                i4 = (char) BZip2Constants.rNums[i5];
                i5++;
                if (i5 == 512) {
                    i5 = 0;
                }
            }
            i4--;
            char[] cArr = this.block;
            i3++;
            char c3 = (char) (cArr[i3] ^ (i4 == 1 ? (char) 1 : (char) 0));
            cArr[i3] = c3;
            char c4 = (char) (c3 & 255);
            cArr[i3] = c4;
            this.inUse[c4] = true;
        }
    }

    private void sendMTFValues() throws IOException {
        int i2;
        int i3;
        int i4;
        char[][] cArr = (char[][]) Array.newInstance((Class<?>) Character.TYPE, 6, 258);
        int i5 = this.nInUse + 2;
        short s2 = 0;
        int i6 = 0;
        while (true) {
            i2 = 6;
            if (i6 >= 6) {
                break;
            }
            for (int i7 = 0; i7 < i5; i7++) {
                cArr[i6][i7] = 15;
            }
            i6++;
        }
        if (this.nMTF <= 0) {
            panic();
        }
        int i8 = this.nMTF;
        int i9 = i8 < 200 ? 2 : i8 < 600 ? 3 : i8 < 1200 ? 4 : i8 < 2400 ? 5 : 6;
        int i10 = 0;
        int i11 = i9;
        while (true) {
            i3 = 1;
            if (i11 <= 0) {
                break;
            }
            int i12 = i8 / i11;
            int i13 = 0;
            int i14 = i10 - 1;
            while (i13 < i12 && i14 < i5 - 1) {
                i14++;
                i13 += this.mtfFreq[i14];
            }
            if (i14 > i10 && i11 != i9 && i11 != 1 && (i9 - i11) % 2 == 1) {
                i13 -= this.mtfFreq[i14];
                i14--;
            }
            for (int i15 = 0; i15 < i5; i15++) {
                if (i15 < i10 || i15 > i14) {
                    cArr[i11 - 1][i15] = 15;
                } else {
                    cArr[i11 - 1][i15] = 0;
                }
            }
            i11--;
            i10 = i14 + 1;
            i8 -= i13;
        }
        int[][] iArr = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, 6, 258);
        int[] iArr2 = new int[6];
        short[] sArr = new short[6];
        int i16 = 0;
        int i17 = 0;
        while (true) {
            int i18 = 20;
            if (i16 >= 4) {
                break;
            }
            for (int i19 = s2; i19 < i9; i19++) {
                iArr2[i19] = s2;
            }
            for (int i20 = s2; i20 < i9; i20++) {
                for (int i21 = s2; i21 < i5; i21++) {
                    iArr[i20][i21] = s2;
                }
            }
            int i22 = s2;
            i17 = i22;
            while (true) {
                int i23 = this.nMTF;
                if (i22 >= i23) {
                    break;
                }
                int i24 = (i22 + 50) - i3;
                if (i24 >= i23) {
                    i24 = i23 - 1;
                }
                for (int i25 = s2; i25 < i9; i25++) {
                    sArr[i25] = s2;
                }
                if (i9 == i2) {
                    int i26 = i22;
                    short s3 = s2;
                    short s4 = s3;
                    short s5 = s4;
                    short s6 = s5;
                    short s7 = s6;
                    short s8 = s7;
                    while (i26 <= i24) {
                        short s9 = this.szptr[i26];
                        short s10 = (short) (s3 + cArr[s2][s9]);
                        short s11 = (short) (s4 + cArr[i3][s9]);
                        short s12 = (short) (s5 + cArr[2][s9]);
                        short s13 = (short) (s6 + cArr[3][s9]);
                        i26++;
                        s7 = (short) (s7 + cArr[4][s9]);
                        s5 = s12;
                        s8 = (short) (s8 + cArr[5][s9]);
                        s6 = s13;
                        s3 = s10;
                        s2 = 0;
                        s4 = s11;
                        i3 = 1;
                    }
                    sArr[s2] = s3;
                    sArr[1] = s4;
                    sArr[2] = s5;
                    sArr[3] = s6;
                    sArr[4] = s7;
                    sArr[5] = s8;
                } else {
                    for (int i27 = i22; i27 <= i24; i27++) {
                        short s14 = this.szptr[i27];
                        for (int i28 = 0; i28 < i9; i28++) {
                            sArr[i28] = (short) (sArr[i28] + cArr[i28][s14]);
                        }
                    }
                }
                int i29 = -1;
                short s15 = 999999999;
                for (int i30 = 0; i30 < i9; i30++) {
                    short s16 = sArr[i30];
                    if (s16 < s15) {
                        i29 = i30;
                        s15 = s16;
                    }
                }
                iArr2[i29] = iArr2[i29] + 1;
                this.selector[i17] = (char) i29;
                i17++;
                while (i22 <= i24) {
                    int[] iArr3 = iArr[i29];
                    short s17 = this.szptr[i22];
                    iArr3[s17] = iArr3[s17] + 1;
                    i22++;
                }
                i22 = i24 + 1;
                i2 = 6;
                s2 = 0;
                i18 = 20;
                i3 = 1;
            }
            for (int i31 = s2; i31 < i9; i31++) {
                hbMakeCodeLengths(cArr[i31], iArr[i31], i5, i18);
            }
            i16++;
        }
        if (i9 >= 8) {
            panic();
        }
        if (i17 >= 32768 || i17 > 18002) {
            panic();
        }
        char[] cArr2 = new char[6];
        for (int i32 = 0; i32 < i9; i32++) {
            cArr2[i32] = (char) i32;
        }
        for (int i33 = 0; i33 < i17; i33++) {
            char c3 = this.selector[i33];
            char c4 = cArr2[0];
            int i34 = 0;
            while (c3 != c4) {
                i34++;
                char c5 = cArr2[i34];
                cArr2[i34] = c4;
                c4 = c5;
            }
            cArr2[0] = c4;
            this.selectorMtf[i33] = (char) i34;
        }
        int[][] iArr4 = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, 6, 258);
        for (int i35 = 0; i35 < i9; i35++) {
            char c6 = ' ';
            char c7 = 0;
            for (int i36 = 0; i36 < i5; i36++) {
                char c8 = cArr[i35][i36];
                if (c8 > c7) {
                    c7 = c8;
                }
                if (c8 < c6) {
                    c6 = c8;
                }
            }
            if (c7 > 20) {
                panic();
            }
            if (c6 < 1) {
                panic();
            }
            hbAssignCodes(iArr4[i35], cArr[i35], c6, c7, i5);
        }
        boolean[] zArr = new boolean[16];
        for (int i37 = 0; i37 < 16; i37++) {
            zArr[i37] = false;
            for (int i38 = 0; i38 < 16; i38++) {
                if (this.inUse[(i37 * 16) + i38]) {
                    zArr[i37] = true;
                }
            }
        }
        for (int i39 = 0; i39 < 16; i39++) {
            if (zArr[i39]) {
                bsW(1, 1);
            } else {
                bsW(1, 0);
            }
        }
        for (int i40 = 0; i40 < 16; i40++) {
            if (zArr[i40]) {
                for (int i41 = 0; i41 < 16; i41++) {
                    if (this.inUse[(i40 * 16) + i41]) {
                        bsW(1, 1);
                    } else {
                        bsW(1, 0);
                    }
                }
            }
        }
        bsW(3, i9);
        bsW(15, i17);
        int i42 = 0;
        while (true) {
            i4 = 0;
            if (i42 >= i17) {
                break;
            }
            while (i4 < this.selectorMtf[i42]) {
                bsW(1, 1);
                i4++;
            }
            bsW(1, 0);
            i42++;
        }
        int i43 = 0;
        while (i43 < i9) {
            char c9 = cArr[i43][i4];
            bsW(5, c9);
            int i44 = c9;
            for (int i45 = 0; i45 < i5; i45++) {
                while (i44 < cArr[i43][i45]) {
                    bsW(2, 2);
                    i44++;
                }
                while (i44 > cArr[i43][i45]) {
                    bsW(2, 3);
                    i44--;
                }
                bsW(1, 0);
            }
            i43++;
            i4 = 0;
        }
        int i46 = i4;
        int i47 = i46;
        while (true) {
            int i48 = this.nMTF;
            if (i47 >= i48) {
                break;
            }
            int i49 = (i47 + 50) - 1;
            if (i49 >= i48) {
                i49 = i48 - 1;
            }
            while (i47 <= i49) {
                char c10 = this.selector[i46];
                char[] cArr3 = cArr[c10];
                short s18 = this.szptr[i47];
                bsW(cArr3[s18], iArr4[c10][s18]);
                i47++;
            }
            i47 = i49 + 1;
            i46++;
        }
        if (i46 != i17) {
            panic();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:51:0x0012, code lost:
    
        continue;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0012, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void simpleSort(int r10, int r11, int r12) {
        /*
            r9 = this;
            int r0 = r11 - r10
            int r0 = r0 + 1
            r1 = 2
            if (r0 >= r1) goto L8
            return
        L8:
            r1 = 0
        L9:
            int[] r2 = r9.incs
            r2 = r2[r1]
            if (r2 >= r0) goto L12
            int r1 = r1 + 1
            goto L9
        L12:
            int r1 = r1 + (-1)
            if (r1 < 0) goto La4
            int[] r0 = r9.incs
            r0 = r0[r1]
            int r2 = r10 + r0
            r3 = r2
        L1d:
            if (r3 <= r11) goto L20
            goto L73
        L20:
            int[] r4 = r9.zptr
            r4 = r4[r3]
            r5 = r3
        L25:
            int[] r6 = r9.zptr
            int r7 = r5 - r0
            r6 = r6[r7]
            int r6 = r6 + r12
            int r8 = r4 + r12
            boolean r6 = r9.fullGtU(r6, r8)
            if (r6 == 0) goto L42
            int[] r6 = r9.zptr
            r8 = r6[r7]
            r6[r5] = r8
            int r5 = r2 + (-1)
            if (r7 > r5) goto L40
            r5 = r7
            goto L42
        L40:
            r5 = r7
            goto L25
        L42:
            int[] r6 = r9.zptr
            r6[r5] = r4
            int r3 = r3 + 1
            if (r3 <= r11) goto L4b
            goto L73
        L4b:
            r4 = r6[r3]
            r5 = r3
        L4e:
            int[] r6 = r9.zptr
            int r7 = r5 - r0
            r6 = r6[r7]
            int r6 = r6 + r12
            int r8 = r4 + r12
            boolean r6 = r9.fullGtU(r6, r8)
            if (r6 == 0) goto L6b
            int[] r6 = r9.zptr
            r8 = r6[r7]
            r6[r5] = r8
            int r5 = r2 + (-1)
            if (r7 > r5) goto L69
            r5 = r7
            goto L6b
        L69:
            r5 = r7
            goto L4e
        L6b:
            int[] r6 = r9.zptr
            r6[r5] = r4
            int r3 = r3 + 1
            if (r3 <= r11) goto L74
        L73:
            goto L12
        L74:
            r4 = r6[r3]
            r5 = r3
        L77:
            int[] r6 = r9.zptr
            int r7 = r5 - r0
            r6 = r6[r7]
            int r6 = r6 + r12
            int r8 = r4 + r12
            boolean r6 = r9.fullGtU(r6, r8)
            if (r6 == 0) goto L94
            int[] r6 = r9.zptr
            r8 = r6[r7]
            r6[r5] = r8
            int r5 = r2 + (-1)
            if (r7 > r5) goto L92
            r5 = r7
            goto L94
        L92:
            r5 = r7
            goto L77
        L94:
            int[] r6 = r9.zptr
            r6[r5] = r4
            int r3 = r3 + 1
            int r4 = r9.workDone
            int r5 = r9.workLimit
            if (r4 <= r5) goto L1d
            boolean r4 = r9.firstAttempt
            if (r4 == 0) goto L1d
        La4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.apache.bzip2.CBZip2OutputStream.simpleSort(int, int, int):void");
    }

    private void vswap(int i2, int i3, int i4) {
        while (i4 > 0) {
            int[] iArr = this.zptr;
            int i5 = iArr[i2];
            iArr[i2] = iArr[i3];
            iArr[i3] = i5;
            i2++;
            i3++;
            i4--;
        }
    }

    private void writeRun() throws IOException {
        int i2;
        if (this.last >= this.allowableBlockSize) {
            endBlock();
            initBlock();
            writeRun();
            return;
        }
        this.inUse[this.currentChar] = true;
        int i3 = 0;
        while (true) {
            i2 = this.runLength;
            if (i3 >= i2) {
                break;
            }
            this.mCrc.updateCRC((char) this.currentChar);
            i3++;
        }
        if (i2 == 1) {
            int i4 = this.last + 1;
            this.last = i4;
            this.block[i4 + 1] = (char) this.currentChar;
            return;
        }
        if (i2 == 2) {
            int i5 = this.last + 1;
            char[] cArr = this.block;
            int i6 = this.currentChar;
            cArr[i5 + 1] = (char) i6;
            int i7 = i5 + 1;
            this.last = i7;
            cArr[i7 + 1] = (char) i6;
            return;
        }
        if (i2 == 3) {
            int i8 = this.last + 1;
            char[] cArr2 = this.block;
            int i9 = this.currentChar;
            cArr2[i8 + 1] = (char) i9;
            int i10 = i8 + 1;
            cArr2[i10 + 1] = (char) i9;
            int i11 = i10 + 1;
            this.last = i11;
            cArr2[i11 + 1] = (char) i9;
            return;
        }
        this.inUse[i2 - 4] = true;
        int i12 = this.last + 1;
        char[] cArr3 = this.block;
        int i13 = this.currentChar;
        cArr3[i12 + 1] = (char) i13;
        int i14 = i12 + 1;
        cArr3[i14 + 1] = (char) i13;
        int i15 = i14 + 1;
        cArr3[i15 + 1] = (char) i13;
        int i16 = i15 + 1;
        cArr3[i16 + 1] = (char) i13;
        int i17 = i16 + 1;
        this.last = i17;
        cArr3[i17 + 1] = (char) (i2 - 4);
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        finish();
        this.closed = true;
        super.close();
        this.bsStream.close();
    }

    public void finalize() throws Throwable {
        close();
        super.finalize();
    }

    public void finish() throws IOException {
        if (this.finished) {
            return;
        }
        if (this.runLength > 0) {
            writeRun();
        }
        this.currentChar = -1;
        endBlock();
        endCompression();
        this.finished = true;
        flush();
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        super.flush();
        this.bsStream.flush();
    }

    @Override // java.io.OutputStream
    public void write(int i2) throws IOException {
        int i3;
        int i4 = (i2 + 256) % 256;
        int i5 = this.currentChar;
        if (i5 == -1) {
            this.currentChar = i4;
            i3 = this.runLength + 1;
        } else if (i5 != i4) {
            writeRun();
            this.runLength = 1;
            this.currentChar = i4;
            return;
        } else {
            int i6 = this.runLength + 1;
            this.runLength = i6;
            if (i6 <= 254) {
                return;
            }
            writeRun();
            this.currentChar = -1;
            i3 = 0;
        }
        this.runLength = i3;
    }
}
