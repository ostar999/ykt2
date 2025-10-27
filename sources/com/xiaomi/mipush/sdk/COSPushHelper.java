package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

/* loaded from: classes6.dex */
public class COSPushHelper {

    /* renamed from: a, reason: collision with root package name */
    private static long f24501a = 0;

    /* renamed from: a, reason: collision with other field name */
    private static volatile boolean f102a = false;

    public static void convertMessage(Intent intent) {
        i.a(intent);
    }

    public static void doInNetworkChange(Context context) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (getNeedRegister()) {
            long j2 = f24501a;
            if (j2 <= 0 || j2 + 300000 <= jElapsedRealtime) {
                f24501a = jElapsedRealtime;
                registerCOSAssemblePush(context);
            }
        }
    }

    public static boolean getNeedRegister() {
        return f102a;
    }

    public static boolean hasNetwork(Context context) {
        return i.m175a(context);
    }

    public static void onNotificationMessageCome(Context context, String str) {
    }

    public static void onPassThoughMessageCome(Context context, String str) {
    }

    public static void registerCOSAssemblePush(Context context) {
        AbstractPushManager abstractPushManagerA = g.a(context).a(f.ASSEMBLE_PUSH_COS);
        if (abstractPushManagerA != null) {
            com.xiaomi.channel.commonutils.logger.b.m117a("ASSEMBLE_PUSH :  register cos when network change!");
            abstractPushManagerA.register();
        }
    }

    public static synchronized void setNeedRegister(boolean z2) {
        f102a = z2;
    }

    public static void uploadToken(Context context, String str) {
        i.a(context, f.ASSEMBLE_PUSH_COS, str);
    }
}
