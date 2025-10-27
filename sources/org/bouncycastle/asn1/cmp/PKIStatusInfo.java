package org.bouncycastle.asn1.cmp;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DEREncodable;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERSequence;

/* loaded from: classes9.dex */
public class PKIStatusInfo extends ASN1Encodable {
    DERBitString failInfo;
    DERInteger status;
    PKIFreeText statusString;

    public PKIStatusInfo(int i2) {
        this.status = new DERInteger(i2);
    }

    public PKIStatusInfo(int i2, PKIFreeText pKIFreeText) {
        this.status = new DERInteger(i2);
        this.statusString = pKIFreeText;
    }

    public PKIStatusInfo(int i2, PKIFreeText pKIFreeText, PKIFailureInfo pKIFailureInfo) {
        this.status = new DERInteger(i2);
        this.statusString = pKIFreeText;
        this.failInfo = pKIFailureInfo;
    }

    public PKIStatusInfo(ASN1Sequence aSN1Sequence) {
        DEREncodable objectAt;
        this.status = DERInteger.getInstance(aSN1Sequence.getObjectAt(0));
        this.statusString = null;
        this.failInfo = null;
        if (aSN1Sequence.size() > 2) {
            this.statusString = PKIFreeText.getInstance(aSN1Sequence.getObjectAt(1));
            objectAt = aSN1Sequence.getObjectAt(2);
        } else {
            if (aSN1Sequence.size() <= 1) {
                return;
            }
            objectAt = aSN1Sequence.getObjectAt(1);
            if (!(objectAt instanceof DERBitString)) {
                this.statusString = PKIFreeText.getInstance(objectAt);
                return;
            }
        }
        this.failInfo = DERBitString.getInstance(objectAt);
    }

    public PKIStatusInfo(PKIStatus pKIStatus) {
        this.status = DERInteger.getInstance(pKIStatus.toASN1Object());
    }

    public PKIStatusInfo(PKIStatus pKIStatus, PKIFreeText pKIFreeText) {
        this.status = DERInteger.getInstance(pKIStatus.toASN1Object());
        this.statusString = pKIFreeText;
    }

    public static PKIStatusInfo getInstance(Object obj) {
        if (obj instanceof PKIStatusInfo) {
            return (PKIStatusInfo) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new PKIStatusInfo((ASN1Sequence) obj);
        }
        throw new IllegalArgumentException("unknown object in factory: " + obj.getClass().getName());
    }

    public static PKIStatusInfo getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z2));
    }

    public DERBitString getFailInfo() {
        return this.failInfo;
    }

    public BigInteger getStatus() {
        return this.status.getValue();
    }

    public PKIFreeText getStatusString() {
        return this.statusString;
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public DERObject toASN1Object() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.status);
        PKIFreeText pKIFreeText = this.statusString;
        if (pKIFreeText != null) {
            aSN1EncodableVector.add(pKIFreeText);
        }
        DERBitString dERBitString = this.failInfo;
        if (dERBitString != null) {
            aSN1EncodableVector.add(dERBitString);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
