package org.eclipse.jetty.security.authentication;

import cn.hutool.crypto.KeyUtil;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.Principal;
import java.security.cert.CRL;
import java.security.cert.X509Certificate;
import java.util.Collection;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.security.ServerAuthException;
import org.eclipse.jetty.security.UserAuthentication;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.util.B64Code;
import org.eclipse.jetty.util.security.CertificateUtils;
import org.eclipse.jetty.util.security.CertificateValidator;
import org.eclipse.jetty.util.security.Constraint;
import org.eclipse.jetty.util.security.Password;

/* loaded from: classes9.dex */
public class ClientCertAuthenticator extends LoginAuthenticator {
    private static final String PASSWORD_PROPERTY = "org.eclipse.jetty.ssl.password";
    private String _crlPath;
    private String _ocspResponderURL;
    private transient Password _trustStorePassword;
    private String _trustStorePath;
    private String _trustStoreProvider;
    private boolean _validateCerts;
    private String _trustStoreType = KeyUtil.KEY_TYPE_JKS;
    private int _maxCertPathLength = -1;
    private boolean _enableCRLDP = false;
    private boolean _enableOCSP = false;

    @Override // org.eclipse.jetty.security.Authenticator
    public String getAuthMethod() {
        return Constraint.__CERT_AUTH;
    }

    public String getCrlPath() {
        return this._crlPath;
    }

    public KeyStore getKeyStore(InputStream inputStream, String str, String str2, String str3, String str4) throws Exception {
        return CertificateUtils.getKeyStore(inputStream, str, str2, str3, str4);
    }

    public int getMaxCertPathLength() {
        return this._maxCertPathLength;
    }

    public String getOcspResponderURL() {
        return this._ocspResponderURL;
    }

    public String getTrustStore() {
        return this._trustStorePath;
    }

    public String getTrustStoreProvider() {
        return this._trustStoreProvider;
    }

    public String getTrustStoreType() {
        return this._trustStoreType;
    }

    public boolean isEnableCRLDP() {
        return this._enableCRLDP;
    }

    public boolean isEnableOCSP() {
        return this._enableOCSP;
    }

    public boolean isValidateCerts() {
        return this._validateCerts;
    }

    public Collection<? extends CRL> loadCRL(String str) throws Exception {
        return CertificateUtils.loadCRL(str);
    }

    @Override // org.eclipse.jetty.security.Authenticator
    public boolean secureResponse(ServletRequest servletRequest, ServletResponse servletResponse, boolean z2, Authentication.User user) throws ServerAuthException {
        return true;
    }

    public void setCrlPath(String str) {
        this._crlPath = str;
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

    public void setTrustStore(String str) {
        this._trustStorePath = str;
    }

    public void setTrustStorePassword(String str) {
        this._trustStorePassword = Password.getPassword("org.eclipse.jetty.ssl.password", str, null);
    }

    public void setTrustStoreProvider(String str) {
        this._trustStoreProvider = str;
    }

    public void setTrustStoreType(String str) {
        this._trustStoreType = str;
    }

    public void setValidateCerts(boolean z2) {
        this._validateCerts = z2;
    }

    @Override // org.eclipse.jetty.security.Authenticator
    public Authentication validateRequest(ServletRequest servletRequest, ServletResponse servletResponse, boolean z2) throws ServerAuthException {
        if (!z2) {
            return new DeferredAuthentication(this);
        }
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        X509Certificate[] x509CertificateArr = (X509Certificate[]) ((HttpServletRequest) servletRequest).getAttribute("javax.servlet.request.X509Certificate");
        if (x509CertificateArr != null) {
            try {
                if (x509CertificateArr.length > 0) {
                    if (this._validateCerts) {
                        String str = this._trustStorePath;
                        String str2 = this._trustStoreType;
                        String str3 = this._trustStoreProvider;
                        Password password = this._trustStorePassword;
                        new CertificateValidator(getKeyStore(null, str, str2, str3, password == null ? null : password.toString()), loadCRL(this._crlPath)).validate(x509CertificateArr);
                    }
                    for (X509Certificate x509Certificate : x509CertificateArr) {
                        if (x509Certificate != null) {
                            Principal subjectDN = x509Certificate.getSubjectDN();
                            if (subjectDN == null) {
                                subjectDN = x509Certificate.getIssuerDN();
                            }
                            UserIdentity userIdentityLogin = login(subjectDN == null ? "clientcert" : subjectDN.getName(), B64Code.encode(x509Certificate.getSignature()), servletRequest);
                            if (userIdentityLogin != null) {
                                return new UserAuthentication(getAuthMethod(), userIdentityLogin);
                            }
                        }
                    }
                }
            } catch (Exception e2) {
                throw new ServerAuthException(e2.getMessage());
            }
        }
        if (DeferredAuthentication.isDeferred(httpServletResponse)) {
            return Authentication.UNAUTHENTICATED;
        }
        httpServletResponse.sendError(403);
        return Authentication.SEND_FAILURE;
    }
}
