package cn.hutool.core.util;

import cn.hutool.core.lang.func.Func0;
import java.io.Serializable;

/* loaded from: classes.dex */
public final /* synthetic */ class b0 implements Func0, Serializable {

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Class f2598c;

    public /* synthetic */ b0(Class cls) {
        this.f2598c = cls;
    }

    @Override // cn.hutool.core.lang.func.Func0
    public final Object call() {
        return ReflectUtil.lambda$getMethods$ea73458f$1(this.f2598c);
    }

    @Override // cn.hutool.core.lang.func.Func0
    public /* synthetic */ Object callWithRuntimeException() {
        return c0.b.a(this);
    }
}
