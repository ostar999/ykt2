package org.bouncycastle.asn1.x509;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;

/* loaded from: classes9.dex */
public class Holder extends ASN1Encodable {
    IssuerSerial baseCertificateID;
    GeneralNames entityName;
    ObjectDigestInfo objectDigestInfo;
    private int version;

    public Holder(ASN1Sequence aSN1Sequence) {
        this.version = 1;
        if (aSN1Sequence.size() > 3) {
            throw new IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
        }
        for (int i2 = 0; i2 != aSN1Sequence.size(); i2++) {
            ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(i2));
            int tagNo = aSN1TaggedObject.getTagNo();
            if (tagNo == 0) {
                this.baseCertificateID = IssuerSerial.getInstance(aSN1TaggedObject, false);
            } else if (tagNo == 1) {
                this.entityName = GeneralNames.getInstance(aSN1TaggedObject, false);
            } else {
                if (tagNo != 2) {
                    throw new IllegalArgumentException("unknown tag in Holder");
                }
                this.objectDigestInfo = ObjectDigestInfo.getInstance(aSN1TaggedObject, false);
            }
        }
        this.version = 1;
    }

    public Holder(ASN1TaggedObject aSN1TaggedObject) {
        this.version = 1;
        int tagNo = aSN1TaggedObject.getTagNo();
        if (tagNo == 0) {
            this.baseCertificateID = IssuerSerial.getInstance(aSN1TaggedObject, false);
        } else {
            if (tagNo != 1) {
                throw new IllegalArgumentException("unknown tag in Holder");
            }
            this.entityName = GeneralNames.getInstance(aSN1TaggedObject, false);
        }
        this.version = 0;
    }

    public Holder(GeneralNames generalNames) {
        this.version = 1;
        this.entityName = generalNames;
    }

    public Holder(GeneralNames generalNames, int i2) {
        this.entityName = generalNames;
        this.version = i2;
    }

    public Holder(IssuerSerial issuerSerial) {
        this.version = 1;
        this.baseCertificateID = issuerSerial;
    }

    public Holder(IssuerSerial issuerSerial, int i2) {
        this.baseCertificateID = issuerSerial;
        this.version = i2;
    }

    public Holder(ObjectDigestInfo objectDigestInfo) {
        this.version = 1;
        this.objectDigestInfo = objectDigestInfo;
    }

    public static Holder getInstance(Object obj) {
        if (obj instanceof Holder) {
            return (Holder) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new Holder((ASN1Sequence) obj);
        }
        if (obj instanceof ASN1TaggedObject) {
            return new Holder((ASN1TaggedObject) obj);
        }
        throw new IllegalArgumentException("unknown object in factory: " + obj.getClass().getName());
    }

    public IssuerSerial getBaseCertificateID() {
        return this.baseCertificateID;
    }

    public GeneralNames getEntityName() {
        return this.entityName;
    }

    public ObjectDigestInfo getObjectDigestInfo() {
        return this.objectDigestInfo;
    }

    public int getVersion() {
        return this.version;
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public DERObject toASN1Object() {
        if (this.version != 1) {
            return this.entityName != null ? new DERTaggedObject(false, 1, this.entityName) : new DERTaggedObject(false, 0, this.baseCertificateID);
        }
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.baseCertificateID != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, this.baseCertificateID));
        }
        if (this.entityName != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, this.entityName));
        }
        if (this.objectDigestInfo != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 2, this.objectDigestInfo));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
