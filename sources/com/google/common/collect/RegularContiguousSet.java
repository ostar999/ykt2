package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.lang.Comparable;
import java.util.Collection;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
/* loaded from: classes4.dex */
final class RegularContiguousSet<C extends Comparable> extends ContiguousSet<C> {
    private static final long serialVersionUID = 0;
    private final Range<C> range;

    @GwtIncompatible
    public static final class SerializedForm<C extends Comparable> implements Serializable {
        final DiscreteDomain<C> domain;
        final Range<C> range;

        private Object readResolve() {
            return new RegularContiguousSet(this.range, this.domain);
        }

        private SerializedForm(Range<C> range, DiscreteDomain<C> discreteDomain) {
            this.range = range;
            this.domain = discreteDomain;
        }
    }

    public RegularContiguousSet(Range<C> range, DiscreteDomain<C> discreteDomain) {
        super(discreteDomain);
        this.range = range;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean equalsOrThrow(Comparable<?> comparable, @NullableDecl Comparable<?> comparable2) {
        return comparable2 != null && Range.compareOrThrow(comparable, comparable2) == 0;
    }

    private ContiguousSet<C> intersectionInCurrentDomain(Range<C> range) {
        return this.range.isConnected(range) ? ContiguousSet.create(this.range.intersection(range), this.domain) : new EmptyContiguousSet(this.domain);
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(@NullableDecl Object obj) {
        if (obj == null) {
            return false;
        }
        try {
            return this.range.contains((Comparable) obj);
        } catch (ClassCastException unused) {
            return false;
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean containsAll(Collection<?> collection) {
        return Collections2.containsAllImpl(this, collection);
    }

    @Override // com.google.common.collect.ImmutableSet
    public ImmutableList<C> createAsList() {
        return this.domain.supportsFastOffset ? new ImmutableAsList<C>() { // from class: com.google.common.collect.RegularContiguousSet.3
            @Override // com.google.common.collect.ImmutableAsList
            public ImmutableSortedSet<C> delegateCollection() {
                return RegularContiguousSet.this;
            }

            @Override // java.util.List
            public C get(int i2) {
                Preconditions.checkElementIndex(i2, size());
                RegularContiguousSet regularContiguousSet = RegularContiguousSet.this;
                return (C) regularContiguousSet.domain.offset(regularContiguousSet.first(), i2);
            }
        } : super.createAsList();
    }

    @Override // com.google.common.collect.ImmutableSet, java.util.Collection, java.util.Set
    public boolean equals(@NullableDecl Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof RegularContiguousSet) {
            RegularContiguousSet regularContiguousSet = (RegularContiguousSet) obj;
            if (this.domain.equals(regularContiguousSet.domain)) {
                return first().equals(regularContiguousSet.first()) && last().equals(regularContiguousSet.last());
            }
        }
        return super.equals(obj);
    }

    @Override // com.google.common.collect.ImmutableSet, java.util.Collection, java.util.Set
    public int hashCode() {
        return Sets.hashCodeImpl(this);
    }

    @Override // com.google.common.collect.ImmutableSortedSet
    @GwtIncompatible
    public int indexOf(Object obj) {
        if (contains(obj)) {
            return (int) this.domain.distance(first(), (Comparable) obj);
        }
        return -1;
    }

    @Override // com.google.common.collect.ContiguousSet
    public ContiguousSet<C> intersection(ContiguousSet<C> contiguousSet) {
        Preconditions.checkNotNull(contiguousSet);
        Preconditions.checkArgument(this.domain.equals(contiguousSet.domain));
        if (contiguousSet.isEmpty()) {
            return contiguousSet;
        }
        Comparable comparable = (Comparable) Ordering.natural().max(first(), contiguousSet.first());
        Comparable comparable2 = (Comparable) Ordering.natural().min(last(), contiguousSet.last());
        return comparable.compareTo(comparable2) <= 0 ? ContiguousSet.create(Range.closed(comparable, comparable2), this.domain) : new EmptyContiguousSet(this.domain);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return false;
    }

    @Override // com.google.common.collect.ImmutableCollection
    public boolean isPartialView() {
        return false;
    }

    @Override // com.google.common.collect.ContiguousSet
    public Range<C> range() {
        BoundType boundType = BoundType.CLOSED;
        return range(boundType, boundType);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        long jDistance = this.domain.distance(first(), last());
        if (jDistance >= 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return ((int) jDistance) + 1;
    }

    @Override // com.google.common.collect.ImmutableSortedSet, com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection
    @GwtIncompatible
    public Object writeReplace() {
        return new SerializedForm(this.range, this.domain);
    }

    @Override // com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    @GwtIncompatible
    public UnmodifiableIterator<C> descendingIterator() {
        return new AbstractSequentialIterator<C>(last()) { // from class: com.google.common.collect.RegularContiguousSet.2
            final C first;

            {
                this.first = (C) RegularContiguousSet.this.first();
            }

            @Override // com.google.common.collect.AbstractSequentialIterator
            public C computeNext(C c3) {
                if (RegularContiguousSet.equalsOrThrow(c3, this.first)) {
                    return null;
                }
                return (C) RegularContiguousSet.this.domain.previous(c3);
            }
        };
    }

    @Override // com.google.common.collect.ImmutableSortedSet, java.util.SortedSet
    public C first() {
        return (C) this.range.lowerBound.leastValueAbove(this.domain);
    }

    @Override // com.google.common.collect.ContiguousSet, com.google.common.collect.ImmutableSortedSet
    public ContiguousSet<C> headSetImpl(C c3, boolean z2) {
        return intersectionInCurrentDomain(Range.upTo(c3, BoundType.forBoolean(z2)));
    }

    @Override // com.google.common.collect.ImmutableSortedSet, com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set, java.util.NavigableSet, com.google.common.collect.SortedIterable
    public UnmodifiableIterator<C> iterator() {
        return new AbstractSequentialIterator<C>(first()) { // from class: com.google.common.collect.RegularContiguousSet.1
            final C last;

            {
                this.last = (C) RegularContiguousSet.this.last();
            }

            @Override // com.google.common.collect.AbstractSequentialIterator
            public C computeNext(C c3) {
                if (RegularContiguousSet.equalsOrThrow(c3, this.last)) {
                    return null;
                }
                return (C) RegularContiguousSet.this.domain.next(c3);
            }
        };
    }

    @Override // com.google.common.collect.ImmutableSortedSet, java.util.SortedSet
    public C last() {
        return (C) this.range.upperBound.greatestValueBelow(this.domain);
    }

    @Override // com.google.common.collect.ContiguousSet
    public Range<C> range(BoundType boundType, BoundType boundType2) {
        return Range.create(this.range.lowerBound.withLowerBoundType(boundType, this.domain), this.range.upperBound.withUpperBoundType(boundType2, this.domain));
    }

    @Override // com.google.common.collect.ContiguousSet, com.google.common.collect.ImmutableSortedSet
    public ContiguousSet<C> subSetImpl(C c3, boolean z2, C c4, boolean z3) {
        return (c3.compareTo(c4) != 0 || z2 || z3) ? intersectionInCurrentDomain(Range.range(c3, BoundType.forBoolean(z2), c4, BoundType.forBoolean(z3))) : new EmptyContiguousSet(this.domain);
    }

    @Override // com.google.common.collect.ContiguousSet, com.google.common.collect.ImmutableSortedSet
    public ContiguousSet<C> tailSetImpl(C c3, boolean z2) {
        return intersectionInCurrentDomain(Range.downTo(c3, BoundType.forBoolean(z2)));
    }
}
