package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;
import kotlin.text.Typography;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
/* loaded from: classes4.dex */
public final class Iterators {

    public static final class ArrayItr<T> extends AbstractIndexedListIterator<T> {
        static final UnmodifiableListIterator<Object> EMPTY = new ArrayItr(new Object[0], 0, 0, 0);
        private final T[] array;
        private final int offset;

        public ArrayItr(T[] tArr, int i2, int i3, int i4) {
            super(i3, i4);
            this.array = tArr;
            this.offset = i2;
        }

        @Override // com.google.common.collect.AbstractIndexedListIterator
        public T get(int i2) {
            return this.array[this.offset + i2];
        }
    }

    public static class ConcatenatedIterator<T> implements Iterator<T> {
        private Iterator<? extends T> iterator = Iterators.emptyIterator();

        @NullableDecl
        private Deque<Iterator<? extends Iterator<? extends T>>> metaIterators;

        @NullableDecl
        private Iterator<? extends T> toRemove;
        private Iterator<? extends Iterator<? extends T>> topMetaIterator;

        public ConcatenatedIterator(Iterator<? extends Iterator<? extends T>> it) {
            this.topMetaIterator = (Iterator) Preconditions.checkNotNull(it);
        }

        @NullableDecl
        private Iterator<? extends Iterator<? extends T>> getTopMetaIterator() {
            while (true) {
                Iterator<? extends Iterator<? extends T>> it = this.topMetaIterator;
                if (it != null && it.hasNext()) {
                    return this.topMetaIterator;
                }
                Deque<Iterator<? extends Iterator<? extends T>>> deque = this.metaIterators;
                if (deque == null || deque.isEmpty()) {
                    return null;
                }
                this.topMetaIterator = this.metaIterators.removeFirst();
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            while (!((Iterator) Preconditions.checkNotNull(this.iterator)).hasNext()) {
                Iterator<? extends Iterator<? extends T>> topMetaIterator = getTopMetaIterator();
                this.topMetaIterator = topMetaIterator;
                if (topMetaIterator == null) {
                    return false;
                }
                Iterator<? extends T> next = topMetaIterator.next();
                this.iterator = next;
                if (next instanceof ConcatenatedIterator) {
                    ConcatenatedIterator concatenatedIterator = (ConcatenatedIterator) next;
                    this.iterator = concatenatedIterator.iterator;
                    if (this.metaIterators == null) {
                        this.metaIterators = new ArrayDeque();
                    }
                    this.metaIterators.addFirst(this.topMetaIterator);
                    if (concatenatedIterator.metaIterators != null) {
                        while (!concatenatedIterator.metaIterators.isEmpty()) {
                            this.metaIterators.addFirst(concatenatedIterator.metaIterators.removeLast());
                        }
                    }
                    this.topMetaIterator = concatenatedIterator.topMetaIterator;
                }
            }
            return true;
        }

        @Override // java.util.Iterator
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Iterator<? extends T> it = this.iterator;
            this.toRemove = it;
            return it.next();
        }

        @Override // java.util.Iterator
        public void remove() {
            CollectPreconditions.checkRemove(this.toRemove != null);
            this.toRemove.remove();
            this.toRemove = null;
        }
    }

    public enum EmptyModifiableIterator implements Iterator<Object> {
        INSTANCE;

        @Override // java.util.Iterator
        public boolean hasNext() {
            return false;
        }

        @Override // java.util.Iterator
        public Object next() {
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void remove() {
            CollectPreconditions.checkRemove(false);
        }
    }

    public static class MergingIterator<T> extends UnmodifiableIterator<T> {
        final Queue<PeekingIterator<T>> queue;

        public MergingIterator(Iterable<? extends Iterator<? extends T>> iterable, final Comparator<? super T> comparator) {
            this.queue = new PriorityQueue(2, new Comparator<PeekingIterator<T>>() { // from class: com.google.common.collect.Iterators.MergingIterator.1
                @Override // java.util.Comparator
                public int compare(PeekingIterator<T> peekingIterator, PeekingIterator<T> peekingIterator2) {
                    return comparator.compare(peekingIterator.peek(), peekingIterator2.peek());
                }
            });
            for (Iterator<? extends T> it : iterable) {
                if (it.hasNext()) {
                    this.queue.add(Iterators.peekingIterator(it));
                }
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return !this.queue.isEmpty();
        }

        @Override // java.util.Iterator
        public T next() {
            PeekingIterator<T> peekingIteratorRemove = this.queue.remove();
            T next = peekingIteratorRemove.next();
            if (peekingIteratorRemove.hasNext()) {
                this.queue.add(peekingIteratorRemove);
            }
            return next;
        }
    }

    public static class PeekingImpl<E> implements PeekingIterator<E> {
        private boolean hasPeeked;
        private final Iterator<? extends E> iterator;

        @NullableDecl
        private E peekedElement;

        public PeekingImpl(Iterator<? extends E> it) {
            this.iterator = (Iterator) Preconditions.checkNotNull(it);
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.hasPeeked || this.iterator.hasNext();
        }

        @Override // com.google.common.collect.PeekingIterator, java.util.Iterator
        public E next() {
            if (!this.hasPeeked) {
                return this.iterator.next();
            }
            E e2 = this.peekedElement;
            this.hasPeeked = false;
            this.peekedElement = null;
            return e2;
        }

        @Override // com.google.common.collect.PeekingIterator
        public E peek() {
            if (!this.hasPeeked) {
                this.peekedElement = this.iterator.next();
                this.hasPeeked = true;
            }
            return this.peekedElement;
        }

        @Override // com.google.common.collect.PeekingIterator, java.util.Iterator
        public void remove() {
            Preconditions.checkState(!this.hasPeeked, "Can't remove after you've peeked at next");
            this.iterator.remove();
        }
    }

    private Iterators() {
    }

    @CanIgnoreReturnValue
    public static <T> boolean addAll(Collection<T> collection, Iterator<? extends T> it) {
        Preconditions.checkNotNull(collection);
        Preconditions.checkNotNull(it);
        boolean zAdd = false;
        while (it.hasNext()) {
            zAdd |= collection.add(it.next());
        }
        return zAdd;
    }

    @CanIgnoreReturnValue
    public static int advance(Iterator<?> it, int i2) {
        Preconditions.checkNotNull(it);
        int i3 = 0;
        Preconditions.checkArgument(i2 >= 0, "numberToAdvance must be nonnegative");
        while (i3 < i2 && it.hasNext()) {
            it.next();
            i3++;
        }
        return i3;
    }

    public static <T> boolean all(Iterator<T> it, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate);
        while (it.hasNext()) {
            if (!predicate.apply(it.next())) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean any(Iterator<T> it, Predicate<? super T> predicate) {
        return indexOf(it, predicate) != -1;
    }

    public static <T> Enumeration<T> asEnumeration(final Iterator<T> it) {
        Preconditions.checkNotNull(it);
        return new Enumeration<T>() { // from class: com.google.common.collect.Iterators.11
            @Override // java.util.Enumeration
            public boolean hasMoreElements() {
                return it.hasNext();
            }

            @Override // java.util.Enumeration
            public T nextElement() {
                return (T) it.next();
            }
        };
    }

    public static <T> ListIterator<T> cast(Iterator<T> it) {
        return (ListIterator) it;
    }

    public static void checkNonnegative(int i2) {
        if (i2 >= 0) {
            return;
        }
        throw new IndexOutOfBoundsException("position (" + i2 + ") must not be negative");
    }

    public static void clear(Iterator<?> it) {
        Preconditions.checkNotNull(it);
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
    }

    public static <T> Iterator<T> concat(Iterator<? extends T> it, Iterator<? extends T> it2) {
        Preconditions.checkNotNull(it);
        Preconditions.checkNotNull(it2);
        return concat(consumingForArray(it, it2));
    }

    public static <T> Iterator<T> concatNoDefensiveCopy(Iterator<? extends T>... itArr) {
        for (Iterator it : (Iterator[]) Preconditions.checkNotNull(itArr)) {
            Preconditions.checkNotNull(it);
        }
        return concat(consumingForArray(itArr));
    }

    private static <T> Iterator<T> consumingForArray(final T... tArr) {
        return new UnmodifiableIterator<T>() { // from class: com.google.common.collect.Iterators.3
            int index = 0;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.index < tArr.length;
            }

            @Override // java.util.Iterator
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Object[] objArr = tArr;
                int i2 = this.index;
                T t2 = (T) objArr[i2];
                objArr[i2] = null;
                this.index = i2 + 1;
                return t2;
            }
        };
    }

    public static <T> Iterator<T> consumingIterator(final Iterator<T> it) {
        Preconditions.checkNotNull(it);
        return new UnmodifiableIterator<T>() { // from class: com.google.common.collect.Iterators.8
            @Override // java.util.Iterator
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override // java.util.Iterator
            public T next() {
                T t2 = (T) it.next();
                it.remove();
                return t2;
            }

            public String toString() {
                return "Iterators.consumingIterator(...)";
            }
        };
    }

    public static boolean contains(Iterator<?> it, @NullableDecl Object obj) {
        if (obj == null) {
            while (it.hasNext()) {
                if (it.next() == null) {
                    return true;
                }
            }
            return false;
        }
        while (it.hasNext()) {
            if (obj.equals(it.next())) {
                return true;
            }
        }
        return false;
    }

    public static <T> Iterator<T> cycle(final Iterable<T> iterable) {
        Preconditions.checkNotNull(iterable);
        return new Iterator<T>() { // from class: com.google.common.collect.Iterators.2
            Iterator<T> iterator = Iterators.emptyModifiableIterator();

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.iterator.hasNext() || iterable.iterator().hasNext();
            }

            @Override // java.util.Iterator
            public T next() {
                if (!this.iterator.hasNext()) {
                    Iterator<T> it = iterable.iterator();
                    this.iterator = it;
                    if (!it.hasNext()) {
                        throw new NoSuchElementException();
                    }
                }
                return this.iterator.next();
            }

            @Override // java.util.Iterator
            public void remove() {
                this.iterator.remove();
            }
        };
    }

    public static boolean elementsEqual(Iterator<?> it, Iterator<?> it2) {
        while (it.hasNext()) {
            if (!it2.hasNext() || !Objects.equal(it.next(), it2.next())) {
                return false;
            }
        }
        return !it2.hasNext();
    }

    public static <T> UnmodifiableIterator<T> emptyIterator() {
        return emptyListIterator();
    }

    public static <T> UnmodifiableListIterator<T> emptyListIterator() {
        return (UnmodifiableListIterator<T>) ArrayItr.EMPTY;
    }

    public static <T> Iterator<T> emptyModifiableIterator() {
        return EmptyModifiableIterator.INSTANCE;
    }

    public static <T> UnmodifiableIterator<T> filter(final Iterator<T> it, final Predicate<? super T> predicate) {
        Preconditions.checkNotNull(it);
        Preconditions.checkNotNull(predicate);
        return new AbstractIterator<T>() { // from class: com.google.common.collect.Iterators.5
            @Override // com.google.common.collect.AbstractIterator
            public T computeNext() {
                while (it.hasNext()) {
                    T t2 = (T) it.next();
                    if (predicate.apply(t2)) {
                        return t2;
                    }
                }
                return endOfData();
            }
        };
    }

    public static <T> T find(Iterator<T> it, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(it);
        Preconditions.checkNotNull(predicate);
        while (it.hasNext()) {
            T next = it.next();
            if (predicate.apply(next)) {
                return next;
            }
        }
        throw new NoSuchElementException();
    }

    @SafeVarargs
    public static <T> UnmodifiableIterator<T> forArray(T... tArr) {
        return forArray(tArr, 0, tArr.length, 0);
    }

    public static <T> UnmodifiableIterator<T> forEnumeration(final Enumeration<T> enumeration) {
        Preconditions.checkNotNull(enumeration);
        return new UnmodifiableIterator<T>() { // from class: com.google.common.collect.Iterators.10
            @Override // java.util.Iterator
            public boolean hasNext() {
                return enumeration.hasMoreElements();
            }

            @Override // java.util.Iterator
            public T next() {
                return (T) enumeration.nextElement();
            }
        };
    }

    public static int frequency(Iterator<?> it, @NullableDecl Object obj) {
        int i2 = 0;
        while (contains(it, obj)) {
            i2++;
        }
        return i2;
    }

    public static <T> T get(Iterator<T> it, int i2) {
        checkNonnegative(i2);
        int iAdvance = advance(it, i2);
        if (it.hasNext()) {
            return it.next();
        }
        throw new IndexOutOfBoundsException("position (" + i2 + ") must be less than the number of elements that remained (" + iAdvance + ")");
    }

    public static <T> T getLast(Iterator<T> it) {
        T next;
        do {
            next = it.next();
        } while (it.hasNext());
        return next;
    }

    @NullableDecl
    public static <T> T getNext(Iterator<? extends T> it, @NullableDecl T t2) {
        return it.hasNext() ? it.next() : t2;
    }

    public static <T> T getOnlyElement(Iterator<T> it) {
        T next = it.next();
        if (!it.hasNext()) {
            return next;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("expected one element but was: <");
        sb.append(next);
        for (int i2 = 0; i2 < 4 && it.hasNext(); i2++) {
            sb.append(", ");
            sb.append(it.next());
        }
        if (it.hasNext()) {
            sb.append(", ...");
        }
        sb.append(Typography.greater);
        throw new IllegalArgumentException(sb.toString());
    }

    public static <T> int indexOf(Iterator<T> it, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate, "predicate");
        int i2 = 0;
        while (it.hasNext()) {
            if (predicate.apply(it.next())) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    public static <T> Iterator<T> limit(final Iterator<T> it, final int i2) {
        Preconditions.checkNotNull(it);
        Preconditions.checkArgument(i2 >= 0, "limit is negative");
        return new Iterator<T>() { // from class: com.google.common.collect.Iterators.7
            private int count;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.count < i2 && it.hasNext();
            }

            @Override // java.util.Iterator
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                this.count++;
                return (T) it.next();
            }

            @Override // java.util.Iterator
            public void remove() {
                it.remove();
            }
        };
    }

    @Beta
    public static <T> UnmodifiableIterator<T> mergeSorted(Iterable<? extends Iterator<? extends T>> iterable, Comparator<? super T> comparator) {
        Preconditions.checkNotNull(iterable, "iterators");
        Preconditions.checkNotNull(comparator, "comparator");
        return new MergingIterator(iterable, comparator);
    }

    public static <T> UnmodifiableIterator<List<T>> paddedPartition(Iterator<T> it, int i2) {
        return partitionImpl(it, i2, true);
    }

    public static <T> UnmodifiableIterator<List<T>> partition(Iterator<T> it, int i2) {
        return partitionImpl(it, i2, false);
    }

    private static <T> UnmodifiableIterator<List<T>> partitionImpl(final Iterator<T> it, final int i2, final boolean z2) {
        Preconditions.checkNotNull(it);
        Preconditions.checkArgument(i2 > 0);
        return new UnmodifiableIterator<List<T>>() { // from class: com.google.common.collect.Iterators.4
            @Override // java.util.Iterator
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override // java.util.Iterator
            public List<T> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Object[] objArr = new Object[i2];
                int i3 = 0;
                while (i3 < i2 && it.hasNext()) {
                    objArr[i3] = it.next();
                    i3++;
                }
                for (int i4 = i3; i4 < i2; i4++) {
                    objArr[i4] = null;
                }
                List<T> listUnmodifiableList = Collections.unmodifiableList(Arrays.asList(objArr));
                return (z2 || i3 == i2) ? listUnmodifiableList : listUnmodifiableList.subList(0, i3);
            }
        };
    }

    public static <T> PeekingIterator<T> peekingIterator(Iterator<? extends T> it) {
        return it instanceof PeekingImpl ? (PeekingImpl) it : new PeekingImpl(it);
    }

    @NullableDecl
    public static <T> T pollNext(Iterator<T> it) {
        if (!it.hasNext()) {
            return null;
        }
        T next = it.next();
        it.remove();
        return next;
    }

    @CanIgnoreReturnValue
    public static boolean removeAll(Iterator<?> it, Collection<?> collection) {
        Preconditions.checkNotNull(collection);
        boolean z2 = false;
        while (it.hasNext()) {
            if (collection.contains(it.next())) {
                it.remove();
                z2 = true;
            }
        }
        return z2;
    }

    @CanIgnoreReturnValue
    public static <T> boolean removeIf(Iterator<T> it, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate);
        boolean z2 = false;
        while (it.hasNext()) {
            if (predicate.apply(it.next())) {
                it.remove();
                z2 = true;
            }
        }
        return z2;
    }

    @CanIgnoreReturnValue
    public static boolean retainAll(Iterator<?> it, Collection<?> collection) {
        Preconditions.checkNotNull(collection);
        boolean z2 = false;
        while (it.hasNext()) {
            if (!collection.contains(it.next())) {
                it.remove();
                z2 = true;
            }
        }
        return z2;
    }

    public static <T> UnmodifiableIterator<T> singletonIterator(@NullableDecl final T t2) {
        return new UnmodifiableIterator<T>() { // from class: com.google.common.collect.Iterators.9
            boolean done;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return !this.done;
            }

            @Override // java.util.Iterator
            public T next() {
                if (this.done) {
                    throw new NoSuchElementException();
                }
                this.done = true;
                return (T) t2;
            }
        };
    }

    public static int size(Iterator<?> it) {
        long j2 = 0;
        while (it.hasNext()) {
            it.next();
            j2++;
        }
        return Ints.saturatedCast(j2);
    }

    @GwtIncompatible
    public static <T> T[] toArray(Iterator<? extends T> it, Class<T> cls) {
        return (T[]) Iterables.toArray(Lists.newArrayList(it), cls);
    }

    public static String toString(Iterator<?> it) {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        boolean z2 = true;
        while (it.hasNext()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append(it.next());
            z2 = false;
        }
        sb.append(']');
        return sb.toString();
    }

    public static <F, T> Iterator<T> transform(Iterator<F> it, final Function<? super F, ? extends T> function) {
        Preconditions.checkNotNull(function);
        return new TransformedIterator<F, T>(it) { // from class: com.google.common.collect.Iterators.6
            @Override // com.google.common.collect.TransformedIterator
            public T transform(F f2) {
                return (T) function.apply(f2);
            }
        };
    }

    public static <T> Optional<T> tryFind(Iterator<T> it, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(it);
        Preconditions.checkNotNull(predicate);
        while (it.hasNext()) {
            T next = it.next();
            if (predicate.apply(next)) {
                return Optional.of(next);
            }
        }
        return Optional.absent();
    }

    public static <T> UnmodifiableIterator<T> unmodifiableIterator(final Iterator<? extends T> it) {
        Preconditions.checkNotNull(it);
        return it instanceof UnmodifiableIterator ? (UnmodifiableIterator) it : new UnmodifiableIterator<T>() { // from class: com.google.common.collect.Iterators.1
            @Override // java.util.Iterator
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override // java.util.Iterator
            public T next() {
                return (T) it.next();
            }
        };
    }

    public static <T> UnmodifiableListIterator<T> forArray(T[] tArr, int i2, int i3, int i4) {
        Preconditions.checkArgument(i3 >= 0);
        Preconditions.checkPositionIndexes(i2, i2 + i3, tArr.length);
        Preconditions.checkPositionIndex(i4, i3);
        return i3 == 0 ? emptyListIterator() : new ArrayItr(tArr, i2, i3, i4);
    }

    @SafeVarargs
    public static <T> Iterator<T> cycle(T... tArr) {
        return cycle(Lists.newArrayList(tArr));
    }

    @NullableDecl
    public static <T> T getLast(Iterator<? extends T> it, @NullableDecl T t2) {
        return it.hasNext() ? (T) getLast(it) : t2;
    }

    public static <T> Iterator<T> concat(Iterator<? extends T> it, Iterator<? extends T> it2, Iterator<? extends T> it3) {
        Preconditions.checkNotNull(it);
        Preconditions.checkNotNull(it2);
        Preconditions.checkNotNull(it3);
        return concat(consumingForArray(it, it2, it3));
    }

    @GwtIncompatible
    public static <T> UnmodifiableIterator<T> filter(Iterator<?> it, Class<T> cls) {
        return filter(it, Predicates.instanceOf(cls));
    }

    @Deprecated
    public static <T> PeekingIterator<T> peekingIterator(PeekingIterator<T> peekingIterator) {
        return (PeekingIterator) Preconditions.checkNotNull(peekingIterator);
    }

    @Deprecated
    public static <T> UnmodifiableIterator<T> unmodifiableIterator(UnmodifiableIterator<T> unmodifiableIterator) {
        return (UnmodifiableIterator) Preconditions.checkNotNull(unmodifiableIterator);
    }

    @NullableDecl
    public static <T> T get(Iterator<? extends T> it, int i2, @NullableDecl T t2) {
        checkNonnegative(i2);
        advance(it, i2);
        return (T) getNext(it, t2);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [T, java.lang.Object] */
    @NullableDecl
    public static <T> T find(Iterator<? extends T> it, Predicate<? super T> predicate, @NullableDecl T t2) {
        Preconditions.checkNotNull(it);
        Preconditions.checkNotNull(predicate);
        while (it.hasNext()) {
            T next = it.next();
            if (predicate.apply(next)) {
                return next;
            }
        }
        return t2;
    }

    public static <T> Iterator<T> concat(Iterator<? extends T> it, Iterator<? extends T> it2, Iterator<? extends T> it3, Iterator<? extends T> it4) {
        Preconditions.checkNotNull(it);
        Preconditions.checkNotNull(it2);
        Preconditions.checkNotNull(it3);
        Preconditions.checkNotNull(it4);
        return concat(consumingForArray(it, it2, it3, it4));
    }

    @NullableDecl
    public static <T> T getOnlyElement(Iterator<? extends T> it, @NullableDecl T t2) {
        return it.hasNext() ? (T) getOnlyElement(it) : t2;
    }

    public static <T> Iterator<T> concat(Iterator<? extends T>... itArr) {
        return concatNoDefensiveCopy((Iterator[]) Arrays.copyOf(itArr, itArr.length));
    }

    public static <T> Iterator<T> concat(Iterator<? extends Iterator<? extends T>> it) {
        return new ConcatenatedIterator(it);
    }
}
