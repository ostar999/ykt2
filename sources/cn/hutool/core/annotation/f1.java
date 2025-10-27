package cn.hutool.core.annotation;

import java.util.function.Function;

/* loaded from: classes.dex */
public final /* synthetic */ class f1 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return ((AnnotationAttribute) obj).getValue();
    }
}
