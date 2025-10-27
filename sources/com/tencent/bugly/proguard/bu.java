package com.tencent.bugly.proguard;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public final class bu extends m {

    /* renamed from: i, reason: collision with root package name */
    static Map<String, String> f17796i;

    /* renamed from: a, reason: collision with root package name */
    public long f17797a = 0;

    /* renamed from: b, reason: collision with root package name */
    public byte f17798b = 0;

    /* renamed from: c, reason: collision with root package name */
    public String f17799c = "";

    /* renamed from: d, reason: collision with root package name */
    public String f17800d = "";

    /* renamed from: e, reason: collision with root package name */
    public String f17801e = "";

    /* renamed from: f, reason: collision with root package name */
    public Map<String, String> f17802f = null;

    /* renamed from: g, reason: collision with root package name */
    public String f17803g = "";

    /* renamed from: h, reason: collision with root package name */
    public boolean f17804h = true;

    static {
        HashMap map = new HashMap();
        f17796i = map;
        map.put("", "");
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(l lVar) throws UnsupportedEncodingException {
        lVar.a(this.f17797a, 0);
        lVar.a(this.f17798b, 1);
        String str = this.f17799c;
        if (str != null) {
            lVar.a(str, 2);
        }
        String str2 = this.f17800d;
        if (str2 != null) {
            lVar.a(str2, 3);
        }
        String str3 = this.f17801e;
        if (str3 != null) {
            lVar.a(str3, 4);
        }
        Map<String, String> map = this.f17802f;
        if (map != null) {
            lVar.a((Map) map, 5);
        }
        String str4 = this.f17803g;
        if (str4 != null) {
            lVar.a(str4, 6);
        }
        lVar.a(this.f17804h, 7);
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(k kVar) {
        this.f17797a = kVar.a(this.f17797a, 0, true);
        this.f17798b = kVar.a(this.f17798b, 1, true);
        this.f17799c = kVar.b(2, false);
        this.f17800d = kVar.b(3, false);
        this.f17801e = kVar.b(4, false);
        this.f17802f = (Map) kVar.a((k) f17796i, 5, false);
        this.f17803g = kVar.b(6, false);
        this.f17804h = kVar.a(7, false);
    }
}
