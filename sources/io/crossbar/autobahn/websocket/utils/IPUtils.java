package io.crossbar.autobahn.websocket.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/* loaded from: classes8.dex */
public class IPUtils {
    public static Inet4Address a(String str, String str2) throws NumberFormatException {
        byte[] bArrA = a(str);
        if (bArrA == null) {
            return null;
        }
        try {
            return (Inet4Address) InetAddress.getByAddress(str2, bArrA);
        } catch (UnknownHostException e2) {
            throw new AssertionError(e2);
        }
    }

    public static byte[] a(String str) throws NumberFormatException {
        if (str == null) {
            return null;
        }
        String[] strArrSplit = str.split("\\.");
        if (strArrSplit.length != 4) {
            return null;
        }
        byte[] bArr = new byte[4];
        for (int i2 = 0; i2 < 4; i2++) {
            int i3 = Integer.parseInt(strArrSplit[i2]);
            if (i3 > 255) {
                return null;
            }
            bArr[i2] = (byte) i3;
        }
        return bArr;
    }
}
