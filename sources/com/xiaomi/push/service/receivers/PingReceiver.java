package com.xiaomi.push.service.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.fm;
import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.ax;
import com.xiaomi.push.service.bf;

/* loaded from: classes6.dex */
public class PingReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        b.c(intent.getPackage() + " is the package name");
        if (!ax.f25625o.equals(intent.getAction())) {
            b.m117a("cancel the old ping timer");
            fm.a();
        } else if (TextUtils.equals(context.getPackageName(), intent.getPackage())) {
            b.c("Ping XMChannelService on timer");
            try {
                Intent intent2 = new Intent(context, (Class<?>) XMPushService.class);
                intent2.putExtra("time_stamp", System.currentTimeMillis());
                intent2.setAction("com.xiaomi.push.timer");
                bf.a(context).m730a(intent2);
            } catch (Exception e2) {
                b.a(e2);
            }
        }
    }
}
