package org.bouncycastle.asn1.x509;

import java.math.BigInteger;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERSequence;

/* loaded from: classes9.dex */
public class DSAParameter extends ASN1Encodable {

    /* renamed from: g, reason: collision with root package name */
    DERInteger f27779g;

    /* renamed from: p, reason: collision with root package name */
    DERInteger f27780p;

    /* renamed from: q, reason: collision with root package name */
    DERInteger f27781q;

    public DSAParameter(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        this.f27780p = new DERInteger(bigInteger);
        this.f27781q = new DERInteger(bigInteger2);
        this.f27779g = new DERInteger(bigInteger3);
    }

    public DSAParameter(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 3) {
            throw new IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
        }
        Enumeration objects = aSN1Sequence.getObjects();
        this.f27780p = DERInteger.getInstance(objects.nextElement());
        this.f27781q = DERInteger.getInstance(objects.nextElement());
        this.f27779g = DERInteger.getInstance(objects.nextElement());
    }

    public static DSAParameter getInstance(Object obj) {
        if (obj == null || (obj instanceof DSAParameter)) {
            return (DSAParameter) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new DSAParameter((ASN1Sequence) obj);
        }
        throw new IllegalArgumentException("Invalid DSAParameter: " + obj.getClass().getName());
    }

    public static DSAParameter getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z2));
    }

    public BigInteger getG() {
        return this.f27779g.getPositiveValue();
    }

    public BigInteger getP() {
        return this.f27780p.getPositiveValue();
    }

    public BigInteger getQ() {
        return this.f27781q.getPositiveValue();
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public DERObject toASN1Object() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.f27780p);
        aSN1EncodableVector.add(this.f27781q);
        aSN1EncodableVector.add(this.f27779g);
        return new DERSequence(aSN1EncodableVector);
    }
}
