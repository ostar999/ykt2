package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Predicate;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes4.dex */
final class FilteredKeySetMultimap<K, V> extends FilteredKeyMultimap<K, V> implements FilteredSetMultimap<K, V> {

    public class EntrySet extends FilteredKeyMultimap<K, V>.Entries implements Set<Map.Entry<K, V>> {
        public EntrySet() {
            super();
        }

        @Override // java.util.Collection, java.util.Set
        public boolean equals(@NullableDecl Object obj) {
            return Sets.equalsImpl(this, obj);
        }

        @Override // java.util.Collection, java.util.Set
        public int hashCode() {
            return Sets.hashCodeImpl(this);
        }
    }

    public FilteredKeySetMultimap(SetMultimap<K, V> setMultimap, Predicate<? super K> predicate) {
        super(setMultimap, predicate);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.FilteredKeyMultimap, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ Collection get(Object obj) {
        return get((FilteredKeySetMultimap<K, V>) obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ Collection replaceValues(Object obj, Iterable iterable) {
        return replaceValues((FilteredKeySetMultimap<K, V>) obj, iterable);
    }

    @Override // com.google.common.collect.FilteredKeyMultimap, com.google.common.collect.AbstractMultimap
    public Set<Map.Entry<K, V>> createEntries() {
        return new EntrySet();
    }

    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    public Set<Map.Entry<K, V>> entries() {
        return (Set) super.entries();
    }

    @Override // com.google.common.collect.FilteredKeyMultimap, com.google.common.collect.Multimap
    public Set<V> get(K k2) {
        return (Set) super.get((FilteredKeySetMultimap<K, V>) k2);
    }

    @Override // com.google.common.collect.FilteredKeyMultimap, com.google.common.collect.Multimap
    public Set<V> removeAll(Object obj) {
        return (Set) super.removeAll(obj);
    }

    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    public Set<V> replaceValues(K k2, Iterable<? extends V> iterable) {
        return (Set) super.replaceValues((FilteredKeySetMultimap<K, V>) k2, (Iterable) iterable);
    }

    @Override // com.google.common.collect.FilteredKeyMultimap, com.google.common.collect.FilteredMultimap
    public SetMultimap<K, V> unfiltered() {
        return (SetMultimap) this.unfiltered;
    }
}
