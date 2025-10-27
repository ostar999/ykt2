package com.tencent.liteav.beauty.b;

/* loaded from: classes6.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private boolean f18823a = false;

    public synchronized void a() {
        this.f18823a = true;
        notify();
    }

    public synchronized void b() throws InterruptedException {
        while (!this.f18823a) {
            wait();
        }
        this.f18823a = false;
    }
}
