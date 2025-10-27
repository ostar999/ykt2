package com.umeng.analytics.pro;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.umeng.commonsdk.utils.UMUtils;

/* loaded from: classes6.dex */
public class ac {

    /* renamed from: a, reason: collision with root package name */
    private static final String f22420a = "ro.build.version.emui";

    /* renamed from: b, reason: collision with root package name */
    private static final String f22421b = "hw_sc.build.platform.version";

    public static z a(Context context) {
        String str = Build.BRAND;
        al.a("Device", "Brand", str);
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.equalsIgnoreCase("huawei") || str.equalsIgnoreCase("honor") || str.equalsIgnoreCase("华为")) {
            return new ad();
        }
        if (str.equalsIgnoreCase("xiaomi") || str.equalsIgnoreCase("redmi") || str.equalsIgnoreCase("meitu") || str.equalsIgnoreCase("小米") || str.equalsIgnoreCase("blackshark")) {
            return new ak();
        }
        if (str.equalsIgnoreCase("vivo")) {
            return new aj();
        }
        if (str.equalsIgnoreCase("oppo") || str.equalsIgnoreCase("oneplus") || str.equalsIgnoreCase("realme")) {
            return new ah();
        }
        if (str.equalsIgnoreCase("lenovo") || str.equalsIgnoreCase("zuk")) {
            return new ae();
        }
        if (str.equalsIgnoreCase("nubia")) {
            return new ag();
        }
        if (Build.MANUFACTURER.equalsIgnoreCase("SAMSUNG")) {
            return new ai();
        }
        if (UMUtils.isAppInstalled(context, "com.coolpad.deviceidsupport")) {
            return new ab();
        }
        if (a()) {
            return new ad();
        }
        if (str.equalsIgnoreCase("meizu") || str.equalsIgnoreCase("mblu")) {
            return new af();
        }
        return null;
    }

    private static boolean a() {
        return (TextUtils.isEmpty(a(f22420a)) && TextUtils.isEmpty(a(f22421b))) ? false : true;
    }

    private static String a(String str) {
        try {
            return (String) Class.forName("android.os.SystemProperties").getDeclaredMethod("get", String.class).invoke(null, str);
        } catch (Throwable unused) {
            return "";
        }
    }
}
