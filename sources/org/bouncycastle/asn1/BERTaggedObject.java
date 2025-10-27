package org.bouncycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;

/* loaded from: classes9.dex */
public class BERTaggedObject extends DERTaggedObject {
    public BERTaggedObject(int i2) {
        super(false, i2, new BERSequence());
    }

    public BERTaggedObject(int i2, DEREncodable dEREncodable) {
        super(i2, dEREncodable);
    }

    public BERTaggedObject(boolean z2, int i2, DEREncodable dEREncodable) {
        super(z2, i2, dEREncodable);
    }

    @Override // org.bouncycastle.asn1.DERTaggedObject, org.bouncycastle.asn1.ASN1TaggedObject, org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.DERObject
    public void encode(DEROutputStream dEROutputStream) throws IOException {
        Enumeration objects;
        if (!(dEROutputStream instanceof ASN1OutputStream) && !(dEROutputStream instanceof BEROutputStream)) {
            super.encode(dEROutputStream);
            return;
        }
        dEROutputStream.writeTag(160, this.tagNo);
        dEROutputStream.write(128);
        if (!this.empty) {
            if (this.explicit) {
                dEROutputStream.writeObject(this.obj);
            } else {
                DEREncodable dEREncodable = this.obj;
                if (dEREncodable instanceof ASN1OctetString) {
                    objects = dEREncodable instanceof BERConstructedOctetString ? ((BERConstructedOctetString) dEREncodable).getObjects() : new BERConstructedOctetString(((ASN1OctetString) dEREncodable).getOctets()).getObjects();
                } else if (dEREncodable instanceof ASN1Sequence) {
                    objects = ((ASN1Sequence) dEREncodable).getObjects();
                } else {
                    if (!(dEREncodable instanceof ASN1Set)) {
                        throw new RuntimeException("not implemented: " + this.obj.getClass().getName());
                    }
                    objects = ((ASN1Set) dEREncodable).getObjects();
                }
                while (objects.hasMoreElements()) {
                    dEROutputStream.writeObject(objects.nextElement());
                }
            }
        }
        dEROutputStream.write(0);
        dEROutputStream.write(0);
    }
}
