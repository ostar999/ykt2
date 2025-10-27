package com.huawei.hms.hatool;

/* loaded from: classes4.dex */
public class m {

    /* renamed from: a, reason: collision with root package name */
    public k f7817a;

    /* renamed from: b, reason: collision with root package name */
    public k f7818b;

    /* renamed from: c, reason: collision with root package name */
    public k f7819c;

    /* renamed from: d, reason: collision with root package name */
    public k f7820d;

    public m(String str) {
    }

    public k a() {
        return this.f7819c;
    }

    public k a(String str) {
        if (str.equals("oper")) {
            return c();
        }
        if (str.equals("maint")) {
            return b();
        }
        if (str.equals("diffprivacy")) {
            return a();
        }
        if (str.equals("preins")) {
            return d();
        }
        y.f("hmsSdk", "HiAnalyticsInstData.getConfig(type): wrong type: " + str);
        return null;
    }

    public void a(k kVar) {
        this.f7817a = kVar;
    }

    public k b() {
        return this.f7817a;
    }

    public void b(k kVar) {
        this.f7818b = kVar;
    }

    public k c() {
        return this.f7818b;
    }

    public k d() {
        return this.f7820d;
    }
}
