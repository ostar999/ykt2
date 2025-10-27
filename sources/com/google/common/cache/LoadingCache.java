package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

@GwtCompatible
/* loaded from: classes4.dex */
public interface LoadingCache<K, V> extends Cache<K, V>, Function<K, V> {
    @Override // com.google.common.base.Function
    @Deprecated
    V apply(K k2);

    @Override // com.google.common.cache.Cache
    ConcurrentMap<K, V> asMap();

    V get(K k2) throws ExecutionException;

    ImmutableMap<K, V> getAll(Iterable<? extends K> iterable) throws ExecutionException;

    V getUnchecked(K k2);

    void refresh(K k2);
}
