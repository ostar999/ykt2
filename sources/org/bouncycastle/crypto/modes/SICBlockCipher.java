package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.params.ParametersWithIV;

/* loaded from: classes9.dex */
public class SICBlockCipher implements BlockCipher {
    private byte[] IV;
    private final int blockSize;
    private final BlockCipher cipher;
    private byte[] counter;
    private byte[] counterOut;

    public SICBlockCipher(BlockCipher blockCipher) {
        this.cipher = blockCipher;
        int blockSize = blockCipher.getBlockSize();
        this.blockSize = blockSize;
        this.IV = new byte[blockSize];
        this.counter = new byte[blockSize];
        this.counterOut = new byte[blockSize];
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return this.cipher.getAlgorithmName() + "/SIC";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return this.cipher.getBlockSize();
    }

    public BlockCipher getUnderlyingCipher() {
        return this.cipher;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) throws IllegalArgumentException {
        if (!(cipherParameters instanceof ParametersWithIV)) {
            throw new IllegalArgumentException("SIC mode requires ParametersWithIV");
        }
        ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
        byte[] iv = parametersWithIV.getIV();
        byte[] bArr = this.IV;
        System.arraycopy(iv, 0, bArr, 0, bArr.length);
        reset();
        this.cipher.init(true, parametersWithIV.getParameters());
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) throws IllegalStateException, DataLengthException {
        this.cipher.processBlock(this.counter, 0, this.counterOut, 0);
        int i4 = 0;
        while (true) {
            byte[] bArr3 = this.counterOut;
            if (i4 >= bArr3.length) {
                break;
            }
            bArr2[i3 + i4] = (byte) (bArr3[i4] ^ bArr[i2 + i4]);
            i4++;
        }
        int i5 = 1;
        for (int length = this.counter.length - 1; length >= 0; length--) {
            byte[] bArr4 = this.counter;
            int i6 = (bArr4[length] & 255) + i5;
            i5 = i6 > 255 ? 1 : 0;
            bArr4[length] = (byte) i6;
        }
        return this.counter.length;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
        byte[] bArr = this.IV;
        byte[] bArr2 = this.counter;
        System.arraycopy(bArr, 0, bArr2, 0, bArr2.length);
        this.cipher.reset();
    }
}
