package com.plv.foundationsdk.net;

import cn.hutool.core.text.CharPool;
import com.google.gson.JsonElement;

/* loaded from: classes4.dex */
public class PLVResponseApiBean<T> extends PLVResponseBean<T> {
    private JsonElement data;

    public PLVResponseApiBean(String str) {
        super(str);
    }

    public JsonElement getData() {
        return this.data;
    }

    public void setData(JsonElement jsonElement) {
        this.data = jsonElement;
    }

    @Override // com.plv.foundationsdk.net.PLVResponseBean
    public String toString() {
        return "PLVResponseApiBean{data=" + this.data + ", code=" + this.code + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", convertBody=" + this.convertBody + ", message='" + this.message + CharPool.SINGLE_QUOTE + '}';
    }
}
