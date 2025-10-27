package com.tencent.bugly.proguard;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public final class br extends m {

    /* renamed from: i, reason: collision with root package name */
    static byte[] f17769i = {0};

    /* renamed from: j, reason: collision with root package name */
    static Map<String, String> f17770j;

    /* renamed from: a, reason: collision with root package name */
    public byte f17771a = 0;

    /* renamed from: b, reason: collision with root package name */
    public int f17772b = 0;

    /* renamed from: c, reason: collision with root package name */
    public byte[] f17773c = null;

    /* renamed from: d, reason: collision with root package name */
    public String f17774d = "";

    /* renamed from: e, reason: collision with root package name */
    public long f17775e = 0;

    /* renamed from: f, reason: collision with root package name */
    public String f17776f = "";

    /* renamed from: g, reason: collision with root package name */
    public String f17777g = "";

    /* renamed from: h, reason: collision with root package name */
    public Map<String, String> f17778h = null;

    static {
        HashMap map = new HashMap();
        f17770j = map;
        map.put("", "");
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(l lVar) throws UnsupportedEncodingException {
        lVar.a(this.f17771a, 0);
        lVar.a(this.f17772b, 1);
        byte[] bArr = this.f17773c;
        if (bArr != null) {
            lVar.a(bArr, 2);
        }
        String str = this.f17774d;
        if (str != null) {
            lVar.a(str, 3);
        }
        lVar.a(this.f17775e, 4);
        String str2 = this.f17776f;
        if (str2 != null) {
            lVar.a(str2, 5);
        }
        String str3 = this.f17777g;
        if (str3 != null) {
            lVar.a(str3, 6);
        }
        Map<String, String> map = this.f17778h;
        if (map != null) {
            lVar.a((Map) map, 7);
        }
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(k kVar) {
        this.f17771a = kVar.a(this.f17771a, 0, true);
        this.f17772b = kVar.a(this.f17772b, 1, true);
        this.f17773c = kVar.c(2, false);
        this.f17774d = kVar.b(3, false);
        this.f17775e = kVar.a(this.f17775e, 4, false);
        this.f17776f = kVar.b(5, false);
        this.f17777g = kVar.b(6, false);
        this.f17778h = (Map) kVar.a((k) f17770j, 7, false);
    }
}
