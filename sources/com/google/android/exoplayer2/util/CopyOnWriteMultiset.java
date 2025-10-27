package com.google.android.exoplayer2.util;

import androidx.annotation.GuardedBy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes3.dex */
public final class CopyOnWriteMultiset<E> implements Iterable<E> {
    private final Object lock = new Object();

    @GuardedBy("lock")
    private final Map<E, Integer> elementCounts = new HashMap();

    @GuardedBy("lock")
    private Set<E> elementSet = Collections.emptySet();

    @GuardedBy("lock")
    private List<E> elements = Collections.emptyList();

    public void add(E e2) {
        synchronized (this.lock) {
            ArrayList arrayList = new ArrayList(this.elements);
            arrayList.add(e2);
            this.elements = Collections.unmodifiableList(arrayList);
            Integer num = this.elementCounts.get(e2);
            if (num == null) {
                HashSet hashSet = new HashSet(this.elementSet);
                hashSet.add(e2);
                this.elementSet = Collections.unmodifiableSet(hashSet);
            }
            this.elementCounts.put(e2, Integer.valueOf(num != null ? 1 + num.intValue() : 1));
        }
    }

    public int count(E e2) {
        int iIntValue;
        synchronized (this.lock) {
            iIntValue = this.elementCounts.containsKey(e2) ? this.elementCounts.get(e2).intValue() : 0;
        }
        return iIntValue;
    }

    public Set<E> elementSet() {
        Set<E> set;
        synchronized (this.lock) {
            set = this.elementSet;
        }
        return set;
    }

    @Override // java.lang.Iterable
    public Iterator<E> iterator() {
        Iterator<E> it;
        synchronized (this.lock) {
            it = this.elements.iterator();
        }
        return it;
    }

    public void remove(E e2) {
        synchronized (this.lock) {
            Integer num = this.elementCounts.get(e2);
            if (num == null) {
                return;
            }
            ArrayList arrayList = new ArrayList(this.elements);
            arrayList.remove(e2);
            this.elements = Collections.unmodifiableList(arrayList);
            if (num.intValue() == 1) {
                this.elementCounts.remove(e2);
                HashSet hashSet = new HashSet(this.elementSet);
                hashSet.remove(e2);
                this.elementSet = Collections.unmodifiableSet(hashSet);
            } else {
                this.elementCounts.put(e2, Integer.valueOf(num.intValue() - 1));
            }
        }
    }
}
