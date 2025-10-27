package cn.hutool.core.lang;

import cn.hutool.core.lang.func.Func0;
import cn.hutool.core.util.ReflectUtil;
import java.io.Serializable;

/* loaded from: classes.dex */
public final /* synthetic */ class l0 implements Func0, Serializable {

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Class f2504c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Object[] f2505d;

    public /* synthetic */ l0(Class cls, Object[] objArr) {
        this.f2504c = cls;
        this.f2505d = objArr;
    }

    @Override // cn.hutool.core.lang.func.Func0
    public final Object call() {
        return ReflectUtil.newInstance(this.f2504c, this.f2505d);
    }

    @Override // cn.hutool.core.lang.func.Func0
    public /* synthetic */ Object callWithRuntimeException() {
        return c0.b.a(this);
    }
}
