package com.alipay.android.phone.mrpc.core;

import android.net.SSLCertificateSocketFactory;
import android.util.Base64;
import android.util.Log;
import com.psychiatrygarden.utils.MimeTypes;
import com.yikaobang.yixue.R2;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.security.Security;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.net.ssl.HttpsURLConnection;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
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
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.RequestWrapper;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HttpContext;
import org.eclipse.jetty.http.HttpHeaderValues;

/* loaded from: classes2.dex */
public final class b implements HttpClient {

    /* renamed from: a, reason: collision with root package name */
    public static long f2939a = 160;

    /* renamed from: b, reason: collision with root package name */
    private static String[] f2940b = {"text/", "application/xml", MimeTypes.APP_JSON};

    /* renamed from: c, reason: collision with root package name */
    private static final HttpRequestInterceptor f2941c = new c();

    /* renamed from: d, reason: collision with root package name */
    private final HttpClient f2942d;

    /* renamed from: e, reason: collision with root package name */
    private RuntimeException f2943e = new IllegalStateException("AndroidHttpClient created and never closed");

    /* renamed from: f, reason: collision with root package name */
    private volatile C0025b f2944f;

    public class a implements HttpRequestInterceptor {
        private a() {
        }

        public /* synthetic */ a(b bVar, byte b3) {
            this();
        }

        @Override // org.apache.http.HttpRequestInterceptor
        public final void process(HttpRequest httpRequest, HttpContext httpContext) {
            C0025b c0025b = b.this.f2944f;
            if (c0025b != null && C0025b.a(c0025b) && (httpRequest instanceof HttpUriRequest)) {
                C0025b.a(c0025b, b.a((HttpUriRequest) httpRequest));
            }
        }
    }

    /* renamed from: com.alipay.android.phone.mrpc.core.b$b, reason: collision with other inner class name */
    public static class C0025b {

        /* renamed from: a, reason: collision with root package name */
        private final String f2946a;

        /* renamed from: b, reason: collision with root package name */
        private final int f2947b;

        public static /* synthetic */ void a(C0025b c0025b, String str) {
            Log.println(c0025b.f2947b, c0025b.f2946a, str);
        }

        public static /* synthetic */ boolean a(C0025b c0025b) {
            return Log.isLoggable(c0025b.f2946a, c0025b.f2947b);
        }
    }

    private b(ClientConnectionManager clientConnectionManager, HttpParams httpParams) {
        this.f2942d = new d(this, clientConnectionManager, httpParams);
    }

    public static b a(String str) {
        BasicHttpParams basicHttpParams = new BasicHttpParams();
        HttpProtocolParams.setVersion(basicHttpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setUseExpectContinue(basicHttpParams, false);
        HttpConnectionParams.setStaleCheckingEnabled(basicHttpParams, true);
        HttpConnectionParams.setConnectionTimeout(basicHttpParams, 20000);
        HttpConnectionParams.setSoTimeout(basicHttpParams, 30000);
        HttpConnectionParams.setSocketBufferSize(basicHttpParams, 8192);
        HttpClientParams.setRedirecting(basicHttpParams, true);
        HttpClientParams.setAuthenticating(basicHttpParams, false);
        HttpProtocolParams.setUserAgent(basicHttpParams, str);
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", SSLCertificateSocketFactory.getHttpSocketFactory(30000, null), R2.attr.banner_indicator_selected_color));
        ThreadSafeClientConnManager threadSafeClientConnManager = new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry);
        ConnManagerParams.setTimeout(basicHttpParams, 60000L);
        ConnManagerParams.setMaxConnectionsPerRoute(basicHttpParams, new ConnPerRouteBean(10));
        ConnManagerParams.setMaxTotalConnections(basicHttpParams, 50);
        Security.setProperty("networkaddress.cache.ttl", "-1");
        HttpsURLConnection.setDefaultHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
        return new b(threadSafeClientConnManager, basicHttpParams);
    }

    public static InputStream a(HttpEntity httpEntity) throws IllegalStateException, IOException {
        Header contentEncoding;
        String value;
        InputStream content = httpEntity.getContent();
        return (content == null || (contentEncoding = httpEntity.getContentEncoding()) == null || (value = contentEncoding.getValue()) == null || !value.contains(HttpHeaderValues.GZIP)) ? content : new GZIPInputStream(content);
    }

    public static /* synthetic */ String a(HttpUriRequest httpUriRequest) throws IOException {
        HttpEntity entity;
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("curl ");
        for (Header header : httpUriRequest.getAllHeaders()) {
            if (!header.getName().equals("Authorization") && !header.getName().equals("Cookie")) {
                sb.append("--header \"");
                sb.append(header.toString().trim());
                sb.append("\" ");
            }
        }
        URI uri = httpUriRequest.getURI();
        if (httpUriRequest instanceof RequestWrapper) {
            HttpRequest original = ((RequestWrapper) httpUriRequest).getOriginal();
            if (original instanceof HttpUriRequest) {
                uri = ((HttpUriRequest) original).getURI();
            }
        }
        sb.append("\"");
        sb.append(uri);
        sb.append("\"");
        if ((httpUriRequest instanceof HttpEntityEnclosingRequest) && (entity = ((HttpEntityEnclosingRequest) httpUriRequest).getEntity()) != null && entity.isRepeatable()) {
            if (entity.getContentLength() < 1024) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                entity.writeTo(byteArrayOutputStream);
                if (b(httpUriRequest)) {
                    sb.insert(0, "echo '" + Base64.encodeToString(byteArrayOutputStream.toByteArray(), 2) + "' | base64 -d > /tmp/$$.bin; ");
                    str = " --data-binary @/tmp/$$.bin";
                } else {
                    String string = byteArrayOutputStream.toString();
                    sb.append(" --data-ascii \"");
                    sb.append(string);
                    sb.append("\"");
                }
            } else {
                str = " [TOO MUCH DATA TO INCLUDE]";
            }
            sb.append(str);
        }
        return sb.toString();
    }

    public static AbstractHttpEntity a(byte[] bArr) throws IOException {
        if (bArr.length < f2939a) {
            return new ByteArrayEntity(bArr);
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        gZIPOutputStream.write(bArr);
        gZIPOutputStream.close();
        ByteArrayEntity byteArrayEntity = new ByteArrayEntity(byteArrayOutputStream.toByteArray());
        byteArrayEntity.setContentEncoding(HttpHeaderValues.GZIP);
        byteArrayEntity.getContentLength();
        return byteArrayEntity;
    }

    public static void a(HttpRequest httpRequest) {
        httpRequest.addHeader("Accept-Encoding", HttpHeaderValues.GZIP);
    }

    public static long b(String str) {
        return k.a(str);
    }

    public static void b(HttpRequest httpRequest) {
        httpRequest.addHeader("Connection", "Keep-Alive");
    }

    private static boolean b(HttpUriRequest httpUriRequest) {
        Header[] headers = httpUriRequest.getHeaders("content-encoding");
        if (headers != null) {
            for (Header header : headers) {
                if (HttpHeaderValues.GZIP.equalsIgnoreCase(header.getValue())) {
                    return true;
                }
            }
        }
        Header[] headers2 = httpUriRequest.getHeaders(com.alipay.sdk.packet.d.f3288d);
        if (headers2 != null) {
            for (Header header2 : headers2) {
                for (String str : f2940b) {
                    if (header2.getValue().startsWith(str)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public final void a(HttpRequestRetryHandler httpRequestRetryHandler) {
        ((DefaultHttpClient) this.f2942d).setHttpRequestRetryHandler(httpRequestRetryHandler);
    }

    @Override // org.apache.http.client.HttpClient
    public final <T> T execute(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler) {
        return (T) this.f2942d.execute(httpHost, httpRequest, responseHandler);
    }

    @Override // org.apache.http.client.HttpClient
    public final <T> T execute(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler, HttpContext httpContext) {
        return (T) this.f2942d.execute(httpHost, httpRequest, responseHandler, httpContext);
    }

    @Override // org.apache.http.client.HttpClient
    public final <T> T execute(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler) {
        return (T) this.f2942d.execute(httpUriRequest, responseHandler);
    }

    @Override // org.apache.http.client.HttpClient
    public final <T> T execute(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler, HttpContext httpContext) {
        return (T) this.f2942d.execute(httpUriRequest, responseHandler, httpContext);
    }

    @Override // org.apache.http.client.HttpClient
    public final HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest) {
        return this.f2942d.execute(httpHost, httpRequest);
    }

    @Override // org.apache.http.client.HttpClient
    public final HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) {
        return this.f2942d.execute(httpHost, httpRequest, httpContext);
    }

    @Override // org.apache.http.client.HttpClient
    public final HttpResponse execute(HttpUriRequest httpUriRequest) {
        return this.f2942d.execute(httpUriRequest);
    }

    @Override // org.apache.http.client.HttpClient
    public final HttpResponse execute(HttpUriRequest httpUriRequest, HttpContext httpContext) {
        return this.f2942d.execute(httpUriRequest, httpContext);
    }

    @Override // org.apache.http.client.HttpClient
    public final ClientConnectionManager getConnectionManager() {
        return this.f2942d.getConnectionManager();
    }

    @Override // org.apache.http.client.HttpClient
    public final HttpParams getParams() {
        return this.f2942d.getParams();
    }
}
