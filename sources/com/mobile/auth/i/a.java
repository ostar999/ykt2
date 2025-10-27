package com.mobile.auth.i;

import java.net.URLEncoder;

/* loaded from: classes4.dex */
public abstract class a extends g {

    /* renamed from: a, reason: collision with root package name */
    protected String f10339a = "";

    /* renamed from: b, reason: collision with root package name */
    protected String f10340b = "";

    /* renamed from: c, reason: collision with root package name */
    protected String f10341c = "";

    /* renamed from: d, reason: collision with root package name */
    protected String f10342d = "";

    /* renamed from: e, reason: collision with root package name */
    protected String f10343e = "";

    /* renamed from: f, reason: collision with root package name */
    protected String f10344f = "";

    /* renamed from: g, reason: collision with root package name */
    protected String f10345g = "";

    /* renamed from: h, reason: collision with root package name */
    protected String f10346h = "";

    /* renamed from: i, reason: collision with root package name */
    protected String f10347i = "";

    /* renamed from: j, reason: collision with root package name */
    protected String f10348j = "0";

    /* renamed from: k, reason: collision with root package name */
    protected String f10349k = "1.0";

    /* renamed from: l, reason: collision with root package name */
    protected String f10350l = "";

    /* renamed from: m, reason: collision with root package name */
    protected String f10351m = "";

    /* renamed from: n, reason: collision with root package name */
    protected String f10352n = "";

    /* renamed from: o, reason: collision with root package name */
    protected String f10353o = "";

    /* renamed from: p, reason: collision with root package name */
    protected String f10354p = "";

    /* renamed from: q, reason: collision with root package name */
    protected String f10355q = "";

    /* renamed from: r, reason: collision with root package name */
    protected String f10356r = "";

    /* renamed from: s, reason: collision with root package name */
    protected String f10357s = "";

    /* renamed from: t, reason: collision with root package name */
    protected String f10358t = "";

    /* renamed from: u, reason: collision with root package name */
    protected String f10359u = "001";

    /* renamed from: v, reason: collision with root package name */
    protected String f10360v = "";

    /* renamed from: w, reason: collision with root package name */
    protected String f10361w = "";

    @Override // com.mobile.auth.i.g
    public String a() {
        return this.f10341c;
    }

    public void a_(String str) {
        this.f10360v = str;
    }

    public void b(String str) {
        this.f10339a = t(str);
    }

    public void c(String str) {
        this.f10340b = t(str);
    }

    public void d(String str) {
        this.f10341c = t(str);
    }

    public void e(String str) {
        this.f10343e = t(str);
    }

    public void f(String str) {
        this.f10344f = t(str);
    }

    public void g(String str) {
        this.f10345g = URLEncoder.encode(t(str));
    }

    public void h(String str) {
        this.f10346h = URLEncoder.encode(t(str));
    }

    public void i(String str) {
        this.f10347i = URLEncoder.encode(t(str));
    }

    public void j(String str) {
        this.f10348j = t(str);
    }

    public void k(String str) {
        this.f10349k = t(str);
    }

    public void l(String str) {
        this.f10351m = t(str);
    }

    public void m(String str) {
        this.f10352n = t(str);
    }

    public void n(String str) {
        this.f10354p = t(str);
    }

    public void o(String str) {
        this.f10355q = t(str);
    }

    public void p(String str) {
        this.f10356r = t(str);
    }

    public void q(String str) {
        this.f10357s = t(str);
    }

    public void r(String str) {
        this.f10358t = t(str);
    }

    public void s(String str) {
        this.f10361w = str;
    }

    public final String t(String str) {
        return str == null ? "" : str;
    }
}
