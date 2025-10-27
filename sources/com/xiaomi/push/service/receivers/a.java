package com.xiaomi.push.service.receivers;

import android.content.Context;

/* loaded from: classes6.dex */
class a implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f25711a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ NetworkStatusReceiver f1089a;

    public a(NetworkStatusReceiver networkStatusReceiver, Context context) {
        this.f1089a = networkStatusReceiver;
        this.f25711a = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f1089a.a(this.f25711a);
    }
}
