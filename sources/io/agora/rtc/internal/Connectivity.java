package io.agora.rtc.internal;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import java.lang.reflect.Method;
import java.util.ArrayList;

/* loaded from: classes8.dex */
class Connectivity {
    public static final int Network_2G = 3;
    public static final int Network_3G = 4;
    public static final int Network_4G = 5;
    public static final int Network_5G = 6;
    public static final int Network_DISCONNECTED = 0;
    public static final int Network_LAN = 1;
    public static final int Network_SubType_WIFI_2P4G = 100;
    public static final int Network_SubType_WIFI_5G = 101;
    public static final int Network_UNKNOWN = -1;
    public static final int Network_WIFI = 2;

    public static ArrayList<String> getDnsList() throws NoSuchMethodException, SecurityException {
        try {
            Method method = Class.forName("android.os.SystemProperties").getMethod("get", String.class);
            ArrayList<String> arrayList = new ArrayList<>();
            String[] strArr = {"net.dns1", "net.dns2", "net.dns3", "net.dns4"};
            for (int i2 = 0; i2 < 4; i2++) {
                String str = (String) method.invoke(null, strArr[i2]);
                if (str != null && !"".equals(str) && !arrayList.contains(str)) {
                    arrayList.add(str);
                }
            }
            return arrayList;
        } catch (Exception unused) {
            return null;
        }
    }

    public static NetworkInfo getNetworkInfo(Context context) {
        if (context == null) {
            return null;
        }
        return ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
    }

    public static int getNetworkType(NetworkInfo info) {
        if (info == null || !info.isConnected()) {
            return 0;
        }
        int type = info.getType();
        if (type == 1) {
            return 2;
        }
        if (type != 0) {
            return -1;
        }
        if (Build.VERSION.SDK_INT >= 29 && info.getSubtype() == 20) {
            return 6;
        }
        switch (info.getSubtype()) {
        }
        return 0;
    }

    public static boolean isConnected(Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context);
        return networkInfo != null && networkInfo.isConnected();
    }

    public static boolean isConnectedFast(Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context);
        return networkInfo != null && networkInfo.isConnected() && isConnectionFast(networkInfo.getType(), networkInfo.getSubtype());
    }

    public static boolean isConnectedMobile(Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context);
        return networkInfo != null && networkInfo.isConnected() && networkInfo.getType() == 0;
    }

    public static boolean isConnectedWifi(Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context);
        return networkInfo != null && networkInfo.isConnected() && networkInfo.getType() == 1;
    }

    public static boolean isConnectionFast(int type, int subType) {
        if (type == 1) {
            return true;
        }
        if (type != 0) {
            return false;
        }
        switch (subType) {
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 13:
            case 14:
            case 15:
                return true;
            case 4:
            case 7:
            case 11:
            default:
                return false;
        }
    }

    public static int getNetworkType(Context context) {
        return getNetworkType(getNetworkInfo(context));
    }
}
