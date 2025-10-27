package org.bouncycastle.asn1.x509;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERSequence;

/* loaded from: classes9.dex */
public class GeneralNames extends ASN1Encodable {
    private final GeneralName[] names;

    public GeneralNames(ASN1Sequence aSN1Sequence) {
        this.names = new GeneralName[aSN1Sequence.size()];
        for (int i2 = 0; i2 != aSN1Sequence.size(); i2++) {
            this.names[i2] = GeneralName.getInstance(aSN1Sequence.getObjectAt(i2));
        }
    }

    public GeneralNames(GeneralName generalName) {
        this.names = new GeneralName[]{generalName};
    }

    public static GeneralNames getInstance(Object obj) {
        if (obj == null || (obj instanceof GeneralNames)) {
            return (GeneralNames) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new GeneralNames((ASN1Sequence) obj);
        }
        throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
    }

    public static GeneralNames getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z2));
    }

    public GeneralName[] getNames() {
        GeneralName[] generalNameArr = this.names;
        GeneralName[] generalNameArr2 = new GeneralName[generalNameArr.length];
        System.arraycopy(generalNameArr, 0, generalNameArr2, 0, generalNameArr.length);
        return generalNameArr2;
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public DERObject toASN1Object() {
        return new DERSequence(this.names);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        String property = System.getProperty("line.separator");
        stringBuffer.append("GeneralNames:");
        stringBuffer.append(property);
        for (int i2 = 0; i2 != this.names.length; i2++) {
            stringBuffer.append("    ");
            stringBuffer.append(this.names[i2]);
            stringBuffer.append(property);
        }
        return stringBuffer.toString();
    }
}
