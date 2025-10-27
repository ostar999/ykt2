package cn.hutool.core.map;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public abstract class TransMap<K, V> extends MapWrapper<K, V> {
    private static final long serialVersionUID = 1;

    public TransMap(Supplier<Map<K, V>> supplier) {
        super(supplier);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$compute$1(BiFunction biFunction, Object obj, Object obj2) {
        return biFunction.apply(customKey(obj), customValue(obj2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$computeIfPresent$0(BiFunction biFunction, Object obj, Object obj2) {
        return biFunction.apply(customKey(obj), customValue(obj2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$merge$2(BiFunction biFunction, Object obj, Object obj2) {
        return biFunction.apply(customValue(obj), customValue(obj2));
    }

    @Override // cn.hutool.core.map.MapWrapper, java.util.Map
    public V compute(K k2, final BiFunction<? super K, ? super V, ? extends V> biFunction) {
        return (V) super.compute(customKey(k2), new BiFunction() { // from class: cn.hutool.core.map.z1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f2577a.lambda$compute$1(biFunction, obj, obj2);
            }
        });
    }

    @Override // cn.hutool.core.map.MapWrapper, java.util.Map
    public V computeIfAbsent(K k2, Function<? super K, ? extends V> function) {
        return (V) super.computeIfAbsent(customKey(k2), function);
    }

    @Override // cn.hutool.core.map.MapWrapper, java.util.Map
    public V computeIfPresent(K k2, final BiFunction<? super K, ? super V, ? extends V> biFunction) {
        return (V) super.computeIfPresent(customKey(k2), new BiFunction() { // from class: cn.hutool.core.map.y1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f2573a.lambda$computeIfPresent$0(biFunction, obj, obj2);
            }
        });
    }

    @Override // cn.hutool.core.map.MapWrapper, java.util.Map
    public boolean containsKey(Object obj) {
        return super.containsKey(customKey(obj));
    }

    public abstract K customKey(Object obj);

    public abstract V customValue(Object obj);

    @Override // cn.hutool.core.map.MapWrapper, java.util.Map
    public V get(Object obj) {
        return (V) super.get(customKey(obj));
    }

    @Override // cn.hutool.core.map.MapWrapper, java.util.Map
    public V getOrDefault(Object obj, V v2) {
        return (V) super.getOrDefault(customKey(obj), customValue(v2));
    }

    @Override // cn.hutool.core.map.MapWrapper, java.util.Map
    public V merge(K k2, V v2, final BiFunction<? super V, ? super V, ? extends V> biFunction) {
        return (V) super.merge(customKey(k2), customValue(v2), new BiFunction() { // from class: cn.hutool.core.map.a2
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f2520a.lambda$merge$2(biFunction, obj, obj2);
            }
        });
    }

    @Override // cn.hutool.core.map.MapWrapper, java.util.Map
    public V put(K k2, V v2) {
        return (V) super.put(customKey(k2), customValue(v2));
    }

    @Override // cn.hutool.core.map.MapWrapper, java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        map.forEach(new BiConsumer() { // from class: cn.hutool.core.map.x1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f2569c.put(obj, obj2);
            }
        });
    }

    @Override // cn.hutool.core.map.MapWrapper, java.util.Map
    public V putIfAbsent(K k2, V v2) {
        return (V) super.putIfAbsent(customKey(k2), customValue(v2));
    }

    @Override // cn.hutool.core.map.MapWrapper, java.util.Map
    public V remove(Object obj) {
        return (V) super.remove(customKey(obj));
    }

    @Override // cn.hutool.core.map.MapWrapper, java.util.Map
    public boolean replace(K k2, V v2, V v3) {
        return super.replace(customKey(k2), customValue(v2), customValue(v3));
    }

    public TransMap(Map<K, V> map) {
        super(map);
    }

    @Override // cn.hutool.core.map.MapWrapper, java.util.Map
    public boolean remove(Object obj, Object obj2) {
        return super.remove(customKey(obj), customValue(obj2));
    }

    @Override // cn.hutool.core.map.MapWrapper, java.util.Map
    public V replace(K k2, V v2) {
        return (V) super.replace(customKey(k2), customValue(v2));
    }
}
