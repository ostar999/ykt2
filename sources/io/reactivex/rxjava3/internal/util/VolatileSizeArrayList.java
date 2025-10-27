package io.reactivex.rxjava3.internal.util;

import io.reactivex.rxjava3.annotations.NonNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes8.dex */
public final class VolatileSizeArrayList<T> extends AtomicInteger implements List<T>, RandomAccess {
    private static final long serialVersionUID = 3972397474470203923L;
    final ArrayList<T> list;

    public VolatileSizeArrayList() {
        this.list = new ArrayList<>();
    }

    @Override // java.util.List, java.util.Collection
    public boolean add(T e2) {
        boolean zAdd = this.list.add(e2);
        lazySet(this.list.size());
        return zAdd;
    }

    @Override // java.util.List, java.util.Collection
    public boolean addAll(@NonNull Collection<? extends T> c3) {
        boolean zAddAll = this.list.addAll(c3);
        lazySet(this.list.size());
        return zAddAll;
    }

    @Override // java.util.List, java.util.Collection
    public void clear() {
        this.list.clear();
        lazySet(0);
    }

    @Override // java.util.List, java.util.Collection
    public boolean contains(Object o2) {
        return this.list.contains(o2);
    }

    @Override // java.util.List, java.util.Collection
    public boolean containsAll(@NonNull Collection<?> c3) {
        return this.list.containsAll(c3);
    }

    @Override // java.util.List, java.util.Collection
    public boolean equals(Object obj) {
        return obj instanceof VolatileSizeArrayList ? this.list.equals(((VolatileSizeArrayList) obj).list) : this.list.equals(obj);
    }

    @Override // java.util.List
    public T get(int index) {
        return this.list.get(index);
    }

    @Override // java.util.List, java.util.Collection
    public int hashCode() {
        return this.list.hashCode();
    }

    @Override // java.util.List
    public int indexOf(Object o2) {
        return this.list.indexOf(o2);
    }

    @Override // java.util.List, java.util.Collection
    public boolean isEmpty() {
        return get() == 0;
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public Iterator<T> iterator() {
        return this.list.iterator();
    }

    @Override // java.util.List
    public int lastIndexOf(Object o2) {
        return this.list.lastIndexOf(o2);
    }

    @Override // java.util.List
    public ListIterator<T> listIterator() {
        return this.list.listIterator();
    }

    @Override // java.util.List, java.util.Collection
    public boolean remove(Object o2) {
        boolean zRemove = this.list.remove(o2);
        lazySet(this.list.size());
        return zRemove;
    }

    @Override // java.util.List, java.util.Collection
    public boolean removeAll(@NonNull Collection<?> c3) {
        boolean zRemoveAll = this.list.removeAll(c3);
        lazySet(this.list.size());
        return zRemoveAll;
    }

    @Override // java.util.List, java.util.Collection
    public boolean retainAll(@NonNull Collection<?> c3) {
        boolean zRetainAll = this.list.retainAll(c3);
        lazySet(this.list.size());
        return zRetainAll;
    }

    @Override // java.util.List
    public T set(int index, T element) {
        return this.list.set(index, element);
    }

    @Override // java.util.List, java.util.Collection
    public int size() {
        return get();
    }

    @Override // java.util.List
    public List<T> subList(int fromIndex, int toIndex) {
        return this.list.subList(fromIndex, toIndex);
    }

    @Override // java.util.List, java.util.Collection
    public Object[] toArray() {
        return this.list.toArray();
    }

    @Override // java.util.concurrent.atomic.AtomicInteger
    public String toString() {
        return this.list.toString();
    }

    @Override // java.util.List
    public ListIterator<T> listIterator(int index) {
        return this.list.listIterator(index);
    }

    @Override // java.util.List, java.util.Collection
    public <E> E[] toArray(@NonNull E[] eArr) {
        return (E[]) this.list.toArray(eArr);
    }

    public VolatileSizeArrayList(int initialCapacity) {
        this.list = new ArrayList<>(initialCapacity);
    }

    @Override // java.util.List
    public void add(int index, T element) {
        this.list.add(index, element);
        lazySet(this.list.size());
    }

    @Override // java.util.List
    public boolean addAll(int index, @NonNull Collection<? extends T> c3) {
        boolean zAddAll = this.list.addAll(index, c3);
        lazySet(this.list.size());
        return zAddAll;
    }

    @Override // java.util.List
    public T remove(int index) {
        T tRemove = this.list.remove(index);
        lazySet(this.list.size());
        return tRemove;
    }
}
