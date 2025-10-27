package com.xiaomi.push;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/* loaded from: classes6.dex */
class fh implements fd {
    private void a(Service service, Intent intent) {
        if ("com.xiaomi.mipush.sdk.WAKEUP".equals(intent.getAction())) {
            String stringExtra = intent.getStringExtra("waker_pkgname");
            String stringExtra2 = intent.getStringExtra("awake_info");
            if (TextUtils.isEmpty(stringExtra)) {
                ev.a(service.getApplicationContext(), "service", 1007, "old version message");
                return;
            }
            if (TextUtils.isEmpty(stringExtra2)) {
                ev.a(service.getApplicationContext(), stringExtra, 1007, "play with service ");
                return;
            }
            String strB = eu.b(stringExtra2);
            boolean zIsEmpty = TextUtils.isEmpty(strB);
            Context applicationContext = service.getApplicationContext();
            if (zIsEmpty) {
                ev.a(applicationContext, "service", 1008, "B get a incorrect message");
            } else {
                ev.a(applicationContext, strB, 1007, "old version message ");
            }
        }
    }

    private void a(Context context, String str, String str2, String str3) {
        if (context == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            if (TextUtils.isEmpty(str3)) {
                ev.a(context, "service", 1008, "argument error");
                return;
            } else {
                ev.a(context, str3, 1008, "argument error");
                return;
            }
        }
        if (!ex.a(context, str)) {
            ev.a(context, str3, 1003, "B is not ready");
            return;
        }
        ev.a(context, str3, 1002, "B is ready");
        ev.a(context, str3, 1004, "A is ready");
        try {
            Intent intent = new Intent();
            intent.setClassName(str, str2);
            intent.setAction("com.xiaomi.mipush.sdk.WAKEUP");
            intent.putExtra("waker_pkgname", context.getPackageName());
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
            return;
        }
        a((Service) context, intent);
    }

    @Override // com.xiaomi.push.fd
    public void a(Context context, ez ezVar) {
        if (ezVar != null) {
            a(context, ezVar.a(), ezVar.c(), ezVar.d());
        }
    }
}
