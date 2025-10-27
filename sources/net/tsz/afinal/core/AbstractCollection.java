package net.tsz.afinal.core;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import okhttp3.HttpUrl;

/* loaded from: classes9.dex */
public abstract class AbstractCollection<E> implements Collection<E> {
    @Override // java.util.Collection
    public boolean add(E e2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public boolean addAll(Collection<? extends E> collection) {
        Iterator<? extends E> it = collection.iterator();
        boolean z2 = false;
        while (it.hasNext()) {
            if (add(it.next())) {
                z2 = true;
            }
        }
        return z2;
    }

    @Override // java.util.Collection
    public void clear() {
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
    }

    @Override // java.util.Collection
    public boolean contains(Object obj) {
        Iterator<E> it = iterator();
        if (obj != null) {
            while (it.hasNext()) {
                if (obj.equals(it.next())) {
                    return true;
                }
            }
            return false;
        }
        while (it.hasNext()) {
            if (it.next() == null) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Collection
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override // java.util.Collection, java.lang.Iterable
    public abstract Iterator<E> iterator();

    @Override // java.util.Collection
    public boolean remove(Object obj) {
        Iterator<E> it = iterator();
        if (obj != null) {
            while (it.hasNext()) {
                if (obj.equals(it.next())) {
                    it.remove();
                    return true;
                }
            }
            return false;
        }
        while (it.hasNext()) {
            if (it.next() == null) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    @Override // java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        Iterator<E> it = iterator();
        boolean z2 = false;
        while (it.hasNext()) {
            if (collection.contains(it.next())) {
                it.remove();
                z2 = true;
            }
        }
        return z2;
    }

    @Override // java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        Iterator<E> it = iterator();
        boolean z2 = false;
        while (it.hasNext()) {
            if (!collection.contains(it.next())) {
                it.remove();
                z2 = true;
            }
        }
        return z2;
    }

    @Override // java.util.Collection
    public abstract int size();

    @Override // java.util.Collection
    public Object[] toArray() {
        int size = size();
        Iterator<E> it = iterator();
        Object[] objArr = new Object[size];
        for (int i2 = 0; i2 < size; i2++) {
            objArr[i2] = it.next();
        }
        return objArr;
    }

    public String toString() {
        if (isEmpty()) {
            return HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
        }
        StringBuilder sb = new StringBuilder(size() * 16);
        sb.append('[');
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            E next = it.next();
            if (next != this) {
                sb.append(next);
            } else {
                sb.append("(this Collection)");
            }
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(']');
        return sb.toString();
    }

    @Override // java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        int size = size();
        if (size > tArr.length) {
            tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), size));
        }
        Iterator<E> it = iterator();
        int i2 = 0;
        while (it.hasNext()) {
            tArr[i2] = it.next();
            i2++;
        }
        if (i2 < tArr.length) {
            tArr[i2] = null;
        }
        return tArr;
    }
}
