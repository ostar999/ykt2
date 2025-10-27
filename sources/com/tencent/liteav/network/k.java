package com.tencent.liteav.network;

import android.content.Context;
import java.util.regex.Pattern;
import org.json.JSONException;

/* loaded from: classes6.dex */
public class k {

    /* renamed from: a, reason: collision with root package name */
    private Context f19611a;

    /* renamed from: b, reason: collision with root package name */
    private String f19612b;

    /* renamed from: c, reason: collision with root package name */
    private String f19613c;

    /* renamed from: d, reason: collision with root package name */
    private String f19614d;

    /* renamed from: e, reason: collision with root package name */
    private String f19615e;

    /* renamed from: f, reason: collision with root package name */
    private long f19616f;

    /* renamed from: g, reason: collision with root package name */
    private long f19617g;

    /* renamed from: h, reason: collision with root package name */
    private String f19618h;

    /* renamed from: i, reason: collision with root package name */
    private long f19619i;

    /* renamed from: j, reason: collision with root package name */
    private long f19620j;

    /* renamed from: k, reason: collision with root package name */
    private long f19621k;

    /* renamed from: l, reason: collision with root package name */
    private long f19622l;

    /* renamed from: m, reason: collision with root package name */
    private long f19623m;

    /* renamed from: n, reason: collision with root package name */
    private long f19624n;

    /* renamed from: o, reason: collision with root package name */
    private long f19625o;

    /* renamed from: p, reason: collision with root package name */
    private long f19626p;

    /* renamed from: q, reason: collision with root package name */
    private long f19627q;

    /* renamed from: r, reason: collision with root package name */
    private long f19628r;

    /* renamed from: s, reason: collision with root package name */
    private long f19629s;

    /* renamed from: t, reason: collision with root package name */
    private long f19630t;

    /* renamed from: u, reason: collision with root package name */
    private long f19631u;

    /* renamed from: v, reason: collision with root package name */
    private long f19632v;

    /* renamed from: w, reason: collision with root package name */
    private boolean f19633w = true;

    public k(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.f19611a = applicationContext;
        this.f19612b = com.tencent.liteav.basic.util.h.b(applicationContext);
        this.f19614d = "Android";
        j.a().a(this.f19611a);
        a();
    }

    private void e() {
        long j2 = this.f19626p;
        long j3 = this.f19627q;
        a();
        this.f19624n = j2;
        this.f19625o = j3;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0135  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void f() throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 386
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.network.k.f():void");
    }

    public void a() {
        this.f19613c = "";
        this.f19616f = 0L;
        this.f19617g = -1L;
        this.f19618h = "";
        this.f19619i = -1L;
        this.f19620j = -1L;
        this.f19621k = -1L;
        this.f19622l = -1L;
        this.f19615e = "";
        this.f19623m = 0L;
        this.f19624n = 0L;
        this.f19625o = 0L;
        this.f19626p = 0L;
        this.f19627q = 0L;
        this.f19628r = 0L;
        this.f19629s = 0L;
        this.f19630t = 0L;
        this.f19631u = 0L;
        this.f19632v = 0L;
        this.f19633w = true;
    }

    public void b() {
        this.f19616f = System.currentTimeMillis();
        this.f19613c = j.a().b();
    }

    public void c() throws JSONException {
        f();
        e();
    }

    public void d() {
        this.f19623m++;
    }

    private boolean c(String str) {
        return Pattern.compile("[0-9]*").matcher(str).matches();
    }

    public void b(long j2, long j3) {
        this.f19632v++;
        this.f19628r += j2;
        this.f19629s += j3;
        if (j2 > this.f19630t) {
            this.f19630t = j2;
        }
        if (j3 > this.f19631u) {
            this.f19631u = j3;
        }
    }

    private boolean b(String str) {
        return str == null || str.length() == 0;
    }

    public void a(boolean z2) {
        this.f19622l = z2 ? 2L : 1L;
        if (z2) {
            this.f19633w = false;
        }
    }

    public void a(String str) {
        this.f19615e = str;
    }

    public void a(boolean z2, String str) {
        this.f19618h = str;
        if (z2) {
            this.f19617g = 1L;
            return;
        }
        if (str != null) {
            int iIndexOf = str.indexOf(":");
            if (iIndexOf != -1) {
                str = str.substring(0, iIndexOf);
            }
            if (str != null) {
                for (String str2 : str.split("[.]")) {
                    if (!c(str2)) {
                        this.f19617g = 3L;
                        return;
                    }
                }
                this.f19617g = 2L;
            }
        }
    }

    public void a(long j2, long j3, long j4) {
        this.f19619i = j2;
        this.f19620j = j3;
        this.f19621k = j4;
    }

    public void a(long j2, long j3) {
        this.f19626p = j2;
        this.f19627q = j3;
    }
}
