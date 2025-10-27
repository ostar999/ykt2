package com.meizu.cloud.pushsdk.b.h;

import android.content.Context;
import android.net.SSLSessionCache;
import android.os.Environment;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.base.a.d;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/* loaded from: classes4.dex */
public class a extends SSLSocketFactory {

    /* renamed from: a, reason: collision with root package name */
    private String f9212a = "TlsSessionTicket";

    /* renamed from: b, reason: collision with root package name */
    private SSLSocketFactory f9213b;

    public a(Context context) throws NoSuchAlgorithmException {
        SSLSessionCache sSLSessionCache;
        try {
            SSLContext sSLContext = SSLContext.getDefault();
            try {
                sSLSessionCache = new SSLSessionCache(new File(Environment.getExternalStorageDirectory(), "sslCache"));
            } catch (IOException e2) {
                DebugLogger.e(this.f9212a, e2.getMessage());
                sSLSessionCache = new SSLSessionCache(context);
            }
            d dVarA = com.meizu.cloud.pushsdk.base.a.a.a(sSLSessionCache).a("install", SSLSessionCache.class, SSLContext.class).a(sSLSessionCache, sSLSessionCache, sSLContext);
            DebugLogger.i(this.f9212a, "install tls session cache " + dVarA.f9230a);
            this.f9213b = sSLContext.getSocketFactory();
        } catch (Exception e3) {
            DebugLogger.e(this.f9212a, e3.getMessage());
            this.f9213b = (SSLSocketFactory) SSLSocketFactory.getDefault();
        }
    }

    private Socket a(Socket socket) throws NoSuchMethodException, SecurityException {
        if (socket instanceof SSLSocket) {
            d dVarA = com.meizu.cloud.pushsdk.base.a.a.a(socket).a("setUseSessionTickets", Boolean.TYPE).a(socket, Boolean.TRUE);
            DebugLogger.i(this.f9212a, "set ssl session ticket support " + dVarA.f9230a);
        }
        return socket;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i2) throws IOException {
        return a(this.f9213b.createSocket(str, i2));
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i2, InetAddress inetAddress, int i3) throws IOException {
        return a(this.f9213b.createSocket(str, i2, inetAddress, i3));
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i2) throws IOException {
        return a(this.f9213b.createSocket(inetAddress, i2));
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i2, InetAddress inetAddress2, int i3) throws IOException {
        return a(this.f9213b.createSocket(inetAddress, i2, inetAddress2, i3));
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public Socket createSocket(Socket socket, String str, int i2, boolean z2) throws IOException {
        return a(this.f9213b.createSocket(socket, str, i2, z2));
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getDefaultCipherSuites() {
        return this.f9213b.getDefaultCipherSuites();
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getSupportedCipherSuites() {
        return this.f9213b.getSupportedCipherSuites();
    }
}
