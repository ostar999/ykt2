package com.bumptech.glide.repackaged.com.google.common.collect;

import com.bumptech.glide.repackaged.com.google.common.base.Preconditions;
import com.bumptech.glide.repackaged.com.google.common.collect.SortedLists;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/* loaded from: classes2.dex */
final class RegularImmutableSortedSet<E> extends ImmutableSortedSet<E> {
    private final transient ImmutableList<E> elements;

    public RegularImmutableSortedSet(ImmutableList<E> immutableList, Comparator<? super E> comparator) {
        super(comparator);
        this.elements = immutableList;
    }

    private int unsafeBinarySearch(Object obj) throws ClassCastException {
        return Collections.binarySearch(this.elements, obj, unsafeComparator());
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    public E ceiling(E e2) {
        int iTailIndex = tailIndex(e2, true);
        if (iTailIndex == size()) {
            return null;
        }
        return this.elements.get(iTailIndex);
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(Object obj) {
        if (obj == null) {
            return false;
        }
        try {
            return unsafeBinarySearch(obj) >= 0;
        } catch (ClassCastException unused) {
            return false;
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean containsAll(Collection<?> collection) {
        if (collection instanceof Multiset) {
            collection = ((Multiset) collection).elementSet();
        }
        if (!SortedIterables.hasSameComparator(comparator(), collection) || collection.size() <= 1) {
            return super.containsAll(collection);
        }
        PeekingIterator peekingIterator = Iterators.peekingIterator(iterator());
        Iterator<?> it = collection.iterator();
        Object next = it.next();
        while (peekingIterator.hasNext()) {
            try {
                int iUnsafeCompare = unsafeCompare(peekingIterator.peek(), next);
                if (iUnsafeCompare < 0) {
                    peekingIterator.next();
                } else if (iUnsafeCompare == 0) {
                    if (!it.hasNext()) {
                        return true;
                    }
                    next = it.next();
                } else if (iUnsafeCompare > 0) {
                    break;
                }
            } catch (ClassCastException | NullPointerException unused) {
            }
        }
        return false;
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection
    public int copyIntoArray(Object[] objArr, int i2) {
        return this.elements.copyIntoArray(objArr, i2);
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection
    public ImmutableList<E> createAsList() {
        return size() <= 1 ? this.elements : new ImmutableSortedAsList(this, this.elements);
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableSortedSet
    public ImmutableSortedSet<E> createDescendingSet() {
        Ordering orderingReverse = Ordering.from(this.comparator).reverse();
        return isEmpty() ? ImmutableSortedSet.emptySet(orderingReverse) : new RegularImmutableSortedSet(this.elements.reverse(), orderingReverse);
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableSet, java.util.Collection, java.util.Set
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Set)) {
            return false;
        }
        Set set = (Set) obj;
        if (size() != set.size()) {
            return false;
        }
        if (isEmpty()) {
            return true;
        }
        if (!SortedIterables.hasSameComparator(this.comparator, set)) {
            return containsAll(set);
        }
        Iterator<E> it = set.iterator();
        try {
            UnmodifiableIterator<E> it2 = iterator();
            while (it2.hasNext()) {
                E next = it2.next();
                E next2 = it.next();
                if (next2 == null || unsafeCompare(next, next2) != 0) {
                    return false;
                }
            }
            return true;
        } catch (ClassCastException | NoSuchElementException unused) {
            return false;
        }
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableSortedSet, java.util.SortedSet
    public E first() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.elements.get(0);
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    public E floor(E e2) {
        int iHeadIndex = headIndex(e2, true) - 1;
        if (iHeadIndex == -1) {
            return null;
        }
        return this.elements.get(iHeadIndex);
    }

    public RegularImmutableSortedSet<E> getSubSet(int i2, int i3) {
        return (i2 == 0 && i3 == size()) ? this : i2 < i3 ? new RegularImmutableSortedSet<>(this.elements.subList(i2, i3), this.comparator) : ImmutableSortedSet.emptySet(this.comparator);
    }

    public int headIndex(E e2, boolean z2) {
        return SortedLists.binarySearch(this.elements, Preconditions.checkNotNull(e2), comparator(), z2 ? SortedLists.KeyPresentBehavior.FIRST_AFTER : SortedLists.KeyPresentBehavior.FIRST_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableSortedSet
    public ImmutableSortedSet<E> headSetImpl(E e2, boolean z2) {
        return getSubSet(0, headIndex(e2, z2));
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    public E higher(E e2) {
        int iTailIndex = tailIndex(e2, false);
        if (iTailIndex == size()) {
            return null;
        }
        return this.elements.get(iTailIndex);
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableSortedSet
    public int indexOf(Object obj) {
        if (obj == null) {
            return -1;
        }
        try {
            int iBinarySearch = SortedLists.binarySearch(this.elements, obj, unsafeComparator(), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.INVERTED_INSERTION_INDEX);
            if (iBinarySearch >= 0) {
                return iBinarySearch;
            }
            return -1;
        } catch (ClassCastException unused) {
            return -1;
        }
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection
    public boolean isPartialView() {
        return this.elements.isPartialView();
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableSortedSet, java.util.SortedSet
    public E last() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.elements.get(size() - 1);
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    public E lower(E e2) {
        int iHeadIndex = headIndex(e2, false) - 1;
        if (iHeadIndex == -1) {
            return null;
        }
        return this.elements.get(iHeadIndex);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.elements.size();
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableSortedSet
    public ImmutableSortedSet<E> subSetImpl(E e2, boolean z2, E e3, boolean z3) {
        return tailSetImpl(e2, z2).headSetImpl(e3, z3);
    }

    public int tailIndex(E e2, boolean z2) {
        return SortedLists.binarySearch(this.elements, Preconditions.checkNotNull(e2), comparator(), z2 ? SortedLists.KeyPresentBehavior.FIRST_PRESENT : SortedLists.KeyPresentBehavior.FIRST_AFTER, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableSortedSet
    public ImmutableSortedSet<E> tailSetImpl(E e2, boolean z2) {
        return getSubSet(tailIndex(e2, z2), size());
    }

    public Comparator<Object> unsafeComparator() {
        return this.comparator;
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    public UnmodifiableIterator<E> descendingIterator() {
        return this.elements.reverse().iterator();
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableSortedSet, com.bumptech.glide.repackaged.com.google.common.collect.ImmutableSet, com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set, java.util.NavigableSet
    public UnmodifiableIterator<E> iterator() {
        return this.elements.iterator();
    }
}
