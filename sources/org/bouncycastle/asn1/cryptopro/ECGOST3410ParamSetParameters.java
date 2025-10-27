package org.bouncycastle.asn1.cryptopro;

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
public class ECGOST3410ParamSetParameters extends ASN1Encodable {

    /* renamed from: a, reason: collision with root package name */
    DERInteger f27761a;

    /* renamed from: b, reason: collision with root package name */
    DERInteger f27762b;

    /* renamed from: p, reason: collision with root package name */
    DERInteger f27763p;

    /* renamed from: q, reason: collision with root package name */
    DERInteger f27764q;

    /* renamed from: x, reason: collision with root package name */
    DERInteger f27765x;

    /* renamed from: y, reason: collision with root package name */
    DERInteger f27766y;

    public ECGOST3410ParamSetParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, int i2, BigInteger bigInteger5) {
        this.f27761a = new DERInteger(bigInteger);
        this.f27762b = new DERInteger(bigInteger2);
        this.f27763p = new DERInteger(bigInteger3);
        this.f27764q = new DERInteger(bigInteger4);
        this.f27765x = new DERInteger(i2);
        this.f27766y = new DERInteger(bigInteger5);
    }

    public ECGOST3410ParamSetParameters(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.f27761a = (DERInteger) objects.nextElement();
        this.f27762b = (DERInteger) objects.nextElement();
        this.f27763p = (DERInteger) objects.nextElement();
        this.f27764q = (DERInteger) objects.nextElement();
        this.f27765x = (DERInteger) objects.nextElement();
        this.f27766y = (DERInteger) objects.nextElement();
    }

    public static ECGOST3410ParamSetParameters getInstance(Object obj) {
        if (obj == null || (obj instanceof ECGOST3410ParamSetParameters)) {
            return (ECGOST3410ParamSetParameters) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new ECGOST3410ParamSetParameters((ASN1Sequence) obj);
        }
        throw new IllegalArgumentException("Invalid GOST3410Parameter: " + obj.getClass().getName());
    }

    public static ECGOST3410ParamSetParameters getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z2));
    }

    public BigInteger getA() {
        return this.f27761a.getPositiveValue();
    }

    public BigInteger getP() {
        return this.f27763p.getPositiveValue();
    }

    public BigInteger getQ() {
        return this.f27764q.getPositiveValue();
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public DERObject toASN1Object() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.f27761a);
        aSN1EncodableVector.add(this.f27762b);
        aSN1EncodableVector.add(this.f27763p);
        aSN1EncodableVector.add(this.f27764q);
        aSN1EncodableVector.add(this.f27765x);
        aSN1EncodableVector.add(this.f27766y);
        return new DERSequence(aSN1EncodableVector);
    }
}
