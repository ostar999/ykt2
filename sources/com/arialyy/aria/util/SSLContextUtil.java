package com.arialyy.aria.util;

import android.text.TextUtils;
import cn.hutool.crypto.KeyUtil;
import com.arialyy.aria.core.AriaConfig;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.WeakHashMap;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/* loaded from: classes2.dex */
public class SSLContextUtil {
    private static final String TAG = "SSLContextUtil";
    private static Map<String, SSLContext> SSL_CACHE = new WeakHashMap();
    private static TrustManager trustManagers = new X509TrustManager() { // from class: com.arialyy.aria.util.SSLContextUtil.1
        @Override // javax.net.ssl.X509TrustManager
        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        }

        @Override // javax.net.ssl.X509TrustManager
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    };
    public static final HostnameVerifier HOSTNAME_VERIFIER = new HostnameVerifier() { // from class: com.arialyy.aria.util.SSLContextUtil.2
        @Override // javax.net.ssl.HostnameVerifier
        public boolean verify(String str, SSLSession sSLSession) {
            return true;
        }
    };

    private static SSLContext createContext(String str, Certificate certificate, String str2, String str3) throws NoSuchAlgorithmException, UnrecoverableKeyException, IOException, KeyStoreException, CertificateException, KeyManagementException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry(str, certificate);
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, null);
            if (TextUtils.isEmpty(str2)) {
                str2 = "TLS";
            }
            SSLContext sSLContext = SSLContext.getInstance(str2);
            sSLContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());
            SSL_CACHE.put(str3, sSLContext);
            return sSLContext;
        } catch (IOException | KeyManagementException | KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException | CertificateException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static String getCacheKey(String str, String str2) {
        return CommonUtil.getStrMd5(String.format("%s_%s", str, str2));
    }

    public static SSLContext getDefaultSLLContext(String str) throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sSLContext;
        SSLContext sSLContext2 = null;
        try {
            if (TextUtils.isEmpty(str)) {
                str = "TLS";
            }
            sSLContext = SSLContext.getInstance(str);
        } catch (Exception e2) {
            e = e2;
        }
        try {
            sSLContext.init(null, new TrustManager[]{trustManagers}, new SecureRandom());
            return sSLContext;
        } catch (Exception e3) {
            sSLContext2 = sSLContext;
            e = e3;
            e.printStackTrace();
            return sSLContext2;
        }
    }

    public static SSLContext getSSLContext(String str, String str2, String str3) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            try {
                String cacheKey = getCacheKey(str, str2);
                SSLContext sSLContext = SSL_CACHE.get(cacheKey);
                return sSLContext != null ? sSLContext : createContext(str, loadCert(new FileInputStream(str2)), str3, cacheKey);
            } catch (IOException | CertificateException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    public static SSLContext getSSLContextFromAssets(String str, String str2, String str3) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            try {
                String cacheKey = getCacheKey(str, str2);
                SSLContext sSLContext = SSL_CACHE.get(cacheKey);
                return sSLContext != null ? sSLContext : createContext(str, loadCert(AriaConfig.getInstance().getAPP().getAssets().open(str2)), str3, cacheKey);
            } catch (IOException | CertificateException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    private static Certificate loadCert(InputStream inputStream) throws IOException, CertificateException {
        Certificate certificateGenerateCertificate = CertificateFactory.getInstance(KeyUtil.CERT_TYPE_X509).generateCertificate(inputStream);
        ALog.d(TAG, String.format("ca【%s】", ((X509Certificate) certificateGenerateCertificate).getSubjectDN()));
        inputStream.close();
        return certificateGenerateCertificate;
    }
}
