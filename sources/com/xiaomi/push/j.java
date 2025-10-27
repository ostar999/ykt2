package com.xiaomi.push;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.hjq.permissions.Permission;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

/* loaded from: classes6.dex */
public class j {

    /* renamed from: a, reason: collision with root package name */
    private static String f25331a = null;

    /* renamed from: a, reason: collision with other field name */
    private static volatile boolean f736a = false;

    /* renamed from: b, reason: collision with root package name */
    private static String f25332b = "";

    /* renamed from: c, reason: collision with root package name */
    private static String f25333c;

    /* renamed from: d, reason: collision with root package name */
    private static String f25334d;

    /* renamed from: e, reason: collision with root package name */
    private static String f25335e;

    @TargetApi(17)
    public static int a() {
        Object objA = at.a("android.os.UserHandle", "myUserId", new Object[0]);
        if (objA == null) {
            return -1;
        }
        return ((Integer) Integer.class.cast(objA)).intValue();
    }

    /* renamed from: a, reason: collision with other method in class */
    public static String m586a() {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 < 26) {
            return Build.SERIAL;
        }
        if (i2 >= 26) {
            return (String) at.a("android.os.Build", "getSerial", (Object[]) null);
        }
        return null;
    }

    public static String a(Context context) {
        return "a-" + ay.b(((String) null) + e(context) + ((String) null));
    }

    public static String a(Context context, boolean z2) {
        if (f25333c == null) {
            String strF = !n.d() ? z2 ? f(context) : n(context) : "";
            String strE = e(context);
            String strM586a = m586a();
            StringBuilder sb = new StringBuilder();
            sb.append("a-");
            sb.append(ay.b(strF + strE + strM586a));
            f25333c = sb.toString();
        }
        return f25333c;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static ArrayList<String> m587a(Context context) {
        g(context);
        i(context);
        if (TextUtils.isEmpty(f25331a)) {
            return null;
        }
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(f25331a);
        if (TextUtils.isEmpty(f25332b)) {
            return arrayList;
        }
        for (String str : f25332b.split(",")) {
            arrayList.add(str);
        }
        return arrayList;
    }

    public static void a(Context context, String str) {
        com.xiaomi.channel.commonutils.logger.b.c("update vdevid = " + str);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        f25335e = str;
        w wVarA = null;
        try {
            try {
                if (m588a(context)) {
                    File file = new File(Environment.getExternalStorageDirectory(), "/Xiaomi/");
                    if (file.exists() && file.isFile()) {
                        file.delete();
                    }
                    File file2 = new File(file, ".vdevid");
                    wVarA = w.a(context, file2);
                    y.m773a(file2);
                    y.a(file2, f25335e);
                }
                y.a(new File(context.getFilesDir(), ".vdevid"), f25335e);
                if (wVarA == null) {
                    return;
                }
            } catch (IOException e2) {
                com.xiaomi.channel.commonutils.logger.b.m117a("update vdevid failure :" + e2.getMessage());
                if (wVarA == null) {
                    return;
                }
            }
            wVarA.a();
        } catch (Throwable th) {
            if (wVarA != null) {
                wVarA.a();
            }
            throw th;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    private static boolean m588a(Context context) {
        if (!o.a(context, Permission.WRITE_EXTERNAL_STORAGE) || n.m679a()) {
            return false;
        }
        boolean z2 = Build.VERSION.SDK_INT >= 26;
        return !z2 ? v.m772a(context) : z2;
    }

    private static boolean a(String str) {
        return !TextUtils.isEmpty(str) && str.length() <= 15 && str.length() >= 14 && ay.m211b(str) && !ay.m212c(str);
    }

    public static String b(Context context) {
        try {
            return k.a(context).a();
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m117a("failure to get gaid:" + e2.getMessage());
            return null;
        }
    }

    /* renamed from: b, reason: collision with other method in class */
    private static boolean m589b(Context context) {
        return context.getPackageManager().checkPermission(Permission.READ_PHONE_STATE, context.getPackageName()) == 0;
    }

    private static boolean b(String str) {
        boolean z2;
        if (TextUtils.isEmpty(str) || str.length() != 17 || !Pattern.compile("^([A-Fa-f0-9]{2}[-,:]){5}[A-Fa-f0-9]{2}$").matcher(str).matches()) {
            return false;
        }
        char cCharAt = str.charAt(0);
        if (cCharAt == '0' || cCharAt == 'f' || cCharAt == 'F') {
            int i2 = 1;
            while (i2 < str.length()) {
                if (str.charAt(i2) != cCharAt) {
                    z2 = false;
                    break;
                }
                if (i2 < str.length() - 1) {
                    int i3 = i2 + 1;
                    if (str.charAt(i3) == '-' || str.charAt(i3) == ':') {
                        i2 = i3;
                    }
                }
                i2++;
            }
            z2 = true;
        } else {
            z2 = true;
        }
        return !z2;
    }

    public static String c(Context context) throws Throwable {
        w wVarA = null;
        if (!m588a(context)) {
            return null;
        }
        if (!TextUtils.isEmpty(f25335e)) {
            return f25335e;
        }
        String strA = y.a(new File(context.getFilesDir(), ".vdevid"));
        f25335e = strA;
        if (!TextUtils.isEmpty(strA)) {
            return f25335e;
        }
        try {
            try {
                File file = new File(new File(Environment.getExternalStorageDirectory(), "/Xiaomi/"), ".vdevid");
                wVarA = w.a(context, file);
                f25335e = "";
                String strA2 = y.a(file);
                if (strA2 != null) {
                    f25335e = strA2;
                }
                String str = f25335e;
                if (wVarA != null) {
                    wVarA.a();
                }
                return str;
            } catch (IOException e2) {
                com.xiaomi.channel.commonutils.logger.b.m117a("getVDevID failure :" + e2.getMessage());
                if (wVarA != null) {
                    wVarA.a();
                }
                return f25335e;
            }
        } catch (Throwable th) {
            if (wVarA != null) {
                wVarA.a();
            }
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00ca  */
    /* JADX WARN: Type inference failed for: r2v0, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v4, types: [boolean] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String d(android.content.Context r6) throws java.lang.Throwable {
        /*
            boolean r0 = m588a(r6)
            r1 = 0
            if (r0 == 0) goto Lce
            boolean r0 = com.xiaomi.push.j.f736a
            if (r0 == 0) goto Ld
            goto Lce
        Ld:
            r0 = 1
            com.xiaomi.push.j.f736a = r0
            java.io.File r0 = new java.io.File
            java.io.File r2 = r6.getFilesDir()
            java.lang.String r3 = ".vdevid"
            r0.<init>(r2, r3)
            java.lang.String r0 = com.xiaomi.push.y.a(r0)
            java.io.File r2 = new java.io.File     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L42
            java.io.File r4 = android.os.Environment.getExternalStorageDirectory()     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L42
            java.lang.String r5 = "/Xiaomi/"
            r2.<init>(r4, r5)     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L42
            java.io.File r4 = new java.io.File     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L42
            r4.<init>(r2, r3)     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L42
            com.xiaomi.push.w r2 = com.xiaomi.push.w.a(r6, r4)     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L42
            java.lang.String r3 = com.xiaomi.push.y.a(r4)     // Catch: java.io.IOException -> L3d java.lang.Throwable -> Lc6
            if (r2 == 0) goto L62
            r2.a()
            goto L62
        L3d:
            r3 = move-exception
            goto L44
        L3f:
            r6 = move-exception
            goto Lc8
        L42:
            r3 = move-exception
            r2 = r1
        L44:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lc6
            r4.<init>()     // Catch: java.lang.Throwable -> Lc6
            java.lang.String r5 = "check id failure :"
            r4.append(r5)     // Catch: java.lang.Throwable -> Lc6
            java.lang.String r3 = r3.getMessage()     // Catch: java.lang.Throwable -> Lc6
            r4.append(r3)     // Catch: java.lang.Throwable -> Lc6
            java.lang.String r3 = r4.toString()     // Catch: java.lang.Throwable -> Lc6
            com.xiaomi.channel.commonutils.logger.b.m117a(r3)     // Catch: java.lang.Throwable -> Lc6
            if (r2 == 0) goto L61
            r2.a()
        L61:
            r3 = r1
        L62:
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 != 0) goto Lbe
            com.xiaomi.push.j.f25335e = r0
            boolean r2 = android.text.TextUtils.isEmpty(r3)
            if (r2 != 0) goto L86
            int r2 = r3.length()
            r4 = 128(0x80, float:1.8E-43)
            if (r2 <= r4) goto L79
            goto L86
        L79:
            boolean r6 = android.text.TextUtils.equals(r0, r3)
            if (r6 != 0) goto L9e
            java.lang.String r6 = "vid changed, need sync"
            com.xiaomi.channel.commonutils.logger.b.m117a(r6)
            return r3
        L86:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "recover vid :"
            r2.append(r4)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.xiaomi.channel.commonutils.logger.b.m117a(r2)
            a(r6, r0)
        L9e:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r0 = "vdevid = "
            r6.append(r0)
            java.lang.String r0 = com.xiaomi.push.j.f25335e
            r6.append(r0)
            java.lang.String r0 = " "
            r6.append(r0)
            r6.append(r3)
            java.lang.String r6 = r6.toString()
            com.xiaomi.channel.commonutils.logger.b.c(r6)
            return r1
        Lbe:
            java.lang.String r6 = "empty local vid"
            com.xiaomi.channel.commonutils.logger.b.m117a(r6)
            java.lang.String r6 = "F*"
            return r6
        Lc6:
            r6 = move-exception
            r1 = r2
        Lc8:
            if (r1 == 0) goto Lcd
            r1.a()
        Lcd:
            throw r6
        Lce:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.j.d(android.content.Context):java.lang.String");
    }

    public static String e(Context context) {
        try {
            return Settings.Secure.getString(context.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
        } catch (Throwable th) {
            com.xiaomi.channel.commonutils.logger.b.a(th);
            return null;
        }
    }

    public static String f(Context context) {
        String strG = g(context);
        int i2 = 10;
        while (strG == null) {
            int i3 = i2 - 1;
            if (i2 <= 0) {
                break;
            }
            try {
                Thread.sleep(500L);
            } catch (InterruptedException unused) {
            }
            strG = g(context);
            i2 = i3;
        }
        return strG;
    }

    public static String g(Context context) {
        Object objA;
        Object objA2;
        Object objA3;
        if (n.d()) {
            return "";
        }
        String str = f25331a;
        if (str != null) {
            return str;
        }
        try {
            String deviceId = (!n.m679a() || (objA2 = at.a("miui.telephony.TelephonyManager", "getDefault", new Object[0])) == null || (objA3 = at.a(objA2, "getMiuiDeviceId", new Object[0])) == null || !(objA3 instanceof String)) ? null : (String) String.class.cast(objA3);
            if (deviceId == null && m589b(context)) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE);
                if (Build.VERSION.SDK_INT < 26) {
                    deviceId = telephonyManager.getDeviceId();
                } else {
                    if (1 == telephonyManager.getPhoneType()) {
                        objA = at.a(telephonyManager, "getImei", (Object[]) null);
                    } else if (2 == telephonyManager.getPhoneType()) {
                        objA = at.a(telephonyManager, "getMeid", (Object[]) null);
                    }
                    deviceId = (String) objA;
                }
            }
            if (!a(deviceId)) {
                return "";
            }
            f25331a = deviceId;
            return deviceId;
        } catch (Throwable th) {
            com.xiaomi.channel.commonutils.logger.b.a(th);
            return null;
        }
    }

    public static String h(Context context) throws InterruptedException {
        String strJ = j(context);
        int i2 = 10;
        while (strJ == null) {
            int i3 = i2 - 1;
            if (i2 <= 0) {
                break;
            }
            try {
                Thread.sleep(500L);
            } catch (InterruptedException unused) {
            }
            strJ = j(context);
            i2 = i3;
        }
        return strJ;
    }

    public static String i(Context context) {
        Object objA;
        if (n.d()) {
            return "";
        }
        if (!TextUtils.isEmpty(f25332b)) {
            return f25332b;
        }
        if (!m589b(context)) {
            return "";
        }
        g(context);
        if (TextUtils.isEmpty(f25331a)) {
            return "";
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE);
            Integer num = (Integer) at.a(telephonyManager, "getPhoneCount", new Object[0]);
            if (num == null || num.intValue() <= 1) {
                return "";
            }
            String str = null;
            for (int i2 = 0; i2 < num.intValue(); i2++) {
                if (Build.VERSION.SDK_INT < 26) {
                    objA = at.a(telephonyManager, "getDeviceId", Integer.valueOf(i2));
                } else if (1 == telephonyManager.getPhoneType()) {
                    objA = at.a(telephonyManager, "getImei", Integer.valueOf(i2));
                } else {
                    if (2 == telephonyManager.getPhoneType()) {
                        objA = at.a(telephonyManager, "getMeid", Integer.valueOf(i2));
                    }
                    if (TextUtils.isEmpty(str) && !TextUtils.equals(f25331a, str) && a(str)) {
                        f25332b += str + ",";
                    }
                }
                str = (String) objA;
                if (TextUtils.isEmpty(str)) {
                }
            }
            int length = f25332b.length();
            if (length > 0) {
                f25332b = f25332b.substring(0, length - 1);
            }
            return f25332b;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.d(e2.toString());
            return "";
        }
    }

    public static String j(Context context) {
        i(context);
        String str = "";
        if (TextUtils.isEmpty(f25332b)) {
            return "";
        }
        for (String str2 : f25332b.split(",")) {
            if (a(str2)) {
                str = str + ay.a(str2) + ",";
            }
        }
        int length = str.length();
        return length > 0 ? str.substring(0, length - 1) : str;
    }

    public static synchronized String k(Context context) {
        String str = f25334d;
        if (str != null) {
            return str;
        }
        String strB = ay.b(e(context) + m586a());
        f25334d = strB;
        return strB;
    }

    public static String l(Context context) {
        return ((TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE)).getSimOperatorName();
    }

    public static String m(Context context) {
        if (n.d()) {
            return "";
        }
        try {
            String macAddress = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
            return b(macAddress) ? ay.c(macAddress) : "";
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            return null;
        }
    }

    private static String n(Context context) throws InterruptedException {
        String strG = g(context);
        int i2 = 10;
        while (TextUtils.isEmpty(strG)) {
            int i3 = i2 - 1;
            if (i2 <= 0) {
                break;
            }
            try {
                Thread.sleep(500L);
            } catch (InterruptedException unused) {
            }
            strG = g(context);
            i2 = i3;
        }
        return strG;
    }
}
