package org.eclipse.jetty.util;

import com.tencent.tbs.one.BuildConfig;
import java.util.AbstractList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes9.dex */
public class BlockingArrayQueue<E> extends AbstractList<E> implements BlockingQueue<E> {
    private volatile int _capacity;
    private Object[] _elements;
    private final int _growCapacity;
    private int _head;
    private final ReentrantLock _headLock;
    private final int _limit;
    private final Condition _notEmpty;
    private long _space0;
    private long _space1;
    private long _space2;
    private long _space3;
    private long _space4;
    private long _space5;
    private long _space6;
    private long _space7;
    private int _tail;
    private final ReentrantLock _tailLock;
    public final int DEFAULT_CAPACITY = 128;
    public final int DEFAULT_GROWTH = 64;
    private final AtomicInteger _size = new AtomicInteger();

    public BlockingArrayQueue() {
        ReentrantLock reentrantLock = new ReentrantLock();
        this._headLock = reentrantLock;
        this._notEmpty = reentrantLock.newCondition();
        this._tailLock = new ReentrantLock();
        Object[] objArr = new Object[128];
        this._elements = objArr;
        this._growCapacity = 64;
        this._capacity = objArr.length;
        this._limit = Integer.MAX_VALUE;
    }

    private boolean grow() {
        int i2;
        if (this._growCapacity <= 0) {
            return false;
        }
        this._tailLock.lock();
        try {
            this._headLock.lock();
            try {
                int i3 = this._head;
                int i4 = this._tail;
                Object[] objArr = new Object[this._capacity + this._growCapacity];
                if (i3 < i4) {
                    i2 = i4 - i3;
                    System.arraycopy(this._elements, i3, objArr, 0, i2);
                } else if (i3 > i4 || this._size.get() > 0) {
                    int i5 = (this._capacity + i4) - i3;
                    int i6 = this._capacity - i3;
                    System.arraycopy(this._elements, i3, objArr, 0, i6);
                    System.arraycopy(this._elements, 0, objArr, i6, i4);
                    i2 = i5;
                } else {
                    i2 = 0;
                }
                this._elements = objArr;
                this._capacity = objArr.length;
                this._head = 0;
                this._tail = i2;
                this._tailLock.unlock();
                return true;
            } finally {
                this._headLock.unlock();
            }
        } catch (Throwable th) {
            this._tailLock.unlock();
            throw th;
        }
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List, java.util.concurrent.BlockingQueue, java.util.Queue
    public boolean add(E e2) {
        return offer(e2);
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        this._tailLock.lock();
        try {
            this._headLock.lock();
            try {
                this._head = 0;
                this._tail = 0;
                this._size.set(0);
            } finally {
                this._headLock.unlock();
            }
        } finally {
            this._tailLock.unlock();
        }
    }

    @Override // java.util.concurrent.BlockingQueue
    public int drainTo(Collection<? super E> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Queue
    public E element() {
        E ePeek = peek();
        if (ePeek != null) {
            return ePeek;
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.AbstractList, java.util.List
    public E get(int i2) {
        this._tailLock.lock();
        try {
            this._headLock.lock();
            if (i2 >= 0) {
                try {
                    if (i2 < this._size.get()) {
                        int i3 = this._head + i2;
                        if (i3 >= this._capacity) {
                            i3 -= this._capacity;
                        }
                        return (E) this._elements[i3];
                    }
                } finally {
                    this._headLock.unlock();
                }
            }
            throw new IndexOutOfBoundsException("!(0<" + i2 + "<=" + this._size + ")");
        } finally {
            this._tailLock.unlock();
        }
    }

    public int getCapacity() {
        return this._capacity;
    }

    public int getLimit() {
        return this._limit;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean isEmpty() {
        return this._size.get() == 0;
    }

    @Override // java.util.concurrent.BlockingQueue, java.util.Queue
    public boolean offer(E e2) {
        e2.getClass();
        this._tailLock.lock();
        try {
            if (this._size.get() < this._limit) {
                if (this._size.get() == this._capacity) {
                    this._headLock.lock();
                    try {
                        if (grow()) {
                            this._headLock.unlock();
                        } else {
                            this._headLock.unlock();
                        }
                    } finally {
                    }
                }
                Object[] objArr = this._elements;
                int i2 = this._tail;
                objArr[i2] = e2;
                this._tail = (i2 + 1) % this._capacity;
                if (this._size.getAndIncrement() == 0) {
                    this._headLock.lock();
                    try {
                        this._notEmpty.signal();
                    } finally {
                    }
                }
                return true;
            }
            return false;
        } finally {
            this._tailLock.unlock();
        }
    }

    @Override // java.util.Queue
    public E peek() {
        E e2 = null;
        if (this._size.get() == 0) {
            return null;
        }
        this._headLock.lock();
        try {
            if (this._size.get() > 0) {
                e2 = (E) this._elements[this._head];
            }
            return e2;
        } finally {
            this._headLock.unlock();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [java.lang.Object[]] */
    /* JADX WARN: Type inference failed for: r3v0 */
    @Override // java.util.Queue
    public E poll() {
        E e2 = null;
        if (this._size.get() == 0) {
            return null;
        }
        this._headLock.lock();
        try {
            if (this._size.get() > 0) {
                int i2 = this._head;
                ?? r2 = this._elements;
                ?? r3 = r2[i2];
                r2[i2] = 0;
                this._head = (i2 + 1) % this._capacity;
                if (this._size.decrementAndGet() > 0) {
                    this._notEmpty.signal();
                }
                e2 = r3;
            }
            return e2;
        } finally {
            this._headLock.unlock();
        }
    }

    @Override // java.util.concurrent.BlockingQueue
    public void put(E e2) throws InterruptedException {
        if (!add(e2)) {
            throw new IllegalStateException(BuildConfig.FLAVOR);
        }
    }

    @Override // java.util.concurrent.BlockingQueue
    public int remainingCapacity() {
        this._tailLock.lock();
        try {
            this._headLock.lock();
            try {
                return getCapacity() - size();
            } finally {
                this._headLock.unlock();
            }
        } finally {
            this._tailLock.unlock();
        }
    }

    @Override // java.util.Queue
    public E remove() {
        E ePoll = poll();
        if (ePoll != null) {
            return ePoll;
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.AbstractList, java.util.List
    public E set(int i2, E e2) {
        e2.getClass();
        this._tailLock.lock();
        try {
            this._headLock.lock();
            if (i2 >= 0) {
                try {
                    if (i2 < this._size.get()) {
                        int i3 = this._head + i2;
                        if (i3 >= this._capacity) {
                            i3 -= this._capacity;
                        }
                        Object[] objArr = this._elements;
                        E e3 = (E) objArr[i3];
                        objArr[i3] = e2;
                        return e3;
                    }
                } finally {
                    this._headLock.unlock();
                }
            }
            throw new IndexOutOfBoundsException("!(0<" + i2 + "<=" + this._size + ")");
        } finally {
            this._tailLock.unlock();
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this._size.get();
    }

    public long sumOfSpace() {
        long j2 = this._space0;
        this._space0 = j2 + 1;
        long j3 = this._space1;
        this._space1 = j3 + 1;
        long j4 = j2 + j3;
        long j5 = this._space2;
        this._space2 = j5 + 1;
        long j6 = j4 + j5;
        long j7 = this._space3;
        this._space3 = j7 + 1;
        long j8 = j6 + j7;
        long j9 = this._space4;
        this._space4 = j9 + 1;
        long j10 = j8 + j9;
        long j11 = this._space5;
        this._space5 = j11 + 1;
        long j12 = j10 + j11;
        long j13 = this._space6;
        this._space6 = j13 + 1;
        long j14 = j12 + j13;
        long j15 = this._space7;
        this._space7 = 1 + j15;
        return j14 + j15;
    }

    @Override // java.util.concurrent.BlockingQueue
    public E take() throws InterruptedException {
        this._headLock.lockInterruptibly();
        while (this._size.get() == 0) {
            try {
                try {
                    this._notEmpty.await();
                } catch (InterruptedException e2) {
                    this._notEmpty.signal();
                    throw e2;
                }
            } finally {
                this._headLock.unlock();
            }
        }
        int i2 = this._head;
        Object[] objArr = this._elements;
        E e3 = (E) objArr[i2];
        objArr[i2] = null;
        this._head = (i2 + 1) % this._capacity;
        if (this._size.decrementAndGet() > 0) {
            this._notEmpty.signal();
        }
        return e3;
    }

    @Override // java.util.AbstractList, java.util.List
    public void add(int i2, E e2) {
        e2.getClass();
        this._tailLock.lock();
        try {
            this._headLock.lock();
            if (i2 >= 0) {
                try {
                    if (i2 <= this._size.get()) {
                        if (i2 == this._size.get()) {
                            add(e2);
                        } else {
                            if (this._tail == this._head && !grow()) {
                                throw new IllegalStateException(BuildConfig.FLAVOR);
                            }
                            int i3 = this._head + i2;
                            if (i3 >= this._capacity) {
                                i3 -= this._capacity;
                            }
                            this._size.incrementAndGet();
                            int i4 = (this._tail + 1) % this._capacity;
                            this._tail = i4;
                            if (i3 < i4) {
                                Object[] objArr = this._elements;
                                System.arraycopy(objArr, i3, objArr, i3 + 1, i4 - i3);
                                this._elements[i3] = e2;
                            } else {
                                if (i4 > 0) {
                                    Object[] objArr2 = this._elements;
                                    System.arraycopy(objArr2, 0, objArr2, 1, i4);
                                    Object[] objArr3 = this._elements;
                                    objArr3[0] = objArr3[this._capacity - 1];
                                }
                                Object[] objArr4 = this._elements;
                                System.arraycopy(objArr4, i3, objArr4, i3 + 1, (this._capacity - i3) - 1);
                                this._elements[i3] = e2;
                            }
                        }
                        return;
                    }
                } finally {
                    this._headLock.unlock();
                }
            }
            throw new IndexOutOfBoundsException("!(0<" + i2 + "<=" + this._size + ")");
        } finally {
            this._tailLock.unlock();
        }
    }

    @Override // java.util.concurrent.BlockingQueue
    public int drainTo(Collection<? super E> collection, int i2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractList, java.util.List
    public E remove(int i2) {
        this._tailLock.lock();
        try {
            this._headLock.lock();
            if (i2 >= 0) {
                try {
                    if (i2 < this._size.get()) {
                        int i3 = this._head + i2;
                        if (i3 >= this._capacity) {
                            i3 -= this._capacity;
                        }
                        Object[] objArr = this._elements;
                        E e2 = (E) objArr[i3];
                        int i4 = this._tail;
                        if (i3 < i4) {
                            System.arraycopy(objArr, i3 + 1, objArr, i3, i4 - i3);
                            this._tail--;
                            this._size.decrementAndGet();
                        } else {
                            System.arraycopy(objArr, i3 + 1, objArr, i3, (this._capacity - i3) - 1);
                            if (this._tail > 0) {
                                Object[] objArr2 = this._elements;
                                int i5 = this._capacity;
                                Object[] objArr3 = this._elements;
                                objArr2[i5] = objArr3[0];
                                System.arraycopy(objArr3, 1, objArr3, 0, this._tail - 1);
                                this._tail--;
                            } else {
                                this._tail = this._capacity - 1;
                            }
                            this._size.decrementAndGet();
                        }
                        return e2;
                    }
                } finally {
                    this._headLock.unlock();
                }
            }
            throw new IndexOutOfBoundsException("!(0<" + i2 + "<=" + this._size + ")");
        } finally {
            this._tailLock.unlock();
        }
    }

    @Override // java.util.concurrent.BlockingQueue
    public E poll(long j2, TimeUnit timeUnit) throws InterruptedException {
        long nanos = timeUnit.toNanos(j2);
        this._headLock.lockInterruptibly();
        while (this._size.get() == 0) {
            try {
                try {
                    if (nanos <= 0) {
                        return null;
                    }
                    nanos = this._notEmpty.awaitNanos(nanos);
                } catch (InterruptedException e2) {
                    this._notEmpty.signal();
                    throw e2;
                }
            } finally {
                this._headLock.unlock();
            }
        }
        Object[] objArr = this._elements;
        int i2 = this._head;
        E e3 = (E) objArr[i2];
        objArr[i2] = null;
        this._head = (i2 + 1) % this._capacity;
        if (this._size.decrementAndGet() > 0) {
            this._notEmpty.signal();
        }
        return e3;
    }

    public BlockingArrayQueue(int i2) {
        ReentrantLock reentrantLock = new ReentrantLock();
        this._headLock = reentrantLock;
        this._notEmpty = reentrantLock.newCondition();
        this._tailLock = new ReentrantLock();
        Object[] objArr = new Object[i2];
        this._elements = objArr;
        this._capacity = objArr.length;
        this._growCapacity = -1;
        this._limit = i2;
    }

    @Override // java.util.concurrent.BlockingQueue
    public boolean offer(E e2, long j2, TimeUnit timeUnit) throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    public BlockingArrayQueue(int i2, int i3) {
        ReentrantLock reentrantLock = new ReentrantLock();
        this._headLock = reentrantLock;
        this._notEmpty = reentrantLock.newCondition();
        this._tailLock = new ReentrantLock();
        Object[] objArr = new Object[i2];
        this._elements = objArr;
        this._capacity = objArr.length;
        this._growCapacity = i3;
        this._limit = Integer.MAX_VALUE;
    }

    public BlockingArrayQueue(int i2, int i3, int i4) {
        ReentrantLock reentrantLock = new ReentrantLock();
        this._headLock = reentrantLock;
        this._notEmpty = reentrantLock.newCondition();
        this._tailLock = new ReentrantLock();
        if (i2 <= i4) {
            Object[] objArr = new Object[i2];
            this._elements = objArr;
            this._capacity = objArr.length;
            this._growCapacity = i3;
            this._limit = i4;
            return;
        }
        throw new IllegalArgumentException();
    }
}
