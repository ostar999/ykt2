package com.xiaomi.push.service;

import com.xiaomi.push.ho;
import com.xiaomi.push.service.XMPushService.d;

/* loaded from: classes6.dex */
class bd {

    /* renamed from: c, reason: collision with root package name */
    private static int f25646c = 300000;

    /* renamed from: a, reason: collision with other field name */
    private XMPushService f1028a;

    /* renamed from: b, reason: collision with root package name */
    private int f25648b = 0;

    /* renamed from: a, reason: collision with root package name */
    private int f25647a = 500;

    /* renamed from: a, reason: collision with other field name */
    private long f1027a = 0;

    public bd(XMPushService xMPushService) {
        this.f1028a = xMPushService;
    }

    private int a() {
        if (this.f25648b > 8) {
            return com.alipay.security.mobile.module.http.constant.a.f3441a;
        }
        double dRandom = (Math.random() * 2.0d) + 1.0d;
        int i2 = this.f25648b;
        if (i2 > 4) {
            return (int) (dRandom * 60000.0d);
        }
        if (i2 > 1) {
            return (int) (dRandom * 10000.0d);
        }
        if (this.f1027a == 0) {
            return 0;
        }
        if (System.currentTimeMillis() - this.f1027a >= 300000) {
            this.f25647a = 1000;
            return 0;
        }
        int i3 = this.f25647a;
        if (i3 >= f25646c) {
            return i3;
        }
        this.f25647a = (int) (i3 * 1.5d);
        return i3;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m727a() {
        this.f1027a = System.currentTimeMillis();
        this.f1028a.a(1);
        this.f25648b = 0;
    }

    public void a(boolean z2) {
        if (!this.f1028a.m698a()) {
            com.xiaomi.channel.commonutils.logger.b.c("should not reconnect as no client or network.");
            return;
        }
        if (z2) {
            if (!this.f1028a.m699a(1)) {
                this.f25648b++;
            }
            this.f1028a.a(1);
            XMPushService xMPushService = this.f1028a;
            xMPushService.getClass();
            xMPushService.a(xMPushService.new d());
            return;
        }
        if (this.f1028a.m699a(1)) {
            return;
        }
        int iA = a();
        if (!this.f1028a.m699a(1)) {
            this.f25648b++;
        }
        com.xiaomi.channel.commonutils.logger.b.m117a("schedule reconnect in " + iA + "ms");
        XMPushService xMPushService2 = this.f1028a;
        xMPushService2.getClass();
        xMPushService2.a(xMPushService2.new d(), (long) iA);
        if (this.f25648b == 2 && ho.m488a().m493a()) {
            al.b();
        }
        if (this.f25648b == 3) {
            al.a();
        }
    }
}
