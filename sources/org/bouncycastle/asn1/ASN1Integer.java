package org.bouncycastle.asn1;

import java.math.BigInteger;

/* loaded from: classes9.dex */
public class ASN1Integer extends DERInteger {
    public ASN1Integer(int i2) {
        super(i2);
    }

    public ASN1Integer(BigInteger bigInteger) {
        super(bigInteger);
    }

    public ASN1Integer(byte[] bArr) {
        super(bArr);
    }
}
