package com.mobile.auth.y;

import android.content.Context;
import android.content.SharedPreferences;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/* loaded from: classes4.dex */
public class f {

    /* renamed from: a, reason: collision with root package name */
    private static final String f10598a = "f";

    /* renamed from: b, reason: collision with root package name */
    private static Boolean f10599b = Boolean.TRUE;

    private static String a() {
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
        } catch (SocketException unused) {
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
        return null;
    }

    public static String a(Context context, String str, String str2) {
        try {
            String strB = b(context, str, str2);
            if (w.a(strB).booleanValue()) {
                return h.a(context, strB);
            }
        } catch (Exception unused) {
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
        return null;
    }

    public static void a(Context context) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("cuAuthCacheName", 0);
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            for (String str : sharedPreferences.getAll().keySet()) {
                if (str.startsWith("accessCode")) {
                    editorEdit.remove(str);
                }
            }
            editorEdit.commit();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static boolean a(Context context, String str, String str2, String str3) {
        try {
            if (w.a(str3).booleanValue()) {
                String strB = b(context, str, str2);
                if (w.a(strB).booleanValue()) {
                    return h.a(context, strB, str3);
                }
            }
            return false;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    private static String b(Context context, String str, String str2) {
        try {
            String strA = v.a(context);
            String strA2 = a();
            if (!w.a(strA2).booleanValue()) {
                return null;
            }
            return "accessCode" + strA + strA2 + str + str2;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }
}
