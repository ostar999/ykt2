package c0;

import cn.hutool.core.lang.func.Supplier2;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public final /* synthetic */ class h<T, P1, P2> {
    public static Supplier a(final Supplier2 supplier2, final Object obj, final Object obj2) {
        return new Supplier() { // from class: c0.g
            @Override // java.util.function.Supplier
            public final Object get() {
                return supplier2.get(obj, obj2);
            }
        };
    }
}
