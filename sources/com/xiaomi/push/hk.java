package com.xiaomi.push;

import com.xiaomi.push.service.XMPushService;

/* loaded from: classes6.dex */
class hk extends XMPushService.i {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ hj f25062a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public hk(hj hjVar, int i2) {
        super(i2);
        this.f25062a = hjVar;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    public String a() {
        return "Handling bind stats";
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a */
    public void mo463a() {
        this.f25062a.c();
    }
}
