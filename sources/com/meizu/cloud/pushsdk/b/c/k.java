package com.meizu.cloud.pushsdk.b.c;

import com.meizu.cloud.pushsdk.b.c.c;

/* loaded from: classes4.dex */
public class k {

    /* renamed from: a, reason: collision with root package name */
    private final i f9146a;

    /* renamed from: b, reason: collision with root package name */
    private final int f9147b;

    /* renamed from: c, reason: collision with root package name */
    private final String f9148c;

    /* renamed from: d, reason: collision with root package name */
    private final c f9149d;

    /* renamed from: e, reason: collision with root package name */
    private final l f9150e;

    /* renamed from: f, reason: collision with root package name */
    private k f9151f;

    /* renamed from: g, reason: collision with root package name */
    private k f9152g;

    /* renamed from: h, reason: collision with root package name */
    private final k f9153h;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private i f9154a;

        /* renamed from: c, reason: collision with root package name */
        private String f9156c;

        /* renamed from: e, reason: collision with root package name */
        private l f9158e;

        /* renamed from: f, reason: collision with root package name */
        private k f9159f;

        /* renamed from: g, reason: collision with root package name */
        private k f9160g;

        /* renamed from: h, reason: collision with root package name */
        private k f9161h;

        /* renamed from: b, reason: collision with root package name */
        private int f9155b = -1;

        /* renamed from: d, reason: collision with root package name */
        private c.a f9157d = new c.a();

        public a a(int i2) {
            this.f9155b = i2;
            return this;
        }

        public a a(c cVar) {
            this.f9157d = cVar.c();
            return this;
        }

        public a a(i iVar) {
            this.f9154a = iVar;
            return this;
        }

        public a a(l lVar) {
            this.f9158e = lVar;
            return this;
        }

        public a a(String str) {
            this.f9156c = str;
            return this;
        }

        public k a() {
            if (this.f9154a == null) {
                throw new IllegalStateException("request == null");
            }
            if (this.f9155b >= 0) {
                return new k(this);
            }
            throw new IllegalStateException("code < 0: " + this.f9155b);
        }
    }

    private k(a aVar) {
        this.f9146a = aVar.f9154a;
        this.f9147b = aVar.f9155b;
        this.f9148c = aVar.f9156c;
        this.f9149d = aVar.f9157d.a();
        this.f9150e = aVar.f9158e;
        this.f9151f = aVar.f9159f;
        this.f9152g = aVar.f9160g;
        this.f9153h = aVar.f9161h;
    }

    public int a() {
        return this.f9147b;
    }

    public l b() {
        return this.f9150e;
    }

    public String toString() {
        return "Response{protocol=, code=" + this.f9147b + ", message=" + this.f9148c + ", url=" + this.f9146a.a() + '}';
    }
}
