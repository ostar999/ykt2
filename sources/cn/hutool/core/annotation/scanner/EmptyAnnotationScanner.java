package cn.hutool.core.annotation.scanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import r.o;

/* loaded from: classes.dex */
public class EmptyAnnotationScanner implements AnnotationScanner {
    @Override // cn.hutool.core.annotation.scanner.AnnotationScanner
    public List<Annotation> getAnnotations(AnnotatedElement annotatedElement) {
        return Collections.emptyList();
    }

    @Override // cn.hutool.core.annotation.scanner.AnnotationScanner
    public /* synthetic */ List getAnnotationsIfSupport(AnnotatedElement annotatedElement) {
        return o.b(this, annotatedElement);
    }

    @Override // cn.hutool.core.annotation.scanner.AnnotationScanner
    public void scan(BiConsumer<Integer, Annotation> biConsumer, AnnotatedElement annotatedElement, Predicate<Annotation> predicate) {
    }

    @Override // cn.hutool.core.annotation.scanner.AnnotationScanner
    public /* synthetic */ void scanIfSupport(BiConsumer biConsumer, AnnotatedElement annotatedElement, Predicate predicate) {
        o.d(this, biConsumer, annotatedElement, predicate);
    }

    @Override // cn.hutool.core.annotation.scanner.AnnotationScanner
    public boolean support(AnnotatedElement annotatedElement) {
        return true;
    }
}
