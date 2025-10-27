package com.mobile.auth.f;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.hjq.permissions.Permission;
import com.mobile.auth.l.c;
import com.mobile.auth.l.g;
import com.mobile.auth.l.m;
import com.umeng.analytics.pro.aq;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static a f9747a;

    /* renamed from: b, reason: collision with root package name */
    private static long f9748b;

    /* renamed from: c, reason: collision with root package name */
    private C0198a f9749c = null;

    /* renamed from: com.mobile.auth.f.a$a, reason: collision with other inner class name */
    public static class C0198a {

        /* renamed from: a, reason: collision with root package name */
        private int f9750a = -1;

        /* renamed from: b, reason: collision with root package name */
        private int f9751b = -1;

        public int a() {
            return this.f9751b;
        }
    }

    private a() {
    }

    public static a a() {
        if (f9747a == null) {
            f9747a = new a();
        }
        return f9747a;
    }

    private void a(Context context, boolean z2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        int i2 = Build.VERSION.SDK_INT;
        SubscriptionManager subscriptionManagerFrom = SubscriptionManager.from(context.getApplicationContext());
        if (subscriptionManagerFrom != null) {
            try {
                if (this.f9749c.f9750a == -1 && i2 >= 24) {
                    this.f9749c.f9751b = SubscriptionManager.getDefaultDataSubscriptionId();
                    c.b("UMCTelephonyManagement", "android 7.0及以上手机getDefaultDataSubscriptionId适配成功: dataSubId = " + this.f9749c.f9751b);
                    return;
                }
            } catch (Exception unused) {
                c.a("UMCTelephonyManagement", "android 7.0及以上手机getDefaultDataSubscriptionId适配失败");
            }
            try {
                Object objInvoke = subscriptionManagerFrom.getClass().getMethod("getDefaultDataSubId", new Class[0]).invoke(subscriptionManagerFrom, new Object[0]);
                if ((objInvoke instanceof Integer) || (objInvoke instanceof Long)) {
                    this.f9749c.f9751b = ((Integer) objInvoke).intValue();
                    c.b("UMCTelephonyManagement", "android 7.0以下手机getDefaultDataSubId适配成功: dataSubId = " + this.f9749c.f9751b);
                    return;
                }
            } catch (Exception unused2) {
                c.a("UMCTelephonyManagement", "readDefaultDataSubId-->getDefaultDataSubId 反射出错");
            }
            try {
                Object objInvoke2 = subscriptionManagerFrom.getClass().getMethod("getDefaultDataSubscriptionId", new Class[0]).invoke(subscriptionManagerFrom, new Object[0]);
                if ((objInvoke2 instanceof Integer) || (objInvoke2 instanceof Long)) {
                    this.f9749c.f9751b = ((Integer) objInvoke2).intValue();
                    c.b("UMCTelephonyManagement", "反射getDefaultDataSubscriptionId适配成功: dataSubId = " + this.f9749c.f9751b);
                }
            } catch (Exception unused3) {
                c.a("UMCTelephonyManagement", "getDefaultDataSubscriptionId-->getDefaultDataSubscriptionId 反射出错");
            }
        }
    }

    private void b(Context context) {
        c.b("UMCTelephonyManagement", "readSimInfoDbStart");
        Uri uri = Uri.parse("content://telephony/siminfo");
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = contentResolver.query(uri, new String[]{aq.f22519d, "sim_id"}, "sim_id>=?", new String[]{"0"}, null);
                if (cursorQuery != null) {
                    while (cursorQuery.moveToNext()) {
                        int i2 = cursorQuery.getInt(cursorQuery.getColumnIndex("sim_id"));
                        int i3 = cursorQuery.getInt(cursorQuery.getColumnIndex(aq.f22519d));
                        if (this.f9749c.f9750a == -1 && this.f9749c.f9751b != -1 && this.f9749c.f9751b == i3) {
                            this.f9749c.f9750a = i2;
                            c.b("UMCTelephonyManagement", "通过读取sim db获取数据流量卡的卡槽值：" + i2);
                        }
                        if (this.f9749c.f9750a == i2) {
                            this.f9749c.f9751b = i3;
                        }
                    }
                }
            } catch (Exception unused) {
                c.a("UMCTelephonyManagement", "readSimInfoDb error");
                if (cursorQuery != null) {
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            c.b("UMCTelephonyManagement", "readSimInfoDbEnd");
        } catch (Throwable th) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            throw th;
        }
    }

    @SuppressLint({"MissingPermission"})
    private int c(Context context) throws NoSuchMethodException, SecurityException {
        TelephonyManager telephonyManager;
        if (!g.a(context, Permission.READ_PHONE_STATE) || (telephonyManager = (TelephonyManager) context.getApplicationContext().getSystemService(AliyunLogCommon.TERMINAL_TYPE)) == null) {
            return -1;
        }
        if (!m.d()) {
            return telephonyManager.getDataNetworkType();
        }
        try {
            Method method = telephonyManager.getClass().getMethod("getDataNetworkType", Integer.TYPE);
            c.b("UMCTelephonyManagement", "data dataNetworkType defaultDataSubId = " + this.f9749c.f9751b);
            int iIntValue = ((Integer) method.invoke(telephonyManager, Integer.valueOf(this.f9749c.f9751b))).intValue();
            c.b("UMCTelephonyManagement", "data dataNetworkType ---------" + iIntValue);
            if (iIntValue != 0 || Build.VERSION.SDK_INT < 24) {
                return iIntValue;
            }
            c.b("UMCTelephonyManagement", "data dataNetworkType ---->=N " + iIntValue);
            return telephonyManager.getDataNetworkType();
        } catch (Exception e2) {
            c.a("UMCTelephonyManagement", "data dataNetworkType ----反射出错-----");
            e2.printStackTrace();
            return -1;
        }
    }

    public String a(Context context) {
        switch (c(context)) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
            case 16:
                return "1";
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
                return "2";
            case 13:
            case 18:
            case 19:
                return "3";
            case 20:
                return "4";
            default:
                return "0";
        }
    }

    public void a(Context context, boolean z2, boolean z3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        long jCurrentTimeMillis = System.currentTimeMillis() - f9748b;
        if (jCurrentTimeMillis >= 5000 || jCurrentTimeMillis <= 0) {
            this.f9749c = new C0198a();
            if (z3) {
                a(context, z2);
                if (m.e() && m.d()) {
                    c.b("UMCTelephonyManagement", "华为手机兼容性处理");
                    if (this.f9749c.f9751b == 0 || this.f9749c.f9751b == 1) {
                        if (this.f9749c.f9750a == -1) {
                            C0198a c0198a = this.f9749c;
                            c0198a.f9750a = c0198a.f9751b;
                        }
                        this.f9749c.f9751b = -1;
                    }
                    if (this.f9749c.f9750a != -1 || this.f9749c.f9751b != -1) {
                        b(context);
                    }
                }
                f9748b = System.currentTimeMillis();
            }
        }
    }

    public C0198a b() {
        C0198a c0198a = this.f9749c;
        return c0198a == null ? new C0198a() : c0198a;
    }
}
