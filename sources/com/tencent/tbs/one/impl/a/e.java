package com.tencent.tbs.one.impl.a;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.tbs.one.TBSOnePrivacy;
import com.tencent.tbs.one.impl.a.a;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/* loaded from: classes6.dex */
public final class e {

    /* renamed from: a, reason: collision with root package name */
    public static String f21738a = "";

    /* renamed from: b, reason: collision with root package name */
    public static String f21739b = "";

    /* renamed from: c, reason: collision with root package name */
    public static String f21740c = "";

    /* renamed from: d, reason: collision with root package name */
    public static String f21741d = "";

    /* renamed from: e, reason: collision with root package name */
    public static String f21742e = null;

    /* renamed from: f, reason: collision with root package name */
    public static boolean f21743f = false;

    public static String a() {
        return f21738a;
    }

    public static String a(long j2) {
        return String.format(Locale.getDefault(), "%d(%s)", Long.valueOf(j2), new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault()).format(new Date(j2)));
    }

    public static String a(Context context) {
        return TBSOnePrivacy.AndroidId.get(context);
    }

    public static String a(CharSequence charSequence, Iterable<? extends CharSequence> iterable) {
        if (iterable == null) {
            return "";
        }
        StringBuilder sb = null;
        for (CharSequence charSequence2 : iterable) {
            if (sb == null) {
                sb = new StringBuilder();
            } else {
                sb.append(charSequence);
            }
            sb.append(charSequence2);
        }
        return sb == null ? "" : sb.toString();
    }

    public static String a(byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b3 : bArr) {
            int i2 = b3 & 255;
            if (i2 < 16) {
                sb.append("0");
            }
            sb.append(Long.toString(i2, 16));
        }
        return sb.toString();
    }

    public static void a(String str) {
        f21738a = str;
    }

    public static void a(boolean z2) {
        f21743f = z2;
    }

    public static boolean a(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Boolean) {
            return ((Boolean) obj).booleanValue();
        }
        if (obj instanceof Number) {
            return ((Number) obj).intValue() != 0;
        }
        if (obj instanceof String) {
            return Boolean.parseBoolean((String) obj);
        }
        return true;
    }

    public static String b() {
        return f21739b;
    }

    public static String b(Context context) {
        TBSOnePrivacy tBSOnePrivacy = TBSOnePrivacy.DeviceModel;
        if (tBSOnePrivacy.isDisabled()) {
            return "";
        }
        try {
            if (!a.a(context, a.EnumC0360a.Mqq)) {
                return a.a(context, a.EnumC0360a.Mtt, a.EnumC0360a.SogouExplorer, a.EnumC0360a.SogouReader) ? tBSOnePrivacy.get(context) : Build.MODEL;
            }
            SharedPreferences sharedPreferences = context.getSharedPreferences("uifa", 0);
            String string = sharedPreferences.getString("model", "");
            if (!TextUtils.isEmpty(string)) {
                return string;
            }
            String str = Build.MODEL;
            sharedPreferences.edit().putString("model", str).commit();
            return str;
        } catch (Throwable unused) {
            return "";
        }
    }

    public static void b(String str) {
        f21739b = str;
    }

    public static String c() {
        return "";
    }

    public static String c(Context context) {
        if (!TextUtils.isEmpty(f21739b)) {
            return f21739b;
        }
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String d() {
        return Build.CPU_ABI;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0094  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String d(android.content.Context r6) {
        /*
            java.lang.String r0 = "ISO8859-1"
            java.lang.String r1 = "UTF-8"
            java.lang.String r2 = com.tencent.tbs.one.impl.a.e.f21742e
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto Lf
            java.lang.String r6 = com.tencent.tbs.one.impl.a.e.f21742e
            return r6
        Lf:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = android.os.Build.VERSION.RELEASE
            java.lang.String r4 = new java.lang.String     // Catch: java.lang.Exception -> L20
            byte[] r5 = r3.getBytes(r1)     // Catch: java.lang.Exception -> L20
            r4.<init>(r5, r0)     // Catch: java.lang.Exception -> L20
            r3 = r4
        L20:
            boolean r4 = android.text.TextUtils.isEmpty(r3)
            if (r4 == 0) goto L28
            java.lang.String r3 = "1.0"
        L28:
            r2.append(r3)
            java.lang.String r3 = "; "
            r2.append(r3)
            java.util.Locale r4 = java.util.Locale.getDefault()
            java.lang.String r5 = r4.getLanguage()
            if (r5 == 0) goto L51
            java.lang.String r5 = r5.toLowerCase()
            r2.append(r5)
            java.lang.String r4 = r4.getCountry()
            if (r4 == 0) goto L56
            java.lang.String r5 = "-"
            r2.append(r5)
            java.lang.String r4 = r4.toLowerCase()
            goto L53
        L51:
            java.lang.String r4 = "en"
        L53:
            r2.append(r4)
        L56:
            java.lang.String r4 = android.os.Build.VERSION.CODENAME
            java.lang.String r5 = "REL"
            boolean r4 = r5.equals(r4)
            java.lang.String r5 = ""
            if (r4 == 0) goto L7d
            java.lang.String r6 = b(r6)
            java.lang.String r4 = new java.lang.String     // Catch: java.lang.Exception -> L70
            byte[] r1 = r6.getBytes(r1)     // Catch: java.lang.Exception -> L70
            r4.<init>(r1, r0)     // Catch: java.lang.Exception -> L70
            r6 = r4
        L70:
            r2.append(r3)
            boolean r0 = android.text.TextUtils.isEmpty(r6)
            if (r0 == 0) goto L7a
            r6 = r5
        L7a:
            r2.append(r6)
        L7d:
            java.lang.String r6 = android.os.Build.ID
            if (r6 != 0) goto L83
            r6 = 0
            goto L89
        L83:
            java.lang.String r0 = "[一-龥]"
            java.lang.String r6 = r6.replaceAll(r0, r5)
        L89:
            java.lang.String r0 = " Build/"
            r2.append(r0)
            boolean r0 = android.text.TextUtils.isEmpty(r6)
            if (r0 == 0) goto L96
            java.lang.String r6 = "00"
        L96:
            r2.append(r6)
            r6 = 1
            java.lang.Object[] r6 = new java.lang.Object[r6]
            r0 = 0
            r6[r0] = r2
            java.lang.String r0 = "Mozilla/5.0 (Linux; U; Android %s) AppleWebKit/533.1 (KHTML, like Gecko)Version/4.0 Mobile Safari/533.1"
            java.lang.String r6 = java.lang.String.format(r0, r6)
            com.tencent.tbs.one.impl.a.e.f21742e = r6
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.tbs.one.impl.a.e.d(android.content.Context):java.lang.String");
    }

    public static List<ActivityManager.RunningAppProcessInfo> e(Context context) {
        ActivityManager activityManager;
        if (f21743f || (activityManager = (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)) == null) {
            return null;
        }
        return activityManager.getRunningAppProcesses();
    }

    public static boolean e() {
        return f21743f;
    }

    public static boolean f(Context context) {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        return connectivityManager != null && (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) != null && activeNetworkInfo.isConnected() && activeNetworkInfo.getType() == 1;
    }
}
