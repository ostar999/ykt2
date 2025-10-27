package cn.hutool.core.annotation;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Opt;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes.dex */
public class SynthesizedAnnotationProxy implements InvocationHandler {
    private final SynthesizedAnnotation annotation;
    private final AnnotationAttributeValueProvider annotationAttributeValueProvider;
    private final Map<String, BiFunction<Method, Object[], Object>> methods;

    public interface SyntheticProxyAnnotation extends SynthesizedAnnotation {
        SynthesizedAnnotation getSynthesizedAnnotation();
    }

    public SynthesizedAnnotationProxy(AnnotationAttributeValueProvider annotationAttributeValueProvider, SynthesizedAnnotation synthesizedAnnotation) throws IllegalArgumentException {
        Assert.notNull(annotationAttributeValueProvider, "annotationAttributeValueProvider must not null", new Object[0]);
        Assert.notNull(synthesizedAnnotation, "annotation must not null", new Object[0]);
        this.annotationAttributeValueProvider = annotationAttributeValueProvider;
        this.annotation = synthesizedAnnotation;
        this.methods = new HashMap(9);
        loadMethods();
    }

    public static <T extends Annotation> T create(Class<T> cls, AnnotationAttributeValueProvider annotationAttributeValueProvider, SynthesizedAnnotation synthesizedAnnotation) {
        if (ObjectUtil.isNull(synthesizedAnnotation)) {
            return null;
        }
        SynthesizedAnnotationProxy synthesizedAnnotationProxy = new SynthesizedAnnotationProxy(annotationAttributeValueProvider, synthesizedAnnotation);
        if (ObjectUtil.isNull(synthesizedAnnotation)) {
            return null;
        }
        return (T) Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls, SyntheticProxyAnnotation.class}, synthesizedAnnotationProxy);
    }

    public static boolean isProxyAnnotation(Class<?> cls) {
        return ClassUtil.isAssignable(SyntheticProxyAnnotation.class, cls);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$invoke$0(Method method, Object[] objArr, BiFunction biFunction) {
        return biFunction.apply(method, objArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$invoke$1(Method method, Object[] objArr) {
        return ReflectUtil.invoke(this, method, objArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$loadMethods$10(Method method, Object[] objArr) {
        throw new UnsupportedOperationException("proxied annotation can not reset attributes");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$loadMethods$11(Method method, Object[] objArr) {
        return this.annotation.getAttributeValue((String) objArr[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$loadMethods$12(Method method, Object[] objArr) {
        return this.annotation.annotationType();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$loadMethods$13(Method method, Object[] objArr) {
        return proxyAttributeValue(method);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$loadMethods$2(Method method, Object[] objArr) {
        return proxyToString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$loadMethods$3(Method method, Object[] objArr) {
        return Integer.valueOf(proxyHashCode());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$loadMethods$4(Method method, Object[] objArr) {
        return proxyGetSynthesizedAnnotation();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$loadMethods$5(Method method, Object[] objArr) {
        return this.annotation.getRoot();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$loadMethods$6(Method method, Object[] objArr) {
        return Integer.valueOf(this.annotation.getVerticalDistance());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$loadMethods$7(Method method, Object[] objArr) {
        return Integer.valueOf(this.annotation.getHorizontalDistance());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$loadMethods$8(Method method, Object[] objArr) {
        return Boolean.valueOf(this.annotation.hasAttribute((String) objArr[0], (Class) objArr[1]));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$loadMethods$9(Method method, Object[] objArr) {
        return this.annotation.getAttributes();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$proxyToString$14(Method method) {
        return CharSequenceUtil.format("{}={}", method.getName(), proxyAttributeValue(method));
    }

    private Object proxyAttributeValue(Method method) {
        return this.annotationAttributeValueProvider.getAttributeValue(method.getName(), method.getReturnType());
    }

    private Object proxyGetSynthesizedAnnotation() {
        return this.annotation;
    }

    private int proxyHashCode() {
        return Objects.hash(this.annotationAttributeValueProvider, this.annotation);
    }

    private String proxyToString() {
        return CharSequenceUtil.format("@{}({})", this.annotation.annotationType().getName(), (String) Stream.of((Object[]) ClassUtil.getDeclaredMethods(this.annotation.getAnnotation().annotationType())).filter(new g1()).map(new Function() { // from class: cn.hutool.core.annotation.c2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f2327c.lambda$proxyToString$14((Method) obj);
            }
        }).collect(Collectors.joining(", ")));
    }

    @Override // java.lang.reflect.InvocationHandler
    public Object invoke(Object obj, final Method method, final Object[] objArr) throws Throwable {
        return Opt.ofNullable(this.methods.get(method.getName())).map(new Function() { // from class: cn.hutool.core.annotation.a2
            @Override // java.util.function.Function
            public final Object apply(Object obj2) {
                return SynthesizedAnnotationProxy.lambda$invoke$0(method, objArr, (BiFunction) obj2);
            }
        }).orElseGet(new Supplier() { // from class: cn.hutool.core.annotation.b2
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f2324a.lambda$invoke$1(method, objArr);
            }
        });
    }

    public void loadMethods() {
        this.methods.put("toString", new BiFunction() { // from class: cn.hutool.core.annotation.d2
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f2328a.lambda$loadMethods$2((Method) obj, (Object[]) obj2);
            }
        });
        this.methods.put("hashCode", new BiFunction() { // from class: cn.hutool.core.annotation.g2
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f2336a.lambda$loadMethods$3((Method) obj, (Object[]) obj2);
            }
        });
        this.methods.put("getSynthesizedAnnotation", new BiFunction() { // from class: cn.hutool.core.annotation.h2
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f2337a.lambda$loadMethods$4((Method) obj, (Object[]) obj2);
            }
        });
        this.methods.put("getRoot", new BiFunction() { // from class: cn.hutool.core.annotation.i2
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f2341a.lambda$loadMethods$5((Method) obj, (Object[]) obj2);
            }
        });
        this.methods.put("getVerticalDistance", new BiFunction() { // from class: cn.hutool.core.annotation.j2
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f2344a.lambda$loadMethods$6((Method) obj, (Object[]) obj2);
            }
        });
        this.methods.put("getHorizontalDistance", new BiFunction() { // from class: cn.hutool.core.annotation.v1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f2372a.lambda$loadMethods$7((Method) obj, (Object[]) obj2);
            }
        });
        this.methods.put("hasAttribute", new BiFunction() { // from class: cn.hutool.core.annotation.w1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f2373a.lambda$loadMethods$8((Method) obj, (Object[]) obj2);
            }
        });
        this.methods.put("getAttributes", new BiFunction() { // from class: cn.hutool.core.annotation.x1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f2375a.lambda$loadMethods$9((Method) obj, (Object[]) obj2);
            }
        });
        this.methods.put("setAttribute", new BiFunction() { // from class: cn.hutool.core.annotation.y1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return SynthesizedAnnotationProxy.lambda$loadMethods$10((Method) obj, (Object[]) obj2);
            }
        });
        this.methods.put("getAttributeValue", new BiFunction() { // from class: cn.hutool.core.annotation.z1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f2377a.lambda$loadMethods$11((Method) obj, (Object[]) obj2);
            }
        });
        this.methods.put("annotationType", new BiFunction() { // from class: cn.hutool.core.annotation.e2
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f2330a.lambda$loadMethods$12((Method) obj, (Object[]) obj2);
            }
        });
        for (Method method : ClassUtil.getDeclaredMethods(this.annotation.getAnnotation().annotationType())) {
            this.methods.put(method.getName(), new BiFunction() { // from class: cn.hutool.core.annotation.f2
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return this.f2333a.lambda$loadMethods$13((Method) obj, (Object[]) obj2);
                }
            });
        }
    }

    public static <T extends Annotation> T create(Class<T> cls, SynthesizedAnnotation synthesizedAnnotation) {
        return (T) create(cls, synthesizedAnnotation, synthesizedAnnotation);
    }
}
