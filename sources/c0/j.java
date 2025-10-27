package c0;

import cn.hutool.core.lang.func.Supplier3;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public final /* synthetic */ class j<T, P1, P2, P3> {
    public static Supplier a(final Supplier3 supplier3, final Object obj, final Object obj2, final Object obj3) {
        return new Supplier() { // from class: c0.i
            @Override // java.util.function.Supplier
            public final Object get() {
                return supplier3.get(obj, obj2, obj3);
            }
        };
    }
}
