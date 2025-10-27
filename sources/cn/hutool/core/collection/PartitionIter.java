package cn.hutool.core.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class PartitionIter<T> implements IterableIter<List<T>>, Serializable {
    private static final long serialVersionUID = 1;
    protected final Iterator<T> iterator;
    protected final int partitionSize;

    public PartitionIter(Iterator<T> it, int i2) {
        this.iterator = it;
        this.partitionSize = i2;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override // cn.hutool.core.collection.IterableIter, java.lang.Iterable
    public /* synthetic */ Iterator iterator() {
        return t0.a(this);
    }

    @Override // java.util.Iterator
    public List<T> next() {
        ArrayList arrayList = new ArrayList(this.partitionSize);
        for (int i2 = 0; i2 < this.partitionSize && this.iterator.hasNext(); i2++) {
            arrayList.add(this.iterator.next());
        }
        return arrayList;
    }
}
