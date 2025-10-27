package cn.hutool.core.convert.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.BeanCopier;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.bean.copier.ValueProvider;
import cn.hutool.core.convert.AbstractConverter;
import cn.hutool.core.convert.ConvertException;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.map.MapProxy;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.TypeUtil;
import java.lang.reflect.Type;
import java.util.Map;

/* loaded from: classes.dex */
public class BeanConverter<T> extends AbstractConverter<T> {
    private static final long serialVersionUID = 1;
    private final Class<T> beanClass;
    private final Type beanType;
    private final CopyOptions copyOptions;

    public BeanConverter(Type type) {
        this(type, CopyOptions.create().setIgnoreError(true));
    }

    @Override // cn.hutool.core.convert.AbstractConverter
    public T convertInternal(Object obj) throws UtilException {
        for (Class<?> cls : this.beanClass.getInterfaces()) {
            if ("cn.hutool.json.JSONBeanParser".equals(cls.getName())) {
                T t2 = (T) ReflectUtil.newInstanceIfPossible(this.beanClass);
                ReflectUtil.invoke(t2, "parse", obj);
                return t2;
            }
        }
        boolean z2 = obj instanceof Map;
        if (z2 || (obj instanceof ValueProvider) || BeanUtil.isBean(obj.getClass())) {
            return (z2 && this.beanClass.isInterface()) ? (T) MapProxy.create((Map) obj).toProxyBean(this.beanClass) : (T) BeanCopier.create(obj, ReflectUtil.newInstanceIfPossible(this.beanClass), this.beanType, this.copyOptions).copy();
        }
        if (obj instanceof byte[]) {
            return (T) ObjectUtil.deserialize((byte[]) obj, new Class[0]);
        }
        if (StrUtil.isEmptyIfStr(obj)) {
            return null;
        }
        throw new ConvertException("Unsupported source type: {}", obj.getClass());
    }

    @Override // cn.hutool.core.convert.AbstractConverter
    public Class<T> getTargetType() {
        return this.beanClass;
    }

    public BeanConverter(Class<T> cls) {
        this(cls, CopyOptions.create().setIgnoreError(true));
    }

    public BeanConverter(Type type, CopyOptions copyOptions) {
        this.beanType = type;
        this.beanClass = (Class<T>) TypeUtil.getClass(type);
        this.copyOptions = copyOptions;
    }
}
