package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;

@GwtCompatible(emulated = true)
/* loaded from: classes4.dex */
class RegularImmutableAsList<E> extends ImmutableAsList<E> {
    private final ImmutableCollection<E> delegate;
    private final ImmutableList<? extends E> delegateList;

    public RegularImmutableAsList(ImmutableCollection<E> immutableCollection, ImmutableList<? extends E> immutableList) {
        this.delegate = immutableCollection;
        this.delegateList = immutableList;
    }

    @Override // com.google.common.collect.ImmutableList, com.google.common.collect.ImmutableCollection
    @GwtIncompatible
    public int copyIntoArray(Object[] objArr, int i2) {
        return this.delegateList.copyIntoArray(objArr, i2);
    }

    @Override // com.google.common.collect.ImmutableAsList
    public ImmutableCollection<E> delegateCollection() {
        return this.delegate;
    }

    public ImmutableList<? extends E> delegateList() {
        return this.delegateList;
    }

    @Override // java.util.List
    public E get(int i2) {
        return this.delegateList.get(i2);
    }

    @Override // com.google.common.collect.ImmutableCollection
    public Object[] internalArray() {
        return this.delegateList.internalArray();
    }

    @Override // com.google.common.collect.ImmutableCollection
    public int internalArrayEnd() {
        return this.delegateList.internalArrayEnd();
    }

    @Override // com.google.common.collect.ImmutableCollection
    public int internalArrayStart() {
        return this.delegateList.internalArrayStart();
    }

    @Override // com.google.common.collect.ImmutableList, java.util.List
    public UnmodifiableListIterator<E> listIterator(int i2) {
        return this.delegateList.listIterator(i2);
    }

    public RegularImmutableAsList(ImmutableCollection<E> immutableCollection, Object[] objArr) {
        this(immutableCollection, ImmutableList.asImmutableList(objArr));
    }

    public RegularImmutableAsList(ImmutableCollection<E> immutableCollection, Object[] objArr, int i2) {
        this(immutableCollection, ImmutableList.asImmutableList(objArr, i2));
    }
}
