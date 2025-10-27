package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.AbstractMapBasedMultimap;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes4.dex */
abstract class AbstractSortedSetMultimap<K, V> extends AbstractSetMultimap<K, V> implements SortedSetMultimap<K, V> {
    private static final long serialVersionUID = 430848587173315748L;

    public AbstractSortedSetMultimap(Map<K, Collection<V>> map) {
        super(map);
    }

    @Override // com.google.common.collect.AbstractSetMultimap, com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Map<K, Collection<V>> asMap() {
        return super.asMap();
    }

    @Override // com.google.common.collect.AbstractSetMultimap, com.google.common.collect.AbstractMapBasedMultimap
    public abstract SortedSet<V> createCollection();

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.AbstractSetMultimap, com.google.common.collect.AbstractMapBasedMultimap, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ Collection get(@NullableDecl Object obj) {
        return get((AbstractSortedSetMultimap<K, V>) obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.AbstractSetMultimap, com.google.common.collect.AbstractMapBasedMultimap, com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ Collection replaceValues(@NullableDecl Object obj, Iterable iterable) {
        return replaceValues((AbstractSortedSetMultimap<K, V>) obj, iterable);
    }

    @Override // com.google.common.collect.AbstractMapBasedMultimap, com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    public Collection<V> values() {
        return super.values();
    }

    @Override // com.google.common.collect.AbstractSetMultimap, com.google.common.collect.AbstractMapBasedMultimap
    public Collection<V> wrapCollection(K k2, Collection<V> collection) {
        return collection instanceof NavigableSet ? new AbstractMapBasedMultimap.WrappedNavigableSet(k2, (NavigableSet) collection, null) : new AbstractMapBasedMultimap.WrappedSortedSet(k2, (SortedSet) collection, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.AbstractSetMultimap, com.google.common.collect.AbstractMapBasedMultimap, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ Set get(@NullableDecl Object obj) {
        return get((AbstractSortedSetMultimap<K, V>) obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.AbstractSetMultimap, com.google.common.collect.AbstractMapBasedMultimap, com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ Set replaceValues(@NullableDecl Object obj, Iterable iterable) {
        return replaceValues((AbstractSortedSetMultimap<K, V>) obj, iterable);
    }

    @Override // com.google.common.collect.AbstractSetMultimap, com.google.common.collect.AbstractMapBasedMultimap
    public <E> SortedSet<E> unmodifiableCollectionSubclass(Collection<E> collection) {
        return collection instanceof NavigableSet ? Sets.unmodifiableNavigableSet((NavigableSet) collection) : Collections.unmodifiableSortedSet((SortedSet) collection);
    }

    @Override // com.google.common.collect.AbstractSetMultimap, com.google.common.collect.AbstractMapBasedMultimap
    public SortedSet<V> createUnmodifiableEmptyCollection() {
        return (SortedSet<V>) unmodifiableCollectionSubclass((Collection) createCollection());
    }

    @Override // com.google.common.collect.AbstractSetMultimap, com.google.common.collect.AbstractMapBasedMultimap, com.google.common.collect.Multimap
    public SortedSet<V> get(@NullableDecl K k2) {
        return (SortedSet) super.get((AbstractSortedSetMultimap<K, V>) k2);
    }

    @Override // com.google.common.collect.AbstractSetMultimap, com.google.common.collect.AbstractMapBasedMultimap, com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    public SortedSet<V> removeAll(@NullableDecl Object obj) {
        return (SortedSet) super.removeAll(obj);
    }

    @Override // com.google.common.collect.AbstractSetMultimap, com.google.common.collect.AbstractMapBasedMultimap, com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    public SortedSet<V> replaceValues(@NullableDecl K k2, Iterable<? extends V> iterable) {
        return (SortedSet) super.replaceValues((AbstractSortedSetMultimap<K, V>) k2, (Iterable) iterable);
    }
}
