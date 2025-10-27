package com.tencent.bugly.proguard;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public final class bt extends m implements Cloneable {

    /* renamed from: m, reason: collision with root package name */
    static bs f17781m = new bs();

    /* renamed from: n, reason: collision with root package name */
    static Map<String, String> f17782n = null;

    /* renamed from: o, reason: collision with root package name */
    static final /* synthetic */ boolean f17783o = true;

    /* renamed from: a, reason: collision with root package name */
    public boolean f17784a = true;

    /* renamed from: b, reason: collision with root package name */
    public boolean f17785b = true;

    /* renamed from: c, reason: collision with root package name */
    public boolean f17786c = true;

    /* renamed from: d, reason: collision with root package name */
    public String f17787d = "";

    /* renamed from: e, reason: collision with root package name */
    public String f17788e = "";

    /* renamed from: f, reason: collision with root package name */
    public bs f17789f = null;

    /* renamed from: g, reason: collision with root package name */
    public Map<String, String> f17790g = null;

    /* renamed from: h, reason: collision with root package name */
    public long f17791h = 0;

    /* renamed from: i, reason: collision with root package name */
    public String f17792i = "";

    /* renamed from: j, reason: collision with root package name */
    public String f17793j = "";

    /* renamed from: k, reason: collision with root package name */
    public int f17794k = 0;

    /* renamed from: l, reason: collision with root package name */
    public int f17795l = 0;

    static {
        HashMap map = new HashMap();
        f17782n = map;
        map.put("", "");
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(l lVar) throws UnsupportedEncodingException {
        lVar.a(this.f17784a, 0);
        lVar.a(this.f17785b, 1);
        lVar.a(this.f17786c, 2);
        String str = this.f17787d;
        if (str != null) {
            lVar.a(str, 3);
        }
        String str2 = this.f17788e;
        if (str2 != null) {
            lVar.a(str2, 4);
        }
        bs bsVar = this.f17789f;
        if (bsVar != null) {
            lVar.a((m) bsVar, 5);
        }
        Map<String, String> map = this.f17790g;
        if (map != null) {
            lVar.a((Map) map, 6);
        }
        lVar.a(this.f17791h, 7);
        String str3 = this.f17792i;
        if (str3 != null) {
            lVar.a(str3, 8);
        }
        String str4 = this.f17793j;
        if (str4 != null) {
            lVar.a(str4, 9);
        }
        lVar.a(this.f17794k, 10);
        lVar.a(this.f17795l, 11);
    }

    public final Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException unused) {
            if (f17783o) {
                return null;
            }
            throw new AssertionError();
        }
    }

    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        bt btVar = (bt) obj;
        return n.a(this.f17784a, btVar.f17784a) && n.a(this.f17785b, btVar.f17785b) && n.a(this.f17786c, btVar.f17786c) && n.a(this.f17787d, btVar.f17787d) && n.a(this.f17788e, btVar.f17788e) && n.a(this.f17789f, btVar.f17789f) && n.a(this.f17790g, btVar.f17790g) && n.a(this.f17791h, btVar.f17791h) && n.a(this.f17792i, btVar.f17792i) && n.a(this.f17793j, btVar.f17793j) && n.a(this.f17794k, btVar.f17794k) && n.a(this.f17795l, btVar.f17795l);
    }

    public final int hashCode() throws Exception {
        try {
            throw new Exception("Need define key first!");
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(k kVar) {
        this.f17784a = kVar.a(0, true);
        this.f17785b = kVar.a(1, true);
        this.f17786c = kVar.a(2, true);
        this.f17787d = kVar.b(3, false);
        this.f17788e = kVar.b(4, false);
        this.f17789f = (bs) kVar.a((m) f17781m, 5, false);
        this.f17790g = (Map) kVar.a((k) f17782n, 6, false);
        this.f17791h = kVar.a(this.f17791h, 7, false);
        this.f17792i = kVar.b(8, false);
        this.f17793j = kVar.b(9, false);
        this.f17794k = kVar.a(this.f17794k, 10, false);
        this.f17795l = kVar.a(this.f17795l, 11, false);
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(StringBuilder sb, int i2) {
        i iVar = new i(sb, i2);
        iVar.a(this.f17784a, "enable");
        iVar.a(this.f17785b, "enableUserInfo");
        iVar.a(this.f17786c, "enableQuery");
        iVar.a(this.f17787d, "url");
        iVar.a(this.f17788e, "expUrl");
        iVar.a((m) this.f17789f, "security");
        iVar.a((Map) this.f17790g, "valueMap");
        iVar.a(this.f17791h, "strategylastUpdateTime");
        iVar.a(this.f17792i, "httpsUrl");
        iVar.a(this.f17793j, "httpsExpUrl");
        iVar.a(this.f17794k, "eventRecordCount");
        iVar.a(this.f17795l, "eventTimeInterval");
    }
}
