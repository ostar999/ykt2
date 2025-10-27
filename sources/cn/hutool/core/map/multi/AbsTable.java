package cn.hutool.core.map.multi;

import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.collection.TransIter;
import cn.hutool.core.lang.func.Consumer3;
import cn.hutool.core.map.multi.Table;
import cn.hutool.core.util.ObjectUtil;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

/* loaded from: classes.dex */
public abstract class AbsTable<R, C, V> implements Table<R, C, V> {
    private Set<Table.Cell<R, C, V>> cellSet;
    private Collection<V> values;

    public class CellIterator implements Iterator<Table.Cell<R, C, V>> {
        Iterator<Map.Entry<C, V>> columnIterator;
        Map.Entry<R, Map<C, V>> rowEntry;
        final Iterator<Map.Entry<R, Map<C, V>>> rowIterator;

        private CellIterator() {
            this.rowIterator = AbsTable.this.rowMap().entrySet().iterator();
            this.columnIterator = IterUtil.empty();
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
            return new SimpleCell(this.rowEntry.getKey(), next2.getKey(), next2.getValue());
        }
    }

    public class CellSet extends AbstractSet<Table.Cell<R, C, V>> {
        private CellSet() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            AbsTable.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            if (!(obj instanceof Table.Cell)) {
                return false;
            }
            Table.Cell cell = (Table.Cell) obj;
            Map row = AbsTable.this.getRow(cell.getRowKey());
            if (row != null) {
                return ObjectUtil.equals(row.get(cell.getColumnKey()), cell.getValue());
            }
            return false;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Table.Cell<R, C, V>> iterator() {
            return new CellIterator();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            if (!contains(obj)) {
                return false;
            }
            Table.Cell cell = (Table.Cell) obj;
            AbsTable.this.remove(cell.getRowKey(), cell.getColumnKey());
            return false;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return AbsTable.this.size();
        }
    }

    public static class SimpleCell<R, C, V> implements Table.Cell<R, C, V>, Serializable {
        private static final long serialVersionUID = 1;
        private final C columnKey;
        private final R rowKey;
        private final V value;

        public SimpleCell(R r2, C c3, V v2) {
            this.rowKey = r2;
            this.columnKey = c3;
            this.value = v2;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Table.Cell)) {
                return false;
            }
            Table.Cell cell = (Table.Cell) obj;
            return ObjectUtil.equal(this.rowKey, cell.getRowKey()) && ObjectUtil.equal(this.columnKey, cell.getColumnKey()) && ObjectUtil.equal(this.value, cell.getValue());
        }

        @Override // cn.hutool.core.map.multi.Table.Cell
        public C getColumnKey() {
            return this.columnKey;
        }

        @Override // cn.hutool.core.map.multi.Table.Cell
        public R getRowKey() {
            return this.rowKey;
        }

        @Override // cn.hutool.core.map.multi.Table.Cell
        public V getValue() {
            return this.value;
        }

        public int hashCode() {
            return Objects.hash(this.rowKey, this.columnKey, this.value);
        }

        public String toString() {
            return "(" + this.rowKey + "," + this.columnKey + ")=" + this.value;
        }
    }

    public class Values extends AbstractCollection<V> {
        private Values() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            AbsTable.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object obj) {
            return AbsTable.this.containsValue(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            return new TransIter(AbsTable.this.cellSet().iterator(), new Function() { // from class: cn.hutool.core.map.multi.c
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((Table.Cell) obj).getValue();
                }
            });
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return AbsTable.this.size();
        }
    }

    @Override // cn.hutool.core.map.multi.Table
    public Set<Table.Cell<R, C, V>> cellSet() {
        Set<Table.Cell<R, C, V>> set = this.cellSet;
        if (set != null) {
            return set;
        }
        CellSet cellSet = new CellSet();
        this.cellSet = cellSet;
        return cellSet;
    }

    @Override // cn.hutool.core.map.multi.Table
    public /* synthetic */ Set columnKeySet() {
        return q.a(this);
    }

    @Override // cn.hutool.core.map.multi.Table
    public /* synthetic */ List columnKeys() {
        return q.b(this);
    }

    @Override // cn.hutool.core.map.multi.Table
    public /* synthetic */ boolean contains(Object obj, Object obj2) {
        return q.c(this, obj, obj2);
    }

    @Override // cn.hutool.core.map.multi.Table
    public /* synthetic */ boolean containsColumn(Object obj) {
        return q.d(this, obj);
    }

    @Override // cn.hutool.core.map.multi.Table
    public /* synthetic */ boolean containsRow(Object obj) {
        return q.e(this, obj);
    }

    @Override // cn.hutool.core.map.multi.Table
    public /* synthetic */ boolean containsValue(Object obj) {
        return q.f(this, obj);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Table) {
            return cellSet().equals(((Table) obj).cellSet());
        }
        return false;
    }

    @Override // cn.hutool.core.map.multi.Table
    public /* synthetic */ void forEach(Consumer3 consumer3) {
        q.g(this, consumer3);
    }

    @Override // cn.hutool.core.map.multi.Table
    public /* synthetic */ Object get(Object obj, Object obj2) {
        return q.h(this, obj, obj2);
    }

    @Override // cn.hutool.core.map.multi.Table
    public /* synthetic */ Map getColumn(Object obj) {
        return q.i(this, obj);
    }

    @Override // cn.hutool.core.map.multi.Table
    public /* synthetic */ Map getRow(Object obj) {
        return q.j(this, obj);
    }

    public int hashCode() {
        return cellSet().hashCode();
    }

    @Override // java.lang.Iterable
    public Iterator<Table.Cell<R, C, V>> iterator() {
        return new CellIterator();
    }

    @Override // cn.hutool.core.map.multi.Table
    public /* synthetic */ void putAll(Table table) {
        q.k(this, table);
    }

    @Override // cn.hutool.core.map.multi.Table
    public /* synthetic */ Set rowKeySet() {
        return q.l(this);
    }

    @Override // cn.hutool.core.map.multi.Table
    public /* synthetic */ int size() {
        return q.m(this);
    }

    public String toString() {
        return rowMap().toString();
    }

    @Override // cn.hutool.core.map.multi.Table
    public Collection<V> values() {
        Collection<V> collection = this.values;
        if (collection != null) {
            return collection;
        }
        Values values = new Values();
        this.values = values;
        return values;
    }
}
