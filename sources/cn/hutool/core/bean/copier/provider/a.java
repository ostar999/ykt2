package cn.hutool.core.bean.copier.provider;

import cn.hutool.core.lang.Editor;
import java.io.Serializable;
import java.util.function.Function;

/* loaded from: classes.dex */
public final /* synthetic */ class a implements Function, Serializable {

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ boolean f2388c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Editor f2389d;

    public /* synthetic */ a(boolean z2, Editor editor) {
        this.f2388c = z2;
        this.f2389d = editor;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return BeanValueProvider.lambda$new$9d0d83f1$1(this.f2388c, this.f2389d, obj);
    }
}
