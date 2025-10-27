package com.hyphenate.push.a;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import com.hyphenate.easeui.utils.RomUtils;
import com.hyphenate.push.EMPushConfig;
import com.hyphenate.push.EMPushType;
import com.hyphenate.util.EMLog;
import com.meizu.cloud.pushsdk.constants.PushConstants;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f8869a = "PushUtil";

    /* renamed from: com.hyphenate.push.a.a$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f8870a;

        static {
            int[] iArr = new int[EMPushType.values().length];
            f8870a = iArr;
            try {
                iArr[EMPushType.MIPUSH.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f8870a[EMPushType.OPPOPUSH.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f8870a[EMPushType.VIVOPUSH.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f8870a[EMPushType.MEIZUPUSH.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f8870a[EMPushType.HMSPUSH.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0061 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0064  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String a() throws java.lang.Throwable {
        /*
            java.lang.String r0 = ""
            java.lang.String r1 = "ro.miui.ui.version.name"
            r2 = 0
            java.lang.Runtime r3 = java.lang.Runtime.getRuntime()     // Catch: java.lang.Throwable -> L3e java.io.IOException -> L40
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3e java.io.IOException -> L40
            r4.<init>()     // Catch: java.lang.Throwable -> L3e java.io.IOException -> L40
            java.lang.String r5 = "getprop "
            r4.append(r5)     // Catch: java.lang.Throwable -> L3e java.io.IOException -> L40
            r4.append(r1)     // Catch: java.lang.Throwable -> L3e java.io.IOException -> L40
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> L3e java.io.IOException -> L40
            java.lang.Process r3 = r3.exec(r4)     // Catch: java.lang.Throwable -> L3e java.io.IOException -> L40
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L3e java.io.IOException -> L40
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L3e java.io.IOException -> L40
            java.io.InputStream r3 = r3.getInputStream()     // Catch: java.lang.Throwable -> L3e java.io.IOException -> L40
            r5.<init>(r3)     // Catch: java.lang.Throwable -> L3e java.io.IOException -> L40
            r3 = 1024(0x400, float:1.435E-42)
            r4.<init>(r5, r3)     // Catch: java.lang.Throwable -> L3e java.io.IOException -> L40
            java.lang.String r0 = r4.readLine()     // Catch: java.lang.Throwable -> L39 java.io.IOException -> L3c
            r4.close()     // Catch: java.lang.Throwable -> L39 java.io.IOException -> L3c
            r4.close()     // Catch: java.io.IOException -> L5b
            goto L5b
        L39:
            r0 = move-exception
            r2 = r4
            goto L73
        L3c:
            r2 = r4
            goto L40
        L3e:
            r0 = move-exception
            goto L73
        L40:
            java.lang.String r3 = "DeviceUtils"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3e
            r4.<init>()     // Catch: java.lang.Throwable -> L3e
            java.lang.String r5 = "Unable to read sysprop "
            r4.append(r5)     // Catch: java.lang.Throwable -> L3e
            r4.append(r1)     // Catch: java.lang.Throwable -> L3e
            java.lang.String r1 = r4.toString()     // Catch: java.lang.Throwable -> L3e
            android.util.Log.e(r3, r1)     // Catch: java.lang.Throwable -> L3e
            if (r2 == 0) goto L5b
            r2.close()     // Catch: java.io.IOException -> L5b
        L5b:
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L64
            java.lang.String r0 = "XIAOMI"
            return r0
        L64:
            java.lang.String r0 = android.os.Build.MANUFACTURER
            java.lang.String r1 = "-"
            java.lang.String r2 = "_"
            java.lang.String r0 = r0.replace(r1, r2)
            java.lang.String r0 = r0.toUpperCase()
            return r0
        L73:
            if (r2 == 0) goto L78
            r2.close()     // Catch: java.io.IOException -> L78
        L78:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hyphenate.push.a.a.a():java.lang.String");
    }

    public static boolean a(Context context) {
        int iMyPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses()) {
            if (runningAppProcessInfo.pid == iMyPid) {
                return context.getApplicationInfo().packageName.equals(runningAppProcessInfo.processName);
            }
        }
        return false;
    }

    public static boolean a(EMPushType eMPushType, EMPushConfig eMPushConfig) throws Throwable {
        String str;
        String strA = a();
        EMLog.e(f8869a, "Current device manufacturer: " + strA);
        int i2 = AnonymousClass1.f8870a[eMPushType.ordinal()];
        if (i2 == 1) {
            str = "XIAOMI";
        } else if (i2 == 2) {
            str = RomUtils.ROM_OPPO;
        } else if (i2 == 3) {
            str = RomUtils.ROM_VIVO;
        } else {
            if (i2 != 4) {
                if (i2 != 5) {
                    return false;
                }
                return strA.contains("HUAWEI") || strA.contains("HONOR") || strA.contains("PTAC");
            }
            str = "MEIZU";
        }
        return strA.contains(str);
    }
}
