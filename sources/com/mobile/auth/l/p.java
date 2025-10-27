package com.mobile.auth.l;

import android.text.TextUtils;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/* loaded from: classes4.dex */
public class p {
    public static String a(boolean z2) throws SocketException {
        StringBuilder sb = new StringBuilder();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                if (!z2 || !networkInterfaceNextElement.getName().toLowerCase().contains("wlan")) {
                    Enumeration<InetAddress> inetAddresses = networkInterfaceNextElement.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress inetAddressNextElement = inetAddresses.nextElement();
                        if (!inetAddressNextElement.isLoopbackAddress() && (inetAddressNextElement instanceof Inet4Address)) {
                            sb.append(inetAddressNextElement.getHostAddress());
                            sb.append(",");
                        }
                    }
                }
            }
            if (!TextUtils.isEmpty(sb)) {
                sb = sb.delete(sb.length() - 1, sb.length());
            }
            c.b("UmcIPUtils", "onlyMobileDataIp " + z2 + " IPV4 ip：" + sb.toString());
            return sb.toString();
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static String b(boolean z2) throws SocketException {
        StringBuilder sb = new StringBuilder();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                if (!z2 || !networkInterfaceNextElement.getName().toLowerCase().contains("wlan")) {
                    Enumeration<InetAddress> inetAddresses = networkInterfaceNextElement.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress inetAddressNextElement = inetAddresses.nextElement();
                        if (!inetAddressNextElement.isLoopbackAddress() && (inetAddressNextElement instanceof Inet6Address) && !inetAddressNextElement.isLinkLocalAddress()) {
                            String hostAddress = inetAddressNextElement.getHostAddress();
                            if (!TextUtils.isEmpty(hostAddress)) {
                                sb.append(hostAddress);
                                sb.append(",");
                            }
                        }
                    }
                }
            }
            if (!TextUtils.isEmpty(sb)) {
                sb = sb.delete(sb.length() - 1, sb.length());
            }
            c.b("UmcIPUtils", "onlyMobileDataIp " + z2 + " IPV6 ip：" + sb.toString());
            return sb.toString();
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }
}
