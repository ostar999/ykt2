package org.apache.commons.compress.compressors.bzip2;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import org.apache.commons.compress.compressors.CompressorInputStream;

/* loaded from: classes9.dex */
public class BZip2CompressorInputStream extends CompressorInputStream implements BZip2Constants {
    private static final int EOF = 0;
    private static final int NO_RAND_PART_A_STATE = 5;
    private static final int NO_RAND_PART_B_STATE = 6;
    private static final int NO_RAND_PART_C_STATE = 7;
    private static final int RAND_PART_A_STATE = 2;
    private static final int RAND_PART_B_STATE = 3;
    private static final int RAND_PART_C_STATE = 4;
    private static final int START_BLOCK_STATE = 1;
    private boolean blockRandomised;
    private int blockSize100k;
    private int bsBuff;
    private int bsLive;
    private int computedBlockCRC;
    private int computedCombinedCRC;
    private final CRC crc;
    private int currentState;
    private Data data;
    private final boolean decompressConcatenated;
    private InputStream in;
    private int last;
    private int nInUse;
    private int origPtr;
    private int storedBlockCRC;
    private int storedCombinedCRC;
    private int su_ch2;
    private int su_chPrev;
    private int su_count;
    private int su_i2;
    private int su_j2;
    private int su_rNToGo;
    private int su_rTPos;
    private int su_tPos;
    private char su_z;

    public static final class Data {
        final int[][] base;
        final int[] cftab;
        final char[] getAndMoveToFrontDecode_yy;
        final int[][] limit;
        byte[] ll8;
        final int[] minLens;
        final int[][] perm;
        final byte[] recvDecodingTables_pos;
        final char[][] temp_charArray2d;
        int[] tt;
        final boolean[] inUse = new boolean[256];
        final byte[] seqToUnseq = new byte[256];
        final byte[] selector = new byte[18002];
        final byte[] selectorMtf = new byte[18002];
        final int[] unzftab = new int[256];

        public Data(int i2) {
            Class cls = Integer.TYPE;
            this.limit = (int[][]) Array.newInstance((Class<?>) cls, 6, 258);
            this.base = (int[][]) Array.newInstance((Class<?>) cls, 6, 258);
            this.perm = (int[][]) Array.newInstance((Class<?>) cls, 6, 258);
            this.minLens = new int[6];
            this.cftab = new int[257];
            this.getAndMoveToFrontDecode_yy = new char[256];
            this.temp_charArray2d = (char[][]) Array.newInstance((Class<?>) Character.TYPE, 6, 258);
            this.recvDecodingTables_pos = new byte[6];
            this.ll8 = new byte[i2 * 100000];
        }

        public int[] initTT(int i2) {
            int[] iArr = this.tt;
            if (iArr != null && iArr.length >= i2) {
                return iArr;
            }
            int[] iArr2 = new int[i2];
            this.tt = iArr2;
            return iArr2;
        }
    }

    public BZip2CompressorInputStream(InputStream inputStream) throws IOException {
        this(inputStream, false);
    }

    private boolean bsGetBit() throws IOException {
        return bsR(1) != 0;
    }

    private int bsGetInt() throws IOException {
        return bsR(8) | (((((bsR(8) << 8) | bsR(8)) << 8) | bsR(8)) << 8);
    }

    private char bsGetUByte() throws IOException {
        return (char) bsR(8);
    }

    private int bsR(int i2) throws IOException {
        int i3 = this.bsLive;
        int i4 = this.bsBuff;
        if (i3 < i2) {
            InputStream inputStream = this.in;
            do {
                int i5 = inputStream.read();
                if (i5 < 0) {
                    throw new IOException("unexpected end of stream");
                }
                i4 = (i4 << 8) | i5;
                i3 += 8;
            } while (i3 < i2);
            this.bsBuff = i4;
        }
        int i6 = i3 - i2;
        this.bsLive = i6;
        return ((1 << i2) - 1) & (i4 >> i6);
    }

    private boolean complete() throws IOException {
        int iBsGetInt = bsGetInt();
        this.storedCombinedCRC = iBsGetInt;
        this.currentState = 0;
        this.data = null;
        if (iBsGetInt == this.computedCombinedCRC) {
            return (this.decompressConcatenated && init(false)) ? false : true;
        }
        throw new IOException("BZip2 CRC error");
    }

    private void createHuffmanDecodingTables(int i2, int i3) {
        Data data = this.data;
        char[][] cArr = data.temp_charArray2d;
        int[] iArr = data.minLens;
        int[][] iArr2 = data.limit;
        int[][] iArr3 = data.base;
        int[][] iArr4 = data.perm;
        for (int i4 = 0; i4 < i3; i4++) {
            char[] cArr2 = cArr[i4];
            char c3 = ' ';
            int i5 = i2;
            char c4 = 0;
            while (true) {
                i5--;
                if (i5 >= 0) {
                    char c5 = cArr2[i5];
                    if (c5 > c4) {
                        c4 = c5;
                    }
                    if (c5 < c3) {
                        c3 = c5;
                    }
                }
            }
            hbCreateDecodeTables(iArr2[i4], iArr3[i4], iArr4[i4], cArr[i4], c3, c4, i2);
            iArr[i4] = c3;
        }
    }

    private void endBlock() throws IOException {
        int finalCRC = this.crc.getFinalCRC();
        this.computedBlockCRC = finalCRC;
        int i2 = this.storedBlockCRC;
        if (i2 == finalCRC) {
            int i3 = this.computedCombinedCRC;
            this.computedCombinedCRC = finalCRC ^ ((i3 >>> 31) | (i3 << 1));
        } else {
            int i4 = this.storedCombinedCRC;
            this.computedCombinedCRC = ((i4 >>> 31) | (i4 << 1)) ^ i2;
            throw new IOException("BZip2 CRC error");
        }
    }

    private void getAndMoveToFrontDecode() throws IOException {
        int i2;
        char c3;
        int i3;
        BZip2CompressorInputStream bZip2CompressorInputStream = this;
        bZip2CompressorInputStream.origPtr = bZip2CompressorInputStream.bsR(24);
        recvDecodingTables();
        InputStream inputStream = bZip2CompressorInputStream.in;
        Data data = bZip2CompressorInputStream.data;
        byte[] bArr = data.ll8;
        int[] iArr = data.unzftab;
        byte[] bArr2 = data.selector;
        byte[] bArr3 = data.seqToUnseq;
        char[] cArr = data.getAndMoveToFrontDecode_yy;
        int[] iArr2 = data.minLens;
        int[][] iArr3 = data.limit;
        int[][] iArr4 = data.base;
        int[][] iArr5 = data.perm;
        int i4 = bZip2CompressorInputStream.blockSize100k * 100000;
        int i5 = 256;
        while (true) {
            i5--;
            if (i5 < 0) {
                break;
            }
            cArr[i5] = (char) i5;
            iArr[i5] = 0;
        }
        int i6 = bZip2CompressorInputStream.nInUse + 1;
        int andMoveToFrontDecode0 = bZip2CompressorInputStream.getAndMoveToFrontDecode0(0);
        int i7 = bZip2CompressorInputStream.bsBuff;
        int i8 = bZip2CompressorInputStream.bsLive;
        int i9 = bArr2[0] & 255;
        int[] iArr6 = iArr4[i9];
        int[] iArr7 = iArr3[i9];
        int[] iArr8 = iArr5[i9];
        int i10 = 0;
        int i11 = i8;
        int i12 = andMoveToFrontDecode0;
        int i13 = 49;
        int i14 = -1;
        int i15 = iArr2[i9];
        int i16 = i7;
        while (i12 != i6) {
            int i17 = i6;
            int i18 = i16;
            if (i12 == 0 || i12 == 1) {
                byte[] bArr4 = bArr3;
                int i19 = i4;
                int i20 = 1;
                int i21 = -1;
                while (true) {
                    if (i12 == 0) {
                        i21 += i20;
                    } else if (i12 == 1) {
                        i21 += i20 << 1;
                    } else {
                        int[][] iArr9 = iArr5;
                        byte[] bArr5 = bArr2;
                        byte b3 = bArr4[cArr[0]];
                        int i22 = b3 & 255;
                        iArr[i22] = iArr[i22] + i21 + 1;
                        while (true) {
                            int i23 = i21 - 1;
                            if (i21 < 0) {
                                break;
                            }
                            i14++;
                            bArr[i14] = b3;
                            i21 = i23;
                        }
                        i4 = i19;
                        if (i14 >= i4) {
                            throw new IOException("block overrun");
                        }
                        bZip2CompressorInputStream = this;
                        i6 = i17;
                        i16 = i18;
                        bArr3 = bArr4;
                        iArr5 = iArr9;
                        bArr2 = bArr5;
                    }
                    if (i13 == 0) {
                        i10++;
                        int i24 = bArr2[i10] & 255;
                        iArr6 = iArr4[i24];
                        iArr7 = iArr3[i24];
                        iArr8 = iArr5[i24];
                        i2 = iArr2[i24];
                        i13 = 49;
                    } else {
                        i13--;
                        i2 = i15;
                    }
                    int i25 = i11;
                    while (i25 < i2) {
                        int i26 = inputStream.read();
                        if (i26 < 0) {
                            throw new IOException("unexpected end of stream");
                        }
                        i18 = (i18 << 8) | i26;
                        i25 += 8;
                    }
                    int i27 = i25 - i2;
                    int[][] iArr10 = iArr5;
                    i11 = i27;
                    int i28 = (i18 >> i27) & ((1 << i2) - 1);
                    int i29 = i2;
                    while (i28 > iArr7[i29]) {
                        int i30 = i29 + 1;
                        byte[] bArr6 = bArr2;
                        int i31 = i11;
                        while (i31 < 1) {
                            int i32 = inputStream.read();
                            if (i32 < 0) {
                                throw new IOException("unexpected end of stream");
                            }
                            i18 = (i18 << 8) | i32;
                            i31 += 8;
                        }
                        i11 = i31 - 1;
                        i28 = (i28 << 1) | ((i18 >> i11) & 1);
                        i29 = i30;
                        bArr2 = bArr6;
                    }
                    i12 = iArr8[i28 - iArr6[i29]];
                    i20 <<= 1;
                    i15 = i2;
                    iArr5 = iArr10;
                }
            } else {
                i14++;
                if (i14 >= i4) {
                    throw new IOException("block overrun");
                }
                int i33 = i12 - 1;
                char c4 = cArr[i33];
                int i34 = i4;
                byte b4 = bArr3[c4];
                byte[] bArr7 = bArr3;
                int i35 = b4 & 255;
                iArr[i35] = iArr[i35] + 1;
                bArr[i14] = b4;
                if (i12 <= 16) {
                    while (i33 > 0) {
                        int i36 = i33 - 1;
                        cArr[i33] = cArr[i36];
                        i33 = i36;
                    }
                    c3 = 0;
                } else {
                    c3 = 0;
                    System.arraycopy(cArr, 0, cArr, 1, i33);
                }
                cArr[c3] = c4;
                if (i13 == 0) {
                    i10++;
                    int i37 = bArr2[i10] & 255;
                    int[] iArr11 = iArr4[i37];
                    int[] iArr12 = iArr3[i37];
                    int[] iArr13 = iArr5[i37];
                    i3 = iArr2[i37];
                    iArr6 = iArr11;
                    iArr7 = iArr12;
                    iArr8 = iArr13;
                    i13 = 49;
                } else {
                    i13--;
                    i3 = i15;
                }
                int i38 = i11;
                while (i38 < i3) {
                    int i39 = inputStream.read();
                    if (i39 < 0) {
                        throw new IOException("unexpected end of stream");
                    }
                    i18 = (i18 << 8) | i39;
                    i38 += 8;
                }
                int i40 = i38 - i3;
                int i41 = 1;
                int i42 = (i18 >> i40) & ((1 << i3) - 1);
                i11 = i40;
                int i43 = i3;
                while (i42 > iArr7[i43]) {
                    i43++;
                    int i44 = i11;
                    while (i44 < i41) {
                        int i45 = inputStream.read();
                        if (i45 < 0) {
                            throw new IOException("unexpected end of stream");
                        }
                        i18 = (i18 << 8) | i45;
                        i44 += 8;
                        i41 = 1;
                    }
                    i11 = i44 - 1;
                    i42 = (i42 << 1) | ((i18 >> i11) & 1);
                    i41 = 1;
                }
                i12 = iArr8[i42 - iArr6[i43]];
                i15 = i3;
                i6 = i17;
                i16 = i18;
                i4 = i34;
                bArr3 = bArr7;
                bZip2CompressorInputStream = this;
            }
        }
        bZip2CompressorInputStream.last = i14;
        bZip2CompressorInputStream.bsLive = i11;
        bZip2CompressorInputStream.bsBuff = i16;
    }

    private int getAndMoveToFrontDecode0(int i2) throws IOException {
        InputStream inputStream = this.in;
        Data data = this.data;
        int i3 = data.selector[i2] & 255;
        int[] iArr = data.limit[i3];
        int i4 = data.minLens[i3];
        int iBsR = bsR(i4);
        int i5 = this.bsLive;
        int i6 = this.bsBuff;
        while (iBsR > iArr[i4]) {
            i4++;
            while (i5 < 1) {
                int i7 = inputStream.read();
                if (i7 < 0) {
                    throw new IOException("unexpected end of stream");
                }
                i6 = (i6 << 8) | i7;
                i5 += 8;
            }
            i5--;
            iBsR = (iBsR << 1) | (1 & (i6 >> i5));
        }
        this.bsLive = i5;
        this.bsBuff = i6;
        return data.perm[i3][iBsR - data.base[i3][i4]];
    }

    private static void hbCreateDecodeTables(int[] iArr, int[] iArr2, int[] iArr3, char[] cArr, int i2, int i3, int i4) {
        int i5 = 0;
        int i6 = 0;
        for (int i7 = i2; i7 <= i3; i7++) {
            for (int i8 = 0; i8 < i4; i8++) {
                if (cArr[i8] == i7) {
                    iArr3[i6] = i8;
                    i6++;
                }
            }
        }
        int i9 = 23;
        while (true) {
            i9--;
            if (i9 <= 0) {
                break;
            }
            iArr2[i9] = 0;
            iArr[i9] = 0;
        }
        for (int i10 = 0; i10 < i4; i10++) {
            int i11 = cArr[i10] + 1;
            iArr2[i11] = iArr2[i11] + 1;
        }
        int i12 = iArr2[0];
        for (int i13 = 1; i13 < 23; i13++) {
            i12 += iArr2[i13];
            iArr2[i13] = i12;
        }
        int i14 = iArr2[i2];
        int i15 = i2;
        while (i15 <= i3) {
            int i16 = i15 + 1;
            int i17 = iArr2[i16];
            int i18 = i5 + (i17 - i14);
            iArr[i15] = i18 - 1;
            i5 = i18 << 1;
            i15 = i16;
            i14 = i17;
        }
        for (int i19 = i2 + 1; i19 <= i3; i19++) {
            iArr2[i19] = ((iArr[i19 - 1] + 1) << 1) - iArr2[i19];
        }
    }

    private boolean init(boolean z2) throws IOException {
        InputStream inputStream = this.in;
        if (inputStream == null) {
            throw new IOException("No InputStream");
        }
        int i2 = inputStream.read();
        if (i2 == -1 && !z2) {
            return false;
        }
        int i3 = this.in.read();
        int i4 = this.in.read();
        if (i2 != 66 || i3 != 90 || i4 != 104) {
            throw new IOException(z2 ? "Stream is not in the BZip2 format" : "Garbage after a valid BZip2 stream");
        }
        int i5 = this.in.read();
        if (i5 < 49 || i5 > 57) {
            throw new IOException("BZip2 block size is invalid");
        }
        this.blockSize100k = i5 - 48;
        this.bsLive = 0;
        this.computedCombinedCRC = 0;
        return true;
    }

    private void initBlock() throws IOException {
        do {
            char cBsGetUByte = bsGetUByte();
            char cBsGetUByte2 = bsGetUByte();
            char cBsGetUByte3 = bsGetUByte();
            char cBsGetUByte4 = bsGetUByte();
            char cBsGetUByte5 = bsGetUByte();
            char cBsGetUByte6 = bsGetUByte();
            if (cBsGetUByte != 23 || cBsGetUByte2 != 'r' || cBsGetUByte3 != 'E' || cBsGetUByte4 != '8' || cBsGetUByte5 != 'P' || cBsGetUByte6 != 144) {
                if (cBsGetUByte != '1' || cBsGetUByte2 != 'A' || cBsGetUByte3 != 'Y' || cBsGetUByte4 != '&' || cBsGetUByte5 != 'S' || cBsGetUByte6 != 'Y') {
                    this.currentState = 0;
                    throw new IOException("bad block header");
                }
                this.storedBlockCRC = bsGetInt();
                this.blockRandomised = bsR(1) == 1;
                if (this.data == null) {
                    this.data = new Data(this.blockSize100k);
                }
                getAndMoveToFrontDecode();
                this.crc.initialiseCRC();
                this.currentState = 1;
                return;
            }
        } while (!complete());
    }

    private void makeMaps() {
        Data data = this.data;
        boolean[] zArr = data.inUse;
        byte[] bArr = data.seqToUnseq;
        int i2 = 0;
        for (int i3 = 0; i3 < 256; i3++) {
            if (zArr[i3]) {
                bArr[i2] = (byte) i3;
                i2++;
            }
        }
        this.nInUse = i2;
    }

    public static boolean matches(byte[] bArr, int i2) {
        return i2 >= 3 && bArr[0] == 66 && bArr[1] == 90 && bArr[2] == 104;
    }

    private int read0() throws IOException {
        switch (this.currentState) {
            case 0:
                return -1;
            case 1:
                return setupBlock();
            case 2:
                throw new IllegalStateException();
            case 3:
                return setupRandPartB();
            case 4:
                return setupRandPartC();
            case 5:
                throw new IllegalStateException();
            case 6:
                return setupNoRandPartB();
            case 7:
                return setupNoRandPartC();
            default:
                throw new IllegalStateException();
        }
    }

    private void recvDecodingTables() throws IOException {
        Data data = this.data;
        boolean[] zArr = data.inUse;
        byte[] bArr = data.recvDecodingTables_pos;
        byte[] bArr2 = data.selector;
        byte[] bArr3 = data.selectorMtf;
        int i2 = 0;
        for (int i3 = 0; i3 < 16; i3++) {
            if (bsGetBit()) {
                i2 |= 1 << i3;
            }
        }
        int i4 = 256;
        while (true) {
            i4--;
            if (i4 < 0) {
                break;
            } else {
                zArr[i4] = false;
            }
        }
        for (int i5 = 0; i5 < 16; i5++) {
            if (((1 << i5) & i2) != 0) {
                int i6 = i5 << 4;
                for (int i7 = 0; i7 < 16; i7++) {
                    if (bsGetBit()) {
                        zArr[i6 + i7] = true;
                    }
                }
            }
        }
        makeMaps();
        int i8 = this.nInUse + 2;
        int iBsR = bsR(3);
        int iBsR2 = bsR(15);
        for (int i9 = 0; i9 < iBsR2; i9++) {
            int i10 = 0;
            while (bsGetBit()) {
                i10++;
            }
            bArr3[i9] = (byte) i10;
        }
        int i11 = iBsR;
        while (true) {
            i11--;
            if (i11 < 0) {
                break;
            } else {
                bArr[i11] = (byte) i11;
            }
        }
        for (int i12 = 0; i12 < iBsR2; i12++) {
            int i13 = bArr3[i12] & 255;
            byte b3 = bArr[i13];
            while (i13 > 0) {
                bArr[i13] = bArr[i13 - 1];
                i13--;
            }
            bArr[0] = b3;
            bArr2[i12] = b3;
        }
        char[][] cArr = data.temp_charArray2d;
        for (int i14 = 0; i14 < iBsR; i14++) {
            int iBsR3 = bsR(5);
            char[] cArr2 = cArr[i14];
            for (int i15 = 0; i15 < i8; i15++) {
                while (bsGetBit()) {
                    iBsR3 += bsGetBit() ? -1 : 1;
                }
                cArr2[i15] = (char) iBsR3;
            }
        }
        createHuffmanDecodingTables(i8, iBsR);
    }

    private int setupBlock() throws IOException {
        Data data;
        if (this.currentState == 0 || (data = this.data) == null) {
            return -1;
        }
        int[] iArr = data.cftab;
        int[] iArrInitTT = data.initTT(this.last + 1);
        Data data2 = this.data;
        byte[] bArr = data2.ll8;
        iArr[0] = 0;
        System.arraycopy(data2.unzftab, 0, iArr, 1, 256);
        int i2 = iArr[0];
        for (int i3 = 1; i3 <= 256; i3++) {
            i2 += iArr[i3];
            iArr[i3] = i2;
        }
        int i4 = this.last;
        for (int i5 = 0; i5 <= i4; i5++) {
            int i6 = bArr[i5] & 255;
            int i7 = iArr[i6];
            iArr[i6] = i7 + 1;
            iArrInitTT[i7] = i5;
        }
        int i8 = this.origPtr;
        if (i8 < 0 || i8 >= iArrInitTT.length) {
            throw new IOException("stream corrupted");
        }
        this.su_tPos = iArrInitTT[i8];
        this.su_count = 0;
        this.su_i2 = 0;
        this.su_ch2 = 256;
        if (!this.blockRandomised) {
            return setupNoRandPartA();
        }
        this.su_rNToGo = 0;
        this.su_rTPos = 0;
        return setupRandPartA();
    }

    private int setupNoRandPartA() throws IOException {
        int i2 = this.su_i2;
        if (i2 > this.last) {
            this.currentState = 5;
            endBlock();
            initBlock();
            return setupBlock();
        }
        this.su_chPrev = this.su_ch2;
        Data data = this.data;
        byte[] bArr = data.ll8;
        int i3 = this.su_tPos;
        int i4 = bArr[i3] & 255;
        this.su_ch2 = i4;
        this.su_tPos = data.tt[i3];
        this.su_i2 = i2 + 1;
        this.currentState = 6;
        this.crc.updateCRC(i4);
        return i4;
    }

    private int setupNoRandPartB() throws IOException {
        if (this.su_ch2 != this.su_chPrev) {
            this.su_count = 1;
            return setupNoRandPartA();
        }
        int i2 = this.su_count + 1;
        this.su_count = i2;
        if (i2 < 4) {
            return setupNoRandPartA();
        }
        Data data = this.data;
        byte[] bArr = data.ll8;
        int i3 = this.su_tPos;
        this.su_z = (char) (bArr[i3] & 255);
        this.su_tPos = data.tt[i3];
        this.su_j2 = 0;
        return setupNoRandPartC();
    }

    private int setupNoRandPartC() throws IOException {
        if (this.su_j2 >= this.su_z) {
            this.su_i2++;
            this.su_count = 0;
            return setupNoRandPartA();
        }
        int i2 = this.su_ch2;
        this.crc.updateCRC(i2);
        this.su_j2++;
        this.currentState = 7;
        return i2;
    }

    private int setupRandPartA() throws IOException {
        if (this.su_i2 > this.last) {
            endBlock();
            initBlock();
            return setupBlock();
        }
        this.su_chPrev = this.su_ch2;
        Data data = this.data;
        byte[] bArr = data.ll8;
        int i2 = this.su_tPos;
        int i3 = bArr[i2] & 255;
        this.su_tPos = data.tt[i2];
        int i4 = this.su_rNToGo;
        if (i4 == 0) {
            this.su_rNToGo = Rand.rNums(this.su_rTPos) - 1;
            int i5 = this.su_rTPos + 1;
            this.su_rTPos = i5;
            if (i5 == 512) {
                this.su_rTPos = 0;
            }
        } else {
            this.su_rNToGo = i4 - 1;
        }
        int i6 = i3 ^ (this.su_rNToGo == 1 ? 1 : 0);
        this.su_ch2 = i6;
        this.su_i2++;
        this.currentState = 3;
        this.crc.updateCRC(i6);
        return i6;
    }

    private int setupRandPartB() throws IOException {
        if (this.su_ch2 != this.su_chPrev) {
            this.currentState = 2;
            this.su_count = 1;
            return setupRandPartA();
        }
        int i2 = this.su_count + 1;
        this.su_count = i2;
        if (i2 < 4) {
            this.currentState = 2;
            return setupRandPartA();
        }
        Data data = this.data;
        byte[] bArr = data.ll8;
        int i3 = this.su_tPos;
        this.su_z = (char) (bArr[i3] & 255);
        this.su_tPos = data.tt[i3];
        int i4 = this.su_rNToGo;
        if (i4 == 0) {
            this.su_rNToGo = Rand.rNums(this.su_rTPos) - 1;
            int i5 = this.su_rTPos + 1;
            this.su_rTPos = i5;
            if (i5 == 512) {
                this.su_rTPos = 0;
            }
        } else {
            this.su_rNToGo = i4 - 1;
        }
        this.su_j2 = 0;
        this.currentState = 4;
        if (this.su_rNToGo == 1) {
            this.su_z = (char) (this.su_z ^ 1);
        }
        return setupRandPartC();
    }

    private int setupRandPartC() throws IOException {
        if (this.su_j2 < this.su_z) {
            this.crc.updateCRC(this.su_ch2);
            this.su_j2++;
            return this.su_ch2;
        }
        this.currentState = 2;
        this.su_i2++;
        this.su_count = 0;
        return setupRandPartA();
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        InputStream inputStream = this.in;
        if (inputStream != null) {
            try {
                if (inputStream != System.in) {
                    inputStream.close();
                }
            } finally {
                this.data = null;
                this.in = null;
            }
        }
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (this.in == null) {
            throw new IOException("stream closed");
        }
        int i2 = read0();
        count(i2 < 0 ? -1 : 1);
        return i2;
    }

    public BZip2CompressorInputStream(InputStream inputStream, boolean z2) throws IOException {
        this.crc = new CRC();
        this.currentState = 1;
        this.in = inputStream;
        this.decompressConcatenated = z2;
        init(true);
        initBlock();
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        if (i2 < 0) {
            throw new IndexOutOfBoundsException("offs(" + i2 + ") < 0.");
        }
        if (i3 >= 0) {
            int i4 = i2 + i3;
            if (i4 <= bArr.length) {
                if (this.in == null) {
                    throw new IOException("stream closed");
                }
                if (i3 == 0) {
                    return 0;
                }
                int i5 = i2;
                while (i5 < i4) {
                    int i6 = read0();
                    if (i6 < 0) {
                        break;
                    }
                    bArr[i5] = (byte) i6;
                    count(1);
                    i5++;
                }
                if (i5 == i2) {
                    return -1;
                }
                return i5 - i2;
            }
            throw new IndexOutOfBoundsException("offs(" + i2 + ") + len(" + i3 + ") > dest.length(" + bArr.length + ").");
        }
        throw new IndexOutOfBoundsException("len(" + i3 + ") < 0.");
    }
}
