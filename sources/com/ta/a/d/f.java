package com.ta.a.d;

import android.net.SSLCertificateSocketFactory;
import android.os.Build;
import android.text.TextUtils;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/* loaded from: classes6.dex */
class f extends SSLSocketFactory {

    /* renamed from: d, reason: collision with root package name */
    private String f17214d;

    /* renamed from: a, reason: collision with root package name */
    private Method f17213a = null;
    private HostnameVerifier hostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier();

    public f(String str) {
        this.f17214d = str;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket() throws IOException {
        return null;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i2) throws IOException {
        return null;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i2, InetAddress inetAddress, int i3) throws IOException {
        return null;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i2) throws IOException {
        return null;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i2, InetAddress inetAddress2, int i3) throws IOException {
        return null;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public Socket createSocket(Socket socket, String str, int i2, boolean z2) throws IOException {
        com.ta.a.e.h.b("", "peerHost", this.f17214d, com.alipay.sdk.cons.c.f3231f, str, "port", Integer.valueOf(i2), "autoClose", Boolean.valueOf(z2));
        SSLCertificateSocketFactory sSLCertificateSocketFactory = (SSLCertificateSocketFactory) SSLCertificateSocketFactory.getDefault(0);
        if (Build.VERSION.SDK_INT < 24) {
            sSLCertificateSocketFactory.setTrustManagers(g.getTrustManagers());
        } else {
            sSLCertificateSocketFactory.setTrustManagers(c.getTrustManagers());
        }
        com.ta.a.e.h.m109a("", "createSocket");
        SSLSocket sSLSocket = (SSLSocket) sSLCertificateSocketFactory.createSocket(socket, this.f17214d, i2, true);
        com.ta.a.e.h.m109a("", "createSocket end");
        sSLSocket.setEnabledProtocols(sSLSocket.getSupportedProtocols());
        sSLCertificateSocketFactory.setHostname(sSLSocket, this.f17214d);
        SSLSession session = sSLSocket.getSession();
        if (this.hostnameVerifier.verify(this.f17214d, session)) {
            com.ta.a.e.h.b("", "SSLSession PeerHost", session.getPeerHost());
            return sSLSocket;
        }
        throw new SSLPeerUnverifiedException("Cannot verify hostname: " + this.f17214d);
    }

    public boolean equals(Object obj) {
        if (TextUtils.isEmpty(this.f17214d) || !(obj instanceof f)) {
            return false;
        }
        String str = ((f) obj).f17214d;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return this.f17214d.equals(str);
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getDefaultCipherSuites() {
        return new String[0];
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getSupportedCipherSuites() {
        return new String[0];
    }
}
