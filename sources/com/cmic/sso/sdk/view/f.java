package com.cmic.sso.sdk.view;

/* loaded from: classes3.dex */
public class f {

    /* renamed from: a, reason: collision with root package name */
    private static f f6518a;

    /* renamed from: b, reason: collision with root package name */
    private a f6519b;

    public interface a {
        void a();
    }

    public static f a() {
        if (f6518a == null) {
            synchronized (f.class) {
                if (f6518a == null) {
                    f6518a = new f();
                }
            }
        }
        return f6518a;
    }

    public void a(a aVar) {
        this.f6519b = aVar;
    }

    public a b() {
        return this.f6519b;
    }

    public void c() {
        if (this.f6519b != null) {
            this.f6519b = null;
        }
    }
}
