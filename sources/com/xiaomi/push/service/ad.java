package com.xiaomi.push.service;

import android.content.Context;
import com.xiaomi.push.gn;
import com.xiaomi.push.jb;
import com.xiaomi.push.service.XMPushService;

/* loaded from: classes6.dex */
final class ad extends XMPushService.i {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ jb f25566a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ XMPushService f982a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ String f983a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f25567b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ad(int i2, XMPushService xMPushService, jb jbVar, String str, String str2) {
        super(i2);
        this.f982a = xMPushService;
        this.f25566a = jbVar;
        this.f983a = str;
        this.f25567b = str2;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    public String a() {
        return "send wrong message ack for message.";
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a */
    public void mo463a() {
        try {
            jb jbVarA = x.a((Context) this.f982a, this.f25566a);
            jbVarA.f751a.a("error", this.f983a);
            jbVarA.f751a.a("reason", this.f25567b);
            af.a(this.f982a, jbVarA);
        } catch (gn e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            this.f982a.a(10, e2);
        }
    }
}
