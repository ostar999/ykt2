package cn.hutool.core.annotation;

import cn.hutool.core.annotation.scanner.AnnotationScanner;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.lang.Filter;
import cn.hutool.core.lang.Opt;
import cn.hutool.core.lang.func.Func1;
import cn.hutool.core.lang.func.LambdaUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes.dex */
public class AnnotationUtil {
    static final Set<Class<? extends Annotation>> META_ANNOTATIONS = CollUtil.newHashSet(Target.class, Retention.class, Inherited.class, Documented.class, SuppressWarnings.class, Override.class, Deprecated.class);

    public static SynthesizedAggregateAnnotation aggregatingFromAnnotation(Annotation... annotationArr) {
        return new GenericSynthesizedAggregateAnnotation(Arrays.asList(annotationArr), AnnotationScanner.NOTHING);
    }

    public static SynthesizedAggregateAnnotation aggregatingFromAnnotationWithMeta(Annotation... annotationArr) {
        return new GenericSynthesizedAggregateAnnotation(Arrays.asList(annotationArr), AnnotationScanner.DIRECTLY_AND_META_ANNOTATION);
    }

    public static <T extends Annotation> List<T> getAllSynthesizedAnnotations(AnnotatedElement annotatedElement, final Class<T> cls) {
        return (List) AnnotationScanner.DIRECTLY.getAnnotationsIfSupport(annotatedElement).stream().map(new Function() { // from class: cn.hutool.core.annotation.i0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AnnotationUtil.lambda$getAllSynthesizedAnnotations$4(cls, (Annotation) obj);
            }
        }).filter(new Predicate() { // from class: cn.hutool.core.annotation.j0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return g0.a((Annotation) obj);
            }
        }).collect(Collectors.toList());
    }

    public static <A extends Annotation> A getAnnotation(AnnotatedElement annotatedElement, Class<A> cls) {
        if (annotatedElement == null) {
            return null;
        }
        return (A) toCombination(annotatedElement).getAnnotation(cls);
    }

    public static <T extends Annotation> T getAnnotationAlias(AnnotatedElement annotatedElement, Class<T> cls) {
        Annotation annotation = getAnnotation(annotatedElement, cls);
        if (annotation == null) {
            return null;
        }
        return (T) aggregatingFromAnnotation(annotation).synthesize(cls);
    }

    public static <T> T getAnnotationValue(AnnotatedElement annotatedElement, Class<? extends Annotation> cls) throws UtilException {
        return (T) getAnnotationValue(annotatedElement, cls, "value");
    }

    public static Map<String, Object> getAnnotationValueMap(AnnotatedElement annotatedElement, Class<? extends Annotation> cls) throws UtilException, SecurityException {
        Annotation annotation = getAnnotation(annotatedElement, cls);
        if (annotation == null) {
            return null;
        }
        Method[] methods = ReflectUtil.getMethods(cls, new Filter() { // from class: cn.hutool.core.annotation.l0
            @Override // cn.hutool.core.lang.Filter
            public final boolean accept(Object obj) {
                return AnnotationUtil.lambda$getAnnotationValueMap$1((Method) obj);
            }
        });
        HashMap map = new HashMap(methods.length, 1.0f);
        for (Method method : methods) {
            map.put(method.getName(), ReflectUtil.invoke(annotation, method, new Object[0]));
        }
        return map;
    }

    public static Annotation[] getAnnotations(AnnotatedElement annotatedElement, boolean z2) {
        return getAnnotations(annotatedElement, z2, (Predicate<Annotation>) null);
    }

    public static <T> T[] getCombinationAnnotations(AnnotatedElement annotatedElement, Class<T> cls) {
        return (T[]) getAnnotations(annotatedElement, true, (Class) cls);
    }

    public static RetentionPolicy getRetentionPolicy(Class<? extends Annotation> cls) {
        Retention retention = (Retention) cls.getAnnotation(Retention.class);
        return retention == null ? RetentionPolicy.CLASS : retention.value();
    }

    public static <T extends Annotation> T getSynthesizedAnnotation(final Class<T> cls, Annotation... annotationArr) {
        return (T) Opt.ofNullable(annotationArr).filter(new Predicate() { // from class: cn.hutool.core.annotation.n0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ArrayUtil.isNotEmpty((Object[]) obj);
            }
        }).map(new Function() { // from class: cn.hutool.core.annotation.o0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AnnotationUtil.aggregatingFromAnnotationWithMeta((Annotation[]) obj);
            }
        }).map(new Function() { // from class: cn.hutool.core.annotation.p0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AnnotationUtil.lambda$getSynthesizedAnnotation$2(cls, (SynthesizedAggregateAnnotation) obj);
            }
        }).get();
    }

    public static ElementType[] getTargetType(Class<? extends Annotation> cls) {
        Target target = (Target) cls.getAnnotation(Target.class);
        return target == null ? new ElementType[]{ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.CONSTRUCTOR, ElementType.LOCAL_VARIABLE, ElementType.ANNOTATION_TYPE, ElementType.PACKAGE} : target.value();
    }

    public static boolean hasAnnotation(AnnotatedElement annotatedElement, Class<? extends Annotation> cls) {
        return getAnnotation(annotatedElement, cls) != null;
    }

    public static boolean isAttributeMethod(Method method) {
        return method.getParameterCount() == 0 && method.getReturnType() != Void.TYPE;
    }

    public static boolean isDocumented(Class<? extends Annotation> cls) {
        return cls.isAnnotationPresent(Documented.class);
    }

    public static boolean isInherited(Class<? extends Annotation> cls) {
        return cls.isAnnotationPresent(Inherited.class);
    }

    public static boolean isJdkMetaAnnotation(Class<? extends Annotation> cls) {
        return META_ANNOTATIONS.contains(cls);
    }

    public static boolean isNotJdkMateAnnotation(Class<? extends Annotation> cls) {
        return !isJdkMetaAnnotation(cls);
    }

    public static boolean isSynthesizedAnnotation(Annotation annotation) {
        return SynthesizedAnnotationProxy.isProxyAnnotation(annotation.getClass());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Annotation lambda$getAllSynthesizedAnnotations$4(Class cls, Annotation annotation) {
        return getSynthesizedAnnotation(cls, annotation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getAnnotationValueMap$1(Method method) {
        if (!ArrayUtil.isEmpty((Object[]) method.getParameterTypes())) {
            return false;
        }
        String name = method.getName();
        return ("hashCode".equals(name) || "toString".equals(name) || "annotationType".equals(name)) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getAnnotations$0(Class cls, Annotation annotation) {
        return cls == null || cls.isAssignableFrom(annotation.getClass());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Annotation lambda$getSynthesizedAnnotation$2(Class cls, SynthesizedAggregateAnnotation synthesizedAggregateAnnotation) {
        return synthesizedAggregateAnnotation.synthesize(cls);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Annotation lambda$getSynthesizedAnnotation$3(Class cls, Annotation annotation) {
        return getSynthesizedAnnotation(cls, annotation);
    }

    public static List<Annotation> scanClass(Class<?> cls) {
        return AnnotationScanner.TYPE_HIERARCHY.getAnnotationsIfSupport(cls);
    }

    public static List<Annotation> scanMetaAnnotation(Class<? extends Annotation> cls) {
        return AnnotationScanner.DIRECTLY_AND_META_ANNOTATION.getAnnotationsIfSupport(cls);
    }

    public static List<Annotation> scanMethod(Method method) {
        return AnnotationScanner.TYPE_HIERARCHY.getAnnotationsIfSupport(method);
    }

    public static void setValue(Annotation annotation, String str, Object obj) {
        ((Map) ReflectUtil.getFieldValue(Proxy.getInvocationHandler(annotation), "memberValues")).put(str, obj);
    }

    public static CombinationAnnotationElement toCombination(AnnotatedElement annotatedElement) {
        return annotatedElement instanceof CombinationAnnotationElement ? (CombinationAnnotationElement) annotatedElement : new CombinationAnnotationElement(annotatedElement);
    }

    public static <T> T getAnnotationValue(AnnotatedElement annotatedElement, Class<? extends Annotation> cls, String str) throws UtilException {
        Method methodOfObj;
        Annotation annotation = getAnnotation(annotatedElement, cls);
        if (annotation == null || (methodOfObj = ReflectUtil.getMethodOfObj(annotation, str, new Object[0])) == null) {
            return null;
        }
        return (T) ReflectUtil.invoke(annotation, methodOfObj, new Object[0]);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T[] getAnnotations(AnnotatedElement annotatedElement, boolean z2, final Class<T> cls) {
        Annotation[] annotations = getAnnotations(annotatedElement, z2, (Predicate<Annotation>) new Predicate() { // from class: cn.hutool.core.annotation.k0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AnnotationUtil.lambda$getAnnotations$0(cls, (Annotation) obj);
            }
        });
        T[] tArr = (T[]) ArrayUtil.newArray(cls, annotations.length);
        for (int i2 = 0; i2 < annotations.length; i2++) {
            tArr[i2] = annotations[i2];
        }
        return tArr;
    }

    public static <A extends Annotation, R> R getAnnotationValue(AnnotatedElement annotatedElement, Func1<A, R> func1) {
        if (func1 == null) {
            return null;
        }
        SerializedLambda serializedLambdaResolve = LambdaUtil.resolve(func1);
        String instantiatedMethodType = serializedLambdaResolve.getInstantiatedMethodType();
        return (R) getAnnotationValue(annotatedElement, ClassUtil.loadClass(CharSequenceUtil.sub(instantiatedMethodType, 2, CharSequenceUtil.indexOf(instantiatedMethodType, ';'))), serializedLambdaResolve.getImplMethodName());
    }

    public static Annotation[] getAnnotations(AnnotatedElement annotatedElement, boolean z2, final Predicate<Annotation> predicate) {
        if (annotatedElement == null) {
            return null;
        }
        if (z2) {
            if (predicate == null) {
                return toCombination(annotatedElement).getAnnotations();
            }
            return CombinationAnnotationElement.of(annotatedElement, predicate).getAnnotations();
        }
        Annotation[] annotations = annotatedElement.getAnnotations();
        if (predicate == null) {
            return annotations;
        }
        predicate.getClass();
        return (Annotation[]) ArrayUtil.filter(annotations, new Filter() { // from class: cn.hutool.core.annotation.m0
            @Override // cn.hutool.core.lang.Filter
            public final boolean accept(Object obj) {
                return predicate.test((Annotation) obj);
            }
        });
    }

    public static <T extends Annotation> T getSynthesizedAnnotation(AnnotatedElement annotatedElement, final Class<T> cls) {
        T t2 = (T) annotatedElement.getAnnotation(cls);
        return ObjectUtil.isNotNull(t2) ? t2 : (T) AnnotationScanner.DIRECTLY.getAnnotationsIfSupport(annotatedElement).stream().map(new Function() { // from class: cn.hutool.core.annotation.q0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AnnotationUtil.lambda$getSynthesizedAnnotation$3(cls, (Annotation) obj);
            }
        }).filter(new Predicate() { // from class: cn.hutool.core.annotation.h0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return g0.a((Annotation) obj);
            }
        }).findFirst().orElse(null);
    }
}
