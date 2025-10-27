package org.bouncycastle.crypto.engines;

import com.yikaobang.yixue.R2;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

/* loaded from: classes9.dex */
public class HC256Engine implements StreamCipher {
    private boolean initialised;
    private byte[] iv;
    private byte[] key;

    /* renamed from: p, reason: collision with root package name */
    private int[] f27830p = new int[1024];

    /* renamed from: q, reason: collision with root package name */
    private int[] f27831q = new int[1024];
    private int cnt = 0;
    private byte[] buf = new byte[4];
    private int idx = 0;

    private byte getByte() {
        if (this.idx == 0) {
            int iStep = step();
            byte[] bArr = this.buf;
            bArr[0] = (byte) (iStep & 255);
            int i2 = iStep >> 8;
            bArr[1] = (byte) (i2 & 255);
            int i3 = i2 >> 8;
            bArr[2] = (byte) (i3 & 255);
            bArr[3] = (byte) ((i3 >> 8) & 255);
        }
        byte[] bArr2 = this.buf;
        int i4 = this.idx;
        byte b3 = bArr2[i4];
        this.idx = 3 & (i4 + 1);
        return b3;
    }

    private void init() {
        byte[] bArr = this.key;
        if (bArr.length != 32 && bArr.length != 16) {
            throw new IllegalArgumentException("The key must be 128/256 bits long");
        }
        if (this.iv.length < 16) {
            throw new IllegalArgumentException("The IV must be at least 128 bits long");
        }
        if (bArr.length != 32) {
            byte[] bArr2 = new byte[32];
            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
            byte[] bArr3 = this.key;
            System.arraycopy(bArr3, 0, bArr2, 16, bArr3.length);
            this.key = bArr2;
        }
        byte[] bArr4 = this.iv;
        if (bArr4.length < 32) {
            byte[] bArr5 = new byte[32];
            System.arraycopy(bArr4, 0, bArr5, 0, bArr4.length);
            byte[] bArr6 = this.iv;
            System.arraycopy(bArr6, 0, bArr5, bArr6.length, 32 - bArr6.length);
            this.iv = bArr5;
        }
        this.cnt = 0;
        int[] iArr = new int[R2.attr.mvGravity];
        for (int i2 = 0; i2 < 32; i2++) {
            int i3 = i2 >> 2;
            iArr[i3] = iArr[i3] | ((this.key[i2] & 255) << ((i2 & 3) * 8));
        }
        for (int i4 = 0; i4 < 32; i4++) {
            int i5 = (i4 >> 2) + 8;
            iArr[i5] = iArr[i5] | ((this.iv[i4] & 255) << ((i4 & 3) * 8));
        }
        for (int i6 = 16; i6 < 2560; i6++) {
            int i7 = iArr[i6 - 2];
            int i8 = iArr[i6 - 15];
            iArr[i6] = ((i7 >>> 10) ^ (rotateRight(i7, 17) ^ rotateRight(i7, 19))) + iArr[i6 - 7] + ((i8 >>> 3) ^ (rotateRight(i8, 7) ^ rotateRight(i8, 18))) + iArr[i6 - 16] + i6;
        }
        System.arraycopy(iArr, 512, this.f27830p, 0, 1024);
        System.arraycopy(iArr, 1536, this.f27831q, 0, 1024);
        for (int i9 = 0; i9 < 4096; i9++) {
            step();
        }
        this.cnt = 0;
    }

    private static int rotateRight(int i2, int i3) {
        return (i2 << (-i3)) | (i2 >>> i3);
    }

    private int step() {
        int i2;
        int i3;
        int i4 = this.cnt;
        int i5 = i4 & 1023;
        if (i4 < 1024) {
            int[] iArr = this.f27830p;
            int i6 = iArr[(i5 - 3) & 1023];
            int i7 = iArr[(i5 - 1023) & 1023];
            int i8 = iArr[i5];
            int iRotateRight = iArr[(i5 - 10) & 1023] + (rotateRight(i7, 23) ^ rotateRight(i6, 10));
            int[] iArr2 = this.f27831q;
            iArr[i5] = i8 + iRotateRight + iArr2[(i6 ^ i7) & 1023];
            int[] iArr3 = this.f27830p;
            int i9 = iArr3[(i5 - 12) & 1023];
            i2 = iArr2[i9 & 255] + iArr2[((i9 >> 8) & 255) + 256] + iArr2[((i9 >> 16) & 255) + 512] + iArr2[((i9 >> 24) & 255) + R2.attr.bl_unSelected_gradient_useLevel];
            i3 = iArr3[i5];
        } else {
            int[] iArr4 = this.f27831q;
            int i10 = iArr4[(i5 - 3) & 1023];
            int i11 = iArr4[(i5 - 1023) & 1023];
            int i12 = iArr4[i5];
            int iRotateRight2 = iArr4[(i5 - 10) & 1023] + (rotateRight(i11, 23) ^ rotateRight(i10, 10));
            int[] iArr5 = this.f27830p;
            iArr4[i5] = i12 + iRotateRight2 + iArr5[(i10 ^ i11) & 1023];
            int[] iArr6 = this.f27831q;
            int i13 = iArr6[(i5 - 12) & 1023];
            i2 = iArr5[i13 & 255] + iArr5[((i13 >> 8) & 255) + 256] + iArr5[((i13 >> 16) & 255) + 512] + iArr5[((i13 >> 24) & 255) + R2.attr.bl_unSelected_gradient_useLevel];
            i3 = iArr6[i5];
        }
        int i14 = i3 ^ i2;
        this.cnt = (this.cnt + 1) & R2.attr.indicatorSize;
        return i14;
    }

    @Override // org.bouncycastle.crypto.StreamCipher
    public String getAlgorithmName() {
        return "HC-256";
    }

    @Override // org.bouncycastle.crypto.StreamCipher
    public void init(boolean z2, CipherParameters cipherParameters) throws IllegalArgumentException {
        CipherParameters parameters;
        if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            this.iv = parametersWithIV.getIV();
            parameters = parametersWithIV.getParameters();
        } else {
            this.iv = new byte[0];
            parameters = cipherParameters;
        }
        if (parameters instanceof KeyParameter) {
            this.key = ((KeyParameter) parameters).getKey();
            init();
            this.initialised = true;
        } else {
            throw new IllegalArgumentException("Invalid parameter passed to HC256 init - " + cipherParameters.getClass().getName());
        }
    }

    @Override // org.bouncycastle.crypto.StreamCipher
    public void processBytes(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) throws DataLengthException {
        if (!this.initialised) {
            throw new IllegalStateException(getAlgorithmName() + " not initialised");
        }
        if (i2 + i3 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (i4 + i3 > bArr2.length) {
            throw new DataLengthException("output buffer too short");
        }
        for (int i5 = 0; i5 < i3; i5++) {
            bArr2[i4 + i5] = (byte) (bArr[i2 + i5] ^ getByte());
        }
    }

    @Override // org.bouncycastle.crypto.StreamCipher
    public void reset() {
        this.idx = 0;
        init();
    }

    @Override // org.bouncycastle.crypto.StreamCipher
    public byte returnByte(byte b3) {
        return (byte) (b3 ^ getByte());
    }
}
