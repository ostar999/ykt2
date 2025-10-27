package cn.hutool.core.map;

import cn.hutool.core.util.JdkUtil;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/* loaded from: classes.dex */
public class SafeConcurrentHashMap<K, V> extends ConcurrentHashMap<K, V> {
    private static final long serialVersionUID = 1;

    public SafeConcurrentHashMap() {
    }

    @Override // java.util.concurrent.ConcurrentHashMap, java.util.Map, java.util.concurrent.ConcurrentMap
    public V computeIfAbsent(K k2, Function<? super K, ? extends V> function) {
        return JdkUtil.IS_JDK8 ? (V) MapUtil.computeIfAbsentForJdk8(this, k2, function) : (V) super.computeIfAbsent(k2, function);
    }

    public SafeConcurrentHashMap(int i2) {
        super(i2);
    }

    public SafeConcurrentHashMap(Map<? extends K, ? extends V> map) {
        super(map);
    }

    public SafeConcurrentHashMap(int i2, float f2) {
        super(i2, f2);
    }

    public SafeConcurrentHashMap(int i2, float f2, int i3) {
        super(i2, f2, i3);
    }
}
