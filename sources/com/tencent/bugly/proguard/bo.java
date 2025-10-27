package com.tencent.bugly.proguard;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public final class bo extends m {
    static ArrayList<bn> A;
    static Map<String, String> B;
    static Map<String, String> C;

    /* renamed from: v, reason: collision with root package name */
    static Map<String, String> f17715v;

    /* renamed from: w, reason: collision with root package name */
    static bm f17716w;

    /* renamed from: x, reason: collision with root package name */
    static bl f17717x;

    /* renamed from: y, reason: collision with root package name */
    static ArrayList<bl> f17718y;

    /* renamed from: z, reason: collision with root package name */
    static ArrayList<bl> f17719z;

    /* renamed from: a, reason: collision with root package name */
    public String f17720a = "";

    /* renamed from: b, reason: collision with root package name */
    public long f17721b = 0;

    /* renamed from: c, reason: collision with root package name */
    public String f17722c = "";

    /* renamed from: d, reason: collision with root package name */
    public String f17723d = "";

    /* renamed from: e, reason: collision with root package name */
    public String f17724e = "";

    /* renamed from: f, reason: collision with root package name */
    public String f17725f = "";

    /* renamed from: g, reason: collision with root package name */
    public String f17726g = "";

    /* renamed from: h, reason: collision with root package name */
    public Map<String, String> f17727h = null;

    /* renamed from: i, reason: collision with root package name */
    public String f17728i = "";

    /* renamed from: j, reason: collision with root package name */
    public bm f17729j = null;

    /* renamed from: k, reason: collision with root package name */
    public int f17730k = 0;

    /* renamed from: l, reason: collision with root package name */
    public String f17731l = "";

    /* renamed from: m, reason: collision with root package name */
    public String f17732m = "";

    /* renamed from: n, reason: collision with root package name */
    public bl f17733n = null;

    /* renamed from: o, reason: collision with root package name */
    public ArrayList<bl> f17734o = null;

    /* renamed from: p, reason: collision with root package name */
    public ArrayList<bl> f17735p = null;

    /* renamed from: q, reason: collision with root package name */
    public ArrayList<bn> f17736q = null;

    /* renamed from: r, reason: collision with root package name */
    public Map<String, String> f17737r = null;

    /* renamed from: s, reason: collision with root package name */
    public Map<String, String> f17738s = null;

    /* renamed from: t, reason: collision with root package name */
    public String f17739t = "";

    /* renamed from: u, reason: collision with root package name */
    public boolean f17740u = true;

    static {
        HashMap map = new HashMap();
        f17715v = map;
        map.put("", "");
        f17716w = new bm();
        f17717x = new bl();
        f17718y = new ArrayList<>();
        f17718y.add(new bl());
        f17719z = new ArrayList<>();
        f17719z.add(new bl());
        A = new ArrayList<>();
        A.add(new bn());
        HashMap map2 = new HashMap();
        B = map2;
        map2.put("", "");
        HashMap map3 = new HashMap();
        C = map3;
        map3.put("", "");
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(l lVar) throws UnsupportedEncodingException {
        lVar.a(this.f17720a, 0);
        lVar.a(this.f17721b, 1);
        lVar.a(this.f17722c, 2);
        String str = this.f17723d;
        if (str != null) {
            lVar.a(str, 3);
        }
        String str2 = this.f17724e;
        if (str2 != null) {
            lVar.a(str2, 4);
        }
        String str3 = this.f17725f;
        if (str3 != null) {
            lVar.a(str3, 5);
        }
        String str4 = this.f17726g;
        if (str4 != null) {
            lVar.a(str4, 6);
        }
        Map<String, String> map = this.f17727h;
        if (map != null) {
            lVar.a((Map) map, 7);
        }
        String str5 = this.f17728i;
        if (str5 != null) {
            lVar.a(str5, 8);
        }
        bm bmVar = this.f17729j;
        if (bmVar != null) {
            lVar.a((m) bmVar, 9);
        }
        lVar.a(this.f17730k, 10);
        String str6 = this.f17731l;
        if (str6 != null) {
            lVar.a(str6, 11);
        }
        String str7 = this.f17732m;
        if (str7 != null) {
            lVar.a(str7, 12);
        }
        bl blVar = this.f17733n;
        if (blVar != null) {
            lVar.a((m) blVar, 13);
        }
        ArrayList<bl> arrayList = this.f17734o;
        if (arrayList != null) {
            lVar.a((Collection) arrayList, 14);
        }
        ArrayList<bl> arrayList2 = this.f17735p;
        if (arrayList2 != null) {
            lVar.a((Collection) arrayList2, 15);
        }
        ArrayList<bn> arrayList3 = this.f17736q;
        if (arrayList3 != null) {
            lVar.a((Collection) arrayList3, 16);
        }
        Map<String, String> map2 = this.f17737r;
        if (map2 != null) {
            lVar.a((Map) map2, 17);
        }
        Map<String, String> map3 = this.f17738s;
        if (map3 != null) {
            lVar.a((Map) map3, 18);
        }
        String str8 = this.f17739t;
        if (str8 != null) {
            lVar.a(str8, 19);
        }
        lVar.a(this.f17740u, 20);
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(k kVar) {
        this.f17720a = kVar.b(0, true);
        this.f17721b = kVar.a(this.f17721b, 1, true);
        this.f17722c = kVar.b(2, true);
        this.f17723d = kVar.b(3, false);
        this.f17724e = kVar.b(4, false);
        this.f17725f = kVar.b(5, false);
        this.f17726g = kVar.b(6, false);
        this.f17727h = (Map) kVar.a((k) f17715v, 7, false);
        this.f17728i = kVar.b(8, false);
        this.f17729j = (bm) kVar.a((m) f17716w, 9, false);
        this.f17730k = kVar.a(this.f17730k, 10, false);
        this.f17731l = kVar.b(11, false);
        this.f17732m = kVar.b(12, false);
        this.f17733n = (bl) kVar.a((m) f17717x, 13, false);
        this.f17734o = (ArrayList) kVar.a((k) f17718y, 14, false);
        this.f17735p = (ArrayList) kVar.a((k) f17719z, 15, false);
        this.f17736q = (ArrayList) kVar.a((k) A, 16, false);
        this.f17737r = (Map) kVar.a((k) B, 17, false);
        this.f17738s = (Map) kVar.a((k) C, 18, false);
        this.f17739t = kVar.b(19, false);
        this.f17740u = kVar.a(20, false);
    }
}
