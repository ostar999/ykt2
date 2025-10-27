package com.bumptech.glide.repackaged.com.google.common.collect;

import com.bumptech.glide.repackaged.com.google.common.base.Preconditions;
import java.util.NoSuchElementException;

/* loaded from: classes2.dex */
abstract class AbstractIndexedListIterator<E> extends UnmodifiableListIterator<E> {
    private int position;
    private final int size;

    public AbstractIndexedListIterator(int i2, int i3) {
        Preconditions.checkPositionIndex(i3, i2);
        this.size = i2;
        this.position = i3;
    }

    public abstract E get(int i2);

    @Override // java.util.Iterator, java.util.ListIterator
    public final boolean hasNext() {
        return this.position < this.size;
    }

    @Override // java.util.ListIterator
    public final boolean hasPrevious() {
        return this.position > 0;
    }

    @Override // java.util.Iterator, java.util.ListIterator
    public final E next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int i2 = this.position;
        this.position = i2 + 1;
        return get(i2);
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        return this.position;
    }

    @Override // java.util.ListIterator
    public final E previous() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        int i2 = this.position - 1;
        this.position = i2;
        return get(i2);
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        return this.position - 1;
    }
}
