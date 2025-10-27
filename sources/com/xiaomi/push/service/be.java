package com.xiaomi.push.service;

import com.xiaomi.push.fv;
import com.xiaomi.push.gn;
import com.xiaomi.push.service.XMPushService;

/* loaded from: classes6.dex */
class be extends XMPushService.i {

    /* renamed from: a, reason: collision with root package name */
    private fv f25649a;

    /* renamed from: a, reason: collision with other field name */
    private XMPushService f1029a;

    public be(XMPushService xMPushService, fv fvVar) {
        super(4);
        this.f1029a = xMPushService;
        this.f25649a = fvVar;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    public String a() {
        return "send a message.";
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a */
    public void mo463a() {
        try {
            fv fvVar = this.f25649a;
            if (fvVar != null) {
                this.f1029a.a(fvVar);
            }
        } catch (gn e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            this.f1029a.a(10, e2);
        }
    }
}
