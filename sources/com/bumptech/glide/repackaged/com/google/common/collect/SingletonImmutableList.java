package com.bumptech.glide.repackaged.com.google.common.collect;

import com.bumptech.glide.repackaged.com.google.common.base.Preconditions;

/* loaded from: classes2.dex */
final class SingletonImmutableList<E> extends ImmutableList<E> {
    final transient E element;

    public SingletonImmutableList(E e2) {
        this.element = (E) Preconditions.checkNotNull(e2);
    }

    @Override // java.util.List
    public E get(int i2) {
        Preconditions.checkElementIndex(i2, 1);
        return this.element;
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection
    public boolean isPartialView() {
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
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

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableList, com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set, java.util.NavigableSet
    public UnmodifiableIterator<E> iterator() {
        return Iterators.singletonIterator(this.element);
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableList, java.util.List
    public ImmutableList<E> subList(int i2, int i3) {
        Preconditions.checkPositionIndexes(i2, i3, 1);
        return i2 == i3 ? ImmutableList.of() : this;
    }
}
