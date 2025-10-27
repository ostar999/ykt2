package cn.hutool.core.annotation;

import cn.hutool.core.util.ReflectUtil;
import java.lang.annotation.Annotation;

/* loaded from: classes.dex */
public final /* synthetic */ class x {
    public static Annotation a(AnnotationAttribute annotationAttribute, Class cls) {
        return annotationAttribute.getAttribute().getAnnotation(cls);
    }

    public static Class b(AnnotationAttribute annotationAttribute) {
        return annotationAttribute.getAttribute().getDeclaringClass();
    }

    public static String c(AnnotationAttribute annotationAttribute) {
        return annotationAttribute.getAttribute().getName();
    }

    public static Class d(AnnotationAttribute annotationAttribute) {
        return annotationAttribute.getAttribute().getReturnType();
    }

    public static Object e(AnnotationAttribute annotationAttribute) {
        return ReflectUtil.invoke(annotationAttribute.getAnnotation(), annotationAttribute.getAttribute(), new Object[0]);
    }

    public static boolean f(AnnotationAttribute annotationAttribute) {
        return false;
    }
}
