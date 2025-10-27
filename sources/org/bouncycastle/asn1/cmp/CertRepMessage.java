package org.bouncycastle.asn1.cmp;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;

/* loaded from: classes9.dex */
public class CertRepMessage extends ASN1Encodable {
    private ASN1Sequence caPubs;
    private ASN1Sequence response;

    private CertRepMessage(ASN1Sequence aSN1Sequence) {
        int i2 = 0;
        if (aSN1Sequence.size() > 1) {
            this.caPubs = ASN1Sequence.getInstance((ASN1TaggedObject) aSN1Sequence.getObjectAt(0), true);
            i2 = 1;
        }
        this.response = ASN1Sequence.getInstance(aSN1Sequence.getObjectAt(i2));
    }

    public CertRepMessage(CMPCertificate[] cMPCertificateArr, CertResponse[] certResponseArr) {
        if (certResponseArr == null) {
            throw new IllegalArgumentException("'response' cannot be null");
        }
        if (cMPCertificateArr != null) {
            ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
            for (CMPCertificate cMPCertificate : cMPCertificateArr) {
                aSN1EncodableVector.add(cMPCertificate);
            }
            this.caPubs = new DERSequence(aSN1EncodableVector);
        }
        ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
        for (CertResponse certResponse : certResponseArr) {
            aSN1EncodableVector2.add(certResponse);
        }
        this.response = new DERSequence(aSN1EncodableVector2);
    }

    public static CertRepMessage getInstance(Object obj) {
        if (obj instanceof CertRepMessage) {
            return (CertRepMessage) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new CertRepMessage((ASN1Sequence) obj);
        }
        throw new IllegalArgumentException("Invalid object: " + obj.getClass().getName());
    }

    public CMPCertificate[] getCaPubs() {
        ASN1Sequence aSN1Sequence = this.caPubs;
        if (aSN1Sequence == null) {
            return null;
        }
        int size = aSN1Sequence.size();
        CMPCertificate[] cMPCertificateArr = new CMPCertificate[size];
        for (int i2 = 0; i2 != size; i2++) {
            cMPCertificateArr[i2] = CMPCertificate.getInstance(this.caPubs.getObjectAt(i2));
        }
        return cMPCertificateArr;
    }

    public CertResponse[] getResponse() {
        int size = this.response.size();
        CertResponse[] certResponseArr = new CertResponse[size];
        for (int i2 = 0; i2 != size; i2++) {
            certResponseArr[i2] = CertResponse.getInstance(this.response.getObjectAt(i2));
        }
        return certResponseArr;
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public DERObject toASN1Object() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.caPubs != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 1, this.caPubs));
        }
        aSN1EncodableVector.add(this.response);
        return new DERSequence(aSN1EncodableVector);
    }
}
