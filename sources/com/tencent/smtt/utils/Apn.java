package com.tencent.smtt.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;

/* loaded from: classes6.dex */
public class Apn {
    public static final int APN_2G = 1;
    public static final int APN_3G = 2;
    public static final int APN_4G = 4;
    public static final int APN_CELLULAR = 5;
    public static final int APN_ETHERNET = 6;
    public static final int APN_UNKNOWN = 0;
    public static final int APN_WIFI = 3;

    private static int a(NetworkInfo networkInfo) {
        if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
            return 0;
        }
        int type = networkInfo.getType();
        if (type != 0) {
            return type != 1 ? 0 : 3;
        }
        switch (networkInfo.getSubtype()) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return 1;
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return 2;
            case 13:
                return 4;
            default:
                return 0;
        }
    }

    public static String getApnInfo(Context context) {
        String extraInfo = "unknown";
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isConnectedOrConnecting()) {
                return "unknown";
            }
            int type = activeNetworkInfo.getType();
            if (type != 0) {
                return type != 1 ? "unknown" : "wifi";
            }
            try {
                Object obj = QbSdk.getSettings().get(TbsCoreSettings.ID_NWINFO_GETEXTRAINFO);
                if (obj == null || !TbsCoreSettings.ID_OPERATE_PROHIBIT.equals(String.valueOf(obj))) {
                    TbsLog.i("Apn", "getApnInfo networkInfo.getExtraInfo() allow");
                    extraInfo = activeNetworkInfo.getExtraInfo();
                } else {
                    TbsLog.i("Apn", "getApnInfo networkInfo.getExtraInfo() prohibit");
                }
                return extraInfo;
            } catch (Throwable th) {
                TbsLog.i("Apn", "stack is " + Log.getStackTraceString(th));
                return extraInfo;
            }
        } catch (Exception unused) {
            return extraInfo;
        }
    }

    public static int getApnType(Context context) {
        NetworkCapabilities networkCapabilities;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (Build.VERSION.SDK_INT <= 28) {
            return a(connectivityManager.getActiveNetworkInfo());
        }
        Network activeNetwork = connectivityManager.getActiveNetwork();
        if (activeNetwork == null || (networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)) == null) {
            return 0;
        }
        if (networkCapabilities.hasTransport(1)) {
            return 3;
        }
        if (networkCapabilities.hasTransport(0)) {
            return 5;
        }
        return networkCapabilities.hasTransport(3) ? 6 : 0;
    }

    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return false;
        }
        return activeNetworkInfo.isConnected() || activeNetworkInfo.isAvailable();
    }
}
