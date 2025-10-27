package net.tsz.afinal.core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* loaded from: classes9.dex */
public class ArrayDeque<E> extends AbstractCollection<E> implements Deque<E>, Cloneable, Serializable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int MIN_INITIAL_CAPACITY = 8;
    private static final long serialVersionUID = 2340985798034038923L;
    private transient E[] elements;
    private transient int head;
    private transient int tail;

    public class DeqIterator implements Iterator<E> {
        private int cursor;
        private int fence;
        private int lastRet;

        private DeqIterator() {
            this.cursor = ArrayDeque.this.head;
            this.fence = ArrayDeque.this.tail;
            this.lastRet = -1;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.cursor != this.fence;
        }

        @Override // java.util.Iterator
        public E next() {
            if (this.cursor == this.fence) {
                throw new NoSuchElementException();
            }
            E e2 = (E) ArrayDeque.this.elements[this.cursor];
            if (ArrayDeque.this.tail != this.fence || e2 == null) {
                throw new ConcurrentModificationException();
            }
            int i2 = this.cursor;
            this.lastRet = i2;
            this.cursor = (i2 + 1) & (ArrayDeque.this.elements.length - 1);
            return e2;
        }

        @Override // java.util.Iterator
        public void remove() {
            int i2 = this.lastRet;
            if (i2 < 0) {
                throw new IllegalStateException();
            }
            if (ArrayDeque.this.delete(i2)) {
                this.cursor = (this.cursor - 1) & (ArrayDeque.this.elements.length - 1);
                this.fence = ArrayDeque.this.tail;
            }
            this.lastRet = -1;
        }
    }

    public class DescendingIterator implements Iterator<E> {
        private int cursor;
        private int fence;
        private int lastRet;

        private DescendingIterator() {
            this.cursor = ArrayDeque.this.tail;
            this.fence = ArrayDeque.this.head;
            this.lastRet = -1;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.cursor != this.fence;
        }

        @Override // java.util.Iterator
        public E next() {
            int i2 = this.cursor;
            if (i2 == this.fence) {
                throw new NoSuchElementException();
            }
            this.cursor = (i2 - 1) & (ArrayDeque.this.elements.length - 1);
            E e2 = (E) ArrayDeque.this.elements[this.cursor];
            if (ArrayDeque.this.head != this.fence || e2 == null) {
                throw new ConcurrentModificationException();
            }
            this.lastRet = this.cursor;
            return e2;
        }

        @Override // java.util.Iterator
        public void remove() {
            int i2 = this.lastRet;
            if (i2 < 0) {
                throw new IllegalStateException();
            }
            if (!ArrayDeque.this.delete(i2)) {
                this.cursor = (this.cursor + 1) & (ArrayDeque.this.elements.length - 1);
                this.fence = ArrayDeque.this.head;
            }
            this.lastRet = -1;
        }
    }

    public ArrayDeque() {
        this.elements = (E[]) new Object[16];
    }

    private void allocateElements(int i2) {
        int i3 = 8;
        if (i2 >= 8) {
            int i4 = i2 | (i2 >>> 1);
            int i5 = i4 | (i4 >>> 2);
            int i6 = i5 | (i5 >>> 4);
            int i7 = i6 | (i6 >>> 8);
            i3 = (i7 | (i7 >>> 16)) + 1;
            if (i3 < 0) {
                i3 >>>= 1;
            }
        }
        this.elements = (E[]) new Object[i3];
    }

    private void checkInvariants() {
    }

    private <T> T[] copyElements(T[] tArr) {
        int i2 = this.head;
        int i3 = this.tail;
        if (i2 < i3) {
            System.arraycopy(this.elements, i2, tArr, 0, size());
        } else if (i2 > i3) {
            E[] eArr = this.elements;
            int length = eArr.length - i2;
            System.arraycopy(eArr, i2, tArr, 0, length);
            System.arraycopy(this.elements, 0, tArr, length, this.tail);
        }
        return tArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean delete(int i2) {
        checkInvariants();
        E[] eArr = this.elements;
        int length = eArr.length - 1;
        int i3 = this.head;
        int i4 = this.tail;
        int i5 = (i2 - i3) & length;
        int i6 = (i4 - i2) & length;
        if (i5 >= ((i4 - i3) & length)) {
            throw new ConcurrentModificationException();
        }
        if (i5 < i6) {
            if (i3 <= i2) {
                System.arraycopy(eArr, i3, eArr, i3 + 1, i5);
            } else {
                System.arraycopy(eArr, 0, eArr, 1, i2);
                eArr[0] = eArr[length];
                System.arraycopy(eArr, i3, eArr, i3 + 1, length - i3);
            }
            eArr[i3] = null;
            this.head = (i3 + 1) & length;
            return false;
        }
        if (i2 < i4) {
            System.arraycopy(eArr, i2 + 1, eArr, i2, i6);
            this.tail = i4 - 1;
        } else {
            System.arraycopy(eArr, i2 + 1, eArr, i2, length - i2);
            eArr[length] = eArr[0];
            System.arraycopy(eArr, 1, eArr, 0, i4);
            this.tail = (i4 - 1) & length;
        }
        return true;
    }

    private void doubleCapacity() {
        int i2 = this.head;
        E[] eArr = this.elements;
        int length = eArr.length;
        int i3 = length - i2;
        int i4 = length << 1;
        if (i4 < 0) {
            throw new IllegalStateException("Sorry, deque too big");
        }
        E[] eArr2 = (E[]) new Object[i4];
        System.arraycopy(eArr, i2, eArr2, 0, i3);
        System.arraycopy(this.elements, 0, eArr2, i3, i2);
        this.elements = eArr2;
        this.head = 0;
        this.tail = length;
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        int i2 = objectInputStream.readInt();
        allocateElements(i2);
        this.head = 0;
        this.tail = i2;
        for (int i3 = 0; i3 < i2; i3++) {
            ((E[]) this.elements)[i3] = objectInputStream.readObject();
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(size());
        int length = this.elements.length - 1;
        for (int i2 = this.head; i2 != this.tail; i2 = (i2 + 1) & length) {
            objectOutputStream.writeObject(this.elements[i2]);
        }
    }

    @Override // net.tsz.afinal.core.AbstractCollection, java.util.Collection
    public boolean add(E e2) {
        addLast(e2);
        return true;
    }

    @Override // net.tsz.afinal.core.Deque
    public void addFirst(E e2) {
        e2.getClass();
        E[] eArr = this.elements;
        int length = (this.head - 1) & (eArr.length - 1);
        this.head = length;
        eArr[length] = e2;
        if (length == this.tail) {
            doubleCapacity();
        }
    }

    @Override // net.tsz.afinal.core.Deque
    public void addLast(E e2) {
        e2.getClass();
        E[] eArr = this.elements;
        int i2 = this.tail;
        eArr[i2] = e2;
        int length = (eArr.length - 1) & (i2 + 1);
        this.tail = length;
        if (length == this.head) {
            doubleCapacity();
        }
    }

    @Override // net.tsz.afinal.core.AbstractCollection, java.util.Collection
    public void clear() {
        int i2 = this.head;
        int i3 = this.tail;
        if (i2 != i3) {
            this.tail = 0;
            this.head = 0;
            int length = this.elements.length - 1;
            do {
                this.elements[i2] = null;
                i2 = (i2 + 1) & length;
            } while (i2 != i3);
        }
    }

    @Override // net.tsz.afinal.core.AbstractCollection, java.util.Collection
    public boolean contains(Object obj) {
        if (obj == null) {
            return false;
        }
        int length = this.elements.length - 1;
        int i2 = this.head;
        while (true) {
            E e2 = this.elements[i2];
            if (e2 == null) {
                return false;
            }
            if (obj.equals(e2)) {
                return true;
            }
            i2 = (i2 + 1) & length;
        }
    }

    @Override // net.tsz.afinal.core.Deque
    public Iterator<E> descendingIterator() {
        return new DescendingIterator();
    }

    @Override // net.tsz.afinal.core.Deque, net.tsz.afinal.core.Queue
    public E element() {
        return getFirst();
    }

    @Override // net.tsz.afinal.core.Deque
    public E getFirst() {
        E e2 = this.elements[this.head];
        if (e2 != null) {
            return e2;
        }
        throw new NoSuchElementException();
    }

    @Override // net.tsz.afinal.core.Deque
    public E getLast() {
        E e2 = this.elements[(this.tail - 1) & (r0.length - 1)];
        if (e2 != null) {
            return e2;
        }
        throw new NoSuchElementException();
    }

    @Override // net.tsz.afinal.core.AbstractCollection, java.util.Collection
    public boolean isEmpty() {
        return this.head == this.tail;
    }

    @Override // net.tsz.afinal.core.AbstractCollection, java.util.Collection, java.lang.Iterable
    public Iterator<E> iterator() {
        return new DeqIterator();
    }

    @Override // net.tsz.afinal.core.Deque, net.tsz.afinal.core.Queue
    public boolean offer(E e2) {
        return offerLast(e2);
    }

    @Override // net.tsz.afinal.core.Deque
    public boolean offerFirst(E e2) {
        addFirst(e2);
        return true;
    }

    @Override // net.tsz.afinal.core.Deque
    public boolean offerLast(E e2) {
        addLast(e2);
        return true;
    }

    @Override // net.tsz.afinal.core.Deque, net.tsz.afinal.core.Queue
    public E peek() {
        return peekFirst();
    }

    @Override // net.tsz.afinal.core.Deque
    public E peekFirst() {
        return this.elements[this.head];
    }

    @Override // net.tsz.afinal.core.Deque
    public E peekLast() {
        return this.elements[(this.tail - 1) & (r0.length - 1)];
    }

    @Override // net.tsz.afinal.core.Deque, net.tsz.afinal.core.Queue
    public E poll() {
        return pollFirst();
    }

    @Override // net.tsz.afinal.core.Deque
    public E pollFirst() {
        int i2 = this.head;
        E[] eArr = this.elements;
        E e2 = eArr[i2];
        if (e2 == null) {
            return null;
        }
        eArr[i2] = null;
        this.head = (i2 + 1) & (eArr.length - 1);
        return e2;
    }

    @Override // net.tsz.afinal.core.Deque
    public E pollLast() {
        int i2 = this.tail - 1;
        E[] eArr = this.elements;
        int length = i2 & (eArr.length - 1);
        E e2 = eArr[length];
        if (e2 == null) {
            return null;
        }
        eArr[length] = null;
        this.tail = length;
        return e2;
    }

    @Override // net.tsz.afinal.core.Deque
    public E pop() {
        return removeFirst();
    }

    @Override // net.tsz.afinal.core.Deque
    public void push(E e2) {
        addFirst(e2);
    }

    @Override // net.tsz.afinal.core.Deque, net.tsz.afinal.core.Queue
    public E remove() {
        return removeFirst();
    }

    @Override // net.tsz.afinal.core.Deque
    public E removeFirst() {
        E ePollFirst = pollFirst();
        if (ePollFirst != null) {
            return ePollFirst;
        }
        throw new NoSuchElementException();
    }

    @Override // net.tsz.afinal.core.Deque
    public boolean removeFirstOccurrence(Object obj) {
        if (obj == null) {
            return false;
        }
        int length = this.elements.length - 1;
        int i2 = this.head;
        while (true) {
            E e2 = this.elements[i2];
            if (e2 == null) {
                return false;
            }
            if (obj.equals(e2)) {
                delete(i2);
                return true;
            }
            i2 = (i2 + 1) & length;
        }
    }

    @Override // net.tsz.afinal.core.Deque
    public E removeLast() {
        E ePollLast = pollLast();
        if (ePollLast != null) {
            return ePollLast;
        }
        throw new NoSuchElementException();
    }

    @Override // net.tsz.afinal.core.Deque
    public boolean removeLastOccurrence(Object obj) {
        if (obj == null) {
            return false;
        }
        int length = this.elements.length - 1;
        int i2 = this.tail - 1;
        while (true) {
            int i3 = i2 & length;
            E e2 = this.elements[i3];
            if (e2 == null) {
                return false;
            }
            if (obj.equals(e2)) {
                delete(i3);
                return true;
            }
            i2 = i3 - 1;
        }
    }

    @Override // net.tsz.afinal.core.AbstractCollection, java.util.Collection
    public int size() {
        return (this.tail - this.head) & (this.elements.length - 1);
    }

    @Override // net.tsz.afinal.core.AbstractCollection, java.util.Collection
    public Object[] toArray() {
        return copyElements(new Object[size()]);
    }

    public ArrayDeque<E> clone() {
        try {
            ArrayDeque<E> arrayDeque = (ArrayDeque) super.clone();
            E[] eArr = this.elements;
            arrayDeque.elements = (E[]) Arrays.copyOf(eArr, eArr.length);
            return arrayDeque;
        } catch (CloneNotSupportedException unused) {
            throw new AssertionError();
        }
    }

    @Override // net.tsz.afinal.core.AbstractCollection, java.util.Collection
    public boolean remove(Object obj) {
        return removeFirstOccurrence(obj);
    }

    @Override // net.tsz.afinal.core.AbstractCollection, java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        int size = size();
        if (tArr.length < size) {
            tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), size));
        }
        copyElements(tArr);
        if (tArr.length > size) {
            tArr[size] = null;
        }
        return tArr;
    }

    public ArrayDeque(int i2) {
        allocateElements(i2);
    }

    public ArrayDeque(Collection<? extends E> collection) {
        allocateElements(collection.size());
        addAll(collection);
    }
}
