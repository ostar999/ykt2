package com.xiaomi.push.service;

import com.xiaomi.push.service.XMPushService.f;
import com.xiaomi.push.service.at;

/* loaded from: classes6.dex */
class bx implements at.a {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ XMPushService f25670a;

    public bx(XMPushService xMPushService) {
        this.f25670a = xMPushService;
    }

    @Override // com.xiaomi.push.service.at.a
    public void a() {
        this.f25670a.e();
        if (at.a().m715a() <= 0) {
            XMPushService xMPushService = this.f25670a;
            xMPushService.a(xMPushService.new f(12, null));
        }
    }
}
