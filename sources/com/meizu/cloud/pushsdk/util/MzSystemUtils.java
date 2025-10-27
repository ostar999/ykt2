package com.meizu.cloud.pushsdk.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Process;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.base.k;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
public class MzSystemUtils {
    private static final String TAG = "MzSystemUtils";

    public static boolean compareVersion(String str, String str2) {
        String[] strArrSplit = str.split("\\.");
        String[] strArrSplit2 = str2.split("\\.");
        int iMin = Math.min(strArrSplit.length, strArrSplit2.length);
        int length = 0;
        for (int i2 = 0; i2 < iMin; i2++) {
            length = strArrSplit[i2].length() - strArrSplit2[i2].length();
            if (length != 0 || (length = strArrSplit[i2].compareTo(strArrSplit2[i2])) != 0) {
                break;
            }
        }
        if (length == 0) {
            length = strArrSplit.length - strArrSplit2.length;
        }
        return length >= 0;
    }

    public static boolean compatApi(int i2) {
        return Build.VERSION.SDK_INT >= i2;
    }

    public static String findReceiver(Context context, String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            Intent intent = new Intent(str);
            intent.setPackage(str2);
            List<ResolveInfo> listQueryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(intent, 0);
            if (listQueryBroadcastReceivers != null && listQueryBroadcastReceivers.size() > 0) {
                return listQueryBroadcastReceivers.get(0).activityInfo.name;
            }
        }
        return null;
    }

    public static int getAppVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException unused) {
            return 0;
        }
    }

    public static String getAppVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException unused) {
            return "";
        }
    }

    public static String getAppVersionName(Context context, String str) {
        try {
            String str2 = context.getPackageManager().getPackageInfo(str, 0).versionName;
            if (str2 != null) {
                if (str2.length() > 0) {
                    return str2;
                }
            }
            return "";
        } catch (Exception e2) {
            DebugLogger.e("VersionInfo", "Exception message " + e2.getMessage());
            return "";
        }
    }

    public static String getBSSID(Context context) {
        WifiInfo connectionInfo;
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            if (wifiManager == null || (connectionInfo = wifiManager.getConnectionInfo()) == null) {
                return null;
            }
            return connectionInfo.getBSSID();
        } catch (Exception e2) {
            DebugLogger.e(TAG, "getOperator error " + e2.getMessage());
            return null;
        }
    }

    public static String getCurrentLanguage() {
        try {
            return Locale.getDefault().getLanguage();
        } catch (Exception e2) {
            DebugLogger.e(TAG, "getCurrentLanguage error " + e2.getMessage());
            return null;
        }
    }

    public static String getDeviceId(Context context) {
        try {
            return com.meizu.cloud.pushsdk.base.c.a(context);
        } catch (Exception e2) {
            DebugLogger.e(TAG, "getDeviceId error " + e2.getMessage());
            return null;
        }
    }

    public static List<String> getInstalledPackage(Context context) {
        List<PackageInfo> installedPackages;
        ArrayList arrayList = new ArrayList();
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null && (installedPackages = packageManager.getInstalledPackages(0)) != null) {
                Iterator<PackageInfo> it = installedPackages.iterator();
                while (it.hasNext()) {
                    arrayList.add(it.next().packageName);
                }
            }
        } catch (Exception e2) {
            DebugLogger.e(TAG, "getInstalledPackage error " + e2.getMessage());
        }
        return arrayList;
    }

    public static String getLineNumber(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE);
            if (telephonyManager != null) {
                return telephonyManager.getLine1Number();
            }
            return null;
        } catch (Exception e2) {
            DebugLogger.e(TAG, "getLineNumber error " + e2.getMessage());
            return null;
        }
    }

    public static String getMzPushServicePackageName(Context context) {
        String packageName = context.getPackageName();
        try {
            String servicesByPackageName = getServicesByPackageName(context, "com.meizu.cloud");
            if (!TextUtils.isEmpty(servicesByPackageName)) {
                if (servicesByPackageName.contains("mzservice_v1")) {
                    return "com.meizu.cloud";
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        DebugLogger.i("SystemUtils", "startservice package name " + packageName);
        return packageName;
    }

    public static String getNetWorkType(Context context) {
        String str;
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return "";
            }
            int type = activeNetworkInfo.getType();
            if (type == 0) {
                switch (activeNetworkInfo.getSubtype()) {
                    case 1:
                    case 2:
                    case 4:
                    case 7:
                    case 11:
                        str = "MOBILE_2G";
                        break;
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 9:
                    case 10:
                    case 12:
                    case 14:
                    case 15:
                        str = "MOBILE_3G";
                        break;
                    case 13:
                        str = "MOBILE_4G";
                        break;
                    default:
                        str = "MOBILE_XG";
                        break;
                }
            } else {
                if (type == 1) {
                    return "WIFI";
                }
                if (type == 7) {
                    return "BLUETOOTH";
                }
                if (type == 9) {
                    return "ETHERNET";
                }
            }
            return "OTHER";
        } catch (SecurityException e2) {
            DebugLogger.e(TAG, "Security exception checking connection: " + e2.getMessage());
            return "";
        }
    }

    public static String getOperator(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE);
            if (telephonyManager != null) {
                return telephonyManager.getSimOperator();
            }
            return null;
        } catch (Exception e2) {
            DebugLogger.e(TAG, "getOperator error " + e2.getMessage());
            return null;
        }
    }

    public static String getProcessName(Context context) {
        try {
            int iMyPid = Process.myPid();
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getApplicationContext().getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses()) {
                DebugLogger.i(TAG, "processName " + runningAppProcessInfo.processName);
                if (runningAppProcessInfo.pid == iMyPid) {
                    return runningAppProcessInfo.processName;
                }
            }
            return "";
        } catch (Exception e2) {
            DebugLogger.e(TAG, "getProcessName error " + e2.getMessage());
            return null;
        }
    }

    public static List<String> getRunningProcess(Context context) {
        ArrayList arrayList = new ArrayList();
        try {
            Iterator<ActivityManager.RunningAppProcessInfo> it = ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses().iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().processName);
            }
        } catch (Exception unused) {
            DebugLogger.e(TAG, "can not get running process info so set running true");
        }
        return arrayList;
    }

    private static String getServicesByPackageName(Context context, String str) {
        ServiceInfo[] serviceInfoArr;
        try {
            serviceInfoArr = context.getPackageManager().getPackageInfo(str, 4).services;
        } catch (PackageManager.NameNotFoundException unused) {
            serviceInfoArr = null;
        }
        if (serviceInfoArr == null) {
            return null;
        }
        for (int i2 = 0; i2 < serviceInfoArr.length; i2++) {
            if ("com.meizu.cloud.pushsdk.pushservice.MzPushService".equals(serviceInfoArr[i2].name)) {
                return serviceInfoArr[i2].processName;
            }
        }
        return null;
    }

    public static String getSn() {
        String strA = k.a("ro.serialno");
        return !TextUtils.isEmpty(strA) ? strA : Build.SERIAL;
    }

    public static String getSubscribeId(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE);
            if (telephonyManager != null) {
                return telephonyManager.getSubscriberId();
            }
            return null;
        } catch (Exception e2) {
            DebugLogger.e(TAG, "getSubscribeId error " + e2.getMessage());
            return null;
        }
    }

    public static String getWifiMac(Context context) {
        WifiInfo connectionInfo;
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            if (wifiManager == null || (connectionInfo = wifiManager.getConnectionInfo()) == null) {
                return null;
            }
            return connectionInfo.getMacAddress();
        } catch (Exception e2) {
            DebugLogger.e(TAG, "getWifiMac error " + e2.getMessage());
            return null;
        }
    }

    public static boolean isApplicationDebug(Context context) {
        try {
            return (context.getApplicationInfo().flags & 2) != 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isBrandMeizu(android.content.Context r2) {
        /*
            java.lang.String r0 = "ro.meizu.product.model"
            java.lang.String r0 = com.meizu.cloud.pushsdk.base.k.a(r0)
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto L21
            java.lang.String r0 = android.os.Build.BRAND
            java.lang.String r1 = "meizu"
            boolean r1 = r1.equalsIgnoreCase(r0)
            if (r1 != 0) goto L21
            java.lang.String r1 = "22c4185e"
            boolean r0 = r1.equalsIgnoreCase(r0)
            if (r0 == 0) goto L1f
            goto L21
        L1f:
            r0 = 0
            goto L22
        L21:
            r0 = 1
        L22:
            if (r0 != 0) goto L2b
            android.content.Context r2 = r2.getApplicationContext()
            com.meizu.cloud.pushsdk.a.a.b(r2)
        L2b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.pushsdk.util.MzSystemUtils.isBrandMeizu(android.content.Context):boolean");
    }

    public static boolean isHuaWei() {
        DebugLogger.e(TAG, "huawei eui " + k.a("ro.build.version.emui"));
        return !TextUtils.isEmpty(r0);
    }

    public static boolean isIndiaLocal() {
        return "india".equals(k.a("ro.meizu.locale.region"));
    }

    public static boolean isInternational() {
        if (com.meizu.cloud.pushsdk.base.a.a().f9230a) {
            return com.meizu.cloud.pushsdk.base.a.a().f9231b.booleanValue();
        }
        return false;
    }

    public static boolean isMeizu(Context context) {
        return isBrandMeizu(context);
    }

    public static boolean isPackageInstalled(Context context, String str) throws PackageManager.NameNotFoundException {
        try {
            context.getPackageManager().getPackageInfo(str, 0);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static boolean isRunningProcess(Context context, String str) {
        try {
            Iterator<ActivityManager.RunningAppProcessInfo> it = ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses().iterator();
            boolean zContains = false;
            while (it.hasNext() && !(zContains = it.next().processName.contains(str))) {
            }
            DebugLogger.i(TAG, str + " is running " + zContains);
            return zContains;
        } catch (Exception unused) {
            DebugLogger.e(TAG, "can not get running process info so set running true");
            return true;
        }
    }

    public static boolean isXiaoMi() {
        return "Xiaomi".equals(Build.MODEL) || "Xiaomi".equals(Build.MANUFACTURER);
    }

    public static void sendMessageFromBroadcast(Context context, Intent intent, String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            intent.setAction(str);
        }
        if (!TextUtils.isEmpty(str2)) {
            intent.setPackage(str2);
        }
        String strFindReceiver = findReceiver(context, str, str2);
        if (!TextUtils.isEmpty(strFindReceiver)) {
            intent.setClassName(str2, strFindReceiver);
        }
        context.sendBroadcast(intent);
    }
}
