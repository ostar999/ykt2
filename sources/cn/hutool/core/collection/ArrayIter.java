package cn.hutool.core.collection;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* loaded from: classes.dex */
public class ArrayIter<E> implements IterableIter<E>, ResettableIter<E>, Serializable {
    private static final long serialVersionUID = 1;
    private final Object array;
    private int endIndex;
    private int index;
    private int startIndex;

    public ArrayIter(E[] eArr) {
        this((Object) eArr);
    }

    public Object getArray() {
        return this.array;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.index < this.endIndex;
    }

    @Override // cn.hutool.core.collection.IterableIter, java.lang.Iterable
    public /* synthetic */ Iterator iterator() {
        return t0.a(this);
    }

    @Override // java.util.Iterator
    public E next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Object obj = this.array;
        int i2 = this.index;
        this.index = i2 + 1;
        return (E) Array.get(obj, i2);
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("remove() method is not supported");
    }

    @Override // cn.hutool.core.collection.ResettableIter
    public void reset() {
        this.index = this.startIndex;
    }

    public ArrayIter(Object obj) {
        this(obj, 0);
    }

    public ArrayIter(Object obj, int i2) {
        this(obj, i2, -1);
    }

    public ArrayIter(Object obj, int i2, int i3) {
        int length = Array.getLength(obj);
        this.endIndex = length;
        if (i3 > 0 && i3 < length) {
            this.endIndex = i3;
        }
        if (i2 >= 0 && i2 < this.endIndex) {
            this.startIndex = i2;
        }
        this.array = obj;
        this.index = this.startIndex;
    }
}
