package com.xiaomi.push;

/* loaded from: classes6.dex */
public class dt {

    /* renamed from: a, reason: collision with root package name */
    private static volatile dt f24747a;

    /* renamed from: a, reason: collision with other field name */
    private ds f334a;

    public static dt a() {
        if (f24747a == null) {
            synchronized (dt.class) {
                if (f24747a == null) {
                    f24747a = new dt();
                }
            }
        }
        return f24747a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public ds m331a() {
        return this.f334a;
    }

    public void a(ds dsVar) {
        this.f334a = dsVar;
    }
}
