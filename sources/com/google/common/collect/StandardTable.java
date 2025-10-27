package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Supplier;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes4.dex */
class StandardTable<R, C, V> extends AbstractTable<R, C, V> implements Serializable {
    private static final long serialVersionUID = 0;

    @GwtTransient
    final Map<R, Map<C, V>> backingMap;

    @MonotonicNonNullDecl
    private transient Set<C> columnKeySet;

    @MonotonicNonNullDecl
    private transient StandardTable<R, C, V>.ColumnMap columnMap;

    @GwtTransient
    final Supplier<? extends Map<C, V>> factory;

    @MonotonicNonNullDecl
    private transient Map<R, Map<C, V>> rowMap;

    public class CellIterator implements Iterator<Table.Cell<R, C, V>> {
        Iterator<Map.Entry<C, V>> columnIterator;

        @NullableDecl
        Map.Entry<R, Map<C, V>> rowEntry;
        final Iterator<Map.Entry<R, Map<C, V>>> rowIterator;

        private CellIterator() {
            this.rowIterator = StandardTable.this.backingMap.entrySet().iterator();
            this.columnIterator = Iterators.emptyModifiableIterator();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.rowIterator.hasNext() || this.columnIterator.hasNext();
        }

        @Override // java.util.Iterator
        public void remove() {
            this.columnIterator.remove();
            if (this.rowEntry.getValue().isEmpty()) {
                this.rowIterator.remove();
                this.rowEntry = null;
            }
        }

        @Override // java.util.Iterator
        public Table.Cell<R, C, V> next() {
            if (!this.columnIterator.hasNext()) {
                Map.Entry<R, Map<C, V>> next = this.rowIterator.next();
                this.rowEntry = next;
                this.columnIterator = next.getValue().entrySet().iterator();
            }
            Map.Entry<C, V> next2 = this.columnIterator.next();
            return Tables.immutableCell(this.rowEntry.getKey(), next2.getKey(), next2.getValue());
        }
    }

    public class Column extends Maps.ViewCachingAbstractMap<R, V> {
        final C columnKey;

        public class EntrySet extends Sets.ImprovedAbstractSet<Map.Entry<R, V>> {
            private EntrySet() {
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public void clear() {
                Column.this.removeFromColumnIf(Predicates.alwaysTrue());
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(Object obj) {
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry entry = (Map.Entry) obj;
                return StandardTable.this.containsMapping(entry.getKey(), Column.this.columnKey, entry.getValue());
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean isEmpty() {
                Column column = Column.this;
                return !StandardTable.this.containsColumn(column.columnKey);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Map.Entry<R, V>> iterator() {
                return new EntrySetIterator();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean remove(Object obj) {
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry entry = (Map.Entry) obj;
                return StandardTable.this.removeMapping(entry.getKey(), Column.this.columnKey, entry.getValue());
            }

            @Override // com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean retainAll(Collection<?> collection) {
                return Column.this.removeFromColumnIf(Predicates.not(Predicates.in(collection)));
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                Iterator<Map<C, V>> it = StandardTable.this.backingMap.values().iterator();
                int i2 = 0;
                while (it.hasNext()) {
                    if (it.next().containsKey(Column.this.columnKey)) {
                        i2++;
                    }
                }
                return i2;
            }
        }

        public class EntrySetIterator extends AbstractIterator<Map.Entry<R, V>> {
            final Iterator<Map.Entry<R, Map<C, V>>> iterator;

            private EntrySetIterator() {
                this.iterator = StandardTable.this.backingMap.entrySet().iterator();
            }

            @Override // com.google.common.collect.AbstractIterator
            public Map.Entry<R, V> computeNext() {
                while (this.iterator.hasNext()) {
                    final Map.Entry<R, Map<C, V>> next = this.iterator.next();
                    if (next.getValue().containsKey(Column.this.columnKey)) {
                        return new AbstractMapEntry<R, V>() { // from class: com.google.common.collect.StandardTable.Column.EntrySetIterator.1EntryImpl
                            @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
                            public R getKey() {
                                return (R) next.getKey();
                            }

                            @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
                            public V getValue() {
                                return (V) ((Map) next.getValue()).get(Column.this.columnKey);
                            }

                            /* JADX WARN: Multi-variable type inference failed */
                            @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
                            public V setValue(V v2) {
                                return (V) ((Map) next.getValue()).put(Column.this.columnKey, Preconditions.checkNotNull(v2));
                            }
                        };
                    }
                }
                return endOfData();
            }
        }

        public class KeySet extends Maps.KeySet<R, V> {
            public KeySet() {
                super(Column.this);
            }

            @Override // com.google.common.collect.Maps.KeySet, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(Object obj) {
                Column column = Column.this;
                return StandardTable.this.contains(obj, column.columnKey);
            }

            @Override // com.google.common.collect.Maps.KeySet, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean remove(Object obj) {
                Column column = Column.this;
                return StandardTable.this.remove(obj, column.columnKey) != null;
            }

            @Override // com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean retainAll(Collection<?> collection) {
                return Column.this.removeFromColumnIf(Maps.keyPredicateOnEntries(Predicates.not(Predicates.in(collection))));
            }
        }

        public class Values extends Maps.Values<R, V> {
            public Values() {
                super(Column.this);
            }

            @Override // com.google.common.collect.Maps.Values, java.util.AbstractCollection, java.util.Collection
            public boolean remove(Object obj) {
                return obj != null && Column.this.removeFromColumnIf(Maps.valuePredicateOnEntries(Predicates.equalTo(obj)));
            }

            @Override // com.google.common.collect.Maps.Values, java.util.AbstractCollection, java.util.Collection
            public boolean removeAll(Collection<?> collection) {
                return Column.this.removeFromColumnIf(Maps.valuePredicateOnEntries(Predicates.in(collection)));
            }

            @Override // com.google.common.collect.Maps.Values, java.util.AbstractCollection, java.util.Collection
            public boolean retainAll(Collection<?> collection) {
                return Column.this.removeFromColumnIf(Maps.valuePredicateOnEntries(Predicates.not(Predicates.in(collection))));
            }
        }

        public Column(C c3) {
            this.columnKey = (C) Preconditions.checkNotNull(c3);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(Object obj) {
            return StandardTable.this.contains(obj, this.columnKey);
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap
        public Set<Map.Entry<R, V>> createEntrySet() {
            return new EntrySet();
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap
        public Set<R> createKeySet() {
            return new KeySet();
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap
        public Collection<V> createValues() {
            return new Values();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V get(Object obj) {
            return (V) StandardTable.this.get(obj, this.columnKey);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V put(R r2, V v2) {
            return (V) StandardTable.this.put(r2, this.columnKey, v2);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V remove(Object obj) {
            return (V) StandardTable.this.remove(obj, this.columnKey);
        }

        @CanIgnoreReturnValue
        public boolean removeFromColumnIf(Predicate<? super Map.Entry<R, V>> predicate) {
            Iterator<Map.Entry<R, Map<C, V>>> it = StandardTable.this.backingMap.entrySet().iterator();
            boolean z2 = false;
            while (it.hasNext()) {
                Map.Entry<R, Map<C, V>> next = it.next();
                Map<C, V> value = next.getValue();
                V v2 = value.get(this.columnKey);
                if (v2 != null && predicate.apply(Maps.immutableEntry(next.getKey(), v2))) {
                    value.remove(this.columnKey);
                    if (value.isEmpty()) {
                        it.remove();
                    }
                    z2 = true;
                }
            }
            return z2;
        }
    }

    public class ColumnKeyIterator extends AbstractIterator<C> {
        Iterator<Map.Entry<C, V>> entryIterator;
        final Iterator<Map<C, V>> mapIterator;
        final Map<C, V> seen;

        private ColumnKeyIterator() {
            this.seen = StandardTable.this.factory.get();
            this.mapIterator = StandardTable.this.backingMap.values().iterator();
            this.entryIterator = Iterators.emptyIterator();
        }

        @Override // com.google.common.collect.AbstractIterator
        public C computeNext() {
            while (true) {
                if (this.entryIterator.hasNext()) {
                    Map.Entry<C, V> next = this.entryIterator.next();
                    if (!this.seen.containsKey(next.getKey())) {
                        this.seen.put(next.getKey(), next.getValue());
                        return next.getKey();
                    }
                } else {
                    if (!this.mapIterator.hasNext()) {
                        return endOfData();
                    }
                    this.entryIterator = this.mapIterator.next().entrySet().iterator();
                }
            }
        }
    }

    public class ColumnKeySet extends StandardTable<R, C, V>.TableSet<C> {
        private ColumnKeySet() {
            super();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return StandardTable.this.containsColumn(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<C> iterator() {
            return StandardTable.this.createColumnKeyIterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            boolean z2 = false;
            if (obj == null) {
                return false;
            }
            Iterator<Map<C, V>> it = StandardTable.this.backingMap.values().iterator();
            while (it.hasNext()) {
                Map<C, V> next = it.next();
                if (next.keySet().remove(obj)) {
                    if (next.isEmpty()) {
                        it.remove();
                    }
                    z2 = true;
                }
            }
            return z2;
        }

        @Override // com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean removeAll(Collection<?> collection) {
            Preconditions.checkNotNull(collection);
            Iterator<Map<C, V>> it = StandardTable.this.backingMap.values().iterator();
            boolean z2 = false;
            while (it.hasNext()) {
                Map<C, V> next = it.next();
                if (Iterators.removeAll(next.keySet().iterator(), collection)) {
                    if (next.isEmpty()) {
                        it.remove();
                    }
                    z2 = true;
                }
            }
            return z2;
        }

        @Override // com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean retainAll(Collection<?> collection) {
            Preconditions.checkNotNull(collection);
            Iterator<Map<C, V>> it = StandardTable.this.backingMap.values().iterator();
            boolean z2 = false;
            while (it.hasNext()) {
                Map<C, V> next = it.next();
                if (next.keySet().retainAll(collection)) {
                    if (next.isEmpty()) {
                        it.remove();
                    }
                    z2 = true;
                }
            }
            return z2;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Iterators.size(iterator());
        }
    }

    public class ColumnMap extends Maps.ViewCachingAbstractMap<C, Map<R, V>> {

        public class ColumnMapEntrySet extends StandardTable<R, C, V>.TableSet<Map.Entry<C, Map<R, V>>> {
            public ColumnMapEntrySet() {
                super();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(Object obj) {
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry entry = (Map.Entry) obj;
                if (!StandardTable.this.containsColumn(entry.getKey())) {
                    return false;
                }
                return ColumnMap.this.get(entry.getKey()).equals(entry.getValue());
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Map.Entry<C, Map<R, V>>> iterator() {
                return Maps.asMapEntryIterator(StandardTable.this.columnKeySet(), new Function<C, Map<R, V>>() { // from class: com.google.common.collect.StandardTable.ColumnMap.ColumnMapEntrySet.1
                    @Override // com.google.common.base.Function
                    public /* bridge */ /* synthetic */ Object apply(Object obj) {
                        return apply((AnonymousClass1) obj);
                    }

                    @Override // com.google.common.base.Function
                    public Map<R, V> apply(C c3) {
                        return StandardTable.this.column(c3);
                    }
                });
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean remove(Object obj) {
                if (!contains(obj)) {
                    return false;
                }
                StandardTable.this.removeColumn(((Map.Entry) obj).getKey());
                return true;
            }

            @Override // com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean removeAll(Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                return Sets.removeAllImpl(this, collection.iterator());
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean retainAll(Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                Iterator it = Lists.newArrayList(StandardTable.this.columnKeySet().iterator()).iterator();
                boolean z2 = false;
                while (it.hasNext()) {
                    Object next = it.next();
                    if (!collection.contains(Maps.immutableEntry(next, StandardTable.this.column(next)))) {
                        StandardTable.this.removeColumn(next);
                        z2 = true;
                    }
                }
                return z2;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return StandardTable.this.columnKeySet().size();
            }
        }

        public class ColumnMapValues extends Maps.Values<C, Map<R, V>> {
            public ColumnMapValues() {
                super(ColumnMap.this);
            }

            @Override // com.google.common.collect.Maps.Values, java.util.AbstractCollection, java.util.Collection
            public boolean remove(Object obj) {
                for (Map.Entry<C, Map<R, V>> entry : ColumnMap.this.entrySet()) {
                    if (entry.getValue().equals(obj)) {
                        StandardTable.this.removeColumn(entry.getKey());
                        return true;
                    }
                }
                return false;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.Maps.Values, java.util.AbstractCollection, java.util.Collection
            public boolean removeAll(Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                Iterator it = Lists.newArrayList(StandardTable.this.columnKeySet().iterator()).iterator();
                boolean z2 = false;
                while (it.hasNext()) {
                    Object next = it.next();
                    if (collection.contains(StandardTable.this.column(next))) {
                        StandardTable.this.removeColumn(next);
                        z2 = true;
                    }
                }
                return z2;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.Maps.Values, java.util.AbstractCollection, java.util.Collection
            public boolean retainAll(Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                Iterator it = Lists.newArrayList(StandardTable.this.columnKeySet().iterator()).iterator();
                boolean z2 = false;
                while (it.hasNext()) {
                    Object next = it.next();
                    if (!collection.contains(StandardTable.this.column(next))) {
                        StandardTable.this.removeColumn(next);
                        z2 = true;
                    }
                }
                return z2;
            }
        }

        private ColumnMap() {
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(Object obj) {
            return StandardTable.this.containsColumn(obj);
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap
        public Set<Map.Entry<C, Map<R, V>>> createEntrySet() {
            return new ColumnMapEntrySet();
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap
        public Collection<Map<R, V>> createValues() {
            return new ColumnMapValues();
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap, java.util.AbstractMap, java.util.Map
        public Set<C> keySet() {
            return StandardTable.this.columnKeySet();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Map<R, V> get(Object obj) {
            if (StandardTable.this.containsColumn(obj)) {
                return StandardTable.this.column(obj);
            }
            return null;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Map<R, V> remove(Object obj) {
            if (StandardTable.this.containsColumn(obj)) {
                return StandardTable.this.removeColumn(obj);
            }
            return null;
        }
    }

    public class Row extends Maps.IteratorBasedAbstractMap<C, V> {

        @NullableDecl
        Map<C, V> backingRowMap;
        final R rowKey;

        public Row(R r2) {
            this.rowKey = (R) Preconditions.checkNotNull(r2);
        }

        public Map<C, V> backingRowMap() {
            Map<C, V> map = this.backingRowMap;
            if (map != null && (!map.isEmpty() || !StandardTable.this.backingMap.containsKey(this.rowKey))) {
                return this.backingRowMap;
            }
            Map<C, V> mapComputeBackingRowMap = computeBackingRowMap();
            this.backingRowMap = mapComputeBackingRowMap;
            return mapComputeBackingRowMap;
        }

        @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap, java.util.AbstractMap, java.util.Map
        public void clear() {
            Map<C, V> mapBackingRowMap = backingRowMap();
            if (mapBackingRowMap != null) {
                mapBackingRowMap.clear();
            }
            maintainEmptyInvariant();
        }

        public Map<C, V> computeBackingRowMap() {
            return StandardTable.this.backingMap.get(this.rowKey);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(Object obj) {
            Map<C, V> mapBackingRowMap = backingRowMap();
            return (obj == null || mapBackingRowMap == null || !Maps.safeContainsKey(mapBackingRowMap, obj)) ? false : true;
        }

        @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap
        public Iterator<Map.Entry<C, V>> entryIterator() {
            Map<C, V> mapBackingRowMap = backingRowMap();
            if (mapBackingRowMap == null) {
                return Iterators.emptyModifiableIterator();
            }
            final Iterator<Map.Entry<C, V>> it = mapBackingRowMap.entrySet().iterator();
            return new Iterator<Map.Entry<C, V>>() { // from class: com.google.common.collect.StandardTable.Row.1
                @Override // java.util.Iterator
                public boolean hasNext() {
                    return it.hasNext();
                }

                @Override // java.util.Iterator
                public void remove() {
                    it.remove();
                    Row.this.maintainEmptyInvariant();
                }

                @Override // java.util.Iterator
                public Map.Entry<C, V> next() {
                    return Row.this.wrapEntry((Map.Entry) it.next());
                }
            };
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V get(Object obj) {
            Map<C, V> mapBackingRowMap = backingRowMap();
            if (obj == null || mapBackingRowMap == null) {
                return null;
            }
            return (V) Maps.safeGet(mapBackingRowMap, obj);
        }

        public void maintainEmptyInvariant() {
            if (backingRowMap() == null || !this.backingRowMap.isEmpty()) {
                return;
            }
            StandardTable.this.backingMap.remove(this.rowKey);
            this.backingRowMap = null;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V put(C c3, V v2) {
            Preconditions.checkNotNull(c3);
            Preconditions.checkNotNull(v2);
            Map<C, V> map = this.backingRowMap;
            return (map == null || map.isEmpty()) ? (V) StandardTable.this.put(this.rowKey, c3, v2) : this.backingRowMap.put(c3, v2);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V remove(Object obj) {
            Map<C, V> mapBackingRowMap = backingRowMap();
            if (mapBackingRowMap == null) {
                return null;
            }
            V v2 = (V) Maps.safeRemove(mapBackingRowMap, obj);
            maintainEmptyInvariant();
            return v2;
        }

        @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap, java.util.AbstractMap, java.util.Map
        public int size() {
            Map<C, V> mapBackingRowMap = backingRowMap();
            if (mapBackingRowMap == null) {
                return 0;
            }
            return mapBackingRowMap.size();
        }

        public Map.Entry<C, V> wrapEntry(final Map.Entry<C, V> entry) {
            return new ForwardingMapEntry<C, V>() { // from class: com.google.common.collect.StandardTable.Row.2
                @Override // com.google.common.collect.ForwardingMapEntry, java.util.Map.Entry
                public boolean equals(Object obj) {
                    return standardEquals(obj);
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // com.google.common.collect.ForwardingMapEntry, java.util.Map.Entry
                public V setValue(V v2) {
                    return (V) super.setValue(Preconditions.checkNotNull(v2));
                }

                @Override // com.google.common.collect.ForwardingMapEntry, com.google.common.collect.ForwardingObject
                public Map.Entry<C, V> delegate() {
                    return entry;
                }
            };
        }
    }

    public class RowMap extends Maps.ViewCachingAbstractMap<R, Map<C, V>> {

        public class EntrySet extends StandardTable<R, C, V>.TableSet<Map.Entry<R, Map<C, V>>> {
            public EntrySet() {
                super();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(Object obj) {
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry entry = (Map.Entry) obj;
                return entry.getKey() != null && (entry.getValue() instanceof Map) && Collections2.safeContains(StandardTable.this.backingMap.entrySet(), entry);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Map.Entry<R, Map<C, V>>> iterator() {
                return Maps.asMapEntryIterator(StandardTable.this.backingMap.keySet(), new Function<R, Map<C, V>>() { // from class: com.google.common.collect.StandardTable.RowMap.EntrySet.1
                    @Override // com.google.common.base.Function
                    public /* bridge */ /* synthetic */ Object apply(Object obj) {
                        return apply((AnonymousClass1) obj);
                    }

                    @Override // com.google.common.base.Function
                    public Map<C, V> apply(R r2) {
                        return StandardTable.this.row(r2);
                    }
                });
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean remove(Object obj) {
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry entry = (Map.Entry) obj;
                return entry.getKey() != null && (entry.getValue() instanceof Map) && StandardTable.this.backingMap.entrySet().remove(entry);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return StandardTable.this.backingMap.size();
            }
        }

        public RowMap() {
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(Object obj) {
            return StandardTable.this.containsRow(obj);
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap
        public Set<Map.Entry<R, Map<C, V>>> createEntrySet() {
            return new EntrySet();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Map<C, V> get(Object obj) {
            if (StandardTable.this.containsRow(obj)) {
                return StandardTable.this.row(obj);
            }
            return null;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Map<C, V> remove(Object obj) {
            if (obj == null) {
                return null;
            }
            return StandardTable.this.backingMap.remove(obj);
        }
    }

    public abstract class TableSet<T> extends Sets.ImprovedAbstractSet<T> {
        private TableSet() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            StandardTable.this.backingMap.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return StandardTable.this.backingMap.isEmpty();
        }
    }

    public StandardTable(Map<R, Map<C, V>> map, Supplier<? extends Map<C, V>> supplier) {
        this.backingMap = map;
        this.factory = supplier;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean containsMapping(Object obj, Object obj2, Object obj3) {
        return obj3 != null && obj3.equals(get(obj, obj2));
    }

    private Map<C, V> getOrCreate(R r2) {
        Map<C, V> map = this.backingMap.get(r2);
        if (map != null) {
            return map;
        }
        Map<C, V> map2 = this.factory.get();
        this.backingMap.put(r2, map2);
        return map2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @CanIgnoreReturnValue
    public Map<R, V> removeColumn(Object obj) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator<Map.Entry<R, Map<C, V>>> it = this.backingMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<R, Map<C, V>> next = it.next();
            V vRemove = next.getValue().remove(obj);
            if (vRemove != null) {
                linkedHashMap.put(next.getKey(), vRemove);
                if (next.getValue().isEmpty()) {
                    it.remove();
                }
            }
        }
        return linkedHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean removeMapping(Object obj, Object obj2, Object obj3) {
        if (!containsMapping(obj, obj2, obj3)) {
            return false;
        }
        remove(obj, obj2);
        return true;
    }

    @Override // com.google.common.collect.AbstractTable
    public Iterator<Table.Cell<R, C, V>> cellIterator() {
        return new CellIterator();
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public Set<Table.Cell<R, C, V>> cellSet() {
        return super.cellSet();
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public void clear() {
        this.backingMap.clear();
    }

    @Override // com.google.common.collect.Table
    public Map<R, V> column(C c3) {
        return new Column(c3);
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public Set<C> columnKeySet() {
        Set<C> set = this.columnKeySet;
        if (set != null) {
            return set;
        }
        ColumnKeySet columnKeySet = new ColumnKeySet();
        this.columnKeySet = columnKeySet;
        return columnKeySet;
    }

    @Override // com.google.common.collect.Table
    public Map<C, Map<R, V>> columnMap() {
        StandardTable<R, C, V>.ColumnMap columnMap = this.columnMap;
        if (columnMap != null) {
            return columnMap;
        }
        StandardTable<R, C, V>.ColumnMap columnMap2 = new ColumnMap();
        this.columnMap = columnMap2;
        return columnMap2;
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public boolean contains(@NullableDecl Object obj, @NullableDecl Object obj2) {
        return (obj == null || obj2 == null || !super.contains(obj, obj2)) ? false : true;
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public boolean containsColumn(@NullableDecl Object obj) {
        if (obj == null) {
            return false;
        }
        Iterator<Map<C, V>> it = this.backingMap.values().iterator();
        while (it.hasNext()) {
            if (Maps.safeContainsKey(it.next(), obj)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public boolean containsRow(@NullableDecl Object obj) {
        return obj != null && Maps.safeContainsKey(this.backingMap, obj);
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public boolean containsValue(@NullableDecl Object obj) {
        return obj != null && super.containsValue(obj);
    }

    public Iterator<C> createColumnKeyIterator() {
        return new ColumnKeyIterator();
    }

    public Map<R, Map<C, V>> createRowMap() {
        return new RowMap();
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public V get(@NullableDecl Object obj, @NullableDecl Object obj2) {
        if (obj == null || obj2 == null) {
            return null;
        }
        return (V) super.get(obj, obj2);
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public boolean isEmpty() {
        return this.backingMap.isEmpty();
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    @CanIgnoreReturnValue
    public V put(R r2, C c3, V v2) {
        Preconditions.checkNotNull(r2);
        Preconditions.checkNotNull(c3);
        Preconditions.checkNotNull(v2);
        return getOrCreate(r2).put(c3, v2);
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    @CanIgnoreReturnValue
    public V remove(@NullableDecl Object obj, @NullableDecl Object obj2) {
        Map map;
        if (obj == null || obj2 == null || (map = (Map) Maps.safeGet(this.backingMap, obj)) == null) {
            return null;
        }
        V v2 = (V) map.remove(obj2);
        if (map.isEmpty()) {
            this.backingMap.remove(obj);
        }
        return v2;
    }

    @Override // com.google.common.collect.Table
    public Map<C, V> row(R r2) {
        return new Row(r2);
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public Set<R> rowKeySet() {
        return rowMap().keySet();
    }

    @Override // com.google.common.collect.Table
    public Map<R, Map<C, V>> rowMap() {
        Map<R, Map<C, V>> map = this.rowMap;
        if (map != null) {
            return map;
        }
        Map<R, Map<C, V>> mapCreateRowMap = createRowMap();
        this.rowMap = mapCreateRowMap;
        return mapCreateRowMap;
    }

    @Override // com.google.common.collect.Table
    public int size() {
        Iterator<Map<C, V>> it = this.backingMap.values().iterator();
        int size = 0;
        while (it.hasNext()) {
            size += it.next().size();
        }
        return size;
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public Collection<V> values() {
        return super.values();
    }
}
