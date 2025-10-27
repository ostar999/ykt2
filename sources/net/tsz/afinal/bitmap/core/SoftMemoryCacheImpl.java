package net.tsz.afinal.bitmap.core;

import android.graphics.Bitmap;
import java.lang.ref.SoftReference;
import net.tsz.afinal.utils.Utils;

/* loaded from: classes9.dex */
public class SoftMemoryCacheImpl implements IMemoryCache {
    private final LruMemoryCache<String, SoftReference<Bitmap>> mMemoryCache;

    public SoftMemoryCacheImpl(int i2) {
        this.mMemoryCache = new LruMemoryCache<String, SoftReference<Bitmap>>(i2) { // from class: net.tsz.afinal.bitmap.core.SoftMemoryCacheImpl.1
            @Override // net.tsz.afinal.bitmap.core.LruMemoryCache
            public int sizeOf(String str, SoftReference<Bitmap> softReference) {
                Bitmap bitmap = softReference == null ? null : softReference.get();
                if (bitmap == null) {
                    return 1;
                }
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
        SoftReference<Bitmap> softReference = this.mMemoryCache.get(str);
        if (softReference != null) {
            return softReference.get();
        }
        return null;
    }

    @Override // net.tsz.afinal.bitmap.core.IMemoryCache
    public void put(String str, Bitmap bitmap) {
        this.mMemoryCache.put(str, new SoftReference<>(bitmap));
    }

    @Override // net.tsz.afinal.bitmap.core.IMemoryCache
    public void remove(String str) {
        this.mMemoryCache.remove(str);
    }
}
