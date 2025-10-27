package cn.hutool.core.annotation;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ReflectUtil;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class AnnotationProxy<T extends Annotation> implements Annotation, InvocationHandler, Serializable {
    private static final long serialVersionUID = 1;
    private final T annotation;
    private final Map<String, Object> attributes = initAttributes();
    private final Class<T> type;

    public AnnotationProxy(T t2) {
        this.annotation = t2;
        this.type = (Class<T>) t2.annotationType();
    }

    private Map<String, Object> initAttributes() throws SecurityException, IllegalArgumentException {
        Method[] methods = ReflectUtil.getMethods(this.type);
        HashMap map = new HashMap(methods.length, 1.0f);
        for (Method method : methods) {
            if (!method.isSynthetic()) {
                map.put(method.getName(), ReflectUtil.invoke(this.annotation, method, new Object[0]));
            }
        }
        return map;
    }

    @Override // java.lang.annotation.Annotation
    public Class<? extends Annotation> annotationType() {
        return this.type;
    }

    @Override // java.lang.reflect.InvocationHandler
    public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
        Alias alias = (Alias) method.getAnnotation(Alias.class);
        if (alias != null) {
            String strValue = alias.value();
            if (CharSequenceUtil.isNotBlank(strValue)) {
                if (this.attributes.containsKey(strValue)) {
                    return this.attributes.get(strValue);
                }
                throw new IllegalArgumentException(CharSequenceUtil.format("No method for alias: [{}]", strValue));
            }
        }
        Object obj2 = this.attributes.get(method.getName());
        return obj2 != null ? obj2 : method.invoke(this, objArr);
    }
}
