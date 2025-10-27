package com.huawei.hms.aaid.init;

import android.content.Context;
import android.content.pm.PackageManager;
import com.huawei.hms.opendevice.f;
import com.huawei.hms.opendevice.i;
import com.huawei.hms.support.log.HMSLog;

/* loaded from: classes4.dex */
public class AutoInitHelper {
    public static void doAutoInit(Context context) {
        try {
            if (isAutoInitEnabled(context)) {
                HMSLog.i("AutoInit", "Push init start");
                new Thread(new f(context)).start();
            }
        } catch (Exception e2) {
            HMSLog.e("AutoInit", "Push init failed", e2);
        }
    }

    public static boolean isAutoInitEnabled(Context context) {
        i iVarA = i.a(context);
        if (iVarA.containsKey("push_kit_auto_init_enabled")) {
            return iVarA.getBoolean("push_kit_auto_init_enabled");
        }
        try {
            return context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getBoolean("push_kit_auto_init_enabled");
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static void setAutoInitEnabled(Context context, boolean z2) {
        i iVarA = i.a(context);
        boolean z3 = iVarA.getBoolean("push_kit_auto_init_enabled");
        iVarA.saveBoolean("push_kit_auto_init_enabled", z2);
        if (!z2 || z3) {
            return;
        }
        doAutoInit(context);
    }
}
