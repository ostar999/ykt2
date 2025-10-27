package org.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes9.dex */
public class DERTaggedObject extends ASN1TaggedObject {
    private static final byte[] ZERO_BYTES = new byte[0];

    public DERTaggedObject(int i2) {
        super(false, i2, new DERSequence());
    }

    public DERTaggedObject(int i2, DEREncodable dEREncodable) {
        super(i2, dEREncodable);
    }

    public DERTaggedObject(boolean z2, int i2, DEREncodable dEREncodable) {
        super(z2, i2, dEREncodable);
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObject, org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.DERObject
    public void encode(DEROutputStream dEROutputStream) throws IOException {
        if (this.empty) {
            dEROutputStream.writeEncoded(160, this.tagNo, ZERO_BYTES);
            return;
        }
        byte[] encoded = this.obj.getDERObject().getEncoded(ASN1Encodable.DER);
        if (this.explicit) {
            dEROutputStream.writeEncoded(160, this.tagNo, encoded);
        } else {
            dEROutputStream.writeTag((encoded[0] & 32) == 0 ? 128 : 160, this.tagNo);
            dEROutputStream.write(encoded, 1, encoded.length - 1);
        }
    }
}
