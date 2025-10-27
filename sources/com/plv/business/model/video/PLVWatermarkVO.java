package com.plv.business.model.video;

import cn.hutool.core.text.CharPool;
import com.plv.business.model.PLVBaseVO;

/* loaded from: classes4.dex */
public class PLVWatermarkVO implements PLVBaseVO {
    public static final String WATERMARK_TYPE_FIXED = "fixed";
    public static final String WATERMARK_TYPE_NICKNAME = "nickname";
    public final String watermarkContent;
    public final String watermarkFontSize;
    public final String watermarkOpacity;
    public final String watermarkRestrict;
    public final String watermarkType;

    public PLVWatermarkVO(String str, String str2, String str3, String str4, String str5) {
        this.watermarkContent = str;
        this.watermarkFontSize = str2;
        this.watermarkOpacity = str3;
        this.watermarkRestrict = str4;
        this.watermarkType = str5;
    }

    public String toString() {
        return "PLVLiveMarqueeVO{watermarkContent='" + this.watermarkContent + CharPool.SINGLE_QUOTE + ", watermarkFontSize='" + this.watermarkFontSize + CharPool.SINGLE_QUOTE + ", watermarkOpacity='" + this.watermarkOpacity + CharPool.SINGLE_QUOTE + ", watermarkRestrict=" + this.watermarkRestrict + CharPool.SINGLE_QUOTE + ", watermarkType='" + this.watermarkType + CharPool.SINGLE_QUOTE + '}';
    }
}
