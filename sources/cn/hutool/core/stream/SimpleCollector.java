package cn.hutool.core.stream;

import cn.hutool.core.stream.SimpleCollector;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/* loaded from: classes.dex */
public class SimpleCollector<T, A, R> implements Collector<T, A, R> {
    private final BiConsumer<A, T> accumulator;
    private final Set<Collector.Characteristics> characteristics;
    private final BinaryOperator<A> combiner;
    private final Function<A, R> finisher;
    private final Supplier<A> supplier;

    public SimpleCollector(Supplier<A> supplier, BiConsumer<A, T> biConsumer, BinaryOperator<A> binaryOperator, Function<A, R> function, Set<Collector.Characteristics> set) {
        this.supplier = supplier;
        this.accumulator = biConsumer;
        this.combiner = binaryOperator;
        this.finisher = function;
        this.characteristics = set;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$new$0(Object obj) {
        return obj;
    }

    @Override // java.util.stream.Collector
    public BiConsumer<A, T> accumulator() {
        return this.accumulator;
    }

    @Override // java.util.stream.Collector
    public Set<Collector.Characteristics> characteristics() {
        return this.characteristics;
    }

    @Override // java.util.stream.Collector
    public BinaryOperator<A> combiner() {
        return this.combiner;
    }

    @Override // java.util.stream.Collector
    public Function<A, R> finisher() {
        return this.finisher;
    }

    @Override // java.util.stream.Collector
    public Supplier<A> supplier() {
        return this.supplier;
    }

    public SimpleCollector(Supplier<A> supplier, BiConsumer<A, T> biConsumer, BinaryOperator<A> binaryOperator, Set<Collector.Characteristics> set) {
        this(supplier, biConsumer, binaryOperator, new Function() { // from class: k0.d0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SimpleCollector.lambda$new$0(obj);
            }
        }, set);
    }
}
