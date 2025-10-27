package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes4.dex */
public abstract class ForwardingSetMultimap<K, V> extends ForwardingMultimap<K, V> implements SetMultimap<K, V> {
    @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.ForwardingObject
    public abstract SetMultimap<K, V> delegate();

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ Collection get(@NullableDecl Object obj) {
        return get((ForwardingSetMultimap<K, V>) obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ Collection replaceValues(Object obj, Iterable iterable) {
        return replaceValues((ForwardingSetMultimap<K, V>) obj, iterable);
    }

    @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap
    public Set<Map.Entry<K, V>> entries() {
        return delegate().entries();
    }

    @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap
    public Set<V> get(@NullableDecl K k2) {
        return delegate().get((SetMultimap<K, V>) k2);
    }

    @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    public Set<V> removeAll(@NullableDecl Object obj) {
        return delegate().removeAll(obj);
    }

    @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    public Set<V> replaceValues(K k2, Iterable<? extends V> iterable) {
        return delegate().replaceValues((SetMultimap<K, V>) k2, (Iterable) iterable);
    }
}
