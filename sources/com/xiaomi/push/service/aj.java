package com.xiaomi.push.service;

import android.app.NotificationManager;
import com.xiaomi.push.ai;

/* loaded from: classes6.dex */
final class aj extends ai.a {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ int f25578a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ NotificationManager f993a;

    public aj(int i2, NotificationManager notificationManager) {
        this.f25578a = i2;
        this.f993a = notificationManager;
    }

    @Override // com.xiaomi.push.ai.a
    /* renamed from: a */
    public int mo185a() {
        return this.f25578a;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f993a.cancel(this.f25578a);
    }
}
