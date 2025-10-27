package com.ta.a.b;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;

@TargetApi(23)
/* loaded from: classes6.dex */
class b {
    public static int a(Context context, String str, String str2) {
        return ((AppOpsManager) context.getSystemService(AppOpsManager.class)).noteProxyOp(str, str2);
    }

    public static String permissionToOp(String str) {
        return AppOpsManager.permissionToOp(str);
    }
}
