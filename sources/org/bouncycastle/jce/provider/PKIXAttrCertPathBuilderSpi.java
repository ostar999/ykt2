package org.bouncycastle.jce.provider;

import cn.hutool.core.text.StrPool;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertPathBuilderSpi;
import java.security.cert.CertPathParameters;
import java.security.cert.CertificateException;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.jce.exception.ExtCertPathBuilderException;
import org.bouncycastle.util.Selector;
import org.bouncycastle.x509.ExtendedPKIXBuilderParameters;
import org.bouncycastle.x509.X509AttributeCertStoreSelector;
import org.bouncycastle.x509.X509AttributeCertificate;
import org.bouncycastle.x509.X509CertStoreSelector;

/* loaded from: classes9.dex */
public class PKIXAttrCertPathBuilderSpi extends CertPathBuilderSpi {
    private Exception certPathException;

    /* JADX WARN: Removed duplicated region for block: B:51:0x00d7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.security.cert.CertPathBuilderResult build(org.bouncycastle.x509.X509AttributeCertificate r6, java.security.cert.X509Certificate r7, org.bouncycastle.x509.ExtendedPKIXBuilderParameters r8, java.util.List r9) throws java.security.NoSuchAlgorithmException, org.bouncycastle.jce.provider.AnnotatedException, java.security.cert.CertificateException, java.security.NoSuchProviderException {
        /*
            r5 = this;
            boolean r0 = r9.contains(r7)
            r1 = 0
            if (r0 == 0) goto L8
            return r1
        L8:
            java.util.Set r0 = r8.getExcludedCerts()
            boolean r0 = r0.contains(r7)
            if (r0 == 0) goto L13
            return r1
        L13:
            int r0 = r8.getMaxPathLength()
            r2 = -1
            if (r0 == r2) goto L27
            int r0 = r9.size()
            int r0 = r0 + (-1)
            int r2 = r8.getMaxPathLength()
            if (r0 <= r2) goto L27
            return r1
        L27:
            r9.add(r7)
            java.lang.String r0 = "X.509"
            java.lang.String r2 = org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME     // Catch: java.lang.Exception -> Ldb
            java.security.cert.CertificateFactory r0 = java.security.cert.CertificateFactory.getInstance(r0, r2)     // Catch: java.lang.Exception -> Ldb
            java.lang.String r2 = "RFC3281"
            java.lang.String r3 = org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME     // Catch: java.lang.Exception -> Ldb
            java.security.cert.CertPathValidator r2 = java.security.cert.CertPathValidator.getInstance(r2, r3)     // Catch: java.lang.Exception -> Ldb
            java.util.Set r3 = r8.getTrustAnchors()     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
            java.lang.String r4 = r8.getSigProvider()     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
            java.security.cert.TrustAnchor r3 = org.bouncycastle.jce.provider.CertPathValidatorUtilities.findTrustAnchor(r7, r3, r4)     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
            if (r3 == 0) goto L76
            java.security.cert.CertPath r6 = r0.generateCertPath(r9)     // Catch: java.lang.Exception -> L6d
            java.security.cert.CertPathValidatorResult r8 = r2.validate(r6, r8)     // Catch: java.lang.Exception -> L64
            java.security.cert.PKIXCertPathValidatorResult r8 = (java.security.cert.PKIXCertPathValidatorResult) r8     // Catch: java.lang.Exception -> L64
            java.security.cert.PKIXCertPathBuilderResult r0 = new java.security.cert.PKIXCertPathBuilderResult     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
            java.security.cert.TrustAnchor r2 = r8.getTrustAnchor()     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
            java.security.cert.PolicyNode r3 = r8.getPolicyTree()     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
            java.security.PublicKey r8 = r8.getPublicKey()     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
            r0.<init>(r6, r2, r3, r8)     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
            return r0
        L64:
            r6 = move-exception
            org.bouncycastle.jce.provider.AnnotatedException r8 = new org.bouncycastle.jce.provider.AnnotatedException     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
            java.lang.String r0 = "Certification path could not be validated."
            r8.<init>(r0, r6)     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
            throw r8     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
        L6d:
            r6 = move-exception
            org.bouncycastle.jce.provider.AnnotatedException r8 = new org.bouncycastle.jce.provider.AnnotatedException     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
            java.lang.String r0 = "Certification path could not be constructed from certificate list."
            r8.<init>(r0, r6)     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
            throw r8     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
        L76:
            org.bouncycastle.jce.provider.CertPathValidatorUtilities.addAdditionalStoresFromAltNames(r7, r8)     // Catch: java.security.cert.CertificateParsingException -> Lc2 org.bouncycastle.jce.provider.AnnotatedException -> Lcb
            java.util.HashSet r0 = new java.util.HashSet     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
            r0.<init>()     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
            java.util.Collection r2 = org.bouncycastle.jce.provider.CertPathValidatorUtilities.findIssuerCerts(r7, r8)     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lb9
            r0.addAll(r2)     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lb9
            boolean r2 = r0.isEmpty()     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
            if (r2 != 0) goto Lb1
            java.util.Iterator r0 = r0.iterator()     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
        L8f:
            boolean r2 = r0.hasNext()     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
            if (r2 == 0) goto Ld5
            if (r1 != 0) goto Ld5
            java.lang.Object r2 = r0.next()     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
            java.security.cert.X509Certificate r2 = (java.security.cert.X509Certificate) r2     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
            javax.security.auth.x500.X500Principal r3 = r2.getIssuerX500Principal()     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
            javax.security.auth.x500.X500Principal r4 = r2.getSubjectX500Principal()     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
            boolean r3 = r3.equals(r4)     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
            if (r3 == 0) goto Lac
            goto L8f
        Lac:
            java.security.cert.CertPathBuilderResult r1 = r5.build(r6, r2, r8, r9)     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
            goto L8f
        Lb1:
            org.bouncycastle.jce.provider.AnnotatedException r6 = new org.bouncycastle.jce.provider.AnnotatedException     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
            java.lang.String r8 = "No issuer certificate for certificate in certification path found."
            r6.<init>(r8)     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
            throw r6     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
        Lb9:
            r6 = move-exception
            org.bouncycastle.jce.provider.AnnotatedException r8 = new org.bouncycastle.jce.provider.AnnotatedException     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
            java.lang.String r0 = "Cannot find issuer certificate for certificate in certification path."
            r8.<init>(r0, r6)     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
            throw r8     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
        Lc2:
            r6 = move-exception
            org.bouncycastle.jce.provider.AnnotatedException r8 = new org.bouncycastle.jce.provider.AnnotatedException     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
            java.lang.String r0 = "No additional X.509 stores can be added from certificate locations."
            r8.<init>(r0, r6)     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
            throw r8     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lcb
        Lcb:
            r6 = move-exception
            org.bouncycastle.jce.provider.AnnotatedException r8 = new org.bouncycastle.jce.provider.AnnotatedException
            java.lang.String r0 = "No valid certification path could be build."
            r8.<init>(r0, r6)
            r5.certPathException = r8
        Ld5:
            if (r1 != 0) goto Lda
            r9.remove(r7)
        Lda:
            return r1
        Ldb:
            java.lang.RuntimeException r6 = new java.lang.RuntimeException
            java.lang.String r7 = "Exception creating support classes."
            r6.<init>(r7)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.PKIXAttrCertPathBuilderSpi.build(org.bouncycastle.x509.X509AttributeCertificate, java.security.cert.X509Certificate, org.bouncycastle.x509.ExtendedPKIXBuilderParameters, java.util.List):java.security.cert.CertPathBuilderResult");
    }

    @Override // java.security.cert.CertPathBuilderSpi
    public CertPathBuilderResult engineBuild(CertPathParameters certPathParameters) throws CertPathBuilderException, NoSuchAlgorithmException, AnnotatedException, IOException, CertificateException, NoSuchProviderException, InvalidAlgorithmParameterException {
        if (!(certPathParameters instanceof PKIXBuilderParameters) && !(certPathParameters instanceof ExtendedPKIXBuilderParameters)) {
            throw new InvalidAlgorithmParameterException("Parameters must be an instance of " + PKIXBuilderParameters.class.getName() + " or " + ExtendedPKIXBuilderParameters.class.getName() + StrPool.DOT);
        }
        if (!(certPathParameters instanceof ExtendedPKIXBuilderParameters)) {
            certPathParameters = ExtendedPKIXBuilderParameters.getInstance((PKIXBuilderParameters) certPathParameters);
        }
        ExtendedPKIXBuilderParameters extendedPKIXBuilderParameters = (ExtendedPKIXBuilderParameters) certPathParameters;
        ArrayList arrayList = new ArrayList();
        Selector targetConstraints = extendedPKIXBuilderParameters.getTargetConstraints();
        if (!(targetConstraints instanceof X509AttributeCertStoreSelector)) {
            throw new CertPathBuilderException("TargetConstraints must be an instance of " + X509AttributeCertStoreSelector.class.getName() + " for " + getClass().getName() + " class.");
        }
        try {
            Collection collectionFindCertificates = CertPathValidatorUtilities.findCertificates((X509AttributeCertStoreSelector) targetConstraints, extendedPKIXBuilderParameters.getStores());
            if (collectionFindCertificates.isEmpty()) {
                throw new CertPathBuilderException("No attribute certificate found matching targetContraints.");
            }
            Iterator it = collectionFindCertificates.iterator();
            CertPathBuilderResult certPathBuilderResultBuild = null;
            while (it.hasNext() && certPathBuilderResultBuild == null) {
                X509AttributeCertificate x509AttributeCertificate = (X509AttributeCertificate) it.next();
                X509CertStoreSelector x509CertStoreSelector = new X509CertStoreSelector();
                Principal[] principals = x509AttributeCertificate.getIssuer().getPrincipals();
                HashSet hashSet = new HashSet();
                for (Principal principal : principals) {
                    try {
                        if (principal instanceof X500Principal) {
                            x509CertStoreSelector.setSubject(((X500Principal) principal).getEncoded());
                        }
                        hashSet.addAll(CertPathValidatorUtilities.findCertificates(x509CertStoreSelector, extendedPKIXBuilderParameters.getStores()));
                        hashSet.addAll(CertPathValidatorUtilities.findCertificates(x509CertStoreSelector, extendedPKIXBuilderParameters.getCertStores()));
                    } catch (IOException e2) {
                        throw new ExtCertPathBuilderException("cannot encode X500Principal.", e2);
                    } catch (AnnotatedException e3) {
                        throw new ExtCertPathBuilderException("Public key certificate for attribute certificate cannot be searched.", e3);
                    }
                }
                if (hashSet.isEmpty()) {
                    throw new CertPathBuilderException("Public key certificate for attribute certificate cannot be found.");
                }
                Iterator it2 = hashSet.iterator();
                while (it2.hasNext() && certPathBuilderResultBuild == null) {
                    certPathBuilderResultBuild = build(x509AttributeCertificate, (X509Certificate) it2.next(), extendedPKIXBuilderParameters, arrayList);
                }
            }
            if (certPathBuilderResultBuild == null && this.certPathException != null) {
                throw new ExtCertPathBuilderException("Possible certificate chain could not be validated.", this.certPathException);
            }
            if (certPathBuilderResultBuild == null && this.certPathException == null) {
                throw new CertPathBuilderException("Unable to find certificate chain.");
            }
            return certPathBuilderResultBuild;
        } catch (AnnotatedException e4) {
            throw new ExtCertPathBuilderException("Error finding target attribute certificate.", e4);
        }
    }
}
