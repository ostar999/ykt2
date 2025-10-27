package org.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes9.dex */
public class BERNull extends DERNull {
    public static final BERNull INSTANCE = new BERNull();

    @Override // org.bouncycastle.asn1.DERNull, org.bouncycastle.asn1.ASN1Null, org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.DERObject
    public void encode(DEROutputStream dEROutputStream) throws IOException {
        if ((dEROutputStream instanceof ASN1OutputStream) || (dEROutputStream instanceof BEROutputStream)) {
            dEROutputStream.write(5);
        } else {
            super.encode(dEROutputStream);
        }
    }
}
