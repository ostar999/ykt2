package com.huawei.secure.android.common;

import android.content.Context;
import com.huawei.secure.android.common.ssl.SSLUtil;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.apache.http.conn.ssl.StrictHostnameVerifier;
import org.apache.http.conn.ssl.X509HostnameVerifier;

@Deprecated
/* loaded from: classes4.dex */
public class SecureSSLSocketFactory extends SSLSocketFactory {
    public static final X509HostnameVerifier BROWSER_COMPATIBLE_HOSTNAME_VERIFIER = new BrowserCompatHostnameVerifier();
    public static final X509HostnameVerifier STRICT_HOSTNAME_VERIFIER = new StrictHostnameVerifier();

    /* renamed from: c, reason: collision with root package name */
    private static volatile SecureSSLSocketFactory f8167c = null;

    /* renamed from: a, reason: collision with root package name */
    private SSLContext f8168a;

    /* renamed from: b, reason: collision with root package name */
    private Context f8169b;

    private SecureSSLSocketFactory(Context context) throws IllegalAccessException, NoSuchAlgorithmException, IOException, KeyManagementException, CertificateException, KeyStoreException {
        this.f8168a = null;
        this.f8169b = context;
        this.f8168a = SSLUtil.setSSLContext();
        this.f8168a.init(null, new X509TrustManager[]{new SecureX509TrustManager(this.f8169b)}, null);
    }

    private void a(Socket socket) {
        SSLSocket sSLSocket = (SSLSocket) socket;
        SSLUtil.setEnabledProtocols(sSLSocket);
        SSLUtil.setEnableSafeCipherSuites(sSLSocket);
    }

    @Deprecated
    public static SecureSSLSocketFactory getInstance(Context context) throws IllegalAccessException, NoSuchAlgorithmException, IOException, CertificateException, KeyStoreException, KeyManagementException {
        if (f8167c == null) {
            synchronized (SecureSSLSocketFactory.class) {
                if (f8167c == null) {
                    f8167c = new SecureSSLSocketFactory(context);
                }
            }
        }
        return f8167c;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i2) throws IOException {
        Socket socketCreateSocket = this.f8168a.getSocketFactory().createSocket(str, i2);
        if (socketCreateSocket instanceof SSLSocket) {
            a(socketCreateSocket);
        }
        return socketCreateSocket;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getDefaultCipherSuites() {
        return new String[0];
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getSupportedCipherSuites() {
        return new String[0];
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i2) throws IOException {
        return createSocket(inetAddress.getHostAddress(), i2);
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i2, InetAddress inetAddress, int i3) throws IOException {
        return createSocket(str, i2);
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i2, InetAddress inetAddress2, int i3) throws IOException {
        return createSocket(inetAddress.getHostAddress(), i2);
    }

    @Deprecated
    public SecureSSLSocketFactory(InputStream inputStream, String str) throws NoSuchAlgorithmException, IOException, KeyManagementException, CertificateException, KeyStoreException {
        this.f8168a = null;
        this.f8168a = SSLUtil.setSSLContext();
        this.f8168a.init(null, new X509TrustManager[]{new HiCloudX509TrustManager(inputStream, str)}, null);
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public Socket createSocket(Socket socket, String str, int i2, boolean z2) throws IOException {
        Socket socketCreateSocket = this.f8168a.getSocketFactory().createSocket(socket, str, i2, z2);
        if (socketCreateSocket instanceof SSLSocket) {
            a(socketCreateSocket);
        }
        return socketCreateSocket;
    }
}
