package cn.hutool.core.collection;

import cn.hutool.core.lang.Assert;
import java.util.List;

/* loaded from: classes.dex */
public class AvgPartition<T> extends Partition<T> {
    final int limit;
    final int remainder;

    public AvgPartition(List<T> list, int i2) throws Throwable {
        super(list, list.size() / (i2 <= 0 ? 1 : i2));
        Assert.isTrue(i2 > 0, "Partition limit must be > 0", new Object[0]);
        this.limit = i2;
        this.remainder = list.size() % i2;
    }

    @Override // cn.hutool.core.collection.Partition, java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.limit;
    }

    @Override // cn.hutool.core.collection.Partition, java.util.AbstractList, java.util.List
    public List<T> get(int i2) {
        int i3 = this.size;
        int i4 = this.remainder;
        int iMin = (i2 * i3) + Math.min(i2, i4);
        int i5 = i3 + iMin;
        if (i2 + 1 <= i4) {
            i5++;
        }
        return this.list.subList(iMin, i5);
    }
}
