package org.bouncycastle.asn1.x509;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERGeneralizedTime;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.DERUTCTime;

/* loaded from: classes9.dex */
public class TBSCertList extends ASN1Encodable {
    X509Extensions crlExtensions;
    X509Name issuer;
    Time nextUpdate;
    ASN1Sequence revokedCertificates;
    ASN1Sequence seq;
    AlgorithmIdentifier signature;
    Time thisUpdate;
    DERInteger version;

    public static class CRLEntry extends ASN1Encodable {
        X509Extensions crlEntryExtensions;
        Time revocationDate;
        ASN1Sequence seq;
        DERInteger userCertificate;

        public CRLEntry(ASN1Sequence aSN1Sequence) {
            if (aSN1Sequence.size() < 2 || aSN1Sequence.size() > 3) {
                throw new IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
            }
            this.seq = aSN1Sequence;
            this.userCertificate = DERInteger.getInstance(aSN1Sequence.getObjectAt(0));
            this.revocationDate = Time.getInstance(aSN1Sequence.getObjectAt(1));
        }

        public X509Extensions getExtensions() {
            if (this.crlEntryExtensions == null && this.seq.size() == 3) {
                this.crlEntryExtensions = X509Extensions.getInstance(this.seq.getObjectAt(2));
            }
            return this.crlEntryExtensions;
        }

        public Time getRevocationDate() {
            return this.revocationDate;
        }

        public DERInteger getUserCertificate() {
            return this.userCertificate;
        }

        @Override // org.bouncycastle.asn1.ASN1Encodable
        public DERObject toASN1Object() {
            return this.seq;
        }
    }

    public class EmptyEnumeration implements Enumeration {
        private EmptyEnumeration() {
        }

        @Override // java.util.Enumeration
        public boolean hasMoreElements() {
            return false;
        }

        @Override // java.util.Enumeration
        public Object nextElement() {
            return null;
        }
    }

    public class RevokedCertificatesEnumeration implements Enumeration {
        private final Enumeration en;

        public RevokedCertificatesEnumeration(Enumeration enumeration) {
            this.en = enumeration;
        }

        @Override // java.util.Enumeration
        public boolean hasMoreElements() {
            return this.en.hasMoreElements();
        }

        @Override // java.util.Enumeration
        public Object nextElement() {
            return new CRLEntry(ASN1Sequence.getInstance(this.en.nextElement()));
        }
    }

    public TBSCertList(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() < 3 || aSN1Sequence.size() > 7) {
            throw new IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
        }
        this.seq = aSN1Sequence;
        int i2 = 0;
        if (aSN1Sequence.getObjectAt(0) instanceof DERInteger) {
            this.version = DERInteger.getInstance(aSN1Sequence.getObjectAt(0));
            i2 = 1;
        } else {
            this.version = new DERInteger(0);
        }
        int i3 = i2 + 1;
        this.signature = AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(i2));
        int i4 = i3 + 1;
        this.issuer = X509Name.getInstance(aSN1Sequence.getObjectAt(i3));
        int i5 = i4 + 1;
        this.thisUpdate = Time.getInstance(aSN1Sequence.getObjectAt(i4));
        if (i5 < aSN1Sequence.size() && ((aSN1Sequence.getObjectAt(i5) instanceof DERUTCTime) || (aSN1Sequence.getObjectAt(i5) instanceof DERGeneralizedTime) || (aSN1Sequence.getObjectAt(i5) instanceof Time))) {
            this.nextUpdate = Time.getInstance(aSN1Sequence.getObjectAt(i5));
            i5++;
        }
        if (i5 < aSN1Sequence.size() && !(aSN1Sequence.getObjectAt(i5) instanceof DERTaggedObject)) {
            this.revokedCertificates = ASN1Sequence.getInstance(aSN1Sequence.getObjectAt(i5));
            i5++;
        }
        if (i5 >= aSN1Sequence.size() || !(aSN1Sequence.getObjectAt(i5) instanceof DERTaggedObject)) {
            return;
        }
        this.crlExtensions = X509Extensions.getInstance(aSN1Sequence.getObjectAt(i5));
    }

    public static TBSCertList getInstance(Object obj) {
        if (obj instanceof TBSCertList) {
            return (TBSCertList) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new TBSCertList((ASN1Sequence) obj);
        }
        throw new IllegalArgumentException("unknown object in factory: " + obj.getClass().getName());
    }

    public static TBSCertList getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z2));
    }

    public X509Extensions getExtensions() {
        return this.crlExtensions;
    }

    public X509Name getIssuer() {
        return this.issuer;
    }

    public Time getNextUpdate() {
        return this.nextUpdate;
    }

    public Enumeration getRevokedCertificateEnumeration() {
        ASN1Sequence aSN1Sequence = this.revokedCertificates;
        return aSN1Sequence == null ? new EmptyEnumeration() : new RevokedCertificatesEnumeration(aSN1Sequence.getObjects());
    }

    public CRLEntry[] getRevokedCertificates() {
        ASN1Sequence aSN1Sequence = this.revokedCertificates;
        if (aSN1Sequence == null) {
            return new CRLEntry[0];
        }
        int size = aSN1Sequence.size();
        CRLEntry[] cRLEntryArr = new CRLEntry[size];
        for (int i2 = 0; i2 < size; i2++) {
            cRLEntryArr[i2] = new CRLEntry(ASN1Sequence.getInstance(this.revokedCertificates.getObjectAt(i2)));
        }
        return cRLEntryArr;
    }

    public AlgorithmIdentifier getSignature() {
        return this.signature;
    }

    public Time getThisUpdate() {
        return this.thisUpdate;
    }

    public int getVersion() {
        return this.version.getValue().intValue() + 1;
    }

    public DERInteger getVersionNumber() {
        return this.version;
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public DERObject toASN1Object() {
        return this.seq;
    }
}
