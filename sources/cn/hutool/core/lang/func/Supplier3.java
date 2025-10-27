package cn.hutool.core.lang.func;

import java.util.function.Supplier;

@FunctionalInterface
/* loaded from: classes.dex */
public interface Supplier3<T, P1, P2, P3> {
    T get(P1 p12, P2 p2, P3 p3);

    Supplier<T> toSupplier(P1 p12, P2 p2, P3 p3);
}
