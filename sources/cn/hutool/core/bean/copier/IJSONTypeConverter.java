package cn.hutool.core.bean.copier;

import java.lang.reflect.Type;

/* loaded from: classes.dex */
public interface IJSONTypeConverter {
    <T> T toBean(Type type);
}
