package net.tsz.afinal.core;

import java.util.Iterator;

/* loaded from: classes9.dex */
public interface Deque<E> extends Queue<E> {
    @Override // net.tsz.afinal.core.Queue, java.util.Collection
    boolean add(E e2);

    void addFirst(E e2);

    void addLast(E e2);

    @Override // java.util.Collection
    boolean contains(Object obj);

    Iterator<E> descendingIterator();

    @Override // net.tsz.afinal.core.Queue
    E element();

    E getFirst();

    E getLast();

    @Override // java.util.Collection, java.lang.Iterable
    Iterator<E> iterator();

    @Override // net.tsz.afinal.core.Queue
    boolean offer(E e2);

    boolean offerFirst(E e2);

    boolean offerLast(E e2);

    @Override // net.tsz.afinal.core.Queue
    E peek();

    E peekFirst();

    E peekLast();

    @Override // net.tsz.afinal.core.Queue
    E poll();

    E pollFirst();

    E pollLast();

    E pop();

    void push(E e2);

    @Override // net.tsz.afinal.core.Queue
    E remove();

    @Override // java.util.Collection
    boolean remove(Object obj);

    E removeFirst();

    boolean removeFirstOccurrence(Object obj);

    E removeLast();

    boolean removeLastOccurrence(Object obj);

    @Override // java.util.Collection
    int size();
}
