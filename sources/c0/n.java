package c0;

import cn.hutool.core.lang.func.Supplier5;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public final /* synthetic */ class n<T, P1, P2, P3, P4, P5> {
    public static Supplier a(final Supplier5 supplier5, final Object obj, final Object obj2, final Object obj3, final Object obj4, final Object obj5) {
        return new Supplier() { // from class: c0.m
            @Override // java.util.function.Supplier
            public final Object get() {
                return supplier5.get(obj, obj2, obj3, obj4, obj5);
            }
        };
    }
}
