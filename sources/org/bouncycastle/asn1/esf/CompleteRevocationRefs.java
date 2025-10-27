package org.bouncycastle.asn1.esf;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERSequence;

/* loaded from: classes9.dex */
public class CompleteRevocationRefs extends ASN1Encodable {
    private ASN1Sequence crlOcspRefs;

    private CompleteRevocationRefs(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        while (objects.hasMoreElements()) {
            CrlOcspRef.getInstance(objects.nextElement());
        }
        this.crlOcspRefs = aSN1Sequence;
    }

    public CompleteRevocationRefs(CrlOcspRef[] crlOcspRefArr) {
        this.crlOcspRefs = new DERSequence(crlOcspRefArr);
    }

    public static CompleteRevocationRefs getInstance(Object obj) {
        if (obj instanceof CompleteRevocationRefs) {
            return (CompleteRevocationRefs) obj;
        }
        if (obj != null) {
            return new CompleteRevocationRefs(ASN1Sequence.getInstance(obj));
        }
        throw new IllegalArgumentException("null value in getInstance");
    }

    public CrlOcspRef[] getCrlOcspRefs() {
        int size = this.crlOcspRefs.size();
        CrlOcspRef[] crlOcspRefArr = new CrlOcspRef[size];
        for (int i2 = 0; i2 < size; i2++) {
            crlOcspRefArr[i2] = CrlOcspRef.getInstance(this.crlOcspRefs.getObjectAt(i2));
        }
        return crlOcspRefArr;
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public DERObject toASN1Object() {
        return this.crlOcspRefs;
    }
}
