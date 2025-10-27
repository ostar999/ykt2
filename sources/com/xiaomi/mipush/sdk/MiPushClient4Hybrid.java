package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.d;
import com.xiaomi.push.fq;
import com.xiaomi.push.g;
import com.xiaomi.push.hw;
import com.xiaomi.push.in;
import com.xiaomi.push.iq;
import com.xiaomi.push.is;
import com.xiaomi.push.iv;
import com.xiaomi.push.je;
import com.xiaomi.push.jf;
import com.xiaomi.push.jg;
import com.xiaomi.push.jl;
import com.xiaomi.push.jm;
import com.xiaomi.push.jp;
import com.yikaobang.yixue.R2;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class MiPushClient4Hybrid {

    /* renamed from: a, reason: collision with root package name */
    private static HybridProvider f24507a;

    /* renamed from: a, reason: collision with other field name */
    private static MiPushClientCallbackV2 f107a;

    /* renamed from: a, reason: collision with other field name */
    private static Map<String, d.a> f108a = new HashMap();

    /* renamed from: b, reason: collision with root package name */
    private static Map<String, Long> f24508b = new HashMap();

    private static short a(MiPushMessage miPushMessage, String str) {
        String str2 = miPushMessage.getExtra() == null ? "" : miPushMessage.getExtra().get(Constants.EXTRA_KEY_HYBRID_DEVICE_STATUS);
        int iIntValue = !TextUtils.isEmpty(str2) ? Integer.valueOf(str2).intValue() : 0;
        HybridProvider hybridProvider = f24507a;
        if (hybridProvider != null && !hybridProvider.isAllowNotification(str)) {
            iIntValue = (iIntValue & (-4)) + g.a.NOT_ALLOWED.a();
        }
        return (short) iIntValue;
    }

    private static void a(Context context, MiPushMessage miPushMessage) throws URISyntaxException {
        Intent uri;
        String str = miPushMessage.getExtra().get("web_uri");
        String str2 = miPushMessage.getExtra().get("intent_uri");
        if (!TextUtils.isEmpty(str)) {
            uri = new Intent("android.intent.action.VIEW");
            uri.setData(Uri.parse(str));
        } else if (TextUtils.isEmpty(str2)) {
            uri = null;
        } else {
            try {
                uri = Intent.parseUri(str2, 0);
            } catch (URISyntaxException e2) {
                com.xiaomi.channel.commonutils.logger.b.a("intent uri parse failed", e2);
            }
        }
        if (uri == null) {
            com.xiaomi.channel.commonutils.logger.b.m117a("web uri and intent uri all are empty");
            return;
        }
        uri.addFlags(268435456);
        try {
            context.startActivity(uri);
        } catch (Throwable th) {
            com.xiaomi.channel.commonutils.logger.b.a("start activity failed from web uri or intent uri", th);
        }
    }

    private static void a(Context context, MiPushMessage miPushMessage, String str, short s2) {
        if (miPushMessage == null || miPushMessage.getExtra() == null) {
            com.xiaomi.channel.commonutils.logger.b.m117a("do not ack message, message is null");
            return;
        }
        try {
            iv ivVar = new iv();
            ivVar.b(d.m156a(context).m157a());
            ivVar.a(miPushMessage.getMessageId());
            ivVar.a(Long.valueOf(miPushMessage.getExtra().get(Constants.EXTRA_KEY_HYBRID_MESSAGE_TS)).longValue());
            ivVar.a(s2);
            if (!TextUtils.isEmpty(miPushMessage.getTopic())) {
                ivVar.c(miPushMessage.getTopic());
            }
            az.a(context).a((az) ivVar, hw.AckMessage, false, PushMessageHelper.generateMessage(miPushMessage));
            com.xiaomi.channel.commonutils.logger.b.b("MiPushClient4Hybrid ack mina message, app is :" + str + ", messageId is " + miPushMessage.getMessageId());
        } finally {
            try {
            } finally {
            }
        }
    }

    private static void a(Context context, String str) {
        context.getSharedPreferences("mipush_extra", 0).edit().putLong("last_pull_notification_" + str, System.currentTimeMillis()).commit();
    }

    /* renamed from: a, reason: collision with other method in class */
    private static boolean m127a(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("mipush_extra", 0);
        StringBuilder sb = new StringBuilder();
        sb.append("last_pull_notification_");
        sb.append(str);
        return Math.abs(System.currentTimeMillis() - sharedPreferences.getLong(sb.toString(), -1L)) > 300000;
    }

    private static boolean a(MiPushMessage miPushMessage) {
        return TextUtils.equals(miPushMessage.getExtra() == null ? "" : miPushMessage.getExtra().get(Constants.EXTRA_KEY_PUSH_SERVER_ACTION), Constants.EXTRA_VALUE_PLATFORM_MESSAGE);
    }

    public static void ackMessage(Context context, MiPushMessage miPushMessage) {
        if (miPushMessage == null || miPushMessage.getExtra() == null) {
            com.xiaomi.channel.commonutils.logger.b.m117a("do not ack message, message is null");
        } else {
            String str = miPushMessage.getExtra().get(Constants.EXTRA_KEY_HYBRID_PKGNAME);
            a(context, miPushMessage, str, a(miPushMessage, str));
        }
    }

    public static boolean isRegistered(Context context, String str) {
        return d.m156a(context).a(str) != null;
    }

    public static void onNotificationMessageArrived(Context context, String str, MiPushMessage miPushMessage) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        ackMessage(context, miPushMessage);
        MiPushClientCallbackV2 miPushClientCallbackV2 = f107a;
        if (miPushClientCallbackV2 != null) {
            miPushClientCallbackV2.onNotificationMessageArrived(str, miPushMessage);
        }
    }

    public static void onNotificationMessageClicked(Context context, String str, MiPushMessage miPushMessage) throws URISyntaxException {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (a(miPushMessage)) {
            a(context, miPushMessage);
            return;
        }
        MiPushClientCallbackV2 miPushClientCallbackV2 = f107a;
        if (miPushClientCallbackV2 != null) {
            miPushClientCallbackV2.onNotificationMessageClicked(str, miPushMessage);
        }
    }

    public static void onPlatformNotificationMessageArrived(Context context, MiPushMessage miPushMessage, boolean z2) {
        int iIntValue = Integer.valueOf(miPushMessage.getExtra().get(Constants.EXTRA_KEY_HYBRID_DEVICE_STATUS)).intValue();
        if (!z2) {
            iIntValue = g.a.NOT_ALLOWED.a() + (iIntValue & (-4));
        }
        a(context, miPushMessage, context.getPackageName(), (short) iIntValue);
    }

    public static void onReceivePassThroughMessage(Context context, String str, MiPushMessage miPushMessage) {
        MiPushClientCallbackV2 miPushClientCallbackV2;
        if (TextUtils.isEmpty(str) || (miPushClientCallbackV2 = f107a) == null) {
            return;
        }
        miPushClientCallbackV2.onReceivePassThroughMessage(str, miPushMessage);
    }

    public static void onReceiveRegisterResult(Context context, jg jgVar) {
        ArrayList arrayList;
        d.a aVar;
        String strB = jgVar.b();
        if (jgVar.a() == 0 && (aVar = f108a.get(strB)) != null) {
            aVar.a(jgVar.f822e, jgVar.f823f);
            d.m156a(context).a(strB, aVar);
        }
        if (TextUtils.isEmpty(jgVar.f822e)) {
            arrayList = null;
        } else {
            arrayList = new ArrayList();
            arrayList.add(jgVar.f822e);
        }
        MiPushCommandMessage miPushCommandMessageGenerateCommandMessage = PushMessageHelper.generateCommandMessage(fq.COMMAND_REGISTER.f429a, arrayList, jgVar.f812a, jgVar.f821d, null);
        MiPushClientCallbackV2 miPushClientCallbackV2 = f107a;
        if (miPushClientCallbackV2 != null) {
            miPushClientCallbackV2.onReceiveRegisterResult(strB, miPushCommandMessageGenerateCommandMessage);
        }
    }

    public static void onReceiveUnregisterResult(Context context, jm jmVar) {
        MiPushCommandMessage miPushCommandMessageGenerateCommandMessage = PushMessageHelper.generateCommandMessage(fq.COMMAND_UNREGISTER.f429a, null, jmVar.f888a, jmVar.f896d, null);
        String strA = jmVar.a();
        MiPushClientCallbackV2 miPushClientCallbackV2 = f107a;
        if (miPushClientCallbackV2 != null) {
            miPushClientCallbackV2.onReceiveUnregisterResult(strA, miPushCommandMessageGenerateCommandMessage);
        }
    }

    public static void registerPush(Context context, String str, String str2, String str3) {
        if (d.m156a(context).m161a(str2, str3, str)) {
            ArrayList arrayList = new ArrayList();
            d.a aVarA = d.m156a(context).a(str);
            if (aVarA != null) {
                arrayList.add(aVarA.f24557c);
                MiPushCommandMessage miPushCommandMessageGenerateCommandMessage = PushMessageHelper.generateCommandMessage(fq.COMMAND_REGISTER.f429a, arrayList, 0L, null, null);
                MiPushClientCallbackV2 miPushClientCallbackV2 = f107a;
                if (miPushClientCallbackV2 != null) {
                    miPushClientCallbackV2.onReceiveRegisterResult(str, miPushCommandMessageGenerateCommandMessage);
                }
            }
            if (m127a(context, str)) {
                je jeVar = new je();
                jeVar.b(str2);
                jeVar.c(in.PullOfflineMessage.f622a);
                jeVar.a(com.xiaomi.push.service.ar.a());
                jeVar.a(false);
                az.a(context).a(jeVar, hw.Notification, false, true, null, false, str, str2);
                com.xiaomi.channel.commonutils.logger.b.b("MiPushClient4Hybrid pull offline pass through message");
                a(context, str);
                return;
            }
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (Math.abs(jCurrentTimeMillis - (f24508b.get(str) != null ? f24508b.get(str).longValue() : 0L)) < 5000) {
            com.xiaomi.channel.commonutils.logger.b.m117a("MiPushClient4Hybrid  Could not send register message within 5s repeatedly.");
            return;
        }
        f24508b.put(str, Long.valueOf(jCurrentTimeMillis));
        String strA = com.xiaomi.push.ay.a(6);
        d.a aVar = new d.a(context);
        aVar.c(str2, str3, strA);
        f108a.put(str, aVar);
        jf jfVar = new jf();
        jfVar.a(com.xiaomi.push.service.ar.a());
        jfVar.b(str2);
        jfVar.e(str3);
        jfVar.d(str);
        jfVar.f(strA);
        jfVar.c(com.xiaomi.push.g.m439a(context, context.getPackageName()));
        jfVar.b(com.xiaomi.push.g.a(context, context.getPackageName()));
        jfVar.g("3_6_12");
        jfVar.a(R2.styleable.background_selector_bl_focused_activated);
        jfVar.h(com.xiaomi.push.j.e(context));
        jfVar.a(is.Init);
        if (!com.xiaomi.push.n.d()) {
            String strG = com.xiaomi.push.j.g(context);
            if (!TextUtils.isEmpty(strG)) {
                if (com.xiaomi.push.n.m680b()) {
                    jfVar.i(strG);
                }
                jfVar.k(com.xiaomi.push.ay.a(strG));
            }
        }
        jfVar.j(com.xiaomi.push.j.m586a());
        int iA = com.xiaomi.push.j.a();
        if (iA >= 0) {
            jfVar.c(iA);
        }
        je jeVar2 = new je();
        jeVar2.c(in.HybridRegister.f622a);
        jeVar2.b(d.m156a(context).m157a());
        jeVar2.d(context.getPackageName());
        jeVar2.a(jp.a(jfVar));
        jeVar2.a(com.xiaomi.push.service.ar.a());
        az.a(context).a((az) jeVar2, hw.Notification, (iq) null);
    }

    public static void removeDuplicateCache(Context context, MiPushMessage miPushMessage) {
        String messageId = miPushMessage.getExtra() != null ? miPushMessage.getExtra().get("jobkey") : null;
        if (TextUtils.isEmpty(messageId)) {
            messageId = miPushMessage.getMessageId();
        }
        aw.a(context, messageId);
    }

    public static void setCallback(MiPushClientCallbackV2 miPushClientCallbackV2) {
        f107a = miPushClientCallbackV2;
    }

    public static void setProvider(HybridProvider hybridProvider) {
        f24507a = hybridProvider;
    }

    public static void unregisterPush(Context context, String str) {
        f24508b.remove(str);
        d.a aVarA = d.m156a(context).a(str);
        if (aVarA == null) {
            return;
        }
        jl jlVar = new jl();
        jlVar.a(com.xiaomi.push.service.ar.a());
        jlVar.d(str);
        jlVar.b(aVarA.f152a);
        jlVar.c(aVarA.f24557c);
        jlVar.e(aVarA.f24556b);
        je jeVar = new je();
        jeVar.c(in.HybridUnregister.f622a);
        jeVar.b(d.m156a(context).m157a());
        jeVar.d(context.getPackageName());
        jeVar.a(jp.a(jlVar));
        jeVar.a(com.xiaomi.push.service.ar.a());
        az.a(context).a((az) jeVar, hw.Notification, (iq) null);
        d.m156a(context).b(str);
        MIPushNotificationHelper4Hybrid.clearNotification(context, str);
    }
}
