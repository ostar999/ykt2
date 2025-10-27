package w;

import c0.q;
import cn.hutool.core.exceptions.CheckedUtil;
import cn.hutool.core.lang.func.Supplier1;
import cn.hutool.core.lang.func.VoidFunc1;
import java.io.Serializable;

/* loaded from: classes.dex */
public final /* synthetic */ class b implements CheckedUtil.VoidFunc1Rt, Serializable {

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ VoidFunc1 f28253c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Supplier1 f28254d;

    public /* synthetic */ b(VoidFunc1 voidFunc1, Supplier1 supplier1) {
        this.f28253c = voidFunc1;
        this.f28254d = supplier1;
    }

    @Override // cn.hutool.core.exceptions.CheckedUtil.VoidFunc1Rt, cn.hutool.core.lang.func.VoidFunc1
    public final void call(Object obj) throws RuntimeException {
        CheckedUtil.lambda$uncheck$ad313ebc$1(this.f28253c, this.f28254d, obj);
    }

    @Override // cn.hutool.core.lang.func.VoidFunc1
    public /* synthetic */ void callWithRuntimeException(Object obj) {
        q.a(this, obj);
    }
}
