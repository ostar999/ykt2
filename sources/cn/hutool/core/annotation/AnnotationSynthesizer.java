package cn.hutool.core.annotation;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;

/* loaded from: classes.dex */
public interface AnnotationSynthesizer {
    Map<Class<? extends Annotation>, SynthesizedAnnotation> getAllSynthesizedAnnotation();

    Collection<SynthesizedAnnotationPostProcessor> getAnnotationPostProcessors();

    SynthesizedAnnotationSelector getAnnotationSelector();

    Object getSource();

    SynthesizedAnnotation getSynthesizedAnnotation(Class<?> cls);

    <T extends Annotation> T synthesize(Class<T> cls);
}
