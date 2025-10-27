package com.meizu.cloud.pushsdk.b.b;

import com.meizu.cloud.pushsdk.b.c.k;

/* loaded from: classes4.dex */
public class a extends Exception {

    /* renamed from: a, reason: collision with root package name */
    private String f9067a;

    /* renamed from: b, reason: collision with root package name */
    private int f9068b;

    /* renamed from: c, reason: collision with root package name */
    private String f9069c;

    /* renamed from: d, reason: collision with root package name */
    private k f9070d;

    public a() {
        this.f9068b = 0;
    }

    public a(k kVar) {
        this.f9068b = 0;
        this.f9070d = kVar;
    }

    public a(Throwable th) {
        super(th);
        this.f9068b = 0;
    }

    public k a() {
        return this.f9070d;
    }

    public void a(int i2) {
        this.f9068b = i2;
    }

    public void a(String str) {
        this.f9069c = str;
    }

    public int b() {
        return this.f9068b;
    }

    public void b(String str) {
        this.f9067a = str;
    }

    public String c() {
        return this.f9067a;
    }
}
