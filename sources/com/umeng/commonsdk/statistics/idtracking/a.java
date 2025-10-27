package com.umeng.commonsdk.statistics.idtracking;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes6.dex */
public abstract class a {

    /* renamed from: a, reason: collision with root package name */
    private final int f23352a = 10;

    /* renamed from: b, reason: collision with root package name */
    private final int f23353b = 100;

    /* renamed from: c, reason: collision with root package name */
    private final String f23354c;

    /* renamed from: d, reason: collision with root package name */
    private List<com.umeng.commonsdk.statistics.proto.a> f23355d;

    /* renamed from: e, reason: collision with root package name */
    private com.umeng.commonsdk.statistics.proto.b f23356e;

    public a(String str) {
        this.f23354c = str;
    }

    private boolean g() {
        com.umeng.commonsdk.statistics.proto.b bVar = this.f23356e;
        String strB = bVar == null ? null : bVar.b();
        int iH = bVar == null ? 0 : bVar.h();
        String strA = a(f());
        if (strA == null || strA.equals(strB)) {
            return false;
        }
        if (bVar == null) {
            bVar = new com.umeng.commonsdk.statistics.proto.b();
        }
        bVar.a(strA);
        bVar.a(System.currentTimeMillis());
        bVar.a(iH + 1);
        com.umeng.commonsdk.statistics.proto.a aVar = new com.umeng.commonsdk.statistics.proto.a();
        aVar.a(this.f23354c);
        aVar.c(strA);
        aVar.b(strB);
        aVar.a(bVar.e());
        if (this.f23355d == null) {
            this.f23355d = new ArrayList(2);
        }
        this.f23355d.add(aVar);
        if (this.f23355d.size() > 10) {
            this.f23355d.remove(0);
        }
        this.f23356e = bVar;
        return true;
    }

    public boolean a() {
        return g();
    }

    public String b() {
        return this.f23354c;
    }

    public boolean c() {
        com.umeng.commonsdk.statistics.proto.b bVar = this.f23356e;
        return bVar == null || bVar.h() <= 100;
    }

    public com.umeng.commonsdk.statistics.proto.b d() {
        return this.f23356e;
    }

    public List<com.umeng.commonsdk.statistics.proto.a> e() {
        return this.f23355d;
    }

    public abstract String f();

    public void a(com.umeng.commonsdk.statistics.proto.b bVar) {
        this.f23356e = bVar;
    }

    public void a(List<com.umeng.commonsdk.statistics.proto.a> list) {
        this.f23355d = list;
    }

    public String a(String str) {
        if (str == null) {
            return null;
        }
        String strTrim = str.trim();
        if (strTrim.length() == 0 || "0".equals(strTrim) || "unknown".equals(strTrim.toLowerCase(Locale.US))) {
            return null;
        }
        return strTrim;
    }

    public void a(com.umeng.commonsdk.statistics.proto.c cVar) {
        this.f23356e = cVar.c().get(this.f23354c);
        List<com.umeng.commonsdk.statistics.proto.a> listH = cVar.h();
        if (listH == null || listH.size() <= 0) {
            return;
        }
        if (this.f23355d == null) {
            this.f23355d = new ArrayList();
        }
        for (com.umeng.commonsdk.statistics.proto.a aVar : listH) {
            if (this.f23354c.equals(aVar.f23418a)) {
                this.f23355d.add(aVar);
            }
        }
    }
}
