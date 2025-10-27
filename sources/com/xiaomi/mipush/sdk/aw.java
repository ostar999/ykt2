package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.ServiceInfo;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.PushMessageHandler;
import com.xiaomi.push.fl;
import com.xiaomi.push.hw;
import com.xiaomi.push.in;
import com.xiaomi.push.ip;
import com.xiaomi.push.iq;
import com.xiaomi.push.is;
import com.xiaomi.push.iv;
import com.xiaomi.push.iw;
import com.xiaomi.push.jb;
import com.xiaomi.push.je;
import com.xiaomi.push.ji;
import com.xiaomi.push.jp;
import com.xiaomi.push.jq;
import com.xiaomi.push.jv;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TimeZone;

/* loaded from: classes6.dex */
public class aw {

    /* renamed from: a, reason: collision with root package name */
    private static aw f24534a;

    /* renamed from: a, reason: collision with other field name */
    private static Object f129a = new Object();

    /* renamed from: a, reason: collision with other field name */
    private static Queue<String> f130a;

    /* renamed from: a, reason: collision with other field name */
    private Context f131a;

    private aw(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.f131a = applicationContext;
        if (applicationContext == null) {
            this.f131a = context;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:63:0x013c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.content.Intent a(android.content.Context r6, java.lang.String r7, java.util.Map<java.lang.String, java.lang.String> r8) throws java.net.URISyntaxException, java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 363
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.mipush.sdk.aw.a(android.content.Context, java.lang.String, java.util.Map):android.content.Intent");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0140  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.xiaomi.mipush.sdk.PushMessageHandler.a a(com.xiaomi.push.jb r20, boolean r21, byte[] r22, java.lang.String r23, int r24) throws java.net.URISyntaxException, java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 2558
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.mipush.sdk.aw.a(com.xiaomi.push.jb, boolean, byte[], java.lang.String, int):com.xiaomi.mipush.sdk.PushMessageHandler$a");
    }

    private PushMessageHandler.a a(jb jbVar, byte[] bArr) {
        String str;
        jq jqVarA;
        String str2 = null;
        try {
            jqVarA = as.a(this.f131a, jbVar);
        } catch (t e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            str = "message arrived: receive a message but decrypt failed. report when click.";
        } catch (jv e3) {
            com.xiaomi.channel.commonutils.logger.b.a(e3);
            str = "message arrived: receive a message which action string is not valid. is the reg expired?";
        }
        if (jqVarA == null) {
            com.xiaomi.channel.commonutils.logger.b.d("message arrived: receiving an un-recognized message. " + jbVar.f750a);
            return null;
        }
        hw hwVarA = jbVar.a();
        com.xiaomi.channel.commonutils.logger.b.m117a("message arrived: processing an arrived message, action=" + hwVarA);
        if (ay.f24536a[hwVarA.ordinal()] != 1) {
            return null;
        }
        ji jiVar = (ji) jqVarA;
        ip ipVarA = jiVar.a();
        if (ipVarA == null) {
            str = "message arrived: receive an empty message without push content, drop it";
            com.xiaomi.channel.commonutils.logger.b.d(str);
            return null;
        }
        iq iqVar = jbVar.f751a;
        if (iqVar != null && iqVar.m555a() != null) {
            str2 = jbVar.f751a.f657a.get("jobkey");
        }
        MiPushMessage miPushMessageGenerateMessage = PushMessageHelper.generateMessage(jiVar, jbVar.m595a(), false);
        miPushMessageGenerateMessage.setArrivedMessage(true);
        com.xiaomi.channel.commonutils.logger.b.m117a("message arrived: receive a message, msgid=" + ipVarA.m546a() + ", jobkey=" + str2);
        return miPushMessageGenerateMessage;
    }

    public static aw a(Context context) {
        if (f24534a == null) {
            f24534a = new aw(context);
        }
        return f24534a;
    }

    private void a() {
        SharedPreferences sharedPreferences = this.f131a.getSharedPreferences("mipush_extra", 0);
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (Math.abs(jCurrentTimeMillis - sharedPreferences.getLong(Constants.SP_KEY_LAST_REINITIALIZE, 0L)) > 1800000) {
            MiPushClient.reInitialize(this.f131a, is.PackageUnregistered);
            sharedPreferences.edit().putLong(Constants.SP_KEY_LAST_REINITIALIZE, jCurrentTimeMillis).commit();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Context context, PackageInfo packageInfo) {
        ServiceInfo[] serviceInfoArr = packageInfo.services;
        if (serviceInfoArr != null) {
            for (ServiceInfo serviceInfo : serviceInfoArr) {
                if (serviceInfo.exported && serviceInfo.enabled && "com.xiaomi.mipush.sdk.PushMessageHandler".equals(serviceInfo.name) && !context.getPackageName().equals(serviceInfo.packageName)) {
                    try {
                        Intent intent = new Intent();
                        intent.setClassName(serviceInfo.packageName, serviceInfo.name);
                        intent.setAction("com.xiaomi.mipush.sdk.SYNC_LOG");
                        PushMessageHandler.a(context, intent);
                        return;
                    } catch (Throwable unused) {
                        return;
                    }
                }
            }
        }
    }

    public static void a(Context context, String str) {
        synchronized (f129a) {
            f130a.remove(str);
            d.m156a(context);
            SharedPreferences sharedPreferencesA = d.a(context);
            String strA = com.xiaomi.push.ay.a(f130a, ",");
            SharedPreferences.Editor editorEdit = sharedPreferencesA.edit();
            editorEdit.putString("pref_msg_ids", strA);
            com.xiaomi.push.t.a(editorEdit);
        }
    }

    private void a(Context context, String[] strArr) {
        com.xiaomi.push.ai.a(context).a(new ax(this, strArr, context));
    }

    private void a(iw iwVar) {
        Context context;
        f fVar;
        com.xiaomi.channel.commonutils.logger.b.c("ASSEMBLE_PUSH : " + iwVar.toString());
        String strA = iwVar.a();
        Map<String, String> mapM574a = iwVar.m574a();
        if (mapM574a != null) {
            String str = mapM574a.get(Constants.ASSEMBLE_PUSH_REG_INFO);
            if (TextUtils.isEmpty(str)) {
                return;
            }
            if (str.contains("brand:" + aq.FCM.name())) {
                com.xiaomi.channel.commonutils.logger.b.m117a("ASSEMBLE_PUSH : receive fcm token sync ack");
                context = this.f131a;
                fVar = f.ASSEMBLE_PUSH_FCM;
            } else {
                if (str.contains("brand:" + aq.HUAWEI.name())) {
                    com.xiaomi.channel.commonutils.logger.b.m117a("ASSEMBLE_PUSH : receive hw token sync ack");
                    context = this.f131a;
                    fVar = f.ASSEMBLE_PUSH_HUAWEI;
                } else {
                    if (!str.contains("brand:" + aq.OPPO.name())) {
                        return;
                    }
                    com.xiaomi.channel.commonutils.logger.b.m117a("ASSEMBLE_PUSH : receive COS token sync ack");
                    context = this.f131a;
                    fVar = f.ASSEMBLE_PUSH_COS;
                }
            }
            i.b(context, fVar, str);
            a(strA, iwVar.f707a, fVar);
        }
    }

    private void a(jb jbVar) {
        com.xiaomi.channel.commonutils.logger.b.m117a("receive a message but decrypt failed. report now.");
        je jeVar = new je(jbVar.m595a().f655a, false);
        jeVar.c(in.DecryptMessageFail.f622a);
        jeVar.b(jbVar.m596a());
        jeVar.d(jbVar.f757b);
        HashMap map = new HashMap();
        jeVar.f769a = map;
        map.put("regid", MiPushClient.getRegId(this.f131a));
        az.a(this.f131a).a((az) jeVar, hw.Notification, false, (iq) null);
    }

    private void a(ji jiVar, jb jbVar) {
        iq iqVarM595a = jbVar.m595a();
        iv ivVar = new iv();
        ivVar.b(jiVar.b());
        ivVar.a(jiVar.m630a());
        ivVar.a(jiVar.a().a());
        if (!TextUtils.isEmpty(jiVar.c())) {
            ivVar.c(jiVar.c());
        }
        if (!TextUtils.isEmpty(jiVar.d())) {
            ivVar.d(jiVar.d());
        }
        ivVar.a(jp.a(this.f131a, jbVar));
        az.a(this.f131a).a((az) ivVar, hw.AckMessage, iqVarM595a);
    }

    private void a(String str, long j2, f fVar) {
        be beVarA = l.a(fVar);
        if (beVarA == null) {
            return;
        }
        if (j2 == 0) {
            synchronized (ap.class) {
                if (ap.a(this.f131a).m134a(str)) {
                    ap.a(this.f131a).c(str);
                    if ("syncing".equals(ap.a(this.f131a).a(beVarA))) {
                        ap.a(this.f131a).a(beVarA, "synced");
                    }
                }
            }
            return;
        }
        if (!"syncing".equals(ap.a(this.f131a).a(beVarA))) {
            ap.a(this.f131a).c(str);
            return;
        }
        synchronized (ap.class) {
            if (ap.a(this.f131a).m134a(str)) {
                if (ap.a(this.f131a).a(str) < 10) {
                    ap.a(this.f131a).b(str);
                    az.a(this.f131a).a(str, beVarA, fVar);
                } else {
                    ap.a(this.f131a).c(str);
                }
            }
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    private static boolean m135a(Context context, String str) {
        synchronized (f129a) {
            d.m156a(context);
            SharedPreferences sharedPreferencesA = d.a(context);
            if (f130a == null) {
                String[] strArrSplit = sharedPreferencesA.getString("pref_msg_ids", "").split(",");
                f130a = new LinkedList();
                for (String str2 : strArrSplit) {
                    f130a.add(str2);
                }
            }
            if (f130a.contains(str)) {
                return true;
            }
            f130a.add(str);
            if (f130a.size() > 25) {
                f130a.poll();
            }
            String strA = com.xiaomi.push.ay.a(f130a, ",");
            SharedPreferences.Editor editorEdit = sharedPreferencesA.edit();
            editorEdit.putString("pref_msg_ids", strA);
            com.xiaomi.push.t.a(editorEdit);
            return false;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    private boolean m136a(jb jbVar) {
        if (!TextUtils.equals(Constants.HYBRID_PACKAGE_NAME, jbVar.b()) && !TextUtils.equals(Constants.HYBRID_DEBUG_PACKAGE_NAME, jbVar.b())) {
            return false;
        }
        Map<String, String> mapM555a = jbVar.m595a() == null ? null : jbVar.m595a().m555a();
        if (mapM555a == null) {
            return false;
        }
        String str = mapM555a.get(Constants.EXTRA_KEY_PUSH_SERVER_ACTION);
        return TextUtils.equals(str, Constants.EXTRA_VALUE_HYBRID_MESSAGE) || TextUtils.equals(str, Constants.EXTRA_VALUE_PLATFORM_MESSAGE);
    }

    private void b(jb jbVar) {
        iq iqVarM595a = jbVar.m595a();
        iv ivVar = new iv();
        ivVar.b(jbVar.m596a());
        ivVar.a(iqVarM595a.m554a());
        ivVar.a(iqVarM595a.m552a());
        if (!TextUtils.isEmpty(iqVarM595a.m559b())) {
            ivVar.c(iqVarM595a.m559b());
        }
        ivVar.a(jp.a(this.f131a, jbVar));
        az.a(this.f131a).a((az) ivVar, hw.AckMessage, false, jbVar.m595a());
    }

    public PushMessageHandler.a a(Intent intent) {
        String str;
        String action = intent.getAction();
        com.xiaomi.channel.commonutils.logger.b.m117a("receive an intent from server, action=" + action);
        String stringExtra = intent.getStringExtra("mrt");
        if (stringExtra == null) {
            stringExtra = Long.toString(System.currentTimeMillis());
        }
        if ("com.xiaomi.mipush.RECEIVE_MESSAGE".equals(action)) {
            byte[] byteArrayExtra = intent.getByteArrayExtra("mipush_payload");
            boolean booleanExtra = intent.getBooleanExtra("mipush_notified", false);
            String stringExtra2 = intent.getStringExtra("messageId");
            int intExtra = intent.getIntExtra("eventMessageType", -1);
            if (byteArrayExtra == null) {
                com.xiaomi.channel.commonutils.logger.b.d("receiving an empty message, drop");
                fl.a(this.f131a).a(this.f131a.getPackageName(), intent, "receiving an empty message, drop");
                return null;
            }
            jb jbVar = new jb();
            try {
                jp.a(jbVar, byteArrayExtra);
                d dVarM156a = d.m156a(this.f131a);
                iq iqVarM595a = jbVar.m595a();
                hw hwVarA = jbVar.a();
                hw hwVar = hw.SendMessage;
                if (hwVarA == hwVar && iqVarM595a != null && !dVarM156a.m166d() && !booleanExtra) {
                    iqVarM595a.a("mrt", stringExtra);
                    iqVarM595a.a("mat", Long.toString(System.currentTimeMillis()));
                    if (m136a(jbVar)) {
                        com.xiaomi.channel.commonutils.logger.b.b("this is a mina's message, ack later");
                        iqVarM595a.a(Constants.EXTRA_KEY_HYBRID_MESSAGE_TS, String.valueOf(iqVarM595a.m552a()));
                        iqVarM595a.a(Constants.EXTRA_KEY_HYBRID_DEVICE_STATUS, String.valueOf((int) jp.a(this.f131a, jbVar)));
                    } else {
                        b(jbVar);
                    }
                }
                if (jbVar.a() == hwVar && !jbVar.m603b()) {
                    if (!com.xiaomi.push.service.ai.m709a(jbVar)) {
                        Object[] objArr = new Object[2];
                        objArr[0] = jbVar.b();
                        objArr[1] = iqVarM595a != null ? iqVarM595a.m554a() : "";
                        com.xiaomi.channel.commonutils.logger.b.m117a(String.format("drop an un-encrypted messages. %1$s, %2$s", objArr));
                        fl flVarA = fl.a(this.f131a);
                        String packageName = this.f131a.getPackageName();
                        Object[] objArr2 = new Object[2];
                        objArr2[0] = jbVar.b();
                        objArr2[1] = iqVarM595a != null ? iqVarM595a.m554a() : "";
                        flVarA.a(packageName, intent, String.format("drop an un-encrypted messages. %1$s, %2$s", objArr2));
                        return null;
                    }
                    if (!booleanExtra || iqVarM595a.m555a() == null || !iqVarM595a.m555a().containsKey("notify_effect")) {
                        com.xiaomi.channel.commonutils.logger.b.m117a(String.format("drop an un-encrypted messages. %1$s, %2$s", jbVar.b(), iqVarM595a.m554a()));
                        fl.a(this.f131a).a(this.f131a.getPackageName(), intent, String.format("drop an un-encrypted messages. %1$s, %2$s", jbVar.b(), iqVarM595a.m554a()));
                        return null;
                    }
                }
                if (!dVarM156a.m165c() && jbVar.f750a != hw.Registration) {
                    if (com.xiaomi.push.service.ai.m709a(jbVar)) {
                        return a(jbVar, booleanExtra, byteArrayExtra, stringExtra2, intExtra);
                    }
                    com.xiaomi.channel.commonutils.logger.b.d("receive message without registration. need re-register!");
                    fl.a(this.f131a).a(this.f131a.getPackageName(), intent, "receive message without registration. need re-register!");
                    a();
                    return null;
                }
                if (!dVarM156a.m165c() || !dVarM156a.m167e()) {
                    return a(jbVar, booleanExtra, byteArrayExtra, stringExtra2, intExtra);
                }
                if (jbVar.f750a != hw.UnRegistration) {
                    MiPushClient.unregisterPush(this.f131a);
                    return null;
                }
                dVarM156a.m158a();
                MiPushClient.clearExtras(this.f131a);
                PushMessageHandler.a();
                return null;
            } catch (jv | Exception e2) {
                e = e2;
                fl.a(this.f131a).a(this.f131a.getPackageName(), intent, e);
            }
        } else {
            if ("com.xiaomi.mipush.ERROR".equals(action)) {
                MiPushCommandMessage miPushCommandMessage = new MiPushCommandMessage();
                jb jbVar2 = new jb();
                try {
                    byte[] byteArrayExtra2 = intent.getByteArrayExtra("mipush_payload");
                    if (byteArrayExtra2 != null) {
                        jp.a(jbVar2, byteArrayExtra2);
                    }
                } catch (jv unused) {
                }
                miPushCommandMessage.setCommand(String.valueOf(jbVar2.a()));
                miPushCommandMessage.setResultCode(intent.getIntExtra("mipush_error_code", 0));
                miPushCommandMessage.setReason(intent.getStringExtra("mipush_error_msg"));
                com.xiaomi.channel.commonutils.logger.b.d("receive a error message. code = " + intent.getIntExtra("mipush_error_code", 0) + ", msg= " + intent.getStringExtra("mipush_error_msg"));
                return miPushCommandMessage;
            }
            if (!"com.xiaomi.mipush.MESSAGE_ARRIVED".equals(action)) {
                return null;
            }
            byte[] byteArrayExtra3 = intent.getByteArrayExtra("mipush_payload");
            if (byteArrayExtra3 == null) {
                com.xiaomi.channel.commonutils.logger.b.d("message arrived: receiving an empty message, drop");
                return null;
            }
            jb jbVar3 = new jb();
            try {
                jp.a(jbVar3, byteArrayExtra3);
                d dVarM156a2 = d.m156a(this.f131a);
                if (com.xiaomi.push.service.ai.m709a(jbVar3)) {
                    str = "message arrived: receive ignore reg message, ignore!";
                } else if (!dVarM156a2.m165c()) {
                    str = "message arrived: receive message without registration. need unregister or re-register!";
                } else {
                    if (!dVarM156a2.m165c() || !dVarM156a2.m167e()) {
                        return a(jbVar3, byteArrayExtra3);
                    }
                    str = "message arrived: app info is invalidated";
                }
                com.xiaomi.channel.commonutils.logger.b.d(str);
                return null;
            } catch (jv e3) {
                e = e3;
            } catch (Exception e4) {
                e = e4;
            }
        }
        com.xiaomi.channel.commonutils.logger.b.a(e);
        return null;
    }

    public List<String> a(TimeZone timeZone, TimeZone timeZone2, List<String> list) throws NumberFormatException {
        if (timeZone.equals(timeZone2)) {
            return list;
        }
        long rawOffset = ((timeZone.getRawOffset() - timeZone2.getRawOffset()) / 1000) / 60;
        long j2 = ((((Long.parseLong(list.get(0).split(":")[0]) * 60) + Long.parseLong(list.get(0).split(":")[1])) - rawOffset) + 1440) % 1440;
        long j3 = ((((Long.parseLong(list.get(1).split(":")[0]) * 60) + Long.parseLong(list.get(1).split(":")[1])) - rawOffset) + 1440) % 1440;
        ArrayList arrayList = new ArrayList();
        arrayList.add(String.format("%1$02d:%2$02d", Long.valueOf(j2 / 60), Long.valueOf(j2 % 60)));
        arrayList.add(String.format("%1$02d:%2$02d", Long.valueOf(j3 / 60), Long.valueOf(j3 % 60)));
        return arrayList;
    }
}
