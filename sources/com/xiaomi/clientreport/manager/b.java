package com.xiaomi.clientreport.manager;

import com.xiaomi.push.bd;

/* loaded from: classes6.dex */
class b implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ a f24495a;

    public b(a aVar) {
        this.f24495a = aVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f24495a.f99a.execute(new bd(this.f24495a.f94a, this.f24495a.f96a));
    }
}
