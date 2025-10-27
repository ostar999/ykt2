package com.bumptech.glide.repackaged.com.google.common.collect;

/* loaded from: classes2.dex */
class DescendingImmutableSortedSet<E> extends ImmutableSortedSet<E> {
    private final ImmutableSortedSet<E> forward;

    public DescendingImmutableSortedSet(ImmutableSortedSet<E> immutableSortedSet) {
        super(Ordering.from(immutableSortedSet.comparator()).reverse());
        this.forward = immutableSortedSet;
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    public E ceiling(E e2) {
        return this.forward.floor(e2);
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(Object obj) {
        return this.forward.contains(obj);
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableSortedSet
    public ImmutableSortedSet<E> createDescendingSet() {
        throw new AssertionError("should never be called");
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    public E floor(E e2) {
        return this.forward.ceiling(e2);
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableSortedSet
    public ImmutableSortedSet<E> headSetImpl(E e2, boolean z2) {
        return this.forward.tailSet((ImmutableSortedSet<E>) e2, z2).descendingSet();
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    public E higher(E e2) {
        return this.forward.lower(e2);
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableSortedSet
    public int indexOf(Object obj) {
        int iIndexOf = this.forward.indexOf(obj);
        return iIndexOf == -1 ? iIndexOf : (size() - 1) - iIndexOf;
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection
    public boolean isPartialView() {
        return this.forward.isPartialView();
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    public E lower(E e2) {
        return this.forward.higher(e2);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.forward.size();
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableSortedSet
    public ImmutableSortedSet<E> subSetImpl(E e2, boolean z2, E e3, boolean z3) {
        return this.forward.subSet((boolean) e3, z3, (boolean) e2, z2).descendingSet();
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableSortedSet
    public ImmutableSortedSet<E> tailSetImpl(E e2, boolean z2) {
        return this.forward.headSet((ImmutableSortedSet<E>) e2, z2).descendingSet();
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    public UnmodifiableIterator<E> descendingIterator() {
        return this.forward.iterator();
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    public ImmutableSortedSet<E> descendingSet() {
        return this.forward;
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableSortedSet, com.bumptech.glide.repackaged.com.google.common.collect.ImmutableSet, com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set, java.util.NavigableSet
    public UnmodifiableIterator<E> iterator() {
        return this.forward.descendingIterator();
    }
}
