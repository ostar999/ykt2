package com.xiaomi.push.service;

import android.content.Context;
import com.xiaomi.push.gd;
import com.xiaomi.push.service.XMPushService;

/* loaded from: classes6.dex */
class cd extends XMPushService.i {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ XMPushService f25678a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ String f1053a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ byte[] f1054a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ int f25679b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public cd(XMPushService xMPushService, int i2, int i3, byte[] bArr, String str) {
        super(i2);
        this.f25678a = xMPushService;
        this.f25679b = i3;
        this.f1054a = bArr;
        this.f1053a = str;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    public String a() {
        return "clear account cache.";
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a */
    public void mo463a() {
        t.m764a((Context) this.f25678a);
        at.a().m720a("5");
        com.xiaomi.push.ab.a(this.f25679b);
        this.f25678a.f955a.c(gd.a());
        this.f25678a.a(this.f1054a, this.f1053a);
    }
}
