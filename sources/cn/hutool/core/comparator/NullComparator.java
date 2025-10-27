package cn.hutool.core.comparator;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

/* loaded from: classes.dex */
public class NullComparator<T> implements Comparator<T>, Serializable {
    private static final long serialVersionUID = 1;
    protected final Comparator<T> comparator;
    protected final boolean nullGreater;

    /* JADX WARN: Multi-variable type inference failed */
    public NullComparator(boolean z2, Comparator<? super T> comparator) {
        this.nullGreater = z2;
        this.comparator = comparator;
    }

    @Override // java.util.Comparator
    public int compare(T t2, T t3) {
        if (t2 == t3) {
            return 0;
        }
        return t2 == null ? this.nullGreater ? 1 : -1 : t3 == null ? this.nullGreater ? -1 : 1 : doCompare(t2, t3);
    }

    public int doCompare(T t2, T t3) {
        Comparator<T> comparator = this.comparator;
        if (comparator != null) {
            return comparator.compare(t2, t3);
        }
        if ((t2 instanceof Comparable) && (t3 instanceof Comparable)) {
            return ((Comparable) t2).compareTo(t3);
        }
        return 0;
    }

    @Override // java.util.Comparator
    public Comparator<T> thenComparing(Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator);
        boolean z2 = this.nullGreater;
        Comparator<T> comparator2 = this.comparator;
        if (comparator2 != null) {
            comparator = comparator2.thenComparing(comparator);
        }
        return new NullComparator(z2, comparator);
    }
}
