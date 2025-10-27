package com.alibaba.sdk.android.emas;

import java.util.List;

/* loaded from: classes2.dex */
public class d {

    /* renamed from: b, reason: collision with root package name */
    private List<e> f2689b;

    /* renamed from: c, reason: collision with root package name */
    private b f2690c;

    /* renamed from: c, reason: collision with other field name */
    private String f8c;

    public d(List<e> list) {
        this(list, b.MEM_CACHE, null);
    }

    public b a() {
        return this.f2690c;
    }

    public String getLocation() {
        if (this.f2690c == b.DISK_CACHE) {
            return this.f8c;
        }
        return null;
    }

    public d(List<e> list, b bVar, String str) {
        this.f2689b = list;
        this.f2690c = bVar;
        this.f8c = str;
    }

    /* renamed from: a, reason: collision with other method in class */
    public List<e> m24a() {
        return this.f2689b;
    }
}
