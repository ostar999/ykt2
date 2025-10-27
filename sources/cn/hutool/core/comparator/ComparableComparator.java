package cn.hutool.core.comparator;

import java.io.Serializable;
import java.lang.Comparable;
import java.util.Comparator;

/* loaded from: classes.dex */
public class ComparableComparator<E extends Comparable<? super E>> implements Comparator<E>, Serializable {
    public static final ComparableComparator INSTANCE = new ComparableComparator();
    private static final long serialVersionUID = 3020871676147289162L;

    @Override // java.util.Comparator
    public boolean equals(Object obj) {
        return this == obj || (obj != null && obj.getClass().equals(getClass()));
    }

    public int hashCode() {
        return 1769708912;
    }

    @Override // java.util.Comparator
    public int compare(E e2, E e3) {
        return e2.compareTo(e3);
    }
}
