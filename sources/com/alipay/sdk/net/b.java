package com.alipay.sdk.net;

import com.yikaobang.yixue.R2;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HttpContext;

/* loaded from: classes2.dex */
public final class b {

    /* renamed from: a, reason: collision with root package name */
    public static final String f3278a = "msp";

    /* renamed from: b, reason: collision with root package name */
    static b f3279b;

    /* renamed from: c, reason: collision with root package name */
    final DefaultHttpClient f3280c;

    private b(HttpParams httpParams) {
        this.f3280c = new DefaultHttpClient(httpParams);
    }

    public static b a() {
        if (f3279b == null) {
            BasicHttpParams basicHttpParams = new BasicHttpParams();
            HttpProtocolParams.setVersion(basicHttpParams, HttpVersion.HTTP_1_1);
            HttpConnectionParams.setStaleCheckingEnabled(basicHttpParams, true);
            basicHttpParams.setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
            ConnManagerParams.setMaxTotalConnections(basicHttpParams, 50);
            ConnManagerParams.setMaxConnectionsPerRoute(basicHttpParams, new ConnPerRouteBean(30));
            ConnManagerParams.setTimeout(basicHttpParams, 1000L);
            HttpConnectionParams.setConnectionTimeout(basicHttpParams, 20000);
            HttpConnectionParams.setSoTimeout(basicHttpParams, 30000);
            HttpConnectionParams.setSocketBufferSize(basicHttpParams, 16384);
            HttpProtocolParams.setUseExpectContinue(basicHttpParams, false);
            HttpClientParams.setRedirecting(basicHttpParams, true);
            HttpClientParams.setAuthenticating(basicHttpParams, false);
            HttpProtocolParams.setUserAgent(basicHttpParams, f3278a);
            try {
                SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
                socketFactory.setHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
                Scheme scheme = new Scheme("https", socketFactory, R2.attr.banner_indicator_selected_color);
                Scheme scheme2 = new Scheme("http", PlainSocketFactory.getSocketFactory(), 80);
                SchemeRegistry schemeRegistry = new SchemeRegistry();
                schemeRegistry.register(scheme);
                schemeRegistry.register(scheme2);
                f3279b = new b(new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry), basicHttpParams);
            } catch (Exception unused) {
                f3279b = new b(basicHttpParams);
            }
        }
        return f3279b;
    }

    private static b b() {
        return f3279b;
    }

    private static void c() {
        f3279b = null;
    }

    private void d() {
        ClientConnectionManager connectionManager = this.f3280c.getConnectionManager();
        if (connectionManager != null) {
            connectionManager.closeExpiredConnections();
            connectionManager.closeIdleConnections(30L, TimeUnit.MINUTES);
        }
    }

    private void e() {
        ClientConnectionManager connectionManager = this.f3280c.getConnectionManager();
        if (connectionManager != null) {
            connectionManager.shutdown();
            f3279b = null;
        }
    }

    private HttpParams f() {
        return this.f3280c.getParams();
    }

    private ClientConnectionManager g() {
        return this.f3280c.getConnectionManager();
    }

    private b(ClientConnectionManager clientConnectionManager, HttpParams httpParams) {
        this.f3280c = new DefaultHttpClient(clientConnectionManager, httpParams);
    }

    public final HttpResponse a(HttpUriRequest httpUriRequest) throws Exception {
        try {
            return this.f3280c.execute(httpUriRequest);
        } catch (Exception e2) {
            throw e2;
        }
    }

    private HttpResponse a(HttpUriRequest httpUriRequest, HttpContext httpContext) throws Exception {
        try {
            return this.f3280c.execute(httpUriRequest, httpContext);
        } catch (Exception e2) {
            throw new Exception(e2);
        }
    }

    private HttpResponse a(HttpHost httpHost, HttpRequest httpRequest) throws Exception {
        try {
            return this.f3280c.execute(httpHost, httpRequest);
        } catch (Exception e2) {
            throw new Exception(e2);
        }
    }

    private HttpResponse a(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws Exception {
        try {
            return this.f3280c.execute(httpHost, httpRequest, httpContext);
        } catch (Exception e2) {
            throw new Exception(e2);
        }
    }

    private <T> T a(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler) throws Exception {
        try {
            return (T) this.f3280c.execute(httpUriRequest, responseHandler);
        } catch (Exception e2) {
            throw new Exception(e2);
        }
    }

    private <T> T a(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler, HttpContext httpContext) throws Exception {
        try {
            return (T) this.f3280c.execute(httpUriRequest, responseHandler, httpContext);
        } catch (Exception e2) {
            throw new Exception(e2);
        }
    }

    private <T> T a(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler) throws Exception {
        try {
            return (T) this.f3280c.execute(httpHost, httpRequest, responseHandler);
        } catch (Exception e2) {
            throw new Exception(e2);
        }
    }

    private <T> T a(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler, HttpContext httpContext) throws Exception {
        try {
            return (T) this.f3280c.execute(httpHost, httpRequest, responseHandler, httpContext);
        } catch (Exception e2) {
            throw new Exception(e2);
        }
    }
}
