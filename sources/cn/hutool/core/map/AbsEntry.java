package cn.hutool.core.map;

import cn.hutool.core.util.ObjectUtil;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class AbsEntry<K, V> implements Map.Entry<K, V> {
    @Override // java.util.Map.Entry
    public boolean equals(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return ObjectUtil.equals(getKey(), entry.getKey()) && ObjectUtil.equals(getValue(), entry.getValue());
    }

    @Override // java.util.Map.Entry
    public int hashCode() {
        K key = getKey();
        V value = getValue();
        return (key == null ? 0 : key.hashCode()) ^ (value != null ? value.hashCode() : 0);
    }

    @Override // java.util.Map.Entry
    public V setValue(V v2) {
        throw new UnsupportedOperationException("Entry is read only.");
    }

    public String toString() {
        return getKey() + "=" + getValue();
    }
}
