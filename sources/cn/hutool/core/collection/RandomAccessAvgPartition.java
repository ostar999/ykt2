package cn.hutool.core.collection;

import java.util.List;
import java.util.RandomAccess;

/* loaded from: classes.dex */
public class RandomAccessAvgPartition<T> extends AvgPartition<T> implements RandomAccess {
    public RandomAccessAvgPartition(List<T> list, int i2) {
        super(list, i2);
    }
}
