package com.xiaomi.mipush.sdk;

import android.text.TextUtils;
import com.xiaomi.push.hw;
import com.xiaomi.push.iq;
import com.xiaomi.push.je;
import java.util.HashMap;

/* loaded from: classes6.dex */
final class ai implements Runnable {
    @Override // java.lang.Runnable
    public void run() throws InterruptedException {
        if (com.xiaomi.push.n.d() || com.xiaomi.push.j.f(MiPushClient.sContext) == null) {
            return;
        }
        je jeVar = new je();
        jeVar.b(d.m156a(MiPushClient.sContext).m157a());
        jeVar.c("client_info_update");
        jeVar.a(com.xiaomi.push.service.ar.a());
        jeVar.a(new HashMap());
        String strF = com.xiaomi.push.j.f(MiPushClient.sContext);
        String str = "";
        if (!TextUtils.isEmpty(strF)) {
            str = "" + com.xiaomi.push.ay.a(strF);
        }
        String strH = com.xiaomi.push.j.h(MiPushClient.sContext);
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(strH)) {
            str = str + "," + strH;
        }
        if (!TextUtils.isEmpty(str)) {
            jeVar.m610a().put(Constants.EXTRA_KEY_IMEI_MD5, str);
        }
        int iA = com.xiaomi.push.j.a();
        if (iA >= 0) {
            jeVar.m610a().put("space_id", Integer.toString(iA));
        }
        az.a(MiPushClient.sContext).a((az) jeVar, hw.Notification, false, (iq) null);
    }
}
