package com.huawei.secure.android.common;

import android.content.Context;
import com.huawei.secure.android.common.ssl.SSLUtil;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.X509TrustManager;
import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.StrictHostnameVerifier;
import org.apache.http.conn.ssl.X509HostnameVerifier;

@Deprecated
/* loaded from: classes4.dex */
public class SecureApacheSSLSocketFactory extends SSLSocketFactory {
    public static final X509HostnameVerifier BROWSER_COMPATIBLE_HOSTNAME_VERIFIER = new BrowserCompatHostnameVerifier();
    public static final X509HostnameVerifier STRICT_HOSTNAME_VERIFIER = new StrictHostnameVerifier();

    /* renamed from: c, reason: collision with root package name */
    private static volatile SecureApacheSSLSocketFactory f8164c = null;

    /* renamed from: a, reason: collision with root package name */
    private SSLContext f8165a;

    /* renamed from: b, reason: collision with root package name */
    private Context f8166b;

    private SecureApacheSSLSocketFactory(KeyStore keyStore) throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException, KeyStoreException {
        super(keyStore);
    }

    private void a(Socket socket) {
        SSLSocket sSLSocket = (SSLSocket) socket;
        SSLUtil.setEnabledProtocols(sSLSocket);
        SSLUtil.setEnableSafeCipherSuites(sSLSocket);
    }

    @Deprecated
    public static SecureApacheSSLSocketFactory getInstance(KeyStore keyStore, Context context) throws IllegalAccessException, NoSuchAlgorithmException, UnrecoverableKeyException, IOException, KeyManagementException, KeyStoreException, CertificateException {
        if (f8164c == null) {
            synchronized (SecureApacheSSLSocketFactory.class) {
                if (f8164c == null) {
                    f8164c = new SecureApacheSSLSocketFactory(keyStore, context);
                }
            }
        }
        return f8164c;
    }

    @Override // org.apache.http.conn.ssl.SSLSocketFactory, org.apache.http.conn.scheme.LayeredSocketFactory
    public Socket createSocket(Socket socket, String str, int i2, boolean z2) throws IOException {
        Socket socketCreateSocket = this.f8165a.getSocketFactory().createSocket(socket, str, i2, z2);
        a(socketCreateSocket);
        return socketCreateSocket;
    }

    private SecureApacheSSLSocketFactory(KeyStore keyStore, Context context) throws IllegalAccessException, NoSuchAlgorithmException, UnrecoverableKeyException, IOException, KeyManagementException, KeyStoreException, CertificateException {
        super(keyStore);
        this.f8166b = context;
        this.f8165a = SSLUtil.setSSLContext();
        this.f8165a.init(null, new X509TrustManager[]{new SecureX509TrustManager(this.f8166b)}, null);
    }

    @Override // org.apache.http.conn.ssl.SSLSocketFactory, org.apache.http.conn.scheme.SocketFactory
    public Socket createSocket() throws IOException {
        Socket socketCreateSocket = this.f8165a.getSocketFactory().createSocket();
        a(socketCreateSocket);
        return socketCreateSocket;
    }

    @Deprecated
    public SecureApacheSSLSocketFactory(KeyStore keyStore, InputStream inputStream, String str) throws NoSuchAlgorithmException, UnrecoverableKeyException, IOException, KeyManagementException, KeyStoreException, CertificateException {
        super(keyStore);
        this.f8165a = SSLUtil.setSSLContext();
        this.f8165a.init(null, new X509TrustManager[]{new HiCloudX509TrustManager(inputStream, str)}, null);
    }
}
