package w;

import c0.o;
import cn.hutool.core.exceptions.CheckedUtil;
import cn.hutool.core.lang.func.Supplier1;
import cn.hutool.core.lang.func.VoidFunc;
import java.io.Serializable;

/* loaded from: classes.dex */
public final /* synthetic */ class f implements CheckedUtil.VoidFuncRt, Serializable {

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ VoidFunc f28261c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Supplier1 f28262d;

    public /* synthetic */ f(VoidFunc voidFunc, Supplier1 supplier1) {
        this.f28261c = voidFunc;
        this.f28262d = supplier1;
    }

    @Override // cn.hutool.core.exceptions.CheckedUtil.VoidFuncRt, cn.hutool.core.lang.func.VoidFunc
    public final void call(Object[] objArr) throws RuntimeException {
        CheckedUtil.lambda$uncheck$5b7ace8e$1(this.f28261c, this.f28262d, objArr);
    }

    @Override // cn.hutool.core.lang.func.VoidFunc
    public /* synthetic */ void callWithRuntimeException(Object... objArr) {
        o.a(this, objArr);
    }
}
