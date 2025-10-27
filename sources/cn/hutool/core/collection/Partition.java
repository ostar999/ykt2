package cn.hutool.core.collection;

import cn.hutool.core.lang.Assert;
import java.util.AbstractList;
import java.util.List;

/* loaded from: classes.dex */
public class Partition<T> extends AbstractList<List<T>> {
    protected final List<T> list;
    protected final int size;

    public Partition(List<T> list, int i2) {
        this.list = (List) Assert.notNull(list);
        this.size = Math.min(list.size(), i2);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        int i2 = this.size;
        if (i2 == 0) {
            return 0;
        }
        return ((this.list.size() + i2) - 1) / i2;
    }

    @Override // java.util.AbstractList, java.util.List
    public List<T> get(int i2) {
        int i3 = this.size;
        int i4 = i2 * i3;
        return this.list.subList(i4, Math.min(i3 + i4, this.list.size()));
    }
}
