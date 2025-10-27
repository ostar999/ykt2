package com.bumptech.glide.repackaged.com.google.common.collect;

import java.util.Comparator;

/* loaded from: classes2.dex */
public abstract class Ordering<T> implements Comparator<T> {
    public static <T> Ordering<T> from(Comparator<T> comparator) {
        return comparator instanceof Ordering ? (Ordering) comparator : new ComparatorOrdering(comparator);
    }

    public static <C extends Comparable> Ordering<C> natural() {
        return NaturalOrdering.INSTANCE;
    }

    @Override // java.util.Comparator
    public abstract int compare(T t2, T t3);

    public <S extends T> Ordering<S> reverse() {
        return new ReverseOrdering(this);
    }
}
