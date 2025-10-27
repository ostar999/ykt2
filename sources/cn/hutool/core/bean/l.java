package cn.hutool.core.bean;

import cn.hutool.core.lang.func.Func0;
import java.io.Serializable;

/* loaded from: classes.dex */
public final /* synthetic */ class l implements Func0, Serializable {

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Class f2397c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ boolean f2398d;

    public /* synthetic */ l(Class cls, boolean z2) {
        this.f2397c = cls;
        this.f2398d = z2;
    }

    @Override // cn.hutool.core.lang.func.Func0
    public final Object call() {
        return BeanUtil.internalGetPropertyDescriptorMap(this.f2397c, this.f2398d);
    }

    @Override // cn.hutool.core.lang.func.Func0
    public /* synthetic */ Object callWithRuntimeException() {
        return c0.b.a(this);
    }
}
