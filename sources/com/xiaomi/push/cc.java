package com.xiaomi.push;

import android.content.Context;
import cn.hutool.core.text.StrPool;

/* loaded from: classes6.dex */
public class cc {

    /* renamed from: a, reason: collision with root package name */
    private long f24661a;

    /* renamed from: a, reason: collision with other field name */
    private Context f242a;

    /* renamed from: a, reason: collision with other field name */
    private String f243a;

    /* renamed from: b, reason: collision with root package name */
    private long f24662b;

    /* renamed from: c, reason: collision with root package name */
    private long f24663c;

    /* renamed from: d, reason: collision with root package name */
    private long f24664d;

    public cc(Context context) {
        this.f242a = context;
        m275a();
    }

    public long a() {
        return this.f24661a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m274a() {
        return this.f243a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m275a() {
        this.f243a = null;
        this.f24661a = 0L;
        this.f24662b = 0L;
        this.f24663c = 0L;
        this.f24664d = 0L;
    }

    public void a(String str) {
        e();
        m275a();
        b(str);
    }

    public long b() {
        return this.f24662b;
    }

    /* renamed from: b, reason: collision with other method in class */
    public void m276b() {
        this.f24662b += System.currentTimeMillis() - this.f24661a;
    }

    public void b(String str) {
        String strA = cj.a(this.f242a, str, "none");
        if (strA == null || "none".equals(strA)) {
            m275a();
            this.f243a = str;
            long jCurrentTimeMillis = System.currentTimeMillis();
            this.f24664d = jCurrentTimeMillis;
            this.f24663c = jCurrentTimeMillis;
            this.f24661a = jCurrentTimeMillis;
            return;
        }
        try {
            String[] strArrSplit = strA.split(StrPool.UNDERLINE);
            this.f243a = str;
            this.f24661a = Long.valueOf(strArrSplit[1]).longValue();
            this.f24662b = Long.valueOf(strArrSplit[2]).longValue();
            this.f24663c = Long.valueOf(strArrSplit[3]).longValue();
            this.f24664d = Long.valueOf(strArrSplit[4]).longValue();
        } catch (Exception unused) {
        }
    }

    public long c() {
        return this.f24664d;
    }

    /* renamed from: c, reason: collision with other method in class */
    public void m277c() {
        this.f24664d = System.currentTimeMillis();
    }

    public void d() {
        m276b();
        e();
        m275a();
    }

    public void e() {
        String str = this.f243a;
        if (str != null) {
            cj.m288a(this.f242a, str, toString());
        }
    }

    public String toString() {
        if (this.f243a == null) {
            return "";
        }
        return this.f243a + StrPool.UNDERLINE + this.f24661a + StrPool.UNDERLINE + this.f24662b + StrPool.UNDERLINE + this.f24663c + StrPool.UNDERLINE + this.f24664d;
    }
}
