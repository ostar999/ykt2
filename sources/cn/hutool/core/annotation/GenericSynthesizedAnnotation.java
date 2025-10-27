package cn.hutool.core.annotation;

import cn.hutool.core.lang.Opt;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes.dex */
public class GenericSynthesizedAnnotation<R, T extends Annotation> implements SynthesizedAnnotation {
    private final T annotation;
    private final Map<String, AnnotationAttribute> attributeMethodCaches;
    private final int horizontalDistance;
    private final R root;
    private final int verticalDistance;

    public GenericSynthesizedAnnotation(R r2, T t2, int i2, int i3) {
        this.root = r2;
        this.annotation = t2;
        this.verticalDistance = i2;
        this.horizontalDistance = i3;
        HashMap map = new HashMap();
        this.attributeMethodCaches = map;
        map.putAll(loadAttributeMethods());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getAttributeValue$2(Class cls, AnnotationAttribute annotationAttribute) {
        return ClassUtil.isAssignable(cls, annotationAttribute.getAttributeType());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$hasAttribute$1(Class cls, AnnotationAttribute annotationAttribute) {
        return ClassUtil.isAssignable(cls, annotationAttribute.getAttributeType());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ AnnotationAttribute lambda$loadAttributeMethods$0(Method method) {
        return new CacheableAnnotationAttribute(this.annotation, method);
    }

    @Override // java.lang.annotation.Annotation
    public Class<? extends Annotation> annotationType() {
        return this.annotation.annotationType();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // cn.hutool.core.annotation.Hierarchical
    public /* synthetic */ int compareTo(Hierarchical hierarchical) {
        return o1.a(this, hierarchical);
    }

    @Override // cn.hutool.core.annotation.Hierarchical, java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(Hierarchical hierarchical) {
        return compareTo((Hierarchical) hierarchical);
    }

    @Override // cn.hutool.core.annotation.SynthesizedAnnotation
    public T getAnnotation() {
        return this.annotation;
    }

    @Override // cn.hutool.core.annotation.SynthesizedAnnotation
    public Object getAttributeValue(String str) {
        return Opt.ofNullable(this.attributeMethodCaches.get(str)).map(new f1()).get();
    }

    @Override // cn.hutool.core.annotation.SynthesizedAnnotation
    public Map<String, AnnotationAttribute> getAttributes() {
        return this.attributeMethodCaches;
    }

    @Override // cn.hutool.core.annotation.SynthesizedAnnotation, cn.hutool.core.annotation.Hierarchical
    public int getHorizontalDistance() {
        return this.horizontalDistance;
    }

    @Override // cn.hutool.core.annotation.Hierarchical
    public R getRoot() {
        return this.root;
    }

    @Override // cn.hutool.core.annotation.SynthesizedAnnotation, cn.hutool.core.annotation.Hierarchical
    public int getVerticalDistance() {
        return this.verticalDistance;
    }

    public boolean hasAttribute(String str) {
        return this.attributeMethodCaches.containsKey(str);
    }

    public Map<String, AnnotationAttribute> loadAttributeMethods() {
        return (Map) Stream.of((Object[]) ClassUtil.getDeclaredMethods(this.annotation.annotationType())).filter(new g1()).collect(Collectors.toMap(new Function() { // from class: cn.hutool.core.annotation.h1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Method) obj).getName();
            }
        }, new Function() { // from class: cn.hutool.core.annotation.i1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f2340c.lambda$loadAttributeMethods$0((Method) obj);
            }
        }));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // cn.hutool.core.annotation.SynthesizedAnnotation
    public void replaceAttribute(String str, UnaryOperator<AnnotationAttribute> unaryOperator) {
        AnnotationAttribute annotationAttribute = this.attributeMethodCaches.get(str);
        if (ObjectUtil.isNotNull(annotationAttribute)) {
            this.attributeMethodCaches.put(str, unaryOperator.apply(annotationAttribute));
        }
    }

    @Override // cn.hutool.core.annotation.SynthesizedAnnotation
    public void setAttribute(String str, AnnotationAttribute annotationAttribute) {
        this.attributeMethodCaches.put(str, annotationAttribute);
    }

    @Override // cn.hutool.core.annotation.SynthesizedAnnotation
    public /* synthetic */ void setAttributes(Map map) {
        r1.a(this, map);
    }

    @Override // cn.hutool.core.annotation.SynthesizedAnnotation
    public boolean hasAttribute(String str, final Class<?> cls) {
        return Opt.ofNullable(this.attributeMethodCaches.get(str)).filter(new Predicate() { // from class: cn.hutool.core.annotation.j1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return GenericSynthesizedAnnotation.lambda$hasAttribute$1(cls, (AnnotationAttribute) obj);
            }
        }).isPresent();
    }

    @Override // cn.hutool.core.annotation.AnnotationAttributeValueProvider
    public Object getAttributeValue(String str, final Class<?> cls) {
        return Opt.ofNullable(this.attributeMethodCaches.get(str)).filter(new Predicate() { // from class: cn.hutool.core.annotation.k1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return GenericSynthesizedAnnotation.lambda$getAttributeValue$2(cls, (AnnotationAttribute) obj);
            }
        }).map(new f1()).get();
    }
}
