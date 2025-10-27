package cn.hutool.core.annotation;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.function.UnaryOperator;

/* loaded from: classes.dex */
public interface SynthesizedAnnotation extends Annotation, Hierarchical, AnnotationAttributeValueProvider {
    Annotation getAnnotation();

    Object getAttributeValue(String str);

    Map<String, AnnotationAttribute> getAttributes();

    int getHorizontalDistance();

    int getVerticalDistance();

    boolean hasAttribute(String str, Class<?> cls);

    void replaceAttribute(String str, UnaryOperator<AnnotationAttribute> unaryOperator);

    void setAttribute(String str, AnnotationAttribute annotationAttribute);

    void setAttributes(Map<String, AnnotationAttribute> map);
}
