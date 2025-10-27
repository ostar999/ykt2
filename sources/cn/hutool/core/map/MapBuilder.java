package cn.hutool.core.map;

import cn.hutool.core.builder.Builder;
import java.util.Map;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class MapBuilder<K, V> implements Builder<Map<K, V>> {
    private static final long serialVersionUID = 1;
    private final Map<K, V> map;

    public MapBuilder(Map<K, V> map) {
        this.map = map;
    }

    public static <K, V> MapBuilder<K, V> create() {
        return create(false);
    }

    public MapBuilder<K, V> clear() {
        this.map.clear();
        return this;
    }

    public String join(String str, String str2) {
        return MapUtil.join(this.map, str, str2, new String[0]);
    }

    public String joinIgnoreNull(String str, String str2) {
        return MapUtil.joinIgnoreNull(this.map, str, str2, new String[0]);
    }

    public Map<K, V> map() {
        return this.map;
    }

    public MapBuilder<K, V> put(K k2, V v2) {
        this.map.put(k2, v2);
        return this;
    }

    public MapBuilder<K, V> putAll(Map<K, V> map) {
        this.map.putAll(map);
        return this;
    }

    public static <K, V> MapBuilder<K, V> create(boolean z2) {
        return create(MapUtil.newHashMap(z2));
    }

    @Override // cn.hutool.core.builder.Builder
    public Map<K, V> build() {
        return map();
    }

    public String join(String str, String str2, boolean z2) {
        return MapUtil.join(this.map, str, str2, z2, new String[0]);
    }

    public MapBuilder<K, V> put(boolean z2, K k2, V v2) {
        if (z2) {
            put(k2, v2);
        }
        return this;
    }

    public static <K, V> MapBuilder<K, V> create(Map<K, V> map) {
        return new MapBuilder<>(map);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public MapBuilder<K, V> put(boolean z2, K k2, Supplier<V> supplier) {
        if (z2) {
            put(k2, supplier.get());
        }
        return this;
    }
}
