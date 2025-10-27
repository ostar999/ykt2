package org.bouncycastle.jce.provider;

import cn.hutool.core.text.StrPool;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertPathBuilderSpi;
import java.security.cert.CertPathParameters;
import java.security.cert.CertificateException;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.bouncycastle.jce.exception.ExtCertPathBuilderException;
import org.bouncycastle.util.Selector;
import org.bouncycastle.x509.ExtendedPKIXBuilderParameters;
import org.bouncycastle.x509.X509CertStoreSelector;

/* loaded from: classes9.dex */
public class PKIXCertPathBuilderSpi extends CertPathBuilderSpi {
    private Exception certPathException;

    /* JADX WARN: Removed duplicated region for block: B:48:0x00c1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.security.cert.CertPathBuilderResult build(java.security.cert.X509Certificate r6, org.bouncycastle.x509.ExtendedPKIXBuilderParameters r7, java.util.List r8) throws java.security.NoSuchAlgorithmException, org.bouncycastle.jce.provider.AnnotatedException, java.security.cert.CertificateException, java.security.NoSuchProviderException {
        /*
            r5 = this;
            boolean r0 = r8.contains(r6)
            r1 = 0
            if (r0 == 0) goto L8
            return r1
        L8:
            java.util.Set r0 = r7.getExcludedCerts()
            boolean r0 = r0.contains(r6)
            if (r0 == 0) goto L13
            return r1
        L13:
            int r0 = r7.getMaxPathLength()
            r2 = -1
            if (r0 == r2) goto L27
            int r0 = r8.size()
            int r0 = r0 + (-1)
            int r2 = r7.getMaxPathLength()
            if (r0 <= r2) goto L27
            return r1
        L27:
            r8.add(r6)
            java.lang.String r0 = "X.509"
            java.lang.String r2 = org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME     // Catch: java.lang.Exception -> Lc5
            java.security.cert.CertificateFactory r0 = java.security.cert.CertificateFactory.getInstance(r0, r2)     // Catch: java.lang.Exception -> Lc5
            java.lang.String r2 = "PKIX"
            java.lang.String r3 = org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME     // Catch: java.lang.Exception -> Lc5
            java.security.cert.CertPathValidator r2 = java.security.cert.CertPathValidator.getInstance(r2, r3)     // Catch: java.lang.Exception -> Lc5
            java.util.Set r3 = r7.getTrustAnchors()     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lbc
            java.lang.String r4 = r7.getSigProvider()     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lbc
            java.security.cert.TrustAnchor r3 = org.bouncycastle.jce.provider.CertPathValidatorUtilities.findTrustAnchor(r6, r3, r4)     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lbc
            if (r3 == 0) goto L76
            java.security.cert.CertPath r0 = r0.generateCertPath(r8)     // Catch: java.lang.Exception -> L6d
            java.security.cert.CertPathValidatorResult r7 = r2.validate(r0, r7)     // Catch: java.lang.Exception -> L64
            java.security.cert.PKIXCertPathValidatorResult r7 = (java.security.cert.PKIXCertPathValidatorResult) r7     // Catch: java.lang.Exception -> L64
            java.security.cert.PKIXCertPathBuilderResult r2 = new java.security.cert.PKIXCertPathBuilderResult     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lbc
            java.security.cert.TrustAnchor r3 = r7.getTrustAnchor()     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lbc
            java.security.cert.PolicyNode r4 = r7.getPolicyTree()     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lbc
            java.security.PublicKey r7 = r7.getPublicKey()     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lbc
            r2.<init>(r0, r3, r4, r7)     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lbc
            return r2
        L64:
            r7 = move-exception
            org.bouncycastle.jce.provider.AnnotatedException r0 = new org.bouncycastle.jce.provider.AnnotatedException     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lbc
            java.lang.String r2 = "Certification path could not be validated."
            r0.<init>(r2, r7)     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lbc
            throw r0     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lbc
        L6d:
            r7 = move-exception
            org.bouncycastle.jce.provider.AnnotatedException r0 = new org.bouncycastle.jce.provider.AnnotatedException     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lbc
            java.lang.String r2 = "Certification path could not be constructed from certificate list."
            r0.<init>(r2, r7)     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lbc
            throw r0     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lbc
        L76:
            org.bouncycastle.jce.provider.CertPathValidatorUtilities.addAdditionalStoresFromAltNames(r6, r7)     // Catch: java.security.cert.CertificateParsingException -> Lb3 org.bouncycastle.jce.provider.AnnotatedException -> Lbc
            java.util.HashSet r0 = new java.util.HashSet     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lbc
            r0.<init>()     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lbc
            java.util.Collection r2 = org.bouncycastle.jce.provider.CertPathValidatorUtilities.findIssuerCerts(r6, r7)     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Laa
            r0.addAll(r2)     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Laa
            boolean r2 = r0.isEmpty()     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lbc
            if (r2 != 0) goto La2
            java.util.Iterator r0 = r0.iterator()     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lbc
        L8f:
            boolean r2 = r0.hasNext()     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lbc
            if (r2 == 0) goto Lbf
            if (r1 != 0) goto Lbf
            java.lang.Object r2 = r0.next()     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lbc
            java.security.cert.X509Certificate r2 = (java.security.cert.X509Certificate) r2     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lbc
            java.security.cert.CertPathBuilderResult r1 = r5.build(r2, r7, r8)     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lbc
            goto L8f
        La2:
            org.bouncycastle.jce.provider.AnnotatedException r7 = new org.bouncycastle.jce.provider.AnnotatedException     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lbc
            java.lang.String r0 = "No issuer certificate for certificate in certification path found."
            r7.<init>(r0)     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lbc
            throw r7     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lbc
        Laa:
            r7 = move-exception
            org.bouncycastle.jce.provider.AnnotatedException r0 = new org.bouncycastle.jce.provider.AnnotatedException     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lbc
            java.lang.String r2 = "Cannot find issuer certificate for certificate in certification path."
            r0.<init>(r2, r7)     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lbc
            throw r0     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lbc
        Lb3:
            r7 = move-exception
            org.bouncycastle.jce.provider.AnnotatedException r0 = new org.bouncycastle.jce.provider.AnnotatedException     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lbc
            java.lang.String r2 = "No additiontal X.509 stores can be added from certificate locations."
            r0.<init>(r2, r7)     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lbc
            throw r0     // Catch: org.bouncycastle.jce.provider.AnnotatedException -> Lbc
        Lbc:
            r7 = move-exception
            r5.certPathException = r7
        Lbf:
            if (r1 != 0) goto Lc4
            r8.remove(r6)
        Lc4:
            return r1
        Lc5:
            java.lang.RuntimeException r6 = new java.lang.RuntimeException
            java.lang.String r7 = "Exception creating support classes."
            r6.<init>(r7)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.PKIXCertPathBuilderSpi.build(java.security.cert.X509Certificate, org.bouncycastle.x509.ExtendedPKIXBuilderParameters, java.util.List):java.security.cert.CertPathBuilderResult");
    }

    @Override // java.security.cert.CertPathBuilderSpi
    public CertPathBuilderResult engineBuild(CertPathParameters certPathParameters) throws CertPathBuilderException, NoSuchAlgorithmException, AnnotatedException, CertificateException, NoSuchProviderException, InvalidAlgorithmParameterException {
        Exception exc;
        if (!(certPathParameters instanceof PKIXBuilderParameters) && !(certPathParameters instanceof ExtendedPKIXBuilderParameters)) {
            throw new InvalidAlgorithmParameterException("Parameters must be an instance of " + PKIXBuilderParameters.class.getName() + " or " + ExtendedPKIXBuilderParameters.class.getName() + StrPool.DOT);
        }
        if (!(certPathParameters instanceof ExtendedPKIXBuilderParameters)) {
            certPathParameters = ExtendedPKIXBuilderParameters.getInstance((PKIXBuilderParameters) certPathParameters);
        }
        ExtendedPKIXBuilderParameters extendedPKIXBuilderParameters = (ExtendedPKIXBuilderParameters) certPathParameters;
        ArrayList arrayList = new ArrayList();
        Selector targetConstraints = extendedPKIXBuilderParameters.getTargetConstraints();
        if (!(targetConstraints instanceof X509CertStoreSelector)) {
            throw new CertPathBuilderException("TargetConstraints must be an instance of " + X509CertStoreSelector.class.getName() + " for " + getClass().getName() + " class.");
        }
        try {
            Collection collectionFindCertificates = CertPathValidatorUtilities.findCertificates((X509CertStoreSelector) targetConstraints, extendedPKIXBuilderParameters.getStores());
            collectionFindCertificates.addAll(CertPathValidatorUtilities.findCertificates((X509CertStoreSelector) targetConstraints, extendedPKIXBuilderParameters.getCertStores()));
            if (collectionFindCertificates.isEmpty()) {
                throw new CertPathBuilderException("No certificate found matching targetContraints.");
            }
            Iterator it = collectionFindCertificates.iterator();
            CertPathBuilderResult certPathBuilderResultBuild = null;
            while (it.hasNext() && certPathBuilderResultBuild == null) {
                certPathBuilderResultBuild = build((X509Certificate) it.next(), extendedPKIXBuilderParameters, arrayList);
            }
            if (certPathBuilderResultBuild == null && (exc = this.certPathException) != null) {
                if (exc instanceof AnnotatedException) {
                    throw new CertPathBuilderException(this.certPathException.getMessage(), this.certPathException.getCause());
                }
                throw new CertPathBuilderException("Possible certificate chain could not be validated.", this.certPathException);
            }
            if (certPathBuilderResultBuild == null && this.certPathException == null) {
                throw new CertPathBuilderException("Unable to find certificate chain.");
            }
            return certPathBuilderResultBuild;
        } catch (AnnotatedException e2) {
            throw new ExtCertPathBuilderException("Error finding target certificate.", e2);
        }
    }
}
