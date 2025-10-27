package cn.hutool.core.bean;

import cn.hutool.core.lang.func.Func0;
import java.io.Serializable;

/* loaded from: classes.dex */
public final /* synthetic */ class m implements Func0, Serializable {

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Class f2399c;

    public /* synthetic */ m(Class cls) {
        this.f2399c = cls;
    }

    @Override // cn.hutool.core.lang.func.Func0
    public final Object call() {
        return BeanUtil.lambda$getBeanDesc$e7c7684d$1(this.f2399c);
    }

    @Override // cn.hutool.core.lang.func.Func0
    public /* synthetic */ Object callWithRuntimeException() {
        return c0.b.a(this);
    }
}
