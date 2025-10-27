package com.xiaomi.push;

import com.xiaomi.push.service.XMPushService;

/* loaded from: classes6.dex */
class gk extends XMPushService.i {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ long f24941a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ gj f492a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public gk(gj gjVar, int i2, long j2) {
        super(i2);
        this.f492a = gjVar;
        this.f24941a = j2;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    public String a() {
        return "check the ping-pong." + this.f24941a;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a, reason: collision with other method in class */
    public void mo463a() {
        Thread.yield();
        if (!this.f492a.m454c() || this.f492a.a(this.f24941a)) {
            return;
        }
        this.f492a.f24935b.a(22, (Exception) null);
        this.f492a.f24935b.a(true);
    }
}
