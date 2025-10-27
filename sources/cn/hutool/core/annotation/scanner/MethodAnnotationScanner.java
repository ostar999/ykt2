package cn.hutool.core.annotation.scanner;

import cn.hutool.core.annotation.scanner.MethodAnnotationScanner;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ClassUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* loaded from: classes.dex */
public class MethodAnnotationScanner extends AbstractTypeAnnotationScanner<MethodAnnotationScanner> implements AnnotationScanner {
    public MethodAnnotationScanner() {
        this(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: hasSameSignature, reason: merged with bridge method [inline-methods] */
    public boolean lambda$getAnnotationsFromTargetClass$2(Method method, Method method2) {
        if (!CharSequenceUtil.equals(method.getName(), method2.getName())) {
            return false;
        }
        Class<?>[] parameterTypes = method.getParameterTypes();
        Class<?>[] parameterTypes2 = method2.getParameterTypes();
        if (parameterTypes.length == parameterTypes2.length && ArrayUtil.containsAll(parameterTypes, parameterTypes2)) {
            return ClassUtil.isAssignable(method2.getReturnType(), method.getReturnType());
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getAnnotationsFromTargetClass$1(Method method) {
        return !method.isBridge();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Annotation[] lambda$getAnnotationsFromTargetClass$3(int i2) {
        return new Annotation[i2];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$new$0(Class cls) {
        return true;
    }

    @Override // cn.hutool.core.annotation.scanner.AbstractTypeAnnotationScanner
    public Annotation[] getAnnotationsFromTargetClass(AnnotatedElement annotatedElement, int i2, Class<?> cls) {
        final Method method = (Method) annotatedElement;
        return (Annotation[]) Stream.of((Object[]) ClassUtil.getDeclaredMethods(cls)).filter(new Predicate() { // from class: r.m0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return MethodAnnotationScanner.lambda$getAnnotationsFromTargetClass$1((Method) obj);
            }
        }).filter(new Predicate() { // from class: r.n0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f28178a.lambda$getAnnotationsFromTargetClass$2(method, (Method) obj);
            }
        }).map(new Function() { // from class: r.o0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Method) obj).getAnnotations();
            }
        }).flatMap(new Function() { // from class: r.p0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Stream.of((Object[]) obj);
            }
        }).toArray(new IntFunction() { // from class: r.l0
            @Override // java.util.function.IntFunction
            public final Object apply(int i3) {
                return MethodAnnotationScanner.lambda$getAnnotationsFromTargetClass$3(i3);
            }
        });
    }

    @Override // cn.hutool.core.annotation.scanner.AbstractTypeAnnotationScanner
    public Class<?> getClassFormAnnotatedElement(AnnotatedElement annotatedElement) {
        return ((Method) annotatedElement).getDeclaringClass();
    }

    public MethodAnnotationScanner setScanSameSignatureMethod(boolean z2) {
        setIncludeInterfaces(z2);
        setIncludeSuperClass(z2);
        return this;
    }

    @Override // cn.hutool.core.annotation.scanner.AbstractTypeAnnotationScanner, cn.hutool.core.annotation.scanner.AnnotationScanner
    public boolean support(AnnotatedElement annotatedElement) {
        return annotatedElement instanceof Method;
    }

    public MethodAnnotationScanner(boolean z2) {
        this(z2, new Predicate() { // from class: r.k0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return MethodAnnotationScanner.lambda$new$0((Class) obj);
            }
        }, CollUtil.newLinkedHashSet(new Class[0]));
    }

    public MethodAnnotationScanner(boolean z2, Predicate<Class<?>> predicate, Set<Class<?>> set) {
        super(z2, z2, predicate, set);
    }

    public MethodAnnotationScanner(boolean z2, boolean z3, Predicate<Class<?>> predicate, Set<Class<?>> set) {
        super(z2, z3, predicate, set);
    }
}
