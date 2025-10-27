package cn.hutool.core.convert;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.ClassUtil;
import java.io.Serializable;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class AbstractConverter<T> implements Converter<T>, Serializable {
    private static final long serialVersionUID = 1;

    @Override // cn.hutool.core.convert.Converter
    public T convert(Object obj, T t2) {
        Class targetType = getTargetType();
        if (targetType == null && t2 == null) {
            throw new NullPointerException(CharSequenceUtil.format("[type] and [defaultValue] are both null for Converter [{}], we can not know what type to convert !", getClass().getName()));
        }
        if (targetType == null) {
            targetType = t2.getClass();
        }
        if (obj == null) {
            return t2;
        }
        if (t2 != null && !targetType.isInstance(t2)) {
            throw new IllegalArgumentException(CharSequenceUtil.format("Default value [{}]({}) is not the instance of [{}]", t2, t2.getClass(), targetType));
        }
        if (targetType.isInstance(obj) && !Map.class.isAssignableFrom(targetType)) {
            return (T) targetType.cast(obj);
        }
        T tConvertInternal = convertInternal(obj);
        return tConvertInternal == null ? t2 : tConvertInternal;
    }

    public abstract T convertInternal(Object obj);

    public T convertQuietly(Object obj, T t2) {
        try {
            return convert(obj, t2);
        } catch (Exception unused) {
            return t2;
        }
    }

    public String convertToStr(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj instanceof CharSequence ? obj.toString() : ArrayUtil.isArray(obj) ? ArrayUtil.toString(obj) : CharUtil.isChar(obj) ? CharUtil.toString(((Character) obj).charValue()) : obj.toString();
    }

    @Override // cn.hutool.core.convert.Converter
    public /* synthetic */ Object convertWithCheck(Object obj, Object obj2, boolean z2) {
        return a.a(this, obj, obj2, z2);
    }

    public Class<T> getTargetType() {
        return (Class<T>) ClassUtil.getTypeArgument(getClass());
    }
}
