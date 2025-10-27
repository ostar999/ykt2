package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Maps;
import java.lang.Comparable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NoSuchElementException;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
@GwtIncompatible
/* loaded from: classes4.dex */
public final class TreeRangeMap<K extends Comparable, V> implements RangeMap<K, V> {
    private static final RangeMap EMPTY_SUB_RANGE_MAP = new RangeMap() { // from class: com.google.common.collect.TreeRangeMap.1
        @Override // com.google.common.collect.RangeMap
        public Map<Range, Object> asDescendingMapOfRanges() {
            return Collections.emptyMap();
        }

        @Override // com.google.common.collect.RangeMap
        public Map<Range, Object> asMapOfRanges() {
            return Collections.emptyMap();
        }

        @Override // com.google.common.collect.RangeMap
        public void clear() {
        }

        @Override // com.google.common.collect.RangeMap
        @NullableDecl
        public Object get(Comparable comparable) {
            return null;
        }

        @Override // com.google.common.collect.RangeMap
        @NullableDecl
        public Map.Entry<Range, Object> getEntry(Comparable comparable) {
            return null;
        }

        @Override // com.google.common.collect.RangeMap
        public void put(Range range, Object obj) {
            Preconditions.checkNotNull(range);
            throw new IllegalArgumentException("Cannot insert range " + range + " into an empty subRangeMap");
        }

        @Override // com.google.common.collect.RangeMap
        public void putAll(RangeMap rangeMap) {
            if (!rangeMap.asMapOfRanges().isEmpty()) {
                throw new IllegalArgumentException("Cannot putAll(nonEmptyRangeMap) into an empty subRangeMap");
            }
        }

        @Override // com.google.common.collect.RangeMap
        public void putCoalescing(Range range, Object obj) {
            Preconditions.checkNotNull(range);
            throw new IllegalArgumentException("Cannot insert range " + range + " into an empty subRangeMap");
        }

        @Override // com.google.common.collect.RangeMap
        public void remove(Range range) {
            Preconditions.checkNotNull(range);
        }

        @Override // com.google.common.collect.RangeMap
        public Range span() {
            throw new NoSuchElementException();
        }

        @Override // com.google.common.collect.RangeMap
        public RangeMap subRangeMap(Range range) {
            Preconditions.checkNotNull(range);
            return this;
        }
    };
    private final NavigableMap<Cut<K>, RangeMapEntry<K, V>> entriesByLowerBound = Maps.newTreeMap();

    public final class AsMapOfRanges extends Maps.IteratorBasedAbstractMap<Range<K>, V> {
        final Iterable<Map.Entry<Range<K>, V>> entryIterable;

        public AsMapOfRanges(Iterable<RangeMapEntry<K, V>> iterable) {
            this.entryIterable = iterable;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(@NullableDecl Object obj) {
            return get(obj) != null;
        }

        @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap
        public Iterator<Map.Entry<Range<K>, V>> entryIterator() {
            return this.entryIterable.iterator();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V get(@NullableDecl Object obj) {
            if (!(obj instanceof Range)) {
                return null;
            }
            Range range = (Range) obj;
            RangeMapEntry rangeMapEntry = (RangeMapEntry) TreeRangeMap.this.entriesByLowerBound.get(range.lowerBound);
            if (rangeMapEntry == null || !rangeMapEntry.getKey().equals(range)) {
                return null;
            }
            return (V) rangeMapEntry.getValue();
        }

        @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap, java.util.AbstractMap, java.util.Map
        public int size() {
            return TreeRangeMap.this.entriesByLowerBound.size();
        }
    }

    public static final class RangeMapEntry<K extends Comparable, V> extends AbstractMapEntry<Range<K>, V> {
        private final Range<K> range;
        private final V value;

        public RangeMapEntry(Cut<K> cut, Cut<K> cut2, V v2) {
            this(Range.create(cut, cut2), v2);
        }

        public boolean contains(K k2) {
            return this.range.contains(k2);
        }

        public Cut<K> getLowerBound() {
            return (Cut<K>) this.range.lowerBound;
        }

        public Cut<K> getUpperBound() {
            return (Cut<K>) this.range.upperBound;
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        public V getValue() {
            return this.value;
        }

        public RangeMapEntry(Range<K> range, V v2) {
            this.range = range;
            this.value = v2;
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        public Range<K> getKey() {
            return this.range;
        }
    }

    public class SubRangeMap implements RangeMap<K, V> {
        private final Range<K> subRange;

        public class SubRangeMapAsMap extends AbstractMap<Range<K>, V> {
            public SubRangeMapAsMap() {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public boolean removeEntryIf(Predicate<? super Map.Entry<Range<K>, V>> predicate) {
                ArrayList arrayListNewArrayList = Lists.newArrayList();
                for (Map.Entry<Range<K>, V> entry : entrySet()) {
                    if (predicate.apply(entry)) {
                        arrayListNewArrayList.add(entry.getKey());
                    }
                }
                Iterator it = arrayListNewArrayList.iterator();
                while (it.hasNext()) {
                    TreeRangeMap.this.remove((Range) it.next());
                }
                return !arrayListNewArrayList.isEmpty();
            }

            @Override // java.util.AbstractMap, java.util.Map
            public void clear() {
                SubRangeMap.this.clear();
            }

            @Override // java.util.AbstractMap, java.util.Map
            public boolean containsKey(Object obj) {
                return get(obj) != null;
            }

            public Iterator<Map.Entry<Range<K>, V>> entryIterator() {
                if (SubRangeMap.this.subRange.isEmpty()) {
                    return Iterators.emptyIterator();
                }
                final Iterator<V> it = TreeRangeMap.this.entriesByLowerBound.tailMap((Cut) MoreObjects.firstNonNull(TreeRangeMap.this.entriesByLowerBound.floorKey(SubRangeMap.this.subRange.lowerBound), SubRangeMap.this.subRange.lowerBound), true).values().iterator();
                return new AbstractIterator<Map.Entry<Range<K>, V>>() { // from class: com.google.common.collect.TreeRangeMap.SubRangeMap.SubRangeMapAsMap.3
                    @Override // com.google.common.collect.AbstractIterator
                    public Map.Entry<Range<K>, V> computeNext() {
                        while (it.hasNext()) {
                            RangeMapEntry rangeMapEntry = (RangeMapEntry) it.next();
                            if (rangeMapEntry.getLowerBound().compareTo((Cut) SubRangeMap.this.subRange.upperBound) >= 0) {
                                return (Map.Entry) endOfData();
                            }
                            if (rangeMapEntry.getUpperBound().compareTo((Cut) SubRangeMap.this.subRange.lowerBound) > 0) {
                                return Maps.immutableEntry(rangeMapEntry.getKey().intersection(SubRangeMap.this.subRange), rangeMapEntry.getValue());
                            }
                        }
                        return (Map.Entry) endOfData();
                    }
                };
            }

            @Override // java.util.AbstractMap, java.util.Map
            public Set<Map.Entry<Range<K>, V>> entrySet() {
                return new Maps.EntrySet<Range<K>, V>() { // from class: com.google.common.collect.TreeRangeMap.SubRangeMap.SubRangeMapAsMap.2
                    @Override // com.google.common.collect.Maps.EntrySet, java.util.AbstractCollection, java.util.Collection, java.util.Set
                    public boolean isEmpty() {
                        return !iterator().hasNext();
                    }

                    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
                    public Iterator<Map.Entry<Range<K>, V>> iterator() {
                        return SubRangeMapAsMap.this.entryIterator();
                    }

                    @Override // com.google.common.collect.Maps.EntrySet
                    public Map<Range<K>, V> map() {
                        return SubRangeMapAsMap.this;
                    }

                    @Override // com.google.common.collect.Maps.EntrySet, com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
                    public boolean retainAll(Collection<?> collection) {
                        return SubRangeMapAsMap.this.removeEntryIf(Predicates.not(Predicates.in(collection)));
                    }

                    @Override // com.google.common.collect.Maps.EntrySet, java.util.AbstractCollection, java.util.Collection, java.util.Set
                    public int size() {
                        return Iterators.size(iterator());
                    }
                };
            }

            @Override // java.util.AbstractMap, java.util.Map
            public V get(Object obj) {
                RangeMapEntry rangeMapEntry;
                try {
                    if (obj instanceof Range) {
                        Range range = (Range) obj;
                        if (SubRangeMap.this.subRange.encloses(range) && !range.isEmpty()) {
                            if (range.lowerBound.compareTo((Cut) SubRangeMap.this.subRange.lowerBound) == 0) {
                                Map.Entry entryFloorEntry = TreeRangeMap.this.entriesByLowerBound.floorEntry(range.lowerBound);
                                rangeMapEntry = entryFloorEntry != null ? (RangeMapEntry) entryFloorEntry.getValue() : null;
                            } else {
                                rangeMapEntry = (RangeMapEntry) TreeRangeMap.this.entriesByLowerBound.get(range.lowerBound);
                            }
                            if (rangeMapEntry != null && rangeMapEntry.getKey().isConnected(SubRangeMap.this.subRange) && rangeMapEntry.getKey().intersection(SubRangeMap.this.subRange).equals(range)) {
                                return (V) rangeMapEntry.getValue();
                            }
                        }
                    }
                } catch (ClassCastException unused) {
                }
                return null;
            }

            @Override // java.util.AbstractMap, java.util.Map
            public Set<Range<K>> keySet() {
                return new Maps.KeySet<Range<K>, V>(this) { // from class: com.google.common.collect.TreeRangeMap.SubRangeMap.SubRangeMapAsMap.1
                    @Override // com.google.common.collect.Maps.KeySet, java.util.AbstractCollection, java.util.Collection, java.util.Set
                    public boolean remove(@NullableDecl Object obj) {
                        return SubRangeMapAsMap.this.remove(obj) != null;
                    }

                    @Override // com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
                    public boolean retainAll(Collection<?> collection) {
                        return SubRangeMapAsMap.this.removeEntryIf(Predicates.compose(Predicates.not(Predicates.in(collection)), Maps.keyFunction()));
                    }
                };
            }

            @Override // java.util.AbstractMap, java.util.Map
            public V remove(Object obj) {
                V v2 = (V) get(obj);
                if (v2 == null) {
                    return null;
                }
                TreeRangeMap.this.remove((Range) obj);
                return v2;
            }

            @Override // java.util.AbstractMap, java.util.Map
            public Collection<V> values() {
                return new Maps.Values<Range<K>, V>(this) { // from class: com.google.common.collect.TreeRangeMap.SubRangeMap.SubRangeMapAsMap.4
                    @Override // com.google.common.collect.Maps.Values, java.util.AbstractCollection, java.util.Collection
                    public boolean removeAll(Collection<?> collection) {
                        return SubRangeMapAsMap.this.removeEntryIf(Predicates.compose(Predicates.in(collection), Maps.valueFunction()));
                    }

                    @Override // com.google.common.collect.Maps.Values, java.util.AbstractCollection, java.util.Collection
                    public boolean retainAll(Collection<?> collection) {
                        return SubRangeMapAsMap.this.removeEntryIf(Predicates.compose(Predicates.not(Predicates.in(collection)), Maps.valueFunction()));
                    }
                };
            }
        }

        public SubRangeMap(Range<K> range) {
            this.subRange = range;
        }

        @Override // com.google.common.collect.RangeMap
        public Map<Range<K>, V> asDescendingMapOfRanges() {
            return new TreeRangeMap<K, V>.SubRangeMap.SubRangeMapAsMap() { // from class: com.google.common.collect.TreeRangeMap.SubRangeMap.1
                @Override // com.google.common.collect.TreeRangeMap.SubRangeMap.SubRangeMapAsMap
                public Iterator<Map.Entry<Range<K>, V>> entryIterator() {
                    if (SubRangeMap.this.subRange.isEmpty()) {
                        return Iterators.emptyIterator();
                    }
                    final Iterator<V> it = TreeRangeMap.this.entriesByLowerBound.headMap(SubRangeMap.this.subRange.upperBound, false).descendingMap().values().iterator();
                    return new AbstractIterator<Map.Entry<Range<K>, V>>() { // from class: com.google.common.collect.TreeRangeMap.SubRangeMap.1.1
                        @Override // com.google.common.collect.AbstractIterator
                        public Map.Entry<Range<K>, V> computeNext() {
                            if (!it.hasNext()) {
                                return (Map.Entry) endOfData();
                            }
                            RangeMapEntry rangeMapEntry = (RangeMapEntry) it.next();
                            return rangeMapEntry.getUpperBound().compareTo((Cut) SubRangeMap.this.subRange.lowerBound) <= 0 ? (Map.Entry) endOfData() : Maps.immutableEntry(rangeMapEntry.getKey().intersection(SubRangeMap.this.subRange), rangeMapEntry.getValue());
                        }
                    };
                }
            };
        }

        @Override // com.google.common.collect.RangeMap
        public Map<Range<K>, V> asMapOfRanges() {
            return new SubRangeMapAsMap();
        }

        @Override // com.google.common.collect.RangeMap
        public void clear() {
            TreeRangeMap.this.remove(this.subRange);
        }

        @Override // com.google.common.collect.RangeMap
        public boolean equals(@NullableDecl Object obj) {
            if (obj instanceof RangeMap) {
                return asMapOfRanges().equals(((RangeMap) obj).asMapOfRanges());
            }
            return false;
        }

        @Override // com.google.common.collect.RangeMap
        @NullableDecl
        public V get(K k2) {
            if (this.subRange.contains(k2)) {
                return (V) TreeRangeMap.this.get(k2);
            }
            return null;
        }

        @Override // com.google.common.collect.RangeMap
        @NullableDecl
        public Map.Entry<Range<K>, V> getEntry(K k2) {
            Map.Entry<Range<K>, V> entry;
            if (!this.subRange.contains(k2) || (entry = TreeRangeMap.this.getEntry(k2)) == null) {
                return null;
            }
            return Maps.immutableEntry(entry.getKey().intersection(this.subRange), entry.getValue());
        }

        @Override // com.google.common.collect.RangeMap
        public int hashCode() {
            return asMapOfRanges().hashCode();
        }

        @Override // com.google.common.collect.RangeMap
        public void put(Range<K> range, V v2) {
            Preconditions.checkArgument(this.subRange.encloses(range), "Cannot put range %s into a subRangeMap(%s)", range, this.subRange);
            TreeRangeMap.this.put(range, v2);
        }

        @Override // com.google.common.collect.RangeMap
        public void putAll(RangeMap<K, V> rangeMap) {
            if (rangeMap.asMapOfRanges().isEmpty()) {
                return;
            }
            Range<K> rangeSpan = rangeMap.span();
            Preconditions.checkArgument(this.subRange.encloses(rangeSpan), "Cannot putAll rangeMap with span %s into a subRangeMap(%s)", rangeSpan, this.subRange);
            TreeRangeMap.this.putAll(rangeMap);
        }

        @Override // com.google.common.collect.RangeMap
        public void putCoalescing(Range<K> range, V v2) {
            if (TreeRangeMap.this.entriesByLowerBound.isEmpty() || range.isEmpty() || !this.subRange.encloses(range)) {
                put(range, v2);
            } else {
                put(TreeRangeMap.this.coalescedRange(range, Preconditions.checkNotNull(v2)).intersection(this.subRange), v2);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.RangeMap
        public void remove(Range<K> range) {
            if (range.isConnected(this.subRange)) {
                TreeRangeMap.this.remove(range.intersection(this.subRange));
            }
        }

        @Override // com.google.common.collect.RangeMap
        public Range<K> span() {
            Cut cut;
            Map.Entry entryFloorEntry = TreeRangeMap.this.entriesByLowerBound.floorEntry(this.subRange.lowerBound);
            if (entryFloorEntry == null || ((RangeMapEntry) entryFloorEntry.getValue()).getUpperBound().compareTo((Cut) this.subRange.lowerBound) <= 0) {
                cut = (Cut) TreeRangeMap.this.entriesByLowerBound.ceilingKey(this.subRange.lowerBound);
                if (cut == null || cut.compareTo((Cut) this.subRange.upperBound) >= 0) {
                    throw new NoSuchElementException();
                }
            } else {
                cut = this.subRange.lowerBound;
            }
            Map.Entry entryLowerEntry = TreeRangeMap.this.entriesByLowerBound.lowerEntry(this.subRange.upperBound);
            if (entryLowerEntry != null) {
                return Range.create(cut, ((RangeMapEntry) entryLowerEntry.getValue()).getUpperBound().compareTo((Cut) this.subRange.upperBound) >= 0 ? this.subRange.upperBound : ((RangeMapEntry) entryLowerEntry.getValue()).getUpperBound());
            }
            throw new NoSuchElementException();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.RangeMap
        public RangeMap<K, V> subRangeMap(Range<K> range) {
            return !range.isConnected(this.subRange) ? TreeRangeMap.this.emptySubRangeMap() : TreeRangeMap.this.subRangeMap(range.intersection(this.subRange));
        }

        @Override // com.google.common.collect.RangeMap
        public String toString() {
            return asMapOfRanges().toString();
        }
    }

    private TreeRangeMap() {
    }

    private static <K extends Comparable, V> Range<K> coalesce(Range<K> range, V v2, @NullableDecl Map.Entry<Cut<K>, RangeMapEntry<K, V>> entry) {
        return (entry != null && entry.getValue().getKey().isConnected(range) && entry.getValue().getValue().equals(v2)) ? (Range<K>) range.span(entry.getValue().getKey()) : range;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Range<K> coalescedRange(Range<K> range, V v2) {
        return coalesce(coalesce(range, v2, this.entriesByLowerBound.lowerEntry(range.lowerBound)), v2, this.entriesByLowerBound.floorEntry(range.upperBound));
    }

    public static <K extends Comparable, V> TreeRangeMap<K, V> create() {
        return new TreeRangeMap<>();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public RangeMap<K, V> emptySubRangeMap() {
        return EMPTY_SUB_RANGE_MAP;
    }

    private void putRangeMapEntry(Cut<K> cut, Cut<K> cut2, V v2) {
        this.entriesByLowerBound.put(cut, new RangeMapEntry<>(cut, cut2, v2));
    }

    @Override // com.google.common.collect.RangeMap
    public Map<Range<K>, V> asDescendingMapOfRanges() {
        return new AsMapOfRanges(this.entriesByLowerBound.descendingMap().values());
    }

    @Override // com.google.common.collect.RangeMap
    public Map<Range<K>, V> asMapOfRanges() {
        return new AsMapOfRanges(this.entriesByLowerBound.values());
    }

    @Override // com.google.common.collect.RangeMap
    public void clear() {
        this.entriesByLowerBound.clear();
    }

    @Override // com.google.common.collect.RangeMap
    public boolean equals(@NullableDecl Object obj) {
        if (obj instanceof RangeMap) {
            return asMapOfRanges().equals(((RangeMap) obj).asMapOfRanges());
        }
        return false;
    }

    @Override // com.google.common.collect.RangeMap
    @NullableDecl
    public V get(K k2) {
        Map.Entry<Range<K>, V> entry = getEntry(k2);
        if (entry == null) {
            return null;
        }
        return entry.getValue();
    }

    @Override // com.google.common.collect.RangeMap
    @NullableDecl
    public Map.Entry<Range<K>, V> getEntry(K k2) {
        Map.Entry<Cut<K>, RangeMapEntry<K, V>> entryFloorEntry = this.entriesByLowerBound.floorEntry(Cut.belowValue(k2));
        if (entryFloorEntry == null || !entryFloorEntry.getValue().contains(k2)) {
            return null;
        }
        return entryFloorEntry.getValue();
    }

    @Override // com.google.common.collect.RangeMap
    public int hashCode() {
        return asMapOfRanges().hashCode();
    }

    @Override // com.google.common.collect.RangeMap
    public void put(Range<K> range, V v2) {
        if (range.isEmpty()) {
            return;
        }
        Preconditions.checkNotNull(v2);
        remove(range);
        this.entriesByLowerBound.put(range.lowerBound, new RangeMapEntry(range, v2));
    }

    @Override // com.google.common.collect.RangeMap
    public void putAll(RangeMap<K, V> rangeMap) {
        for (Map.Entry<Range<K>, V> entry : rangeMap.asMapOfRanges().entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.RangeMap
    public void putCoalescing(Range<K> range, V v2) {
        if (this.entriesByLowerBound.isEmpty()) {
            put(range, v2);
        } else {
            put(coalescedRange(range, Preconditions.checkNotNull(v2)), v2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.RangeMap
    public void remove(Range<K> range) {
        if (range.isEmpty()) {
            return;
        }
        Map.Entry entryLowerEntry = this.entriesByLowerBound.lowerEntry(range.lowerBound);
        if (entryLowerEntry != null) {
            RangeMapEntry rangeMapEntry = (RangeMapEntry) entryLowerEntry.getValue();
            if (rangeMapEntry.getUpperBound().compareTo((Cut) range.lowerBound) > 0) {
                if (rangeMapEntry.getUpperBound().compareTo((Cut) range.upperBound) > 0) {
                    putRangeMapEntry(range.upperBound, rangeMapEntry.getUpperBound(), ((RangeMapEntry) entryLowerEntry.getValue()).getValue());
                }
                putRangeMapEntry(rangeMapEntry.getLowerBound(), range.lowerBound, ((RangeMapEntry) entryLowerEntry.getValue()).getValue());
            }
        }
        Map.Entry entryLowerEntry2 = this.entriesByLowerBound.lowerEntry(range.upperBound);
        if (entryLowerEntry2 != null) {
            RangeMapEntry rangeMapEntry2 = (RangeMapEntry) entryLowerEntry2.getValue();
            if (rangeMapEntry2.getUpperBound().compareTo((Cut) range.upperBound) > 0) {
                putRangeMapEntry(range.upperBound, rangeMapEntry2.getUpperBound(), ((RangeMapEntry) entryLowerEntry2.getValue()).getValue());
            }
        }
        this.entriesByLowerBound.subMap(range.lowerBound, range.upperBound).clear();
    }

    @Override // com.google.common.collect.RangeMap
    public Range<K> span() {
        Map.Entry<Cut<K>, RangeMapEntry<K, V>> entryFirstEntry = this.entriesByLowerBound.firstEntry();
        Map.Entry<Cut<K>, RangeMapEntry<K, V>> entryLastEntry = this.entriesByLowerBound.lastEntry();
        if (entryFirstEntry != null) {
            return Range.create(entryFirstEntry.getValue().getKey().lowerBound, entryLastEntry.getValue().getKey().upperBound);
        }
        throw new NoSuchElementException();
    }

    @Override // com.google.common.collect.RangeMap
    public RangeMap<K, V> subRangeMap(Range<K> range) {
        return range.equals(Range.all()) ? this : new SubRangeMap(range);
    }

    @Override // com.google.common.collect.RangeMap
    public String toString() {
        return this.entriesByLowerBound.values().toString();
    }
}
