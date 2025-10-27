package net.tsz.afinal;

import com.yikaobang.yixue.R2;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.GZIPInputStream;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import net.tsz.afinal.http.HttpHandler;
import net.tsz.afinal.http.RetryHandler;
import net.tsz.afinal.http.SyncRequestHandler;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.ParseException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.SyncBasicHttpContext;

/* loaded from: classes9.dex */
public class FinalHttp {
    private static final int DEFAULT_SOCKET_BUFFER_SIZE = 8192;
    private static final String ENCODING_GZIP = "gzip";
    private static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    private static final Executor executor;
    private static int httpThreadCount = 3;
    private static int maxConnections = 10;
    private static int maxRetries = 0;
    private static final ThreadFactory sThreadFactory;
    private static int socketTimeout = 60000;
    private String charset = "utf-8";
    private final Map<String, String> clientHeaderMap;
    private final DefaultHttpClient httpClient;
    private final HttpContext httpContext;

    public static class InflatingEntity extends HttpEntityWrapper {
        public InflatingEntity(HttpEntity httpEntity) {
            super(httpEntity);
        }

        @Override // org.apache.http.entity.HttpEntityWrapper, org.apache.http.HttpEntity
        public InputStream getContent() throws IOException {
            return new GZIPInputStream(this.wrappedEntity.getContent());
        }

        @Override // org.apache.http.entity.HttpEntityWrapper, org.apache.http.HttpEntity
        public long getContentLength() {
            return -1L;
        }
    }

    static {
        ThreadFactory threadFactory = new ThreadFactory() { // from class: net.tsz.afinal.FinalHttp.1
            private final AtomicInteger mCount = new AtomicInteger(1);

            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable, "FinalHttp #" + this.mCount.getAndIncrement());
                thread.setPriority(4);
                return thread;
            }
        };
        sThreadFactory = threadFactory;
        executor = Executors.newFixedThreadPool(httpThreadCount, threadFactory);
    }

    public FinalHttp() throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
        BasicHttpParams basicHttpParams = new BasicHttpParams();
        ConnManagerParams.setTimeout(basicHttpParams, socketTimeout);
        ConnManagerParams.setMaxConnectionsPerRoute(basicHttpParams, new ConnPerRouteBean(maxConnections));
        ConnManagerParams.setMaxTotalConnections(basicHttpParams, 10);
        HttpConnectionParams.setSoTimeout(basicHttpParams, socketTimeout);
        HttpConnectionParams.setConnectionTimeout(basicHttpParams, socketTimeout);
        HttpConnectionParams.setTcpNoDelay(basicHttpParams, true);
        HttpConnectionParams.setSocketBufferSize(basicHttpParams, 8192);
        HttpProtocolParams.setVersion(basicHttpParams, HttpVersion.HTTP_1_1);
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            SSLSocketFactoryEx sSLSocketFactoryEx = new SSLSocketFactoryEx(keyStore);
            sSLSocketFactoryEx.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            schemeRegistry.register(new Scheme("https", sSLSocketFactoryEx, R2.attr.banner_indicator_selected_color));
        } catch (Exception unused) {
            schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), R2.attr.banner_indicator_selected_color));
        }
        ThreadSafeClientConnManager threadSafeClientConnManager = new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry);
        this.httpContext = new SyncBasicHttpContext(new BasicHttpContext());
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient(threadSafeClientConnManager, basicHttpParams);
        this.httpClient = defaultHttpClient;
        defaultHttpClient.addRequestInterceptor(new HttpRequestInterceptor() { // from class: net.tsz.afinal.FinalHttp.2
            @Override // org.apache.http.HttpRequestInterceptor
            public void process(HttpRequest httpRequest, HttpContext httpContext) {
                if (!httpRequest.containsHeader("Accept-Encoding")) {
                    httpRequest.addHeader("Accept-Encoding", "gzip");
                }
                for (String str : FinalHttp.this.clientHeaderMap.keySet()) {
                    httpRequest.addHeader(str, (String) FinalHttp.this.clientHeaderMap.get(str));
                }
            }
        });
        defaultHttpClient.addResponseInterceptor(new HttpResponseInterceptor() { // from class: net.tsz.afinal.FinalHttp.3
            @Override // org.apache.http.HttpResponseInterceptor
            public void process(HttpResponse httpResponse, HttpContext httpContext) throws ParseException {
                Header contentEncoding;
                HttpEntity entity = httpResponse.getEntity();
                if (entity == null || (contentEncoding = entity.getContentEncoding()) == null) {
                    return;
                }
                for (HeaderElement headerElement : contentEncoding.getElements()) {
                    if (headerElement.getName().equalsIgnoreCase("gzip")) {
                        httpResponse.setEntity(new InflatingEntity(httpResponse.getEntity()));
                        return;
                    }
                }
            }
        });
        defaultHttpClient.setHttpRequestRetryHandler(new RetryHandler(maxRetries));
        this.clientHeaderMap = new HashMap();
    }

    private HttpEntityEnclosingRequestBase addEntityToRequestBase(HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase, HttpEntity httpEntity) {
        if (httpEntity != null) {
            httpEntityEnclosingRequestBase.setEntity(httpEntity);
        }
        return httpEntityEnclosingRequestBase;
    }

    public static String getUrlWithQueryString(String str, AjaxParams ajaxParams) {
        if (ajaxParams == null) {
            return str;
        }
        return str + "?" + ajaxParams.getParamString();
    }

    private HttpEntity paramsToEntity(AjaxParams ajaxParams) {
        if (ajaxParams != null) {
            return ajaxParams.getEntity();
        }
        return null;
    }

    public void addHeader(String str, String str2) {
        this.clientHeaderMap.put(str, str2);
    }

    public void configCharset(String str) {
        if (str == null || str.trim().length() == 0) {
            return;
        }
        this.charset = str;
    }

    public void configCookieStore(CookieStore cookieStore) {
        this.httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
    }

    public void configRequestExecutionRetryCount(int i2) {
        this.httpClient.setHttpRequestRetryHandler(new RetryHandler(i2));
    }

    public void configSSLSocketFactory(SSLSocketFactory sSLSocketFactory) {
        Scheme scheme = new Scheme("https", sSLSocketFactory, R2.attr.banner_indicator_selected_color);
        this.httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        this.httpClient.getConnectionManager().getSchemeRegistry().register(scheme);
    }

    public void configTimeout(int i2) {
        HttpParams params = this.httpClient.getParams();
        ConnManagerParams.setTimeout(params, i2);
        HttpConnectionParams.setSoTimeout(params, i2);
        HttpConnectionParams.setConnectionTimeout(params, i2);
    }

    public void configUserAgent(String str) {
        HttpProtocolParams.setUserAgent(this.httpClient.getParams(), str);
    }

    public void delete(String str, AjaxCallBack<? extends Object> ajaxCallBack) {
        sendRequest(this.httpClient, this.httpContext, new HttpDelete(str), null, ajaxCallBack);
    }

    public Object deleteSync(String str) {
        return deleteSync(str, null);
    }

    public HttpHandler<File> download(String str, String str2, AjaxCallBack<File> ajaxCallBack) {
        return download(str, null, str2, false, ajaxCallBack);
    }

    public HttpHandler<File> download2(String str, AjaxParams ajaxParams, Header[] headerArr, String str2, boolean z2, AjaxCallBack<File> ajaxCallBack) {
        HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBaseAddEntityToRequestBase = addEntityToRequestBase(new HttpPost(str), paramsToEntity(ajaxParams));
        if (headerArr != null) {
            httpEntityEnclosingRequestBaseAddEntityToRequestBase.setHeaders(headerArr);
        }
        HttpHandler<File> httpHandler = new HttpHandler<>(this.httpClient, this.httpContext, ajaxCallBack, this.charset);
        httpHandler.executeOnExecutor(executor, httpEntityEnclosingRequestBaseAddEntityToRequestBase, str2, Boolean.valueOf(z2));
        return httpHandler;
    }

    public void get(String str, AjaxCallBack<? extends Object> ajaxCallBack) {
        get(str, null, ajaxCallBack);
    }

    public HttpClient getHttpClient() {
        return this.httpClient;
    }

    public HttpContext getHttpContext() {
        return this.httpContext;
    }

    public Object getSync(String str) {
        return getSync(str, null);
    }

    public void post(String str, AjaxCallBack<? extends Object> ajaxCallBack) {
        post(str, null, ajaxCallBack);
    }

    public Object postSync(String str) {
        return postSync(str, null);
    }

    public void put(String str, AjaxCallBack<? extends Object> ajaxCallBack) {
        put(str, null, ajaxCallBack);
    }

    public Object putSync(String str) {
        return putSync(str, null);
    }

    public <T> void sendRequest(DefaultHttpClient defaultHttpClient, HttpContext httpContext, HttpUriRequest httpUriRequest, String str, AjaxCallBack<T> ajaxCallBack) {
        if (str != null) {
            httpUriRequest.addHeader("Content-Type", str);
        }
        try {
            new HttpHandler(defaultHttpClient, httpContext, ajaxCallBack, this.charset).executeOnExecutor(executor, httpUriRequest);
        } catch (Exception unused) {
        }
    }

    public Object sendSyncRequest(DefaultHttpClient defaultHttpClient, HttpContext httpContext, HttpUriRequest httpUriRequest, String str) {
        if (str != null) {
            httpUriRequest.addHeader("Content-Type", str);
        }
        return new SyncRequestHandler(defaultHttpClient, httpContext, this.charset).sendRequest(httpUriRequest);
    }

    public Object deleteSync(String str, Header[] headerArr) {
        HttpDelete httpDelete = new HttpDelete(str);
        if (headerArr != null) {
            httpDelete.setHeaders(headerArr);
        }
        return sendSyncRequest(this.httpClient, this.httpContext, httpDelete, null);
    }

    public HttpHandler<File> download(String str, String str2, boolean z2, AjaxCallBack<File> ajaxCallBack) {
        return download(str, null, str2, z2, ajaxCallBack);
    }

    public void get(String str, AjaxParams ajaxParams, AjaxCallBack<? extends Object> ajaxCallBack) {
        sendRequest(this.httpClient, this.httpContext, new HttpGet(getUrlWithQueryString(str, ajaxParams)), null, ajaxCallBack);
    }

    public Object getSync(String str, AjaxParams ajaxParams) {
        return sendSyncRequest(this.httpClient, this.httpContext, new HttpGet(getUrlWithQueryString(str, ajaxParams)), null);
    }

    public void post(String str, AjaxParams ajaxParams, AjaxCallBack<? extends Object> ajaxCallBack) {
        post(str, paramsToEntity(ajaxParams), null, ajaxCallBack);
    }

    public Object postSync(String str, AjaxParams ajaxParams) {
        return postSync(str, paramsToEntity(ajaxParams), null);
    }

    public void put(String str, AjaxParams ajaxParams, AjaxCallBack<? extends Object> ajaxCallBack) {
        put(str, paramsToEntity(ajaxParams), null, ajaxCallBack);
    }

    public Object putSync(String str, AjaxParams ajaxParams) {
        return putSync(str, paramsToEntity(ajaxParams), null);
    }

    public void delete(String str, Header[] headerArr, AjaxCallBack<? extends Object> ajaxCallBack) {
        HttpDelete httpDelete = new HttpDelete(str);
        if (headerArr != null) {
            httpDelete.setHeaders(headerArr);
        }
        sendRequest(this.httpClient, this.httpContext, httpDelete, null, ajaxCallBack);
    }

    public HttpHandler<File> download(String str, AjaxParams ajaxParams, String str2, AjaxCallBack<File> ajaxCallBack) {
        return download(str, ajaxParams, str2, false, ajaxCallBack);
    }

    public void get(String str, Header[] headerArr, String str2, AjaxParams ajaxParams, AjaxCallBack<? extends Object> ajaxCallBack) {
        HttpGet httpGet = new HttpGet(getUrlWithQueryString(str, ajaxParams));
        if (headerArr != null) {
            httpGet.setHeaders(headerArr);
        }
        sendRequest(this.httpClient, this.httpContext, httpGet, str2, ajaxCallBack);
    }

    public void post(String str, Header[] headerArr, String str2, AjaxParams ajaxParams, AjaxCallBack<? extends Object> ajaxCallBack) {
        post(str, headerArr, paramsToEntity(ajaxParams), str2, ajaxCallBack);
    }

    public Object postSync(String str, HttpEntity httpEntity, String str2) {
        return sendSyncRequest(this.httpClient, this.httpContext, addEntityToRequestBase(new HttpPost(str), httpEntity), str2);
    }

    public void put(String str, HttpEntity httpEntity, String str2, AjaxCallBack<? extends Object> ajaxCallBack) {
        sendRequest(this.httpClient, this.httpContext, addEntityToRequestBase(new HttpPut(str), httpEntity), str2, ajaxCallBack);
    }

    public Object putSync(String str, HttpEntity httpEntity, String str2) {
        return putSync(str, null, httpEntity, str2);
    }

    public HttpHandler<File> download(String str, AjaxParams ajaxParams, String str2, boolean z2, AjaxCallBack<File> ajaxCallBack) {
        HttpGet httpGet = new HttpGet(getUrlWithQueryString(str, ajaxParams));
        HttpHandler<File> httpHandler = new HttpHandler<>(this.httpClient, this.httpContext, ajaxCallBack, this.charset);
        httpHandler.executeOnExecutor(executor, httpGet, str2, Boolean.valueOf(z2));
        return httpHandler;
    }

    public Object getSync(String str, Header[] headerArr, AjaxParams ajaxParams) {
        HttpGet httpGet = new HttpGet(getUrlWithQueryString(str, ajaxParams));
        if (headerArr != null) {
            httpGet.setHeaders(headerArr);
        }
        return sendSyncRequest(this.httpClient, this.httpContext, httpGet, null);
    }

    public void post(String str, HttpEntity httpEntity, String str2, AjaxCallBack<? extends Object> ajaxCallBack) {
        sendRequest(this.httpClient, this.httpContext, addEntityToRequestBase(new HttpPost(str), httpEntity), str2, ajaxCallBack);
    }

    public Object postSync(String str, Header[] headerArr, AjaxParams ajaxParams, String str2) {
        HttpPost httpPost = new HttpPost(str);
        if (ajaxParams != null) {
            httpPost.setEntity(paramsToEntity(ajaxParams));
        }
        if (headerArr != null) {
            httpPost.setHeaders(headerArr);
        }
        return sendSyncRequest(this.httpClient, this.httpContext, httpPost, str2);
    }

    public void put(String str, Header[] headerArr, HttpEntity httpEntity, String str2, AjaxCallBack<? extends Object> ajaxCallBack) {
        HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBaseAddEntityToRequestBase = addEntityToRequestBase(new HttpPut(str), httpEntity);
        if (headerArr != null) {
            httpEntityEnclosingRequestBaseAddEntityToRequestBase.setHeaders(headerArr);
        }
        sendRequest(this.httpClient, this.httpContext, httpEntityEnclosingRequestBaseAddEntityToRequestBase, str2, ajaxCallBack);
    }

    public Object putSync(String str, Header[] headerArr, HttpEntity httpEntity, String str2) {
        HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBaseAddEntityToRequestBase = addEntityToRequestBase(new HttpPut(str), httpEntity);
        if (headerArr != null) {
            httpEntityEnclosingRequestBaseAddEntityToRequestBase.setHeaders(headerArr);
        }
        return sendSyncRequest(this.httpClient, this.httpContext, httpEntityEnclosingRequestBaseAddEntityToRequestBase, str2);
    }

    public <T> void post(String str, Header[] headerArr, AjaxParams ajaxParams, String str2, AjaxCallBack<T> ajaxCallBack) {
        HttpPost httpPost = new HttpPost(str);
        if (ajaxParams != null) {
            httpPost.setEntity(paramsToEntity(ajaxParams));
        }
        if (headerArr != null) {
            httpPost.setHeaders(headerArr);
        }
        sendRequest(this.httpClient, this.httpContext, httpPost, str2, ajaxCallBack);
    }

    public void get(String str, Header[] headerArr, AjaxParams ajaxParams, AjaxCallBack<? extends Object> ajaxCallBack) {
        HttpGet httpGet = new HttpGet(getUrlWithQueryString(str, ajaxParams));
        if (headerArr != null) {
            httpGet.setHeaders(headerArr);
        }
        try {
            sendRequest(this.httpClient, this.httpContext, httpGet, null, ajaxCallBack);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public Object postSync(String str, Header[] headerArr, HttpEntity httpEntity, String str2) {
        HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBaseAddEntityToRequestBase = addEntityToRequestBase(new HttpPost(str), httpEntity);
        if (headerArr != null) {
            httpEntityEnclosingRequestBaseAddEntityToRequestBase.setHeaders(headerArr);
        }
        return sendSyncRequest(this.httpClient, this.httpContext, httpEntityEnclosingRequestBaseAddEntityToRequestBase, str2);
    }

    public void post(String str, Header[] headerArr, HttpEntity httpEntity, String str2, AjaxCallBack<? extends Object> ajaxCallBack) {
        HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBaseAddEntityToRequestBase = addEntityToRequestBase(new HttpPost(str), httpEntity);
        if (headerArr != null) {
            httpEntityEnclosingRequestBaseAddEntityToRequestBase.setHeaders(headerArr);
        }
        sendRequest(this.httpClient, this.httpContext, httpEntityEnclosingRequestBaseAddEntityToRequestBase, str2, ajaxCallBack);
    }
}
