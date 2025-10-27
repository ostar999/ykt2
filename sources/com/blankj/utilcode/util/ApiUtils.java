package com.blankj.utilcode.util;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public final class ApiUtils {
    private static final String TAG = "ApiUtils";
    private Map<Class, BaseApi> mApiMap;
    private Map<Class, Class> mInjectApiImplMap;

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.CLASS)
    public @interface Api {
        boolean isMock() default false;
    }

    public static class BaseApi {
    }

    public static class LazyHolder {
        private static final ApiUtils INSTANCE = new ApiUtils();

        private LazyHolder() {
        }
    }

    @Nullable
    public static <T extends BaseApi> T getApi(@NonNull Class<T> cls) {
        return (T) getInstance().getApiInner(cls);
    }

    private <Result> Result getApiInner(Class cls) {
        Result result = (Result) ((BaseApi) this.mApiMap.get(cls));
        if (result != null) {
            return result;
        }
        synchronized (cls) {
            Result result2 = (Result) ((BaseApi) this.mApiMap.get(cls));
            if (result2 != null) {
                return result2;
            }
            Class cls2 = this.mInjectApiImplMap.get(cls);
            if (cls2 == null) {
                Log.e(TAG, "The <" + cls + "> doesn't implement.");
                return null;
            }
            try {
                Result result3 = (Result) ((BaseApi) cls2.newInstance());
                this.mApiMap.put(cls, result3);
                return result3;
            } catch (Exception unused) {
                Log.e(TAG, "The <" + cls2 + "> has no parameterless constructor.");
                return null;
            }
        }
    }

    private static ApiUtils getInstance() {
        return LazyHolder.INSTANCE;
    }

    private void init() {
    }

    public static void register(@Nullable Class<? extends BaseApi> cls) {
        if (cls == null) {
            return;
        }
        getInstance().registerImpl(cls);
    }

    private void registerImpl(Class cls) {
        this.mInjectApiImplMap.put(cls.getSuperclass(), cls);
    }

    @NonNull
    public static String toString_() {
        return getInstance().toString();
    }

    public String toString() {
        return "ApiUtils: " + this.mInjectApiImplMap;
    }

    private ApiUtils() {
        this.mApiMap = new ConcurrentHashMap();
        this.mInjectApiImplMap = new HashMap();
        init();
    }
}
