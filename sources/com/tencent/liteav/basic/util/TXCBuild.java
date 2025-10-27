package com.tencent.liteav.basic.util;

import android.os.Build;
import com.tencent.liteav.basic.log.TXCLog;

/* loaded from: classes6.dex */
public class TXCBuild {
    private static String BOARD = "";
    private static String BRAND = "";
    private static String HARDWARE = "";
    private static String MANUFACTURER = "";
    private static String MODEL = "";
    private static final String TAG = "TXCBuild";
    private static String VERSION = "";
    private static int VERSION_INT;

    public static String Board() {
        String str = BOARD;
        if (str == null || str.isEmpty()) {
            synchronized (TXCBuild.class) {
                String str2 = BOARD;
                if (str2 == null || str2.isEmpty()) {
                    BOARD = Build.BOARD;
                    TXCLog.i(TAG, "get BOARD by Build.BOARD :" + BOARD);
                }
            }
        }
        return BOARD;
    }

    public static String Brand() {
        String str = BRAND;
        if (str == null || str.isEmpty()) {
            synchronized (TXCBuild.class) {
                String str2 = BRAND;
                if (str2 == null || str2.isEmpty()) {
                    BRAND = Build.BRAND;
                    TXCLog.i(TAG, "get BRAND by Build.BRAND :" + BRAND);
                }
            }
        }
        return BRAND;
    }

    public static String Hardware() {
        String str = HARDWARE;
        if (str == null || str.isEmpty()) {
            synchronized (TXCBuild.class) {
                String str2 = HARDWARE;
                if (str2 == null || str2.isEmpty()) {
                    HARDWARE = Build.HARDWARE;
                    TXCLog.i(TAG, "get HARDWARE by Build.HARDWARE :" + HARDWARE);
                }
            }
        }
        return HARDWARE;
    }

    public static String Manufacturer() {
        String str = MANUFACTURER;
        if (str == null || str.isEmpty()) {
            synchronized (TXCBuild.class) {
                String str2 = MANUFACTURER;
                if (str2 == null || str2.isEmpty()) {
                    MANUFACTURER = Build.MANUFACTURER;
                    TXCLog.i(TAG, "get MANUFACTURER by Build.MANUFACTURER :" + MANUFACTURER);
                }
            }
        }
        return MANUFACTURER;
    }

    public static String Model() {
        String str = MODEL;
        if (str == null || str.isEmpty()) {
            synchronized (TXCBuild.class) {
                String str2 = MODEL;
                if (str2 == null || str2.isEmpty()) {
                    MODEL = Build.MODEL;
                    TXCLog.i(TAG, "get MODEL by Build.MODEL :" + MODEL);
                }
            }
        }
        return MODEL;
    }

    public static void SetBoard(String str) {
        synchronized (TXCBuild.class) {
            BOARD = str;
        }
    }

    public static void SetBrand(String str) {
        synchronized (TXCBuild.class) {
            BRAND = str;
        }
    }

    public static void SetHardware(String str) {
        synchronized (TXCBuild.class) {
            HARDWARE = str;
        }
    }

    public static void SetManufacturer(String str) {
        synchronized (TXCBuild.class) {
            MANUFACTURER = str;
        }
    }

    public static void SetModel(String str) {
        synchronized (TXCBuild.class) {
            MODEL = str;
        }
    }

    public static void SetVersion(String str) {
        synchronized (TXCBuild.class) {
            VERSION = str;
        }
    }

    public static void SetVersionInt(int i2) {
        synchronized (TXCBuild.class) {
            VERSION_INT = i2;
        }
    }

    public static String Version() {
        String str = VERSION;
        if (str == null || str.isEmpty()) {
            synchronized (TXCBuild.class) {
                String str2 = VERSION;
                if (str2 == null || str2.isEmpty()) {
                    VERSION = Build.VERSION.RELEASE;
                    TXCLog.i(TAG, "get VERSION by Build.VERSION.RELEASE :" + VERSION);
                }
            }
        }
        return VERSION;
    }

    public static int VersionInt() {
        if (VERSION_INT == 0) {
            synchronized (TXCBuild.class) {
                if (VERSION_INT == 0) {
                    VERSION_INT = Build.VERSION.SDK_INT;
                    TXCLog.i(TAG, "get VERSION_INT by Build.VERSION.SDK_INT :" + VERSION_INT);
                }
            }
        }
        return VERSION_INT;
    }
}
