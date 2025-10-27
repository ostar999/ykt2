package cn.hutool.core.annotation;

import java.util.function.Function;

/* loaded from: classes.dex */
public final /* synthetic */ class d implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return Integer.valueOf(((SynthesizedAnnotationPostProcessor) obj).order());
    }
}
