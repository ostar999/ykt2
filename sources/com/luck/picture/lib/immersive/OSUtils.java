package com.luck.picture.lib.immersive;

import android.text.TextUtils;

/* loaded from: classes4.dex */
public class OSUtils {
    private static final String KEY_EMUI_VERSION_NAME = "ro.build.version.emui";

    public static String getEMUIVersion() {
        return isEMUI() ? getSystemProperty() : "";
    }

    private static String getSystemProperty() throws ClassNotFoundException {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod("get", String.class, String.class).invoke(cls, KEY_EMUI_VERSION_NAME, "");
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static boolean isEMUI() {
        return !TextUtils.isEmpty(getSystemProperty());
    }

    public static boolean isEMUI3_0() {
        return getEMUIVersion().contains("EmotionUI_3.0");
    }

    public static boolean isEMUI3_1() {
        String eMUIVersion = getEMUIVersion();
        return "EmotionUI 3".equals(eMUIVersion) || eMUIVersion.contains("EmotionUI_3.1");
    }

    public static boolean isEMUI3_x() {
        return isEMUI3_0() || isEMUI3_1();
    }
}
