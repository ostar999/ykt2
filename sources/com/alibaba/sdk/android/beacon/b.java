package com.alibaba.sdk.android.beacon;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.sdk.android.beacon.Beacon;
import com.just.agentweb.DefaultWebClient;
import com.ta.utdid2.device.UTDevice;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes2.dex */
final class b {

    /* renamed from: a, reason: collision with root package name */
    private static final String f2632a;

    /* renamed from: b, reason: collision with root package name */
    private static final String f2633b;

    /* renamed from: a, reason: collision with other field name */
    private final Beacon f3a;

    /* renamed from: c, reason: collision with root package name */
    private final List<Beacon.Config> f2634c = new ArrayList();

    /* renamed from: a, reason: collision with other field name */
    private final a f4a = new a();

    public final class a {
        private a() {
        }

        /* JADX WARN: Removed duplicated region for block: B:46:0x00bb A[Catch: IOException -> 0x00be, TRY_LEAVE, TryCatch #7 {IOException -> 0x00be, blocks: (B:44:0x00b6, B:46:0x00bb), top: B:57:0x00b6 }] */
        /* JADX WARN: Removed duplicated region for block: B:57:0x00b6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public java.lang.String a(java.lang.String r7, byte[] r8) throws java.lang.Throwable {
            /*
                r6 = this;
                r0 = 0
                java.net.URL r1 = new java.net.URL     // Catch: java.lang.Throwable -> L8d java.lang.Exception -> L90
                r1.<init>(r7)     // Catch: java.lang.Throwable -> L8d java.lang.Exception -> L90
                java.net.URLConnection r7 = r1.openConnection()     // Catch: java.lang.Throwable -> L8d java.lang.Exception -> L90
                java.net.HttpURLConnection r7 = (java.net.HttpURLConnection) r7     // Catch: java.lang.Throwable -> L8d java.lang.Exception -> L90
                r1 = 10000(0x2710, float:1.4013E-41)
                r7.setReadTimeout(r1)     // Catch: java.lang.Throwable -> L8d java.lang.Exception -> L90
                r7.setConnectTimeout(r1)     // Catch: java.lang.Throwable -> L8d java.lang.Exception -> L90
                java.lang.String r1 = "POST"
                r7.setRequestMethod(r1)     // Catch: java.lang.Throwable -> L8d java.lang.Exception -> L90
                r1 = 1
                r7.setDoOutput(r1)     // Catch: java.lang.Throwable -> L8d java.lang.Exception -> L90
                r7.setDoInput(r1)     // Catch: java.lang.Throwable -> L8d java.lang.Exception -> L90
                r1 = 0
                r7.setUseCaches(r1)     // Catch: java.lang.Throwable -> L8d java.lang.Exception -> L90
                boolean r1 = com.alibaba.sdk.android.beacon.a.f2631a     // Catch: java.lang.Throwable -> L8d java.lang.Exception -> L90
                if (r1 == 0) goto L2f
                java.lang.String r1 = "Host"
                java.lang.String r2 = "beacon-api.aliyuncs.com"
                r7.setRequestProperty(r1, r2)     // Catch: java.lang.Throwable -> L8d java.lang.Exception -> L90
            L2f:
                java.io.OutputStream r1 = r7.getOutputStream()     // Catch: java.lang.Throwable -> L8d java.lang.Exception -> L90
                r1.write(r8)     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L89
                r1.flush()     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L89
                int r8 = r7.getResponseCode()     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L89
                boolean r2 = r6.a(r8)     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L89
                if (r2 == 0) goto L48
                java.io.InputStream r7 = r7.getInputStream()     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L89
                goto L4c
            L48:
                java.io.InputStream r7 = r7.getErrorStream()     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L89
            L4c:
                java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L89
                java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L89
                java.lang.String r5 = "UTF-8"
                r4.<init>(r7, r5)     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L89
                r3.<init>(r4)     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L89
                java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L81 java.lang.Exception -> L83
                r7.<init>()     // Catch: java.lang.Throwable -> L81 java.lang.Exception -> L83
            L5d:
                java.lang.String r0 = r3.readLine()     // Catch: java.lang.Throwable -> L81 java.lang.Exception -> L83
                if (r0 == 0) goto L67
                r7.append(r0)     // Catch: java.lang.Throwable -> L81 java.lang.Exception -> L83
                goto L5d
            L67:
                if (r2 != 0) goto L76
                com.alibaba.sdk.android.beacon.b r0 = com.alibaba.sdk.android.beacon.b.this     // Catch: java.lang.Throwable -> L81 java.lang.Exception -> L83
                java.lang.String r8 = java.lang.String.valueOf(r8)     // Catch: java.lang.Throwable -> L81 java.lang.Exception -> L83
                java.lang.String r2 = r7.toString()     // Catch: java.lang.Throwable -> L81 java.lang.Exception -> L83
                com.alibaba.sdk.android.beacon.b.a(r0, r8, r2)     // Catch: java.lang.Throwable -> L81 java.lang.Exception -> L83
            L76:
                java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> L81 java.lang.Exception -> L83
                r1.close()     // Catch: java.io.IOException -> L80
                r3.close()     // Catch: java.io.IOException -> L80
            L80:
                return r7
            L81:
                r7 = move-exception
                goto L87
            L83:
                r7 = move-exception
                goto L8b
            L85:
                r7 = move-exception
                r3 = r0
            L87:
                r0 = r1
                goto Lb4
            L89:
                r7 = move-exception
                r3 = r0
            L8b:
                r0 = r1
                goto L92
            L8d:
                r7 = move-exception
                r3 = r0
                goto Lb4
            L90:
                r7 = move-exception
                r3 = r0
            L92:
                java.lang.String r8 = "beacon"
                java.lang.String r1 = r7.getMessage()     // Catch: java.lang.Throwable -> Lb3
                android.util.Log.i(r8, r1, r7)     // Catch: java.lang.Throwable -> Lb3
                com.alibaba.sdk.android.beacon.b r8 = com.alibaba.sdk.android.beacon.b.this     // Catch: java.lang.Throwable -> Lb3
                java.lang.String r1 = "-100"
                java.lang.String r7 = r7.getMessage()     // Catch: java.lang.Throwable -> Lb3
                com.alibaba.sdk.android.beacon.b.a(r8, r1, r7)     // Catch: java.lang.Throwable -> Lb3
                if (r0 == 0) goto Lab
                r0.close()     // Catch: java.io.IOException -> Lb0
            Lab:
                if (r3 == 0) goto Lb0
                r3.close()     // Catch: java.io.IOException -> Lb0
            Lb0:
                java.lang.String r7 = ""
                return r7
            Lb3:
                r7 = move-exception
            Lb4:
                if (r0 == 0) goto Lb9
                r0.close()     // Catch: java.io.IOException -> Lbe
            Lb9:
                if (r3 == 0) goto Lbe
                r3.close()     // Catch: java.io.IOException -> Lbe
            Lbe:
                throw r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.beacon.b.a.a(java.lang.String, byte[]):java.lang.String");
        }

        public boolean a(int i2) {
            return i2 >= 200 && i2 < 300;
        }
    }

    /* renamed from: com.alibaba.sdk.android.beacon.b$b, reason: collision with other inner class name */
    public static final class C0016b {

        /* renamed from: a, reason: collision with root package name */
        final Map<String, String> f2636a;

        /* renamed from: c, reason: collision with root package name */
        final String f2637c;

        /* renamed from: d, reason: collision with root package name */
        final String f2638d;

        /* renamed from: e, reason: collision with root package name */
        final String f2639e;

        /* renamed from: f, reason: collision with root package name */
        final String f2640f;

        /* renamed from: g, reason: collision with root package name */
        final String f2641g;

        /* renamed from: h, reason: collision with root package name */
        final String f2642h;

        /* renamed from: i, reason: collision with root package name */
        final String f2643i;
        final String mAppKey;
        final Map<String, String> mExtras;

        /* renamed from: com.alibaba.sdk.android.beacon.b$b$a */
        public static final class a {

            /* renamed from: b, reason: collision with root package name */
            Map<String, String> f2644b = new HashMap();

            /* renamed from: j, reason: collision with root package name */
            String f2645j;

            /* renamed from: k, reason: collision with root package name */
            String f2646k;

            /* renamed from: l, reason: collision with root package name */
            String f2647l;

            /* renamed from: m, reason: collision with root package name */
            String f2648m;

            /* renamed from: n, reason: collision with root package name */
            String f2649n;

            /* renamed from: o, reason: collision with root package name */
            String f2650o;

            /* renamed from: p, reason: collision with root package name */
            String f2651p;

            public a a(String str) {
                this.f2645j = str;
                return this;
            }

            public a a(Map<String, String> map) {
                this.f2644b.putAll(map);
                return this;
            }

            public C0016b a() {
                return new C0016b(this);
            }

            public a b(String str) {
                this.f2646k = str;
                return this;
            }

            public a c(String str) {
                this.f2647l = str;
                return this;
            }

            public a d(String str) {
                this.f2648m = str;
                return this;
            }

            public a e(String str) {
                this.f2649n = str;
                return this;
            }

            public a f(String str) {
                this.f2650o = str;
                return this;
            }

            public a g(String str) {
                this.f2651p = str;
                return this;
            }
        }

        private C0016b(a aVar) {
            this.f2636a = new TreeMap();
            this.mAppKey = aVar.f2645j;
            this.f2637c = aVar.f2646k;
            this.f2638d = aVar.f2647l;
            this.f2639e = aVar.f2648m;
            this.f2640f = aVar.f2649n;
            this.f2641g = aVar.f2650o;
            this.f2642h = aVar.f2651p;
            this.mExtras = aVar.f2644b;
            this.f2643i = a();
        }

        private String a() {
            this.f2636a.put(com.heytap.mcssdk.constant.b.f7201z, this.mAppKey);
            this.f2636a.put("appVer", this.f2638d);
            this.f2636a.put("osType", this.f2639e);
            this.f2636a.put("osVer", this.f2640f);
            this.f2636a.put("deviceId", this.f2641g);
            this.f2636a.put("beaconVer", this.f2642h);
            for (String str : this.mExtras.keySet()) {
                this.f2636a.put(str, this.mExtras.get(str));
            }
            StringBuilder sb = new StringBuilder();
            for (String str2 : this.f2636a.keySet()) {
                sb.append(str2);
                sb.append(this.f2636a.get(str2));
            }
            String strA = c.a(this.f2637c, sb.toString());
            this.f2636a.put("sign", strA);
            return strA;
        }
    }

    static {
        String str = com.alibaba.sdk.android.beacon.a.f2631a ? "100.67.64.54" : "beacon-api.aliyuncs.com";
        f2632a = str;
        f2633b = DefaultWebClient.HTTPS_SCHEME + str + "/beacon/fetch/config";
    }

    public b(Beacon beacon) {
        this.f3a = beacon;
    }

    private C0016b a(Context context, String str, String str2, Map<String, String> map) {
        return new C0016b.a().a(str).b(str2).c(c.a(context)).d("Android").e(String.valueOf(Build.VERSION.SDK_INT)).f(UTDevice.getUtdid(context)).g("1.0.5").a(map).a();
    }

    private String a(C0016b c0016b) {
        Map<String, String> map = c0016b.f2636a;
        StringBuilder sb = new StringBuilder();
        for (String str : map.keySet()) {
            sb.append(encode(str));
            sb.append("=");
            sb.append(encode(map.get(str)));
            sb.append("&");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    private void a(String str) {
        b(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, String str2) {
        this.f3a.a(new Beacon.Error(str, str2));
    }

    private void b(String str) {
        JSONArray jSONArrayOptJSONArray;
        try {
            if (TextUtils.isEmpty(str) || (jSONArrayOptJSONArray = new JSONObject(str).optJSONArray("result")) == null || jSONArrayOptJSONArray.length() <= 0) {
                return;
            }
            this.f2634c.clear();
            for (int i2 = 0; i2 < jSONArrayOptJSONArray.length(); i2++) {
                JSONObject jSONObject = (JSONObject) jSONArrayOptJSONArray.get(i2);
                this.f2634c.add(new Beacon.Config(jSONObject.optString("key"), jSONObject.optString("value")));
            }
        } catch (Exception unused) {
        }
    }

    private String encode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public List<Beacon.Config> a() {
        return Collections.unmodifiableList(this.f2634c);
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m20a(Context context, String str, String str2, Map<String, String> map) {
        C0016b c0016bA = a(context, str, str2, map);
        String str3 = f2633b + "/byappkey";
        Log.i("beacon", "url=" + str3);
        String strA = this.f4a.a(str3, a(c0016bA).getBytes());
        Log.i("beacon", "[fetchByAppKey] result: " + strA);
        a(strA);
    }
}
