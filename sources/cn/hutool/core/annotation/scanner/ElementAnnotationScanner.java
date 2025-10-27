package cn.hutool.core.annotation.scanner;

import cn.hutool.core.annotation.scanner.ElementAnnotationScanner;
import cn.hutool.core.util.ObjectUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import r.o;

/* loaded from: classes.dex */
public class ElementAnnotationScanner implements AnnotationScanner {
    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$null$0(Annotation annotation) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Predicate lambda$scan$1(Predicate predicate) {
        return new Predicate() { // from class: r.s
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ElementAnnotationScanner.lambda$null$0((Annotation) obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$scan$2(BiConsumer biConsumer, Annotation annotation) {
        biConsumer.accept(0, annotation);
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
    public void scan(final BiConsumer<Integer, Annotation> biConsumer, AnnotatedElement annotatedElement, Predicate<Annotation> predicate) {
        Stream.of((Object[]) annotatedElement.getAnnotations()).filter((Predicate) ObjectUtil.defaultIfNull(predicate, (Function<Predicate<Annotation>, ? extends Predicate<Annotation>>) new Function() { // from class: r.q
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ElementAnnotationScanner.lambda$scan$1((Predicate) obj);
            }
        })).forEach(new Consumer() { // from class: r.r
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ElementAnnotationScanner.lambda$scan$2(biConsumer, (Annotation) obj);
            }
        });
    }

    @Override // cn.hutool.core.annotation.scanner.AnnotationScanner
    public /* synthetic */ void scanIfSupport(BiConsumer biConsumer, AnnotatedElement annotatedElement, Predicate predicate) {
        o.d(this, biConsumer, annotatedElement, predicate);
    }

    @Override // cn.hutool.core.annotation.scanner.AnnotationScanner
    public boolean support(AnnotatedElement annotatedElement) {
        return ObjectUtil.isNotNull(annotatedElement);
    }
}
