package com.meizu.cloud.pushsdk.c.f;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import com.aliyun.vod.log.core.AliyunLogCommon;
import java.util.Map;
import java.util.UUID;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class e {

    /* renamed from: a, reason: collision with root package name */
    private static final String f9437a = "e";

    public static long a(String str) {
        long j2;
        long j3 = 0;
        int i2 = 0;
        while (i2 < str.length()) {
            char cCharAt = str.charAt(i2);
            if (cCharAt <= 127) {
                j2 = 1;
            } else if (cCharAt <= 2047) {
                j2 = 2;
            } else {
                if (cCharAt >= 55296 && cCharAt <= 57343) {
                    j3 += 4;
                    i2++;
                } else if (cCharAt < 65535) {
                    j2 = 3;
                } else {
                    j3 += 4;
                }
                i2++;
            }
            j3 += j2;
            i2++;
        }
        return j3;
    }

    private static Object a(Object obj) {
        return obj;
    }

    public static String a() {
        return Long.toString(System.currentTimeMillis());
    }

    public static JSONObject a(Map map) {
        return new JSONObject(map);
    }

    public static boolean a(long j2, long j3, long j4) {
        return j2 > j3 - j4;
    }

    public static boolean a(Context context) {
        try {
            String str = f9437a;
            c.c(str, "Checking tracker internet connectivity.", new Object[0]);
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            boolean z2 = activeNetworkInfo != null && activeNetworkInfo.isConnected();
            c.b(str, "Tracker connection online: %s", Boolean.valueOf(z2));
            return z2;
        } catch (Exception e2) {
            c.a(f9437a, "Security exception checking connection: %s", e2.toString());
            return true;
        }
    }

    public static String b() {
        return UUID.randomUUID().toString();
    }

    public static String b(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE);
            if (telephonyManager != null) {
                return telephonyManager.getNetworkOperatorName();
            }
            return null;
        } catch (Exception e2) {
            c.a(f9437a, "getCarrier: %s", e2.toString());
            return null;
        }
    }

    public static Location c(Context context) {
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService("location");
            if (locationManager == null) {
                c.a(f9437a, "Location Manager is null.", new Object[0]);
                return null;
            }
            Criteria criteria = new Criteria();
            criteria.setPowerRequirement(1);
            criteria.setAccuracy(2);
            String bestProvider = locationManager.getBestProvider(criteria, true);
            if (bestProvider == null) {
                c.a(f9437a, "Location Manager provider is null.", new Object[0]);
                return null;
            }
            Location lastKnownLocation = locationManager.getLastKnownLocation(bestProvider);
            c.b(f9437a, "Location found: %s", lastKnownLocation);
            return lastKnownLocation;
        } catch (Exception e2) {
            c.a(f9437a, "Failed to retrieve location: %s", e2.toString());
            return null;
        }
    }
}
