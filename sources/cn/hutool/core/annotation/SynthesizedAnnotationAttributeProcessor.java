package cn.hutool.core.annotation;

import java.util.Collection;

@FunctionalInterface
/* loaded from: classes.dex */
public interface SynthesizedAnnotationAttributeProcessor {
    <R> R getAttributeValue(String str, Class<R> cls, Collection<? extends SynthesizedAnnotation> collection);
}
