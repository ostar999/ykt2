package com.huawei.secure.android.common.ssl;

import android.content.Context;
import com.huawei.secure.android.common.ssl.util.g;
import java.io.IOException;
import java.io.InputStream;
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
public class SecureSSLSocketFactoryNew extends SSLSocketFactory {

    /* renamed from: i, reason: collision with root package name */
    private static final String f8336i = "SecureSSLSocketFactoryNew";

    /* renamed from: j, reason: collision with root package name */
    private static volatile SecureSSLSocketFactoryNew f8337j;

    /* renamed from: a, reason: collision with root package name */
    protected SSLContext f8338a;

    /* renamed from: b, reason: collision with root package name */
    protected SSLSocket f8339b;

    /* renamed from: c, reason: collision with root package name */
    protected Context f8340c;

    /* renamed from: d, reason: collision with root package name */
    protected String[] f8341d;

    /* renamed from: e, reason: collision with root package name */
    protected X509TrustManager f8342e;

    /* renamed from: f, reason: collision with root package name */
    protected String[] f8343f;

    /* renamed from: g, reason: collision with root package name */
    protected String[] f8344g;

    /* renamed from: h, reason: collision with root package name */
    protected String[] f8345h;

    public SecureSSLSocketFactoryNew(InputStream inputStream, String str) throws NoSuchAlgorithmException, IOException, KeyManagementException, CertificateException, KeyStoreException, IllegalArgumentException {
        this.f8338a = null;
        this.f8339b = null;
        this.f8338a = SSLUtil.setSSLContext();
        HiCloudX509TrustManager hiCloudX509TrustManager = new HiCloudX509TrustManager(inputStream, str);
        setX509TrustManager(hiCloudX509TrustManager);
        this.f8338a.init(null, new X509TrustManager[]{hiCloudX509TrustManager}, null);
    }

    private void a(Socket socket) {
        boolean z2;
        boolean z3 = true;
        if (com.huawei.secure.android.common.ssl.util.a.a(this.f8345h)) {
            z2 = false;
        } else {
            g.c(f8336i, "set protocols");
            SSLUtil.setEnabledProtocols((SSLSocket) socket, this.f8345h);
            z2 = true;
        }
        if (com.huawei.secure.android.common.ssl.util.a.a(this.f8344g) && com.huawei.secure.android.common.ssl.util.a.a(this.f8343f)) {
            z3 = false;
        } else {
            g.c(f8336i, "set white cipher or black cipher");
            SSLSocket sSLSocket = (SSLSocket) socket;
            SSLUtil.setEnabledProtocols(sSLSocket);
            if (com.huawei.secure.android.common.ssl.util.a.a(this.f8344g)) {
                SSLUtil.setBlackListCipherSuites(sSLSocket, this.f8343f);
            } else {
                SSLUtil.setWhiteListCipherSuites(sSLSocket, this.f8344g);
            }
        }
        if (!z2) {
            g.c(f8336i, "set default protocols");
            SSLUtil.setEnabledProtocols((SSLSocket) socket);
        }
        if (z3) {
            return;
        }
        g.c(f8336i, "set default cipher suites");
        SSLUtil.setEnableSafeCipherSuites((SSLSocket) socket);
    }

    public static SecureSSLSocketFactoryNew getInstance(Context context) throws IllegalAccessException, NoSuchAlgorithmException, IOException, CertificateException, KeyStoreException, KeyManagementException, IllegalArgumentException {
        long jCurrentTimeMillis = System.currentTimeMillis();
        com.huawei.secure.android.common.ssl.util.c.a(context);
        if (f8337j == null) {
            synchronized (SecureSSLSocketFactoryNew.class) {
                if (f8337j == null) {
                    f8337j = new SecureSSLSocketFactoryNew(context);
                }
            }
        }
        if (f8337j.f8340c == null && context != null) {
            f8337j.setContext(context);
        }
        g.a(f8336i, "getInstance: cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
        return f8337j;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i2) throws IOException {
        g.c(f8336i, "createSocket: host , port");
        Socket socketCreateSocket = this.f8338a.getSocketFactory().createSocket(str, i2);
        if (socketCreateSocket instanceof SSLSocket) {
            a(socketCreateSocket);
            SSLSocket sSLSocket = (SSLSocket) socketCreateSocket;
            this.f8339b = sSLSocket;
            this.f8341d = (String[]) sSLSocket.getEnabledCipherSuites().clone();
        }
        return socketCreateSocket;
    }

    public String[] getBlackCiphers() {
        return this.f8343f;
    }

    public X509Certificate[] getChain() {
        X509TrustManager x509TrustManager = this.f8342e;
        return x509TrustManager instanceof SecureX509TrustManager ? ((SecureX509TrustManager) x509TrustManager).getChain() : new X509Certificate[0];
    }

    public Context getContext() {
        return this.f8340c;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getDefaultCipherSuites() {
        return new String[0];
    }

    public String[] getProtocols() {
        return this.f8345h;
    }

    public SSLContext getSslContext() {
        return this.f8338a;
    }

    public SSLSocket getSslSocket() {
        return this.f8339b;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getSupportedCipherSuites() {
        String[] strArr = this.f8341d;
        return strArr != null ? strArr : new String[0];
    }

    public String[] getWhiteCiphers() {
        return this.f8344g;
    }

    public X509TrustManager getX509TrustManager() {
        return this.f8342e;
    }

    public void setBlackCiphers(String[] strArr) {
        this.f8343f = strArr;
    }

    public void setContext(Context context) {
        this.f8340c = context.getApplicationContext();
    }

    public void setProtocols(String[] strArr) {
        this.f8345h = strArr;
    }

    public void setSslContext(SSLContext sSLContext) {
        this.f8338a = sSLContext;
    }

    public void setWhiteCiphers(String[] strArr) {
        this.f8344g = strArr;
    }

    public void setX509TrustManager(X509TrustManager x509TrustManager) {
        this.f8342e = x509TrustManager;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i2) throws IOException {
        return createSocket(inetAddress.getHostAddress(), i2);
    }

    private SecureSSLSocketFactoryNew(Context context) throws NoSuchAlgorithmException, IOException, CertificateException, KeyStoreException, KeyManagementException {
        this.f8338a = null;
        this.f8339b = null;
        if (context == null) {
            g.b(f8336i, "SecureSSLSocketFactory: context is null");
            return;
        }
        setContext(context);
        setSslContext(SSLUtil.setSSLContext());
        SecureX509TrustManager secureX509SingleInstance = SecureX509SingleInstance.getInstance(context);
        this.f8342e = secureX509SingleInstance;
        this.f8338a.init(null, new X509TrustManager[]{secureX509SingleInstance}, null);
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i2, InetAddress inetAddress, int i3) throws IOException {
        return createSocket(str, i2);
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i2, InetAddress inetAddress2, int i3) throws IOException {
        return createSocket(inetAddress.getHostAddress(), i2);
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public Socket createSocket(Socket socket, String str, int i2, boolean z2) throws IOException {
        g.c(f8336i, "createSocket s host port autoClose");
        Socket socketCreateSocket = this.f8338a.getSocketFactory().createSocket(socket, str, i2, z2);
        if (socketCreateSocket instanceof SSLSocket) {
            a(socketCreateSocket);
            SSLSocket sSLSocket = (SSLSocket) socketCreateSocket;
            this.f8339b = sSLSocket;
            this.f8341d = (String[]) sSLSocket.getEnabledCipherSuites().clone();
        }
        return socketCreateSocket;
    }

    public SecureSSLSocketFactoryNew(X509TrustManager x509TrustManager) throws NoSuchAlgorithmException, KeyManagementException, IllegalArgumentException {
        this.f8338a = null;
        this.f8339b = null;
        this.f8338a = SSLUtil.setSSLContext();
        setX509TrustManager(x509TrustManager);
        this.f8338a.init(null, new X509TrustManager[]{x509TrustManager}, null);
    }
}
