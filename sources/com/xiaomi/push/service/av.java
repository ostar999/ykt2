package com.xiaomi.push.service;

import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.at;

/* loaded from: classes6.dex */
class av extends XMPushService.i {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ at.b.c f25609a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public av(at.b.c cVar, int i2) {
        super(i2);
        this.f25609a = cVar;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    public String a() {
        return "clear peer job";
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a */
    public void mo463a() {
        at.b.c cVar = this.f25609a;
        if (cVar.f25603a == cVar.f1019a.f1006a) {
            com.xiaomi.channel.commonutils.logger.b.b("clean peer, chid = " + this.f25609a.f1019a.f25597g);
            this.f25609a.f1019a.f1006a = null;
        }
    }
}
