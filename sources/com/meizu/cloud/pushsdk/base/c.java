package com.meizu.cloud.pushsdk.base;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private static String f9247a = "";

    /* renamed from: b, reason: collision with root package name */
    private static String f9248b = "";

    public static String a(Context context) {
        String strA;
        if (TextUtils.isEmpty(f9248b)) {
            if (a()) {
                strA = i.a(context);
            } else if (TextUtils.isEmpty(f9248b)) {
                StringBuilder sb = new StringBuilder();
                String str = Build.SERIAL;
                DebugLogger.i("DeviceUtils", "device serial " + str);
                if (!TextUtils.isEmpty(str)) {
                    sb.append(str);
                    String strB = b(context);
                    DebugLogger.e("DeviceUtils", "mac address " + strB);
                    if (!TextUtils.isEmpty(strB)) {
                        sb.append(strB.replace(":", "").toUpperCase());
                        strA = sb.toString();
                    }
                }
                return null;
            }
            f9248b = strA;
        }
        return f9248b;
    }

    private static String a(String str) throws IOException {
        String str2;
        try {
            FileInputStream fileInputStream = new FileInputStream("/sys/class/net/" + str + "/address");
            Scanner scanner = new Scanner(fileInputStream);
            strTrim = scanner.hasNextLine() ? scanner.nextLine().trim() : null;
            fileInputStream.close();
        } catch (FileNotFoundException unused) {
            str2 = "getMacAddressWithIfName File not found Exception";
            DebugLogger.e("DeviceUtils", str2);
            return strTrim;
        } catch (IOException unused2) {
            str2 = "getMacAddressWithIfName IOException";
            DebugLogger.e("DeviceUtils", str2);
            return strTrim;
        }
        return strTrim;
    }

    public static boolean a() {
        String strA = k.a("ro.target.product");
        if (TextUtils.isEmpty(strA)) {
            DebugLogger.i("DeviceUtils", "current product is phone");
            return true;
        }
        DebugLogger.i("DeviceUtils", "current product is " + strA);
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0043  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String b(android.content.Context r4) {
        /*
            java.lang.String r0 = com.meizu.cloud.pushsdk.base.c.f9247a
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto Lb
            java.lang.String r4 = com.meizu.cloud.pushsdk.base.c.f9247a
            return r4
        Lb:
            java.lang.String r0 = "connectivity"
            java.lang.Object r4 = r4.getSystemService(r0)     // Catch: java.lang.Exception -> L47
            android.net.ConnectivityManager r4 = (android.net.ConnectivityManager) r4     // Catch: java.lang.Exception -> L47
            if (r4 == 0) goto L43
            android.net.NetworkInfo r4 = r4.getActiveNetworkInfo()     // Catch: java.lang.Exception -> L47
            java.lang.String r0 = "eth0"
            java.lang.String r1 = "wlan0"
            if (r4 == 0) goto L38
            int r2 = r4.getType()     // Catch: java.lang.Exception -> L47
            r3 = 1
            if (r2 != r3) goto L2b
            java.lang.String r4 = a(r1)     // Catch: java.lang.Exception -> L47
            goto L44
        L2b:
            int r4 = r4.getType()     // Catch: java.lang.Exception -> L47
            r1 = 9
            if (r4 != r1) goto L43
        L33:
            java.lang.String r4 = a(r0)     // Catch: java.lang.Exception -> L47
            goto L44
        L38:
            java.lang.String r4 = a(r1)     // Catch: java.lang.Exception -> L47
            boolean r1 = android.text.TextUtils.isEmpty(r4)     // Catch: java.lang.Exception -> L47
            if (r1 == 0) goto L44
            goto L33
        L43:
            r4 = 0
        L44:
            com.meizu.cloud.pushsdk.base.c.f9247a = r4     // Catch: java.lang.Exception -> L47
            goto L4e
        L47:
            java.lang.String r4 = "DeviceUtils"
            java.lang.String r0 = "get address exception "
            com.meizu.cloud.pushinternal.DebugLogger.e(r4, r0)
        L4e:
            java.lang.String r4 = com.meizu.cloud.pushsdk.base.c.f9247a
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.pushsdk.base.c.b(android.content.Context):java.lang.String");
    }
}
