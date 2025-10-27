package cn.hutool.core.util;

import cn.hutool.core.lang.func.Func0;
import java.io.Serializable;

/* loaded from: classes.dex */
public final /* synthetic */ class d0 implements Func0, Serializable {

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Class f2602c;

    public /* synthetic */ d0(Class cls) {
        this.f2602c = cls;
    }

    @Override // cn.hutool.core.lang.func.Func0
    public final Object call() {
        return ReflectUtil.lambda$getFields$54eedd5e$1(this.f2602c);
    }

    @Override // cn.hutool.core.lang.func.Func0
    public /* synthetic */ Object callWithRuntimeException() {
        return c0.b.a(this);
    }
}
