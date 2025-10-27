package org.bouncycastle.asn1.x9;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.math.ec.ECCurve;

/* loaded from: classes9.dex */
public class X9Curve extends ASN1Encodable implements X9ObjectIdentifiers {
    private ECCurve curve;
    private DERObjectIdentifier fieldIdentifier;
    private byte[] seed;

    /* JADX WARN: Removed duplicated region for block: B:15:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public X9Curve(org.bouncycastle.asn1.x9.X9FieldID r14, org.bouncycastle.asn1.ASN1Sequence r15) {
        /*
            Method dump skipped, instructions count: 263
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.asn1.x9.X9Curve.<init>(org.bouncycastle.asn1.x9.X9FieldID, org.bouncycastle.asn1.ASN1Sequence):void");
    }

    public X9Curve(ECCurve eCCurve) {
        this.fieldIdentifier = null;
        this.curve = eCCurve;
        this.seed = null;
        setFieldIdentifier();
    }

    public X9Curve(ECCurve eCCurve, byte[] bArr) {
        this.fieldIdentifier = null;
        this.curve = eCCurve;
        this.seed = bArr;
        setFieldIdentifier();
    }

    private void setFieldIdentifier() {
        ASN1ObjectIdentifier aSN1ObjectIdentifier;
        ECCurve eCCurve = this.curve;
        if (eCCurve instanceof ECCurve.Fp) {
            aSN1ObjectIdentifier = X9ObjectIdentifiers.prime_field;
        } else {
            if (!(eCCurve instanceof ECCurve.F2m)) {
                throw new IllegalArgumentException("This type of ECCurve is not implemented");
            }
            aSN1ObjectIdentifier = X9ObjectIdentifiers.characteristic_two_field;
        }
        this.fieldIdentifier = aSN1ObjectIdentifier;
    }

    public ECCurve getCurve() {
        return this.curve;
    }

    public byte[] getSeed() {
        return this.seed;
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public DERObject toASN1Object() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.fieldIdentifier.equals(X9ObjectIdentifiers.prime_field) || this.fieldIdentifier.equals(X9ObjectIdentifiers.characteristic_two_field)) {
            aSN1EncodableVector.add(new X9FieldElement(this.curve.getA()).getDERObject());
            X9FieldElement x9FieldElement = new X9FieldElement(this.curve.getB());
            aSN1EncodableVector.add(x9FieldElement.getDERObject());
        }
        if (this.seed != null) {
            aSN1EncodableVector.add(new DERBitString(this.seed));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
