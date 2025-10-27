package com.tencent.bugly.proguard;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public final class bq extends m {

    /* renamed from: y, reason: collision with root package name */
    static byte[] f17743y = {0};

    /* renamed from: z, reason: collision with root package name */
    static Map<String, String> f17744z;

    /* renamed from: a, reason: collision with root package name */
    public int f17745a = 0;

    /* renamed from: b, reason: collision with root package name */
    public String f17746b = "";

    /* renamed from: c, reason: collision with root package name */
    public String f17747c = "";

    /* renamed from: d, reason: collision with root package name */
    public String f17748d = "";

    /* renamed from: e, reason: collision with root package name */
    public String f17749e = "";

    /* renamed from: f, reason: collision with root package name */
    public String f17750f = "";

    /* renamed from: g, reason: collision with root package name */
    public int f17751g = 0;

    /* renamed from: h, reason: collision with root package name */
    public byte[] f17752h = null;

    /* renamed from: i, reason: collision with root package name */
    public String f17753i = "";

    /* renamed from: j, reason: collision with root package name */
    public String f17754j = "";

    /* renamed from: k, reason: collision with root package name */
    public Map<String, String> f17755k = null;

    /* renamed from: l, reason: collision with root package name */
    public String f17756l = "";

    /* renamed from: m, reason: collision with root package name */
    public long f17757m = 0;

    /* renamed from: n, reason: collision with root package name */
    public String f17758n = "";

    /* renamed from: o, reason: collision with root package name */
    public String f17759o = "";

    /* renamed from: p, reason: collision with root package name */
    public String f17760p = "";

    /* renamed from: q, reason: collision with root package name */
    public long f17761q = 0;

    /* renamed from: r, reason: collision with root package name */
    public String f17762r = "";

    /* renamed from: s, reason: collision with root package name */
    public String f17763s = "";

    /* renamed from: t, reason: collision with root package name */
    public String f17764t = "";

    /* renamed from: u, reason: collision with root package name */
    public String f17765u = "";

    /* renamed from: v, reason: collision with root package name */
    public String f17766v = "";

    /* renamed from: w, reason: collision with root package name */
    public String f17767w = "";

    /* renamed from: x, reason: collision with root package name */
    public String f17768x = "";

    static {
        HashMap map = new HashMap();
        f17744z = map;
        map.put("", "");
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(l lVar) throws UnsupportedEncodingException {
        lVar.a(this.f17745a, 0);
        lVar.a(this.f17746b, 1);
        lVar.a(this.f17747c, 2);
        lVar.a(this.f17748d, 3);
        String str = this.f17749e;
        if (str != null) {
            lVar.a(str, 4);
        }
        lVar.a(this.f17750f, 5);
        lVar.a(this.f17751g, 6);
        lVar.a(this.f17752h, 7);
        String str2 = this.f17753i;
        if (str2 != null) {
            lVar.a(str2, 8);
        }
        String str3 = this.f17754j;
        if (str3 != null) {
            lVar.a(str3, 9);
        }
        Map<String, String> map = this.f17755k;
        if (map != null) {
            lVar.a((Map) map, 10);
        }
        String str4 = this.f17756l;
        if (str4 != null) {
            lVar.a(str4, 11);
        }
        lVar.a(this.f17757m, 12);
        String str5 = this.f17758n;
        if (str5 != null) {
            lVar.a(str5, 13);
        }
        String str6 = this.f17759o;
        if (str6 != null) {
            lVar.a(str6, 14);
        }
        String str7 = this.f17760p;
        if (str7 != null) {
            lVar.a(str7, 15);
        }
        lVar.a(this.f17761q, 16);
        String str8 = this.f17762r;
        if (str8 != null) {
            lVar.a(str8, 17);
        }
        String str9 = this.f17763s;
        if (str9 != null) {
            lVar.a(str9, 18);
        }
        String str10 = this.f17764t;
        if (str10 != null) {
            lVar.a(str10, 19);
        }
        String str11 = this.f17765u;
        if (str11 != null) {
            lVar.a(str11, 20);
        }
        String str12 = this.f17766v;
        if (str12 != null) {
            lVar.a(str12, 21);
        }
        String str13 = this.f17767w;
        if (str13 != null) {
            lVar.a(str13, 22);
        }
        String str14 = this.f17768x;
        if (str14 != null) {
            lVar.a(str14, 23);
        }
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(k kVar) {
        this.f17745a = kVar.a(this.f17745a, 0, true);
        this.f17746b = kVar.b(1, true);
        this.f17747c = kVar.b(2, true);
        this.f17748d = kVar.b(3, true);
        this.f17749e = kVar.b(4, false);
        this.f17750f = kVar.b(5, true);
        this.f17751g = kVar.a(this.f17751g, 6, true);
        this.f17752h = kVar.c(7, true);
        this.f17753i = kVar.b(8, false);
        this.f17754j = kVar.b(9, false);
        this.f17755k = (Map) kVar.a((k) f17744z, 10, false);
        this.f17756l = kVar.b(11, false);
        this.f17757m = kVar.a(this.f17757m, 12, false);
        this.f17758n = kVar.b(13, false);
        this.f17759o = kVar.b(14, false);
        this.f17760p = kVar.b(15, false);
        this.f17761q = kVar.a(this.f17761q, 16, false);
        this.f17762r = kVar.b(17, false);
        this.f17763s = kVar.b(18, false);
        this.f17764t = kVar.b(19, false);
        this.f17765u = kVar.b(20, false);
        this.f17766v = kVar.b(21, false);
        this.f17767w = kVar.b(22, false);
        this.f17768x = kVar.b(23, false);
    }
}
