package org.bouncycastle.asn1.crmf;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERSequence;

/* loaded from: classes9.dex */
public class CertRequest extends ASN1Encodable {
    private DERInteger certReqId;
    private CertTemplate certTemplate;
    private Controls controls;

    public CertRequest(int i2, CertTemplate certTemplate, Controls controls) {
        this(new DERInteger(i2), certTemplate, controls);
    }

    private CertRequest(ASN1Sequence aSN1Sequence) {
        this.certReqId = DERInteger.getInstance(aSN1Sequence.getObjectAt(0));
        this.certTemplate = CertTemplate.getInstance(aSN1Sequence.getObjectAt(1));
        if (aSN1Sequence.size() > 2) {
            this.controls = Controls.getInstance(aSN1Sequence.getObjectAt(2));
        }
    }

    public CertRequest(DERInteger dERInteger, CertTemplate certTemplate, Controls controls) {
        this.certReqId = dERInteger;
        this.certTemplate = certTemplate;
        this.controls = controls;
    }

    public static CertRequest getInstance(Object obj) {
        if (obj instanceof CertRequest) {
            return (CertRequest) obj;
        }
        if (obj != null) {
            return new CertRequest(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public DERInteger getCertReqId() {
        return this.certReqId;
    }

    public CertTemplate getCertTemplate() {
        return this.certTemplate;
    }

    public Controls getControls() {
        return this.controls;
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public DERObject toASN1Object() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.certReqId);
        aSN1EncodableVector.add(this.certTemplate);
        Controls controls = this.controls;
        if (controls != null) {
            aSN1EncodableVector.add(controls);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
