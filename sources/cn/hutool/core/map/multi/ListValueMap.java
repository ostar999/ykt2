package cn.hutool.core.map.multi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class ListValueMap<K, V> extends AbsCollValueMap<K, V, List<V>> {
    private static final long serialVersionUID = 6044017508487827899L;

    public ListValueMap() {
        this(16);
    }

    public ListValueMap(int i2) {
        this(i2, 0.75f);
    }

    @Override // cn.hutool.core.map.multi.AbsCollValueMap
    public List<V> createCollection() {
        return new ArrayList(3);
    }

    public ListValueMap(Map<? extends K, ? extends Collection<V>> map) {
        this(0.75f, map);
    }

    public ListValueMap(float f2, Map<? extends K, ? extends Collection<V>> map) {
        this(map.size(), f2);
        putAllValues(map);
    }

    public ListValueMap(int i2, float f2) {
        super(new HashMap(i2, f2));
    }
}
