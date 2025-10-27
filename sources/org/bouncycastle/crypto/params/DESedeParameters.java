package org.bouncycastle.crypto.params;

/* loaded from: classes9.dex */
public class DESedeParameters extends DESParameters {
    public static final int DES_EDE_KEY_LENGTH = 24;

    public DESedeParameters(byte[] bArr) {
        super(bArr);
        if (isWeakKey(bArr, 0, bArr.length)) {
            throw new IllegalArgumentException("attempt to create weak DESede key");
        }
    }

    public static boolean isWeakKey(byte[] bArr, int i2) {
        return isWeakKey(bArr, i2, bArr.length - i2);
    }

    public static boolean isWeakKey(byte[] bArr, int i2, int i3) {
        while (i2 < i3) {
            if (DESParameters.isWeakKey(bArr, i2)) {
                return true;
            }
            i2 += 8;
        }
        return false;
    }
}
