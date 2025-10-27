package com.beizi.ad.a.a;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private static final AtomicBoolean f3689a = new AtomicBoolean();

    /* renamed from: b, reason: collision with root package name */
    private static volatile c f3690b;

    /* renamed from: c, reason: collision with root package name */
    private static volatile ThreadPoolExecutor f3691c;

    /* renamed from: d, reason: collision with root package name */
    private static volatile ThreadPoolExecutor f3692d;

    /* renamed from: e, reason: collision with root package name */
    private static volatile ThreadPoolExecutor f3693e;

    private c() {
        if (f3689a.get()) {
            return;
        }
        a();
    }

    public static void a() {
        AtomicBoolean atomicBoolean = f3689a;
        if (atomicBoolean.get()) {
            return;
        }
        f3691c = e.a();
        f3692d = e.b();
        f3693e = e.c();
        atomicBoolean.set(true);
    }

    public static c b() {
        if (f3690b == null) {
            synchronized (c.class) {
                if (f3690b == null) {
                    f3690b = new c();
                }
            }
        }
        return f3690b;
    }

    public ExecutorService c() {
        if (f3691c == null) {
            f3691c = e.a();
        }
        return f3691c;
    }

    public ExecutorService d() {
        if (f3693e == null) {
            f3693e = e.c();
        }
        return f3693e;
    }
}
