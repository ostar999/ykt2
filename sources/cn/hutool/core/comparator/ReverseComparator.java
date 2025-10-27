package cn.hutool.core.comparator;

import java.io.Serializable;
import java.util.Comparator;

/* loaded from: classes.dex */
public class ReverseComparator<E> implements Comparator<E>, Serializable {
    private static final long serialVersionUID = 8083701245147495562L;
    private final Comparator<? super E> comparator;

    public ReverseComparator(Comparator<? super E> comparator) {
        this.comparator = comparator == null ? ComparableComparator.INSTANCE : comparator;
    }

    @Override // java.util.Comparator
    public int compare(E e2, E e3) {
        return this.comparator.compare(e3, e2);
    }

    @Override // java.util.Comparator
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && obj.getClass().equals(getClass())) {
            return this.comparator.equals(((ReverseComparator) obj).comparator);
        }
        return false;
    }

    public int hashCode() {
        return this.comparator.hashCode() ^ 175311160;
    }
}
