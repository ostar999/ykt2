package com.xiaomi.clientreport.manager;

import com.xiaomi.push.bd;

/* loaded from: classes6.dex */
class c implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ a f24496a;

    public c(a aVar) {
        this.f24496a = aVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f24496a.f99a.execute(new bd(this.f24496a.f94a, this.f24496a.f97a));
    }
}
