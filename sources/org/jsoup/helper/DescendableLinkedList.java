package org.jsoup.helper;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/* loaded from: classes9.dex */
public class DescendableLinkedList<E> extends LinkedList<E> {

    public class DescendingIterator<E> implements Iterator<E> {
        private final ListIterator<E> iter;

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.iter.hasPrevious();
        }

        @Override // java.util.Iterator
        public E next() {
            return this.iter.previous();
        }

        @Override // java.util.Iterator
        public void remove() {
            this.iter.remove();
        }

        private DescendingIterator(int i2) {
            this.iter = DescendableLinkedList.this.listIterator(i2);
        }
    }

    @Override // java.util.LinkedList, java.util.Deque
    public Iterator<E> descendingIterator() {
        return new DescendingIterator(size());
    }

    @Override // java.util.LinkedList, java.util.Deque
    public E peekLast() {
        if (size() == 0) {
            return null;
        }
        return getLast();
    }

    @Override // java.util.LinkedList, java.util.Deque
    public E pollLast() {
        if (size() == 0) {
            return null;
        }
        return removeLast();
    }

    @Override // java.util.LinkedList, java.util.Deque
    public void push(E e2) {
        addFirst(e2);
    }
}
