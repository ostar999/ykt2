package com.xiaomi.push.service;

import com.xiaomi.push.gn;
import com.xiaomi.push.service.XMPushService;

/* loaded from: classes6.dex */
class br extends XMPushService.i {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ XMPushService f25664a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ String f1047a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ byte[] f1048a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public br(XMPushService xMPushService, int i2, String str, byte[] bArr) {
        super(i2);
        this.f25664a = xMPushService;
        this.f1047a = str;
        this.f1048a = bArr;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    public String a() {
        return "send mi push message";
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a */
    public void mo463a() {
        try {
            af.a(this.f25664a, this.f1047a, this.f1048a);
        } catch (gn e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            this.f25664a.a(10, e2);
        }
    }
}
