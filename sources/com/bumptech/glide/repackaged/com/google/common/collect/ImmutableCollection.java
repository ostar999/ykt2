package com.bumptech.glide.repackaged.com.google.common.collect;

import com.bumptech.glide.repackaged.com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

/* loaded from: classes2.dex */
public abstract class ImmutableCollection<E> extends AbstractCollection<E> implements Serializable {
    private transient ImmutableList<E> asList;

    public static abstract class ArrayBasedBuilder<E> extends Builder<E> {
        Object[] contents;
        int size;

        public ArrayBasedBuilder(int i2) {
            CollectPreconditions.checkNonnegative(i2, "initialCapacity");
            this.contents = new Object[i2];
            this.size = 0;
        }

        private void ensureCapacity(int i2) {
            Object[] objArr = this.contents;
            if (objArr.length < i2) {
                this.contents = ObjectArrays.arraysCopyOf(objArr, Builder.expandedCapacity(objArr.length, i2));
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection.Builder
        public /* bridge */ /* synthetic */ Builder add(Object obj) {
            return add((ArrayBasedBuilder<E>) obj);
        }

        @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection.Builder
        public ArrayBasedBuilder<E> add(E e2) {
            Preconditions.checkNotNull(e2);
            ensureCapacity(this.size + 1);
            Object[] objArr = this.contents;
            int i2 = this.size;
            this.size = i2 + 1;
            objArr[i2] = e2;
            return this;
        }
    }

    public static abstract class Builder<E> {
        public static int expandedCapacity(int i2, int i3) {
            if (i3 < 0) {
                throw new AssertionError("cannot store more than MAX_VALUE elements");
            }
            int iHighestOneBit = i2 + (i2 >> 1) + 1;
            if (iHighestOneBit < i3) {
                iHighestOneBit = Integer.highestOneBit(i3 - 1) << 1;
            }
            if (iHighestOneBit < 0) {
                return Integer.MAX_VALUE;
            }
            return iHighestOneBit;
        }

        public abstract Builder<E> add(E e2);

        public Builder<E> addAll(Iterator<? extends E> it) {
            while (it.hasNext()) {
                add(it.next());
            }
            return this;
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @Deprecated
    public final boolean add(E e2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @Deprecated
    public final boolean addAll(Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    public ImmutableList<E> asList() {
        ImmutableList<E> immutableList = this.asList;
        if (immutableList != null) {
            return immutableList;
        }
        ImmutableList<E> immutableListCreateAsList = createAsList();
        this.asList = immutableListCreateAsList;
        return immutableListCreateAsList;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public abstract boolean contains(Object obj);

    int copyIntoArray(Object[] objArr, int i2) {
        Iterator it = iterator();
        while (it.hasNext()) {
            objArr[i2] = it.next();
            i2++;
        }
        return i2;
    }

    public ImmutableList<E> createAsList() {
        int size = size();
        return size != 0 ? size != 1 ? new RegularImmutableAsList(this, toArray()) : ImmutableList.of((Object) iterator().next()) : ImmutableList.of();
    }

    abstract boolean isPartialView();

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set, java.util.NavigableSet
    public abstract UnmodifiableIterator<E> iterator();

    @Override // java.util.AbstractCollection, java.util.Collection
    @Deprecated
    public final boolean remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @Deprecated
    public final boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @Deprecated
    public final boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final Object[] toArray() {
        int size = size();
        if (size == 0) {
            return ObjectArrays.EMPTY_ARRAY;
        }
        Object[] objArr = new Object[size];
        copyIntoArray(objArr, 0);
        return objArr;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final <T> T[] toArray(T[] tArr) {
        Preconditions.checkNotNull(tArr);
        int size = size();
        if (tArr.length < size) {
            tArr = (T[]) ObjectArrays.newArray(tArr, size);
        } else if (tArr.length > size) {
            tArr[size] = null;
        }
        copyIntoArray(tArr, 0);
        return tArr;
    }
}
