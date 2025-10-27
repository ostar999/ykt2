package com.mobile.auth.l;

import android.content.Context;

/* loaded from: classes4.dex */
public class g {
    public static boolean a(Context context, String str) {
        return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
    }
}
