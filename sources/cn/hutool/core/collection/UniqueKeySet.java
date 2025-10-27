package cn.hutool.core.collection;

import cn.hutool.core.map.MapBuilder;
import cn.hutool.core.util.ObjectUtil;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;

/* loaded from: classes.dex */
public class UniqueKeySet<K, V> extends AbstractSet<V> implements Serializable {
    private static final long serialVersionUID = 1;
    private Map<K, V> map;
    private final Function<V, K> uniqueGenerator;

    public UniqueKeySet(Function<V, K> function) {
        this(false, (Function) function);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean add(V v2) {
        return this.map.put(this.uniqueGenerator.apply(v2), v2) == null;
    }

    public boolean addAllIfAbsent(Collection<? extends V> collection) {
        Iterator<? extends V> it = collection.iterator();
        boolean z2 = false;
        while (it.hasNext()) {
            if (addIfAbsent(it.next())) {
                z2 = true;
            }
        }
        return z2;
    }

    public boolean addIfAbsent(V v2) {
        return this.map.putIfAbsent(this.uniqueGenerator.apply(v2), v2) == null;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public void clear() {
        this.map.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(Object obj) {
        return this.map.containsKey(this.uniqueGenerator.apply(obj));
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public Iterator<V> iterator() {
        return this.map.values().iterator();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean remove(Object obj) {
        return this.map.remove(this.uniqueGenerator.apply(obj)) != null;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.map.size();
    }

    public UniqueKeySet(Function<V, K> function, Collection<? extends V> collection) {
        this(false, (Function) function, (Collection) collection);
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public UniqueKeySet<K, V> m17clone() {
        try {
            UniqueKeySet<K, V> uniqueKeySet = (UniqueKeySet) super.clone();
            uniqueKeySet.map = (Map) ObjectUtil.clone(this.map);
            return uniqueKeySet;
        } catch (CloneNotSupportedException e2) {
            throw new InternalError(e2);
        }
    }

    public UniqueKeySet(boolean z2, Function<V, K> function) {
        this(MapBuilder.create(z2), function);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public UniqueKeySet(boolean z2, Function<V, K> function, Collection<? extends V> collection) {
        this(z2, function);
        addAll(collection);
    }

    public UniqueKeySet(int i2, float f2, Function<V, K> function) {
        this(MapBuilder.create(new HashMap(i2, f2)), function);
    }

    public UniqueKeySet(MapBuilder<K, V> mapBuilder, Function<V, K> function) {
        this.map = mapBuilder.build();
        this.uniqueGenerator = function;
    }
}
