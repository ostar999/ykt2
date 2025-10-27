package org.eclipse.jetty.util;

import java.util.AbstractList;
import java.util.NoSuchElementException;
import java.util.Queue;

/* loaded from: classes9.dex */
public class ArrayQueue<E> extends AbstractList<E> implements Queue<E> {
    public static final int DEFAULT_CAPACITY = 64;
    public static final int DEFAULT_GROWTH = 32;
    protected Object[] _elements;
    protected final int _growCapacity;
    protected final Object _lock;
    protected int _nextE;
    protected int _nextSlot;
    protected int _size;

    public ArrayQueue() {
        this(64, -1);
    }

    private E at(int i2) {
        return (E) this._elements[i2];
    }

    private E dequeue() {
        E eAt = at(this._nextE);
        Object[] objArr = this._elements;
        int i2 = this._nextE;
        objArr[i2] = null;
        this._size--;
        int i3 = i2 + 1;
        this._nextE = i3;
        if (i3 == objArr.length) {
            this._nextE = 0;
        }
        return eAt;
    }

    private boolean enqueue(E e2) {
        if (this._size == this._elements.length && !grow()) {
            return false;
        }
        this._size++;
        Object[] objArr = this._elements;
        int i2 = this._nextSlot;
        int i3 = i2 + 1;
        this._nextSlot = i3;
        objArr[i2] = e2;
        if (i3 == objArr.length) {
            this._nextSlot = 0;
        }
        return true;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List, java.util.Queue
    public boolean add(E e2) {
        if (offer(e2)) {
            return true;
        }
        throw new IllegalStateException("Full");
    }

    public void addUnsafe(E e2) {
        if (!enqueue(e2)) {
            throw new IllegalStateException("Full");
        }
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        synchronized (this._lock) {
            this._size = 0;
            this._nextE = 0;
            this._nextSlot = 0;
        }
    }

    @Override // java.util.Queue
    public E element() {
        E eAt;
        synchronized (this._lock) {
            if (isEmpty()) {
                throw new NoSuchElementException();
            }
            eAt = at(this._nextE);
        }
        return eAt;
    }

    @Override // java.util.AbstractList, java.util.List
    public E get(int i2) {
        E unsafe;
        synchronized (this._lock) {
            if (i2 >= 0) {
                if (i2 < this._size) {
                    unsafe = getUnsafe(i2);
                }
            }
            throw new IndexOutOfBoundsException("!(0<" + i2 + "<=" + this._size + ")");
        }
        return unsafe;
    }

    public int getCapacity() {
        int length;
        synchronized (this._lock) {
            length = this._elements.length;
        }
        return length;
    }

    public E getUnsafe(int i2) {
        return at((this._nextE + i2) % this._elements.length);
    }

    public boolean grow() {
        synchronized (this._lock) {
            int i2 = this._growCapacity;
            if (i2 <= 0) {
                return false;
            }
            Object[] objArr = this._elements;
            Object[] objArr2 = new Object[objArr.length + i2];
            int length = objArr.length;
            int i3 = this._nextE;
            int i4 = length - i3;
            if (i4 > 0) {
                System.arraycopy(objArr, i3, objArr2, 0, i4);
            }
            if (this._nextE != 0) {
                System.arraycopy(this._elements, 0, objArr2, i4, this._nextSlot);
            }
            this._elements = objArr2;
            this._nextE = 0;
            this._nextSlot = this._size;
            return true;
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean isEmpty() {
        boolean z2;
        synchronized (this._lock) {
            z2 = this._size == 0;
        }
        return z2;
    }

    @Override // java.util.Queue
    public boolean offer(E e2) {
        boolean zEnqueue;
        synchronized (this._lock) {
            zEnqueue = enqueue(e2);
        }
        return zEnqueue;
    }

    @Override // java.util.Queue
    public E peek() {
        synchronized (this._lock) {
            if (isEmpty()) {
                return null;
            }
            return at(this._nextE);
        }
    }

    @Override // java.util.Queue
    public E poll() {
        synchronized (this._lock) {
            if (this._size == 0) {
                return null;
            }
            return dequeue();
        }
    }

    @Override // java.util.Queue
    public E remove() {
        E eDequeue;
        synchronized (this._lock) {
            if (this._size == 0) {
                throw new NoSuchElementException();
            }
            eDequeue = dequeue();
        }
        return eDequeue;
    }

    @Override // java.util.AbstractList, java.util.List
    public E set(int i2, E e2) {
        E eAt;
        synchronized (this._lock) {
            if (i2 >= 0) {
                if (i2 < this._size) {
                    int length = this._nextE + i2;
                    Object[] objArr = this._elements;
                    if (length >= objArr.length) {
                        length -= objArr.length;
                    }
                    eAt = at(length);
                    this._elements[length] = e2;
                }
            }
            throw new IndexOutOfBoundsException("!(0<" + i2 + "<=" + this._size + ")");
        }
        return eAt;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        int i2;
        synchronized (this._lock) {
            i2 = this._size;
        }
        return i2;
    }

    public ArrayQueue(int i2) {
        this(i2, -1);
    }

    public ArrayQueue(int i2, int i3) {
        this(i2, i3, null);
    }

    @Override // java.util.AbstractList, java.util.List
    public void add(int i2, E e2) {
        synchronized (this._lock) {
            if (i2 >= 0) {
                int i3 = this._size;
                if (i2 <= i3) {
                    if (i3 == this._elements.length && !grow()) {
                        throw new IllegalStateException("Full");
                    }
                    int i4 = this._size;
                    if (i2 == i4) {
                        add(e2);
                    } else {
                        int length = this._nextE + i2;
                        Object[] objArr = this._elements;
                        if (length >= objArr.length) {
                            length -= objArr.length;
                        }
                        this._size = i4 + 1;
                        int i5 = this._nextSlot + 1;
                        this._nextSlot = i5;
                        if (i5 == objArr.length) {
                            this._nextSlot = 0;
                        }
                        int i6 = this._nextSlot;
                        if (length < i6) {
                            System.arraycopy(objArr, length, objArr, length + 1, i6 - length);
                            this._elements[length] = e2;
                        } else {
                            if (i6 > 0) {
                                System.arraycopy(objArr, 0, objArr, 1, i6);
                                Object[] objArr2 = this._elements;
                                objArr2[0] = objArr2[objArr2.length - 1];
                            }
                            Object[] objArr3 = this._elements;
                            System.arraycopy(objArr3, length, objArr3, length + 1, (objArr3.length - length) - 1);
                            this._elements[length] = e2;
                        }
                    }
                }
            }
            throw new IndexOutOfBoundsException("!(0<" + i2 + "<=" + this._size + ")");
        }
    }

    public ArrayQueue(int i2, int i3, Object obj) {
        this._lock = obj == null ? this : obj;
        this._growCapacity = i3;
        this._elements = new Object[i2];
    }

    @Override // java.util.AbstractList, java.util.List
    public E remove(int i2) {
        E eAt;
        synchronized (this._lock) {
            if (i2 >= 0) {
                if (i2 < this._size) {
                    int length = (this._nextE + i2) % this._elements.length;
                    eAt = at(length);
                    int i3 = this._nextSlot;
                    if (length < i3) {
                        Object[] objArr = this._elements;
                        System.arraycopy(objArr, length + 1, objArr, length, i3 - length);
                        this._nextSlot--;
                        this._size--;
                    } else {
                        Object[] objArr2 = this._elements;
                        System.arraycopy(objArr2, length + 1, objArr2, length, (objArr2.length - length) - 1);
                        int i4 = this._nextSlot;
                        if (i4 > 0) {
                            Object[] objArr3 = this._elements;
                            objArr3[objArr3.length - 1] = objArr3[0];
                            System.arraycopy(objArr3, 1, objArr3, 0, i4 - 1);
                            this._nextSlot--;
                        } else {
                            this._nextSlot = this._elements.length - 1;
                        }
                        this._size--;
                    }
                }
            }
            throw new IndexOutOfBoundsException("!(0<" + i2 + "<=" + this._size + ")");
        }
        return eAt;
    }
}
