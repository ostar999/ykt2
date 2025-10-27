package com.alipay.apmobilesecuritysdk.c;

import android.content.Context;
import android.os.Build;
import cn.hutool.core.date.DatePattern;
import com.alipay.security.mobile.module.d.d;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/* loaded from: classes2.dex */
public final class a {
    public static synchronized void a(Context context, String str, String str2, String str3) {
        com.alipay.security.mobile.module.d.a aVarB = b(context, str, str2, str3);
        d.a(context.getFilesDir().getAbsolutePath() + "/log/ap", new SimpleDateFormat(DatePattern.PURE_DATE_PATTERN).format(Calendar.getInstance().getTime()) + ".log", aVarB.toString());
    }

    public static synchronized void a(String str) {
        d.a(str);
    }

    public static synchronized void a(Throwable th) {
        d.a(th);
    }

    private static com.alipay.security.mobile.module.d.a b(Context context, String str, String str2, String str3) {
        String packageName;
        try {
            packageName = context.getPackageName();
        } catch (Throwable unused) {
            packageName = "";
        }
        return new com.alipay.security.mobile.module.d.a(Build.MODEL, packageName, "APPSecuritySDK-ALIPAY", "3.2.2-20180331", str, str2, str3);
    }
}
