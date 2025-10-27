package com.nostra13.universalimageloader.core.assist.deque;

import java.util.Iterator;
import java.util.Queue;

/* loaded from: classes4.dex */
public interface Deque<E> extends Queue<E> {
    boolean add(E e2);

    void addFirst(E e2);

    void addLast(E e2);

    boolean contains(Object obj);

    Iterator<E> descendingIterator();

    E element();

    E getFirst();

    E getLast();

    Iterator<E> iterator();

    boolean offer(E e2);

    boolean offerFirst(E e2);

    boolean offerLast(E e2);

    E peek();

    E peekFirst();

    E peekLast();

    E poll();

    E pollFirst();

    E pollLast();

    E pop();

    void push(E e2);

    E remove();

    boolean remove(Object obj);

    E removeFirst();

    boolean removeFirstOccurrence(Object obj);

    E removeLast();

    boolean removeLastOccurrence(Object obj);

    int size();
}
