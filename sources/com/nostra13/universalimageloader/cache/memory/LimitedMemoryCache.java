package com.nostra13.universalimageloader.cache.memory;

import android.graphics.Bitmap;
import com.nostra13.universalimageloader.utils.L;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public abstract class LimitedMemoryCache extends BaseMemoryCache {
    private static final int MAX_NORMAL_CACHE_SIZE = 16777216;
    private static final int MAX_NORMAL_CACHE_SIZE_IN_MB = 16;
    private final int sizeLimit;
    private final List<Bitmap> hardCache = Collections.synchronizedList(new LinkedList());
    private final AtomicInteger cacheSize = new AtomicInteger();

    public LimitedMemoryCache(int i2) {
        this.sizeLimit = i2;
        if (i2 > 16777216) {
            L.w("You set too large memory cache size (more than %1$d Mb)", 16);
        }
    }

    @Override // com.nostra13.universalimageloader.cache.memory.BaseMemoryCache, com.nostra13.universalimageloader.cache.memory.MemoryCache
    public void clear() {
        this.hardCache.clear();
        this.cacheSize.set(0);
        super.clear();
    }

    public abstract int getSize(Bitmap bitmap);

    public int getSizeLimit() {
        return this.sizeLimit;
    }

    @Override // com.nostra13.universalimageloader.cache.memory.BaseMemoryCache, com.nostra13.universalimageloader.cache.memory.MemoryCache
    public boolean put(String str, Bitmap bitmap) {
        boolean z2;
        int size = getSize(bitmap);
        int sizeLimit = getSizeLimit();
        int iAddAndGet = this.cacheSize.get();
        if (size < sizeLimit) {
            while (iAddAndGet + size > sizeLimit) {
                Bitmap bitmapRemoveNext = removeNext();
                if (this.hardCache.remove(bitmapRemoveNext)) {
                    iAddAndGet = this.cacheSize.addAndGet(-getSize(bitmapRemoveNext));
                }
            }
            this.hardCache.add(bitmap);
            this.cacheSize.addAndGet(size);
            z2 = true;
        } else {
            z2 = false;
        }
        super.put(str, bitmap);
        return z2;
    }

    @Override // com.nostra13.universalimageloader.cache.memory.BaseMemoryCache, com.nostra13.universalimageloader.cache.memory.MemoryCache
    public Bitmap remove(String str) {
        Bitmap bitmap = super.get(str);
        if (bitmap != null && this.hardCache.remove(bitmap)) {
            this.cacheSize.addAndGet(-getSize(bitmap));
        }
        return super.remove(str);
    }

    public abstract Bitmap removeNext();
}
