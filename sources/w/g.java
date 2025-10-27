package w;

import c0.p;
import cn.hutool.core.exceptions.CheckedUtil;
import cn.hutool.core.lang.func.VoidFunc0;
import java.io.Serializable;

/* loaded from: classes.dex */
public final /* synthetic */ class g implements CheckedUtil.VoidFunc0Rt, Serializable {

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ VoidFunc0 f28263c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ RuntimeException f28264d;

    public /* synthetic */ g(VoidFunc0 voidFunc0, RuntimeException runtimeException) {
        this.f28263c = voidFunc0;
        this.f28264d = runtimeException;
    }

    @Override // cn.hutool.core.exceptions.CheckedUtil.VoidFunc0Rt, cn.hutool.core.lang.func.VoidFunc0
    public final void call() throws RuntimeException {
        CheckedUtil.lambda$uncheck$a3c5d001$1(this.f28263c, this.f28264d);
    }

    @Override // cn.hutool.core.lang.func.VoidFunc0
    public /* synthetic */ void callWithRuntimeException() {
        p.a(this);
    }
}
