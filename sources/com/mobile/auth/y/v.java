package com.mobile.auth.y;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.HashSet;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public final class v {

    /* renamed from: a, reason: collision with root package name */
    private static final char[] f10652a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /* renamed from: b, reason: collision with root package name */
    private static int f10653b = 1;

    /* renamed from: c, reason: collision with root package name */
    private static int f10654c = 0;

    public static String a() {
        try {
            return u.d();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static String a(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE)).getSimOperator();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static String a(Context context, String str, String str2) {
        try {
            try {
                return a(a(context, str), str2.toLowerCase());
            } catch (Exception unused) {
                t.b();
                return "";
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static String a(String str) {
        try {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA256");
                messageDigest.update(str.getBytes());
                byte[] bArrDigest = messageDigest.digest();
                StringBuffer stringBuffer = new StringBuffer("");
                for (int i2 = 0; i2 < bArrDigest.length; i2++) {
                    int i3 = bArrDigest[i2];
                    if (i3 < 0) {
                        i3 += 256;
                    }
                    if (i3 < 16) {
                        stringBuffer.append("0");
                    }
                    stringBuffer.append(Integer.toHexString(i3));
                }
                return stringBuffer.toString();
            } catch (NoSuchAlgorithmException unused) {
                t.b();
                return "";
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static String a(String str, String str2, String str3) {
        try {
            return i.b(str, str2, str3);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    private static String a(byte[] bArr, String str) {
        try {
            try {
                byte[] bArrDigest = MessageDigest.getInstance(str).digest(bArr);
                String str2 = "";
                for (int i2 = 0; i2 < bArrDigest.length; i2++) {
                    if (i2 != 0) {
                        str2 = str2 + ":";
                    }
                    String hexString = Integer.toHexString(bArrDigest[i2] & 255);
                    if (hexString.length() == 1) {
                        str2 = str2 + "0";
                    }
                    str2 = str2 + hexString;
                }
                return str2;
            } catch (Exception unused) {
                t.b();
                return "";
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    private static boolean a(ConnectivityManager connectivityManager) {
        try {
            try {
                Method declaredMethod = ConnectivityManager.class.getDeclaredMethod("getMobileDataEnabled", new Class[0]);
                declaredMethod.setAccessible(true);
                return ((Boolean) declaredMethod.invoke(connectivityManager, new Object[0])).booleanValue();
            } catch (Exception unused) {
                t.b();
                return true;
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    private static byte[] a(Context context, String str) {
        try {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 64);
                if (packageInfo.packageName.equals(str)) {
                    return packageInfo.signatures[0].toByteArray();
                }
                return null;
            } catch (Exception unused) {
                t.b();
                return null;
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static int b(Context context) {
        try {
            int iD = d(context);
            u.a(iD);
            return iD;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1;
        }
    }

    public static int b(String str) {
        try {
            try {
                byte[] address = InetAddress.getByName(str).getAddress();
                return (address[0] & 255) | ((address[3] & 255) << 24) | ((address[2] & 255) << 16) | ((address[1] & 255) << 8);
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return -1;
            }
        } catch (UnknownHostException unused) {
            return -1;
        }
    }

    public static String b(String str, String str2, String str3) {
        try {
            return i.a(str, str2, str3);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static void b() {
        try {
            t.a();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static int c() {
        try {
            f10654c = 0;
            f10653b = 0;
            return 0;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1;
        }
    }

    public static String c(Context context) {
        try {
            return (String) context.getPackageManager().getApplicationLabel(context.getPackageManager().getApplicationInfo(context.getPackageName(), 0));
        } catch (Exception unused) {
            return "";
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static void c(String str) {
        try {
            t.b(str);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static int d() {
        try {
            return f10653b;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1;
        }
    }

    private static int d(Context context) {
        ConnectivityManager connectivityManager;
        NetworkInfo activeNetworkInfo;
        try {
            try {
                connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
                t.c("android Build.VERSION:" + Build.VERSION.SDK_INT);
                activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            } catch (Exception unused) {
                t.b();
            }
            if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
                if (activeNetworkInfo.getType() == 1) {
                    if (a(connectivityManager)) {
                        t.c("Data and WIFI");
                        return 1;
                    }
                    t.c("Only WIFI");
                    return 2;
                }
                if (activeNetworkInfo.getType() == 0) {
                    t.c("Only Data");
                    String extraInfo = activeNetworkInfo.getExtraInfo();
                    if (TextUtils.isEmpty(extraInfo)) {
                        return 0;
                    }
                    u.d(extraInfo);
                    u.a(u.e(extraInfo));
                    return 0;
                }
                return -1;
            }
            return -1;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1;
        }
    }

    public static int e() {
        try {
            return f10654c;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1;
        }
    }

    public static int f() {
        try {
            int i2 = f10654c;
            if (i2 < 0 || i2 > f10653b) {
                return f10653b;
            }
            int i3 = i2 + 1;
            f10654c = i3;
            return i3;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1;
        }
    }

    public static String g() {
        int i2;
        try {
            if (u.f10643d != null) {
                return c.a();
            }
            JSONObject jSONObject = new JSONObject();
            try {
                HashSet hashSet = new HashSet();
                HashSet hashSet2 = new HashSet();
                StringBuilder sb = new StringBuilder();
                StringBuilder sb2 = new StringBuilder();
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                while (true) {
                    if (!networkInterfaces.hasMoreElements()) {
                        break;
                    }
                    NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                    if (!networkInterfaceNextElement.isVirtual() && networkInterfaceNextElement.isUp()) {
                        Enumeration<InetAddress> inetAddresses = networkInterfaceNextElement.getInetAddresses();
                        while (inetAddresses.hasMoreElements()) {
                            InetAddress inetAddressNextElement = inetAddresses.nextElement();
                            if (!inetAddressNextElement.isLoopbackAddress() && !inetAddressNextElement.isLinkLocalAddress() && !inetAddressNextElement.isMulticastAddress() && !inetAddressNextElement.isAnyLocalAddress()) {
                                if (inetAddressNextElement instanceof Inet4Address) {
                                    hashSet.add(inetAddressNextElement.getHostAddress());
                                }
                                if (inetAddressNextElement instanceof Inet6Address) {
                                    String hostAddress = inetAddressNextElement.getHostAddress();
                                    if (hostAddress.contains("%")) {
                                        hostAddress = hostAddress.substring(0, hostAddress.indexOf("%"));
                                    }
                                    hashSet2.add(hostAddress);
                                }
                            }
                        }
                    }
                }
                if (hashSet.size() > 0) {
                    Object[] array = hashSet.toArray();
                    int iMin = Math.min(array.length, 5);
                    for (int i3 = 0; i3 < iMin; i3++) {
                        sb.append((String) array[i3]);
                        if (i3 < iMin - 1) {
                            sb.append("-");
                        }
                    }
                    "&private_ip=".concat(String.valueOf(sb));
                    jSONObject.put("privateIp", sb.toString());
                }
                if (hashSet2.size() > 0) {
                    Object[] array2 = hashSet2.toArray();
                    int iMin2 = Math.min(array2.length, 5);
                    for (i2 = 0; i2 < iMin2; i2++) {
                        sb2.append((String) array2[i2]);
                        if (i2 < iMin2 - 1) {
                            sb2.append("-");
                        }
                    }
                    "&private_ip_v6=".concat(String.valueOf(sb2));
                    jSONObject.put("privateIp_v6", sb2.toString());
                }
                if (sb.length() != 0) {
                    return jSONObject.toString();
                }
            } catch (Exception unused) {
            }
            return "{\"privateIp\":\"0.0.0.0\"}";
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }
}
