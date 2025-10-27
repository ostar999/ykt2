package org.bouncycastle.crypto;

/* loaded from: classes9.dex */
public interface AsymmetricBlockCipher {
    int getInputBlockSize();

    int getOutputBlockSize();

    void init(boolean z2, CipherParameters cipherParameters);

    byte[] processBlock(byte[] bArr, int i2, int i3) throws InvalidCipherTextException;
}
