package cn.hutool.core.annotation;

import cn.hutool.core.map.TableMap;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class CombinationAnnotationElement implements AnnotatedElement, Serializable {
    private static final long serialVersionUID = 1;
    private Map<Class<? extends Annotation>, Annotation> annotationMap;
    private Map<Class<? extends Annotation>, Annotation> declaredAnnotationMap;
    private final Predicate<Annotation> predicate;

    public CombinationAnnotationElement(AnnotatedElement annotatedElement) {
        this(annotatedElement, null);
    }

    private void init(AnnotatedElement annotatedElement) {
        Annotation[] declaredAnnotations = annotatedElement.getDeclaredAnnotations();
        this.declaredAnnotationMap = new TableMap();
        parseDeclared(declaredAnnotations);
        Annotation[] annotations = annotatedElement.getAnnotations();
        if (Arrays.equals(declaredAnnotations, annotations)) {
            this.annotationMap = this.declaredAnnotationMap;
        } else {
            this.annotationMap = new TableMap();
            parse(annotations);
        }
    }

    public static CombinationAnnotationElement of(AnnotatedElement annotatedElement, Predicate<Annotation> predicate) {
        return new CombinationAnnotationElement(annotatedElement, predicate);
    }

    private void parse(Annotation[] annotationArr) {
        for (Annotation annotation : annotationArr) {
            Class<? extends Annotation> clsAnnotationType = annotation.annotationType();
            if (AnnotationUtil.isNotJdkMateAnnotation(clsAnnotationType) && !this.annotationMap.containsKey(clsAnnotationType)) {
                if (test(annotation)) {
                    this.annotationMap.put(clsAnnotationType, annotation);
                }
                parse(clsAnnotationType.getAnnotations());
            }
        }
    }

    private void parseDeclared(Annotation[] annotationArr) {
        for (Annotation annotation : annotationArr) {
            Class<? extends Annotation> clsAnnotationType = annotation.annotationType();
            if (AnnotationUtil.isNotJdkMateAnnotation(clsAnnotationType) && !this.declaredAnnotationMap.containsKey(clsAnnotationType)) {
                if (test(annotation)) {
                    this.declaredAnnotationMap.put(clsAnnotationType, annotation);
                }
                parseDeclared(clsAnnotationType.getDeclaredAnnotations());
            }
        }
    }

    private boolean test(Annotation annotation) {
        Predicate<Annotation> predicate = this.predicate;
        return predicate == null || predicate.test(annotation);
    }

    @Override // java.lang.reflect.AnnotatedElement
    public <T extends Annotation> T getAnnotation(Class<T> cls) {
        T t2 = (T) this.annotationMap.get(cls);
        if (t2 == null) {
            return null;
        }
        return t2;
    }

    @Override // java.lang.reflect.AnnotatedElement
    public Annotation[] getAnnotations() {
        return (Annotation[]) this.annotationMap.values().toArray(new Annotation[0]);
    }

    @Override // java.lang.reflect.AnnotatedElement
    public Annotation[] getDeclaredAnnotations() {
        return (Annotation[]) this.declaredAnnotationMap.values().toArray(new Annotation[0]);
    }

    @Override // java.lang.reflect.AnnotatedElement
    public boolean isAnnotationPresent(Class<? extends Annotation> cls) {
        return this.annotationMap.containsKey(cls);
    }

    public CombinationAnnotationElement(AnnotatedElement annotatedElement, Predicate<Annotation> predicate) {
        this.predicate = predicate;
        init(annotatedElement);
    }
}
