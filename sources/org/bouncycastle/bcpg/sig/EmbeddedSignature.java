package org.bouncycastle.bcpg.sig;

import org.bouncycastle.bcpg.SignatureSubpacket;

/* loaded from: classes9.dex */
public class EmbeddedSignature extends SignatureSubpacket {
    public EmbeddedSignature(boolean z2, byte[] bArr) {
        super(32, z2, bArr);
    }
}
