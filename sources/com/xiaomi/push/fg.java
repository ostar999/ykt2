package com.xiaomi.push;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/* loaded from: classes6.dex */
class fg implements fd {
    private void a(Service service, Intent intent) {
        String stringExtra = intent.getStringExtra("awake_info");
        if (!TextUtils.isEmpty(stringExtra)) {
            String strB = eu.b(stringExtra);
            if (!TextUtils.isEmpty(strB)) {
                ev.a(service.getApplicationContext(), strB, 1007, "play with service successfully");
                return;
            }
        }
        ev.a(service.getApplicationContext(), "service", 1008, "B get a incorrect message");
    }

    private void a(Context context, String str, String str2, String str3) {
        if (context == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            if (TextUtils.isEmpty(str3)) {
                ev.a(context, "service", 1008, "argument error");
                return;
            } else {
                ev.a(context, str3, 1008, "argument error");
                return;
            }
        }
        if (!ex.a(context, str, str2)) {
            ev.a(context, str3, 1003, "B is not ready");
            return;
        }
        ev.a(context, str3, 1002, "B is ready");
        ev.a(context, str3, 1004, "A is ready");
        try {
            Intent intent = new Intent();
            intent.setAction(str2);
            intent.setPackage(str);
            intent.putExtra("awake_info", eu.a(str3));
            if (context.startService(intent) == null) {
                ev.a(context, str3, 1008, "A is fail to help B's service");
            } else {
                ev.a(context, str3, 1005, "A is successful");
                ev.a(context, str3, 1006, "The job is finished");
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            ev.a(context, str3, 1008, "A meet a exception when help B's service");
        }
    }

    @Override // com.xiaomi.push.fd
    public void a(Context context, Intent intent, String str) {
        if (context == null || !(context instanceof Service)) {
            ev.a(context, "service", 1008, "A receive incorrect message");
        } else {
            a((Service) context, intent);
        }
    }

    @Override // com.xiaomi.push.fd
    public void a(Context context, ez ezVar) {
        if (ezVar != null) {
            a(context, ezVar.a(), ezVar.b(), ezVar.d());
        } else {
            ev.a(context, "service", 1008, "A receive incorrect message");
        }
    }
}
