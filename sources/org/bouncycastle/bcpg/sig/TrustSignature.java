package org.bouncycastle.bcpg.sig;

import org.bouncycastle.bcpg.SignatureSubpacket;

/* loaded from: classes9.dex */
public class TrustSignature extends SignatureSubpacket {
    public TrustSignature(boolean z2, int i2, int i3) {
        super(5, z2, intToByteArray(i2, i3));
    }

    public TrustSignature(boolean z2, byte[] bArr) {
        super(5, z2, bArr);
    }

    private static byte[] intToByteArray(int i2, int i3) {
        return new byte[]{(byte) i2, (byte) i3};
    }

    public int getDepth() {
        return this.data[0] & 255;
    }

    public int getTrustAmount() {
        return this.data[1] & 255;
    }
}
