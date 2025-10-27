package com.huawei.hms.framework.network.grs.g;

import android.text.TextUtils;
import com.huawei.hms.framework.common.Logger;
import com.huawei.hms.framework.common.StringUtils;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class d {

    /* renamed from: o, reason: collision with root package name */
    private static final String f7650o = "d";

    /* renamed from: a, reason: collision with root package name */
    private Map<String, List<String>> f7651a;

    /* renamed from: b, reason: collision with root package name */
    private byte[] f7652b;

    /* renamed from: c, reason: collision with root package name */
    private int f7653c;

    /* renamed from: d, reason: collision with root package name */
    private long f7654d;

    /* renamed from: e, reason: collision with root package name */
    private long f7655e;

    /* renamed from: f, reason: collision with root package name */
    private long f7656f;

    /* renamed from: g, reason: collision with root package name */
    private String f7657g;

    /* renamed from: h, reason: collision with root package name */
    private int f7658h;

    /* renamed from: i, reason: collision with root package name */
    private int f7659i;

    /* renamed from: j, reason: collision with root package name */
    private String f7660j;

    /* renamed from: k, reason: collision with root package name */
    private long f7661k;

    /* renamed from: l, reason: collision with root package name */
    private String f7662l;

    /* renamed from: m, reason: collision with root package name */
    private Exception f7663m;

    /* renamed from: n, reason: collision with root package name */
    private String f7664n;

    public d(int i2, Map<String, List<String>> map, byte[] bArr, long j2) throws NumberFormatException {
        this.f7658h = 2;
        this.f7659i = 9001;
        this.f7660j = "";
        this.f7661k = 0L;
        this.f7662l = "";
        this.f7653c = i2;
        this.f7651a = map;
        this.f7652b = ByteBuffer.wrap(bArr).array();
        this.f7654d = j2;
        s();
    }

    public d(Exception exc, long j2) {
        this.f7653c = 0;
        this.f7658h = 2;
        this.f7659i = 9001;
        this.f7660j = "";
        this.f7661k = 0L;
        this.f7662l = "";
        this.f7663m = exc;
        this.f7654d = j2;
    }

    private void a(Map<String, String> map) {
        String str;
        String str2;
        if (map.containsKey("ETag")) {
            String str3 = map.get("ETag");
            if (!TextUtils.isEmpty(str3)) {
                Logger.i(f7650o, "success get Etag from server");
                a(str3);
                return;
            } else {
                str = f7650o;
                str2 = "The Response Heads Etag is Empty";
            }
        } else {
            str = f7650o;
            str2 = "Response Heads has not Etag";
        }
        Logger.i(str, str2);
    }

    private void b(int i2) {
        this.f7659i = i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x00ba  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void b(java.util.Map<java.lang.String, java.lang.String> r12) throws java.lang.NumberFormatException {
        /*
            r11 = this;
            java.lang.String r0 = "Cache-Control"
            boolean r1 = r12.containsKey(r0)
            r2 = 1000(0x3e8, double:4.94E-321)
            r4 = 0
            r5 = 1
            r6 = 0
            if (r1 == 0) goto L4d
            java.lang.Object r12 = r12.get(r0)
            java.lang.String r12 = (java.lang.String) r12
            boolean r0 = android.text.TextUtils.isEmpty(r12)
            if (r0 != 0) goto Lae
            java.lang.String r0 = "max-age="
            boolean r1 = r12.contains(r0)
            if (r1 == 0) goto Lae
            int r0 = r12.indexOf(r0)     // Catch: java.lang.NumberFormatException -> L43
            int r0 = r0 + 8
            java.lang.String r12 = r12.substring(r0)     // Catch: java.lang.NumberFormatException -> L43
            long r0 = java.lang.Long.parseLong(r12)     // Catch: java.lang.NumberFormatException -> L43
            java.lang.String r12 = com.huawei.hms.framework.network.grs.g.d.f7650o     // Catch: java.lang.NumberFormatException -> L41
            java.lang.String r8 = "Cache-Control value{%s}"
            java.lang.Object[] r9 = new java.lang.Object[r5]     // Catch: java.lang.NumberFormatException -> L41
            java.lang.Long r10 = java.lang.Long.valueOf(r0)     // Catch: java.lang.NumberFormatException -> L41
            r9[r4] = r10     // Catch: java.lang.NumberFormatException -> L41
            com.huawei.hms.framework.common.Logger.v(r12, r8, r9)     // Catch: java.lang.NumberFormatException -> L41
            goto Laf
        L41:
            r12 = move-exception
            goto L45
        L43:
            r12 = move-exception
            r0 = r6
        L45:
            java.lang.String r8 = com.huawei.hms.framework.network.grs.g.d.f7650o
            java.lang.String r9 = "getExpireTime addHeadersToResult NumberFormatException"
            com.huawei.hms.framework.common.Logger.w(r8, r9, r12)
            goto Laf
        L4d:
            java.lang.String r0 = "Expires"
            boolean r1 = r12.containsKey(r0)
            if (r1 == 0) goto La7
            java.lang.Object r0 = r12.get(r0)
            java.lang.String r0 = (java.lang.String) r0
            java.lang.String r1 = com.huawei.hms.framework.network.grs.g.d.f7650o
            java.lang.Object[] r8 = new java.lang.Object[r5]
            r8[r4] = r0
            java.lang.String r9 = "expires is{%s}"
            com.huawei.hms.framework.common.Logger.v(r1, r9, r8)
            java.text.SimpleDateFormat r1 = new java.text.SimpleDateFormat
            java.util.Locale r8 = java.util.Locale.ROOT
            java.lang.String r9 = "EEE, d MMM yyyy HH:mm:ss 'GMT'"
            r1.<init>(r9, r8)
            java.lang.String r8 = "Date"
            boolean r9 = r12.containsKey(r8)
            if (r9 == 0) goto L7e
            java.lang.Object r12 = r12.get(r8)
            java.lang.String r12 = (java.lang.String) r12
            goto L7f
        L7e:
            r12 = 0
        L7f:
            java.util.Date r0 = r1.parse(r0)     // Catch: java.text.ParseException -> L9e
            boolean r8 = android.text.TextUtils.isEmpty(r12)     // Catch: java.text.ParseException -> L9e
            if (r8 == 0) goto L8f
            java.util.Date r12 = new java.util.Date     // Catch: java.text.ParseException -> L9e
            r12.<init>()     // Catch: java.text.ParseException -> L9e
            goto L93
        L8f:
            java.util.Date r12 = r1.parse(r12)     // Catch: java.text.ParseException -> L9e
        L93:
            long r0 = r0.getTime()     // Catch: java.text.ParseException -> L9e
            long r8 = r12.getTime()     // Catch: java.text.ParseException -> L9e
            long r0 = r0 - r8
            long r0 = r0 / r2
            goto Laf
        L9e:
            r12 = move-exception
            java.lang.String r0 = com.huawei.hms.framework.network.grs.g.d.f7650o
            java.lang.String r1 = "getExpireTime ParseException."
            com.huawei.hms.framework.common.Logger.w(r0, r1, r12)
            goto Lae
        La7:
            java.lang.String r12 = com.huawei.hms.framework.network.grs.g.d.f7650o
            java.lang.String r0 = "response headers neither contains Cache-Control nor Expires."
            com.huawei.hms.framework.common.Logger.i(r12, r0)
        Lae:
            r0 = r6
        Laf:
            int r12 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
            if (r12 <= 0) goto Lba
            r6 = 2592000(0x278d00, double:1.280618E-317)
            int r12 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
            if (r12 <= 0) goto Lbd
        Lba:
            r0 = 86400(0x15180, double:4.26873E-319)
        Lbd:
            long r0 = r0 * r2
            java.lang.String r12 = com.huawei.hms.framework.network.grs.g.d.f7650o
            java.lang.Object[] r2 = new java.lang.Object[r5]
            java.lang.Long r3 = java.lang.Long.valueOf(r0)
            r2[r4] = r3
            java.lang.String r3 = "convert expireTime{%s}"
            com.huawei.hms.framework.common.Logger.i(r12, r3, r2)
            long r2 = java.lang.System.currentTimeMillis()
            long r0 = r0 + r2
            java.lang.String r12 = java.lang.String.valueOf(r0)
            r11.c(r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.framework.network.grs.g.d.b(java.util.Map):void");
    }

    private void c(int i2) {
        this.f7658h = i2;
    }

    private void c(long j2) {
        this.f7661k = j2;
    }

    private void c(String str) {
        this.f7660j = str;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void c(java.util.Map<java.lang.String, java.lang.String> r6) throws java.lang.NumberFormatException {
        /*
            r5 = this;
            java.lang.String r0 = "Retry-After"
            boolean r1 = r6.containsKey(r0)
            if (r1 == 0) goto L21
            java.lang.Object r6 = r6.get(r0)
            java.lang.String r6 = (java.lang.String) r6
            boolean r0 = android.text.TextUtils.isEmpty(r6)
            if (r0 != 0) goto L21
            long r0 = java.lang.Long.parseLong(r6)     // Catch: java.lang.NumberFormatException -> L19
            goto L23
        L19:
            r6 = move-exception
            java.lang.String r0 = com.huawei.hms.framework.network.grs.g.d.f7650o
            java.lang.String r1 = "getRetryAfter addHeadersToResult NumberFormatException"
            com.huawei.hms.framework.common.Logger.w(r0, r1, r6)
        L21:
            r0 = 0
        L23:
            r2 = 1000(0x3e8, double:4.94E-321)
            long r0 = r0 * r2
            java.lang.String r6 = com.huawei.hms.framework.network.grs.g.d.f7650o
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.Long r3 = java.lang.Long.valueOf(r0)
            r4 = 0
            r2[r4] = r3
            java.lang.String r3 = "convert retry-afterTime{%s}"
            com.huawei.hms.framework.common.Logger.v(r6, r3, r2)
            r5.c(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.framework.network.grs.g.d.c(java.util.Map):void");
    }

    private void d(String str) {
    }

    private void e(String str) {
    }

    private void f(String str) {
        this.f7657g = str;
    }

    private void p() {
        int i2;
        if (m()) {
            Logger.i(f7650o, "GRSSDK get httpcode{304} not any changed.");
            c(1);
            return;
        }
        if (!o()) {
            Logger.i(f7650o, "GRSSDK parse server body all failed.");
            c(2);
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(StringUtils.byte2Str(this.f7652b));
            if (jSONObject.has("isSuccess")) {
                if (jSONObject.getInt("isSuccess") == 1) {
                }
            } else if (jSONObject.has("resultCode")) {
                i2 = jSONObject.getInt("resultCode") == 0 ? 1 : 2;
            } else {
                Logger.e(f7650o, "sth. wrong because server errorcode's key.");
                i2 = -1;
            }
            if (i2 != 1 && jSONObject.has("services")) {
                i2 = 0;
            }
            c(i2);
            if (i2 == 1 || i2 == 0) {
                f(jSONObject.has("services") ? jSONObject.getJSONObject("services").toString() : "");
                e(jSONObject.has("errorList") ? jSONObject.getJSONObject("errorList").toString() : "");
            } else {
                b(jSONObject.has("errorCode") ? jSONObject.getInt("errorCode") : 9001);
                d(jSONObject.has("errorDesc") ? jSONObject.getString("errorDesc") : "");
            }
        } catch (JSONException e2) {
            Logger.w(f7650o, "GrsResponse GrsResponse(String result) JSONException: %s", StringUtils.anonymizeMessage(e2.getMessage()));
            c(2);
        }
    }

    private void q() throws NumberFormatException {
        if (o() || n() || m()) {
            Map<String, String> mapR = r();
            if (mapR.size() <= 0) {
                Logger.w(f7650o, "parseHeader {headers.size() <= 0}");
                return;
            }
            try {
                if (o() || m()) {
                    b(mapR);
                    a(mapR);
                }
                if (n()) {
                    c(mapR);
                }
            } catch (JSONException e2) {
                Logger.w(f7650o, "parseHeader catch JSONException: %s", StringUtils.anonymizeMessage(e2.getMessage()));
            }
        }
    }

    private Map<String, String> r() {
        HashMap map = new HashMap(16);
        Map<String, List<String>> map2 = this.f7651a;
        if (map2 == null || map2.size() <= 0) {
            Logger.v(f7650o, "parseRespHeaders {respHeaders == null} or {respHeaders.size() <= 0}");
            return map;
        }
        for (Map.Entry<String, List<String>> entry : this.f7651a.entrySet()) {
            String key = entry.getKey();
            Iterator<String> it = entry.getValue().iterator();
            while (it.hasNext()) {
                map.put(key, it.next());
            }
        }
        return map;
    }

    private void s() throws NumberFormatException {
        q();
        p();
    }

    public String a() {
        return this.f7660j;
    }

    public void a(int i2) {
    }

    public void a(long j2) {
        this.f7656f = j2;
    }

    public void a(String str) {
        this.f7662l = str;
    }

    public int b() {
        return this.f7653c;
    }

    public void b(long j2) {
        this.f7655e = j2;
    }

    public void b(String str) {
        this.f7664n = str;
    }

    public int c() {
        return this.f7659i;
    }

    public Exception d() {
        return this.f7663m;
    }

    public String e() {
        return this.f7662l;
    }

    public int f() {
        return this.f7658h;
    }

    public long g() {
        return this.f7656f;
    }

    public long h() {
        return this.f7655e;
    }

    public long i() {
        return this.f7654d;
    }

    public String j() {
        return this.f7657g;
    }

    public long k() {
        return this.f7661k;
    }

    public String l() {
        return this.f7664n;
    }

    public boolean m() {
        return this.f7653c == 304;
    }

    public boolean n() {
        return this.f7653c == 503;
    }

    public boolean o() {
        return this.f7653c == 200;
    }
}
