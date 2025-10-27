package com.xiaomi.push.service;

import com.xiaomi.push.fv;
import com.xiaomi.push.gn;
import com.xiaomi.push.service.XMPushService;

/* loaded from: classes6.dex */
class c extends XMPushService.i {

    /* renamed from: a, reason: collision with root package name */
    private XMPushService f25673a;

    /* renamed from: a, reason: collision with other field name */
    private fv[] f1049a;

    public c(XMPushService xMPushService, fv[] fvVarArr) {
        super(4);
        this.f25673a = xMPushService;
        this.f1049a = fvVarArr;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    public String a() {
        return "batch send message.";
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a */
    public void mo463a() {
        try {
            fv[] fvVarArr = this.f1049a;
            if (fvVarArr != null) {
                this.f25673a.a(fvVarArr);
            }
        } catch (gn e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            this.f25673a.a(10, e2);
        }
    }
}
