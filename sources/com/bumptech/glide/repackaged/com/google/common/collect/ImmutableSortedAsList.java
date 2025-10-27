package com.bumptech.glide.repackaged.com.google.common.collect;

import java.util.Comparator;

/* loaded from: classes2.dex */
final class ImmutableSortedAsList<E> extends RegularImmutableAsList<E> implements SortedIterable<E> {
    public ImmutableSortedAsList(ImmutableSortedSet<E> immutableSortedSet, ImmutableList<E> immutableList) {
        super(immutableSortedSet, immutableList);
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.SortedIterable
    public Comparator<? super E> comparator() {
        return delegateCollection().comparator();
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableAsList, com.bumptech.glide.repackaged.com.google.common.collect.ImmutableList, com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(Object obj) {
        return indexOf(obj) >= 0;
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableList, java.util.List
    public int indexOf(Object obj) {
        int iIndexOf = delegateCollection().indexOf(obj);
        if (iIndexOf < 0 || !get(iIndexOf).equals(obj)) {
            return -1;
        }
        return iIndexOf;
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableList, java.util.List
    public int lastIndexOf(Object obj) {
        return indexOf(obj);
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableList
    public ImmutableList<E> subListUnchecked(int i2, int i3) {
        return new RegularImmutableSortedSet(super.subListUnchecked(i2, i3), comparator()).asList();
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.RegularImmutableAsList, com.bumptech.glide.repackaged.com.google.common.collect.ImmutableAsList
    public ImmutableSortedSet<E> delegateCollection() {
        return (ImmutableSortedSet) super.delegateCollection();
    }
}
