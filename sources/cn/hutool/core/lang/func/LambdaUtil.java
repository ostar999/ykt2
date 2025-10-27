package cn.hutool.core.lang.func;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.func.LambdaUtil;
import cn.hutool.core.map.WeakConcurrentMap;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.util.function.Function;

/* loaded from: classes.dex */
public class LambdaUtil {
    private static final WeakConcurrentMap<String, SerializedLambda> cache = new WeakConcurrentMap<>();

    private static SerializedLambda _resolve(final Serializable serializable) {
        return cache.computeIfAbsent((WeakConcurrentMap<String, SerializedLambda>) serializable.getClass().getName(), (Function<? super WeakConcurrentMap<String, SerializedLambda>, ? extends SerializedLambda>) new Function() { // from class: c0.d
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return LambdaUtil.lambda$_resolve$0(serializable, (String) obj);
            }
        });
    }

    private static void checkLambdaTypeCanGetClass(int i2) {
        if (i2 != 5 && i2 != 6) {
            throw new IllegalArgumentException("该lambda不是合适的方法引用");
        }
    }

    public static <T> String getFieldName(Func1<T, ?> func1) throws IllegalArgumentException {
        return BeanUtil.getFieldName(getMethodName(func1));
    }

    public static <P> String getMethodName(Func1<P, ?> func1) {
        return resolve(func1).getImplMethodName();
    }

    public static <R> Class<R> getRealClass(Func0<?> func0) {
        SerializedLambda serializedLambdaResolve = resolve(func0);
        checkLambdaTypeCanGetClass(serializedLambdaResolve.getImplMethodKind());
        return ClassUtil.loadClass(serializedLambdaResolve.getImplClass());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ SerializedLambda lambda$_resolve$0(Serializable serializable, String str) {
        return (SerializedLambda) ReflectUtil.invoke(serializable, "writeReplace", new Object[0]);
    }

    public static <T> SerializedLambda resolve(Func1<T, ?> func1) {
        return _resolve(func1);
    }

    public static <T> String getFieldName(Func0<T> func0) throws IllegalArgumentException {
        return BeanUtil.getFieldName(getMethodName(func0));
    }

    public static <R> String getMethodName(Func0<R> func0) {
        return resolve(func0).getImplMethodName();
    }

    public static <R> SerializedLambda resolve(Func0<R> func0) {
        return _resolve(func0);
    }

    public static <P, R> Class<P> getRealClass(Func1<P, R> func1) {
        SerializedLambda serializedLambdaResolve = resolve(func1);
        checkLambdaTypeCanGetClass(serializedLambdaResolve.getImplMethodKind());
        String instantiatedMethodType = serializedLambdaResolve.getInstantiatedMethodType();
        return ClassUtil.loadClass(CharSequenceUtil.sub(instantiatedMethodType, 2, CharSequenceUtil.indexOf(instantiatedMethodType, ';')));
    }
}
