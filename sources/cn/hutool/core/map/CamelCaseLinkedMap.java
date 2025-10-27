package cn.hutool.core.map;

import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class CamelCaseLinkedMap<K, V> extends CamelCaseMap<K, V> {
    private static final long serialVersionUID = 4043263744224569870L;

    public CamelCaseLinkedMap() {
        this(16);
    }

    public CamelCaseLinkedMap(int i2) {
        this(i2, 0.75f);
    }

    public CamelCaseLinkedMap(Map<? extends K, ? extends V> map) {
        this(0.75f, map);
    }

    public CamelCaseLinkedMap(float f2, Map<? extends K, ? extends V> map) {
        this(map.size(), f2);
        putAll(map);
    }

    public CamelCaseLinkedMap(int i2, float f2) {
        super(new LinkedHashMap(i2, f2));
    }
}
