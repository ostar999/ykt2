package com.alipay.sdk.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes2.dex */
public final class a {

    /* renamed from: b, reason: collision with root package name */
    private static final String f3340b = "00:00:00:00:00:00";

    /* renamed from: e, reason: collision with root package name */
    private static a f3341e;

    /* renamed from: a, reason: collision with root package name */
    public String f3342a;

    /* renamed from: c, reason: collision with root package name */
    private String f3343c;

    /* renamed from: d, reason: collision with root package name */
    private String f3344d;

    private a(Context context) {
        try {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getApplicationContext().getSystemService(AliyunLogCommon.TERMINAL_TYPE);
                b(telephonyManager.getDeviceId());
                String subscriberId = telephonyManager.getSubscriberId();
                if (subscriberId != null) {
                    subscriberId = (subscriberId + "000000000000000").substring(0, 15);
                }
                this.f3343c = subscriberId;
                String macAddress = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo().getMacAddress();
                this.f3342a = macAddress;
                if (TextUtils.isEmpty(macAddress)) {
                    this.f3342a = f3340b;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                if (TextUtils.isEmpty(this.f3342a)) {
                    this.f3342a = f3340b;
                }
            }
        } catch (Throwable th) {
            if (TextUtils.isEmpty(this.f3342a)) {
                this.f3342a = f3340b;
            }
            throw th;
        }
    }

    public static a a(Context context) {
        if (f3341e == null) {
            f3341e = new a(context);
        }
        return f3341e;
    }

    private String c() {
        String str = b() + HiAnalyticsConstant.REPORT_VAL_SEPARATOR;
        String strA = a();
        if (TextUtils.isEmpty(strA)) {
            return str + "000000000000000";
        }
        return str + strA;
    }

    private String d() {
        return this.f3342a;
    }

    public final String b() {
        if (TextUtils.isEmpty(this.f3344d)) {
            this.f3344d = "000000000000000";
        }
        return this.f3344d;
    }

    public static String d(Context context) {
        if (context == null) {
            return "";
        }
        try {
            return context.getResources().getConfiguration().locale.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    private void b(String str) {
        if (str != null) {
            byte[] bytes = str.getBytes();
            for (int i2 = 0; i2 < bytes.length; i2++) {
                byte b3 = bytes[i2];
                if (b3 < 48 || b3 > 57) {
                    bytes[i2] = TarConstants.LF_NORMAL;
                }
            }
            str = (new String(bytes) + "000000000000000").substring(0, 15);
        }
        this.f3344d = str;
    }

    public final String a() {
        if (TextUtils.isEmpty(this.f3343c)) {
            this.f3343c = "000000000000000";
        }
        return this.f3343c;
    }

    private void a(String str) {
        if (str != null) {
            str = (str + "000000000000000").substring(0, 15);
        }
        this.f3343c = str;
    }

    public static String c(Context context) {
        String str;
        a aVarA = a(context);
        String str2 = aVarA.b() + HiAnalyticsConstant.REPORT_VAL_SEPARATOR;
        String strA = aVarA.a();
        if (TextUtils.isEmpty(strA)) {
            str = str2 + "000000000000000";
        } else {
            str = str2 + strA;
        }
        return str.substring(0, 8);
    }

    public static d b(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getApplicationContext().getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.getType() == 0) {
                return d.a(activeNetworkInfo.getSubtype());
            }
            if (activeNetworkInfo != null && activeNetworkInfo.getType() == 1) {
                return d.WIFI;
            }
            return d.NONE;
        } catch (Exception unused) {
            return d.NONE;
        }
    }
}
