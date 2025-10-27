package com.xiaomi.push.service;

import android.content.Context;
import com.xiaomi.push.gn;
import com.xiaomi.push.jb;
import com.xiaomi.push.service.XMPushService;

/* loaded from: classes6.dex */
final class aa extends XMPushService.i {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ jb f25563a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ XMPushService f978a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public aa(int i2, XMPushService xMPushService, jb jbVar) {
        super(i2);
        this.f978a = xMPushService;
        this.f25563a = jbVar;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    public String a() {
        return "send ack message for obsleted message.";
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a */
    public void mo463a() {
        try {
            jb jbVarA = x.a((Context) this.f978a, this.f25563a);
            jbVarA.m595a().a("message_obsleted", "1");
            af.a(this.f978a, jbVarA);
        } catch (gn e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            this.f978a.a(10, e2);
        }
    }
}
