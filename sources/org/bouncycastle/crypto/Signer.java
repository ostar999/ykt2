package org.bouncycastle.crypto;

/* loaded from: classes9.dex */
public interface Signer {
    byte[] generateSignature() throws DataLengthException, CryptoException;

    void init(boolean z2, CipherParameters cipherParameters);

    void reset();

    void update(byte b3);

    void update(byte[] bArr, int i2, int i3);

    boolean verifySignature(byte[] bArr);
}
