package org.bouncycastle.crypto;

/* loaded from: classes9.dex */
public interface BlockCipher {
    String getAlgorithmName();

    int getBlockSize();

    void init(boolean z2, CipherParameters cipherParameters) throws IllegalArgumentException;

    int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) throws IllegalStateException, DataLengthException;

    void reset();
}
