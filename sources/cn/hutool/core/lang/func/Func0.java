package cn.hutool.core.lang.func;

import java.io.Serializable;

@FunctionalInterface
/* loaded from: classes.dex */
public interface Func0<R> extends Serializable {
    R call() throws Exception;

    R callWithRuntimeException();
}
