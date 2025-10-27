package cn.hutool.core.comparator;

import cn.hutool.core.lang.Assert;
import java.util.Comparator;

/* loaded from: classes.dex */
public class InstanceComparator<T> implements Comparator<T> {
    private final boolean atEndIfMiss;
    private final Class<?>[] instanceOrder;

    public InstanceComparator(Class<?>... clsArr) {
        this(false, clsArr);
    }

    private int getOrder(T t2) {
        if (t2 != null) {
            int i2 = 0;
            while (true) {
                Class<?>[] clsArr = this.instanceOrder;
                if (i2 >= clsArr.length) {
                    break;
                }
                if (clsArr[i2].isInstance(t2)) {
                    return i2;
                }
                i2++;
            }
        }
        if (this.atEndIfMiss) {
            return this.instanceOrder.length;
        }
        return -1;
    }

    @Override // java.util.Comparator
    public int compare(T t2, T t3) {
        return Integer.compare(getOrder(t2), getOrder(t3));
    }

    public InstanceComparator(boolean z2, Class<?>... clsArr) throws IllegalArgumentException {
        Assert.notNull(clsArr, "'instanceOrder' array must not be null", new Object[0]);
        this.atEndIfMiss = z2;
        this.instanceOrder = clsArr;
    }
}
