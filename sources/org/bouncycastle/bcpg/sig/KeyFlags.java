package org.bouncycastle.bcpg.sig;

import org.bouncycastle.bcpg.SignatureSubpacket;

/* loaded from: classes9.dex */
public class KeyFlags extends SignatureSubpacket {
    public static final int AUTHENTICATION = 32;
    public static final int CERTIFY_OTHER = 1;
    public static final int ENCRYPT_COMMS = 4;
    public static final int ENCRYPT_STORAGE = 8;
    public static final int SHARED = 128;
    public static final int SIGN_DATA = 2;
    public static final int SPLIT = 16;

    public KeyFlags(boolean z2, int i2) {
        super(27, z2, intToByteArray(i2));
    }

    public KeyFlags(boolean z2, byte[] bArr) {
        super(27, z2, bArr);
    }

    private static byte[] intToByteArray(int i2) {
        byte[] bArr = new byte[4];
        int i3 = 0;
        for (int i4 = 0; i4 != 4; i4++) {
            byte b3 = (byte) (i2 >> (i4 * 8));
            bArr[i4] = b3;
            if (b3 != 0) {
                i3 = i4;
            }
        }
        int i5 = i3 + 1;
        byte[] bArr2 = new byte[i5];
        System.arraycopy(bArr, 0, bArr2, 0, i5);
        return bArr2;
    }

    public int getFlags() {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            byte[] bArr = this.data;
            if (i2 == bArr.length) {
                return i3;
            }
            i3 |= (bArr[i2] & 255) << (i2 * 8);
            i2++;
        }
    }
}
