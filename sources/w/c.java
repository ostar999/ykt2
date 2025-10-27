package w;

import cn.hutool.core.exceptions.CheckedUtil;
import cn.hutool.core.lang.func.Func1;
import cn.hutool.core.lang.func.Supplier1;
import java.io.Serializable;

/* loaded from: classes.dex */
public final /* synthetic */ class c implements CheckedUtil.Func1Rt, Serializable {

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Func1 f28255c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Supplier1 f28256d;

    public /* synthetic */ c(Func1 func1, Supplier1 supplier1) {
        this.f28255c = func1;
        this.f28256d = supplier1;
    }

    @Override // cn.hutool.core.exceptions.CheckedUtil.Func1Rt, cn.hutool.core.lang.func.Func1
    public final Object call(Object obj) {
        return CheckedUtil.lambda$uncheck$5732f3b9$1(this.f28255c, this.f28256d, obj);
    }

    @Override // cn.hutool.core.lang.func.Func1
    public /* synthetic */ Object callWithRuntimeException(Object obj) {
        return c0.c.a(this, obj);
    }
}
