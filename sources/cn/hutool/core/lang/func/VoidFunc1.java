package cn.hutool.core.lang.func;

import java.io.Serializable;

@FunctionalInterface
/* loaded from: classes.dex */
public interface VoidFunc1<P> extends Serializable {
    void call(P p2) throws Exception;

    void callWithRuntimeException(P p2);
}
