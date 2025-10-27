package com.xiaomi.push.service.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.bb;
import com.xiaomi.push.service.bf;
import com.xiaomi.push.service.j;

/* loaded from: classes6.dex */
public class PkgUninstallReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent == null || !"android.intent.action.PACKAGE_REMOVED".equals(intent.getAction())) {
            return;
        }
        boolean z2 = intent.getExtras().getBoolean("android.intent.extra.REPLACING");
        Uri data = intent.getData();
        if (data == null || z2) {
            return;
        }
        try {
            Intent intent2 = new Intent(context, (Class<?>) XMPushService.class);
            intent2.setAction(bb.f25640a);
            intent2.putExtra("uninstall_pkg_name", data.getEncodedSchemeSpecificPart());
            bf.a(context).m730a(intent2);
            j.a(context.getApplicationContext(), data.getEncodedSchemeSpecificPart());
        } catch (Exception e2) {
            b.a(e2);
        }
    }
}
