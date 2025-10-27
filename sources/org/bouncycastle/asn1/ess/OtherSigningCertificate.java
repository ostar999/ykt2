package org.bouncycastle.asn1.ess;

import cn.hutool.core.text.StrPool;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.PolicyInformation;

/* loaded from: classes9.dex */
public class OtherSigningCertificate extends ASN1Encodable {
    ASN1Sequence certs;
    ASN1Sequence policies;

    public OtherSigningCertificate(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() < 1 || aSN1Sequence.size() > 2) {
            throw new IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
        }
        this.certs = ASN1Sequence.getInstance(aSN1Sequence.getObjectAt(0));
        if (aSN1Sequence.size() > 1) {
            this.policies = ASN1Sequence.getInstance(aSN1Sequence.getObjectAt(1));
        }
    }

    public OtherSigningCertificate(OtherCertID otherCertID) {
        this.certs = new DERSequence(otherCertID);
    }

    public static OtherSigningCertificate getInstance(Object obj) {
        if (obj == null || (obj instanceof OtherSigningCertificate)) {
            return (OtherSigningCertificate) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new OtherSigningCertificate((ASN1Sequence) obj);
        }
        throw new IllegalArgumentException("unknown object in 'OtherSigningCertificate' factory : " + obj.getClass().getName() + StrPool.DOT);
    }

    public OtherCertID[] getCerts() {
        OtherCertID[] otherCertIDArr = new OtherCertID[this.certs.size()];
        for (int i2 = 0; i2 != this.certs.size(); i2++) {
            otherCertIDArr[i2] = OtherCertID.getInstance(this.certs.getObjectAt(i2));
        }
        return otherCertIDArr;
    }

    public PolicyInformation[] getPolicies() {
        ASN1Sequence aSN1Sequence = this.policies;
        if (aSN1Sequence == null) {
            return null;
        }
        PolicyInformation[] policyInformationArr = new PolicyInformation[aSN1Sequence.size()];
        for (int i2 = 0; i2 != this.policies.size(); i2++) {
            policyInformationArr[i2] = PolicyInformation.getInstance(this.policies.getObjectAt(i2));
        }
        return policyInformationArr;
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public DERObject toASN1Object() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.certs);
        ASN1Sequence aSN1Sequence = this.policies;
        if (aSN1Sequence != null) {
            aSN1EncodableVector.add(aSN1Sequence);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
