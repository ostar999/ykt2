package cn.hutool.core.annotation.scanner;

import cn.hutool.core.annotation.scanner.GenericAnnotationScanner;
import cn.hutool.core.map.multi.ListValueMap;
import cn.hutool.core.util.ObjectUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import r.o;

/* loaded from: classes.dex */
public class GenericAnnotationScanner implements AnnotationScanner {
    private final AnnotationScanner elementScanner;
    private final AnnotationScanner metaScanner;
    private final AnnotationScanner methodScanner;
    private final AnnotationScanner typeScanner;

    public GenericAnnotationScanner(boolean z2, boolean z3, boolean z4) {
        this.metaScanner = z2 ? new MetaAnnotationScanner() : new EmptyAnnotationScanner();
        this.typeScanner = new TypeAnnotationScanner(z3, z4, new Predicate() { // from class: r.y
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return GenericAnnotationScanner.lambda$new$0((Class) obj);
            }
        }, Collections.emptySet());
        this.methodScanner = new MethodAnnotationScanner(z3, z4, new Predicate() { // from class: r.z
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return GenericAnnotationScanner.lambda$new$1((Class) obj);
            }
        }, Collections.emptySet());
        this.elementScanner = new ElementAnnotationScanner();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$new$0(Class cls) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$new$1(Class cls) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$null$2(Annotation annotation) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$null$5(BiConsumer biConsumer, Integer num, Predicate predicate, Annotation annotation) {
        biConsumer.accept(num, annotation);
        this.metaScanner.scan(biConsumer, annotation.annotationType(), predicate);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Predicate lambda$scan$3(Predicate predicate) {
        return new Predicate() { // from class: r.w
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return GenericAnnotationScanner.lambda$null$2((Annotation) obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$scanElements$4(Predicate predicate, ListValueMap listValueMap, Integer num, Annotation annotation) {
        if (predicate.test(annotation)) {
            listValueMap.lambda$null$0(num, annotation);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scanElements$6(final BiConsumer biConsumer, final Predicate predicate, final Integer num, List list) {
        list.forEach(new Consumer() { // from class: r.x
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f28181c.lambda$null$5(biConsumer, num, predicate, (Annotation) obj);
            }
        });
    }

    private void scanElements(AnnotationScanner annotationScanner, final BiConsumer<Integer, Annotation> biConsumer, AnnotatedElement annotatedElement, final Predicate<Annotation> predicate) {
        final ListValueMap listValueMap = new ListValueMap(new LinkedHashMap());
        annotationScanner.scan(new BiConsumer() { // from class: r.a0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                GenericAnnotationScanner.lambda$scanElements$4(predicate, listValueMap, (Integer) obj, (Annotation) obj2);
            }
        }, annotatedElement, predicate);
        listValueMap.forEach(new BiConsumer() { // from class: r.b0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f28168c.lambda$scanElements$6(biConsumer, predicate, (Integer) obj, (List) obj2);
            }
        });
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
        Predicate<Annotation> predicate2 = (Predicate) ObjectUtil.defaultIfNull(predicate, (Function<Predicate<Annotation>, ? extends Predicate<Annotation>>) new Function() { // from class: r.c0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return GenericAnnotationScanner.lambda$scan$3((Predicate) obj);
            }
        });
        if (ObjectUtil.isNull(annotatedElement)) {
            return;
        }
        if (annotatedElement instanceof Class) {
            scanElements(this.typeScanner, biConsumer, annotatedElement, predicate2);
        } else if (annotatedElement instanceof Method) {
            scanElements(this.methodScanner, biConsumer, annotatedElement, predicate2);
        } else {
            scanElements(this.elementScanner, biConsumer, annotatedElement, predicate2);
        }
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
