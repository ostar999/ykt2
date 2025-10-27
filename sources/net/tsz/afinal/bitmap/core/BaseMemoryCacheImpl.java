package net.tsz.afinal.bitmap.core;

import android.graphics.Bitmap;
import net.tsz.afinal.utils.Utils;

/* loaded from: classes9.dex */
public class BaseMemoryCacheImpl implements IMemoryCache {
    private final LruMemoryCache<String, Bitmap> mMemoryCache;

    public BaseMemoryCacheImpl(int i2) {
        this.mMemoryCache = new LruMemoryCache<String, Bitmap>(i2) { // from class: net.tsz.afinal.bitmap.core.BaseMemoryCacheImpl.1
            @Override // net.tsz.afinal.bitmap.core.LruMemoryCache
            public int sizeOf(String str, Bitmap bitmap) {
                return Utils.getBitmapSize(bitmap);
            }
        };
    }

    @Override // net.tsz.afinal.bitmap.core.IMemoryCache
    public void evictAll() {
        this.mMemoryCache.evictAll();
    }

    @Override // net.tsz.afinal.bitmap.core.IMemoryCache
    public Bitmap get(String str) {
        return this.mMemoryCache.get(str);
    }

    @Override // net.tsz.afinal.bitmap.core.IMemoryCache
    public void put(String str, Bitmap bitmap) {
        this.mMemoryCache.put(str, bitmap);
    }

    @Override // net.tsz.afinal.bitmap.core.IMemoryCache
    public void remove(String str) {
        this.mMemoryCache.remove(str);
    }
}
