package com.plv.business.sub.danmaku.auxiliary;

import com.plv.business.sub.danmaku.entity.PLVDanmakuInfo;
import java.util.Locale;

/* loaded from: classes4.dex */
public class PLVDanmakuTransfer {
    public static String checkFontColorTransfer(int i2) {
        int i3 = i2 | (-16777216);
        return (i3 > -1 || i3 < -16777216) ? toPlvFontColor(-1) : toPlvFontColor(i2);
    }

    public static String checkFontModeTransfer(String str) {
        if (str != null) {
            return (str.equals(PLVDanmakuInfo.FONTMODE_ROLL) || str.equals(PLVDanmakuInfo.FONTMODE_TOP) || str.equals(PLVDanmakuInfo.FONTMODE_BOTTOM)) ? str : PLVDanmakuInfo.FONTMODE_ROLL;
        }
        throw new NullPointerException("fontMode is null");
    }

    public static String checkFontSizeTransfer(String str) {
        if (str != null) {
            return (str.equals("24") || str.equals("18") || str.equals("16")) ? str : "18";
        }
        throw new NullPointerException("fontSize is null");
    }

    public static String checkMsg(String str) {
        if (str == null) {
            throw new NullPointerException("msg is null");
        }
        if (str.trim().length() != 0) {
            return str;
        }
        throw new IllegalArgumentException("msg cannot be empty");
    }

    public static String checkTime(String str) {
        if (str == null) {
            throw new NullPointerException("time is null");
        }
        if (str.matches("[0-9][0-9]:[0-5][0-9]:[0-5][0-9]")) {
            return str;
        }
        throw new IllegalArgumentException("time format is not correct.and see [0-9][0-9]:[0-5][0-9]:[0-5][0-9]");
    }

    public static String checkVid(String str) {
        if (str != null) {
            return str;
        }
        throw new NullPointerException("vid is null");
    }

    public static String toPlvDanmakuTime(long j2) {
        int i2 = (int) (j2 / 1000);
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", Integer.valueOf(i2 / 3600), Integer.valueOf((i2 / 60) % 60), Integer.valueOf(i2 % 60));
    }

    private static String toPlvFontColor(int i2) {
        String hexString = Integer.toHexString(i2);
        if (hexString.length() != 8) {
            return "0xFFFFFF";
        }
        return "0x" + hexString.substring(2).toUpperCase();
    }
}
