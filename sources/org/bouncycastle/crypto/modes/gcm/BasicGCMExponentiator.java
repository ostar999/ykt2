package org.bouncycastle.crypto.modes.gcm;

import org.bouncycastle.util.Arrays;

/* loaded from: classes9.dex */
public class BasicGCMExponentiator implements GCMExponentiator {

    /* renamed from: x, reason: collision with root package name */
    private byte[] f27849x;

    @Override // org.bouncycastle.crypto.modes.gcm.GCMExponentiator
    public void exponentiateX(long j2, byte[] bArr) {
        byte[] bArrOneAsBytes = GCMUtil.oneAsBytes();
        if (j2 > 0) {
            byte[] bArrClone = Arrays.clone(this.f27849x);
            do {
                if ((1 & j2) != 0) {
                    GCMUtil.multiply(bArrOneAsBytes, bArrClone);
                }
                GCMUtil.multiply(bArrClone, bArrClone);
                j2 >>>= 1;
            } while (j2 > 0);
        }
        System.arraycopy(bArrOneAsBytes, 0, bArr, 0, 16);
    }

    @Override // org.bouncycastle.crypto.modes.gcm.GCMExponentiator
    public void init(byte[] bArr) {
        this.f27849x = Arrays.clone(bArr);
    }
}
