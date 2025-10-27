package org.bouncycastle.asn1.pkcs;

import java.math.BigInteger;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERSequence;

/* loaded from: classes9.dex */
public class DHParameter extends ASN1Encodable {

    /* renamed from: g, reason: collision with root package name */
    DERInteger f27772g;

    /* renamed from: l, reason: collision with root package name */
    DERInteger f27773l;

    /* renamed from: p, reason: collision with root package name */
    DERInteger f27774p;

    public DHParameter(BigInteger bigInteger, BigInteger bigInteger2, int i2) {
        this.f27774p = new DERInteger(bigInteger);
        this.f27772g = new DERInteger(bigInteger2);
        this.f27773l = i2 != 0 ? new DERInteger(i2) : null;
    }

    public DHParameter(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.f27774p = (DERInteger) objects.nextElement();
        this.f27772g = (DERInteger) objects.nextElement();
        this.f27773l = objects.hasMoreElements() ? (DERInteger) objects.nextElement() : null;
    }

    public BigInteger getG() {
        return this.f27772g.getPositiveValue();
    }

    public BigInteger getL() {
        DERInteger dERInteger = this.f27773l;
        if (dERInteger == null) {
            return null;
        }
        return dERInteger.getPositiveValue();
    }

    public BigInteger getP() {
        return this.f27774p.getPositiveValue();
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public DERObject toASN1Object() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.f27774p);
        aSN1EncodableVector.add(this.f27772g);
        if (getL() != null) {
            aSN1EncodableVector.add(this.f27773l);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
