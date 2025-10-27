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
import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.apache.http.conn.ssl.StrictHostnameVerifier;
import org.apache.http.conn.ssl.X509HostnameVerifier;

@Deprecated
/* loaded from: classes4.dex */
public class SecureSSLSocketFactory extends SSLSocketFactory {

    @Deprecated
    public static final X509HostnameVerifier BROWSER_COMPATIBLE_HOSTNAME_VERIFIER = new BrowserCompatHostnameVerifier();

    @Deprecated
    public static final X509HostnameVerifier STRICT_HOSTNAME_VERIFIER = new StrictHostnameVerifier();

    /* renamed from: i, reason: collision with root package name */
    private static final String f8326i = SecureSSLSocketFactory.class.getSimpleName();

    /* renamed from: j, reason: collision with root package name */
    private static volatile SecureSSLSocketFactory f8327j = null;

    /* renamed from: a, reason: collision with root package name */
    private SSLContext f8328a;

    /* renamed from: b, reason: collision with root package name */
    private SSLSocket f8329b;

    /* renamed from: c, reason: collision with root package name */
    private Context f8330c;

    /* renamed from: d, reason: collision with root package name */
    private String[] f8331d;

    /* renamed from: e, reason: collision with root package name */
    private X509TrustManager f8332e;

    /* renamed from: f, reason: collision with root package name */
    private String[] f8333f;

    /* renamed from: g, reason: collision with root package name */
    private String[] f8334g;

    /* renamed from: h, reason: collision with root package name */
    private String[] f8335h;

    public SecureSSLSocketFactory(InputStream inputStream, String str) throws NoSuchAlgorithmException, IOException, KeyManagementException, CertificateException, KeyStoreException, IllegalArgumentException {
        this.f8328a = null;
        this.f8329b = null;
        this.f8328a = SSLUtil.setSSLContext();
        HiCloudX509TrustManager hiCloudX509TrustManager = new HiCloudX509TrustManager(inputStream, str);
        setX509TrustManager(hiCloudX509TrustManager);
        this.f8328a.init(null, new X509TrustManager[]{hiCloudX509TrustManager}, null);
    }

    public static void a(X509TrustManager x509TrustManager) {
        g.c(f8326i, "ssf update socket factory trust manager");
        long jCurrentTimeMillis = System.currentTimeMillis();
        try {
            f8327j = new SecureSSLSocketFactory(x509TrustManager);
        } catch (KeyManagementException unused) {
            g.b(f8326i, "KeyManagementException");
        } catch (NoSuchAlgorithmException unused2) {
            g.b(f8326i, "NoSuchAlgorithmException");
        }
        g.a(f8326i, "update: cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
    }

    public static SecureSSLSocketFactory getInstance(Context context) throws IllegalAccessException, NoSuchAlgorithmException, IOException, CertificateException, KeyStoreException, KeyManagementException, IllegalArgumentException {
        long jCurrentTimeMillis = System.currentTimeMillis();
        com.huawei.secure.android.common.ssl.util.c.a(context);
        if (f8327j == null) {
            synchronized (SecureSSLSocketFactory.class) {
                if (f8327j == null) {
                    f8327j = new SecureSSLSocketFactory(context);
                }
            }
        }
        if (f8327j.f8330c == null && context != null) {
            f8327j.setContext(context);
        }
        g.a(f8326i, "getInstance: cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
        return f8327j;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i2) throws IOException {
        g.c(f8326i, "createSocket: host , port");
        Socket socketCreateSocket = this.f8328a.getSocketFactory().createSocket(str, i2);
        if (socketCreateSocket instanceof SSLSocket) {
            a(socketCreateSocket);
            SSLSocket sSLSocket = (SSLSocket) socketCreateSocket;
            this.f8329b = sSLSocket;
            this.f8331d = (String[]) sSLSocket.getEnabledCipherSuites().clone();
        }
        return socketCreateSocket;
    }

    public String[] getBlackCiphers() {
        return this.f8333f;
    }

    public X509Certificate[] getChain() {
        X509TrustManager x509TrustManager = this.f8332e;
        return x509TrustManager instanceof SecureX509TrustManager ? ((SecureX509TrustManager) x509TrustManager).getChain() : new X509Certificate[0];
    }

    public Context getContext() {
        return this.f8330c;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getDefaultCipherSuites() {
        return new String[0];
    }

    public String[] getProtocols() {
        return this.f8335h;
    }

    public SSLContext getSslContext() {
        return this.f8328a;
    }

    public SSLSocket getSslSocket() {
        return this.f8329b;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getSupportedCipherSuites() {
        String[] strArr = this.f8331d;
        return strArr != null ? strArr : new String[0];
    }

    public String[] getWhiteCiphers() {
        return this.f8334g;
    }

    public X509TrustManager getX509TrustManager() {
        return this.f8332e;
    }

    public void setBlackCiphers(String[] strArr) {
        this.f8333f = strArr;
    }

    public void setContext(Context context) {
        this.f8330c = context.getApplicationContext();
    }

    public void setProtocols(String[] strArr) {
        this.f8335h = strArr;
    }

    public void setSslContext(SSLContext sSLContext) {
        this.f8328a = sSLContext;
    }

    public void setWhiteCiphers(String[] strArr) {
        this.f8334g = strArr;
    }

    public void setX509TrustManager(X509TrustManager x509TrustManager) {
        this.f8332e = x509TrustManager;
    }

    private void a(Socket socket) {
        boolean z2;
        boolean z3 = true;
        if (com.huawei.secure.android.common.ssl.util.a.a(this.f8335h)) {
            z2 = false;
        } else {
            g.c(f8326i, "set protocols");
            SSLUtil.setEnabledProtocols((SSLSocket) socket, this.f8335h);
            z2 = true;
        }
        if (com.huawei.secure.android.common.ssl.util.a.a(this.f8334g) && com.huawei.secure.android.common.ssl.util.a.a(this.f8333f)) {
            z3 = false;
        } else {
            g.c(f8326i, "set white cipher or black cipher");
            SSLSocket sSLSocket = (SSLSocket) socket;
            SSLUtil.setEnabledProtocols(sSLSocket);
            if (!com.huawei.secure.android.common.ssl.util.a.a(this.f8334g)) {
                SSLUtil.setWhiteListCipherSuites(sSLSocket, this.f8334g);
            } else {
                SSLUtil.setBlackListCipherSuites(sSLSocket, this.f8333f);
            }
        }
        if (!z2) {
            g.c(f8326i, "set default protocols");
            SSLUtil.setEnabledProtocols((SSLSocket) socket);
        }
        if (z3) {
            return;
        }
        g.c(f8326i, "set default cipher suites");
        SSLUtil.setEnableSafeCipherSuites((SSLSocket) socket);
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i2) throws IOException {
        return createSocket(inetAddress.getHostAddress(), i2);
    }

    private SecureSSLSocketFactory(Context context) throws NoSuchAlgorithmException, IOException, CertificateException, KeyStoreException, KeyManagementException {
        this.f8328a = null;
        this.f8329b = null;
        if (context == null) {
            g.b(f8326i, "SecureSSLSocketFactory: context is null");
            return;
        }
        setContext(context);
        setSslContext(SSLUtil.setSSLContext());
        SecureX509TrustManager secureX509SingleInstance = SecureX509SingleInstance.getInstance(context);
        this.f8332e = secureX509SingleInstance;
        this.f8328a.init(null, new X509TrustManager[]{secureX509SingleInstance}, null);
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
        g.c(f8326i, "createSocket s host port autoClose");
        Socket socketCreateSocket = this.f8328a.getSocketFactory().createSocket(socket, str, i2, z2);
        if (socketCreateSocket instanceof SSLSocket) {
            a(socketCreateSocket);
            SSLSocket sSLSocket = (SSLSocket) socketCreateSocket;
            this.f8329b = sSLSocket;
            this.f8331d = (String[]) sSLSocket.getEnabledCipherSuites().clone();
        }
        return socketCreateSocket;
    }

    public SecureSSLSocketFactory(X509TrustManager x509TrustManager) throws NoSuchAlgorithmException, KeyManagementException, IllegalArgumentException {
        this.f8328a = null;
        this.f8329b = null;
        this.f8328a = SSLUtil.setSSLContext();
        setX509TrustManager(x509TrustManager);
        this.f8328a.init(null, new X509TrustManager[]{x509TrustManager}, null);
    }
}
