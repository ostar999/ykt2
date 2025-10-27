package org.bouncycastle.bcpg.sig;

import org.bouncycastle.bcpg.SignatureSubpacket;

/* loaded from: classes9.dex */
public class Exportable extends SignatureSubpacket {
    public Exportable(boolean z2, boolean z3) {
        super(4, z2, booleanToByteArray(z3));
    }

    public Exportable(boolean z2, byte[] bArr) {
        super(4, z2, bArr);
    }

    private static byte[] booleanToByteArray(boolean z2) {
        byte[] bArr = new byte[1];
        if (z2) {
            bArr[0] = 1;
        }
        return bArr;
    }

    public boolean isExportable() {
        return this.data[0] != 0;
    }
}
