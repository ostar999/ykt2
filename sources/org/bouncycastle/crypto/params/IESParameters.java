package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.CipherParameters;

/* loaded from: classes9.dex */
public class IESParameters implements CipherParameters {
    private byte[] derivation;
    private byte[] encoding;
    private int macKeySize;

    public IESParameters(byte[] bArr, byte[] bArr2, int i2) {
        this.derivation = bArr;
        this.encoding = bArr2;
        this.macKeySize = i2;
    }

    public byte[] getDerivationV() {
        return this.derivation;
    }

    public byte[] getEncodingV() {
        return this.encoding;
    }

    public int getMacKeySize() {
        return this.macKeySize;
    }
}
