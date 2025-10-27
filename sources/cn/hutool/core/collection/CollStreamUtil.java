package cn.hutool.core.collection;

import cn.hutool.core.lang.Opt;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.stream.CollectorUtil;
import cn.hutool.core.stream.StreamUtil;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/* loaded from: classes.dex */
public class CollStreamUtil {
    public static <E, T, U> Map<T, Map<U, E>> group2Map(Collection<E> collection, Function<E, T> function, Function<E, U> function2) {
        return group2Map(collection, function, function2, false);
    }

    public static <E, K, D> Map<K, D> groupBy(Collection<E> collection, Function<E, K> function, Collector<E, ?, D> collector) {
        return CollUtil.isEmpty((Collection<?>) collection) ? MapUtil.newHashMap(0) : groupBy(collection, function, collector, false);
    }

    public static <E, K, U> Map<K, Map<U, List<E>>> groupBy2Key(Collection<E> collection, Function<E, K> function, Function<E, U> function2) {
        return groupBy2Key(collection, function, function2, false);
    }

    public static <E, K> Map<K, List<E>> groupByKey(Collection<E> collection, Function<E, K> function) {
        return groupByKey(collection, function, false);
    }

    public static <E, K, V> Map<K, List<V>> groupKeyValue(Collection<E> collection, Function<E, K> function, Function<E, V> function2) {
        return groupKeyValue(collection, function, function2, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$group2Map$2(Object obj, Object obj2) {
        return obj;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$groupKeyValue$3(Function function, Object obj) {
        return Opt.ofNullable(obj).map(function).orElse(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$toIdentityMap$0(Function function, Object obj) {
        return Opt.ofNullable(obj).map(function).get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$toMap$1(Function function, Function function2, HashMap map, Object obj) {
        map.put(function.apply(obj), function2.apply(obj));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <K, X, Y, V> Map<K, V> merge(Map<K, X> map, Map<K, Y> map2, BiFunction<X, Y, V> biFunction) {
        if (MapUtil.isEmpty(map) && MapUtil.isEmpty(map2)) {
            return MapUtil.newHashMap(0);
        }
        if (MapUtil.isEmpty(map)) {
            map = MapUtil.newHashMap(0);
        } else if (MapUtil.isEmpty(map2)) {
            map2 = MapUtil.newHashMap(0);
        }
        HashSet hashSet = new HashSet();
        hashSet.addAll(map.keySet());
        hashSet.addAll(map2.keySet());
        HashMap mapNewHashMap = MapUtil.newHashMap(hashSet.size());
        for (Object obj : hashSet) {
            Object objApply = biFunction.apply(map.get(obj), map2.get(obj));
            if (objApply != null) {
                mapNewHashMap.put(obj, objApply);
            }
        }
        return mapNewHashMap;
    }

    public static <V, K> Map<K, V> toIdentityMap(Collection<V> collection, Function<V, K> function) {
        return toIdentityMap(collection, function, false);
    }

    public static <E, T> List<T> toList(Collection<E> collection, Function<E, T> function) {
        return toList(collection, function, false);
    }

    public static <E, K, V> Map<K, V> toMap(Collection<E> collection, Function<E, K> function, Function<E, V> function2) {
        return toMap(collection, function, function2, false);
    }

    public static <E, T> Set<T> toSet(Collection<E> collection, Function<E, T> function) {
        return toSet(collection, function, false);
    }

    public static <E, T, U> Map<T, Map<U, E>> group2Map(Collection<E> collection, Function<E, T> function, Function<E, U> function2, boolean z2) {
        return (CollUtil.isEmpty((Collection<?>) collection) || function == null || function2 == null) ? MapUtil.newHashMap(0) : groupBy(collection, function, CollectorUtil.toMap(function2, Function.identity(), new BinaryOperator() { // from class: cn.hutool.core.collection.f
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return CollStreamUtil.lambda$group2Map$2(obj, obj2);
            }
        }), z2);
    }

    public static <E, K, U> Map<K, Map<U, List<E>>> groupBy2Key(Collection<E> collection, Function<E, K> function, Function<E, U> function2, boolean z2) {
        return CollUtil.isEmpty((Collection<?>) collection) ? MapUtil.newHashMap(0) : groupBy(collection, function, CollectorUtil.groupingBy(function2, Collectors.toList()), z2);
    }

    public static <E, K> Map<K, List<E>> groupByKey(Collection<E> collection, Function<E, K> function, boolean z2) {
        return CollUtil.isEmpty((Collection<?>) collection) ? MapUtil.newHashMap(0) : groupBy(collection, function, Collectors.toList(), z2);
    }

    public static <E, K, V> Map<K, List<V>> groupKeyValue(Collection<E> collection, Function<E, K> function, final Function<E, V> function2, boolean z2) {
        return CollUtil.isEmpty((Collection<?>) collection) ? MapUtil.newHashMap(0) : groupBy(collection, function, Collectors.mapping(new Function() { // from class: cn.hutool.core.collection.h
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CollStreamUtil.lambda$groupKeyValue$3(function2, obj);
            }
        }, Collectors.toList()), z2);
    }

    public static <V, K> Map<K, V> toIdentityMap(Collection<V> collection, final Function<V, K> function, boolean z2) {
        return CollUtil.isEmpty((Collection<?>) collection) ? MapUtil.newHashMap(0) : toMap(collection, new Function() { // from class: cn.hutool.core.collection.i
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CollStreamUtil.lambda$toIdentityMap$0(function, obj);
            }
        }, Function.identity(), z2);
    }

    public static <E, T> List<T> toList(Collection<E> collection, Function<E, T> function, boolean z2) {
        return CollUtil.isEmpty((Collection<?>) collection) ? CollUtil.newArrayList(new Object[0]) : (List) StreamUtil.of(collection, z2).map(function).filter(new Predicate() { // from class: cn.hutool.core.collection.g
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return cn.hutool.core.annotation.g0.a(obj);
            }
        }).collect(Collectors.toList());
    }

    public static <E, K, V> Map<K, V> toMap(Collection<E> collection, final Function<E, K> function, final Function<E, V> function2, boolean z2) {
        return CollUtil.isEmpty((Collection<?>) collection) ? MapUtil.newHashMap(0) : (Map) StreamUtil.of(collection, z2).collect(new k(), new BiConsumer() { // from class: cn.hutool.core.collection.l
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CollStreamUtil.lambda$toMap$1(function, function2, (HashMap) obj, obj2);
            }
        }, new BiConsumer() { // from class: cn.hutool.core.collection.m
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((HashMap) obj).putAll((HashMap) obj2);
            }
        });
    }

    public static <E, T> Set<T> toSet(Collection<E> collection, Function<E, T> function, boolean z2) {
        return CollUtil.isEmpty((Collection<?>) collection) ? CollUtil.newHashSet(new Object[0]) : (Set) StreamUtil.of(collection, z2).map(function).filter(new Predicate() { // from class: cn.hutool.core.collection.j
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return cn.hutool.core.annotation.g0.a(obj);
            }
        }).collect(Collectors.toSet());
    }

    public static <E, K, D> Map<K, D> groupBy(Collection<E> collection, Function<E, K> function, Collector<E, ?, D> collector, boolean z2) {
        if (CollUtil.isEmpty((Collection<?>) collection)) {
            return MapUtil.newHashMap(0);
        }
        return (Map) StreamUtil.of(collection, z2).collect(CollectorUtil.groupingBy(function, collector));
    }
}
