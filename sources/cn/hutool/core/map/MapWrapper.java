package cn.hutool.core.map;

import cn.hutool.core.util.ObjectUtil;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class MapWrapper<K, V> implements Map<K, V>, Iterable<Map.Entry<K, V>>, Serializable, Cloneable {
    protected static final int DEFAULT_INITIAL_CAPACITY = 16;
    protected static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private static final long serialVersionUID = -7524578042008586382L;
    private Map<K, V> raw;

    public MapWrapper(Supplier<Map<K, V>> supplier) {
        this((Map) supplier.get());
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.raw = (Map) objectInputStream.readObject();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.raw);
    }

    @Override // java.util.Map
    public void clear() {
        this.raw.clear();
    }

    @Override // java.util.Map
    public V compute(K k2, BiFunction<? super K, ? super V, ? extends V> biFunction) {
        return (V) this.raw.compute(k2, biFunction);
    }

    @Override // java.util.Map
    public V computeIfAbsent(K k2, Function<? super K, ? extends V> function) {
        return (V) this.raw.computeIfAbsent(k2, function);
    }

    @Override // java.util.Map
    public V computeIfPresent(K k2, BiFunction<? super K, ? super V, ? extends V> biFunction) {
        return (V) this.raw.computeIfPresent(k2, biFunction);
    }

    @Override // java.util.Map
    public boolean containsKey(Object obj) {
        return this.raw.containsKey(obj);
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        return this.raw.containsValue(obj);
    }

    @Override // java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        return this.raw.entrySet();
    }

    @Override // java.util.Map
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.raw, ((MapWrapper) obj).raw);
    }

    @Override // java.util.Map
    public void forEach(BiConsumer<? super K, ? super V> biConsumer) {
        this.raw.forEach(biConsumer);
    }

    @Override // java.util.Map
    public V get(Object obj) {
        return this.raw.get(obj);
    }

    @Override // java.util.Map
    public V getOrDefault(Object obj, V v2) {
        return (V) this.raw.getOrDefault(obj, v2);
    }

    public Map<K, V> getRaw() {
        return this.raw;
    }

    @Override // java.util.Map
    public int hashCode() {
        return Objects.hash(this.raw);
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return this.raw.isEmpty();
    }

    @Override // java.lang.Iterable
    public Iterator<Map.Entry<K, V>> iterator() {
        return entrySet().iterator();
    }

    @Override // java.util.Map
    public Set<K> keySet() {
        return this.raw.keySet();
    }

    @Override // java.util.Map
    public V merge(K k2, V v2, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        return (V) this.raw.merge(k2, v2, biFunction);
    }

    @Override // java.util.Map
    public V put(K k2, V v2) {
        return this.raw.put(k2, v2);
    }

    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        this.raw.putAll(map);
    }

    @Override // java.util.Map
    public V putIfAbsent(K k2, V v2) {
        return (V) this.raw.putIfAbsent(k2, v2);
    }

    @Override // java.util.Map
    public V remove(Object obj) {
        return this.raw.remove(obj);
    }

    @Override // java.util.Map
    public boolean replace(K k2, V v2, V v3) {
        return this.raw.replace(k2, v2, v3);
    }

    @Override // java.util.Map
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> biFunction) {
        this.raw.replaceAll(biFunction);
    }

    @Override // java.util.Map
    public int size() {
        return this.raw.size();
    }

    public String toString() {
        return this.raw.toString();
    }

    @Override // java.util.Map
    public Collection<V> values() {
        return this.raw.values();
    }

    public MapWrapper(Map<K, V> map) {
        this.raw = map;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public MapWrapper<K, V> m18clone() throws CloneNotSupportedException {
        MapWrapper<K, V> mapWrapper = (MapWrapper) super.clone();
        mapWrapper.raw = (Map) ObjectUtil.clone(this.raw);
        return mapWrapper;
    }

    @Override // java.util.Map
    public boolean remove(Object obj, Object obj2) {
        return this.raw.remove(obj, obj2);
    }

    @Override // java.util.Map
    public V replace(K k2, V v2) {
        return (V) this.raw.replace(k2, v2);
    }
}
