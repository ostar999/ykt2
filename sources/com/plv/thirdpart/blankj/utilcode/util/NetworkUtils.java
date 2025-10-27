package com.plv.thirdpart.blankj.utilcode.util;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.plv.thirdpart.blankj.utilcode.util.ShellUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/* loaded from: classes5.dex */
public final class NetworkUtils {
    private static final int NETWORK_TYPE_GSM = 16;
    private static final int NETWORK_TYPE_IWLAN = 18;
    private static final int NETWORK_TYPE_TD_SCDMA = 17;

    public enum NetworkType {
        NETWORK_WIFI,
        NETWORK_4G,
        NETWORK_3G,
        NETWORK_2G,
        NETWORK_UNKNOWN,
        NETWORK_NO
    }

    private NetworkUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static NetworkInfo getActiveNetworkInfo() {
        return ((ConnectivityManager) Utils.getApp().getSystemService("connectivity")).getActiveNetworkInfo();
    }

    public static boolean getDataEnabled() throws NoSuchMethodException, SecurityException {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) Utils.getApp().getSystemService(AliyunLogCommon.TERMINAL_TYPE);
            Method declaredMethod = telephonyManager.getClass().getDeclaredMethod("getDataEnabled", new Class[0]);
            if (declaredMethod != null) {
                return ((Boolean) declaredMethod.invoke(telephonyManager, new Object[0])).booleanValue();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return false;
    }

    public static String getDomainAddress(String str) {
        try {
            return InetAddress.getByName(str).getHostAddress();
        } catch (UnknownHostException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String getIPAddress(boolean z2) throws SocketException {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                if (networkInterfaceNextElement.isUp()) {
                    Enumeration<InetAddress> inetAddresses = networkInterfaceNextElement.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress inetAddressNextElement = inetAddresses.nextElement();
                        if (!inetAddressNextElement.isLoopbackAddress()) {
                            String hostAddress = inetAddressNextElement.getHostAddress();
                            boolean z3 = hostAddress.indexOf(58) < 0;
                            if (z2) {
                                if (z3) {
                                    return hostAddress;
                                }
                            } else if (!z3) {
                                int iIndexOf = hostAddress.indexOf(37);
                                return iIndexOf < 0 ? hostAddress.toUpperCase() : hostAddress.substring(0, iIndexOf).toUpperCase();
                            }
                        }
                    }
                }
            }
            return null;
        } catch (SocketException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String getNetworkOperatorName() {
        TelephonyManager telephonyManager = (TelephonyManager) Utils.getApp().getSystemService(AliyunLogCommon.TERMINAL_TYPE);
        if (telephonyManager != null) {
            return telephonyManager.getNetworkOperatorName();
        }
        return null;
    }

    public static NetworkType getNetworkType() {
        NetworkType networkType = NetworkType.NETWORK_NO;
        NetworkInfo activeNetworkInfo = getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
            return networkType;
        }
        if (activeNetworkInfo.getType() == 1) {
            return NetworkType.NETWORK_WIFI;
        }
        if (activeNetworkInfo.getType() != 0) {
            return NetworkType.NETWORK_UNKNOWN;
        }
        switch (activeNetworkInfo.getSubtype()) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
            case 16:
                return NetworkType.NETWORK_2G;
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
                return NetworkType.NETWORK_3G;
            case 13:
            case 18:
                return NetworkType.NETWORK_4G;
            default:
                String subtypeName = activeNetworkInfo.getSubtypeName();
                return (subtypeName.equalsIgnoreCase("TD-SCDMA") || subtypeName.equalsIgnoreCase("WCDMA") || subtypeName.equalsIgnoreCase("CDMA2000")) ? NetworkType.NETWORK_3G : NetworkType.NETWORK_UNKNOWN;
        }
    }

    public static boolean getWifiEnabled() {
        return ((WifiManager) Utils.getApp().getSystemService("wifi")).isWifiEnabled();
    }

    public static boolean is4G() {
        NetworkInfo activeNetworkInfo = getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.getSubtype() == 13;
    }

    public static boolean isAvailableByPing() {
        return isAvailableByPing(null);
    }

    public static boolean isConnected() {
        NetworkInfo activeNetworkInfo = getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean isWifiAvailable() {
        return getWifiEnabled() && isAvailableByPing();
    }

    public static boolean isWifiConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) Utils.getApp().getSystemService("connectivity");
        return (connectivityManager == null || connectivityManager.getActiveNetworkInfo() == null || connectivityManager.getActiveNetworkInfo().getType() != 1) ? false : true;
    }

    public static void openWirelessSettings() {
        Utils.getApp().startActivity(new Intent("android.settings.WIRELESS_SETTINGS").setFlags(268435456));
    }

    public static void setDataEnabled(boolean z2) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) Utils.getApp().getSystemService(AliyunLogCommon.TERMINAL_TYPE);
            Method declaredMethod = telephonyManager.getClass().getDeclaredMethod("setDataEnabled", Boolean.TYPE);
            if (declaredMethod != null) {
                declaredMethod.invoke(telephonyManager, Boolean.valueOf(z2));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void setWifiEnabled(boolean z2) {
        WifiManager wifiManager = (WifiManager) Utils.getApp().getSystemService("wifi");
        if (z2) {
            if (wifiManager.isWifiEnabled()) {
                return;
            }
            wifiManager.setWifiEnabled(true);
        } else if (wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);
        }
    }

    public static boolean isAvailableByPing(String str) {
        if (str == null || str.length() <= 0) {
            str = "223.5.5.5";
        }
        ShellUtils.CommandResult commandResultExecCmd = ShellUtils.execCmd(String.format("ping -c 1 %s", str), false);
        boolean z2 = commandResultExecCmd.result == 0;
        if (commandResultExecCmd.errorMsg != null) {
            Log.d("NetworkUtils", "isAvailableByPing() called" + commandResultExecCmd.errorMsg);
        }
        if (commandResultExecCmd.successMsg != null) {
            Log.d("NetworkUtils", "isAvailableByPing() called" + commandResultExecCmd.successMsg);
        }
        return z2;
    }
}
