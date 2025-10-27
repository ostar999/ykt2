package com.tencent.open.log;

import com.tencent.open.log.d;

/* loaded from: classes6.dex */
public abstract class Tracer {

    /* renamed from: a, reason: collision with root package name */
    private volatile int f20595a;

    /* renamed from: b, reason: collision with root package name */
    private volatile boolean f20596b;

    /* renamed from: c, reason: collision with root package name */
    private g f20597c;

    public Tracer() {
        this(c.f20621a, true, g.f20642a);
    }

    public void a(int i2, Thread thread, long j2, String str, String str2, Throwable th) {
        if (d() && d.a.a(this.f20595a, i2)) {
            doTrace(i2, thread, j2, str, str2, th);
        }
    }

    public boolean d() {
        return this.f20596b;
    }

    public abstract void doTrace(int i2, Thread thread, long j2, String str, String str2, Throwable th);

    public g e() {
        return this.f20597c;
    }

    public Tracer(int i2, boolean z2, g gVar) {
        this.f20595a = c.f20621a;
        this.f20596b = true;
        this.f20597c = g.f20642a;
        a(i2);
        a(z2);
        a(gVar);
    }

    public void a(int i2) {
        this.f20595a = i2;
    }

    public void a(boolean z2) {
        this.f20596b = z2;
    }

    public void a(g gVar) {
        this.f20597c = gVar;
    }
}
