package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.ImmutableSortedSet;

@GwtIncompatible
/* loaded from: classes4.dex */
abstract class ImmutableSortedSetFauxverideShim<E> extends ImmutableSet<E> {
    @Deprecated
    public static <E> ImmutableSortedSet.Builder<E> builder() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public static <E> ImmutableSortedSet.Builder<E> builderWithExpectedSize(int i2) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public static <E> ImmutableSortedSet<E> copyOf(E[] eArr) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public static <E> ImmutableSortedSet<E> of(E e2) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public static <E> ImmutableSortedSet<E> of(E e2, E e3) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public static <E> ImmutableSortedSet<E> of(E e2, E e3, E e4) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public static <E> ImmutableSortedSet<E> of(E e2, E e3, E e4, E e5) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public static <E> ImmutableSortedSet<E> of(E e2, E e3, E e4, E e5, E e6) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public static <E> ImmutableSortedSet<E> of(E e2, E e3, E e4, E e5, E e6, E e7, E... eArr) {
        throw new UnsupportedOperationException();
    }
}
