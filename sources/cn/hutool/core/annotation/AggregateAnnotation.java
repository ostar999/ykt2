package cn.hutool.core.annotation;

import java.lang.annotation.Annotation;

/* loaded from: classes.dex */
public interface AggregateAnnotation extends Annotation {
    Annotation[] getAnnotations();

    boolean isAnnotationPresent(Class<? extends Annotation> cls);
}
