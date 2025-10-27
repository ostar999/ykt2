package com.huawei.secure.android.common.ssl;

import android.content.Context;
import com.huawei.secure.android.common.ssl.util.g;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.X509TrustManager;
import org.apache.http.conn.ssl.SSLSocketFactory;

/* loaded from: classes4.dex */
public class SASFCompatiableSystemCA extends SSLSocketFactory {

    /* renamed from: i, reason: collision with root package name */
    private static final String f8286i = "SASFCompatiableSystemCA";

    /* renamed from: j, reason: collision with root package name */
    private static volatile SASFCompatiableSystemCA f8287j;

    /* renamed from: a, reason: collision with root package name */
    private SSLContext f8288a;

    /* renamed from: b, reason: collision with root package name */
    private SSLSocket f8289b;

    /* renamed from: c, reason: collision with root package name */
    private Context f8290c;

    /* renamed from: d, reason: collision with root package name */
    private String[] f8291d;

    /* renamed from: e, reason: collision with root package name */
    private X509TrustManager f8292e;

    /* renamed from: f, reason: collision with root package name */
    private String[] f8293f;

    /* renamed from: g, reason: collision with root package name */
    private String[] f8294g;

    /* renamed from: h, reason: collision with root package name */
    private String[] f8295h;

    private SASFCompatiableSystemCA(KeyStore keyStore) throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException, KeyStoreException {
        super(keyStore);
        this.f8289b = null;
    }

    public static void a(X509TrustManager x509TrustManager) {
        g.c(f8286i, "sasfc update socket factory trust manager");
        long jCurrentTimeMillis = System.currentTimeMillis();
        try {
            f8287j = new SASFCompatiableSystemCA((KeyStore) null, x509TrustManager);
        } catch (KeyManagementException unused) {
            g.b(f8286i, "KeyManagementException");
        } catch (KeyStoreException unused2) {
            g.b(f8286i, "KeyStoreException");
        } catch (NoSuchAlgorithmException unused3) {
            g.b(f8286i, "NoSuchAlgorithmException");
        } catch (UnrecoverableKeyException unused4) {
            g.b(f8286i, "UnrecoverableKeyException");
        }
        g.a(f8286i, "sasf system ca update: cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
    }

    public static SASFCompatiableSystemCA getInstance(KeyStore keyStore, Context context) throws NoSuchAlgorithmException, UnrecoverableKeyException, IOException, KeyManagementException, KeyStoreException, CertificateException, IllegalArgumentException {
        com.huawei.secure.android.common.ssl.util.c.a(context);
        if (f8287j == null) {
            synchronized (SecureApacheSSLSocketFactory.class) {
                if (f8287j == null) {
                    f8287j = new SASFCompatiableSystemCA(keyStore, context);
                }
            }
        }
        return f8287j;
    }

    @Override // org.apache.http.conn.ssl.SSLSocketFactory, org.apache.http.conn.scheme.LayeredSocketFactory
    public Socket createSocket(Socket socket, String str, int i2, boolean z2) throws IOException {
        g.c(f8286i, "createSocket: socket host port autoClose");
        Socket socketCreateSocket = this.f8288a.getSocketFactory().createSocket(socket, str, i2, z2);
        if (socketCreateSocket instanceof SSLSocket) {
            a(socketCreateSocket);
            SSLSocket sSLSocket = (SSLSocket) socketCreateSocket;
            this.f8289b = sSLSocket;
            this.f8291d = (String[]) sSLSocket.getEnabledCipherSuites().clone();
        }
        return socketCreateSocket;
    }

    public String[] getBlackCiphers() {
        return this.f8293f;
    }

    public X509Certificate[] getChain() {
        X509TrustManager x509TrustManager = this.f8292e;
        return x509TrustManager instanceof SecureX509TrustManager ? ((SecureX509TrustManager) x509TrustManager).getChain() : new X509Certificate[0];
    }

    public Context getContext() {
        return this.f8290c;
    }

    public String[] getProtocols() {
        return this.f8295h;
    }

    public SSLContext getSslContext() {
        return this.f8288a;
    }

    public SSLSocket getSslSocket() {
        return this.f8289b;
    }

    public String[] getSupportedCipherSuites() {
        String[] strArr = this.f8291d;
        return strArr != null ? strArr : new String[0];
    }

    public String[] getWhiteCiphers() {
        return this.f8294g;
    }

    public X509TrustManager getX509TrustManager() {
        return this.f8292e;
    }

    public void setBlackCiphers(String[] strArr) {
        this.f8293f = strArr;
    }

    public void setContext(Context context) {
        this.f8290c = context.getApplicationContext();
    }

    public void setProtocols(String[] strArr) {
        this.f8295h = strArr;
    }

    public void setSslContext(SSLContext sSLContext) {
        this.f8288a = sSLContext;
    }

    public void setSslSocket(SSLSocket sSLSocket) {
        this.f8289b = sSLSocket;
    }

    public void setWhiteCiphers(String[] strArr) {
        this.f8294g = strArr;
    }

    public void setX509TrustManager(X509TrustManager x509TrustManager) {
        this.f8292e = x509TrustManager;
    }

    private SASFCompatiableSystemCA(KeyStore keyStore, Context context) throws NoSuchAlgorithmException, UnrecoverableKeyException, IOException, CertificateException, KeyStoreException, KeyManagementException, IllegalArgumentException {
        super(keyStore);
        this.f8289b = null;
        if (context == null) {
            g.b(f8286i, "SecureSSLSocketFactory: context is null");
            return;
        }
        setContext(context);
        setSslContext(SSLUtil.setSSLContext());
        SecureX509TrustManager sSFSecureX509SingleInstance = SSFSecureX509SingleInstance.getInstance(context);
        this.f8292e = sSFSecureX509SingleInstance;
        this.f8288a.init(null, new X509TrustManager[]{sSFSecureX509SingleInstance}, null);
    }

    @Override // org.apache.http.conn.ssl.SSLSocketFactory, org.apache.http.conn.scheme.SocketFactory
    public Socket createSocket() throws IOException {
        g.c(f8286i, "createSocket: ");
        Socket socketCreateSocket = this.f8288a.getSocketFactory().createSocket();
        if (socketCreateSocket instanceof SSLSocket) {
            a(socketCreateSocket);
            SSLSocket sSLSocket = (SSLSocket) socketCreateSocket;
            this.f8289b = sSLSocket;
            this.f8291d = (String[]) sSLSocket.getEnabledCipherSuites().clone();
        }
        return socketCreateSocket;
    }

    private void a(Socket socket) {
        boolean z2;
        boolean z3 = true;
        if (com.huawei.secure.android.common.ssl.util.a.a(this.f8295h)) {
            z2 = false;
        } else {
            g.c(f8286i, "set protocols");
            SSLUtil.setEnabledProtocols((SSLSocket) socket, this.f8295h);
            z2 = true;
        }
        if (com.huawei.secure.android.common.ssl.util.a.a(this.f8294g) && com.huawei.secure.android.common.ssl.util.a.a(this.f8293f)) {
            z3 = false;
        } else {
            g.c(f8286i, "set white cipher or black cipher");
            SSLSocket sSLSocket = (SSLSocket) socket;
            SSLUtil.setEnabledProtocols(sSLSocket);
            if (!com.huawei.secure.android.common.ssl.util.a.a(this.f8294g)) {
                SSLUtil.setWhiteListCipherSuites(sSLSocket, this.f8294g);
            } else {
                SSLUtil.setBlackListCipherSuites(sSLSocket, this.f8293f);
            }
        }
        if (!z2) {
            g.c(f8286i, "set default protocols");
            SSLUtil.setEnabledProtocols((SSLSocket) socket);
        }
        if (z3) {
            return;
        }
        g.c(f8286i, "set default cipher suites");
        SSLUtil.setEnableSafeCipherSuites((SSLSocket) socket);
    }

    public SASFCompatiableSystemCA(KeyStore keyStore, X509TrustManager x509TrustManager) throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException, KeyStoreException, IllegalArgumentException {
        super(keyStore);
        this.f8289b = null;
        this.f8288a = SSLUtil.setSSLContext();
        setX509TrustManager(x509TrustManager);
        this.f8288a.init(null, new X509TrustManager[]{x509TrustManager}, null);
    }
}
