package org.bouncycastle.crypto.digests;

import androidx.core.view.MotionEventCompat;
import java.lang.reflect.Array;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.ExtendedDigest;
import org.bouncycastle.crypto.engines.GOST28147Engine;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithSBox;
import org.bouncycastle.crypto.util.Pack;
import org.bouncycastle.util.Arrays;

/* loaded from: classes9.dex */
public class GOST3411Digest implements ExtendedDigest {
    private static final byte[] C2 = {0, -1, 0, -1, 0, -1, 0, -1, -1, 0, -1, 0, -1, 0, -1, 0, 0, -1, -1, 0, -1, 0, 0, -1, -1, 0, 0, 0, -1, -1, 0, -1};
    private static final int DIGEST_LENGTH = 32;
    private byte[][] C;
    private byte[] H;
    private byte[] K;
    private byte[] L;
    private byte[] M;
    byte[] S;
    private byte[] Sum;
    byte[] U;
    byte[] V;
    byte[] W;

    /* renamed from: a, reason: collision with root package name */
    byte[] f27819a;
    private long byteCount;
    private BlockCipher cipher;
    private byte[] sBox;
    short[] wS;
    short[] w_S;
    private byte[] xBuf;
    private int xBufOff;

    public GOST3411Digest() throws IllegalArgumentException {
        this.H = new byte[32];
        this.L = new byte[32];
        this.M = new byte[32];
        this.Sum = new byte[32];
        this.C = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, 4, 32);
        this.xBuf = new byte[32];
        this.cipher = new GOST28147Engine();
        this.K = new byte[32];
        this.f27819a = new byte[8];
        this.wS = new short[16];
        this.w_S = new short[16];
        this.S = new byte[32];
        this.U = new byte[32];
        this.V = new byte[32];
        this.W = new byte[32];
        byte[] sBox = GOST28147Engine.getSBox("D-A");
        this.sBox = sBox;
        this.cipher.init(true, new ParametersWithSBox(null, sBox));
        reset();
    }

    public GOST3411Digest(GOST3411Digest gOST3411Digest) throws IllegalArgumentException {
        this.H = new byte[32];
        this.L = new byte[32];
        this.M = new byte[32];
        this.Sum = new byte[32];
        this.C = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, 4, 32);
        this.xBuf = new byte[32];
        GOST28147Engine gOST28147Engine = new GOST28147Engine();
        this.cipher = gOST28147Engine;
        this.K = new byte[32];
        this.f27819a = new byte[8];
        this.wS = new short[16];
        this.w_S = new short[16];
        this.S = new byte[32];
        this.U = new byte[32];
        this.V = new byte[32];
        this.W = new byte[32];
        byte[] bArr = gOST3411Digest.sBox;
        this.sBox = bArr;
        gOST28147Engine.init(true, new ParametersWithSBox(null, bArr));
        reset();
        byte[] bArr2 = gOST3411Digest.H;
        System.arraycopy(bArr2, 0, this.H, 0, bArr2.length);
        byte[] bArr3 = gOST3411Digest.L;
        System.arraycopy(bArr3, 0, this.L, 0, bArr3.length);
        byte[] bArr4 = gOST3411Digest.M;
        System.arraycopy(bArr4, 0, this.M, 0, bArr4.length);
        byte[] bArr5 = gOST3411Digest.Sum;
        System.arraycopy(bArr5, 0, this.Sum, 0, bArr5.length);
        byte[] bArr6 = gOST3411Digest.C[1];
        System.arraycopy(bArr6, 0, this.C[1], 0, bArr6.length);
        byte[] bArr7 = gOST3411Digest.C[2];
        System.arraycopy(bArr7, 0, this.C[2], 0, bArr7.length);
        byte[] bArr8 = gOST3411Digest.C[3];
        System.arraycopy(bArr8, 0, this.C[3], 0, bArr8.length);
        byte[] bArr9 = gOST3411Digest.xBuf;
        System.arraycopy(bArr9, 0, this.xBuf, 0, bArr9.length);
        this.xBufOff = gOST3411Digest.xBufOff;
        this.byteCount = gOST3411Digest.byteCount;
    }

    public GOST3411Digest(byte[] bArr) throws IllegalArgumentException {
        this.H = new byte[32];
        this.L = new byte[32];
        this.M = new byte[32];
        this.Sum = new byte[32];
        this.C = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, 4, 32);
        this.xBuf = new byte[32];
        this.cipher = new GOST28147Engine();
        this.K = new byte[32];
        this.f27819a = new byte[8];
        this.wS = new short[16];
        this.w_S = new short[16];
        this.S = new byte[32];
        this.U = new byte[32];
        this.V = new byte[32];
        this.W = new byte[32];
        byte[] bArrClone = Arrays.clone(bArr);
        this.sBox = bArrClone;
        this.cipher.init(true, new ParametersWithSBox(null, bArrClone));
        reset();
    }

    private byte[] A(byte[] bArr) {
        for (int i2 = 0; i2 < 8; i2++) {
            this.f27819a[i2] = (byte) (bArr[i2] ^ bArr[i2 + 8]);
        }
        System.arraycopy(bArr, 8, bArr, 0, 24);
        System.arraycopy(this.f27819a, 0, bArr, 24, 8);
        return bArr;
    }

    private void E(byte[] bArr, byte[] bArr2, int i2, byte[] bArr3, int i3) throws IllegalStateException, DataLengthException, IllegalArgumentException {
        this.cipher.init(true, new KeyParameter(bArr));
        this.cipher.processBlock(bArr3, i3, bArr2, i2);
    }

    private byte[] P(byte[] bArr) {
        for (int i2 = 0; i2 < 8; i2++) {
            byte[] bArr2 = this.K;
            int i3 = i2 * 4;
            bArr2[i3] = bArr[i2];
            bArr2[i3 + 1] = bArr[i2 + 8];
            bArr2[i3 + 2] = bArr[i2 + 16];
            bArr2[i3 + 3] = bArr[i2 + 24];
        }
        return this.K;
    }

    private void cpyBytesToShort(byte[] bArr, short[] sArr) {
        for (int i2 = 0; i2 < bArr.length / 2; i2++) {
            int i3 = i2 * 2;
            sArr[i2] = (short) ((bArr[i3] & 255) | ((bArr[i3 + 1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK));
        }
    }

    private void cpyShortToBytes(short[] sArr, byte[] bArr) {
        for (int i2 = 0; i2 < bArr.length / 2; i2++) {
            int i3 = i2 * 2;
            short s2 = sArr[i2];
            bArr[i3 + 1] = (byte) (s2 >> 8);
            bArr[i3] = (byte) s2;
        }
    }

    private void finish() throws IllegalStateException, DataLengthException, IllegalArgumentException {
        Pack.longToLittleEndian(this.byteCount * 8, this.L, 0);
        while (this.xBufOff != 0) {
            update((byte) 0);
        }
        processBlock(this.L, 0);
        processBlock(this.Sum, 0);
    }

    private void fw(byte[] bArr) {
        cpyBytesToShort(bArr, this.wS);
        short[] sArr = this.w_S;
        short[] sArr2 = this.wS;
        sArr[15] = (short) (((((sArr2[0] ^ sArr2[1]) ^ sArr2[2]) ^ sArr2[3]) ^ sArr2[12]) ^ sArr2[15]);
        System.arraycopy(sArr2, 1, sArr, 0, 15);
        cpyShortToBytes(this.w_S, bArr);
    }

    private void sumByteArray(byte[] bArr) {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            byte[] bArr2 = this.Sum;
            if (i2 == bArr2.length) {
                return;
            }
            int i4 = (bArr2[i2] & 255) + (bArr[i2] & 255) + i3;
            bArr2[i2] = (byte) i4;
            i3 = i4 >>> 8;
            i2++;
        }
    }

    @Override // org.bouncycastle.crypto.Digest
    public int doFinal(byte[] bArr, int i2) throws IllegalStateException, DataLengthException, IllegalArgumentException {
        finish();
        byte[] bArr2 = this.H;
        System.arraycopy(bArr2, 0, bArr, i2, bArr2.length);
        reset();
        return 32;
    }

    @Override // org.bouncycastle.crypto.Digest
    public String getAlgorithmName() {
        return "GOST3411";
    }

    @Override // org.bouncycastle.crypto.ExtendedDigest
    public int getByteLength() {
        return 32;
    }

    @Override // org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return 32;
    }

    public void processBlock(byte[] bArr, int i2) throws IllegalStateException, DataLengthException, IllegalArgumentException {
        System.arraycopy(bArr, i2, this.M, 0, 32);
        System.arraycopy(this.H, 0, this.U, 0, 32);
        System.arraycopy(this.M, 0, this.V, 0, 32);
        for (int i3 = 0; i3 < 32; i3++) {
            this.W[i3] = (byte) (this.U[i3] ^ this.V[i3]);
        }
        E(P(this.W), this.S, 0, this.H, 0);
        for (int i4 = 1; i4 < 4; i4++) {
            byte[] bArrA = A(this.U);
            for (int i5 = 0; i5 < 32; i5++) {
                this.U[i5] = (byte) (bArrA[i5] ^ this.C[i4][i5]);
            }
            this.V = A(A(this.V));
            for (int i6 = 0; i6 < 32; i6++) {
                this.W[i6] = (byte) (this.U[i6] ^ this.V[i6]);
            }
            int i7 = i4 * 8;
            E(P(this.W), this.S, i7, this.H, i7);
        }
        for (int i8 = 0; i8 < 12; i8++) {
            fw(this.S);
        }
        for (int i9 = 0; i9 < 32; i9++) {
            byte[] bArr2 = this.S;
            bArr2[i9] = (byte) (bArr2[i9] ^ this.M[i9]);
        }
        fw(this.S);
        for (int i10 = 0; i10 < 32; i10++) {
            byte[] bArr3 = this.S;
            bArr3[i10] = (byte) (this.H[i10] ^ bArr3[i10]);
        }
        for (int i11 = 0; i11 < 61; i11++) {
            fw(this.S);
        }
        byte[] bArr4 = this.S;
        byte[] bArr5 = this.H;
        System.arraycopy(bArr4, 0, bArr5, 0, bArr5.length);
    }

    @Override // org.bouncycastle.crypto.Digest
    public void reset() {
        this.byteCount = 0L;
        this.xBufOff = 0;
        int i2 = 0;
        while (true) {
            byte[] bArr = this.H;
            if (i2 >= bArr.length) {
                break;
            }
            bArr[i2] = 0;
            i2++;
        }
        int i3 = 0;
        while (true) {
            byte[] bArr2 = this.L;
            if (i3 >= bArr2.length) {
                break;
            }
            bArr2[i3] = 0;
            i3++;
        }
        int i4 = 0;
        while (true) {
            byte[] bArr3 = this.M;
            if (i4 >= bArr3.length) {
                break;
            }
            bArr3[i4] = 0;
            i4++;
        }
        int i5 = 0;
        while (true) {
            byte[] bArr4 = this.C[1];
            if (i5 >= bArr4.length) {
                break;
            }
            bArr4[i5] = 0;
            i5++;
        }
        int i6 = 0;
        while (true) {
            byte[] bArr5 = this.C[3];
            if (i6 >= bArr5.length) {
                break;
            }
            bArr5[i6] = 0;
            i6++;
        }
        int i7 = 0;
        while (true) {
            byte[] bArr6 = this.Sum;
            if (i7 >= bArr6.length) {
                break;
            }
            bArr6[i7] = 0;
            i7++;
        }
        int i8 = 0;
        while (true) {
            byte[] bArr7 = this.xBuf;
            if (i8 >= bArr7.length) {
                byte[] bArr8 = C2;
                System.arraycopy(bArr8, 0, this.C[2], 0, bArr8.length);
                return;
            } else {
                bArr7[i8] = 0;
                i8++;
            }
        }
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte b3) throws IllegalStateException, DataLengthException, IllegalArgumentException {
        byte[] bArr = this.xBuf;
        int i2 = this.xBufOff;
        int i3 = i2 + 1;
        this.xBufOff = i3;
        bArr[i2] = b3;
        if (i3 == bArr.length) {
            sumByteArray(bArr);
            processBlock(this.xBuf, 0);
            this.xBufOff = 0;
        }
        this.byteCount++;
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte[] bArr, int i2, int i3) throws IllegalStateException, DataLengthException, IllegalArgumentException {
        while (this.xBufOff != 0 && i3 > 0) {
            update(bArr[i2]);
            i2++;
            i3--;
        }
        while (true) {
            byte[] bArr2 = this.xBuf;
            if (i3 <= bArr2.length) {
                break;
            }
            System.arraycopy(bArr, i2, bArr2, 0, bArr2.length);
            sumByteArray(this.xBuf);
            processBlock(this.xBuf, 0);
            byte[] bArr3 = this.xBuf;
            i2 += bArr3.length;
            i3 -= bArr3.length;
            this.byteCount += bArr3.length;
        }
        while (i3 > 0) {
            update(bArr[i2]);
            i2++;
            i3--;
        }
    }
}
