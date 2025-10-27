package org.bouncycastle.bcpg.sig;

import java.util.Date;
import org.bouncycastle.bcpg.SignatureSubpacket;

/* loaded from: classes9.dex */
public class SignatureCreationTime extends SignatureSubpacket {
    public SignatureCreationTime(boolean z2, Date date) {
        super(2, z2, timeToBytes(date));
    }

    public SignatureCreationTime(boolean z2, byte[] bArr) {
        super(2, z2, bArr);
    }

    public static byte[] timeToBytes(Date date) {
        return new byte[]{(byte) (r1 >> 24), (byte) (r1 >> 16), (byte) (r1 >> 8), (byte) (date.getTime() / 1000)};
    }

    public Date getTime() {
        byte[] bArr = this.data;
        return new Date((((bArr[0] & 255) << 24) | ((bArr[1] & 255) << 16) | ((bArr[2] & 255) << 8) | (bArr[3] & 255)) * 1000);
    }
}
