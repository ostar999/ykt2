package cn.hutool.core.lang.func;

import java.io.Serializable;

@FunctionalInterface
/* loaded from: classes.dex */
public interface Func<P, R> extends Serializable {
    R call(P... pArr) throws Exception;

    R callWithRuntimeException(P... pArr);
}
