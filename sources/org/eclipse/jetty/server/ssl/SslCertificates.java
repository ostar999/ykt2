package org.eclipse.jetty.server.ssl;

import cn.hutool.crypto.KeyUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class SslCertificates {
    private static final Logger LOG = Log.getLogger((Class<?>) SslCertificates.class);
    static final String CACHED_INFO_ATTR = CachedInfo.class.getName();

    public static class CachedInfo {
        private final X509Certificate[] _certs;
        private final String _idStr;
        private final Integer _keySize;

        public CachedInfo(Integer num, X509Certificate[] x509CertificateArr, String str) {
            this._keySize = num;
            this._certs = x509CertificateArr;
            this._idStr = str;
        }

        public X509Certificate[] getCerts() {
            return this._certs;
        }

        public String getIdStr() {
            return this._idStr;
        }

        public Integer getKeySize() {
            return this._keySize;
        }
    }

    public static void customize(SSLSession sSLSession, EndPoint endPoint, Request request) throws IOException {
        Integer keySize;
        X509Certificate[] certs;
        String idStr;
        request.setScheme("https");
        try {
            String cipherSuite = sSLSession.getCipherSuite();
            String str = CACHED_INFO_ATTR;
            CachedInfo cachedInfo = (CachedInfo) sSLSession.getValue(str);
            if (cachedInfo != null) {
                keySize = cachedInfo.getKeySize();
                certs = cachedInfo.getCerts();
                idStr = cachedInfo.getIdStr();
            } else {
                Integer num = new Integer(ServletSSL.deduceKeyLength(cipherSuite));
                X509Certificate[] certChain = getCertChain(sSLSession);
                String hexString = TypeUtil.toHexString(sSLSession.getId());
                sSLSession.putValue(str, new CachedInfo(num, certChain, hexString));
                keySize = num;
                certs = certChain;
                idStr = hexString;
            }
            if (certs != null) {
                request.setAttribute("javax.servlet.request.X509Certificate", certs);
            }
            request.setAttribute("javax.servlet.request.cipher_suite", cipherSuite);
            request.setAttribute("javax.servlet.request.key_size", keySize);
            request.setAttribute("javax.servlet.request.ssl_session_id", idStr);
        } catch (Exception e2) {
            LOG.warn(Log.EXCEPTION, e2);
        }
    }

    public static X509Certificate[] getCertChain(SSLSession sSLSession) throws CertificateException, SSLPeerUnverifiedException {
        try {
            javax.security.cert.X509Certificate[] peerCertificateChain = sSLSession.getPeerCertificateChain();
            if (peerCertificateChain != null && peerCertificateChain.length != 0) {
                int length = peerCertificateChain.length;
                X509Certificate[] x509CertificateArr = new X509Certificate[length];
                CertificateFactory certificateFactory = CertificateFactory.getInstance(KeyUtil.CERT_TYPE_X509);
                for (int i2 = 0; i2 < length; i2++) {
                    x509CertificateArr[i2] = (X509Certificate) certificateFactory.generateCertificate(new ByteArrayInputStream(peerCertificateChain[i2].getEncoded()));
                }
                return x509CertificateArr;
            }
            return null;
        } catch (SSLPeerUnverifiedException unused) {
            return null;
        } catch (Exception e2) {
            LOG.warn(Log.EXCEPTION, e2);
            return null;
        }
    }
}
