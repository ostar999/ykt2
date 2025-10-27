package cn.hutool.core.bean.copier;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.copier.Copier;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Map;

/* loaded from: classes.dex */
public class BeanCopier<T> implements Copier<T>, Serializable {
    private static final long serialVersionUID = 1;
    private final Copier<T> copier;

    public BeanCopier(Object obj, T t2, Type type, CopyOptions copyOptions) throws IllegalArgumentException {
        Assert.notNull(obj, "Source bean must be not null!", new Object[0]);
        Assert.notNull(t2, "Target bean must be not null!", new Object[0]);
        this.copier = obj instanceof Map ? t2 instanceof Map ? new MapToMapCopier((Map) obj, (Map) t2, type, copyOptions) : new MapToBeanCopier<>((Map) obj, t2, type, copyOptions) : obj instanceof ValueProvider ? new ValueProviderToBeanCopier<>((ValueProvider) obj, t2, type, copyOptions) : t2 instanceof Map ? new BeanToMapCopier(obj, (Map) t2, type, copyOptions) : new BeanToBeanCopier<>(obj, t2, type, copyOptions);
    }

    public static <T> BeanCopier<T> create(Object obj, T t2, CopyOptions copyOptions) {
        return create(obj, t2, t2.getClass(), copyOptions);
    }

    @Override // cn.hutool.core.lang.copier.Copier
    public T copy() {
        return this.copier.copy();
    }

    public static <T> BeanCopier<T> create(Object obj, T t2, Type type, CopyOptions copyOptions) {
        return new BeanCopier<>(obj, t2, type, copyOptions);
    }
}
