package com.plv.foundationsdk.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import com.aliyun.vod.log.core.AliyunLogCommon;

/* loaded from: classes4.dex */
public class PLVNetworkUtils {
    public static final int NETWORK_2G = 2;
    public static final int NETWORK_3G = 3;
    public static final int NETWORK_4G = 4;
    public static final int NETWORK_NO = -1;
    private static final int NETWORK_TYPE_GSM = 16;
    private static final int NETWORK_TYPE_IWLAN = 18;
    private static final int NETWORK_TYPE_TD_SCDMA = 17;
    public static final int NETWORK_UNKNOWN = 5;
    public static final int NETWORK_WIFI = 1;

    private static NetworkInfo getActiveNetworkInfo(Context context) {
        return ((ConnectivityManager) context.getApplicationContext().getSystemService("connectivity")).getActiveNetworkInfo();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int getNetWorkType(Context context) {
        int i2;
        NetworkInfo activeNetworkInfo = getActiveNetworkInfo(context);
        if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
            return -1;
        }
        if (activeNetworkInfo.getType() == 1) {
            return 1;
        }
        if (activeNetworkInfo.getType() != 0) {
            return 5;
        }
        switch (activeNetworkInfo.getSubtype()) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
            case 16:
                i2 = 2;
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
            case 17:
                break;
            case 13:
            case 18:
                i2 = 4;
                break;
            default:
                String subtypeName = activeNetworkInfo.getSubtypeName();
                if (!subtypeName.equalsIgnoreCase("TD-SCDMA") && !subtypeName.equalsIgnoreCase("WCDMA") && !subtypeName.equalsIgnoreCase("CDMA2000")) {
                }
                break;
        }
        return 5;
    }

    public static String getNetWorkTypeName(Context context) {
        int netWorkType = getNetWorkType(context);
        return netWorkType != -1 ? netWorkType != 1 ? netWorkType != 2 ? netWorkType != 3 ? netWorkType != 4 ? "NETWORK_UNKNOWN" : "NETWORK_4G" : "NETWORK_3G" : "NETWORK_2G" : "NETWORK_WIFI" : "NETWORK_NO";
    }

    public static String getNetworkOperatorName(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE);
        if (telephonyManager != null) {
            return telephonyManager.getNetworkOperatorName();
        }
        return null;
    }

    public static int getPhoneType(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE);
        if (telephonyManager != null) {
            return telephonyManager.getPhoneType();
        }
        return -1;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0035  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getWIFISSID(android.content.Context r4) {
        /*
            java.lang.String r0 = "wifi"
            java.lang.Object r4 = r4.getSystemService(r0)
            android.net.wifi.WifiManager r4 = (android.net.wifi.WifiManager) r4
            java.lang.String r0 = ""
            if (r4 == 0) goto L35
            android.net.wifi.WifiInfo r1 = r4.getConnectionInfo()
            if (r1 == 0) goto L35
            int r1 = r1.getNetworkId()
            java.util.List r4 = r4.getConfiguredNetworks()
            if (r4 == 0) goto L35
            java.util.Iterator r4 = r4.iterator()
        L20:
            boolean r2 = r4.hasNext()
            if (r2 == 0) goto L35
            java.lang.Object r2 = r4.next()
            android.net.wifi.WifiConfiguration r2 = (android.net.wifi.WifiConfiguration) r2
            if (r2 == 0) goto L20
            int r3 = r2.networkId
            if (r3 != r1) goto L20
            java.lang.String r4 = r2.SSID
            goto L36
        L35:
            r4 = r0
        L36:
            if (r4 == 0) goto L44
            java.lang.String r1 = "\""
            boolean r2 = r4.contains(r1)
            if (r2 == 0) goto L44
            java.lang.String r4 = r4.replace(r1, r0)
        L44:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.plv.foundationsdk.utils.PLVNetworkUtils.getWIFISSID(android.content.Context):java.lang.String");
    }

    public static boolean is4G(Context context) {
        NetworkInfo activeNetworkInfo = getActiveNetworkInfo(context);
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.getSubtype() == 13;
    }

    public static boolean isAvailable(Context context) {
        NetworkInfo activeNetworkInfo = getActiveNetworkInfo(context);
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable();
    }

    public static boolean isConnected(Context context) {
        NetworkInfo activeNetworkInfo = getActiveNetworkInfo(context);
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        return connectivityManager != null && connectivityManager.getActiveNetworkInfo().getType() == 1;
    }

    public static void openWirelessSettings(Context context) {
        context.startActivity(new Intent("android.settings.SETTINGS"));
    }
}
