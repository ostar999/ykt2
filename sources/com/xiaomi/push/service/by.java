package com.xiaomi.push.service;

import android.database.ContentObserver;
import android.os.Handler;
import com.xiaomi.push.service.XMPushService.f;

/* loaded from: classes6.dex */
class by extends ContentObserver {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ XMPushService f25671a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public by(XMPushService xMPushService, Handler handler) {
        super(handler);
        this.f25671a = xMPushService;
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z2) {
        super.onChange(z2);
        boolean zM692f = this.f25671a.m692f();
        com.xiaomi.channel.commonutils.logger.b.m117a("ExtremePowerMode:" + zM692f);
        if (!zM692f) {
            this.f25671a.a(true);
        } else {
            XMPushService xMPushService = this.f25671a;
            xMPushService.a(xMPushService.new f(23, null));
        }
    }
}
