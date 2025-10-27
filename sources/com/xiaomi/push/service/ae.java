package com.xiaomi.push.service;

import android.content.Context;
import com.xiaomi.push.gn;
import com.xiaomi.push.jb;
import com.xiaomi.push.service.XMPushService;

/* loaded from: classes6.dex */
final class ae extends XMPushService.i {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ jb f25568a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ XMPushService f984a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ boolean f985a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ boolean f25569b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ boolean f25570c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ boolean f25571d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ae(int i2, XMPushService xMPushService, jb jbVar, boolean z2, boolean z3, boolean z4, boolean z5) {
        super(i2);
        this.f984a = xMPushService;
        this.f25568a = jbVar;
        this.f985a = z2;
        this.f25569b = z3;
        this.f25570c = z4;
        this.f25571d = z5;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    public String a() {
        return "send wrong message ack for message.";
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a */
    public void mo463a() {
        try {
            jb jbVarA = x.a((Context) this.f984a, this.f25568a, this.f985a, this.f25569b, this.f25570c);
            if (this.f25571d) {
                jbVarA.m595a().a("permission_to_location", bb.f25641b);
            }
            af.a(this.f984a, jbVarA);
        } catch (gn e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            this.f984a.a(10, e2);
        }
    }
}
