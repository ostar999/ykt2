package com.plv.business.model.video;

import android.text.TextUtils;
import cn.hutool.core.text.CharPool;
import com.plv.business.model.PLVBaseVO;

/* loaded from: classes4.dex */
public class PLVLiveMarqueeVO implements PLVBaseVO {
    public static final String MARQUEETYPE_DIYURL = "diyurl";
    public static final String MARQUEETYPE_FIXED = "fixed";
    public static final String MARQUEETYPE_NICKNAME = "nickname";
    private String channelId;
    public final String marquee;
    public final String marqueeAutoZoomEnabled;
    public final String marqueeFontColor;
    public final int marqueeFontSize;
    public final String marqueeOpacity;
    public final String marqueeSetting;
    public final String marqueeType;
    private String userId;

    public PLVLiveMarqueeVO(String str, String str2, String str3, int i2, String str4, String str5, String str6) {
        this.marquee = str;
        this.marqueeType = str2;
        this.marqueeFontColor = str3;
        this.marqueeFontSize = i2;
        this.marqueeOpacity = str4;
        this.marqueeAutoZoomEnabled = str6;
        this.marqueeSetting = str5;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public String getUserId() {
        return this.userId;
    }

    public boolean hasMarquee() {
        return !TextUtils.isEmpty(this.marqueeType);
    }

    public void setChannelId(String str) {
        this.channelId = str;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public String toString() {
        return "PLVLiveMarqueeVO{marquee='" + this.marquee + CharPool.SINGLE_QUOTE + ", marqueeType='" + this.marqueeType + CharPool.SINGLE_QUOTE + ", marqueeFontColor='" + this.marqueeFontColor + CharPool.SINGLE_QUOTE + ", marqueeFontSize=" + this.marqueeFontSize + ", marqueeOpacity='" + this.marqueeOpacity + CharPool.SINGLE_QUOTE + ", marqueeAutoZoomEnabled='" + this.marqueeAutoZoomEnabled + CharPool.SINGLE_QUOTE + '}';
    }
}
