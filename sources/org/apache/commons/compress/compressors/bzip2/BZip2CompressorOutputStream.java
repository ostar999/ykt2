package org.apache.commons.compress.compressors.bzip2;

import androidx.core.view.InputDeviceCompat;
import com.yikaobang.yixue.R2;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import org.apache.commons.compress.compressors.CompressorOutputStream;

/* loaded from: classes9.dex */
public class BZip2CompressorOutputStream extends CompressorOutputStream implements BZip2Constants {
    private static final int GREATER_ICOST = 15;
    private static final int LESSER_ICOST = 0;
    public static final int MAX_BLOCKSIZE = 9;
    public static final int MIN_BLOCKSIZE = 1;
    private final int allowableBlockSize;
    private int blockCRC;
    private final int blockSize100k;
    private BlockSort blockSorter;
    private int bsBuff;
    private int bsLive;
    private volatile boolean closed;
    private int combinedCRC;
    private final CRC crc;
    private int currentChar;
    private Data data;
    private int last;
    private int nInUse;
    private int nMTF;
    private OutputStream out;
    private int runLength;

    public static final class Data {
        final byte[] block;
        final int[] fmap;
        final int[] heap;
        int origPtr;
        final int[] parent;
        final byte[] sendMTFValues2_pos;
        final int[][] sendMTFValues_code;
        final short[] sendMTFValues_cost;
        final int[] sendMTFValues_fave;
        final int[][] sendMTFValues_rfreq;
        final boolean[] sentMTFValues4_inUse16;
        final char[] sfmap;
        final int[] weight;
        final boolean[] inUse = new boolean[256];
        final byte[] unseqToSeq = new byte[256];
        final int[] mtfFreq = new int[258];
        final byte[] selector = new byte[18002];
        final byte[] selectorMtf = new byte[18002];
        final byte[] generateMTFValues_yy = new byte[256];
        final byte[][] sendMTFValues_len = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, 6, 258);

        public Data(int i2) {
            Class cls = Integer.TYPE;
            this.sendMTFValues_rfreq = (int[][]) Array.newInstance((Class<?>) cls, 6, 258);
            this.sendMTFValues_fave = new int[6];
            this.sendMTFValues_cost = new short[6];
            this.sendMTFValues_code = (int[][]) Array.newInstance((Class<?>) cls, 6, 258);
            this.sendMTFValues2_pos = new byte[6];
            this.sentMTFValues4_inUse16 = new boolean[16];
            this.heap = new int[R2.attr.actionSheetBackground];
            this.weight = new int[R2.attr.biaozianzi];
            this.parent = new int[R2.attr.biaozianzi];
            int i3 = i2 * 100000;
            this.block = new byte[i3 + 1 + 20];
            this.fmap = new int[i3];
            this.sfmap = new char[i3 * 2];
        }
    }

    public BZip2CompressorOutputStream(OutputStream outputStream) throws IOException {
        this(outputStream, 9);
    }

    private void blockSort() {
        this.blockSorter.blockSort(this.data, this.last);
    }

    private void bsFinishedWithStream() throws IOException {
        while (this.bsLive > 0) {
            this.out.write(this.bsBuff >> 24);
            this.bsBuff <<= 8;
            this.bsLive -= 8;
        }
    }

    private void bsPutInt(int i2) throws IOException {
        bsW(8, (i2 >> 24) & 255);
        bsW(8, (i2 >> 16) & 255);
        bsW(8, (i2 >> 8) & 255);
        bsW(8, i2 & 255);
    }

    private void bsPutUByte(int i2) throws IOException {
        bsW(8, i2);
    }

    private void bsW(int i2, int i3) throws IOException {
        OutputStream outputStream = this.out;
        int i4 = this.bsLive;
        int i5 = this.bsBuff;
        while (i4 >= 8) {
            outputStream.write(i5 >> 24);
            i5 <<= 8;
            i4 -= 8;
        }
        this.bsBuff = (i3 << ((32 - i4) - i2)) | i5;
        this.bsLive = i4 + i2;
    }

    public static int chooseBlockSize(long j2) {
        if (j2 > 0) {
            return (int) Math.min((j2 / 132000) + 1, 9L);
        }
        return 9;
    }

    private void endBlock() throws IOException {
        int finalCRC = this.crc.getFinalCRC();
        this.blockCRC = finalCRC;
        int i2 = this.combinedCRC;
        this.combinedCRC = finalCRC ^ ((i2 >>> 31) | (i2 << 1));
        if (this.last == -1) {
            return;
        }
        blockSort();
        bsPutUByte(49);
        bsPutUByte(65);
        bsPutUByte(89);
        bsPutUByte(38);
        bsPutUByte(83);
        bsPutUByte(89);
        bsPutInt(this.blockCRC);
        bsW(1, 0);
        moveToFrontCodeAndSend();
    }

    private void endCompression() throws IOException {
        bsPutUByte(23);
        bsPutUByte(114);
        bsPutUByte(69);
        bsPutUByte(56);
        bsPutUByte(80);
        bsPutUByte(144);
        bsPutInt(this.combinedCRC);
        bsFinishedWithStream();
    }

    private void generateMTFValues() {
        int i2 = this.last;
        Data data = this.data;
        boolean[] zArr = data.inUse;
        byte[] bArr = data.block;
        int[] iArr = data.fmap;
        char[] cArr = data.sfmap;
        int[] iArr2 = data.mtfFreq;
        byte[] bArr2 = data.unseqToSeq;
        byte[] bArr3 = data.generateMTFValues_yy;
        int i3 = 0;
        for (int i4 = 0; i4 < 256; i4++) {
            if (zArr[i4]) {
                bArr2[i4] = (byte) i3;
                i3++;
            }
        }
        this.nInUse = i3;
        int i5 = i3 + 1;
        for (int i6 = i5; i6 >= 0; i6--) {
            iArr2[i6] = 0;
        }
        while (true) {
            i3--;
            if (i3 < 0) {
                break;
            } else {
                bArr3[i3] = (byte) i3;
            }
        }
        int i7 = 0;
        int i8 = 0;
        for (int i9 = 0; i9 <= i2; i9++) {
            byte b3 = bArr2[bArr[iArr[i9]] & 255];
            byte b4 = bArr3[0];
            int i10 = 0;
            while (b3 != b4) {
                i10++;
                byte b5 = bArr3[i10];
                bArr3[i10] = b4;
                b4 = b5;
            }
            bArr3[0] = b4;
            if (i10 == 0) {
                i7++;
            } else {
                if (i7 > 0) {
                    int i11 = i7 - 1;
                    while (true) {
                        if ((i11 & 1) == 0) {
                            cArr[i8] = 0;
                            i8++;
                            iArr2[0] = iArr2[0] + 1;
                        } else {
                            cArr[i8] = 1;
                            i8++;
                            iArr2[1] = iArr2[1] + 1;
                        }
                        if (i11 < 2) {
                            break;
                        } else {
                            i11 = (i11 - 2) >> 1;
                        }
                    }
                    i7 = 0;
                }
                int i12 = i10 + 1;
                cArr[i8] = (char) i12;
                i8++;
                iArr2[i12] = iArr2[i12] + 1;
            }
        }
        if (i7 > 0) {
            int i13 = i7 - 1;
            while (true) {
                if ((i13 & 1) == 0) {
                    cArr[i8] = 0;
                    i8++;
                    iArr2[0] = iArr2[0] + 1;
                } else {
                    cArr[i8] = 1;
                    i8++;
                    iArr2[1] = iArr2[1] + 1;
                }
                if (i13 < 2) {
                    break;
                } else {
                    i13 = (i13 - 2) >> 1;
                }
            }
        }
        cArr[i8] = (char) i5;
        iArr2[i5] = iArr2[i5] + 1;
        this.nMTF = i8 + 1;
    }

    private static void hbAssignCodes(int[] iArr, byte[] bArr, int i2, int i3, int i4) {
        int i5 = 0;
        while (i2 <= i3) {
            for (int i6 = 0; i6 < i4; i6++) {
                if ((bArr[i6] & 255) == i2) {
                    iArr[i6] = i5;
                    i5++;
                }
            }
            i5 <<= 1;
            i2++;
        }
    }

    private static void hbMakeCodeLengths(byte[] bArr, int[] iArr, Data data, int i2, int i3) {
        int[] iArr2 = data.heap;
        int[] iArr3 = data.weight;
        int[] iArr4 = data.parent;
        int i4 = i2;
        while (true) {
            i4--;
            int i5 = 1;
            if (i4 < 0) {
                break;
            }
            int i6 = i4 + 1;
            int i7 = iArr[i4];
            if (i7 != 0) {
                i5 = i7;
            }
            iArr3[i6] = i5 << 8;
        }
        boolean z2 = true;
        while (z2) {
            iArr2[0] = 0;
            iArr3[0] = 0;
            iArr4[0] = -2;
            int i8 = 0;
            for (int i9 = 1; i9 <= i2; i9++) {
                iArr4[i9] = -1;
                i8++;
                iArr2[i8] = i9;
                int i10 = i8;
                while (true) {
                    int i11 = iArr3[i9];
                    int i12 = i10 >> 1;
                    int i13 = iArr2[i12];
                    if (i11 < iArr3[i13]) {
                        iArr2[i10] = i13;
                        i10 = i12;
                    }
                }
                iArr2[i10] = i9;
            }
            int i14 = i2;
            while (i8 > 1) {
                int i15 = iArr2[1];
                int i16 = iArr2[i8];
                iArr2[1] = i16;
                int i17 = i8 - 1;
                int i18 = 1;
                while (true) {
                    int i19 = i18 << 1;
                    if (i19 > i17) {
                        break;
                    }
                    if (i19 < i17) {
                        int i20 = i19 + 1;
                        if (iArr3[iArr2[i20]] < iArr3[iArr2[i19]]) {
                            i19 = i20;
                        }
                    }
                    int i21 = iArr3[i16];
                    int i22 = iArr2[i19];
                    if (i21 < iArr3[i22]) {
                        break;
                    }
                    iArr2[i18] = i22;
                    i18 = i19;
                }
                iArr2[i18] = i16;
                int i23 = iArr2[1];
                int i24 = iArr2[i17];
                iArr2[1] = i24;
                int i25 = i17 - 1;
                int i26 = 1;
                while (true) {
                    int i27 = i26 << 1;
                    if (i27 > i25) {
                        break;
                    }
                    if (i27 < i25) {
                        int i28 = i27 + 1;
                        if (iArr3[iArr2[i28]] < iArr3[iArr2[i27]]) {
                            i27 = i28;
                        }
                    }
                    int i29 = iArr3[i24];
                    int i30 = iArr2[i27];
                    if (i29 < iArr3[i30]) {
                        break;
                    }
                    iArr2[i26] = i30;
                    i26 = i27;
                }
                iArr2[i26] = i24;
                i14++;
                iArr4[i23] = i14;
                iArr4[i15] = i14;
                int i31 = iArr3[i15];
                int i32 = iArr3[i23];
                int i33 = (i31 & InputDeviceCompat.SOURCE_ANY) + (i32 & InputDeviceCompat.SOURCE_ANY);
                int i34 = i31 & 255;
                int i35 = i32 & 255;
                if (i34 <= i35) {
                    i34 = i35;
                }
                iArr3[i14] = (i34 + 1) | i33;
                iArr4[i14] = -1;
                i8 = i25 + 1;
                iArr2[i8] = i14;
                int i36 = iArr3[i14];
                int i37 = i8;
                while (true) {
                    int i38 = i37 >> 1;
                    int i39 = iArr2[i38];
                    if (i36 < iArr3[i39]) {
                        iArr2[i37] = i39;
                        i37 = i38;
                    }
                }
                iArr2[i37] = i14;
            }
            z2 = false;
            for (int i40 = 1; i40 <= i2; i40++) {
                int i41 = i40;
                int i42 = 0;
                while (true) {
                    i41 = iArr4[i41];
                    if (i41 < 0) {
                        break;
                    } else {
                        i42++;
                    }
                }
                bArr[i40 - 1] = (byte) i42;
                if (i42 > i3) {
                    z2 = true;
                }
            }
            if (z2) {
                for (int i43 = 1; i43 < i2; i43++) {
                    iArr3[i43] = (((iArr3[i43] >> 8) >> 1) + 1) << 8;
                }
            }
        }
    }

    private void init() throws IOException {
        bsPutUByte(66);
        bsPutUByte(90);
        this.data = new Data(this.blockSize100k);
        this.blockSorter = new BlockSort(this.data);
        bsPutUByte(104);
        bsPutUByte(this.blockSize100k + 48);
        this.combinedCRC = 0;
        initBlock();
    }

    private void initBlock() {
        this.crc.initialiseCRC();
        this.last = -1;
        boolean[] zArr = this.data.inUse;
        int i2 = 256;
        while (true) {
            i2--;
            if (i2 < 0) {
                return;
            } else {
                zArr[i2] = false;
            }
        }
    }

    private void moveToFrontCodeAndSend() throws IOException {
        bsW(24, this.data.origPtr);
        generateMTFValues();
        sendMTFValues();
    }

    private void sendMTFValues() throws IOException {
        byte[][] bArr = this.data.sendMTFValues_len;
        int i2 = this.nInUse + 2;
        int i3 = 6;
        while (true) {
            i3--;
            if (i3 < 0) {
                break;
            }
            byte[] bArr2 = bArr[i3];
            int i4 = i2;
            while (true) {
                i4--;
                if (i4 >= 0) {
                    bArr2[i4] = 15;
                }
            }
        }
        int i5 = this.nMTF;
        int i6 = i5 >= 200 ? i5 < 600 ? 3 : i5 < 1200 ? 4 : i5 < 2400 ? 5 : 6 : 2;
        sendMTFValues0(i6, i2);
        int iSendMTFValues1 = sendMTFValues1(i6, i2);
        sendMTFValues2(i6, iSendMTFValues1);
        sendMTFValues3(i6, i2);
        sendMTFValues4();
        sendMTFValues5(i6, iSendMTFValues1);
        sendMTFValues6(i6, i2);
        sendMTFValues7();
    }

    private void sendMTFValues0(int i2, int i3) {
        Data data = this.data;
        byte[][] bArr = data.sendMTFValues_len;
        int[] iArr = data.mtfFreq;
        int i4 = this.nMTF;
        int i5 = 0;
        for (int i6 = i2; i6 > 0; i6--) {
            int i7 = i4 / i6;
            int i8 = i5 - 1;
            int i9 = i3 - 1;
            int i10 = 0;
            while (i10 < i7 && i8 < i9) {
                i8++;
                i10 += iArr[i8];
            }
            if (i8 > i5 && i6 != i2 && i6 != 1 && (1 & (i2 - i6)) != 0) {
                i10 -= iArr[i8];
                i8--;
            }
            byte[] bArr2 = bArr[i6 - 1];
            int i11 = i3;
            while (true) {
                i11--;
                if (i11 >= 0) {
                    if (i11 < i5 || i11 > i8) {
                        bArr2[i11] = 15;
                    } else {
                        bArr2[i11] = 0;
                    }
                }
            }
            i5 = i8 + 1;
            i4 -= i10;
        }
    }

    private int sendMTFValues1(int i2, int i3) {
        byte[] bArr;
        int i4;
        BZip2CompressorOutputStream bZip2CompressorOutputStream = this;
        Data data = bZip2CompressorOutputStream.data;
        int[][] iArr = data.sendMTFValues_rfreq;
        int[] iArr2 = data.sendMTFValues_fave;
        short[] sArr = data.sendMTFValues_cost;
        char[] cArr = data.sfmap;
        byte[] bArr2 = data.selector;
        byte[][] bArr3 = data.sendMTFValues_len;
        int i5 = 0;
        byte[] bArr4 = bArr3[0];
        byte[] bArr5 = bArr3[1];
        byte[] bArr6 = bArr3[2];
        byte[] bArr7 = bArr3[3];
        int i6 = 4;
        byte[] bArr8 = bArr3[4];
        byte[] bArr9 = bArr3[5];
        int i7 = bZip2CompressorOutputStream.nMTF;
        int i8 = 0;
        int i9 = 0;
        while (i8 < i6) {
            int i10 = i2;
            while (true) {
                i10--;
                if (i10 < 0) {
                    break;
                }
                iArr2[i10] = i5;
                int[] iArr3 = iArr[i10];
                int i11 = i3;
                while (true) {
                    i11--;
                    if (i11 >= 0) {
                        iArr3[i11] = i5;
                    }
                }
            }
            int i12 = i5;
            i9 = i12;
            while (i12 < bZip2CompressorOutputStream.nMTF) {
                int i13 = i12;
                int iMin = Math.min((i12 + 50) - 1, i7 - 1);
                if (i2 == 6) {
                    int i14 = i13;
                    short s2 = 0;
                    short s3 = 0;
                    short s4 = 0;
                    short s5 = 0;
                    short s6 = 0;
                    short s7 = 0;
                    while (i14 <= iMin) {
                        char c3 = cArr[i14];
                        int i15 = i7;
                        short s8 = (short) (s2 + (bArr4[c3] & 255));
                        byte[] bArr10 = bArr4;
                        short s9 = (short) (s3 + (bArr5[c3] & 255));
                        short s10 = (short) (s4 + (bArr6[c3] & 255));
                        short s11 = (short) (s5 + (bArr7[c3] & 255));
                        short s12 = (short) (s6 + (bArr8[c3] & 255));
                        i14++;
                        s7 = (short) (s7 + (bArr9[c3] & 255));
                        s6 = s12;
                        bArr4 = bArr10;
                        s5 = s11;
                        s4 = s10;
                        s3 = s9;
                        s2 = s8;
                        i7 = i15;
                    }
                    bArr = bArr4;
                    i4 = i7;
                    sArr[0] = s2;
                    sArr[1] = s3;
                    sArr[2] = s4;
                    sArr[3] = s5;
                    sArr[4] = s6;
                    sArr[5] = s7;
                } else {
                    bArr = bArr4;
                    i4 = i7;
                    int i16 = i2;
                    while (true) {
                        i16--;
                        if (i16 < 0) {
                            break;
                        }
                        sArr[i16] = 0;
                    }
                    for (int i17 = i13; i17 <= iMin; i17++) {
                        char c4 = cArr[i17];
                        int i18 = i2;
                        while (true) {
                            i18--;
                            if (i18 >= 0) {
                                sArr[i18] = (short) (sArr[i18] + (bArr3[i18][c4] & 255));
                            }
                        }
                    }
                }
                short s13 = 999999999;
                int i19 = i2;
                int i20 = -1;
                while (true) {
                    i19--;
                    if (i19 < 0) {
                        break;
                    }
                    byte[] bArr11 = bArr5;
                    short s14 = sArr[i19];
                    if (s14 < s13) {
                        s13 = s14;
                        i20 = i19;
                    }
                    bArr5 = bArr11;
                }
                byte[] bArr12 = bArr5;
                iArr2[i20] = iArr2[i20] + 1;
                bArr2[i9] = (byte) i20;
                i9++;
                int[] iArr4 = iArr[i20];
                for (int i21 = i13; i21 <= iMin; i21++) {
                    char c5 = cArr[i21];
                    iArr4[c5] = iArr4[c5] + 1;
                }
                i12 = iMin + 1;
                bArr5 = bArr12;
                i7 = i4;
                bArr4 = bArr;
            }
            byte[] bArr13 = bArr4;
            byte[] bArr14 = bArr5;
            int i22 = i7;
            int i23 = 0;
            while (i23 < i2) {
                hbMakeCodeLengths(bArr3[i23], iArr[i23], bZip2CompressorOutputStream.data, i3, 20);
                i23++;
                bZip2CompressorOutputStream = this;
            }
            i8++;
            i5 = 0;
            bZip2CompressorOutputStream = this;
            i6 = 4;
            bArr5 = bArr14;
            i7 = i22;
            bArr4 = bArr13;
        }
        return i9;
    }

    private void sendMTFValues2(int i2, int i3) {
        Data data = this.data;
        byte[] bArr = data.sendMTFValues2_pos;
        while (true) {
            i2--;
            if (i2 < 0) {
                break;
            } else {
                bArr[i2] = (byte) i2;
            }
        }
        for (int i4 = 0; i4 < i3; i4++) {
            byte b3 = data.selector[i4];
            byte b4 = bArr[0];
            int i5 = 0;
            while (b3 != b4) {
                i5++;
                byte b5 = bArr[i5];
                bArr[i5] = b4;
                b4 = b5;
            }
            bArr[0] = b4;
            data.selectorMtf[i4] = (byte) i5;
        }
    }

    private void sendMTFValues3(int i2, int i3) {
        Data data = this.data;
        int[][] iArr = data.sendMTFValues_code;
        byte[][] bArr = data.sendMTFValues_len;
        for (int i4 = 0; i4 < i2; i4++) {
            byte[] bArr2 = bArr[i4];
            int i5 = 32;
            int i6 = i3;
            int i7 = 0;
            while (true) {
                i6--;
                if (i6 >= 0) {
                    int i8 = bArr2[i6] & 255;
                    if (i8 > i7) {
                        i7 = i8;
                    }
                    if (i8 < i5) {
                        i5 = i8;
                    }
                }
            }
            hbAssignCodes(iArr[i4], bArr[i4], i5, i7, i3);
        }
    }

    private void sendMTFValues4() throws IOException {
        Data data = this.data;
        boolean[] zArr = data.inUse;
        boolean[] zArr2 = data.sentMTFValues4_inUse16;
        int i2 = 16;
        while (true) {
            i2--;
            if (i2 < 0) {
                break;
            }
            zArr2[i2] = false;
            int i3 = i2 * 16;
            int i4 = 16;
            while (true) {
                i4--;
                if (i4 >= 0) {
                    if (zArr[i3 + i4]) {
                        zArr2[i2] = true;
                    }
                }
            }
        }
        for (int i5 = 0; i5 < 16; i5++) {
            bsW(1, zArr2[i5] ? 1 : 0);
        }
        OutputStream outputStream = this.out;
        int i6 = this.bsLive;
        int i7 = this.bsBuff;
        for (int i8 = 0; i8 < 16; i8++) {
            if (zArr2[i8]) {
                int i9 = i8 * 16;
                for (int i10 = 0; i10 < 16; i10++) {
                    while (i6 >= 8) {
                        outputStream.write(i7 >> 24);
                        i7 <<= 8;
                        i6 -= 8;
                    }
                    if (zArr[i9 + i10]) {
                        i7 |= 1 << ((32 - i6) - 1);
                    }
                    i6++;
                }
            }
        }
        this.bsBuff = i7;
        this.bsLive = i6;
    }

    private void sendMTFValues5(int i2, int i3) throws IOException {
        bsW(3, i2);
        bsW(15, i3);
        OutputStream outputStream = this.out;
        byte[] bArr = this.data.selectorMtf;
        int i4 = this.bsLive;
        int i5 = this.bsBuff;
        for (int i6 = 0; i6 < i3; i6++) {
            int i7 = bArr[i6] & 255;
            for (int i8 = 0; i8 < i7; i8++) {
                while (i4 >= 8) {
                    outputStream.write(i5 >> 24);
                    i5 <<= 8;
                    i4 -= 8;
                }
                i5 |= 1 << ((32 - i4) - 1);
                i4++;
            }
            while (i4 >= 8) {
                outputStream.write(i5 >> 24);
                i5 <<= 8;
                i4 -= 8;
            }
            i4++;
        }
        this.bsBuff = i5;
        this.bsLive = i4;
    }

    private void sendMTFValues6(int i2, int i3) throws IOException {
        byte[][] bArr = this.data.sendMTFValues_len;
        OutputStream outputStream = this.out;
        int i4 = this.bsLive;
        int i5 = this.bsBuff;
        for (int i6 = 0; i6 < i2; i6++) {
            byte[] bArr2 = bArr[i6];
            int i7 = bArr2[0] & 255;
            while (i4 >= 8) {
                outputStream.write(i5 >> 24);
                i5 <<= 8;
                i4 -= 8;
            }
            i5 |= i7 << ((32 - i4) - 5);
            i4 += 5;
            for (int i8 = 0; i8 < i3; i8++) {
                int i9 = bArr2[i8] & 255;
                while (i7 < i9) {
                    while (i4 >= 8) {
                        outputStream.write(i5 >> 24);
                        i5 <<= 8;
                        i4 -= 8;
                    }
                    i5 |= 2 << ((32 - i4) - 2);
                    i4 += 2;
                    i7++;
                }
                while (i7 > i9) {
                    while (i4 >= 8) {
                        outputStream.write(i5 >> 24);
                        i5 <<= 8;
                        i4 -= 8;
                    }
                    i5 |= 3 << ((32 - i4) - 2);
                    i4 += 2;
                    i7--;
                }
                while (i4 >= 8) {
                    outputStream.write(i5 >> 24);
                    i5 <<= 8;
                    i4 -= 8;
                }
                i4++;
            }
        }
        this.bsBuff = i5;
        this.bsLive = i4;
    }

    private void sendMTFValues7() throws IOException {
        Data data = this.data;
        byte[][] bArr = data.sendMTFValues_len;
        int[][] iArr = data.sendMTFValues_code;
        OutputStream outputStream = this.out;
        byte[] bArr2 = data.selector;
        char[] cArr = data.sfmap;
        int i2 = this.nMTF;
        int i3 = this.bsLive;
        int i4 = this.bsBuff;
        int i5 = 0;
        int i6 = 0;
        while (i5 < i2) {
            int iMin = Math.min((i5 + 50) - 1, i2 - 1);
            int i7 = bArr2[i6] & 255;
            int[] iArr2 = iArr[i7];
            byte[] bArr3 = bArr[i7];
            while (i5 <= iMin) {
                char c3 = cArr[i5];
                while (i3 >= 8) {
                    outputStream.write(i4 >> 24);
                    i4 <<= 8;
                    i3 -= 8;
                }
                int i8 = bArr3[c3] & 255;
                i4 |= iArr2[c3] << ((32 - i3) - i8);
                i3 += i8;
                i5++;
            }
            i5 = iMin + 1;
            i6++;
        }
        this.bsBuff = i4;
        this.bsLive = i3;
    }

    private void write0(int i2) throws IOException {
        int i3 = this.currentChar;
        if (i3 == -1) {
            this.currentChar = i2 & 255;
            this.runLength++;
            return;
        }
        int i4 = i2 & 255;
        if (i3 != i4) {
            writeRun();
            this.runLength = 1;
            this.currentChar = i4;
            return;
        }
        int i5 = this.runLength + 1;
        this.runLength = i5;
        if (i5 > 254) {
            writeRun();
            this.currentChar = -1;
            this.runLength = 0;
        }
    }

    private void writeRun() throws IOException {
        int i2 = this.last;
        if (i2 >= this.allowableBlockSize) {
            endBlock();
            initBlock();
            writeRun();
            return;
        }
        int i3 = this.currentChar;
        Data data = this.data;
        data.inUse[i3] = true;
        byte b3 = (byte) i3;
        int i4 = this.runLength;
        this.crc.updateCRC(i3, i4);
        if (i4 == 1) {
            data.block[i2 + 2] = b3;
            this.last = i2 + 1;
            return;
        }
        if (i4 == 2) {
            byte[] bArr = data.block;
            int i5 = i2 + 2;
            bArr[i5] = b3;
            bArr[i2 + 3] = b3;
            this.last = i5;
            return;
        }
        if (i4 == 3) {
            byte[] bArr2 = data.block;
            bArr2[i2 + 2] = b3;
            int i6 = i2 + 3;
            bArr2[i6] = b3;
            bArr2[i2 + 4] = b3;
            this.last = i6;
            return;
        }
        int i7 = i4 - 4;
        data.inUse[i7] = true;
        byte[] bArr3 = data.block;
        bArr3[i2 + 2] = b3;
        bArr3[i2 + 3] = b3;
        bArr3[i2 + 4] = b3;
        int i8 = i2 + 5;
        bArr3[i8] = b3;
        bArr3[i2 + 6] = (byte) i7;
        this.last = i8;
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        OutputStream outputStream = this.out;
        finish();
        outputStream.close();
    }

    public void finalize() throws Throwable {
        if (!this.closed) {
            System.err.println("Unclosed BZip2CompressorOutputStream detected, will *not* close it");
        }
        super.finalize();
    }

    public void finish() throws IOException {
        if (this.closed) {
            return;
        }
        this.closed = true;
        try {
            if (this.runLength > 0) {
                writeRun();
            }
            this.currentChar = -1;
            endBlock();
            endCompression();
        } finally {
            this.out = null;
            this.blockSorter = null;
            this.data = null;
        }
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        OutputStream outputStream = this.out;
        if (outputStream != null) {
            outputStream.flush();
        }
    }

    public final int getBlockSize() {
        return this.blockSize100k;
    }

    @Override // java.io.OutputStream
    public void write(int i2) throws IOException {
        if (this.closed) {
            throw new IOException("closed");
        }
        write0(i2);
    }

    public BZip2CompressorOutputStream(OutputStream outputStream, int i2) throws IOException {
        this.crc = new CRC();
        this.currentChar = -1;
        this.runLength = 0;
        if (i2 < 1) {
            throw new IllegalArgumentException("blockSize(" + i2 + ") < 1");
        }
        if (i2 <= 9) {
            this.blockSize100k = i2;
            this.out = outputStream;
            this.allowableBlockSize = (i2 * 100000) - 20;
            init();
            return;
        }
        throw new IllegalArgumentException("blockSize(" + i2 + ") > 9");
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) throws IOException {
        if (i2 < 0) {
            throw new IndexOutOfBoundsException("offs(" + i2 + ") < 0.");
        }
        if (i3 >= 0) {
            int i4 = i2 + i3;
            if (i4 <= bArr.length) {
                if (this.closed) {
                    throw new IOException("stream closed");
                }
                while (i2 < i4) {
                    write0(bArr[i2]);
                    i2++;
                }
                return;
            }
            throw new IndexOutOfBoundsException("offs(" + i2 + ") + len(" + i3 + ") > buf.length(" + bArr.length + ").");
        }
        throw new IndexOutOfBoundsException("len(" + i3 + ") < 0.");
    }
}
