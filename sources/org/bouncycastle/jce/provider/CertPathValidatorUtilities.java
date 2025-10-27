package org.bouncycastle.jce.provider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.PKIXParameters;
import java.security.cert.PolicyQualifierInfo;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLSelector;
import java.security.cert.X509Certificate;
import java.security.cert.X509Extension;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.DSAPublicKeySpec;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1OutputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEREnumerated;
import org.bouncycastle.asn1.DERGeneralizedTime;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.isismtt.ISISMTTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.CRLDistPoint;
import org.bouncycastle.asn1.x509.CertificateList;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.DistributionPointName;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.jce.X509LDAPCertStoreParameters;
import org.bouncycastle.jce.exception.ExtCertPathValidatorException;
import org.bouncycastle.util.StoreException;
import org.bouncycastle.x509.ExtendedPKIXBuilderParameters;
import org.bouncycastle.x509.ExtendedPKIXParameters;
import org.bouncycastle.x509.X509AttributeCertStoreSelector;
import org.bouncycastle.x509.X509AttributeCertificate;
import org.bouncycastle.x509.X509CRLStoreSelector;
import org.bouncycastle.x509.X509CertStoreSelector;
import org.bouncycastle.x509.X509Store;

/* loaded from: classes9.dex */
public class CertPathValidatorUtilities {
    protected static final String ANY_POLICY = "2.5.29.32.0";
    protected static final int CRL_SIGN = 6;
    protected static final int KEY_CERT_SIGN = 5;
    protected static final PKIXCRLUtil CRL_UTIL = new PKIXCRLUtil();
    protected static final String CERTIFICATE_POLICIES = X509Extensions.CertificatePolicies.getId();
    protected static final String BASIC_CONSTRAINTS = X509Extensions.BasicConstraints.getId();
    protected static final String POLICY_MAPPINGS = X509Extensions.PolicyMappings.getId();
    protected static final String SUBJECT_ALTERNATIVE_NAME = X509Extensions.SubjectAlternativeName.getId();
    protected static final String NAME_CONSTRAINTS = X509Extensions.NameConstraints.getId();
    protected static final String KEY_USAGE = X509Extensions.KeyUsage.getId();
    protected static final String INHIBIT_ANY_POLICY = X509Extensions.InhibitAnyPolicy.getId();
    protected static final String ISSUING_DISTRIBUTION_POINT = X509Extensions.IssuingDistributionPoint.getId();
    protected static final String DELTA_CRL_INDICATOR = X509Extensions.DeltaCRLIndicator.getId();
    protected static final String POLICY_CONSTRAINTS = X509Extensions.PolicyConstraints.getId();
    protected static final String FRESHEST_CRL = X509Extensions.FreshestCRL.getId();
    protected static final String CRL_DISTRIBUTION_POINTS = X509Extensions.CRLDistributionPoints.getId();
    protected static final String AUTHORITY_KEY_IDENTIFIER = X509Extensions.AuthorityKeyIdentifier.getId();
    protected static final String CRL_NUMBER = X509Extensions.CRLNumber.getId();
    protected static final String[] crlReasons = {"unspecified", "keyCompromise", "cACompromise", "affiliationChanged", "superseded", "cessationOfOperation", "certificateHold", "unknown", "removeFromCRL", "privilegeWithdrawn", "aACompromise"};

    public static void addAdditionalStoreFromLocation(String str, ExtendedPKIXParameters extendedPKIXParameters) {
        String str2;
        String strSubstring;
        if (extendedPKIXParameters.isAdditionalLocationsEnabled()) {
            try {
                if (str.startsWith("ldap://")) {
                    String strSubstring2 = str.substring(7);
                    if (strSubstring2.indexOf("/") != -1) {
                        strSubstring = strSubstring2.substring(strSubstring2.indexOf("/"));
                        str2 = "ldap://" + strSubstring2.substring(0, strSubstring2.indexOf("/"));
                    } else {
                        str2 = "ldap://" + strSubstring2;
                        strSubstring = null;
                    }
                    X509LDAPCertStoreParameters x509LDAPCertStoreParametersBuild = new X509LDAPCertStoreParameters.Builder(str2, strSubstring).build();
                    extendedPKIXParameters.addAdditionalStore(X509Store.getInstance("CERTIFICATE/LDAP", x509LDAPCertStoreParametersBuild, BouncyCastleProvider.PROVIDER_NAME));
                    extendedPKIXParameters.addAdditionalStore(X509Store.getInstance("CRL/LDAP", x509LDAPCertStoreParametersBuild, BouncyCastleProvider.PROVIDER_NAME));
                    extendedPKIXParameters.addAdditionalStore(X509Store.getInstance("ATTRIBUTECERTIFICATE/LDAP", x509LDAPCertStoreParametersBuild, BouncyCastleProvider.PROVIDER_NAME));
                    extendedPKIXParameters.addAdditionalStore(X509Store.getInstance("CERTIFICATEPAIR/LDAP", x509LDAPCertStoreParametersBuild, BouncyCastleProvider.PROVIDER_NAME));
                }
            } catch (Exception unused) {
                throw new RuntimeException("Exception adding X.509 stores.");
            }
        }
    }

    public static void addAdditionalStoresFromAltNames(X509Certificate x509Certificate, ExtendedPKIXParameters extendedPKIXParameters) throws CertificateParsingException {
        if (x509Certificate.getIssuerAlternativeNames() != null) {
            for (List<?> list : x509Certificate.getIssuerAlternativeNames()) {
                if (list.get(0).equals(new Integer(6))) {
                    addAdditionalStoreFromLocation((String) list.get(1), extendedPKIXParameters);
                }
            }
        }
    }

    public static void addAdditionalStoresFromCRLDistributionPoint(CRLDistPoint cRLDistPoint, ExtendedPKIXParameters extendedPKIXParameters) throws AnnotatedException {
        if (cRLDistPoint != null) {
            try {
                for (DistributionPoint distributionPoint : cRLDistPoint.getDistributionPoints()) {
                    DistributionPointName distributionPoint2 = distributionPoint.getDistributionPoint();
                    if (distributionPoint2 != null && distributionPoint2.getType() == 0) {
                        GeneralName[] names = GeneralNames.getInstance(distributionPoint2.getName()).getNames();
                        for (int i2 = 0; i2 < names.length; i2++) {
                            if (names[i2].getTagNo() == 6) {
                                addAdditionalStoreFromLocation(DERIA5String.getInstance(names[i2].getName()).getString(), extendedPKIXParameters);
                            }
                        }
                    }
                }
            } catch (Exception e2) {
                throw new AnnotatedException("Distribution points could not be read.", e2);
            }
        }
    }

    public static Collection findCertificates(X509AttributeCertStoreSelector x509AttributeCertStoreSelector, List list) throws AnnotatedException {
        HashSet hashSet = new HashSet();
        for (Object obj : list) {
            if (obj instanceof X509Store) {
                try {
                    hashSet.addAll(((X509Store) obj).getMatches(x509AttributeCertStoreSelector));
                } catch (StoreException e2) {
                    throw new AnnotatedException("Problem while picking certificates from X.509 store.", e2);
                }
            }
        }
        return hashSet;
    }

    public static Collection findCertificates(X509CertStoreSelector x509CertStoreSelector, List list) throws AnnotatedException {
        HashSet hashSet = new HashSet();
        for (Object obj : list) {
            if (obj instanceof X509Store) {
                try {
                    hashSet.addAll(((X509Store) obj).getMatches(x509CertStoreSelector));
                } catch (StoreException e2) {
                    throw new AnnotatedException("Problem while picking certificates from X.509 store.", e2);
                }
            } else {
                try {
                    hashSet.addAll(((CertStore) obj).getCertificates(x509CertStoreSelector));
                } catch (CertStoreException e3) {
                    throw new AnnotatedException("Problem while picking certificates from certificate store.", e3);
                }
            }
        }
        return hashSet;
    }

    public static Collection findIssuerCerts(X509Certificate x509Certificate, ExtendedPKIXBuilderParameters extendedPKIXBuilderParameters) throws AnnotatedException, IOException {
        X509CertStoreSelector x509CertStoreSelector = new X509CertStoreSelector();
        HashSet hashSet = new HashSet();
        try {
            x509CertStoreSelector.setSubject(x509Certificate.getIssuerX500Principal().getEncoded());
            try {
                ArrayList arrayList = new ArrayList();
                arrayList.addAll(findCertificates(x509CertStoreSelector, extendedPKIXBuilderParameters.getCertStores()));
                arrayList.addAll(findCertificates(x509CertStoreSelector, extendedPKIXBuilderParameters.getStores()));
                arrayList.addAll(findCertificates(x509CertStoreSelector, extendedPKIXBuilderParameters.getAdditionalStores()));
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    hashSet.add((X509Certificate) it.next());
                }
                return hashSet;
            } catch (AnnotatedException e2) {
                throw new AnnotatedException("Issuer certificate cannot be searched.", e2);
            }
        } catch (IOException e3) {
            throw new AnnotatedException("Subject criteria for certificate selector to find issuer certificate could not be set.", e3);
        }
    }

    public static TrustAnchor findTrustAnchor(X509Certificate x509Certificate, Set set) throws AnnotatedException {
        return findTrustAnchor(x509Certificate, set, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x005f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.security.cert.TrustAnchor findTrustAnchor(java.security.cert.X509Certificate r8, java.util.Set r9, java.lang.String r10) throws org.bouncycastle.jce.provider.AnnotatedException, java.io.IOException {
        /*
            java.security.cert.X509CertSelector r0 = new java.security.cert.X509CertSelector
            r0.<init>()
            javax.security.auth.x500.X500Principal r1 = getEncodedIssuerPrincipal(r8)
            byte[] r2 = r1.getEncoded()     // Catch: java.io.IOException -> L77
            r0.setSubject(r2)     // Catch: java.io.IOException -> L77
            java.util.Iterator r9 = r9.iterator()
            r2 = 0
            r3 = r2
            r4 = r3
            r5 = r4
        L18:
            boolean r6 = r9.hasNext()
            if (r6 == 0) goto L69
            if (r3 != 0) goto L69
            java.lang.Object r3 = r9.next()
            java.security.cert.TrustAnchor r3 = (java.security.cert.TrustAnchor) r3
            java.security.cert.X509Certificate r6 = r3.getTrustedCert()
            if (r6 == 0) goto L3f
            java.security.cert.X509Certificate r6 = r3.getTrustedCert()
            boolean r6 = r0.match(r6)
            if (r6 == 0) goto L5f
            java.security.cert.X509Certificate r5 = r3.getTrustedCert()
            java.security.PublicKey r5 = r5.getPublicKey()
            goto L60
        L3f:
            java.lang.String r6 = r3.getCAName()
            if (r6 == 0) goto L5f
            java.security.PublicKey r6 = r3.getCAPublicKey()
            if (r6 == 0) goto L5f
            javax.security.auth.x500.X500Principal r6 = new javax.security.auth.x500.X500Principal     // Catch: java.lang.IllegalArgumentException -> L5f
            java.lang.String r7 = r3.getCAName()     // Catch: java.lang.IllegalArgumentException -> L5f
            r6.<init>(r7)     // Catch: java.lang.IllegalArgumentException -> L5f
            boolean r6 = r1.equals(r6)     // Catch: java.lang.IllegalArgumentException -> L5f
            if (r6 == 0) goto L5f
            java.security.PublicKey r5 = r3.getCAPublicKey()     // Catch: java.lang.IllegalArgumentException -> L5f
            goto L60
        L5f:
            r3 = r2
        L60:
            if (r5 == 0) goto L18
            verifyX509Certificate(r8, r5, r10)     // Catch: java.lang.Exception -> L66
            goto L18
        L66:
            r4 = move-exception
            r3 = r2
            goto L18
        L69:
            if (r3 != 0) goto L76
            if (r4 != 0) goto L6e
            goto L76
        L6e:
            org.bouncycastle.jce.provider.AnnotatedException r8 = new org.bouncycastle.jce.provider.AnnotatedException
            java.lang.String r9 = "TrustAnchor found but certificate validation failed."
            r8.<init>(r9, r4)
            throw r8
        L76:
            return r3
        L77:
            r8 = move-exception
            org.bouncycastle.jce.provider.AnnotatedException r9 = new org.bouncycastle.jce.provider.AnnotatedException
            java.lang.String r10 = "Cannot set subject search criteria for trust anchor."
            r9.<init>(r10, r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.CertPathValidatorUtilities.findTrustAnchor(java.security.cert.X509Certificate, java.util.Set, java.lang.String):java.security.cert.TrustAnchor");
    }

    public static AlgorithmIdentifier getAlgorithmIdentifier(PublicKey publicKey) throws CertPathValidatorException {
        try {
            return SubjectPublicKeyInfo.getInstance(new ASN1InputStream(publicKey.getEncoded()).readObject()).getAlgorithmId();
        } catch (Exception e2) {
            throw new ExtCertPathValidatorException("Subject public key cannot be decoded.", e2);
        }
    }

    public static void getCRLIssuersFromDistributionPoint(DistributionPoint distributionPoint, Collection collection, X509CRLSelector x509CRLSelector, ExtendedPKIXParameters extendedPKIXParameters) throws AnnotatedException, IOException {
        ArrayList arrayList = new ArrayList();
        if (distributionPoint.getCRLIssuer() != null) {
            GeneralName[] names = distributionPoint.getCRLIssuer().getNames();
            for (int i2 = 0; i2 < names.length; i2++) {
                if (names[i2].getTagNo() == 4) {
                    try {
                        arrayList.add(new X500Principal(names[i2].getName().getDERObject().getEncoded()));
                    } catch (IOException e2) {
                        throw new AnnotatedException("CRL issuer information from distribution point cannot be decoded.", e2);
                    }
                }
            }
        } else {
            if (distributionPoint.getDistributionPoint() == null) {
                throw new AnnotatedException("CRL issuer is omitted from distribution point but no distributionPoint field present.");
            }
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                arrayList.add((X500Principal) it.next());
            }
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            try {
                x509CRLSelector.addIssuerName(((X500Principal) it2.next()).getEncoded());
            } catch (IOException e3) {
                throw new AnnotatedException("Cannot decode CRL issuer information.", e3);
            }
        }
    }

    public static void getCertStatus(Date date, X509CRL x509crl, Object obj, CertStatus certStatus) throws AnnotatedException {
        DEREnumerated dEREnumerated;
        try {
            X509CRLEntryObject x509CRLEntryObject = (X509CRLEntryObject) new X509CRLObject(new CertificateList((ASN1Sequence) ASN1Object.fromByteArray(x509crl.getEncoded()))).getRevokedCertificate(getSerialNumber(obj));
            if (x509CRLEntryObject != null) {
                if (getEncodedIssuerPrincipal(obj).equals(x509CRLEntryObject.getCertificateIssuer()) || getEncodedIssuerPrincipal(obj).equals(getIssuerPrincipal(x509crl))) {
                    if (x509CRLEntryObject.hasExtensions()) {
                        try {
                            dEREnumerated = DEREnumerated.getInstance(getExtensionValue(x509CRLEntryObject, X509Extensions.ReasonCode.getId()));
                        } catch (Exception e2) {
                            new AnnotatedException("Reason code CRL entry extension could not be decoded.", e2);
                        }
                    } else {
                        dEREnumerated = null;
                    }
                    if (date.getTime() >= x509CRLEntryObject.getRevocationDate().getTime() || dEREnumerated == null || dEREnumerated.getValue().intValue() == 0 || dEREnumerated.getValue().intValue() == 1 || dEREnumerated.getValue().intValue() == 2 || dEREnumerated.getValue().intValue() == 8) {
                        certStatus.setCertStatus(dEREnumerated != null ? dEREnumerated.getValue().intValue() : 0);
                        certStatus.setRevocationDate(x509CRLEntryObject.getRevocationDate());
                    }
                }
            }
        } catch (Exception e3) {
            throw new AnnotatedException("Bouncy Castle X509CRLObject could not be created.", e3);
        }
    }

    public static Set getCompleteCRLs(DistributionPoint distributionPoint, Object obj, Date date, ExtendedPKIXParameters extendedPKIXParameters) throws AnnotatedException, IOException {
        X509CRLStoreSelector x509CRLStoreSelector = new X509CRLStoreSelector();
        try {
            HashSet hashSet = new HashSet();
            hashSet.add(obj instanceof X509AttributeCertificate ? ((X509AttributeCertificate) obj).getIssuer().getPrincipals()[0] : getEncodedIssuerPrincipal(obj));
            getCRLIssuersFromDistributionPoint(distributionPoint, hashSet, x509CRLStoreSelector, extendedPKIXParameters);
        } catch (AnnotatedException e2) {
            new AnnotatedException("Could not get issuer information from distribution point.", e2);
        }
        if (obj instanceof X509Certificate) {
            x509CRLStoreSelector.setCertificateChecking((X509Certificate) obj);
        } else if (obj instanceof X509AttributeCertificate) {
            x509CRLStoreSelector.setAttrCertificateChecking((X509AttributeCertificate) obj);
        }
        x509CRLStoreSelector.setCompleteCRLEnabled(true);
        Set setFindCRLs = CRL_UTIL.findCRLs(x509CRLStoreSelector, extendedPKIXParameters, date);
        if (!setFindCRLs.isEmpty()) {
            return setFindCRLs;
        }
        if (obj instanceof X509AttributeCertificate) {
            throw new AnnotatedException("No CRLs found for issuer \"" + ((X509AttributeCertificate) obj).getIssuer().getPrincipals()[0] + "\"");
        }
        throw new AnnotatedException("No CRLs found for issuer \"" + ((X509Certificate) obj).getIssuerX500Principal() + "\"");
    }

    public static Set getDeltaCRLs(Date date, ExtendedPKIXParameters extendedPKIXParameters, X509CRL x509crl) throws AnnotatedException, IOException {
        X509CRLStoreSelector x509CRLStoreSelector = new X509CRLStoreSelector();
        try {
            x509CRLStoreSelector.addIssuerName(getIssuerPrincipal(x509crl).getEncoded());
        } catch (IOException e2) {
            new AnnotatedException("Cannot extract issuer from CRL.", e2);
        }
        try {
            DERObject extensionValue = getExtensionValue(x509crl, CRL_NUMBER);
            BigInteger positiveValue = extensionValue != null ? DERInteger.getInstance(extensionValue).getPositiveValue() : null;
            try {
                byte[] extensionValue2 = x509crl.getExtensionValue(ISSUING_DISTRIBUTION_POINT);
                x509CRLStoreSelector.setMinCRLNumber(positiveValue != null ? positiveValue.add(BigInteger.valueOf(1L)) : null);
                x509CRLStoreSelector.setIssuingDistributionPoint(extensionValue2);
                x509CRLStoreSelector.setIssuingDistributionPointEnabled(true);
                x509CRLStoreSelector.setMaxBaseCRLNumber(positiveValue);
                Set<X509CRL> setFindCRLs = CRL_UTIL.findCRLs(x509CRLStoreSelector, extendedPKIXParameters, date);
                HashSet hashSet = new HashSet();
                for (X509CRL x509crl2 : setFindCRLs) {
                    if (isDeltaCRL(x509crl2)) {
                        hashSet.add(x509crl2);
                    }
                }
                return hashSet;
            } catch (Exception e3) {
                throw new AnnotatedException("Issuing distribution point extension value could not be read.", e3);
            }
        } catch (Exception e4) {
            throw new AnnotatedException("CRL number extension could not be extracted from CRL.", e4);
        }
    }

    public static X500Principal getEncodedIssuerPrincipal(Object obj) {
        return obj instanceof X509Certificate ? ((X509Certificate) obj).getIssuerX500Principal() : (X500Principal) ((X509AttributeCertificate) obj).getIssuer().getPrincipals()[0];
    }

    public static DERObject getExtensionValue(X509Extension x509Extension, String str) throws AnnotatedException {
        byte[] extensionValue = x509Extension.getExtensionValue(str);
        if (extensionValue == null) {
            return null;
        }
        return getObject(str, extensionValue);
    }

    public static X500Principal getIssuerPrincipal(X509CRL x509crl) {
        return x509crl.getIssuerX500Principal();
    }

    public static PublicKey getNextWorkingKey(List list, int i2) throws CertPathValidatorException {
        DSAPublicKey dSAPublicKey;
        PublicKey publicKey = ((Certificate) list.get(i2)).getPublicKey();
        if (!(publicKey instanceof DSAPublicKey)) {
            return publicKey;
        }
        DSAPublicKey dSAPublicKey2 = (DSAPublicKey) publicKey;
        if (dSAPublicKey2.getParams() != null) {
            return dSAPublicKey2;
        }
        do {
            i2++;
            if (i2 >= list.size()) {
                throw new CertPathValidatorException("DSA parameters cannot be inherited from previous certificate.");
            }
            PublicKey publicKey2 = ((X509Certificate) list.get(i2)).getPublicKey();
            if (!(publicKey2 instanceof DSAPublicKey)) {
                throw new CertPathValidatorException("DSA parameters cannot be inherited from previous certificate.");
            }
            dSAPublicKey = (DSAPublicKey) publicKey2;
        } while (dSAPublicKey.getParams() == null);
        DSAParams params = dSAPublicKey.getParams();
        try {
            return KeyFactory.getInstance("DSA", BouncyCastleProvider.PROVIDER_NAME).generatePublic(new DSAPublicKeySpec(dSAPublicKey2.getY(), params.getP(), params.getQ(), params.getG()));
        } catch (Exception e2) {
            throw new RuntimeException(e2.getMessage());
        }
    }

    private static DERObject getObject(String str, byte[] bArr) throws AnnotatedException {
        try {
            return new ASN1InputStream(((ASN1OctetString) new ASN1InputStream(bArr).readObject()).getOctets()).readObject();
        } catch (Exception e2) {
            throw new AnnotatedException("exception processing extension " + str, e2);
        }
    }

    public static final Set getQualifierSet(ASN1Sequence aSN1Sequence) throws CertPathValidatorException {
        HashSet hashSet = new HashSet();
        if (aSN1Sequence == null) {
            return hashSet;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ASN1OutputStream aSN1OutputStream = new ASN1OutputStream(byteArrayOutputStream);
        Enumeration objects = aSN1Sequence.getObjects();
        while (objects.hasMoreElements()) {
            try {
                aSN1OutputStream.writeObject(objects.nextElement());
                hashSet.add(new PolicyQualifierInfo(byteArrayOutputStream.toByteArray()));
                byteArrayOutputStream.reset();
            } catch (IOException e2) {
                throw new ExtCertPathValidatorException("Policy qualifier info cannot be decoded.", e2);
            }
        }
        return hashSet;
    }

    private static BigInteger getSerialNumber(Object obj) {
        return obj instanceof X509Certificate ? ((X509Certificate) obj).getSerialNumber() : ((X509AttributeCertificate) obj).getSerialNumber();
    }

    public static X500Principal getSubjectPrincipal(X509Certificate x509Certificate) {
        return x509Certificate.getSubjectX500Principal();
    }

    public static Date getValidCertDateFromValidityModel(ExtendedPKIXParameters extendedPKIXParameters, CertPath certPath, int i2) throws AnnotatedException {
        if (extendedPKIXParameters.getValidityModel() == 1 && i2 > 0) {
            int i3 = i2 - 1;
            if (i3 == 0) {
                try {
                    byte[] extensionValue = ((X509Certificate) certPath.getCertificates().get(i3)).getExtensionValue(ISISMTTObjectIdentifiers.id_isismtt_at_dateOfCertGen.getId());
                    DERGeneralizedTime dERGeneralizedTime = extensionValue != null ? DERGeneralizedTime.getInstance(ASN1Object.fromByteArray(extensionValue)) : null;
                    if (dERGeneralizedTime != null) {
                        try {
                            return dERGeneralizedTime.getDate();
                        } catch (ParseException e2) {
                            throw new AnnotatedException("Date from date of cert gen extension could not be parsed.", e2);
                        }
                    }
                } catch (IOException unused) {
                    throw new AnnotatedException("Date of cert gen extension could not be read.");
                } catch (IllegalArgumentException unused2) {
                    throw new AnnotatedException("Date of cert gen extension could not be read.");
                }
            }
            return ((X509Certificate) certPath.getCertificates().get(i3)).getNotBefore();
        }
        return getValidDate(extendedPKIXParameters);
    }

    public static Date getValidDate(PKIXParameters pKIXParameters) {
        Date date = pKIXParameters.getDate();
        return date == null ? new Date() : date;
    }

    public static boolean isAnyPolicy(Set set) {
        return set == null || set.contains(ANY_POLICY) || set.isEmpty();
    }

    private static boolean isDeltaCRL(X509CRL x509crl) {
        return x509crl.getCriticalExtensionOIDs().contains(RFC3280CertPathUtilities.DELTA_CRL_INDICATOR);
    }

    public static boolean isSelfIssued(X509Certificate x509Certificate) {
        return x509Certificate.getSubjectDN().equals(x509Certificate.getIssuerDN());
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x008e, code lost:
    
        r7 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0093, code lost:
    
        if (r15.getCriticalExtensionOIDs() == null) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0095, code lost:
    
        r8 = r15.getCriticalExtensionOIDs().contains(org.bouncycastle.jce.provider.CertPathValidatorUtilities.CERTIFICATE_POLICIES);
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00a1, code lost:
    
        r8 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00a2, code lost:
    
        r9 = (org.bouncycastle.jce.provider.PKIXPolicyNode) r3.getParent();
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00b1, code lost:
    
        if (org.bouncycastle.jce.provider.CertPathValidatorUtilities.ANY_POLICY.equals(r9.getValidPolicy()) == false) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00b3, code lost:
    
        r10 = new org.bouncycastle.jce.provider.PKIXPolicyNode(new java.util.ArrayList(), r11, (java.util.Set) r14.get(r13), r9, r7, r13, r8);
        r9.addChild(r10);
        r12[r11].add(r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:?, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void prepareNextCertB1(int r11, java.util.List[] r12, java.lang.String r13, java.util.Map r14, java.security.cert.X509Certificate r15) throws org.bouncycastle.jce.provider.AnnotatedException, java.security.cert.CertPathValidatorException {
        /*
            r2 = r12[r11]
            java.util.Iterator r2 = r2.iterator()
        L6:
            boolean r3 = r2.hasNext()
            r4 = 0
            if (r3 == 0) goto L27
            java.lang.Object r3 = r2.next()
            org.bouncycastle.jce.provider.PKIXPolicyNode r3 = (org.bouncycastle.jce.provider.PKIXPolicyNode) r3
            java.lang.String r5 = r3.getValidPolicy()
            boolean r5 = r5.equals(r13)
            if (r5 == 0) goto L6
            java.lang.Object r2 = r14.get(r13)
            java.util.Set r2 = (java.util.Set) r2
            r3.expectedPolicies = r2
            r2 = 1
            goto L28
        L27:
            r2 = r4
        L28:
            if (r2 != 0) goto Ldb
            r2 = r12[r11]
            java.util.Iterator r2 = r2.iterator()
        L30:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto Ldb
            java.lang.Object r3 = r2.next()
            org.bouncycastle.jce.provider.PKIXPolicyNode r3 = (org.bouncycastle.jce.provider.PKIXPolicyNode) r3
            java.lang.String r5 = r3.getValidPolicy()
            java.lang.String r6 = "2.5.29.32.0"
            boolean r5 = r6.equals(r5)
            if (r5 == 0) goto L30
            java.lang.String r2 = org.bouncycastle.jce.provider.CertPathValidatorUtilities.CERTIFICATE_POLICIES     // Catch: java.lang.Exception -> Ld2
            org.bouncycastle.asn1.DERObject r2 = getExtensionValue(r15, r2)     // Catch: java.lang.Exception -> Ld2
            org.bouncycastle.asn1.ASN1Sequence r2 = org.bouncycastle.asn1.ASN1Sequence.getInstance(r2)     // Catch: java.lang.Exception -> Ld2
            java.util.Enumeration r2 = r2.getObjects()
        L56:
            boolean r7 = r2.hasMoreElements()
            if (r7 == 0) goto L8d
            java.lang.Object r7 = r2.nextElement()     // Catch: java.lang.Exception -> L84
            org.bouncycastle.asn1.x509.PolicyInformation r7 = org.bouncycastle.asn1.x509.PolicyInformation.getInstance(r7)     // Catch: java.lang.Exception -> L84
            org.bouncycastle.asn1.DERObjectIdentifier r8 = r7.getPolicyIdentifier()
            java.lang.String r8 = r8.getId()
            boolean r8 = r6.equals(r8)
            if (r8 == 0) goto L56
            org.bouncycastle.asn1.ASN1Sequence r2 = r7.getPolicyQualifiers()     // Catch: java.security.cert.CertPathValidatorException -> L7b
            java.util.Set r2 = getQualifierSet(r2)     // Catch: java.security.cert.CertPathValidatorException -> L7b
            goto L8e
        L7b:
            r0 = move-exception
            org.bouncycastle.jce.exception.ExtCertPathValidatorException r1 = new org.bouncycastle.jce.exception.ExtCertPathValidatorException
            java.lang.String r2 = "Policy qualifier info set could not be built."
            r1.<init>(r2, r0)
            throw r1
        L84:
            r0 = move-exception
            org.bouncycastle.jce.provider.AnnotatedException r1 = new org.bouncycastle.jce.provider.AnnotatedException
            java.lang.String r2 = "Policy information cannot be decoded."
            r1.<init>(r2, r0)
            throw r1
        L8d:
            r2 = 0
        L8e:
            r7 = r2
            java.util.Set r2 = r15.getCriticalExtensionOIDs()
            if (r2 == 0) goto La1
            java.util.Set r2 = r15.getCriticalExtensionOIDs()
            java.lang.String r4 = org.bouncycastle.jce.provider.CertPathValidatorUtilities.CERTIFICATE_POLICIES
            boolean r2 = r2.contains(r4)
            r8 = r2
            goto La2
        La1:
            r8 = r4
        La2:
            java.security.cert.PolicyNode r2 = r3.getParent()
            r9 = r2
            org.bouncycastle.jce.provider.PKIXPolicyNode r9 = (org.bouncycastle.jce.provider.PKIXPolicyNode) r9
            java.lang.String r2 = r9.getValidPolicy()
            boolean r2 = r6.equals(r2)
            if (r2 == 0) goto Ldb
            org.bouncycastle.jce.provider.PKIXPolicyNode r10 = new org.bouncycastle.jce.provider.PKIXPolicyNode
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            java.lang.Object r1 = r14.get(r13)
            r4 = r1
            java.util.Set r4 = (java.util.Set) r4
            r1 = r10
            r3 = r11
            r5 = r9
            r6 = r7
            r7 = r13
            r1.<init>(r2, r3, r4, r5, r6, r7, r8)
            r9.addChild(r10)
            r0 = r12[r11]
            r0.add(r10)
            goto Ldb
        Ld2:
            r0 = move-exception
            org.bouncycastle.jce.provider.AnnotatedException r1 = new org.bouncycastle.jce.provider.AnnotatedException
            java.lang.String r2 = "Certificate policies cannot be decoded."
            r1.<init>(r2, r0)
            throw r1
        Ldb:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.CertPathValidatorUtilities.prepareNextCertB1(int, java.util.List[], java.lang.String, java.util.Map, java.security.cert.X509Certificate):void");
    }

    public static PKIXPolicyNode prepareNextCertB2(int i2, List[] listArr, String str, PKIXPolicyNode pKIXPolicyNode) {
        int i3;
        Iterator it = listArr[i2].iterator();
        while (it.hasNext()) {
            PKIXPolicyNode pKIXPolicyNode2 = (PKIXPolicyNode) it.next();
            if (pKIXPolicyNode2.getValidPolicy().equals(str)) {
                ((PKIXPolicyNode) pKIXPolicyNode2.getParent()).removeChild(pKIXPolicyNode2);
                it.remove();
                for (int i4 = i2 - 1; i4 >= 0; i4--) {
                    List list = listArr[i4];
                    while (i3 < list.size()) {
                        PKIXPolicyNode pKIXPolicyNode3 = (PKIXPolicyNode) list.get(i3);
                        i3 = (pKIXPolicyNode3.hasChildren() || (pKIXPolicyNode = removePolicyNode(pKIXPolicyNode, listArr, pKIXPolicyNode3)) != null) ? i3 + 1 : 0;
                    }
                }
            }
        }
        return pKIXPolicyNode;
    }

    public static boolean processCertD1i(int i2, List[] listArr, DERObjectIdentifier dERObjectIdentifier, Set set) {
        List list = listArr[i2 - 1];
        for (int i3 = 0; i3 < list.size(); i3++) {
            PKIXPolicyNode pKIXPolicyNode = (PKIXPolicyNode) list.get(i3);
            if (pKIXPolicyNode.getExpectedPolicies().contains(dERObjectIdentifier.getId())) {
                HashSet hashSet = new HashSet();
                hashSet.add(dERObjectIdentifier.getId());
                PKIXPolicyNode pKIXPolicyNode2 = new PKIXPolicyNode(new ArrayList(), i2, hashSet, pKIXPolicyNode, set, dERObjectIdentifier.getId(), false);
                pKIXPolicyNode.addChild(pKIXPolicyNode2);
                listArr[i2].add(pKIXPolicyNode2);
                return true;
            }
        }
        return false;
    }

    public static void processCertD1ii(int i2, List[] listArr, DERObjectIdentifier dERObjectIdentifier, Set set) {
        List list = listArr[i2 - 1];
        for (int i3 = 0; i3 < list.size(); i3++) {
            PKIXPolicyNode pKIXPolicyNode = (PKIXPolicyNode) list.get(i3);
            if (ANY_POLICY.equals(pKIXPolicyNode.getValidPolicy())) {
                HashSet hashSet = new HashSet();
                hashSet.add(dERObjectIdentifier.getId());
                PKIXPolicyNode pKIXPolicyNode2 = new PKIXPolicyNode(new ArrayList(), i2, hashSet, pKIXPolicyNode, set, dERObjectIdentifier.getId(), false);
                pKIXPolicyNode.addChild(pKIXPolicyNode2);
                listArr[i2].add(pKIXPolicyNode2);
                return;
            }
        }
    }

    public static PKIXPolicyNode removePolicyNode(PKIXPolicyNode pKIXPolicyNode, List[] listArr, PKIXPolicyNode pKIXPolicyNode2) {
        PKIXPolicyNode pKIXPolicyNode3 = (PKIXPolicyNode) pKIXPolicyNode2.getParent();
        if (pKIXPolicyNode == null) {
            return null;
        }
        if (pKIXPolicyNode3 != null) {
            pKIXPolicyNode3.removeChild(pKIXPolicyNode2);
            removePolicyNodeRecurse(listArr, pKIXPolicyNode2);
            return pKIXPolicyNode;
        }
        for (int i2 = 0; i2 < listArr.length; i2++) {
            listArr[i2] = new ArrayList();
        }
        return null;
    }

    private static void removePolicyNodeRecurse(List[] listArr, PKIXPolicyNode pKIXPolicyNode) {
        listArr[pKIXPolicyNode.getDepth()].remove(pKIXPolicyNode);
        if (pKIXPolicyNode.hasChildren()) {
            Iterator children = pKIXPolicyNode.getChildren();
            while (children.hasNext()) {
                removePolicyNodeRecurse(listArr, (PKIXPolicyNode) children.next());
            }
        }
    }

    public static void verifyX509Certificate(X509Certificate x509Certificate, PublicKey publicKey, String str) throws GeneralSecurityException {
        if (str == null) {
            x509Certificate.verify(publicKey);
        } else {
            x509Certificate.verify(publicKey, str);
        }
    }
}
