package w;

import cn.hutool.core.exceptions.CheckedUtil;
import cn.hutool.core.lang.func.Func0;
import cn.hutool.core.lang.func.Supplier1;
import java.io.Serializable;

/* loaded from: classes.dex */
public final /* synthetic */ class d implements CheckedUtil.Func0Rt, Serializable {

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Func0 f28257c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Supplier1 f28258d;

    public /* synthetic */ d(Func0 func0, Supplier1 supplier1) {
        this.f28257c = func0;
        this.f28258d = supplier1;
    }

    @Override // cn.hutool.core.exceptions.CheckedUtil.Func0Rt, cn.hutool.core.lang.func.Func0
    public final Object call() {
        return CheckedUtil.lambda$uncheck$e9066ec4$1(this.f28257c, this.f28258d);
    }

    @Override // cn.hutool.core.lang.func.Func0
    public /* synthetic */ Object callWithRuntimeException() {
        return c0.b.a(this);
    }
}
