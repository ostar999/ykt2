package com.xiaomi.push.service;

import com.xiaomi.push.service.XMPushService;

/* loaded from: classes6.dex */
class bs extends XMPushService.i {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ XMPushService f25665a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public bs(XMPushService xMPushService, int i2) {
        super(i2);
        this.f25665a = xMPushService;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    public String a() {
        return "disconnect for service destroy.";
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a */
    public void mo463a() {
        if (this.f25665a.f954a != null) {
            this.f25665a.f954a.b(15, (Exception) null);
            this.f25665a.f954a = null;
        }
    }
}
