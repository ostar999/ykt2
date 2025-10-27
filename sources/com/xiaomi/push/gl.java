package com.xiaomi.push;

import com.xiaomi.push.service.XMPushService;

/* loaded from: classes6.dex */
class gl extends XMPushService.i {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ gj f24942a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ Exception f493a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ int f24943b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public gl(gj gjVar, int i2, int i3, Exception exc) {
        super(i2);
        this.f24942a = gjVar;
        this.f24943b = i3;
        this.f493a = exc;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    public String a() {
        return "shutdown the connection. " + this.f24943b + ", " + this.f493a;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a */
    public void mo463a() {
        this.f24942a.f24935b.a(this.f24943b, this.f493a);
    }
}
