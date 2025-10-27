package cn.hutool.core.collection;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Filter;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* loaded from: classes.dex */
public class FilterIter<E> implements Iterator<E> {
    private final Filter<? super E> filter;
    private final Iterator<? extends E> iterator;
    private E nextObject;
    private boolean nextObjectSet = false;

    public FilterIter(Iterator<? extends E> it, Filter<? super E> filter) {
        this.iterator = (Iterator) Assert.notNull(it);
        this.filter = filter;
    }

    private boolean setNextObject() {
        while (this.iterator.hasNext()) {
            E next = this.iterator.next();
            Filter<? super E> filter = this.filter;
            if (filter == null || filter.accept(next)) {
                this.nextObject = next;
                this.nextObjectSet = true;
                return true;
            }
        }
        return false;
    }

    public Filter<? super E> getFilter() {
        return this.filter;
    }

    public Iterator<? extends E> getIterator() {
        return this.iterator;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.nextObjectSet || setNextObject();
    }

    @Override // java.util.Iterator
    public E next() {
        if (!this.nextObjectSet && !setNextObject()) {
            throw new NoSuchElementException();
        }
        this.nextObjectSet = false;
        return this.nextObject;
    }

    @Override // java.util.Iterator
    public void remove() {
        if (this.nextObjectSet) {
            throw new IllegalStateException("remove() cannot be called");
        }
        this.iterator.remove();
    }
}
