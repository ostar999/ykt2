package com.beizi.ad.internal.utilities;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import com.beizi.ad.R;
import com.beizi.ad.a.a.m;
import com.beizi.ad.c.e;
import com.beizi.ad.internal.g;
import java.util.Locale;

/* loaded from: classes2.dex */
public class DeviceInfo {
    public static String SDK_UID_KEY = "SDK_UID_KEY";
    public static String SDK_UID_KEY_NEW = "SDK_UID_KEY_NEW";
    public static String density;
    public static String harddiskSizeByte;
    public static String physicalMemoryByte;
    private static DeviceInfo sDeviceInfoInstance;
    public String agVercode;
    public String bootMark;
    public String root;
    public String updateMark;
    public boolean wxInstalled;
    public String sdkUID = null;
    public boolean firstLaunch = false;
    public final String os = Build.VERSION.SDK_INT + " (" + Build.VERSION.RELEASE + ")";
    public e.b devType = e.b.DEVICE_OTHER;
    public final String brand = Build.BRAND;
    public final String model = Build.MODEL;
    public final String manufacturer = Build.MANUFACTURER;
    public String resolution = null;
    public String screenSize = null;
    public final String language = Locale.getDefault().getLanguage();

    static {
        try {
            System.loadLibrary("devInfo");
        } catch (Throwable th) {
            th.printStackTrace();
        }
        sDeviceInfoInstance = null;
    }

    private DeviceInfo() {
    }

    public static DeviceInfo getInstance() {
        DeviceInfo deviceInfo;
        synchronized (DeviceInfo.class) {
            if (sDeviceInfoInstance == null) {
                sDeviceInfoInstance = new DeviceInfo();
                HaoboLog.v(HaoboLog.baseLogTag, HaoboLog.getString(R.string.init));
                density = g.a().f4184i.getResources().getDisplayMetrics().density + "";
                physicalMemoryByte = m.i(g.a().f4184i);
                harddiskSizeByte = m.b();
            }
            deviceInfo = sDeviceInfoInstance;
        }
        return deviceInfo;
    }

    public native String getBootMark();

    public void getMarks(Context context) {
        try {
            int i2 = SPUtils.getInt(context, "SystemMarkStatus", 0);
            Log.e("BeiZisAd", "getMarks status= " + i2);
            int i3 = 1;
            if (i2 == 1) {
                SPUtils.putInt(context, "SystemMarkStatus", 3);
                SPUtils.put(context, "SystemMarkStatusExpireTime", Long.valueOf(System.currentTimeMillis()));
                i2 = 3;
            }
            if (i2 == 0 || i2 == 2) {
                SPUtils.putInt(context, "SystemMarkStatus", 1);
            } else {
                i3 = i2;
            }
            if (i3 == 3) {
                Long l2 = (Long) SPUtils.get(context, "SystemMarkStatusExpireTime", Long.valueOf(Long.parseLong("0")));
                if (l2.longValue() == 0 || Long.valueOf(System.currentTimeMillis() - l2.longValue()).longValue() <= -1702967296) {
                    return;
                }
                SPUtils.putInt(context, "SystemMarkStatus", 0);
                SPUtils.put(context, "SystemMarkStatusExpireTime", Long.valueOf(Long.parseLong("0")));
                Log.d("BeiZisAd", "bootMark expireTime= null and status = 0");
                return;
            }
            String bootMark = getBootMark();
            if (bootMark != null) {
                this.bootMark = bootMark.substring(0, Math.min(36, bootMark.length()));
            }
            this.updateMark = getUpdateMark();
            SPUtils.putInt(context, "SystemMarkStatus", 2);
            Log.e("BeiZisAd", "bootMark = " + this.bootMark + ",updateMark = " + this.updateMark);
        } catch (Throwable th) {
            th.printStackTrace();
            Log.d("BeiZisAd", "Exception：" + th.getMessage());
        }
    }

    public native String getUpdateMark();
}
