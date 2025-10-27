package com.easefun.polyv.livecommon.module.utils.imageloader;

/* loaded from: classes3.dex */
public class PLVUrlTag {
    private Object data;
    private String url;

    public PLVUrlTag(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public void setTag(Object data) {
        this.data = data;
    }

    public String toString() {
        Object obj = this.data;
        return obj == null ? super.toString() : obj.toString();
    }

    public PLVUrlTag(String url, Object data) {
        this.url = url;
        this.data = data;
    }
}
