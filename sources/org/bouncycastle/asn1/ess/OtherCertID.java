package org.bouncycastle.asn1.ess;

import cn.hutool.core.text.StrPool;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEREncodable;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.DigestInfo;
import org.bouncycastle.asn1.x509.IssuerSerial;
import org.bouncycastle.ocsp.CertificateID;

/* loaded from: classes9.dex */
public class OtherCertID extends ASN1Encodable {
    private IssuerSerial issuerSerial;
    private ASN1Encodable otherCertHash;

    public OtherCertID(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() < 1 || aSN1Sequence.size() > 2) {
            throw new IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
        }
        boolean z2 = aSN1Sequence.getObjectAt(0).getDERObject() instanceof ASN1OctetString;
        DEREncodable objectAt = aSN1Sequence.getObjectAt(0);
        this.otherCertHash = z2 ? ASN1OctetString.getInstance(objectAt) : DigestInfo.getInstance(objectAt);
        if (aSN1Sequence.size() > 1) {
            this.issuerSerial = new IssuerSerial(ASN1Sequence.getInstance(aSN1Sequence.getObjectAt(1)));
        }
    }

    public OtherCertID(AlgorithmIdentifier algorithmIdentifier, byte[] bArr) {
        this.otherCertHash = new DigestInfo(algorithmIdentifier, bArr);
    }

    public OtherCertID(AlgorithmIdentifier algorithmIdentifier, byte[] bArr, IssuerSerial issuerSerial) {
        this.otherCertHash = new DigestInfo(algorithmIdentifier, bArr);
        this.issuerSerial = issuerSerial;
    }

    public static OtherCertID getInstance(Object obj) {
        if (obj == null || (obj instanceof OtherCertID)) {
            return (OtherCertID) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new OtherCertID((ASN1Sequence) obj);
        }
        throw new IllegalArgumentException("unknown object in 'OtherCertID' factory : " + obj.getClass().getName() + StrPool.DOT);
    }

    public AlgorithmIdentifier getAlgorithmHash() {
        return this.otherCertHash.getDERObject() instanceof ASN1OctetString ? new AlgorithmIdentifier(CertificateID.HASH_SHA1) : DigestInfo.getInstance(this.otherCertHash).getAlgorithmId();
    }

    public byte[] getCertHash() {
        return this.otherCertHash.getDERObject() instanceof ASN1OctetString ? ((ASN1OctetString) this.otherCertHash.getDERObject()).getOctets() : DigestInfo.getInstance(this.otherCertHash).getDigest();
    }

    public IssuerSerial getIssuerSerial() {
        return this.issuerSerial;
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public DERObject toASN1Object() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.otherCertHash);
        IssuerSerial issuerSerial = this.issuerSerial;
        if (issuerSerial != null) {
            aSN1EncodableVector.add(issuerSerial);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
