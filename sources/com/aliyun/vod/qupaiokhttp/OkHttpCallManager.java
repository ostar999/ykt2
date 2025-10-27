package com.aliyun.vod.qupaiokhttp;

import com.aliyun.vod.common.utils.StringUtils;
import java.util.concurrent.ConcurrentHashMap;
import okhttp3.Call;

/* loaded from: classes2.dex */
class OkHttpCallManager {
    private static OkHttpCallManager manager;
    private ConcurrentHashMap<String, Call> callMap = new ConcurrentHashMap<>();

    private OkHttpCallManager() {
    }

    public static OkHttpCallManager getInstance() {
        if (manager == null) {
            manager = new OkHttpCallManager();
        }
        return manager;
    }

    public void addCall(String str, Call call) {
        if (call == null || StringUtils.isEmpty(str)) {
            return;
        }
        this.callMap.put(str, call);
    }

    public Call getCall(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        return this.callMap.get(str);
    }

    public void removeCall(String str) {
        if (StringUtils.isEmpty(str)) {
            return;
        }
        this.callMap.remove(str);
    }
}
