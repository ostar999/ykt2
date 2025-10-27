package com.google.common.graph;

import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* loaded from: classes4.dex */
class MapRetrievalCache<K, V> extends MapIteratorCache<K, V> {

    @NullableDecl
    private transient CacheEntry<K, V> cacheEntry1;

    @NullableDecl
    private transient CacheEntry<K, V> cacheEntry2;

    public static final class CacheEntry<K, V> {
        final K key;
        final V value;

        public CacheEntry(K k2, V v2) {
            this.key = k2;
            this.value = v2;
        }
    }

    public MapRetrievalCache(Map<K, V> map) {
        super(map);
    }

    private void addToCache(K k2, V v2) {
        addToCache(new CacheEntry<>(k2, v2));
    }

    @Override // com.google.common.graph.MapIteratorCache
    public void clearCache() {
        super.clearCache();
        this.cacheEntry1 = null;
        this.cacheEntry2 = null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.MapIteratorCache
    public V get(@NullableDecl Object obj) {
        V ifCached = getIfCached(obj);
        if (ifCached != null) {
            return ifCached;
        }
        V withoutCaching = getWithoutCaching(obj);
        if (withoutCaching != null) {
            addToCache(obj, withoutCaching);
        }
        return withoutCaching;
    }

    @Override // com.google.common.graph.MapIteratorCache
    public V getIfCached(@NullableDecl Object obj) {
        V v2 = (V) super.getIfCached(obj);
        if (v2 != null) {
            return v2;
        }
        CacheEntry<K, V> cacheEntry = this.cacheEntry1;
        if (cacheEntry != null && cacheEntry.key == obj) {
            return cacheEntry.value;
        }
        CacheEntry<K, V> cacheEntry2 = this.cacheEntry2;
        if (cacheEntry2 == null || cacheEntry2.key != obj) {
            return null;
        }
        addToCache(cacheEntry2);
        return cacheEntry2.value;
    }

    private void addToCache(CacheEntry<K, V> cacheEntry) {
        this.cacheEntry2 = this.cacheEntry1;
        this.cacheEntry1 = cacheEntry;
    }
}
