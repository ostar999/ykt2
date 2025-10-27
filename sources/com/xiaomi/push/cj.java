package com.xiaomi.push;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import java.net.InetAddress;
import java.net.UnknownHostException;

/* loaded from: classes6.dex */
class cj {
    public static DhcpInfo a(Context context) {
        WifiManager wifiManager;
        if (context == null || (wifiManager = (WifiManager) context.getSystemService("wifi")) == null || !wifiManager.isWifiEnabled()) {
            return null;
        }
        try {
            if (context.getPackageManager().checkPermission("android.permission.ACCESS_WIFI_STATE", context.getPackageName()) == 0) {
                return wifiManager.getDhcpInfo();
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static String m287a(Context context) {
        try {
            DhcpInfo dhcpInfoA = a(context);
            if (dhcpInfoA == null) {
                return null;
            }
            return a(dhcpInfoA.gateway).getHostAddress();
        } catch (Exception unused) {
            return null;
        }
    }

    public static String a(Context context, int i2) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            if (wifiManager != null && wifiManager.isWifiEnabled()) {
                WifiInfo connectionInfo = context.getPackageManager().checkPermission("android.permission.ACCESS_WIFI_STATE", context.getPackageName()) == 0 ? wifiManager.getConnectionInfo() : null;
                if (connectionInfo == null) {
                    return null;
                }
                if (i2 == 0) {
                    return connectionInfo.getMacAddress();
                }
                if (i2 == 1) {
                    return connectionInfo.getBSSID();
                }
                if (i2 == 2) {
                    return a(connectionInfo.getSSID());
                }
            }
        } catch (Exception unused) {
        }
        return null;
    }

    public static String a(Context context, String str, String str2) {
        return context.getSharedPreferences("devicediscover", 0).getString(str, str2);
    }

    private static String a(String str) {
        return (str.startsWith("\"") && str.endsWith("\"")) ? str.substring(1, str.length() - 1) : str;
    }

    public static InetAddress a(int i2) {
        try {
            return InetAddress.getByAddress(new byte[]{(byte) (i2 & 255), (byte) ((i2 >> 8) & 255), (byte) ((i2 >> 16) & 255), (byte) ((i2 >> 24) & 255)});
        } catch (UnknownHostException unused) {
            throw new AssertionError();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static void m288a(Context context, String str, String str2) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("devicediscover", 0).edit();
        editorEdit.putString(str, str2);
        editorEdit.commit();
    }

    public static String b(Context context) {
        try {
            DhcpInfo dhcpInfoA = a(context);
            if (dhcpInfoA == null) {
                return null;
            }
            return a(dhcpInfoA.serverAddress).getHostAddress();
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0040, code lost:
    
        if (r2 == null) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0042, code lost:
    
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0046, code lost:
    
        if (r2 == null) goto L26;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String c(android.content.Context r6) throws java.lang.Throwable {
        /*
            r0 = 0
            java.lang.String r6 = a(r6, r0)
            if (r6 == 0) goto L15
            boolean r1 = r6.isEmpty()
            if (r1 != 0) goto L15
            java.lang.String r1 = "02:00:00:00:00:00"
            boolean r1 = r1.equals(r6)
            if (r1 == 0) goto L49
        L15:
            r1 = 1024(0x400, float:1.435E-42)
            r2 = 0
            char[] r3 = new char[r1]     // Catch: java.lang.Throwable -> L39 java.lang.Exception -> L40 java.io.FileNotFoundException -> L46
            java.io.FileReader r4 = new java.io.FileReader     // Catch: java.lang.Throwable -> L39 java.lang.Exception -> L40 java.io.FileNotFoundException -> L46
            java.lang.String r5 = "/sys/class/net/wlan0/address"
            r4.<init>(r5)     // Catch: java.lang.Throwable -> L39 java.lang.Exception -> L40 java.io.FileNotFoundException -> L46
            int r1 = r4.read(r3, r0, r1)     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L35 java.io.FileNotFoundException -> L37
            java.lang.String r2 = new java.lang.String     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L35 java.io.FileNotFoundException -> L37
            r2.<init>(r3, r0, r1)     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L35 java.io.FileNotFoundException -> L37
            java.lang.String r6 = r2.trim()     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L35 java.io.FileNotFoundException -> L37
            r4.close()     // Catch: java.lang.Exception -> L49
            goto L49
        L32:
            r6 = move-exception
            r2 = r4
            goto L3a
        L35:
            r2 = r4
            goto L40
        L37:
            r2 = r4
            goto L46
        L39:
            r6 = move-exception
        L3a:
            if (r2 == 0) goto L3f
            r2.close()     // Catch: java.lang.Exception -> L3f
        L3f:
            throw r6
        L40:
            if (r2 == 0) goto L49
        L42:
            r2.close()     // Catch: java.lang.Exception -> L49
            goto L49
        L46:
            if (r2 == 0) goto L49
            goto L42
        L49:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.cj.c(android.content.Context):java.lang.String");
    }
}
