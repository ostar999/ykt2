package com.hyphenate.easeui.utils;

import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Properties;

/* loaded from: classes4.dex */
public class RomUtils {
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    public static final String ROM_COOLPAD = "COOLPAD";
    public static final String ROM_EMUI = "EMUI";
    public static final String ROM_FLYME = "FLYME";
    public static final String ROM_LENOVO = "LENOVO";
    public static final String ROM_LETV = "LETV";
    public static final String ROM_MIUI = "MIUI";
    public static final String ROM_NUBIA = "NUBIA";
    public static final String ROM_OPPO = "OPPO";
    public static final String ROM_QIKU = "QIKU";
    public static final String ROM_SMARTISAN = "SMARTISAN";
    public static final String ROM_UNKNOWN = "UNKNOWN";
    public static final String ROM_VIVO = "VIVO";
    public static final String ROM_ZTE = "ZTE";
    private static final String SYSTEM_VERSION_EMUI = "ro.build.version.emui";
    private static final String SYSTEM_VERSION_FLYME = "ro.build.display.id";
    private static final String SYSTEM_VERSION_LENOVO = "ro.lenovo.lvp.version";
    private static final String SYSTEM_VERSION_LETV = "ro.letv.eui";
    private static final String SYSTEM_VERSION_MIUI = "ro.miui.ui.version.name";
    private static final String SYSTEM_VERSION_OPPO = "ro.build.version.opporom";
    private static final String SYSTEM_VERSION_SMARTISAN = "ro.smartisan.version";
    private static final String SYSTEM_VERSION_VIVO = "ro.vivo.os.version";

    public class AvailableRomType {
        public static final int ANDROID_NATIVE = 3;
        public static final int FLYME = 2;
        public static final int MIUI = 1;
        public static final int NA = 4;

        public AvailableRomType() {
        }
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RomName {
    }

    public static String getDeviceManufacture() {
        return isMiuiRom() ? "小米" : isHuaweiRom() ? "华为" : isVivoRom() ? ROM_VIVO : isOppoRom() ? ROM_OPPO : isMeizuRom() ? "魅族" : isSmartisanRom() ? "锤子" : is360Rom() ? "奇酷" : isLetvRom() ? "乐视" : isLenovoRom() ? "联想" : isZTERom() ? "中兴" : isCoolPadRom() ? "酷派" : Build.MANUFACTURER;
    }

    public static int getLightStatusBarAvailableRomType() {
        if (isMiUIV7OrAbove()) {
            return 3;
        }
        if (isMiUIV6OrAbove()) {
            return 1;
        }
        if (isFlymeV4OrAbove()) {
            return 2;
        }
        return isAndroidMOrAbove() ? 3 : 4;
    }

    public static String getRomName() {
        return isMiuiRom() ? ROM_MIUI : isHuaweiRom() ? ROM_EMUI : isVivoRom() ? ROM_VIVO : isOppoRom() ? ROM_OPPO : isMeizuRom() ? ROM_FLYME : isSmartisanRom() ? ROM_SMARTISAN : is360Rom() ? ROM_QIKU : isLetvRom() ? ROM_LETV : isLenovoRom() ? ROM_LENOVO : isZTERom() ? ROM_ZTE : isCoolPadRom() ? ROM_COOLPAD : "UNKNOWN";
    }

    private static String getSystemProperty(String str) {
        return "";
    }

    public static boolean is360Rom() {
        String str = Build.MANUFACTURER;
        return !TextUtils.isEmpty(str) && str.toUpperCase().contains(ROM_QIKU);
    }

    private static boolean isAndroidMOrAbove() {
        return true;
    }

    public static boolean isCoolPadRom() {
        String str = Build.MODEL;
        String str2 = Build.FINGERPRINT;
        return (!TextUtils.isEmpty(str) && str.toLowerCase().contains(ROM_COOLPAD)) || (!TextUtils.isEmpty(str2) && str2.toLowerCase().contains(ROM_COOLPAD));
    }

    public static boolean isDomesticSpecialRom() {
        return isMiuiRom() || isHuaweiRom() || isMeizuRom() || is360Rom() || isOppoRom() || isVivoRom() || isLetvRom() || isZTERom() || isLenovoRom() || isCoolPadRom();
    }

    private static boolean isFlymeV4OrAbove() {
        String str = Build.DISPLAY;
        if (!TextUtils.isEmpty(str) && str.contains("Flyme")) {
            for (String str2 : str.split(" ")) {
                if (str2.matches("^[4-9]\\.(\\d+\\.)+\\S*")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isHuaweiRom() {
        return !TextUtils.isEmpty(getSystemProperty(SYSTEM_VERSION_EMUI));
    }

    public static boolean isLenovoRom() {
        return !TextUtils.isEmpty(getSystemProperty(SYSTEM_VERSION_LENOVO));
    }

    public static boolean isLetvRom() {
        return !TextUtils.isEmpty(getSystemProperty(SYSTEM_VERSION_LETV));
    }

    public static boolean isMeizuRom() {
        String systemProperty = getSystemProperty(SYSTEM_VERSION_FLYME);
        return !TextUtils.isEmpty(systemProperty) && systemProperty.toUpperCase().contains(ROM_FLYME);
    }

    private static boolean isMiUIV6OrAbove() throws Throwable {
        Properties properties;
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2 = null;
        try {
            properties = new Properties();
            fileInputStream = new FileInputStream(new File(Environment.getRootDirectory(), "build.prop"));
        } catch (Exception unused) {
        } catch (Throwable th) {
            th = th;
        }
        try {
            properties.load(fileInputStream);
            String property = properties.getProperty(KEY_MIUI_VERSION_CODE, null);
            if (property == null) {
                try {
                    fileInputStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                return false;
            }
            boolean z2 = Integer.parseInt(property) >= 4;
            try {
                fileInputStream.close();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
            return z2;
        } catch (Exception unused2) {
            fileInputStream2 = fileInputStream;
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            return false;
        } catch (Throwable th2) {
            th = th2;
            fileInputStream2 = fileInputStream;
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static boolean isMiUIV7OrAbove() throws Throwable {
        Properties properties;
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2 = null;
        try {
            properties = new Properties();
            fileInputStream = new FileInputStream(new File(Environment.getRootDirectory(), "build.prop"));
        } catch (Exception unused) {
        } catch (Throwable th) {
            th = th;
        }
        try {
            properties.load(fileInputStream);
            String property = properties.getProperty(KEY_MIUI_VERSION_CODE, null);
            if (property == null) {
                try {
                    fileInputStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                return false;
            }
            boolean z2 = Integer.parseInt(property) >= 5;
            try {
                fileInputStream.close();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
            return z2;
        } catch (Exception unused2) {
            fileInputStream2 = fileInputStream;
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            return false;
        } catch (Throwable th2) {
            th = th2;
            fileInputStream2 = fileInputStream;
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static boolean isMiuiRom() {
        return !TextUtils.isEmpty(getSystemProperty(SYSTEM_VERSION_MIUI));
    }

    public static boolean isOppoRom() {
        return !TextUtils.isEmpty(getSystemProperty(SYSTEM_VERSION_OPPO));
    }

    public static boolean isSmartisanRom() {
        return !TextUtils.isEmpty(getSystemProperty(SYSTEM_VERSION_SMARTISAN));
    }

    public static boolean isVivoRom() {
        return !TextUtils.isEmpty(getSystemProperty(SYSTEM_VERSION_VIVO));
    }

    public static boolean isZTERom() {
        String str = Build.MANUFACTURER;
        String str2 = Build.FINGERPRINT;
        return (!TextUtils.isEmpty(str) && (str2.toLowerCase().contains(ROM_NUBIA) || str2.toLowerCase().contains(ROM_ZTE))) || (!TextUtils.isEmpty(str2) && (str2.toLowerCase().contains(ROM_NUBIA) || str2.toLowerCase().contains(ROM_ZTE)));
    }
}
