package org.bouncycastle.crypto.digests;

import com.yikaobang.yixue.R2;
import org.bouncycastle.crypto.ExtendedDigest;
import org.bouncycastle.util.Arrays;

/* loaded from: classes9.dex */
public final class WhirlpoolDigest implements ExtendedDigest {
    private static final int BITCOUNT_ARRAY_SIZE = 32;
    private static final int BYTE_LENGTH = 64;
    private static final int DIGEST_LENGTH_BYTES = 64;
    private static final short[] EIGHT;
    private static final int REDUCTION_POLYNOMIAL = 285;
    private static final int ROUNDS = 10;
    private long[] _K;
    private long[] _L;
    private short[] _bitCount;
    private long[] _block;
    private byte[] _buffer;
    private int _bufferPos;
    private long[] _hash;
    private final long[] _rc;
    private long[] _state;
    private static final int[] SBOX = {24, 35, 198, 232, 135, 184, 1, 79, 54, 166, 210, R2.attr.actionModeCloseDrawable, 121, 111, 145, 82, 96, 188, 155, 142, 163, 12, 123, 53, 29, 224, 215, R2.array.ease_numbers_file_suffix, 46, 75, 254, 87, 21, 119, 55, 229, 159, 240, 74, 218, 88, 201, 41, 10, 177, 160, 107, 133, 189, 93, 16, 244, 203, 62, 5, 103, 228, 39, 65, 139, 167, 125, 149, 216, R2.attr.actionModeSelectAllDrawable, 238, 124, 102, 221, 23, 71, 158, 202, 45, R2.array.ease_file_file_suffix, 7, R2.anim.window_bottom_out, 90, 131, 51, 99, 2, R2.anim.welcome_loading, 113, 200, 25, 73, 217, 242, 227, 91, 136, 154, 38, 50, 176, 233, 15, 213, 128, R2.array.ease_excel_file_suffix, 205, 52, 72, 255, 122, 144, 95, 32, 104, 26, R2.anim.window_ios_in, 180, 84, 147, 34, 100, 241, 115, 18, 64, 8, R2.array.ease_other_file_suffix, 236, 219, 161, 141, 61, 151, 0, 207, 43, 118, 130, 214, 27, 181, R2.anim.window_ios_out, 106, 80, 69, 243, 48, 239, 63, 85, 162, 234, 101, 186, 47, 192, 222, 28, R2.attr.actionModeSplitBackground, 77, 146, 117, 6, 138, 178, 230, 14, 31, 98, 212, R2.anim.voice_from_icon, 150, R2.attr.actionModePasteDrawable, R2.array.ease_pdf_file_suffix, 37, 89, 132, 114, 57, 76, 94, 120, 56, 140, 209, 165, 226, 97, 179, 33, 156, 30, 67, 199, R2.attr.actionModeShareDrawable, 4, 81, 153, 109, 13, 250, 223, 126, 36, 59, R2.anim.widget_zoom_in, 206, 17, 143, 78, 183, 235, 60, 129, 148, R2.attr.actionModeCutDrawable, 185, 19, 44, 211, 231, 110, R2.array.ease_pages_file_suffix, 3, 86, 68, 127, 169, 42, 187, 193, 83, 220, 11, 157, 108, 49, 116, R2.attr.actionModeCopyDrawable, 70, 172, 137, 20, 225, 22, 58, 105, 9, 112, 182, 208, 237, 204, 66, 152, 164, 40, 92, R2.attr.actionModeFindDrawable, 134};
    private static final long[] C0 = new long[256];
    private static final long[] C1 = new long[256];
    private static final long[] C2 = new long[256];
    private static final long[] C3 = new long[256];
    private static final long[] C4 = new long[256];
    private static final long[] C5 = new long[256];
    private static final long[] C6 = new long[256];
    private static final long[] C7 = new long[256];

    static {
        short[] sArr = new short[32];
        EIGHT = sArr;
        sArr[31] = 8;
    }

    public WhirlpoolDigest() {
        this._rc = new long[11];
        this._buffer = new byte[64];
        this._bufferPos = 0;
        this._bitCount = new short[32];
        this._hash = new long[8];
        this._K = new long[8];
        this._L = new long[8];
        this._block = new long[8];
        this._state = new long[8];
        for (int i2 = 0; i2 < 256; i2++) {
            int i3 = SBOX[i2];
            int iMaskWithReductionPolynomial = maskWithReductionPolynomial(i3 << 1);
            int iMaskWithReductionPolynomial2 = maskWithReductionPolynomial(iMaskWithReductionPolynomial << 1);
            int i4 = iMaskWithReductionPolynomial2 ^ i3;
            int iMaskWithReductionPolynomial3 = maskWithReductionPolynomial(iMaskWithReductionPolynomial2 << 1);
            int i5 = iMaskWithReductionPolynomial3 ^ i3;
            C0[i2] = packIntoLong(i3, i3, iMaskWithReductionPolynomial2, i3, iMaskWithReductionPolynomial3, i4, iMaskWithReductionPolynomial, i5);
            C1[i2] = packIntoLong(i5, i3, i3, iMaskWithReductionPolynomial2, i3, iMaskWithReductionPolynomial3, i4, iMaskWithReductionPolynomial);
            C2[i2] = packIntoLong(iMaskWithReductionPolynomial, i5, i3, i3, iMaskWithReductionPolynomial2, i3, iMaskWithReductionPolynomial3, i4);
            C3[i2] = packIntoLong(i4, iMaskWithReductionPolynomial, i5, i3, i3, iMaskWithReductionPolynomial2, i3, iMaskWithReductionPolynomial3);
            C4[i2] = packIntoLong(iMaskWithReductionPolynomial3, i4, iMaskWithReductionPolynomial, i5, i3, i3, iMaskWithReductionPolynomial2, i3);
            C5[i2] = packIntoLong(i3, iMaskWithReductionPolynomial3, i4, iMaskWithReductionPolynomial, i5, i3, i3, iMaskWithReductionPolynomial2);
            C6[i2] = packIntoLong(iMaskWithReductionPolynomial2, i3, iMaskWithReductionPolynomial3, i4, iMaskWithReductionPolynomial, i5, i3, i3);
            C7[i2] = packIntoLong(i3, iMaskWithReductionPolynomial2, i3, iMaskWithReductionPolynomial3, i4, iMaskWithReductionPolynomial, i5, i3);
        }
        this._rc[0] = 0;
        for (int i6 = 1; i6 <= 10; i6++) {
            int i7 = (i6 - 1) * 8;
            this._rc[i6] = (((((((C0[i7] & (-72057594037927936L)) ^ (C1[i7 + 1] & 71776119061217280L)) ^ (C2[i7 + 2] & 280375465082880L)) ^ (C3[i7 + 3] & 1095216660480L)) ^ (C4[i7 + 4] & 4278190080L)) ^ (C5[i7 + 5] & 16711680)) ^ (C6[i7 + 6] & 65280)) ^ (C7[i7 + 7] & 255);
        }
    }

    public WhirlpoolDigest(WhirlpoolDigest whirlpoolDigest) {
        long[] jArr = new long[11];
        this._rc = jArr;
        this._buffer = new byte[64];
        this._bufferPos = 0;
        this._bitCount = new short[32];
        this._hash = new long[8];
        this._K = new long[8];
        this._L = new long[8];
        this._block = new long[8];
        this._state = new long[8];
        System.arraycopy(whirlpoolDigest._rc, 0, jArr, 0, jArr.length);
        byte[] bArr = whirlpoolDigest._buffer;
        byte[] bArr2 = this._buffer;
        System.arraycopy(bArr, 0, bArr2, 0, bArr2.length);
        this._bufferPos = whirlpoolDigest._bufferPos;
        short[] sArr = whirlpoolDigest._bitCount;
        short[] sArr2 = this._bitCount;
        System.arraycopy(sArr, 0, sArr2, 0, sArr2.length);
        long[] jArr2 = whirlpoolDigest._hash;
        long[] jArr3 = this._hash;
        System.arraycopy(jArr2, 0, jArr3, 0, jArr3.length);
        long[] jArr4 = whirlpoolDigest._K;
        long[] jArr5 = this._K;
        System.arraycopy(jArr4, 0, jArr5, 0, jArr5.length);
        long[] jArr6 = whirlpoolDigest._L;
        long[] jArr7 = this._L;
        System.arraycopy(jArr6, 0, jArr7, 0, jArr7.length);
        long[] jArr8 = whirlpoolDigest._block;
        long[] jArr9 = this._block;
        System.arraycopy(jArr8, 0, jArr9, 0, jArr9.length);
        long[] jArr10 = whirlpoolDigest._state;
        long[] jArr11 = this._state;
        System.arraycopy(jArr10, 0, jArr11, 0, jArr11.length);
    }

    private long bytesToLongFromBuffer(byte[] bArr, int i2) {
        return (bArr[i2 + 7] & 255) | ((bArr[i2 + 0] & 255) << 56) | ((bArr[i2 + 1] & 255) << 48) | ((bArr[i2 + 2] & 255) << 40) | ((bArr[i2 + 3] & 255) << 32) | ((bArr[i2 + 4] & 255) << 24) | ((bArr[i2 + 5] & 255) << 16) | ((bArr[i2 + 6] & 255) << 8);
    }

    private void convertLongToByteArray(long j2, byte[] bArr, int i2) {
        for (int i3 = 0; i3 < 8; i3++) {
            bArr[i2 + i3] = (byte) ((j2 >> (56 - (i3 * 8))) & 255);
        }
    }

    private byte[] copyBitLength() {
        byte[] bArr = new byte[32];
        for (int i2 = 0; i2 < 32; i2++) {
            bArr[i2] = (byte) (this._bitCount[i2] & 255);
        }
        return bArr;
    }

    private void finish() {
        byte[] bArrCopyBitLength = copyBitLength();
        byte[] bArr = this._buffer;
        int i2 = this._bufferPos;
        int i3 = i2 + 1;
        this._bufferPos = i3;
        bArr[i2] = (byte) (bArr[i2] | 128);
        if (i3 == bArr.length) {
            processFilledBuffer(bArr, 0);
        }
        if (this._bufferPos > 32) {
            while (this._bufferPos != 0) {
                update((byte) 0);
            }
        }
        while (this._bufferPos <= 32) {
            update((byte) 0);
        }
        System.arraycopy(bArrCopyBitLength, 0, this._buffer, 32, bArrCopyBitLength.length);
        processFilledBuffer(this._buffer, 0);
    }

    private void increment() {
        int i2 = 0;
        for (int length = this._bitCount.length - 1; length >= 0; length--) {
            short[] sArr = this._bitCount;
            int i3 = (sArr[length] & 255) + EIGHT[length] + i2;
            i2 = i3 >>> 8;
            sArr[length] = (short) (i3 & 255);
        }
    }

    private int maskWithReductionPolynomial(int i2) {
        return ((long) i2) >= 256 ? i2 ^ 285 : i2;
    }

    private long packIntoLong(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        return (((((((i3 << 48) ^ (i2 << 56)) ^ (i4 << 40)) ^ (i5 << 32)) ^ (i6 << 24)) ^ (i7 << 16)) ^ (i8 << 8)) ^ i9;
    }

    private void processFilledBuffer(byte[] bArr, int i2) {
        for (int i3 = 0; i3 < this._state.length; i3++) {
            this._block[i3] = bytesToLongFromBuffer(this._buffer, i3 * 8);
        }
        processBlock();
        this._bufferPos = 0;
        Arrays.fill(this._buffer, (byte) 0);
    }

    @Override // org.bouncycastle.crypto.Digest
    public int doFinal(byte[] bArr, int i2) {
        finish();
        for (int i3 = 0; i3 < 8; i3++) {
            convertLongToByteArray(this._hash[i3], bArr, (i3 * 8) + i2);
        }
        reset();
        return getDigestSize();
    }

    @Override // org.bouncycastle.crypto.Digest
    public String getAlgorithmName() {
        return "Whirlpool";
    }

    @Override // org.bouncycastle.crypto.ExtendedDigest
    public int getByteLength() {
        return 64;
    }

    @Override // org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return 64;
    }

    public void processBlock() {
        long[] jArr;
        for (int i2 = 0; i2 < 8; i2++) {
            long[] jArr2 = this._state;
            long j2 = this._block[i2];
            long[] jArr3 = this._K;
            long j3 = this._hash[i2];
            jArr3[i2] = j3;
            jArr2[i2] = j2 ^ j3;
        }
        int i3 = 1;
        while (i3 <= 10) {
            int i4 = 0;
            while (i4 < 8) {
                long[] jArr4 = this._L;
                jArr4[i4] = 0;
                long[] jArr5 = C0;
                long[] jArr6 = this._K;
                long j4 = jArr5[((int) (jArr6[(i4 + 0) & 7] >>> 56)) & 255] ^ 0;
                jArr4[i4] = j4;
                long j5 = j4 ^ C1[((int) (jArr6[(i4 - 1) & 7] >>> 48)) & 255];
                jArr4[i4] = j5;
                long j6 = j5 ^ C2[((int) (jArr6[(i4 - 2) & 7] >>> 40)) & 255];
                jArr4[i4] = j6;
                long j7 = j6 ^ C3[((int) (jArr6[(i4 - 3) & 7] >>> 32)) & 255];
                jArr4[i4] = j7;
                long j8 = j7 ^ C4[((int) (jArr6[(i4 - 4) & 7] >>> 24)) & 255];
                jArr4[i4] = j8;
                long j9 = j8 ^ C5[((int) (jArr6[(i4 - 5) & 7] >>> 16)) & 255];
                jArr4[i4] = j9;
                long j10 = j9 ^ C6[((int) (jArr6[(i4 - 6) & 7] >>> 8)) & 255];
                jArr4[i4] = j10;
                jArr4[i4] = j10 ^ C7[((int) jArr6[(i4 - 7) & 7]) & 255];
                i4++;
                i3 = i3;
            }
            int i5 = i3;
            long[] jArr7 = this._L;
            long[] jArr8 = this._K;
            System.arraycopy(jArr7, 0, jArr8, 0, jArr8.length);
            long[] jArr9 = this._K;
            jArr9[0] = jArr9[0] ^ this._rc[i5];
            int i6 = 0;
            while (true) {
                jArr = this._L;
                if (i6 < 8) {
                    long j11 = this._K[i6];
                    jArr[i6] = j11;
                    long[] jArr10 = C0;
                    long[] jArr11 = this._state;
                    long j12 = j11 ^ jArr10[((int) (jArr11[(i6 + 0) & 7] >>> 56)) & 255];
                    jArr[i6] = j12;
                    long j13 = j12 ^ C1[((int) (jArr11[(i6 - 1) & 7] >>> 48)) & 255];
                    jArr[i6] = j13;
                    long j14 = j13 ^ C2[((int) (jArr11[(i6 - 2) & 7] >>> 40)) & 255];
                    jArr[i6] = j14;
                    long j15 = j14 ^ C3[((int) (jArr11[(i6 - 3) & 7] >>> 32)) & 255];
                    jArr[i6] = j15;
                    long j16 = j15 ^ C4[((int) (jArr11[(i6 - 4) & 7] >>> 24)) & 255];
                    jArr[i6] = j16;
                    long j17 = j16 ^ C5[((int) (jArr11[(i6 - 5) & 7] >>> 16)) & 255];
                    jArr[i6] = j17;
                    long j18 = j17 ^ C6[((int) (jArr11[(i6 - 6) & 7] >>> 8)) & 255];
                    jArr[i6] = j18;
                    jArr[i6] = j18 ^ C7[((int) jArr11[(i6 - 7) & 7]) & 255];
                    i6++;
                }
            }
            long[] jArr12 = this._state;
            System.arraycopy(jArr, 0, jArr12, 0, jArr12.length);
            i3 = i5 + 1;
        }
        for (int i7 = 0; i7 < 8; i7++) {
            long[] jArr13 = this._hash;
            jArr13[i7] = jArr13[i7] ^ (this._state[i7] ^ this._block[i7]);
        }
    }

    @Override // org.bouncycastle.crypto.Digest
    public void reset() {
        this._bufferPos = 0;
        Arrays.fill(this._bitCount, (short) 0);
        Arrays.fill(this._buffer, (byte) 0);
        Arrays.fill(this._hash, 0L);
        Arrays.fill(this._K, 0L);
        Arrays.fill(this._L, 0L);
        Arrays.fill(this._block, 0L);
        Arrays.fill(this._state, 0L);
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte b3) {
        byte[] bArr = this._buffer;
        int i2 = this._bufferPos;
        bArr[i2] = b3;
        int i3 = i2 + 1;
        this._bufferPos = i3;
        if (i3 == bArr.length) {
            processFilledBuffer(bArr, 0);
        }
        increment();
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte[] bArr, int i2, int i3) {
        while (i3 > 0) {
            update(bArr[i2]);
            i2++;
            i3--;
        }
    }
}
