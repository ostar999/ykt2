package com.mobile.auth.l;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.aliyun.vod.log.core.AliyunLogCommon;

/* loaded from: classes4.dex */
public class j {

    /* renamed from: b, reason: collision with root package name */
    @SuppressLint({"StaticFieldLeak"})
    private static j f10429b;

    /* renamed from: a, reason: collision with root package name */
    private final Context f10430a;

    private j(Context context) {
        this.f10430a = context;
    }

    public static j a() {
        return f10429b;
    }

    public static void a(Context context) {
        f10429b = new j(context);
    }

    private String b(String str) {
        str.hashCode();
        switch (str) {
            case "46000":
            case "46002":
            case "46004":
            case "46007":
                c.a("SIMUtils", "中国移动");
                return "1";
            case "46001":
            case "46006":
            case "46009":
                c.a("SIMUtils", "中国联通");
                return "2";
            case "46003":
            case "46005":
            case "46011":
                c.a("SIMUtils", "中国电信");
                return "3";
            default:
                return "0";
        }
    }

    public String a(String str) {
        if (TextUtils.isEmpty(str)) {
            str = c();
        }
        return b(str);
    }

    public String b() {
        try {
            int iA = com.mobile.auth.f.a.a().b().a();
            return iA >= 0 ? Integer.toString(iA) : "";
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public String c() {
        TelephonyManager telephonyManager = (TelephonyManager) this.f10430a.getSystemService(AliyunLogCommon.TERMINAL_TYPE);
        if (telephonyManager == null) {
            return "";
        }
        String simOperator = telephonyManager.getSimOperator();
        c.b("SIMUtils", "SysOperator= " + simOperator);
        return simOperator;
    }
}
