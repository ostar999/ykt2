package cn.hutool.core.annotation.scanner;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.annotation.scanner.FieldAnnotationScanner;
import cn.hutool.core.util.ObjectUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import r.o;

/* loaded from: classes.dex */
public class FieldAnnotationScanner implements AnnotationScanner {
    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$null$0(Annotation annotation) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Predicate lambda$scan$1(Predicate predicate) {
        return new Predicate() { // from class: r.t
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return FieldAnnotationScanner.lambda$null$0((Annotation) obj);
            }
        };
    }

    @Override // cn.hutool.core.annotation.scanner.AnnotationScanner
    public /* synthetic */ List getAnnotations(AnnotatedElement annotatedElement) {
        return o.a(this, annotatedElement);
    }

    @Override // cn.hutool.core.annotation.scanner.AnnotationScanner
    public /* synthetic */ List getAnnotationsIfSupport(AnnotatedElement annotatedElement) {
        return o.b(this, annotatedElement);
    }

    @Override // cn.hutool.core.annotation.scanner.AnnotationScanner
    public void scan(BiConsumer<Integer, Annotation> biConsumer, AnnotatedElement annotatedElement, Predicate<Annotation> predicate) {
        Predicate predicate2 = (Predicate) ObjectUtil.defaultIfNull(predicate, (Function<Predicate<Annotation>, ? extends Predicate<Annotation>>) new Function() { // from class: r.u
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return FieldAnnotationScanner.lambda$scan$1((Predicate) obj);
            }
        });
        for (Annotation annotation : annotatedElement.getAnnotations()) {
            if (AnnotationUtil.isNotJdkMateAnnotation(annotation.annotationType()) && predicate2.test(annotation)) {
                biConsumer.accept(0, annotation);
            }
        }
    }

    @Override // cn.hutool.core.annotation.scanner.AnnotationScanner
    public /* synthetic */ void scanIfSupport(BiConsumer biConsumer, AnnotatedElement annotatedElement, Predicate predicate) {
        o.d(this, biConsumer, annotatedElement, predicate);
    }

    @Override // cn.hutool.core.annotation.scanner.AnnotationScanner
    public boolean support(AnnotatedElement annotatedElement) {
        return annotatedElement instanceof Field;
    }
}
