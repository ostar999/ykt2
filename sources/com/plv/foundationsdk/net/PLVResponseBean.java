package com.plv.foundationsdk.net;

import cn.hutool.core.text.CharPool;
import com.google.gson.JsonElement;

/* loaded from: classes4.dex */
public class PLVResponseBean<T> extends PLVBaseResponseBean<T> {
    private JsonElement body;
    private String bodyMesaage;

    public PLVResponseBean(String str) {
        this.bodyMesaage = str;
    }

    public JsonElement getBody() {
        return this.body;
    }

    public void setBody(JsonElement jsonElement) {
        this.body = jsonElement;
    }

    public String toString() {
        return "PLVResponseBean{code=" + this.code + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", body=" + this.body + ", convertBody=" + this.convertBody + ", bodyMesaage='" + this.bodyMesaage + CharPool.SINGLE_QUOTE + '}';
    }
}
