package org.bouncycastle.crypto.params;

/* loaded from: classes9.dex */
public class IESWithCipherParameters extends IESParameters {
    private int cipherKeySize;

    public IESWithCipherParameters(byte[] bArr, byte[] bArr2, int i2, int i3) {
        super(bArr, bArr2, i2);
        this.cipherKeySize = i3;
    }

    public int getCipherKeySize() {
        return this.cipherKeySize;
    }
}
