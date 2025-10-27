package org.bouncycastle.crypto;

/* loaded from: classes9.dex */
public interface Wrapper {
    String getAlgorithmName();

    void init(boolean z2, CipherParameters cipherParameters);

    byte[] unwrap(byte[] bArr, int i2, int i3) throws InvalidCipherTextException;

    byte[] wrap(byte[] bArr, int i2, int i3);
}
