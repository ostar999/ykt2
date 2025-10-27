package cn.hutool.core.map.multi;

import cn.hutool.core.lang.func.Func0;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class CollectionValueMap<K, V> extends AbsCollValueMap<K, V, Collection<V>> {
    private static final long serialVersionUID = 9012989578038102983L;
    private final Func0<Collection<V>> collectionCreateFunc;

    public CollectionValueMap() {
        this(16);
    }

    @Override // cn.hutool.core.map.multi.AbsCollValueMap
    public Collection<V> createCollection() {
        return this.collectionCreateFunc.callWithRuntimeException();
    }

    public CollectionValueMap(int i2) {
        this(i2, 0.75f);
    }

    public CollectionValueMap(Map<? extends K, ? extends Collection<V>> map) {
        this(0.75f, map);
    }

    public CollectionValueMap(float f2, Map<? extends K, ? extends Collection<V>> map) {
        this(f2, map, new d());
    }

    public CollectionValueMap(int i2, float f2) {
        this(i2, f2, new d());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public CollectionValueMap(float f2, Map<? extends K, ? extends Collection<V>> map, Func0<Collection<V>> func0) {
        this(map.size(), f2, func0);
        putAll(map);
    }

    public CollectionValueMap(int i2, float f2, Func0<Collection<V>> func0) {
        super(new HashMap(i2, f2));
        this.collectionCreateFunc = func0;
    }
}
