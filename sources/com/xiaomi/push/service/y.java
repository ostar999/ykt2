package com.xiaomi.push.service;

import com.xiaomi.push.gn;
import com.xiaomi.push.jb;
import com.xiaomi.push.service.XMPushService;

/* loaded from: classes6.dex */
final class y extends XMPushService.i {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ jb f25726a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ XMPushService f1097a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public y(int i2, XMPushService xMPushService, jb jbVar) {
        super(i2);
        this.f1097a = xMPushService;
        this.f25726a = jbVar;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    public String a() {
        return "send app absent message.";
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a */
    public void mo463a() {
        try {
            af.a(this.f1097a, af.a(this.f25726a.b(), this.f25726a.m596a()));
        } catch (gn e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            this.f1097a.a(10, e2);
        }
    }
}
