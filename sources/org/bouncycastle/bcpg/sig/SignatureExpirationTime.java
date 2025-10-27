package org.bouncycastle.bcpg.sig;

import org.bouncycastle.bcpg.SignatureSubpacket;

/* loaded from: classes9.dex */
public class SignatureExpirationTime extends SignatureSubpacket {
    public SignatureExpirationTime(boolean z2, long j2) {
        super(3, z2, timeToBytes(j2));
    }

    public SignatureExpirationTime(boolean z2, byte[] bArr) {
        super(3, z2, bArr);
    }

    public static byte[] timeToBytes(long j2) {
        return new byte[]{(byte) (j2 >> 24), (byte) (j2 >> 16), (byte) (j2 >> 8), (byte) j2};
    }

    public long getTime() {
        byte[] bArr = this.data;
        return ((bArr[0] & 255) << 24) | ((bArr[1] & 255) << 16) | ((bArr[2] & 255) << 8) | (bArr[3] & 255);
    }
}
