package cn.hutool.core.bean.copier;

import java.lang.reflect.Type;

/* loaded from: classes.dex */
public interface ValueProvider<T> {
    boolean containsKey(T t2);

    Object value(T t2, Type type);
}
