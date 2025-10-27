package com.hyphenate.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import com.aliyun.vod.log.core.AliyunLogCommon;

@SuppressLint({"MissingPermission"})
/* loaded from: classes4.dex */
public class NetUtils {
    private static final int HIGH_SPEED_DOWNLOAD_BUF_SIZE = 30720;
    private static final int HIGH_SPEED_UPLOAD_BUF_SIZE = 10240;
    private static final int LOW_SPEED_DOWNLOAD_BUF_SIZE = 2024;
    private static final int LOW_SPEED_UPLOAD_BUF_SIZE = 1024;
    private static final int MAX_SPEED_DOWNLOAD_BUF_SIZE = 102400;
    private static final int MAX_SPEED_UPLOAD_BUF_SIZE = 102400;
    private static final String TAG = "net";

    public enum Types {
        NONE,
        WIFI,
        MOBILE,
        ETHERNET,
        OTHERS
    }

    public static int getDownloadBufSize(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.getType() == 1) {
            return 102400;
        }
        if (activeNetworkInfo != null && activeNetworkInfo.getType() == 9) {
            return 102400;
        }
        if (activeNetworkInfo == null || !isConnectionFast(activeNetworkInfo.getType(), activeNetworkInfo.getSubtype())) {
            return 2024;
        }
        return HIGH_SPEED_DOWNLOAD_BUF_SIZE;
    }

    public static String getNetworkType(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
            return "no network";
        }
        int type = activeNetworkInfo.getType();
        if (type == 9) {
            return "ETHERNET";
        }
        if (type == 1) {
            return "WIFI";
        }
        switch (((TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE)).getNetworkType()) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return "2G";
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return "3G";
            case 13:
                return "4G";
            default:
                return "unkonw network";
        }
    }

    public static Types getNetworkTypes(Context context) {
        Types types = Types.NONE;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (networkCapabilities != null) {
                boolean zHasTransport = networkCapabilities.hasTransport(0);
                boolean zHasTransport2 = networkCapabilities.hasTransport(1);
                boolean zHasTransport3 = networkCapabilities.hasTransport(2);
                boolean zHasTransport4 = networkCapabilities.hasTransport(3);
                boolean zHasTransport5 = networkCapabilities.hasTransport(4);
                if (zHasTransport) {
                    types = Types.MOBILE;
                } else if (zHasTransport2) {
                    types = Types.WIFI;
                } else if (zHasTransport4) {
                    types = Types.ETHERNET;
                } else if (zHasTransport3 || zHasTransport5) {
                    types = Types.OTHERS;
                }
            }
            EMLog.i("net", "Network info: " + types);
        } catch (Exception e2) {
            EMLog.e("net", e2.getMessage());
        }
        return types;
    }

    public static int getUploadBufSize(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.getType() == 1) {
            return 102400;
        }
        if (activeNetworkInfo == null || activeNetworkInfo.getType() != 9) {
            return (activeNetworkInfo == null || !isConnectionFast(activeNetworkInfo.getType(), activeNetworkInfo.getSubtype())) ? 1024 : 10240;
        }
        return 102400;
    }

    @TargetApi(13)
    public static boolean hasDataConnection(Context context) {
        return getNetworkTypes(context) != Types.NONE;
    }

    public static boolean hasNetwork(Context context) {
        if (context != null) {
            try {
                NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                if (activeNetworkInfo != null) {
                    return activeNetworkInfo.isAvailable();
                }
            } catch (Exception e2) {
                EMLog.e("net", e2.getMessage());
            }
        }
        return false;
    }

    private static boolean isConnectionFast(int i2, int i3) {
        if (i2 == 1 || i2 == 9) {
            return true;
        }
        if (i2 != 0) {
            return false;
        }
        switch (i3) {
            case 1:
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
            case 6:
                break;
            case 7:
                break;
            case 8:
            case 9:
            case 10:
                break;
            default:
                if (i3 == 14 || i3 == 13 || i3 == 12) {
                }
                break;
        }
        return true;
    }

    public static boolean isEthernetConnected(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (networkCapabilities != null) {
                return networkCapabilities.hasTransport(3);
            }
            return false;
        } catch (Exception e2) {
            EMLog.e("net", e2.getMessage());
            return false;
        }
    }

    public static boolean isMobileConnected(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (networkCapabilities != null) {
                return networkCapabilities.hasTransport(0);
            }
            return false;
        } catch (Exception e2) {
            EMLog.e("net", e2.getMessage());
            return false;
        }
    }

    public static boolean isOthersConnected(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (networkCapabilities == null) {
                return false;
            }
            return networkCapabilities.hasTransport(2) || networkCapabilities.hasTransport(4);
        } catch (Exception e2) {
            EMLog.e("net", e2.getMessage());
            return false;
        }
    }

    public static boolean isWifiConnected(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (networkCapabilities == null) {
                return false;
            }
            return networkCapabilities.hasTransport(1);
        } catch (Exception e2) {
            EMLog.e("net", e2.getMessage());
            return false;
        }
    }
}
