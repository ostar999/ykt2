package cn.hutool.core.convert.impl;

import cn.hutool.core.convert.AbstractConverter;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.convert.ConvertException;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ObjectUtil;
import java.util.function.Function;

/* loaded from: classes.dex */
public class PrimitiveConverter extends AbstractConverter<Object> {
    private static final long serialVersionUID = 1;
    private final Class<?> targetType;

    public PrimitiveConverter(Class<?> cls) {
        if (cls == null) {
            throw new NullPointerException("PrimitiveConverter not allow null target type!");
        }
        if (cls.isPrimitive()) {
            this.targetType = cls;
            return;
        }
        throw new IllegalArgumentException(StrPool.BRACKET_START + cls + "] is not a primitive class!");
    }

    public static Object convert(Object obj, Class<?> cls, Function<Object, String> function) {
        if (Byte.TYPE == cls) {
            return ObjectUtil.defaultIfNull((int) NumberConverter.convert(obj, Byte.class, function), 0);
        }
        if (Short.TYPE == cls) {
            return ObjectUtil.defaultIfNull((int) NumberConverter.convert(obj, Short.class, function), 0);
        }
        if (Integer.TYPE == cls) {
            return ObjectUtil.defaultIfNull((int) NumberConverter.convert(obj, Integer.class, function), 0);
        }
        if (Long.TYPE == cls) {
            return ObjectUtil.defaultIfNull((int) NumberConverter.convert(obj, Long.class, function), 0);
        }
        if (Float.TYPE == cls) {
            return ObjectUtil.defaultIfNull((int) NumberConverter.convert(obj, Float.class, function), 0);
        }
        if (Double.TYPE == cls) {
            return ObjectUtil.defaultIfNull((int) NumberConverter.convert(obj, Double.class, function), 0);
        }
        if (Character.TYPE == cls) {
            return Convert.convert(Character.class, obj);
        }
        if (Boolean.TYPE == cls) {
            return Convert.convert(Boolean.class, obj);
        }
        throw new ConvertException("Unsupported target type: {}", cls);
    }

    @Override // cn.hutool.core.convert.AbstractConverter
    public Object convertInternal(Object obj) {
        return convert(obj, this.targetType, new Function() { // from class: v.y
            @Override // java.util.function.Function
            public final Object apply(Object obj2) {
                return this.f28250c.convertToStr(obj2);
            }
        });
    }

    @Override // cn.hutool.core.convert.AbstractConverter
    public String convertToStr(Object obj) {
        return CharSequenceUtil.trim(super.convertToStr(obj));
    }

    @Override // cn.hutool.core.convert.AbstractConverter
    public Class<Object> getTargetType() {
        return this.targetType;
    }
}
