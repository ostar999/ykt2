package cn.hutool.core.map;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.func.Func0;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReferenceUtil;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/* loaded from: classes.dex */
public class ReferenceConcurrentMap<K, V> implements ConcurrentMap<K, V>, Iterable<Map.Entry<K, V>>, Serializable {
    private final ReferenceUtil.ReferenceType keyType;
    private final ReferenceQueue<K> lastQueue = new ReferenceQueue<>();
    private BiConsumer<Reference<? extends K>, V> purgeListener;
    final ConcurrentMap<Reference<K>, V> raw;

    /* renamed from: cn.hutool.core.map.ReferenceConcurrentMap$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$cn$hutool$core$util$ReferenceUtil$ReferenceType;

        static {
            int[] iArr = new int[ReferenceUtil.ReferenceType.values().length];
            $SwitchMap$cn$hutool$core$util$ReferenceUtil$ReferenceType = iArr;
            try {
                iArr[ReferenceUtil.ReferenceType.WEAK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$cn$hutool$core$util$ReferenceUtil$ReferenceType[ReferenceUtil.ReferenceType.SOFT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static class SoftKey<K> extends SoftReference<K> {
        private final int hashCode;

        public SoftKey(K k2, ReferenceQueue<? super K> referenceQueue) {
            super(k2, referenceQueue);
            this.hashCode = k2.hashCode();
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof SoftKey) {
                return ObjectUtil.equals(((SoftKey) obj).get(), get());
            }
            return false;
        }

        public int hashCode() {
            return this.hashCode;
        }
    }

    public static class WeakKey<K> extends WeakReference<K> {
        private final int hashCode;

        public WeakKey(K k2, ReferenceQueue<? super K> referenceQueue) {
            super(k2, referenceQueue);
            this.hashCode = k2.hashCode();
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof WeakKey) {
                return ObjectUtil.equals(((WeakKey) obj).get(), get());
            }
            return false;
        }

        public int hashCode() {
            return this.hashCode;
        }
    }

    public ReferenceConcurrentMap(ConcurrentMap<Reference<K>, V> concurrentMap, ReferenceUtil.ReferenceType referenceType) {
        this.raw = concurrentMap;
        this.keyType = referenceType;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$compute$7(BiFunction biFunction, Object obj, Reference reference, Object obj2) {
        return biFunction.apply(obj, obj2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$computeIfAbsent$1(Function function, Object obj, Reference reference) {
        return function.apply(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$computeIfAbsent$3(Func0 func0, Object obj) {
        return func0.callWithRuntimeException();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$computeIfPresent$2(BiFunction biFunction, Object obj, Reference reference, Object obj2) {
        return biFunction.apply(obj, obj2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractMap.SimpleImmutableEntry lambda$entrySet$5(Map.Entry entry) {
        return new AbstractMap.SimpleImmutableEntry(((Reference) entry.getKey()).get(), entry.getValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$forEach$6(BiConsumer biConsumer, Reference reference, Object obj) {
        biConsumer.accept(reference.get(), obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$keySet$4(Reference reference) {
        if (reference == null) {
            return null;
        }
        return reference.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$replaceAll$0(BiFunction biFunction, Reference reference, Object obj) {
        return biFunction.apply(reference.get(), obj);
    }

    private Reference<K> ofKey(K k2, ReferenceQueue<? super K> referenceQueue) {
        int i2 = AnonymousClass1.$SwitchMap$cn$hutool$core$util$ReferenceUtil$ReferenceType[this.keyType.ordinal()];
        if (i2 == 1) {
            return new WeakKey(k2, referenceQueue);
        }
        if (i2 == 2) {
            return new SoftKey(k2, referenceQueue);
        }
        throw new IllegalArgumentException("Unsupported key type: " + this.keyType);
    }

    private void purgeStaleKeys() {
        while (true) {
            Reference<? extends K> referencePoll = this.lastQueue.poll();
            if (referencePoll == null) {
                return;
            }
            V vRemove = this.raw.remove(referencePoll);
            BiConsumer<Reference<? extends K>, V> biConsumer = this.purgeListener;
            if (biConsumer != null) {
                biConsumer.accept(referencePoll, vRemove);
            }
        }
    }

    @Override // java.util.Map
    public void clear() {
        this.raw.clear();
        while (this.lastQueue.poll() != null) {
        }
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public V compute(final K k2, final BiFunction<? super K, ? super V, ? extends V> biFunction) {
        purgeStaleKeys();
        return (V) this.raw.compute(ofKey(k2, this.lastQueue), new BiFunction() { // from class: cn.hutool.core.map.s1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return ReferenceConcurrentMap.lambda$compute$7(biFunction, k2, (Reference) obj, obj2);
            }
        });
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public V computeIfAbsent(final K k2, final Function<? super K, ? extends V> function) {
        purgeStaleKeys();
        return (V) this.raw.computeIfAbsent(ofKey(k2, this.lastQueue), new Function() { // from class: cn.hutool.core.map.u1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ReferenceConcurrentMap.lambda$computeIfAbsent$1(function, k2, (Reference) obj);
            }
        });
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public V computeIfPresent(final K k2, final BiFunction<? super K, ? super V, ? extends V> biFunction) {
        purgeStaleKeys();
        return (V) this.raw.computeIfPresent(ofKey(k2, this.lastQueue), new BiFunction() { // from class: cn.hutool.core.map.p1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return ReferenceConcurrentMap.lambda$computeIfPresent$2(biFunction, k2, (Reference) obj, obj2);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    public boolean containsKey(Object obj) {
        purgeStaleKeys();
        return this.raw.containsKey(ofKey(obj, null));
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        purgeStaleKeys();
        return this.raw.containsValue(obj);
    }

    @Override // java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        purgeStaleKeys();
        return (Set) this.raw.entrySet().stream().map(new Function() { // from class: cn.hutool.core.map.m1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ReferenceConcurrentMap.lambda$entrySet$5((Map.Entry) obj);
            }
        }).collect(Collectors.toSet());
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public void forEach(final BiConsumer<? super K, ? super V> biConsumer) {
        purgeStaleKeys();
        this.raw.forEach(new BiConsumer() { // from class: cn.hutool.core.map.r1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ReferenceConcurrentMap.lambda$forEach$6(biConsumer, (Reference) obj, obj2);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    public V get(Object obj) {
        purgeStaleKeys();
        return this.raw.get(ofKey(obj, null));
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override // java.lang.Iterable
    public Iterator<Map.Entry<K, V>> iterator() {
        return entrySet().iterator();
    }

    @Override // java.util.Map
    public Set<K> keySet() {
        return new HashSet(CollUtil.trans(this.raw.keySet(), new Function() { // from class: cn.hutool.core.map.o1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ReferenceConcurrentMap.lambda$keySet$4((Reference) obj);
            }
        }));
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public V merge(K k2, V v2, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        purgeStaleKeys();
        return (V) this.raw.merge(ofKey(k2, this.lastQueue), v2, biFunction);
    }

    @Override // java.util.Map
    public V put(K k2, V v2) {
        purgeStaleKeys();
        return this.raw.put(ofKey(k2, this.lastQueue), v2);
    }

    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        map.forEach(new BiConsumer() { // from class: cn.hutool.core.map.t1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f2560c.put(obj, obj2);
            }
        });
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public V putIfAbsent(K k2, V v2) {
        purgeStaleKeys();
        return this.raw.putIfAbsent(ofKey(k2, this.lastQueue), v2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    public V remove(Object obj) {
        purgeStaleKeys();
        return this.raw.remove(ofKey(obj, null));
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public V replace(K k2, V v2) {
        purgeStaleKeys();
        return this.raw.replace(ofKey(k2, this.lastQueue), v2);
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public void replaceAll(final BiFunction<? super K, ? super V, ? extends V> biFunction) {
        purgeStaleKeys();
        this.raw.replaceAll(new BiFunction() { // from class: cn.hutool.core.map.n1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return ReferenceConcurrentMap.lambda$replaceAll$0(biFunction, (Reference) obj, obj2);
            }
        });
    }

    public void setPurgeListener(BiConsumer<Reference<? extends K>, V> biConsumer) {
        this.purgeListener = biConsumer;
    }

    @Override // java.util.Map
    public int size() {
        purgeStaleKeys();
        return this.raw.size();
    }

    @Override // java.util.Map
    public Collection<V> values() {
        purgeStaleKeys();
        return this.raw.values();
    }

    public V computeIfAbsent(K k2, final Func0<? extends V> func0) {
        return computeIfAbsent((ReferenceConcurrentMap<K, V>) k2, (Function<? super ReferenceConcurrentMap<K, V>, ? extends V>) new Function() { // from class: cn.hutool.core.map.q1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ReferenceConcurrentMap.lambda$computeIfAbsent$3(func0, obj);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public boolean remove(Object obj, Object obj2) {
        purgeStaleKeys();
        return this.raw.remove(ofKey(obj, null), obj2);
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public boolean replace(K k2, V v2, V v3) {
        purgeStaleKeys();
        return this.raw.replace(ofKey(k2, this.lastQueue), v2, v3);
    }
}
