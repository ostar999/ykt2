package cn.hutool.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;

/* loaded from: classes.dex */
public interface WrappedAnnotationAttribute extends AnnotationAttribute {
    Collection<AnnotationAttribute> getAllLinkedNonWrappedAttributes();

    @Override // cn.hutool.core.annotation.AnnotationAttribute
    Annotation getAnnotation();

    @Override // cn.hutool.core.annotation.AnnotationAttribute
    <T extends Annotation> T getAnnotation(Class<T> cls);

    @Override // cn.hutool.core.annotation.AnnotationAttribute
    Method getAttribute();

    @Override // cn.hutool.core.annotation.AnnotationAttribute
    Class<?> getAttributeType();

    AnnotationAttribute getLinked();

    AnnotationAttribute getNonWrappedOriginal();

    AnnotationAttribute getOriginal();

    @Override // cn.hutool.core.annotation.AnnotationAttribute
    boolean isValueEquivalentToDefaultValue();

    @Override // cn.hutool.core.annotation.AnnotationAttribute
    boolean isWrapped();
}
