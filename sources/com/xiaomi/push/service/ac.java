package com.xiaomi.push.service;

import android.content.Context;
import com.xiaomi.push.gn;
import com.xiaomi.push.jb;
import com.xiaomi.push.service.XMPushService;

/* loaded from: classes6.dex */
final class ac extends XMPushService.i {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ jb f25565a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ XMPushService f980a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ String f981a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ac(int i2, XMPushService xMPushService, jb jbVar, String str) {
        super(i2);
        this.f980a = xMPushService;
        this.f25565a = jbVar;
        this.f981a = str;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    public String a() {
        return "send app absent ack message for message.";
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a */
    public void mo463a() {
        try {
            jb jbVarA = x.a((Context) this.f980a, this.f25565a);
            jbVarA.m595a().a("absent_target_package", this.f981a);
            af.a(this.f980a, jbVarA);
        } catch (gn e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            this.f980a.a(10, e2);
        }
    }
}
