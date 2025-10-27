package org.bouncycastle.crypto;

/* loaded from: classes9.dex */
public interface DerivationFunction {
    int generateBytes(byte[] bArr, int i2, int i3) throws DataLengthException, IllegalArgumentException;

    Digest getDigest();

    void init(DerivationParameters derivationParameters);
}
