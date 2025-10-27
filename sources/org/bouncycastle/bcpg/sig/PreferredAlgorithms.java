package org.bouncycastle.bcpg.sig;

import org.bouncycastle.bcpg.SignatureSubpacket;

/* loaded from: classes9.dex */
public class PreferredAlgorithms extends SignatureSubpacket {
    public PreferredAlgorithms(int i2, boolean z2, byte[] bArr) {
        super(i2, z2, bArr);
    }

    public PreferredAlgorithms(int i2, boolean z2, int[] iArr) {
        super(i2, z2, intToByteArray(iArr));
    }

    private static byte[] intToByteArray(int[] iArr) {
        byte[] bArr = new byte[iArr.length];
        for (int i2 = 0; i2 != iArr.length; i2++) {
            bArr[i2] = (byte) iArr[i2];
        }
        return bArr;
    }

    public int[] getPreferences() {
        int length = this.data.length;
        int[] iArr = new int[length];
        for (int i2 = 0; i2 != length; i2++) {
            iArr[i2] = this.data[i2] & 255;
        }
        return iArr;
    }

    public int[] getPreferrences() {
        return getPreferences();
    }
}
