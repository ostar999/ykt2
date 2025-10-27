package com.xiaomi.push.service;

import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.push.service.at;

/* loaded from: classes6.dex */
final class ah implements at.b.a {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ XMPushService f25573a;

    public ah(XMPushService xMPushService) {
        this.f25573a = xMPushService;
    }

    @Override // com.xiaomi.push.service.at.b.a
    public void a(at.c cVar, at.c cVar2, int i2) {
        if (cVar2 == at.c.binded) {
            w.a(this.f25573a);
            w.b(this.f25573a);
        } else if (cVar2 == at.c.unbind) {
            w.a(this.f25573a, ErrorCode.ERROR_SERVICE_UNAVAILABLE, " the push is not connected.");
        }
    }
}
