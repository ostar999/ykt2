package org.bouncycastle.crypto.modes.gcm;

import org.bouncycastle.util.Arrays;

/* loaded from: classes9.dex */
public class Tables1kGCMExponentiator implements GCMExponentiator {
    byte[][] lookupPowX2 = new byte[64][];

    @Override // org.bouncycastle.crypto.modes.gcm.GCMExponentiator
    public void exponentiateX(long j2, byte[] bArr) {
        byte[] bArrOneAsBytes = GCMUtil.oneAsBytes();
        int i2 = 1;
        while (j2 > 0) {
            if ((1 & j2) != 0) {
                GCMUtil.multiply(bArrOneAsBytes, this.lookupPowX2[i2]);
            }
            i2++;
            j2 >>>= 1;
        }
        System.arraycopy(bArrOneAsBytes, 0, bArr, 0, 16);
    }

    @Override // org.bouncycastle.crypto.modes.gcm.GCMExponentiator
    public void init(byte[] bArr) {
        byte[][] bArr2 = this.lookupPowX2;
        byte[] bArr3 = new byte[16];
        bArr2[0] = bArr3;
        bArr3[0] = -128;
        bArr2[1] = Arrays.clone(bArr);
        for (int i2 = 2; i2 != 64; i2++) {
            byte[] bArrClone = Arrays.clone(this.lookupPowX2[i2 - 1]);
            GCMUtil.multiply(bArrClone, bArrClone);
            this.lookupPowX2[i2] = bArrClone;
        }
    }
}
