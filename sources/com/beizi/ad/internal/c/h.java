package com.beizi.ad.internal.c;

import android.text.TextUtils;
import com.beizi.ad.internal.utilities.HaoboLog;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.HttpURLConnection;
import java.net.URL;

/* loaded from: classes2.dex */
public class h implements o {

    /* renamed from: a, reason: collision with root package name */
    private final com.beizi.ad.internal.c.b.c f4101a;

    /* renamed from: b, reason: collision with root package name */
    private p f4102b;

    /* renamed from: c, reason: collision with root package name */
    private HttpURLConnection f4103c;

    /* renamed from: d, reason: collision with root package name */
    private InputStream f4104d;

    public h(String str) {
        this(str, com.beizi.ad.internal.c.b.d.a());
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x008d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void e() throws java.lang.Throwable {
        /*
            r6 = this;
            java.lang.String r0 = com.beizi.ad.internal.utilities.HaoboLog.httpUrlSourceLogTag
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Read content info failFrom "
            r1.append(r2)
            com.beizi.ad.internal.c.p r2 = r6.f4102b
            java.lang.String r2 = r2.f4118a
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.beizi.ad.internal.utilities.HaoboLog.d(r0, r1)
            r0 = 0
            r1 = 10000(0x2710, float:1.4013E-41)
            r2 = 0
            java.net.HttpURLConnection r0 = r6.a(r0, r1)     // Catch: java.lang.Throwable -> L60 java.io.IOException -> L63
            int r1 = r0.getContentLength()     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L5e
            java.lang.String r3 = r0.getContentType()     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L5e
            java.io.InputStream r2 = r0.getInputStream()     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L5e
            com.beizi.ad.internal.c.p r4 = new com.beizi.ad.internal.c.p     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L5e
            com.beizi.ad.internal.c.p r5 = r6.f4102b     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L5e
            java.lang.String r5 = r5.f4118a     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L5e
            r4.<init>(r5, r1, r3)     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L5e
            r6.f4102b = r4     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L5e
            com.beizi.ad.internal.c.b.c r1 = r6.f4101a     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L5e
            java.lang.String r3 = r4.f4118a     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L5e
            r1.a(r3, r4)     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L5e
            java.lang.String r1 = com.beizi.ad.internal.utilities.HaoboLog.httpUrlSourceLogTag     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L5e
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L5e
            r3.<init>()     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L5e
            java.lang.String r4 = "Source info fetched: "
            r3.append(r4)     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L5e
            com.beizi.ad.internal.c.p r4 = r6.f4102b     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L5e
            r3.append(r4)     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L5e
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L5e
            com.beizi.ad.internal.utilities.HaoboLog.d(r1, r3)     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L5e
            com.beizi.ad.internal.c.n.a(r2)
            goto L84
        L5c:
            r1 = move-exception
            goto L88
        L5e:
            r1 = move-exception
            goto L65
        L60:
            r1 = move-exception
            r0 = r2
            goto L88
        L63:
            r1 = move-exception
            r0 = r2
        L65:
            java.lang.String r3 = com.beizi.ad.internal.utilities.HaoboLog.httpUrlSourceLogTag     // Catch: java.lang.Throwable -> L5c
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L5c
            r4.<init>()     // Catch: java.lang.Throwable -> L5c
            java.lang.String r5 = "Error fetching info failFrom "
            r4.append(r5)     // Catch: java.lang.Throwable -> L5c
            com.beizi.ad.internal.c.p r5 = r6.f4102b     // Catch: java.lang.Throwable -> L5c
            java.lang.String r5 = r5.f4118a     // Catch: java.lang.Throwable -> L5c
            r4.append(r5)     // Catch: java.lang.Throwable -> L5c
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> L5c
            com.beizi.ad.internal.utilities.HaoboLog.e(r3, r4, r1)     // Catch: java.lang.Throwable -> L5c
            com.beizi.ad.internal.c.n.a(r2)
            if (r0 == 0) goto L87
        L84:
            r0.disconnect()
        L87:
            return
        L88:
            com.beizi.ad.internal.c.n.a(r2)
            if (r0 == 0) goto L90
            r0.disconnect()
        L90:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.beizi.ad.internal.c.h.e():void");
    }

    @Override // com.beizi.ad.internal.c.o
    public synchronized int a() throws m {
        if (this.f4102b.f4119b == Integer.MIN_VALUE) {
            e();
        }
        return this.f4102b.f4119b;
    }

    @Override // com.beizi.ad.internal.c.o
    public void b() throws m {
        HttpURLConnection httpURLConnection = this.f4103c;
        if (httpURLConnection != null) {
            try {
                httpURLConnection.disconnect();
            } catch (IllegalArgumentException | NullPointerException e2) {
                throw new RuntimeException("Wait... but why? WTF!? ", e2);
            }
        }
    }

    public synchronized String c() throws m {
        if (TextUtils.isEmpty(this.f4102b.f4120c)) {
            e();
        }
        return this.f4102b.f4120c;
    }

    public String d() {
        return this.f4102b.f4118a;
    }

    public String toString() {
        return "HttpUrlSource{sourceInfo='" + this.f4102b + "}";
    }

    public h(String str, com.beizi.ad.internal.c.b.c cVar) {
        this.f4101a = (com.beizi.ad.internal.c.b.c) k.a(cVar);
        p pVarA = cVar.a(str);
        this.f4102b = pVarA == null ? new p(str, Integer.MIN_VALUE, n.a(str)) : pVarA;
    }

    @Override // com.beizi.ad.internal.c.o
    public void a(int i2) throws m {
        try {
            HttpURLConnection httpURLConnectionA = a(i2, -1);
            this.f4103c = httpURLConnectionA;
            String contentType = httpURLConnectionA.getContentType();
            this.f4104d = new BufferedInputStream(this.f4103c.getInputStream(), 8192);
            HttpURLConnection httpURLConnection = this.f4103c;
            p pVar = new p(this.f4102b.f4118a, a(httpURLConnection, i2, httpURLConnection.getResponseCode()), contentType);
            this.f4102b = pVar;
            this.f4101a.a(pVar.f4118a, pVar);
        } catch (IOException e2) {
            throw new m("Error opening connection for " + this.f4102b.f4118a + " with offset " + i2, e2);
        }
    }

    public h(h hVar) {
        this.f4102b = hVar.f4102b;
        this.f4101a = hVar.f4101a;
    }

    private int a(HttpURLConnection httpURLConnection, int i2, int i3) throws IOException {
        int contentLength = httpURLConnection.getContentLength();
        return i3 == 200 ? contentLength : i3 == 206 ? contentLength + i2 : this.f4102b.f4119b;
    }

    @Override // com.beizi.ad.internal.c.o
    public int a(byte[] bArr) throws m {
        InputStream inputStream = this.f4104d;
        if (inputStream != null) {
            try {
                return inputStream.read(bArr, 0, bArr.length);
            } catch (InterruptedIOException e2) {
                throw new i("Reading source " + this.f4102b.f4118a + " is interrupted", e2);
            } catch (IOException e3) {
                throw new m("Error reading data failFrom " + this.f4102b.f4118a, e3);
            }
        }
        throw new m("Error reading data failFrom " + this.f4102b.f4118a + ": connection is absent!");
    }

    private HttpURLConnection a(int i2, int i3) throws IOException, m {
        String str;
        HttpURLConnection httpURLConnection;
        boolean z2;
        String headerField = this.f4102b.f4118a;
        int i4 = 0;
        do {
            String str2 = HaoboLog.httpUrlSourceLogTag;
            StringBuilder sb = new StringBuilder();
            sb.append("Open connection ");
            if (i2 > 0) {
                str = " with offset " + i2;
            } else {
                str = "";
            }
            sb.append(str);
            sb.append(" to ");
            sb.append(headerField);
            HaoboLog.d(str2, sb.toString());
            httpURLConnection = (HttpURLConnection) new URL(headerField).openConnection();
            if (i2 > 0) {
                httpURLConnection.setRequestProperty("Range", "bytes=" + i2 + "-");
            }
            if (i3 > 0) {
                httpURLConnection.setConnectTimeout(i3);
                httpURLConnection.setReadTimeout(i3);
            }
            int responseCode = httpURLConnection.getResponseCode();
            z2 = responseCode == 301 || responseCode == 302 || responseCode == 303;
            if (z2) {
                headerField = httpURLConnection.getHeaderField("Location");
                i4++;
                httpURLConnection.disconnect();
            }
            if (i4 > 5) {
                throw new m("Too many redirects: " + i4);
            }
        } while (z2);
        return httpURLConnection;
    }
}
