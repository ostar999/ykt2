package com.hyphenate.easeui.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

/* loaded from: classes4.dex */
public class EaseImageCache {
    private static EaseImageCache imageCache;
    private LruCache<String, Bitmap> cache;

    private EaseImageCache() {
        this.cache = null;
        this.cache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / 8)) { // from class: com.hyphenate.easeui.utils.EaseImageCache.1
            @Override // android.util.LruCache
            public int sizeOf(String str, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight();
            }
        };
    }

    public static synchronized EaseImageCache getInstance() {
        if (imageCache == null) {
            imageCache = new EaseImageCache();
        }
        return imageCache;
    }

    public Bitmap get(String str) {
        return this.cache.get(str);
    }

    public Bitmap put(String str, Bitmap bitmap) {
        return this.cache.put(str, bitmap);
    }
}
