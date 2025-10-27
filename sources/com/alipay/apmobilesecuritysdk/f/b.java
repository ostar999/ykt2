package com.alipay.apmobilesecuritysdk.f;

import java.util.LinkedList;

/* loaded from: classes2.dex */
public final class b {

    /* renamed from: a, reason: collision with root package name */
    private static b f3044a = new b();

    /* renamed from: b, reason: collision with root package name */
    private Thread f3045b = null;

    /* renamed from: c, reason: collision with root package name */
    private LinkedList<Runnable> f3046c = new LinkedList<>();

    public static b a() {
        return f3044a;
    }

    public static /* synthetic */ Thread b(b bVar) {
        bVar.f3045b = null;
        return null;
    }

    public final synchronized void a(Runnable runnable) {
        this.f3046c.add(runnable);
        if (this.f3045b == null) {
            Thread thread = new Thread(new c(this));
            this.f3045b = thread;
            thread.start();
        }
    }
}
