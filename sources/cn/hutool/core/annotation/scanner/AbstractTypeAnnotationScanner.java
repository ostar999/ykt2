package cn.hutool.core.annotation.scanner;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.annotation.scanner.AbstractTypeAnnotationScanner;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import r.o;

/* loaded from: classes.dex */
public abstract class AbstractTypeAnnotationScanner<T extends AbstractTypeAnnotationScanner<T>> implements AnnotationScanner {
    private final List<UnaryOperator<Class<?>>> converters;
    private final Set<Class<?>> excludeTypes;
    private Predicate<Class<?>> filter;
    private boolean hasConverters;
    private boolean includeInterfaces;
    private boolean includeSuperClass;
    private final T typedThis;

    public static class JdkProxyClassConverter implements UnaryOperator<Class<?>> {
        @Override // java.util.function.Function
        public Class<?> apply(Class<?> cls) {
            return Proxy.isProxyClass(cls) ? apply((Class<?>) cls.getSuperclass()) : cls;
        }
    }

    public AbstractTypeAnnotationScanner(boolean z2, boolean z3, Predicate<Class<?>> predicate, Set<Class<?>> set) throws IllegalArgumentException {
        Assert.notNull(predicate, "filter must not null", new Object[0]);
        Assert.notNull(set, "excludeTypes must not null", new Object[0]);
        this.includeSuperClass = z2;
        this.includeInterfaces = z3;
        this.filter = predicate;
        this.excludeTypes = set;
        this.converters = new ArrayList();
        this.typedThis = this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$null$0(Annotation annotation) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Predicate lambda$scan$1(Predicate predicate) {
        return new Predicate() { // from class: r.d
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AbstractTypeAnnotationScanner.lambda$null$0((Annotation) obj);
            }
        };
    }

    public T addConverters(UnaryOperator<Class<?>> unaryOperator) throws IllegalArgumentException {
        Assert.notNull(unaryOperator, "converter must not null", new Object[0]);
        this.converters.add(unaryOperator);
        if (!this.hasConverters) {
            this.hasConverters = CollUtil.isNotEmpty((Collection<?>) this.converters);
        }
        return this.typedThis;
    }

    public T addExcludeTypes(Class<?>... clsArr) {
        CollUtil.addAll((Collection) this.excludeTypes, (Object[]) clsArr);
        return this.typedThis;
    }

    public Class<?> convert(Class<?> cls) {
        if (this.hasConverters) {
            Iterator<UnaryOperator<Class<?>>> it = this.converters.iterator();
            while (it.hasNext()) {
                cls = (Class) it.next().apply(cls);
            }
        }
        return cls;
    }

    @Override // cn.hutool.core.annotation.scanner.AnnotationScanner
    public /* synthetic */ List getAnnotations(AnnotatedElement annotatedElement) {
        return o.a(this, annotatedElement);
    }

    public abstract Annotation[] getAnnotationsFromTargetClass(AnnotatedElement annotatedElement, int i2, Class<?> cls);

    @Override // cn.hutool.core.annotation.scanner.AnnotationScanner
    public /* synthetic */ List getAnnotationsIfSupport(AnnotatedElement annotatedElement) {
        return o.b(this, annotatedElement);
    }

    public abstract Class<?> getClassFormAnnotatedElement(AnnotatedElement annotatedElement);

    public boolean isIncludeInterfaces() {
        return this.includeInterfaces;
    }

    public boolean isIncludeSuperClass() {
        return this.includeSuperClass;
    }

    public boolean isNotNeedProcess(Set<Class<?>> set, Class<?> cls) {
        return ObjectUtil.isNull(cls) || set.contains(cls) || this.excludeTypes.contains(cls) || this.filter.negate().test(cls);
    }

    @Override // cn.hutool.core.annotation.scanner.AnnotationScanner
    public void scan(BiConsumer<Integer, Annotation> biConsumer, AnnotatedElement annotatedElement, Predicate<Annotation> predicate) {
        Predicate predicate2 = (Predicate) ObjectUtil.defaultIfNull(predicate, (Function<Predicate<Annotation>, ? extends Predicate<Annotation>>) new Function() { // from class: r.c
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractTypeAnnotationScanner.lambda$scan$1((Predicate) obj);
            }
        });
        LinkedList linkedListNewLinkedList = CollUtil.newLinkedList(CollUtil.newArrayList(getClassFormAnnotatedElement(annotatedElement)));
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        int i2 = 0;
        while (!linkedListNewLinkedList.isEmpty()) {
            List list = (List) linkedListNewLinkedList.removeFirst();
            ArrayList arrayList = new ArrayList();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Class<?> clsConvert = convert((Class) it.next());
                if (!isNotNeedProcess(linkedHashSet, clsConvert)) {
                    linkedHashSet.add(clsConvert);
                    scanSuperClassIfNecessary(arrayList, clsConvert);
                    scanInterfaceIfNecessary(arrayList, clsConvert);
                    for (Annotation annotation : getAnnotationsFromTargetClass(annotatedElement, i2, clsConvert)) {
                        if (AnnotationUtil.isNotJdkMateAnnotation(annotation.annotationType()) && predicate2.test(annotation)) {
                            biConsumer.accept(Integer.valueOf(i2), annotation);
                        }
                    }
                    i2++;
                }
            }
            if (CollUtil.isNotEmpty((Collection<?>) arrayList)) {
                linkedListNewLinkedList.addLast(arrayList);
            }
        }
    }

    @Override // cn.hutool.core.annotation.scanner.AnnotationScanner
    public /* synthetic */ void scanIfSupport(BiConsumer biConsumer, AnnotatedElement annotatedElement, Predicate predicate) {
        o.d(this, biConsumer, annotatedElement, predicate);
    }

    public void scanInterfaceIfNecessary(List<Class<?>> list, Class<?> cls) {
        if (this.includeInterfaces) {
            Class<?>[] interfaces = cls.getInterfaces();
            if (ArrayUtil.isNotEmpty((Object[]) interfaces)) {
                CollUtil.addAll((Collection) list, (Object[]) interfaces);
            }
        }
    }

    public void scanSuperClassIfNecessary(List<Class<?>> list, Class<?> cls) {
        if (this.includeSuperClass) {
            Class<? super Object> superclass = cls.getSuperclass();
            if (ObjectUtil.equals(superclass, Object.class) || !ObjectUtil.isNotNull(superclass)) {
                return;
            }
            list.add(superclass);
        }
    }

    public T setFilter(Predicate<Class<?>> predicate) throws IllegalArgumentException {
        Assert.notNull(predicate, "filter must not null", new Object[0]);
        this.filter = predicate;
        return this.typedThis;
    }

    public T setIncludeInterfaces(boolean z2) {
        this.includeInterfaces = z2;
        return this.typedThis;
    }

    public T setIncludeSuperClass(boolean z2) {
        this.includeSuperClass = z2;
        return this.typedThis;
    }

    @Override // cn.hutool.core.annotation.scanner.AnnotationScanner
    public /* synthetic */ boolean support(AnnotatedElement annotatedElement) {
        return o.e(this, annotatedElement);
    }
}
