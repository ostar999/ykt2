package com.xiaomi.push.service;

import com.xiaomi.push.service.XMPushService;

/* loaded from: classes6.dex */
class bz extends XMPushService.i {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ XMPushService f25672a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public bz(XMPushService xMPushService, int i2) {
        super(i2);
        this.f25672a = xMPushService;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    public String a() {
        return "prepare the mi push account.";
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a */
    public void mo463a() {
        af.a(this.f25672a);
        if (com.xiaomi.push.as.b(this.f25672a)) {
            this.f25672a.a(true);
        }
    }
}
