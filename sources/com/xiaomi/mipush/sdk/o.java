package com.xiaomi.mipush.sdk;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.xiaomi.push.ai;
import com.xiaomi.push.fa;
import com.xiaomi.push.fc;
import com.xiaomi.push.ic;
import com.xiaomi.push.in;
import com.xiaomi.push.je;
import com.xiaomi.push.jp;
import com.xiaomi.push.jq;
import com.yikaobang.yixue.R2;
import java.util.HashMap;

/* loaded from: classes6.dex */
public class o {
    public static void a(Context context, Intent intent, Uri uri) {
        fa faVarA;
        fc fcVar;
        if (context == null) {
            return;
        }
        az.a(context).m145a();
        if (fa.a(context.getApplicationContext()).m415a() == null) {
            fa.a(context.getApplicationContext()).a(d.m156a(context.getApplicationContext()).m157a(), context.getPackageName(), com.xiaomi.push.service.ao.a(context.getApplicationContext()).a(ic.AwakeInfoUploadWaySwitch.a(), 0), new e());
        }
        if ((context instanceof Activity) && intent != null) {
            faVarA = fa.a(context.getApplicationContext());
            fcVar = fc.ACTIVITY;
        } else {
            if (!(context instanceof Service) || intent == null) {
                if (uri == null || TextUtils.isEmpty(uri.toString())) {
                    return;
                }
                fa.a(context.getApplicationContext()).a(fc.PROVIDER, context, (Intent) null, uri.toString());
                return;
            }
            if ("com.xiaomi.mipush.sdk.WAKEUP".equals(intent.getAction())) {
                faVarA = fa.a(context.getApplicationContext());
                fcVar = fc.SERVICE_COMPONENT;
            } else {
                faVarA = fa.a(context.getApplicationContext());
                fcVar = fc.SERVICE_ACTION;
            }
        }
        faVarA.a(fcVar, context, intent, (String) null);
    }

    private static void a(Context context, je jeVar) {
        boolean zA = com.xiaomi.push.service.ao.a(context).a(ic.AwakeAppPingSwitch.a(), false);
        int iA = com.xiaomi.push.service.ao.a(context).a(ic.AwakeAppPingFrequency.a(), 0);
        if (iA >= 0 && iA < 30) {
            com.xiaomi.channel.commonutils.logger.b.c("aw_ping: frquency need > 30s.");
            iA = 30;
        }
        boolean z2 = iA >= 0 ? zA : false;
        if (!com.xiaomi.push.n.m679a()) {
            a(context, jeVar, z2, iA);
        } else if (z2) {
            com.xiaomi.push.ai.a(context.getApplicationContext()).a((ai.a) new p(jeVar, context), iA);
        }
    }

    public static final <T extends jq<T, ?>> void a(Context context, T t2, boolean z2, int i2) {
        byte[] bArrA = jp.a(t2);
        if (bArrA == null) {
            com.xiaomi.channel.commonutils.logger.b.m117a("send message fail, because msgBytes is null.");
            return;
        }
        Intent intent = new Intent();
        intent.setAction("action_help_ping");
        intent.putExtra("extra_help_ping_switch", z2);
        intent.putExtra("extra_help_ping_frequency", i2);
        intent.putExtra("mipush_payload", bArrA);
        intent.putExtra("com.xiaomi.mipush.MESSAGE_CACHE", true);
        az.a(context).m146a(intent);
    }

    public static void a(Context context, String str) {
        com.xiaomi.channel.commonutils.logger.b.m117a("aw_ping : send aw_ping cmd and content to push service from 3rd app");
        HashMap map = new HashMap();
        map.put("awake_info", str);
        map.put("event_type", String.valueOf(R2.drawable.homepage_shangcheng_press));
        map.put("description", "ping message");
        je jeVar = new je();
        jeVar.b(d.m156a(context).m157a());
        jeVar.d(context.getPackageName());
        jeVar.c(in.AwakeAppResponse.f622a);
        jeVar.a(com.xiaomi.push.service.ar.a());
        jeVar.f769a = map;
        a(context, jeVar);
    }

    public static void a(Context context, String str, int i2, String str2) {
        je jeVar = new je();
        jeVar.b(str);
        jeVar.a(new HashMap());
        jeVar.m610a().put("extra_aw_app_online_cmd", String.valueOf(i2));
        jeVar.m610a().put("extra_help_aw_info", str2);
        jeVar.a(com.xiaomi.push.service.ar.a());
        byte[] bArrA = jp.a(jeVar);
        if (bArrA == null) {
            com.xiaomi.channel.commonutils.logger.b.m117a("send message fail, because msgBytes is null.");
            return;
        }
        Intent intent = new Intent();
        intent.setAction("action_aw_app_logic");
        intent.putExtra("mipush_payload", bArrA);
        az.a(context).m146a(intent);
    }
}
