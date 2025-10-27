package cn.hutool.core.lang;

import cn.hutool.core.clone.CloneSupport;
import cn.hutool.core.text.StrPool;
import java.io.Serializable;
import java.util.Objects;

/* loaded from: classes.dex */
public class Pair<K, V> extends CloneSupport<Pair<K, V>> implements Serializable {
    private static final long serialVersionUID = 1;
    protected K key;
    protected V value;

    public Pair(K k2, V v2) {
        this.key = k2;
        this.value = v2;
    }

    public static <K, V> Pair<K, V> of(K k2, V v2) {
        return new Pair<>(k2, v2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Pair)) {
            return false;
        }
        Pair pair = (Pair) obj;
        return Objects.equals(getKey(), pair.getKey()) && Objects.equals(getValue(), pair.getValue());
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

    public int hashCode() {
        return Objects.hashCode(this.key) ^ Objects.hashCode(this.value);
    }

    public String toString() {
        return "Pair [key=" + this.key + ", value=" + this.value + StrPool.BRACKET_END;
    }
}
