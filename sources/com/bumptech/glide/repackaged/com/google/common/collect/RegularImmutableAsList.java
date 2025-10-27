package com.bumptech.glide.repackaged.com.google.common.collect;

import com.google.j2objc.annotations.Weak;

/* loaded from: classes2.dex */
class RegularImmutableAsList<E> extends ImmutableAsList<E> {

    @Weak
    private final ImmutableCollection<E> delegate;
    private final ImmutableList<? extends E> delegateList;

    public RegularImmutableAsList(ImmutableCollection<E> immutableCollection, ImmutableList<? extends E> immutableList) {
        this.delegate = immutableCollection;
        this.delegateList = immutableList;
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableList, com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection
    int copyIntoArray(Object[] objArr, int i2) {
        return this.delegateList.copyIntoArray(objArr, i2);
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableAsList
    public ImmutableCollection<E> delegateCollection() {
        return this.delegate;
    }

    @Override // java.util.List
    public E get(int i2) {
        return this.delegateList.get(i2);
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableList, java.util.List
    public UnmodifiableListIterator<E> listIterator(int i2) {
        return this.delegateList.listIterator(i2);
    }

    public RegularImmutableAsList(ImmutableCollection<E> immutableCollection, Object[] objArr) {
        this(immutableCollection, ImmutableList.asImmutableList(objArr));
    }
}
