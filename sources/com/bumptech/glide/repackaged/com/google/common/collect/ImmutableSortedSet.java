package com.bumptech.glide.repackaged.com.google.common.collect;

import com.bumptech.glide.repackaged.com.google.common.base.Preconditions;
import java.util.Comparator;
import java.util.NavigableSet;
import java.util.SortedSet;

/* loaded from: classes2.dex */
public abstract class ImmutableSortedSet<E> extends ImmutableSortedSetFauxverideShim<E> implements SortedIterable<E>, NavigableSet<E> {
    private static final RegularImmutableSortedSet<Comparable> NATURAL_EMPTY_SET;
    private static final Comparator<Comparable> NATURAL_ORDER;
    final transient Comparator<? super E> comparator;
    transient ImmutableSortedSet<E> descendingSet;

    static {
        Ordering orderingNatural = Ordering.natural();
        NATURAL_ORDER = orderingNatural;
        NATURAL_EMPTY_SET = new RegularImmutableSortedSet<>(ImmutableList.of(), orderingNatural);
    }

    public ImmutableSortedSet(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    public static <E> RegularImmutableSortedSet<E> emptySet(Comparator<? super E> comparator) {
        return NATURAL_ORDER.equals(comparator) ? (RegularImmutableSortedSet<E>) NATURAL_EMPTY_SET : new RegularImmutableSortedSet<>(ImmutableList.of(), comparator);
    }

    public E ceiling(E e2) {
        return (E) Iterables.getFirst(tailSet((ImmutableSortedSet<E>) e2, true), null);
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.SortedIterable
    public Comparator<? super E> comparator() {
        return this.comparator;
    }

    public ImmutableSortedSet<E> createDescendingSet() {
        return new DescendingImmutableSortedSet(this);
    }

    @Override // java.util.NavigableSet
    public abstract UnmodifiableIterator<E> descendingIterator();

    @Override // java.util.SortedSet
    public E first() {
        return iterator().next();
    }

    public E floor(E e2) {
        return (E) Iterators.getNext(headSet((ImmutableSortedSet<E>) e2, true).descendingIterator(), null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.NavigableSet
    public /* bridge */ /* synthetic */ NavigableSet headSet(Object obj, boolean z2) {
        return headSet((ImmutableSortedSet<E>) obj, z2);
    }

    public abstract ImmutableSortedSet<E> headSetImpl(E e2, boolean z2);

    public E higher(E e2) {
        return (E) Iterables.getFirst(tailSet((ImmutableSortedSet<E>) e2, false), null);
    }

    public abstract int indexOf(Object obj);

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableSet, com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set, java.util.NavigableSet
    public abstract UnmodifiableIterator<E> iterator();

    @Override // java.util.SortedSet
    public E last() {
        return descendingIterator().next();
    }

    public E lower(E e2) {
        return (E) Iterators.getNext(headSet((ImmutableSortedSet<E>) e2, false).descendingIterator(), null);
    }

    @Override // java.util.NavigableSet
    @Deprecated
    public final E pollFirst() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.NavigableSet
    @Deprecated
    public final E pollLast() {
        throw new UnsupportedOperationException();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.NavigableSet
    public /* bridge */ /* synthetic */ NavigableSet subSet(Object obj, boolean z2, Object obj2, boolean z3) {
        return subSet((boolean) obj, z2, (boolean) obj2, z3);
    }

    public abstract ImmutableSortedSet<E> subSetImpl(E e2, boolean z2, E e3, boolean z3);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.NavigableSet
    public /* bridge */ /* synthetic */ NavigableSet tailSet(Object obj, boolean z2) {
        return tailSet((ImmutableSortedSet<E>) obj, z2);
    }

    public abstract ImmutableSortedSet<E> tailSetImpl(E e2, boolean z2);

    public int unsafeCompare(Object obj, Object obj2) {
        return unsafeCompare(this.comparator, obj, obj2);
    }

    public static int unsafeCompare(Comparator<?> comparator, Object obj, Object obj2) {
        return comparator.compare(obj, obj2);
    }

    @Override // java.util.NavigableSet
    public ImmutableSortedSet<E> descendingSet() {
        ImmutableSortedSet<E> immutableSortedSet = this.descendingSet;
        if (immutableSortedSet != null) {
            return immutableSortedSet;
        }
        ImmutableSortedSet<E> immutableSortedSetCreateDescendingSet = createDescendingSet();
        this.descendingSet = immutableSortedSetCreateDescendingSet;
        immutableSortedSetCreateDescendingSet.descendingSet = this;
        return immutableSortedSetCreateDescendingSet;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.NavigableSet, java.util.SortedSet
    public /* bridge */ /* synthetic */ SortedSet headSet(Object obj) {
        return headSet((ImmutableSortedSet<E>) obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.NavigableSet, java.util.SortedSet
    public /* bridge */ /* synthetic */ SortedSet tailSet(Object obj) {
        return tailSet((ImmutableSortedSet<E>) obj);
    }

    @Override // java.util.NavigableSet, java.util.SortedSet
    public ImmutableSortedSet<E> headSet(E e2) {
        return headSet((ImmutableSortedSet<E>) e2, false);
    }

    @Override // java.util.NavigableSet, java.util.SortedSet
    public ImmutableSortedSet<E> subSet(E e2, E e3) {
        return subSet((boolean) e2, true, (boolean) e3, false);
    }

    @Override // java.util.NavigableSet, java.util.SortedSet
    public ImmutableSortedSet<E> tailSet(E e2) {
        return tailSet((ImmutableSortedSet<E>) e2, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.NavigableSet
    public ImmutableSortedSet<E> headSet(E e2, boolean z2) {
        return headSetImpl(Preconditions.checkNotNull(e2), z2);
    }

    @Override // java.util.NavigableSet
    public ImmutableSortedSet<E> subSet(E e2, boolean z2, E e3, boolean z3) {
        Preconditions.checkNotNull(e2);
        Preconditions.checkNotNull(e3);
        Preconditions.checkArgument(this.comparator.compare(e2, e3) <= 0);
        return subSetImpl(e2, z2, e3, z3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.NavigableSet
    public ImmutableSortedSet<E> tailSet(E e2, boolean z2) {
        return tailSetImpl(Preconditions.checkNotNull(e2), z2);
    }
}
