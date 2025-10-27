package w;

import cn.hutool.core.exceptions.CheckedUtil;
import cn.hutool.core.lang.func.Func;
import cn.hutool.core.lang.func.Supplier1;
import java.io.Serializable;

/* loaded from: classes.dex */
public final /* synthetic */ class e implements CheckedUtil.FuncRt, Serializable {

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Func f28259c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Supplier1 f28260d;

    public /* synthetic */ e(Func func, Supplier1 supplier1) {
        this.f28259c = func;
        this.f28260d = supplier1;
    }

    @Override // cn.hutool.core.exceptions.CheckedUtil.FuncRt, cn.hutool.core.lang.func.Func
    public final Object call(Object[] objArr) {
        return CheckedUtil.lambda$uncheck$6c25eb8b$1(this.f28259c, this.f28260d, objArr);
    }

    @Override // cn.hutool.core.lang.func.Func
    public /* synthetic */ Object callWithRuntimeException(Object... objArr) {
        return c0.a.a(this, objArr);
    }
}
