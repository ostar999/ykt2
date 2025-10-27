package cn.hutool.core.comparator;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import java.util.Comparator;

/* loaded from: classes.dex */
public class IndexedComparator<T> implements Comparator<T> {
    private final T[] array;
    private final boolean atEndIfMiss;

    public IndexedComparator(T... tArr) {
        this(false, tArr);
    }

    private int getOrder(T t2) {
        int iIndexOf = ArrayUtil.indexOf(this.array, t2);
        if (iIndexOf >= 0) {
            return iIndexOf;
        }
        if (this.atEndIfMiss) {
            return this.array.length;
        }
        return -1;
    }

    @Override // java.util.Comparator
    public int compare(T t2, T t3) {
        int order = getOrder(t2);
        int order2 = getOrder(t3);
        return order == order2 ? (order < 0 || order == this.array.length) ? 1 : 0 : Integer.compare(order, order2);
    }

    public IndexedComparator(boolean z2, T... tArr) throws IllegalArgumentException {
        Assert.notNull(tArr, "'objs' array must not be null", new Object[0]);
        this.atEndIfMiss = z2;
        this.array = tArr;
    }
}
