package org.bouncycastle.crypto.engines;

import com.google.common.base.Ascii;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.params.KeyParameter;

/* loaded from: classes9.dex */
public class TEAEngine implements BlockCipher {
    private static final int block_size = 8;
    private static final int d_sum = -957401312;
    private static final int delta = -1640531527;
    private static final int rounds = 32;
    private int _a;
    private int _b;
    private int _c;
    private int _d;
    private boolean _forEncryption;
    private boolean _initialised = false;

    private int bytesToInt(byte[] bArr, int i2) {
        int i3 = i2 + 1;
        int i4 = i3 + 1;
        int i5 = (bArr[i2] << Ascii.CAN) | ((bArr[i3] & 255) << 16);
        int i6 = i4 + 1;
        return (bArr[i6] & 255) | i5 | ((bArr[i4] & 255) << 8);
    }

    private int decryptBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        int iBytesToInt = bytesToInt(bArr, i2);
        int iBytesToInt2 = bytesToInt(bArr, i2 + 4);
        int i4 = d_sum;
        for (int i5 = 0; i5 != 32; i5++) {
            iBytesToInt2 -= (((iBytesToInt << 4) + this._c) ^ (iBytesToInt + i4)) ^ ((iBytesToInt >>> 5) + this._d);
            iBytesToInt -= (((iBytesToInt2 << 4) + this._a) ^ (iBytesToInt2 + i4)) ^ ((iBytesToInt2 >>> 5) + this._b);
            i4 += 1640531527;
        }
        unpackInt(iBytesToInt, bArr2, i3);
        unpackInt(iBytesToInt2, bArr2, i3 + 4);
        return 8;
    }

    private int encryptBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        int iBytesToInt = bytesToInt(bArr, i2);
        int iBytesToInt2 = bytesToInt(bArr, i2 + 4);
        int i4 = iBytesToInt;
        int i5 = 0;
        for (int i6 = 0; i6 != 32; i6++) {
            i5 -= 1640531527;
            i4 += (((iBytesToInt2 << 4) + this._a) ^ (iBytesToInt2 + i5)) ^ ((iBytesToInt2 >>> 5) + this._b);
            iBytesToInt2 += (((i4 << 4) + this._c) ^ (i4 + i5)) ^ ((i4 >>> 5) + this._d);
        }
        unpackInt(i4, bArr2, i3);
        unpackInt(iBytesToInt2, bArr2, i3 + 4);
        return 8;
    }

    private void setKey(byte[] bArr) {
        this._a = bytesToInt(bArr, 0);
        this._b = bytesToInt(bArr, 4);
        this._c = bytesToInt(bArr, 8);
        this._d = bytesToInt(bArr, 12);
    }

    private void unpackInt(int i2, byte[] bArr, int i3) {
        int i4 = i3 + 1;
        bArr[i3] = (byte) (i2 >>> 24);
        int i5 = i4 + 1;
        bArr[i4] = (byte) (i2 >>> 16);
        bArr[i5] = (byte) (i2 >>> 8);
        bArr[i5 + 1] = (byte) i2;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return "TEA";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return 8;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        if (cipherParameters instanceof KeyParameter) {
            this._forEncryption = z2;
            this._initialised = true;
            setKey(((KeyParameter) cipherParameters).getKey());
        } else {
            throw new IllegalArgumentException("invalid parameter passed to TEA init - " + cipherParameters.getClass().getName());
        }
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        if (!this._initialised) {
            throw new IllegalStateException(getAlgorithmName() + " not initialised");
        }
        if (i2 + 8 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (i3 + 8 <= bArr2.length) {
            return this._forEncryption ? encryptBlock(bArr, i2, bArr2, i3) : decryptBlock(bArr, i2, bArr2, i3);
        }
        throw new DataLengthException("output buffer too short");
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
    }
}
