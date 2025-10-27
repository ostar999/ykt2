package r;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.annotation.scanner.AnnotationScanner;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes.dex */
public final /* synthetic */ class o {
    static {
        AnnotationScanner annotationScanner = AnnotationScanner.NOTHING;
    }

    public static List a(AnnotationScanner annotationScanner, AnnotatedElement annotatedElement) {
        final ArrayList arrayList = new ArrayList();
        annotationScanner.scan(new BiConsumer() { // from class: r.m
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                arrayList.add((Annotation) obj2);
            }
        }, annotatedElement, null);
        return arrayList;
    }

    public static List b(AnnotationScanner annotationScanner, AnnotatedElement annotatedElement) {
        return annotationScanner.support(annotatedElement) ? annotationScanner.getAnnotations(annotatedElement) : Collections.emptyList();
    }

    public static void c(AnnotationScanner annotationScanner, BiConsumer biConsumer, AnnotatedElement annotatedElement, Predicate predicate) {
        Predicate predicate2 = (Predicate) ObjectUtil.defaultIfNull(predicate, (Function<Predicate, ? extends Predicate>) new Function() { // from class: r.g
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return o.h((Predicate) obj);
            }
        });
        for (Annotation annotation : annotatedElement.getAnnotations()) {
            if (AnnotationUtil.isNotJdkMateAnnotation(annotation.annotationType()) && predicate2.test(annotation)) {
                biConsumer.accept(0, annotation);
            }
        }
    }

    public static void d(AnnotationScanner annotationScanner, BiConsumer biConsumer, AnnotatedElement annotatedElement, Predicate predicate) {
        if (annotationScanner.support(annotatedElement)) {
            annotationScanner.scan(biConsumer, annotatedElement, predicate);
        }
    }

    public static boolean e(AnnotationScanner annotationScanner, AnnotatedElement annotatedElement) {
        return false;
    }

    public static /* synthetic */ boolean g(Annotation annotation) {
        return true;
    }

    public static /* synthetic */ Predicate h(Predicate predicate) {
        return new Predicate() { // from class: r.n
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return o.g((Annotation) obj);
            }
        };
    }

    public static List<Annotation> l(final AnnotatedElement annotatedElement, AnnotationScanner... annotationScannerArr) {
        return (ObjectUtil.isNull(annotatedElement) && ArrayUtil.isNotEmpty((Object[]) annotationScannerArr)) ? Collections.emptyList() : (List) Stream.of((Object[]) annotationScannerArr).map(new Function() { // from class: r.k
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((AnnotationScanner) obj).getAnnotationsIfSupport(annotatedElement);
            }
        }).flatMap(new Function() { // from class: r.l
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((List) obj).stream();
            }
        }).collect(Collectors.toList());
    }

    public static List<Annotation> m(final AnnotatedElement annotatedElement, AnnotationScanner... annotationScannerArr) {
        return (ObjectUtil.isNull(annotatedElement) && ArrayUtil.isNotEmpty((Object[]) annotationScannerArr)) ? Collections.emptyList() : (List) Stream.of((Object[]) annotationScannerArr).filter(new Predicate() { // from class: r.h
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((AnnotationScanner) obj).support(annotatedElement);
            }
        }).findFirst().map(new Function() { // from class: r.i
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((AnnotationScanner) obj).getAnnotations(annotatedElement);
            }
        }).orElseGet(new Supplier() { // from class: r.j
            @Override // java.util.function.Supplier
            public final Object get() {
                return Collections.emptyList();
            }
        });
    }
}
