package com.xiaomi.push;

import android.text.TextUtils;
import cn.hutool.core.text.StrPool;
import com.huawei.hms.push.constant.RemoteMessageConst;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class cy {

    /* renamed from: a, reason: collision with other field name */
    private long f289a;

    /* renamed from: a, reason: collision with other field name */
    public String f290a;

    /* renamed from: b, reason: collision with other field name */
    public String f292b;

    /* renamed from: c, reason: collision with root package name */
    public String f24707c;

    /* renamed from: d, reason: collision with root package name */
    public String f24708d;

    /* renamed from: e, reason: collision with root package name */
    public String f24709e;

    /* renamed from: f, reason: collision with root package name */
    public String f24710f;

    /* renamed from: g, reason: collision with root package name */
    public String f24711g;

    /* renamed from: h, reason: collision with root package name */
    protected String f24712h;

    /* renamed from: i, reason: collision with root package name */
    private String f24713i;

    /* renamed from: a, reason: collision with other field name */
    private ArrayList<dh> f291a = new ArrayList<>();

    /* renamed from: a, reason: collision with root package name */
    private double f24705a = 0.1d;

    /* renamed from: j, reason: collision with root package name */
    private String f24714j = "s.mi1.cc";

    /* renamed from: b, reason: collision with root package name */
    private long f24706b = 86400000;

    public cy(String str) {
        this.f290a = "";
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("the host is empty");
        }
        this.f289a = System.currentTimeMillis();
        this.f291a.add(new dh(str, -1));
        this.f290a = dc.m313a();
        this.f292b = str;
    }

    private synchronized void c(String str) {
        Iterator<dh> it = this.f291a.iterator();
        while (it.hasNext()) {
            if (TextUtils.equals(it.next().f308a, str)) {
                it.remove();
            }
        }
    }

    public synchronized cy a(JSONObject jSONObject) {
        this.f290a = jSONObject.optString(com.alipay.sdk.app.statistic.c.f3111a);
        this.f24706b = jSONObject.getLong(RemoteMessageConst.TTL);
        this.f24705a = jSONObject.getDouble("pct");
        this.f289a = jSONObject.getLong("ts");
        this.f24708d = jSONObject.optString("city");
        this.f24707c = jSONObject.optString("prv");
        this.f24711g = jSONObject.optString("cty");
        this.f24709e = jSONObject.optString("isp");
        this.f24710f = jSONObject.optString("ip");
        this.f292b = jSONObject.optString(com.alipay.sdk.cons.c.f3231f);
        this.f24712h = jSONObject.optString("xf");
        JSONArray jSONArray = jSONObject.getJSONArray("fbs");
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            a(new dh().a(jSONArray.getJSONObject(i2)));
        }
        return this;
    }

    public synchronized String a() {
        if (!TextUtils.isEmpty(this.f24713i)) {
            return this.f24713i;
        }
        if (TextUtils.isEmpty(this.f24709e)) {
            return "hardcode_isp";
        }
        String strA = ay.a(new String[]{this.f24709e, this.f24707c, this.f24708d, this.f24711g, this.f24710f}, StrPool.UNDERLINE);
        this.f24713i = strA;
        return strA;
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized ArrayList<String> m304a() {
        return a(false);
    }

    public ArrayList<String> a(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("the url is empty.");
        }
        URL url = new URL(str);
        if (!TextUtils.equals(url.getHost(), this.f292b)) {
            throw new IllegalArgumentException("the url is not supported by the fallback");
        }
        ArrayList<String> arrayList = new ArrayList<>();
        Iterator<String> it = a(true).iterator();
        while (it.hasNext()) {
            da daVarA = da.a(it.next(), url.getPort());
            arrayList.add(new URL(url.getProtocol(), daVarA.m312a(), daVarA.a(), url.getFile()).toString());
        }
        return arrayList;
    }

    public synchronized ArrayList<String> a(boolean z2) {
        ArrayList<String> arrayList;
        int iIndexOf;
        int size = this.f291a.size();
        dh[] dhVarArr = new dh[size];
        this.f291a.toArray(dhVarArr);
        Arrays.sort(dhVarArr);
        arrayList = new ArrayList<>();
        for (int i2 = 0; i2 < size; i2++) {
            dh dhVar = dhVarArr[i2];
            String strSubstring = (z2 || (iIndexOf = dhVar.f308a.indexOf(":")) == -1) ? dhVar.f308a : dhVar.f308a.substring(0, iIndexOf);
            arrayList.add(strSubstring);
        }
        return arrayList;
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized JSONObject m305a() {
        JSONObject jSONObject;
        jSONObject = new JSONObject();
        jSONObject.put(com.alipay.sdk.app.statistic.c.f3111a, this.f290a);
        jSONObject.put(RemoteMessageConst.TTL, this.f24706b);
        jSONObject.put("pct", this.f24705a);
        jSONObject.put("ts", this.f289a);
        jSONObject.put("city", this.f24708d);
        jSONObject.put("prv", this.f24707c);
        jSONObject.put("cty", this.f24711g);
        jSONObject.put("isp", this.f24709e);
        jSONObject.put("ip", this.f24710f);
        jSONObject.put(com.alipay.sdk.cons.c.f3231f, this.f292b);
        jSONObject.put("xf", this.f24712h);
        JSONArray jSONArray = new JSONArray();
        Iterator<dh> it = this.f291a.iterator();
        while (it.hasNext()) {
            jSONArray.put(it.next().a());
        }
        jSONObject.put("fbs", jSONArray);
        return jSONObject;
    }

    public void a(double d3) {
        this.f24705a = d3;
    }

    public void a(long j2) {
        if (j2 > 0) {
            this.f24706b = j2;
            return;
        }
        throw new IllegalArgumentException("the duration is invalid " + j2);
    }

    public synchronized void a(dh dhVar) {
        c(dhVar.f308a);
        this.f291a.add(dhVar);
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized void m306a(String str) {
        a(new dh(str));
    }

    public void a(String str, int i2, long j2, long j3, Exception exc) {
        a(str, new cx(i2, j2, j3, exc));
    }

    public void a(String str, long j2, long j3) {
        try {
            b(new URL(str).getHost(), j2, j3);
        } catch (MalformedURLException unused) {
        }
    }

    public void a(String str, long j2, long j3, Exception exc) {
        try {
            b(new URL(str).getHost(), j2, j3, exc);
        } catch (MalformedURLException unused) {
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x001b, code lost:
    
        r1.a(r5);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized void a(java.lang.String r4, com.xiaomi.push.cx r5) {
        /*
            r3 = this;
            monitor-enter(r3)
            java.util.ArrayList<com.xiaomi.push.dh> r0 = r3.f291a     // Catch: java.lang.Throwable -> L20
            java.util.Iterator r0 = r0.iterator()     // Catch: java.lang.Throwable -> L20
        L7:
            boolean r1 = r0.hasNext()     // Catch: java.lang.Throwable -> L20
            if (r1 == 0) goto L1e
            java.lang.Object r1 = r0.next()     // Catch: java.lang.Throwable -> L20
            com.xiaomi.push.dh r1 = (com.xiaomi.push.dh) r1     // Catch: java.lang.Throwable -> L20
            java.lang.String r2 = r1.f308a     // Catch: java.lang.Throwable -> L20
            boolean r2 = android.text.TextUtils.equals(r4, r2)     // Catch: java.lang.Throwable -> L20
            if (r2 == 0) goto L7
            r1.a(r5)     // Catch: java.lang.Throwable -> L20
        L1e:
            monitor-exit(r3)
            return
        L20:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.cy.a(java.lang.String, com.xiaomi.push.cx):void");
    }

    public synchronized void a(String[] strArr) {
        int i2;
        int size = this.f291a.size() - 1;
        while (true) {
            i2 = 0;
            if (size < 0) {
                break;
            }
            int length = strArr.length;
            while (true) {
                if (i2 < length) {
                    if (TextUtils.equals(this.f291a.get(size).f308a, strArr[i2])) {
                        this.f291a.remove(size);
                        break;
                    }
                    i2++;
                }
            }
            size--;
        }
        Iterator<dh> it = this.f291a.iterator();
        int i3 = 0;
        while (it.hasNext()) {
            int i4 = it.next().f24725a;
            if (i4 > i3) {
                i3 = i4;
            }
        }
        while (i2 < strArr.length) {
            a(new dh(strArr[i2], (strArr.length + i3) - i2));
            i2++;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m307a() {
        return TextUtils.equals(this.f290a, dc.m313a());
    }

    public boolean a(cy cyVar) {
        return TextUtils.equals(this.f290a, cyVar.f290a);
    }

    public void b(String str) {
        this.f24714j = str;
    }

    public void b(String str, long j2, long j3) {
        a(str, 0, j2, j3, null);
    }

    public void b(String str, long j2, long j3, Exception exc) {
        a(str, -1, j2, j3, exc);
    }

    public boolean b() {
        return System.currentTimeMillis() - this.f289a < this.f24706b;
    }

    public boolean c() {
        long j2 = this.f24706b;
        if (864000000 >= j2) {
            j2 = 864000000;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j3 = this.f289a;
        return jCurrentTimeMillis - j3 > j2 || (jCurrentTimeMillis - j3 > this.f24706b && this.f290a.startsWith("WIFI-"));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.f290a);
        sb.append("\n");
        sb.append(a());
        Iterator<dh> it = this.f291a.iterator();
        while (it.hasNext()) {
            dh next = it.next();
            sb.append("\n");
            sb.append(next.toString());
        }
        sb.append("\n");
        return sb.toString();
    }
}
