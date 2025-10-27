package com.xiaomi.push.service;

import com.xiaomi.push.fv;
import com.xiaomi.push.gh;
import com.xiaomi.push.gt;
import com.xiaomi.push.service.XMPushService.c;
import com.xiaomi.push.service.XMPushService.k;

/* loaded from: classes6.dex */
class bp implements gh {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ XMPushService f25662a;

    public bp(XMPushService xMPushService) {
        this.f25662a = xMPushService;
    }

    @Override // com.xiaomi.push.gh
    public void a(fv fvVar) {
        XMPushService xMPushService = this.f25662a;
        xMPushService.a(xMPushService.new c(fvVar));
    }

    @Override // com.xiaomi.push.gh
    public void a(gt gtVar) {
        XMPushService xMPushService = this.f25662a;
        xMPushService.a(xMPushService.new k(gtVar));
    }
}
