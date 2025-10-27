package com.bumptech.glide.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class LruCache<T, Y> {
    private final Map<T, Entry<Y>> cache = new LinkedHashMap(100, 0.75f, true);
    private long currentSize;
    private final long initialMaxSize;
    private long maxSize;

    public static final class Entry<Y> {
        final int size;
        final Y value;

        public Entry(Y y2, int i2) {
            this.value = y2;
            this.size = i2;
        }
    }

    public LruCache(long j2) {
        this.initialMaxSize = j2;
        this.maxSize = j2;
    }

    private void evict() {
        trimToSize(this.maxSize);
    }

    public void clearMemory() {
        trimToSize(0L);
    }

    public synchronized boolean contains(@NonNull T t2) {
        return this.cache.containsKey(t2);
    }

    @Nullable
    public synchronized Y get(@NonNull T t2) {
        Entry<Y> entry;
        entry = this.cache.get(t2);
        return entry != null ? entry.value : null;
    }

    public synchronized int getCount() {
        return this.cache.size();
    }

    public synchronized long getCurrentSize() {
        return this.currentSize;
    }

    public synchronized long getMaxSize() {
        return this.maxSize;
    }

    public int getSize(@Nullable Y y2) {
        return 1;
    }

    public void onItemEvicted(@NonNull T t2, @Nullable Y y2) {
    }

    @Nullable
    public synchronized Y put(@NonNull T t2, @Nullable Y y2) {
        int size = getSize(y2);
        long j2 = size;
        if (j2 >= this.maxSize) {
            onItemEvicted(t2, y2);
            return null;
        }
        if (y2 != null) {
            this.currentSize += j2;
        }
        Entry<Y> entryPut = this.cache.put(t2, y2 == null ? null : new Entry<>(y2, size));
        if (entryPut != null) {
            this.currentSize -= entryPut.size;
            if (!entryPut.value.equals(y2)) {
                onItemEvicted(t2, entryPut.value);
            }
        }
        evict();
        return entryPut != null ? entryPut.value : null;
    }

    @Nullable
    public synchronized Y remove(@NonNull T t2) {
        Entry<Y> entryRemove = this.cache.remove(t2);
        if (entryRemove == null) {
            return null;
        }
        this.currentSize -= entryRemove.size;
        return entryRemove.value;
    }

    public synchronized void setSizeMultiplier(float f2) {
        if (f2 < 0.0f) {
            throw new IllegalArgumentException("Multiplier must be >= 0");
        }
        this.maxSize = Math.round(this.initialMaxSize * f2);
        evict();
    }

    public synchronized void trimToSize(long j2) {
        while (this.currentSize > j2) {
            Iterator<Map.Entry<T, Entry<Y>>> it = this.cache.entrySet().iterator();
            Map.Entry<T, Entry<Y>> next = it.next();
            Entry<Y> value = next.getValue();
            this.currentSize -= value.size;
            T key = next.getKey();
            it.remove();
            onItemEvicted(key, value.value);
        }
    }
}
