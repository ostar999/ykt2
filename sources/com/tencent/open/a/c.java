package com.tencent.open.a;

import com.tencent.open.log.SLog;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/* loaded from: classes6.dex */
public class c extends SSLSocketFactory {

    /* renamed from: a, reason: collision with root package name */
    private SSLSocketFactory f20514a;

    /* renamed from: b, reason: collision with root package name */
    private TrustManager[] f20515b;

    public c() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        SSLContext sSLContext = SSLContext.getInstance("TLS");
        TrustManager[] trustManagerArrB = b();
        this.f20515b = trustManagerArrB;
        sSLContext.init(null, trustManagerArrB, null);
        this.f20514a = sSLContext.getSocketFactory();
    }

    private TrustManager[] b() throws NoSuchAlgorithmException, KeyStoreException {
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length == 1 && (trustManagers[0] instanceof X509TrustManager)) {
                return trustManagers;
            }
            SLog.e("openSDK_LOG.Tls2SupportedSocketFactory", "Unexpected default trust managers: " + Arrays.toString(trustManagers));
            return null;
        } catch (GeneralSecurityException unused) {
            SLog.e("openSDK_LOG.Tls2SupportedSocketFactory", "The system has no TLS. Just give up.");
            return null;
        }
    }

    public TrustManager a() {
        TrustManager[] trustManagerArr = this.f20515b;
        if (trustManagerArr == null || trustManagerArr.length <= 0) {
            return null;
        }
        return trustManagerArr[0];
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i2) throws IOException {
        return a(this.f20514a.createSocket(str, i2));
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getDefaultCipherSuites() {
        return this.f20514a.getDefaultCipherSuites();
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getSupportedCipherSuites() {
        return this.f20514a.getSupportedCipherSuites();
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i2, InetAddress inetAddress, int i3) throws IOException {
        return a(this.f20514a.createSocket(str, i2, inetAddress, i3));
    }

    private Socket a(Socket socket) {
        if (socket instanceof SSLSocket) {
            SSLSocket sSLSocket = (SSLSocket) socket;
            sSLSocket.setEnabledProtocols(sSLSocket.getSupportedProtocols());
        }
        return socket;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i2) throws IOException {
        return a(this.f20514a.createSocket(inetAddress, i2));
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i2, InetAddress inetAddress2, int i3) throws IOException {
        return a(this.f20514a.createSocket(inetAddress, i2, inetAddress2, i3));
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public Socket createSocket(Socket socket, String str, int i2, boolean z2) throws IOException {
        return a(this.f20514a.createSocket(socket, str, i2, z2));
    }
}
