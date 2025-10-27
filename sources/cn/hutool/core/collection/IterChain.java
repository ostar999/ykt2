package cn.hutool.core.collection;

import cn.hutool.core.lang.Chain;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/* loaded from: classes.dex */
public class IterChain<T> implements Iterator<T>, Chain<Iterator<T>, IterChain<T>> {
    protected final List<Iterator<T>> allIterators = new ArrayList();
    protected int currentIter = -1;

    public IterChain() {
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        if (this.currentIter == -1) {
            this.currentIter = 0;
        }
        int size = this.allIterators.size();
        for (int i2 = this.currentIter; i2 < size; i2++) {
            if (this.allIterators.get(i2).hasNext()) {
                this.currentIter = i2;
                return true;
            }
        }
        return false;
    }

    @Override // java.lang.Iterable
    public Iterator<Iterator<T>> iterator() {
        return this.allIterators.iterator();
    }

    @Override // java.util.Iterator
    public T next() {
        if (hasNext()) {
            return this.allIterators.get(this.currentIter).next();
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.Iterator
    public void remove() {
        int i2 = this.currentIter;
        if (-1 == i2) {
            throw new IllegalStateException("next() has not yet been called");
        }
        this.allIterators.get(i2).remove();
    }

    @Override // cn.hutool.core.lang.Chain
    public IterChain<T> addChain(Iterator<T> it) {
        if (this.allIterators.contains(it)) {
            throw new IllegalArgumentException("Duplicate iterator");
        }
        this.allIterators.add(it);
        return this;
    }

    @SafeVarargs
    public IterChain(Iterator<T>... itArr) {
        for (Iterator<T> it : itArr) {
            addChain((Iterator) it);
        }
    }
}
