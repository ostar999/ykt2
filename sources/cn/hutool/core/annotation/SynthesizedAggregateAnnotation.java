package cn.hutool.core.annotation;

import java.lang.annotation.Annotation;

/* loaded from: classes.dex */
public interface SynthesizedAggregateAnnotation extends AggregateAnnotation, Hierarchical, AnnotationSynthesizer, AnnotationAttributeValueProvider {
    @Override // java.lang.annotation.Annotation
    Class<? extends Annotation> annotationType();

    <T extends Annotation> T getAnnotation(Class<T> cls);

    SynthesizedAnnotationAttributeProcessor getAnnotationAttributeProcessor();

    Object getAttributeValue(String str, Class<?> cls);

    int getHorizontalDistance();

    int getVerticalDistance();
}
