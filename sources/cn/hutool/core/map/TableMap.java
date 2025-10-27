package cn.hutool.core.map;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.Matcher;
import cn.hutool.core.util.ObjectUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/* loaded from: classes.dex */
public class TableMap<K, V> implements Map<K, V>, Iterable<Map.Entry<K, V>>, Serializable {
    private static final int DEFAULT_CAPACITY = 10;
    private static final long serialVersionUID = 1;
    private final List<K> keys;
    private final List<V> values;

    public TableMap() {
        this(10);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getKeys$1(Object obj, Object obj2) {
        return ObjectUtil.equal(obj2, obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getValues$0(Object obj, Object obj2) {
        return ObjectUtil.equal(obj2, obj);
    }

    @Override // java.util.Map
    public void clear() {
        this.keys.clear();
        this.values.clear();
    }

    @Override // java.util.Map
    public V computeIfPresent(K k2, BiFunction<? super K, ? super V, ? extends V> biFunction) {
        V v2 = null;
        if (biFunction == null) {
            return null;
        }
        int i2 = 0;
        while (i2 < size()) {
            if (ObjectUtil.equals(k2, this.keys.get(i2))) {
                Object objApply = biFunction.apply(k2, this.values.get(i2));
                if (objApply != null) {
                    v2 = (V) this.values.set(i2, objApply);
                } else {
                    removeByIndex(i2);
                    i2--;
                }
            }
            i2++;
        }
        return v2;
    }

    @Override // java.util.Map
    public boolean containsKey(Object obj) {
        return this.keys.contains(obj);
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        return this.values.contains(obj);
    }

    @Override // java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (int i2 = 0; i2 < size(); i2++) {
            linkedHashSet.add(MapUtil.entry(this.keys.get(i2), this.values.get(i2)));
        }
        return linkedHashSet;
    }

    @Override // java.util.Map
    public void forEach(BiConsumer<? super K, ? super V> biConsumer) {
        for (int i2 = 0; i2 < size(); i2++) {
            biConsumer.accept(this.keys.get(i2), this.values.get(i2));
        }
    }

    @Override // java.util.Map
    public V get(Object obj) {
        int iIndexOf = this.keys.indexOf(obj);
        if (iIndexOf > -1) {
            return this.values.get(iIndexOf);
        }
        return null;
    }

    public K getKey(V v2) {
        int iIndexOf = this.values.indexOf(v2);
        if (iIndexOf > -1) {
            return this.keys.get(iIndexOf);
        }
        return null;
    }

    public List<K> getKeys(final V v2) {
        return CollUtil.getAny(this.keys, ListUtil.indexOfAll(this.values, new Matcher() { // from class: cn.hutool.core.map.v1
            @Override // cn.hutool.core.lang.Matcher
            public final boolean match(Object obj) {
                return TableMap.lambda$getKeys$1(v2, obj);
            }
        }));
    }

    public List<V> getValues(final K k2) {
        return CollUtil.getAny(this.values, ListUtil.indexOfAll(this.keys, new Matcher() { // from class: cn.hutool.core.map.w1
            @Override // cn.hutool.core.lang.Matcher
            public final boolean match(Object obj) {
                return TableMap.lambda$getValues$0(k2, obj);
            }
        }));
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return CollUtil.isEmpty((Collection<?>) this.keys);
    }

    @Override // java.lang.Iterable
    public Iterator<Map.Entry<K, V>> iterator() {
        return new Iterator<Map.Entry<K, V>>() { // from class: cn.hutool.core.map.TableMap.1
            private final Iterator<K> keysIter;
            private final Iterator<V> valuesIter;

            {
                this.keysIter = TableMap.this.keys.iterator();
                this.valuesIter = TableMap.this.values.iterator();
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.keysIter.hasNext() && this.valuesIter.hasNext();
            }

            @Override // java.util.Iterator
            public void remove() {
                this.keysIter.remove();
                this.valuesIter.remove();
            }

            @Override // java.util.Iterator
            public Map.Entry<K, V> next() {
                return MapUtil.entry(this.keysIter.next(), this.valuesIter.next());
            }
        };
    }

    @Override // java.util.Map
    public Set<K> keySet() {
        return new HashSet(this.keys);
    }

    public List<K> keys() {
        return Collections.unmodifiableList(this.keys);
    }

    @Override // java.util.Map
    public V put(K k2, V v2) {
        this.keys.add(k2);
        this.values.add(v2);
        return null;
    }

    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override // java.util.Map
    public V remove(Object obj) {
        V vRemoveByIndex = null;
        while (true) {
            int iIndexOf = this.keys.indexOf(obj);
            if (iIndexOf <= -1) {
                return vRemoveByIndex;
            }
            vRemoveByIndex = removeByIndex(iIndexOf);
        }
    }

    public V removeByIndex(int i2) {
        this.keys.remove(i2);
        return this.values.remove(i2);
    }

    @Override // java.util.Map
    public boolean replace(K k2, V v2, V v3) {
        for (int i2 = 0; i2 < size(); i2++) {
            if (ObjectUtil.equals(k2, this.keys.get(i2)) && ObjectUtil.equals(v2, this.values.get(i2))) {
                this.values.set(i2, v3);
                return true;
            }
        }
        return false;
    }

    @Override // java.util.Map
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> biFunction) {
        for (int i2 = 0; i2 < size(); i2++) {
            this.values.set(i2, biFunction.apply(this.keys.get(i2), this.values.get(i2)));
        }
    }

    @Override // java.util.Map
    public int size() {
        return this.keys.size();
    }

    public String toString() {
        return "TableMap{keys=" + this.keys + ", values=" + this.values + '}';
    }

    @Override // java.util.Map
    public Collection<V> values() {
        return Collections.unmodifiableList(this.values);
    }

    public TableMap(int i2) {
        this.keys = new ArrayList(i2);
        this.values = new ArrayList(i2);
    }

    @Override // java.util.Map
    public boolean remove(Object obj, Object obj2) {
        int i2 = 0;
        boolean z2 = false;
        while (i2 < size()) {
            if (ObjectUtil.equals(obj, this.keys.get(i2)) && ObjectUtil.equals(obj2, this.values.get(i2))) {
                removeByIndex(i2);
                i2--;
                z2 = true;
            }
            i2++;
        }
        return z2;
    }

    @Override // java.util.Map
    public V replace(K k2, V v2) {
        V v3 = null;
        for (int i2 = 0; i2 < size(); i2++) {
            if (ObjectUtil.equals(k2, this.keys.get(i2))) {
                v3 = this.values.set(i2, v2);
            }
        }
        return v3;
    }

    public TableMap(K[] kArr, V[] vArr) {
        this.keys = CollUtil.toList(kArr);
        this.values = CollUtil.toList(vArr);
    }
}
