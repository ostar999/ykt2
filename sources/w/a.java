package w;

import c0.p;
import cn.hutool.core.exceptions.CheckedUtil;
import cn.hutool.core.lang.func.Supplier1;
import cn.hutool.core.lang.func.VoidFunc0;
import java.io.Serializable;

/* loaded from: classes.dex */
public final /* synthetic */ class a implements CheckedUtil.VoidFunc0Rt, Serializable {

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ VoidFunc0 f28251c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Supplier1 f28252d;

    public /* synthetic */ a(VoidFunc0 voidFunc0, Supplier1 supplier1) {
        this.f28251c = voidFunc0;
        this.f28252d = supplier1;
    }

    @Override // cn.hutool.core.exceptions.CheckedUtil.VoidFunc0Rt, cn.hutool.core.lang.func.VoidFunc0
    public final void call() throws RuntimeException {
        CheckedUtil.lambda$uncheck$2300d7df$1(this.f28251c, this.f28252d);
    }

    @Override // cn.hutool.core.lang.func.VoidFunc0
    public /* synthetic */ void callWithRuntimeException() {
        p.a(this);
    }
}
