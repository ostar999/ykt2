package com.mobile.auth.y;

import android.content.Context;
import android.net.Network;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import cn.hutool.core.text.StrPool;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public final class n {

    /* renamed from: a, reason: collision with root package name */
    public static boolean f10620a = false;

    /* renamed from: b, reason: collision with root package name */
    public static boolean f10621b = false;

    public class a implements HostnameVerifier {
        public a() {
        }

        @Override // javax.net.ssl.HostnameVerifier
        public final boolean verify(String str, SSLSession sSLSession) {
            try {
                if (!TextUtils.isEmpty(str) && sSLSession != null) {
                    try {
                        return u.a(str, ((X509Certificate) sSLSession.getPeerCertificates()[0]).getSubjectDN().getName());
                    } catch (SSLPeerUnverifiedException unused) {
                        t.b();
                    }
                }
                return false;
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return false;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0049 A[Catch: Exception -> 0x004c, TRY_LEAVE, TryCatch #2 {Exception -> 0x004c, blocks: (B:27:0x0044, B:29:0x0049), top: B:38:0x0044 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0044 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String a(java.io.InputStream r6) throws java.lang.Throwable {
        /*
            r0 = 0
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L2b
            r1.<init>()     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L2b
            r2 = 1024(0x400, float:1.435E-42)
            byte[] r2 = new byte[r2]     // Catch: java.lang.Exception -> L2c java.lang.Throwable -> L41
        La:
            int r3 = r6.read(r2)     // Catch: java.lang.Exception -> L2c java.lang.Throwable -> L41
            r4 = -1
            if (r3 == r4) goto L16
            r4 = 0
            r1.write(r2, r4, r3)     // Catch: java.lang.Exception -> L2c java.lang.Throwable -> L41
            goto La
        L16:
            byte[] r2 = r1.toByteArray()     // Catch: java.lang.Exception -> L2c java.lang.Throwable -> L41
            java.lang.String r3 = new java.lang.String     // Catch: java.lang.Exception -> L2c java.lang.Throwable -> L41
            r3.<init>(r2)     // Catch: java.lang.Exception -> L2c java.lang.Throwable -> L41
            r1.close()     // Catch: java.lang.Exception -> L25 java.lang.Throwable -> L35
            r6.close()     // Catch: java.lang.Exception -> L25 java.lang.Throwable -> L35
        L25:
            return r3
        L26:
            r1 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
            goto L42
        L2b:
            r1 = r0
        L2c:
            com.mobile.auth.y.t.b()     // Catch: java.lang.Throwable -> L41
            if (r1 == 0) goto L37
            r1.close()     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L40
            goto L37
        L35:
            r6 = move-exception
            goto L3d
        L37:
            if (r6 == 0) goto L40
            r6.close()     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L40
            goto L40
        L3d:
            com.mobile.auth.gatewayauth.ExceptionProcessor.processException(r6)
        L40:
            return r0
        L41:
            r0 = move-exception
        L42:
            if (r1 == 0) goto L47
            r1.close()     // Catch: java.lang.Exception -> L4c
        L47:
            if (r6 == 0) goto L4c
            r6.close()     // Catch: java.lang.Exception -> L4c
        L4c:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobile.auth.y.n.a(java.io.InputStream):java.lang.String");
    }

    private static String a(String str) {
        try {
            if (!str.contains(":")) {
                return str;
            }
            return StrPool.BRACKET_START + str + StrPool.BRACKET_END;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public final String a(Context context, String str, HashMap<String, String> map, Object obj) {
        HttpsURLConnection httpsURLConnection;
        String queryParameter;
        String strReplaceFirst = str;
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            String host = "";
            try {
                host = new URL(strReplaceFirst).getHost();
            } catch (MalformedURLException unused) {
                t.b();
            }
            if (host.contains(e.d()) && f10621b && f10620a) {
                if (!TextUtils.isEmpty(u.f10640a)) {
                    strReplaceFirst = strReplaceFirst.replaceFirst(e.d(), a(u.f10640a));
                }
                f10621b = false;
            }
            String strReplaceFirst2 = strReplaceFirst;
            try {
                URL url = new URL(strReplaceFirst2);
                httpsURLConnection = (HttpsURLConnection) (obj != null ? ((Network) obj).openConnection(url) : url.openConnection());
                try {
                    httpsURLConnection.setDoInput(true);
                    httpsURLConnection.setDoOutput(false);
                    httpsURLConnection.setUseCaches(false);
                    httpsURLConnection.setInstanceFollowRedirects(false);
                    httpsURLConnection.setReadTimeout(10000);
                    httpsURLConnection.setConnectTimeout(10000);
                    httpsURLConnection.setRequestMethod("GET");
                    httpsURLConnection.setHostnameVerifier(new a());
                    p.a();
                    httpsURLConnection.setInstanceFollowRedirects(true);
                    HttpURLConnection.setFollowRedirects(true);
                    if (map != null) {
                        for (Iterator<String> it = map.keySet().iterator(); it.hasNext(); it = it) {
                            String next = it.next();
                            httpsURLConnection.setRequestProperty(next, map.get(next));
                        }
                    }
                    httpsURLConnection.addRequestProperty("Connection", "close");
                    t.c("TAG\thttpsURLConnection.connect();\n");
                    httpsURLConnection.connect();
                    t.c("connect cost:" + (System.currentTimeMillis() - jCurrentTimeMillis));
                    long jCurrentTimeMillis2 = System.currentTimeMillis();
                    int responseCode = httpsURLConnection.getResponseCode();
                    t.c("response cost:" + (System.currentTimeMillis() - jCurrentTimeMillis2));
                    String string = httpsURLConnection.getURL().toString();
                    if (string.contains("ret_url")) {
                        queryParameter = Uri.parse(new String(Base64.decode(Uri.parse(string).getQueryParameter("ret_url"), 0))).getQueryParameter("seq");
                        t.c("seq = " + queryParameter + "\nstatusCode = " + responseCode);
                    } else {
                        queryParameter = "seqAndroidEmpty";
                    }
                    if (responseCode == 200) {
                        InputStream inputStream = httpsURLConnection.getInputStream();
                        String str2 = new String(a(inputStream));
                        httpsURLConnection.disconnect();
                        inputStream.close();
                        if (!TextUtils.isEmpty(str2)) {
                            JSONObject jSONObject = new JSONObject(str2);
                            if (TextUtils.isEmpty(jSONObject.optString("seq"))) {
                                jSONObject.put("seq", queryParameter);
                            }
                            return jSONObject.toString();
                        }
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("code", 410012);
                        jSONObject2.put("msg", "outputStr isEmpty");
                        jSONObject2.put("seq", queryParameter);
                        jSONObject2.put("data", "requestUrl:".concat(String.valueOf(strReplaceFirst2)));
                        return jSONObject2.toString();
                    }
                    if (responseCode != 302) {
                        JSONObject jSONObject3 = new JSONObject();
                        jSONObject3.put("code", 410010);
                        jSONObject3.put("msg", "https statusCode NOK ".concat(String.valueOf(responseCode)));
                        jSONObject3.put("data", "requestUrl:".concat(String.valueOf(strReplaceFirst2)));
                        return jSONObject3.toString();
                    }
                    String str3 = new String(httpsURLConnection.getHeaderField("Location"));
                    t.c("statusCode == 302, redirectUrl is \n".concat(str3));
                    t.c("System.currentTimeMillis() is  \n" + System.currentTimeMillis());
                    httpsURLConnection.getInputStream().close();
                    httpsURLConnection.disconnect();
                    if (TextUtils.isEmpty(str3)) {
                        JSONObject jSONObject4 = new JSONObject();
                        jSONObject4.put("code", 410013);
                        jSONObject4.put("msg", "无跳转地址");
                        jSONObject4.put("data", host);
                        return jSONObject4.toString();
                    }
                    if (str3.startsWith("https")) {
                        return a(context, str3, map, obj);
                    }
                    JSONObject jSONObject5 = new JSONObject();
                    jSONObject5.put("code", 410013);
                    jSONObject5.put("msg", "无法跳转HTTP地址");
                    jSONObject5.put("data", host);
                    return jSONObject5.toString();
                } catch (Exception e2) {
                    e = e2;
                    try {
                        httpsURLConnection.getInputStream().close();
                        httpsURLConnection.disconnect();
                    } catch (Exception unused2) {
                    }
                    int iE = v.e();
                    t.c("\n■★■★■★■★■★■★■★■★■★■\n iRetry = >" + iE + " \n   e-->" + e + "\n ■★■★■★■★■★■★■★■★■★■\n");
                    t.b();
                    String message = e.getMessage();
                    if (message == null || iE >= v.d()) {
                        try {
                            t.c("catch (Exception e) is  ".concat(String.valueOf(e)));
                            JSONObject jSONObject6 = new JSONObject();
                            jSONObject6.put("code", 410011);
                            jSONObject6.put("msg", "https异常 : ".concat(String.valueOf(message)));
                            jSONObject6.put("data", "requestUrl->".concat(String.valueOf(strReplaceFirst2)));
                            return jSONObject6.toString();
                        } catch (Exception unused3) {
                            return null;
                        }
                    }
                    int iF = v.f();
                    if (message.contains("resolve host")) {
                        if (host.contains(e.d()) || host.contains(e.e())) {
                            t.c("resolve host error: retry->" + iF + " times \ne_getMessage=" + message);
                            if (f10621b && f10620a && !TextUtils.isEmpty(u.f10640a)) {
                                strReplaceFirst2 = strReplaceFirst2.replaceFirst(e.d(), a(u.f10640a));
                            }
                            return a(context, strReplaceFirst2, map, obj);
                        }
                    }
                    if (message.contains("Failed to connect")) {
                        t.c("Failed to connect error: retry->" + iF + " times \ne_getMessage=" + message);
                        return a(context, strReplaceFirst2, map, obj);
                    }
                    t.c("other  error: retry->" + iF + " times \ne_getMessage=" + message);
                    return a(context, strReplaceFirst2, map, obj);
                }
            } catch (Exception e3) {
                e = e3;
                httpsURLConnection = null;
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }
}
