package cn.hutool.core.collection;

import java.util.Iterator;

/* loaded from: classes.dex */
public interface IterableIter<T> extends Iterable<T>, Iterator<T> {
    @Override // java.lang.Iterable
    Iterator<T> iterator();
}
