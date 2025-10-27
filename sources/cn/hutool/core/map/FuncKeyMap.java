package cn.hutool.core.map;

import java.util.Map;
import java.util.function.Function;

/* loaded from: classes.dex */
public class FuncKeyMap<K, V> extends CustomKeyMap<K, V> {
    private static final long serialVersionUID = 1;
    private final Function<Object, K> keyFunc;

    public FuncKeyMap(Map<K, V> map, Function<Object, K> function) {
        super(map);
        this.keyFunc = function;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // cn.hutool.core.map.TransMap
    public K customKey(Object obj) {
        Function<Object, K> function = this.keyFunc;
        return function != null ? (K) function.apply(obj) : obj;
    }
}
