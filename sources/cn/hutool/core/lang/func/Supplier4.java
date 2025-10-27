package cn.hutool.core.lang.func;

import java.util.function.Supplier;

@FunctionalInterface
/* loaded from: classes.dex */
public interface Supplier4<T, P1, P2, P3, P4> {
    T get(P1 p12, P2 p2, P3 p3, P4 p4);

    Supplier<T> toSupplier(P1 p12, P2 p2, P3 p3, P4 p4);
}
