package com.tencent.tbs.one.impl.a;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

/* loaded from: classes6.dex */
public final class n {

    /* renamed from: a, reason: collision with root package name */
    public static SharedPreferences f21763a;

    /* renamed from: b, reason: collision with root package name */
    public static SharedPreferences.Editor f21764b;

    public static String a() {
        return Build.FINGERPRINT + new Random().nextInt(2147483646);
    }

    public static String a(Context context) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences("tsui", 0);
        f21763a = sharedPreferences;
        if (sharedPreferences != null) {
            String string = sharedPreferences.getString("tsui", "");
            if (!TextUtils.isEmpty(string)) {
                g.a("getSDKUID from sp is " + string, new Object[0]);
                return string;
            }
        }
        StringBuilder sb = new StringBuilder();
        String strA = a();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(System.currentTimeMillis());
        String string2 = sb2.toString();
        String strC = c(context);
        if (strA != null && strA.length() > 0) {
            sb.append(strA);
            sb.append(HiAnalyticsConstant.REPORT_VAL_SEPARATOR);
        }
        if (string2 != null && string2.length() > 0) {
            sb.append(string2);
            sb.append(HiAnalyticsConstant.REPORT_VAL_SEPARATOR);
        }
        if (strC != null && strC.length() > 0) {
            sb.append(strC);
            sb.append(HiAnalyticsConstant.REPORT_VAL_SEPARATOR);
        }
        String strReplace = b(context).replace("-", "");
        if (strReplace != null && strReplace.length() > 0) {
            sb.append(strReplace);
        }
        if (sb.length() > 0) {
            try {
                String strA2 = a(a(sb.toString()));
                if (strA2 != null && strA2.length() > 0) {
                    g.a("getSDKUID sha1 is " + strA2, new Object[0]);
                    a(context, "tsui", strA2);
                    return strA2;
                }
            } catch (Exception e2) {
                Log.getStackTraceString(e2);
            }
        }
        String strReplace2 = UUID.randomUUID().toString().replace("-", "");
        g.a("getSDKUID default is " + strReplace2, new Object[0]);
        a(context, "tsui", strReplace2);
        return strReplace2;
    }

    public static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b3 : bArr) {
            String hexString = Integer.toHexString(b3 & 255);
            if (hexString.length() == 1) {
                sb.append("0");
            }
            sb.append(hexString);
        }
        return sb.toString().toUpperCase(Locale.CHINA);
    }

    public static void a(Context context, String str, String str2) {
        if (f21763a == null) {
            f21763a = context.getApplicationContext().getSharedPreferences("tsui", 0);
        }
        SharedPreferences.Editor editorEdit = f21763a.edit();
        f21764b = editorEdit;
        editorEdit.putString(str, str2);
        f21764b.commit();
    }

    public static byte[] a(String str) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
            return messageDigest.digest();
        } catch (Exception unused) {
            return "".getBytes();
        }
    }

    public static String b(Context context) {
        try {
            String strB = e.b(context);
            int iNextInt = new Random().nextInt(2147483646);
            StringBuilder sb = new StringBuilder();
            sb.append(iNextInt);
            sb.append(Build.BOARD.length() % 10);
            sb.append(Build.BRAND.length() % 10);
            sb.append(Build.DEVICE.length() % 10);
            sb.append(Build.HARDWARE.length() % 10);
            sb.append(Build.ID.length() % 10);
            sb.append(strB.length() % 10);
            sb.append(Build.PRODUCT.length() % 10);
            sb.append(c(context).length() % 10);
            return new UUID(sb.toString().hashCode(), c(context).hashCode()).toString();
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static String c(Context context) {
        try {
            String string = context.getSharedPreferences("uifa", 0).getString("serial", "");
            if (!TextUtils.isEmpty(string) && !string.contains("unknown")) {
                return string;
            }
            return a();
        } catch (Exception unused) {
            return "unknown";
        }
    }
}
