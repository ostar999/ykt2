package org.bouncycastle.asn1.x9;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DERSequence;

/* loaded from: classes9.dex */
public class X9FieldID extends ASN1Encodable implements X9ObjectIdentifiers {
    private DERObjectIdentifier id;
    private DERObject parameters;

    public X9FieldID(int i2, int i3, int i4, int i5) {
        this.id = X9ObjectIdentifiers.characteristic_two_field;
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(new DERInteger(i2));
        if (i4 == 0) {
            aSN1EncodableVector.add(X9ObjectIdentifiers.tpBasis);
            aSN1EncodableVector.add(new DERInteger(i3));
        } else {
            aSN1EncodableVector.add(X9ObjectIdentifiers.ppBasis);
            ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
            aSN1EncodableVector2.add(new DERInteger(i3));
            aSN1EncodableVector2.add(new DERInteger(i4));
            aSN1EncodableVector2.add(new DERInteger(i5));
            aSN1EncodableVector.add(new DERSequence(aSN1EncodableVector2));
        }
        this.parameters = new DERSequence(aSN1EncodableVector);
    }

    public X9FieldID(BigInteger bigInteger) {
        this.id = X9ObjectIdentifiers.prime_field;
        this.parameters = new DERInteger(bigInteger);
    }

    public X9FieldID(ASN1Sequence aSN1Sequence) {
        this.id = (DERObjectIdentifier) aSN1Sequence.getObjectAt(0);
        this.parameters = (DERObject) aSN1Sequence.getObjectAt(1);
    }

    public DERObjectIdentifier getIdentifier() {
        return this.id;
    }

    public DERObject getParameters() {
        return this.parameters;
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public DERObject toASN1Object() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.id);
        aSN1EncodableVector.add(this.parameters);
        return new DERSequence(aSN1EncodableVector);
    }
}
