package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.Multiset;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true, serializable = true)
/* loaded from: classes4.dex */
public abstract class ImmutableMultiset<E> extends ImmutableMultisetGwtSerializationDependencies<E> implements Multiset<E> {

    @LazyInit
    private transient ImmutableList<E> asList;

    @LazyInit
    private transient ImmutableSet<Multiset.Entry<E>> entrySet;

    public static class Builder<E> extends ImmutableCollection.Builder<E> {
        boolean buildInvoked;
        ObjectCountHashMap<E> contents;
        boolean isLinkedHash;

        public Builder() {
            this(4);
        }

        @NullableDecl
        public static <T> ObjectCountHashMap<T> tryGetMap(Iterable<T> iterable) {
            if (iterable instanceof RegularImmutableMultiset) {
                return ((RegularImmutableMultiset) iterable).contents;
            }
            if (iterable instanceof AbstractMapBasedMultiset) {
                return ((AbstractMapBasedMultiset) iterable).backingMap;
            }
            return null;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.ImmutableCollection.Builder
        @CanIgnoreReturnValue
        public /* bridge */ /* synthetic */ ImmutableCollection.Builder add(Object obj) {
            return add((Builder<E>) obj);
        }

        @CanIgnoreReturnValue
        public Builder<E> addCopies(E e2, int i2) {
            if (i2 == 0) {
                return this;
            }
            if (this.buildInvoked) {
                this.contents = new ObjectCountHashMap<>(this.contents);
                this.isLinkedHash = false;
            }
            this.buildInvoked = false;
            Preconditions.checkNotNull(e2);
            ObjectCountHashMap<E> objectCountHashMap = this.contents;
            objectCountHashMap.put(e2, i2 + objectCountHashMap.get(e2));
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<E> setCount(E e2, int i2) {
            if (i2 == 0 && !this.isLinkedHash) {
                this.contents = new ObjectCountLinkedHashMap(this.contents);
                this.isLinkedHash = true;
            } else if (this.buildInvoked) {
                this.contents = new ObjectCountHashMap<>(this.contents);
                this.isLinkedHash = false;
            }
            this.buildInvoked = false;
            Preconditions.checkNotNull(e2);
            if (i2 == 0) {
                this.contents.remove(e2);
            } else {
                this.contents.put(Preconditions.checkNotNull(e2), i2);
            }
            return this;
        }

        public Builder(int i2) {
            this.buildInvoked = false;
            this.isLinkedHash = false;
            this.contents = ObjectCountHashMap.createWithExpectedSize(i2);
        }

        @Override // com.google.common.collect.ImmutableCollection.Builder
        public ImmutableMultiset<E> build() {
            if (this.contents.size() == 0) {
                return ImmutableMultiset.of();
            }
            if (this.isLinkedHash) {
                this.contents = new ObjectCountHashMap<>(this.contents);
                this.isLinkedHash = false;
            }
            this.buildInvoked = true;
            return new RegularImmutableMultiset(this.contents);
        }

        @Override // com.google.common.collect.ImmutableCollection.Builder
        @CanIgnoreReturnValue
        public Builder<E> add(E e2) {
            return addCopies(e2, 1);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.ImmutableCollection.Builder
        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterable<? extends E> iterable) {
            if (iterable instanceof Multiset) {
                Multiset multisetCast = Multisets.cast(iterable);
                ObjectCountHashMap objectCountHashMapTryGetMap = tryGetMap(multisetCast);
                if (objectCountHashMapTryGetMap != null) {
                    ObjectCountHashMap<E> objectCountHashMap = this.contents;
                    objectCountHashMap.ensureCapacity(Math.max(objectCountHashMap.size(), objectCountHashMapTryGetMap.size()));
                    for (int iFirstIndex = objectCountHashMapTryGetMap.firstIndex(); iFirstIndex >= 0; iFirstIndex = objectCountHashMapTryGetMap.nextIndex(iFirstIndex)) {
                        addCopies(objectCountHashMapTryGetMap.getKey(iFirstIndex), objectCountHashMapTryGetMap.getValue(iFirstIndex));
                    }
                } else {
                    Set<Multiset.Entry<E>> setEntrySet = multisetCast.entrySet();
                    ObjectCountHashMap<E> objectCountHashMap2 = this.contents;
                    objectCountHashMap2.ensureCapacity(Math.max(objectCountHashMap2.size(), setEntrySet.size()));
                    for (Multiset.Entry<E> entry : multisetCast.entrySet()) {
                        addCopies(entry.getElement(), entry.getCount());
                    }
                }
            } else {
                super.addAll((Iterable) iterable);
            }
            return this;
        }

        @Override // com.google.common.collect.ImmutableCollection.Builder
        @CanIgnoreReturnValue
        public Builder<E> add(E... eArr) {
            super.add((Object[]) eArr);
            return this;
        }

        public Builder(boolean z2) {
            this.buildInvoked = false;
            this.isLinkedHash = false;
            this.contents = null;
        }

        @Override // com.google.common.collect.ImmutableCollection.Builder
        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterator<? extends E> it) {
            super.addAll((Iterator) it);
            return this;
        }
    }

    public final class EntrySet extends IndexedImmutableSet<Multiset.Entry<E>> {
        private static final long serialVersionUID = 0;

        private EntrySet() {
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            if (!(obj instanceof Multiset.Entry)) {
                return false;
            }
            Multiset.Entry entry = (Multiset.Entry) obj;
            return entry.getCount() > 0 && ImmutableMultiset.this.count(entry.getElement()) == entry.getCount();
        }

        @Override // com.google.common.collect.ImmutableSet, java.util.Collection, java.util.Set
        public int hashCode() {
            return ImmutableMultiset.this.hashCode();
        }

        @Override // com.google.common.collect.ImmutableCollection
        public boolean isPartialView() {
            return ImmutableMultiset.this.isPartialView();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return ImmutableMultiset.this.elementSet().size();
        }

        @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection
        @GwtIncompatible
        public Object writeReplace() {
            return new EntrySetSerializedForm(ImmutableMultiset.this);
        }

        @Override // com.google.common.collect.IndexedImmutableSet
        public Multiset.Entry<E> get(int i2) {
            return ImmutableMultiset.this.getEntry(i2);
        }
    }

    @GwtIncompatible
    public static class EntrySetSerializedForm<E> implements Serializable {
        final ImmutableMultiset<E> multiset;

        public EntrySetSerializedForm(ImmutableMultiset<E> immutableMultiset) {
            this.multiset = immutableMultiset;
        }

        public Object readResolve() {
            return this.multiset.entrySet();
        }
    }

    public static <E> Builder<E> builder() {
        return new Builder<>();
    }

    private static <E> ImmutableMultiset<E> copyFromElements(E... eArr) {
        return new Builder().add((Object[]) eArr).build();
    }

    public static <E> ImmutableMultiset<E> copyFromEntries(Collection<? extends Multiset.Entry<? extends E>> collection) {
        Builder builder = new Builder(collection.size());
        for (Multiset.Entry<? extends E> entry : collection) {
            builder.addCopies(entry.getElement(), entry.getCount());
        }
        return builder.build();
    }

    public static <E> ImmutableMultiset<E> copyOf(E[] eArr) {
        return copyFromElements(eArr);
    }

    private ImmutableSet<Multiset.Entry<E>> createEntrySet() {
        return isEmpty() ? ImmutableSet.of() : new EntrySet();
    }

    public static <E> ImmutableMultiset<E> of() {
        return RegularImmutableMultiset.EMPTY;
    }

    @Override // com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    @Deprecated
    public final int add(E e2, int i2) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.ImmutableCollection
    public ImmutableList<E> asList() {
        ImmutableList<E> immutableList = this.asList;
        if (immutableList != null) {
            return immutableList;
        }
        ImmutableList<E> immutableListAsList = super.asList();
        this.asList = immutableListAsList;
        return immutableListAsList;
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(@NullableDecl Object obj) {
        return count(obj) > 0;
    }

    @Override // com.google.common.collect.ImmutableCollection
    @GwtIncompatible
    int copyIntoArray(Object[] objArr, int i2) {
        UnmodifiableIterator<Multiset.Entry<E>> it = entrySet().iterator();
        while (it.hasNext()) {
            Multiset.Entry<E> next = it.next();
            Arrays.fill(objArr, i2, next.getCount() + i2, next.getElement());
            i2 += next.getCount();
        }
        return i2;
    }

    @Override // com.google.common.collect.Multiset
    public abstract ImmutableSet<E> elementSet();

    @Override // java.util.Collection, com.google.common.collect.Multiset
    public boolean equals(@NullableDecl Object obj) {
        return Multisets.equalsImpl(this, obj);
    }

    public abstract Multiset.Entry<E> getEntry(int i2);

    @Override // java.util.Collection, com.google.common.collect.Multiset
    public int hashCode() {
        return Sets.hashCodeImpl(entrySet());
    }

    @Override // com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    @Deprecated
    public final int remove(Object obj, int i2) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    @Deprecated
    public final int setCount(E e2, int i2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, com.google.common.collect.Multiset
    public String toString() {
        return entrySet().toString();
    }

    @Override // com.google.common.collect.ImmutableCollection
    @GwtIncompatible
    abstract Object writeReplace();

    public static <E> ImmutableMultiset<E> copyOf(Iterable<? extends E> iterable) {
        if (iterable instanceof ImmutableMultiset) {
            ImmutableMultiset<E> immutableMultiset = (ImmutableMultiset) iterable;
            if (!immutableMultiset.isPartialView()) {
                return immutableMultiset;
            }
        }
        Builder builder = new Builder(Multisets.inferDistinctElements(iterable));
        builder.addAll((Iterable) iterable);
        return builder.build();
    }

    public static <E> ImmutableMultiset<E> of(E e2) {
        return copyFromElements(e2);
    }

    @Override // com.google.common.collect.Multiset
    public ImmutableSet<Multiset.Entry<E>> entrySet() {
        ImmutableSet<Multiset.Entry<E>> immutableSet = this.entrySet;
        if (immutableSet != null) {
            return immutableSet;
        }
        ImmutableSet<Multiset.Entry<E>> immutableSetCreateEntrySet = createEntrySet();
        this.entrySet = immutableSetCreateEntrySet;
        return immutableSetCreateEntrySet;
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set, java.util.NavigableSet, com.google.common.collect.SortedIterable
    public UnmodifiableIterator<E> iterator() {
        final UnmodifiableIterator<Multiset.Entry<E>> it = entrySet().iterator();
        return new UnmodifiableIterator<E>() { // from class: com.google.common.collect.ImmutableMultiset.1

            @MonotonicNonNullDecl
            E element;
            int remaining;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.remaining > 0 || it.hasNext();
            }

            @Override // java.util.Iterator
            public E next() {
                if (this.remaining <= 0) {
                    Multiset.Entry entry = (Multiset.Entry) it.next();
                    this.element = (E) entry.getElement();
                    this.remaining = entry.getCount();
                }
                this.remaining--;
                return this.element;
            }
        };
    }

    @Override // com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    @Deprecated
    public final boolean setCount(E e2, int i2, int i3) {
        throw new UnsupportedOperationException();
    }

    public static <E> ImmutableMultiset<E> of(E e2, E e3) {
        return copyFromElements(e2, e3);
    }

    public static <E> ImmutableMultiset<E> of(E e2, E e3, E e4) {
        return copyFromElements(e2, e3, e4);
    }

    public static <E> ImmutableMultiset<E> of(E e2, E e3, E e4, E e5) {
        return copyFromElements(e2, e3, e4, e5);
    }

    public static <E> ImmutableMultiset<E> of(E e2, E e3, E e4, E e5, E e6) {
        return copyFromElements(e2, e3, e4, e5, e6);
    }

    public static <E> ImmutableMultiset<E> of(E e2, E e3, E e4, E e5, E e6, E e7, E... eArr) {
        return new Builder().add((Builder) e2).add((Builder<E>) e3).add((Builder<E>) e4).add((Builder<E>) e5).add((Builder<E>) e6).add((Builder<E>) e7).add((Object[]) eArr).build();
    }

    public static <E> ImmutableMultiset<E> copyOf(Iterator<? extends E> it) {
        return new Builder().addAll((Iterator) it).build();
    }
}
