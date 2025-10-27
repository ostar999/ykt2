package org.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;

/* loaded from: classes9.dex */
public class DERSequence extends ASN1Sequence {
    public DERSequence() {
    }

    public DERSequence(ASN1EncodableVector aSN1EncodableVector) {
        for (int i2 = 0; i2 != aSN1EncodableVector.size(); i2++) {
            addObject(aSN1EncodableVector.get(i2));
        }
    }

    public DERSequence(DEREncodable dEREncodable) {
        addObject(dEREncodable);
    }

    public DERSequence(ASN1Encodable[] aSN1EncodableArr) {
        for (int i2 = 0; i2 != aSN1EncodableArr.length; i2++) {
            addObject(aSN1EncodableArr[i2]);
        }
    }

    @Override // org.bouncycastle.asn1.ASN1Sequence, org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.DERObject
    public void encode(DEROutputStream dEROutputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DEROutputStream dEROutputStream2 = new DEROutputStream(byteArrayOutputStream);
        Enumeration objects = getObjects();
        while (objects.hasMoreElements()) {
            dEROutputStream2.writeObject(objects.nextElement());
        }
        dEROutputStream2.close();
        dEROutputStream.writeEncoded(48, byteArrayOutputStream.toByteArray());
    }
}
