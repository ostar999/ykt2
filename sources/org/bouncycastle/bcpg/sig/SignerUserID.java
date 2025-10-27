package org.bouncycastle.bcpg.sig;

import org.bouncycastle.bcpg.SignatureSubpacket;

/* loaded from: classes9.dex */
public class SignerUserID extends SignatureSubpacket {
    public SignerUserID(boolean z2, String str) {
        super(28, z2, userIDToBytes(str));
    }

    public SignerUserID(boolean z2, byte[] bArr) {
        super(28, z2, bArr);
    }

    private static byte[] userIDToBytes(String str) {
        byte[] bArr = new byte[str.length()];
        for (int i2 = 0; i2 != str.length(); i2++) {
            bArr[i2] = (byte) str.charAt(i2);
        }
        return bArr;
    }

    public String getID() {
        int length = this.data.length;
        char[] cArr = new char[length];
        for (int i2 = 0; i2 != length; i2++) {
            cArr[i2] = (char) (this.data[i2] & 255);
        }
        return new String(cArr);
    }
}
