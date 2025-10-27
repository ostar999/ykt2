package cn.hutool.core.lang;

import cn.hutool.core.thread.lock.NoLock;
import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes.dex */
public class Range<T> implements Iterable<T>, Iterator<T>, Serializable {
    private static final long serialVersionUID = 1;
    private final T end;
    private final boolean includeEnd;
    private final boolean includeStart;
    private int index;
    private Lock lock;
    private T next;
    private final T start;
    private final Stepper<T> stepper;

    @FunctionalInterface
    public interface Stepper<T> {
        T step(T t2, T t3, int i2);
    }

    public Range(T t2, Stepper<T> stepper) {
        this(t2, null, stepper);
    }

    private T nextUncheck() {
        T t2;
        int i2 = this.index;
        if (i2 == 0) {
            t2 = this.start;
            if (!this.includeStart) {
                this.index = i2 + 1;
                return nextUncheck();
            }
        } else {
            t2 = this.next;
            this.next = safeStep(t2);
        }
        this.index++;
        return t2;
    }

    private T safeStep(T t2) {
        try {
            return this.stepper.step(t2, this.end, this.index);
        } catch (Exception unused) {
            return null;
        }
    }

    public Range<T> disableLock() {
        this.lock = new NoLock();
        return this;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0029, code lost:
    
        if (r0.equals(r4.end) != false) goto L12;
     */
    @Override // java.util.Iterator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean hasNext() {
        /*
            r4 = this;
            java.util.concurrent.locks.Lock r0 = r4.lock
            r0.lock()
            int r0 = r4.index     // Catch: java.lang.Throwable -> L32
            r1 = 1
            if (r0 != 0) goto L14
            boolean r0 = r4.includeStart     // Catch: java.lang.Throwable -> L32
            if (r0 == 0) goto L14
            java.util.concurrent.locks.Lock r0 = r4.lock
            r0.unlock()
            return r1
        L14:
            T r0 = r4.next     // Catch: java.lang.Throwable -> L32
            r2 = 0
            if (r0 != 0) goto L1f
        L19:
            java.util.concurrent.locks.Lock r0 = r4.lock
            r0.unlock()
            return r2
        L1f:
            boolean r3 = r4.includeEnd     // Catch: java.lang.Throwable -> L32
            if (r3 != 0) goto L2c
            T r3 = r4.end     // Catch: java.lang.Throwable -> L32
            boolean r0 = r0.equals(r3)     // Catch: java.lang.Throwable -> L32
            if (r0 == 0) goto L2c
            goto L19
        L2c:
            java.util.concurrent.locks.Lock r0 = r4.lock
            r0.unlock()
            return r1
        L32:
            r0 = move-exception
            java.util.concurrent.locks.Lock r1 = r4.lock
            r1.unlock()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.hutool.core.lang.Range.hasNext():boolean");
    }

    @Override // java.lang.Iterable
    public Iterator<T> iterator() {
        return this;
    }

    @Override // java.util.Iterator
    public T next() {
        this.lock.lock();
        try {
            if (hasNext()) {
                return nextUncheck();
            }
            throw new NoSuchElementException("Has no next range!");
        } finally {
            this.lock.unlock();
        }
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Can not remove ranged element!");
    }

    public Range<T> reset() {
        this.lock.lock();
        try {
            this.index = 0;
            this.next = safeStep(this.start);
            return this;
        } finally {
            this.lock.unlock();
        }
    }

    public Range(T t2, T t3, Stepper<T> stepper) {
        this(t2, t3, stepper, true, true);
    }

    public Range(T t2, T t3, Stepper<T> stepper, boolean z2, boolean z3) throws IllegalArgumentException {
        this.lock = new ReentrantLock();
        this.index = 0;
        Assert.notNull(t2, "First element must be not null!", new Object[0]);
        this.start = t2;
        this.end = t3;
        this.stepper = stepper;
        this.next = safeStep(t2);
        this.includeStart = z2;
        this.includeEnd = z3;
    }
}
