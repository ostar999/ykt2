package com.xiaomi.push.service;

import android.content.Context;
import android.content.Intent;
import java.util.Iterator;

/* loaded from: classes6.dex */
class cc implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f25676a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ cb f1051a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ String f1052a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f25677b;

    public cc(cb cbVar, Context context, String str, String str2) {
        this.f1051a = cbVar;
        this.f25676a = context;
        this.f1052a = str;
        this.f25677b = str2;
    }

    @Override // java.lang.Runnable
    public void run() {
        StringBuilder sb;
        String str;
        String string;
        Iterator<com.xiaomi.push.service.module.a> it = i.a(this.f25676a).m744a(this.f1052a).iterator();
        while (it.hasNext()) {
            com.xiaomi.push.service.module.a next = it.next();
            if (XMPushService.a(next.a(), this.f25677b)) {
                if (next.m755a() >= System.currentTimeMillis()) {
                    byte[] bArrM757a = next.m757a();
                    if (bArrM757a == null) {
                        string = "Geo canBeShownMessage content null";
                    } else {
                        Intent intentA = x.a(bArrM757a, System.currentTimeMillis());
                        if (intentA == null) {
                            string = "Geo canBeShownMessage intent null";
                        } else {
                            x.a(this.f1051a.f25675a, (String) null, bArrM757a, intentA, true);
                            if (i.a(this.f1051a.f25675a).a(next.m756a()) == 0) {
                                sb = new StringBuilder();
                                str = "show some exit geofence message. then remove this message failed. message_id:";
                                sb.append(str);
                                sb.append(next.m756a());
                                string = sb.toString();
                            }
                        }
                    }
                } else if (i.a(this.f25676a).a(next.m756a()) == 0) {
                    sb = new StringBuilder();
                    str = "XMPushService remove some geofence message failed. message_id:";
                    sb.append(str);
                    sb.append(next.m756a());
                    string = sb.toString();
                }
                com.xiaomi.channel.commonutils.logger.b.m117a(string);
            }
        }
    }
}
