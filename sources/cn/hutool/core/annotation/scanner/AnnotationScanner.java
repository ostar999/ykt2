package cn.hutool.core.annotation.scanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public interface AnnotationScanner {
    public static final AnnotationScanner NOTHING = new EmptyAnnotationScanner();
    public static final AnnotationScanner DIRECTLY = new GenericAnnotationScanner(false, false, false);
    public static final AnnotationScanner DIRECTLY_AND_META_ANNOTATION = new GenericAnnotationScanner(true, false, false);
    public static final AnnotationScanner SUPERCLASS = new GenericAnnotationScanner(false, true, false);
    public static final AnnotationScanner SUPERCLASS_AND_META_ANNOTATION = new GenericAnnotationScanner(true, true, false);
    public static final AnnotationScanner INTERFACE = new GenericAnnotationScanner(false, false, true);
    public static final AnnotationScanner INTERFACE_AND_META_ANNOTATION = new GenericAnnotationScanner(true, false, true);
    public static final AnnotationScanner TYPE_HIERARCHY = new GenericAnnotationScanner(false, true, true);
    public static final AnnotationScanner TYPE_HIERARCHY_AND_META_ANNOTATION = new GenericAnnotationScanner(true, true, true);

    List<Annotation> getAnnotations(AnnotatedElement annotatedElement);

    List<Annotation> getAnnotationsIfSupport(AnnotatedElement annotatedElement);

    void scan(BiConsumer<Integer, Annotation> biConsumer, AnnotatedElement annotatedElement, Predicate<Annotation> predicate);

    void scanIfSupport(BiConsumer<Integer, Annotation> biConsumer, AnnotatedElement annotatedElement, Predicate<Annotation> predicate);

    boolean support(AnnotatedElement annotatedElement);
}
