package com.xiaomi.push.service;

import com.xiaomi.push.ai;
import com.xiaomi.push.hw;
import com.xiaomi.push.je;
import com.xiaomi.push.jp;
import java.lang.ref.WeakReference;

/* loaded from: classes6.dex */
public class b extends ai.a {

    /* renamed from: a, reason: collision with root package name */
    private je f25638a;

    /* renamed from: a, reason: collision with other field name */
    private WeakReference<XMPushService> f1022a;

    /* renamed from: a, reason: collision with other field name */
    private boolean f1023a;

    public b(je jeVar, WeakReference<XMPushService> weakReference, boolean z2) {
        this.f25638a = jeVar;
        this.f1022a = weakReference;
        this.f1023a = z2;
    }

    @Override // com.xiaomi.push.ai.a
    /* renamed from: a */
    public int mo185a() {
        return 22;
    }

    @Override // java.lang.Runnable
    public void run() {
        XMPushService xMPushService;
        WeakReference<XMPushService> weakReference = this.f1022a;
        if (weakReference == null || this.f25638a == null || (xMPushService = weakReference.get()) == null) {
            return;
        }
        this.f25638a.a(ar.a());
        this.f25638a.a(false);
        com.xiaomi.channel.commonutils.logger.b.c("MoleInfo aw_ping : send aw_Ping msg " + this.f25638a.a());
        try {
            String strC = this.f25638a.c();
            xMPushService.a(strC, jp.a(af.a(strC, this.f25638a.b(), this.f25638a, hw.Notification)), this.f1023a);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.d("MoleInfo aw_ping : send help app ping error" + e2.toString());
        }
    }
}
