package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.ConcurrentMap;

@GwtCompatible
/* loaded from: classes4.dex */
public abstract class ForwardingConcurrentMap<K, V> extends ForwardingMap<K, V> implements ConcurrentMap<K, V> {
    @Override // com.google.common.collect.ForwardingMap, com.google.common.collect.ForwardingObject
    public abstract ConcurrentMap<K, V> delegate();

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    @CanIgnoreReturnValue
    public V putIfAbsent(K k2, V v2) {
        return delegate().putIfAbsent(k2, v2);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    @CanIgnoreReturnValue
    public boolean remove(Object obj, Object obj2) {
        return delegate().remove(obj, obj2);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    @CanIgnoreReturnValue
    public V replace(K k2, V v2) {
        return delegate().replace(k2, v2);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    @CanIgnoreReturnValue
    public boolean replace(K k2, V v2, V v3) {
        return delegate().replace(k2, v2, v3);
    }
}
