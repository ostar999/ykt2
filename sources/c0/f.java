package c0;

import cn.hutool.core.lang.func.Supplier1;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public final /* synthetic */ class f<T, P1> {
    public static Supplier a(final Supplier1 supplier1, final Object obj) {
        return new Supplier() { // from class: c0.e
            @Override // java.util.function.Supplier
            public final Object get() {
                return supplier1.get(obj);
            }
        };
    }
}
