package cn.hutool.core.stream;

import cn.hutool.core.collection.k;
import cn.hutool.core.lang.Opt;
import cn.hutool.core.stream.CollectorUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/* loaded from: classes.dex */
public class CollectorUtil {
    public static final Set<Collector.Characteristics> CH_ID = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
    public static final Set<Collector.Characteristics> CH_NOID = Collections.emptySet();

    public static <T, K, D, A, M extends Map<K, D>> Collector<T, ?, M> groupingBy(final Function<? super T, ? extends K> function, Supplier<M> supplier, Collector<? super T, A, D> collector) {
        final Supplier supplier2 = collector.supplier();
        final BiConsumer biConsumerAccumulator = collector.accumulator();
        BiConsumer biConsumer = new BiConsumer() { // from class: k0.z
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CollectorUtil.lambda$groupingBy$3(function, supplier2, biConsumerAccumulator, (Map) obj, obj2);
            }
        };
        BinaryOperator binaryOperatorMapMerger = mapMerger(collector.combiner());
        if (collector.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH)) {
            return new SimpleCollector(supplier, biConsumer, binaryOperatorMapMerger, CH_ID);
        }
        final Function functionFinisher = collector.finisher();
        return new SimpleCollector(supplier, biConsumer, binaryOperatorMapMerger, new Function() { // from class: k0.a0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CollectorUtil.lambda$groupingBy$5(functionFinisher, (Map) obj);
            }
        }, CH_NOID);
    }

    public static <T> Collector<T, ?, String> joining(CharSequence charSequence) {
        return joining(charSequence, new Function() { // from class: k0.s
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return obj.toString();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$groupingBy$3(Function function, final Supplier supplier, BiConsumer biConsumer, Map map, Object obj) {
        biConsumer.accept(map.computeIfAbsent(Opt.ofNullable(obj).map(function).orElse(null), new Function() { // from class: k0.b0
            @Override // java.util.function.Function
            public final Object apply(Object obj2) {
                return CollectorUtil.lambda$null$2(supplier, obj2);
            }
        }), obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Map lambda$groupingBy$5(final Function function, Map map) {
        map.replaceAll(new BiFunction() { // from class: k0.q
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return CollectorUtil.lambda$null$4(function, obj, obj2);
            }
        });
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ StringJoiner lambda$joining$0(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        return new StringJoiner(charSequence, charSequence2, charSequence3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$joining$1(Function function, StringJoiner stringJoiner, Object obj) {
        stringJoiner.add((CharSequence) function.apply(obj));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Map lambda$mapMerger$7(BinaryOperator binaryOperator, Map map, Map map2) {
        for (Map.Entry entry : map2.entrySet()) {
            map.merge(entry.getKey(), entry.getValue(), binaryOperator);
        }
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List lambda$null$11(Object obj) {
        return new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$null$12(Map map, Object obj, List list) {
        ((List) map.computeIfAbsent(obj, new Function() { // from class: k0.u
            @Override // java.util.function.Function
            public final Object apply(Object obj2) {
                return CollectorUtil.lambda$null$11(obj2);
            }
        })).addAll(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$null$2(Supplier supplier, Object obj) {
        return supplier.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$null$4(Function function, Object obj, Object obj2) {
        return function.apply(obj2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List lambda$null$8(Object obj) {
        return new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$null$9(Map map, Object obj, Object obj2) {
        ((List) map.computeIfAbsent(obj, new Function() { // from class: k0.p
            @Override // java.util.function.Function
            public final Object apply(Object obj3) {
                return CollectorUtil.lambda$null$8(obj3);
            }
        })).add(obj2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Map lambda$reduceListMap$10(Supplier supplier, Map map) {
        final Map map2 = (Map) supplier.get();
        map.forEach(new BiConsumer() { // from class: k0.w
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CollectorUtil.lambda$null$9(map2, obj, obj2);
            }
        });
        return map2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Map lambda$reduceListMap$13(final Map map, Map map2) {
        map2.forEach(new BiConsumer() { // from class: k0.r
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CollectorUtil.lambda$null$12(map, obj, (List) obj2);
            }
        });
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$toMap$6(Function function, Function function2, Map map, Object obj) {
        map.put(Opt.ofNullable(obj).map(function).get(), Opt.ofNullable(obj).map(function2).get());
    }

    public static <K, V, M extends Map<K, V>> BinaryOperator<M> mapMerger(final BinaryOperator<V> binaryOperator) {
        return new BinaryOperator() { // from class: k0.c0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return CollectorUtil.lambda$mapMerger$7(binaryOperator, (Map) obj, (Map) obj2);
            }
        };
    }

    public static <K, V> Collector<Map<K, V>, ?, Map<K, List<V>>> reduceListMap() {
        return reduceListMap(new k());
    }

    public static <T, K, U> Collector<T, ?, Map<K, U>> toMap(Function<? super T, ? extends K> function, Function<? super T, ? extends U> function2, BinaryOperator<U> binaryOperator) {
        return toMap(function, function2, binaryOperator, new k());
    }

    public static <T> Collector<T, ?, String> joining(CharSequence charSequence, Function<T, ? extends CharSequence> function) {
        return joining(charSequence, "", "", function);
    }

    public static <K, V, R extends Map<K, List<V>>> Collector<Map<K, V>, ?, R> reduceListMap(final Supplier<R> supplier) {
        return Collectors.reducing(supplier.get(), new Function() { // from class: k0.x
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CollectorUtil.lambda$reduceListMap$10(supplier, (Map) obj);
            }
        }, new BinaryOperator() { // from class: k0.y
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return CollectorUtil.lambda$reduceListMap$13((Map) obj, (Map) obj2);
            }
        });
    }

    public static <T, K, U, M extends Map<K, U>> Collector<T, ?, M> toMap(final Function<? super T, ? extends K> function, final Function<? super T, ? extends U> function2, BinaryOperator<U> binaryOperator, Supplier<M> supplier) {
        return new SimpleCollector(supplier, new BiConsumer() { // from class: k0.t
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CollectorUtil.lambda$toMap$6(function, function2, (Map) obj, obj2);
            }
        }, mapMerger(binaryOperator), CH_ID);
    }

    public static <T> Collector<T, ?, String> joining(final CharSequence charSequence, final CharSequence charSequence2, final CharSequence charSequence3, final Function<T, ? extends CharSequence> function) {
        return new SimpleCollector(new Supplier() { // from class: k0.l
            @Override // java.util.function.Supplier
            public final Object get() {
                return CollectorUtil.lambda$joining$0(charSequence, charSequence2, charSequence3);
            }
        }, new BiConsumer() { // from class: k0.m
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CollectorUtil.lambda$joining$1(function, (StringJoiner) obj, obj2);
            }
        }, new BinaryOperator() { // from class: k0.n
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return ((StringJoiner) obj).merge((StringJoiner) obj2);
            }
        }, new Function() { // from class: k0.o
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((StringJoiner) obj).toString();
            }
        }, Collections.emptySet());
    }

    public static <T, K, A, D> Collector<T, ?, Map<K, D>> groupingBy(Function<? super T, ? extends K> function, Collector<? super T, A, D> collector) {
        return groupingBy(function, new k(), collector);
    }

    public static <T, K> Collector<T, ?, Map<K, List<T>>> groupingBy(Function<? super T, ? extends K> function) {
        return groupingBy(function, Collectors.toList());
    }

    public static <T, K, R, C extends Collection<R>, M extends Map<K, C>> Collector<T, ?, M> groupingBy(Function<? super T, ? extends K> function, Function<? super T, ? extends R> function2, Supplier<C> supplier, Supplier<M> supplier2) {
        return groupingBy(function, supplier2, Collectors.mapping(function2, Collectors.toCollection(supplier)));
    }

    public static <T, K, R, C extends Collection<R>> Collector<T, ?, Map<K, C>> groupingBy(Function<? super T, ? extends K> function, Function<? super T, ? extends R> function2, Supplier<C> supplier) {
        return groupingBy(function, function2, supplier, new k());
    }

    public static <T, K, R> Collector<T, ?, Map<K, List<R>>> groupingBy(Function<? super T, ? extends K> function, Function<? super T, ? extends R> function2) {
        return groupingBy(function, function2, new Supplier() { // from class: k0.v
            @Override // java.util.function.Supplier
            public final Object get() {
                return new ArrayList();
            }
        }, new k());
    }
}
