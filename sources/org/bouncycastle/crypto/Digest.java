package org.bouncycastle.crypto;

/* loaded from: classes9.dex */
public interface Digest {
    int doFinal(byte[] bArr, int i2);

    String getAlgorithmName();

    int getDigestSize();

    void reset();

    void update(byte b3);

    void update(byte[] bArr, int i2, int i3);
}
