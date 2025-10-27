package com.bumptech.glide.repackaged.com.google.common.collect;

import com.bumptech.glide.repackaged.com.google.common.base.Preconditions;

/* loaded from: classes2.dex */
class RegularImmutableList<E> extends ImmutableList<E> {
    static final ImmutableList<Object> EMPTY = new RegularImmutableList(ObjectArrays.EMPTY_ARRAY);
    private final transient Object[] array;
    private final transient int offset;
    private final transient int size;

    public RegularImmutableList(Object[] objArr, int i2, int i3) {
        this.offset = i2;
        this.size = i3;
        this.array = objArr;
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableList, com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection
    public int copyIntoArray(Object[] objArr, int i2) {
        System.arraycopy(this.array, this.offset, objArr, i2, this.size);
        return i2 + this.size;
    }

    @Override // java.util.List
    public E get(int i2) {
        Preconditions.checkElementIndex(i2, this.size);
        return (E) this.array[i2 + this.offset];
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection
    public boolean isPartialView() {
        return this.size != this.array.length;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.size;
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableList
    public ImmutableList<E> subListUnchecked(int i2, int i3) {
        return new RegularImmutableList(this.array, this.offset + i2, i3 - i2);
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableList, java.util.List
    public UnmodifiableListIterator<E> listIterator(int i2) {
        return Iterators.forArray(this.array, this.offset, this.size, i2);
    }

    public RegularImmutableList(Object[] objArr) {
        this(objArr, 0, objArr.length);
    }
}
