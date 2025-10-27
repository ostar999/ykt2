package org.apache.http.conn.ssl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.HostNameResolver;
import org.apache.http.conn.scheme.LayeredSchemeSocketFactory;
import org.apache.http.conn.scheme.LayeredSocketFactory;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

@ThreadSafe
/* loaded from: classes9.dex */
public class SSLSocketFactory implements LayeredSchemeSocketFactory, LayeredSocketFactory {
    public static final String SSL = "SSL";
    public static final String SSLV2 = "SSLv2";
    public static final String TLS = "TLS";
    private volatile X509HostnameVerifier hostnameVerifier;
    private final HostNameResolver nameResolver;
    private final javax.net.ssl.SSLSocketFactory socketfactory;
    public static final X509HostnameVerifier ALLOW_ALL_HOSTNAME_VERIFIER = new AllowAllHostnameVerifier();
    public static final X509HostnameVerifier BROWSER_COMPATIBLE_HOSTNAME_VERIFIER = new BrowserCompatHostnameVerifier();
    public static final X509HostnameVerifier STRICT_HOSTNAME_VERIFIER = new StrictHostnameVerifier();

    @Deprecated
    public SSLSocketFactory(String str, KeyStore keyStore, String str2, KeyStore keyStore2, SecureRandom secureRandom, HostNameResolver hostNameResolver) throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException, KeyStoreException {
        this(createSSLContext(str, keyStore, str2, keyStore2, secureRandom, null), hostNameResolver);
    }

    private static SSLContext createDefaultSSLContext() {
        try {
            return createSSLContext("TLS", null, null, null, null, null);
        } catch (Exception e2) {
            throw new IllegalStateException("Failure initializing default SSL context", e2);
        }
    }

    private static SSLContext createSSLContext(String str, KeyStore keyStore, String str2, KeyStore keyStore2, SecureRandom secureRandom, TrustStrategy trustStrategy) throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException, KeyManagementException {
        if (str == null) {
            str = "TLS";
        }
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, str2 != null ? str2.toCharArray() : null);
        KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore2);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers != null && trustStrategy != null) {
            for (int i2 = 0; i2 < trustManagers.length; i2++) {
                TrustManager trustManager = trustManagers[i2];
                if (trustManager instanceof X509TrustManager) {
                    trustManagers[i2] = new TrustManagerDecorator((X509TrustManager) trustManager, trustStrategy);
                }
            }
        }
        SSLContext sSLContext = SSLContext.getInstance(str);
        sSLContext.init(keyManagers, trustManagers, secureRandom);
        return sSLContext;
    }

    public static SSLSocketFactory getSocketFactory() {
        return new SSLSocketFactory();
    }

    @Override // org.apache.http.conn.scheme.SchemeSocketFactory
    public Socket connectSocket(Socket socket, InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2, HttpParams httpParams) throws IOException {
        if (inetSocketAddress == null) {
            throw new IllegalArgumentException("Remote address may not be null");
        }
        if (httpParams == null) {
            throw new IllegalArgumentException("HTTP parameters may not be null");
        }
        if (socket == null) {
            socket = new Socket();
        }
        if (inetSocketAddress2 != null) {
            socket.setReuseAddress(HttpConnectionParams.getSoReuseaddr(httpParams));
            socket.bind(inetSocketAddress2);
        }
        int connectionTimeout = HttpConnectionParams.getConnectionTimeout(httpParams);
        try {
            socket.setSoTimeout(HttpConnectionParams.getSoTimeout(httpParams));
            socket.connect(inetSocketAddress, connectionTimeout);
            String string = inetSocketAddress.toString();
            int port = inetSocketAddress.getPort();
            String str = ":" + port;
            if (string.endsWith(str)) {
                string = string.substring(0, string.length() - str.length());
            }
            SSLSocket sSLSocket = socket instanceof SSLSocket ? (SSLSocket) socket : (SSLSocket) this.socketfactory.createSocket(socket, string, port, true);
            if (this.hostnameVerifier != null) {
                try {
                    this.hostnameVerifier.verify(string, sSLSocket);
                } catch (IOException e2) {
                    try {
                        sSLSocket.close();
                    } catch (Exception unused) {
                    }
                    throw e2;
                }
            }
            return sSLSocket;
        } catch (SocketTimeoutException unused2) {
            throw new ConnectTimeoutException("Connect to " + inetSocketAddress + " timed out");
        }
    }

    @Override // org.apache.http.conn.scheme.LayeredSchemeSocketFactory
    public Socket createLayeredSocket(Socket socket, String str, int i2, boolean z2) throws IOException {
        SSLSocket sSLSocket = (SSLSocket) this.socketfactory.createSocket(socket, str, i2, z2);
        if (this.hostnameVerifier != null) {
            this.hostnameVerifier.verify(str, sSLSocket);
        }
        return sSLSocket;
    }

    @Override // org.apache.http.conn.scheme.SchemeSocketFactory
    public Socket createSocket(HttpParams httpParams) throws IOException {
        return this.socketfactory.createSocket();
    }

    public X509HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    @Override // org.apache.http.conn.scheme.SchemeSocketFactory
    public boolean isSecure(Socket socket) throws IllegalArgumentException {
        if (socket == null) {
            throw new IllegalArgumentException("Socket may not be null");
        }
        if (!(socket instanceof SSLSocket)) {
            throw new IllegalArgumentException("Socket not created by this factory");
        }
        if (socket.isClosed()) {
            throw new IllegalArgumentException("Socket is closed");
        }
        return true;
    }

    @Deprecated
    public void setHostnameVerifier(X509HostnameVerifier x509HostnameVerifier) {
        if (x509HostnameVerifier == null) {
            throw new IllegalArgumentException("Hostname verifier may not be null");
        }
        this.hostnameVerifier = x509HostnameVerifier;
    }

    public SSLSocketFactory(String str, KeyStore keyStore, String str2, KeyStore keyStore2, SecureRandom secureRandom, X509HostnameVerifier x509HostnameVerifier) throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException, KeyStoreException {
        this(createSSLContext(str, keyStore, str2, keyStore2, secureRandom, null), x509HostnameVerifier);
    }

    @Deprecated
    public Socket createSocket() throws IOException {
        return this.socketfactory.createSocket();
    }

    public SSLSocketFactory(String str, KeyStore keyStore, String str2, KeyStore keyStore2, SecureRandom secureRandom, TrustStrategy trustStrategy, X509HostnameVerifier x509HostnameVerifier) throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException, KeyStoreException {
        this(createSSLContext(str, keyStore, str2, keyStore2, secureRandom, trustStrategy), x509HostnameVerifier);
    }

    @Deprecated
    public Socket createSocket(Socket socket, String str, int i2, boolean z2) throws IOException {
        return createLayeredSocket(socket, str, i2, z2);
    }

    public SSLSocketFactory(KeyStore keyStore, String str, KeyStore keyStore2) throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException, KeyStoreException {
        this("TLS", keyStore, str, keyStore2, null, null, BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    public SSLSocketFactory(KeyStore keyStore, String str) throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException, KeyStoreException {
        this("TLS", keyStore, str, null, null, null, BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    public SSLSocketFactory(KeyStore keyStore) throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException, KeyStoreException {
        this("TLS", null, null, keyStore, null, null, BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    public SSLSocketFactory(TrustStrategy trustStrategy, X509HostnameVerifier x509HostnameVerifier) throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException, KeyStoreException {
        this("TLS", null, null, null, null, trustStrategy, x509HostnameVerifier);
    }

    public SSLSocketFactory(TrustStrategy trustStrategy) throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException, KeyStoreException {
        this("TLS", null, null, null, null, trustStrategy, BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    public SSLSocketFactory(SSLContext sSLContext) {
        this(sSLContext, BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    @Deprecated
    public SSLSocketFactory(SSLContext sSLContext, HostNameResolver hostNameResolver) {
        this.socketfactory = sSLContext.getSocketFactory();
        this.hostnameVerifier = BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
        this.nameResolver = hostNameResolver;
    }

    public SSLSocketFactory(SSLContext sSLContext, X509HostnameVerifier x509HostnameVerifier) {
        this.socketfactory = sSLContext.getSocketFactory();
        this.hostnameVerifier = x509HostnameVerifier;
        this.nameResolver = null;
    }

    private SSLSocketFactory() {
        this(createDefaultSSLContext());
    }

    @Override // org.apache.http.conn.scheme.SocketFactory
    @Deprecated
    public Socket connectSocket(Socket socket, String str, int i2, InetAddress inetAddress, int i3, HttpParams httpParams) throws IOException {
        InetSocketAddress inetSocketAddress;
        InetAddress byName;
        if (inetAddress != null || i3 > 0) {
            if (i3 < 0) {
                i3 = 0;
            }
            inetSocketAddress = new InetSocketAddress(inetAddress, i3);
        } else {
            inetSocketAddress = null;
        }
        HostNameResolver hostNameResolver = this.nameResolver;
        if (hostNameResolver != null) {
            byName = hostNameResolver.resolve(str);
        } else {
            byName = InetAddress.getByName(str);
        }
        return connectSocket(socket, new InetSocketAddress(byName, i2), inetSocketAddress, httpParams);
    }
}
