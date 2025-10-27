package org.bouncycastle.crypto.modes.gcm;

import org.bouncycastle.util.Arrays;

/* loaded from: classes9.dex */
public class BasicGCMMultiplier implements GCMMultiplier {
    private byte[] H;

    @Override // org.bouncycastle.crypto.modes.gcm.GCMMultiplier
    public void init(byte[] bArr) {
        this.H = Arrays.clone(bArr);
    }

    @Override // org.bouncycastle.crypto.modes.gcm.GCMMultiplier
    public void multiplyH(byte[] bArr) {
        GCMUtil.multiply(bArr, this.H);
    }
}
