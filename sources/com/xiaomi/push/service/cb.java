package com.xiaomi.push.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.xiaomi.push.hf;

/* loaded from: classes6.dex */
class cb extends BroadcastReceiver {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ XMPushService f25675a;

    public cb(XMPushService xMPushService) {
        this.f25675a = xMPushService;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (TextUtils.equals("com.xiaomi.metoknlp.geofencing.state_change_protected", intent.getAction())) {
            String stringExtra = intent.getStringExtra("Describe");
            String stringExtra2 = intent.getStringExtra("State");
            if (TextUtils.isEmpty(stringExtra)) {
                return;
            }
            if (!this.f25675a.a(stringExtra2, stringExtra, context)) {
                com.xiaomi.channel.commonutils.logger.b.m117a(" updated geofence statue about geo_id:" + stringExtra + " falied. current_statue:Unknown");
                stringExtra2 = "Unknown";
            }
            hf.a(new cc(this, context, stringExtra, stringExtra2));
            com.xiaomi.channel.commonutils.logger.b.c("ownresilt结果:state= " + stringExtra2 + "\n describe=" + stringExtra);
        }
    }
}
