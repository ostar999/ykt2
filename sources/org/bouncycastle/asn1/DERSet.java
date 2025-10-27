package org.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;

/* loaded from: classes9.dex */
public class DERSet extends ASN1Set {
    public DERSet() {
    }

    public DERSet(ASN1EncodableVector aSN1EncodableVector) {
        this(aSN1EncodableVector, true);
    }

    public DERSet(ASN1EncodableVector aSN1EncodableVector, boolean z2) {
        for (int i2 = 0; i2 != aSN1EncodableVector.size(); i2++) {
            addObject(aSN1EncodableVector.get(i2));
        }
        if (z2) {
            sort();
        }
    }

    public DERSet(DEREncodable dEREncodable) {
        addObject(dEREncodable);
    }

    public DERSet(ASN1Encodable[] aSN1EncodableArr) {
        for (int i2 = 0; i2 != aSN1EncodableArr.length; i2++) {
            addObject(aSN1EncodableArr[i2]);
        }
        sort();
    }

    @Override // org.bouncycastle.asn1.ASN1Set, org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.DERObject
    public void encode(DEROutputStream dEROutputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DEROutputStream dEROutputStream2 = new DEROutputStream(byteArrayOutputStream);
        Enumeration objects = getObjects();
        while (objects.hasMoreElements()) {
            dEROutputStream2.writeObject(objects.nextElement());
        }
        dEROutputStream2.close();
        dEROutputStream.writeEncoded(49, byteArrayOutputStream.toByteArray());
    }
}
