package org.bouncycastle.jce.provider;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathParameters;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertPathValidatorSpi;
import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.jce.exception.ExtCertPathValidatorException;
import org.bouncycastle.x509.ExtendedPKIXParameters;

/* loaded from: classes9.dex */
public class PKIXCertPathValidatorSpi extends CertPathValidatorSpi {
    @Override // java.security.cert.CertPathValidatorSpi
    public CertPathValidatorResult engineValidate(CertPath certPath, CertPathParameters certPathParameters) throws CertificateNotYetValidException, IOException, CertPathValidatorException, CertificateExpiredException, InvalidAlgorithmParameterException {
        X500Principal x500Principal;
        PublicKey cAPublicKey;
        HashSet hashSet;
        List<PKIXCertPathChecker> list;
        ArrayList[] arrayListArr;
        HashSet hashSet2;
        if (!(certPathParameters instanceof PKIXParameters)) {
            throw new InvalidAlgorithmParameterException("Parameters must be a " + PKIXParameters.class.getName() + " instance.");
        }
        ExtendedPKIXParameters extendedPKIXParameters = certPathParameters instanceof ExtendedPKIXParameters ? (ExtendedPKIXParameters) certPathParameters : ExtendedPKIXParameters.getInstance((PKIXParameters) certPathParameters);
        if (extendedPKIXParameters.getTrustAnchors() == null) {
            throw new InvalidAlgorithmParameterException("trustAnchors is null, this is not allowed for certification path validation.");
        }
        List<? extends Certificate> certificates = certPath.getCertificates();
        int size = certificates.size();
        if (certificates.isEmpty()) {
            throw new CertPathValidatorException("Certification path is empty.", null, certPath, 0);
        }
        Set<String> initialPolicies = extendedPKIXParameters.getInitialPolicies();
        try {
            TrustAnchor trustAnchorFindTrustAnchor = CertPathValidatorUtilities.findTrustAnchor((X509Certificate) certificates.get(certificates.size() - 1), extendedPKIXParameters.getTrustAnchors(), extendedPKIXParameters.getSigProvider());
            if (trustAnchorFindTrustAnchor == null) {
                throw new CertPathValidatorException("Trust anchor for certification path not found.", null, certPath, -1);
            }
            int i2 = size + 1;
            ArrayList[] arrayListArr2 = new ArrayList[i2];
            for (int i3 = 0; i3 < i2; i3++) {
                arrayListArr2[i3] = new ArrayList();
            }
            HashSet hashSet3 = new HashSet();
            hashSet3.add("2.5.29.32.0");
            PKIXPolicyNode pKIXPolicyNode = new PKIXPolicyNode(new ArrayList(), 0, hashSet3, null, new HashSet(), "2.5.29.32.0", false);
            arrayListArr2[0].add(pKIXPolicyNode);
            PKIXNameConstraintValidator pKIXNameConstraintValidator = new PKIXNameConstraintValidator();
            HashSet hashSet4 = new HashSet();
            int i4 = extendedPKIXParameters.isExplicitPolicyRequired() ? 0 : i2;
            int i5 = extendedPKIXParameters.isAnyPolicyInhibited() ? 0 : i2;
            if (extendedPKIXParameters.isPolicyMappingInhibited()) {
                i2 = 0;
            }
            X509Certificate trustedCert = trustAnchorFindTrustAnchor.getTrustedCert();
            try {
                if (trustedCert != null) {
                    X500Principal subjectPrincipal = CertPathValidatorUtilities.getSubjectPrincipal(trustedCert);
                    cAPublicKey = trustedCert.getPublicKey();
                    x500Principal = subjectPrincipal;
                } else {
                    x500Principal = new X500Principal(trustAnchorFindTrustAnchor.getCAName());
                    cAPublicKey = trustAnchorFindTrustAnchor.getCAPublicKey();
                }
                try {
                    AlgorithmIdentifier algorithmIdentifier = CertPathValidatorUtilities.getAlgorithmIdentifier(cAPublicKey);
                    algorithmIdentifier.getObjectId();
                    algorithmIdentifier.getParameters();
                    if (extendedPKIXParameters.getTargetConstraints() != null && !extendedPKIXParameters.getTargetConstraints().match((X509Certificate) certificates.get(0))) {
                        throw new ExtCertPathValidatorException("Target certificate in certification path does not match targetConstraints.", null, certPath, 0);
                    }
                    List<PKIXCertPathChecker> certPathCheckers = extendedPKIXParameters.getCertPathCheckers();
                    Iterator<PKIXCertPathChecker> it = certPathCheckers.iterator();
                    while (it.hasNext()) {
                        it.next().init(false);
                        x500Principal = x500Principal;
                    }
                    X500Principal x500Principal2 = x500Principal;
                    int iPrepareNextCertM = size;
                    PublicKey publicKey = cAPublicKey;
                    X509Certificate x509Certificate = trustedCert;
                    PKIXPolicyNode pKIXPolicyNode2 = pKIXPolicyNode;
                    int i6 = i2;
                    int i7 = i5;
                    int size2 = certificates.size() - 1;
                    X509Certificate x509Certificate2 = null;
                    while (size2 >= 0) {
                        int i8 = size - size2;
                        X509Certificate x509Certificate3 = (X509Certificate) certificates.get(size2);
                        List<? extends Certificate> list2 = certificates;
                        int i9 = i7;
                        TrustAnchor trustAnchor = trustAnchorFindTrustAnchor;
                        int i10 = i4;
                        Set<String> set = initialPolicies;
                        int i11 = i6;
                        List<PKIXCertPathChecker> list3 = certPathCheckers;
                        int i12 = size2;
                        PKIXNameConstraintValidator pKIXNameConstraintValidator2 = pKIXNameConstraintValidator;
                        ArrayList[] arrayListArr3 = arrayListArr2;
                        RFC3280CertPathUtilities.processCertA(certPath, extendedPKIXParameters, size2, publicKey, size2 == certificates.size() + (-1), x500Principal2, x509Certificate);
                        RFC3280CertPathUtilities.processCertBC(certPath, i12, pKIXNameConstraintValidator2);
                        PKIXPolicyNode pKIXPolicyNodeProcessCertE = RFC3280CertPathUtilities.processCertE(certPath, i12, RFC3280CertPathUtilities.processCertD(certPath, i12, hashSet4, pKIXPolicyNode2, arrayListArr3, i9));
                        RFC3280CertPathUtilities.processCertF(certPath, i12, pKIXPolicyNodeProcessCertE, i10);
                        if (i8 == size) {
                            list = list3;
                            arrayListArr = arrayListArr3;
                            pKIXPolicyNode2 = pKIXPolicyNodeProcessCertE;
                            i7 = i9;
                            i6 = i11;
                            i4 = i10;
                        } else {
                            if (x509Certificate3 != null && x509Certificate3.getVersion() == 1) {
                                throw new CertPathValidatorException("Version 1 certificates can't be used as CA ones.", null, certPath, i12);
                            }
                            RFC3280CertPathUtilities.prepareNextCertA(certPath, i12);
                            arrayListArr = arrayListArr3;
                            PKIXPolicyNode pKIXPolicyNodePrepareCertB = RFC3280CertPathUtilities.prepareCertB(certPath, i12, arrayListArr, pKIXPolicyNodeProcessCertE, i11);
                            RFC3280CertPathUtilities.prepareNextCertG(certPath, i12, pKIXNameConstraintValidator2);
                            int iPrepareNextCertH1 = RFC3280CertPathUtilities.prepareNextCertH1(certPath, i12, i10);
                            int iPrepareNextCertH2 = RFC3280CertPathUtilities.prepareNextCertH2(certPath, i12, i11);
                            int iPrepareNextCertH3 = RFC3280CertPathUtilities.prepareNextCertH3(certPath, i12, i9);
                            int iPrepareNextCertI1 = RFC3280CertPathUtilities.prepareNextCertI1(certPath, i12, iPrepareNextCertH1);
                            int iPrepareNextCertI2 = RFC3280CertPathUtilities.prepareNextCertI2(certPath, i12, iPrepareNextCertH2);
                            int iPrepareNextCertJ = RFC3280CertPathUtilities.prepareNextCertJ(certPath, i12, iPrepareNextCertH3);
                            RFC3280CertPathUtilities.prepareNextCertK(certPath, i12);
                            iPrepareNextCertM = RFC3280CertPathUtilities.prepareNextCertM(certPath, i12, RFC3280CertPathUtilities.prepareNextCertL(certPath, i12, iPrepareNextCertM));
                            RFC3280CertPathUtilities.prepareNextCertN(certPath, i12);
                            Set<String> criticalExtensionOIDs = x509Certificate3.getCriticalExtensionOIDs();
                            if (criticalExtensionOIDs != null) {
                                hashSet2 = new HashSet(criticalExtensionOIDs);
                                hashSet2.remove(RFC3280CertPathUtilities.KEY_USAGE);
                                hashSet2.remove(RFC3280CertPathUtilities.CERTIFICATE_POLICIES);
                                hashSet2.remove(RFC3280CertPathUtilities.POLICY_MAPPINGS);
                                hashSet2.remove(RFC3280CertPathUtilities.INHIBIT_ANY_POLICY);
                                hashSet2.remove(RFC3280CertPathUtilities.ISSUING_DISTRIBUTION_POINT);
                                hashSet2.remove(RFC3280CertPathUtilities.DELTA_CRL_INDICATOR);
                                hashSet2.remove(RFC3280CertPathUtilities.POLICY_CONSTRAINTS);
                                hashSet2.remove(RFC3280CertPathUtilities.BASIC_CONSTRAINTS);
                                hashSet2.remove(RFC3280CertPathUtilities.SUBJECT_ALTERNATIVE_NAME);
                                hashSet2.remove(RFC3280CertPathUtilities.NAME_CONSTRAINTS);
                            } else {
                                hashSet2 = new HashSet();
                            }
                            list = list3;
                            RFC3280CertPathUtilities.prepareNextCertO(certPath, i12, hashSet2, list);
                            X500Principal subjectPrincipal2 = CertPathValidatorUtilities.getSubjectPrincipal(x509Certificate3);
                            try {
                                PublicKey nextWorkingKey = CertPathValidatorUtilities.getNextWorkingKey(certPath.getCertificates(), i12);
                                AlgorithmIdentifier algorithmIdentifier2 = CertPathValidatorUtilities.getAlgorithmIdentifier(nextWorkingKey);
                                algorithmIdentifier2.getObjectId();
                                algorithmIdentifier2.getParameters();
                                pKIXPolicyNode2 = pKIXPolicyNodePrepareCertB;
                                x500Principal2 = subjectPrincipal2;
                                publicKey = nextWorkingKey;
                                x509Certificate = x509Certificate3;
                                i4 = iPrepareNextCertI1;
                                i7 = iPrepareNextCertJ;
                                i6 = iPrepareNextCertI2;
                            } catch (CertPathValidatorException e2) {
                                throw new CertPathValidatorException("Next working key could not be retrieved.", e2, certPath, i12);
                            }
                        }
                        int i13 = i12 - 1;
                        arrayListArr2 = arrayListArr;
                        certPathCheckers = list;
                        pKIXNameConstraintValidator = pKIXNameConstraintValidator2;
                        certificates = list2;
                        trustAnchorFindTrustAnchor = trustAnchor;
                        initialPolicies = set;
                        size2 = i13;
                        x509Certificate2 = x509Certificate3;
                    }
                    List<PKIXCertPathChecker> list4 = certPathCheckers;
                    Set<String> set2 = initialPolicies;
                    TrustAnchor trustAnchor2 = trustAnchorFindTrustAnchor;
                    int i14 = size2;
                    ArrayList[] arrayListArr4 = arrayListArr2;
                    int i15 = i14 + 1;
                    int iWrapupCertB = RFC3280CertPathUtilities.wrapupCertB(certPath, i15, RFC3280CertPathUtilities.wrapupCertA(i4, x509Certificate2));
                    Set<String> criticalExtensionOIDs2 = x509Certificate2.getCriticalExtensionOIDs();
                    if (criticalExtensionOIDs2 != null) {
                        hashSet = new HashSet(criticalExtensionOIDs2);
                        hashSet.remove(RFC3280CertPathUtilities.KEY_USAGE);
                        hashSet.remove(RFC3280CertPathUtilities.CERTIFICATE_POLICIES);
                        hashSet.remove(RFC3280CertPathUtilities.POLICY_MAPPINGS);
                        hashSet.remove(RFC3280CertPathUtilities.INHIBIT_ANY_POLICY);
                        hashSet.remove(RFC3280CertPathUtilities.ISSUING_DISTRIBUTION_POINT);
                        hashSet.remove(RFC3280CertPathUtilities.DELTA_CRL_INDICATOR);
                        hashSet.remove(RFC3280CertPathUtilities.POLICY_CONSTRAINTS);
                        hashSet.remove(RFC3280CertPathUtilities.BASIC_CONSTRAINTS);
                        hashSet.remove(RFC3280CertPathUtilities.SUBJECT_ALTERNATIVE_NAME);
                        hashSet.remove(RFC3280CertPathUtilities.NAME_CONSTRAINTS);
                        hashSet.remove(RFC3280CertPathUtilities.CRL_DISTRIBUTION_POINTS);
                    } else {
                        hashSet = new HashSet();
                    }
                    RFC3280CertPathUtilities.wrapupCertF(certPath, i15, list4, hashSet);
                    X509Certificate x509Certificate4 = x509Certificate2;
                    PKIXPolicyNode pKIXPolicyNodeWrapupCertG = RFC3280CertPathUtilities.wrapupCertG(certPath, extendedPKIXParameters, set2, i15, arrayListArr4, pKIXPolicyNode2, hashSet4);
                    if (iWrapupCertB > 0 || pKIXPolicyNodeWrapupCertG != null) {
                        return new PKIXCertPathValidatorResult(trustAnchor2, pKIXPolicyNodeWrapupCertG, x509Certificate4.getPublicKey());
                    }
                    throw new CertPathValidatorException("Path processing failed on policy.", null, certPath, i14);
                } catch (CertPathValidatorException e3) {
                    throw new ExtCertPathValidatorException("Algorithm identifier of public key of trust anchor could not be read.", e3, certPath, -1);
                }
            } catch (IllegalArgumentException e4) {
                throw new ExtCertPathValidatorException("Subject of trust anchor could not be (re)encoded.", e4, certPath, -1);
            }
        } catch (AnnotatedException e5) {
            throw new CertPathValidatorException(e5.getMessage(), e5, certPath, certificates.size() - 1);
        }
    }
}
