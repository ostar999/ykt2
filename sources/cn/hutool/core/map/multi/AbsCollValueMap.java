package cn.hutool.core.map.multi;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapWrapper;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public abstract class AbsCollValueMap<K, V, C extends Collection<V>> extends MapWrapper<K, C> {
    protected static final int DEFAULT_COLLECTION_INITIAL_CAPACITY = 3;
    private static final long serialVersionUID = 1;

    public AbsCollValueMap() {
        this(16);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putAllValues$1(final Object obj, Collection collection) {
        if (collection != null) {
            collection.forEach(new Consumer() { // from class: cn.hutool.core.map.multi.b
                @Override // java.util.function.Consumer
                public final void accept(Object obj2) {
                    this.f2537c.lambda$null$0(obj, obj2);
                }
            });
        }
    }

    public abstract C createCollection();

    public V get(K k2, int i2) {
        return (V) CollUtil.get((Collection) get(k2), i2);
    }

    public void putAllValues(Map<? extends K, ? extends Collection<V>> map) {
        if (map != null) {
            map.forEach(new BiConsumer() { // from class: cn.hutool.core.map.multi.a
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f2536c.lambda$putAllValues$1(obj, (Collection) obj2);
                }
            });
        }
    }

    /* renamed from: putValue, reason: merged with bridge method [inline-methods] */
    public void lambda$null$0(K k2, V v2) {
        Collection collectionCreateCollection = (Collection) get(k2);
        if (collectionCreateCollection == null) {
            collectionCreateCollection = createCollection();
            put(k2, collectionCreateCollection);
        }
        collectionCreateCollection.add(v2);
    }

    public boolean removeValue(K k2, V v2) {
        Collection collection = (Collection) get(k2);
        return collection != null && collection.remove(v2);
    }

    public boolean removeValues(K k2, Collection<V> collection) {
        Collection collection2 = (Collection) get(k2);
        return collection2 != null && collection2.removeAll(collection);
    }

    public AbsCollValueMap(int i2) {
        this(i2, 0.75f);
    }

    public AbsCollValueMap(Map<? extends K, C> map) {
        this(0.75f, map);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public AbsCollValueMap(float f2, Map<? extends K, C> map) {
        this(map.size(), f2);
        putAll(map);
    }

    public AbsCollValueMap(int i2, float f2) {
        super(new HashMap(i2, f2));
    }
}
