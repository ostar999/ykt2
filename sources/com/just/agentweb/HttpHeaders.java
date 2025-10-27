package com.just.agentweb;

import android.net.Uri;
import android.text.TextUtils;
import androidx.collection.ArrayMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class HttpHeaders {
    private final Map<String, Map<String, String>> mHeaders = new ArrayMap();

    public static HttpHeaders create() {
        return new HttpHeaders();
    }

    private String subBaseUrl(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        try {
            Uri uri = Uri.parse(str);
            if (TextUtils.isEmpty(uri.getScheme()) || TextUtils.isEmpty(uri.getAuthority())) {
                return "";
            }
            return uri.getScheme() + "://" + uri.getAuthority();
        } catch (Throwable th) {
            th.printStackTrace();
            return "";
        }
    }

    public void additionalHttpHeader(String str, String str2, String str3) {
        if (str == null) {
            return;
        }
        String strSubBaseUrl = subBaseUrl(str);
        Map<String, Map<String, String>> headers = getHeaders();
        Map<String, String> arrayMap = headers.get(subBaseUrl(strSubBaseUrl));
        if (arrayMap == null) {
            arrayMap = new ArrayMap<>();
        }
        arrayMap.put(str2, str3);
        headers.put(strSubBaseUrl, arrayMap);
    }

    public void additionalHttpHeaders(String str, Map<String, String> map) {
        if (str == null) {
            return;
        }
        String strSubBaseUrl = subBaseUrl(str);
        Map<String, Map<String, String>> headers = getHeaders();
        if (map == null) {
            map = new ArrayMap<>();
        }
        headers.put(strSubBaseUrl, map);
    }

    public Map<String, Map<String, String>> getHeaders() {
        return this.mHeaders;
    }

    public Map<String, String> getHeaders(String str) {
        String strSubBaseUrl = subBaseUrl(str);
        if (this.mHeaders.get(strSubBaseUrl) != null) {
            return this.mHeaders.get(strSubBaseUrl);
        }
        ArrayMap arrayMap = new ArrayMap();
        this.mHeaders.put(strSubBaseUrl, arrayMap);
        return arrayMap;
    }

    public boolean isEmptyHeaders(String str) {
        Map<String, String> headers = getHeaders(subBaseUrl(str));
        return headers == null || headers.isEmpty();
    }

    public void removeHttpHeader(String str, String str2) {
        if (str == null) {
            return;
        }
        Map<String, String> map = getHeaders().get(subBaseUrl(str));
        if (map != null) {
            map.remove(str2);
        }
    }

    public String toString() {
        return "HttpHeaders{mHeaders=" + this.mHeaders + '}';
    }
}
