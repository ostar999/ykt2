package com.tencent.bugly.proguard;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public final class bv extends m implements Cloneable {

    /* renamed from: f, reason: collision with root package name */
    static ArrayList<bu> f17805f;

    /* renamed from: g, reason: collision with root package name */
    static Map<String, String> f17806g;

    /* renamed from: a, reason: collision with root package name */
    public byte f17807a = 0;

    /* renamed from: b, reason: collision with root package name */
    public String f17808b = "";

    /* renamed from: c, reason: collision with root package name */
    public String f17809c = "";

    /* renamed from: d, reason: collision with root package name */
    public ArrayList<bu> f17810d = null;

    /* renamed from: e, reason: collision with root package name */
    public Map<String, String> f17811e = null;

    @Override // com.tencent.bugly.proguard.m
    public final void a(l lVar) throws UnsupportedEncodingException {
        lVar.a(this.f17807a, 0);
        String str = this.f17808b;
        if (str != null) {
            lVar.a(str, 1);
        }
        String str2 = this.f17809c;
        if (str2 != null) {
            lVar.a(str2, 2);
        }
        ArrayList<bu> arrayList = this.f17810d;
        if (arrayList != null) {
            lVar.a((Collection) arrayList, 3);
        }
        Map<String, String> map = this.f17811e;
        if (map != null) {
            lVar.a((Map) map, 4);
        }
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(StringBuilder sb, int i2) {
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(k kVar) {
        this.f17807a = kVar.a(this.f17807a, 0, true);
        this.f17808b = kVar.b(1, false);
        this.f17809c = kVar.b(2, false);
        if (f17805f == null) {
            f17805f = new ArrayList<>();
            f17805f.add(new bu());
        }
        this.f17810d = (ArrayList) kVar.a((k) f17805f, 3, false);
        if (f17806g == null) {
            HashMap map = new HashMap();
            f17806g = map;
            map.put("", "");
        }
        this.f17811e = (Map) kVar.a((k) f17806g, 4, false);
    }
}
