package cn.hutool.core.collection;

import java.io.Serializable;
import java.util.Iterator;

/* loaded from: classes.dex */
public class CopiedIter<E> implements IterableIter<E>, Serializable {
    private static final long serialVersionUID = 1;
    private final Iterator<E> listIterator;

    public CopiedIter(Iterator<E> it) {
        this.listIterator = ListUtil.toList(it).iterator();
    }

    public static <E> CopiedIter<E> copyOf(Iterator<E> it) {
        return new CopiedIter<>(it);
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.listIterator.hasNext();
    }

    @Override // cn.hutool.core.collection.IterableIter, java.lang.Iterable
    public /* synthetic */ Iterator iterator() {
        return t0.a(this);
    }

    @Override // java.util.Iterator
    public E next() {
        return this.listIterator.next();
    }

    @Override // java.util.Iterator
    public void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This is a read-only iterator.");
    }
}
