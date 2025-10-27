package cn.hutool.core.map;

import cn.hutool.core.util.ReferenceUtil;
import java.lang.ref.Reference;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes.dex */
public class WeakConcurrentMap<K, V> extends ReferenceConcurrentMap<K, V> {
    public WeakConcurrentMap() {
        this(new SafeConcurrentHashMap());
    }

    public WeakConcurrentMap(ConcurrentMap<Reference<K>, V> concurrentMap) {
        super(concurrentMap, ReferenceUtil.ReferenceType.WEAK);
    }
}
