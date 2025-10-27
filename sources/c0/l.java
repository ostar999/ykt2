package c0;

import cn.hutool.core.lang.func.Supplier4;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public final /* synthetic */ class l<T, P1, P2, P3, P4> {
    public static Supplier a(final Supplier4 supplier4, final Object obj, final Object obj2, final Object obj3, final Object obj4) {
        return new Supplier() { // from class: c0.k
            @Override // java.util.function.Supplier
            public final Object get() {
                return supplier4.get(obj, obj2, obj3, obj4);
            }
        };
    }
}
