package org.bouncycastle.asn1.oiw;

import java.math.BigInteger;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERSequence;

/* loaded from: classes9.dex */
public class ElGamalParameter extends ASN1Encodable {

    /* renamed from: g, reason: collision with root package name */
    DERInteger f27770g;

    /* renamed from: p, reason: collision with root package name */
    DERInteger f27771p;

    public ElGamalParameter(BigInteger bigInteger, BigInteger bigInteger2) {
        this.f27771p = new DERInteger(bigInteger);
        this.f27770g = new DERInteger(bigInteger2);
    }

    public ElGamalParameter(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.f27771p = (DERInteger) objects.nextElement();
        this.f27770g = (DERInteger) objects.nextElement();
    }

    public BigInteger getG() {
        return this.f27770g.getPositiveValue();
    }

    public BigInteger getP() {
        return this.f27771p.getPositiveValue();
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public DERObject toASN1Object() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.f27771p);
        aSN1EncodableVector.add(this.f27770g);
        return new DERSequence(aSN1EncodableVector);
    }
}
