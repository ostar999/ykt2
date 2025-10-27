package com.huawei.secure.android.common.ssl;

import android.content.Context;
import com.huawei.secure.android.common.ssl.util.g;
import java.io.IOException;
import java.io.InputStream;
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
import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.StrictHostnameVerifier;
import org.apache.http.conn.ssl.X509HostnameVerifier;

/* loaded from: classes4.dex */
public class SecureApacheSSLSocketFactory extends SSLSocketFactory {
    public static final X509HostnameVerifier BROWSER_COMPATIBLE_HOSTNAME_VERIFIER = new BrowserCompatHostnameVerifier();
    public static final X509HostnameVerifier STRICT_HOSTNAME_VERIFIER = new StrictHostnameVerifier();

    /* renamed from: i, reason: collision with root package name */
    private static final String f8316i = SecureApacheSSLSocketFactory.class.getSimpleName();

    /* renamed from: j, reason: collision with root package name */
    private static volatile SecureApacheSSLSocketFactory f8317j = null;

    /* renamed from: a, reason: collision with root package name */
    private SSLContext f8318a;

    /* renamed from: b, reason: collision with root package name */
    private SSLSocket f8319b;

    /* renamed from: c, reason: collision with root package name */
    private Context f8320c;

    /* renamed from: d, reason: collision with root package name */
    private String[] f8321d;

    /* renamed from: e, reason: collision with root package name */
    private X509TrustManager f8322e;

    /* renamed from: f, reason: collision with root package name */
    private String[] f8323f;

    /* renamed from: g, reason: collision with root package name */
    private String[] f8324g;

    /* renamed from: h, reason: collision with root package name */
    private String[] f8325h;

    private SecureApacheSSLSocketFactory(KeyStore keyStore) throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException, KeyStoreException {
        super(keyStore);
        this.f8319b = null;
    }

    public static void a(X509TrustManager x509TrustManager) {
        g.c(f8316i, "sasf update socket factory trust manager");
        try {
            f8317j = new SecureApacheSSLSocketFactory((KeyStore) null, x509TrustManager);
        } catch (IOException unused) {
            g.b(f8316i, "IOException");
        } catch (KeyManagementException unused2) {
            g.b(f8316i, "KeyManagementException");
        } catch (KeyStoreException unused3) {
            g.b(f8316i, "KeyStoreException");
        } catch (NoSuchAlgorithmException unused4) {
            g.b(f8316i, "NoSuchAlgorithmException");
        } catch (UnrecoverableKeyException unused5) {
            g.b(f8316i, "UnrecoverableKeyException");
        } catch (CertificateException unused6) {
            g.b(f8316i, "CertificateException");
        }
    }

    public static SecureApacheSSLSocketFactory getInstance(KeyStore keyStore, Context context) throws NoSuchAlgorithmException, UnrecoverableKeyException, IOException, KeyManagementException, KeyStoreException, CertificateException, IllegalArgumentException {
        com.huawei.secure.android.common.ssl.util.c.a(context);
        if (f8317j == null) {
            synchronized (SecureApacheSSLSocketFactory.class) {
                if (f8317j == null) {
                    f8317j = new SecureApacheSSLSocketFactory(keyStore, context);
                }
            }
        }
        return f8317j;
    }

    @Override // org.apache.http.conn.ssl.SSLSocketFactory, org.apache.http.conn.scheme.LayeredSocketFactory
    public Socket createSocket(Socket socket, String str, int i2, boolean z2) throws IOException {
        g.c(f8316i, "createSocket: socket host port autoClose");
        Socket socketCreateSocket = this.f8318a.getSocketFactory().createSocket(socket, str, i2, z2);
        if (socketCreateSocket instanceof SSLSocket) {
            a(socketCreateSocket);
            SSLSocket sSLSocket = (SSLSocket) socketCreateSocket;
            this.f8319b = sSLSocket;
            this.f8321d = (String[]) sSLSocket.getEnabledCipherSuites().clone();
        }
        return socketCreateSocket;
    }

    public String[] getBlackCiphers() {
        return this.f8323f;
    }

    public X509Certificate[] getChain() {
        X509TrustManager x509TrustManager = this.f8322e;
        return x509TrustManager instanceof SecureX509TrustManager ? ((SecureX509TrustManager) x509TrustManager).getChain() : new X509Certificate[0];
    }

    public Context getContext() {
        return this.f8320c;
    }

    public String[] getProtocols() {
        return this.f8325h;
    }

    public SSLContext getSslContext() {
        return this.f8318a;
    }

    public SSLSocket getSslSocket() {
        return this.f8319b;
    }

    public String[] getSupportedCipherSuites() {
        String[] strArr = this.f8321d;
        return strArr != null ? strArr : new String[0];
    }

    public String[] getWhiteCiphers() {
        return this.f8324g;
    }

    public X509TrustManager getX509TrustManager() {
        return this.f8322e;
    }

    public void setBlackCiphers(String[] strArr) {
        this.f8323f = strArr;
    }

    public void setContext(Context context) {
        this.f8320c = context.getApplicationContext();
    }

    public void setProtocols(String[] strArr) {
        this.f8325h = strArr;
    }

    public void setSslContext(SSLContext sSLContext) {
        this.f8318a = sSLContext;
    }

    public void setSslSocket(SSLSocket sSLSocket) {
        this.f8319b = sSLSocket;
    }

    public void setWhiteCiphers(String[] strArr) {
        this.f8324g = strArr;
    }

    public void setX509TrustManager(X509TrustManager x509TrustManager) {
        this.f8322e = x509TrustManager;
    }

    private SecureApacheSSLSocketFactory(KeyStore keyStore, Context context) throws NoSuchAlgorithmException, UnrecoverableKeyException, IOException, CertificateException, KeyStoreException, KeyManagementException, IllegalArgumentException {
        super(keyStore);
        this.f8319b = null;
        if (context == null) {
            g.b(f8316i, "SecureSSLSocketFactory: context is null");
            return;
        }
        setContext(context);
        setSslContext(SSLUtil.setSSLContext());
        SecureX509TrustManager secureX509SingleInstance = SecureX509SingleInstance.getInstance(context);
        this.f8322e = secureX509SingleInstance;
        this.f8318a.init(null, new X509TrustManager[]{secureX509SingleInstance}, null);
    }

    @Override // org.apache.http.conn.ssl.SSLSocketFactory, org.apache.http.conn.scheme.SocketFactory
    public Socket createSocket() throws IOException {
        g.c(f8316i, "createSocket: ");
        Socket socketCreateSocket = this.f8318a.getSocketFactory().createSocket();
        if (socketCreateSocket instanceof SSLSocket) {
            a(socketCreateSocket);
            SSLSocket sSLSocket = (SSLSocket) socketCreateSocket;
            this.f8319b = sSLSocket;
            this.f8321d = (String[]) sSLSocket.getEnabledCipherSuites().clone();
        }
        return socketCreateSocket;
    }

    private void a(Socket socket) {
        boolean z2;
        boolean z3 = true;
        if (com.huawei.secure.android.common.ssl.util.a.a(this.f8325h)) {
            z2 = false;
        } else {
            g.c(f8316i, "set protocols");
            SSLUtil.setEnabledProtocols((SSLSocket) socket, this.f8325h);
            z2 = true;
        }
        if (com.huawei.secure.android.common.ssl.util.a.a(this.f8324g) && com.huawei.secure.android.common.ssl.util.a.a(this.f8323f)) {
            z3 = false;
        } else {
            g.c(f8316i, "set white cipher or black cipher");
            SSLSocket sSLSocket = (SSLSocket) socket;
            SSLUtil.setEnabledProtocols(sSLSocket);
            if (!com.huawei.secure.android.common.ssl.util.a.a(this.f8324g)) {
                SSLUtil.setWhiteListCipherSuites(sSLSocket, this.f8324g);
            } else {
                SSLUtil.setBlackListCipherSuites(sSLSocket, this.f8323f);
            }
        }
        if (!z2) {
            g.c(f8316i, "set default protocols");
            SSLUtil.setEnabledProtocols((SSLSocket) socket);
        }
        if (z3) {
            return;
        }
        g.c(f8316i, "set default cipher suites");
        SSLUtil.setEnableSafeCipherSuites((SSLSocket) socket);
    }

    public SecureApacheSSLSocketFactory(KeyStore keyStore, InputStream inputStream, String str) throws NoSuchAlgorithmException, UnrecoverableKeyException, IOException, KeyManagementException, KeyStoreException, CertificateException, IllegalArgumentException {
        super(keyStore);
        this.f8319b = null;
        this.f8318a = SSLUtil.setSSLContext();
        HiCloudX509TrustManager hiCloudX509TrustManager = new HiCloudX509TrustManager(inputStream, str);
        setX509TrustManager(hiCloudX509TrustManager);
        this.f8318a.init(null, new X509TrustManager[]{hiCloudX509TrustManager}, null);
    }

    public SecureApacheSSLSocketFactory(KeyStore keyStore, X509TrustManager x509TrustManager) throws NoSuchAlgorithmException, UnrecoverableKeyException, IOException, KeyManagementException, KeyStoreException, CertificateException, IllegalArgumentException {
        super(keyStore);
        this.f8319b = null;
        this.f8318a = SSLUtil.setSSLContext();
        setX509TrustManager(x509TrustManager);
        this.f8318a.init(null, new X509TrustManager[]{x509TrustManager}, null);
    }
}
