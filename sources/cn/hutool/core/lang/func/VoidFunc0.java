package cn.hutool.core.lang.func;

import java.io.Serializable;

@FunctionalInterface
/* loaded from: classes.dex */
public interface VoidFunc0 extends Serializable {
    void call() throws Exception;

    void callWithRuntimeException();
}
