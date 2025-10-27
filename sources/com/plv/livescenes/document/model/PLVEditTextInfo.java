package com.plv.livescenes.document.model;

import com.plv.foundationsdk.model.PLVFoundationVO;

/* loaded from: classes4.dex */
public class PLVEditTextInfo implements PLVFoundationVO {
    private String content;

    public PLVEditTextInfo(String str) {
        this.content = str;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        this.content = str;
    }
}
