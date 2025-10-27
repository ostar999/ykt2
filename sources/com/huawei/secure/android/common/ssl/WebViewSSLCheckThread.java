package com.huawei.secure.android.common.ssl;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.SslErrorHandler;
import com.huawei.secure.android.common.ssl.hostname.StrictHostnameVerifier;
import com.huawei.secure.android.common.ssl.util.f;
import com.huawei.secure.android.common.ssl.util.g;
import com.yikaobang.yixue.R2;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

/* loaded from: classes4.dex */
public class WebViewSSLCheckThread extends Thread {

    /* renamed from: i, reason: collision with root package name */
    private static final String f8357i = "WebViewSSLCheckThread";

    /* renamed from: a, reason: collision with root package name */
    private SSLSocketFactory f8358a;

    /* renamed from: b, reason: collision with root package name */
    private HostnameVerifier f8359b;

    /* renamed from: c, reason: collision with root package name */
    private org.apache.http.conn.ssl.SSLSocketFactory f8360c;

    /* renamed from: d, reason: collision with root package name */
    private X509HostnameVerifier f8361d;

    /* renamed from: e, reason: collision with root package name */
    private SslErrorHandler f8362e;

    /* renamed from: f, reason: collision with root package name */
    private String f8363f;

    /* renamed from: g, reason: collision with root package name */
    private Callback f8364g;

    /* renamed from: h, reason: collision with root package name */
    private Context f8365h;

    public interface Callback {
        void onCancel(Context context, String str);

        void onProceed(Context context, String str);
    }

    public static class a implements okhttp3.Callback {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Callback f8366a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ Context f8367b;

        /* renamed from: c, reason: collision with root package name */
        final /* synthetic */ String f8368c;

        /* renamed from: d, reason: collision with root package name */
        final /* synthetic */ SslErrorHandler f8369d;

        public a(Callback callback, Context context, String str, SslErrorHandler sslErrorHandler) {
            this.f8366a = callback;
            this.f8367b = context;
            this.f8368c = str;
            this.f8369d = sslErrorHandler;
        }

        @Override // okhttp3.Callback
        public void onFailure(Call call, IOException iOException) {
            g.b(WebViewSSLCheckThread.f8357i, "onFailure , IO Exception : " + iOException.getMessage());
            Callback callback = this.f8366a;
            if (callback != null) {
                callback.onCancel(this.f8367b, this.f8368c);
            } else {
                this.f8369d.cancel();
            }
        }

        @Override // okhttp3.Callback
        public void onResponse(Call call, Response response) throws IOException {
            g.b(WebViewSSLCheckThread.f8357i, "onResponse . proceed");
            Callback callback = this.f8366a;
            if (callback != null) {
                callback.onProceed(this.f8367b, this.f8368c);
            } else {
                this.f8369d.proceed();
            }
        }
    }

    public WebViewSSLCheckThread() {
    }

    private void b() {
        String str = f8357i;
        g.c(str, "callbackCancel: ");
        Callback callback = this.f8364g;
        if (callback != null) {
            callback.onCancel(this.f8365h, this.f8363f);
        } else if (this.f8362e != null) {
            g.c(str, "callbackCancel 2: ");
            this.f8362e.cancel();
        }
    }

    private void c() {
        g.c(f8357i, "callbackProceed: ");
        Callback callback = this.f8364g;
        if (callback != null) {
            callback.onProceed(this.f8365h, this.f8363f);
            return;
        }
        SslErrorHandler sslErrorHandler = this.f8362e;
        if (sslErrorHandler != null) {
            sslErrorHandler.proceed();
        }
    }

    public static void checkServerCertificateWithOK(SslErrorHandler sslErrorHandler, String str, Context context) {
        checkServerCertificateWithOK(sslErrorHandler, str, context, null);
    }

    public X509HostnameVerifier getApacheHostnameVerifier() {
        return this.f8361d;
    }

    public org.apache.http.conn.ssl.SSLSocketFactory getApacheSSLSocketFactory() {
        return this.f8360c;
    }

    public Callback getCallback() {
        return this.f8364g;
    }

    public Context getContext() {
        return this.f8365h;
    }

    public HostnameVerifier getHostnameVerifier() {
        return this.f8359b;
    }

    public SslErrorHandler getSslErrorHandler() {
        return this.f8362e;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return this.f8358a;
    }

    public String getUrl() {
        return this.f8363f;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [javax.net.ssl.HostnameVerifier] */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r0v5 */
    @Override // java.lang.Thread, java.lang.Runnable
    public void run() throws Throwable {
        Exception e2;
        HttpsURLConnection httpsURLConnection;
        super.run();
        HttpsURLConnection httpsURLConnection2 = null;
        if (this.f8360c != null && this.f8361d != null) {
            if (this.f8362e != null) {
                try {
                    if (!TextUtils.isEmpty(this.f8363f)) {
                        try {
                            this.f8360c.setHostnameVerifier(this.f8361d);
                            org.apache.http.conn.ssl.SSLSocketFactory sSLSocketFactory = this.f8360c;
                            if (sSLSocketFactory instanceof SecureApacheSSLSocketFactory) {
                                ((SecureApacheSSLSocketFactory) sSLSocketFactory).setContext(this.f8365h);
                            }
                            BasicHttpParams basicHttpParams = new BasicHttpParams();
                            HttpConnectionParams.setConnectionTimeout(basicHttpParams, 30000);
                            HttpConnectionParams.setSoTimeout(basicHttpParams, 30000);
                            SchemeRegistry schemeRegistry = new SchemeRegistry();
                            schemeRegistry.register(new Scheme("https", this.f8360c, R2.attr.banner_indicator_selected_color));
                            schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
                            DefaultHttpClient defaultHttpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry), basicHttpParams);
                            HttpGet httpGet = new HttpGet();
                            httpGet.setURI(new URI(this.f8363f));
                            HttpResponse httpResponseExecute = defaultHttpClient.execute(httpGet);
                            g.c(f8357i, "status code is : " + httpResponseExecute.getStatusLine().getStatusCode());
                            f.a((Reader) null);
                            c();
                            return;
                        } catch (Exception e3) {
                            g.b(f8357i, "run: exception : " + e3.getMessage());
                            b();
                            f.a((Reader) null);
                            return;
                        }
                    }
                } catch (Throwable th) {
                    f.a((Reader) null);
                    throw th;
                }
            }
            g.b(f8357i, "sslErrorHandler or url is null");
            b();
            return;
        }
        if (this.f8358a != null) {
            ?? r02 = this.f8359b;
            try {
                if (r02 != 0) {
                    try {
                        URLConnection uRLConnectionOpenConnection = new URL(this.f8363f).openConnection();
                        if (uRLConnectionOpenConnection instanceof HttpsURLConnection) {
                            httpsURLConnection = (HttpsURLConnection) uRLConnectionOpenConnection;
                            try {
                                httpsURLConnection.setSSLSocketFactory(this.f8358a);
                                httpsURLConnection.setHostnameVerifier(this.f8359b);
                                httpsURLConnection.setRequestMethod("GET");
                                httpsURLConnection.setConnectTimeout(10000);
                                httpsURLConnection.setReadTimeout(20000);
                                httpsURLConnection.connect();
                                httpsURLConnection2 = httpsURLConnection;
                            } catch (Exception e4) {
                                e2 = e4;
                                g.b(f8357i, "exception : " + e2.getMessage());
                                b();
                                if (httpsURLConnection != null) {
                                    httpsURLConnection.disconnect();
                                    return;
                                }
                                return;
                            }
                        }
                        if (httpsURLConnection2 != null) {
                            httpsURLConnection2.disconnect();
                        }
                        c();
                        return;
                    } catch (Exception e5) {
                        e2 = e5;
                        httpsURLConnection = null;
                    } catch (Throwable th2) {
                        th = th2;
                        r02 = 0;
                        if (r02 != 0) {
                            r02.disconnect();
                        }
                        throw th;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
            }
        }
        b();
    }

    public void setApacheHostnameVerifier(X509HostnameVerifier x509HostnameVerifier) {
        this.f8361d = x509HostnameVerifier;
    }

    public void setApacheSSLSocketFactory(org.apache.http.conn.ssl.SSLSocketFactory sSLSocketFactory) {
        this.f8360c = sSLSocketFactory;
    }

    public void setCallback(Callback callback) {
        this.f8364g = callback;
    }

    public void setContext(Context context) {
        this.f8365h = context;
    }

    public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.f8359b = hostnameVerifier;
    }

    public void setSslErrorHandler(SslErrorHandler sslErrorHandler) {
        this.f8362e = sslErrorHandler;
    }

    public void setSslSocketFactory(SSLSocketFactory sSLSocketFactory) {
        this.f8358a = sSLSocketFactory;
    }

    public void setUrl(String str) {
        this.f8363f = str;
    }

    public WebViewSSLCheckThread(SslErrorHandler sslErrorHandler, String str, Context context) throws IllegalAccessException, NoSuchAlgorithmException, IOException, CertificateException, KeyManagementException, KeyStoreException {
        setSslErrorHandler(sslErrorHandler);
        setUrl(str);
        setContext(context);
        setSslSocketFactory(new SecureSSLSocketFactoryNew(new c(context)));
        setHostnameVerifier(new StrictHostnameVerifier());
        try {
            setApacheSSLSocketFactory(new SecureApacheSSLSocketFactory((KeyStore) null, new c(context)));
        } catch (UnrecoverableKeyException e2) {
            g.b(f8357i, "WebViewSSLCheckThread: UnrecoverableKeyException : " + e2.getMessage());
        }
        setApacheHostnameVerifier(SecureApacheSSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
    }

    public static void checkServerCertificateWithOK(SslErrorHandler sslErrorHandler, String str, Context context, Callback callback) {
        if (sslErrorHandler == null || TextUtils.isEmpty(str) || context == null) {
            g.b(f8357i, "checkServerCertificateWithOK: handler or url or context is null");
            return;
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        try {
            SecureSSLSocketFactoryNew secureSSLSocketFactoryNew = new SecureSSLSocketFactoryNew(new c(context));
            secureSSLSocketFactoryNew.setContext(context);
            builder.sslSocketFactory(secureSSLSocketFactoryNew, new c(context));
            builder.hostnameVerifier(new StrictHostnameVerifier());
            builder.build().newCall(new Request.Builder().url(str).build()).enqueue(new a(callback, context, str, sslErrorHandler));
        } catch (Exception e2) {
            g.b(f8357i, "checkServerCertificateWithOK: exception : " + e2.getMessage());
            sslErrorHandler.cancel();
        }
    }

    @Deprecated
    public WebViewSSLCheckThread(SslErrorHandler sslErrorHandler, String str, SSLSocketFactory sSLSocketFactory, HostnameVerifier hostnameVerifier) {
        setSslErrorHandler(sslErrorHandler);
        setUrl(str);
        setSslSocketFactory(sSLSocketFactory);
        setHostnameVerifier(hostnameVerifier);
    }

    @Deprecated
    public WebViewSSLCheckThread(SslErrorHandler sslErrorHandler, String str, org.apache.http.conn.ssl.SSLSocketFactory sSLSocketFactory, X509HostnameVerifier x509HostnameVerifier) {
        setSslErrorHandler(sslErrorHandler);
        setUrl(str);
        setApacheSSLSocketFactory(sSLSocketFactory);
        setApacheHostnameVerifier(x509HostnameVerifier);
    }

    @Deprecated
    public WebViewSSLCheckThread(SslErrorHandler sslErrorHandler, String str, org.apache.http.conn.ssl.SSLSocketFactory sSLSocketFactory, X509HostnameVerifier x509HostnameVerifier, Callback callback, Context context) {
        this.f8362e = sslErrorHandler;
        this.f8363f = str;
        this.f8360c = sSLSocketFactory;
        this.f8361d = x509HostnameVerifier;
        this.f8364g = callback;
        this.f8365h = context;
    }
}
