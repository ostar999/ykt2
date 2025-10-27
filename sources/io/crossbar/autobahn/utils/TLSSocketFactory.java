package io.crossbar.autobahn.utils;

import android.os.Build;
import c.h;
import d.b;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/* loaded from: classes8.dex */
public class TLSSocketFactory extends SSLSocketFactory {

    /* renamed from: b, reason: collision with root package name */
    public static final String f27153b = "TLSSocketFactory";

    /* renamed from: c, reason: collision with root package name */
    public static final String[] f27154c;

    /* renamed from: d, reason: collision with root package name */
    public static final X509TrustManager f27155d;

    /* renamed from: a, reason: collision with root package name */
    public SSLSocketFactory f27156a;

    static {
        if (Build.VERSION.SDK_INT >= 26) {
            f27154c = new String[]{"TLSv1", "TLSv1.1", "TLSv1.2"};
        } else {
            f27154c = new String[]{"TLSv1.2"};
        }
        f27155d = new X509TrustManager() { // from class: io.crossbar.autobahn.utils.TLSSocketFactory.1
            @Override // javax.net.ssl.X509TrustManager
            public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            }

            @Override // javax.net.ssl.X509TrustManager
            public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
                h.a(TLSSocketFactory.f27153b, "authType tls socketFactory: " + str + "verify: " + b.a.f26736v);
                if (!b.a.f26736v) {
                    h.a(TLSSocketFactory.f27153b, "tls verify in socketFactory closed: ");
                    return;
                }
                if (x509CertificateArr == null) {
                    throw new IllegalArgumentException("Check Server x509Certificates is null");
                }
                if (x509CertificateArr.length <= 0) {
                    throw new IllegalArgumentException("Check Server x509Certificates is empty");
                }
                for (X509Certificate x509Certificate : x509CertificateArr) {
                    x509Certificate.checkValidity();
                }
                h.a(TLSSocketFactory.f27153b, "tls verify finish: ");
            }

            @Override // javax.net.ssl.X509TrustManager
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
    }

    public TLSSocketFactory() throws NoSuchAlgorithmException, KeyManagementException {
        try {
            SSLContext sSLContext = SSLContext.getInstance("TLS");
            sSLContext.init(null, new TrustManager[]{f27155d}, new SecureRandom());
            this.f27156a = sSLContext.getSocketFactory();
        } catch (GeneralSecurityException unused) {
            throw new AssertionError();
        }
    }

    public static void a(Socket socket) {
        if (socket instanceof SSLSocket) {
            ((SSLSocket) socket).setEnabledProtocols(f27154c);
        }
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public Socket createSocket(Socket socket, String str, int i2, boolean z2) throws IOException {
        Socket socketCreateSocket = this.f27156a.createSocket(socket, str, i2, z2);
        a(socketCreateSocket);
        return socketCreateSocket;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getDefaultCipherSuites() {
        return this.f27156a.getDefaultCipherSuites();
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getSupportedCipherSuites() {
        return this.f27156a.getSupportedCipherSuites();
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i2) throws IOException {
        Socket socketCreateSocket = this.f27156a.createSocket(str, i2);
        a(socketCreateSocket);
        return socketCreateSocket;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i2, InetAddress inetAddress, int i3) throws IOException {
        Socket socketCreateSocket = this.f27156a.createSocket(str, i2, inetAddress, i3);
        a(socketCreateSocket);
        return socketCreateSocket;
    }

    public TLSSocketFactory(SSLSocketFactory sSLSocketFactory) {
        this.f27156a = sSLSocketFactory;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i2) throws IOException {
        Socket socketCreateSocket = this.f27156a.createSocket(inetAddress, i2);
        a(socketCreateSocket);
        return socketCreateSocket;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i2, InetAddress inetAddress2, int i3) throws IOException {
        Socket socketCreateSocket = this.f27156a.createSocket(inetAddress, i2, inetAddress2, i3);
        a(socketCreateSocket);
        return socketCreateSocket;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket() throws IOException {
        Socket socketCreateSocket = this.f27156a.createSocket();
        a(socketCreateSocket);
        return socketCreateSocket;
    }
}
