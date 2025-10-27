package com.xiaomi.push;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Process;
import android.text.TextUtils;
import android.util.Base64;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class g {

    public enum a {
        UNKNOWN(0),
        ALLOWED(1),
        NOT_ALLOWED(2);


        /* renamed from: a, reason: collision with other field name */
        private final int f466a;

        a(int i2) {
            this.f466a = i2;
        }

        public int a() {
            return this.f466a;
        }
    }

    public static int a(Context context, String str) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(str, 16384);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            packageInfo = null;
        }
        if (packageInfo != null) {
            return packageInfo.versionCode;
        }
        return 0;
    }

    @TargetApi(19)
    /* renamed from: a, reason: collision with other method in class */
    public static a m438a(Context context, String str) {
        if (context == null || TextUtils.isEmpty(str)) {
            return a.UNKNOWN;
        }
        try {
            Integer num = (Integer) at.a((Class<? extends Object>) AppOpsManager.class, "OP_POST_NOTIFICATION");
            if (num == null) {
                return a.UNKNOWN;
            }
            Integer num2 = (Integer) at.a((AppOpsManager) context.getSystemService("appops"), "checkOpNoThrow", num, Integer.valueOf(context.getPackageManager().getApplicationInfo(str, 0).uid), str);
            return (num2 == null || num2.intValue() != 0) ? a.NOT_ALLOWED : a.ALLOWED;
        } catch (Throwable unused) {
            return a.UNKNOWN;
        }
    }

    public static String a(Context context) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        if (context == null || (runningAppProcesses = ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses()) == null) {
            return null;
        }
        int iMyPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.pid == iMyPid) {
                return runningAppProcessInfo.processName;
            }
        }
        return null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static String m439a(Context context, String str) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(str, 16384);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            packageInfo = null;
        }
        return packageInfo != null ? packageInfo.versionName : "1.0";
    }

    public static String a(String[] strArr) {
        boolean z2;
        h[] hVarArrValues = h.values();
        byte[] bArr = new byte[(int) Math.ceil(hVarArrValues.length / 8.0d)];
        if (strArr == null) {
            com.xiaomi.channel.commonutils.logger.b.c("AppInfoUtils.: no permissions");
            return "";
        }
        int i2 = -1;
        for (String str : strArr) {
            if (!TextUtils.isEmpty(str) && str.startsWith("android.permission.")) {
                int i3 = 0;
                while (true) {
                    if (i3 >= hVarArrValues.length) {
                        z2 = false;
                        break;
                    }
                    if (TextUtils.equals("android.permission." + hVarArrValues[i3].name(), str)) {
                        i2 = i3;
                        z2 = true;
                        break;
                    }
                    i3++;
                }
                if (z2 && i2 != -1) {
                    int i4 = i2 / 8;
                    bArr[i4] = (byte) (bArr[i4] | (1 << (7 - (i2 % 8))));
                }
            }
        }
        return new String(Base64.encode(bArr, 0));
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m440a(Context context) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses();
        if (runningAppProcesses != null && runningAppProcesses.size() >= 1) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (runningAppProcessInfo.pid == Process.myPid() && runningAppProcessInfo.processName.equals(context.getPackageName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m441a(Context context, String str) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return false;
        }
        Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses.iterator();
        while (it.hasNext()) {
            if (Arrays.asList(it.next().pkgList).contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static String b(Context context) {
        String str;
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses();
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        if (runningAppProcesses != null && runningAppProcesses.size() > 0) {
            Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses.iterator();
            while (it.hasNext()) {
                String[] strArr = it.next().pkgList;
                for (int i2 = 0; strArr != null && i2 < strArr.length; i2++) {
                    if (!arrayList.contains(strArr[i2])) {
                        arrayList.add(strArr[i2]);
                        if (arrayList.size() == 1) {
                            str = (String) arrayList.get(0);
                        } else {
                            sb.append(DictionaryFactory.SHARP);
                            str = strArr[i2];
                        }
                        sb.append(str.hashCode() % 100000);
                    }
                }
            }
        }
        return sb.toString();
    }

    public static String b(Context context, String str) {
        try {
            return a(context.getPackageManager().getPackageInfo(str, 4096).requestedPermissions);
        } catch (PackageManager.NameNotFoundException e2) {
            com.xiaomi.channel.commonutils.logger.b.d(e2.toString());
            return "";
        }
    }

    /* renamed from: b, reason: collision with other method in class */
    public static boolean m442b(Context context, String str) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(str, 0);
        } catch (PackageManager.NameNotFoundException unused) {
            packageInfo = null;
        }
        return packageInfo != null;
    }

    public static String c(Context context, String str) throws PackageManager.NameNotFoundException {
        ApplicationInfo applicationInfo;
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(str, 0);
            return (packageInfo == null || (applicationInfo = packageInfo.applicationInfo) == null) ? str : packageManager.getApplicationLabel(applicationInfo).toString();
        } catch (PackageManager.NameNotFoundException e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            return str;
        }
    }

    /* renamed from: c, reason: collision with other method in class */
    public static boolean m443c(Context context, String str) {
        return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
    }
}
