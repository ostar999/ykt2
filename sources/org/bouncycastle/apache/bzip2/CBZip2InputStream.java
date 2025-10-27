package org.bouncycastle.apache.bzip2;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;

/* loaded from: classes9.dex */
public class CBZip2InputStream extends InputStream implements BZip2Constants {
    private static final int NO_RAND_PART_A_STATE = 5;
    private static final int NO_RAND_PART_B_STATE = 6;
    private static final int NO_RAND_PART_C_STATE = 7;
    private static final int RAND_PART_A_STATE = 2;
    private static final int RAND_PART_B_STATE = 3;
    private static final int RAND_PART_C_STATE = 4;
    private static final int START_BLOCK_STATE = 1;
    private int[][] base;
    private boolean blockRandomised;
    private int blockSize100k;
    private int bsBuff;
    private int bsLive;
    private InputStream bsStream;
    int ch2;
    int chPrev;
    private int computedBlockCRC;
    private int computedCombinedCRC;
    int count;
    private int currentChar;
    private int currentState;

    /* renamed from: i, reason: collision with root package name */
    int f27757i;
    int i2;
    int j2;
    private int last;
    private int[][] limit;
    private char[] ll8;
    private int[] minLens;
    private int nInUse;
    private int origPtr;
    private int[][] perm;
    int rNToGo;
    int rTPos;
    private int storedBlockCRC;
    private int storedCombinedCRC;
    private boolean streamEnd;
    int tPos;
    private int[] tt;

    /* renamed from: z, reason: collision with root package name */
    char f27758z;
    private CRC mCrc = new CRC();
    private boolean[] inUse = new boolean[256];
    private char[] seqToUnseq = new char[256];
    private char[] unseqToSeq = new char[256];
    private char[] selector = new char[18002];
    private char[] selectorMtf = new char[18002];
    private int[] unzftab = new int[256];

    public CBZip2InputStream(InputStream inputStream) throws IOException {
        Class cls = Integer.TYPE;
        this.limit = (int[][]) Array.newInstance((Class<?>) cls, 6, 258);
        this.base = (int[][]) Array.newInstance((Class<?>) cls, 6, 258);
        this.perm = (int[][]) Array.newInstance((Class<?>) cls, 6, 258);
        this.minLens = new int[6];
        this.streamEnd = false;
        this.currentChar = -1;
        this.currentState = 1;
        this.rNToGo = 0;
        this.rTPos = 0;
        this.ll8 = null;
        this.tt = null;
        bsSetStream(inputStream);
        initialize();
        initBlock();
        setupBlock();
    }

    private static void badBlockHeader() {
        cadvise();
    }

    private static void blockOverrun() {
        cadvise();
    }

    private void bsFinishedWithStream() throws IOException {
        try {
            InputStream inputStream = this.bsStream;
            if (inputStream == null || inputStream == System.in) {
                return;
            }
            inputStream.close();
            this.bsStream = null;
        } catch (IOException unused) {
        }
    }

    private int bsGetInt32() {
        return bsGetint();
    }

    private int bsGetIntVS(int i2) {
        return bsR(i2);
    }

    private char bsGetUChar() {
        return (char) bsR(8);
    }

    private int bsGetint() {
        return bsR(8) | ((((((bsR(8) | 0) << 8) | bsR(8)) << 8) | bsR(8)) << 8);
    }

    private int bsR(int i2) {
        char c3;
        while (true) {
            int i3 = this.bsLive;
            if (i3 >= i2) {
                int i4 = (this.bsBuff >> (i3 - i2)) & ((1 << i2) - 1);
                this.bsLive = i3 - i2;
                return i4;
            }
            try {
                c3 = (char) this.bsStream.read();
            } catch (IOException unused) {
                compressedStreamEOF();
                c3 = 0;
            }
            if (c3 == 65535) {
                compressedStreamEOF();
            }
            this.bsBuff = (c3 & 255) | (this.bsBuff << 8);
            this.bsLive += 8;
        }
    }

    private void bsSetStream(InputStream inputStream) {
        this.bsStream = inputStream;
        this.bsLive = 0;
        this.bsBuff = 0;
    }

    private static void cadvise() {
        System.out.println("CRC Error");
    }

    private void complete() throws IOException {
        int iBsGetInt32 = bsGetInt32();
        this.storedCombinedCRC = iBsGetInt32;
        if (iBsGetInt32 != this.computedCombinedCRC) {
            crcError();
        }
        bsFinishedWithStream();
        this.streamEnd = true;
    }

    private static void compressedStreamEOF() {
        cadvise();
    }

    private static void crcError() {
        cadvise();
    }

    private void endBlock() {
        int finalCRC = this.mCrc.getFinalCRC();
        this.computedBlockCRC = finalCRC;
        if (this.storedBlockCRC != finalCRC) {
            crcError();
        }
        int i2 = this.computedCombinedCRC;
        this.computedCombinedCRC = ((i2 >>> 31) | (i2 << 1)) ^ this.computedBlockCRC;
    }

    /* JADX WARN: Removed duplicated region for block: B:61:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0163  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void getAndMoveToFrontDecode() {
        /*
            Method dump skipped, instructions count: 475
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.apache.bzip2.CBZip2InputStream.getAndMoveToFrontDecode():void");
    }

    private void hbCreateDecodeTables(int[] iArr, int[] iArr2, int[] iArr3, char[] cArr, int i2, int i3, int i4) {
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
        for (int i9 = 0; i9 < 23; i9++) {
            iArr2[i9] = 0;
        }
        for (int i10 = 0; i10 < i4; i10++) {
            int i11 = cArr[i10] + 1;
            iArr2[i11] = iArr2[i11] + 1;
        }
        for (int i12 = 1; i12 < 23; i12++) {
            iArr2[i12] = iArr2[i12] + iArr2[i12 - 1];
        }
        for (int i13 = 0; i13 < 23; i13++) {
            iArr[i13] = 0;
        }
        int i14 = i2;
        while (i14 <= i3) {
            int i15 = i14 + 1;
            int i16 = i5 + (iArr2[i15] - iArr2[i14]);
            iArr[i14] = i16 - 1;
            i5 = i16 << 1;
            i14 = i15;
        }
        for (int i17 = i2 + 1; i17 <= i3; i17++) {
            iArr2[i17] = ((iArr[i17 - 1] + 1) << 1) - iArr2[i17];
        }
    }

    private void initBlock() throws IOException {
        char cBsGetUChar = bsGetUChar();
        char cBsGetUChar2 = bsGetUChar();
        char cBsGetUChar3 = bsGetUChar();
        char cBsGetUChar4 = bsGetUChar();
        char cBsGetUChar5 = bsGetUChar();
        char cBsGetUChar6 = bsGetUChar();
        if (cBsGetUChar == 23 && cBsGetUChar2 == 'r' && cBsGetUChar3 == 'E' && cBsGetUChar4 == '8' && cBsGetUChar5 == 'P' && cBsGetUChar6 == 144) {
            complete();
            return;
        }
        if (cBsGetUChar != '1' || cBsGetUChar2 != 'A' || cBsGetUChar3 != 'Y' || cBsGetUChar4 != '&' || cBsGetUChar5 != 'S' || cBsGetUChar6 != 'Y') {
            badBlockHeader();
            this.streamEnd = true;
            return;
        }
        this.storedBlockCRC = bsGetInt32();
        if (bsR(1) == 1) {
            this.blockRandomised = true;
        } else {
            this.blockRandomised = false;
        }
        getAndMoveToFrontDecode();
        this.mCrc.initialiseCRC();
        this.currentState = 1;
    }

    private void initialize() throws IOException {
        char cBsGetUChar = bsGetUChar();
        char cBsGetUChar2 = bsGetUChar();
        if (cBsGetUChar != 'B' && cBsGetUChar2 != 'Z') {
            throw new IOException("Not a BZIP2 marked stream");
        }
        char cBsGetUChar3 = bsGetUChar();
        char cBsGetUChar4 = bsGetUChar();
        if (cBsGetUChar3 != 'h' || cBsGetUChar4 < '1' || cBsGetUChar4 > '9') {
            bsFinishedWithStream();
            this.streamEnd = true;
        } else {
            setDecompressStructureSizes(cBsGetUChar4 - '0');
            this.computedCombinedCRC = 0;
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

    private void recvDecodingTables() {
        char[][] cArr = (char[][]) Array.newInstance((Class<?>) Character.TYPE, 6, 258);
        boolean[] zArr = new boolean[16];
        for (int i2 = 0; i2 < 16; i2++) {
            if (bsR(1) == 1) {
                zArr[i2] = true;
            } else {
                zArr[i2] = false;
            }
        }
        for (int i3 = 0; i3 < 256; i3++) {
            this.inUse[i3] = false;
        }
        for (int i4 = 0; i4 < 16; i4++) {
            if (zArr[i4]) {
                for (int i5 = 0; i5 < 16; i5++) {
                    if (bsR(1) == 1) {
                        this.inUse[(i4 * 16) + i5] = true;
                    }
                }
            }
        }
        makeMaps();
        int i6 = this.nInUse + 2;
        int iBsR = bsR(3);
        int iBsR2 = bsR(15);
        for (int i7 = 0; i7 < iBsR2; i7++) {
            int i8 = 0;
            while (bsR(1) == 1) {
                i8++;
            }
            this.selectorMtf[i7] = (char) i8;
        }
        char[] cArr2 = new char[6];
        for (char c3 = 0; c3 < iBsR; c3 = (char) (c3 + 1)) {
            cArr2[c3] = c3;
        }
        for (int i9 = 0; i9 < iBsR2; i9++) {
            char c4 = this.selectorMtf[i9];
            char c5 = cArr2[c4];
            while (c4 > 0) {
                int i10 = c4 - 1;
                cArr2[c4] = cArr2[i10];
                c4 = (char) i10;
            }
            cArr2[0] = c5;
            this.selector[i9] = c5;
        }
        for (int i11 = 0; i11 < iBsR; i11++) {
            int iBsR3 = bsR(5);
            for (int i12 = 0; i12 < i6; i12++) {
                while (bsR(1) == 1) {
                    iBsR3 = bsR(1) == 0 ? iBsR3 + 1 : iBsR3 - 1;
                }
                cArr[i11][i12] = (char) iBsR3;
            }
        }
        for (int i13 = 0; i13 < iBsR; i13++) {
            char c6 = ' ';
            char c7 = 0;
            for (int i14 = 0; i14 < i6; i14++) {
                char c8 = cArr[i13][i14];
                if (c8 > c7) {
                    c7 = c8;
                }
                if (c8 < c6) {
                    c6 = c8;
                }
            }
            hbCreateDecodeTables(this.limit[i13], this.base[i13], this.perm[i13], cArr[i13], c6, c7, i6);
            this.minLens[i13] = c6;
        }
    }

    private void setDecompressStructureSizes(int i2) {
        if (i2 >= 0) {
        }
        this.blockSize100k = i2;
        if (i2 == 0) {
            return;
        }
        int i3 = i2 * 100000;
        this.ll8 = new char[i3];
        this.tt = new int[i3];
    }

    private void setupBlock() throws IOException {
        int[] iArr = new int[257];
        iArr[0] = 0;
        this.f27757i = 1;
        while (true) {
            int i2 = this.f27757i;
            if (i2 > 256) {
                break;
            }
            iArr[i2] = this.unzftab[i2 - 1];
            this.f27757i = i2 + 1;
        }
        this.f27757i = 1;
        while (true) {
            int i3 = this.f27757i;
            if (i3 > 256) {
                break;
            }
            iArr[i3] = iArr[i3] + iArr[i3 - 1];
            this.f27757i = i3 + 1;
        }
        this.f27757i = 0;
        while (true) {
            int i4 = this.f27757i;
            if (i4 > this.last) {
                break;
            }
            char c3 = this.ll8[i4];
            this.tt[iArr[c3]] = i4;
            iArr[c3] = iArr[c3] + 1;
            this.f27757i = i4 + 1;
        }
        this.tPos = this.tt[this.origPtr];
        this.count = 0;
        this.i2 = 0;
        this.ch2 = 256;
        if (!this.blockRandomised) {
            setupNoRandPartA();
            return;
        }
        this.rNToGo = 0;
        this.rTPos = 0;
        setupRandPartA();
    }

    private void setupNoRandPartA() throws IOException {
        int i2 = this.i2;
        if (i2 > this.last) {
            endBlock();
            initBlock();
            setupBlock();
            return;
        }
        this.chPrev = this.ch2;
        char[] cArr = this.ll8;
        int i3 = this.tPos;
        char c3 = cArr[i3];
        this.ch2 = c3;
        this.tPos = this.tt[i3];
        this.i2 = i2 + 1;
        this.currentChar = c3;
        this.currentState = 6;
        this.mCrc.updateCRC(c3);
    }

    private void setupNoRandPartB() throws IOException {
        if (this.ch2 != this.chPrev) {
            this.currentState = 5;
            this.count = 1;
        } else {
            int i2 = this.count + 1;
            this.count = i2;
            if (i2 >= 4) {
                char[] cArr = this.ll8;
                int i3 = this.tPos;
                this.f27758z = cArr[i3];
                this.tPos = this.tt[i3];
                this.currentState = 7;
                this.j2 = 0;
                setupNoRandPartC();
                return;
            }
            this.currentState = 5;
        }
        setupNoRandPartA();
    }

    private void setupNoRandPartC() throws IOException {
        if (this.j2 < this.f27758z) {
            int i2 = this.ch2;
            this.currentChar = i2;
            this.mCrc.updateCRC(i2);
            this.j2++;
            return;
        }
        this.currentState = 5;
        this.i2++;
        this.count = 0;
        setupNoRandPartA();
    }

    private void setupRandPartA() throws IOException {
        int i2 = this.i2;
        if (i2 > this.last) {
            endBlock();
            initBlock();
            setupBlock();
            return;
        }
        this.chPrev = this.ch2;
        char[] cArr = this.ll8;
        int i3 = this.tPos;
        char c3 = cArr[i3];
        this.ch2 = c3;
        this.tPos = this.tt[i3];
        if (this.rNToGo == 0) {
            int[] iArr = BZip2Constants.rNums;
            int i4 = this.rTPos;
            this.rNToGo = iArr[i4];
            int i5 = i4 + 1;
            this.rTPos = i5;
            if (i5 == 512) {
                this.rTPos = 0;
            }
        }
        int i6 = this.rNToGo - 1;
        this.rNToGo = i6;
        int i7 = c3 ^ (i6 == 1 ? (char) 1 : (char) 0);
        this.ch2 = i7;
        this.i2 = i2 + 1;
        this.currentChar = i7;
        this.currentState = 3;
        this.mCrc.updateCRC(i7);
    }

    private void setupRandPartB() throws IOException {
        if (this.ch2 != this.chPrev) {
            this.currentState = 2;
            this.count = 1;
        } else {
            int i2 = this.count + 1;
            this.count = i2;
            if (i2 >= 4) {
                char[] cArr = this.ll8;
                int i3 = this.tPos;
                char c3 = cArr[i3];
                this.f27758z = c3;
                this.tPos = this.tt[i3];
                if (this.rNToGo == 0) {
                    int[] iArr = BZip2Constants.rNums;
                    int i4 = this.rTPos;
                    this.rNToGo = iArr[i4];
                    int i5 = i4 + 1;
                    this.rTPos = i5;
                    if (i5 == 512) {
                        this.rTPos = 0;
                    }
                }
                int i6 = this.rNToGo - 1;
                this.rNToGo = i6;
                this.f27758z = (char) (c3 ^ (i6 != 1 ? (char) 0 : (char) 1));
                this.j2 = 0;
                this.currentState = 4;
                setupRandPartC();
                return;
            }
            this.currentState = 2;
        }
        setupRandPartA();
    }

    private void setupRandPartC() throws IOException {
        if (this.j2 < this.f27758z) {
            int i2 = this.ch2;
            this.currentChar = i2;
            this.mCrc.updateCRC(i2);
            this.j2++;
            return;
        }
        this.currentState = 2;
        this.i2++;
        this.count = 0;
        setupRandPartA();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (this.streamEnd) {
            return -1;
        }
        int i2 = this.currentChar;
        int i3 = this.currentState;
        if (i3 == 3) {
            setupRandPartB();
        } else if (i3 == 4) {
            setupRandPartC();
        } else if (i3 == 6) {
            setupNoRandPartB();
        } else if (i3 == 7) {
            setupNoRandPartC();
        }
        return i2;
    }
}
