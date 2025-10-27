package com.android.volley;

import com.android.volley.Cache;

/* loaded from: classes2.dex */
public class Response<T> {
    public final Cache.Entry cacheEntry;
    public final VolleyError error;
    public boolean intermediate;
    public final T result;

    public interface ErrorListener {
        void onErrorResponse(VolleyError volleyError, String str);
    }

    public interface Listener<T> {
        void onResponse(T t2, String str);
    }

    private Response(T t2, Cache.Entry entry) {
        this.intermediate = false;
        this.result = t2;
        this.cacheEntry = entry;
        this.error = null;
    }

    public static <T> Response<T> error(VolleyError volleyError) {
        return new Response<>(volleyError);
    }

    public static <T> Response<T> success(T t2, Cache.Entry entry) {
        return new Response<>(t2, entry);
    }

    public boolean isSuccess() {
        return this.error == null;
    }

    private Response(VolleyError volleyError) {
        this.intermediate = false;
        this.result = null;
        this.cacheEntry = null;
        this.error = volleyError;
    }
}
