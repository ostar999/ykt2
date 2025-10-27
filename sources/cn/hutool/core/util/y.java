package cn.hutool.core.util;

import cn.hutool.core.lang.func.Func0;
import java.io.Serializable;

/* loaded from: classes.dex */
public final /* synthetic */ class y implements Func0, Serializable {

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Class f2620c;

    public /* synthetic */ y(Class cls) {
        this.f2620c = cls;
    }

    @Override // cn.hutool.core.lang.func.Func0
    public final Object call() {
        return ReflectUtil.getConstructorsDirectly(this.f2620c);
    }

    @Override // cn.hutool.core.lang.func.Func0
    public /* synthetic */ Object callWithRuntimeException() {
        return c0.b.a(this);
    }
}
