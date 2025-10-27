package com.alipay.android.phone.mrpc.core;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.CookieManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.Callable;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.eclipse.jetty.http.HttpHeaderValues;

/* loaded from: classes2.dex */
public final class q implements Callable<u> {

    /* renamed from: e, reason: collision with root package name */
    private static final HttpRequestRetryHandler f2985e = new ad();

    /* renamed from: a, reason: collision with root package name */
    protected l f2986a;

    /* renamed from: b, reason: collision with root package name */
    protected Context f2987b;

    /* renamed from: c, reason: collision with root package name */
    protected o f2988c;

    /* renamed from: d, reason: collision with root package name */
    String f2989d;

    /* renamed from: f, reason: collision with root package name */
    private HttpUriRequest f2990f;

    /* renamed from: i, reason: collision with root package name */
    private CookieManager f2993i;

    /* renamed from: j, reason: collision with root package name */
    private AbstractHttpEntity f2994j;

    /* renamed from: k, reason: collision with root package name */
    private HttpHost f2995k;

    /* renamed from: l, reason: collision with root package name */
    private URL f2996l;

    /* renamed from: q, reason: collision with root package name */
    private String f3001q;

    /* renamed from: g, reason: collision with root package name */
    private HttpContext f2991g = new BasicHttpContext();

    /* renamed from: h, reason: collision with root package name */
    private CookieStore f2992h = new BasicCookieStore();

    /* renamed from: m, reason: collision with root package name */
    private int f2997m = 0;

    /* renamed from: n, reason: collision with root package name */
    private boolean f2998n = false;

    /* renamed from: o, reason: collision with root package name */
    private boolean f2999o = false;

    /* renamed from: p, reason: collision with root package name */
    private String f3000p = null;

    public q(l lVar, o oVar) {
        this.f2986a = lVar;
        this.f2987b = lVar.f2963a;
        this.f2988c = oVar;
    }

    private static long a(String[] strArr) {
        String str;
        for (int i2 = 0; i2 < strArr.length; i2++) {
            if (ClientCookie.MAX_AGE_ATTR.equalsIgnoreCase(strArr[i2]) && (str = strArr[i2 + 1]) != null) {
                try {
                    return Long.parseLong(str);
                } catch (Exception unused) {
                    continue;
                }
            }
        }
        return 0L;
    }

    private static HttpUrlHeader a(HttpResponse httpResponse) {
        HttpUrlHeader httpUrlHeader = new HttpUrlHeader();
        for (Header header : httpResponse.getAllHeaders()) {
            httpUrlHeader.setHead(header.getName(), header.getValue());
        }
        return httpUrlHeader;
    }

    private u a(HttpResponse httpResponse, int i2, String str) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        String str2;
        Thread.currentThread().getId();
        HttpEntity entity = httpResponse.getEntity();
        ByteArrayOutputStream byteArrayOutputStream2 = null;
        String str3 = null;
        if (entity == null || httpResponse.getStatusLine().getStatusCode() != 200) {
            if (entity != null) {
                return null;
            }
            httpResponse.getStatusLine().getStatusCode();
            return null;
        }
        Thread.currentThread().getId();
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
        } catch (Throwable th) {
            th = th;
        }
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            a(entity, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            this.f2999o = false;
            this.f2986a.c(System.currentTimeMillis() - jCurrentTimeMillis);
            this.f2986a.a(byteArray.length);
            p pVar = new p(a(httpResponse), i2, str, byteArray);
            long jB = b(httpResponse);
            Header contentType = httpResponse.getEntity().getContentType();
            if (contentType != null) {
                HashMap<String, String> mapA = a(contentType.getValue());
                String str4 = mapA.get("charset");
                str3 = mapA.get("Content-Type");
                str2 = str4;
            } else {
                str2 = null;
            }
            pVar.b(str3);
            pVar.a(str2);
            pVar.a(System.currentTimeMillis());
            pVar.b(jB);
            try {
                byteArrayOutputStream.close();
                return pVar;
            } catch (IOException e2) {
                throw new RuntimeException("ArrayOutputStream close error!", e2.getCause());
            }
        } catch (Throwable th2) {
            th = th2;
            byteArrayOutputStream2 = byteArrayOutputStream;
            if (byteArrayOutputStream2 != null) {
                try {
                    byteArrayOutputStream2.close();
                } catch (IOException e3) {
                    throw new RuntimeException("ArrayOutputStream close error!", e3.getCause());
                }
            }
            throw th;
        }
    }

    private static HashMap<String, String> a(String str) {
        HashMap<String, String> map = new HashMap<>();
        for (String str2 : str.split(com.alipay.sdk.util.h.f3376b)) {
            String[] strArrSplit = str2.indexOf(61) == -1 ? new String[]{"Content-Type", str2} : str2.split("=");
            map.put(strArrSplit[0], strArrSplit[1]);
        }
        return map;
    }

    private void a(HttpEntity httpEntity, OutputStream outputStream) throws IllegalStateException, IOException {
        InputStream inputStreamA = b.a(httpEntity);
        long contentLength = httpEntity.getContentLength();
        try {
            try {
                byte[] bArr = new byte[2048];
                while (true) {
                    int i2 = inputStreamA.read(bArr);
                    if (i2 == -1 || this.f2988c.h()) {
                        break;
                    }
                    outputStream.write(bArr, 0, i2);
                    if (this.f2988c.f() != null && contentLength > 0) {
                        this.f2988c.f();
                    }
                }
                outputStream.flush();
            } catch (Exception e2) {
                e2.getCause();
                throw new IOException("HttpWorker Request Error!" + e2.getLocalizedMessage());
            }
        } finally {
            r.a(inputStreamA);
        }
    }

    private static long b(HttpResponse httpResponse) {
        Header firstHeader = httpResponse.getFirstHeader("Cache-Control");
        if (firstHeader != null) {
            String[] strArrSplit = firstHeader.getValue().split("=");
            if (strArrSplit.length >= 2) {
                try {
                    return a(strArrSplit);
                } catch (NumberFormatException unused) {
                }
            }
        }
        Header firstHeader2 = httpResponse.getFirstHeader("Expires");
        if (firstHeader2 != null) {
            return b.b(firstHeader2.getValue()) - System.currentTimeMillis();
        }
        return 0L;
    }

    private URI b() {
        String strA = this.f2988c.a();
        String str = this.f2989d;
        if (str != null) {
            strA = str;
        }
        if (strA != null) {
            return new URI(strA);
        }
        throw new RuntimeException("url should not be null");
    }

    private HttpUriRequest c() {
        HttpUriRequest httpUriRequest = this.f2990f;
        if (httpUriRequest != null) {
            return httpUriRequest;
        }
        if (this.f2994j == null) {
            byte[] bArrB = this.f2988c.b();
            String strB = this.f2988c.b(HttpHeaderValues.GZIP);
            if (bArrB != null) {
                if (TextUtils.equals(strB, k.a.f27523u)) {
                    this.f2994j = b.a(bArrB);
                } else {
                    this.f2994j = new ByteArrayEntity(bArrB);
                }
                this.f2994j.setContentType(this.f2988c.c());
            }
        }
        AbstractHttpEntity abstractHttpEntity = this.f2994j;
        if (abstractHttpEntity != null) {
            HttpPost httpPost = new HttpPost(b());
            httpPost.setEntity(abstractHttpEntity);
            this.f2990f = httpPost;
        } else {
            this.f2990f = new HttpGet(b());
        }
        return this.f2990f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00df  */
    @Override // java.util.concurrent.Callable
    /* renamed from: d, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.alipay.android.phone.mrpc.core.u call() throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 1062
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mrpc.core.q.call():com.alipay.android.phone.mrpc.core.u");
    }

    private void e() throws UnsupportedOperationException {
        HttpUriRequest httpUriRequest = this.f2990f;
        if (httpUriRequest != null) {
            httpUriRequest.abort();
        }
    }

    private String f() {
        if (!TextUtils.isEmpty(this.f3001q)) {
            return this.f3001q;
        }
        String strB = this.f2988c.b("operationType");
        this.f3001q = strB;
        return strB;
    }

    private int g() {
        URL urlH = h();
        return urlH.getPort() == -1 ? urlH.getDefaultPort() : urlH.getPort();
    }

    private URL h() {
        URL url = this.f2996l;
        if (url != null) {
            return url;
        }
        URL url2 = new URL(this.f2988c.a());
        this.f2996l = url2;
        return url2;
    }

    private CookieManager i() {
        CookieManager cookieManager = this.f2993i;
        if (cookieManager != null) {
            return cookieManager;
        }
        CookieManager cookieManager2 = CookieManager.getInstance();
        this.f2993i = cookieManager2;
        return cookieManager2;
    }

    public final o a() {
        return this.f2988c;
    }
}
