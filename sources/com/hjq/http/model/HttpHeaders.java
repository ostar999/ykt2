package com.hjq.http.model;

import com.hjq.http.EasyConfig;
import java.util.HashMap;
import java.util.Set;

/* loaded from: classes4.dex */
public final class HttpHeaders {
    private HashMap<String, String> mHeaders = EasyConfig.getInstance().getHeaders();

    public String get(String str) {
        return this.mHeaders.get(str);
    }

    public HashMap<String, String> getHeaders() {
        return this.mHeaders;
    }

    public Set<String> getNames() {
        return this.mHeaders.keySet();
    }

    public boolean isEmpty() {
        HashMap<String, String> map = this.mHeaders;
        return map == null || map.isEmpty();
    }

    public void put(String str, String str2) {
        if (str == null || str2 == null) {
            return;
        }
        if (this.mHeaders == EasyConfig.getInstance().getHeaders()) {
            this.mHeaders = new HashMap<>(this.mHeaders);
        }
        this.mHeaders.put(str, str2);
    }

    public void remove(String str) {
        if (str == null) {
            return;
        }
        if (this.mHeaders == EasyConfig.getInstance().getHeaders()) {
            this.mHeaders = new HashMap<>(this.mHeaders);
        }
        this.mHeaders.remove(str);
    }
}
