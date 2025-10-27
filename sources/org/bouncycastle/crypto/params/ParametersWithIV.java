package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.CipherParameters;

/* loaded from: classes9.dex */
public class ParametersWithIV implements CipherParameters {
    private byte[] iv;
    private CipherParameters parameters;

    public ParametersWithIV(CipherParameters cipherParameters, byte[] bArr) {
        this(cipherParameters, bArr, 0, bArr.length);
    }

    public ParametersWithIV(CipherParameters cipherParameters, byte[] bArr, int i2, int i3) {
        byte[] bArr2 = new byte[i3];
        this.iv = bArr2;
        this.parameters = cipherParameters;
        System.arraycopy(bArr, i2, bArr2, 0, i3);
    }

    public byte[] getIV() {
        return this.iv;
    }

    public CipherParameters getParameters() {
        return this.parameters;
    }
}
