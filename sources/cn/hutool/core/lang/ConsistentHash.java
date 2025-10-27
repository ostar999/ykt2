package cn.hutool.core.lang;

import cn.hutool.core.lang.hash.Hash32;
import cn.hutool.core.util.HashUtil;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

/* loaded from: classes.dex */
public class ConsistentHash<T> implements Serializable {
    private static final long serialVersionUID = 1;
    private final SortedMap<Integer, T> circle;
    Hash32<Object> hashFunc;
    private final int numberOfReplicas;

    public ConsistentHash(int i2, Collection<T> collection) {
        this.circle = new TreeMap();
        this.numberOfReplicas = i2;
        this.hashFunc = new Hash32() { // from class: cn.hutool.core.lang.u
            @Override // cn.hutool.core.lang.hash.Hash32, cn.hutool.core.lang.hash.Hash
            public /* synthetic */ Number hash(Object obj) {
                return d0.b.a(this, obj);
            }

            @Override // cn.hutool.core.lang.hash.Hash32
            public final int hash32(Object obj) {
                return ConsistentHash.lambda$new$0(obj);
            }
        };
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            add(it.next());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$new$0(Object obj) {
        return HashUtil.fnvHash(obj.toString());
    }

    public void add(T t2) {
        for (int i2 = 0; i2 < this.numberOfReplicas; i2++) {
            this.circle.put(Integer.valueOf(this.hashFunc.hash32(t2.toString() + i2)), t2);
        }
    }

    public T get(Object obj) {
        if (this.circle.isEmpty()) {
            return null;
        }
        int iHash32 = this.hashFunc.hash32(obj);
        if (!this.circle.containsKey(Integer.valueOf(iHash32))) {
            SortedMap<Integer, T> sortedMapTailMap = this.circle.tailMap(Integer.valueOf(iHash32));
            if (sortedMapTailMap.isEmpty()) {
                sortedMapTailMap = this.circle;
            }
            iHash32 = sortedMapTailMap.firstKey().intValue();
        }
        return this.circle.get(Integer.valueOf(iHash32));
    }

    public void remove(T t2) {
        for (int i2 = 0; i2 < this.numberOfReplicas; i2++) {
            this.circle.remove(Integer.valueOf(this.hashFunc.hash32(t2.toString() + i2)));
        }
    }

    public ConsistentHash(Hash32<Object> hash32, int i2, Collection<T> collection) {
        this.circle = new TreeMap();
        this.numberOfReplicas = i2;
        this.hashFunc = hash32;
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            add(it.next());
        }
    }
}
