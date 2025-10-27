package org.bouncycastle.asn1.x9;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DEREncodable;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERSequence;

/* loaded from: classes9.dex */
public class DHDomainParameters extends ASN1Encodable {

    /* renamed from: g, reason: collision with root package name */
    private DERInteger f27782g;

    /* renamed from: j, reason: collision with root package name */
    private DERInteger f27783j;

    /* renamed from: p, reason: collision with root package name */
    private DERInteger f27784p;

    /* renamed from: q, reason: collision with root package name */
    private DERInteger f27785q;
    private DHValidationParms validationParms;

    private DHDomainParameters(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() < 3 || aSN1Sequence.size() > 5) {
            throw new IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
        }
        Enumeration objects = aSN1Sequence.getObjects();
        this.f27784p = DERInteger.getInstance(objects.nextElement());
        this.f27782g = DERInteger.getInstance(objects.nextElement());
        this.f27785q = DERInteger.getInstance(objects.nextElement());
        DEREncodable next = getNext(objects);
        if (next != null && (next instanceof DERInteger)) {
            this.f27783j = DERInteger.getInstance(next);
            next = getNext(objects);
        }
        if (next != null) {
            this.validationParms = DHValidationParms.getInstance(next.getDERObject());
        }
    }

    public DHDomainParameters(DERInteger dERInteger, DERInteger dERInteger2, DERInteger dERInteger3, DERInteger dERInteger4, DHValidationParms dHValidationParms) {
        if (dERInteger == null) {
            throw new IllegalArgumentException("'p' cannot be null");
        }
        if (dERInteger2 == null) {
            throw new IllegalArgumentException("'g' cannot be null");
        }
        if (dERInteger3 == null) {
            throw new IllegalArgumentException("'q' cannot be null");
        }
        this.f27784p = dERInteger;
        this.f27782g = dERInteger2;
        this.f27785q = dERInteger3;
        this.f27783j = dERInteger4;
        this.validationParms = dHValidationParms;
    }

    public static DHDomainParameters getInstance(Object obj) {
        if (obj == null || (obj instanceof DHDomainParameters)) {
            return (DHDomainParameters) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new DHDomainParameters((ASN1Sequence) obj);
        }
        throw new IllegalArgumentException("Invalid DHDomainParameters: " + obj.getClass().getName());
    }

    public static DHDomainParameters getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z2));
    }

    private static DEREncodable getNext(Enumeration enumeration) {
        if (enumeration.hasMoreElements()) {
            return (DEREncodable) enumeration.nextElement();
        }
        return null;
    }

    public DERInteger getG() {
        return this.f27782g;
    }

    public DERInteger getJ() {
        return this.f27783j;
    }

    public DERInteger getP() {
        return this.f27784p;
    }

    public DERInteger getQ() {
        return this.f27785q;
    }

    public DHValidationParms getValidationParms() {
        return this.validationParms;
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public DERObject toASN1Object() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.f27784p);
        aSN1EncodableVector.add(this.f27782g);
        aSN1EncodableVector.add(this.f27785q);
        DERInteger dERInteger = this.f27783j;
        if (dERInteger != null) {
            aSN1EncodableVector.add(dERInteger);
        }
        DHValidationParms dHValidationParms = this.validationParms;
        if (dHValidationParms != null) {
            aSN1EncodableVector.add(dHValidationParms);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
