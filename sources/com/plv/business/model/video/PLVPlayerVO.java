package com.plv.business.model.video;

import com.plv.business.model.PLVBaseVO;

/* loaded from: classes4.dex */
public class PLVPlayerVO implements PLVBaseVO {
    private final String pColor;
    private final String skinColor;
    private final String zColor;

    public PLVPlayerVO(String str, String str2, String str3) {
        this.zColor = str;
        this.skinColor = str2;
        this.pColor = str3;
    }

    public String getSkinColor() {
        return this.skinColor;
    }

    public String getpColor() {
        return this.pColor;
    }

    public String getzColor() {
        return this.zColor;
    }
}
