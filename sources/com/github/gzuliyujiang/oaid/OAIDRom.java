package com.github.gzuliyujiang.oaid;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import com.hyphenate.easeui.utils.RomUtils;

/* loaded from: classes3.dex */
public final class OAIDRom {
    private OAIDRom() {
    }

    public static boolean is360OS() {
        return !TextUtils.isEmpty(sysProperty("ro.build.uiversion", ""));
    }

    public static boolean isASUS() {
        return Build.MANUFACTURER.equalsIgnoreCase("ASUS") || Build.BRAND.equalsIgnoreCase("ASUS");
    }

    public static boolean isBlackShark() {
        return Build.MANUFACTURER.equalsIgnoreCase("BLACKSHARK") || Build.BRAND.equalsIgnoreCase("BLACKSHARK");
    }

    public static boolean isCoolpad(Context context) throws PackageManager.NameNotFoundException {
        try {
            context.getPackageManager().getPackageInfo("com.coolpad.deviceidsupport", 0);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean isCoosea() {
        return sysProperty("ro.odm.manufacturer", "").equalsIgnoreCase("PRIZE");
    }

    public static boolean isEmui() {
        return !TextUtils.isEmpty(sysProperty("ro.build.version.emui", ""));
    }

    public static boolean isFreeme() {
        return !TextUtils.isEmpty(sysProperty("ro.build.freeme.label", ""));
    }

    public static boolean isHonor() {
        return Build.BRAND.equalsIgnoreCase("HONOR");
    }

    public static boolean isHuawei() {
        if (!Build.MANUFACTURER.equalsIgnoreCase("HUAWEI")) {
            String str = Build.BRAND;
            if (!str.equalsIgnoreCase("HUAWEI") && !str.equalsIgnoreCase("HONOR")) {
                return false;
            }
        }
        return true;
    }

    public static boolean isLenovo() {
        if (!Build.MANUFACTURER.equalsIgnoreCase(RomUtils.ROM_LENOVO)) {
            String str = Build.BRAND;
            if (!str.equalsIgnoreCase(RomUtils.ROM_LENOVO) && !str.equalsIgnoreCase("ZUK")) {
                return false;
            }
        }
        return true;
    }

    public static boolean isMeizu() {
        return Build.MANUFACTURER.equalsIgnoreCase("MEIZU") || Build.BRAND.equalsIgnoreCase("MEIZU") || Build.DISPLAY.toUpperCase().contains(RomUtils.ROM_FLYME);
    }

    public static boolean isMiui() {
        return !TextUtils.isEmpty(sysProperty("ro.miui.ui.version.name", ""));
    }

    public static boolean isMotolora() {
        return Build.MANUFACTURER.equalsIgnoreCase("MOTOLORA") || Build.BRAND.equalsIgnoreCase("MOTOLORA");
    }

    public static boolean isNubia() {
        return Build.MANUFACTURER.equalsIgnoreCase(RomUtils.ROM_NUBIA) || Build.BRAND.equalsIgnoreCase(RomUtils.ROM_NUBIA);
    }

    public static boolean isOnePlus() {
        return Build.MANUFACTURER.equalsIgnoreCase("ONEPLUS") || Build.BRAND.equalsIgnoreCase("ONEPLUS");
    }

    public static boolean isOppo() {
        if (!Build.MANUFACTURER.equalsIgnoreCase(RomUtils.ROM_OPPO)) {
            String str = Build.BRAND;
            if (!str.equalsIgnoreCase(RomUtils.ROM_OPPO) && !str.equalsIgnoreCase("REALME") && TextUtils.isEmpty(sysProperty("ro.build.version.opporom", ""))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSSUI() {
        return !TextUtils.isEmpty(sysProperty("ro.ssui.product", ""));
    }

    public static boolean isSamsung() {
        return Build.MANUFACTURER.equalsIgnoreCase("SAMSUNG") || Build.BRAND.equalsIgnoreCase("SAMSUNG");
    }

    public static boolean isVivo() {
        return Build.MANUFACTURER.equalsIgnoreCase(RomUtils.ROM_VIVO) || Build.BRAND.equalsIgnoreCase(RomUtils.ROM_VIVO) || !TextUtils.isEmpty(sysProperty("ro.vivo.os.version", ""));
    }

    public static boolean isXiaomi() {
        if (!Build.MANUFACTURER.equalsIgnoreCase("XIAOMI")) {
            String str = Build.BRAND;
            if (!str.equalsIgnoreCase("XIAOMI") && !str.equalsIgnoreCase("REDMI")) {
                return false;
            }
        }
        return true;
    }

    public static boolean isZTE() {
        return Build.MANUFACTURER.equalsIgnoreCase(RomUtils.ROM_ZTE) || Build.BRAND.equalsIgnoreCase(RomUtils.ROM_ZTE);
    }

    public static String sysProperty(String str, String str2) throws ClassNotFoundException {
        String str3;
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            str3 = (String) cls.getMethod("get", String.class, String.class).invoke(cls, str, str2);
        } catch (Exception e2) {
            OAIDLog.print("System property invoke error: " + e2);
            str3 = null;
        }
        return str3 == null ? "" : str3;
    }
}
