package cn.hutool.core.map.multi;

import cn.hutool.core.builder.Builder;
import cn.hutool.core.collection.ComputeIter;
import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.collection.TransIter;
import cn.hutool.core.map.AbsEntry;
import cn.hutool.core.map.MapUtil;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

/* loaded from: classes.dex */
public class RowKeyTable<R, C, V> extends AbsTable<R, C, V> {
    final Builder<? extends Map<C, V>> columnBuilder;
    private Set<C> columnKeySet;
    private Map<C, Map<R, V>> columnMap;
    final Map<R, Map<C, V>> raw;

    public class Column extends AbstractMap<R, V> {
        final C columnKey;

        public class EntrySet extends AbstractSet<Map.Entry<R, V>> {
            private EntrySet() {
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Map.Entry<R, V>> iterator() {
                return new EntrySetIterator();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                Iterator<Map<C, V>> it = RowKeyTable.this.raw.values().iterator();
                int i2 = 0;
                while (it.hasNext()) {
                    if (it.next().containsKey(Column.this.columnKey)) {
                        i2++;
                    }
                }
                return i2;
            }
        }

        public class EntrySetIterator extends ComputeIter<Map.Entry<R, V>> {
            final Iterator<Map.Entry<R, Map<C, V>>> iterator;

            private EntrySetIterator() {
                this.iterator = RowKeyTable.this.raw.entrySet().iterator();
            }

            @Override // cn.hutool.core.collection.ComputeIter
            public Map.Entry<R, V> computeNext() {
                while (this.iterator.hasNext()) {
                    final Map.Entry<R, Map<C, V>> next = this.iterator.next();
                    if (next.getValue().containsKey(Column.this.columnKey)) {
                        return new AbsEntry<R, V>() { // from class: cn.hutool.core.map.multi.RowKeyTable.Column.EntrySetIterator.1
                            @Override // java.util.Map.Entry
                            public R getKey() {
                                return (R) next.getKey();
                            }

                            @Override // java.util.Map.Entry
                            public V getValue() {
                                return (V) ((Map) next.getValue()).get(Column.this.columnKey);
                            }

                            @Override // cn.hutool.core.map.AbsEntry, java.util.Map.Entry
                            public V setValue(V v2) {
                                return (V) ((Map) next.getValue()).put(Column.this.columnKey, v2);
                            }
                        };
                    }
                }
                return null;
            }
        }

        public Column(C c3) {
            this.columnKey = c3;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<Map.Entry<R, V>> entrySet() {
            return new EntrySet();
        }
    }

    public class ColumnKeyIterator extends ComputeIter<C> {
        Iterator<Map.Entry<C, V>> entryIterator;
        final Iterator<Map<C, V>> mapIterator;
        final Map<C, V> seen;

        private ColumnKeyIterator() {
            this.seen = RowKeyTable.this.columnBuilder.build();
            this.mapIterator = RowKeyTable.this.raw.values().iterator();
            this.entryIterator = IterUtil.empty();
        }

        @Override // cn.hutool.core.collection.ComputeIter
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
                        return null;
                    }
                    this.entryIterator = this.mapIterator.next().entrySet().iterator();
                }
            }
        }
    }

    public class ColumnKeySet extends AbstractSet<C> {
        private ColumnKeySet() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<C> iterator() {
            return new ColumnKeyIterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return IterUtil.size((Iterator<?>) iterator());
        }
    }

    public class ColumnMap extends AbstractMap<C, Map<R, V>> {
        private ColumnMap() {
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<Map.Entry<C, Map<R, V>>> entrySet() {
            return new ColumnMapEntrySet();
        }
    }

    public class ColumnMapEntrySet extends AbstractSet<Map.Entry<C, Map<R, V>>> {
        private final Set<C> columnKeySet;

        private ColumnMapEntrySet() {
            this.columnKeySet = RowKeyTable.this.columnKeySet();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Map.Entry lambda$iterator$0(Object obj) {
            return MapUtil.entry(obj, RowKeyTable.this.getColumn(obj));
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<C, Map<R, V>>> iterator() {
            return new TransIter(this.columnKeySet.iterator(), new Function() { // from class: cn.hutool.core.map.multi.i
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f2542c.lambda$iterator$0(obj);
                }
            });
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return this.columnKeySet.size();
        }
    }

    public RowKeyTable() {
        this(new HashMap());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Map lambda$put$0(Object obj) {
        return this.columnBuilder.build();
    }

    @Override // cn.hutool.core.map.multi.Table
    public void clear() {
        this.raw.clear();
    }

    @Override // cn.hutool.core.map.multi.AbsTable, cn.hutool.core.map.multi.Table
    public Set<C> columnKeySet() {
        Set<C> set = this.columnKeySet;
        if (set != null) {
            return set;
        }
        ColumnKeySet columnKeySet = new ColumnKeySet();
        this.columnKeySet = columnKeySet;
        return columnKeySet;
    }

    @Override // cn.hutool.core.map.multi.AbsTable, cn.hutool.core.map.multi.Table
    public List<C> columnKeys() {
        Collection<Map<C, V>> collectionValues = this.raw.values();
        final ArrayList arrayList = new ArrayList(collectionValues.size() * 16);
        Iterator<Map<C, V>> it = collectionValues.iterator();
        while (it.hasNext()) {
            it.next().forEach(new BiConsumer() { // from class: cn.hutool.core.map.multi.g
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    arrayList.add(obj);
                }
            });
        }
        return arrayList;
    }

    @Override // cn.hutool.core.map.multi.Table
    public Map<C, Map<R, V>> columnMap() {
        Map<C, Map<R, V>> map = this.columnMap;
        if (map != null) {
            return map;
        }
        ColumnMap columnMap = new ColumnMap();
        this.columnMap = columnMap;
        return columnMap;
    }

    @Override // cn.hutool.core.map.multi.AbsTable, cn.hutool.core.map.multi.Table
    public boolean containsColumn(C c3) {
        if (c3 == null) {
            return false;
        }
        for (Map<C, V> map : this.raw.values()) {
            if (map != null && map.containsKey(c3)) {
                return true;
            }
        }
        return false;
    }

    @Override // cn.hutool.core.map.multi.AbsTable, cn.hutool.core.map.multi.Table
    public Map<R, V> getColumn(C c3) {
        return new Column(c3);
    }

    @Override // cn.hutool.core.map.multi.Table
    public boolean isEmpty() {
        return this.raw.isEmpty();
    }

    @Override // cn.hutool.core.map.multi.Table
    public V put(R r2, C c3, V v2) {
        return (V) ((Map) this.raw.computeIfAbsent(r2, new Function() { // from class: cn.hutool.core.map.multi.h
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f2541c.lambda$put$0(obj);
            }
        })).put(c3, v2);
    }

    @Override // cn.hutool.core.map.multi.Table
    public V remove(R r2, C c3) {
        Map row = getRow(r2);
        if (row == null) {
            return null;
        }
        V v2 = (V) row.remove(c3);
        if (row.isEmpty()) {
            this.raw.remove(r2);
        }
        return v2;
    }

    @Override // cn.hutool.core.map.multi.Table
    public Map<R, Map<C, V>> rowMap() {
        return this.raw;
    }

    public RowKeyTable(boolean z2) {
        this(MapUtil.newHashMap(z2), new e(z2));
    }

    public RowKeyTable(Map<R, Map<C, V>> map) {
        this(map, new f());
    }

    public RowKeyTable(Map<R, Map<C, V>> map, Builder<? extends Map<C, V>> builder) {
        this.raw = map;
        this.columnBuilder = builder == null ? new f() : builder;
    }
}
