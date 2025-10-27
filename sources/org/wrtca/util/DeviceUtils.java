package org.wrtca.util;

import android.content.Context;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.TypedValue;
import android.view.WindowManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/* loaded from: classes9.dex */
public class DeviceUtils {
    public static int dipToPX(Context context, float f2) {
        return (int) TypedValue.applyDimension(1, f2, context.getResources().getDisplayMetrics());
    }

    public static String getCpuInfo() throws IOException {
        try {
            if (!new File("/proc/cpuinfo").exists()) {
                return "";
            }
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/cpuinfo"), 8192);
            String line = bufferedReader.readLine();
            bufferedReader.close();
            return line != null ? line.split(":")[1].trim().split(" ")[0] : line;
        } catch (IOException | Exception unused) {
            return "";
        }
    }

    public static String getDeviceModel() {
        return StringUtils.trim(Build.MODEL);
    }

    public static String getManufacturer() {
        return StringUtils.trim(Build.MANUFACTURER);
    }

    public static String getReleaseVersion() {
        return StringUtils.makeSafe(Build.VERSION.RELEASE);
    }

    public static String getSDKVersion() {
        return Build.VERSION.SDK;
    }

    public static int getSDKVersionInt() {
        return Build.VERSION.SDK_INT;
    }

    public static int getScreenHeight(Context context) {
        return ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getHeight();
    }

    public static int getScreenWidth(Context context) {
        return ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getWidth();
    }

    public static boolean hasFroyo() {
        return true;
    }

    public static boolean hasGingerbread() {
        return true;
    }

    public static boolean hasHoneycomb() {
        return true;
    }

    public static boolean hasHoneycombMR1() {
        return true;
    }

    public static boolean hasICS() {
        return true;
    }

    public static boolean hasJellyBean() {
        return true;
    }

    public static boolean hasJellyBeanMr1() {
        return true;
    }

    public static boolean hasJellyBeanMr2() {
        return true;
    }

    public static boolean hasKitkat() {
        return true;
    }

    public static boolean isDevice(String... strArr) {
        String deviceModel = getDeviceModel();
        if (strArr != null && deviceModel != null) {
            for (String str : strArr) {
                if (deviceModel.indexOf(str) != -1) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isHTC() {
        return getManufacturer().toLowerCase().indexOf("htc") != -1;
    }

    public static boolean isHoneycombTablet(Context context) {
        return hasHoneycomb() && isTablet(context);
    }

    public static boolean isSamsung() {
        return getManufacturer().toLowerCase().indexOf("samsung") != -1;
    }

    public static boolean isSupportCameraHardware(Context context) {
        return context != null && context.getPackageManager().hasSystemFeature("android.hardware.camera");
    }

    public static boolean isSupportCameraLedFlash(PackageManager packageManager) {
        FeatureInfo[] systemAvailableFeatures;
        if (packageManager != null && (systemAvailableFeatures = packageManager.getSystemAvailableFeatures()) != null) {
            for (FeatureInfo featureInfo : systemAvailableFeatures) {
                if (featureInfo != null && "android.hardware.camera.flash".equals(featureInfo.name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & 15) >= 3;
    }

    public static boolean isZte() {
        return getDeviceModel().toLowerCase().indexOf("zte") != -1;
    }
}
