package com.aliyun.utils;

import com.cicada.player.utils.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.net.ssl.HttpsURLConnection;

/* loaded from: classes2.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    private static final String f3589a = "d";

    /* renamed from: b, reason: collision with root package name */
    private static ExecutorService f3590b = Executors.newCachedThreadPool();

    /* renamed from: d, reason: collision with root package name */
    private String f3592d;

    /* renamed from: c, reason: collision with root package name */
    private URLConnection f3591c = null;

    /* renamed from: e, reason: collision with root package name */
    private String f3593e = null;

    /* renamed from: f, reason: collision with root package name */
    private int f3594f = 10000;

    /* renamed from: g, reason: collision with root package name */
    private String f3595g = null;

    /* renamed from: h, reason: collision with root package name */
    private String f3596h = null;

    /* renamed from: i, reason: collision with root package name */
    private String[] f3597i = null;

    public class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            HttpURLConnection httpURLConnection;
            try {
                if (d.this.f3591c instanceof HttpsURLConnection) {
                    Logger.i(d.f3589a, "HttpClientHelper stop().... HttpsURLConnection.disconnect ");
                    httpURLConnection = (HttpsURLConnection) d.this.f3591c;
                } else {
                    if (!(d.this.f3591c instanceof HttpURLConnection)) {
                        return;
                    }
                    Logger.i(d.f3589a, "HttpClientHelper stop().... HttpURLConnection.disconnect ");
                    httpURLConnection = (HttpURLConnection) d.this.f3591c;
                }
                httpURLConnection.disconnect();
            } catch (Exception e2) {
                Logger.e(d.f3589a, e2.getMessage());
            }
        }
    }

    public d(String str) {
        this.f3592d = str;
    }

    private URLConnection a(String str) throws ProtocolException {
        Proxy proxy;
        try {
            if (this.f3595g != null) {
                try {
                    URL url = new URL(this.f3595g);
                    proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(url.getHost(), url.getPort()));
                } catch (Exception unused) {
                }
            } else {
                proxy = null;
            }
            URL url2 = new URL(str);
            URLConnection uRLConnectionOpenConnection = proxy != null ? url2.openConnection(proxy) : url2.openConnection();
            if (!(uRLConnectionOpenConnection instanceof HttpURLConnection)) {
                return null;
            }
            HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(this.f3594f);
            httpURLConnection.setReadTimeout(this.f3594f);
            return uRLConnectionOpenConnection;
        } catch (Exception unused2) {
            return null;
        }
    }

    private URLConnection b(String str) throws ProtocolException {
        Proxy proxy;
        try {
            if (this.f3595g != null) {
                try {
                    URL url = new URL(this.f3595g);
                    proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(url.getHost(), url.getPort()));
                } catch (Exception unused) {
                }
            } else {
                proxy = null;
            }
            URL url2 = new URL(str);
            URLConnection uRLConnectionOpenConnection = proxy != null ? url2.openConnection(proxy) : url2.openConnection();
            if (!(uRLConnectionOpenConnection instanceof HttpsURLConnection)) {
                return null;
            }
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) uRLConnectionOpenConnection;
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setConnectTimeout(this.f3594f);
            httpsURLConnection.setReadTimeout(this.f3594f);
            return uRLConnectionOpenConnection;
        } catch (Exception unused2) {
            return null;
        }
    }

    private InputStream c() {
        HttpURLConnection httpURLConnection;
        URLConnection uRLConnection = this.f3591c;
        if (uRLConnection instanceof HttpsURLConnection) {
            httpURLConnection = (HttpsURLConnection) uRLConnection;
        } else {
            if (!(uRLConnection instanceof HttpURLConnection)) {
                return null;
            }
            httpURLConnection = (HttpURLConnection) uRLConnection;
        }
        return httpURLConnection.getErrorStream();
    }

    private int d() throws IOException {
        HttpURLConnection httpURLConnection;
        URLConnection uRLConnection = this.f3591c;
        if (uRLConnection instanceof HttpsURLConnection) {
            httpURLConnection = (HttpsURLConnection) uRLConnection;
        } else {
            if (!(uRLConnection instanceof HttpURLConnection)) {
                return 0;
            }
            httpURLConnection = (HttpURLConnection) uRLConnection;
        }
        return httpURLConnection.getResponseCode();
    }

    public void a(int i2) {
        this.f3594f = i2;
    }

    public void a(String[] strArr) {
        this.f3597i = strArr;
    }

    /* JADX WARN: Removed duplicated region for block: B:111:0x015e A[Catch: IOException -> 0x0166, TryCatch #13 {IOException -> 0x0166, blocks: (B:109:0x0159, B:111:0x015e, B:113:0x0163), top: B:153:0x0159 }] */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0163 A[Catch: IOException -> 0x0166, TRY_LEAVE, TryCatch #13 {IOException -> 0x0166, blocks: (B:109:0x0159, B:111:0x015e, B:113:0x0163), top: B:153:0x0159 }] */
    /* JADX WARN: Removed duplicated region for block: B:116:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002b A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0189 A[Catch: IOException -> 0x0191, TryCatch #5 {IOException -> 0x0191, blocks: (B:128:0x0184, B:130:0x0189, B:132:0x018e), top: B:149:0x0184 }] */
    /* JADX WARN: Removed duplicated region for block: B:132:0x018e A[Catch: IOException -> 0x0191, TRY_LEAVE, TryCatch #5 {IOException -> 0x0191, blocks: (B:128:0x0184, B:130:0x0189, B:132:0x018e), top: B:149:0x0184 }] */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0195  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x0184 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:153:0x0159 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:175:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String b() throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 422
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.utils.d.b():java.lang.String");
    }

    public void c(String str) {
        this.f3595g = str;
    }

    public void d(String str) {
        this.f3593e = str;
    }

    public void e() {
        Logger.d(f3589a, "HttpClientHelper stop().... urlConnection = " + this.f3591c);
        if (this.f3591c != null) {
            f3590b.execute(new a());
        }
    }

    public void e(String str) {
        this.f3596h = str;
    }
}
