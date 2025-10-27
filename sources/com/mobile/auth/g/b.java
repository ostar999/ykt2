package com.mobile.auth.g;

import com.mobile.auth.l.c;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/* loaded from: classes4.dex */
public class b extends SSLSocketFactory {

    /* renamed from: b, reason: collision with root package name */
    private static final String[] f9754b = {"TLSv1.2"};

    /* renamed from: a, reason: collision with root package name */
    final SSLSocketFactory f9755a;

    public b(SSLSocketFactory sSLSocketFactory) {
        this.f9755a = sSLSocketFactory;
    }

    private Socket a(Socket socket) {
        if (socket instanceof SSLSocket) {
            SSLSocket sSLSocket = (SSLSocket) socket;
            for (String str : sSLSocket.getEnabledProtocols()) {
                c.a("enableProtocol", str);
            }
            sSLSocket.setEnabledProtocols(f9754b);
            sSLSocket.setEnabledCipherSuites(new String[]{"TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA", "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA"});
        }
        return socket;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket() throws IOException {
        return a(this.f9755a.createSocket());
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i2) throws IOException {
        return a(this.f9755a.createSocket(str, i2));
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i2, InetAddress inetAddress, int i3) throws IOException {
        return a(this.f9755a.createSocket(str, i2, inetAddress, i3));
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i2) throws IOException {
        return a(this.f9755a.createSocket(inetAddress, i2));
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i2, InetAddress inetAddress2, int i3) throws IOException {
        return a(this.f9755a.createSocket(inetAddress, i2, inetAddress2, i3));
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public Socket createSocket(Socket socket, String str, int i2, boolean z2) throws IOException {
        return a(this.f9755a.createSocket(socket, str, i2, z2));
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getDefaultCipherSuites() {
        return this.f9755a.getDefaultCipherSuites();
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getSupportedCipherSuites() {
        return this.f9755a.getSupportedCipherSuites();
    }

    public String toString() {
        return "Tls12SocketFactory";
    }
}
