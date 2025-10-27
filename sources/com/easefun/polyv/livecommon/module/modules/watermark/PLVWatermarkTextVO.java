package com.easefun.polyv.livecommon.module.modules.watermark;

import com.tencent.connect.common.Constants;

/* loaded from: classes3.dex */
public class PLVWatermarkTextVO {
    private String content = " ";
    private String fontSize = "middle";
    private String fontAlpha = Constants.VIA_REPORT_TYPE_SHARE_TO_QQ;
    private int fontColor = -16777216;

    public String getContent() {
        return this.content;
    }

    public String getFontAlpha() {
        return this.fontAlpha;
    }

    public int getFontColor() {
        return this.fontColor;
    }

    public int getFontSize() {
        String str = this.fontSize;
        str.hashCode();
        switch (str) {
            case "middle":
                return 16;
            case "large":
                return 20;
            case "small":
                return 12;
            default:
                return 30;
        }
    }

    public PLVWatermarkTextVO setContent(String content) {
        this.content = content;
        return this;
    }

    public PLVWatermarkTextVO setFontAlpha(String fontAlpha) {
        this.fontAlpha = fontAlpha;
        return this;
    }

    public PLVWatermarkTextVO setFontColor(int fontColor) {
        this.fontColor = fontColor;
        return this;
    }

    public PLVWatermarkTextVO setFontSize(String fontSize) {
        this.fontSize = fontSize;
        return this;
    }
}
