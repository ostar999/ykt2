package com.umeng.commonsdk.internal.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import com.umeng.commonsdk.internal.crash.UMCrashManager;
import com.umeng.commonsdk.statistics.common.ULog;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class j {

    /* renamed from: a, reason: collision with root package name */
    public static final String f23243a = "UM_PROBE_DATA";

    /* renamed from: b, reason: collision with root package name */
    public static final String f23244b = "_dsk_s";

    /* renamed from: c, reason: collision with root package name */
    public static final String f23245c = "_thm_z";

    /* renamed from: d, reason: collision with root package name */
    public static final String f23246d = "_gdf_r";

    /* renamed from: e, reason: collision with root package name */
    private static Object f23247e = new Object();

    public static void b(final Context context) {
        if (c(context)) {
            return;
        }
        final String[] strArr = {"unknown", "unknown", "unknown"};
        new Thread() { // from class: com.umeng.commonsdk.internal.utils.j.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                super.run();
                try {
                    strArr[0] = j.c();
                    strArr[1] = j.a();
                    strArr[2] = j.b();
                    ULog.i("diskType = " + strArr[0] + "; ThremalZone = " + strArr[1] + "; GoldFishRc = " + strArr[2]);
                    j.b(context, strArr);
                } catch (Throwable th) {
                    UMCrashManager.reportCrash(context, th);
                }
            }
        }.start();
    }

    public static boolean c(Context context) {
        SharedPreferences sharedPreferences;
        return (context == null || (sharedPreferences = context.getApplicationContext().getSharedPreferences(f23243a, 0)) == null || TextUtils.isEmpty(sharedPreferences.getString(f23244b, ""))) ? false : true;
    }

    public static String a(Context context) {
        try {
            SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(f23243a, 0);
            if (sharedPreferences == null) {
                return null;
            }
            JSONObject jSONObject = new JSONObject();
            synchronized (f23247e) {
                jSONObject.put(f23244b, sharedPreferences.getString(f23244b, ""));
                jSONObject.put(f23245c, sharedPreferences.getString(f23245c, ""));
                jSONObject.put(f23246d, sharedPreferences.getString(f23246d, ""));
            }
            return jSONObject.toString();
        } catch (Exception e2) {
            UMCrashManager.reportCrash(context, e2);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(Context context, String[] strArr) {
        SharedPreferences sharedPreferences;
        if (context == null || (sharedPreferences = context.getApplicationContext().getSharedPreferences(f23243a, 0)) == null) {
            return;
        }
        synchronized (f23247e) {
            sharedPreferences.edit().putString(f23244b, strArr[0]).putString(f23245c, strArr[1]).putString(f23246d, strArr[2]).commit();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x003b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String c() {
        /*
            java.lang.String r0 = "mtd"
            java.lang.String r1 = "sda"
            java.lang.String r2 = "mmcblk"
            r3 = 0
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L36
            java.io.FileReader r5 = new java.io.FileReader     // Catch: java.lang.Throwable -> L36
            java.lang.String r6 = "/proc/diskstats"
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L36
            r4.<init>(r5)     // Catch: java.lang.Throwable -> L36
        L14:
            java.lang.String r3 = r4.readLine()     // Catch: java.lang.Throwable -> L35
            if (r3 == 0) goto L31
            boolean r5 = r3.contains(r2)     // Catch: java.lang.Throwable -> L35
            if (r5 == 0) goto L22
            r0 = r2
            goto L39
        L22:
            boolean r5 = r3.contains(r1)     // Catch: java.lang.Throwable -> L35
            if (r5 == 0) goto L2a
            r0 = r1
            goto L39
        L2a:
            boolean r3 = r3.contains(r0)     // Catch: java.lang.Throwable -> L35
            if (r3 == 0) goto L14
            goto L39
        L31:
            java.lang.String r0 = "unknown"
            goto L39
        L35:
            r3 = r4
        L36:
            java.lang.String r0 = "noper"
            r4 = r3
        L39:
            if (r4 == 0) goto L3e
            r4.close()     // Catch: java.lang.Throwable -> L3e
        L3e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.internal.utils.j.c():java.lang.String");
    }

    public static String b() {
        int iA;
        try {
            iA = a("ls /", "goldfish");
        } catch (Throwable unused) {
            iA = -1;
        }
        return iA > 0 ? "goldfish" : iA < 0 ? "noper" : "unknown";
    }

    public static int a(String str, String str2) throws IOException {
        int i2;
        if (Build.VERSION.SDK_INT > 28) {
            return -1;
        }
        Process processExec = Runtime.getRuntime().exec(str);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(processExec.getInputStream()));
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                i2 = -1;
                break;
            }
            if (line.contains(str2)) {
                i2 = 1;
                break;
            }
        }
        try {
            if (processExec.waitFor() != 0) {
                return -1;
            }
            return i2;
        } catch (InterruptedException unused) {
            return -1;
        }
    }

    public static String a() {
        int iA;
        try {
            iA = a("ls /sys/class/thermal", "thermal_zone");
        } catch (Throwable unused) {
            iA = -1;
        }
        return iA > 0 ? "thermal_zone" : iA < 0 ? "noper" : "unknown";
    }
}
