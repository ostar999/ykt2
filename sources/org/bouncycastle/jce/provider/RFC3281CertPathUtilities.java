package org.bouncycastle.jce.provider;

import cn.hutool.core.text.StrPool;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.TargetInformation;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.jce.exception.ExtCertPathValidatorException;
import org.bouncycastle.x509.ExtendedPKIXBuilderParameters;
import org.bouncycastle.x509.ExtendedPKIXParameters;
import org.bouncycastle.x509.PKIXAttrCertChecker;
import org.bouncycastle.x509.X509AttributeCertificate;
import org.bouncycastle.x509.X509CertStoreSelector;

/* loaded from: classes9.dex */
class RFC3281CertPathUtilities {
    private static final String TARGET_INFORMATION = X509Extensions.TargetInformation.getId();
    private static final String NO_REV_AVAIL = X509Extensions.NoRevAvail.getId();
    private static final String CRL_DISTRIBUTION_POINTS = X509Extensions.CRLDistributionPoints.getId();
    private static final String AUTHORITY_INFO_ACCESS = X509Extensions.AuthorityInfoAccess.getId();

    public static void additionalChecks(X509AttributeCertificate x509AttributeCertificate, ExtendedPKIXParameters extendedPKIXParameters) throws CertPathValidatorException {
        for (String str : extendedPKIXParameters.getProhibitedACAttributes()) {
            if (x509AttributeCertificate.getAttributes(str) != null) {
                throw new CertPathValidatorException("Attribute certificate contains prohibited attribute: " + str + StrPool.DOT);
            }
        }
        for (String str2 : extendedPKIXParameters.getNecessaryACAttributes()) {
            if (x509AttributeCertificate.getAttributes(str2) == null) {
                throw new CertPathValidatorException("Attribute certificate does not contain necessary attribute: " + str2 + StrPool.DOT);
            }
        }
    }

    private static void checkCRL(DistributionPoint distributionPoint, X509AttributeCertificate x509AttributeCertificate, ExtendedPKIXParameters extendedPKIXParameters, Date date, X509Certificate x509Certificate, CertStatus certStatus, ReasonsMask reasonsMask, List list) throws AnnotatedException {
        Iterator it;
        X509CRL x509crl;
        ReasonsMask reasonsMaskProcessCRLD;
        if (x509AttributeCertificate.getExtensionValue(X509Extensions.NoRevAvail.getId()) != null) {
            return;
        }
        Date date2 = new Date(System.currentTimeMillis());
        if (date.getTime() > date2.getTime()) {
            throw new AnnotatedException("Validation time is in future.");
        }
        Iterator it2 = CertPathValidatorUtilities.getCompleteCRLs(distributionPoint, x509AttributeCertificate, date2, extendedPKIXParameters).iterator();
        boolean z2 = false;
        AnnotatedException e2 = null;
        while (it2.hasNext() && certStatus.getCertStatus() == 11 && !reasonsMask.isAllReasons()) {
            try {
                x509crl = (X509CRL) it2.next();
                reasonsMaskProcessCRLD = RFC3280CertPathUtilities.processCRLD(x509crl, distributionPoint);
            } catch (AnnotatedException e3) {
                e2 = e3;
                it = it2;
            }
            if (reasonsMaskProcessCRLD.hasNewReasons(reasonsMask)) {
                it = it2;
                try {
                    X509CRL x509crlProcessCRLH = extendedPKIXParameters.isUseDeltasEnabled() ? RFC3280CertPathUtilities.processCRLH(CertPathValidatorUtilities.getDeltaCRLs(date2, extendedPKIXParameters, x509crl), RFC3280CertPathUtilities.processCRLG(x509crl, RFC3280CertPathUtilities.processCRLF(x509crl, x509AttributeCertificate, null, null, extendedPKIXParameters, list))) : null;
                    if (extendedPKIXParameters.getValidityModel() != 1 && x509AttributeCertificate.getNotAfter().getTime() < x509crl.getThisUpdate().getTime()) {
                        throw new AnnotatedException("No valid CRL for current time found.");
                    }
                    RFC3280CertPathUtilities.processCRLB1(distributionPoint, x509AttributeCertificate, x509crl);
                    RFC3280CertPathUtilities.processCRLB2(distributionPoint, x509AttributeCertificate, x509crl);
                    RFC3280CertPathUtilities.processCRLC(x509crlProcessCRLH, x509crl, extendedPKIXParameters);
                    RFC3280CertPathUtilities.processCRLI(date, x509crlProcessCRLH, x509AttributeCertificate, certStatus, extendedPKIXParameters);
                    RFC3280CertPathUtilities.processCRLJ(date, x509crl, x509AttributeCertificate, certStatus);
                    if (certStatus.getCertStatus() == 8) {
                        certStatus.setCertStatus(11);
                    }
                    reasonsMask.addReasons(reasonsMaskProcessCRLD);
                    z2 = true;
                } catch (AnnotatedException e4) {
                    e2 = e4;
                }
                it2 = it;
            } else {
                continue;
            }
        }
        if (!z2) {
            throw e2;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x00e7 A[PHI: r0
      0x00e7: PHI (r0v17 org.bouncycastle.jce.provider.AnnotatedException) = 
      (r0v16 org.bouncycastle.jce.provider.AnnotatedException)
      (r0v16 org.bouncycastle.jce.provider.AnnotatedException)
      (r0v31 org.bouncycastle.jce.provider.AnnotatedException)
     binds: [B:35:0x0089, B:37:0x008f, B:44:0x00e1] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0148  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void checkCRLs(org.bouncycastle.x509.X509AttributeCertificate r18, org.bouncycastle.x509.ExtendedPKIXParameters r19, java.security.cert.X509Certificate r20, java.util.Date r21, java.util.List r22) throws org.bouncycastle.jce.provider.AnnotatedException, java.security.cert.CertPathValidatorException {
        /*
            Method dump skipped, instructions count: 381
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.RFC3281CertPathUtilities.checkCRLs(org.bouncycastle.x509.X509AttributeCertificate, org.bouncycastle.x509.ExtendedPKIXParameters, java.security.cert.X509Certificate, java.util.Date, java.util.List):void");
    }

    public static CertPath processAttrCert1(X509AttributeCertificate x509AttributeCertificate, ExtendedPKIXParameters extendedPKIXParameters) throws CertPathBuilderException, NoSuchAlgorithmException, IOException, CertPathValidatorException, NoSuchProviderException, InvalidAlgorithmParameterException {
        HashSet hashSet = new HashSet();
        if (x509AttributeCertificate.getHolder().getIssuer() != null) {
            X509CertStoreSelector x509CertStoreSelector = new X509CertStoreSelector();
            x509CertStoreSelector.setSerialNumber(x509AttributeCertificate.getHolder().getSerialNumber());
            for (Principal principal : x509AttributeCertificate.getHolder().getIssuer()) {
                try {
                    if (principal instanceof X500Principal) {
                        x509CertStoreSelector.setIssuer(((X500Principal) principal).getEncoded());
                    }
                    hashSet.addAll(CertPathValidatorUtilities.findCertificates(x509CertStoreSelector, extendedPKIXParameters.getStores()));
                } catch (IOException e2) {
                    throw new ExtCertPathValidatorException("Unable to encode X500 principal.", e2);
                } catch (AnnotatedException e3) {
                    throw new ExtCertPathValidatorException("Public key certificate for attribute certificate cannot be searched.", e3);
                }
            }
            if (hashSet.isEmpty()) {
                throw new CertPathValidatorException("Public key certificate specified in base certificate ID for attribute certificate cannot be found.");
            }
        }
        if (x509AttributeCertificate.getHolder().getEntityNames() != null) {
            X509CertStoreSelector x509CertStoreSelector2 = new X509CertStoreSelector();
            for (Principal principal2 : x509AttributeCertificate.getHolder().getEntityNames()) {
                try {
                    if (principal2 instanceof X500Principal) {
                        x509CertStoreSelector2.setIssuer(((X500Principal) principal2).getEncoded());
                    }
                    hashSet.addAll(CertPathValidatorUtilities.findCertificates(x509CertStoreSelector2, extendedPKIXParameters.getStores()));
                } catch (IOException e4) {
                    throw new ExtCertPathValidatorException("Unable to encode X500 principal.", e4);
                } catch (AnnotatedException e5) {
                    throw new ExtCertPathValidatorException("Public key certificate for attribute certificate cannot be searched.", e5);
                }
            }
            if (hashSet.isEmpty()) {
                throw new CertPathValidatorException("Public key certificate specified in entity name for attribute certificate cannot be found.");
            }
        }
        ExtendedPKIXBuilderParameters extendedPKIXBuilderParameters = (ExtendedPKIXBuilderParameters) ExtendedPKIXBuilderParameters.getInstance(extendedPKIXParameters);
        Iterator it = hashSet.iterator();
        ExtCertPathValidatorException extCertPathValidatorException = null;
        CertPathBuilderResult certPathBuilderResultBuild = null;
        while (it.hasNext()) {
            X509CertStoreSelector x509CertStoreSelector3 = new X509CertStoreSelector();
            x509CertStoreSelector3.setCertificate((X509Certificate) it.next());
            extendedPKIXBuilderParameters.setTargetConstraints(x509CertStoreSelector3);
            try {
                try {
                    certPathBuilderResultBuild = CertPathBuilder.getInstance("PKIX", BouncyCastleProvider.PROVIDER_NAME).build(ExtendedPKIXBuilderParameters.getInstance(extendedPKIXBuilderParameters));
                } catch (InvalidAlgorithmParameterException e6) {
                    throw new RuntimeException(e6.getMessage());
                } catch (CertPathBuilderException e7) {
                    extCertPathValidatorException = new ExtCertPathValidatorException("Certification path for public key certificate of attribute certificate could not be build.", e7);
                }
            } catch (NoSuchAlgorithmException e8) {
                throw new ExtCertPathValidatorException("Support class could not be created.", e8);
            } catch (NoSuchProviderException e9) {
                throw new ExtCertPathValidatorException("Support class could not be created.", e9);
            }
        }
        if (extCertPathValidatorException == null) {
            return certPathBuilderResultBuild.getCertPath();
        }
        throw extCertPathValidatorException;
    }

    public static CertPathValidatorResult processAttrCert2(CertPath certPath, ExtendedPKIXParameters extendedPKIXParameters) throws CertPathValidatorException {
        try {
            try {
                return CertPathValidator.getInstance("PKIX", BouncyCastleProvider.PROVIDER_NAME).validate(certPath, extendedPKIXParameters);
            } catch (InvalidAlgorithmParameterException e2) {
                throw new RuntimeException(e2.getMessage());
            } catch (CertPathValidatorException e3) {
                throw new ExtCertPathValidatorException("Certification path for issuer certificate of attribute certificate could not be validated.", e3);
            }
        } catch (NoSuchAlgorithmException e4) {
            throw new ExtCertPathValidatorException("Support class could not be created.", e4);
        } catch (NoSuchProviderException e5) {
            throw new ExtCertPathValidatorException("Support class could not be created.", e5);
        }
    }

    public static void processAttrCert3(X509Certificate x509Certificate, ExtendedPKIXParameters extendedPKIXParameters) throws CertPathValidatorException {
        if (x509Certificate.getKeyUsage() != null && !x509Certificate.getKeyUsage()[0] && !x509Certificate.getKeyUsage()[1]) {
            throw new CertPathValidatorException("Attribute certificate issuer public key cannot be used to validate digital signatures.");
        }
        if (x509Certificate.getBasicConstraints() != -1) {
            throw new CertPathValidatorException("Attribute certificate issuer is also a public key certificate issuer.");
        }
    }

    public static void processAttrCert4(X509Certificate x509Certificate, ExtendedPKIXParameters extendedPKIXParameters) throws CertPathValidatorException {
        boolean z2 = false;
        for (TrustAnchor trustAnchor : extendedPKIXParameters.getTrustedACIssuers()) {
            if (x509Certificate.getSubjectX500Principal().getName("RFC2253").equals(trustAnchor.getCAName()) || x509Certificate.equals(trustAnchor.getTrustedCert())) {
                z2 = true;
            }
        }
        if (!z2) {
            throw new CertPathValidatorException("Attribute certificate issuer is not directly trusted.");
        }
    }

    public static void processAttrCert5(X509AttributeCertificate x509AttributeCertificate, ExtendedPKIXParameters extendedPKIXParameters) throws CertPathValidatorException {
        try {
            x509AttributeCertificate.checkValidity(CertPathValidatorUtilities.getValidDate(extendedPKIXParameters));
        } catch (CertificateExpiredException e2) {
            throw new ExtCertPathValidatorException("Attribute certificate is not valid.", e2);
        } catch (CertificateNotYetValidException e3) {
            throw new ExtCertPathValidatorException("Attribute certificate is not valid.", e3);
        }
    }

    public static void processAttrCert7(X509AttributeCertificate x509AttributeCertificate, CertPath certPath, CertPath certPath2, ExtendedPKIXParameters extendedPKIXParameters) throws CertPathValidatorException {
        Set<String> criticalExtensionOIDs = x509AttributeCertificate.getCriticalExtensionOIDs();
        String str = TARGET_INFORMATION;
        if (criticalExtensionOIDs.contains(str)) {
            try {
                TargetInformation.getInstance(CertPathValidatorUtilities.getExtensionValue(x509AttributeCertificate, str));
            } catch (IllegalArgumentException e2) {
                throw new ExtCertPathValidatorException("Target information extension could not be read.", e2);
            } catch (AnnotatedException e3) {
                throw new ExtCertPathValidatorException("Target information extension could not be read.", e3);
            }
        }
        criticalExtensionOIDs.remove(str);
        Iterator it = extendedPKIXParameters.getAttrCertCheckers().iterator();
        while (it.hasNext()) {
            ((PKIXAttrCertChecker) it.next()).check(x509AttributeCertificate, certPath, certPath2, criticalExtensionOIDs);
        }
        if (criticalExtensionOIDs.isEmpty()) {
            return;
        }
        throw new CertPathValidatorException("Attribute certificate contains unsupported critical extensions: " + criticalExtensionOIDs);
    }
}
