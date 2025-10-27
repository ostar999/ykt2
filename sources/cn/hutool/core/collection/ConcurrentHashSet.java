package cn.hutool.core.collection;

import cn.hutool.core.map.SafeConcurrentHashMap;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;

/* loaded from: classes.dex */
public class ConcurrentHashSet<E> extends AbstractSet<E> implements Serializable {
    private static final Boolean PRESENT = Boolean.TRUE;
    private static final long serialVersionUID = 7997886765361607470L;
    private final SafeConcurrentHashMap<E, Boolean> map;

    public ConcurrentHashSet() {
        this.map = new SafeConcurrentHashMap<>();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean add(E e2) {
        return this.map.put(e2, PRESENT) == null;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public void clear() {
        this.map.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(Object obj) {
        return this.map.containsKey(obj);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public Iterator<E> iterator() {
        return this.map.keySet().iterator();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean remove(Object obj) {
        return PRESENT.equals(this.map.remove(obj));
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.map.size();
    }

    public ConcurrentHashSet(int i2) {
        this.map = new SafeConcurrentHashMap<>(i2);
    }

    public ConcurrentHashSet(int i2, float f2) {
        this.map = new SafeConcurrentHashMap<>(i2, f2);
    }

    public ConcurrentHashSet(int i2, float f2, int i3) {
        this.map = new SafeConcurrentHashMap<>(i2, f2, i3);
    }

    public ConcurrentHashSet(Iterable<E> iterable) {
        if (iterable instanceof Collection) {
            this.map = new SafeConcurrentHashMap<>((int) (r4.size() / 0.75f));
            addAll((Collection) iterable);
        } else {
            this.map = new SafeConcurrentHashMap<>();
            Iterator<E> it = iterable.iterator();
            while (it.hasNext()) {
                add(it.next());
            }
        }
    }
}
