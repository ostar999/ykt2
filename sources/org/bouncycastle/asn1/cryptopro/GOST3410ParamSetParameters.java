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
public class GOST3410ParamSetParameters extends ASN1Encodable {

    /* renamed from: a, reason: collision with root package name */
    DERInteger f27767a;
    int keySize;

    /* renamed from: p, reason: collision with root package name */
    DERInteger f27768p;

    /* renamed from: q, reason: collision with root package name */
    DERInteger f27769q;

    public GOST3410ParamSetParameters(int i2, BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        this.keySize = i2;
        this.f27768p = new DERInteger(bigInteger);
        this.f27769q = new DERInteger(bigInteger2);
        this.f27767a = new DERInteger(bigInteger3);
    }

    public GOST3410ParamSetParameters(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.keySize = ((DERInteger) objects.nextElement()).getValue().intValue();
        this.f27768p = (DERInteger) objects.nextElement();
        this.f27769q = (DERInteger) objects.nextElement();
        this.f27767a = (DERInteger) objects.nextElement();
    }

    public static GOST3410ParamSetParameters getInstance(Object obj) {
        if (obj == null || (obj instanceof GOST3410ParamSetParameters)) {
            return (GOST3410ParamSetParameters) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new GOST3410ParamSetParameters((ASN1Sequence) obj);
        }
        throw new IllegalArgumentException("Invalid GOST3410Parameter: " + obj.getClass().getName());
    }

    public static GOST3410ParamSetParameters getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z2));
    }

    public BigInteger getA() {
        return this.f27767a.getPositiveValue();
    }

    public int getKeySize() {
        return this.keySize;
    }

    public int getLKeySize() {
        return this.keySize;
    }

    public BigInteger getP() {
        return this.f27768p.getPositiveValue();
    }

    public BigInteger getQ() {
        return this.f27769q.getPositiveValue();
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public DERObject toASN1Object() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(new DERInteger(this.keySize));
        aSN1EncodableVector.add(this.f27768p);
        aSN1EncodableVector.add(this.f27769q);
        aSN1EncodableVector.add(this.f27767a);
        return new DERSequence(aSN1EncodableVector);
    }
}
