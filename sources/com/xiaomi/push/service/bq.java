package com.xiaomi.push.service;

import com.xiaomi.push.service.XMPushService;

/* loaded from: classes6.dex */
class bq extends XMPushService.i {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ XMPushService f25663a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public bq(XMPushService xMPushService, int i2) {
        super(i2);
        this.f25663a = xMPushService;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    public String a() {
        return "disconnect for disable push";
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a */
    public void mo463a() {
        if (this.f25663a.f954a != null) {
            this.f25663a.f954a.m452b();
            this.f25663a.f954a = null;
        }
    }
}
