package org.eclipse.jetty.util.security;

import cn.hutool.core.text.StrPool;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Security;
import java.security.cert.CRL;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicLong;
import k.a;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class CertificateValidator {
    private static final Logger LOG = Log.getLogger((Class<?>) CertificateValidator.class);
    private static AtomicLong __aliasCount = new AtomicLong();
    private Collection<? extends CRL> _crls;
    private String _ocspResponderURL;
    private KeyStore _trustStore;
    private int _maxCertPathLength = -1;
    private boolean _enableCRLDP = false;
    private boolean _enableOCSP = false;

    public CertificateValidator(KeyStore keyStore, Collection<? extends CRL> collection) {
        if (keyStore == null) {
            throw new InvalidParameterException("TrustStore must be specified for CertificateValidator.");
        }
        this._trustStore = keyStore;
        this._crls = collection;
    }

    public Collection<? extends CRL> getCrls() {
        return this._crls;
    }

    public int getMaxCertPathLength() {
        return this._maxCertPathLength;
    }

    public String getOcspResponderURL() {
        return this._ocspResponderURL;
    }

    public KeyStore getTrustStore() {
        return this._trustStore;
    }

    public boolean isEnableCRLDP() {
        return this._enableCRLDP;
    }

    public boolean isEnableOCSP() {
        return this._enableOCSP;
    }

    public void setEnableCRLDP(boolean z2) {
        this._enableCRLDP = z2;
    }

    public void setEnableOCSP(boolean z2) {
        this._enableOCSP = z2;
    }

    public void setMaxCertPathLength(int i2) {
        this._maxCertPathLength = i2;
    }

    public void setOcspResponderURL(String str) {
        this._ocspResponderURL = str;
    }

    public void validate(KeyStore keyStore) throws CertPathBuilderException, KeyStoreException, CertPathValidatorException, CertificateException, InvalidAlgorithmParameterException {
        try {
            Enumeration<String> enumerationAliases = keyStore.aliases();
            while (enumerationAliases.hasMoreElements()) {
                validate(keyStore, enumerationAliases.nextElement());
            }
        } catch (KeyStoreException e2) {
            throw new CertificateException("Unable to retrieve aliases from keystore", e2);
        }
    }

    public String validate(KeyStore keyStore, String str) throws CertPathBuilderException, CertPathValidatorException, CertificateException, InvalidAlgorithmParameterException {
        if (str == null) {
            return null;
        }
        try {
            validate(keyStore, keyStore.getCertificate(str));
            return str;
        } catch (KeyStoreException e2) {
            LOG.debug(e2);
            throw new CertificateException("Unable to validate certificate for alias [" + str + "]: " + e2.getMessage(), e2);
        }
    }

    public void validate(KeyStore keyStore, Certificate certificate) throws CertPathBuilderException, KeyStoreException, CertPathValidatorException, CertificateException, InvalidAlgorithmParameterException {
        String str;
        if (certificate == null || !(certificate instanceof X509Certificate)) {
            return;
        }
        ((X509Certificate) certificate).checkValidity();
        try {
            if (keyStore != null) {
                String certificateAlias = keyStore.getCertificateAlias((X509Certificate) certificate);
                if (certificateAlias == null) {
                    certificateAlias = "JETTY" + String.format("%016X", Long.valueOf(__aliasCount.incrementAndGet()));
                    keyStore.setCertificateEntry(certificateAlias, certificate);
                }
                Certificate[] certificateChain = keyStore.getCertificateChain(certificateAlias);
                if (certificateChain != null && certificateChain.length != 0) {
                    validate(certificateChain);
                    return;
                }
                throw new IllegalStateException("Unable to retrieve certificate chain");
            }
            throw new InvalidParameterException("Keystore cannot be null");
        } catch (KeyStoreException e2) {
            LOG.debug(e2);
            StringBuilder sb = new StringBuilder();
            sb.append("Unable to validate certificate");
            if (0 == 0) {
                str = "";
            } else {
                str = " for alias [" + ((String) null) + StrPool.BRACKET_END;
            }
            sb.append(str);
            sb.append(": ");
            sb.append(e2.getMessage());
            throw new CertificateException(sb.toString(), e2);
        }
    }

    public void validate(Certificate[] certificateArr) throws CertPathBuilderException, CertPathValidatorException, CertificateException, InvalidAlgorithmParameterException {
        try {
            ArrayList arrayList = new ArrayList();
            for (Certificate certificate : certificateArr) {
                if (certificate != null) {
                    if (certificate instanceof X509Certificate) {
                        arrayList.add((X509Certificate) certificate);
                    } else {
                        throw new IllegalStateException("Invalid certificate type in chain");
                    }
                }
            }
            if (!arrayList.isEmpty()) {
                X509CertSelector x509CertSelector = new X509CertSelector();
                x509CertSelector.setCertificate((X509Certificate) arrayList.get(0));
                PKIXBuilderParameters pKIXBuilderParameters = new PKIXBuilderParameters(this._trustStore, x509CertSelector);
                pKIXBuilderParameters.addCertStore(CertStore.getInstance("Collection", new CollectionCertStoreParameters(arrayList)));
                pKIXBuilderParameters.setMaxPathLength(this._maxCertPathLength);
                pKIXBuilderParameters.setRevocationEnabled(true);
                Collection<? extends CRL> collection = this._crls;
                if (collection != null && !collection.isEmpty()) {
                    pKIXBuilderParameters.addCertStore(CertStore.getInstance("Collection", new CollectionCertStoreParameters(this._crls)));
                }
                if (this._enableOCSP) {
                    Security.setProperty("ocsp.enable", a.f27523u);
                }
                if (this._enableCRLDP) {
                    System.setProperty("com.sun.security.enableCRLDP", a.f27523u);
                }
                CertPathValidator.getInstance("PKIX").validate(CertPathBuilder.getInstance("PKIX").build(pKIXBuilderParameters).getCertPath(), pKIXBuilderParameters);
                return;
            }
            throw new IllegalStateException("Invalid certificate chain");
        } catch (GeneralSecurityException e2) {
            LOG.debug(e2);
            throw new CertificateException("Unable to validate certificate: " + e2.getMessage(), e2);
        }
    }
}
