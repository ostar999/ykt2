package cn.hutool.core.map;

import java.util.Map;

/* loaded from: classes.dex */
public abstract class CustomKeyMap<K, V> extends TransMap<K, V> {
    private static final long serialVersionUID = 4043263744224569870L;

    public CustomKeyMap(Map<K, V> map) {
        super(map);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // cn.hutool.core.map.TransMap
    public V customValue(Object obj) {
        return obj;
    }
}
