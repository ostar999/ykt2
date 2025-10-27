package com.nirvana.tools.core;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.aliyun.vod.log.core.AliyunLogCommon;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/* loaded from: classes4.dex */
public class NetworkUtils {
    public static String getLocalIPAddress() throws SocketException {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddressNextElement = inetAddresses.nextElement();
                    if (!inetAddressNextElement.isLoopbackAddress() && (inetAddressNextElement instanceof Inet4Address)) {
                        return inetAddressNextElement.getHostAddress();
                    }
                }
            }
            return "";
        } catch (Exception e2) {
            Log.e("NetworkUtils", e2.getMessage());
            return "";
        }
    }

    public static boolean hasSimCard(Context context) {
        return ((TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE)).getSimState() == 5;
    }

    public static boolean isAirModeEnable(Context context) {
        try {
            return Settings.Global.getInt(context.getContentResolver(), "airplane_mode_on", 0) == 1;
        } catch (Throwable th) {
            Log.e("NetworkUtils", th.getMessage());
            return false;
        }
    }

    public static boolean isMobileNetConnected(Context context) {
        NetworkInfo networkInfo;
        return context != null && (networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getNetworkInfo(0)) != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    public static boolean isMobileNetworkOpen(Context context) throws NoSuchMethodException, SecurityException {
        if (!hasSimCard(context) || isAirModeEnable(context)) {
            return false;
        }
        if (isMobileNetConnected(context)) {
            return true;
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
            Method declaredMethod = ConnectivityManager.class.getDeclaredMethod("getMobileDataEnabled", new Class[0]);
            declaredMethod.setAccessible(true);
            return ((Boolean) declaredMethod.invoke(connectivityManager, new Object[0])).booleanValue();
        } catch (Exception e2) {
            Log.e("NetworkUtils", e2.getMessage());
            return true;
        }
    }
}
