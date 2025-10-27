package com.blankj.utilcode.util;

import androidx.annotation.NonNull;
import androidx.collection.LruCache;
import com.blankj.utilcode.constant.CacheConstants;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public final class CacheMemoryUtils implements CacheConstants {
    private static final Map<String, CacheMemoryUtils> CACHE_MAP = new HashMap();
    private static final int DEFAULT_MAX_COUNT = 256;
    private final String mCacheKey;
    private final LruCache<String, CacheValue> mMemoryCache;

    public static final class CacheValue {
        long dueTime;
        Object value;

        public CacheValue(long j2, Object obj) {
            this.dueTime = j2;
            this.value = obj;
        }
    }

    private CacheMemoryUtils(String str, LruCache<String, CacheValue> lruCache) {
        this.mCacheKey = str;
        this.mMemoryCache = lruCache;
    }

    public static CacheMemoryUtils getInstance() {
        return getInstance(256);
    }

    public void clear() {
        this.mMemoryCache.evictAll();
    }

    public <T> T get(@NonNull String str) {
        return (T) get(str, null);
    }

    public int getCacheCount() {
        return this.mMemoryCache.size();
    }

    public void put(@NonNull String str, Object obj) {
        put(str, obj, -1);
    }

    public Object remove(@NonNull String str) {
        CacheValue cacheValueRemove = this.mMemoryCache.remove(str);
        if (cacheValueRemove == null) {
            return null;
        }
        return cacheValueRemove.value;
    }

    public String toString() {
        return this.mCacheKey + "@" + Integer.toHexString(hashCode());
    }

    public static CacheMemoryUtils getInstance(int i2) {
        return getInstance(String.valueOf(i2), i2);
    }

    public <T> T get(@NonNull String str, T t2) {
        CacheValue cacheValue = this.mMemoryCache.get(str);
        if (cacheValue == null) {
            return t2;
        }
        long j2 = cacheValue.dueTime;
        if (j2 == -1 || j2 >= System.currentTimeMillis()) {
            return (T) cacheValue.value;
        }
        this.mMemoryCache.remove(str);
        return t2;
    }

    public void put(@NonNull String str, Object obj, int i2) {
        if (obj == null) {
            return;
        }
        this.mMemoryCache.put(str, new CacheValue(i2 < 0 ? -1L : System.currentTimeMillis() + (i2 * 1000), obj));
    }

    public static CacheMemoryUtils getInstance(String str, int i2) {
        Map<String, CacheMemoryUtils> map = CACHE_MAP;
        CacheMemoryUtils cacheMemoryUtils = map.get(str);
        if (cacheMemoryUtils == null) {
            synchronized (CacheMemoryUtils.class) {
                cacheMemoryUtils = map.get(str);
                if (cacheMemoryUtils == null) {
                    cacheMemoryUtils = new CacheMemoryUtils(str, new LruCache(i2));
                    map.put(str, cacheMemoryUtils);
                }
            }
        }
        return cacheMemoryUtils;
    }
}
