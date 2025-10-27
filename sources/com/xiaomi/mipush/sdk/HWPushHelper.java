package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class HWPushHelper {

    /* renamed from: a, reason: collision with root package name */
    private static boolean f24502a = false;

    public static void convertMessage(Intent intent) {
        i.a(intent);
    }

    public static boolean hasNetwork(Context context) {
        return i.m175a(context);
    }

    public static boolean isHmsTokenSynced(Context context) {
        String strA = i.a(f.ASSEMBLE_PUSH_HUAWEI);
        if (TextUtils.isEmpty(strA)) {
            return false;
        }
        String strA2 = i.a(context, strA);
        String strA3 = ap.a(context).a(be.UPLOAD_HUAWEI_TOKEN);
        return (TextUtils.isEmpty(strA2) || TextUtils.isEmpty(strA3) || !"synced".equals(strA3)) ? false : true;
    }

    public static boolean isUserOpenHmsPush() {
        return MiPushClient.getOpenHmsPush();
    }

    public static boolean needConnect() {
        return f24502a;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x002b, code lost:
    
        r2 = r3.getString("pushMsg");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void notifyHmsNotificationMessageClicked(android.content.Context r5, java.lang.String r6) throws org.json.JSONException {
        /*
            java.lang.String r0 = "pushMsg"
            boolean r1 = android.text.TextUtils.isEmpty(r6)
            java.lang.String r2 = ""
            if (r1 != 0) goto L38
            org.json.JSONArray r1 = new org.json.JSONArray     // Catch: java.lang.Exception -> L30
            r1.<init>(r6)     // Catch: java.lang.Exception -> L30
            int r6 = r1.length()     // Catch: java.lang.Exception -> L30
            if (r6 <= 0) goto L38
            r6 = 0
        L17:
            int r3 = r1.length()     // Catch: java.lang.Exception -> L30
            if (r6 >= r3) goto L38
            org.json.JSONObject r3 = r1.getJSONObject(r6)     // Catch: java.lang.Exception -> L30
            boolean r4 = r3.has(r0)     // Catch: java.lang.Exception -> L30
            if (r4 == 0) goto L2d
            java.lang.String r6 = r3.getString(r0)     // Catch: java.lang.Exception -> L30
            r2 = r6
            goto L38
        L2d:
            int r6 = r6 + 1
            goto L17
        L30:
            r6 = move-exception
            java.lang.String r6 = r6.toString()
            com.xiaomi.channel.commonutils.logger.b.d(r6)
        L38:
            com.xiaomi.mipush.sdk.PushMessageReceiver r6 = com.xiaomi.mipush.sdk.i.a(r5)
            if (r6 == 0) goto L45
            com.xiaomi.mipush.sdk.MiPushMessage r0 = com.xiaomi.mipush.sdk.i.a(r2)
            r6.onNotificationMessageClicked(r5, r0)
        L45:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.mipush.sdk.HWPushHelper.notifyHmsNotificationMessageClicked(android.content.Context, java.lang.String):void");
    }

    public static void notifyHmsPassThoughMessageArrived(Context context, String str) throws JSONException {
        String string = "";
        try {
            if (!TextUtils.isEmpty(str)) {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("content")) {
                    string = jSONObject.getString("content");
                }
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.d(e2.toString());
        }
        PushMessageReceiver pushMessageReceiverA = i.a(context);
        if (pushMessageReceiverA != null) {
            pushMessageReceiverA.onReceivePassThroughMessage(context, i.a(string));
        }
    }

    public static void registerHuaWeiAssemblePush(Context context) {
        AbstractPushManager abstractPushManagerA = g.a(context).a(f.ASSEMBLE_PUSH_HUAWEI);
        if (abstractPushManagerA != null) {
            abstractPushManagerA.register();
        }
    }

    public static void reportError(String str, int i2) {
        i.a(str, i2);
    }

    public static synchronized void setConnectTime(Context context) {
        context.getSharedPreferences("mipush_extra", 0).edit().putLong("last_connect_time", System.currentTimeMillis()).commit();
    }

    public static synchronized void setGetTokenTime(Context context) {
        context.getSharedPreferences("mipush_extra", 0).edit().putLong("last_get_token_time", System.currentTimeMillis()).commit();
    }

    public static void setNeedConnect(boolean z2) {
        f24502a = z2;
    }

    public static synchronized boolean shouldGetToken(Context context) {
        return Math.abs(System.currentTimeMillis() - context.getSharedPreferences("mipush_extra", 0).getLong("last_get_token_time", -1L)) > 172800000;
    }

    public static synchronized boolean shouldTryConnect(Context context) {
        return Math.abs(System.currentTimeMillis() - context.getSharedPreferences("mipush_extra", 0).getLong("last_connect_time", -1L)) > 5000;
    }

    public static void uploadToken(Context context, String str) {
        i.a(context, f.ASSEMBLE_PUSH_HUAWEI, str);
    }
}
