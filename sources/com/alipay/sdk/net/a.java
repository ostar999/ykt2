package com.alipay.sdk.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.text.TextUtils;
import java.net.URL;
import org.apache.http.HttpHost;

/* loaded from: classes2.dex */
public final class a {

    /* renamed from: a, reason: collision with root package name */
    public static final String f3275a = "application/octet-stream;binary/octet-stream";

    /* renamed from: b, reason: collision with root package name */
    public String f3276b;

    /* renamed from: c, reason: collision with root package name */
    private Context f3277c;

    private a(Context context) {
        this(context, null);
    }

    private void a(String str) {
        this.f3276b = str;
    }

    private URL b() {
        try {
            return new URL(this.f3276b);
        } catch (Exception unused) {
            return null;
        }
    }

    private HttpHost c() {
        URL urlB;
        String strG = g();
        if ((strG != null && !strG.contains("wap")) || (urlB = b()) == null) {
            return null;
        }
        "https".equalsIgnoreCase(urlB.getProtocol());
        String property = System.getProperty("https.proxyHost");
        String property2 = System.getProperty("https.proxyPort");
        if (TextUtils.isEmpty(property)) {
            return null;
        }
        return new HttpHost(property, Integer.parseInt(property2));
    }

    private HttpHost d() {
        NetworkInfo networkInfoF = f();
        if (networkInfoF != null && networkInfoF.isAvailable() && networkInfoF.getType() == 0) {
            String defaultHost = Proxy.getDefaultHost();
            int defaultPort = Proxy.getDefaultPort();
            if (defaultHost != null) {
                return new HttpHost(defaultHost, defaultPort);
            }
        }
        return null;
    }

    private HttpHost e() {
        URL urlB;
        String strG = g();
        if ((strG != null && !strG.contains("wap")) || (urlB = b()) == null) {
            return null;
        }
        "https".equalsIgnoreCase(urlB.getProtocol());
        String property = System.getProperty("https.proxyHost");
        String property2 = System.getProperty("https.proxyPort");
        if (TextUtils.isEmpty(property)) {
            return null;
        }
        return new HttpHost(property, Integer.parseInt(property2));
    }

    private NetworkInfo f() {
        try {
            return ((ConnectivityManager) this.f3277c.getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Exception unused) {
            return null;
        }
    }

    private String g() {
        try {
            NetworkInfo networkInfoF = f();
            return (networkInfoF == null || !networkInfoF.isAvailable()) ? "none" : networkInfoF.getType() == 1 ? "wifi" : networkInfoF.getExtraInfo().toLowerCase();
        } catch (Exception unused) {
            return "none";
        }
    }

    public a(Context context, String str) {
        if (context != null) {
            this.f3277c = context.getApplicationContext();
        } else {
            this.f3277c = context;
        }
        this.f3276b = str;
    }

    private String a() {
        return this.f3276b;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0022  */
    /* JADX WARN: Type inference failed for: r5v6, types: [org.apache.http.HttpMessage, org.apache.http.client.methods.HttpEntityEnclosingRequestBase, org.apache.http.client.methods.HttpPost] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final org.apache.http.HttpResponse a(byte[] r10, java.util.List<org.apache.http.Header> r11) throws java.lang.Throwable {
        /*
            r9 = this;
            java.lang.String r0 = "Keep-Alive"
            java.lang.String r1 = "X-ExecuteTime"
            java.lang.String r2 = "X-Hostname"
            com.alipay.sdk.net.b r3 = com.alipay.sdk.net.b.a()
            r4 = 0
            if (r3 != 0) goto Le
            return r4
        Le:
            org.apache.http.impl.client.DefaultHttpClient r5 = r3.f3280c     // Catch: java.lang.Throwable -> Ld0
            org.apache.http.params.HttpParams r5 = r5.getParams()     // Catch: java.lang.Throwable -> Ld0
            java.lang.String r6 = r9.g()     // Catch: java.lang.Throwable -> Ld0
            if (r6 == 0) goto L24
            java.lang.String r7 = "wap"
            boolean r6 = r6.contains(r7)     // Catch: java.lang.Throwable -> Ld0
            if (r6 != 0) goto L24
        L22:
            r8 = r4
            goto L4e
        L24:
            java.net.URL r6 = r9.b()     // Catch: java.lang.Throwable -> Ld0
            if (r6 == 0) goto L22
            java.lang.String r6 = r6.getProtocol()     // Catch: java.lang.Throwable -> Ld0
            java.lang.String r7 = "https"
            r7.equalsIgnoreCase(r6)     // Catch: java.lang.Throwable -> Ld0
            java.lang.String r6 = "https.proxyHost"
            java.lang.String r6 = java.lang.System.getProperty(r6)     // Catch: java.lang.Throwable -> Ld0
            java.lang.String r7 = "https.proxyPort"
            java.lang.String r7 = java.lang.System.getProperty(r7)     // Catch: java.lang.Throwable -> Ld0
            boolean r8 = android.text.TextUtils.isEmpty(r6)     // Catch: java.lang.Throwable -> Ld0
            if (r8 != 0) goto L22
            org.apache.http.HttpHost r8 = new org.apache.http.HttpHost     // Catch: java.lang.Throwable -> Ld0
            int r7 = java.lang.Integer.parseInt(r7)     // Catch: java.lang.Throwable -> Ld0
            r8.<init>(r6, r7)     // Catch: java.lang.Throwable -> Ld0
        L4e:
            if (r8 == 0) goto L55
            java.lang.String r6 = "http.route.default-proxy"
            r5.setParameter(r6, r8)     // Catch: java.lang.Throwable -> Ld0
        L55:
            if (r10 == 0) goto L81
            int r5 = r10.length     // Catch: java.lang.Throwable -> Ld0
            if (r5 != 0) goto L5b
            goto L81
        L5b:
            org.apache.http.client.methods.HttpPost r5 = new org.apache.http.client.methods.HttpPost     // Catch: java.lang.Throwable -> Ld0
            java.lang.String r6 = r9.f3276b     // Catch: java.lang.Throwable -> Ld0
            r5.<init>(r6)     // Catch: java.lang.Throwable -> Ld0
            org.apache.http.entity.ByteArrayEntity r6 = new org.apache.http.entity.ByteArrayEntity     // Catch: java.lang.Throwable -> Ld0
            r6.<init>(r10)     // Catch: java.lang.Throwable -> Ld0
            java.lang.String r10 = "application/octet-stream;binary/octet-stream"
            r6.setContentType(r10)     // Catch: java.lang.Throwable -> Ld0
            r5.setEntity(r6)     // Catch: java.lang.Throwable -> Ld0
            java.lang.String r10 = "Accept-Charset"
            java.lang.String r6 = "UTF-8"
            r5.addHeader(r10, r6)     // Catch: java.lang.Throwable -> Ld0
            java.lang.String r10 = "Connection"
            r5.addHeader(r10, r0)     // Catch: java.lang.Throwable -> Ld0
            java.lang.String r10 = "timeout=180, max=100"
            r5.addHeader(r0, r10)     // Catch: java.lang.Throwable -> Ld0
            goto L88
        L81:
            org.apache.http.client.methods.HttpGet r5 = new org.apache.http.client.methods.HttpGet     // Catch: java.lang.Throwable -> Ld0
            java.lang.String r10 = r9.f3276b     // Catch: java.lang.Throwable -> Ld0
            r5.<init>(r10)     // Catch: java.lang.Throwable -> Ld0
        L88:
            if (r11 == 0) goto L9e
            java.util.Iterator r10 = r11.iterator()     // Catch: java.lang.Throwable -> Ld0
        L8e:
            boolean r11 = r10.hasNext()     // Catch: java.lang.Throwable -> Ld0
            if (r11 == 0) goto L9e
            java.lang.Object r11 = r10.next()     // Catch: java.lang.Throwable -> Ld0
            org.apache.http.Header r11 = (org.apache.http.Header) r11     // Catch: java.lang.Throwable -> Ld0
            r5.addHeader(r11)     // Catch: java.lang.Throwable -> Ld0
            goto L8e
        L9e:
            org.apache.http.HttpResponse r10 = r3.a(r5)     // Catch: java.lang.Throwable -> Ld0
            org.apache.http.Header[] r11 = r10.getHeaders(r2)     // Catch: java.lang.Throwable -> Ld0
            r0 = 0
            if (r11 == 0) goto Lb9
            int r5 = r11.length     // Catch: java.lang.Throwable -> Ld0
            if (r5 <= 0) goto Lb9
            r11 = r11[r0]     // Catch: java.lang.Throwable -> Ld0
            if (r11 == 0) goto Lb9
            org.apache.http.Header[] r11 = r10.getHeaders(r2)     // Catch: java.lang.Throwable -> Ld0
            r11 = r11[r0]     // Catch: java.lang.Throwable -> Ld0
            r11.toString()     // Catch: java.lang.Throwable -> Ld0
        Lb9:
            org.apache.http.Header[] r11 = r10.getHeaders(r1)     // Catch: java.lang.Throwable -> Ld0
            if (r11 == 0) goto Lcf
            int r2 = r11.length     // Catch: java.lang.Throwable -> Ld0
            if (r2 <= 0) goto Lcf
            r11 = r11[r0]     // Catch: java.lang.Throwable -> Ld0
            if (r11 == 0) goto Lcf
            org.apache.http.Header[] r11 = r10.getHeaders(r1)     // Catch: java.lang.Throwable -> Ld0
            r11 = r11[r0]     // Catch: java.lang.Throwable -> Ld0
            r11.toString()     // Catch: java.lang.Throwable -> Ld0
        Lcf:
            return r10
        Ld0:
            r10 = move-exception
            org.apache.http.impl.client.DefaultHttpClient r11 = r3.f3280c     // Catch: java.lang.Throwable -> Lde
            org.apache.http.conn.ClientConnectionManager r11 = r11.getConnectionManager()     // Catch: java.lang.Throwable -> Lde
            if (r11 == 0) goto Lde
            r11.shutdown()     // Catch: java.lang.Throwable -> Lde
            com.alipay.sdk.net.b.f3279b = r4     // Catch: java.lang.Throwable -> Lde
        Lde:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.net.a.a(byte[], java.util.List):org.apache.http.HttpResponse");
    }
}
