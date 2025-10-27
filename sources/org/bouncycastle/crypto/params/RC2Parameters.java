package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.CipherParameters;

/* loaded from: classes9.dex */
public class RC2Parameters implements CipherParameters {
    private int bits;
    private byte[] key;

    public RC2Parameters(byte[] bArr) {
        this(bArr, bArr.length > 128 ? 1024 : bArr.length * 8);
    }

    public RC2Parameters(byte[] bArr, int i2) {
        byte[] bArr2 = new byte[bArr.length];
        this.key = bArr2;
        this.bits = i2;
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
    }

    public int getEffectiveKeyBits() {
        return this.bits;
    }

    public byte[] getKey() {
        return this.key;
    }
}
