package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Multiset;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;

@GwtCompatible(emulated = true)
/* loaded from: classes4.dex */
public interface SortedMultiset<E> extends SortedMultisetBridge<E>, SortedIterable<E> {
    Comparator<? super E> comparator();

    SortedMultiset<E> descendingMultiset();

    @Override // com.google.common.collect.SortedMultisetBridge, com.google.common.collect.Multiset
    NavigableSet<E> elementSet();

    @Override // com.google.common.collect.Multiset
    Set<Multiset.Entry<E>> entrySet();

    Multiset.Entry<E> firstEntry();

    SortedMultiset<E> headMultiset(E e2, BoundType boundType);

    @Override // com.google.common.collect.Multiset, java.util.Collection, java.lang.Iterable
    Iterator<E> iterator();

    Multiset.Entry<E> lastEntry();

    Multiset.Entry<E> pollFirstEntry();

    Multiset.Entry<E> pollLastEntry();

    SortedMultiset<E> subMultiset(E e2, BoundType boundType, E e3, BoundType boundType2);

    SortedMultiset<E> tailMultiset(E e2, BoundType boundType);
}
