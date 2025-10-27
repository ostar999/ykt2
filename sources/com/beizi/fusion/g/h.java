package com.beizi.fusion.g;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
public class h {

    /* renamed from: a, reason: collision with root package name */
    private static final AtomicBoolean f5194a = new AtomicBoolean();

    /* renamed from: b, reason: collision with root package name */
    private static volatile h f5195b;

    /* renamed from: c, reason: collision with root package name */
    private static volatile ThreadPoolExecutor f5196c;

    /* renamed from: d, reason: collision with root package name */
    private static volatile ThreadPoolExecutor f5197d;

    /* renamed from: e, reason: collision with root package name */
    private static volatile ThreadPoolExecutor f5198e;

    /* renamed from: f, reason: collision with root package name */
    private static volatile ThreadPoolExecutor f5199f;

    private h() {
        if (f5194a.get()) {
            return;
        }
        a();
    }

    public static void a() {
        AtomicBoolean atomicBoolean = f5194a;
        if (atomicBoolean.get()) {
            return;
        }
        f5196c = l.a();
        f5197d = l.b();
        f5198e = l.c();
        f5199f = l.d();
        atomicBoolean.set(true);
    }

    public static h b() {
        if (f5195b == null) {
            synchronized (h.class) {
                if (f5195b == null) {
                    f5195b = new h();
                }
            }
        }
        return f5195b;
    }

    public ExecutorService c() {
        if (f5196c == null) {
            f5196c = l.a();
        }
        return f5196c;
    }

    public ExecutorService d() {
        if (f5197d == null) {
            f5197d = l.b();
        }
        return f5197d;
    }

    public ExecutorService e() {
        if (f5198e == null) {
            f5198e = l.c();
        }
        return f5198e;
    }

    public ExecutorService f() {
        if (f5199f == null) {
            f5199f = l.d();
        }
        return f5199f;
    }
}
