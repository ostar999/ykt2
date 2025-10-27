package cn.hutool.core.annotation.scanner;

import cn.hutool.core.annotation.scanner.TypeAnnotationScanner;
import cn.hutool.core.collection.CollUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Proxy;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/* loaded from: classes.dex */
public class TypeAnnotationScanner extends AbstractTypeAnnotationScanner<TypeAnnotationScanner> implements AnnotationScanner {

    public static class JdkProxyClassConverter implements UnaryOperator<Class<?>> {
        @Override // java.util.function.Function
        public Class<?> apply(Class<?> cls) {
            return Proxy.isProxyClass(cls) ? apply((Class<?>) cls.getSuperclass()) : cls;
        }
    }

    public TypeAnnotationScanner(boolean z2, boolean z3, Predicate<Class<?>> predicate, Set<Class<?>> set) {
        super(z2, z3, predicate, set);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$new$0(Class cls) {
        return true;
    }

    @Override // cn.hutool.core.annotation.scanner.AbstractTypeAnnotationScanner
    public Annotation[] getAnnotationsFromTargetClass(AnnotatedElement annotatedElement, int i2, Class<?> cls) {
        return cls.getAnnotations();
    }

    @Override // cn.hutool.core.annotation.scanner.AbstractTypeAnnotationScanner
    public Class<?> getClassFormAnnotatedElement(AnnotatedElement annotatedElement) {
        return (Class) annotatedElement;
    }

    @Override // cn.hutool.core.annotation.scanner.AbstractTypeAnnotationScanner, cn.hutool.core.annotation.scanner.AnnotationScanner
    public boolean support(AnnotatedElement annotatedElement) {
        return annotatedElement instanceof Class;
    }

    public TypeAnnotationScanner() {
        this(true, true, new Predicate() { // from class: r.q0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return TypeAnnotationScanner.lambda$new$0((Class) obj);
            }
        }, CollUtil.newLinkedHashSet(new Class[0]));
    }

    @Override // cn.hutool.core.annotation.scanner.AbstractTypeAnnotationScanner
    public TypeAnnotationScanner setIncludeInterfaces(boolean z2) {
        return (TypeAnnotationScanner) super.setIncludeInterfaces(z2);
    }

    @Override // cn.hutool.core.annotation.scanner.AbstractTypeAnnotationScanner
    public TypeAnnotationScanner setIncludeSuperClass(boolean z2) {
        return (TypeAnnotationScanner) super.setIncludeSuperClass(z2);
    }
}
