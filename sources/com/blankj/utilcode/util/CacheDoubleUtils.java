package com.blankj.utilcode.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import cn.hutool.core.text.StrPool;
import com.blankj.utilcode.constant.CacheConstants;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class CacheDoubleUtils implements CacheConstants {
    private static final Map<String, CacheDoubleUtils> CACHE_MAP = new HashMap();
    private final CacheDiskUtils mCacheDiskUtils;
    private final CacheMemoryUtils mCacheMemoryUtils;

    private CacheDoubleUtils(CacheMemoryUtils cacheMemoryUtils, CacheDiskUtils cacheDiskUtils) {
        this.mCacheMemoryUtils = cacheMemoryUtils;
        this.mCacheDiskUtils = cacheDiskUtils;
    }

    public static CacheDoubleUtils getInstance() {
        return getInstance(CacheMemoryUtils.getInstance(), CacheDiskUtils.getInstance());
    }

    public void clear() {
        this.mCacheMemoryUtils.clear();
        this.mCacheDiskUtils.clear();
    }

    public Bitmap getBitmap(@NonNull String str) {
        return getBitmap(str, null);
    }

    public byte[] getBytes(@NonNull String str) {
        return getBytes(str, null);
    }

    public int getCacheDiskCount() {
        return this.mCacheDiskUtils.getCacheCount();
    }

    public long getCacheDiskSize() {
        return this.mCacheDiskUtils.getCacheSize();
    }

    public int getCacheMemoryCount() {
        return this.mCacheMemoryUtils.getCacheCount();
    }

    public Drawable getDrawable(@NonNull String str) {
        return getDrawable(str, null);
    }

    public JSONArray getJSONArray(@NonNull String str) {
        return getJSONArray(str, null);
    }

    public JSONObject getJSONObject(@NonNull String str) {
        return getJSONObject(str, null);
    }

    public <T> T getParcelable(@NonNull String str, @NonNull Parcelable.Creator<T> creator) {
        return (T) getParcelable(str, creator, null);
    }

    public Object getSerializable(@NonNull String str) {
        return getSerializable(str, null);
    }

    public String getString(@NonNull String str) {
        return getString(str, null);
    }

    public void put(@NonNull String str, byte[] bArr) throws InterruptedException {
        put(str, bArr, -1);
    }

    public void remove(@NonNull String str) {
        this.mCacheMemoryUtils.remove(str);
        this.mCacheDiskUtils.remove(str);
    }

    public static CacheDoubleUtils getInstance(@NonNull CacheMemoryUtils cacheMemoryUtils, @NonNull CacheDiskUtils cacheDiskUtils) {
        String str = cacheDiskUtils.toString() + StrPool.UNDERLINE + cacheMemoryUtils.toString();
        Map<String, CacheDoubleUtils> map = CACHE_MAP;
        CacheDoubleUtils cacheDoubleUtils = map.get(str);
        if (cacheDoubleUtils == null) {
            synchronized (CacheDoubleUtils.class) {
                cacheDoubleUtils = map.get(str);
                if (cacheDoubleUtils == null) {
                    cacheDoubleUtils = new CacheDoubleUtils(cacheMemoryUtils, cacheDiskUtils);
                    map.put(str, cacheDoubleUtils);
                }
            }
        }
        return cacheDoubleUtils;
    }

    public Bitmap getBitmap(@NonNull String str, Bitmap bitmap) {
        Bitmap bitmap2 = (Bitmap) this.mCacheMemoryUtils.get(str);
        if (bitmap2 != null) {
            return bitmap2;
        }
        Bitmap bitmap3 = this.mCacheDiskUtils.getBitmap(str);
        if (bitmap3 == null) {
            return bitmap;
        }
        this.mCacheMemoryUtils.put(str, bitmap3);
        return bitmap3;
    }

    public byte[] getBytes(@NonNull String str, byte[] bArr) {
        byte[] bArr2 = (byte[]) this.mCacheMemoryUtils.get(str);
        if (bArr2 != null) {
            return bArr2;
        }
        byte[] bytes = this.mCacheDiskUtils.getBytes(str);
        if (bytes == null) {
            return bArr;
        }
        this.mCacheMemoryUtils.put(str, bytes);
        return bytes;
    }

    public Drawable getDrawable(@NonNull String str, Drawable drawable) {
        Drawable drawable2 = (Drawable) this.mCacheMemoryUtils.get(str);
        if (drawable2 != null) {
            return drawable2;
        }
        Drawable drawable3 = this.mCacheDiskUtils.getDrawable(str);
        if (drawable3 == null) {
            return drawable;
        }
        this.mCacheMemoryUtils.put(str, drawable3);
        return drawable3;
    }

    public JSONArray getJSONArray(@NonNull String str, JSONArray jSONArray) {
        JSONArray jSONArray2 = (JSONArray) this.mCacheMemoryUtils.get(str);
        if (jSONArray2 != null) {
            return jSONArray2;
        }
        JSONArray jSONArray3 = this.mCacheDiskUtils.getJSONArray(str);
        if (jSONArray3 == null) {
            return jSONArray;
        }
        this.mCacheMemoryUtils.put(str, jSONArray3);
        return jSONArray3;
    }

    public JSONObject getJSONObject(@NonNull String str, JSONObject jSONObject) {
        JSONObject jSONObject2 = (JSONObject) this.mCacheMemoryUtils.get(str);
        if (jSONObject2 != null) {
            return jSONObject2;
        }
        JSONObject jSONObject3 = this.mCacheDiskUtils.getJSONObject(str);
        if (jSONObject3 == null) {
            return jSONObject;
        }
        this.mCacheMemoryUtils.put(str, jSONObject3);
        return jSONObject3;
    }

    public <T> T getParcelable(@NonNull String str, @NonNull Parcelable.Creator<T> creator, T t2) {
        T t3 = (T) this.mCacheMemoryUtils.get(str);
        if (t3 != null) {
            return t3;
        }
        T t4 = (T) this.mCacheDiskUtils.getParcelable(str, creator);
        if (t4 == null) {
            return t2;
        }
        this.mCacheMemoryUtils.put(str, t4);
        return t4;
    }

    public Object getSerializable(@NonNull String str, Object obj) {
        Object obj2 = this.mCacheMemoryUtils.get(str);
        if (obj2 != null) {
            return obj2;
        }
        Object serializable = this.mCacheDiskUtils.getSerializable(str);
        if (serializable == null) {
            return obj;
        }
        this.mCacheMemoryUtils.put(str, serializable);
        return serializable;
    }

    public String getString(@NonNull String str, String str2) {
        String str3 = (String) this.mCacheMemoryUtils.get(str);
        if (str3 != null) {
            return str3;
        }
        String string = this.mCacheDiskUtils.getString(str);
        if (string == null) {
            return str2;
        }
        this.mCacheMemoryUtils.put(str, string);
        return string;
    }

    public void put(@NonNull String str, byte[] bArr, int i2) throws InterruptedException {
        this.mCacheMemoryUtils.put(str, bArr, i2);
        this.mCacheDiskUtils.put(str, bArr, i2);
    }

    public void put(@NonNull String str, String str2) throws InterruptedException {
        put(str, str2, -1);
    }

    public void put(@NonNull String str, String str2, int i2) throws InterruptedException {
        this.mCacheMemoryUtils.put(str, str2, i2);
        this.mCacheDiskUtils.put(str, str2, i2);
    }

    public void put(@NonNull String str, JSONObject jSONObject) throws InterruptedException {
        put(str, jSONObject, -1);
    }

    public void put(@NonNull String str, JSONObject jSONObject, int i2) throws InterruptedException {
        this.mCacheMemoryUtils.put(str, jSONObject, i2);
        this.mCacheDiskUtils.put(str, jSONObject, i2);
    }

    public void put(@NonNull String str, JSONArray jSONArray) throws InterruptedException {
        put(str, jSONArray, -1);
    }

    public void put(@NonNull String str, JSONArray jSONArray, int i2) throws InterruptedException {
        this.mCacheMemoryUtils.put(str, jSONArray, i2);
        this.mCacheDiskUtils.put(str, jSONArray, i2);
    }

    public void put(@NonNull String str, Bitmap bitmap) throws InterruptedException {
        put(str, bitmap, -1);
    }

    public void put(@NonNull String str, Bitmap bitmap, int i2) throws InterruptedException {
        this.mCacheMemoryUtils.put(str, bitmap, i2);
        this.mCacheDiskUtils.put(str, bitmap, i2);
    }

    public void put(@NonNull String str, Drawable drawable) throws InterruptedException {
        put(str, drawable, -1);
    }

    public void put(@NonNull String str, Drawable drawable, int i2) throws InterruptedException {
        this.mCacheMemoryUtils.put(str, drawable, i2);
        this.mCacheDiskUtils.put(str, drawable, i2);
    }

    public void put(@NonNull String str, Parcelable parcelable) throws InterruptedException {
        put(str, parcelable, -1);
    }

    public void put(@NonNull String str, Parcelable parcelable, int i2) throws InterruptedException {
        this.mCacheMemoryUtils.put(str, parcelable, i2);
        this.mCacheDiskUtils.put(str, parcelable, i2);
    }

    public void put(@NonNull String str, Serializable serializable) throws InterruptedException {
        put(str, serializable, -1);
    }

    public void put(@NonNull String str, Serializable serializable, int i2) throws InterruptedException {
        this.mCacheMemoryUtils.put(str, serializable, i2);
        this.mCacheDiskUtils.put(str, serializable, i2);
    }
}
