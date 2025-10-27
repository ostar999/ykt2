package cn.hutool.core.collection;

import cn.hutool.core.lang.func.Func1;
import java.io.Serializable;

/* loaded from: classes.dex */
public final /* synthetic */ class n0 implements Func1, Serializable {

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ String f2426c;

    public /* synthetic */ n0(String str) {
        this.f2426c = str;
    }

    @Override // cn.hutool.core.lang.func.Func1
    public final Object call(Object obj) {
        return IterUtil.lambda$fieldValueMap$a3f4a90f$1(this.f2426c, obj);
    }

    @Override // cn.hutool.core.lang.func.Func1
    public /* synthetic */ Object callWithRuntimeException(Object obj) {
        return c0.c.a(this, obj);
    }
}
