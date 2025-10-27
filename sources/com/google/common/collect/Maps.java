package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Converter;
import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Sets;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.j2objc.annotations.RetainedWith;
import com.google.j2objc.annotations.Weak;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
/* loaded from: classes4.dex */
public final class Maps {

    public static abstract class AbstractFilteredMap<K, V> extends ViewCachingAbstractMap<K, V> {
        final Predicate<? super Map.Entry<K, V>> predicate;
        final Map<K, V> unfiltered;

        public AbstractFilteredMap(Map<K, V> map, Predicate<? super Map.Entry<K, V>> predicate) {
            this.unfiltered = map;
            this.predicate = predicate;
        }

        public boolean apply(@NullableDecl Object obj, @NullableDecl V v2) {
            return this.predicate.apply(Maps.immutableEntry(obj, v2));
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(Object obj) {
            return this.unfiltered.containsKey(obj) && apply(obj, this.unfiltered.get(obj));
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap
        public Collection<V> createValues() {
            return new FilteredMapValues(this, this.unfiltered, this.predicate);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V get(Object obj) {
            V v2 = this.unfiltered.get(obj);
            if (v2 == null || !apply(obj, v2)) {
                return null;
            }
            return v2;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean isEmpty() {
            return entrySet().isEmpty();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V put(K k2, V v2) {
            Preconditions.checkArgument(apply(k2, v2));
            return this.unfiltered.put(k2, v2);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public void putAll(Map<? extends K, ? extends V> map) {
            for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
                Preconditions.checkArgument(apply(entry.getKey(), entry.getValue()));
            }
            this.unfiltered.putAll(map);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V remove(Object obj) {
            if (containsKey(obj)) {
                return this.unfiltered.remove(obj);
            }
            return null;
        }
    }

    public static class AsMapView<K, V> extends ViewCachingAbstractMap<K, V> {
        final Function<? super K, V> function;
        private final Set<K> set;

        public AsMapView(Set<K> set, Function<? super K, V> function) {
            this.set = (Set) Preconditions.checkNotNull(set);
            this.function = (Function) Preconditions.checkNotNull(function);
        }

        public Set<K> backingSet() {
            return this.set;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public void clear() {
            backingSet().clear();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(@NullableDecl Object obj) {
            return backingSet().contains(obj);
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap
        public Set<Map.Entry<K, V>> createEntrySet() {
            return new EntrySet<K, V>() { // from class: com.google.common.collect.Maps.AsMapView.1EntrySetImpl
                @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
                public Iterator<Map.Entry<K, V>> iterator() {
                    return Maps.asMapEntryIterator(AsMapView.this.backingSet(), AsMapView.this.function);
                }

                @Override // com.google.common.collect.Maps.EntrySet
                public Map<K, V> map() {
                    return AsMapView.this;
                }
            };
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap
        public Set<K> createKeySet() {
            return Maps.removeOnlySet(backingSet());
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap
        public Collection<V> createValues() {
            return Collections2.transform(this.set, this.function);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V get(@NullableDecl Object obj) {
            if (Collections2.safeContains(backingSet(), obj)) {
                return this.function.apply(obj);
            }
            return null;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V remove(@NullableDecl Object obj) {
            if (backingSet().remove(obj)) {
                return this.function.apply(obj);
            }
            return null;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public int size() {
            return backingSet().size();
        }
    }

    public static final class BiMapConverter<A, B> extends Converter<A, B> implements Serializable {
        private static final long serialVersionUID = 0;
        private final BiMap<A, B> bimap;

        public BiMapConverter(BiMap<A, B> biMap) {
            this.bimap = (BiMap) Preconditions.checkNotNull(biMap);
        }

        private static <X, Y> Y convert(BiMap<X, Y> biMap, X x2) {
            Y y2 = biMap.get(x2);
            Preconditions.checkArgument(y2 != null, "No non-null mapping present for input: %s", x2);
            return y2;
        }

        @Override // com.google.common.base.Converter
        public A doBackward(B b3) {
            return (A) convert(this.bimap.inverse(), b3);
        }

        @Override // com.google.common.base.Converter
        public B doForward(A a3) {
            return (B) convert(this.bimap, a3);
        }

        @Override // com.google.common.base.Converter, com.google.common.base.Function
        public boolean equals(@NullableDecl Object obj) {
            if (obj instanceof BiMapConverter) {
                return this.bimap.equals(((BiMapConverter) obj).bimap);
            }
            return false;
        }

        public int hashCode() {
            return this.bimap.hashCode();
        }

        public String toString() {
            return "Maps.asConverter(" + this.bimap + ")";
        }
    }

    @GwtIncompatible
    public static abstract class DescendingMap<K, V> extends ForwardingMap<K, V> implements NavigableMap<K, V> {

        @MonotonicNonNullDecl
        private transient Comparator<? super K> comparator;

        @MonotonicNonNullDecl
        private transient Set<Map.Entry<K, V>> entrySet;

        @MonotonicNonNullDecl
        private transient NavigableSet<K> navigableKeySet;

        private static <T> Ordering<T> reverse(Comparator<T> comparator) {
            return Ordering.from(comparator).reverse();
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> ceilingEntry(K k2) {
            return forward().floorEntry(k2);
        }

        @Override // java.util.NavigableMap
        public K ceilingKey(K k2) {
            return forward().floorKey(k2);
        }

        @Override // java.util.SortedMap
        public Comparator<? super K> comparator() {
            Comparator<? super K> comparator = this.comparator;
            if (comparator != null) {
                return comparator;
            }
            Comparator<? super K> comparator2 = forward().comparator();
            if (comparator2 == null) {
                comparator2 = Ordering.natural();
            }
            Ordering orderingReverse = reverse(comparator2);
            this.comparator = orderingReverse;
            return orderingReverse;
        }

        public Set<Map.Entry<K, V>> createEntrySet() {
            return new EntrySet<K, V>() { // from class: com.google.common.collect.Maps.DescendingMap.1EntrySetImpl
                @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
                public Iterator<Map.Entry<K, V>> iterator() {
                    return DescendingMap.this.entryIterator();
                }

                @Override // com.google.common.collect.Maps.EntrySet
                public Map<K, V> map() {
                    return DescendingMap.this;
                }
            };
        }

        @Override // java.util.NavigableMap
        public NavigableSet<K> descendingKeySet() {
            return forward().navigableKeySet();
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> descendingMap() {
            return forward();
        }

        public abstract Iterator<Map.Entry<K, V>> entryIterator();

        @Override // com.google.common.collect.ForwardingMap, java.util.Map
        public Set<Map.Entry<K, V>> entrySet() {
            Set<Map.Entry<K, V>> set = this.entrySet;
            if (set != null) {
                return set;
            }
            Set<Map.Entry<K, V>> setCreateEntrySet = createEntrySet();
            this.entrySet = setCreateEntrySet;
            return setCreateEntrySet;
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> firstEntry() {
            return forward().lastEntry();
        }

        @Override // java.util.SortedMap
        public K firstKey() {
            return forward().lastKey();
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> floorEntry(K k2) {
            return forward().ceilingEntry(k2);
        }

        @Override // java.util.NavigableMap
        public K floorKey(K k2) {
            return forward().ceilingKey(k2);
        }

        public abstract NavigableMap<K, V> forward();

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> headMap(K k2, boolean z2) {
            return forward().tailMap(k2, z2).descendingMap();
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> higherEntry(K k2) {
            return forward().lowerEntry(k2);
        }

        @Override // java.util.NavigableMap
        public K higherKey(K k2) {
            return forward().lowerKey(k2);
        }

        @Override // com.google.common.collect.ForwardingMap, java.util.Map
        public Set<K> keySet() {
            return navigableKeySet();
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> lastEntry() {
            return forward().firstEntry();
        }

        @Override // java.util.SortedMap
        public K lastKey() {
            return forward().firstKey();
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> lowerEntry(K k2) {
            return forward().higherEntry(k2);
        }

        @Override // java.util.NavigableMap
        public K lowerKey(K k2) {
            return forward().higherKey(k2);
        }

        @Override // java.util.NavigableMap
        public NavigableSet<K> navigableKeySet() {
            NavigableSet<K> navigableSet = this.navigableKeySet;
            if (navigableSet != null) {
                return navigableSet;
            }
            NavigableKeySet navigableKeySet = new NavigableKeySet(this);
            this.navigableKeySet = navigableKeySet;
            return navigableKeySet;
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> pollFirstEntry() {
            return forward().pollLastEntry();
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> pollLastEntry() {
            return forward().pollFirstEntry();
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> subMap(K k2, boolean z2, K k3, boolean z3) {
            return forward().subMap(k3, z3, k2, z2).descendingMap();
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> tailMap(K k2, boolean z2) {
            return forward().headMap(k2, z2).descendingMap();
        }

        @Override // com.google.common.collect.ForwardingObject
        public String toString() {
            return standardToString();
        }

        @Override // com.google.common.collect.ForwardingMap, java.util.Map
        public Collection<V> values() {
            return new Values(this);
        }

        @Override // com.google.common.collect.ForwardingMap, com.google.common.collect.ForwardingObject
        public final Map<K, V> delegate() {
            return forward();
        }

        @Override // java.util.NavigableMap, java.util.SortedMap
        public SortedMap<K, V> headMap(K k2) {
            return headMap(k2, false);
        }

        @Override // java.util.NavigableMap, java.util.SortedMap
        public SortedMap<K, V> subMap(K k2, K k3) {
            return subMap(k2, true, k3, false);
        }

        @Override // java.util.NavigableMap, java.util.SortedMap
        public SortedMap<K, V> tailMap(K k2) {
            return tailMap(k2, true);
        }
    }

    public enum EntryFunction implements Function<Map.Entry<?, ?>, Object> {
        KEY { // from class: com.google.common.collect.Maps.EntryFunction.1
            @Override // com.google.common.base.Function
            @NullableDecl
            public Object apply(Map.Entry<?, ?> entry) {
                return entry.getKey();
            }
        },
        VALUE { // from class: com.google.common.collect.Maps.EntryFunction.2
            @Override // com.google.common.base.Function
            @NullableDecl
            public Object apply(Map.Entry<?, ?> entry) {
                return entry.getValue();
            }
        }
    }

    public static abstract class EntrySet<K, V> extends Sets.ImprovedAbstractSet<Map.Entry<K, V>> {
        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            map().clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object objSafeGet = Maps.safeGet(map(), key);
            if (Objects.equal(objSafeGet, entry.getValue())) {
                return objSafeGet != null || map().containsKey(key);
            }
            return false;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return map().isEmpty();
        }

        public abstract Map<K, V> map();

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            if (contains(obj)) {
                return map().keySet().remove(((Map.Entry) obj).getKey());
            }
            return false;
        }

        @Override // com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean removeAll(Collection<?> collection) {
            try {
                return super.removeAll((Collection) Preconditions.checkNotNull(collection));
            } catch (UnsupportedOperationException unused) {
                return Sets.removeAllImpl(this, collection.iterator());
            }
        }

        @Override // com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean retainAll(Collection<?> collection) {
            try {
                return super.retainAll((Collection) Preconditions.checkNotNull(collection));
            } catch (UnsupportedOperationException unused) {
                HashSet hashSetNewHashSetWithExpectedSize = Sets.newHashSetWithExpectedSize(collection.size());
                for (Object obj : collection) {
                    if (contains(obj)) {
                        hashSetNewHashSetWithExpectedSize.add(((Map.Entry) obj).getKey());
                    }
                }
                return map().keySet().retainAll(hashSetNewHashSetWithExpectedSize);
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return map().size();
        }
    }

    public interface EntryTransformer<K, V1, V2> {
        V2 transformEntry(@NullableDecl K k2, @NullableDecl V1 v12);
    }

    public static final class FilteredEntryBiMap<K, V> extends FilteredEntryMap<K, V> implements BiMap<K, V> {

        @RetainedWith
        private final BiMap<V, K> inverse;

        public FilteredEntryBiMap(BiMap<K, V> biMap, Predicate<? super Map.Entry<K, V>> predicate) {
            super(biMap, predicate);
            this.inverse = new FilteredEntryBiMap(biMap.inverse(), inversePredicate(predicate), this);
        }

        private static <K, V> Predicate<Map.Entry<V, K>> inversePredicate(final Predicate<? super Map.Entry<K, V>> predicate) {
            return new Predicate<Map.Entry<V, K>>() { // from class: com.google.common.collect.Maps.FilteredEntryBiMap.1
                @Override // com.google.common.base.Predicate
                public boolean apply(Map.Entry<V, K> entry) {
                    return predicate.apply(Maps.immutableEntry(entry.getValue(), entry.getKey()));
                }
            };
        }

        @Override // com.google.common.collect.BiMap
        public V forcePut(@NullableDecl K k2, @NullableDecl V v2) {
            Preconditions.checkArgument(apply(k2, v2));
            return unfiltered().forcePut(k2, v2);
        }

        @Override // com.google.common.collect.BiMap
        public BiMap<V, K> inverse() {
            return this.inverse;
        }

        public BiMap<K, V> unfiltered() {
            return (BiMap) this.unfiltered;
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap, java.util.AbstractMap, java.util.Map
        public Set<V> values() {
            return this.inverse.keySet();
        }

        private FilteredEntryBiMap(BiMap<K, V> biMap, Predicate<? super Map.Entry<K, V>> predicate, BiMap<V, K> biMap2) {
            super(biMap, predicate);
            this.inverse = biMap2;
        }
    }

    public static class FilteredEntryMap<K, V> extends AbstractFilteredMap<K, V> {
        final Set<Map.Entry<K, V>> filteredEntrySet;

        public class KeySet extends KeySet<K, V> {
            public KeySet() {
                super(FilteredEntryMap.this);
            }

            @Override // com.google.common.collect.Maps.KeySet, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean remove(Object obj) {
                if (!FilteredEntryMap.this.containsKey(obj)) {
                    return false;
                }
                FilteredEntryMap.this.unfiltered.remove(obj);
                return true;
            }

            @Override // com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean removeAll(Collection<?> collection) {
                FilteredEntryMap filteredEntryMap = FilteredEntryMap.this;
                return FilteredEntryMap.removeAllKeys(filteredEntryMap.unfiltered, filteredEntryMap.predicate, collection);
            }

            @Override // com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean retainAll(Collection<?> collection) {
                FilteredEntryMap filteredEntryMap = FilteredEntryMap.this;
                return FilteredEntryMap.retainAllKeys(filteredEntryMap.unfiltered, filteredEntryMap.predicate, collection);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public Object[] toArray() {
                return Lists.newArrayList(iterator()).toArray();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public <T> T[] toArray(T[] tArr) {
                return (T[]) Lists.newArrayList(iterator()).toArray(tArr);
            }
        }

        public FilteredEntryMap(Map<K, V> map, Predicate<? super Map.Entry<K, V>> predicate) {
            super(map, predicate);
            this.filteredEntrySet = Sets.filter(map.entrySet(), this.predicate);
        }

        public static <K, V> boolean removeAllKeys(Map<K, V> map, Predicate<? super Map.Entry<K, V>> predicate, Collection<?> collection) {
            Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
            boolean z2 = false;
            while (it.hasNext()) {
                Map.Entry<K, V> next = it.next();
                if (predicate.apply(next) && collection.contains(next.getKey())) {
                    it.remove();
                    z2 = true;
                }
            }
            return z2;
        }

        public static <K, V> boolean retainAllKeys(Map<K, V> map, Predicate<? super Map.Entry<K, V>> predicate, Collection<?> collection) {
            Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
            boolean z2 = false;
            while (it.hasNext()) {
                Map.Entry<K, V> next = it.next();
                if (predicate.apply(next) && !collection.contains(next.getKey())) {
                    it.remove();
                    z2 = true;
                }
            }
            return z2;
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap
        public Set<Map.Entry<K, V>> createEntrySet() {
            return new EntrySet();
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap
        public Set<K> createKeySet() {
            return new KeySet();
        }

        public class EntrySet extends ForwardingSet<Map.Entry<K, V>> {
            private EntrySet() {
            }

            @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Map.Entry<K, V>> iterator() {
                return new TransformedIterator<Map.Entry<K, V>, Map.Entry<K, V>>(FilteredEntryMap.this.filteredEntrySet.iterator()) { // from class: com.google.common.collect.Maps.FilteredEntryMap.EntrySet.1
                    @Override // com.google.common.collect.TransformedIterator
                    public Map.Entry<K, V> transform(final Map.Entry<K, V> entry) {
                        return new ForwardingMapEntry<K, V>() { // from class: com.google.common.collect.Maps.FilteredEntryMap.EntrySet.1.1
                            @Override // com.google.common.collect.ForwardingMapEntry, java.util.Map.Entry
                            public V setValue(V v2) {
                                Preconditions.checkArgument(FilteredEntryMap.this.apply(getKey(), v2));
                                return (V) super.setValue(v2);
                            }

                            @Override // com.google.common.collect.ForwardingMapEntry, com.google.common.collect.ForwardingObject
                            public Map.Entry<K, V> delegate() {
                                return entry;
                            }
                        };
                    }
                };
            }

            @Override // com.google.common.collect.ForwardingSet, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
            public Set<Map.Entry<K, V>> delegate() {
                return FilteredEntryMap.this.filteredEntrySet;
            }
        }
    }

    @GwtIncompatible
    public static class FilteredEntryNavigableMap<K, V> extends AbstractNavigableMap<K, V> {
        private final Predicate<? super Map.Entry<K, V>> entryPredicate;
        private final Map<K, V> filteredDelegate;
        private final NavigableMap<K, V> unfiltered;

        public FilteredEntryNavigableMap(NavigableMap<K, V> navigableMap, Predicate<? super Map.Entry<K, V>> predicate) {
            this.unfiltered = (NavigableMap) Preconditions.checkNotNull(navigableMap);
            this.entryPredicate = predicate;
            this.filteredDelegate = new FilteredEntryMap(navigableMap, predicate);
        }

        @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap, java.util.AbstractMap, java.util.Map
        public void clear() {
            this.filteredDelegate.clear();
        }

        @Override // java.util.SortedMap
        public Comparator<? super K> comparator() {
            return this.unfiltered.comparator();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(@NullableDecl Object obj) {
            return this.filteredDelegate.containsKey(obj);
        }

        @Override // com.google.common.collect.AbstractNavigableMap
        public Iterator<Map.Entry<K, V>> descendingEntryIterator() {
            return Iterators.filter(this.unfiltered.descendingMap().entrySet().iterator(), this.entryPredicate);
        }

        @Override // com.google.common.collect.AbstractNavigableMap, java.util.NavigableMap
        public NavigableMap<K, V> descendingMap() {
            return Maps.filterEntries((NavigableMap) this.unfiltered.descendingMap(), (Predicate) this.entryPredicate);
        }

        @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap
        public Iterator<Map.Entry<K, V>> entryIterator() {
            return Iterators.filter(this.unfiltered.entrySet().iterator(), this.entryPredicate);
        }

        @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap, java.util.AbstractMap, java.util.Map, java.util.SortedMap
        public Set<Map.Entry<K, V>> entrySet() {
            return this.filteredDelegate.entrySet();
        }

        @Override // com.google.common.collect.AbstractNavigableMap, java.util.AbstractMap, java.util.Map
        @NullableDecl
        public V get(@NullableDecl Object obj) {
            return this.filteredDelegate.get(obj);
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> headMap(K k2, boolean z2) {
            return Maps.filterEntries((NavigableMap) this.unfiltered.headMap(k2, z2), (Predicate) this.entryPredicate);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean isEmpty() {
            return !Iterables.any(this.unfiltered.entrySet(), this.entryPredicate);
        }

        @Override // com.google.common.collect.AbstractNavigableMap, java.util.NavigableMap
        public NavigableSet<K> navigableKeySet() {
            return new NavigableKeySet<K, V>(this) { // from class: com.google.common.collect.Maps.FilteredEntryNavigableMap.1
                @Override // com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
                public boolean removeAll(Collection<?> collection) {
                    return FilteredEntryMap.removeAllKeys(FilteredEntryNavigableMap.this.unfiltered, FilteredEntryNavigableMap.this.entryPredicate, collection);
                }

                @Override // com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
                public boolean retainAll(Collection<?> collection) {
                    return FilteredEntryMap.retainAllKeys(FilteredEntryNavigableMap.this.unfiltered, FilteredEntryNavigableMap.this.entryPredicate, collection);
                }
            };
        }

        @Override // com.google.common.collect.AbstractNavigableMap, java.util.NavigableMap
        public Map.Entry<K, V> pollFirstEntry() {
            return (Map.Entry) Iterables.removeFirstMatching(this.unfiltered.entrySet(), this.entryPredicate);
        }

        @Override // com.google.common.collect.AbstractNavigableMap, java.util.NavigableMap
        public Map.Entry<K, V> pollLastEntry() {
            return (Map.Entry) Iterables.removeFirstMatching(this.unfiltered.descendingMap().entrySet(), this.entryPredicate);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V put(K k2, V v2) {
            return this.filteredDelegate.put(k2, v2);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public void putAll(Map<? extends K, ? extends V> map) {
            this.filteredDelegate.putAll(map);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V remove(@NullableDecl Object obj) {
            return this.filteredDelegate.remove(obj);
        }

        @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap, java.util.AbstractMap, java.util.Map
        public int size() {
            return this.filteredDelegate.size();
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> subMap(K k2, boolean z2, K k3, boolean z3) {
            return Maps.filterEntries((NavigableMap) this.unfiltered.subMap(k2, z2, k3, z3), (Predicate) this.entryPredicate);
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> tailMap(K k2, boolean z2) {
            return Maps.filterEntries((NavigableMap) this.unfiltered.tailMap(k2, z2), (Predicate) this.entryPredicate);
        }

        @Override // java.util.AbstractMap, java.util.Map, java.util.SortedMap
        public Collection<V> values() {
            return new FilteredMapValues(this, this.unfiltered, this.entryPredicate);
        }
    }

    public static class FilteredEntrySortedMap<K, V> extends FilteredEntryMap<K, V> implements SortedMap<K, V> {

        public class SortedKeySet extends FilteredEntryMap<K, V>.KeySet implements SortedSet<K> {
            public SortedKeySet() {
                super();
            }

            @Override // java.util.SortedSet
            public Comparator<? super K> comparator() {
                return FilteredEntrySortedMap.this.sortedMap().comparator();
            }

            @Override // java.util.SortedSet
            public K first() {
                return (K) FilteredEntrySortedMap.this.firstKey();
            }

            @Override // java.util.SortedSet
            public SortedSet<K> headSet(K k2) {
                return (SortedSet) FilteredEntrySortedMap.this.headMap(k2).keySet();
            }

            @Override // java.util.SortedSet
            public K last() {
                return (K) FilteredEntrySortedMap.this.lastKey();
            }

            @Override // java.util.SortedSet
            public SortedSet<K> subSet(K k2, K k3) {
                return (SortedSet) FilteredEntrySortedMap.this.subMap(k2, k3).keySet();
            }

            @Override // java.util.SortedSet
            public SortedSet<K> tailSet(K k2) {
                return (SortedSet) FilteredEntrySortedMap.this.tailMap(k2).keySet();
            }
        }

        public FilteredEntrySortedMap(SortedMap<K, V> sortedMap, Predicate<? super Map.Entry<K, V>> predicate) {
            super(sortedMap, predicate);
        }

        @Override // java.util.SortedMap
        public Comparator<? super K> comparator() {
            return sortedMap().comparator();
        }

        @Override // java.util.SortedMap
        public K firstKey() {
            return keySet().iterator().next();
        }

        @Override // java.util.SortedMap
        public SortedMap<K, V> headMap(K k2) {
            return new FilteredEntrySortedMap(sortedMap().headMap(k2), this.predicate);
        }

        @Override // java.util.SortedMap
        public K lastKey() {
            SortedMap<K, V> sortedMap = sortedMap();
            while (true) {
                K kLastKey = sortedMap.lastKey();
                if (apply(kLastKey, this.unfiltered.get(kLastKey))) {
                    return kLastKey;
                }
                sortedMap = sortedMap().headMap(kLastKey);
            }
        }

        public SortedMap<K, V> sortedMap() {
            return (SortedMap) this.unfiltered;
        }

        @Override // java.util.SortedMap
        public SortedMap<K, V> subMap(K k2, K k3) {
            return new FilteredEntrySortedMap(sortedMap().subMap(k2, k3), this.predicate);
        }

        @Override // java.util.SortedMap
        public SortedMap<K, V> tailMap(K k2) {
            return new FilteredEntrySortedMap(sortedMap().tailMap(k2), this.predicate);
        }

        @Override // com.google.common.collect.Maps.FilteredEntryMap, com.google.common.collect.Maps.ViewCachingAbstractMap
        public SortedSet<K> createKeySet() {
            return new SortedKeySet();
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap, java.util.AbstractMap, java.util.Map
        public SortedSet<K> keySet() {
            return (SortedSet) super.keySet();
        }
    }

    public static class FilteredKeyMap<K, V> extends AbstractFilteredMap<K, V> {
        final Predicate<? super K> keyPredicate;

        public FilteredKeyMap(Map<K, V> map, Predicate<? super K> predicate, Predicate<? super Map.Entry<K, V>> predicate2) {
            super(map, predicate2);
            this.keyPredicate = predicate;
        }

        @Override // com.google.common.collect.Maps.AbstractFilteredMap, java.util.AbstractMap, java.util.Map
        public boolean containsKey(Object obj) {
            return this.unfiltered.containsKey(obj) && this.keyPredicate.apply(obj);
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap
        public Set<Map.Entry<K, V>> createEntrySet() {
            return Sets.filter(this.unfiltered.entrySet(), this.predicate);
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap
        public Set<K> createKeySet() {
            return Sets.filter(this.unfiltered.keySet(), this.keyPredicate);
        }
    }

    public static final class FilteredMapValues<K, V> extends Values<K, V> {
        final Predicate<? super Map.Entry<K, V>> predicate;
        final Map<K, V> unfiltered;

        public FilteredMapValues(Map<K, V> map, Map<K, V> map2, Predicate<? super Map.Entry<K, V>> predicate) {
            super(map);
            this.unfiltered = map2;
            this.predicate = predicate;
        }

        @Override // com.google.common.collect.Maps.Values, java.util.AbstractCollection, java.util.Collection
        public boolean remove(Object obj) {
            Iterator<Map.Entry<K, V>> it = this.unfiltered.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<K, V> next = it.next();
                if (this.predicate.apply(next) && Objects.equal(next.getValue(), obj)) {
                    it.remove();
                    return true;
                }
            }
            return false;
        }

        @Override // com.google.common.collect.Maps.Values, java.util.AbstractCollection, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            Iterator<Map.Entry<K, V>> it = this.unfiltered.entrySet().iterator();
            boolean z2 = false;
            while (it.hasNext()) {
                Map.Entry<K, V> next = it.next();
                if (this.predicate.apply(next) && collection.contains(next.getValue())) {
                    it.remove();
                    z2 = true;
                }
            }
            return z2;
        }

        @Override // com.google.common.collect.Maps.Values, java.util.AbstractCollection, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            Iterator<Map.Entry<K, V>> it = this.unfiltered.entrySet().iterator();
            boolean z2 = false;
            while (it.hasNext()) {
                Map.Entry<K, V> next = it.next();
                if (this.predicate.apply(next) && !collection.contains(next.getValue())) {
                    it.remove();
                    z2 = true;
                }
            }
            return z2;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public Object[] toArray() {
            return Lists.newArrayList(iterator()).toArray();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            return (T[]) Lists.newArrayList(iterator()).toArray(tArr);
        }
    }

    public static abstract class IteratorBasedAbstractMap<K, V> extends AbstractMap<K, V> {
        @Override // java.util.AbstractMap, java.util.Map
        public void clear() {
            Iterators.clear(entryIterator());
        }

        public abstract Iterator<Map.Entry<K, V>> entryIterator();

        @Override // java.util.AbstractMap, java.util.Map, java.util.SortedMap
        public Set<Map.Entry<K, V>> entrySet() {
            return new EntrySet<K, V>() { // from class: com.google.common.collect.Maps.IteratorBasedAbstractMap.1
                @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
                public Iterator<Map.Entry<K, V>> iterator() {
                    return IteratorBasedAbstractMap.this.entryIterator();
                }

                @Override // com.google.common.collect.Maps.EntrySet
                public Map<K, V> map() {
                    return IteratorBasedAbstractMap.this;
                }
            };
        }

        @Override // java.util.AbstractMap, java.util.Map
        public abstract int size();
    }

    public static class KeySet<K, V> extends Sets.ImprovedAbstractSet<K> {

        @Weak
        final Map<K, V> map;

        public KeySet(Map<K, V> map) {
            this.map = (Map) Preconditions.checkNotNull(map);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            map().clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return map().containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return map().isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<K> iterator() {
            return Maps.keyIterator(map().entrySet().iterator());
        }

        Map<K, V> map() {
            return this.map;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            if (!contains(obj)) {
                return false;
            }
            map().remove(obj);
            return true;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return map().size();
        }
    }

    public static class MapDifferenceImpl<K, V> implements MapDifference<K, V> {
        final Map<K, MapDifference.ValueDifference<V>> differences;
        final Map<K, V> onBoth;
        final Map<K, V> onlyOnLeft;
        final Map<K, V> onlyOnRight;

        public MapDifferenceImpl(Map<K, V> map, Map<K, V> map2, Map<K, V> map3, Map<K, MapDifference.ValueDifference<V>> map4) {
            this.onlyOnLeft = Maps.unmodifiableMap(map);
            this.onlyOnRight = Maps.unmodifiableMap(map2);
            this.onBoth = Maps.unmodifiableMap(map3);
            this.differences = Maps.unmodifiableMap(map4);
        }

        @Override // com.google.common.collect.MapDifference
        public boolean areEqual() {
            return this.onlyOnLeft.isEmpty() && this.onlyOnRight.isEmpty() && this.differences.isEmpty();
        }

        @Override // com.google.common.collect.MapDifference
        public Map<K, MapDifference.ValueDifference<V>> entriesDiffering() {
            return this.differences;
        }

        @Override // com.google.common.collect.MapDifference
        public Map<K, V> entriesInCommon() {
            return this.onBoth;
        }

        @Override // com.google.common.collect.MapDifference
        public Map<K, V> entriesOnlyOnLeft() {
            return this.onlyOnLeft;
        }

        @Override // com.google.common.collect.MapDifference
        public Map<K, V> entriesOnlyOnRight() {
            return this.onlyOnRight;
        }

        @Override // com.google.common.collect.MapDifference
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof MapDifference)) {
                return false;
            }
            MapDifference mapDifference = (MapDifference) obj;
            return entriesOnlyOnLeft().equals(mapDifference.entriesOnlyOnLeft()) && entriesOnlyOnRight().equals(mapDifference.entriesOnlyOnRight()) && entriesInCommon().equals(mapDifference.entriesInCommon()) && entriesDiffering().equals(mapDifference.entriesDiffering());
        }

        @Override // com.google.common.collect.MapDifference
        public int hashCode() {
            return Objects.hashCode(entriesOnlyOnLeft(), entriesOnlyOnRight(), entriesInCommon(), entriesDiffering());
        }

        public String toString() {
            if (areEqual()) {
                return "equal";
            }
            StringBuilder sb = new StringBuilder("not equal");
            if (!this.onlyOnLeft.isEmpty()) {
                sb.append(": only on left=");
                sb.append(this.onlyOnLeft);
            }
            if (!this.onlyOnRight.isEmpty()) {
                sb.append(": only on right=");
                sb.append(this.onlyOnRight);
            }
            if (!this.differences.isEmpty()) {
                sb.append(": value differences=");
                sb.append(this.differences);
            }
            return sb.toString();
        }
    }

    @GwtIncompatible
    public static final class NavigableAsMapView<K, V> extends AbstractNavigableMap<K, V> {
        private final Function<? super K, V> function;
        private final NavigableSet<K> set;

        public NavigableAsMapView(NavigableSet<K> navigableSet, Function<? super K, V> function) {
            this.set = (NavigableSet) Preconditions.checkNotNull(navigableSet);
            this.function = (Function) Preconditions.checkNotNull(function);
        }

        @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap, java.util.AbstractMap, java.util.Map
        public void clear() {
            this.set.clear();
        }

        @Override // java.util.SortedMap
        public Comparator<? super K> comparator() {
            return this.set.comparator();
        }

        @Override // com.google.common.collect.AbstractNavigableMap
        public Iterator<Map.Entry<K, V>> descendingEntryIterator() {
            return descendingMap().entrySet().iterator();
        }

        @Override // com.google.common.collect.AbstractNavigableMap, java.util.NavigableMap
        public NavigableMap<K, V> descendingMap() {
            return Maps.asMap((NavigableSet) this.set.descendingSet(), (Function) this.function);
        }

        @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap
        public Iterator<Map.Entry<K, V>> entryIterator() {
            return Maps.asMapEntryIterator(this.set, this.function);
        }

        @Override // com.google.common.collect.AbstractNavigableMap, java.util.AbstractMap, java.util.Map
        @NullableDecl
        public V get(@NullableDecl Object obj) {
            if (Collections2.safeContains(this.set, obj)) {
                return this.function.apply(obj);
            }
            return null;
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> headMap(K k2, boolean z2) {
            return Maps.asMap((NavigableSet) this.set.headSet(k2, z2), (Function) this.function);
        }

        @Override // com.google.common.collect.AbstractNavigableMap, java.util.NavigableMap
        public NavigableSet<K> navigableKeySet() {
            return Maps.removeOnlyNavigableSet(this.set);
        }

        @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap, java.util.AbstractMap, java.util.Map
        public int size() {
            return this.set.size();
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> subMap(K k2, boolean z2, K k3, boolean z3) {
            return Maps.asMap((NavigableSet) this.set.subSet(k2, z2, k3, z3), (Function) this.function);
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> tailMap(K k2, boolean z2) {
            return Maps.asMap((NavigableSet) this.set.tailSet(k2, z2), (Function) this.function);
        }
    }

    @GwtIncompatible
    public static class NavigableKeySet<K, V> extends SortedKeySet<K, V> implements NavigableSet<K> {
        public NavigableKeySet(NavigableMap<K, V> navigableMap) {
            super(navigableMap);
        }

        @Override // java.util.NavigableSet
        public K ceiling(K k2) {
            return map().ceilingKey(k2);
        }

        @Override // java.util.NavigableSet
        public Iterator<K> descendingIterator() {
            return descendingSet().iterator();
        }

        @Override // java.util.NavigableSet
        public NavigableSet<K> descendingSet() {
            return map().descendingKeySet();
        }

        @Override // java.util.NavigableSet
        public K floor(K k2) {
            return map().floorKey(k2);
        }

        @Override // java.util.NavigableSet
        public NavigableSet<K> headSet(K k2, boolean z2) {
            return map().headMap(k2, z2).navigableKeySet();
        }

        @Override // java.util.NavigableSet
        public K higher(K k2) {
            return map().higherKey(k2);
        }

        @Override // java.util.NavigableSet
        public K lower(K k2) {
            return map().lowerKey(k2);
        }

        @Override // java.util.NavigableSet
        public K pollFirst() {
            return (K) Maps.keyOrNull(map().pollFirstEntry());
        }

        @Override // java.util.NavigableSet
        public K pollLast() {
            return (K) Maps.keyOrNull(map().pollLastEntry());
        }

        @Override // java.util.NavigableSet
        public NavigableSet<K> subSet(K k2, boolean z2, K k3, boolean z3) {
            return map().subMap(k2, z2, k3, z3).navigableKeySet();
        }

        @Override // java.util.NavigableSet
        public NavigableSet<K> tailSet(K k2, boolean z2) {
            return map().tailMap(k2, z2).navigableKeySet();
        }

        @Override // com.google.common.collect.Maps.SortedKeySet, java.util.SortedSet, java.util.NavigableSet
        public SortedSet<K> headSet(K k2) {
            return headSet(k2, false);
        }

        @Override // com.google.common.collect.Maps.SortedKeySet, java.util.SortedSet, java.util.NavigableSet
        public SortedSet<K> subSet(K k2, K k3) {
            return subSet(k2, true, k3, false);
        }

        @Override // com.google.common.collect.Maps.SortedKeySet, java.util.SortedSet, java.util.NavigableSet
        public SortedSet<K> tailSet(K k2) {
            return tailSet(k2, true);
        }

        @Override // com.google.common.collect.Maps.SortedKeySet, com.google.common.collect.Maps.KeySet
        public NavigableMap<K, V> map() {
            return (NavigableMap) this.map;
        }
    }

    public static class SortedAsMapView<K, V> extends AsMapView<K, V> implements SortedMap<K, V> {
        public SortedAsMapView(SortedSet<K> sortedSet, Function<? super K, V> function) {
            super(sortedSet, function);
        }

        @Override // java.util.SortedMap
        public Comparator<? super K> comparator() {
            return backingSet().comparator();
        }

        @Override // java.util.SortedMap
        public K firstKey() {
            return backingSet().first();
        }

        @Override // java.util.SortedMap
        public SortedMap<K, V> headMap(K k2) {
            return Maps.asMap((SortedSet) backingSet().headSet(k2), (Function) this.function);
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap, java.util.AbstractMap, java.util.Map
        public Set<K> keySet() {
            return Maps.removeOnlySortedSet(backingSet());
        }

        @Override // java.util.SortedMap
        public K lastKey() {
            return backingSet().last();
        }

        @Override // java.util.SortedMap
        public SortedMap<K, V> subMap(K k2, K k3) {
            return Maps.asMap((SortedSet) backingSet().subSet(k2, k3), (Function) this.function);
        }

        @Override // java.util.SortedMap
        public SortedMap<K, V> tailMap(K k2) {
            return Maps.asMap((SortedSet) backingSet().tailSet(k2), (Function) this.function);
        }

        @Override // com.google.common.collect.Maps.AsMapView
        public SortedSet<K> backingSet() {
            return (SortedSet) super.backingSet();
        }
    }

    public static class SortedKeySet<K, V> extends KeySet<K, V> implements SortedSet<K> {
        public SortedKeySet(SortedMap<K, V> sortedMap) {
            super(sortedMap);
        }

        @Override // java.util.SortedSet
        public Comparator<? super K> comparator() {
            return map().comparator();
        }

        @Override // java.util.SortedSet
        public K first() {
            return map().firstKey();
        }

        public SortedSet<K> headSet(K k2) {
            return new SortedKeySet(map().headMap(k2));
        }

        @Override // java.util.SortedSet
        public K last() {
            return map().lastKey();
        }

        public SortedSet<K> subSet(K k2, K k3) {
            return new SortedKeySet(map().subMap(k2, k3));
        }

        public SortedSet<K> tailSet(K k2) {
            return new SortedKeySet(map().tailMap(k2));
        }

        @Override // com.google.common.collect.Maps.KeySet
        public SortedMap<K, V> map() {
            return (SortedMap) super.map();
        }
    }

    public static class SortedMapDifferenceImpl<K, V> extends MapDifferenceImpl<K, V> implements SortedMapDifference<K, V> {
        public SortedMapDifferenceImpl(SortedMap<K, V> sortedMap, SortedMap<K, V> sortedMap2, SortedMap<K, V> sortedMap3, SortedMap<K, MapDifference.ValueDifference<V>> sortedMap4) {
            super(sortedMap, sortedMap2, sortedMap3, sortedMap4);
        }

        @Override // com.google.common.collect.Maps.MapDifferenceImpl, com.google.common.collect.MapDifference
        public SortedMap<K, MapDifference.ValueDifference<V>> entriesDiffering() {
            return (SortedMap) super.entriesDiffering();
        }

        @Override // com.google.common.collect.Maps.MapDifferenceImpl, com.google.common.collect.MapDifference
        public SortedMap<K, V> entriesInCommon() {
            return (SortedMap) super.entriesInCommon();
        }

        @Override // com.google.common.collect.Maps.MapDifferenceImpl, com.google.common.collect.MapDifference
        public SortedMap<K, V> entriesOnlyOnLeft() {
            return (SortedMap) super.entriesOnlyOnLeft();
        }

        @Override // com.google.common.collect.Maps.MapDifferenceImpl, com.google.common.collect.MapDifference
        public SortedMap<K, V> entriesOnlyOnRight() {
            return (SortedMap) super.entriesOnlyOnRight();
        }
    }

    public static class TransformedEntriesMap<K, V1, V2> extends IteratorBasedAbstractMap<K, V2> {
        final Map<K, V1> fromMap;
        final EntryTransformer<? super K, ? super V1, V2> transformer;

        public TransformedEntriesMap(Map<K, V1> map, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
            this.fromMap = (Map) Preconditions.checkNotNull(map);
            this.transformer = (EntryTransformer) Preconditions.checkNotNull(entryTransformer);
        }

        @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap, java.util.AbstractMap, java.util.Map
        public void clear() {
            this.fromMap.clear();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(Object obj) {
            return this.fromMap.containsKey(obj);
        }

        @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap
        public Iterator<Map.Entry<K, V2>> entryIterator() {
            return Iterators.transform(this.fromMap.entrySet().iterator(), Maps.asEntryToEntryFunction(this.transformer));
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V2 get(Object obj) {
            V1 v12 = this.fromMap.get(obj);
            if (v12 != null || this.fromMap.containsKey(obj)) {
                return this.transformer.transformEntry(obj, v12);
            }
            return null;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<K> keySet() {
            return this.fromMap.keySet();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V2 remove(Object obj) {
            if (this.fromMap.containsKey(obj)) {
                return this.transformer.transformEntry(obj, this.fromMap.remove(obj));
            }
            return null;
        }

        @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap, java.util.AbstractMap, java.util.Map
        public int size() {
            return this.fromMap.size();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Collection<V2> values() {
            return new Values(this);
        }
    }

    @GwtIncompatible
    public static class TransformedEntriesNavigableMap<K, V1, V2> extends TransformedEntriesSortedMap<K, V1, V2> implements NavigableMap<K, V2> {
        public TransformedEntriesNavigableMap(NavigableMap<K, V1> navigableMap, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
            super(navigableMap, entryTransformer);
        }

        @NullableDecl
        private Map.Entry<K, V2> transformEntry(@NullableDecl Map.Entry<K, V1> entry) {
            if (entry == null) {
                return null;
            }
            return Maps.transformEntry(this.transformer, entry);
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V2> ceilingEntry(K k2) {
            return transformEntry(fromMap().ceilingEntry(k2));
        }

        @Override // java.util.NavigableMap
        public K ceilingKey(K k2) {
            return fromMap().ceilingKey(k2);
        }

        @Override // java.util.NavigableMap
        public NavigableSet<K> descendingKeySet() {
            return fromMap().descendingKeySet();
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V2> descendingMap() {
            return Maps.transformEntries((NavigableMap) fromMap().descendingMap(), (EntryTransformer) this.transformer);
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V2> firstEntry() {
            return transformEntry(fromMap().firstEntry());
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V2> floorEntry(K k2) {
            return transformEntry(fromMap().floorEntry(k2));
        }

        @Override // java.util.NavigableMap
        public K floorKey(K k2) {
            return fromMap().floorKey(k2);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.Maps.TransformedEntriesSortedMap, java.util.SortedMap, java.util.NavigableMap
        public /* bridge */ /* synthetic */ SortedMap headMap(Object obj) {
            return headMap((TransformedEntriesNavigableMap<K, V1, V2>) obj);
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V2> higherEntry(K k2) {
            return transformEntry(fromMap().higherEntry(k2));
        }

        @Override // java.util.NavigableMap
        public K higherKey(K k2) {
            return fromMap().higherKey(k2);
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V2> lastEntry() {
            return transformEntry(fromMap().lastEntry());
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V2> lowerEntry(K k2) {
            return transformEntry(fromMap().lowerEntry(k2));
        }

        @Override // java.util.NavigableMap
        public K lowerKey(K k2) {
            return fromMap().lowerKey(k2);
        }

        @Override // java.util.NavigableMap
        public NavigableSet<K> navigableKeySet() {
            return fromMap().navigableKeySet();
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V2> pollFirstEntry() {
            return transformEntry(fromMap().pollFirstEntry());
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V2> pollLastEntry() {
            return transformEntry(fromMap().pollLastEntry());
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.Maps.TransformedEntriesSortedMap, java.util.SortedMap, java.util.NavigableMap
        public /* bridge */ /* synthetic */ SortedMap tailMap(Object obj) {
            return tailMap((TransformedEntriesNavigableMap<K, V1, V2>) obj);
        }

        @Override // com.google.common.collect.Maps.TransformedEntriesSortedMap
        public NavigableMap<K, V1> fromMap() {
            return (NavigableMap) super.fromMap();
        }

        @Override // com.google.common.collect.Maps.TransformedEntriesSortedMap, java.util.SortedMap, java.util.NavigableMap
        public NavigableMap<K, V2> headMap(K k2) {
            return headMap(k2, false);
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V2> subMap(K k2, boolean z2, K k3, boolean z3) {
            return Maps.transformEntries((NavigableMap) fromMap().subMap(k2, z2, k3, z3), (EntryTransformer) this.transformer);
        }

        @Override // com.google.common.collect.Maps.TransformedEntriesSortedMap, java.util.SortedMap, java.util.NavigableMap
        public NavigableMap<K, V2> tailMap(K k2) {
            return tailMap(k2, true);
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V2> headMap(K k2, boolean z2) {
            return Maps.transformEntries((NavigableMap) fromMap().headMap(k2, z2), (EntryTransformer) this.transformer);
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V2> tailMap(K k2, boolean z2) {
            return Maps.transformEntries((NavigableMap) fromMap().tailMap(k2, z2), (EntryTransformer) this.transformer);
        }

        @Override // com.google.common.collect.Maps.TransformedEntriesSortedMap, java.util.SortedMap, java.util.NavigableMap
        public NavigableMap<K, V2> subMap(K k2, K k3) {
            return subMap(k2, true, k3, false);
        }
    }

    public static class TransformedEntriesSortedMap<K, V1, V2> extends TransformedEntriesMap<K, V1, V2> implements SortedMap<K, V2> {
        public TransformedEntriesSortedMap(SortedMap<K, V1> sortedMap, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
            super(sortedMap, entryTransformer);
        }

        @Override // java.util.SortedMap
        public Comparator<? super K> comparator() {
            return fromMap().comparator();
        }

        @Override // java.util.SortedMap
        public K firstKey() {
            return fromMap().firstKey();
        }

        public SortedMap<K, V1> fromMap() {
            return (SortedMap) this.fromMap;
        }

        public SortedMap<K, V2> headMap(K k2) {
            return Maps.transformEntries((SortedMap) fromMap().headMap(k2), (EntryTransformer) this.transformer);
        }

        @Override // java.util.SortedMap
        public K lastKey() {
            return fromMap().lastKey();
        }

        public SortedMap<K, V2> subMap(K k2, K k3) {
            return Maps.transformEntries((SortedMap) fromMap().subMap(k2, k3), (EntryTransformer) this.transformer);
        }

        public SortedMap<K, V2> tailMap(K k2) {
            return Maps.transformEntries((SortedMap) fromMap().tailMap(k2), (EntryTransformer) this.transformer);
        }
    }

    public static class UnmodifiableBiMap<K, V> extends ForwardingMap<K, V> implements BiMap<K, V>, Serializable {
        private static final long serialVersionUID = 0;
        final BiMap<? extends K, ? extends V> delegate;

        @RetainedWith
        @MonotonicNonNullDecl
        BiMap<V, K> inverse;
        final Map<K, V> unmodifiableMap;

        @MonotonicNonNullDecl
        transient Set<V> values;

        public UnmodifiableBiMap(BiMap<? extends K, ? extends V> biMap, @NullableDecl BiMap<V, K> biMap2) {
            this.unmodifiableMap = Collections.unmodifiableMap(biMap);
            this.delegate = biMap;
            this.inverse = biMap2;
        }

        @Override // com.google.common.collect.BiMap
        public V forcePut(K k2, V v2) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.BiMap
        public BiMap<V, K> inverse() {
            BiMap<V, K> biMap = this.inverse;
            if (biMap != null) {
                return biMap;
            }
            UnmodifiableBiMap unmodifiableBiMap = new UnmodifiableBiMap(this.delegate.inverse(), this);
            this.inverse = unmodifiableBiMap;
            return unmodifiableBiMap;
        }

        @Override // com.google.common.collect.ForwardingMap, com.google.common.collect.ForwardingObject
        public Map<K, V> delegate() {
            return this.unmodifiableMap;
        }

        @Override // com.google.common.collect.ForwardingMap, java.util.Map
        public Set<V> values() {
            Set<V> set = this.values;
            if (set != null) {
                return set;
            }
            Set<V> setUnmodifiableSet = Collections.unmodifiableSet(this.delegate.values());
            this.values = setUnmodifiableSet;
            return setUnmodifiableSet;
        }
    }

    public static class UnmodifiableEntries<K, V> extends ForwardingCollection<Map.Entry<K, V>> {
        private final Collection<Map.Entry<K, V>> entries;

        public UnmodifiableEntries(Collection<Map.Entry<K, V>> collection) {
            this.entries = collection;
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<K, V>> iterator() {
            return Maps.unmodifiableEntryIterator(this.entries.iterator());
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public Object[] toArray() {
            return standardToArray();
        }

        @Override // com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
        public Collection<Map.Entry<K, V>> delegate() {
            return this.entries;
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public <T> T[] toArray(T[] tArr) {
            return (T[]) standardToArray(tArr);
        }
    }

    public static class UnmodifiableEntrySet<K, V> extends UnmodifiableEntries<K, V> implements Set<Map.Entry<K, V>> {
        public UnmodifiableEntrySet(Set<Map.Entry<K, V>> set) {
            super(set);
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

    @GwtIncompatible
    public static class UnmodifiableNavigableMap<K, V> extends ForwardingSortedMap<K, V> implements NavigableMap<K, V>, Serializable {
        private final NavigableMap<K, ? extends V> delegate;

        @MonotonicNonNullDecl
        private transient UnmodifiableNavigableMap<K, V> descendingMap;

        public UnmodifiableNavigableMap(NavigableMap<K, ? extends V> navigableMap) {
            this.delegate = navigableMap;
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> ceilingEntry(K k2) {
            return Maps.unmodifiableOrNull(this.delegate.ceilingEntry(k2));
        }

        @Override // java.util.NavigableMap
        public K ceilingKey(K k2) {
            return this.delegate.ceilingKey(k2);
        }

        @Override // java.util.NavigableMap
        public NavigableSet<K> descendingKeySet() {
            return Sets.unmodifiableNavigableSet(this.delegate.descendingKeySet());
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> descendingMap() {
            UnmodifiableNavigableMap<K, V> unmodifiableNavigableMap = this.descendingMap;
            if (unmodifiableNavigableMap != null) {
                return unmodifiableNavigableMap;
            }
            UnmodifiableNavigableMap<K, V> unmodifiableNavigableMap2 = new UnmodifiableNavigableMap<>(this.delegate.descendingMap(), this);
            this.descendingMap = unmodifiableNavigableMap2;
            return unmodifiableNavigableMap2;
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> firstEntry() {
            return Maps.unmodifiableOrNull(this.delegate.firstEntry());
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> floorEntry(K k2) {
            return Maps.unmodifiableOrNull(this.delegate.floorEntry(k2));
        }

        @Override // java.util.NavigableMap
        public K floorKey(K k2) {
            return this.delegate.floorKey(k2);
        }

        @Override // com.google.common.collect.ForwardingSortedMap, java.util.SortedMap
        public SortedMap<K, V> headMap(K k2) {
            return headMap(k2, false);
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> higherEntry(K k2) {
            return Maps.unmodifiableOrNull(this.delegate.higherEntry(k2));
        }

        @Override // java.util.NavigableMap
        public K higherKey(K k2) {
            return this.delegate.higherKey(k2);
        }

        @Override // com.google.common.collect.ForwardingMap, java.util.Map
        public Set<K> keySet() {
            return navigableKeySet();
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> lastEntry() {
            return Maps.unmodifiableOrNull(this.delegate.lastEntry());
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> lowerEntry(K k2) {
            return Maps.unmodifiableOrNull(this.delegate.lowerEntry(k2));
        }

        @Override // java.util.NavigableMap
        public K lowerKey(K k2) {
            return this.delegate.lowerKey(k2);
        }

        @Override // java.util.NavigableMap
        public NavigableSet<K> navigableKeySet() {
            return Sets.unmodifiableNavigableSet(this.delegate.navigableKeySet());
        }

        @Override // java.util.NavigableMap
        public final Map.Entry<K, V> pollFirstEntry() {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.NavigableMap
        public final Map.Entry<K, V> pollLastEntry() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingSortedMap, java.util.SortedMap
        public SortedMap<K, V> subMap(K k2, K k3) {
            return subMap(k2, true, k3, false);
        }

        @Override // com.google.common.collect.ForwardingSortedMap, java.util.SortedMap
        public SortedMap<K, V> tailMap(K k2) {
            return tailMap(k2, true);
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> headMap(K k2, boolean z2) {
            return Maps.unmodifiableNavigableMap(this.delegate.headMap(k2, z2));
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> subMap(K k2, boolean z2, K k3, boolean z3) {
            return Maps.unmodifiableNavigableMap(this.delegate.subMap(k2, z2, k3, z3));
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> tailMap(K k2, boolean z2) {
            return Maps.unmodifiableNavigableMap(this.delegate.tailMap(k2, z2));
        }

        public UnmodifiableNavigableMap(NavigableMap<K, ? extends V> navigableMap, UnmodifiableNavigableMap<K, V> unmodifiableNavigableMap) {
            this.delegate = navigableMap;
            this.descendingMap = unmodifiableNavigableMap;
        }

        @Override // com.google.common.collect.ForwardingSortedMap, com.google.common.collect.ForwardingMap, com.google.common.collect.ForwardingObject
        public SortedMap<K, V> delegate() {
            return Collections.unmodifiableSortedMap(this.delegate);
        }
    }

    public static class ValueDifferenceImpl<V> implements MapDifference.ValueDifference<V> {

        @NullableDecl
        private final V left;

        @NullableDecl
        private final V right;

        private ValueDifferenceImpl(@NullableDecl V v2, @NullableDecl V v3) {
            this.left = v2;
            this.right = v3;
        }

        public static <V> MapDifference.ValueDifference<V> create(@NullableDecl V v2, @NullableDecl V v3) {
            return new ValueDifferenceImpl(v2, v3);
        }

        @Override // com.google.common.collect.MapDifference.ValueDifference
        public boolean equals(@NullableDecl Object obj) {
            if (!(obj instanceof MapDifference.ValueDifference)) {
                return false;
            }
            MapDifference.ValueDifference valueDifference = (MapDifference.ValueDifference) obj;
            return Objects.equal(this.left, valueDifference.leftValue()) && Objects.equal(this.right, valueDifference.rightValue());
        }

        @Override // com.google.common.collect.MapDifference.ValueDifference
        public int hashCode() {
            return Objects.hashCode(this.left, this.right);
        }

        @Override // com.google.common.collect.MapDifference.ValueDifference
        public V leftValue() {
            return this.left;
        }

        @Override // com.google.common.collect.MapDifference.ValueDifference
        public V rightValue() {
            return this.right;
        }

        public String toString() {
            return "(" + this.left + ", " + this.right + ")";
        }
    }

    public static class Values<K, V> extends AbstractCollection<V> {

        @Weak
        final Map<K, V> map;

        public Values(Map<K, V> map) {
            this.map = (Map) Preconditions.checkNotNull(map);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            map().clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(@NullableDecl Object obj) {
            return map().containsValue(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return map().isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            return Maps.valueIterator(map().entrySet().iterator());
        }

        public final Map<K, V> map() {
            return this.map;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean remove(Object obj) {
            try {
                return super.remove(obj);
            } catch (UnsupportedOperationException unused) {
                for (Map.Entry<K, V> entry : map().entrySet()) {
                    if (Objects.equal(obj, entry.getValue())) {
                        map().remove(entry.getKey());
                        return true;
                    }
                }
                return false;
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            try {
                return super.removeAll((Collection) Preconditions.checkNotNull(collection));
            } catch (UnsupportedOperationException unused) {
                HashSet hashSetNewHashSet = Sets.newHashSet();
                for (Map.Entry<K, V> entry : map().entrySet()) {
                    if (collection.contains(entry.getValue())) {
                        hashSetNewHashSet.add(entry.getKey());
                    }
                }
                return map().keySet().removeAll(hashSetNewHashSet);
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            try {
                return super.retainAll((Collection) Preconditions.checkNotNull(collection));
            } catch (UnsupportedOperationException unused) {
                HashSet hashSetNewHashSet = Sets.newHashSet();
                for (Map.Entry<K, V> entry : map().entrySet()) {
                    if (collection.contains(entry.getValue())) {
                        hashSetNewHashSet.add(entry.getKey());
                    }
                }
                return map().keySet().retainAll(hashSetNewHashSet);
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return map().size();
        }
    }

    @GwtCompatible
    public static abstract class ViewCachingAbstractMap<K, V> extends AbstractMap<K, V> {

        @MonotonicNonNullDecl
        private transient Set<Map.Entry<K, V>> entrySet;

        @MonotonicNonNullDecl
        private transient Set<K> keySet;

        @MonotonicNonNullDecl
        private transient Collection<V> values;

        public abstract Set<Map.Entry<K, V>> createEntrySet();

        public Set<K> createKeySet() {
            return new KeySet(this);
        }

        public Collection<V> createValues() {
            return new Values(this);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<Map.Entry<K, V>> entrySet() {
            Set<Map.Entry<K, V>> set = this.entrySet;
            if (set != null) {
                return set;
            }
            Set<Map.Entry<K, V>> setCreateEntrySet = createEntrySet();
            this.entrySet = setCreateEntrySet;
            return setCreateEntrySet;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<K> keySet() {
            Set<K> set = this.keySet;
            if (set != null) {
                return set;
            }
            Set<K> setCreateKeySet = createKeySet();
            this.keySet = setCreateKeySet;
            return setCreateKeySet;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Collection<V> values() {
            Collection<V> collection = this.values;
            if (collection != null) {
                return collection;
            }
            Collection<V> collectionCreateValues = createValues();
            this.values = collectionCreateValues;
            return collectionCreateValues;
        }
    }

    private Maps() {
    }

    public static <A, B> Converter<A, B> asConverter(BiMap<A, B> biMap) {
        return new BiMapConverter(biMap);
    }

    public static <K, V1, V2> Function<Map.Entry<K, V1>, Map.Entry<K, V2>> asEntryToEntryFunction(final EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
        Preconditions.checkNotNull(entryTransformer);
        return new Function<Map.Entry<K, V1>, Map.Entry<K, V2>>() { // from class: com.google.common.collect.Maps.13
            @Override // com.google.common.base.Function
            public Map.Entry<K, V2> apply(Map.Entry<K, V1> entry) {
                return Maps.transformEntry(entryTransformer, entry);
            }
        };
    }

    public static <K, V1, V2> Function<Map.Entry<K, V1>, V2> asEntryToValueFunction(final EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
        Preconditions.checkNotNull(entryTransformer);
        return new Function<Map.Entry<K, V1>, V2>() { // from class: com.google.common.collect.Maps.11
            @Override // com.google.common.base.Function
            public V2 apply(Map.Entry<K, V1> entry) {
                return (V2) entryTransformer.transformEntry(entry.getKey(), entry.getValue());
            }
        };
    }

    public static <K, V1, V2> EntryTransformer<K, V1, V2> asEntryTransformer(final Function<? super V1, V2> function) {
        Preconditions.checkNotNull(function);
        return new EntryTransformer<K, V1, V2>() { // from class: com.google.common.collect.Maps.9
            @Override // com.google.common.collect.Maps.EntryTransformer
            public V2 transformEntry(K k2, V1 v12) {
                return (V2) function.apply(v12);
            }
        };
    }

    public static <K, V> Map<K, V> asMap(Set<K> set, Function<? super K, V> function) {
        return new AsMapView(set, function);
    }

    public static <K, V> Iterator<Map.Entry<K, V>> asMapEntryIterator(Set<K> set, final Function<? super K, V> function) {
        return new TransformedIterator<K, Map.Entry<K, V>>(set.iterator()) { // from class: com.google.common.collect.Maps.3
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.TransformedIterator
            public /* bridge */ /* synthetic */ Object transform(Object obj) {
                return transform((AnonymousClass3<K, V>) obj);
            }

            @Override // com.google.common.collect.TransformedIterator
            public Map.Entry<K, V> transform(K k2) {
                return Maps.immutableEntry(k2, function.apply(k2));
            }
        };
    }

    public static <K, V1, V2> Function<V1, V2> asValueToValueFunction(final EntryTransformer<? super K, V1, V2> entryTransformer, final K k2) {
        Preconditions.checkNotNull(entryTransformer);
        return new Function<V1, V2>() { // from class: com.google.common.collect.Maps.10
            @Override // com.google.common.base.Function
            public V2 apply(@NullableDecl V1 v12) {
                return (V2) entryTransformer.transformEntry(k2, v12);
            }
        };
    }

    public static int capacity(int i2) {
        if (i2 < 3) {
            CollectPreconditions.checkNonnegative(i2, "expectedSize");
            return i2 + 1;
        }
        if (i2 < 1073741824) {
            return (int) ((i2 / 0.75f) + 1.0f);
        }
        return Integer.MAX_VALUE;
    }

    public static <K, V> boolean containsEntryImpl(Collection<Map.Entry<K, V>> collection, Object obj) {
        if (obj instanceof Map.Entry) {
            return collection.contains(unmodifiableEntry((Map.Entry) obj));
        }
        return false;
    }

    public static boolean containsKeyImpl(Map<?, ?> map, @NullableDecl Object obj) {
        return Iterators.contains(keyIterator(map.entrySet().iterator()), obj);
    }

    public static boolean containsValueImpl(Map<?, ?> map, @NullableDecl Object obj) {
        return Iterators.contains(valueIterator(map.entrySet().iterator()), obj);
    }

    public static <K, V> MapDifference<K, V> difference(Map<? extends K, ? extends V> map, Map<? extends K, ? extends V> map2) {
        return map instanceof SortedMap ? difference((SortedMap) map, (Map) map2) : difference(map, map2, Equivalence.equals());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <K, V> void doDifference(Map<? extends K, ? extends V> map, Map<? extends K, ? extends V> map2, Equivalence<? super V> equivalence, Map<K, V> map3, Map<K, V> map4, Map<K, V> map5, Map<K, MapDifference.ValueDifference<V>> map6) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            if (map2.containsKey(key)) {
                V vRemove = map4.remove(key);
                if (equivalence.equivalent(value, vRemove)) {
                    map5.put(key, value);
                } else {
                    map6.put(key, ValueDifferenceImpl.create(value, vRemove));
                }
            } else {
                map3.put(key, value);
            }
        }
    }

    public static boolean equalsImpl(Map<?, ?> map, Object obj) {
        if (map == obj) {
            return true;
        }
        if (obj instanceof Map) {
            return map.entrySet().equals(((Map) obj).entrySet());
        }
        return false;
    }

    public static <K, V> Map<K, V> filterEntries(Map<K, V> map, Predicate<? super Map.Entry<K, V>> predicate) {
        Preconditions.checkNotNull(predicate);
        return map instanceof AbstractFilteredMap ? filterFiltered((AbstractFilteredMap) map, predicate) : new FilteredEntryMap((Map) Preconditions.checkNotNull(map), predicate);
    }

    private static <K, V> Map<K, V> filterFiltered(AbstractFilteredMap<K, V> abstractFilteredMap, Predicate<? super Map.Entry<K, V>> predicate) {
        return new FilteredEntryMap(abstractFilteredMap.unfiltered, Predicates.and(abstractFilteredMap.predicate, predicate));
    }

    public static <K, V> Map<K, V> filterKeys(Map<K, V> map, Predicate<? super K> predicate) {
        Preconditions.checkNotNull(predicate);
        Predicate predicateKeyPredicateOnEntries = keyPredicateOnEntries(predicate);
        return map instanceof AbstractFilteredMap ? filterFiltered((AbstractFilteredMap) map, predicateKeyPredicateOnEntries) : new FilteredKeyMap((Map) Preconditions.checkNotNull(map), predicate, predicateKeyPredicateOnEntries);
    }

    public static <K, V> Map<K, V> filterValues(Map<K, V> map, Predicate<? super V> predicate) {
        return filterEntries(map, valuePredicateOnEntries(predicate));
    }

    @GwtIncompatible
    public static ImmutableMap<String, String> fromProperties(Properties properties) {
        ImmutableMap.Builder builder = ImmutableMap.builder();
        Enumeration<?> enumerationPropertyNames = properties.propertyNames();
        while (enumerationPropertyNames.hasMoreElements()) {
            String str = (String) enumerationPropertyNames.nextElement();
            builder.put(str, properties.getProperty(str));
        }
        return builder.build();
    }

    @GwtCompatible(serializable = true)
    public static <K, V> Map.Entry<K, V> immutableEntry(@NullableDecl K k2, @NullableDecl V v2) {
        return new ImmutableEntry(k2, v2);
    }

    @GwtCompatible(serializable = true)
    public static <K extends Enum<K>, V> ImmutableMap<K, V> immutableEnumMap(Map<K, ? extends V> map) {
        if (map instanceof ImmutableEnumMap) {
            return (ImmutableEnumMap) map;
        }
        Iterator<Map.Entry<K, ? extends V>> it = map.entrySet().iterator();
        if (!it.hasNext()) {
            return ImmutableMap.of();
        }
        Map.Entry<K, ? extends V> next = it.next();
        K key = next.getKey();
        V value = next.getValue();
        CollectPreconditions.checkEntryNotNull(key, value);
        EnumMap enumMap = new EnumMap(key.getDeclaringClass());
        enumMap.put((EnumMap) key, (K) value);
        while (it.hasNext()) {
            Map.Entry<K, ? extends V> next2 = it.next();
            K key2 = next2.getKey();
            V value2 = next2.getValue();
            CollectPreconditions.checkEntryNotNull(key2, value2);
            enumMap.put((EnumMap) key2, (K) value2);
        }
        return ImmutableEnumMap.asImmutable(enumMap);
    }

    public static <E> ImmutableMap<E, Integer> indexMap(Collection<E> collection) {
        ImmutableMap.Builder builder = new ImmutableMap.Builder(collection.size());
        Iterator<E> it = collection.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            builder.put(it.next(), Integer.valueOf(i2));
            i2++;
        }
        return builder.build();
    }

    public static <K> Function<Map.Entry<K, ?>, K> keyFunction() {
        return EntryFunction.KEY;
    }

    public static <K, V> Iterator<K> keyIterator(Iterator<Map.Entry<K, V>> it) {
        return new TransformedIterator<Map.Entry<K, V>, K>(it) { // from class: com.google.common.collect.Maps.1
            @Override // com.google.common.collect.TransformedIterator
            public K transform(Map.Entry<K, V> entry) {
                return entry.getKey();
            }
        };
    }

    @NullableDecl
    public static <K> K keyOrNull(@NullableDecl Map.Entry<K, ?> entry) {
        if (entry == null) {
            return null;
        }
        return entry.getKey();
    }

    public static <K> Predicate<Map.Entry<K, ?>> keyPredicateOnEntries(Predicate<? super K> predicate) {
        return Predicates.compose(predicate, keyFunction());
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentMap() {
        return new ConcurrentHashMap();
    }

    public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(Class<K> cls) {
        return new EnumMap<>((Class) Preconditions.checkNotNull(cls));
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>();
    }

    public static <K, V> HashMap<K, V> newHashMapWithExpectedSize(int i2) {
        return new HashMap<>(capacity(i2));
    }

    public static <K, V> IdentityHashMap<K, V> newIdentityHashMap() {
        return new IdentityHashMap<>();
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
        return new LinkedHashMap<>();
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMapWithExpectedSize(int i2) {
        return new LinkedHashMap<>(capacity(i2));
    }

    public static <K extends Comparable, V> TreeMap<K, V> newTreeMap() {
        return new TreeMap<>();
    }

    public static <E> Comparator<? super E> orNaturalOrder(@NullableDecl Comparator<? super E> comparator) {
        return comparator != null ? comparator : Ordering.natural();
    }

    public static <K, V> void putAllImpl(Map<K, V> map, Map<? extends K, ? extends V> map2) {
        for (Map.Entry<? extends K, ? extends V> entry : map2.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
    }

    public static <K, V> boolean removeEntryImpl(Collection<Map.Entry<K, V>> collection, Object obj) {
        if (obj instanceof Map.Entry) {
            return collection.remove(unmodifiableEntry((Map.Entry) obj));
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GwtIncompatible
    public static <E> NavigableSet<E> removeOnlyNavigableSet(final NavigableSet<E> navigableSet) {
        return new ForwardingNavigableSet<E>() { // from class: com.google.common.collect.Maps.6
            @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Queue
            public boolean add(E e2) {
                throw new UnsupportedOperationException();
            }

            @Override // com.google.common.collect.ForwardingCollection, java.util.Collection
            public boolean addAll(Collection<? extends E> collection) {
                throw new UnsupportedOperationException();
            }

            @Override // com.google.common.collect.ForwardingNavigableSet, java.util.NavigableSet
            public NavigableSet<E> descendingSet() {
                return Maps.removeOnlyNavigableSet(super.descendingSet());
            }

            @Override // com.google.common.collect.ForwardingSortedSet, java.util.SortedSet
            public SortedSet<E> headSet(E e2) {
                return Maps.removeOnlySortedSet(super.headSet(e2));
            }

            @Override // com.google.common.collect.ForwardingSortedSet, java.util.SortedSet
            public SortedSet<E> subSet(E e2, E e3) {
                return Maps.removeOnlySortedSet(super.subSet(e2, e3));
            }

            @Override // com.google.common.collect.ForwardingSortedSet, java.util.SortedSet
            public SortedSet<E> tailSet(E e2) {
                return Maps.removeOnlySortedSet(super.tailSet(e2));
            }

            @Override // com.google.common.collect.ForwardingNavigableSet, java.util.NavigableSet
            public NavigableSet<E> headSet(E e2, boolean z2) {
                return Maps.removeOnlyNavigableSet(super.headSet(e2, z2));
            }

            @Override // com.google.common.collect.ForwardingNavigableSet, java.util.NavigableSet
            public NavigableSet<E> subSet(E e2, boolean z2, E e3, boolean z3) {
                return Maps.removeOnlyNavigableSet(super.subSet(e2, z2, e3, z3));
            }

            @Override // com.google.common.collect.ForwardingNavigableSet, java.util.NavigableSet
            public NavigableSet<E> tailSet(E e2, boolean z2) {
                return Maps.removeOnlyNavigableSet(super.tailSet(e2, z2));
            }

            @Override // com.google.common.collect.ForwardingNavigableSet, com.google.common.collect.ForwardingSortedSet, com.google.common.collect.ForwardingSet, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
            public NavigableSet<E> delegate() {
                return navigableSet;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <E> Set<E> removeOnlySet(final Set<E> set) {
        return new ForwardingSet<E>() { // from class: com.google.common.collect.Maps.4
            @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Queue
            public boolean add(E e2) {
                throw new UnsupportedOperationException();
            }

            @Override // com.google.common.collect.ForwardingCollection, java.util.Collection
            public boolean addAll(Collection<? extends E> collection) {
                throw new UnsupportedOperationException();
            }

            @Override // com.google.common.collect.ForwardingSet, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
            public Set<E> delegate() {
                return set;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <E> SortedSet<E> removeOnlySortedSet(final SortedSet<E> sortedSet) {
        return new ForwardingSortedSet<E>() { // from class: com.google.common.collect.Maps.5
            @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Queue
            public boolean add(E e2) {
                throw new UnsupportedOperationException();
            }

            @Override // com.google.common.collect.ForwardingCollection, java.util.Collection
            public boolean addAll(Collection<? extends E> collection) {
                throw new UnsupportedOperationException();
            }

            @Override // com.google.common.collect.ForwardingSortedSet, java.util.SortedSet
            public SortedSet<E> headSet(E e2) {
                return Maps.removeOnlySortedSet(super.headSet(e2));
            }

            @Override // com.google.common.collect.ForwardingSortedSet, java.util.SortedSet
            public SortedSet<E> subSet(E e2, E e3) {
                return Maps.removeOnlySortedSet(super.subSet(e2, e3));
            }

            @Override // com.google.common.collect.ForwardingSortedSet, java.util.SortedSet
            public SortedSet<E> tailSet(E e2) {
                return Maps.removeOnlySortedSet(super.tailSet(e2));
            }

            @Override // com.google.common.collect.ForwardingSortedSet, com.google.common.collect.ForwardingSet, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
            public SortedSet<E> delegate() {
                return sortedSet;
            }
        };
    }

    public static boolean safeContainsKey(Map<?, ?> map, Object obj) {
        Preconditions.checkNotNull(map);
        try {
            return map.containsKey(obj);
        } catch (ClassCastException | NullPointerException unused) {
            return false;
        }
    }

    public static <V> V safeGet(Map<?, V> map, @NullableDecl Object obj) {
        Preconditions.checkNotNull(map);
        try {
            return map.get(obj);
        } catch (ClassCastException | NullPointerException unused) {
            return null;
        }
    }

    public static <V> V safeRemove(Map<?, V> map, Object obj) {
        Preconditions.checkNotNull(map);
        try {
            return map.remove(obj);
        } catch (ClassCastException | NullPointerException unused) {
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Beta
    @GwtIncompatible
    public static <K extends Comparable<? super K>, V> NavigableMap<K, V> subMap(NavigableMap<K, V> navigableMap, Range<K> range) {
        if (navigableMap.comparator() != null && navigableMap.comparator() != Ordering.natural() && range.hasLowerBound() && range.hasUpperBound()) {
            Preconditions.checkArgument(navigableMap.comparator().compare(range.lowerEndpoint(), range.upperEndpoint()) <= 0, "map is using a custom comparator which is inconsistent with the natural ordering.");
        }
        if (range.hasLowerBound() && range.hasUpperBound()) {
            Comparable comparableLowerEndpoint = range.lowerEndpoint();
            BoundType boundTypeLowerBoundType = range.lowerBoundType();
            BoundType boundType = BoundType.CLOSED;
            return navigableMap.subMap(comparableLowerEndpoint, boundTypeLowerBoundType == boundType, range.upperEndpoint(), range.upperBoundType() == boundType);
        }
        if (range.hasLowerBound()) {
            return navigableMap.tailMap(range.lowerEndpoint(), range.lowerBoundType() == BoundType.CLOSED);
        }
        if (range.hasUpperBound()) {
            return navigableMap.headMap(range.upperEndpoint(), range.upperBoundType() == BoundType.CLOSED);
        }
        return (NavigableMap) Preconditions.checkNotNull(navigableMap);
    }

    public static <K, V> BiMap<K, V> synchronizedBiMap(BiMap<K, V> biMap) {
        return Synchronized.biMap(biMap, null);
    }

    @GwtIncompatible
    public static <K, V> NavigableMap<K, V> synchronizedNavigableMap(NavigableMap<K, V> navigableMap) {
        return Synchronized.navigableMap(navigableMap);
    }

    public static <K, V> ImmutableMap<K, V> toMap(Iterable<K> iterable, Function<? super K, V> function) {
        return toMap(iterable.iterator(), function);
    }

    public static String toStringImpl(Map<?, ?> map) {
        StringBuilder sbNewStringBuilderForCollection = Collections2.newStringBuilderForCollection(map.size());
        sbNewStringBuilderForCollection.append('{');
        boolean z2 = true;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (!z2) {
                sbNewStringBuilderForCollection.append(", ");
            }
            sbNewStringBuilderForCollection.append(entry.getKey());
            sbNewStringBuilderForCollection.append('=');
            sbNewStringBuilderForCollection.append(entry.getValue());
            z2 = false;
        }
        sbNewStringBuilderForCollection.append('}');
        return sbNewStringBuilderForCollection.toString();
    }

    public static <K, V1, V2> Map<K, V2> transformEntries(Map<K, V1> map, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
        return new TransformedEntriesMap(map, entryTransformer);
    }

    public static <V2, K, V1> Map.Entry<K, V2> transformEntry(final EntryTransformer<? super K, ? super V1, V2> entryTransformer, final Map.Entry<K, V1> entry) {
        Preconditions.checkNotNull(entryTransformer);
        Preconditions.checkNotNull(entry);
        return new AbstractMapEntry<K, V2>() { // from class: com.google.common.collect.Maps.12
            @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
            public K getKey() {
                return (K) entry.getKey();
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
            public V2 getValue() {
                return (V2) entryTransformer.transformEntry(entry.getKey(), entry.getValue());
            }
        };
    }

    public static <K, V1, V2> Map<K, V2> transformValues(Map<K, V1> map, Function<? super V1, V2> function) {
        return transformEntries(map, asEntryTransformer(function));
    }

    @CanIgnoreReturnValue
    public static <K, V> ImmutableMap<K, V> uniqueIndex(Iterable<V> iterable, Function<? super V, K> function) {
        return uniqueIndex(iterable.iterator(), function);
    }

    public static <K, V> BiMap<K, V> unmodifiableBiMap(BiMap<? extends K, ? extends V> biMap) {
        return new UnmodifiableBiMap(biMap, null);
    }

    public static <K, V> Map.Entry<K, V> unmodifiableEntry(final Map.Entry<? extends K, ? extends V> entry) {
        Preconditions.checkNotNull(entry);
        return new AbstractMapEntry<K, V>() { // from class: com.google.common.collect.Maps.7
            @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
            public K getKey() {
                return (K) entry.getKey();
            }

            @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
            public V getValue() {
                return (V) entry.getValue();
            }
        };
    }

    public static <K, V> UnmodifiableIterator<Map.Entry<K, V>> unmodifiableEntryIterator(final Iterator<Map.Entry<K, V>> it) {
        return new UnmodifiableIterator<Map.Entry<K, V>>() { // from class: com.google.common.collect.Maps.8
            @Override // java.util.Iterator
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override // java.util.Iterator
            public Map.Entry<K, V> next() {
                return Maps.unmodifiableEntry((Map.Entry) it.next());
            }
        };
    }

    public static <K, V> Set<Map.Entry<K, V>> unmodifiableEntrySet(Set<Map.Entry<K, V>> set) {
        return new UnmodifiableEntrySet(Collections.unmodifiableSet(set));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <K, V> Map<K, V> unmodifiableMap(Map<K, ? extends V> map) {
        return map instanceof SortedMap ? Collections.unmodifiableSortedMap((SortedMap) map) : Collections.unmodifiableMap(map);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @GwtIncompatible
    public static <K, V> NavigableMap<K, V> unmodifiableNavigableMap(NavigableMap<K, ? extends V> navigableMap) {
        Preconditions.checkNotNull(navigableMap);
        return navigableMap instanceof UnmodifiableNavigableMap ? navigableMap : new UnmodifiableNavigableMap(navigableMap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @NullableDecl
    public static <K, V> Map.Entry<K, V> unmodifiableOrNull(@NullableDecl Map.Entry<K, ? extends V> entry) {
        if (entry == null) {
            return null;
        }
        return unmodifiableEntry(entry);
    }

    public static <V> Function<Map.Entry<?, V>, V> valueFunction() {
        return EntryFunction.VALUE;
    }

    public static <K, V> Iterator<V> valueIterator(Iterator<Map.Entry<K, V>> it) {
        return new TransformedIterator<Map.Entry<K, V>, V>(it) { // from class: com.google.common.collect.Maps.2
            @Override // com.google.common.collect.TransformedIterator
            public V transform(Map.Entry<K, V> entry) {
                return entry.getValue();
            }
        };
    }

    @NullableDecl
    public static <V> V valueOrNull(@NullableDecl Map.Entry<?, V> entry) {
        if (entry == null) {
            return null;
        }
        return entry.getValue();
    }

    public static <V> Predicate<Map.Entry<?, V>> valuePredicateOnEntries(Predicate<? super V> predicate) {
        return Predicates.compose(predicate, valueFunction());
    }

    public static <K, V> SortedMap<K, V> asMap(SortedSet<K> sortedSet, Function<? super K, V> function) {
        return new SortedAsMapView(sortedSet, function);
    }

    public static <K, V> SortedMap<K, V> filterValues(SortedMap<K, V> sortedMap, Predicate<? super V> predicate) {
        return filterEntries((SortedMap) sortedMap, valuePredicateOnEntries(predicate));
    }

    public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(Map<K, ? extends V> map) {
        return new EnumMap<>(map);
    }

    public static <K, V> HashMap<K, V> newHashMap(Map<? extends K, ? extends V> map) {
        return new HashMap<>(map);
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(Map<? extends K, ? extends V> map) {
        return new LinkedHashMap<>(map);
    }

    public static <K, V> TreeMap<K, V> newTreeMap(SortedMap<K, ? extends V> sortedMap) {
        return new TreeMap<>((SortedMap) sortedMap);
    }

    public static <K, V> ImmutableMap<K, V> toMap(Iterator<K> it, Function<? super K, V> function) {
        Preconditions.checkNotNull(function);
        LinkedHashMap linkedHashMapNewLinkedHashMap = newLinkedHashMap();
        while (it.hasNext()) {
            K next = it.next();
            linkedHashMapNewLinkedHashMap.put(next, function.apply(next));
        }
        return ImmutableMap.copyOf((Map) linkedHashMapNewLinkedHashMap);
    }

    public static <K, V1, V2> SortedMap<K, V2> transformEntries(SortedMap<K, V1> sortedMap, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
        return new TransformedEntriesSortedMap(sortedMap, entryTransformer);
    }

    public static <K, V1, V2> SortedMap<K, V2> transformValues(SortedMap<K, V1> sortedMap, Function<? super V1, V2> function) {
        return transformEntries((SortedMap) sortedMap, asEntryTransformer(function));
    }

    @CanIgnoreReturnValue
    public static <K, V> ImmutableMap<K, V> uniqueIndex(Iterator<V> it, Function<? super V, K> function) {
        Preconditions.checkNotNull(function);
        ImmutableMap.Builder builder = ImmutableMap.builder();
        while (it.hasNext()) {
            V next = it.next();
            builder.put(function.apply(next), next);
        }
        try {
            return builder.build();
        } catch (IllegalArgumentException e2) {
            throw new IllegalArgumentException(e2.getMessage() + ". To index multiple values under a key, use Multimaps.index.");
        }
    }

    @GwtIncompatible
    public static <K, V> NavigableMap<K, V> asMap(NavigableSet<K> navigableSet, Function<? super K, V> function) {
        return new NavigableAsMapView(navigableSet, function);
    }

    private static <K, V> SortedMap<K, V> filterFiltered(FilteredEntrySortedMap<K, V> filteredEntrySortedMap, Predicate<? super Map.Entry<K, V>> predicate) {
        return new FilteredEntrySortedMap(filteredEntrySortedMap.sortedMap(), Predicates.and(filteredEntrySortedMap.predicate, predicate));
    }

    @GwtIncompatible
    public static <K, V> NavigableMap<K, V> filterValues(NavigableMap<K, V> navigableMap, Predicate<? super V> predicate) {
        return filterEntries((NavigableMap) navigableMap, valuePredicateOnEntries(predicate));
    }

    public static <C, K extends C, V> TreeMap<K, V> newTreeMap(@NullableDecl Comparator<C> comparator) {
        return new TreeMap<>(comparator);
    }

    @GwtIncompatible
    public static <K, V1, V2> NavigableMap<K, V2> transformEntries(NavigableMap<K, V1> navigableMap, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
        return new TransformedEntriesNavigableMap(navigableMap, entryTransformer);
    }

    @GwtIncompatible
    public static <K, V1, V2> NavigableMap<K, V2> transformValues(NavigableMap<K, V1> navigableMap, Function<? super V1, V2> function) {
        return transformEntries((NavigableMap) navigableMap, asEntryTransformer(function));
    }

    public static <K, V> BiMap<K, V> filterValues(BiMap<K, V> biMap, Predicate<? super V> predicate) {
        return filterEntries((BiMap) biMap, valuePredicateOnEntries(predicate));
    }

    public static <K, V> MapDifference<K, V> difference(Map<? extends K, ? extends V> map, Map<? extends K, ? extends V> map2, Equivalence<? super V> equivalence) {
        Preconditions.checkNotNull(equivalence);
        LinkedHashMap linkedHashMapNewLinkedHashMap = newLinkedHashMap();
        LinkedHashMap linkedHashMap = new LinkedHashMap(map2);
        LinkedHashMap linkedHashMapNewLinkedHashMap2 = newLinkedHashMap();
        LinkedHashMap linkedHashMapNewLinkedHashMap3 = newLinkedHashMap();
        doDifference(map, map2, equivalence, linkedHashMapNewLinkedHashMap, linkedHashMap, linkedHashMapNewLinkedHashMap2, linkedHashMapNewLinkedHashMap3);
        return new MapDifferenceImpl(linkedHashMapNewLinkedHashMap, linkedHashMap, linkedHashMapNewLinkedHashMap2, linkedHashMapNewLinkedHashMap3);
    }

    public static <K, V> SortedMap<K, V> filterEntries(SortedMap<K, V> sortedMap, Predicate<? super Map.Entry<K, V>> predicate) {
        Preconditions.checkNotNull(predicate);
        if (sortedMap instanceof FilteredEntrySortedMap) {
            return filterFiltered((FilteredEntrySortedMap) sortedMap, (Predicate) predicate);
        }
        return new FilteredEntrySortedMap((SortedMap) Preconditions.checkNotNull(sortedMap), predicate);
    }

    @GwtIncompatible
    private static <K, V> NavigableMap<K, V> filterFiltered(FilteredEntryNavigableMap<K, V> filteredEntryNavigableMap, Predicate<? super Map.Entry<K, V>> predicate) {
        return new FilteredEntryNavigableMap(((FilteredEntryNavigableMap) filteredEntryNavigableMap).unfiltered, Predicates.and(((FilteredEntryNavigableMap) filteredEntryNavigableMap).entryPredicate, predicate));
    }

    public static <K, V> SortedMap<K, V> filterKeys(SortedMap<K, V> sortedMap, Predicate<? super K> predicate) {
        return filterEntries((SortedMap) sortedMap, keyPredicateOnEntries(predicate));
    }

    private static <K, V> BiMap<K, V> filterFiltered(FilteredEntryBiMap<K, V> filteredEntryBiMap, Predicate<? super Map.Entry<K, V>> predicate) {
        return new FilteredEntryBiMap(filteredEntryBiMap.unfiltered(), Predicates.and(filteredEntryBiMap.predicate, predicate));
    }

    @GwtIncompatible
    public static <K, V> NavigableMap<K, V> filterKeys(NavigableMap<K, V> navigableMap, Predicate<? super K> predicate) {
        return filterEntries((NavigableMap) navigableMap, keyPredicateOnEntries(predicate));
    }

    public static <K, V> BiMap<K, V> filterKeys(BiMap<K, V> biMap, Predicate<? super K> predicate) {
        Preconditions.checkNotNull(predicate);
        return filterEntries((BiMap) biMap, keyPredicateOnEntries(predicate));
    }

    @GwtIncompatible
    public static <K, V> NavigableMap<K, V> filterEntries(NavigableMap<K, V> navigableMap, Predicate<? super Map.Entry<K, V>> predicate) {
        Preconditions.checkNotNull(predicate);
        if (navigableMap instanceof FilteredEntryNavigableMap) {
            return filterFiltered((FilteredEntryNavigableMap) navigableMap, predicate);
        }
        return new FilteredEntryNavigableMap((NavigableMap) Preconditions.checkNotNull(navigableMap), predicate);
    }

    public static <K, V> SortedMapDifference<K, V> difference(SortedMap<K, ? extends V> sortedMap, Map<? extends K, ? extends V> map) {
        Preconditions.checkNotNull(sortedMap);
        Preconditions.checkNotNull(map);
        Comparator comparatorOrNaturalOrder = orNaturalOrder(sortedMap.comparator());
        TreeMap treeMapNewTreeMap = newTreeMap(comparatorOrNaturalOrder);
        TreeMap treeMapNewTreeMap2 = newTreeMap(comparatorOrNaturalOrder);
        treeMapNewTreeMap2.putAll(map);
        TreeMap treeMapNewTreeMap3 = newTreeMap(comparatorOrNaturalOrder);
        TreeMap treeMapNewTreeMap4 = newTreeMap(comparatorOrNaturalOrder);
        doDifference(sortedMap, map, Equivalence.equals(), treeMapNewTreeMap, treeMapNewTreeMap2, treeMapNewTreeMap3, treeMapNewTreeMap4);
        return new SortedMapDifferenceImpl(treeMapNewTreeMap, treeMapNewTreeMap2, treeMapNewTreeMap3, treeMapNewTreeMap4);
    }

    public static <K, V> BiMap<K, V> filterEntries(BiMap<K, V> biMap, Predicate<? super Map.Entry<K, V>> predicate) {
        Preconditions.checkNotNull(biMap);
        Preconditions.checkNotNull(predicate);
        return biMap instanceof FilteredEntryBiMap ? filterFiltered((FilteredEntryBiMap) biMap, (Predicate) predicate) : new FilteredEntryBiMap(biMap, predicate);
    }
}
