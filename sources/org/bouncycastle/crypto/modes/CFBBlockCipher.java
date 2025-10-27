package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.params.ParametersWithIV;

/* loaded from: classes9.dex */
public class CFBBlockCipher implements BlockCipher {
    private byte[] IV;
    private int blockSize;
    private byte[] cfbOutV;
    private byte[] cfbV;
    private BlockCipher cipher;
    private boolean encrypting;

    public CFBBlockCipher(BlockCipher blockCipher, int i2) {
        this.cipher = blockCipher;
        this.blockSize = i2 / 8;
        this.IV = new byte[blockCipher.getBlockSize()];
        this.cfbV = new byte[blockCipher.getBlockSize()];
        this.cfbOutV = new byte[blockCipher.getBlockSize()];
    }

    public int decryptBlock(byte[] bArr, int i2, byte[] bArr2, int i3) throws IllegalStateException, DataLengthException {
        int i4 = this.blockSize;
        if (i2 + i4 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (i4 + i3 > bArr2.length) {
            throw new DataLengthException("output buffer too short");
        }
        int i5 = 0;
        this.cipher.processBlock(this.cfbV, 0, this.cfbOutV, 0);
        byte[] bArr3 = this.cfbV;
        int i6 = this.blockSize;
        System.arraycopy(bArr3, i6, bArr3, 0, bArr3.length - i6);
        byte[] bArr4 = this.cfbV;
        int length = bArr4.length;
        int i7 = this.blockSize;
        System.arraycopy(bArr, i2, bArr4, length - i7, i7);
        while (true) {
            int i8 = this.blockSize;
            if (i5 >= i8) {
                return i8;
            }
            bArr2[i3 + i5] = (byte) (this.cfbOutV[i5] ^ bArr[i2 + i5]);
            i5++;
        }
    }

    public int encryptBlock(byte[] bArr, int i2, byte[] bArr2, int i3) throws IllegalStateException, DataLengthException {
        int i4 = this.blockSize;
        if (i2 + i4 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (i4 + i3 > bArr2.length) {
            throw new DataLengthException("output buffer too short");
        }
        this.cipher.processBlock(this.cfbV, 0, this.cfbOutV, 0);
        int i5 = 0;
        while (true) {
            int i6 = this.blockSize;
            if (i5 >= i6) {
                byte[] bArr3 = this.cfbV;
                System.arraycopy(bArr3, i6, bArr3, 0, bArr3.length - i6);
                byte[] bArr4 = this.cfbV;
                int length = bArr4.length;
                int i7 = this.blockSize;
                System.arraycopy(bArr2, i3, bArr4, length - i7, i7);
                return this.blockSize;
            }
            bArr2[i3 + i5] = (byte) (this.cfbOutV[i5] ^ bArr[i2 + i5]);
            i5++;
        }
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return this.cipher.getAlgorithmName() + "/CFB" + (this.blockSize * 8);
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return this.blockSize;
    }

    public BlockCipher getUnderlyingCipher() {
        return this.cipher;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) throws IllegalArgumentException {
        BlockCipher blockCipher;
        this.encrypting = z2;
        if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            byte[] iv = parametersWithIV.getIV();
            int length = iv.length;
            byte[] bArr = this.IV;
            if (length < bArr.length) {
                System.arraycopy(iv, 0, bArr, bArr.length - iv.length, iv.length);
                int i2 = 0;
                while (true) {
                    byte[] bArr2 = this.IV;
                    if (i2 >= bArr2.length - iv.length) {
                        break;
                    }
                    bArr2[i2] = 0;
                    i2++;
                }
            } else {
                System.arraycopy(iv, 0, bArr, 0, bArr.length);
            }
            reset();
            blockCipher = this.cipher;
            cipherParameters = parametersWithIV.getParameters();
        } else {
            reset();
            blockCipher = this.cipher;
        }
        blockCipher.init(true, cipherParameters);
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) throws IllegalStateException, DataLengthException {
        return this.encrypting ? encryptBlock(bArr, i2, bArr2, i3) : decryptBlock(bArr, i2, bArr2, i3);
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
        byte[] bArr = this.IV;
        System.arraycopy(bArr, 0, this.cfbV, 0, bArr.length);
        this.cipher.reset();
    }
}
