package cn.hutool.core.annotation;

@FunctionalInterface
/* loaded from: classes.dex */
public interface AnnotationAttributeValueProvider {
    Object getAttributeValue(String str, Class<?> cls);
}
