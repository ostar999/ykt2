package cn.hutool.core.lang.mutable;

import cn.hutool.core.lang.Pair;

/* loaded from: classes.dex */
public class MutablePair<K, V> extends Pair<K, V> implements Mutable<Pair<K, V>> {
    private static final long serialVersionUID = 1;

    public MutablePair(K k2, V v2) {
        super(k2, v2);
    }

    @Override // cn.hutool.core.lang.mutable.Mutable
    public Pair<K, V> get() {
        return this;
    }

    public MutablePair<K, V> setKey(K k2) {
        this.key = k2;
        return this;
    }

    public MutablePair<K, V> setValue(V v2) {
        this.value = v2;
        return this;
    }

    @Override // cn.hutool.core.lang.mutable.Mutable
    public void set(Pair<K, V> pair) {
        this.key = pair.getKey();
        this.value = pair.getValue();
    }
}
