package com.tencent.tbs.one.impl;

import com.tencent.tbs.one.TBSOneCallback;

/* loaded from: classes6.dex */
public final class c<T> extends TBSOneCallback<T> {

    /* renamed from: a, reason: collision with root package name */
    public T f21799a;

    /* renamed from: b, reason: collision with root package name */
    public int f21800b = 109;

    /* renamed from: c, reason: collision with root package name */
    public String f21801c;

    /* renamed from: d, reason: collision with root package name */
    public boolean f21802d;

    private synchronized void a() {
        this.f21802d = true;
        notify();
    }

    public final synchronized void a(long j2) {
        if (!this.f21802d) {
            try {
                wait(j2);
            } catch (InterruptedException unused) {
                this.f21800b = 103;
                this.f21801c = "Failed to wait for loading result";
            }
        }
    }

    @Override // com.tencent.tbs.one.TBSOneCallback
    public final void onCompleted(T t2) {
        this.f21800b = 0;
        this.f21799a = t2;
        a();
    }

    @Override // com.tencent.tbs.one.TBSOneCallback
    public final void onError(int i2, String str) {
        this.f21800b = i2;
        this.f21801c = str;
        a();
    }

    @Override // com.tencent.tbs.one.TBSOneCallback
    public final void onProgressChanged(int i2, int i3) {
    }
}
