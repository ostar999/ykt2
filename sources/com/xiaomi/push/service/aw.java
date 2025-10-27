package com.xiaomi.push.service;

import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.at;

/* loaded from: classes6.dex */
class aw extends XMPushService.i {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ at.b.c f25610a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public aw(at.b.c cVar, int i2) {
        super(i2);
        this.f25610a = cVar;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    public String a() {
        return "check peer job";
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a */
    public void mo463a() {
        at atVarA = at.a();
        at.b bVar = this.f25610a.f1019a;
        if (atVarA.a(bVar.f25597g, bVar.f1015b).f1006a == null) {
            XMPushService xMPushService = at.b.this.f1008a;
            at.b bVar2 = this.f25610a.f1019a;
            xMPushService.a(bVar2.f25597g, bVar2.f1015b, 2, null, null);
        }
    }
}
