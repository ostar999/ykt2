package com.alibaba.sdk.android.httpdns.g;

import com.alibaba.sdk.android.httpdns.log.HttpDnsLog;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/* loaded from: classes2.dex */
public class c<T> {

    /* renamed from: a, reason: collision with root package name */
    private d f2783a;

    /* renamed from: a, reason: collision with other field name */
    private k<T> f53a;

    public c() {
    }

    public c(d dVar, k<T> kVar) {
        this.f2783a = dVar;
        this.f53a = kVar;
    }

    public static StringBuilder a(BufferedReader bufferedReader) throws IOException {
        StringBuilder sb = new StringBuilder();
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                return sb;
            }
            sb.append(line);
        }
    }

    public d a() {
        return this.f2783a;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v15 */
    /* JADX WARN: Type inference failed for: r5v16 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v3, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v2, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v6 */
    /* renamed from: a, reason: collision with other method in class */
    public T mo50a() throws IOException {
        Object obj;
        Throwable th;
        ?? r5;
        ?? r6;
        HttpURLConnection httpURLConnection;
        int responseCode;
        long jCurrentTimeMillis = System.currentTimeMillis();
        String strM = this.f2783a.m();
        HttpDnsLog.d("request url " + strM);
        HttpURLConnection httpURLConnection2 = null;
        try {
            httpURLConnection = (HttpURLConnection) new URL(strM).openConnection();
        } catch (Throwable th2) {
            th = th2;
            obj = null;
        }
        try {
            httpURLConnection.setReadTimeout(this.f2783a.getTimeout());
            httpURLConnection.setConnectTimeout(this.f2783a.getTimeout());
            if (httpURLConnection instanceof HttpsURLConnection) {
                ((HttpsURLConnection) httpURLConnection).setHostnameVerifier(new HostnameVerifier() { // from class: com.alibaba.sdk.android.httpdns.g.c.1
                    @Override // javax.net.ssl.HostnameVerifier
                    public boolean verify(String str, SSLSession sSLSession) {
                        return HttpsURLConnection.getDefaultHostnameVerifier().verify("203.107.1.1", sSLSession);
                    }
                });
            }
            responseCode = httpURLConnection.getResponseCode();
            r6 = 200;
        } catch (Throwable th3) {
            th = th3;
            obj = null;
            httpURLConnection2 = httpURLConnection;
            th = th;
            r5 = obj;
            r6 = obj;
            try {
                HttpDnsLog.w("request " + strM + " fail, cost " + (System.currentTimeMillis() - jCurrentTimeMillis), th);
                throw th;
            } catch (Throwable th4) {
                if (httpURLConnection2 != null) {
                    httpURLConnection2.disconnect();
                }
                if (r5 != 0) {
                    try {
                        r5.close();
                    } catch (IOException unused) {
                        throw th4;
                    }
                }
                if (r6 != 0) {
                    r6.close();
                }
                throw th4;
            }
        }
        try {
            try {
                if (responseCode != 200) {
                    throw b.a(httpURLConnection.getResponseCode(), a(new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream(), "UTF-8"))).toString());
                }
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String string = a(bufferedReader).toString();
                HttpDnsLog.d("request success " + string);
                T tA = this.f53a.a(string);
                httpURLConnection.disconnect();
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException unused2) {
                    }
                }
                bufferedReader.close();
                return tA;
            } catch (Throwable th5) {
                r6 = 0;
                httpURLConnection2 = httpURLConnection;
                th = th5;
                r5 = responseCode;
                HttpDnsLog.w("request " + strM + " fail, cost " + (System.currentTimeMillis() - jCurrentTimeMillis), th);
                throw th;
            }
        } catch (Throwable th6) {
            th = th6;
            httpURLConnection2 = httpURLConnection;
            r5 = responseCode;
            HttpDnsLog.w("request " + strM + " fail, cost " + (System.currentTimeMillis() - jCurrentTimeMillis), th);
            throw th;
        }
    }
}
