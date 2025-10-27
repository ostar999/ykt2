package cn.hutool.core.annotation.scanner;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.annotation.scanner.MetaAnnotationScanner;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import r.o;

/* loaded from: classes.dex */
public class MetaAnnotationScanner implements AnnotationScanner {
    private final boolean includeSupperMetaAnnotation;

    public MetaAnnotationScanner(boolean z2) {
        this.includeSupperMetaAnnotation = z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getAnnotations$1(AnnotatedElement annotatedElement, Annotation annotation) {
        return ObjectUtil.notEqual(annotation, annotatedElement);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$null$2(Annotation annotation) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Predicate lambda$scan$3(Predicate predicate) {
        return new Predicate() { // from class: r.j0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return MetaAnnotationScanner.lambda$null$2((Annotation) obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$scan$4(Annotation annotation) {
        return !AnnotationUtil.isJdkMetaAnnotation(annotation.annotationType());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$scan$5(Set set, Class cls) {
        return !set.contains(cls);
    }

    @Override // cn.hutool.core.annotation.scanner.AnnotationScanner
    public List<Annotation> getAnnotations(final AnnotatedElement annotatedElement) {
        final ArrayList arrayList = new ArrayList();
        scan(new BiConsumer() { // from class: r.d0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                arrayList.add((Annotation) obj2);
            }
        }, annotatedElement, new Predicate() { // from class: r.e0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return MetaAnnotationScanner.lambda$getAnnotations$1(annotatedElement, (Annotation) obj);
            }
        });
        return arrayList;
    }

    @Override // cn.hutool.core.annotation.scanner.AnnotationScanner
    public /* synthetic */ List getAnnotationsIfSupport(AnnotatedElement annotatedElement) {
        return o.b(this, annotatedElement);
    }

    @Override // cn.hutool.core.annotation.scanner.AnnotationScanner
    public void scan(BiConsumer<Integer, Annotation> biConsumer, AnnotatedElement annotatedElement, Predicate<Annotation> predicate) {
        Predicate predicate2 = (Predicate) ObjectUtil.defaultIfNull(predicate, (Function<Predicate<Annotation>, ? extends Predicate<Annotation>>) new Function() { // from class: r.f0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MetaAnnotationScanner.lambda$scan$3((Predicate) obj);
            }
        });
        final HashSet hashSet = new HashSet();
        int i2 = 0;
        LinkedList linkedListNewLinkedList = CollUtil.newLinkedList(CollUtil.newArrayList((Class) annotatedElement));
        do {
            for (Class cls : (List) linkedListNewLinkedList.removeFirst()) {
                List list = (List) Stream.of((Object[]) cls.getAnnotations()).filter(new Predicate() { // from class: r.h0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return MetaAnnotationScanner.lambda$scan$4((Annotation) obj);
                    }
                }).filter(predicate2).collect(Collectors.toList());
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    biConsumer.accept(Integer.valueOf(i2), (Annotation) it.next());
                }
                hashSet.add(cls);
                List list2 = (List) list.stream().map(new Function() { // from class: r.i0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return ((Annotation) obj).annotationType();
                    }
                }).filter(new Predicate() { // from class: r.g0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return MetaAnnotationScanner.lambda$scan$5(hashSet, (Class) obj);
                    }
                }).collect(Collectors.toList());
                if (CollUtil.isNotEmpty((Collection<?>) list2)) {
                    linkedListNewLinkedList.addLast(list2);
                }
            }
            i2++;
            if (!this.includeSupperMetaAnnotation) {
                return;
            }
        } while (!linkedListNewLinkedList.isEmpty());
    }

    @Override // cn.hutool.core.annotation.scanner.AnnotationScanner
    public /* synthetic */ void scanIfSupport(BiConsumer biConsumer, AnnotatedElement annotatedElement, Predicate predicate) {
        o.d(this, biConsumer, annotatedElement, predicate);
    }

    @Override // cn.hutool.core.annotation.scanner.AnnotationScanner
    public boolean support(AnnotatedElement annotatedElement) {
        return (annotatedElement instanceof Class) && ClassUtil.isAssignable(Annotation.class, (Class) annotatedElement);
    }

    public MetaAnnotationScanner() {
        this(true);
    }
}
