package com.google.common.cache;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ForwardingObject;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtIncompatible
/* loaded from: classes4.dex */
public abstract class ForwardingCache<K, V> extends ForwardingObject implements Cache<K, V> {

    public static abstract class SimpleForwardingCache<K, V> extends ForwardingCache<K, V> {
        private final Cache<K, V> delegate;

        public SimpleForwardingCache(Cache<K, V> cache) {
            this.delegate = (Cache) Preconditions.checkNotNull(cache);
        }

        @Override // com.google.common.cache.ForwardingCache, com.google.common.collect.ForwardingObject
        public final Cache<K, V> delegate() {
            return this.delegate;
        }
    }

    @Override // com.google.common.cache.Cache
    public ConcurrentMap<K, V> asMap() {
        return delegate().asMap();
    }

    @Override // com.google.common.cache.Cache
    public void cleanUp() {
        delegate().cleanUp();
    }

    @Override // com.google.common.collect.ForwardingObject
    public abstract Cache<K, V> delegate();

    @Override // com.google.common.cache.Cache
    public V get(K k2, Callable<? extends V> callable) throws ExecutionException {
        return delegate().get(k2, callable);
    }

    @Override // com.google.common.cache.Cache
    public ImmutableMap<K, V> getAllPresent(Iterable<?> iterable) {
        return delegate().getAllPresent(iterable);
    }

    @Override // com.google.common.cache.Cache
    @NullableDecl
    public V getIfPresent(Object obj) {
        return delegate().getIfPresent(obj);
    }

    @Override // com.google.common.cache.Cache
    public void invalidate(Object obj) {
        delegate().invalidate(obj);
    }

    @Override // com.google.common.cache.Cache
    public void invalidateAll(Iterable<?> iterable) {
        delegate().invalidateAll(iterable);
    }

    @Override // com.google.common.cache.Cache
    public void put(K k2, V v2) {
        delegate().put(k2, v2);
    }

    @Override // com.google.common.cache.Cache
    public void putAll(Map<? extends K, ? extends V> map) {
        delegate().putAll(map);
    }

    @Override // com.google.common.cache.Cache
    public long size() {
        return delegate().size();
    }

    @Override // com.google.common.cache.Cache
    public CacheStats stats() {
        return delegate().stats();
    }

    @Override // com.google.common.cache.Cache
    public void invalidateAll() {
        delegate().invalidateAll();
    }
}
