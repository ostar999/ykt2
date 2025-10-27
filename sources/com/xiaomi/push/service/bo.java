package com.xiaomi.push.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.MIPushNotificationHelper4Hybrid;
import com.xiaomi.push.jb;
import java.util.Map;

/* loaded from: classes6.dex */
public class bo {

    /* renamed from: a, reason: collision with root package name */
    public static Runnable f25661a;

    private static String a(Context context, String str) {
        return context.getSharedPreferences("typed_shield_pref", 4).getString(str + "_title", str);
    }

    public static String a(jb jbVar) {
        Map<String, String> mapM555a = jbVar.m595a().m555a();
        if (mapM555a == null) {
            return null;
        }
        return mapM555a.get("__typed_shield_type");
    }

    @TargetApi(19)
    public static void a(Context context, jb jbVar, Notification notification) {
        String strA = a(jbVar);
        if (TextUtils.isEmpty(strA) || !"com.xiaomi.xmsf".equals(ai.a(jbVar))) {
            return;
        }
        Bundle bundle = notification.extras;
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString(MIPushNotificationHelper4Hybrid.KEY_CATEGORY, strA);
        bundle.putString(MIPushNotificationHelper4Hybrid.KEY_SUBST_NAME, a(context, strA));
        notification.extras = bundle;
    }

    public static boolean a(Context context, jb jbVar) {
        Runnable runnable;
        if (!"com.xiaomi.xmsf".equals(ai.a(jbVar))) {
            return false;
        }
        String strA = a(jbVar);
        if (TextUtils.isEmpty(strA)) {
            return false;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("typed_shield_pref", 4);
        if (!sharedPreferences.contains(strA + "_shield") && (runnable = f25661a) != null) {
            runnable.run();
        }
        return sharedPreferences.getBoolean(strA + "_shield", true);
    }
}
