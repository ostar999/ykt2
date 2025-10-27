package com.mobile.auth.y;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.net.InetAddress;
import java.net.Socket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/* loaded from: classes4.dex */
public final class o extends SSLSocketFactory {

    /* renamed from: b, reason: collision with root package name */
    private static final String[] f10623b = {"TLSv1.1", "TLSv1.2"};

    /* renamed from: a, reason: collision with root package name */
    final SSLSocketFactory f10624a;

    public o(SSLSocketFactory sSLSocketFactory) {
        this.f10624a = sSLSocketFactory;
    }

    private static Socket a(Socket socket) {
        try {
            if (socket instanceof SSLSocket) {
                ((SSLSocket) socket).setEnabledProtocols(f10623b);
            }
            return socket;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    @Override // javax.net.SocketFactory
    public final Socket createSocket(String str, int i2) {
        try {
            return a(this.f10624a.createSocket(str, i2));
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    @Override // javax.net.SocketFactory
    public final Socket createSocket(String str, int i2, InetAddress inetAddress, int i3) {
        try {
            return a(this.f10624a.createSocket(str, i2, inetAddress, i3));
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    @Override // javax.net.SocketFactory
    public final Socket createSocket(InetAddress inetAddress, int i2) {
        try {
            return a(this.f10624a.createSocket(inetAddress, i2));
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    @Override // javax.net.SocketFactory
    public final Socket createSocket(InetAddress inetAddress, int i2, InetAddress inetAddress2, int i3) {
        try {
            return a(this.f10624a.createSocket(inetAddress, i2, inetAddress2, i3));
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public final Socket createSocket(Socket socket, String str, int i2, boolean z2) {
        try {
            return a(this.f10624a.createSocket(socket, str, i2, z2));
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public final String[] getDefaultCipherSuites() {
        try {
            return this.f10624a.getDefaultCipherSuites();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public final String[] getSupportedCipherSuites() {
        try {
            return this.f10624a.getSupportedCipherSuites();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }
}
