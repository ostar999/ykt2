package cn.hutool.core.convert;

import java.lang.reflect.Type;

@FunctionalInterface
/* loaded from: classes.dex */
public interface TypeConverter {
    Object convert(Type type, Object obj);
}
