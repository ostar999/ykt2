package cn.hutool.core.map;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/* loaded from: classes.dex */
public class BiMap<K, V> extends MapWrapper<K, V> {
    private static final long serialVersionUID = 1;
    private Map<V, K> inverse;

    public BiMap(Map<K, V> map) {
        super(map);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putAll$0(Object obj, Object obj2) {
        this.inverse.put(obj2, obj);
    }

    private void resetInverseMap() {
        if (this.inverse != null) {
            this.inverse = null;
        }
    }

    @Override // cn.hutool.core.map.MapWrapper, java.util.Map
    public void clear() {
        super.clear();
        this.inverse = null;
    }

    @Override // cn.hutool.core.map.MapWrapper, java.util.Map
    public V compute(K k2, BiFunction<? super K, ? super V, ? extends V> biFunction) {
        V v2 = (V) super.compute(k2, biFunction);
        resetInverseMap();
        return v2;
    }

    @Override // cn.hutool.core.map.MapWrapper, java.util.Map
    public V computeIfAbsent(K k2, Function<? super K, ? extends V> function) {
        V v2 = (V) super.computeIfAbsent(k2, function);
        resetInverseMap();
        return v2;
    }

    @Override // cn.hutool.core.map.MapWrapper, java.util.Map
    public V computeIfPresent(K k2, BiFunction<? super K, ? super V, ? extends V> biFunction) {
        V v2 = (V) super.computeIfPresent(k2, biFunction);
        resetInverseMap();
        return v2;
    }

    public Map<V, K> getInverse() {
        if (this.inverse == null) {
            this.inverse = MapUtil.inverse(getRaw());
        }
        return this.inverse;
    }

    public K getKey(V v2) {
        return getInverse().get(v2);
    }

    @Override // cn.hutool.core.map.MapWrapper, java.util.Map
    public V merge(K k2, V v2, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        V v3 = (V) super.merge(k2, v2, biFunction);
        resetInverseMap();
        return v3;
    }

    @Override // cn.hutool.core.map.MapWrapper, java.util.Map
    public V put(K k2, V v2) {
        Map<V, K> map = this.inverse;
        if (map != null) {
            map.put(v2, k2);
        }
        return (V) super.put(k2, v2);
    }

    @Override // cn.hutool.core.map.MapWrapper, java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        super.putAll(map);
        if (this.inverse != null) {
            map.forEach(new BiConsumer() { // from class: cn.hutool.core.map.b
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f2522c.lambda$putAll$0(obj, obj2);
                }
            });
        }
    }

    @Override // cn.hutool.core.map.MapWrapper, java.util.Map
    public V putIfAbsent(K k2, V v2) {
        Map<V, K> map = this.inverse;
        if (map != null) {
            map.putIfAbsent(v2, k2);
        }
        return (V) super.putIfAbsent(k2, v2);
    }

    @Override // cn.hutool.core.map.MapWrapper, java.util.Map
    public V remove(Object obj) {
        V v2 = (V) super.remove(obj);
        Map<V, K> map = this.inverse;
        if (map != null && v2 != null) {
            map.remove(v2);
        }
        return v2;
    }

    @Override // cn.hutool.core.map.MapWrapper, java.util.Map
    public boolean remove(Object obj, Object obj2) {
        Map<V, K> map;
        return super.remove(obj, obj2) && (map = this.inverse) != null && map.remove(obj2, obj);
    }
}
