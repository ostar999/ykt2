package com.aliyun.vod.qupaiokhttp;

import org.json.JSONObject;

/* loaded from: classes2.dex */
public class JsonHttpRequestCallback extends BaseHttpRequestCallback<JSONObject> {
    public JsonHttpRequestCallback() {
        this.type = JSONObject.class;
    }
}
