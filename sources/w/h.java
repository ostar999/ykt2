package w;

import cn.hutool.core.lang.func.Supplier1;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public final /* synthetic */ class h implements Supplier1 {
    @Override // cn.hutool.core.lang.func.Supplier1
    public final Object get(Object obj) {
        return new RuntimeException((Exception) obj);
    }

    @Override // cn.hutool.core.lang.func.Supplier1
    public /* synthetic */ Supplier toSupplier(Object obj) {
        return c0.f.a(this, obj);
    }
}
