package com.beizi.fusion.g;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.caverock.androidsvg.SVGParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.util.Enumeration;

/* loaded from: classes2.dex */
public class as {

    /* renamed from: a, reason: collision with root package name */
    private static String f5181a = "SystemUtil";

    public static boolean a(Context context, String str) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo;
        if (context == null || str == null || str.isEmpty()) {
            return false;
        }
        try {
            packageInfo = context.getPackageManager().getPackageInfo(str, 0);
        } catch (PackageManager.NameNotFoundException unused) {
            packageInfo = null;
        }
        return packageInfo != null;
    }

    public static long b(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).lastUpdateTime;
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return 0L;
        }
    }

    public static String c(Context context) {
        return context.getPackageName();
    }

    public static String d(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return "";
        } catch (Exception e3) {
            e3.printStackTrace();
            return "";
        }
    }

    public static int e(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException unused) {
            return 0;
        }
    }

    public static String f(Context context) {
        try {
            return (String) context.getPackageManager().getApplicationLabel(context.getApplicationInfo());
        } catch (Exception unused) {
            return "芒果TV";
        }
    }

    public static boolean g(Context context) {
        try {
            return (context.getApplicationInfo().flags & 2) != 0;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static boolean h(Context context) {
        try {
            return Settings.Secure.getInt(context.getContentResolver(), "adb_enabled", 0) > 0;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static boolean i(Context context) {
        boolean z2;
        boolean z3;
        boolean z4;
        try {
            Intent intentRegisterReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            int intExtra = intentRegisterReceiver.getIntExtra("status", -1);
            z2 = intExtra == 2 || intExtra == 5;
            int intExtra2 = intentRegisterReceiver.getIntExtra("plugged", -1);
            z3 = intExtra2 == 2;
            z4 = intExtra2 == 1;
        } catch (Exception e2) {
            e2.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
        if (z2) {
            return z3 || z4;
        }
        return false;
    }

    public static boolean j(Context context) {
        try {
            String property = System.getProperty("http.proxyHost");
            String property2 = System.getProperty("http.proxyPort");
            if (property2 == null) {
                property2 = "-1";
            }
            return (TextUtils.isEmpty(property) || Integer.parseInt(property2) == -1) ? false : true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    public static float k(Context context) {
        float f2 = context.getResources().getDisplayMetrics().density;
        float f3 = context.getResources().getDisplayMetrics().widthPixels;
        if (f2 <= 0.0f) {
            f2 = 1.0f;
        }
        return (f3 / f2) + 0.5f;
    }

    public static float l(Context context) {
        return b(context, context.getResources().getDisplayMetrics().heightPixels);
    }

    public static float m(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static float n(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int o(Context context) {
        return context.getApplicationContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static int p(Context context) {
        return context.getApplicationContext().getResources().getDisplayMetrics().heightPixels;
    }

    public static Long q(Context context) {
        if (context == null) {
            return 0L;
        }
        try {
            return Long.valueOf(context.getPackageManager().getPackageInfo(context.getPackageName(), 0).lastUpdateTime);
        } catch (PackageManager.NameNotFoundException unused) {
            return 0L;
        }
    }

    private static boolean c(String str) {
        Process processExec = null;
        try {
            try {
                processExec = Runtime.getRuntime().exec("ls -l " + str);
                String line = new BufferedReader(new InputStreamReader(processExec.getInputStream())).readLine();
                if (line != null && line.length() >= 4) {
                    char cCharAt = line.charAt(3);
                    if (cCharAt == 's' || cCharAt == 'x') {
                        processExec.destroy();
                        return true;
                    }
                }
            } catch (IOException e2) {
                e2.printStackTrace();
                if (processExec == null) {
                    return false;
                }
            }
            processExec.destroy();
            return false;
        } catch (Throwable th) {
            if (processExec != null) {
                processExec.destroy();
            }
            throw th;
        }
    }

    public static long a(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).firstInstallTime;
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return 0L;
        }
    }

    public static boolean b() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                if ("tun0".equals(networkInterfaceNextElement.getName()) || "ppp0".equals(networkInterfaceNextElement.getName())) {
                    return true;
                }
            }
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    public static String a() {
        return (new File("/system/bin/su").exists() && c("/system/bin/su")) ? "yes" : (new File("/system/xbin/su").exists() && c("/system/xbin/su")) ? "yes" : SVGParser.XML_STYLESHEET_ATTR_ALTERNATE_NO;
    }

    public static int a(Context context, float f2) {
        return (int) ((f2 * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static boolean a(String str) throws ClassNotFoundException {
        try {
            Class.forName(str);
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    public static void a(View view) {
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(view);
            }
        }
    }

    public static int b(Context context, float f2) {
        return (int) ((f2 / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static void b(String str) {
        new IllegalArgumentException(str).printStackTrace();
    }
}
