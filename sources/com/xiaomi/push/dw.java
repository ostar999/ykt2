package com.xiaomi.push;

import android.content.Context;
import android.content.Intent;

/* loaded from: classes6.dex */
class dw implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f24748a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ Intent f335a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ dv f336a;

    public dw(dv dvVar, Context context, Intent intent) {
        this.f336a = dvVar;
        this.f24748a = context;
        this.f335a = intent;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f336a.b(this.f24748a, this.f335a);
    }
}
