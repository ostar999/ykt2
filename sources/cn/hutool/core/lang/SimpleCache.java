package cn.hutool.core.lang;

import cn.hutool.core.collection.TransIter;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.lang.func.Func0;
import cn.hutool.core.lang.mutable.Mutable;
import cn.hutool.core.lang.mutable.MutableObj;
import cn.hutool.core.map.SafeConcurrentHashMap;
import cn.hutool.core.map.WeakConcurrentMap;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class SimpleCache<K, V> implements Iterable<Map.Entry<K, V>>, Serializable {
    private static final long serialVersionUID = 1;
    protected final Map<K, Lock> keyLockMap;
    private final ReadWriteLock lock;
    private final Map<Mutable<K>, V> rawMap;

    public SimpleCache() {
        this(new WeakConcurrentMap());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Lock lambda$get$0(Object obj) {
        return new ReentrantLock();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Map.Entry lambda$iterator$1(final Map.Entry entry) {
        return new Map.Entry<K, V>() { // from class: cn.hutool.core.lang.SimpleCache.1
            @Override // java.util.Map.Entry
            public K getKey() {
                return (K) ((Mutable) entry.getKey()).get2();
            }

            @Override // java.util.Map.Entry
            public V getValue() {
                return (V) entry.getValue();
            }

            @Override // java.util.Map.Entry
            public V setValue(V v2) {
                return (V) entry.setValue(v2);
            }
        };
    }

    public void clear() {
        this.lock.writeLock().lock();
        try {
            this.rawMap.clear();
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public V get(K k2) {
        this.lock.readLock().lock();
        try {
            return this.rawMap.get(MutableObj.of(k2));
        } finally {
            this.lock.readLock().unlock();
        }
    }

    @Override // java.lang.Iterable
    public Iterator<Map.Entry<K, V>> iterator() {
        return new TransIter(this.rawMap.entrySet().iterator(), new Function() { // from class: cn.hutool.core.lang.j0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f2498c.lambda$iterator$1((Map.Entry) obj);
            }
        });
    }

    public V put(K k2, V v2) {
        this.lock.writeLock().lock();
        try {
            this.rawMap.put(MutableObj.of(k2), v2);
            return v2;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public V remove(K k2) {
        this.lock.writeLock().lock();
        try {
            return this.rawMap.remove(MutableObj.of(k2));
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public SimpleCache(Map<Mutable<K>, V> map) {
        this.lock = new ReentrantReadWriteLock();
        this.keyLockMap = new SafeConcurrentHashMap();
        this.rawMap = map;
    }

    public V get(K k2, Func0<V> func0) {
        return get(k2, null, func0);
    }

    public V get(K k2, Predicate<V> predicate, Func0<V> func0) {
        V vCall;
        V v2 = get(k2);
        if (predicate != null && v2 != null && !predicate.test(v2)) {
            v2 = null;
        }
        if (v2 != null || func0 == null) {
            return v2;
        }
        Lock lock = (Lock) this.keyLockMap.computeIfAbsent(k2, new Function() { // from class: cn.hutool.core.lang.i0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SimpleCache.lambda$get$0(obj);
            }
        });
        lock.lock();
        try {
            V v3 = get(k2);
            if (v3 == null || !(predicate == null || predicate.test(v3))) {
                try {
                    vCall = func0.call();
                    put(k2, vCall);
                } catch (Exception e2) {
                    throw ExceptionUtil.wrapRuntime(e2);
                }
            } else {
                vCall = v3;
            }
            lock.unlock();
            this.keyLockMap.remove(k2);
            return vCall;
        } catch (Throwable th) {
            lock.unlock();
            this.keyLockMap.remove(k2);
            throw th;
        }
    }
}
