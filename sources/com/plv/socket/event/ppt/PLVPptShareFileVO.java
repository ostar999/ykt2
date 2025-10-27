package com.plv.socket.event.ppt;

import cn.hutool.core.text.CharPool;

/* loaded from: classes5.dex */
public class PLVPptShareFileVO {
    private String name;
    private String suffix;
    private String url;

    public String getName() {
        return this.name;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public String getUrl() {
        return this.url;
    }

    public PLVPptShareFileVO setName(String str) {
        this.name = str;
        return this;
    }

    public PLVPptShareFileVO setSuffix(String str) {
        this.suffix = str;
        return this;
    }

    public PLVPptShareFileVO setUrl(String str) {
        this.url = str;
        return this;
    }

    public String toString() {
        return "PLVPptShareFileVO{url='" + this.url + CharPool.SINGLE_QUOTE + ", name='" + this.name + CharPool.SINGLE_QUOTE + ", suffix='" + this.suffix + CharPool.SINGLE_QUOTE + '}';
    }
}
