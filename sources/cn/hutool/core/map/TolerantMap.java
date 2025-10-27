package cn.hutool.core.map;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes.dex */
public class TolerantMap<K, V> extends MapWrapper<K, V> {
    private static final long serialVersionUID = -4158133823263496197L;
    private final V defaultValue;

    public TolerantMap(V v2) {
        this(new HashMap(), v2);
    }

    public static <K, V> TolerantMap<K, V> of(Map<K, V> map, V v2) {
        return new TolerantMap<>(map, v2);
    }

    @Override // cn.hutool.core.map.MapWrapper, java.util.Map
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass() || !super.equals(obj)) {
            return false;
        }
        TolerantMap tolerantMap = (TolerantMap) obj;
        return getRaw().equals(tolerantMap.getRaw()) && Objects.equals(this.defaultValue, tolerantMap.defaultValue);
    }

    @Override // cn.hutool.core.map.MapWrapper, java.util.Map
    public V get(Object obj) {
        return getOrDefault(obj, this.defaultValue);
    }

    @Override // cn.hutool.core.map.MapWrapper, java.util.Map
    public int hashCode() {
        return Objects.hash(getRaw(), this.defaultValue);
    }

    @Override // cn.hutool.core.map.MapWrapper
    public String toString() {
        return "TolerantMap{map=" + getRaw() + ", defaultValue=" + this.defaultValue + '}';
    }

    public TolerantMap(int i2, float f2, V v2) {
        this(new HashMap(i2, f2), v2);
    }

    public TolerantMap(int i2, V v2) {
        this(new HashMap(i2), v2);
    }

    public TolerantMap(Map<K, V> map, V v2) {
        super(map);
        this.defaultValue = v2;
    }
}
