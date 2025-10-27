package cn.hutool.core.collection;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Iterator;

/* loaded from: classes.dex */
public class EnumerationIter<E> implements IterableIter<E>, Serializable {
    private static final long serialVersionUID = 1;

    /* renamed from: e, reason: collision with root package name */
    private final Enumeration<E> f2409e;

    public EnumerationIter(Enumeration<E> enumeration) {
        this.f2409e = enumeration;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.f2409e.hasMoreElements();
    }

    @Override // cn.hutool.core.collection.IterableIter, java.lang.Iterable
    public /* synthetic */ Iterator iterator() {
        return t0.a(this);
    }

    @Override // java.util.Iterator
    public E next() {
        return this.f2409e.nextElement();
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
