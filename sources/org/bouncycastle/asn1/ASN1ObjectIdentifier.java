package org.bouncycastle.asn1;

import cn.hutool.core.text.StrPool;

/* loaded from: classes9.dex */
public class ASN1ObjectIdentifier extends DERObjectIdentifier {
    public ASN1ObjectIdentifier(String str) {
        super(str);
    }

    public ASN1ObjectIdentifier(byte[] bArr) {
        super(bArr);
    }

    public ASN1ObjectIdentifier branch(String str) {
        return new ASN1ObjectIdentifier(getId() + StrPool.DOT + str);
    }
}
