package com.mobile.auth.k;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private int f10411a;

    /* renamed from: b, reason: collision with root package name */
    private Map<String, List<String>> f10412b;

    /* renamed from: c, reason: collision with root package name */
    private String f10413c;

    public b(int i2, Map<String, List<String>> map, String str) {
        this.f10411a = i2;
        this.f10412b = map;
        this.f10413c = str;
    }

    public int a() {
        return this.f10411a;
    }

    public Map<String, List<String>> b() {
        Map<String, List<String>> map = this.f10412b;
        return map == null ? new HashMap() : map;
    }

    public String c() {
        String str = this.f10413c;
        return str == null ? "" : str;
    }

    public boolean d() {
        int i2 = this.f10411a;
        return i2 == 302 || i2 == 301;
    }
}
