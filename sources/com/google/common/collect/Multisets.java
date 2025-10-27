package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Multiset;
import com.google.common.collect.Sets;
import com.google.common.math.IntMath;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes4.dex */
public final class Multisets {

    public static abstract class AbstractEntry<E> implements Multiset.Entry<E> {
        @Override // com.google.common.collect.Multiset.Entry
        public boolean equals(@NullableDecl Object obj) {
            if (!(obj instanceof Multiset.Entry)) {
                return false;
            }
            Multiset.Entry entry = (Multiset.Entry) obj;
            return getCount() == entry.getCount() && Objects.equal(getElement(), entry.getElement());
        }

        @Override // com.google.common.collect.Multiset.Entry
        public int hashCode() {
            E element = getElement();
            return (element == null ? 0 : element.hashCode()) ^ getCount();
        }

        @Override // com.google.common.collect.Multiset.Entry
        public String toString() {
            String strValueOf = String.valueOf(getElement());
            int count = getCount();
            if (count == 1) {
                return strValueOf;
            }
            return strValueOf + " x " + count;
        }
    }

    public static final class DecreasingCount implements Comparator<Multiset.Entry<?>> {
        static final DecreasingCount INSTANCE = new DecreasingCount();

        private DecreasingCount() {
        }

        @Override // java.util.Comparator
        public int compare(Multiset.Entry<?> entry, Multiset.Entry<?> entry2) {
            return entry2.getCount() - entry.getCount();
        }
    }

    public static abstract class ElementSet<E> extends Sets.ImprovedAbstractSet<E> {
        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            multiset().clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return multiset().contains(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean containsAll(Collection<?> collection) {
            return multiset().containsAll(collection);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return multiset().isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public abstract Iterator<E> iterator();

        public abstract Multiset<E> multiset();

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            return multiset().remove(obj, Integer.MAX_VALUE) > 0;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return multiset().entrySet().size();
        }
    }

    public static abstract class EntrySet<E> extends Sets.ImprovedAbstractSet<Multiset.Entry<E>> {
        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            multiset().clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@NullableDecl Object obj) {
            if (!(obj instanceof Multiset.Entry)) {
                return false;
            }
            Multiset.Entry entry = (Multiset.Entry) obj;
            return entry.getCount() > 0 && multiset().count(entry.getElement()) == entry.getCount();
        }

        public abstract Multiset<E> multiset();

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            if (obj instanceof Multiset.Entry) {
                Multiset.Entry entry = (Multiset.Entry) obj;
                Object element = entry.getElement();
                int count = entry.getCount();
                if (count != 0) {
                    return multiset().setCount(element, count, 0);
                }
            }
            return false;
        }
    }

    public static final class FilteredMultiset<E> extends ViewMultiset<E> {
        final Predicate<? super E> predicate;
        final Multiset<E> unfiltered;

        public FilteredMultiset(Multiset<E> multiset, Predicate<? super E> predicate) {
            super();
            this.unfiltered = (Multiset) Preconditions.checkNotNull(multiset);
            this.predicate = (Predicate) Preconditions.checkNotNull(predicate);
        }

        @Override // com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
        public int add(@NullableDecl E e2, int i2) {
            Preconditions.checkArgument(this.predicate.apply(e2), "Element %s does not match predicate %s", e2, this.predicate);
            return this.unfiltered.add(e2, i2);
        }

        @Override // com.google.common.collect.Multiset
        public int count(@NullableDecl Object obj) {
            int iCount = this.unfiltered.count(obj);
            if (iCount <= 0 || !this.predicate.apply(obj)) {
                return 0;
            }
            return iCount;
        }

        @Override // com.google.common.collect.AbstractMultiset
        public Set<E> createElementSet() {
            return Sets.filter(this.unfiltered.elementSet(), this.predicate);
        }

        @Override // com.google.common.collect.AbstractMultiset
        public Set<Multiset.Entry<E>> createEntrySet() {
            return Sets.filter(this.unfiltered.entrySet(), new Predicate<Multiset.Entry<E>>() { // from class: com.google.common.collect.Multisets.FilteredMultiset.1
                @Override // com.google.common.base.Predicate
                public boolean apply(Multiset.Entry<E> entry) {
                    return FilteredMultiset.this.predicate.apply(entry.getElement());
                }
            });
        }

        @Override // com.google.common.collect.AbstractMultiset
        public Iterator<E> elementIterator() {
            throw new AssertionError("should never be called");
        }

        @Override // com.google.common.collect.AbstractMultiset
        public Iterator<Multiset.Entry<E>> entryIterator() {
            throw new AssertionError("should never be called");
        }

        @Override // com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
        public int remove(@NullableDecl Object obj, int i2) {
            CollectPreconditions.checkNonnegative(i2, "occurrences");
            if (i2 == 0) {
                return count(obj);
            }
            if (contains(obj)) {
                return this.unfiltered.remove(obj, i2);
            }
            return 0;
        }

        @Override // com.google.common.collect.Multisets.ViewMultiset, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.google.common.collect.Multiset
        public UnmodifiableIterator<E> iterator() {
            return Iterators.filter(this.unfiltered.iterator(), this.predicate);
        }
    }

    public static class ImmutableEntry<E> extends AbstractEntry<E> implements Serializable {
        private static final long serialVersionUID = 0;
        private final int count;

        @NullableDecl
        private final E element;

        public ImmutableEntry(@NullableDecl E e2, int i2) {
            this.element = e2;
            this.count = i2;
            CollectPreconditions.checkNonnegative(i2, "count");
        }

        @Override // com.google.common.collect.Multiset.Entry
        public final int getCount() {
            return this.count;
        }

        @Override // com.google.common.collect.Multiset.Entry
        @NullableDecl
        public final E getElement() {
            return this.element;
        }

        public ImmutableEntry<E> nextInBucket() {
            return null;
        }
    }

    public static final class MultisetIteratorImpl<E> implements Iterator<E> {
        private boolean canRemove;

        @MonotonicNonNullDecl
        private Multiset.Entry<E> currentEntry;
        private final Iterator<Multiset.Entry<E>> entryIterator;
        private int laterCount;
        private final Multiset<E> multiset;
        private int totalCount;

        public MultisetIteratorImpl(Multiset<E> multiset, Iterator<Multiset.Entry<E>> it) {
            this.multiset = multiset;
            this.entryIterator = it;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.laterCount > 0 || this.entryIterator.hasNext();
        }

        @Override // java.util.Iterator
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            if (this.laterCount == 0) {
                Multiset.Entry<E> next = this.entryIterator.next();
                this.currentEntry = next;
                int count = next.getCount();
                this.laterCount = count;
                this.totalCount = count;
            }
            this.laterCount--;
            this.canRemove = true;
            return this.currentEntry.getElement();
        }

        @Override // java.util.Iterator
        public void remove() {
            CollectPreconditions.checkRemove(this.canRemove);
            if (this.totalCount == 1) {
                this.entryIterator.remove();
            } else {
                this.multiset.remove(this.currentEntry.getElement());
            }
            this.totalCount--;
            this.canRemove = false;
        }
    }

    public static class UnmodifiableMultiset<E> extends ForwardingMultiset<E> implements Serializable {
        private static final long serialVersionUID = 0;
        final Multiset<? extends E> delegate;

        @MonotonicNonNullDecl
        transient Set<E> elementSet;

        @MonotonicNonNullDecl
        transient Set<Multiset.Entry<E>> entrySet;

        public UnmodifiableMultiset(Multiset<? extends E> multiset) {
            this.delegate = multiset;
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Queue
        public boolean add(E e2) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection
        public boolean addAll(Collection<? extends E> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public void clear() {
            throw new UnsupportedOperationException();
        }

        public Set<E> createElementSet() {
            return Collections.unmodifiableSet(this.delegate.elementSet());
        }

        @Override // com.google.common.collect.ForwardingMultiset, com.google.common.collect.Multiset
        public Set<E> elementSet() {
            Set<E> set = this.elementSet;
            if (set != null) {
                return set;
            }
            Set<E> setCreateElementSet = createElementSet();
            this.elementSet = setCreateElementSet;
            return setCreateElementSet;
        }

        @Override // com.google.common.collect.ForwardingMultiset, com.google.common.collect.Multiset
        public Set<Multiset.Entry<E>> entrySet() {
            Set<Multiset.Entry<E>> set = this.entrySet;
            if (set != null) {
                return set;
            }
            Set<Multiset.Entry<E>> setUnmodifiableSet = Collections.unmodifiableSet(this.delegate.entrySet());
            this.entrySet = setUnmodifiableSet;
            return setUnmodifiableSet;
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<E> iterator() {
            return Iterators.unmodifiableIterator(this.delegate.iterator());
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingMultiset, com.google.common.collect.Multiset
        public int setCount(E e2, int i2) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingMultiset, com.google.common.collect.Multiset
        public int add(E e2, int i2) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingMultiset, com.google.common.collect.Multiset
        public int remove(Object obj, int i2) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingMultiset, com.google.common.collect.Multiset
        public boolean setCount(E e2, int i2, int i3) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingMultiset, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
        public Multiset<E> delegate() {
            return this.delegate;
        }
    }

    public static abstract class ViewMultiset<E> extends AbstractMultiset<E> {
        private ViewMultiset() {
        }

        @Override // com.google.common.collect.AbstractMultiset, java.util.AbstractCollection, java.util.Collection
        public void clear() {
            elementSet().clear();
        }

        @Override // com.google.common.collect.AbstractMultiset
        public int distinctElements() {
            return elementSet().size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.google.common.collect.Multiset
        public Iterator<E> iterator() {
            return Multisets.iteratorImpl(this);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, com.google.common.collect.Multiset
        public int size() {
            return Multisets.linearTimeSizeImpl(this);
        }
    }

    private Multisets() {
    }

    public static <E> boolean addAllImpl(Multiset<E> multiset, Collection<? extends E> collection) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(collection);
        if (collection instanceof Multiset) {
            return addAllImpl((Multiset) multiset, cast(collection));
        }
        if (collection.isEmpty()) {
            return false;
        }
        return Iterators.addAll(multiset, collection.iterator());
    }

    public static <T> Multiset<T> cast(Iterable<T> iterable) {
        return (Multiset) iterable;
    }

    @CanIgnoreReturnValue
    public static boolean containsOccurrences(Multiset<?> multiset, Multiset<?> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        for (Multiset.Entry<?> entry : multiset2.entrySet()) {
            if (multiset.count(entry.getElement()) < entry.getCount()) {
                return false;
            }
        }
        return true;
    }

    @Beta
    public static <E> ImmutableMultiset<E> copyHighestCountFirst(Multiset<E> multiset) {
        Multiset.Entry[] entryArr = (Multiset.Entry[]) multiset.entrySet().toArray(new Multiset.Entry[0]);
        Arrays.sort(entryArr, DecreasingCount.INSTANCE);
        return ImmutableMultiset.copyFromEntries(Arrays.asList(entryArr));
    }

    @Beta
    public static <E> Multiset<E> difference(final Multiset<E> multiset, final Multiset<?> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        return new ViewMultiset<E>() { // from class: com.google.common.collect.Multisets.4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.google.common.collect.Multisets.ViewMultiset, com.google.common.collect.AbstractMultiset, java.util.AbstractCollection, java.util.Collection
            public void clear() {
                throw new UnsupportedOperationException();
            }

            @Override // com.google.common.collect.Multiset
            public int count(@NullableDecl Object obj) {
                int iCount = multiset.count(obj);
                if (iCount == 0) {
                    return 0;
                }
                return Math.max(0, iCount - multiset2.count(obj));
            }

            @Override // com.google.common.collect.Multisets.ViewMultiset, com.google.common.collect.AbstractMultiset
            public int distinctElements() {
                return Iterators.size(entryIterator());
            }

            @Override // com.google.common.collect.AbstractMultiset
            public Iterator<E> elementIterator() {
                final Iterator<Multiset.Entry<E>> it = multiset.entrySet().iterator();
                return new AbstractIterator<E>() { // from class: com.google.common.collect.Multisets.4.1
                    @Override // com.google.common.collect.AbstractIterator
                    public E computeNext() {
                        while (it.hasNext()) {
                            Multiset.Entry entry = (Multiset.Entry) it.next();
                            E e2 = (E) entry.getElement();
                            if (entry.getCount() > multiset2.count(e2)) {
                                return e2;
                            }
                        }
                        return endOfData();
                    }
                };
            }

            @Override // com.google.common.collect.AbstractMultiset
            public Iterator<Multiset.Entry<E>> entryIterator() {
                final Iterator<Multiset.Entry<E>> it = multiset.entrySet().iterator();
                return new AbstractIterator<Multiset.Entry<E>>() { // from class: com.google.common.collect.Multisets.4.2
                    @Override // com.google.common.collect.AbstractIterator
                    public Multiset.Entry<E> computeNext() {
                        while (it.hasNext()) {
                            Multiset.Entry entry = (Multiset.Entry) it.next();
                            Object element = entry.getElement();
                            int count = entry.getCount() - multiset2.count(element);
                            if (count > 0) {
                                return Multisets.immutableEntry(element, count);
                            }
                        }
                        return endOfData();
                    }
                };
            }
        };
    }

    public static <E> Iterator<E> elementIterator(Iterator<Multiset.Entry<E>> it) {
        return new TransformedIterator<Multiset.Entry<E>, E>(it) { // from class: com.google.common.collect.Multisets.5
            @Override // com.google.common.collect.TransformedIterator
            public E transform(Multiset.Entry<E> entry) {
                return entry.getElement();
            }
        };
    }

    public static boolean equalsImpl(Multiset<?> multiset, @NullableDecl Object obj) {
        if (obj == multiset) {
            return true;
        }
        if (obj instanceof Multiset) {
            Multiset multiset2 = (Multiset) obj;
            if (multiset.size() == multiset2.size() && multiset.entrySet().size() == multiset2.entrySet().size()) {
                for (Multiset.Entry entry : multiset2.entrySet()) {
                    if (multiset.count(entry.getElement()) != entry.getCount()) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Beta
    public static <E> Multiset<E> filter(Multiset<E> multiset, Predicate<? super E> predicate) {
        if (!(multiset instanceof FilteredMultiset)) {
            return new FilteredMultiset(multiset, predicate);
        }
        FilteredMultiset filteredMultiset = (FilteredMultiset) multiset;
        return new FilteredMultiset(filteredMultiset.unfiltered, Predicates.and(filteredMultiset.predicate, predicate));
    }

    public static <E> Multiset.Entry<E> immutableEntry(@NullableDecl E e2, int i2) {
        return new ImmutableEntry(e2, i2);
    }

    public static int inferDistinctElements(Iterable<?> iterable) {
        if (iterable instanceof Multiset) {
            return ((Multiset) iterable).elementSet().size();
        }
        return 11;
    }

    public static <E> Multiset<E> intersection(final Multiset<E> multiset, final Multiset<?> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        return new ViewMultiset<E>() { // from class: com.google.common.collect.Multisets.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.google.common.collect.Multiset
            public int count(Object obj) {
                int iCount = multiset.count(obj);
                if (iCount == 0) {
                    return 0;
                }
                return Math.min(iCount, multiset2.count(obj));
            }

            @Override // com.google.common.collect.AbstractMultiset
            public Set<E> createElementSet() {
                return Sets.intersection(multiset.elementSet(), multiset2.elementSet());
            }

            @Override // com.google.common.collect.AbstractMultiset
            public Iterator<E> elementIterator() {
                throw new AssertionError("should never be called");
            }

            @Override // com.google.common.collect.AbstractMultiset
            public Iterator<Multiset.Entry<E>> entryIterator() {
                final Iterator<Multiset.Entry<E>> it = multiset.entrySet().iterator();
                return new AbstractIterator<Multiset.Entry<E>>() { // from class: com.google.common.collect.Multisets.2.1
                    @Override // com.google.common.collect.AbstractIterator
                    public Multiset.Entry<E> computeNext() {
                        while (it.hasNext()) {
                            Multiset.Entry entry = (Multiset.Entry) it.next();
                            Object element = entry.getElement();
                            int iMin = Math.min(entry.getCount(), multiset2.count(element));
                            if (iMin > 0) {
                                return Multisets.immutableEntry(element, iMin);
                            }
                        }
                        return endOfData();
                    }
                };
            }
        };
    }

    public static <E> Iterator<E> iteratorImpl(Multiset<E> multiset) {
        return new MultisetIteratorImpl(multiset, multiset.entrySet().iterator());
    }

    public static int linearTimeSizeImpl(Multiset<?> multiset) {
        long count = 0;
        while (multiset.entrySet().iterator().hasNext()) {
            count += r4.next().getCount();
        }
        return Ints.saturatedCast(count);
    }

    public static boolean removeAllImpl(Multiset<?> multiset, Collection<?> collection) {
        if (collection instanceof Multiset) {
            collection = ((Multiset) collection).elementSet();
        }
        return multiset.elementSet().removeAll(collection);
    }

    @CanIgnoreReturnValue
    public static boolean removeOccurrences(Multiset<?> multiset, Iterable<?> iterable) {
        if (iterable instanceof Multiset) {
            return removeOccurrences(multiset, (Multiset<?>) iterable);
        }
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(iterable);
        Iterator<?> it = iterable.iterator();
        boolean zRemove = false;
        while (it.hasNext()) {
            zRemove |= multiset.remove(it.next());
        }
        return zRemove;
    }

    public static boolean retainAllImpl(Multiset<?> multiset, Collection<?> collection) {
        Preconditions.checkNotNull(collection);
        if (collection instanceof Multiset) {
            collection = ((Multiset) collection).elementSet();
        }
        return multiset.elementSet().retainAll(collection);
    }

    @CanIgnoreReturnValue
    public static boolean retainOccurrences(Multiset<?> multiset, Multiset<?> multiset2) {
        return retainOccurrencesImpl(multiset, multiset2);
    }

    private static <E> boolean retainOccurrencesImpl(Multiset<E> multiset, Multiset<?> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        Iterator<Multiset.Entry<E>> it = multiset.entrySet().iterator();
        boolean z2 = false;
        while (it.hasNext()) {
            Multiset.Entry<E> next = it.next();
            int iCount = multiset2.count(next.getElement());
            if (iCount == 0) {
                it.remove();
            } else if (iCount < next.getCount()) {
                multiset.setCount(next.getElement(), iCount);
            }
            z2 = true;
        }
        return z2;
    }

    public static <E> int setCountImpl(Multiset<E> multiset, E e2, int i2) {
        CollectPreconditions.checkNonnegative(i2, "count");
        int iCount = multiset.count(e2);
        int i3 = i2 - iCount;
        if (i3 > 0) {
            multiset.add(e2, i3);
        } else if (i3 < 0) {
            multiset.remove(e2, -i3);
        }
        return iCount;
    }

    @Beta
    public static <E> Multiset<E> sum(final Multiset<? extends E> multiset, final Multiset<? extends E> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        return new ViewMultiset<E>() { // from class: com.google.common.collect.Multisets.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.google.common.collect.AbstractMultiset, java.util.AbstractCollection, java.util.Collection, com.google.common.collect.Multiset
            public boolean contains(@NullableDecl Object obj) {
                return multiset.contains(obj) || multiset2.contains(obj);
            }

            @Override // com.google.common.collect.Multiset
            public int count(Object obj) {
                return multiset.count(obj) + multiset2.count(obj);
            }

            @Override // com.google.common.collect.AbstractMultiset
            public Set<E> createElementSet() {
                return Sets.union(multiset.elementSet(), multiset2.elementSet());
            }

            @Override // com.google.common.collect.AbstractMultiset
            public Iterator<E> elementIterator() {
                throw new AssertionError("should never be called");
            }

            @Override // com.google.common.collect.AbstractMultiset
            public Iterator<Multiset.Entry<E>> entryIterator() {
                final Iterator<Multiset.Entry<E>> it = multiset.entrySet().iterator();
                final Iterator<Multiset.Entry<E>> it2 = multiset2.entrySet().iterator();
                return new AbstractIterator<Multiset.Entry<E>>() { // from class: com.google.common.collect.Multisets.3.1
                    @Override // com.google.common.collect.AbstractIterator
                    public Multiset.Entry<E> computeNext() {
                        if (it.hasNext()) {
                            Multiset.Entry entry = (Multiset.Entry) it.next();
                            Object element = entry.getElement();
                            return Multisets.immutableEntry(element, entry.getCount() + multiset2.count(element));
                        }
                        while (it2.hasNext()) {
                            Multiset.Entry entry2 = (Multiset.Entry) it2.next();
                            Object element2 = entry2.getElement();
                            if (!multiset.contains(element2)) {
                                return Multisets.immutableEntry(element2, entry2.getCount());
                            }
                        }
                        return endOfData();
                    }
                };
            }

            @Override // com.google.common.collect.AbstractMultiset, java.util.AbstractCollection, java.util.Collection
            public boolean isEmpty() {
                return multiset.isEmpty() && multiset2.isEmpty();
            }

            @Override // com.google.common.collect.Multisets.ViewMultiset, java.util.AbstractCollection, java.util.Collection, com.google.common.collect.Multiset
            public int size() {
                return IntMath.saturatedAdd(multiset.size(), multiset2.size());
            }
        };
    }

    @Beta
    public static <E> Multiset<E> union(final Multiset<? extends E> multiset, final Multiset<? extends E> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        return new ViewMultiset<E>() { // from class: com.google.common.collect.Multisets.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.google.common.collect.AbstractMultiset, java.util.AbstractCollection, java.util.Collection, com.google.common.collect.Multiset
            public boolean contains(@NullableDecl Object obj) {
                return multiset.contains(obj) || multiset2.contains(obj);
            }

            @Override // com.google.common.collect.Multiset
            public int count(Object obj) {
                return Math.max(multiset.count(obj), multiset2.count(obj));
            }

            @Override // com.google.common.collect.AbstractMultiset
            public Set<E> createElementSet() {
                return Sets.union(multiset.elementSet(), multiset2.elementSet());
            }

            @Override // com.google.common.collect.AbstractMultiset
            public Iterator<E> elementIterator() {
                throw new AssertionError("should never be called");
            }

            @Override // com.google.common.collect.AbstractMultiset
            public Iterator<Multiset.Entry<E>> entryIterator() {
                final Iterator<Multiset.Entry<E>> it = multiset.entrySet().iterator();
                final Iterator<Multiset.Entry<E>> it2 = multiset2.entrySet().iterator();
                return new AbstractIterator<Multiset.Entry<E>>() { // from class: com.google.common.collect.Multisets.1.1
                    @Override // com.google.common.collect.AbstractIterator
                    public Multiset.Entry<E> computeNext() {
                        if (it.hasNext()) {
                            Multiset.Entry entry = (Multiset.Entry) it.next();
                            Object element = entry.getElement();
                            return Multisets.immutableEntry(element, Math.max(entry.getCount(), multiset2.count(element)));
                        }
                        while (it2.hasNext()) {
                            Multiset.Entry entry2 = (Multiset.Entry) it2.next();
                            Object element2 = entry2.getElement();
                            if (!multiset.contains(element2)) {
                                return Multisets.immutableEntry(element2, entry2.getCount());
                            }
                        }
                        return endOfData();
                    }
                };
            }

            @Override // com.google.common.collect.AbstractMultiset, java.util.AbstractCollection, java.util.Collection
            public boolean isEmpty() {
                return multiset.isEmpty() && multiset2.isEmpty();
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <E> Multiset<E> unmodifiableMultiset(Multiset<? extends E> multiset) {
        return ((multiset instanceof UnmodifiableMultiset) || (multiset instanceof ImmutableMultiset)) ? multiset : new UnmodifiableMultiset((Multiset) Preconditions.checkNotNull(multiset));
    }

    @Beta
    public static <E> SortedMultiset<E> unmodifiableSortedMultiset(SortedMultiset<E> sortedMultiset) {
        return new UnmodifiableSortedMultiset((SortedMultiset) Preconditions.checkNotNull(sortedMultiset));
    }

    @Deprecated
    public static <E> Multiset<E> unmodifiableMultiset(ImmutableMultiset<E> immutableMultiset) {
        return (Multiset) Preconditions.checkNotNull(immutableMultiset);
    }

    public static <E> boolean setCountImpl(Multiset<E> multiset, E e2, int i2, int i3) {
        CollectPreconditions.checkNonnegative(i2, "oldCount");
        CollectPreconditions.checkNonnegative(i3, "newCount");
        if (multiset.count(e2) != i2) {
            return false;
        }
        multiset.setCount(e2, i3);
        return true;
    }

    private static <E> boolean addAllImpl(Multiset<E> multiset, Multiset<? extends E> multiset2) {
        if (multiset2 instanceof AbstractMapBasedMultiset) {
            return addAllImpl((Multiset) multiset, (AbstractMapBasedMultiset) multiset2);
        }
        if (multiset2.isEmpty()) {
            return false;
        }
        for (Multiset.Entry<? extends E> entry : multiset2.entrySet()) {
            multiset.add(entry.getElement(), entry.getCount());
        }
        return true;
    }

    @CanIgnoreReturnValue
    public static boolean removeOccurrences(Multiset<?> multiset, Multiset<?> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        Iterator<Multiset.Entry<?>> it = multiset.entrySet().iterator();
        boolean z2 = false;
        while (it.hasNext()) {
            Multiset.Entry<?> next = it.next();
            int iCount = multiset2.count(next.getElement());
            if (iCount >= next.getCount()) {
                it.remove();
            } else if (iCount > 0) {
                multiset.remove(next.getElement(), iCount);
            }
            z2 = true;
        }
        return z2;
    }

    private static <E> boolean addAllImpl(Multiset<E> multiset, AbstractMapBasedMultiset<? extends E> abstractMapBasedMultiset) {
        if (abstractMapBasedMultiset.isEmpty()) {
            return false;
        }
        abstractMapBasedMultiset.addTo(multiset);
        return true;
    }
}
