package net.tsz.afinal.core;

import java.util.Collection;

/* loaded from: classes9.dex */
public interface Queue<E> extends Collection<E> {
    @Override // java.util.Collection
    boolean add(E e2);

    E element();

    boolean offer(E e2);

    E peek();

    E poll();

    E remove();
}
