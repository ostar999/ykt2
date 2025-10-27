package com.xiaomi.push.service;

import com.xiaomi.push.ai;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class f extends ai.a {

    /* renamed from: a, reason: collision with root package name */
    private XMPushService f25681a;

    public f(XMPushService xMPushService) {
        this.f25681a = xMPushService;
    }

    @Override // com.xiaomi.push.ai.a
    /* renamed from: a */
    public int mo185a() {
        return 15;
    }

    @Override // java.lang.Runnable
    public void run() {
        Iterator<com.xiaomi.push.service.module.a> it = i.a(this.f25681a).a().iterator();
        while (it.hasNext()) {
            com.xiaomi.push.service.module.a next = it.next();
            if (next.m755a() < System.currentTimeMillis()) {
                if (i.a(this.f25681a).a(next.m756a()) == 0) {
                    com.xiaomi.channel.commonutils.logger.b.m117a("GeofenceDbCleaner delete a geofence message failed message_id:" + next.m756a());
                }
                x.a(this.f25681a, x.a(next.m757a()), false, false, true);
            }
        }
    }
}
