package com.huawei.secure.android.common.ssl;

import android.content.Context;
import com.huawei.secure.android.common.ssl.util.g;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

/* loaded from: classes4.dex */
public class SSFCompatiableSystemCA extends SSLSocketFactory {

    /* renamed from: i, reason: collision with root package name */
    private static final String f8296i = "SSFCompatiableSystemCA";

    /* renamed from: j, reason: collision with root package name */
    private static volatile SSFCompatiableSystemCA f8297j;

    /* renamed from: a, reason: collision with root package name */
    private SSLContext f8298a;

    /* renamed from: b, reason: collision with root package name */
    private SSLSocket f8299b;

    /* renamed from: c, reason: collision with root package name */
    private Context f8300c;

    /* renamed from: d, reason: collision with root package name */
    private String[] f8301d;

    /* renamed from: e, reason: collision with root package name */
    private X509TrustManager f8302e;

    /* renamed from: f, reason: collision with root package name */
    private String[] f8303f;

    /* renamed from: g, reason: collision with root package name */
    private String[] f8304g;

    /* renamed from: h, reason: collision with root package name */
    private String[] f8305h;

    private SSFCompatiableSystemCA(Context context) throws NoSuchAlgorithmException, IOException, CertificateException, KeyStoreException, KeyManagementException {
        this.f8298a = null;
        this.f8299b = null;
        if (context == null) {
            g.b(f8296i, "SecureSSLSocketFactory: context is null");
            return;
        }
        setContext(context);
        setSslContext(SSLUtil.setSSLContext());
        SecureX509TrustManager sSFSecureX509SingleInstance = SSFSecureX509SingleInstance.getInstance(context);
        this.f8302e = sSFSecureX509SingleInstance;
        this.f8298a.init(null, new X509TrustManager[]{sSFSecureX509SingleInstance}, null);
    }

    public static void a(X509TrustManager x509TrustManager) {
        g.c(f8296i, "ssfc update socket factory trust manager");
        long jCurrentTimeMillis = System.currentTimeMillis();
        try {
            f8297j = new SSFCompatiableSystemCA(x509TrustManager);
        } catch (KeyManagementException unused) {
            g.b(f8296i, "KeyManagementException");
        } catch (NoSuchAlgorithmException unused2) {
            g.b(f8296i, "NoSuchAlgorithmException");
        }
        g.a(f8296i, "SSF system ca update: cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
    }

    public static SSFCompatiableSystemCA getInstance(Context context) throws NoSuchAlgorithmException, IOException, CertificateException, KeyStoreException, KeyManagementException, IllegalArgumentException {
        com.huawei.secure.android.common.ssl.util.c.a(context);
        if (f8297j == null) {
            synchronized (SSFCompatiableSystemCA.class) {
                if (f8297j == null) {
                    f8297j = new SSFCompatiableSystemCA(context);
                }
            }
        }
        if (f8297j.f8300c == null && context != null) {
            f8297j.setContext(context);
        }
        return f8297j;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i2) throws IOException {
        g.c(f8296i, "createSocket: host , port");
        Socket socketCreateSocket = this.f8298a.getSocketFactory().createSocket(str, i2);
        if (socketCreateSocket instanceof SSLSocket) {
            a(socketCreateSocket);
            SSLSocket sSLSocket = (SSLSocket) socketCreateSocket;
            this.f8299b = sSLSocket;
            this.f8301d = (String[]) sSLSocket.getEnabledCipherSuites().clone();
        }
        return socketCreateSocket;
    }

    public String[] getBlackCiphers() {
        return this.f8303f;
    }

    public X509Certificate[] getChain() {
        X509TrustManager x509TrustManager = this.f8302e;
        return x509TrustManager instanceof SecureX509TrustManager ? ((SecureX509TrustManager) x509TrustManager).getChain() : new X509Certificate[0];
    }

    public Context getContext() {
        return this.f8300c;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getDefaultCipherSuites() {
        return new String[0];
    }

    public String[] getProtocols() {
        return this.f8305h;
    }

    public SSLContext getSslContext() {
        return this.f8298a;
    }

    public SSLSocket getSslSocket() {
        return this.f8299b;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getSupportedCipherSuites() {
        String[] strArr = this.f8301d;
        return strArr != null ? strArr : new String[0];
    }

    public String[] getWhiteCiphers() {
        return this.f8304g;
    }

    public X509TrustManager getX509TrustManager() {
        return this.f8302e;
    }

    public void setBlackCiphers(String[] strArr) {
        this.f8303f = strArr;
    }

    public void setContext(Context context) {
        this.f8300c = context.getApplicationContext();
    }

    public void setProtocols(String[] strArr) {
        this.f8305h = strArr;
    }

    public void setSslContext(SSLContext sSLContext) {
        this.f8298a = sSLContext;
    }

    public void setWhiteCiphers(String[] strArr) {
        this.f8304g = strArr;
    }

    public void setX509TrustManager(X509TrustManager x509TrustManager) {
        this.f8302e = x509TrustManager;
    }

    private void a(Socket socket) {
        boolean z2;
        boolean z3 = true;
        if (com.huawei.secure.android.common.ssl.util.a.a(this.f8305h)) {
            z2 = false;
        } else {
            g.c(f8296i, "set protocols");
            SSLUtil.setEnabledProtocols((SSLSocket) socket, this.f8305h);
            z2 = true;
        }
        if (com.huawei.secure.android.common.ssl.util.a.a(this.f8304g) && com.huawei.secure.android.common.ssl.util.a.a(this.f8303f)) {
            z3 = false;
        } else {
            g.c(f8296i, "set white cipher or black cipher");
            SSLSocket sSLSocket = (SSLSocket) socket;
            SSLUtil.setEnabledProtocols(sSLSocket);
            if (!com.huawei.secure.android.common.ssl.util.a.a(this.f8304g)) {
                SSLUtil.setWhiteListCipherSuites(sSLSocket, this.f8304g);
            } else {
                SSLUtil.setBlackListCipherSuites(sSLSocket, this.f8303f);
            }
        }
        if (!z2) {
            g.c(f8296i, "set default protocols");
            SSLUtil.setEnabledProtocols((SSLSocket) socket);
        }
        if (z3) {
            return;
        }
        g.c(f8296i, "set default cipher suites");
        SSLUtil.setEnableSafeCipherSuites((SSLSocket) socket);
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i2) throws IOException {
        return createSocket(inetAddress.getHostAddress(), i2);
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i2, InetAddress inetAddress, int i3) throws IOException {
        return createSocket(str, i2);
    }

    public SSFCompatiableSystemCA(X509TrustManager x509TrustManager) throws NoSuchAlgorithmException, KeyManagementException, IllegalArgumentException {
        this.f8298a = null;
        this.f8299b = null;
        this.f8298a = SSLUtil.setSSLContext();
        setX509TrustManager(x509TrustManager);
        this.f8298a.init(null, new X509TrustManager[]{x509TrustManager}, null);
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i2, InetAddress inetAddress2, int i3) throws IOException {
        return createSocket(inetAddress.getHostAddress(), i2);
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public Socket createSocket(Socket socket, String str, int i2, boolean z2) throws IOException {
        g.c(f8296i, "createSocket: s , host , port , autoClose");
        Socket socketCreateSocket = this.f8298a.getSocketFactory().createSocket(socket, str, i2, z2);
        if (socketCreateSocket instanceof SSLSocket) {
            a(socketCreateSocket);
            SSLSocket sSLSocket = (SSLSocket) socketCreateSocket;
            this.f8299b = sSLSocket;
            this.f8301d = (String[]) sSLSocket.getEnabledCipherSuites().clone();
        }
        return socketCreateSocket;
    }
}
