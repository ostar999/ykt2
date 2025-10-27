package org.bouncycastle.jce.provider;

import cn.hutool.crypto.KeyUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CRLException;
import java.security.cert.Certificate;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1OutputStream;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.x509.CertificateList;
import org.bouncycastle.asn1.x509.IssuingDistributionPoint;
import org.bouncycastle.asn1.x509.TBSCertList;
import org.bouncycastle.asn1.x509.X509Extension;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.x509.extension.X509ExtensionUtil;

/* loaded from: classes9.dex */
public class X509CRLObject extends X509CRL {

    /* renamed from: c, reason: collision with root package name */
    private CertificateList f27905c;
    private boolean isIndirect;
    private String sigAlgName;
    private byte[] sigAlgParams;

    public X509CRLObject(CertificateList certificateList) throws CRLException {
        this.f27905c = certificateList;
        try {
            this.sigAlgName = X509SignatureUtil.getSignatureName(certificateList.getSignatureAlgorithm());
            if (certificateList.getSignatureAlgorithm().getParameters() != null) {
                this.sigAlgParams = ((ASN1Encodable) certificateList.getSignatureAlgorithm().getParameters()).getDEREncoded();
            } else {
                this.sigAlgParams = null;
            }
            this.isIndirect = isIndirectCRL();
        } catch (Exception e2) {
            throw new CRLException("CRL contents invalid: " + e2);
        }
    }

    private Set getExtensionOIDs(boolean z2) {
        X509Extensions extensions;
        if (getVersion() != 2 || (extensions = this.f27905c.getTBSCertList().getExtensions()) == null) {
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

    private boolean isIndirectCRL() throws CRLException {
        byte[] extensionValue = getExtensionValue(X509Extensions.IssuingDistributionPoint.getId());
        if (extensionValue == null) {
            return false;
        }
        try {
            return IssuingDistributionPoint.getInstance(X509ExtensionUtil.fromExtensionValue(extensionValue)).isIndirectCRL();
        } catch (Exception e2) {
            throw new ExtCRLException("Exception reading IssuingDistributionPoint", e2);
        }
    }

    private Set loadCRLEntries() {
        HashSet hashSet = new HashSet();
        Enumeration revokedCertificateEnumeration = this.f27905c.getRevokedCertificateEnumeration();
        X500Principal issuerX500Principal = getIssuerX500Principal();
        while (revokedCertificateEnumeration.hasMoreElements()) {
            X509CRLEntryObject x509CRLEntryObject = new X509CRLEntryObject((TBSCertList.CRLEntry) revokedCertificateEnumeration.nextElement(), this.isIndirect, issuerX500Principal);
            hashSet.add(x509CRLEntryObject);
            issuerX500Principal = x509CRLEntryObject.getCertificateIssuer();
        }
        return hashSet;
    }

    @Override // java.security.cert.X509Extension
    public Set getCriticalExtensionOIDs() {
        return getExtensionOIDs(true);
    }

    @Override // java.security.cert.X509CRL
    public byte[] getEncoded() throws CRLException {
        try {
            return this.f27905c.getEncoded(ASN1Encodable.DER);
        } catch (IOException e2) {
            throw new CRLException(e2.toString());
        }
    }

    @Override // java.security.cert.X509Extension
    public byte[] getExtensionValue(String str) {
        X509Extension extension;
        X509Extensions extensions = this.f27905c.getTBSCertList().getExtensions();
        if (extensions == null || (extension = extensions.getExtension(new DERObjectIdentifier(str))) == null) {
            return null;
        }
        try {
            return extension.getValue().getEncoded();
        } catch (Exception e2) {
            throw new IllegalStateException("error parsing " + e2.toString());
        }
    }

    @Override // java.security.cert.X509CRL
    public Principal getIssuerDN() {
        return new X509Principal(this.f27905c.getIssuer());
    }

    @Override // java.security.cert.X509CRL
    public X500Principal getIssuerX500Principal() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            new ASN1OutputStream(byteArrayOutputStream).writeObject(this.f27905c.getIssuer());
            return new X500Principal(byteArrayOutputStream.toByteArray());
        } catch (IOException unused) {
            throw new IllegalStateException("can't encode issuer DN");
        }
    }

    @Override // java.security.cert.X509CRL
    public Date getNextUpdate() {
        if (this.f27905c.getNextUpdate() != null) {
            return this.f27905c.getNextUpdate().getDate();
        }
        return null;
    }

    @Override // java.security.cert.X509Extension
    public Set getNonCriticalExtensionOIDs() {
        return getExtensionOIDs(false);
    }

    @Override // java.security.cert.X509CRL
    public X509CRLEntry getRevokedCertificate(BigInteger bigInteger) {
        Enumeration revokedCertificateEnumeration = this.f27905c.getRevokedCertificateEnumeration();
        X500Principal issuerX500Principal = getIssuerX500Principal();
        while (revokedCertificateEnumeration.hasMoreElements()) {
            TBSCertList.CRLEntry cRLEntry = (TBSCertList.CRLEntry) revokedCertificateEnumeration.nextElement();
            X509CRLEntryObject x509CRLEntryObject = new X509CRLEntryObject(cRLEntry, this.isIndirect, issuerX500Principal);
            if (bigInteger.equals(cRLEntry.getUserCertificate().getValue())) {
                return x509CRLEntryObject;
            }
            issuerX500Principal = x509CRLEntryObject.getCertificateIssuer();
        }
        return null;
    }

    @Override // java.security.cert.X509CRL
    public Set getRevokedCertificates() {
        Set setLoadCRLEntries = loadCRLEntries();
        if (setLoadCRLEntries.isEmpty()) {
            return null;
        }
        return Collections.unmodifiableSet(setLoadCRLEntries);
    }

    @Override // java.security.cert.X509CRL
    public String getSigAlgName() {
        return this.sigAlgName;
    }

    @Override // java.security.cert.X509CRL
    public String getSigAlgOID() {
        return this.f27905c.getSignatureAlgorithm().getObjectId().getId();
    }

    @Override // java.security.cert.X509CRL
    public byte[] getSigAlgParams() {
        byte[] bArr = this.sigAlgParams;
        if (bArr == null) {
            return null;
        }
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        return bArr2;
    }

    @Override // java.security.cert.X509CRL
    public byte[] getSignature() {
        return this.f27905c.getSignature().getBytes();
    }

    @Override // java.security.cert.X509CRL
    public byte[] getTBSCertList() throws CRLException {
        try {
            return this.f27905c.getTBSCertList().getEncoded(ASN1Encodable.DER);
        } catch (IOException e2) {
            throw new CRLException(e2.toString());
        }
    }

    @Override // java.security.cert.X509CRL
    public Date getThisUpdate() {
        return this.f27905c.getThisUpdate().getDate();
    }

    @Override // java.security.cert.X509CRL
    public int getVersion() {
        return this.f27905c.getVersion();
    }

    @Override // java.security.cert.X509Extension
    public boolean hasUnsupportedCriticalExtension() {
        Set criticalExtensionOIDs = getCriticalExtensionOIDs();
        if (criticalExtensionOIDs == null) {
            return false;
        }
        criticalExtensionOIDs.remove(RFC3280CertPathUtilities.ISSUING_DISTRIBUTION_POINT);
        criticalExtensionOIDs.remove(RFC3280CertPathUtilities.DELTA_CRL_INDICATOR);
        return !criticalExtensionOIDs.isEmpty();
    }

    @Override // java.security.cert.CRL
    public boolean isRevoked(Certificate certificate) {
        if (!certificate.getType().equals(KeyUtil.CERT_TYPE_X509)) {
            throw new RuntimeException("X.509 CRL used with non X.509 Cert");
        }
        TBSCertList.CRLEntry[] revokedCertificates = this.f27905c.getRevokedCertificates();
        if (revokedCertificates != null) {
            BigInteger serialNumber = ((X509Certificate) certificate).getSerialNumber();
            for (TBSCertList.CRLEntry cRLEntry : revokedCertificates) {
                if (cRLEntry.getUserCertificate().getValue().equals(serialNumber)) {
                    return true;
                }
            }
        }
        return false;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:42:0x018d
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1178)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00c2  */
    @Override // java.security.cert.CRL
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String toString() {
        /*
            Method dump skipped, instructions count: 443
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.X509CRLObject.toString():java.lang.String");
    }

    @Override // java.security.cert.X509CRL
    public void verify(PublicKey publicKey) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, CRLException, NoSuchProviderException {
        verify(publicKey, BouncyCastleProvider.PROVIDER_NAME);
    }

    @Override // java.security.cert.X509CRL
    public void verify(PublicKey publicKey, String str) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, CRLException, NoSuchProviderException {
        if (!this.f27905c.getSignatureAlgorithm().equals(this.f27905c.getTBSCertList().getSignature())) {
            throw new CRLException("Signature algorithm on CertificateList does not match TBSCertList.");
        }
        Signature signature = Signature.getInstance(getSigAlgName(), str);
        signature.initVerify(publicKey);
        signature.update(getTBSCertList());
        if (!signature.verify(getSignature())) {
            throw new SignatureException("CRL does not verify with supplied public key.");
        }
    }
}
