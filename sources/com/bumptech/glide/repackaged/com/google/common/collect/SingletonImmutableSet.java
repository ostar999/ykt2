package com.bumptech.glide.repackaged.com.google.common.collect;

import com.bumptech.glide.repackaged.com.google.common.base.Preconditions;

/* loaded from: classes2.dex */
final class SingletonImmutableSet<E> extends ImmutableSet<E> {
    private transient int cachedHashCode;
    final transient E element;

    public SingletonImmutableSet(E e2) {
        this.element = (E) Preconditions.checkNotNull(e2);
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(Object obj) {
        return this.element.equals(obj);
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection
    public int copyIntoArray(Object[] objArr, int i2) {
        objArr[i2] = this.element;
        return i2 + 1;
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableSet, java.util.Collection, java.util.Set
    public final int hashCode() {
        int i2 = this.cachedHashCode;
        if (i2 != 0) {
            return i2;
        }
        int iHashCode = this.element.hashCode();
        this.cachedHashCode = iHashCode;
        return iHashCode;
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableSet
    public boolean isHashCodeFast() {
        return this.cachedHashCode != 0;
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection
    public boolean isPartialView() {
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return 1;
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        String string = this.element.toString();
        StringBuilder sb = new StringBuilder(string.length() + 2);
        sb.append('[');
        sb.append(string);
        sb.append(']');
        return sb.toString();
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableSet, com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set, java.util.NavigableSet
    public UnmodifiableIterator<E> iterator() {
        return Iterators.singletonIterator(this.element);
    }

    public SingletonImmutableSet(E e2, int i2) {
        this.element = e2;
        this.cachedHashCode = i2;
    }
}
