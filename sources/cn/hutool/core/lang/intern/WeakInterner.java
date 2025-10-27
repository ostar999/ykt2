package cn.hutool.core.lang.intern;

import cn.hutool.core.map.WeakConcurrentMap;
import java.lang.ref.WeakReference;
import java.util.function.Function;

/* loaded from: classes.dex */
public class WeakInterner<T> implements Interner<T> {
    private final WeakConcurrentMap<T, WeakReference<T>> cache = new WeakConcurrentMap<>();

    @Override // cn.hutool.core.lang.intern.Interner
    public T intern(T t2) {
        T t3;
        if (t2 == null) {
            return null;
        }
        do {
            t3 = this.cache.computeIfAbsent((WeakConcurrentMap<T, WeakReference<T>>) t2, (Function<? super WeakConcurrentMap<T, WeakReference<T>>, ? extends WeakReference<T>>) new Function() { // from class: e0.a
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return new WeakReference(obj);
                }
            }).get();
        } while (t3 == null);
        return t3;
    }
}
