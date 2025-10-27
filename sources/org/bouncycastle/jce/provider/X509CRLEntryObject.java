package org.bouncycastle.jce.provider;

import java.io.IOException;
import java.math.BigInteger;
import java.security.cert.CRLException;
import java.security.cert.X509CRLEntry;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEREnumerated;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.util.ASN1Dump;
import org.bouncycastle.asn1.x509.CRLReason;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.TBSCertList;
import org.bouncycastle.asn1.x509.X509Extension;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.x509.extension.X509ExtensionUtil;

/* loaded from: classes9.dex */
public class X509CRLEntryObject extends X509CRLEntry {

    /* renamed from: c, reason: collision with root package name */
    private TBSCertList.CRLEntry f27904c;
    private X500Principal certificateIssuer = loadCertificateIssuer();
    private int hashValue;
    private boolean isHashValueSet;
    private boolean isIndirect;
    private X500Principal previousCertificateIssuer;

    public X509CRLEntryObject(TBSCertList.CRLEntry cRLEntry) {
        this.f27904c = cRLEntry;
    }

    public X509CRLEntryObject(TBSCertList.CRLEntry cRLEntry, boolean z2, X500Principal x500Principal) {
        this.f27904c = cRLEntry;
        this.isIndirect = z2;
        this.previousCertificateIssuer = x500Principal;
    }

    private Set getExtensionOIDs(boolean z2) {
        X509Extensions extensions = this.f27904c.getExtensions();
        if (extensions == null) {
            return null;
        }
        HashSet hashSet = new HashSet();
        Enumeration enumerationOids = extensions.oids();
        while (enumerationOids.hasMoreElements()) {
            DERObjectIdentifier dERObjectIdentifier = (DERObjectIdentifier) enumerationOids.nextElement();
            if (z2 == extensions.getExtension(dERObjectIdentifier).isCritical()) {
                hashSet.add(dERObjectIdentifier.getId());
            }
        }
        return hashSet;
    }

    private X500Principal loadCertificateIssuer() {
        if (!this.isIndirect) {
            return null;
        }
        byte[] extensionValue = getExtensionValue(X509Extensions.CertificateIssuer.getId());
        if (extensionValue == null) {
            return this.previousCertificateIssuer;
        }
        try {
            GeneralName[] names = GeneralNames.getInstance(X509ExtensionUtil.fromExtensionValue(extensionValue)).getNames();
            for (int i2 = 0; i2 < names.length; i2++) {
                if (names[i2].getTagNo() == 4) {
                    return new X500Principal(names[i2].getName().getDERObject().getDEREncoded());
                }
            }
        } catch (IOException unused) {
        }
        return null;
    }

    @Override // java.security.cert.X509CRLEntry
    public X500Principal getCertificateIssuer() {
        return this.certificateIssuer;
    }

    @Override // java.security.cert.X509Extension
    public Set getCriticalExtensionOIDs() {
        return getExtensionOIDs(true);
    }

    @Override // java.security.cert.X509CRLEntry
    public byte[] getEncoded() throws CRLException {
        try {
            return this.f27904c.getEncoded(ASN1Encodable.DER);
        } catch (IOException e2) {
            throw new CRLException(e2.toString());
        }
    }

    @Override // java.security.cert.X509Extension
    public byte[] getExtensionValue(String str) {
        X509Extension extension;
        X509Extensions extensions = this.f27904c.getExtensions();
        if (extensions == null || (extension = extensions.getExtension(new DERObjectIdentifier(str))) == null) {
            return null;
        }
        try {
            return extension.getValue().getEncoded();
        } catch (Exception e2) {
            throw new RuntimeException("error encoding " + e2.toString());
        }
    }

    @Override // java.security.cert.X509Extension
    public Set getNonCriticalExtensionOIDs() {
        return getExtensionOIDs(false);
    }

    @Override // java.security.cert.X509CRLEntry
    public Date getRevocationDate() {
        return this.f27904c.getRevocationDate().getDate();
    }

    @Override // java.security.cert.X509CRLEntry
    public BigInteger getSerialNumber() {
        return this.f27904c.getUserCertificate().getValue();
    }

    @Override // java.security.cert.X509CRLEntry
    public boolean hasExtensions() {
        return this.f27904c.getExtensions() != null;
    }

    @Override // java.security.cert.X509Extension
    public boolean hasUnsupportedCriticalExtension() {
        Set criticalExtensionOIDs = getCriticalExtensionOIDs();
        return (criticalExtensionOIDs == null || criticalExtensionOIDs.isEmpty()) ? false : true;
    }

    @Override // java.security.cert.X509CRLEntry
    public int hashCode() {
        if (!this.isHashValueSet) {
            this.hashValue = super.hashCode();
            this.isHashValueSet = true;
        }
        return this.hashValue;
    }

    @Override // java.security.cert.X509CRLEntry
    public String toString() {
        Object generalNames;
        StringBuffer stringBuffer = new StringBuffer();
        String property = System.getProperty("line.separator");
        stringBuffer.append("      userCertificate: ");
        stringBuffer.append(getSerialNumber());
        stringBuffer.append(property);
        stringBuffer.append("       revocationDate: ");
        stringBuffer.append(getRevocationDate());
        stringBuffer.append(property);
        stringBuffer.append("       certificateIssuer: ");
        stringBuffer.append(getCertificateIssuer());
        stringBuffer.append(property);
        X509Extensions extensions = this.f27904c.getExtensions();
        if (extensions != null) {
            Enumeration enumerationOids = extensions.oids();
            if (enumerationOids.hasMoreElements()) {
                String str = "   crlEntryExtensions:";
                loop0: while (true) {
                    stringBuffer.append(str);
                    while (true) {
                        stringBuffer.append(property);
                        while (enumerationOids.hasMoreElements()) {
                            DERObjectIdentifier dERObjectIdentifier = (DERObjectIdentifier) enumerationOids.nextElement();
                            X509Extension extension = extensions.getExtension(dERObjectIdentifier);
                            if (extension.getValue() != null) {
                                ASN1InputStream aSN1InputStream = new ASN1InputStream(extension.getValue().getOctets());
                                stringBuffer.append("                       critical(");
                                stringBuffer.append(extension.isCritical());
                                stringBuffer.append(") ");
                                try {
                                    if (dERObjectIdentifier.equals(X509Extensions.ReasonCode)) {
                                        generalNames = new CRLReason(DEREnumerated.getInstance(aSN1InputStream.readObject()));
                                    } else if (dERObjectIdentifier.equals(X509Extensions.CertificateIssuer)) {
                                        stringBuffer.append("Certificate issuer: ");
                                        generalNames = new GeneralNames((ASN1Sequence) aSN1InputStream.readObject());
                                    } else {
                                        stringBuffer.append(dERObjectIdentifier.getId());
                                        stringBuffer.append(" value = ");
                                        stringBuffer.append(ASN1Dump.dumpAsString(aSN1InputStream.readObject()));
                                        stringBuffer.append(property);
                                    }
                                    stringBuffer.append(generalNames);
                                    stringBuffer.append(property);
                                } catch (Exception unused) {
                                    stringBuffer.append(dERObjectIdentifier.getId());
                                    stringBuffer.append(" value = ");
                                    str = "*****";
                                }
                            }
                        }
                    }
                }
            }
        }
        return stringBuffer.toString();
    }
}
