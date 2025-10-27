package com.plv.business.sub.danmaku.entity;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import cn.hutool.core.text.CharPool;
import com.plv.business.sub.danmaku.auxiliary.PLVDanmakuTransfer;
import com.plv.foundationsdk.model.PLVFoundationVO;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class PLVDanmakuInfo implements PLVFoundationVO {
    public static final String FONTMODE_BOTTOM = "bottom";
    public static final String FONTMODE_ROLL = "roll";
    public static final String FONTMODE_TOP = "top";
    public static final String FONTSIZE_LARGE = "24";
    public static final String FONTSIZE_MIDDLE = "18";
    public static final String FONTSIZE_SMALL = "16";
    private String fontColor;
    private String fontMode;
    private String fontSize;
    private String msg;
    private String time;
    private String timestamp;
    private String vid;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FontMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FontSize {
    }

    public PLVDanmakuInfo(@NonNull String str, @NonNull String str2, @NonNull String str3) {
        this(str, str2, str3, "18", FONTMODE_ROLL, -1);
    }

    public String getFontColor() {
        return this.fontColor;
    }

    public String getFontMode() {
        return this.fontMode;
    }

    public String getFontSize() {
        return this.fontSize;
    }

    public String getMsg() {
        return this.msg;
    }

    public String getTime() {
        return this.time;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public String getVid() {
        return this.vid;
    }

    public void setFontColor(String str) {
        this.fontColor = str;
    }

    public void setFontMode(String str) {
        this.fontMode = str;
    }

    public void setFontSize(String str) {
        this.fontSize = str;
    }

    public void setMsg(String str) {
        this.msg = str;
    }

    public void setTime(String str) {
        this.time = str;
    }

    public void setTimestamp(String str) {
        this.timestamp = str;
    }

    public String toString() {
        return "PLVDanmakuInfo{msg='" + this.msg + CharPool.SINGLE_QUOTE + ", time='" + this.time + CharPool.SINGLE_QUOTE + ", fontSize='" + this.fontSize + CharPool.SINGLE_QUOTE + ", fontMode='" + this.fontMode + CharPool.SINGLE_QUOTE + ", fontColor='" + this.fontColor + CharPool.SINGLE_QUOTE + ", timestamp='" + this.timestamp + CharPool.SINGLE_QUOTE + '}';
    }

    public PLVDanmakuInfo(@NonNull String str, @NonNull String str2, @NonNull String str3, String str4, String str5, @ColorInt int i2) {
        this.vid = PLVDanmakuTransfer.checkVid(str);
        this.msg = PLVDanmakuTransfer.checkMsg(str2);
        this.time = PLVDanmakuTransfer.checkTime(str3);
        this.fontSize = PLVDanmakuTransfer.checkFontSizeTransfer(str4);
        this.fontMode = PLVDanmakuTransfer.checkFontModeTransfer(str5);
        this.fontColor = PLVDanmakuTransfer.checkFontColorTransfer(i2);
    }
}
