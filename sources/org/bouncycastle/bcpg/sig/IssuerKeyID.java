package org.bouncycastle.bcpg.sig;

import org.bouncycastle.bcpg.SignatureSubpacket;

/* loaded from: classes9.dex */
public class IssuerKeyID extends SignatureSubpacket {
    public IssuerKeyID(boolean z2, long j2) {
        super(16, z2, keyIDToBytes(j2));
    }

    public IssuerKeyID(boolean z2, byte[] bArr) {
        super(16, z2, bArr);
    }

    public static byte[] keyIDToBytes(long j2) {
        return new byte[]{(byte) (j2 >> 56), (byte) (j2 >> 48), (byte) (j2 >> 40), (byte) (j2 >> 32), (byte) (j2 >> 24), (byte) (j2 >> 16), (byte) (j2 >> 8), (byte) j2};
    }

    public long getKeyID() {
        byte[] bArr = this.data;
        return ((bArr[0] & 255) << 56) | ((bArr[1] & 255) << 48) | ((bArr[2] & 255) << 40) | ((bArr[3] & 255) << 32) | ((bArr[4] & 255) << 24) | ((bArr[5] & 255) << 16) | ((bArr[6] & 255) << 8) | (bArr[7] & 255);
    }
}
