package cn.hutool.core.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* loaded from: classes.dex */
public abstract class ComputeIter<T> implements Iterator<T> {
    private boolean finished;
    private T next;

    public abstract T computeNext();

    public void finish() {
        this.finished = true;
        this.next = null;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        if (this.next != null) {
            return true;
        }
        if (this.finished) {
            return false;
        }
        T tComputeNext = computeNext();
        if (tComputeNext == null) {
            this.finished = true;
            return false;
        }
        this.next = tComputeNext;
        return true;
    }

    @Override // java.util.Iterator
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more lines");
        }
        T t2 = this.next;
        this.next = null;
        return t2;
    }
}
