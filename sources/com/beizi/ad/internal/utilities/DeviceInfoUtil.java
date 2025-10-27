package com.beizi.ad.internal.utilities;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import cn.hutool.core.text.StrPool;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.beizi.ad.a.a.g;
import com.beizi.ad.a.a.m;
import com.beizi.ad.c.e;

/* loaded from: classes2.dex */
public class DeviceInfoUtil {
    public static String ETHERNET_SERVICE = "ethernet";
    public static int SCREEN_DECIMAL_DIGITS = 2;

    private static void getIdentifiersAndDevType(Context context, DeviceInfo deviceInfo) {
        try {
            if (((TelephonyManager) context.getApplicationContext().getSystemService(AliyunLogCommon.TERMINAL_TYPE)).getPhoneType() != 0) {
                deviceInfo.devType = e.b.DEVICE_PHONE;
            } else {
                deviceInfo.devType = e.b.DEVICE_FLAT;
            }
        } catch (SecurityException unused) {
            HaoboLog.e(HaoboLog.baseLogTag, "No permission to access imei");
            int i2 = context.getResources().getConfiguration().screenLayout & 15;
            if (i2 == 4 || i2 == 3) {
                deviceInfo.devType = e.b.DEVICE_FLAT;
            } else {
                deviceInfo.devType = e.b.DEVICE_PHONE;
            }
        }
        deviceInfo.root = m.a();
    }

    private static void getScreenInformations(Context context, DeviceInfo deviceInfo) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        deviceInfo.resolution = displayMetrics.widthPixels + StrPool.UNDERLINE + displayMetrics.heightPixels;
        float f2 = ((float) displayMetrics.heightPixels) / displayMetrics.ydpi;
        float f3 = ((float) displayMetrics.widthPixels) / displayMetrics.xdpi;
        double dSqrt = Math.sqrt((double) ((f3 * f3) + (f2 * f2)));
        deviceInfo.screenSize = String.format("%.2f", Double.valueOf(Math.round(dSqrt * r2) / Math.pow(10.0d, (double) SCREEN_DECIMAL_DIGITS)));
    }

    private static void getSdkUID(Context context, DeviceInfo deviceInfo) {
        deviceInfo.sdkUID = (String) SPUtils.get(context, DeviceInfo.SDK_UID_KEY_NEW, "");
    }

    public static void retrieveDeviceInfo(Context context) {
        DeviceInfo deviceInfo = DeviceInfo.getInstance();
        if (TextUtils.isEmpty(deviceInfo.sdkUID)) {
            try {
                getIdentifiersAndDevType(context, deviceInfo);
                getScreenInformations(context, deviceInfo);
                getSdkUID(context, deviceInfo);
                deviceInfo.getMarks(context);
                if (!TextUtils.isEmpty(deviceInfo.manufacturer) && (deviceInfo.manufacturer.equalsIgnoreCase("HUAWEI") || deviceInfo.manufacturer.equalsIgnoreCase("HONOR"))) {
                    deviceInfo.agVercode = m.e(context);
                }
                deviceInfo.wxInstalled = g.a(context, "com.tencent.mm");
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }
}
