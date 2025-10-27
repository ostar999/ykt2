package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.LazyInit;

@GwtCompatible(emulated = true, serializable = true)
/* loaded from: classes4.dex */
final class SingletonImmutableSet<E> extends ImmutableSet<E> {

    @LazyInit
    private transient int cachedHashCode;
    final transient E element;

    public SingletonImmutableSet(E e2) {
        this.element = (E) Preconditions.checkNotNull(e2);
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(Object obj) {
        return this.element.equals(obj);
    }

    @Override // com.google.common.collect.ImmutableCollection
    public int copyIntoArray(Object[] objArr, int i2) {
        objArr[i2] = this.element;
        return i2 + 1;
    }

    @Override // com.google.common.collect.ImmutableSet
    public ImmutableList<E> createAsList() {
        return ImmutableList.of((Object) this.element);
    }

    @Override // com.google.common.collect.ImmutableSet, java.util.Collection, java.util.Set
    public final int hashCode() {
        int i2 = this.cachedHashCode;
        if (i2 != 0) {
            return i2;
        }
        int iHashCode = this.element.hashCode();
        this.cachedHashCode = iHashCode;
        return iHashCode;
    }

    @Override // com.google.common.collect.ImmutableSet
    public boolean isHashCodeFast() {
        return this.cachedHashCode != 0;
    }

    @Override // com.google.common.collect.ImmutableCollection
    public boolean isPartialView() {
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return 1;
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        return '[' + this.element.toString() + ']';
    }

    @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set, java.util.NavigableSet, com.google.common.collect.SortedIterable
    public UnmodifiableIterator<E> iterator() {
        return Iterators.singletonIterator(this.element);
    }

    public SingletonImmutableSet(E e2, int i2) {
        this.element = e2;
        this.cachedHashCode = i2;
    }
}
