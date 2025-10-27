package cn.hutool.core.annotation;

import java.lang.reflect.Method;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public final /* synthetic */ class g1 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return AnnotationUtil.isAttributeMethod((Method) obj);
    }
}
