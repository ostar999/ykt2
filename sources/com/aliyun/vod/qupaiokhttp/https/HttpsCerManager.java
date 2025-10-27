package com.aliyun.vod.qupaiokhttp.https;

import cn.hutool.crypto.KeyUtil;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;

/* loaded from: classes2.dex */
public class HttpsCerManager {
    private OkHttpClient.Builder okHttpBuilder;

    public class OkHttpTrustManager implements X509TrustManager {
        private X509TrustManager defaultTrustManager;
        private X509TrustManager localTrustManager;

        public OkHttpTrustManager(X509TrustManager x509TrustManager) throws NoSuchAlgorithmException, KeyStoreException {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            this.defaultTrustManager = HttpsCerManager.this.chooseTrustManager(trustManagerFactory.getTrustManagers());
            this.localTrustManager = x509TrustManager;
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            try {
                this.defaultTrustManager.checkServerTrusted(x509CertificateArr, str);
            } catch (CertificateException unused) {
                this.localTrustManager.checkServerTrusted(x509CertificateArr, str);
            }
        }

        @Override // javax.net.ssl.X509TrustManager
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    public HttpsCerManager(OkHttpClient.Builder builder) {
        this.okHttpBuilder = builder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public X509TrustManager chooseTrustManager(TrustManager[] trustManagerArr) {
        for (TrustManager trustManager : trustManagerArr) {
            if (trustManager instanceof X509TrustManager) {
                return (X509TrustManager) trustManager;
            }
        }
        return null;
    }

    private KeyManager[] prepareKeyManager(InputStream inputStream, String str) throws NoSuchAlgorithmException, UnrecoverableKeyException, IOException, KeyStoreException, CertificateException {
        if (inputStream != null && str != null) {
            try {
                KeyStore keyStore = KeyStore.getInstance("BKS");
                keyStore.load(inputStream, str.toCharArray());
                KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                keyManagerFactory.init(keyStore, str.toCharArray());
                return keyManagerFactory.getKeyManagers();
            } catch (IOException e2) {
                e2.printStackTrace();
            } catch (KeyStoreException e3) {
                e3.printStackTrace();
            } catch (NoSuchAlgorithmException e4) {
                e4.printStackTrace();
            } catch (UnrecoverableKeyException e5) {
                e5.printStackTrace();
            } catch (CertificateException e6) {
                e6.printStackTrace();
            } catch (Exception e7) {
                e7.printStackTrace();
            }
        }
        return null;
    }

    private TrustManager[] prepareTrustManager(InputStream... inputStreamArr) throws NoSuchAlgorithmException, IOException, CertificateException, KeyStoreException {
        if (inputStreamArr != null && inputStreamArr.length > 0) {
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
                        } catch (IOException unused) {
                        }
                    }
                    i2++;
                    i3 = i4;
                }
                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init(keyStore);
                return trustManagerFactory.getTrustManagers();
            } catch (KeyStoreException e2) {
                e2.printStackTrace();
            } catch (NoSuchAlgorithmException e3) {
                e3.printStackTrace();
            } catch (CertificateException e4) {
                e4.printStackTrace();
            } catch (Exception e5) {
                e5.printStackTrace();
            }
        }
        return null;
    }

    public void setCertificates(List<InputStream> list) throws UnrecoverableKeyException, NoSuchAlgorithmException, IOException, CertificateException, KeyManagementException {
        setCertificates((InputStream[]) list.toArray(new InputStream[0]), null, null);
    }

    public void setCertificates(InputStream... inputStreamArr) throws UnrecoverableKeyException, NoSuchAlgorithmException, IOException, CertificateException, KeyManagementException {
        setCertificates(inputStreamArr, null, null);
    }

    private void setCertificates(InputStream[] inputStreamArr, InputStream inputStream, String str) throws UnrecoverableKeyException, NoSuchAlgorithmException, IOException, CertificateException, KeyManagementException {
        try {
            TrustManager[] trustManagerArrPrepareTrustManager = prepareTrustManager(inputStreamArr);
            KeyManager[] keyManagerArrPrepareKeyManager = prepareKeyManager(inputStream, str);
            SSLContext sSLContext = SSLContext.getInstance("TLS");
            sSLContext.init(keyManagerArrPrepareKeyManager, new TrustManager[]{new OkHttpTrustManager(chooseTrustManager(trustManagerArrPrepareTrustManager))}, new SecureRandom());
            this.okHttpBuilder.sslSocketFactory(sSLContext.getSocketFactory());
        } catch (KeyManagementException e2) {
            e2.printStackTrace();
        } catch (KeyStoreException e3) {
            e3.printStackTrace();
        } catch (NoSuchAlgorithmException e4) {
            e4.printStackTrace();
        }
    }
}
