package org.bouncycastle.crypto.engines;

import com.google.common.base.Ascii;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.params.KeyParameter;

/* loaded from: classes9.dex */
public class XTEAEngine implements BlockCipher {
    private static final int block_size = 8;
    private static final int delta = -1640531527;
    private static final int rounds = 32;
    private boolean _forEncryption;
    private int[] _S = new int[4];
    private int[] _sum0 = new int[32];
    private int[] _sum1 = new int[32];
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
        for (int i4 = 31; i4 >= 0; i4--) {
            iBytesToInt2 -= (((iBytesToInt << 4) ^ (iBytesToInt >>> 5)) + iBytesToInt) ^ this._sum1[i4];
            iBytesToInt -= (((iBytesToInt2 << 4) ^ (iBytesToInt2 >>> 5)) + iBytesToInt2) ^ this._sum0[i4];
        }
        unpackInt(iBytesToInt, bArr2, i3);
        unpackInt(iBytesToInt2, bArr2, i3 + 4);
        return 8;
    }

    private int encryptBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        int iBytesToInt = bytesToInt(bArr, i2);
        int iBytesToInt2 = bytesToInt(bArr, i2 + 4);
        for (int i4 = 0; i4 < 32; i4++) {
            iBytesToInt += (((iBytesToInt2 << 4) ^ (iBytesToInt2 >>> 5)) + iBytesToInt2) ^ this._sum0[i4];
            iBytesToInt2 += (((iBytesToInt << 4) ^ (iBytesToInt >>> 5)) + iBytesToInt) ^ this._sum1[i4];
        }
        unpackInt(iBytesToInt, bArr2, i3);
        unpackInt(iBytesToInt2, bArr2, i3 + 4);
        return 8;
    }

    private void setKey(byte[] bArr) {
        int i2 = 0;
        int i3 = 0;
        while (i2 < 4) {
            this._S[i2] = bytesToInt(bArr, i3);
            i2++;
            i3 += 4;
        }
        int i4 = 0;
        for (int i5 = 0; i5 < 32; i5++) {
            int[] iArr = this._sum0;
            int[] iArr2 = this._S;
            iArr[i5] = iArr2[i4 & 3] + i4;
            i4 -= 1640531527;
            this._sum1[i5] = iArr2[(i4 >>> 11) & 3] + i4;
        }
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
        return "XTEA";
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
