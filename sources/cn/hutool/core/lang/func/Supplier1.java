package cn.hutool.core.lang.func;

import java.util.function.Supplier;

@FunctionalInterface
/* loaded from: classes.dex */
public interface Supplier1<T, P1> {
    T get(P1 p12);

    Supplier<T> toSupplier(P1 p12);
}
