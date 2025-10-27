package org.bouncycastle.asn1.crmf;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

/* loaded from: classes9.dex */
public class EncryptedValue extends ASN1Encodable {
    private DERBitString encSymmKey;
    private DERBitString encValue;
    private AlgorithmIdentifier intendedAlg;
    private AlgorithmIdentifier keyAlg;
    private AlgorithmIdentifier symmAlg;
    private ASN1OctetString valueHint;

    private EncryptedValue(ASN1Sequence aSN1Sequence) {
        int i2 = 0;
        while (aSN1Sequence.getObjectAt(i2) instanceof ASN1TaggedObject) {
            ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) aSN1Sequence.getObjectAt(i2);
            int tagNo = aSN1TaggedObject.getTagNo();
            if (tagNo == 0) {
                this.intendedAlg = AlgorithmIdentifier.getInstance(aSN1TaggedObject, false);
            } else if (tagNo == 1) {
                this.symmAlg = AlgorithmIdentifier.getInstance(aSN1TaggedObject, false);
            } else if (tagNo == 2) {
                this.encSymmKey = DERBitString.getInstance(aSN1TaggedObject, false);
            } else if (tagNo == 3) {
                this.keyAlg = AlgorithmIdentifier.getInstance(aSN1TaggedObject, false);
            } else if (tagNo == 4) {
                this.valueHint = ASN1OctetString.getInstance(aSN1TaggedObject, false);
            }
            i2++;
        }
        this.encValue = DERBitString.getInstance(aSN1Sequence.getObjectAt(i2));
    }

    public EncryptedValue(AlgorithmIdentifier algorithmIdentifier, AlgorithmIdentifier algorithmIdentifier2, DERBitString dERBitString, AlgorithmIdentifier algorithmIdentifier3, ASN1OctetString aSN1OctetString, DERBitString dERBitString2) {
        if (dERBitString2 == null) {
            throw new IllegalArgumentException("'encValue' cannot be null");
        }
        this.intendedAlg = algorithmIdentifier;
        this.symmAlg = algorithmIdentifier2;
        this.encSymmKey = dERBitString;
        this.keyAlg = algorithmIdentifier3;
        this.valueHint = aSN1OctetString;
        this.encValue = dERBitString2;
    }

    private void addOptional(ASN1EncodableVector aSN1EncodableVector, int i2, ASN1Encodable aSN1Encodable) {
        if (aSN1Encodable != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, i2, aSN1Encodable));
        }
    }

    public static EncryptedValue getInstance(Object obj) {
        if (obj instanceof EncryptedValue) {
            return (EncryptedValue) obj;
        }
        if (obj != null) {
            return new EncryptedValue(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public DERBitString getEncSymmKey() {
        return this.encSymmKey;
    }

    public DERBitString getEncValue() {
        return this.encValue;
    }

    public AlgorithmIdentifier getIntendedAlg() {
        return this.intendedAlg;
    }

    public AlgorithmIdentifier getKeyAlg() {
        return this.keyAlg;
    }

    public AlgorithmIdentifier getSymmAlg() {
        return this.symmAlg;
    }

    public ASN1OctetString getValueHint() {
        return this.valueHint;
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public DERObject toASN1Object() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        addOptional(aSN1EncodableVector, 0, this.intendedAlg);
        addOptional(aSN1EncodableVector, 1, this.symmAlg);
        addOptional(aSN1EncodableVector, 2, this.encSymmKey);
        addOptional(aSN1EncodableVector, 3, this.keyAlg);
        addOptional(aSN1EncodableVector, 4, this.valueHint);
        aSN1EncodableVector.add(this.encValue);
        return new DERSequence(aSN1EncodableVector);
    }
}
