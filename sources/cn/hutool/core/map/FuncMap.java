package cn.hutool.core.map;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class FuncMap<K, V> extends TransMap<K, V> {
    private static final long serialVersionUID = 1;
    private final Function<Object, K> keyFunc;
    private final Function<Object, V> valueFunc;

    public FuncMap(Supplier<Map<K, V>> supplier, Function<Object, K> function, Function<Object, V> function2) {
        this((Map) supplier.get(), function, function2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // cn.hutool.core.map.TransMap
    public K customKey(Object obj) {
        Function<Object, K> function = this.keyFunc;
        return function != null ? (K) function.apply(obj) : obj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // cn.hutool.core.map.TransMap
    public V customValue(Object obj) {
        Function<Object, V> function = this.valueFunc;
        return function != null ? (V) function.apply(obj) : obj;
    }

    public FuncMap(Map<K, V> map, Function<Object, K> function, Function<Object, V> function2) {
        super(map);
        this.keyFunc = function;
        this.valueFunc = function2;
    }
}
