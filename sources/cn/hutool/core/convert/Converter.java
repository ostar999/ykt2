package cn.hutool.core.convert;

/* loaded from: classes.dex */
public interface Converter<T> {
    T convert(Object obj, T t2) throws IllegalArgumentException;

    T convertWithCheck(Object obj, T t2, boolean z2);
}
