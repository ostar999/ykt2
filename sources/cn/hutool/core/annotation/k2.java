package cn.hutool.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public final /* synthetic */ class k2 {
    public static Annotation a(WrappedAnnotationAttribute wrappedAnnotationAttribute) {
        return wrappedAnnotationAttribute.getOriginal().getAnnotation();
    }

    public static Annotation b(WrappedAnnotationAttribute wrappedAnnotationAttribute, Class cls) {
        return wrappedAnnotationAttribute.getOriginal().getAnnotation(cls);
    }

    public static Method c(WrappedAnnotationAttribute wrappedAnnotationAttribute) {
        return wrappedAnnotationAttribute.getOriginal().getAttribute();
    }

    public static Class d(WrappedAnnotationAttribute wrappedAnnotationAttribute) {
        return wrappedAnnotationAttribute.getOriginal().getAttributeType();
    }

    public static boolean e(WrappedAnnotationAttribute wrappedAnnotationAttribute) {
        return true;
    }
}
