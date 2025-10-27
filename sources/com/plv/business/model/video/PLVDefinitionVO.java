package com.plv.business.model.video;

import com.plv.business.model.PLVBaseVO;

/* loaded from: classes4.dex */
public class PLVDefinitionVO implements PLVBaseVO {
    private String definition;
    private boolean hasSelected;
    private String m3u8;
    private String url;

    public PLVDefinitionVO(String str, String str2) {
        this.definition = str;
        this.url = str2;
    }

    public String getDefinition() {
        return this.definition;
    }

    public String getUrl() {
        return this.url;
    }
}
