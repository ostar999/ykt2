package com.blankj.utilcode.util;

import androidx.annotation.NonNull;

/* loaded from: classes2.dex */
public final class CacheMemoryStaticUtils {
    private static CacheMemoryUtils sDefaultCacheMemoryUtils;

    public static void clear() {
        clear(getDefaultCacheMemoryUtils());
    }

    public static <T> T get(@NonNull String str) {
        return (T) get(str, getDefaultCacheMemoryUtils());
    }

    public static int getCacheCount() {
        return getCacheCount(getDefaultCacheMemoryUtils());
    }

    private static CacheMemoryUtils getDefaultCacheMemoryUtils() {
        CacheMemoryUtils cacheMemoryUtils = sDefaultCacheMemoryUtils;
        return cacheMemoryUtils != null ? cacheMemoryUtils : CacheMemoryUtils.getInstance();
    }

    public static void put(@NonNull String str, Object obj) {
        put(str, obj, getDefaultCacheMemoryUtils());
    }

    public static Object remove(@NonNull String str) {
        return remove(str, getDefaultCacheMemoryUtils());
    }

    public static void setDefaultCacheMemoryUtils(CacheMemoryUtils cacheMemoryUtils) {
        sDefaultCacheMemoryUtils = cacheMemoryUtils;
    }

    public static void clear(@NonNull CacheMemoryUtils cacheMemoryUtils) {
        cacheMemoryUtils.clear();
    }

    public static <T> T get(@NonNull String str, T t2) {
        return (T) get(str, t2, getDefaultCacheMemoryUtils());
    }

    public static int getCacheCount(@NonNull CacheMemoryUtils cacheMemoryUtils) {
        return cacheMemoryUtils.getCacheCount();
    }

    public static void put(@NonNull String str, Object obj, int i2) {
        put(str, obj, i2, getDefaultCacheMemoryUtils());
    }

    public static Object remove(@NonNull String str, @NonNull CacheMemoryUtils cacheMemoryUtils) {
        return cacheMemoryUtils.remove(str);
    }

    public static <T> T get(@NonNull String str, @NonNull CacheMemoryUtils cacheMemoryUtils) {
        return (T) cacheMemoryUtils.get(str);
    }

    public static void put(@NonNull String str, Object obj, @NonNull CacheMemoryUtils cacheMemoryUtils) {
        cacheMemoryUtils.put(str, obj);
    }

    public static <T> T get(@NonNull String str, T t2, @NonNull CacheMemoryUtils cacheMemoryUtils) {
        return (T) cacheMemoryUtils.get(str, t2);
    }

    public static void put(@NonNull String str, Object obj, int i2, @NonNull CacheMemoryUtils cacheMemoryUtils) {
        cacheMemoryUtils.put(str, obj, i2);
    }
}
