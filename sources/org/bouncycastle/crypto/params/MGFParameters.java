package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.DerivationParameters;

/* loaded from: classes9.dex */
public class MGFParameters implements DerivationParameters {
    byte[] seed;

    public MGFParameters(byte[] bArr) {
        this(bArr, 0, bArr.length);
    }

    public MGFParameters(byte[] bArr, int i2, int i3) {
        byte[] bArr2 = new byte[i3];
        this.seed = bArr2;
        System.arraycopy(bArr, i2, bArr2, 0, i3);
    }

    public byte[] getSeed() {
        return this.seed;
    }
}
