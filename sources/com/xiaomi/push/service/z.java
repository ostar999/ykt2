package com.xiaomi.push.service;

import android.content.Context;
import com.xiaomi.push.gn;
import com.xiaomi.push.jb;
import com.xiaomi.push.service.XMPushService;

/* loaded from: classes6.dex */
final class z extends XMPushService.i {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ jb f25727a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ XMPushService f1098a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public z(int i2, XMPushService xMPushService, jb jbVar) {
        super(i2);
        this.f1098a = xMPushService;
        this.f25727a = jbVar;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    public String a() {
        return "send ack message for message.";
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a */
    public void mo463a() {
        try {
            af.a(this.f1098a, x.a((Context) this.f1098a, this.f25727a));
        } catch (gn e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            this.f1098a.a(10, e2);
        }
    }
}
