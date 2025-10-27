package com.nostra13.universalimageloader.cache.memory.impl;

import android.graphics.Bitmap;
import com.nostra13.universalimageloader.cache.memory.BaseMemoryCache;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public class WeakMemoryCache extends BaseMemoryCache {
    @Override // com.nostra13.universalimageloader.cache.memory.BaseMemoryCache
    public Reference<Bitmap> createReference(Bitmap bitmap) {
        return new WeakReference(bitmap);
    }
}
