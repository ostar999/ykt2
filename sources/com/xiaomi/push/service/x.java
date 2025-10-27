package com.xiaomi.push.service;

import android.accounts.Account;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import com.tencent.connect.common.Constants;
import com.xiaomi.mipush.sdk.MIPushNotificationHelper4Hybrid;
import com.xiaomi.push.di;
import com.xiaomi.push.fl;
import com.xiaomi.push.fv;
import com.xiaomi.push.gn;
import com.xiaomi.push.gq;
import com.xiaomi.push.gs;
import com.xiaomi.push.gt;
import com.xiaomi.push.hh;
import com.xiaomi.push.hw;
import com.xiaomi.push.in;
import com.xiaomi.push.iq;
import com.xiaomi.push.iv;
import com.xiaomi.push.jb;
import com.xiaomi.push.je;
import com.xiaomi.push.jp;
import com.xiaomi.push.service.ai;
import com.xiaomi.push.service.at;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;

/* loaded from: classes6.dex */
public class x {
    public static Intent a(byte[] bArr, long j2) {
        jb jbVarA = a(bArr);
        if (jbVarA == null) {
            return null;
        }
        Intent intent = new Intent("com.xiaomi.mipush.RECEIVE_MESSAGE");
        intent.putExtra("mipush_payload", bArr);
        intent.putExtra("mrt", Long.toString(j2));
        intent.setPackage(jbVarA.f757b);
        return intent;
    }

    public static jb a(Context context, jb jbVar) {
        return a(context, jbVar, false, false, false);
    }

    public static jb a(Context context, jb jbVar, boolean z2, boolean z3, boolean z4) {
        iv ivVar = new iv();
        ivVar.b(jbVar.m596a());
        iq iqVarM595a = jbVar.m595a();
        if (iqVarM595a != null) {
            ivVar.a(iqVarM595a.m554a());
            ivVar.a(iqVarM595a.m552a());
            if (!TextUtils.isEmpty(iqVarM595a.m559b())) {
                ivVar.c(iqVarM595a.m559b());
            }
        }
        ivVar.a(jp.a(context, jbVar));
        ivVar.b(jp.a(z2, z3, z4));
        jb jbVarA = af.a(jbVar.b(), jbVar.m596a(), ivVar, hw.AckMessage);
        iq iqVarM553a = jbVar.m595a().m553a();
        iqVarM553a.a("mat", Long.toString(System.currentTimeMillis()));
        jbVarA.a(iqVarM553a);
        return jbVarA;
    }

    public static jb a(byte[] bArr) {
        jb jbVar = new jb();
        try {
            jp.a(jbVar, bArr);
            return jbVar;
        } catch (Throwable th) {
            com.xiaomi.channel.commonutils.logger.b.a(th);
            return null;
        }
    }

    private static void a(XMPushService xMPushService, jb jbVar) {
        xMPushService.a(new y(4, xMPushService, jbVar));
    }

    private static void a(XMPushService xMPushService, jb jbVar, String str) {
        xMPushService.a(new ac(4, xMPushService, jbVar, str));
    }

    private static void a(XMPushService xMPushService, jb jbVar, String str, String str2) {
        xMPushService.a(new ad(4, xMPushService, jbVar, str, str2));
    }

    public static void a(XMPushService xMPushService, jb jbVar, boolean z2, boolean z3, boolean z4) {
        a(xMPushService, jbVar, z2, z3, z4, false);
    }

    public static void a(XMPushService xMPushService, jb jbVar, boolean z2, boolean z3, boolean z4, boolean z5) {
        xMPushService.a(new ae(4, xMPushService, jbVar, z2, z3, z4, z5));
    }

    public static void a(XMPushService xMPushService, String str, byte[] bArr, Intent intent, boolean z2) {
        fl flVarA;
        String strB;
        String strB2;
        String strM554a;
        String str2;
        jb jbVarA = a(bArr);
        iq iqVarM595a = jbVarA.m595a();
        if (bArr != null) {
            di.a(jbVarA.b(), xMPushService.getApplicationContext(), null, jbVarA.a(), bArr.length);
        }
        if (c(jbVarA) && a(xMPushService, str)) {
            if (ai.d(jbVarA)) {
                fl.a(xMPushService.getApplicationContext()).a(jbVarA.b(), ai.b(jbVarA), iqVarM595a.m554a(), "old message received by new SDK.");
            }
            c(xMPushService, jbVarA);
            return;
        }
        if (a(jbVarA) && !a(xMPushService, str) && !b(jbVarA)) {
            if (ai.d(jbVarA)) {
                fl.a(xMPushService.getApplicationContext()).a(jbVarA.b(), ai.b(jbVarA), iqVarM595a.m554a(), "new message received by old SDK.");
            }
            d(xMPushService, jbVarA);
            return;
        }
        if ((!ai.m709a(jbVarA) || !com.xiaomi.push.g.m442b((Context) xMPushService, jbVarA.f757b)) && !a(xMPushService, intent)) {
            if (!com.xiaomi.push.g.m442b((Context) xMPushService, jbVarA.f757b)) {
                if (ai.d(jbVarA)) {
                    fl.a(xMPushService.getApplicationContext()).b(jbVarA.b(), ai.b(jbVarA), iqVarM595a.m554a(), "receive a message, but the package is removed.");
                }
                a(xMPushService, jbVarA);
                return;
            } else {
                com.xiaomi.channel.commonutils.logger.b.m117a("receive a mipush message, we can see the app, but we can't see the receiver.");
                if (ai.d(jbVarA)) {
                    fl.a(xMPushService.getApplicationContext()).b(jbVarA.b(), ai.b(jbVarA), iqVarM595a.m554a(), "receive a mipush message, we can see the app, but we can't see the receiver.");
                    return;
                }
                return;
            }
        }
        if (hw.Registration == jbVarA.a()) {
            String strB3 = jbVarA.b();
            SharedPreferences.Editor editorEdit = xMPushService.getSharedPreferences("pref_registered_pkg_names", 0).edit();
            editorEdit.putString(strB3, jbVarA.f753a);
            editorEdit.commit();
        }
        if (ai.c(jbVarA)) {
            fl.a(xMPushService.getApplicationContext()).a(jbVarA.b(), ai.b(jbVarA), iqVarM595a.m554a(), 1001, System.currentTimeMillis(), "receive notification message ");
            if (!TextUtils.isEmpty(iqVarM595a.m554a())) {
                intent.putExtra("messageId", iqVarM595a.m554a());
                intent.putExtra("eventMessageType", 1000);
            }
        }
        if (ai.m713b(jbVarA)) {
            fl.a(xMPushService.getApplicationContext()).a(jbVarA.b(), ai.b(jbVarA), iqVarM595a.m554a(), 2001, System.currentTimeMillis(), "receive passThrough message");
            if (!TextUtils.isEmpty(iqVarM595a.m554a())) {
                intent.putExtra("messageId", iqVarM595a.m554a());
                intent.putExtra("eventMessageType", 2000);
            }
        }
        if (ai.m709a(jbVarA)) {
            fl.a(xMPushService.getApplicationContext()).a(jbVarA.b(), ai.b(jbVarA), iqVarM595a.m554a(), 3001, System.currentTimeMillis(), "receive business message");
            if (!TextUtils.isEmpty(iqVarM595a.m554a())) {
                intent.putExtra("messageId", iqVarM595a.m554a());
                intent.putExtra("eventMessageType", 3000);
            }
        }
        if (iqVarM595a != null && !TextUtils.isEmpty(iqVarM595a.m562c()) && !TextUtils.isEmpty(iqVarM595a.d()) && iqVarM595a.f659b != 1 && (ai.m710a(iqVarM595a.m555a()) || !ai.m708a((Context) xMPushService, jbVarA.f757b))) {
            Map<String, String> map = iqVarM595a.f657a;
            String strM554a2 = map != null ? map.get("jobkey") : null;
            if (TextUtils.isEmpty(strM554a2)) {
                strM554a2 = iqVarM595a.m554a();
            }
            if (ak.a(xMPushService, jbVarA.f757b, strM554a2)) {
                fl.a(xMPushService.getApplicationContext()).c(jbVarA.b(), ai.b(jbVarA), iqVarM595a.m554a(), "drop a duplicate message");
                com.xiaomi.channel.commonutils.logger.b.m117a("drop a duplicate message, key=" + strM554a2);
            } else {
                ai.c cVarM706a = ai.m706a((Context) xMPushService, jbVarA, bArr);
                if (cVarM706a.f25577a > 0 && !TextUtils.isEmpty(cVarM706a.f992a)) {
                    hh.a(xMPushService, cVarM706a.f992a, cVarM706a.f25577a, true, false, System.currentTimeMillis());
                }
                if (!ai.m709a(jbVarA)) {
                    Intent intent2 = new Intent("com.xiaomi.mipush.MESSAGE_ARRIVED");
                    intent2.putExtra("mipush_payload", bArr);
                    intent2.setPackage(jbVarA.f757b);
                    try {
                        List<ResolveInfo> listQueryBroadcastReceivers = xMPushService.getPackageManager().queryBroadcastReceivers(intent2, 0);
                        if (listQueryBroadcastReceivers != null && !listQueryBroadcastReceivers.isEmpty()) {
                            xMPushService.sendBroadcast(intent2, af.a(jbVarA.f757b));
                        }
                    } catch (Exception e2) {
                        xMPushService.sendBroadcast(intent2, af.a(jbVarA.f757b));
                        fl.a(xMPushService.getApplicationContext()).b(jbVarA.b(), ai.b(jbVarA), iqVarM595a.m554a(), e2.getMessage());
                    }
                }
            }
            if (z2) {
                a(xMPushService, jbVarA, false, true, false);
            } else {
                b(xMPushService, jbVarA);
            }
        } else if ("com.xiaomi.xmsf".contains(jbVarA.f757b) && !jbVarA.m603b() && iqVarM595a != null && iqVarM595a.m555a() != null && iqVarM595a.m555a().containsKey("ab")) {
            b(xMPushService, jbVarA);
            com.xiaomi.channel.commonutils.logger.b.c("receive abtest message. ack it." + iqVarM595a.m554a());
        } else if (a(xMPushService, str, jbVarA, iqVarM595a)) {
            if (iqVarM595a != null && !TextUtils.isEmpty(iqVarM595a.m554a())) {
                if (ai.m713b(jbVarA)) {
                    fl.a(xMPushService.getApplicationContext()).a(jbVarA.b(), ai.b(jbVarA), iqVarM595a.m554a(), 2002, "try send passThrough message Broadcast");
                } else {
                    if (ai.m709a(jbVarA)) {
                        flVarA = fl.a(xMPushService.getApplicationContext());
                        strB = jbVarA.b();
                        strB2 = ai.b(jbVarA);
                        strM554a = iqVarM595a.m554a();
                        str2 = "try show awake message , but it don't show in foreground";
                    } else if (ai.c(jbVarA)) {
                        flVarA = fl.a(xMPushService.getApplicationContext());
                        strB = jbVarA.b();
                        strB2 = ai.b(jbVarA);
                        strM554a = iqVarM595a.m554a();
                        str2 = "try show notification message , but it don't show in foreground";
                    }
                    flVarA.a(strB, strB2, strM554a, str2);
                }
            }
            xMPushService.sendBroadcast(intent, af.a(jbVarA.f757b));
        } else {
            fl.a(xMPushService.getApplicationContext()).a(jbVarA.b(), ai.b(jbVarA), iqVarM595a.m554a(), "passThough message: not permit to send broadcast ");
        }
        if (jbVarA.a() != hw.UnRegistration || "com.xiaomi.xmsf".equals(xMPushService.getPackageName())) {
            return;
        }
        xMPushService.stopSelf();
    }

    private static void a(XMPushService xMPushService, byte[] bArr, long j2) throws NumberFormatException {
        boolean zA;
        Map<String, String> mapM555a;
        jb jbVarA = a(bArr);
        if (jbVarA == null) {
            return;
        }
        if (TextUtils.isEmpty(jbVarA.f757b)) {
            com.xiaomi.channel.commonutils.logger.b.m117a("receive a mipush message without package name");
            return;
        }
        Long lValueOf = Long.valueOf(System.currentTimeMillis());
        Intent intentA = a(bArr, lValueOf.longValue());
        String strA = ai.a(jbVarA);
        hh.a(xMPushService, strA, j2, true, true, System.currentTimeMillis());
        iq iqVarM595a = jbVarA.m595a();
        if (iqVarM595a != null) {
            iqVarM595a.a("mrt", Long.toString(lValueOf.longValue()));
        }
        hw hwVar = hw.SendMessage;
        String strM554a = "";
        if (hwVar == jbVarA.a() && u.a(xMPushService).m766a(jbVarA.f757b) && !ai.m709a(jbVarA)) {
            if (iqVarM595a != null) {
                strM554a = iqVarM595a.m554a();
                if (ai.d(jbVarA)) {
                    fl.a(xMPushService.getApplicationContext()).a(jbVarA.b(), ai.b(jbVarA), strM554a, "Drop a message for unregistered");
                }
            }
            com.xiaomi.channel.commonutils.logger.b.m117a("Drop a message for unregistered, msgid=" + strM554a);
            a(xMPushService, jbVarA, jbVarA.f757b);
            return;
        }
        if (hwVar == jbVarA.a() && u.a(xMPushService).m768c(jbVarA.f757b) && !ai.m709a(jbVarA)) {
            if (iqVarM595a != null) {
                strM554a = iqVarM595a.m554a();
                if (ai.d(jbVarA)) {
                    fl.a(xMPushService.getApplicationContext()).a(jbVarA.b(), ai.b(jbVarA), strM554a, "Drop a message for push closed");
                }
            }
            com.xiaomi.channel.commonutils.logger.b.m117a("Drop a message for push closed, msgid=" + strM554a);
            a(xMPushService, jbVarA, jbVarA.f757b);
            return;
        }
        if (hwVar == jbVarA.a() && !TextUtils.equals(xMPushService.getPackageName(), "com.xiaomi.xmsf") && !TextUtils.equals(xMPushService.getPackageName(), jbVarA.f757b)) {
            com.xiaomi.channel.commonutils.logger.b.m117a("Receive a message with wrong package name, expect " + xMPushService.getPackageName() + ", received " + jbVarA.f757b);
            a(xMPushService, jbVarA, "unmatched_package", "package should be " + xMPushService.getPackageName() + ", but got " + jbVarA.f757b);
            if (iqVarM595a == null || !ai.d(jbVarA)) {
                return;
            }
            fl.a(xMPushService.getApplicationContext()).a(jbVarA.b(), ai.b(jbVarA), iqVarM595a.m554a(), "Receive a message with wrong package name");
            return;
        }
        if (iqVarM595a != null && iqVarM595a.m554a() != null) {
            com.xiaomi.channel.commonutils.logger.b.m117a(String.format("receive a message, appid=%1$s, msgid= %2$s", jbVarA.m596a(), iqVarM595a.m554a()));
        }
        if (iqVarM595a != null && (mapM555a = iqVarM595a.m555a()) != null && mapM555a.containsKey("hide") && k.a.f27523u.equalsIgnoreCase(mapM555a.get("hide"))) {
            b(xMPushService, jbVarA);
            return;
        }
        if (iqVarM595a != null && iqVarM595a.m555a() != null && iqVarM595a.m555a().containsKey("__miid")) {
            String str = iqVarM595a.m555a().get("__miid");
            Account accountA = com.xiaomi.push.m.a((Context) xMPushService);
            if (accountA == null || !TextUtils.equals(str, accountA.name)) {
                if (ai.d(jbVarA)) {
                    fl.a(xMPushService.getApplicationContext()).a(jbVarA.b(), ai.b(jbVarA), iqVarM595a.m554a(), "miid already logout or anther already login");
                }
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(" should be login, but got ");
                sb.append(accountA);
                com.xiaomi.channel.commonutils.logger.b.m117a(sb.toString() == null ? "nothing" : accountA.name);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str);
                sb2.append(" should be login, but got ");
                sb2.append(accountA);
                a(xMPushService, jbVarA, "miid already logout or anther already login", sb2.toString() != null ? accountA.name : "nothing");
                return;
            }
        }
        boolean z2 = iqVarM595a != null && a(iqVarM595a.m555a());
        if (z2) {
            if (!j.e(xMPushService)) {
                a(xMPushService, jbVarA, false, false, false, true);
                return;
            }
            if (!(a(iqVarM595a) && j.d(xMPushService))) {
                zA = true;
            } else if (!m769a(xMPushService, jbVarA)) {
                return;
            } else {
                zA = a(xMPushService, iqVarM595a, bArr);
            }
            a(xMPushService, jbVarA, true, false, false);
            if (!zA) {
                return;
            }
        }
        a(xMPushService, strA, bArr, intentA, z2);
    }

    private static boolean a(Context context, Intent intent) {
        try {
            List<ResolveInfo> listQueryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(intent, 32);
            if (listQueryBroadcastReceivers != null) {
                if (!listQueryBroadcastReceivers.isEmpty()) {
                    return true;
                }
            }
            return false;
        } catch (Exception unused) {
            return true;
        }
    }

    private static boolean a(Context context, String str) {
        Intent intent = new Intent("com.xiaomi.mipush.miui.CLICK_MESSAGE");
        intent.setPackage(str);
        Intent intent2 = new Intent("com.xiaomi.mipush.miui.RECEIVE_MESSAGE");
        intent2.setPackage(str);
        PackageManager packageManager = context.getPackageManager();
        try {
            List<ResolveInfo> listQueryBroadcastReceivers = packageManager.queryBroadcastReceivers(intent2, 32);
            List<ResolveInfo> listQueryIntentServices = packageManager.queryIntentServices(intent, 32);
            if (listQueryBroadcastReceivers.isEmpty()) {
                if (listQueryIntentServices.isEmpty()) {
                    return false;
                }
            }
            return true;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            return false;
        }
    }

    public static boolean a(iq iqVar) {
        Map<String, String> mapM555a;
        if (iqVar == null || (mapM555a = iqVar.m555a()) == null) {
            return false;
        }
        return TextUtils.equals("1", mapM555a.get("__geo_local_check"));
    }

    private static boolean a(jb jbVar) {
        return "com.xiaomi.xmsf".equals(jbVar.f757b) && jbVar.m595a() != null && jbVar.m595a().m555a() != null && jbVar.m595a().m555a().containsKey("miui_package_name");
    }

    private static boolean a(XMPushService xMPushService, iq iqVar, byte[] bArr) throws NumberFormatException {
        Map<String, String> mapM555a = iqVar.m555a();
        String[] strArrSplit = mapM555a.get("__geo_ids").split(",");
        ArrayList<ContentValues> arrayList = new ArrayList<>();
        for (String str : strArrSplit) {
            if (g.a(xMPushService).m740a(str) != null) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("geo_id", str);
                contentValues.put(MIPushNotificationHelper4Hybrid.KEY_MESSAGE_ID, iqVar.m554a());
                int i2 = Integer.parseInt(mapM555a.get("__geo_action"));
                contentValues.put("action", Integer.valueOf(i2));
                contentValues.put("content", bArr);
                contentValues.put("deadline", Long.valueOf(Long.parseLong(mapM555a.get("__geo_deadline"))));
                if (TextUtils.equals(g.a(xMPushService).m741a(str), "Enter") && i2 == 1) {
                    return true;
                }
                arrayList.add(contentValues);
            }
        }
        if (!i.a(xMPushService).a(arrayList)) {
            com.xiaomi.channel.commonutils.logger.b.c("geofence added some new geofence message failed messagi_id:" + iqVar.m554a());
        }
        return false;
    }

    /* renamed from: a, reason: collision with other method in class */
    private static boolean m769a(XMPushService xMPushService, jb jbVar) {
        if (!j.a(xMPushService) || !j.c(xMPushService)) {
            return false;
        }
        if (com.xiaomi.push.g.m442b((Context) xMPushService, jbVar.f757b)) {
            Map<String, String> mapM555a = jbVar.m595a().m555a();
            return (mapM555a == null || !Constants.VIA_REPORT_TYPE_SET_AVATAR.contains(mapM555a.get("__geo_action")) || TextUtils.isEmpty(mapM555a.get("__geo_ids"))) ? false : true;
        }
        a(xMPushService, jbVar);
        return false;
    }

    private static boolean a(XMPushService xMPushService, String str, jb jbVar, iq iqVar) {
        boolean z2 = true;
        if (iqVar != null && iqVar.m555a() != null && iqVar.m555a().containsKey("__check_alive") && iqVar.m555a().containsKey("__awake")) {
            je jeVar = new je();
            jeVar.b(jbVar.m596a());
            jeVar.d(str);
            jeVar.c(in.AwakeSystemApp.f622a);
            jeVar.a(iqVar.m554a());
            jeVar.f769a = new HashMap();
            boolean zM441a = com.xiaomi.push.g.m441a(xMPushService.getApplicationContext(), str);
            jeVar.f769a.put("app_running", Boolean.toString(zM441a));
            if (!zM441a) {
                boolean z3 = Boolean.parseBoolean(iqVar.m555a().get("__awake"));
                jeVar.f769a.put("awaked", Boolean.toString(z3));
                if (!z3) {
                    z2 = false;
                }
            }
            try {
                af.a(xMPushService, af.a(jbVar.b(), jbVar.m596a(), jeVar, hw.Notification));
            } catch (gn e2) {
                com.xiaomi.channel.commonutils.logger.b.a(e2);
            }
        }
        return z2;
    }

    private static boolean a(Map<String, String> map) {
        return map != null && map.containsKey("__geo_ids");
    }

    private static void b(XMPushService xMPushService, jb jbVar) {
        xMPushService.a(new z(4, xMPushService, jbVar));
    }

    private static boolean b(jb jbVar) {
        Map<String, String> mapM555a = jbVar.m595a().m555a();
        return mapM555a != null && mapM555a.containsKey("notify_effect");
    }

    private static void c(XMPushService xMPushService, jb jbVar) {
        xMPushService.a(new aa(4, xMPushService, jbVar));
    }

    private static boolean c(jb jbVar) {
        if (jbVar.m595a() == null || jbVar.m595a().m555a() == null) {
            return false;
        }
        return "1".equals(jbVar.m595a().m555a().get("obslete_ads_message"));
    }

    private static void d(XMPushService xMPushService, jb jbVar) {
        xMPushService.a(new ab(4, xMPushService, jbVar));
    }

    public void a(Context context, at.b bVar, boolean z2, int i2, String str) {
        s sVarA;
        if (z2 || (sVarA = t.a(context)) == null || !"token-expired".equals(str)) {
            return;
        }
        try {
            t.a(context, sVarA.f25717f, sVarA.f25715d, sVarA.f25716e);
        } catch (IOException | JSONException e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
        }
    }

    public void a(XMPushService xMPushService, fv fvVar, at.b bVar) {
        try {
            a(xMPushService, fvVar.m433a(bVar.f25598h), fvVar.c());
        } catch (IllegalArgumentException e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
        }
    }

    public void a(XMPushService xMPushService, gt gtVar, at.b bVar) {
        if (!(gtVar instanceof gs)) {
            com.xiaomi.channel.commonutils.logger.b.m117a("not a mipush message");
            return;
        }
        gs gsVar = (gs) gtVar;
        gq gqVarA = gsVar.a("s");
        if (gqVarA != null) {
            try {
                a(xMPushService, bc.a(bc.a(bVar.f25598h, gsVar.j()), gqVarA.c()), hh.a(gtVar.mo468a()));
            } catch (IllegalArgumentException e2) {
                com.xiaomi.channel.commonutils.logger.b.a(e2);
            }
        }
    }
}
