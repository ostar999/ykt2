package com.hjq.http.ssl;

import cn.hutool.crypto.KeyUtil;
import com.hjq.http.EasyLog;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/* loaded from: classes4.dex */
public final class HttpSslFactory {
    private static X509TrustManager chooseTrustManager(TrustManager[] trustManagerArr) {
        for (TrustManager trustManager : trustManagerArr) {
            if (trustManager instanceof X509TrustManager) {
                return (X509TrustManager) trustManager;
            }
        }
        return null;
    }

    public static HttpSslConfig generateSslConfig() {
        return generateSslConfigBase(null, null, null, new InputStream[0]);
    }

    private static HttpSslConfig generateSslConfigBase(X509TrustManager x509TrustManager, InputStream inputStream, String str, InputStream... inputStreamArr) throws NoSuchAlgorithmException, KeyManagementException {
        try {
            KeyManager[] keyManagerArrPrepareKeyManager = prepareKeyManager(inputStream, str);
            TrustManager[] trustManagerArrPrepareTrustManager = prepareTrustManager(inputStreamArr);
            if (x509TrustManager == null) {
                x509TrustManager = trustManagerArrPrepareTrustManager != null ? chooseTrustManager(trustManagerArrPrepareTrustManager) : new UnSafeTrustManager();
            }
            SSLContext sSLContext = SSLContext.getInstance("TLS");
            sSLContext.init(keyManagerArrPrepareKeyManager, new TrustManager[]{x509TrustManager}, null);
            return new HttpSslConfig(sSLContext.getSocketFactory(), x509TrustManager);
        } catch (KeyManagementException | NoSuchAlgorithmException e2) {
            throw new AssertionError(e2);
        }
    }

    public static HostnameVerifier generateUnSafeHostnameVerifier() {
        return new UnSafeHostnameVerifier();
    }

    private static KeyManager[] prepareKeyManager(InputStream inputStream, String str) throws NoSuchAlgorithmException, UnrecoverableKeyException, IOException, KeyStoreException, CertificateException {
        if (inputStream != null && str != null) {
            try {
                KeyStore keyStore = KeyStore.getInstance("BKS");
                keyStore.load(inputStream, str.toCharArray());
                KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                keyManagerFactory.init(keyStore, str.toCharArray());
                return keyManagerFactory.getKeyManagers();
            } catch (IOException | KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException | CertificateException e2) {
                EasyLog.print(e2);
            }
        }
        return null;
    }

    private static TrustManager[] prepareTrustManager(InputStream... inputStreamArr) throws NoSuchAlgorithmException, IOException, CertificateException, KeyStoreException {
        if (inputStreamArr != null && inputStreamArr.length > 0) {
            try {
                try {
                    CertificateFactory certificateFactory = CertificateFactory.getInstance(KeyUtil.CERT_TYPE_X509);
                    KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                    keyStore.load(null);
                    int length = inputStreamArr.length;
                    int i2 = 0;
                    int i3 = 0;
                    while (i2 < length) {
                        InputStream inputStream = inputStreamArr[i2];
                        int i4 = i3 + 1;
                        keyStore.setCertificateEntry(Integer.toString(i3), certificateFactory.generateCertificate(inputStream));
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e2) {
                                EasyLog.print(e2);
                            }
                        }
                        i2++;
                        i3 = i4;
                    }
                    TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                    trustManagerFactory.init(keyStore);
                    return trustManagerFactory.getTrustManagers();
                } catch (KeyStoreException e3) {
                    e = e3;
                    EasyLog.print(e);
                    return null;
                } catch (NoSuchAlgorithmException e4) {
                    e = e4;
                    EasyLog.print(e);
                    return null;
                } catch (CertificateException e5) {
                    e = e5;
                    EasyLog.print(e);
                    return null;
                }
            } catch (IOException e6) {
                e = e6;
                EasyLog.print(e);
                return null;
            }
        }
        return null;
    }

    public static HttpSslConfig generateSslConfig(X509TrustManager x509TrustManager) {
        return generateSslConfigBase(x509TrustManager, null, null, new InputStream[0]);
    }

    public static HttpSslConfig generateSslConfig(InputStream... inputStreamArr) {
        return generateSslConfigBase(null, null, null, inputStreamArr);
    }

    public static HttpSslConfig generateSslConfig(InputStream inputStream, String str, InputStream... inputStreamArr) {
        return generateSslConfigBase(null, inputStream, str, inputStreamArr);
    }

    public static HttpSslConfig generateSslConfig(InputStream inputStream, String str, X509TrustManager x509TrustManager) {
        return generateSslConfigBase(x509TrustManager, inputStream, str, new InputStream[0]);
    }
}
