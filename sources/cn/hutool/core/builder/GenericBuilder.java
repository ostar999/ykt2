package cn.hutool.core.builder;

import cn.hutool.core.lang.func.Consumer3;
import cn.hutool.core.lang.func.Supplier1;
import cn.hutool.core.lang.func.Supplier2;
import cn.hutool.core.lang.func.Supplier3;
import cn.hutool.core.lang.func.Supplier4;
import cn.hutool.core.lang.func.Supplier5;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class GenericBuilder<T> implements Builder<T> {
    private static final long serialVersionUID = 1;
    private final Supplier<T> instant;
    private final List<Consumer<T>> modifiers = new ArrayList();

    public GenericBuilder(Supplier<T> supplier) {
        this.instant = supplier;
    }

    public static <T> GenericBuilder<T> of(Supplier<T> supplier) {
        return new GenericBuilder<>(supplier);
    }

    @Override // cn.hutool.core.builder.Builder
    public T build() {
        final T t2 = (T) this.instant.get();
        this.modifiers.forEach(new Consumer() { // from class: cn.hutool.core.builder.c
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                a.a((Consumer) obj, t2);
            }
        });
        this.modifiers.clear();
        return t2;
    }

    public GenericBuilder<T> with(Consumer<T> consumer) {
        this.modifiers.add(consumer);
        return this;
    }

    public static <T, P1> GenericBuilder<T> of(Supplier1<T, P1> supplier1, P1 p12) {
        return of(supplier1.toSupplier(p12));
    }

    public <P1> GenericBuilder<T> with(final BiConsumer<T, P1> biConsumer, final P1 p12) {
        this.modifiers.add(new Consumer() { // from class: cn.hutool.core.builder.b
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                r.b.a(biConsumer, obj, p12);
            }
        });
        return this;
    }

    public static <T, P1, P2> GenericBuilder<T> of(Supplier2<T, P1, P2> supplier2, P1 p12, P2 p2) {
        return of(supplier2.toSupplier(p12, p2));
    }

    public <P1, P2> GenericBuilder<T> with(final Consumer3<T, P1, P2> consumer3, final P1 p12, final P2 p2) {
        this.modifiers.add(new Consumer() { // from class: cn.hutool.core.builder.d
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                consumer3.accept(obj, p12, p2);
            }
        });
        return this;
    }

    public static <T, P1, P2, P3> GenericBuilder<T> of(Supplier3<T, P1, P2, P3> supplier3, P1 p12, P2 p2, P3 p3) {
        return of(supplier3.toSupplier(p12, p2, p3));
    }

    public static <T, P1, P2, P3, P4> GenericBuilder<T> of(Supplier4<T, P1, P2, P3, P4> supplier4, P1 p12, P2 p2, P3 p3, P4 p4) {
        return of(supplier4.toSupplier(p12, p2, p3, p4));
    }

    public static <T, P1, P2, P3, P4, P5> GenericBuilder<T> of(Supplier5<T, P1, P2, P3, P4, P5> supplier5, P1 p12, P2 p2, P3 p3, P4 p4, P5 p5) {
        return of(supplier5.toSupplier(p12, p2, p3, p4, p5));
    }
}
