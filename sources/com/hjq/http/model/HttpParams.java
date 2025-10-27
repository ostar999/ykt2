package com.hjq.http.model;

import com.hjq.http.EasyConfig;
import java.util.HashMap;
import java.util.Set;

/* loaded from: classes4.dex */
public final class HttpParams {
    private boolean mMultipart;
    private HashMap<String, Object> mParams = EasyConfig.getInstance().getParams();

    public Object get(String str) {
        return this.mParams.get(str);
    }

    public Set<String> getNames() {
        return this.mParams.keySet();
    }

    public HashMap<String, Object> getParams() {
        return this.mParams;
    }

    public boolean isEmpty() {
        HashMap<String, Object> map = this.mParams;
        return map == null || map.isEmpty();
    }

    public boolean isMultipart() {
        return this.mMultipart;
    }

    public void put(String str, Object obj) {
        if (str == null || obj == null) {
            return;
        }
        if (this.mParams == EasyConfig.getInstance().getParams()) {
            this.mParams = new HashMap<>(this.mParams);
        }
        this.mParams.put(str, obj);
    }

    public void remove(String str) {
        if (str == null) {
            return;
        }
        if (this.mParams == EasyConfig.getInstance().getParams()) {
            this.mParams = new HashMap<>(this.mParams);
        }
        this.mParams.remove(str);
    }

    public void setMultipart(boolean z2) {
        this.mMultipart = z2;
    }
}
