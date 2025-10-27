package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Table;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes4.dex */
abstract class RegularImmutableTable<R, C, V> extends ImmutableTable<R, C, V> {

    public final class CellSet extends IndexedImmutableSet<Table.Cell<R, C, V>> {
        private CellSet() {
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@NullableDecl Object obj) {
            if (!(obj instanceof Table.Cell)) {
                return false;
            }
            Table.Cell cell = (Table.Cell) obj;
            Object obj2 = RegularImmutableTable.this.get(cell.getRowKey(), cell.getColumnKey());
            return obj2 != null && obj2.equals(cell.getValue());
        }

        @Override // com.google.common.collect.ImmutableCollection
        public boolean isPartialView() {
            return false;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return RegularImmutableTable.this.size();
        }

        @Override // com.google.common.collect.IndexedImmutableSet
        public Table.Cell<R, C, V> get(int i2) {
            return RegularImmutableTable.this.getCell(i2);
        }
    }

    public final class Values extends ImmutableList<V> {
        private Values() {
        }

        @Override // java.util.List
        public V get(int i2) {
            return (V) RegularImmutableTable.this.getValue(i2);
        }

        @Override // com.google.common.collect.ImmutableCollection
        public boolean isPartialView() {
            return true;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return RegularImmutableTable.this.size();
        }
    }

    public static <R, C, V> RegularImmutableTable<R, C, V> forCells(List<Table.Cell<R, C, V>> list, @NullableDecl final Comparator<? super R> comparator, @NullableDecl final Comparator<? super C> comparator2) {
        Preconditions.checkNotNull(list);
        if (comparator != null || comparator2 != null) {
            Collections.sort(list, new Comparator<Table.Cell<R, C, V>>() { // from class: com.google.common.collect.RegularImmutableTable.1
                @Override // java.util.Comparator
                public int compare(Table.Cell<R, C, V> cell, Table.Cell<R, C, V> cell2) {
                    Comparator comparator3 = comparator;
                    int iCompare = comparator3 == null ? 0 : comparator3.compare(cell.getRowKey(), cell2.getRowKey());
                    if (iCompare != 0) {
                        return iCompare;
                    }
                    Comparator comparator4 = comparator2;
                    if (comparator4 == null) {
                        return 0;
                    }
                    return comparator4.compare(cell.getColumnKey(), cell2.getColumnKey());
                }
            });
        }
        return forCellsInternal(list, comparator, comparator2);
    }

    private static <R, C, V> RegularImmutableTable<R, C, V> forCellsInternal(Iterable<Table.Cell<R, C, V>> iterable, @NullableDecl Comparator<? super R> comparator, @NullableDecl Comparator<? super C> comparator2) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        ImmutableList immutableListCopyOf = ImmutableList.copyOf(iterable);
        for (Table.Cell<R, C, V> cell : iterable) {
            linkedHashSet.add(cell.getRowKey());
            linkedHashSet2.add(cell.getColumnKey());
        }
        return forOrderedComponents(immutableListCopyOf, comparator == null ? ImmutableSet.copyOf((Collection) linkedHashSet) : ImmutableSet.copyOf((Collection) ImmutableList.sortedCopyOf(comparator, linkedHashSet)), comparator2 == null ? ImmutableSet.copyOf((Collection) linkedHashSet2) : ImmutableSet.copyOf((Collection) ImmutableList.sortedCopyOf(comparator2, linkedHashSet2)));
    }

    public static <R, C, V> RegularImmutableTable<R, C, V> forOrderedComponents(ImmutableList<Table.Cell<R, C, V>> immutableList, ImmutableSet<R> immutableSet, ImmutableSet<C> immutableSet2) {
        return ((long) immutableList.size()) > (((long) immutableSet.size()) * ((long) immutableSet2.size())) / 2 ? new DenseImmutableTable(immutableList, immutableSet, immutableSet2) : new SparseImmutableTable(immutableList, immutableSet, immutableSet2);
    }

    public final void checkNoDuplicate(R r2, C c3, V v2, V v3) {
        Preconditions.checkArgument(v2 == null, "Duplicate key: (row=%s, column=%s), values: [%s, %s].", r2, c3, v3, v2);
    }

    public abstract Table.Cell<R, C, V> getCell(int i2);

    public abstract V getValue(int i2);

    @Override // com.google.common.collect.ImmutableTable, com.google.common.collect.AbstractTable
    public final ImmutableSet<Table.Cell<R, C, V>> createCellSet() {
        return isEmpty() ? ImmutableSet.of() : new CellSet();
    }

    @Override // com.google.common.collect.ImmutableTable, com.google.common.collect.AbstractTable
    public final ImmutableCollection<V> createValues() {
        return isEmpty() ? ImmutableList.of() : new Values();
    }

    public static <R, C, V> RegularImmutableTable<R, C, V> forCells(Iterable<Table.Cell<R, C, V>> iterable) {
        return forCellsInternal(iterable, null, null);
    }
}
